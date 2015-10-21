/**
 * 2015年10月21日hanjiewu
 */
package com.wutianyi.templates;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.google.api.client.util.Maps;

/**
 * @author hanjiewu
 *
 */
public class Dictionary {

	private static AtomicLong index = new AtomicLong(1);

	private static Map<String, Long> dicts = Maps.newHashMap();

	public static long getIndex(String seg) {
		if (dicts.containsKey(seg)) {
			return dicts.get(seg);
		} else {
			synchronized (dicts) {
				if (dicts.containsKey(seg)) {
					return dicts.get(seg);
				}
				long i = index.getAndIncrement();
				dicts.put(seg, i);
				return i;
			}
		}
	}

	public static long getCurIndex() {
		return index.get();
	}

}
