package com.witalk.widget.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoteModel implements Serializable {
	private long imageUrl;
	private String name;
	private String pinying;
	private int id;
	private String phone;
	private String from;

	public long getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(long imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinying() {
		return pinying;
	}

	public void setPinying(String pinying) {
		this.pinying = pinying;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
