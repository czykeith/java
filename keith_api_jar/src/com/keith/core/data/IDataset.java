package com.keith.core.data;

import java.io.Serializable;
import java.util.List;
import com.alibaba.fastjson.JSONArray;


public interface IDataset extends List<IData>,Serializable  {

	public abstract Object get(int i, String s);

	public abstract Object get(int i, String s, Object obj);

	public abstract String[] getNames();

	public abstract int count();

	public abstract void sort(String s, int i);

	public abstract String toJSON();
	
	public abstract JSONArray toJSONArray();
}
