package com.shimne.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class ObjectUtil
{
	public static boolean isNull(Object object)
	{
		return (object == null);
	}

	public static boolean notNull(Object object)
	{
		return !isNull(object);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object object)
	{
		if (isNull(object))
		{
			return true;
		}

		if (object instanceof String)
		{
			return "".equals(object);
		}

		if (object instanceof Collection)
		{
			return ((Collection) object).isEmpty();
		}

		if (object instanceof Map)
		{
			return ((Map) object).isEmpty();
		}

		if (object.getClass().isArray())
		{
			return (Array.getLength(object) == 0);
		}

		return true;
	}

	public static boolean notEmpty(Object object)
	{
		return !isEmpty(object);
	}

	public static <T> T nullSafe(T t, T s)
	{
		return (t == null ? s : t);
	}
}