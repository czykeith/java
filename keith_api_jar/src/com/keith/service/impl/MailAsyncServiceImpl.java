package com.keith.service.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.keith.core.util.DateUtil;
@Service
public class MailAsyncServiceImpl {
	 @Autowired
	 private JavaMailSender mailSender;
	/**
	 * 注册时发送激活邮件
	 * @param username
	 * @param to
	 * @param url
	 */
	 @Async
 	public void sendExceptionMail(String fromMail,String toMail,String content){
	   try {
		MimeMessage mailMsg = this.mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMsg,true, "UTF-8");
		 messageHelper.setTo(toMail);// 接收邮箱  
         messageHelper.setFrom(fromMail);// 发送邮箱  
         messageHelper.setSentDate(new Date());// 发送时间  
         messageHelper.setSubject("抢钱通平台异常:"+DateUtil.Date2Str(DateUtil.getCurrentTime()));// 邮件标题  
         messageHelper.setText(content,true);// 邮件内容  
         mailSender.send(mailMsg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
}
