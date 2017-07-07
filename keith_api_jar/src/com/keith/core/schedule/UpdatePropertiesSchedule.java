package com.keith.core.schedule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.keith.service.PropertiesService;


/**
 * 定时读取配置文件更新
 * @author keith
 * 2015年11月28日 12:44:24
 */
@Component
public class UpdatePropertiesSchedule {
   @Value("${environment}")
   private String environment;
   @Autowired
   private PropertiesService proService;
   @Scheduled(fixedDelay=(1000*60*30))
   public void readProperties(){
	   //获取配置文件路径
	   String proPath=UpdatePropertiesSchedule.class.getClassLoader().getResource(environment+".properties").getPath();
	   Properties pro=new Properties();
	    try {
	    	//加载配置文件放到配置文件管理的service中
		    pro.load(new FileInputStream(new File(proPath)));
			 Set<Entry<Object,Object>> entrySet=pro.entrySet();
			 for (Entry<Object, Object> entry : entrySet) {
				proService.setObject((String)entry.getKey(), entry.getValue());
			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
}
