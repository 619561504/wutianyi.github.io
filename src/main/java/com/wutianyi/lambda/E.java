package com.wutianyi.lambda;

/**
 * 对于两个接口都有的相同的方法，必须自己实现
 */
public class E implements I, J {



	public static void main(String[] args) {
		E e = new E();
		I.f();

	}
}
