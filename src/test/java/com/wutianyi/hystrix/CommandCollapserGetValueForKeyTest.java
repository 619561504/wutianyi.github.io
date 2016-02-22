package com.wutianyi.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandCollapserGetValueForKeyTest {

	@Test
	public void testCollapser() throws ExecutionException, InterruptedException {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try {
			Future<String> f1 = new CommandCollapserGetValueForKey(1).queue();
			Future<String> f2 = new CommandCollapserGetValueForKey(2).queue();
			Future<String> f3 = new CommandCollapserGetValueForKey(3).queue();
			Future<String> f4 = new CommandCollapserGetValueForKey(4).queue();

			System.out.println(f1.get());
			System.out.println(f2.get());
			System.out.println(f3.get());
			System.out.println(f4.get());

			//assertEquals(1, HystrixRequestLog.getCurrentRequest().getExecutedCommands().size());
			HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getExecutedCommands().toArray(new
					HystrixCommand<?>[1])[0];
			System.out.println(command.getCommandKey().name());
			assertTrue(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
			assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
		} finally {
			context.shutdown();
		}
	}
}