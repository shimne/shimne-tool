package com.shimne.exception;

public class AOException extends NestedRuntimeException
{
	private static final long serialVersionUID = -3574034685156475948L;

	public AOException(String message)
	{
		super(message);
	}

	public AOException(String message, Throwable cause)
	{
		super(message, cause);
	}
}