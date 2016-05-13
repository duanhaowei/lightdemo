package com.lightdemo.test;

public class Test {
	public static void main(String[] args) {
		System.out.println(Test.class.getResource(""));
		System.out.println(Test.class.getResource("/"));
		System.out.println(Test.class.getClassLoader().getResource(""));
		System.out.println(Test.class.getClassLoader().getResource("/"));
		System.out.println(Test.class.getResource("name.properties"));
		System.out.println(Test.class.getResource("/name.properties"));
		System.out.println(Test.class.getClassLoader().getResource("name.properties"));

	}
}
