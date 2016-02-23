package com.wutianyi.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class GetPaymentInformationCommand extends HystrixCommand<PaymentInformation> {

	private final UserAccount user;

	public GetPaymentInformationCommand(UserAccount user) {
		super(HystrixCommandGroupKey.Factory.asKey("PaymentInformation"));
		this.user = user;
	}

	@Override
	protected PaymentInformation run() throws Exception {
		try {
			Thread.sleep((int) (Math.random() * 20) + 5);
		} catch (InterruptedException e) {

		}
		if (Math.random() > 0.9999) {
			throw new RuntimeException("random failure loading payment information over network");
		}
		if (Math.random() > 0.98) {
			try {
				Thread.sleep((int) (Math.random() * 100) + 25);
			} catch (InterruptedException e) {

			}
		}
		return new PaymentInformation(user, "4444888833337777", 12, 15);
	}
}
