package com.qst.dms.dos;

import java.util.ArrayList;
import java.util.Scanner;

import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.gather.LogRecAnalyse;
import com.qst.dms.gather.TransportAnalyse;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;

public class MenuDriver {
	public static void main(String[] args) {
		// ����һ���Ӽ��̽������ݵ�ɨ����
		Scanner scanner = new Scanner(System.in);

		// ����һ������ArrayList���ϴ洢��־����
		ArrayList<LogRec> logRecList = new ArrayList<>();
		// ����һ������ArrrayList���ϴ洢��������
		ArrayList<Transport> transportList = new ArrayList<>();

		// ����һ����־ҵ����
		LogRecService logService = new LogRecService();
		// ����һ������ҵ����
		TransportService tranService = new TransportService();

		// ��־����ƥ�伯��
		ArrayList<MatchedLogRec> matchedLogs = null;
		// ��������ƥ�伯��
		ArrayList<MatchedTransport> matchedTrans = null;
		try {
			while (true) {
				// ����˵����棬�貹�� 
				System.out.println("***********************");
				System.out.println("��ӭ������־������Ϣ����ϵͳ��");
				System.out.println("*1�����ݲɼ�            2������ƥ��*");
				System.out.println("*3�����ݼ�¼            4��������ʾ*");
				System.out.println("*5�����ݷ���            0���˳�Ӧ��*");
				System.out.println("***********************");

				// ��ʾ�û�����Ҫ�����Ĳ˵���
				System.out.println("������˵��0~5����");

				// ���ռ��������ѡ��
				int choice = scanner.nextInt();

				switch (choice) {
				case 1: {
					System.out.println("������ɼ��������ͣ�1.��־    2.����");
					// ���ռ��������ѡ��
					int type = scanner.nextInt();
					if (type == 1) {
						System.out.println("���ڲɼ���־���ݣ���������ȷ��Ϣ��ȷ�����ݵ������ɼ���");
						// �ɼ���־���ݣ��貹��LogService���е�inputLog����
						LogRec log = logService.inputLog();
						// ���ɼ�����־������ӵ�logRecList������
						logRecList.add(log);
					} else if (type == 2) {
						System.out.println("���ڲɼ��������ݣ���������ȷ��Ϣ��ȷ�����ݵ������ɼ���");
						// �ɼ��������ݣ��貹��tranService���е�inputTransport����
						Transport tran = tranService.inputTransport();
						// ���ɼ�������������ӵ�transportList������
						transportList.add(tran);
					}
				}
					break;
				case 2: {
					System.out.println("������ƥ���������ͣ�1.��־    2.����");
					// ���ռ��������ѡ��
					int type = scanner.nextInt();
					if (type == 1) {
						System.out.println("������־���ݹ���ƥ��...");
						// ������־���ݷ�������������־����ɸѡ��ƥ��
						LogRecAnalyse logAn = new LogRecAnalyse(logRecList);
						// ��ʵ��doFilter���󷽷�������־���ݽ��й��ˣ�������־��¼״̬
						//�ֱ���ڵ�¼�͵ǳ�����������
						logAn.doFilter();
						// ��־���ݷ���
						matchedLogs = logAn.matchData();
						System.out.println("��־���ݹ���ƥ����ɣ�");
					} else if (type == 2) {
						System.out.println("�����������ݹ���ƥ��...");
						// �����������ݷ�������
						TransportAnalyse transAn = new TransportAnalyse(
								transportList);
						// �������ݹ���
						transAn.doFilter();
						// �������ݷ���
						matchedTrans = transAn.matchData();
						System.out.println("�������ݹ���ƥ����ɣ�");
					}
				}
					break;
				case 3:
					logService.saveMatchLog(matchedLogs);
					System.out.println("���ݼ�¼ ��...");

					break;
				case 4: {
					System.out.println("��ʾƥ������ݣ�");
					if (matchedLogs == null || matchedLogs.size() == 0) {
						System.out.println("ƥ�����־��¼��0����");
					} else {
						//���ƥ�����־��Ϣ
						logService.showMatchLog(matchedLogs);
					}
					if (matchedTrans == null || matchedTrans.size() == 0) {
						System.out.println("ƥ���������¼��0����");
					} else {
						// ���ƥ���������Ϣ
						tranService.showMatchTransport(matchedTrans);
					}
				}
					break;
				case 5:
					System.out.println("���ݷ��� ��...");
					break;
				case 0:
					// Ӧ�ó����˳�
					System.exit(0);
				default:
					System.out.println("��������ȷ�Ĳ˵��0~5����");
				}

			}
		} catch (Exception e) {
			System.out.println("��������ݲ��Ϸ���");
		}
	}
}
