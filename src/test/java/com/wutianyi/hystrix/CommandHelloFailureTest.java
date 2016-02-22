package com.wutianyi.hystrix;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandHelloFailureTest {

	@Test
	public void testSynchronous() {
		System.out.println(new CommandHelloFailure("World").execute());
		System.out.println(new CommandHelloFailure("Bob").execute());
	}
}