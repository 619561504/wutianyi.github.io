package com.wutianyi.lambda;


import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Example2 {

	List<String> words;

	@Before
	public void before() throws IOException {
		words = Arrays.asList(new String(Files.readAllBytes(Paths.get(new File("D:\\微云同步盘\\619561504\\study\\pg100.txt").toURI())), StandardCharsets.UTF_8).split("[\\P{L}]+"));
	}

	@Test
	public void example8() {
		AtomicInteger atomic = new AtomicInteger();
		words.parallelStream().forEach(x -> {
			if (x.length() < 12) atomic.getAndIncrement();
		});
		System.out.println(atomic.get());

		int ss = words.parallelStream().collect(ArrayList::new, (l, v) -> {
			if (v.length() < 12) l.add(v);
		}, List::addAll).size();
		System.out.println(ss);

		long ll = words.parallelStream().collect(Collectors.groupingBy(x -> x.length() < 12, Collectors.counting())).get(true);
		System.out.println(ll);


	}

	@Test
	public void example7() {
		List<Integer> l1 = Lists.newArrayList(1, 2, 3);
		List<Integer> l2 = Lists.newArrayList(4, 5, 6);
		List<Integer> l3 = Lists.newArrayList(7, 8, 9);

		Stream<List<Integer>> stream = Stream.of(l1, l2, l3);
		stream.reduce(new ArrayList<>(), (x, y) -> {
			x.addAll(y);
			return x;
		}).forEach(System.out::println);
		System.out.println("========================");
		stream = Stream.of(l1, l2, l3);
		stream.flatMap(x -> x.stream()).forEach(System.out::println);
		System.out.println("========================");
		stream = Stream.of(l1, l2, l3);
		stream.collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll).forEach(System.out::println);
	}

	@Test
	public void example6() {
		String ss = "abcdefghijklmn";
		characterStream(ss).forEach(System.out::println);
		System.out.println("============");
		characterStream2(ss).forEach(System.out::println);
	}

	public static Stream<Character> characterStream2(String s) {
		IntStream stream = IntStream.range(0, s.length());

		return stream.mapToObj(s::charAt);
	}

	public static Stream<Character> characterStream(String s) {
		List<Character> result = new ArrayList<>();
		for (char c : s.toCharArray()) {
			result.add(c);
		}
		return result.stream();
	}

	@Test
	public void example5() {
		long a = 25214903917l;
		long c = 11;
		long m = (long) Math.pow(2, 48);

		Long[] r = Stream.iterate(1l, x -> (a * x + c) % m).limit(5).toArray(Long[]::new);
		for (long v : r) {
			System.out.println(v);
		}
	}

	@Test
	public void example4() {
		int[] values = {1, 4, 9, 16};
		System.out.println(Math.pow(2, 4));
	}

	@Test
	public void example3() {
		Multiset<Long> multiset = ConcurrentHashMultiset.create();

		long start = System.nanoTime();

		long c = words.stream().filter(line -> {
			if (line.length() > 12) {
				multiset.add(Thread.currentThread().getId());
			}
			return line.length() > 12;
		}).count();

		System.out.println(System.nanoTime() - start);

		System.out.println(c);
		long s = 0l;
		for (Long k : multiset.elementSet()) {
			s += multiset.count(k);
		}
		System.out.println(s);
	}

	@Test
	public void example2() {
		Multiset<Long> multiset = ConcurrentHashMultiset.create();
		long start = System.nanoTime();

		long c = words.parallelStream().filter(line -> {
			if (line.length() > 12)
				multiset.add(Thread.currentThread().getId());

			return line.length() > 12;
		}).count();

		System.out.println(System.nanoTime() - start);

		System.out.println(c);

		long s = 0l;
		for (Long k : multiset.elementSet()) {
			s += multiset.count(k);
		}
		System.out.println(s);
	}

	@Test
	public void example() {
		Stream<Locale> stream = Stream.of(Locale.getAvailableLocales());
		//Map<String, String> languageNames = stream.collect(Collectors.toMap(l -> l.getDisplayLanguage(), l -> l.getDisplayCountry()));

		Random r = new Random();
		File ff = new File("D:\\");
		//Arrays.stream(ff.listFiles(file -> file.getName().endsWith("mp4"))).forEach(f -> System.out.println(f.getName()));
		//Arrays.stream(ff.list((dir, name) -> dir.getName().contains("寻秦记") && name.endsWith("mp4"))).forEach(System.out::println);

		Arrays.stream(ff.listFiles()).forEach(System.out::println);
		System.out.println("============================================");
		Arrays.stream(ff.listFiles()).sorted((f1, f2) -> f2.getAbsolutePath().compareTo(f1.getAbsolutePath()))
				.sorted((f1, f2) -> f2.getName().compareTo(f1.getName())).forEach(System.out::println);

		new Thread(andThen(() -> System.out.println("run1"), () -> System.out.println("run2"))).start();
	}

	public static Runnable andThen(Runnable r1, Runnable r2) {

		return () -> {
			r1.run();
			r2.run();
		};
	}
}
