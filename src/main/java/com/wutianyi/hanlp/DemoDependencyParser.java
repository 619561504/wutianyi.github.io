/**
 * 2015年10月26日hanjiewu
 */
package com.wutianyi.hanlp;

import com.hankcs.hanlp.HanLP;

/**
 * @author hanjiewu
 *
 */
public class DemoDependencyParser {

	public static void main(String[] args) {

		System.out.println(HanLP.parseDependency("把市场经济奉行的等价交换原则引入党的生活和国家机关政务活动中"));
	}
}
