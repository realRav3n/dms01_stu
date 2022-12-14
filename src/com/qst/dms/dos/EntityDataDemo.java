package com.qst.dms.dos;

import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.Transport;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;

public class EntityDataDemo {
	public static void main(String[] args) {
		// ����һ����־ҵ����
		LogRecService logService = new LogRecService();
		// ����һ����־�������飬���ڴ�Ųɼ���������־��Ϣ
		LogRec[] logs = new LogRec[3];
		for (int i = 0; i < logs.length; i++) {
			System.out.println("��"+(i+1)+"����־���ݲɼ���");
			logs[i] = logService.inputLog();
		}
		// ����ɼ�����־��Ϣ
		logService.showLog(logs);

		// ����һ������ҵ����
		TransportService tranService = new TransportService();
		// ����һ�������������飬���ڴ�Ųɼ�������������Ϣ
		Transport[] transports = new Transport[2];
		for (int i = 0; i < transports.length; i++) {
			System.out.println("��"+(i+1)+"���������ݲɼ���");
			transports[i] = tranService.inputTransport();
		}
		//����ɼ���������Ϣ
		tranService.showTransport(transports);

	}

}
