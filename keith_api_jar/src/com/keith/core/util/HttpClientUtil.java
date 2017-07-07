package com.keith.core.util;



import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

/**
 * http请求工具类
 * @author keith
 * 2016年1月10日 16:17:54
 */
public class HttpClientUtil {
	private static HttpClient client=null;
	private static HttpClient sclient=null;
	private static HttpClientUtil clientutil=null;
	private HttpClientUtil(){}
	public static HttpClientUtil getInstances(){
		if(client==null){
			client=HttpClients.createDefault();
		}
		if(clientutil==null){
			clientutil=new HttpClientUtil();
		}
		return clientutil;
	}
	/**
	 * 通用get请求
	 * @param url  请求地址
	 * @param header  请求头设置
	 * @return
	 */
	public String sendGet(String url,Map<String,Object> header){
		HttpClient getClient=client;
		if(url.toLowerCase().startsWith("https")){
			getClient=createSSLhttp();
		}
		HttpGet get=new HttpGet(url);
		try{
			//添加请求头
			if(header!=null&&header.size()>0){
				Set<Entry<String,Object>> ens=header.entrySet();
				for (Entry<String, Object> entry : ens) {
					get.addHeader(entry.getKey(), (String)entry.getValue());
				}
			}
			HttpResponse res=getClient.execute(get);
			int status=res.getStatusLine().getStatusCode();
			String str=null; 
			if(status==200){
				HttpEntity entit=res.getEntity();
				if(entit!=null){
					str= EntityUtils.toString(entit);
					return str;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			get.abort();
		}
		return null;
	}
	/**
	 * 通用post请求方法
	 * @param url  请求地址
	 * @param params  请求参数
	 * @param header  请求头
	 * @param encoding 请求编码
	 * @return
	 */
	public String sendPost(String url,Map<String,Object> params,Map<String,Object> header,String encoding){
	   //根据请求的类型创建不同的连接
	   HttpClient postClient=client;
	   if(url.toLowerCase().startsWith("https")){
		   postClient=createSSLhttp();
		}
		HttpPost post=new HttpPost(url);
		//添加请求头
		if(header!=null&&header.size()>0){
			Set<Entry<String,Object>> ens=header.entrySet();
			for (Entry<String, Object> entry : ens) {
				post.addHeader(entry.getKey(), (String)entry.getValue());
			}
		}
		//添加请求参数
		List<NameValuePair> postList=new ArrayList<NameValuePair>();
		if(params!=null&&params.size()>0){
			Set<Entry<String,Object>> ens=params.entrySet();
			for (Entry<String, Object> entry : ens) {
				postList.add(new BasicNameValuePair(entry.getKey(),(String)entry.getValue()));
			}
		}
		try {
			//发送请求
			post.setEntity(new UrlEncodedFormEntity(postList,encoding));
			HttpResponse res=postClient.execute(post);
			int status=res.getStatusLine().getStatusCode();
			if(status==200){
				HttpEntity entit=res.getEntity();
				if(entit!=null){
				  return EntityUtils.toString(entit);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("通用post异常");
		}finally{
			post.abort();
		}
	   return null;
	}
	/**
	 * 通用post请求   body发送数据
	 * @param url
	 * @param context
	 * @param header
	 * @param encoding
	 * @return
	 */
	public String sendPost(String url,String body,Map<String,Object> header,String encoding){
		   //根据请求的类型创建不同的连接
		   HttpClient postClient=client;
		   if(url.toLowerCase().startsWith("https")){
			   postClient=createSSLhttp();
			}
			HttpPost post=new HttpPost(url);
			//添加请求头
			if(header!=null&&header.size()>0){
				Set<Entry<String,Object>> ens=header.entrySet();
				for (Entry<String, Object> entry : ens) {
					post.addHeader(entry.getKey(), (String)entry.getValue());
				}
			}
			try {
				//发送请求
				post.setEntity(new StringEntity(body, encoding));
				HttpResponse res=postClient.execute(post);
				int status=res.getStatusLine().getStatusCode();
				if(status==200){
					HttpEntity entit=res.getEntity();
					if(entit!=null){
					  return EntityUtils.toString(entit);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("通用post异常");
			}finally{
				post.abort();
			}
		   return null;
		}
	
	/**
	 * 创建https连接
	 * @return
	 */
	private HttpClient createSSLhttp(){
		if(sclient!=null){
			return sclient;
		}
		try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            //绑定https连接
            sclient= HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return sclient;
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
		//如果https连接创建失败，则创建http连接
        return  HttpClients.createDefault();
	}
}
