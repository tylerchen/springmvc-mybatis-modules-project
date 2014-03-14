/*******************************************************************************
 * Copyright (c) 2013-1-31 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.util;

import java.io.ByteArrayOutputStream;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>
 * @since 2013-1-31
 */
@SuppressWarnings("unchecked")
public class BeanHelper {

	private static final ObjectMapper mapper = new ObjectMapper().disable(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
			DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
			DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS).disable(
			SerializationFeature.FAIL_ON_EMPTY_BEANS).disableDefaultTyping();
	private static final Gson GSON = new GsonBuilder().setDateFormat(
			"yyyy-MM-dd HH:mm:ss").create();

	static {
		mapper.setVisibilityChecker(mapper.getVisibilityChecker()
				.withFieldVisibility(Visibility.ANY));
	}

	private BeanHelper() {
	}

	public static <T> T copyProperties(Object dest, Object orig) {
		if (orig == null) {
			return (T) null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
		try {
			mapper.writeValue(baos, orig);
			mapper.readerForUpdating(dest).readValue(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) dest;
	}

	public static <T> T copyProperties(Class<T> clazz, Object orig) {
		if (orig == null) {
			return (T) null;
		}
		Object fromJson = GSON.fromJson(GSON.toJsonTree(orig), clazz);
		return (T) fromJson;
	}

	//	public static void main(String[] args) {
	//		B b = new B();
	//		C c = new C();
	//		b.print();
	//		java.util.Map map = new java.util.HashMap();
	//		{
	//			map.put("aa", "aa");
	//			map.put("b", "b");
	//			map.put("c", 11L);
	//			map.put("d", 11);
	//			map.put("e", new java.util.Date());
	//			map.put("f", "11");
	//			map.put("g", 111.1D);
	//			map.put("h", "2013-01-01 00:00:00");
	//			map.put("i", new java.util.Date());
	//			map.put("j", 111L);
	//			map.put("k", new java.sql.Date(new java.util.Date().getTime()));
	//		}
	//		{
	//			long start = System.currentTimeMillis();
	//			for (int i = 0; i < 10000; i++) {
	//				BeanHelper.copyProperties(c, b);
	//			}
	//			System.out.println("Object2Object:"
	//					+ (System.currentTimeMillis() - start) / 10000.0);
	//		}
	//		{
	//			long start = System.currentTimeMillis();
	//			for (int i = 0; i < 10000; i++) {
	//				BeanHelper.copyProperties(c, map);
	//			}
	//			System.out.println("Map2Object:"
	//					+ (System.currentTimeMillis() - start) / 10000.0);
	//		}
	//		{
	//			long start = System.currentTimeMillis();
	//			for (int i = 0; i < 10000; i++) {
	//				BeanHelper.copyProperties(new java.util.HashMap(), b);
	//			}
	//			System.out.println("Object2Map:"
	//					+ (System.currentTimeMillis() - start) / 10000.0);
	//		}
	//		{
	//			long start = System.currentTimeMillis();
	//			for (int i = 0; i < 10000; i++) {
	//				BeanHelper.copyProperties(new java.util.HashMap(), map);
	//			}
	//			System.out.println("Map2Map:"
	//					+ (System.currentTimeMillis() - start) / 10000.0);
	//		}
	//		System.out.println(BeanHelper.copyProperties(c, b));
	//		System.out.println(BeanHelper.copyProperties(c, map));
	//		System.out.println(BeanHelper
	//				.copyProperties(new java.util.HashMap(), b));
	//		System.out.println(BeanHelper.copyProperties(new java.util.HashMap(),
	//				map));
	//	}
	//
	//	static class A {
	//		private int a = 1;
	//
	//		void print() {
	//			System.out.println(getClass());
	//		}
	//	}
	//
	//	static class B extends A {
	//		private static String aa = "aa";
	//		// Same Type
	//		private String b = "b";
	//		private Long c = 11L;
	//		private int d = 11;
	//		private java.util.Date e = new java.util.Date();
	//		// String to Number
	//		private String f = "11";
	//		// Number to String
	//		private Double g = 111.1D;
	//		// String to Date
	//		private String h = "2013-01-01 00:00:00";
	//		// Date to String
	//		private java.util.Date i = new java.util.Date();
	//		// Other
	//		private Long j = 111L;
	//		private java.sql.Date k = new java.sql.Date(
	//				new java.util.Date().getTime());
	//	}
	//
	//	static class C {
	//		private static String aa = "";
	//		private int a;
	//		// Same Type
	//		private String b;
	//		private Long c;
	//		private int d;
	//		private java.util.Date e;
	//		// String to Number
	//		private long f;
	//		// Number to String
	//		private String g;
	//		// String to Date
	//		private java.util.Date h;
	//		// Date to String
	//		private String i;
	//		// Other
	//		private Integer j;
	//		private java.util.Date k;
	//
	//		@Override
	//		public String toString() {
	//			return "C [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e="
	//					+ e + ", f=" + f + ", g=" + g + ", h=" + h + ", i=" + i
	//					+ ", j=" + j + ", k=" + k + ", aa=" + aa + "]";
	//		}
	//	}
}
