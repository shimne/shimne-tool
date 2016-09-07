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
			File in = new File(filePath);					// ԭͼƬ
			File out = new File(toPath);					// Ŀ��ͼƬ

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

			ScaleParameter scaleParam = new ScaleParameter(zoomWidth, zoomHeight);  //��ͼ�����Ե�1024x1024���ڣ�����1024x1024�����κδ���        

			inStream = new FileInputStream(in);         
			outStream = new FileOutputStream(out);        
			ImageRender rr = new ReadRender(inStream);        
			ImageRender sr = new ScaleRender(rr, scaleParam);        
			wr = new WriteRender(sr, outStream);         
			wr.render();                            //����ͼ����         
		} 
		catch(Exception e) 
		{         
			e.printStackTrace();         
		} 
		finally 
		{         
			IOUtils.closeQuietly(inStream);         //ͼƬ�ļ��������������ǵùر�            
			IOUtils.closeQuietly(outStream);             

			if (wr != null) 
			{              
				try 
				{                  
					wr.dispose();                   //�ͷ�simpleImage���ڲ���Դ                 
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