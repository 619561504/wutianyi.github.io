package com.wutianyi.google;

import com.google.common.net.InternetDomainName;
import org.junit.Test;

/**
 * Created by hanjiewu on 2016/1/28.
 */
public class InternetDomainNameExplained {
	@Test
	public void test() {
		System.out.println(InternetDomainName.isValid("www.baidu.cdddomk"));
		InternetDomainName idn = InternetDomainName.from("www.github.com");
		System.out.println(idn.topPrivateDomain());

	}
}
