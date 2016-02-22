package com.wutianyi.hystrix;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;

/**
 * Created by hanjiewu on 2016/2/2.
 */
public class HystrixCommandTest {
	@Test
	public void helloWorldTest() throws ExecutionException, InterruptedException {
		System.out.println(new CommandHelloWorld("World").execute());
		System.out.println(new CommandHelloWorld("Bob").execute());
		System.out.println(new CommandHelloWorldObs("Hanjie").observe().subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				System.out.println("Call: " + s);
			}
		}));
		//System.out.println(new CommandHelloWorldObs("Tianyi").toObservable().toBlocking().toFuture().get());
	}

	@Test
	public void testObservable() {
		Observable<String> fWorld = new CommandHelloWorld("world").observe();
		Observable<String> fBob = new CommandHelloWorld("Bob").observe();

		System.out.println(fWorld.toBlocking().single());
		System.out.println(fBob.toBlocking().single());

		fWorld.subscribe(new Observer<String>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(String s) {
				System.out.println("onNext: " + s);
			}
		});
		fBob.subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				System.out.println("onNext: " + s);
			}
		});
	}
}
