package com.keith.core.data;

import java.util.Comparator;

public class DataComparator implements Comparator<Object> {

	public DataComparator(String key, int keyType, int order) {
		this.key = key;
		this.keyType = keyType;
		this.order = order;
	}

	public int compare(Object o1, Object o2) {
		IData data1 = (IData) o1;
		IData data2 = (IData) o2;
		if (order == 0) {
			if (keyType == 2) {
				String value1 = data1.getString(key);
				String value2 = data2.getString(key);
				return value1.compareTo(value2);
			}
			if (keyType == 3) {
				int value1 = data1.getInt(key);
				int value2 = data2.getInt(key);
				return value1 >= value2 ? ((int) (value1 != value2 ? 1 : 0)) : -1;
			}
			if (keyType == 4) {
				double value1 = data1.getDouble(key);
				double value2 = data2.getDouble(key);
				return value1 >= value2 ? ((int) (value1 != value2 ? 1 : 0)) : -1;
			}
		} else {
			if (keyType == 2) {
				String value1 = data1.getString(key);
				String value2 = data2.getString(key);
				return value2.compareTo(value1);
			}
			if (keyType == 3) {
				int value1 = data1.getInt(key);
				int value2 = data2.getInt(key);
				return value1 <= value2 ? ((int) (value1 != value2 ? 1 : 0)) : -1;
			}
			if (keyType == 4) {
				double value1 = data1.getDouble(key);
				double value2 = data2.getDouble(key);
				return value1 <= value2 ? ((int) (value1 != value2 ? 1 : 0)) : -1;
			}
		}
		return 0;
	}

	private String key;
	private int keyType;
	private int order;
}
