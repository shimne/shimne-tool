package com.shimne.exception;

import com.shimne.util.StringUtil;

public class NestedRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = -3506614303162821592L;

	private String message = "";

	public NestedRuntimeException(String message)
	{
		this.message = message;
	}

	public NestedRuntimeException(String message, Throwable cause)
	{
		super(cause);
		this.message = message;
	}

	public String getMessage()
	{
		StringBuilder builder = new StringBuilder();

		if (StringUtil.notEmpty(message))
		{
			builder.append(message);
		}
		
		if (getCause() != null)
		{
			builder.append("nested exception is ").append(getCause());
		}

		return builder.toString();
	}

	public String getMessageCode()
	{
		return this.message;
	}

	public Throwable getRootCause()
	{
		Throwable rootCasue = null;
		Throwable cause = getCause();

		while (cause != null && cause != rootCasue)
		{
			rootCasue = cause;
			cause = cause.getCause();
		}

		return rootCasue;
	}

	public Throwable getMostSpecificCause()
	{
		Throwable rootCause = getRootCause();

		return (rootCause != null ? rootCause : this);
	}

	public boolean contains(Class<Throwable> exType)
	{
		if (exType == null)
		{
			return false;
		}

		if (exType.isInstance(this))
		{
			return true;
		}

		Throwable cause = getCause();

		if (cause == this)
		{
			return false;
		}

		if (cause instanceof NestedRuntimeException)
		{
			return ((NestedRuntimeException) cause).contains(exType);
		}

		do
		{
			if (exType.isInstance(this))
			{
				return true;
			}

			if (cause.getCause() == cause)
			{
				break;
			}

			cause = cause.getCause();
		}while (cause != null);

		return false;
	}
}