package com.wutianyi.hystrix;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandThatFailsSilentlyTest {
	@Test
	public void testSuccess() {
		assertEquals("success", new CommandThatFailsSilently(false).execute());
	}

	@Test
	public void testFailure() {
		try {
			assertEquals(null, new CommandThatFailsSilently(true).execute());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}