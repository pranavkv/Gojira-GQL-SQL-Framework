package com.gojira.gql.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.gojira.gql.annotations.DbAnnotations.Column;
import com.gojira.gql.annotations.DbAnnotations.Default;

public class ClassSerialzer {

	private static boolean checkNull(Object val) {

		if (val != null) {
			if (val.getClass().equals(String.class)) {
				String str = (String) val;
				if (str == null || str.isEmpty())
					return true;
				else
					return false;
			} else if (val.getClass().equals(Integer.class)) {
				int i = (Integer) val;
				if (i == 0)
					return true;
				else
					return false;
			} else if (val.getClass().equals(Long.class)) {
				Long l = (Long) val;
				if (l == 0)
					return true;
				else
					return false;
			}
		}

		return true;

	}

	public static HashMap<String, Object> serialize(Object classObject) {

		HashMap<String, Object> FieldKeyMap = new HashMap<>();

		try {
			Class<?> objectClass = classObject.getClass();
			for (Field field : objectClass.getDeclaredFields()) {
				field.setAccessible(true);
				Object val = null;
				if (field.isAnnotationPresent(Column.class)) {
					if (field.isAnnotationPresent(Default.class)) {
						Default def = field.getAnnotation(Default.class);
						val = def.value();
					} else {
						if (checkNull(field.get(classObject))) {
							continue;
						} else
							val = field.get(classObject);
					}
				}

				Column col = field.getAnnotation(Column.class);
				if (col.value().length() > 0)
					FieldKeyMap.put(col.value(), val);
				else
					FieldKeyMap.put(field.getName(), val);
			}
		} catch (IllegalAccessException e) {
			System.out.println(e);
		}

		return FieldKeyMap;
	}

	public static String getTableName(Object classObject) {

		Object value = null;
		for (Annotation annotation : classObject.getClass().getAnnotations()) {

			Class<? extends Annotation> type = annotation.annotationType();
			for (Method method : type.getDeclaredMethods()) {

				try {
					value = method.invoke(annotation, (Object[]) null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		return (String) value;
	}
}
