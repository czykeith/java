package com.keith.core.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSONArray;


public class DatasetList extends ArrayList<IData> implements IDataset {

	public DatasetList() {
	}

	public DatasetList(List<IData> list) {
		addAll(list);
	}

	public Object get(int index, String name) {
		IData data = (IData) get(index);
		return data != null ? data.get(name) : null;
	}

	public Object get(int index, String name, Object def) {
		Object value = get(index, name);
		return value != null ? value : def;
	}

	public String[] getNames() {
		return size() <= 0 ? null : ((IData) get(0)).getNames();
	}

	public int count() {
		return size();
	}

	public void sort(String key, int keyType) {
		sort(key, keyType, 0);
	}

	public void sort(String key, int keyType, int order) {
		IData datas[] = (IData[]) toArray();
		DataComparator c = new DataComparator(key, keyType, order);
		Arrays.sort(datas, c);
		List<IData> list = Arrays.asList(datas);
		clear();
		addAll(list);
	}

	public String toJSON() {
		String result = "[";
		int size = size();
		for (int i = 0; i < size; i++) {
			IData data = get(i);
			if (i == 0)
				result += "{";
			else
				result += ",{";
			int j = 0;
			for (String str : data.getNames()) {
				if (j == 0)
					result += "\"" + str + "\":\"" + data.getString(str) + "\"";
				else
					result += ",\"" + str + "\":\"" + data.getString(str) + "\"";
				j++;
			}
			result += "}";
		}
		return result + "]";
	}

	private static final long serialVersionUID = -5595633746567079354L;

	@Override
	public JSONArray toJSONArray() {
		JSONArray ja = new JSONArray();
		for(int i=0;i<size();i++){
			IData data = (IData) get(i);
			ja.add(data.toJSONObject());
		}
		return ja;
	}
}
