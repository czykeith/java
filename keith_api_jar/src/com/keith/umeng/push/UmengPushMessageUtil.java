package com.keith.umeng.push;


import com.keith.core.data.IData;
import com.keith.umeng.push.android.AndroidBroadcast;
import com.keith.umeng.push.android.AndroidUnicast;
import com.keith.umeng.push.ios.IOSBroadcast;
import com.keith.umeng.push.ios.IOSUnicast;

/**
 * 友盟推送消息
 * @author keith
 * 2016年11月28日 15:10:56
 */
public class UmengPushMessageUtil {
	private PushClient client=new PushClient();
	private static UmengPushMessageUtil pushMessage=null;
	private UmengPushMessageUtil(){};
	public static UmengPushMessageUtil getInstance(){
		if(pushMessage==null){
			pushMessage=new UmengPushMessageUtil();
		}
		return pushMessage;
	}
	/**
	 * 发送安卓广播
	 * @param appkey
	 * @param appMasterSecret
	 * @param pushType
	 * @param title
	 * @param context
	 * @param extData ext_msg附加参数
	 */
	public void pushAndroidBroadcast(String appkey,String appMasterSecret,String pushType,String title,String context,IData extData) {
		try{
			AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
			broadcast.setTicker(pushType);
			broadcast.setTitle(title);
			broadcast.setText(context);
			broadcast.goAppAfterOpen();
			broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			broadcast.setProductionMode();
			broadcast.setExtraField("ext_msg",extData.toJSONObject().toJSONString());
			client.send(broadcast);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 发送安卓单个消息
	 * @param appkey
	 * @param appMasterSecret
	 * @param deviceToken
	 * @param pushType
	 * @param title
	 * @param context
	 * @param extData ext_msg附加参数
	 */
	public void sendAndroidUnicast(String appkey,String appMasterSecret,String deviceToken,String pushType,String title,String context,IData extData) {
		try{
			AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
			// TODO Set your device token
			unicast.setDeviceToken(deviceToken);
			unicast.setTicker(pushType);
			unicast.setTitle(title);
			unicast.setText(context);
			unicast.goAppAfterOpen();
			unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			unicast.setProductionMode();
			// Set customized fields
			unicast.setExtraField("ext_msg",extData.toJSONObject().toJSONString());
			client.send(unicast);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 发送IOS广播
	 * @param appkey
	 * @param appMasterSecret
	 * @param context
	 * @param extData ext_msg附加参数
	 */
	public void pushIOSBroadcast(String appkey,String appMasterSecret,String context,IData extData){
		try{
			IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);
			broadcast.setAlert(context);
			broadcast.setBadge( 0);
			broadcast.setSound("default");
			broadcast.setProductionMode();
			// Set customized fields
			broadcast.setCustomizedField("ext_msg",extData.toJSONObject().toJSONString());
			client.send(broadcast);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 发送IOS单个消息
	 * @param appkey
	 * @param appMasterSecret
	 * @param deviceToken
	 * @param context
	 * @param extData ext_msg附加参数
	 */
	public void pushIOSUnicast(String appkey,String appMasterSecret,String deviceToken,String context,IData extData){
		try{
			IOSUnicast unicast = new IOSUnicast(appkey,appMasterSecret);
			// TODO Set your device token
			unicast.setDeviceToken(deviceToken);
			unicast.setAlert(context);
			unicast.setBadge( 0);
			unicast.setSound( "default");
			// TODO set 'production_mode' to 'true' if your app is under production mode
			unicast.setProductionMode();
			// Set customized fields
			unicast.setCustomizedField("ext_msg",extData.toJSONObject().toJSONString());
			client.send(unicast);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
