package com.wutianyi.google;

import com.google.common.base.CharMatcher;
import org.junit.Test;

/**
 * Created by hanjiewu on 2016/1/28.
 */
public class StringsExplained {
	@Test
	public void charMatcherTest() {
		System.out.println(CharMatcher.JAVA_ISO_CONTROL.removeFrom("abc是中国人"));
		System.out.println(CharMatcher.DIGIT.retainFrom("abc12d32"));
	}
}
