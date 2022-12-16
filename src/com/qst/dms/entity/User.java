/**
 * @作者 awa
 */

package com.qst.dms.entity;

//用户实体
public class User {
	private String user_name;
	private String password;
	private String address;
	private String phone;
	private int user_type;

	public String getUsername() {
		return user_name;
	}
	public String getPassword() {
		return password;
	}
	public String getAddress() {
		return address;
	}
	public String getPhone() {return phone;}
	public int getUsertype() {return user_type;}

	public User() {
	}

	public User(String username, String password,String address, String phone, int usertype) {
		this.user_name = username;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.user_type = usertype;
	}

	public String toString() {
		return this.getUsername() + "," +this.getPassword()+ "," + this.getAddress()
				+ "," + this.getPhone()	+ "," + this.getUsertype();
	}
}
