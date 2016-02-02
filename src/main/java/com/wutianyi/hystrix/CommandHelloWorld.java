package com.wutianyi.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by hanjiewu on 2016/2/2.
 */
public class CommandHelloWorld extends HystrixCommand<String> {

	private final String name;

	protected CommandHelloWorld(String name) {
		super(HystrixCommandGroupKey.Factory.asKey(name));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		return "Hello " + name + " !";
	}
}
