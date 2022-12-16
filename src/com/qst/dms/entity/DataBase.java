
package com.qst.dms.entity;

import java.io.Serializable;
import java.util.Date;

//数据基础类
/**
 * @作者 awa
 */
public class DataBase implements Serializable{
	protected int transport_id; //快递单号
	// 状态
	protected int transport_type; //快递类型
	// 状态常量
	public static final int GATHER=0;//未发货
	public static final int SEND=1;//送货中
	public static final int ARRIVE=2;//已送达
	public static final int RECEIVE=3;//已取件

	public int getTransport_id() {
		return transport_id;
	}

	public int getTransport_type() {
		return transport_type;
	}


	public DataBase() {
	}

	public DataBase(int id, int type) {
		this.transport_id = id;
		this.transport_type = type;
	}


	public String toString() {
		return getTransport_id() + "," + getTransport_type();
	}

}
