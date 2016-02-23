package com.wutianyi.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class GetOrderCommand extends HystrixCommand<Order> {

	private final int orderId;

	public GetOrderCommand(int orderId) {
		super(HystrixCommandGroupKey.Factory.asKey("Order"));
		this.orderId = orderId;
	}

	@Override
	protected Order run() throws Exception {
		try {
			Thread.sleep((int) (Math.random() * 200) + 50);
		} catch (InterruptedException e) {

		}
		if (Math.random() > 0.9999) {
			throw new RuntimeException("random failure loading order over network");
		}
		if (Math.random() > 0.95) {
			try {
				Thread.sleep((int) (Math.random() * 300) + 25);
			} catch (InterruptedException e) {

			}
		}
		return new Order(orderId);
	}
}
