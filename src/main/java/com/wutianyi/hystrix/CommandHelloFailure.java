package com.wutianyi.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandHelloFailure extends HystrixCommand<String> {
	private final String name;

	public CommandHelloFailure(String name) {
		super(HystrixCommandGroupKey.Factory.asKey(name));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		throw new RuntimeException("this command always fails");
	}

	@Override
	protected String getFallback() {
		return "Hello Failure " + name + "!";
	}
}
