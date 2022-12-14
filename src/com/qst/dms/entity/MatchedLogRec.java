
package com.qst.dms.entity;

import java.io.Serializable;
import java.util.Date;

//ƥ����־��¼��"��¼�ǳ���" ����

public class MatchedLogRec implements Serializable {

	private LogRec login;
	private LogRec logout;

	// user�û���¼��
	public String getUser() {
		return login.getUser();
	}

	// ����ʱ��
	public Date getLogInTime() {
		return login.getTime();
	}

	// �ǳ�ʱ��
	public Date getLogoutTime() {
		return logout.getTime();
	}

	// �����¼
	public LogRec getLogin() {
		return login;
	}

	// �ǳ���¼
	public LogRec getLogout() {
		return logout;
	}

	public MatchedLogRec() {
	}

	public MatchedLogRec(LogRec login, LogRec logout) {
		if (login.getLogType() != LogRec.LOG_IN) {
			throw new RuntimeException("���ǵ�¼��¼!");
		}
		if (logout.getLogType() != LogRec.LOG_OUT) {
			throw new RuntimeException("���ǵǳ���¼");
		}
		if (!login.getUser().equals(logout.getUser())) {
			throw new RuntimeException("��¼�ǳ�������ͬһ���û�!");
		}
		if (!login.getIp().equals(logout.getIp())) {
			throw new RuntimeException("��¼�ǳ�������ͬһ��IP��ַ!");
		}
		this.login = login;
		this.logout = logout;
	}

	public String toString() {
		return login.toString() + " | " + logout.toString();
	}

}
