/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package com.witalk.httpthread.volley.data;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.witalk.httpthread.volley.AuthFailureError;
import com.witalk.httpthread.volley.NetworkResponse;
import com.witalk.httpthread.volley.ParseError;
import com.witalk.httpthread.volley.Request;
import com.witalk.httpthread.volley.Response;
import com.witalk.httpthread.volley.Response.ErrorListener;
import com.witalk.httpthread.volley.Response.Listener;
import com.witalk.httpthread.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T> extends Request<T> {
	private final Gson mGson = new Gson();
	private final Class<T> mClazz;
	private final Listener<T> mListener;
	private final Map<String, String> mHeaders;

	public GsonRequest(String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
		this(Method.GET, url, clazz, null, listener, errorListener);
	}

	public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mHeaders = headers;
		this.mListener = listener;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return mHeaders != null ? mHeaders : super.getHeaders();
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(mGson.fromJson(json, mClazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}
