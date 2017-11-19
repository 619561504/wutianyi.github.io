package com.wutianyi.lambda;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hanjiewu on 2017/11/3.
 */
public class Example {

    List<String> words;

    @Before
    public void before() throws URISyntaxException, IOException {
        URL path = Example.class.getClassLoader().getResource("pg100.txt");
        String contents = new String(Files.readAllBytes(Paths.get(path.toURI())));
        words = Arrays.asList(contents.split("[\\P{L}]+"));
    }

    @Test
    public void example6() {
        List<String> ll = Lists.newArrayList("1", "2", "3");
        String[] vs = ll.toString().split("");
        for (String v : vs) {
            System.out.println(v);
        }
        System.out.println(ll.toString());
    }

    @Test
    public void example1() throws IOException, URISyntaxException {
        // 函数式接口
        Comparator<String> i = (String first, String second) -> Integer.compare(first.length(), second.length());
        BiFunction<String, String, Integer> comp = (first, second) -> Integer.compare(first.length(), second.length());


        long count = words.stream().filter(w -> w.length() > 12).count();
        System.out.println(count);

        Stream<String> ss = words.stream();
        Stream<String> longWords = ss.filter(w -> w.length() > 12);
        Stream<String> lowercaseWords = ss.map(String::toLowerCase);
        Stream<Character> firstChars = ss.map(s -> s.charAt(0));

        Stream<Stream<Character>> result = words.stream().map(s -> characterStream(s));
        Stream<Double> randoms = Stream.generate(Math::random);
    }

    @Test
    public void example2() {
        Stream.iterate(1.0, p -> p * 2).peek(System.out::println).limit(10).toArray();
        Stream<String> longestFirst = words.stream().sorted(Comparator.comparing(String::length).reversed());
    }

    @Test
    public void example3() {

        int[] vs = new int[]{1, 2, 3, 4, 5};
        String r = Arrays.stream(vs).mapToObj(String::valueOf).collect(Collectors.joining(","));

        System.out.println(r);

        Stream<Integer> values = Stream.of(1, 2, 3, 4, 5, 6);
        Integer sum = values.reduce(0, (x, y) -> x + y);

    }

    @Test
    public void example4() {
        IntSummaryStatistics intSummaryStatistics = words.stream().collect(Collectors.summarizingInt(String::length));
        System.out.println(intSummaryStatistics.getSum());
        System.out.println(intSummaryStatistics.getMax());
    }

    @Test
    public void example5() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        int i = stream.collect(Collectors.summingInt(a -> a));
        System.out.println(i);
    }

    public static Stream<Character> characterStream(String s) {
        List<Character> result = new ArrayList<>();
        for (char c : s.toCharArray()) {
            result.add(c);
        }
        return result.stream();
    }


    class Greeter {
        public void greet() {
            System.out.println("Hello, World!");
        }
    }

    class ConcurrentGreeter extends Greeter {
        public void greet() {
            Thread t = new Thread(super::greet);
            t.run();
        }
    }

    public static void repeatMessage(String text, int count) {
        Runnable r = () -> {
            for (int i = 0; i < count; i++) {
                System.out.println(text);
                Thread.yield();
            }
        };
    }
}
