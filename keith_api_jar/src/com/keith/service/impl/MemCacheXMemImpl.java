package com.keith.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keith.service.MemCache;

/**
 * 基于Xmemcahe缓存的实现
 * @author zongyong.chen
 * 2015年5月28日 17:48:54
 */
@Service
public class MemCacheXMemImpl implements MemCache{
	@Autowired
	private MemcachedClient client;
	
	@Override
	public boolean setCache(String key, Object value) throws Exception {
		return _set(key, 0, value);
	}

	@Override
	public boolean setCache(String namespace, String key, Object value) throws Exception {
		return _set(String.format("%s:%s",namespace,key), 0, value);
	}

	@Override
	public boolean setCache(String key, int seconds, Object value) throws Exception{
		return _set(key, seconds, value);
	}

	@Override
	public boolean setCache(String namespace, String keyvalue, int seconds,
			Object value) throws Exception{
		return _set(String.format("%s:%s",namespace,keyvalue), seconds, value);
	}
	
	private boolean _set(String key,int seconds,Object value) throws Exception{
		if(value != null)
			return client.set(key,seconds, value);
		return false;
	}

	@Override
	public Object getCache(String key) throws Exception{
		return client.get(key);
	}

	@Override
	public Object getCache(String namespace, String key) throws Exception{
		// TODO Auto-generated method stub
		return getCache(String.format("%s:%s", namespace,key));
	}

	@Override
	public Object getCaches(List<String> keys) throws Exception{
		// TODO Auto-generated method stub
		return client.get(keys);
	}

	@Override
	public Object getCaches(String namespace, List<String> keys) throws Exception{
		Collection<String> mykeys=new Vector<String>();
		for (String str : keys) {
		 mykeys.add(String.format("%s:%s", namespace,str));	
		}
		// TODO Auto-generated method stub
		return client.get(keys);
	}

	@Override
	public boolean removeCache(String key) throws Exception{
		// TODO Auto-generated method stub
		return client.delete(key);
	}

	@Override
	public boolean removeCache(String namespace, String key) throws Exception{
		// TODO Auto-generated method stub
		return client.delete(String.format("%s:%s", namespace,key));
	}

	@Override
	public boolean removeCaches(List<String> keys) throws Exception {
		for (String key : keys) {
			removeCache(key);
		}
		return true;
	}

	@Override
	public boolean removeCaches(String namespace, List<String> keys)
			throws Exception {
		// TODO Auto-generated method stub
		for (String key : keys) {
			removeCache(namespace, key);
		}
		return true;
	}

	@Override
	public boolean replaceCache(String key, Object value) throws Exception {
		// TODO Auto-generated method stub
		return client.replace(key, 0, value);
	}

	@Override
	public boolean replaceCache(String key, Object value, int seconds)
			throws Exception {
		// TODO Auto-generated method stub
		return client.replace(key, seconds, value);
	}

	@Override
	public boolean replaceCache(String namespace, String key, Object value)
			throws Exception {
		// TODO Auto-generated method stub
		return client.replace(String.format("%s:%s", namespace,key), 0, value);
	}
	
	@Override
	public boolean replaceCache(String namespace, String key, Object value,
			int seconds) throws Exception {
		// TODO Auto-generated method stub
		return client.replace(String.format("%s:%s", namespace,key), seconds, value);
	}

	@Override
	public boolean keyExists(String key) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyExists(String namespace, String key) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean expire(String key, int seconds) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean expire(String namespace, String key, int seconds)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getKeys() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getKeys(String namespace) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValues() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValues(String namespace) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean fushAll() throws Exception {
		// TODO Auto-generated method stub
		client.flushAll();
		return true;
	}
}
