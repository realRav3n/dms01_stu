package com.qst.dms.gather;

import java.util.ArrayList;

import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.exception.DataAnalyseException;

//���������࣬�̳�DataFilter�����࣬ʵ�����ݷ����ӿ�
public class TransportAnalyse extends DataFilter implements IDataAnalyse {
	// ��������
	private ArrayList<Transport> transSends = new ArrayList<>();
	// �ͻ�����
	private ArrayList<Transport> transIngs = new ArrayList<>();
	// ��ǩ�ռ���
	private ArrayList<Transport> transRecs = new ArrayList<>();

	// ���췽��
	public TransportAnalyse() {
	}

	public TransportAnalyse(ArrayList<Transport> trans) {
		super(trans);
	}

	// ʵ��DataFilter�������еĹ��˳��󷽷�
	public void doFilter() {
		// ��ȡ���ݼ���
		ArrayList<Transport> trans = (ArrayList<Transport>) this.getDatas();

		// ���������������ݽ��й��ˣ���������״̬�ֱ���ڲ�ͬ�ļ�����
		for (Transport tran : trans) {
			if (tran.getTransportType() == Transport.SENDDING) {
				transSends.add(tran);
			} else if (tran.getTransportType() == Transport.TRANSPORTING) {
				transIngs.add(tran);
			} else if (tran.getTransportType() == Transport.RECIEVED) {
				transRecs.add(tran);
			}
		}

	}

	// ʵ��IDataAnalyse�ӿ������ݷ�������
	public ArrayList<MatchedTransport> matchData() {
		// ��������ƥ�伯��
		ArrayList<MatchedTransport> matchTrans = new ArrayList<>();
		// ����ƥ�����
		for (Transport send : transSends) {
			for (Transport tran : transIngs) {
				for (Transport rec : transRecs) {
					if ((send.getReciver().equals(tran.getReciver()))
							&& (send.getReciver().equals(rec.getReciver()))) {
						// �޸�����״̬����Ϊ��ƥ�䡱
						send.setType(DataBase.MATHCH);
						tran.setType(DataBase.MATHCH);
						rec.setType(DataBase.MATHCH);
						// ��ӵ�ƥ�伯����
						matchTrans.add(new MatchedTransport(send, tran, rec));
					}
				}
			}
		}
		try {
			if (matchTrans.size() == 0) {
				// û�ҵ�ƥ�������,�׳�DataAnalyseException�쳣
				throw new DataAnalyseException("û��ƥ����������ݣ�");
			}
		} catch (DataAnalyseException e) {
			e.printStackTrace();
		}
		return matchTrans;
	}
}
