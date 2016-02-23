package com.wutianyi.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.net.HttpCookie;

/**
 * Created by hanjiewu on 2016/2/23.
 */
public class GetUserAccountCommand extends HystrixCommand<UserAccount> {

	private final HttpCookie httpCookie;
	private final UserCookie userCookie;

	public GetUserAccountCommand(HttpCookie cookie) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("User")));
		this.httpCookie = cookie;
		this.userCookie = UserCookie.parseCookie(cookie);
	}

	@Override
	protected UserAccount run() throws Exception {
		try {
			Thread.sleep((int) (Math.random() * 10) + 2);
		} catch (InterruptedException e) {

		}
		if (Math.random() > 0.95) {
			throw new RuntimeException("random failure processing UserAccount net response");
		}
		if (Math.random() > 0.95) {
			try {
				Thread.sleep((int) (Math.random() * 300) + 25);
			} catch (InterruptedException e) {

			}
		}
		return new UserAccount(86975, "John James", 2, true, false, true);
	}

	@Override
	protected String getCacheKey() {
		return httpCookie.getValue();
	}

	@Override
	protected UserAccount getFallback() {
		return new UserAccount(userCookie.userId, userCookie.name, userCookie.accountType, true, true, true);
	}

	private static class UserCookie {

		private static UserCookie parseCookie(HttpCookie cookie) {
			if (Math.random() < 0.998) {
				return new UserCookie(12345, "Henry Peter", 1);
			} else {
				throw new IllegalArgumentException();
			}
		}

		public UserCookie(int userId, String name, int accountType) {
			this.userId = userId;
			this.name = name;
			this.accountType = accountType;
		}

		private final int userId;
		private final String name;
		private final int accountType;
	}

}
