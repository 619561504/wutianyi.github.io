package com.wutianyi.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.concurrent.ExecutionException;

/**
 * Created by hanjiewu on 2016/2/3.
 * <p/>
 * 记录下上一次请求的状态，当出现异常的时候，从出错的地方继续进行
 */
public class CommandWithStubbedFallback extends HystrixObservableCommand<Integer> {

	private int lastSeen = 0;

	public CommandWithStubbedFallback(HystrixCommandGroupKey group) {
		super(group);
	}

	@Override
	protected Observable<Integer> construct() {
		return Observable.just(1, 2, 3)
				.doOnNext(new Action1<Integer>() {
					@Override
					public void call(Integer integer) {
						System.out.println("call: " + integer);
						lastSeen = integer;
					}
				}).concatWith(Observable.<Integer>error(new RuntimeException("force error")));
	}

	@Override
	protected Observable<Integer> resumeWithFallback() {
		if (lastSeen < 4) {
			System.out.println("resume: " + lastSeen);
			return Observable.range(lastSeen + 1, 4 - lastSeen);
		} else {
			return Observable.empty();
		}
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		CommandWithStubbedFallback command = new CommandWithStubbedFallback(HystrixCommandGroupKey.Factory.asKey
				("ExampleGroup"));
		command.observe().subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(Integer integer) {
				System.out.println("onNext: " + integer);
			}
		});
	}
}
