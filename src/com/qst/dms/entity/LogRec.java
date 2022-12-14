
package com.qst.dms.entity;

import java.io.Serializable;
import java.util.Date;

//�û���¼��־��¼
public class LogRec extends DataBase implements Serializable{
	/**
	 *  ��¼�û���
	 */
	private String user;
	/**
	 * ��¼�û�����IP��ַ
	 */
	private String ip;
	/**
	 * ��¼״̬����¼���ǳ�
	 */
	private int logType;
	/**
	 * ��¼����LOG_IN���ǳ���������LOG_OUT
	 */
    public static final int LOG_IN=1;
    public static final int LOG_OUT=0;
    
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getLogType() {
		return logType;
	}

	
	public void setLogType(int logType) {
		this.logType = logType;
	}

	public LogRec() {
	}

	public LogRec(int id, Date time, String address, int type,String user,String ip,int logType) {
		super(id,time,address,type);
		this.user=user;
		this.ip=ip;
		this.logType=logType;
	}
	
	public String toString() {
		return this.getId() + "," +this.getTime() + "," +this.getAddress() + "," + this.getType() + ","+user+","+ip+","+logType;
	}
}
