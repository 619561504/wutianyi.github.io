/**
 * 2015年9月23日hanjiewu
 */
package com.wutianyi.google;

import com.google.api.client.http.GenericUrl;

/**
 * @author hanjiewu
 *
 */
public class UrlMain {

	public static void main(String[] args) {
		GenericUrl genericUrl = new GenericUrl("http://(null)/login.htm");
		System.out.println(genericUrl.getHost());
		System.out.println(genericUrl.getPort());
		System.out.println(genericUrl.getFragment());
		System.out.println(genericUrl.getRawPath());
		System.out.println(genericUrl.getUserInfo());
		
		for (String path : genericUrl.getPathParts()) {
			System.out.println("Paths: " + path);
		}

	}
}
