package com.foreveross.infra.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public final class JsonHelper {

	private static final Gson GSON = new GsonBuilder().setDateFormat(
			"yyyy-MM-dd HH:mm:ss").create();

	private JsonHelper() {
	}

	public static <T> T toObject(Class<T> clazz, CharSequence json) {
		return (T) GSON.fromJson(json.toString(), clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> toObjectList(Class<T> clazz, CharSequence json) {
		return (List<T>) GSON.fromJson(json.toString(),
				new TypeToken<List<T>>() {
				}.getType());
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> toObjectList(String className, CharSequence json) {
		try {
			return (List<T>) GSON.fromJson(json.toString(),
					new TypeToken<List<T>>() {
					}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<T>();
	}

	public static String toJson(Object obj) {
		return GSON.toJson(obj);
	}
}
