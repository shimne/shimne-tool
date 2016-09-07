package com.shimne.util;

import java.math.BigDecimal;

import com.shimne.exception.BOException;

public class NumberUtil
{
	public static int parseInt(String s)
	{
		int value;

		try
		{
			value = Integer.parseInt(s);
		}
		catch (Exception e)
		{
			value = 0;
		}

		return value;
	}

	public static long parseLong(String s)
	{
		long value;

		try
		{
			value = Long.parseLong(s);
		}
		catch (Exception e)
		{
			value = 0L;
		}

		return value;
	}

	public static float parseFloat(String s)
	{
		float value;

		try
		{
			value = Float.parseFloat(s);
		}
		catch (Exception e)
		{
			value = 0.0F;
		}

		return value;
	}

	public static Double parseDouble(String s)
	{
		double value;

		try
		{
			value = Double.parseDouble(s);
		}
		catch (Exception e)
		{
			value = 0.0D;
		}

		return value;
	}

	public static double add(double d1, double d2)
	{
		BigDecimal bigDecimal1 = new BigDecimal(d1);
		BigDecimal bigDecimal2 = new BigDecimal(d2);

		return bigDecimal1.add(bigDecimal2).doubleValue();
	}

	public static double sub(double d1, double d2)
	{
		BigDecimal bigDecimal1 = new BigDecimal(d1);
		BigDecimal bigDecimal2 = new BigDecimal(d2);

		return bigDecimal1.subtract(bigDecimal2).doubleValue();
	}

	public static double mul(double d1, double d2)
	{
		BigDecimal bigDecimal1 = new BigDecimal(d1);
		BigDecimal bigDecimal2 = new BigDecimal(d2);

		return bigDecimal1.multiply(bigDecimal2).doubleValue();
	}

	public static double div(double d1, double d2, int scale)
	{
		if (scale < 0)
		{
			throw new BOException("The scale must be a positive integer or zero");
		}

		BigDecimal bigDecimal1 = new BigDecimal(d1);
		BigDecimal bigDecimal2 = new BigDecimal(d2);

		return bigDecimal1.divide(bigDecimal2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}

	public static double round(double d1, int scale)
	{
		if (scale < 0)
		{
			throw new BOException("The scale must be a positive integer or zero");
		}

		BigDecimal bigDecimal1 = new BigDecimal(d1);
		BigDecimal bigDecimal2 = new BigDecimal(1);

		return bigDecimal1.divide(bigDecimal2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}
}