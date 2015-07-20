/**
 * @author Administrator
 * @CreateTime 2015年6月26日
 * @UpdateAuthor 
 * @UpdateTime	 
 */
package com.witalk.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.witalk.widget.model.CallLogModel;
import com.witalk.widget.model.NoteModel;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 工具类
 * 
 * @author Pro.Linyl
 * @CreateTime 2015年6月26日
 * @UpdateAuthor
 * @UpdateTime
 * @description
 *
 */
@SuppressLint({ "DefaultLocale", "SimpleDateFormat" })
public class CMBLTools {
	/***
	 * 用来判断文件是否存在
	 * 
	 * @param filepath
	 *            文件路径
	 * @return
	 */
	public static boolean isFileExist(String filepath) {
		File f = new File(filepath);
		if (!f.exists())
			return false;
		return true;
	}

	/***
	 * 用来判断是否联网
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月15日
	 * @UpdateAuthor
	 * @UpdateTime
	 * @description
	 *
	 * @param context
	 * @return
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
	 * 创建文件夹
	 * 
	 * @param filePath
	 *            文件夹路径
	 */
	public static void mkFilePath(String filePath) {
		File file = new File(filePath);
		if (!file.exists())
			file.mkdirs();
	}

	/***
	 * 打开数据库操作
	 * 
	 * @param dbPath
	 * @return
	 */
	public static SQLiteDatabase dbOpen(String dbPath) {
		return SQLiteDatabase.openDatabase(dbPath, null, 0);
	}

	/***
	 * 获取首页显示的纯色bitmap对象
	 * 
	 * @param color
	 *            背景颜色
	 * @return 带文字的存在bitmap
	 */
	public static Bitmap getThumb(int color) {
		Bitmap bmp = Bitmap.createBitmap(150, 150, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();

		paint.setColor(color);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		canvas.drawRect(new Rect(0, 0, 150, 150), paint);
		if (bmp != null) {
			bmp = getRoundedCornerBitmap(bmp, 6);
		}
		return bmp;
	}

	/***
	 * 获取首页显示的纯色bitmap对象
	 * 
	 * @param color
	 *            背景颜色
	 * @return 带文字的存在bitmap
	 */
	public static Bitmap getThumb(int width, int hight, int color) {
		Bitmap bmp = Bitmap.createBitmap(width, hight, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();

		paint.setColor(color);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		canvas.drawRect(new Rect(0, 0, width, hight), paint);
		if (bmp != null) {
			bmp = getRoundedCornerBitmap(bmp, 6);
		}
		return bmp;
	}

	/***
	 * intent跳转
	 * 
	 * @param activity
	 *            原activity
	 * @param cls
	 *            跳转activity
	 * @param bundle
	 *            传递的bundle
	 */
	public static void IntentToOther(Activity activity, Class<?> cls,
			Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		if (bundle != null)
			intent.putExtras(bundle);
		activity.startActivity(intent);
	}

	/***
	 * 带result的activity跳转
	 * 
	 * @param activity
	 *            原activity
	 * @param cls
	 *            跳转activity
	 * @param bundle
	 *            传递的bundle
	 * @param code
	 *            result值
	 */
	public static void IntentToOtherForResult(Object activity, Class<?> cls,
			Bundle bundle, int code) {

		Intent intent = new Intent();
		if (bundle != null)
			intent.putExtras(bundle);
		if (activity instanceof Activity) {
			intent.setClass((Activity) activity, cls);
			((Activity) activity).startActivityForResult(intent, code);
		} else if (activity instanceof Fragment) {
			intent.setClass(((Fragment) activity).getActivity(), cls);
			((Fragment) activity).startActivityForResult(intent, code);
		}
	}

	/**
	 * 获得圆角图片的方法
	 * 
	 * @param bitmap
	 * @param roundPx
	 *            一般设成14
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		if (bitmap == null) {
			return null;
		}
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/***
	 * toast提示显示
	 * 
	 * @param msg
	 *            显示内容
	 * @param context
	 *            显示支持上下文
	 */
	public static void toastMsg(String msg, Context context) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/***
	 * 获取视图宽度
	 * 
	 * @param v
	 * @return
	 */
	public static int getViewWidth(View v) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
		return v.getMeasuredWidth();
	}

	/***
	 * 获取视图高度
	 * 
	 * @param v
	 * @return
	 */
	public static int getViewHeight(View v) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
		return v.getMeasuredHeight();
	}

