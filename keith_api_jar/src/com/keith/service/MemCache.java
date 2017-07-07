package com.keith.service;

import java.util.List;
/**
 * 缓存管理工具接口
 * @author zongyong.chen
 *	2015年5月28日 16:51:54
 */
public interface MemCache {
  /**
   * 设置缓存
   * @param key
   * @param value
   */
  public boolean setCache(String key,Object value) throws Exception;
  /**
   * 设置缓存
   * @param namespace
   * @param key
   * @param value
   */
  public boolean setCache(String namespace,String key,Object value) throws Exception;
  /**
   * 设置缓存带有效期
   * @param key
   * @param seconds
   * @param value
   * @return
   */
  public boolean setCache(String key, int seconds, Object value)throws Exception;
  /**
   * 设置缓存带有效期
   * @param namespace
   * @param keyvalue
   * @param seconds
   * @param value
   * @return
   */
  public boolean setCache(String namespace, String keyvalue, int seconds, Object value)throws Exception;
  /**
   * 获取值
   * @param key
   * @return
   */
  public Object getCache(String key)throws Exception;
  /**
   * 获取值
   * @param namespace
   * @param key
   * @return
   */
  public Object getCache(String namespace,String key)throws Exception;
  /**
   * 批量获取值
   * @param keys
   * @return
   */
  public Object getCaches(List<String> keys)throws Exception;
  /**
   * 批量获取值
   * @param namespace
   * @param keys
   * @return
   */
  public Object getCaches(String namespace,List<String> keys)throws Exception;
  /**
   * 删除指定key的值
   * @param key
   * @return
   */
  public boolean removeCache(String key)throws Exception;
  /**
   * 删除指定值
   * @param namespace
   * @param key
   * @return
   */
  public boolean removeCache(String namespace,String key)throws Exception;
  /**
   * 批量删除
   * @param keys
   * @return
   */
  public boolean removeCaches(List<String> keys)throws Exception;
  /**
   * 批量删除
   * @param namespace
   * @param keys
   * @return
   */
  public boolean removeCaches(String namespace,List<String> keys)throws Exception;
  /**
   * 替换指定key的值
   * @param key
   * @param value
   * @return
   */
  public boolean replaceCache(String key,Object value)throws Exception;
  /**
   * 替换指定key值，带有效期
   * @param key
   * @param value
   * @param seconds
   * @return
   */
  public boolean replaceCache(String key,Object value,int seconds)throws Exception;
  /**
   * 替换指定key的值
   * @param namespace
   * @param key
   * @param value
   * @return
   */
  public boolean replaceCache(String namespace,String key,Object value)throws Exception;
  /**
   * 替换指定key的值带有效期
   * @param namespace
   * @param key
   * @param value
   * @param seconds
   * @return
   */
  public boolean replaceCache(String namespace,String key,Object value,int seconds)throws Exception;
  /**
   * 判断key是否存在，存在返回true
   * @param key
   * @return
   */
  public boolean keyExists(String key)throws Exception;
  /**
   * 判断key是否存在，存在返回true
   * @param namespace
   * @param key
   * @return
   */
  public boolean keyExists(String namespace,String key)throws Exception;
  /**
   * 设置有效期
   * @param key
   * @param seconds
   * @return
   */
  public boolean expire(String key,int seconds)throws Exception;
  /**
   * 设置有效期
   * @param namespace
   * @param key
   * @param seconds
   * @return
   */
  public boolean expire(String namespace,String key,int seconds)throws Exception;
  /**
   * 获取所有的key
   * @return
   */
  public Object getKeys()throws Exception;
  /**
   * 获取所有的key
   * @param namespace
   * @return
   */
  public Object getKeys(String namespace)throws Exception;
  /**
   * 获取所有的值
   * @return
   */
  public Object getValues()throws Exception;
  /**
   * 获取所有的值
   * @param namespace
   * @return
   */
  public Object getValues(String namespace)throws Exception;
  /**
   * 清空所有缓存
   * @return
   */
  public boolean fushAll()throws Exception;
}
