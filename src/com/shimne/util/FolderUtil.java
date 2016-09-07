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
	 * ��ϵͳ�йص�Ĭ�����Ʒָ�����Ϊ�˷��㣬������ʾΪһ���ַ���
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
	 * ���ָ��Ŀ¼�Ƿ����
	 * 
	 * @param sFolder ����Ŀ¼·��
	 * @return true Ŀ¼���ڣ�false Ŀ¼������
	 */
	public static boolean exists(String folder)
	{
		File file = new File(folder);

		return file.isDirectory() && file.exists();
	}

	/**
	 * ����ָ��Ŀ¼·������һ��Ŀ¼����Ŀ¼�������
	 * 
	 * @param sFolder ����Ŀ¼·��
	 * @return true �����ɹ���false ����ʧ��
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
	 * ����ָ��Ŀ¼·������һ��Ŀ¼
	 * 
	 * @param sFolder ����Ŀ¼·��
	 * @return true �����ɹ���false ����ʧ��
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
	 * ������һ������Ŀ¼����
	 * 
	 * @param sFromFolder ������ԴĿ¼·��
	 * @param sToFolder ������Ŀ��Ŀ¼·��
	 * @return true �������ɹ���false ������ʧ��
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
	 * ɾ��ָ��·��Ŀ¼
	 * 
	 * @param sFolder ����Ŀ¼·��
	 * @return true ɾ���ɹ���false ɾ��ʧ��
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
	 * ����EXCEL
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