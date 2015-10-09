/**
 * 2015年10月9日hanjiewu
 */
package com.wutianyi.spi.impl;

import com.wutianyi.spi.HelloInterface;

/**
 * @author hanjiewu
 *
 */
public class TextHello implements HelloInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wutianyi.spi.HelloInterface#sayHello()
	 */
	@Override
	public void sayHello() {
		System.out.println("Text Hello");
	}

}
