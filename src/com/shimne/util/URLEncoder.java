package com.shimne.util;

public class URLEncoder
{
	private static final String CODE = "ABCDEFGHIJKLMNOPQRTUVWXY12345678";
	private static final int FIVE = 5;
	private static final int EIGHT = 8;
	private static final int BINARY = 2;

	public static String encode(String string)
	{
		return encode(string, "");
	}

	public static String encode(String string, String charset)
	{
		if (StringUtil.isEmpty(string))
		{
			return "";
		}

		byte[] bytes = null;

		try
		{
			if (StringUtil.isEmpty(charset))
			{
				bytes = string.getBytes();
			}
			else
			{
				bytes = string.getBytes(charset);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return encode(bytes);
	}

	public static String decode(String string)
	{
		return decode(string, "");
	}

	public static String decode(String string, String charset)
	{
		if (string.isEmpty())
		{
			return "";
		}

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < string.length(); i++)
		{
			formatBinary(Integer.toBinaryString(getCodeIndex(string.charAt(i))), builder, FIVE);
		}

		byte[] bytes = new byte[builder.length() / 8];

		for (int i = 0, j = 0; i < bytes.length;  i++, j += EIGHT)
		{
			bytes[i] = Integer.valueOf(builder.substring(j, j + EIGHT), BINARY).byteValue();
		}

		String decode = "";

		try
		{
			if (StringUtil.isEmpty(charset))
			{
				decode = new String(bytes);
			}
			else
			{
				decode = new String(bytes, charset);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return decode;
	}

	private static String encode(byte[] bytes)
	{
		if (ObjectUtil.isEmpty(bytes))
		{
			return StringUtil.EMPTY;
		}

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < bytes.length; i++)
		{
			formatBinary(bytes[i], builder);
		}

		int groupCount = builder.length() / FIVE;
		int lastCount = builder.length() % FIVE;

		groupCount = lastCount > 0 ? ++groupCount : groupCount;

		StringBuilder binarys = new StringBuilder();

		int max = groupCount * FIVE;

		for (int i = 0; i < max; i+=FIVE)
		{
			int end = i + FIVE;

			boolean flag = false;

			if (builder.length() < end)
			{
				end = builder.length();
				flag = true;
			}

			String strFivBit = builder.substring(i, end);
			int intFivBit = Integer.parseInt(strFivBit, BINARY);

			if (flag)
			{
				intFivBit <<= FIVE - lastCount;
			}

			binarys.append(CODE.charAt(intFivBit));
		}

		return binarys.toString();
	}

	private static int getCodeIndex(char c)
	{
		for (int i = 0; i < CODE.length(); i++)
		{
			if (CODE.charAt(i) == c)
			{
				return i;
			}
		}

		return -1;
	}

	private static StringBuilder formatBinary(byte b, StringBuilder builder)
	{
		return formatBinary(b, builder, EIGHT);
	}

	private static StringBuilder formatBinary(byte b, StringBuilder builder, int bit)
	{
		return formatBinary(Integer.toBinaryString(b), builder, bit);
	}

	private static StringBuilder formatBinary(String string, StringBuilder builder, int bit)
	{
		if (StringUtil.isEmpty(string))
		{
			return builder;
		}

		builder = builder == null ? new StringBuilder() : builder;

		if (string.length() == bit)
		{
			return builder.append(string);
		}

		if (string.length() < bit)
		{
			StringBuilder stringBuilder = new StringBuilder(string);

			do
			{
				stringBuilder.insert(0, "0");
			}
			while(stringBuilder.length() < bit);

			return builder.append(stringBuilder);
		}

		if (string.length() > bit)
		{
			return builder.append(string.substring(string.length() - bit));
		}

		return builder;
	}

	public static void main(String[] args)
	{
		String string = encode("151106S00041");
		System.out.println(string);
		string = decode(string);
		System.out.println(string);
	}
}