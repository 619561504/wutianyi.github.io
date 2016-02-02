package com.wutianyi.hystrix;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * Created by hanjiewu on 2016/2/2.
 */
public class HystrixCommandTest {
	@Test
	public void helloWorldTest() throws ExecutionException, InterruptedException {
		System.out.println(new CommandHelloWorld("World").execute());
		System.out.println(new CommandHelloWorld("Bob").execute());
		System.out.println(new CommandHelloWorldObs("Hanjie").observe().toBlocking().toFuture().get());
	}
}
