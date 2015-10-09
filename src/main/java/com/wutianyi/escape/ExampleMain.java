/**
 * 2015年10月8日hanjiewu
 */
package com.wutianyi.escape;

import com.coverity.security.Escape;

/**
 * @author hanjiewu
 *
 */
public class ExampleMain {
	public static void main(String[] args) {
		System.out.println(Escape.sqlLikeClause("ADSL_1M~!@#$%^&*<>/?\\", '\\'));
	}
}
