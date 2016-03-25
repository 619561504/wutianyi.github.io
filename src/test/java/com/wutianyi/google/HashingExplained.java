package com.wutianyi.google;

import com.google.common.base.Charsets;
import com.google.common.hash.*;
import com.google.common.net.InternetDomainName;
import org.junit.Test;

/**
 * Created by hanjiewu on 2016/1/28.
 */
public class HashingExplained {
	@Test
	public void hashFunctionTest() {
		HashFunction hf = Hashing.md5();
		HashCode hc = hf.newHasher().putLong(1).putString("hanjiewu", Charsets.UTF_8).hash();

		System.out.println(hc.toString());
		System.out.println(hc.asLong());
		System.out.println(hc.asInt());
		//hc = hf.newHasher().putLong(1).putString("hanjiewu", Charsets.UTF_8).hash();
		System.out.println(hc.toString());
		System.out.println(hc.asLong());

	}

	@Test
	public void hashingTest() {
		Funnel<Person> personFunnel = new Funnel<Person>() {
			@Override
			public void funnel(Person from, PrimitiveSink into) {
				into.putInt(from.id).putString(from.firstName, Charsets.UTF_8).putString(from.lastName, Charsets.UTF_8)
						.putInt(from
								.birthYear);
			}
		};
	}

	class Person {
		final int id;
		final String firstName;
		final String lastName;
		final int birthYear;

		public Person(int id, String firstName, String lastName, int birthYear) {
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.birthYear = birthYear;
		}
	}

}
