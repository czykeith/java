package com.keith.core.data;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DataMap extends HashMap<String, Object> implements IData {

	public DataMap() {
	}
	
	public DataMap(String key,Object value){
		put(key, value);
	}

	public DataMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}



	public DataMap(int initialCapacity) {
		super(initialCapacity);
	}



	public DataMap(Map<? extends String, ? extends Object> m) {
		super(m);
	}



	public Object get(String name, Object def) {
		Object value = get(name);
		return value != null ? value : def;
	}

	public String[] getNames() {
		String names[] = (String[]) keySet().toArray(new String[0]);
		Arrays.sort(names);
		return names;
	}

	public String getString(String name) {
		Object value = get(name);
		return value != null ? value.toString() : null;
	}

	public String getString(String name, String defaultValue) {
		return get(name, defaultValue).toString();
	}

	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		String value = getString(name, "");
		return "".equals(value) ? defaultValue : Integer.parseInt(value);
	}

	public double getDouble(String name) {
		return getDouble(name, 0.0D);
	}

	public double getDouble(String name, double defaultValue) {
		String value = getString(name, "");
		return "".equals(value) ? defaultValue : Double.parseDouble(value);
	}

	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		String value = getString(name, "");
		return "".equals(value) ? defaultValue : Boolean.valueOf(value).booleanValue();
	}

	public Date getDate(String s) {
		return (Date) get(s);
	}

	public long getLong(String s) {
		return getLong(s, 0);
	}

	public long getLong(String name, long defaultValue) {
		String value = getString(name, "");
		return "".equals(value) ? defaultValue : Long.parseLong(value);
	}
	
	public String getBlankString(String name) {
		Object value = get(name);
		return value != null ? value.toString() : "";
	}
	
	public JSONObject toJSONObject(){
		JSONObject o = new JSONObject();
		String[] names = getNames();
		for(int i=0;i<names.length;i++){
			if(get(names[i]) instanceof IData){
				IData value = (IData)get(names[i]);
				o.put(names[i], value.toJSONObject());
			}else if(get(names[i]) instanceof IDataset){
				IDataset value = (IDataset)get(names[i]);
				o.put(names[i], value.toJSONArray());
			}else if(get(names[i]) instanceof List){
				List<Object> list=(List<Object>) get(names[i]);
				JSONArray array=new JSONArray(list);
				o.put(names[i],array);
			}
			else {
				String value = getBlankString(names[i]);
				o.put(names[i], value);
			}
		}
		return o;
	}

	private static final long serialVersionUID = 1L;
}

