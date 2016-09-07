package com.shimne.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
	public static void main(String[] args)
	{
		/**
		 * 加密方法使用
		 * 说明 参数每位仅为数字 最多支持6位
		 * 返回对应位数加密码
		 */
		String s1 = Code.enCode("070374");

		/**
		 * 解密方法使用
		 * 说明参数为加密后的字符最多支持6位
		 * 返回对应位数的明文
		 */
		String s2 = Code.deCode(s1);

		System.out.println(s1);
		System.out.println(s2);
	}

	public static String formatString(long time, String style)
	{
		if (style == null || style.equals(""))
		{
			style = "yyyy-MM-dd HH:mm:ss";
		}

		return new SimpleDateFormat(style).format(new Date(time));
	}

	public static String formatString(long time)
	{
		return formatString(time, "yyyy-MM-dd");
	}

	public static String formatString(Date date, String style)
	{
		return formatString(date.getTime(), style);
	}

	public static String formatString(Date date)
	{
		return formatString(date, "yyyy-MM-dd");
	}

	public static long formatLong(String time, String style)
	{
		return formatDate(time, style).getTime();
	}

	public static long formatLong(String time)
	{
		return formatLong(time, "yyyy-MM-dd");
	}

	public static long formatLong(Date date)
	{
		return date.getTime();
	}

	public static Date formatDate(String time, String style)
	{
		Date date;

		try
		{
			date = new SimpleDateFormat(style).parse(time);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			date = new Date(0L);
		}

		return date;
	}

	public static Date formatDate(String time)
	{
		return formatDate(time, "");
	}

	public static Date formatDate(long time, String style)
	{
		if (style == null || style.equals(""))
		{
			style = "yyyy-MM-dd HH:mm:ss";
		}

		return formatDate(formatString(time, style), style);
	}

	public static Date formatDate(long time)
	{
		return formatDate(time, "");
	}
}