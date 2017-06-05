package com.security.model;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User(String userName, String passWord, Integer userId) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.userId = userId;
	}

	private String userName;
	private String passWord;
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public User() {
		
	}
	public User(User user) {
		this.userName = user.getUserName();
		this.passWord = user.getPassWord();
		this.userId = user.getUserId();
	}
}
