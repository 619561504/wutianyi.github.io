package com.wutianyi.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandThatFailsFast extends HystrixCommand<String>{
	private final boolean throwException;

	public CommandThatFailsFast(boolean throwException) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.throwException = throwException;
	}

	@Override
	protected String run() throws Exception {
		if (throwException) {
			throw new RuntimeException("failure from CommandThatFailsFast");
		} else {
			return "success";
		}
	}
}
