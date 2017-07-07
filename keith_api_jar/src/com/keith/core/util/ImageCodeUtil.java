package com.keith.core.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageCodeUtil{
	 private Font font = new Font("Verdana", Font.ITALIC|Font.BOLD, 28);   // 字体
	 private int len = 5;  // 验证码随机字符长度
	 private int width = 150;  // 验证码显示跨度
	 private int height = 40;  // 验证码显示高度
	 private String chars = null;  // 随机字符串
	 public String getChars(){
		 return chars;
	 }
    public ImageCodeUtil()
    {
    }
    public ImageCodeUtil(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
    public ImageCodeUtil(int width, int height, int len){
        this(width,height);
        this.len = len;
    }
    public ImageCodeUtil(int width, int height, int len, Font font){
        this(width,height,len);
        this.font = font;
    }
    /**
     * 生成验证码
     * @throws java.io.IOException IO异常
     */
    public void out(OutputStream out){
        graphicsImage(alphas(), out);
    }
 
    /**
     * 画随机码图
     * @param strs 文本
     * @param out 输出流
     */
    private boolean graphicsImage(char[] strs, OutputStream out){
        boolean ok = false;
        try
        {
            BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D)bi.getGraphics();
            AlphaComposite ac3;
            Color color ;
            int len = strs.length;
            g.setColor(Color.WHITE);
            g.fillRect(0,0,width,height);
            // 随机画干扰的蛋蛋
            for(int i=0;i<2;i++){
                color = new Color(20 + num(110), 20 + num(110), 20 + num(110));
                g.setColor(color);
                g.setStroke(new BasicStroke(4));
              /*  g.drawOval(num(width), num(height), 5+num(10), 5+num(10));// 画蛋蛋，有蛋的生活才精彩
*/              g.drawLine(RANDOM.nextInt(10), RANDOM.nextInt(40), 100, RANDOM.nextInt(40));
                color = null;
            }
            g.setFont(font);
            int h  = height - ((height - font.getSize()) >>1),
                w = width/len,
                size = w-font.getSize()+1;
            /* 画字符串 */
            for(int i=0;i<len;i++)
            {
                ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);// 指定透明度
                g.setComposite(ac3);
                color = new Color(20 + num(110), 20 + num(110), 20 + num(110));// 对每个字符都用随机颜色
                g.setColor(color);
                g.drawString(strs[i]+"",(width-(len-i)*w)+size, h-4);
                color = null;
                ac3 = null;
            }
            ImageIO.write(bi, "png", out);
            out.flush();
            ok = true;
        }catch (IOException e){
            ok = false;
        }
        return ok;
    }
    private char[] alphas()
    {
        char[] cs = new char[len];
        for(int i = 0;i<len;i++)
        {
            cs[i] = alpha();
        }
        chars = new String(cs);
        return cs;
    }
    private Color color(int fc, int bc)
    {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + num(bc - fc);
        int g = fc + num(bc - fc);
        int b = fc + num(bc - fc);
        return new Color(r, g, b);
    }
    private  final Random RANDOM = new Random();
    //定义验证码字符.去除了O和I等容易混淆的字母
    public static final char ALPHA[]={'A','B','C','D','E','F','G','H','G','K','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'
            ,'a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z','2','3','4','5','6','7','8','9'};
 
    /**
     * 产生两个数之间的随机数
     * @param min 小数
     * @param max 比min大的数
     * @return int 随机数字
     */
    private  int num(int min, int max)
    {
        return min + RANDOM.nextInt(max - min);
    }
 
    /**
     * 产生0--num的随机数,不包括num
     * @param num 数字
     * @return int 随机数字
     */
    private  int num(int num)
    {
        return RANDOM.nextInt(num);
    }
 
    public  char alpha()
    {
        return ALPHA[num(0, ALPHA.length)];
    }
}