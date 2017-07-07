package com.keith.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  
 * author:Lei.Huang
 * Date:2015年5月8日下午3:20:13 
 * 
 * comments:
 *    集合的一些工具方法
 */  
public class CollectionUtil {
	
	//获取集合的属性集合
	public static <T extends Map> List<T> getProps(Collection<T> datas,String prop){
		if(datas == null || datas.isEmpty())
			return new ArrayList();
		List result = new ArrayList(datas.size());
		for(Map data:datas){
			result.add(data.get(prop));
		}
		return result;
		
	}
	
	//根据某个属性生成Map
	public static <T extends Map> Map<? extends Object,T> toMap(Collection<T> datas,String keyProp){
		if(datas == null || datas.isEmpty())
			return new HashMap();
		Map<Object, T> map = new HashMap<Object, T>();
		for(T data :datas){
			Object value = data.get(keyProp);
			if(value != null)
				map.put(value, data);
		}
		return map;
	}
	
	
	
	//集合中查找
	public static <T extends Map> T find(Collection<T> datas,String prop,Object value){
		for(T data:datas){
			Object propValue = data.get(prop);
			if(_equals(propValue,value))
				return data;
		}
		return null;
		
	}
	
	

	//内部比较值相等
	private static boolean _equals(Object propValue, Object destValue) {
		if(destValue == null || propValue == null)
			return false;
		return propValue.equals(destValue);
	}
	
	
	/**
	 * 判断集合是否为空
	 * 
	 * @author leo.zhou
	 *
	 * @param collection 集合对象
	 * @return 是否为空
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	/**
	 * 判断Map是否为空
	 * 
	 * @author leo.zhou
	 *
	 * @param map Map对象
	 * @return 是否为空
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

}
