package com.keith.core.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class ImageUtil {
	/**
	 * 剪切图片
	 * 
	 * @param srcFile
	 *            图片源地址
	 * @param destFile
	 *            图片要保存的地址
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param width
	 *            图片要截取的宽
	 * @param height
	 *            图片要截取的高
	 */
	public static void cutImage(String srcFile, String destFile, int x, int y,
			int width, int height) {
		FileInputStream fis = null;
		ImageInputStream iis = null;
		try {
			fis = new FileInputStream(srcFile);
			String endName = srcFile.substring(srcFile.lastIndexOf(".") + 1);
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(endName);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(fis);
			reader.setInput(iis, true);
			ImageReadParam irp = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			irp.setSourceRegion(rect);
			BufferedImage bImage = reader.read(0, irp);
			ImageIO.write(bImage, endName, new File(destFile));
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 生成缩略图
	 * 
	 * @param srcFile
	 *            源图片地址
	 * @param destFile
	 *            图片保存地址
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 */
	private static void change(Image img, String destFile, int width,
			int height, String type) {
		if (img != null) {
			try {
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				image.getGraphics().drawImage(img, 0, 0, width, height, null);
				FileOutputStream out = new FileOutputStream(destFile);
				image.flush();
				ImageIO.write(image, type, out);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 等比缩放图片
	 * 
	 * @param srcFile
	 *            源图片地址
	 * @param destFile
	 *            要保存的图片地址
	 * @param d
	 *            比例
	 */
	public static void thumbImage(String srcFile, String destFile, double d) {
		File file = new File(srcFile);
		String type = srcFile.substring(srcFile.lastIndexOf(".") + 1);
		try {
			Image img = ImageIO.read(file);
			int w = img.getWidth(null);
			int h = img.getHeight(null);
			int nwidth = (int) Math.round(d * w);
			int nheight = (int) Math.rint(h * d);
			change(img, destFile, nwidth, nheight, type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 固定宽度或高度等比缩放
	 * 
	 * @param srcFile
	 *            源图片地址
	 * @param destFile
	 *            要保存的图片地址
	 * @param size
	 *            图片宽度
	 * @param isWidth
	 *            是否固定宽,为false时为固定高度
	 */
	public static void thumbImage(String srcFile, String destFile, int size,
			boolean isWidth) {
		File file = new File(srcFile);
		String type = srcFile.substring(srcFile.lastIndexOf(".") + 1);
		try {
			int nwidth = size;
			int nheight = size;
			Image img = ImageIO.read(file);
			int w = img.getWidth(null);
			int h = img.getHeight(null);
			if (isWidth) {
				float d = (w / size);
				nwidth = size;
				nheight = (int) Math.rint(h / d);
			} else {
				float d = (h / size);
				nheight = size;
				nwidth = (int) Math.rint(w / d);
			}
			change(img, destFile, nwidth, nheight, type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 固定高度和宽度缩放
	 * 
	 * @param srcFile
	 *            图片源地址
	 * @param destFile
	 *            图片保存地址
	 * @param width
	 *            图片高度
	 * @param height
	 *            图片宽度
	 */
	public static void thumbImage(String srcFile, String destFile, int width,
			int height) {
		File file = new File(srcFile);
		String type = srcFile.substring(srcFile.lastIndexOf(".") + 1);
		try {
			Image img = ImageIO.read(file);
			change(img, destFile, width, height, type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加图片水印
	 * 
	 * @param logoFile
	 *            水印图片
	 * @param srcFile
	 *            要加水印的图片路径
	 * @param destFile
	 *            要保存的位置
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param alpha
	 *            透明度
	 */
	public static void pressImage(String logoFile, String srcFile,
			String destFile, int x, int y, Float alpha) {
		File file = new File(srcFile);
		String type = srcFile.substring(srcFile.lastIndexOf(".") + 1);
		try {
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			Image waterImage = ImageIO.read(new File(logoFile));
			int width_1 = waterImage.getWidth(null);
			int height_1 = waterImage.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			int w = width - width_1;
			int h = height - height_1;
			if (x < w) {
				w = x;
			}
			if (y < h) {
				h = y;
			}
			g.drawImage(waterImage, w, h, width_1, height_1, null);
			g.dispose();
			FileOutputStream out = new FileOutputStream(destFile);
			ImageIO.write(bufferedImage, type, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加文字水印
	 * 
	 * @param text
	 *            文字
	 * @param srcFile
	 *            原图片
	 * @param destFile
	 *            保存路径
	 * @param fontName
	 *            字体名称
	 * @param fontStyle
	 *            字体类型
	 * @param fontSize
	 *            字号
	 * @param color
	 *            颜色
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param alpha
	 *            透明度
	 */
	public static void pressText(String text, String srcFile, String destFile,
			String fontName, int fontStyle, int fontSize, Color color, int x,
			int y, Float alpha) {
		File file = new File(srcFile);
		String type = srcFile.substring(srcFile.lastIndexOf(".") + 1);
		try {
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setColor(color);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawString(text, x + fontSize, y + fontSize);
			g.dispose();
			FileOutputStream out = new FileOutputStream(destFile);
			ImageIO.write(bufferedImage, type, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加水印（按照水印图片大小，算出添加水印的个数）
	 * 
	 * @param oriFile 原图片
	 * @param watermarkFile  水印图片
	 * @param number 水印个数
	 * @param targetFile  存放位置
	 * @param lateralSeparation 横向间隔
	 * @param longitudinalSeparation 纵向间隔
	 * @param alpha 透明度
	 * @throws IOException
	 */
	public static byte[] watermark(String oriFile,String watermarkFile,int lateralSeparation, int longitudinalSeparation,Float alpha) throws IOException{
		//获取原团片
		BufferedImage oriImage = ImageIO.read(new File(oriFile));
		String type = oriFile.substring(oriFile.lastIndexOf(".") + 1);
		//原图片的宽度
		int oWidth = oriImage.getWidth();
		//原图片的高度
		int oHeight = oriImage.getHeight();
		//获取水印图片
		BufferedImage watermarkImage = ImageIO.read(new File(watermarkFile));
		//水印图片的宽度
		int wWidth = watermarkImage.getWidth();
		//水印图片的高度
		int wHeight = watermarkImage.getHeight();
		//横向个数
		int xNumber = oWidth/(wWidth+lateralSeparation)+1;
		//纵向个数
		int yNumber = oHeight/(wHeight+longitudinalSeparation)+1;
		String xCoord = "";
		String yCoord = "";
		for(int i=0;i<xNumber;i++){
			for(int j=0;j<yNumber;j++){
				int x=(wWidth+lateralSeparation)*i;
				int y=(wHeight+longitudinalSeparation)*j;
				xCoord += x+"&";
				yCoord += y+"&";
			}
		}
		Graphics2D g = oriImage.createGraphics();
		g.drawImage(oriImage, 0, 0, oWidth, oHeight, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		String[] xc = xCoord.split("&");
		String[] yc = yCoord.split("&");
		for(int m=0;m<xc.length;m++){
			g.drawImage(watermarkImage, Integer.parseInt(xc[m]), Integer.parseInt(yc[m]), wWidth, wHeight, null);
		}
		g.dispose();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		//FileOutputStream out = new FileOutputStream(targetFile);
		ImageIO.write(oriImage, type, out);
		byte [] re=out.toByteArray();
		out.close();
		return re;
	}
}
