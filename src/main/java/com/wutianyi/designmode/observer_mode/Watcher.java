package com.wutianyi.designmode.observer_mode;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by hanjiewu on 2016/6/27.
 */
public class Watcher implements Observer {

	public Watcher(Observable observable) {
		observable.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("状态发生改变：" + ((Watched) o).getData());
	}
}
