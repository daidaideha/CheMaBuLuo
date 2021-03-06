package com.witalk.widget;

import android.content.Context;
import android.content.SharedPreferences;

public class sPreferences {
	//private Context c;
	private  SharedPreferences sp;
	public sPreferences(Context context) {
		sp = context.getSharedPreferences("cmbl", 0);
	}
	/** 获取全局配置文件对象 **/
	public SharedPreferences getSp(){
		return sp;
	}
	/** 更新全局配置文件键�?�?**/
	public boolean updateSp(String key, Object value){
		SharedPreferences.Editor editor = sp.edit();
		if(value instanceof String){
			editor.putString(key, value.toString());
		}else if(value instanceof Integer){
			editor.putInt(key, ((Integer)value).intValue());
		}else if(value instanceof Boolean){
			editor.putBoolean(key, ((Boolean)value).booleanValue());
		}else if(value instanceof Long){
			editor.putLong(key, ((Long)value).longValue());
		}else if(value instanceof Float){
			editor.putFloat(key, ((Float)value).floatValue());
		}else{
			editor.putString(key, value.toString());
		}
		return editor.commit();
	}

	/***
	 * 清除关键字为key的键值对
	 * @param key 关键字
	 * @return
	 */
	public boolean remove(String key) {
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);

		return editor.commit();
	}
}
