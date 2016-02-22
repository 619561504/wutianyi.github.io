package com.wutianyi.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * Created by hanjiewu on 2016/2/2.
 */
public class CommandHelloWorld extends HystrixCommand<String> {
	/**
	 * 不同的command可以分为同一个组，可以通过threadpoolkey来区分它们资源，使其资源相互独立
	 */
	Setter setter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")).andCommandKey
			(HystrixCommandKey.Factory.asKey("HelloWorld")).andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey
			("Helloworld"));

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
