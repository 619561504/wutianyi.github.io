package com.wutianyi.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by hanjiewu on 2016/2/2.
 */
public class CommandHelloWorldObs extends HystrixObservableCommand<String> {
	private final String name;

	protected CommandHelloWorldObs(String name) {
		super(HystrixCommandGroupKey.Factory.asKey(name));
		this.name = name;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				if (!subscriber.isUnsubscribed()) {
					subscriber.onNext("Hello " + name + "!");
					subscriber.onCompleted();
				}
			}
		});
	}
}
