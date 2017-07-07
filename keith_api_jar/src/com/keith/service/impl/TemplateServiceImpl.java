package com.keith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.keith.core.data.IData;
import freemarker.template.Template;

/**
 * 获取模板消息
 * @author keith
 * 2016年1月2日17:48:05
 */
@Service
public class TemplateServiceImpl {
	
	   @Autowired
	   private FreeMarkerConfigurer freeConfig;
	   public String getHTML(String templateName,IData params){
			String html=null;
	        try { 
				Template tpl =freeConfig.getConfiguration().getTemplate(templateName);
			    html = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, params); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       return html;
	   }
}

