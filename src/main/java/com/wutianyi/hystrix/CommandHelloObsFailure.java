package com.wutianyi.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandHelloObsFailure extends HystrixObservableCommand<String> {
	private final String name;

	public CommandHelloObsFailure(String name) {
		super(HystrixCommandGroupKey.Factory.asKey(name));
		this.name = name;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create(new Observable.OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> subscriber) {
				throw new RuntimeException("this command alway failure");
			}
		});
	}

	@Override
	protected Observable<String> resumeWithFallback() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				if (!subscriber.isUnsubscribed()) {
					subscriber.onNext("Failure " + name + "!");
					subscriber.onCompleted();
				}
			}
		});
	}
}
