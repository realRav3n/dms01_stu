/**   
 * @��˾   ����ʵѵQST
 * @���� zhaokl
 */
package com.qst.dms.entity;

import java.io.Serializable;
import java.util.Date;

//����������Ϣ
public class Transport extends DataBase implements Serializable{
	/**
	 * ������
	 */
	private String handler;
	/**
	 * �ջ���
	 */
	private String reciver;
	/**
	 * ����״̬
	 */
	private int transportType;
	/**
	 * ����״̬����:������, �ͻ���, ��ǩ��
	 */
	public static final int SENDDING = 1;// ������
	public static final int TRANSPORTING = 2;// �ͻ���
	public static final int RECIEVED = 3;// ��ǩ��

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public int getTransportType() {
		return transportType;
	}

	public void setTransportType(int transportType) {
		this.transportType = transportType;
	}

	public Transport() {

	}

	public Transport(int id, Date time, String address, int type,
			String handler, String reciver, int transportType) {
		super(id, time, address, type);
		this.handler = handler;
		this.reciver = reciver;
		this.transportType = transportType;
	}

	public String toString() {
		return this.getId() + "," + this.getTime() + "," + this.getAddress()
				+ "," + this.getType() + "," + handler + "," + transportType;
	}

}
