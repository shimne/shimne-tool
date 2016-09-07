package com.shimne.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;

public class ImageUtil2
{
	public static void createThumbnail(String filePath, String toPath, int zoomWidth, int zoomHeight)
	{
		FileInputStream inStream = null;         
		FileOutputStream outStream = null;         
		ImageRender wr = null;  

		try 
		{       
			File in = new File(filePath);					// 原图片
			File out = new File(toPath);					// 目标图片

			BufferedImage srcBufferImage = ImageIO.read(in);
			int originalWidth = srcBufferImage.getWidth();
			int originalHeight = srcBufferImage.getHeight();

			if (zoomWidth > 0 && zoomHeight == 0 && zoomWidth < originalWidth)
			{
				zoomHeight = getHeight(zoomWidth, originalWidth, originalHeight);
			}
			else if (zoomWidth == 0 && zoomHeight > 0 && zoomHeight < originalHeight)
			{
				zoomWidth = getWidth(zoomHeight, originalWidth, originalHeight);
			}
			else if (zoomWidth <= 0 || zoomHeight <= 0)
			{
				zoomWidth = originalWidth;
				zoomHeight = originalHeight;
			}

			ScaleParameter scaleParam = new ScaleParameter(zoomWidth, zoomHeight);  //将图像缩略到1024x1024以内，不足1024x1024则不做任何处理        

			inStream = new FileInputStream(in);         
			outStream = new FileOutputStream(out);        
			ImageRender rr = new ReadRender(inStream);        
			ImageRender sr = new ScaleRender(rr, scaleParam);        
			wr = new WriteRender(sr, outStream);         
			wr.render();                            //触发图像处理         
		} 
		catch(Exception e) 
		{         
			e.printStackTrace();         
		} 
		finally 
		{         
			IOUtils.closeQuietly(inStream);         //图片文件输入输出流必须记得关闭            
			IOUtils.closeQuietly(outStream);             

			if (wr != null) 
			{              
				try 
				{                  
					wr.dispose();                   //释放simpleImage的内部资源                 
				} 
				catch (SimpleImageException ignore) 
				{                   
					ignore.printStackTrace();
				}             
			}
		}
	}

	private static int getHeight(int width, int originalWidth, int originalHeight)
	{
		double wd = NumberUtil.div(Double.parseDouble(String.valueOf(width)), Double.parseDouble(String.valueOf(originalWidth)), 10);
		double hd = NumberUtil.mul(wd, Double.parseDouble(String.valueOf(originalHeight)));
        hd = NumberUtil.round(hd, 0);
        String hs = String.valueOf(hd);
        hs = hs.substring(0, hs.indexOf("."));
        int height = Integer.parseInt(hs);

        if (height < 1)
        {
        	height = originalHeight;
        }

        return height;
	}

	private static int getWidth(int height, int originalWidth, int originalHeight)
	{
		double hd = NumberUtil.div(Double.parseDouble(String.valueOf(height)), Double.parseDouble(String.valueOf(originalHeight)), 10);
		double wd = NumberUtil.mul(hd, Double.parseDouble(String.valueOf(originalWidth)));
        wd = NumberUtil.round(wd, 0);
        String ws = String.valueOf(wd);
        ws = ws.substring(0, ws.indexOf("."));
        int width = Integer.parseInt(ws);

        if (width < 1)
        {
        	width = originalWidth;
        }

        return width;
	}

	public static void main(String[] args)
	{
		createThumbnail("D:/1.jpg", "D:/3.jpg", 200, 120);
	}
}