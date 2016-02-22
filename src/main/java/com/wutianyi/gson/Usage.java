/**
 * hanjiewu 
 * 2015年11月17日:下午4:40:26
 */
package com.wutianyi.gson;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author hanjiewu
 *
 */
public class Usage {

	public static void main(String[] args) {
		Gson gson = new Gson();
		System.out.println(gson.toJson(1));
		System.out.println(gson.toJson("abcd"));
		System.out.println(gson.toJson(new Long(10)));
		int[] values = { 1 };
		System.out.println(gson.toJson(values));

		int one = gson.fromJson("1", int.class);
		String[] anotherStr = gson.fromJson("[\"abc\"]", String[].class);
		System.out.println(one);
		System.out.println(StringUtils.join(anotherStr));

		Type collectionType = new TypeToken<Collection<Integer>>() {
		}.getType();
		Collection<Integer> ints2 = gson.fromJson("[1,2,3,4]", collectionType);
		System.out.println(ints2);
	}
}
