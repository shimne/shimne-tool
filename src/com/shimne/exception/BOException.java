package com.shimne.exception;

public class BOException extends NestedRuntimeException
{
	private static final long serialVersionUID = 4867685889052829049L;

	public BOException(String message)
	{
		super(message);
	}

	public BOException(String message, Throwable cause)
	{
		super(message, cause);
	}
}