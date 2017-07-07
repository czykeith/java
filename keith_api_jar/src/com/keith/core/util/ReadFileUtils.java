package com.keith.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFileUtils {
  /**
   * 读取文件内容  按行读取
   * @param filePath
   * @return
   */
  public static List<String> readFileToString(String filePath) {
	  List<String> sb=new ArrayList<String>();
	  try{
	    String path=ReadFileUtils.class.getResource(filePath).getPath();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
		String readLine = null;
		while ((readLine = br.readLine())!= null) {
			sb.add(readLine);
		}
		br.close();
	  }catch(Exception e){
		  
	  }
	  return sb;
  }
}
