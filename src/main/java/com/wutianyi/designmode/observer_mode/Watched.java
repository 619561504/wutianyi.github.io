package com.wutianyi.designmode.observer_mode;

import org.apache.commons.lang.StringUtils;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by hanjiewu on 2016/6/27.
 */
public class Watched extends Observable {
	private String data = StringUtils.EMPTY;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		if (!StringUtils.equals(data, this.data)) {
			this.data = data;
			setChanged();
		}
		notifyObservers();
	}

	public static void main(String[] args) {
		Watched watched = new Watched();

		Observer observer = new Watcher(watched);
		watched.setData("start");
		watched.setData("run");
		watched.setData("stop");
	}
}
