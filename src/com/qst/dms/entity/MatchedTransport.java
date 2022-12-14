/**   
 * @��˾   ����ʵѵQST
 * @���� zhaokl
 */
package com.qst.dms.entity;

import java.io.Serializable;

public class MatchedTransport implements Serializable{
	private Transport send;
	private Transport trans;
	private Transport receive;

	public Transport getSend() {
		return send;
	}

	public void setSend(Transport send) {
		this.send = send;
	}

	public Transport getTrans() {
		return trans;
	}

	public void setTrans(Transport trans) {
		this.trans = trans;
	}

	public Transport getReceive() {
		return receive;
	}

	public void setReceive(Transport receive) {
		this.receive = receive;
	}

	public MatchedTransport() {

	}

	public MatchedTransport(Transport send, Transport trans, Transport receive) {
		if (send.getTransportType() != Transport.SENDDING) {
			throw new RuntimeException("���Ƿ�����¼!");
		}
		if (trans.getTransportType() != Transport.TRANSPORTING) {
			throw new RuntimeException("�����ͻ���¼!");
		}
		if (receive.getTransportType() != Transport.RECIEVED) {
			throw new RuntimeException("����ǩ�ռ�¼!");
		}
		this.send = send;
		this.trans = trans;
		this.receive = receive;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return send.toString() + "|" + trans.toString() + "|" + receive;
	}

}
