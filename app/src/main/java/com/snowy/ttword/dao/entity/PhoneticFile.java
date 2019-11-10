package com.snowy.ttword.dao.entity;

public class PhoneticFile {

	private String fileStr;
	private String text;
	private String regex;

	public PhoneticFile(String fileStr, String text, String regex) {
		this.fileStr = fileStr;
		this.text = text;
		this.regex = regex;
	}

	public String getFileStr() {
		return fileStr;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFileStr(String fileStr) {
		this.fileStr = fileStr;
	}


	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
