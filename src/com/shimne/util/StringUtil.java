package com.shimne.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtil
{
	public static final String EMPTY = "";

	private static final char[] CHARS = 
	{
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
		'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
		'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z'
	};

	public static boolean isNull(String string)
	{
		return ObjectUtil.isNull(string);
	}

	public static boolean notNull(String string)
	{
		return !isNull(string);
	}

	public static boolean isEmpty(String string)
	{
		return (string == null || string.isEmpty());
	}

	public static boolean notEmpty(String string)
	{
		return !isEmpty(string);
	}

	public static String nullSafe(String string)
	{
		return ((String) ObjectUtil.nullSafe(string, EMPTY));
	}

	public static boolean isTrimEmpty(String string)
	{
		return isEmpty(nullSafe(string));
	}

	public static boolean notTrimEmpty(String string)
	{
		return !isTrimEmpty(string);
	}

	public static boolean equals(String s1, String s2)
	{
		if (s1 == null)
		{
			return s2 == null;
		}

		return s1.equals(s2);
	}

	public static boolean equalsIgnoreCase(String s1, String s2)
	{
		if (s1 == null)
		{
			return s2 == null;
		}

		return s1.equalsIgnoreCase(s2);
	}

	public static String replace(String string, String oldString, String newString)
	{
		if (isEmpty(string))
		{
			return EMPTY;
		}

		if (equals(oldString, newString))
		{
			return string;
		}

		int i = 0;

		if ((i = string.indexOf(oldString, i)) >= 0)
		{
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();

			StringBuffer stringBuffer = new StringBuffer();

			stringBuffer.append(string2, 0 , i).append(newString2);

			int oldStringLength = oldString.length();
			i += oldStringLength;
			int j = i;

			while ((i = string.indexOf(oldString, i)) > 0)
			{
				stringBuffer.append(string2, j, i - j).append(newString2);
				i += oldStringLength;
				j = i;
			}

			return (stringBuffer.append(string2, j, string.length() - j)).toString();
		}

		return string;
	}

	public static String replace(String string, String oldString, String newString, int[] count)
	{
		if (isEmpty(string))
		{
			return EMPTY;
		}

		if (equals(oldString, newString))
		{
			return string;
		}

		int i = 0;

		if ((i = string.indexOf(oldString, i)) >= 0)
		{
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(string2, 0, i).append(newString2);

			int oldStringLength = oldString.length();
			i += oldStringLength;
			int j = i;

			int counter = 1;

			while ((i = string.indexOf(oldString, i)) > 0)
			{
				counter ++;

				stringBuffer.append(string2, j, i - j).append(newString2);
				i += oldStringLength;
				j = i;
			}

			count[0] = counter;

			return (stringBuffer.append(string2, j, string2.length - j)).toString();
		}

		return string;
	}

	public static String replaceIgnoreCase(String string, String oldString, String newString)
	{
		if (isEmpty(string))
		{
			return EMPTY;
		}

		if (equals(oldString, newString))
		{
			return string;
		}

		String lcString = string.toLowerCase();
		String lcOldString = oldString.toLowerCase();

		int i = 0;

		if ((i = lcString.indexOf(lcOldString, i)) >= 0)
		{
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(string2, 0, i).append(newString2);

			int oldStringLength = oldString.length();
			i += oldStringLength;
			int j = i;

			while ((i = lcString.indexOf(lcOldString, i)) > 0)
			{
				stringBuffer.append(string2, j , j - i).append(newString2);
				i += oldStringLength;
				j = i;
			}

			return stringBuffer.append(string2, j, string.length() - j).toString();
		}

		return string;
	}

	public static String replaceIgnoreCase(String string, String oldString, String newString, int[] count)
	{
		if (isEmpty(string))
		{
			return EMPTY;
		}

		if (equals(oldString, newString))
		{
			return string;
		}

		String lcString = string.toLowerCase();
		String lcOldString = oldString.toLowerCase();

		int i = 0;

		if ((i = lcString.indexOf(lcOldString, i)) >= 0)
		{
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(string2, 0, i).append(newString2);

			int oldStringLength = oldString.length();
			i += oldStringLength;
			int j = i;

			int counter = 1;

			while ((i = lcString.indexOf(lcOldString, i)) > 0)
			{
				counter++;

				stringBuffer.append(string2, j , j - i).append(newString2);
				i += oldStringLength;
				j = i;
			}

			count[0] = counter;

			return stringBuffer.append(string2, j, string.length() - j).toString();
		}

		return string;
	}

	public static int strLength(String string, String charset)
	{
		if (isEmpty(string))
		{
			return 0;
		}

		try
		{
			return string.getBytes(charset).length;
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		return string.getBytes().length;
	}

	public static String substring(String string, int length, String charset)
	{
		byte[] bytes;

		try
		{
			bytes = nullSafe(string).getBytes(charset);

			if (bytes.length < length)
			{
				return string;
			}

			if (length < 1)
			{
				return EMPTY;
			}

			int len = string.length();

			for (int i = 0; i < len; i++)
			{
				int destLength = strLength(string.substring(0, i + 1), charset);

				if (destLength > length)
				{
					if (i == 0)
					{
						return EMPTY;
					}

					return string.substring(0, i);
				}
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		return EMPTY;
	}

	public static String substring(String string, int length, String dot, String charset)
	{
		byte[] bytes = string.getBytes();

		if (bytes.length < length)
		{
			return string;
		}

		int len = length - dot.length();

		if (len < 1)
		{
			len = 1;
		}

		return substring(string, len, charset) + dot;
	}

	public static String getRandomString(int size)
	{
		Random random = new Random();

		StringBuffer stringBuffer = new StringBuffer(size);

		for (int i = 0; i < size; i++)
		{
			stringBuffer.append(CHARS[Math.abs(random.nextInt() % CHARS.length)]);
		}

		return stringBuffer.toString();
	}

	public static String md5String(String string)
	{
		byte[] bytes = null;
		MessageDigest messageDigest = null;

		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(string.getBytes());
			bytes = messageDigest.digest();

			StringBuffer stringBuffer = new StringBuffer();

			for (int i = 0; i < bytes.length; i++)
			{
				int digest = bytes[i];

				if (digest < 0)
				{
					digest += 256;
				}

				if (digest < 16)
				{
					stringBuffer.append("0");
				}

				stringBuffer.append(Integer.toHexString(digest));
			}

			return stringBuffer.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return EMPTY;
	}

	public static String text2Html(String string)
	{
		if (isEmpty(string))
		{
			return EMPTY;
		}

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < string.length(); i++)
		{
			char c = string.charAt(i);

			switch(c)
			{
				case '&' : stringBuilder.append("&amp;"); 	break;
				case '<' : stringBuilder.append("&lt;");  	break;
				case '>' : stringBuilder.append("&gt;");  	break;
				case '"' : stringBuilder.append("&quot;"); 	break;
				case ' ' : stringBuilder.append("&nbsp;");	break;
				case '\n' : stringBuilder.append("<br/>");	break;
				default : stringBuilder.append(c);
			}
		}

		return stringBuilder.toString();
	}

	public static String html2Text(String string)
	{
		if (isEmpty(string))
		{
			return "";
		}

		string = replace(string, "&amp;", "&");
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		string = replace(string, "&nbsp;", " ");
		string = replace(string, "<br/>", "\n");

		return string;
	}

	public static String html2String(String string)
	{
		if (isEmpty(string))
		{
			return "";
		}

		return string.replaceAll("<.*?>", "");
	}

	public static boolean hasChineseCharset(String string)
	{
		if (notEmpty(string))
		{
			for (int i = 0; i < string.length(); i++)
			{
				if (string.codePointAt(i) >= 256)
				{
					return true;
				}
			}
		}

		return false;
	}

	public static String compress(String string)
	{
		String result = EMPTY;

		try
		{
			int number;

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(string.getBytes("UTF-8"));
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);

			byte[] bytes = new byte[1024];

			while ((number = byteArrayInputStream.read(bytes)) != -1)
			{
				gzipOutputStream.write(bytes, 0, number);
			}

			gzipOutputStream.close();
			byteArrayInputStream.close();

			BASE64Encoder encoder = new BASE64Encoder();
			result = encoder.encode(byteArrayOutputStream.toByteArray());
			byteArrayOutputStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public static String decompress(String string)
	{
		String result = EMPTY;

		try
		{
			int number;

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(string));
			GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);

			byte[] bytes = new byte[1024];

			while ((number = gzipInputStream.read(bytes)) != -1)
			{
				byteArrayOutputStream.write(bytes, 0, number);
			}

			gzipInputStream.close();
			byteArrayInputStream.close();
			result = byteArrayOutputStream.toString("UTF-8");
			byteArrayOutputStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args)
	{
		String s = "Q13001520";
		System.out.println(md5String(s).toUpperCase());
	}
}