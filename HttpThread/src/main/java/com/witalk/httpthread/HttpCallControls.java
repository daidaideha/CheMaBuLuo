/**
 * @author Administrator
 * @CreateTime 2015年7月2日
 * @UpdateAuthor 
 * @UpdateTime	 
 */
package com.witalk.httpthread;

import java.util.Map;

import com.witalk.httpthread.volley.Request.Method;
import com.witalk.httpthread.volley.Response;
import com.witalk.httpthread.volley.data.ApiParams;
import com.witalk.httpthread.volley.data.RequestManager;
import com.witalk.httpthread.volley.toolbox.StringRequest;

public abstract class HttpCallControls {
	private String url = "";

	public void doCallRequest(final String caller, final String callees, final String appkey) {
		RequestManager.addRequest(new StringRequest(Method.POST, url,
				responseListener(), errorListener()) {
			protected Map<String, String> getParams() {
				return new ApiParams()
						.with("caller", caller)
						.with("callees", callees)
						.with("appkey", appkey);
			}
		}, this);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    /***
     * http呼叫请求回馈监听
     * @return
     */
	public abstract Response.Listener<String> responseListener();

    /***
     * http呼叫请求错误回馈监听
     * @return
     */
	public abstract Response.ErrorListener errorListener();
}
