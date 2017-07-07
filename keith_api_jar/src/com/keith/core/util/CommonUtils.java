package com.keith.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
public class CommonUtils {	
	/**
	 * 国际化处理方法
	 * @param request
	 * @param messageProp
	 * @param params
	 * @return
	 */
	public static String dealNationalString(HttpServletRequest request,String messageProp,String[] params){
		Locale locale = request.getLocale();
		WebApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
		String value = context.getMessage(messageProp, params, locale);
		return value;
	}
	
	/**
	 * 字符转byte数组
	 * @param Str
	 * @return
	 */
	public static byte[] Str2ByteArray(String Str){
		String[] strList = Str.split(",");
		byte[] reValue = new byte[strList.length];
		for(int i=0;i<strList.length;i++){
			reValue[i]=(byte)(Integer.parseInt(strList[i].trim()));
		}
		return reValue;
	}
	
	/**
	 * 随机生成字符串
	 * @return
	 */
	public static String createToken(){
		UUID uuid = UUID.randomUUID();
		StringBuffer buf = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(uuid.toString().getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (Exception e) {
			
		}
		return buf.toString();
	}
	/**
	 * 获取用户的真是ip
	 * @param request
	 * @return
	 */
	public static String getRealIp(HttpServletRequest request){
		  String ipAddress = null;   
		     //ipAddress = request.getRemoteAddr();   
		     ipAddress = request.getHeader("x-forwarded-for");   
		     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
		      ipAddress = request.getHeader("Proxy-Client-IP");   
		     }   
		     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
		         ipAddress = request.getHeader("WL-Proxy-Client-IP");   
		     }   
		     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
		      ipAddress = request.getRemoteAddr();   
		      if(ipAddress.equals("127.0.0.1")){   
		       //根据网卡取本机配置的IP   
		       InetAddress inet=null;   
		    try {   
		     inet = InetAddress.getLocalHost();   
		    } catch (UnknownHostException e) {   
		     e.printStackTrace();   
		    }   
		    ipAddress= inet.getHostAddress();   
		    }   
		            
		    }   
		  
		     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
		     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
		         if(ipAddress.indexOf(",")>0){   
		             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
		         }   
		     }   
		     return ipAddress; 
	}
	/**
	 * 控制台输出统一处理
	 * @param content
	 */
	public static void outPrint(Class c,String content){
		System.out.println(DateUtil.Date2Str(DateUtil.getCurrentTime())+":"+c.getName()+":"+content);
	}
}
