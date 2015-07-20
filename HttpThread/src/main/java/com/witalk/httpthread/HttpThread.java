/**
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @UpdateAuthor Pro.Linyl
 * @UpdateTime 2015年6月5日 
 */
package com.witalk.httpthread;

import java.io.File;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.witalk.httpthread.volley.Request;
import com.witalk.httpthread.volley.Request.Method;
import com.witalk.httpthread.volley.RequestQueue;
import com.witalk.httpthread.volley.Response;
import com.witalk.httpthread.volley.VolleyError;
import com.witalk.httpthread.volley.data.ApiParams;
import com.witalk.httpthread.volley.data.RequestManager;
import com.witalk.httpthread.volley.files.MultiPartStack;
import com.witalk.httpthread.volley.files.MultiPartStringRequest;
import com.witalk.httpthread.volley.toolbox.StringRequest;
import com.witalk.httpthread.volley.toolbox.Volley;

/***
 * 
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @UpdateAuthor Pro.Linyl
 * @UpdateTime 2015年6月5日
 * @description http请求操作类
 *
 */
public class HttpThread {
	/***
	 * 上下文对象
	 */
	private Context context;
	
	public HttpThread(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/***
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime 2015年6月5日
	 * @description http请求发起操作
	 *
	 * @param bodyBase http请求消息类
	 * @param handler 返回监听handler
	 */
	public void executeRequest(final HttpBodyBase bodyBase, Handler handler) {
		if (bodyBase == null) {
			Log.e(HttpFinalValue.Tag, "the HttpBodyBase is null");
			return;
		}
		Request<String> request = new StringRequest(Method.POST, bodyBase.getUrl(), 
				responseListener(handler, bodyBase.getAction()), errorListener()) {
			
			protected Map<String, String> getParams() {
				ApiParams apiParams = new ApiParams();
				if (!HttpTools.isValueEmpty(bodyBase.getToken())) {
					apiParams.with("token", bodyBase.getToken());
				}
				if (!HttpTools.isValueEmpty(bodyBase.getParams().toString())) {
					apiParams.with("params", bodyBase.getParams().toString());
				}
				apiParams.with("timestamp", bodyBase.getTimestamp() + "");
				apiParams.with("appSign", bodyBase.getAppSign());
				apiParams.with("appKey", bodyBase.getAppKey());
				apiParams.with("appClient", bodyBase.getAppClient());
				apiParams.with("appVersion", bodyBase.getAppVersion());
				apiParams.with("versionCode", bodyBase.getVersionCode() + "");
				return apiParams;
			}
			
		};
		if (!HttpTools.isConnection(context)) {
			Toast.makeText(context, "网络未连接,请打开网络后重新操作", Toast.LENGTH_LONG)
					.show();
		} else {
			RequestManager.addRequest(request, context);
		}
	}
	
	/***
	 * http请求发起操作(文件上传)
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月23日
	 * @UpdateAuthor 
	 * @UpdateTime	
	 * @description
	 *
	 * @param url 上传路径
	 * @param handler 请求返回监听回调
	 * @param files 上传的文件
	 * @param map 上传的其他参数
	 */
	public void executeRequest(final HttpBodyBase bodyBase, Handler handler, final Map<String, File[]> files,
			final Map<String, String> map) {
		if (bodyBase == null) {
			Log.e(HttpFinalValue.Tag, "the HttpBodyBase is null");
			return;
		}
		MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
				Method.POST, bodyBase.getUrl(), responseListener(handler, bodyBase.getAction()), errorListener()) {

			@Override
			public Map<String, File[]> getFileUploads() {
				return files;
			}

			@Override
			public Map<String, String> getStringUploads() {
				ApiParams apiParams = new ApiParams();
				if (!HttpTools.isValueEmpty(bodyBase.getToken())) {
					apiParams.with("token", bodyBase.getToken());
				}
				apiParams.with("timestamp", bodyBase.getTimestamp() + "");
				apiParams.with("appSign", bodyBase.getAppSign());
				apiParams.with("appKey", bodyBase.getAppKey());
				apiParams.with("appClient", bodyBase.getAppClient());
				apiParams.with("appVersion", bodyBase.getAppVersion());
				apiParams.with("versionCode", bodyBase.getVersionCode() + "");
				apiParams.putAll(map);
				return apiParams;
			}
			
		};
		if (!HttpTools.isConnection(context)) {
			Toast.makeText(context, "网络未连接,请打开网络后重新操作", Toast.LENGTH_LONG)
					.show();
		} else {
			RequestQueue mSingleQueue = Volley.newRequestQueue(context, new MultiPartStack());
			mSingleQueue.add(multiPartRequest);
		}
	}

	/***
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime 2015年6月5日
	 * @description 获取http请求返回的结果
	 *
	 * @param handler http请求结果返回监听handler
	 * @return http请求返回的结果
	 */
	private Response.Listener<String> responseListener(final Handler handler, final String action) {
		if (handler == null) {
			Log.e(HttpFinalValue.Tag, "the handler is null");
			return new Response.Listener<String>() {

				@Override
				public void onResponse(String response) {
					// TODO Auto-generated method stub
					Log.i(HttpFinalValue.Tag, response);
				}
				
			};
		}
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					int code = jsonObject.getInt("code");
					String message = jsonObject.getString("msg");
					if (code == HttpFinalValue.HTTP_SUCCESS) {
						String data = jsonObject.getString("data");
						Message msg = handler.obtainMessage();
						Bundle bundle = new Bundle();
						bundle.putString("data", data);
						bundle.putString("action", action);
						msg.setData(bundle);
						handler.sendMessage(msg);
					} else {
						Toast.makeText(context, message, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	/***
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime 2015年6月5日
	 * @description 获取http请求链接出错监听
	 *
	 * @return http请求链接出错监听
	 */
	private Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
    			Toast.makeText(context, "链接失败！", Toast.LENGTH_LONG).show();
			}
		};
	}

}
