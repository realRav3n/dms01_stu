package com.qst.dms.entity;

//用户实体
public class User {
	// 用户id
	private int id;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 性别
	private int userClass;
	// 爱好
	private String phone;
	// 地址
	private String address;
	// 学历
	//private String degree;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserClass() {
		return userClass;
	}

	public void setUserClass(int userClass) {
		this.userClass = userClass;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User(int id, String username, String password, int userClass, String phone,String address) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.userClass = userClass;
		this.phone = phone;
		this.address = address;
	}
	public User( String username, String password, int userClass, String phone, String address) {
		this.username = username;
		this.password = password;
		this.userClass = userClass;
		this.address = address;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", userClass=" + userClass +
				", address='" + address + '\'' +
				'}';
	}
}