	/***
	 * 获取通讯录列表数据
	 * 
	 * @param context
	 * @param type
	 *            1:获取收藏通讯录 0：普通通讯录
	 * @return
	 */
	public static ArrayList<NoteModel> getContact(Activity context, int type) {
		int preId = 0;
		ArrayList<NoteModel> listMembers = new ArrayList<NoteModel>();
		Cursor cursor = null;
		try {

			Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			// 这里是获取联系人表的电话里的信息 包括：名字，名字拼音，联系人id,电话号码；
			// 然后在根据"sort-key"排序
			ContentResolver resolver = context.getContentResolver();
			String selection = null; // 搜索条件
			if (type == 1) {
				selection = ContactsContract.Contacts.STARRED + " = 1";
			}
			cursor = resolver.query(uri, new String[] { "display_name",
					"sort_key", "contact_id", "data1", "photo_id" }, selection,
					null, "sort_key");
			NoteModel model;
			while (cursor.moveToNext()) {
				model = new NoteModel();
				int contact_id = cursor.getInt(2);
				String name = cursor.getString(0);
				String sortKey = getSortKey(cursor.getString(1));

				// 得到联系人头像ID
				Long photoid = cursor.getLong(4);
				model.setImageUrl(photoid);
				model.setName(name);
				model.setPinying(sortKey);
				model.setId(contact_id);
				// model.setPhone(phoneNumbers);
				if (name != null && preId != contact_id)
					listMembers.add(model);
				preId = contact_id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context = null;
			cursor.close();
		}
		return listMembers;
	}

	/***
	 * 得到联系人头像Bitamp
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月12日
	 * @UpdateAuthor
	 * @UpdateTime
	 * @description
	 *
	 * @param id
	 *            联系人id
	 * @param pid
	 *            联系人图片id
	 * @param mContext
	 *            上下文对象
	 * @return 联系人头像bitmap对象
	 */
	public static Bitmap getContactPhoto(int id, long pid, Context mContext, int defaultImg) {
		ContentResolver resolver = mContext.getContentResolver();
		Bitmap contactPhoto = null;
		if (pid > 0) {
			Uri puri = ContentUris.withAppendedId(
					ContactsContract.Contacts.CONTENT_URI, id);
			InputStream input = ContactsContract.Contacts
					.openContactPhotoInputStream(resolver, puri);
			contactPhoto = BitmapFactory.decodeStream(input);
		} else {
			contactPhoto = BitmapFactory.decodeResource(
					mContext.getResources(), defaultImg);
		}
		return contactPhoto;
	}

	// /***
	// * bitmap转化成byte流
	// *
	// * @param bitmap
	// * @return
	// */
	// private static byte[] getBytes(Bitmap bitmap) {
	// // 实例化字节数组输出流
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);// 压缩位图
	// return baos.toByteArray();// 创建分配字节数组
	// }

	/***
	 * btye流转化成bitmap
	 * 
	 * @param data
	 * @return
	 */
	public static Bitmap getBitmap(byte[] data) {
		return BitmapFactory.decodeByteArray(data, 0, data.length);// 从字节数组解码位图
	}

	/**
	 * 获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
	 * 
	 * @param sortKeyString
	 *            数据库中读取出的sort key
	 * @return 英文字母或者#
	 */
	private static String getSortKey(String sortKeyString) {
		String key = sortKeyString.substring(0, 1).toUpperCase();
		if (key.matches("[A-Z]")) {
			return key;
		}
		return "#";
	}

	/***
	 * 插入通话记录
	 * 
	 * @param calllogModel
	 * @param mContext
	 */
	public static void insertCallLog(CallLogModel calllogModel, Context mContext) {
		ContentResolver contentResolver = mContext.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(CallLog.Calls.CACHED_NAME, calllogModel.getName());
		values.put(CallLog.Calls.NUMBER, calllogModel.getPhone());
		values.put(CallLog.Calls.DATE, calllogModel.getTime());
		values.put(CallLog.Calls.DURATION, calllogModel.getDuration());
		values.put(CallLog.Calls.TYPE, calllogModel.getType());
		contentResolver.insert(CallLog.Calls.CONTENT_URI, values);
	}

