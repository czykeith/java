package com.keith.service;


import java.util.List;


/**
 * 主键生成策略
 * @author keith
 * 2015年11月12日 10:33:58
 */
public interface IdGenerator {
	/**
	 * 生成唯一主键
	 * @return
	 */
	public String getNextId();
	/**
	 * 获取交易id
	 * @return
	 */
	public  String getTradeFlowId();
}
