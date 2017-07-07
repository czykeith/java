package com.keith.core.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface IData extends Map<String, Object>, Serializable {

	public abstract Object get(String s, Object obj);

	public abstract String[] getNames();

	public abstract String getString(String s);

	public abstract String getString(String s, String s1);

	public abstract int getInt(String s);

	public abstract Date getDate(String s);

	public abstract int getInt(String s, int i);

	public abstract double getDouble(String s);

	public abstract double getDouble(String s, double d);

	public abstract long getLong(String s);

	public abstract long getLong(String s, long l);

	public abstract boolean getBoolean(String s);

	public abstract boolean getBoolean(String s, boolean flag);
	
	public abstract JSONObject toJSONObject();
}
