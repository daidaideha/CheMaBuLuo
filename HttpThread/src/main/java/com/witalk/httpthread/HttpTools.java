/**
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @UpdateAuthor Pro.Linyl
 * @UpdateTime	 2015年6月5日
 */
package com.witalk.httpthread;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/***
 * 
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @UpdateAuthor Pro.Linyl
 * @UpdateTime	 2015年6月5日
 * @description Http链接工具类
 *
 */
public class HttpTools {

	/***
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime 2015年6月5日
	 * @description 获取当前应用程序的版本号
	 *
	 * @param context application
	 * @return 版本名,版本号（versionName+ "," + versionCode）
	 */
	private static String getVersion(Application context) {
		String versionInfo = "";
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(context.getPackageName(), 0);
			versionInfo += packinfo.versionName;
			versionInfo += ("," + packinfo.versionCode);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionInfo;
	}
	
	/***
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime 2015年6月8日（加入空值判断）
	 * @description 设置固定入参
	 *
	 * @param base http请求消息类
	 * @param context application
	 */
	public static void setFixedInfo(HttpBodyBase base, Application context) {
		if (base == null) {
			Log.e(HttpFinalValue.Tag, "the HttpBodyBase is null");
			return;
		}
		String[] versionInfo = getVersion(context).split(",");
		PackageInfo ai = null;
		try {
			ai = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ai.applicationInfo.metaData == null) {
			Toast.makeText(context, "app_key为空，请在AndroidManifest.xml文件中加入你的app_key", Toast.LENGTH_LONG).show();
			return;
		}
		Object app_key = (Object) ai.applicationInfo.metaData.get("app_key");
		if (app_key == null) {
			Toast.makeText(context, "app_key为空，请在AndroidManifest.xml文件中加入你的app_key", Toast.LENGTH_LONG).show();
			return;
		}
		Object app_secret = (Object) ai.applicationInfo.metaData.get("app_secret");
		if (app_secret == null) {
			Toast.makeText(context, "app_secret为空，请在AndroidManifest.xml文件中加入你的app_secret", Toast.LENGTH_LONG).show();
			return;
		}
		long currentTimeMillis = System.currentTimeMillis() / 1000;
		base.setAppClient("android");
		base.setAppSecret(app_secret.toString());
		base.setAppKey(app_key.toString());
		base.setAppVersion(versionInfo[0]);
		base.setVersionCode(Integer.valueOf(versionInfo[1]));
		base.setTimestamp(currentTimeMillis);
	}

	/***
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime 2015年6月5日
	 * @description MD5加密方法
	 *
	 * @param string 需加密的参数
	 * @return 加密后的字符串
	 */
	public static String md5(String string) {
	    byte[] hash;
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Huh, MD5 should be supported?", e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	    }

	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString();
	}

	/***
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime 2015年6月5日
	 * @description 网络链接判断
	 *
	 * @param context 上下文对象
	 * @return true 为已连接  false 为未连接
	 */
	public static boolean isConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			return info.isAvailable();
		}
		return false;
	}
	
	/***
	 * 判断字符串非空，非null
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月18日
	 * @UpdateAuthor 
	 * @UpdateTime	
	 * @description
	 *
	 * @param text 需要判断的字符串
	 * @return
	 */
	public static boolean isValueEmpty(String text) {
		if (text != null && (!text.trim().equals("") || !text.trim().equals("null"))) {
			return false;
		}
		return true;
	}
}
