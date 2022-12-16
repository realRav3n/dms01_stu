/**
 * @作者 zhaokl
 */
package com.qst.dms.entity;

import java.io.Serializable;
import java.util.Date;

//货运物流信息
public class Transport extends DataBase implements Serializable{
	protected String transport_name;
	protected String dispatch;
	protected String destination;
	protected String recipient;
	protected String recipient_phone;
	protected String now_handle;
	protected String now_handle_phone;

	public String getTransport_name() {
		return transport_name;
	}

	public String getDispatch() {
		return dispatch;
	}

	public String getDestination() {
		return destination;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getRecipient_phone() {
		return recipient_phone;
	}

	public String getNow_handle() {
		return now_handle;
	}

	public String getNow_handle_phone() {
		return now_handle_phone;
	}

	public Transport() {

	}

	public Transport(int transport_id, String name, String dispatch, String destination,
			String recipient, String recipient_phone, String now_handler,
					 String now_handler_phone, int transport_type ) {
		this.transport_id=transport_id;
		this.transport_name = name;
		this.dispatch = dispatch;
		this.destination = destination;
		this.recipient = recipient;
		this.recipient_phone = recipient_phone;
		this.now_handle = now_handler;
		this.now_handle_phone = now_handler_phone;
		this.transport_type=transport_type;
	}

	public String toString() {
		return this.getTransport_id() + "," + this.getTransport_name() + "," + this.getDispatch()
				+ "," + this.getDestination() + "," + this.getRecipient() + "," + this.getRecipient_phone()
				+ "," + this.getNow_handle() + "," + this.getNow_handle_phone();
	}

}
