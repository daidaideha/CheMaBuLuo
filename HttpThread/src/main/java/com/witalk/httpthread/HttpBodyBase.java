/**
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @company 杭州微聊网络科技有限公司 版权所有
 * 
 */
package com.witalk.httpthread;

import org.json.JSONObject;

import android.app.Application;

/***
 * 
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @UpdateAuthor Pro.Linyl
 * @UpdateTime 2015年6月5日
 * @description http求情消息基类
 *
 */
public abstract class HttpBodyBase {
	/*** http url path */
	protected String url = "";
	/*** url body path */
	protected String action = "";
	/***
	 * 应用AppSecret 示例：32位小写MD5
	 */
	protected String appSecret = "";
	/***
	 * 应用AppKey 示例：10001
	 */
	protected String appKey = "";
	/***
	 * 应用版本号 示例：1.0.1
	 */
	protected String appVersion = "";
	/***
	 * 客户端类型 示例：iphone/android
	 */
	protected String appClient = "";
	/***
	 * 接口调用的时间戳，精确到秒 示例：1395509173
	 */
	protected long timestamp = 0;
	/***
	 * 用户令牌 示例：
	 */
	protected String token = "";
	/***
	 * 接口调用签名 示例：9bc52f77b9778fe7b829b4c4bdf32a74
	 */
	protected String appSign = "";
	/***
	 * 接口参数 示例：
	 */
	protected JSONObject params;
	/***
	 * 整数值，不参与签名 示例：1234
	 */
	protected int versionCode = 0;
	
	public HttpBodyBase(Application context) {
		// TODO Auto-generated constructor stub
		HttpTools.setFixedInfo(this, context);
	}

	/***
	 * 初始化URL
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description 初始化URL
	 *
	 */
	private void initUrl() {
		// TODO Auto-generated constructor stub
		url = HttpFinalValue.domain + action + HttpFinalValue.end;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
		initUrl();
	}

	/***
	 * 计算appSign值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description MD5加密出appSign的值
	 *
	 */
	protected void initAppSign() {
		appSign = HttpTools.md5(appKey + appVersion + appClient + timestamp
				+ token + appSecret + HttpTools.md5(params.toString()));
	}

	/***
	 * 获取appSecret的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return appSecret的值
	 */
	public String getAppSecret() {
		return appSecret;
	}

	/***
	 * 设置appSecret的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param appSecret的值
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	/***
	 * 获取appKey的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return appKey的值
	 */
	public String getAppKey() {
		return appKey;
	}

	/***
	 * 设置appKey的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param appKey的值
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/***
	 * 获取appVersion的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return appVersion的值
	 */
	public String getAppVersion() {
		return appVersion;
	}

	/***
	 * 设置appVersion的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param appVersion的值
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/***
	 * 获取appClient的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return appClient的值
	 */
	public String getAppClient() {
		return appClient;
	}

	/***
	 * 设置appClient的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param appClient的值
	 */
	public void setAppClient(String appClient) {
		this.appClient = appClient;
	}

	/***
	 * 获取versionCode的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return versionCode的值
	 */
	public int getVersionCode() {
		return versionCode;
	}

	/***
	 * 设置versionCode的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param versionCode的值
	 */
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	/***
	 * 获取timestamp的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return timestamp的值
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/***
	 * 设置timestamp的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param timestamp的值
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/***
	 * 获取token的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return token的值
	 */
	public String getToken() {
		return token;
	}

	/***
	 * 设置token的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param token的值
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/***
	 * 获取params的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return params的值
	 */
	public JSONObject getParams() {
		return params;
	}

	/***
	 * 设置params的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param params的值
	 */
	public void setParams(JSONObject params) {
		this.params = params;
	}

	/***
	 * 获取url的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return url的值
	 */
	public String getUrl() {
		return url;
	}

	/***
	 * 设置url的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param url的值
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/***
	 * 获取appSign的值
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @return appSign的值
	 */
	public String getAppSign() {
		return appSign;
	}
	
	/***
	 * 构建params参数所需的json对象
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor 
	 * @UpdateTime	
	 * @description
	 *
	 */
	public abstract void packget();
}
