/**
 * 2015年10月9日hanjiewu
 */
package com.wutianyi.spi;

import java.util.ServiceLoader;

/**
 * @author hanjiewu
 *
 */
public class SPIMain {
	public static void main(String[] args) {
		ServiceLoader<HelloInterface> loaders = ServiceLoader.load(HelloInterface.class);

		for (HelloInterface in : loaders) {
			in.sayHello();
		}
	}
}
