package com.shimne.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpUtil
{
	private static String SYS_LINE_SEPARATOR = System.getProperty("line.separator");

	public static String read(String url, String encoding)
	{
		return read(url, encoding, SYS_LINE_SEPARATOR);
	}

	public static String read(String url, String encoding, String separator)
	{
		StringBuilder builder = new StringBuilder();

		BufferedReader reader = null;

		if (!StringUtil.isTrimEmpty(url))
		{
			try
			{
				if (StringUtil.isTrimEmpty(encoding))
				{
					encoding = "UTF-8";
				}

				URL uRL = new URL(url);
				URLConnection urlConnection = uRL.openConnection();
				HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
				httpURLConnection.connect();

				if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode())
				{
					reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), encoding));

					String text;
					while ((text = reader.readLine()) != null)
					{
						builder.append(text);

						if (!StringUtil.isTrimEmpty(separator))
						{
							builder.append(separator);
						}
					}
				}

				httpURLConnection.disconnect();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (reader != null)
				{
					try
					{
						reader.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		return builder.toString();
	}

	public static String readByCookie(String url, String encoding, String separator, String cookie)
	{
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;

		if (StringUtil.notTrimEmpty(url))
		{
			try
			{
				if (StringUtil.notTrimEmpty(encoding))
				{
					encoding = "UTF-8";
				}

				HttpClient httpClient = new HttpClient();
				GetMethod getMethod = new GetMethod(url);

				if (StringUtil.notTrimEmpty(cookie))
				{
					getMethod.setRequestHeader("Cookie", cookie);
				}

				int status = httpClient.executeMethod(getMethod);

				if (status == 200)
				{
					reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), encoding));

					String text;
					while ((text = reader.readLine()) != null)
					{
						builder.append(text);

						if (!StringUtil.isTrimEmpty(separator))
						{
							builder.append(separator);
						}
					}

					reader.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (reader != null)
				{
					try
					{
						reader.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		return builder.toString();
	}
}