	/***
	 * 获取系统通话记录
	 * 
	 * @param context
	 * @return
	 */
	public static List<CallLogModel> getCallLog(Activity context) {
		List<CallLogModel> listData = new ArrayList<CallLogModel>();
		Cursor cursor = context.getContentResolver()
				.query(CallLog.Calls.CONTENT_URI,
						new String[] { CallLog.Calls.NUMBER, Calls.CACHED_NAME,
								CallLog.Calls.TYPE, CallLog.Calls.DATE,
								Calls.DURATION }, null, null,
						CallLog.Calls.DEFAULT_SORT_ORDER);
		Date nowDate = new Date(System.currentTimeMillis());
		int i = 0;
		if (cursor.moveToFirst()) {
			do {
				CallLogModel calls = new CallLogModel();
				// 号码
				String number = cursor.getString(cursor
						.getColumnIndex(Calls.NUMBER));
				// 呼叫类型
				int type = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(Calls.TYPE)));
				// String type;
				// switch (Integer.parseInt(cursor.getString(cursor
				// .getColumnIndex(Calls.TYPE)))) {
				// case Calls.INCOMING_TYPE:
				// type = "呼入";
				// break;
				// case Calls.OUTGOING_TYPE:
				// type = "呼出";
				// break;
				// case Calls.MISSED_TYPE:
				// type = "未接";
				// break;
				// default:
				// type = "挂断";// 应该是挂断.根据我手机类型判断出的
				// break;
				// }
				SimpleDateFormat sfd = null;
				String time = "";
				Date date = new Date(Long.parseLong(cursor.getString(cursor
						.getColumnIndexOrThrow(Calls.DATE))));
				if (nowDate.getTime() - date.getTime() <= 24 * 60 * 60 * 1000) {
					sfd = new SimpleDateFormat("HH:mm");
					// 呼叫时间
					time = sfd.format(date);
				} else if (nowDate.getTime() - date.getTime() > 24 * 60 * 60 * 1000
						&& nowDate.getTime() - date.getTime() < 24 * 60 * 60
								* 1000 * 2) {
					time = "昨天";
				} else if (nowDate.getTime() - date.getTime() > 24 * 60 * 60
						* 1000 * 2
						&& nowDate.getTime() - date.getTime() < 24 * 60 * 60
								* 1000 * 3) {
					time = "前天";
				} else {
					sfd = new SimpleDateFormat("yy/M/d");
					// 呼叫时间
					time = sfd.format(date);
				}
				// 联系人
				String name = cursor.getString(cursor
						.getColumnIndexOrThrow(Calls.CACHED_NAME));
				// 通话时间,单位:s
				String duration = cursor.getString(cursor
						.getColumnIndexOrThrow(Calls.DURATION));
				calls.setName(name);
				if (number.substring(0, 3).equals("930")) {
					number = number.substring(3, number.length());
				}
				calls.setPhone(number);
				calls.setType(type);
				calls.setTime(time);
				calls.setDuration(duration);
				listData.add(calls);
				i++;
			} while (cursor.moveToNext() && i < 40);
		}
		cursor.close();
		return listData;
	}

	/***
	 * 验证手机号或者固话号
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;

		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;

	}

	/**
	 * 添加联系人 在同一个事务中完成联系人各项数据的添加
	 * 使用ArrayList<ContentProviderOperation>，把每步操作放在它的对象中执行
	 * */
	public static void testAddContacts2(Context context) {
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = context.getContentResolver();
		// 第一个参数：内容提供者的主机名
		// 第二个参数：要执行的操作
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		for (int i = 0; i < 100; i++) {
			// 操作2.添加data表中name字段
			uri = Uri.parse("content://com.android.contacts/data");
			ContentProviderOperation operation2 = ContentProviderOperation
					.newInsert(uri)
					// 第二个参数int previousResult:表示上一个操作的位于operations的第0个索引，
					// 所以能够将上一个操作返回的raw_contact_id作为该方法的参数
					.withValueBackReference("raw_contact_id", 0)
					.withValue("mimetype", "vnd.android.cursor.item/name")
					.withValue("data2", "周国平" + i).build();

			// 操作3.添加data表中phone字段
			uri = Uri.parse("content://com.android.contacts/data");
			ContentProviderOperation operation3 = ContentProviderOperation
					.newInsert(uri).withValueBackReference("raw_contact_id", i)
					.withValue("mimetype", "vnd.android.cursor.item/phone_v2")
					.withValue("data2", "2").withValue("data1", "15099144117")
					.build();

			operations.add(operation2);
			operations.add(operation3);

		}

		try {
			resolver.applyBatch("com.android.contacts", operations);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * dp单位转换成px单位
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月26日
	 * @UpdateAuthor
	 * @UpdateTime
	 * @description
	 *
	 * @param dp
	 *            dp单位的大小
	 * @param context
	 *            上下文对象
	 * @return px单位的大小
	 */
	public static int dp2px(int dp, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

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
	public static String getVersion(Application context) {
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
		if (text != null && !text.trim().equals("") && !text.trim().equals("null")) {
			return false;
		}
		return true;
	}
}
