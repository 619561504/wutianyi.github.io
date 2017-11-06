package com.wutianyi.lambda;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class Example3 {

	@Test
	public void example1() {
		withLock(new ReentrantLock(), () -> System.out.println("Hello World"));
	}

	static <T> void withLock(ReentrantLock lock, MyConsumer consumer) {
		lock.lock();
		try {
			consumer.apply();
		} finally {
			lock.unlock();
		}
	}

	@FunctionalInterface
	interface MyConsumer {
		void apply();
	}

}
