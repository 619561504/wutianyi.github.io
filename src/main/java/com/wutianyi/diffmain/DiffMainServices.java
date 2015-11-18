/**
 * 2014-上午11:35:21
 */
package com.wutianyi.diffmain;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.wutianyi.diffmain.diff_match_patch.Diff;
import com.wutianyi.diffmain.diff_match_patch.Operation;

/**
 * @author hanjiewu
 *
 *
 *         hadoop + mahout + DIFF_MAIN 对字符串进行归一化处理
 */
public class DiffMainServices {

	private static diff_match_patch dmp = new diff_match_patch();

	public static final Map<Character, Character> REGULARIZE_CHARS = Maps.newHashMap();

	static {
		REGULARIZE_CHARS.put('【', '[');
		REGULARIZE_CHARS.put('】', ']');
	}

	public static final String REPLACE_CHARS = "〤〤〤〤〤";

	/**
	 * 比较两个字符串，如果相似则获取两者的模式，相似是根据不同的来计算的
	 * 
	 * @param m1
	 * @param m2
	 * @param distinctCount
	 * @return
	 */
	public static String diffMain(String m1, String m2, int distinctCount) {
		m1 = regularizes(m1);
		m2 = regularizes(m2);

		int maxLen = m1.length() > m2.length() ? m1.length() : m2.length();

		List<Diff> diffs = dmp.diff_main(m1, m2);
		StringBuilder builder = new StringBuilder();

		boolean isAppend = false;
		int modifys = 0;
		Diff lastDiff = null;

		// String debug = "";

		for (Diff diff : diffs) {
			if (diff.operation == Operation.EQUAL) {

				if (isAppend && (diff.text.length() == 1
						|| org.apache.commons.lang3.StringUtils.isAsciiPrintable(diff.text))) {
					lastDiff = diff;
					// debug += diff.text;
					continue;
				}

				if (isAppend) {
					if (null != lastDiff) {
						// builder.append(lastDiff.text);
						lastDiff = null;
					}
					builder.append(REPLACE_CHARS);
					isAppend = false;
					++modifys;
					// System.out.println(debug);
					// debug = "";
				}
				builder.append(diff.text);
			} else {
				isAppend = true;
				// debug += diff.text;
			}
		}
		if (null != lastDiff) {
			builder.append(lastDiff.text);
		}
		if (isAppend) {
			builder.append(REPLACE_CHARS);
		}
		System.out.println("modifiys: " + modifys);
		String m = builder.toString();
		if (m.length() < (maxLen / 2) || StringUtils.equals(m, REPLACE_CHARS) || modifys > distinctCount) {
			return null;
		}

		return builder.toString();
	}

	/**
	 * 比较两个字符串，如果相似则获取两者的模式，相似是根据两者的相同部分来计算的
	 * 
	 * @param m1
	 * @param m2
	 * @param baseRate
	 *            基础比率，根据该比率和文本长度，计算最低的相似比率
	 * @return
	 */
	public static String someMain(String m1, String m2, int baseRate) {
		String model1 = m1;
		String model2 = m2;
		m1 = regularizes(m1);
		m2 = regularizes(m2);

		int someLen = 0;

		StringBuilder builder = new StringBuilder();
		List<Diff> diffs = dmp.diff_main(m1, m2);
		boolean isAppend = false;
		Diff lastDiff = null;
		// FIXME!需要引入对词性不一致的判断
		for (Diff diff : diffs) {
			int len = diff.text.length();

			if (diff.operation == Operation.EQUAL) {

				if (isAppend && (diff.text.length() == 1
						|| org.apache.commons.lang3.StringUtils.isAsciiPrintable(diff.text))) {
					lastDiff = diff;
					continue;
				}
				if (isAppend) {
					if (null != lastDiff) {
						lastDiff = null;
					}
					builder.append(REPLACE_CHARS);
					isAppend = false;
				}
				builder.append(diff.text);
				someLen += len;
			} else {
				isAppend = true;
			}
		}
		if (null != lastDiff) {
			builder.append(lastDiff.text);
		}
		if (isAppend) {
			builder.append(REPLACE_CHARS);
		}
		int d1 = someLen == 0 ? 100 : (m1.length() - someLen) * 100 / someLen;
		int d2 = someLen == 0 ? 100 : (m2.length() - someLen) * 100 / someLen;

		int rate1 = baseRate - m1.length() / 50 * 5;
		int rate2 = baseRate - m2.length() / 50 * 5;

		rate1 = rate1 < 33 ? 33 : rate1;
		rate2 = rate2 < 33 ? 33 : rate2;

		if (d1 > rate1 || d2 > rate2) {
			return null;
		}

		return model1.length() > model2.length() ? model2 : model1;

	}

	/**
	 * 进行字符规格化（全角转半角，大写转小写处理）
	 * 
	 * @param input
	 * @return char
	 */
	public static char regularize(char input) {
		if (input == 12288) {
			input = (char) 32;

		} else if (input > 65280 && input < 65375) {
			input = (char) (input - 65248);

		} else if (input >= 'A' && input <= 'Z') {
			input += 32;
		} else if (REGULARIZE_CHARS.containsKey(input)) {
			input = REGULARIZE_CHARS.get(input);
		}

		return input;
	}

	/**
	 * 对input进行统一处理
	 * 
	 * @param input
	 * @return
	 */
	public static String regularizes(String input) {
		if (StringUtils.isBlank(input)) {
			return input;
		}
		StringBuilder builder = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {

			char c = regularize(input.charAt(i));

			if (CharUtils.isAsciiNumeric(c)) {
				temp.append(c);
				continue;
			} else if (CharUtils.isAscii(c) && temp.length() > 0) {
				temp.append(c);
				continue;
			}

			if (temp.length() > 0) {
				char lastC = temp.charAt(temp.length() - 1);
				int len = !CharUtils.isAsciiAlphanumeric(lastC) ? temp.length() - 1 : temp.length();

				if (len >= 4) {
					builder.append(REPLACE_CHARS);
					if (len != temp.length()) {
						builder.append(lastC);
					}
				} else {
					builder.append(temp.toString());
				}
				temp.delete(0, temp.length());
			}

			builder.append(c);
		}

		if (temp.length() > 0) {
			char lastC = temp.charAt(temp.length() - 1);
			int len = !CharUtils.isAsciiAlphanumeric(lastC) ? temp.length() - 1 : temp.length();

			if (len >= 4) {
				builder.append(REPLACE_CHARS);
				if (len != temp.length()) {
					builder.append(lastC);
				}
			} else {
				builder.append(temp.toString());
			}
			temp.delete(0, temp.length());
		}

		return builder.toString();
	}

	public static void main(String[] args) {
		String sms1 = "王晓春  女士 ，根据我行授信政策，对您用卡进行综合评估，您 个人信用卡 固定额度已调至RMB {1} 元。提醒：我行信用卡支持取现（除金通卡），每卡每日最多可取 {2}";
		String sms2 = "王春麟  女士 ，根据我行授信政策，对您用卡进行综合评估，您 个人信用卡 固定额度已调至RMB {1} 元。提醒：我行信用卡支持取现（除金通卡），每卡每日最多可取 {2}";
		System.out.println(someMain(sms1, sms2, 100));
	}
}
