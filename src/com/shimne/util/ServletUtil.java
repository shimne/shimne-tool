package com.shimne.util;

import java.io.File;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtil
{
	public static String getWebRootPath()
	{
		return System.getProperty("webapp.root");
	}

	public static String  getWebRealPath(String path)
	{
		if (!StringUtil.isTrimEmpty(getWebRootPath()))
		{
			path = path.trim();

			if (path.startsWith(File.separator))
			{
				path = getWebRootPath() + path + File.separator;
			}
			else
			{
				path = File.separator + getWebRootPath() + File.separator;
			}

			return path;
		}

		return getWebRootPath();
	}

	public static String getRequestValue(HttpServletRequest request, String key)
	{
		return request.getParameter(key) == null ? "" : request.getParameter(key);
	}

	public static String[] getRequestValues(HttpServletRequest request, String key)
	{
		return request.getParameterValues(key) == null ? new String[0] : request.getParameterValues(key);
	}

	public static String getCookieValue(HttpServletRequest request, String name)
	{
		Cookie[] cookies = request.getCookies();

		if (ObjectUtil.notEmpty(cookies))
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equalsIgnoreCase(name))
				{
					return cookie.getValue();
				}
			}
		}

		return "";
	}

	public static void setCookieValue(HttpServletResponse response, String name, String value, String domain, int time)
	{
		Cookie cookie = new Cookie(name, value);

		cookie.setMaxAge(time);
		cookie.setPath("/");

		if (StringUtil.notEmpty(domain))
		{
			cookie.setDomain(domain);
		}

		response.addCookie(cookie);
	}

	public static String getRemoteAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");

		if (StringUtil.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (StringUtil.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (StringUtil.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}

		if (StringUtil.notEmpty(ip))
		{
			int index = ip.indexOf(",");

			if (index > -1)
			{
				ip = ip.substring(0, index);
			}

			index = ip.indexOf(":");

			if (index > -1)
			{
				ip = ip.substring(0, index);
			}
		}

		return ip;
	}
}