package com.wutianyi.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandThatFailsFastObservable extends HystrixObservableCommand<String> {

	private final boolean throwException;

	public CommandThatFailsFastObservable(boolean throwException) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGruop"));
		this.throwException = throwException;
	}


	@Override
	protected Observable<String> construct() {
		return null;
	}

	@Override
	protected Observable<String> resumeWithFallback() {
		if (throwException) {
			return Observable.error(new Throwable("failure from CommandThatFailsFast"));
		} else {
			return Observable.just("success");
		}
	}
}
