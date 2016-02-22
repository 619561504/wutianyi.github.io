package com.wutianyi.hystrix;

import org.junit.Test;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandHelloObsFailureTest {
	@Test
	public void testFialure() throws ExecutionException, InterruptedException {
		new CommandHelloObsFailure("Hanjiewu").observe().subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				System.out.println(s);
			}
		});
		System.out.println(new CommandHelloObsFailure("Hanjiewu").observe().toBlocking().toFuture().get());
	}

}