package com.wutianyi.hystrix.demo;

import java.net.HttpCookie;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class Order {
	private final int orderId;
	private UserAccount user;

	public Order(int orderId) {
		this.orderId = orderId;
		user = new GetUserAccountCommand(new HttpCookie("mockkey", "mockValueFromHttpRequest")).execute();
	}
}
