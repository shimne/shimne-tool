package com.shimne.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FolderUtil
{
	private FolderUtil()
	{
	}

	/**
	 * 与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串
	 */
	public static final String SYS_FILE_SEPARATOR = File.separator;
	public static final String SYS_LINE_SEPARATOR = System.getProperty("line.separator");

	public static String formatPath(Object...objects)
	{
		String path = "";
		if (objects != null)
		{
			for (Object obj : objects)
			{
				if ("".equals(path))
				{
					path += obj;
				}
				else
				{
					path += SYS_FILE_SEPARATOR + obj;
				}
			}
		}

		return path;
	}

	/**
	 * 检测指定目录是否存在
	 * 
	 * @param sFolder 完整目录路径
	 * @return true 目录存在，false 目录不存在
	 */
	public static boolean exists(String folder)
	{
		File file = new File(folder);

		return file.isDirectory() && file.exists();
	}

	/**
	 * 根据指定目录路径创建一个目录，父目录必须存在
	 * 
	 * @param sFolder 完整目录路径
	 * @return true 创建成功，false 创建失败
	 */
	public static boolean mkdir(String folder)
	{
		File file = new File(folder);
		if (file.exists())
		{
			if (!file.isDirectory())
			{
				return file.mkdir();
			}
			else
			{
				return true;
			}
		}
		else
		{
			return file.mkdir();
		}
	}

	/**
	 * 根据指定目录路径创建一个目录
	 * 
	 * @param sFolder 完整目录路径
	 * @return true 创建成功，false 创建失败
	 */
	public static boolean mkdirs(String folder)
	{
		File file = new File(folder);
		if (file.exists())
		{
			if (!file.isDirectory())
			{
				return file.mkdirs();
			}
			else
			{
				return true;
			}
		}
		else
		{
			return file.mkdirs();
		}
	}

	/**
	 * 重命名一个现有目录名称
	 * 
	 * @param sFromFolder 完整的源目录路径
	 * @param sToFolder 完整的目标目录路径
	 * @return true 重命名成功，false 重命名失败
	 */
	public static boolean rename(String fromFolder, String toFolder)
	{
		File fromPath = new File(fromFolder);
		File toPath = new File(toFolder);
		if (exists(fromFolder) && !exists(toFolder)) 
		{
			return fromPath.renameTo(toPath);
		}

		return false;
	}

	/**
	 * 删除指定路径目录
	 * 
	 * @param sFolder 完整目录路径
	 * @return true 删除成功，false 删除失败
	 */
	public static boolean delete(File folder)
	{
		if (folder == null || !folder.exists() || !folder.isDirectory())
		{
			return false;
		}

		File[] files = folder.listFiles();
		for (File file : files) 
		{
			if (file.isDirectory())
			{
				if (!delete(file))
				{
					return delete(file);
				}
			}
			else
			{
				if (!file.delete())
				{
					return file.delete();
				}
			}
		}

		return folder.delete();
	}

	/***
	 * 创建EXCEL
	 * @param sFolder
	 * @param sFilePath
	 * @param sText
	 * @param sEncoding
	 * @return
	 */
	public static boolean create(String folder, String filePath, String text, String encoding)
	{
		mkdirs(folder);

		PrintWriter printWriter = null;

		try
		{
			printWriter = new PrintWriter(filePath, encoding);
			printWriter.print(text);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();

			return false;
		}
		finally
		{
			if (printWriter != null)
			{
				printWriter.close();
			}
		}

		return true;
	}
}