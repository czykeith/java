package com.keith.umeng.push;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import com.keith.core.data.DataMap;
import com.keith.core.data.IData;

public class PushKeyUtil {
	private static PushKeyUtil ut=null;
	private static IData map=new DataMap();
	private Properties proUtil=null;
	private PushKeyUtil(){
		proUtil=new Properties();
		String path=PushKeyUtil.class.getResource("/pro/push.properties").getPath();
		try {
			proUtil.load(new FileInputStream(new File(path)));
			Set<Entry<Object,Object>> en=proUtil.entrySet();
			for (Entry<Object, Object> entry : en) {
				map.put((String)entry.getKey(), (String)entry.getValue());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  public static PushKeyUtil getInstance(){
	  if(ut==null){
		  ut=new PushKeyUtil();
	  }
	  return ut;
  }
  public String getAppKey(String key){
	  String keyStr=map.getString(key);
	  if(keyStr!=null){
		  return keyStr.split(",")[0];
	  }
	  return "";
  }
  public String getAppMarsterKey(String key){
	  String keyStr=map.getString(key);
	  if(keyStr!=null){
		  return keyStr.split(",")[1];
	  }
	  return "";
  }
}
