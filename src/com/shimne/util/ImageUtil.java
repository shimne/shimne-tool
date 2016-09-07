package com.shimne.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class ImageUtil
{
	private int width;
	private int height;
	private int zoomWidth;
	private int zoomHeight;

	private File destFile;
	private BufferedImage bufferedImage;

	public static void resize(File srcFile, File destFile, int zoomWidth, int zoomHeight)
	{
		new ImageUtil(srcFile, destFile, zoomWidth, zoomHeight);
	}

	public static void resize(BufferedImage bufferedImage, File destFile, int zoomWidth, int zoomHeight)
	{
		new ImageUtil(bufferedImage, destFile, zoomWidth, zoomHeight);
	}

	protected ImageUtil(File srcFile, File destFile, int zoomWidth, int zoomHeight)
	{
		try
		{
			this.destFile = destFile;
			this.zoomWidth = zoomWidth;
			this.zoomHeight = zoomHeight;
			this.bufferedImage = ImageIO.read(srcFile);
			this.width = this.bufferedImage.getWidth();
			this.height = this.bufferedImage.getHeight();

			if (((this.width <= zoomWidth) && (this.height <= zoomHeight)))
			{
				FileUtils.copyFile(srcFile, destFile);
			}
			else
			{
				resize();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected ImageUtil(BufferedImage bufferedImage, File destFile, int zoomWidth, int zoomHeight)
	{
		this.bufferedImage = bufferedImage;
		this.destFile = destFile;
		this.zoomWidth = zoomWidth;
		this.zoomHeight = zoomHeight;
		this.width = this.bufferedImage.getWidth();
		this.height = this.bufferedImage.getHeight();

		resize();
	}

	protected void resize()
	{
		if ((this.width <= this.zoomWidth) && (this.height <= this.zoomHeight))
		{
			resize(this.width, this.height);
		}
		else if (this.width / this.height > this.zoomWidth / this.zoomHeight)
		{
			resize(this.zoomWidth, Math.round(this.zoomWidth * this.height / this.width));
		}
		else
		{
			resize(Math.round(this.zoomHeight * this.width / this.height), this.zoomHeight);
		}
	}

	private void resize(int w, int h)
	{
		File parent = this.destFile.getParentFile();

		if (!parent.exists())
		{
			parent.mkdirs();
		}

		BufferedImage image = scaleImage(w, h);

		try
		{
			ImageIO.write(image, "jpeg", this.destFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private BufferedImage scaleImage(int outWidth, int outHeight)
	{
		double wScale = (double) this.width / (double) outWidth;
		double hScale = (double) this.height / (double) outHeight;

		int[] rgbArray = this.bufferedImage.getRGB(0, 0, this.width, this.height, null, 0, this.width);

		BufferedImage outImage = new BufferedImage(outWidth, outHeight, 1);

		int valueRGB = 0;

		for (int y = 0; y < outHeight; y++)
		{
			int winY0 = (int) (y * hScale + 0.5D);

			if (winY0 < 0)
			{
				winY0 = 0;
			}

			int winY1 = (int) (winY0 + hScale + 0.5D);

			if (winY1 > this.height)
			{
				winY1 = this.height;
			}

			for (int x = 0; x < outWidth; x++)
			{
				int winX0 = (int) (x * wScale + 0.5D);

				if (winX0 < 0)
				{
					winX0 = 0;
				}

				int winX1 = (int) (winX0 + wScale + 0.5D);

				if (winX1 > this.width)
				{
					winX1 = this.width;
				}

				long R = 0L;
				long G = 0L;
				long B = 0L;

				for (int i = winX0; i < winX1; i++)
				{
					for (int j = winY0; j < winY1; j++)
					{
						valueRGB = rgbArray[(this.width * j + i)];

						R += getRedValue(valueRGB);
						G += getGreenValue(valueRGB);
						B += getBlueValue(valueRGB);
					}
				}

				int n = (winX1 - winX0) * (winY1 - winY0);

				R = (int) (R / n + 0.5);
				G = (int) (G / n + 0.5);
				B = (int) (B / n + 0.5);

				valueRGB = comRGB(clip((int)R), clip((int)G), clip((int)B));

				outImage.setRGB(x, y, valueRGB);
			}
		}

		return outImage;
	}

	private int clip(int x)
	{
		if (x < 0)
		{
			return 0;
		}

		if (x > 255)
		{
			return 255;
		}

		return x;
	}

	private int getRedValue(int rgbValue)
	{
		int tmp = rgbValue & 0xFF0000;
		return (tmp >> 16);
	}

	private int getGreenValue(int rgbValue)
	{
		int temp = rgbValue & 0xFF00;
		return (temp >> 8);
	}

	private int getBlueValue(int rgbValue)
	{
		return (rgbValue & 0xFF);
	}

	private int comRGB(int r, int g, int b)
	{
		return ((r << 16) + (g << 8) + b);
	}

	public static void main(String[] args)
	{
//		System.out.println(((double)1024 / (double)533));
		resize(new File("d:/1.jpg"), new File("d:/2.jpg"), 500, 400);
	}
}