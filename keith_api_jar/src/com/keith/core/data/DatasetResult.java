package com.keith.core.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

public class DatasetResult extends DatasetList implements IDataset {

	public DatasetResult() throws Exception {
		names = new ArrayList<String>();
	}

	public DatasetResult(List<IData> list) {
		for (IData data : list) {
			add(data);
		}
		this.count = list.size();
	}

	public DatasetResult(SqlRowSet srs) {
		names = new ArrayList<String>();
		IData data;
		for (; srs.next(); add(data)) {
			SqlRowSetMetaData ssmd = srs.getMetaData();
			data = new DataMap();
			for (int i = 1; i <= ssmd.getColumnCount(); i++) {
//				String name = ssmd.getColumnName(i).toLowerCase();
				String name = ssmd.getColumnLabel(i).toLowerCase();
				data.put(name, getValueByRowtSet(srs, ssmd.getColumnType(i), name));
				if (srs.isFirst())
					names.add(name);
			}
		}
		count = size();
	}

	public DatasetResult(ResultSet rs) throws SQLException {
		names = new ArrayList<String>();
		IData data;
		for (; rs.next(); add(data)) {
			ResultSetMetaData rsmd = rs.getMetaData();
			data = new DataMap();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//				String name = rsmd.getColumnName(i).toLowerCase();
				String name = rsmd.getColumnLabel(i).toLowerCase();
				data.put(name, getValueByRowtSet(rs, rsmd.getColumnType(i), name));
				if (rs.isFirst())
					names.add(name);
			}
		}
		count = size();
	}

	private Object getValueByRowtSet(SqlRowSet srs, int type, String name) {
		if (type == 2004)
			return srs.getObject(name);
		else if (type == 91)
			return srs.getDate(name);
		else if(type==92){
			return srs.getTime(name);
		}else if(type==93){
			return srs.getTimestamp(name);
		}
		else if (type == 16)
			return srs.getBoolean(name);
		else if (type == 6)
			return srs.getFloat(name);
		else if (type == 4)
			return srs.getInt(name);
		else if(type==8){
			double v=srs.getDouble(name);
			DecimalFormat ft=new DecimalFormat("#.#####");
			String val=ft.format(v);
			return val;
		}
		else
			return srs.getString(name);
	}

	private Object getValueByRowtSet(ResultSet rs, int type, String name) throws SQLException {
		if (type == 2004)
			return rs.getObject(name);
		else if (type == 91)
			return rs.getDate(name);
		else if(type==92){
			return rs.getTime(name);
		}else if(type==93){
			return rs.getTimestamp(name);
		}
		else if (type == 16)
			return rs.getBoolean(name);
		else if (type == 6)
			return rs.getFloat(name);
		else if (type == 4)
			return rs.getInt(name);
		else if(type==8){
			double v=rs.getDouble(name);
			DecimalFormat ft=new DecimalFormat("#.#####");
			String val=ft.format(v);
			return val;
		}
		else
			return rs.getString(name);
	}

	public String[] getNames() {
		return (String[]) names.toArray(new String[0]);
	}

	public int count() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private static final long serialVersionUID = 1L;
	private List<String> names;
	private int count;
}
