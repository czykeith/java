package com.keith.service.impl;

import java.text.SimpleDateFormat;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.keith.service.IdGenerator;


@Service
public class IdGeneratorImpl implements IdGenerator{

   private String buffer;
	private long count = 0;
	private SimpleDateFormat sf;
	//机器码，表示本台机器，默认随机生成4位
	private String workId;
	private long lastTime  ;
	//流水号format
	private String serialFormat ;
	
	public IdGeneratorImpl() {
		
		this(String.format("%04d",new Random().nextInt(10000)),9);
	}
	
	
	public IdGeneratorImpl(String workerId,int length) {
		this("yyyyMMddHHmmssSSS",workerId,length);
	}
	
	/**
	 * 指定时间格式，机器码和流水号长度
	 * @param format
	 * @param workerId
	 * @param length
	 */
	public IdGeneratorImpl(String format,String workerId,int length) {
		this.sf =  new SimpleDateFormat(format);
		this.workId = workerId;
		this.serialFormat = "%0"+length+"d";
	}
	
	public synchronized String getNextId(){
		long currentTimeMillis = System.currentTimeMillis();
		if(currentTimeMillis > lastTime){
			lastTime = currentTimeMillis;
			buffer = sf.format(currentTimeMillis)+workId;
		}
		return buffer + String.format(serialFormat, ++count);
	}
    /**
     * 获取交易流水号
     * @return
     */
	private long flowLastTime=0;
	private long idCount=0;
	private String fworkId=String.format("%05d", new Random().nextInt(100000));
	private final String flowFomat="%05d";
	private SimpleDateFormat ft=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public synchronized String getTradeFlowId(){
		long st=System.currentTimeMillis();
		if(st>flowLastTime){
			flowLastTime=st;
		}
		String flowId=ft.format(flowLastTime)+fworkId+String.format(flowFomat, ++idCount);
		return flowId;
	}
}
