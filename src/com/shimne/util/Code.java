package com.shimne.util;

import java.util.Random;

public class Code
{
	public static final String S = "GHIJKLMNOPQRSTUVWXYZ";
	public static final int D = 16;

	public static String enCode(String code)
	{
		String enCode = "";

		if (code != null && !code.trim().equals(""))
		{
			for (int i = 0; i < code.length(); i++)
			{
				String c = code.substring(i, i+1);

				if (c.equals("0"))
				{
					enCode += getRandomChar();
				}
				else
				{
					int ic = Integer.parseInt(c);

					int tc = D - ic - i;

					enCode += Integer.toString(tc, 16);
				}
			}
		}

		return enCode.toUpperCase();
	}

	public static String deCode(String code)
	{
		String deCode = "";

		if (code != null && !code.trim().equals(""))
		{
			for (int i = 0; i < code.length(); i++)
			{
				String c = code.substring(i, i+1);

				if (S.indexOf(c) > -1)
				{
					deCode += "0";
				}
				else
				{
					int ic = Integer.parseInt(c, 16);

					int tc = D - ic - i;

					deCode += tc;
				}
			}
		}

		return deCode.toUpperCase();
	}

	private static String getRandomChar()
	{
		Random random = new Random();

		int i = random.nextInt(19);

		return S.charAt(i) + "";
	}
}