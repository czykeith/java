package com.keith.core.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.keith.core.data.DataMap;
import com.keith.core.data.DatasetResult;
import com.keith.core.data.IData;
import com.keith.core.data.IDataset;

/**
 * 核心数据访问类 实现简单的读写分离
 * 
 * @author keith 2015年11月20日 09:46:25
 */
@SuppressWarnings("rawtypes")
public class BaseDAO {
	@Autowired
	@Resource(name = "dataSourceWrite")
	private DataSource dataSourceWrite;
	
	@Autowired
	@Resource(name = "dataSourceRead")
	private DataSource dataSourceRead;

	/**
	 * 新增记录
	 * 
	 * @param tbName
	 *            ->表名
	 * @param params
	 *            ->参数，格式IData.put(列名,列值)
	 * @return
	 * @throws Exception
	 */
	public int insertBean(String tbName, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceWrite);
		SqlParameterSource paramSource = new MapSqlParameterSource(params);
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append(tbName);
		sql.append("(");
		Set<String> names = params.keySet();
		int i = 0;
		StringBuffer column = new StringBuffer();
		StringBuffer param = new StringBuffer();
		for (String name : names) {
			if (i == 0) {
				column.append(name);
				param.append(":" + name);
			} else {
				column.append("," + name);
				param.append(",:" + name);
			}
			i++;
		}
		sql.append(column + ") VALUES(");
		sql.append(param + ")");
		return jdbcTemplate.update(sql.toString(), paramSource);
	}

	/**
	 * 插入数据返回主键
	 * 
	 * @author leo.zhou
	 * 
	 * @param tbName
	 *            表名
	 * @param params
	 *            参数集合：key-表字段名 value-表字段值
	 * @return
	 * @throws Exception
	 */
	public Number insertTable(String tbName, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceWrite);
		SqlParameterSource paramSource = new MapSqlParameterSource(params);
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append(tbName);
		sql.append("(");
		Set<String> names = params.keySet();
		int i = 0;
		StringBuffer column = new StringBuffer();
		StringBuffer param = new StringBuffer();
		for (String name : names) {
			if (i == 0) {
				column.append(name);
				param.append(":" + name);
			} else {
				column.append("," + name);
				param.append(",:" + name);
			}
			i++;
		}
		sql.append(column + ") VALUES(");
		sql.append(param + ")");

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql.toString(), paramSource, keyHolder);

		return keyHolder.getKey();
	}

	/**
	 * 执行 Insert 语句
	 * 
	 * @author leo.zhou
	 * 
	 * @param sql
	 *            Insert 语句
	 * @param params
	 *            参数集合：key-参数名 value-参数值
	 * @return
	 * @throws Exception
	 */
	public Number insert(String sql, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceWrite);
		SqlParameterSource paramSource = new MapSqlParameterSource(params);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql.toString(), paramSource, keyHolder);

		return keyHolder.getKey();
	}

	/**
	 * merge记录
	 * 
	 * @param tbName
	 *            ->表名
	 * @param params
	 *            ->参数，格式IData.put(列名,列值)
	 * @return
	 * @throws Exception
	 */
	public int mergeBean(String tbName, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceWrite);
		SqlParameterSource paramSource = new MapSqlParameterSource(params);
		StringBuffer sql = new StringBuffer();
		/*
		 * replace into 跟 insert 功能类似，不同点在于：
		 * replace into 首先尝试插入数据到表中， 
		 * 1.如果发现表中已经有此行数据（根据主键或者唯一索引判断）则先删除此行数据，
		 * 	  然后插入新的数据。 
		 * 2. 否则，直接插入新数据。
		 * 要注意的是：插入数据的表必须有主键或者是唯一索引！
		 * 否则的话，replace into 会直接插入数据，这将导致表中出现重复的数据。
		 */
		sql.append("REPLACE INTO ");
		sql.append(tbName);
		sql.append(" (");
		Set<String> names = params.keySet();
		int i = 0;
		StringBuffer column = new StringBuffer();
		StringBuffer param = new StringBuffer();
		for (String name : names) {
			if (i == 0) {
				column.append(name);
				param.append(":" + name);
			} else {
				column.append("," + name);
				param.append(",:" + name);
			}
			i++;
		}
		sql.append(column + ") VALUES(");
		sql.append(param + ")");

		return jdbcTemplate.update(sql.toString(), paramSource);
	}

	public int dropBean(String tbName, IData keyParam) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceWrite);
		SqlParameterSource paramSource = new MapSqlParameterSource(keyParam);
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ");
		sql.append(tbName);
		sql.append(" WHERE ");
		Set<String> names = keyParam.keySet();
		int i = 0;
		StringBuffer column = new StringBuffer();
		for (String name : names) {
			if (i == 0) {
				column.append(name);
			}
			i++;
		}
		sql.append(column);
		sql.append(" = :");
		sql.append(column);

		return jdbcTemplate.update(sql.toString(), paramSource);
	}

	/** 原生SQL查询 **/
	public IDataset query(String sql, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceRead);
		SqlParameterSource sqlParams = new MapSqlParameterSource(params);

		return new DatasetResult(jdbcTemplate.queryForRowSet(sql, sqlParams));
	}

	public IData queryIDataByParams(String sql, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceRead);
		SqlParameterSource sqlParams = new MapSqlParameterSource(params);
		IDataset list=new DatasetResult(jdbcTemplate.queryForRowSet(sql, sqlParams));
		return list.size()>0?list.get(0):null;
	}
	
	public IDataset queryWithoutLog(String sql, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceRead);
		SqlParameterSource sqlParams = new MapSqlParameterSource(params);
		return new DatasetResult(jdbcTemplate.queryForRowSet(sql, sqlParams));
	}

	/** 原生SQL查询 **/
	public IData queryIData(String tableName, IData keyParam) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceRead);
		try {
			String keyColumn = keyParam.keySet().iterator().next();
			Map o = jdbcTemplate.queryForMap(
					"select * from " + tableName + " where " + keyColumn
							+ " = :" + keyColumn + " limit 0,1", keyParam);
			IData result = new DataMap();
			Iterator it = o.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				result.put(key, o.get(key));
			}
			return result;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	/** 原生SQL更新 **/
	public int update(String sql, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceWrite);
		SqlParameterSource paramSource = new MapSqlParameterSource(params);
		return jdbcTemplate.update(sql, paramSource);
	}

	public String save(String sql, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceWrite);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);
		return keyHolder.getKey().intValue() + "";
	}

	/** SQL批量更新 **/
	public int[] batchUpdate(String sql, IDataset params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceWrite);
		SqlParameterSource[] batchArgs = new MapSqlParameterSource[params
				.count()];
		int i = 0;
		for (IData data : params) {
			SqlParameterSource paramSource = new MapSqlParameterSource(data);
			batchArgs[i] = paramSource;
			i++;
		}
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	/**** 原生SQL统计 ****/
	public int count(String sql, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceRead);
		SqlParameterSource sqlParams = new MapSqlParameterSource(params);
		return jdbcTemplate.queryForObject(sql, sqlParams, Integer.class)
				.intValue();
	}

	/**** 原生SQL统计 ****/
	public double sum(String sql, IData params) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceWrite);
		SqlParameterSource sqlParams = new MapSqlParameterSource(params);
		String str = jdbcTemplate.queryForObject(sql, sqlParams, String.class);
		if (str != null && !str.isEmpty()) {
			return Double.parseDouble(str);
		} else {
			return 0;
		}
	}
}