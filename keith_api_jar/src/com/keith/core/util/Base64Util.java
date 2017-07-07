package com.keith.core.util;

import com.alibaba.druid.util.Base64;

public class Base64Util {
	public static String getBASE64(String str){
		byte [] t=str.getBytes();
		String res=null;
		if(t!=null){
			res=new Base64().byteArrayToBase64(t);
		}
		return res;
	}
	public static String getFromBASE64(String str){
		String res=null;
		byte [] b=new Base64().base64ToByteArray(str);
		if(b!=null){
			res=new String(b);
		}
		return res;
	}
}
