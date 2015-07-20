package com.witalk.widget.model;

/***
 * 通讯记录实体类
 * @author linyl12723
 *
 */
public class CallLogModel {
	/***
	 * 通话人姓名
	 */
	private String name;
	/***
	 * 通话电话
	 */
	private String phone;
	/***
	 * 电话类型
	 * 1：呼入 2：呼出 3：未接
	 */
	private int type;
	/***
	 * 通话开始时间
	 */
	private String time;
	/***
	 * 通话时间
	 */
	private String duration;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
