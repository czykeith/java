package com.keith.service;

/**
 * 配置文件管理service
 * @author keith
 *  2015年11月28日 12:45:28
 */
public interface PropertiesService {
   /**
    * 设置配置项
    * @param key
    * @param obj
    */
   public void setObject(String key,Object obj);
   /**
    * 通过key获取
    * @param key
    * @return
    */
   public Object getObject(String key);
   
   
   public String getString(String key);
}
