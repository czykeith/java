package com.keith.service.impl;

import org.springframework.stereotype.Service;

import com.keith.core.data.DataMap;
import com.keith.core.data.IData;
import com.keith.service.PropertiesService;

/**
 * 实现配置文件的程序缓存
 * @author keith
 * 2015年11月28日 12:49:04
 */
@Service
public class PropertiesServiceImpl implements PropertiesService{
	private IData map=new DataMap();
	@Override
	public void setObject(String key, Object obj) {
		map.put(key, obj);
	}
	public Object getObject(String key){
		return map.get(key);
	}

	@Override
	public String getString(String key) {
		return (String) map.get(key);
	}
}
