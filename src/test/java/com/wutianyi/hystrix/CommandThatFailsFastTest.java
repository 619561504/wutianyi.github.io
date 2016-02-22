package com.wutianyi.hystrix;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandThatFailsFastTest {

	@Test
	public void testSuccess() {
		assertEquals("success", new CommandThatFailsFast(false).execute());
	}

	@Test
	public void testFailure() {
		try {
			new CommandThatFailsFast(true).execute();
			fail("we should have thrown an exception");
		} catch (HystrixRuntimeException e) {
			e.printStackTrace();
		}

	}
}