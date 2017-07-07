package com.keith.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String md5(String str){
		 StringBuffer hexValue = new StringBuffer();  
		try {
			MessageDigest m=MessageDigest.getInstance("MD5");
			char [] charArray=str.toCharArray();
			byte[] byteArray=new byte[charArray.length];
			 for (int i = 0; i < charArray.length; i++)  
		            byteArray[i] = (byte) charArray[i];  
		        byte[] md5Bytes = m.digest(byteArray);  
		      
		        for (int i = 0; i < md5Bytes.length; i++){  
		            int val = ((int) md5Bytes[i]) & 0xff;  
		            if (val < 16)  
		                hexValue.append("0");  
		            hexValue.append(Integer.toHexString(val));  
		        }  
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hexValue.toString();
	}
}
