package com.qst.dms.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.qst.dms.db.DBUtil;
import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTableModel;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.gather.LogRecAnalyse;
import com.qst.dms.gather.TransportAnalyse;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;
import com.qst.dms.util.Config;

//������
public class MainFrame_temp extends JFrame {
	// �����������
	private String serverIP;
	private JMenuBar menuBar;
	private JMenu menuOperate, menuHelp, menuMatch;
	private JMenuItem miGather, miSave, miSend,
			miShow, miExit, miCheck, miAbout, miLogMatch, miTransMatch;
	private JTabbedPane tpGather, showPane;
	private JPanel p, pLog, pTran, pLogId, pName, pLocation, pIP, pLogStatus,
			pLogButton, pTransId, pAdress, pHandler, pReceiver, pTranStatus,
			pTranButton;
	private JLabel lblLogId, lblName, lblLocation, lblIP, lblLogStatus,
			lblTransId, lblAdress, lblHandler, lblReceiver, lblTranStatus;
	private JTextField txtLogId, txtName, txtLocation, txtIP, txtTransId,
			txtAdress, txtHandler, txtReceiver;
	private JRadioButton rbLogin, rbLogout, rbSend, rbReceive, rbTrans;
	private JButton btnLogConfirm, btnLogReset, btnTranConfirm, btnTranReset,
			btnGather, btnMatchLog, btnMatchTrans, btnSave, btnSend, btnShow;
	private JComboBox<String> cmbTanStatus;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private CardLayout card;
	// ������־����
	private LogRec log;
	// ������������
	private Transport trans;
	// ������־�б�
	private ArrayList<LogRec> logList;
	// ���������б�
	private ArrayList<Transport> transList;
	// ����ƥ����־�б�
	private ArrayList<MatchedLogRec> matchedLogs;
	// ����ƥ�������б�
	private ArrayList<MatchedTransport> matchedTrans;
	// ������־ҵ�����
	private LogRecService logRecService;
	// ��������ҵ�����
	private TransportService transportService;
	private JButton btnNewButton;

	// ���췽��
	public MainFrame_temp() {
		// ���ø���Ĺ��췽��
		super("Q-DMSϵͳ�ͻ���");

		// ���ô����icon
		ImageIcon icon = new ImageIcon("images\\dms.png");
		this.setIconImage(icon.getImage());

		// �б�ҵ������ʼ��
		logList = new ArrayList<LogRec>();
		transList = new ArrayList<Transport>();
		matchedLogs = new ArrayList<MatchedLogRec>();
		matchedTrans = new ArrayList<MatchedTransport>();
		logRecService = new LogRecService();
		transportService = new TransportService();

		// ��ʼ���˵�
		initMenu();
		// ��ʼ��������
		initToolBar();

		// ���������ΪCardLayout��Ƭ����
		card = new CardLayout();
		p = new JPanel();
		p.setLayout(card);
		// ���ݲɼ���ѡ����
		tpGather = new JTabbedPane(JTabbedPane.TOP);
		// ������ʾ��ѡ����
		showPane = new JTabbedPane(JTabbedPane.TOP);
		showPane.addTab("��־", new JScrollPane());
		showPane.addTab("����", new JScrollPane());
		// ������ѡ������ӵ���Ƭ�����
		p.add(tpGather, "gather");
		p.add(showPane, "show");

		// ���������ӵ�������
		getContentPane().add(p, BorderLayout.CENTER);

		// ��ʼ����־���ݲɼ�����
		initLogGatherGUI();
		// ��ʼ���������ݲɼ�����
		initTransGatherGUI();

		// ���ô����ʼ�ɼ�
		this.setVisible(true);
		// ���ô����ʼ���
		this.setSize(600, 400);
		// ���ô��ڳ�ʼ������
		this.setLocationRelativeTo(null);
		// ����Ĭ�ϵĹرհ�ť����Ϊ�˳�����
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		new UpdateTableThread().start();
		serverIP = Config.getValue("serverIP");
	}

	// ��ʼ���˵��ķ���
	private void initMenu() {
		// ��ʼ���˵����
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		menuOperate = new JMenu("����");
		menuBar.add(menuOperate);

		miGather = new JMenuItem("�ɼ�����");
		// ע�����
		miGather.addActionListener(new GatherListener());
		menuOperate.add(miGather);

		// �����˵�
		menuMatch = new JMenu("ƥ������");
		miLogMatch = new JMenuItem("ƥ����־����");
		miLogMatch.addActionListener(new MatchLogListener());
		menuMatch.add(miLogMatch);
		miTransMatch = new JMenuItem("ƥ����������");
		miTransMatch.addActionListener(new MatchTransListener());
		menuMatch.add(miTransMatch);
		menuOperate.add(menuMatch);


		miSave = new JMenuItem("��������");
		miSave.addActionListener(new SaveDataListener());
		menuOperate.add(miSave);

		miSend = new JMenuItem("��������");
		miSend.addActionListener(new SendDataListener());
		menuOperate.add(miSend);

		miShow = new JMenuItem("��ʾ����");
		// ע�����
		miShow.addActionListener(new ShowDataListener());
		menuOperate.add(miShow);

		// ��ӷָ���
		menuOperate.addSeparator();

		miExit = new JMenuItem("�˳�ϵͳ");
		// ע�����
		miExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ʾȷ�϶Ի��򣬵�ѡ��YES_OPTIONʱ�˳�ϵͳ
				if (JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ�˳�ϵͳ��", "�˳�ϵͳ",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					// �˳�ϵͳ
					System.exit(0);
				}
			}
		});
		menuOperate.add(miExit);

		menuHelp = new JMenu("����");
		menuBar.add(menuHelp);

		miCheck = new JMenuItem("�鿴����");
		// ע�����
		miCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ʾ��Ϣ�Ի���
				JOptionPane.showMessageDialog(null,
						"��ϵͳʵ�����ݵĲɼ������˷���ƥ�䡢���桢���ͼ���ʾ����", "����",
						JOptionPane.QUESTION_MESSAGE);
			}
		});
		menuHelp.add(miCheck);

		miAbout = new JMenuItem("����ϵͳ");
		// ע�����
		miAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ʾ��Ϣ�Ի���
				JOptionPane.showMessageDialog(null,
						"�����ˣ�", "���в���",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		menuHelp.add(miAbout);
	}

	// ��ʼ���������ķ���
	private void initToolBar() {
		// ����������
		toolBar = new JToolBar();
		// ����������ӵ����山�������棩
		getContentPane().add(toolBar, BorderLayout.NORTH);

		// ��Ӵ���ͼ��Ĺ�������ť--->�ɼ�����
		ImageIcon gatherIcon = new ImageIcon("images\\gatherData.png");
		btnGather = new JButton("�ɼ�����", gatherIcon);
		// ע�����
		btnGather.addActionListener(new GatherListener());
		toolBar.add(btnGather);
		
		// ��Ӵ���ͼ��Ĺ�������ť--->ƥ����־����
		ImageIcon matchIcon = new ImageIcon("images\\matchData.png");
		btnMatchLog = new JButton("ƥ����־����", matchIcon);
		// ע�����
		btnMatchLog.addActionListener(new MatchLogListener());
		toolBar.add(btnMatchLog);

		ImageIcon matchIconTrans = new ImageIcon("images\\matchData.png");
		btnMatchTrans = new JButton("ƥ����������", matchIconTrans);
		btnMatchTrans.addActionListener(new MatchTransListener());
		toolBar.add(btnMatchTrans);

		// ��Ӵ���ͼ��Ĺ�������ť--->��������
		ImageIcon saveIcon = new ImageIcon("images\\saveData.png");
		btnSave = new JButton("��������", saveIcon);
		// ע�����
		btnSave.addActionListener(new SaveDataListener());
		toolBar.add(btnSave);

		// ��Ӵ���ͼ��Ĺ�������ť--->��������
		ImageIcon sendIcon = new ImageIcon("images\\sendData.png");
		btnSend = new JButton("��������", sendIcon);
		btnSend.addActionListener(new SendDataListener());
//		btnSend.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
		toolBar.add(btnSend);
		
		// ��Ӵ���ͼ��Ĺ�������ť--->��ʾ����
		ImageIcon showIcon = new ImageIcon("images\\showData.png");
		btnShow = new JButton("��ʾ����", showIcon);
		btnShow.addActionListener(new ShowDataListener());
		toolBar.add(btnShow);
	}

	// ��ʼ����־���ݲɼ�����ķ���
	private void initLogGatherGUI() {
			pLog = new JPanel();
			tpGather.addTab("��־", pLog);
			pLog.setLayout(new BoxLayout(pLog, BoxLayout.Y_AXIS));

			pLogId = new JPanel();
			pLog.add(pLogId);
			pLogId.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			//lblLogId = new JLabel("��־ID��");
			//pLogId.add(lblLogId);
			//txtLogId = new JTextField();
			//txtLogId.setPreferredSize(new Dimension(100, 20));
			//pLogId.add(txtLogId);

			pName = new JPanel();
			pLog.add(pName);
			pName.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			lblName = new JLabel("�û�����");
			pName.add(lblName);

			txtName = new JTextField();
			txtName.setPreferredSize(new Dimension(100, 20));
			pName.add(txtName);

			pLocation = new JPanel();
			pLog.add(pLocation);

			lblLocation = new JLabel("��¼�ص㣺");
			pLocation.add(lblLocation);

			txtLocation = new JTextField();
			txtLocation.setPreferredSize(new Dimension(100, 20));
			pLocation.add(txtLocation);

			pIP = new JPanel();
			pLog.add(pIP);

			lblIP = new JLabel("��¼IP��");
			pIP.add(lblIP);

			txtIP = new JTextField();
			txtIP.setPreferredSize(new Dimension(100, 20));
			pIP.add(txtIP);
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();
			txtIP.setText(ip);
		} catch (Exception e) {
			e.printStackTrace();
		}

			pLogStatus = new JPanel();
			pLog.add(pLogStatus);

			lblLogStatus = new JLabel("��¼״̬��");
			pLogStatus.add(lblLogStatus);

			rbLogin = new JRadioButton("��¼");
			pLogStatus.add(rbLogin);
			rbLogin.setSelected(true);

			rbLogout = new JRadioButton("�ǳ�");
			pLogStatus.add(rbLogout);

			ButtonGroup bg = new ButtonGroup();
			bg.add(rbLogin);
			bg.add(rbLogout);

			pLogButton = new JPanel();
			pLog.add(pLogButton);

			btnLogConfirm = new JButton("ȷ��");
			// ���ȷ�ϰ�ť����
			btnLogConfirm.addActionListener(new GatherLogListener());

			pLogButton.add(btnLogConfirm);

			btnLogReset = new JButton("����");
			// ������ð�ť����
			btnLogReset.addActionListener(new ResetListener());
			pLogButton.add(btnLogReset);
		}

	// ��ʼ���������ݲɼ�����ķ���
	private void initTransGatherGUI() {
		pTran = new JPanel();
		tpGather.addTab("����", pTran);
		pTran.setLayout(new BoxLayout(pTran, BoxLayout.Y_AXIS));

//		pTransId = new JPanel();
//		pTran.add(pTransId);

//		lblTransId = new JLabel("����ID��");
//		pTransId.add(lblTransId);
//
//		txtTransId = new JTextField();
//		txtTransId.setPreferredSize(new Dimension(100, 20));
//		pTransId.add(txtTransId);

		pAdress = new JPanel();
		pTran.add(pAdress);
		//pTran.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblAdress = new JLabel("Ŀ�ĵأ�");
		pAdress.add(lblAdress);
		//pAdress.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		txtAdress = new JTextField();
		txtAdress.setPreferredSize(new Dimension(100, 20));
		pAdress.add(txtAdress);

		pHandler = new JPanel();
		pTran.add(pHandler);

		lblHandler = new JLabel("�����ˣ�");
		pHandler.add(lblHandler);

		txtHandler = new JTextField();
		txtHandler.setPreferredSize(new Dimension(100, 20));
		pHandler.add(txtHandler);

		pReceiver = new JPanel();
		pTran.add(pReceiver);

		lblReceiver = new JLabel("�ջ��ˣ�");
		pReceiver.add(lblReceiver);

		txtReceiver = new JTextField();
		txtReceiver.setPreferredSize(new Dimension(100, 20));
		pReceiver.add(txtReceiver);

		pTranStatus = new JPanel();
		pTran.add(pTranStatus);

		lblTranStatus = new JLabel("����״̬��");
		pTranStatus.add(lblTranStatus);

		String[] tranStatus = new String[] { "������", "�ͻ���", "��ǩ��" };
		//cmbTanStatus = new JComboBox<String>(tranStatus);
		//cmbTanStatus.setEditable(true);
		rbSend = new JRadioButton("������");
		pTranStatus.add(rbSend);
		rbSend.setSelected(true);

		rbTrans = new JRadioButton("�ͻ���");
		pTranStatus.add(rbTrans);
		//rbReceive.setSelected(true);

		rbReceive = new JRadioButton("��ǩ��");
		pTranStatus.add(rbReceive);
		//rbReceive.setSelected(true);

		pTranButton = new JPanel();
		pTran.add(pTranButton);

		btnTranConfirm = new JButton("ȷ��");
		btnTranConfirm.addActionListener(new GatherTransListener());
		pTranButton.add(btnTranConfirm);

		btnTranReset = new JButton("����");
		btnTranReset.addActionListener(new ResetListener());
		pTranButton.add(btnTranReset);
	}

	// ���ݲɼ�������
	private class GatherListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// �л������Ŀ�ƬΪ�ɼ����
			card.show(p, "gather");
		}
	}

	// ��־���ݲɼ�������
	private class GatherLogListener implements ActionListener {
		// ���ݲɼ����¼�������
		public void actionPerformed(ActionEvent e) {
			// ��ȡ��־ID
			//int id = Integer.parseInt(txtLogId.getText().trim());
			int id = -1;
			// ������ǰʱ��
			Date time = new Date();
			// ��ȡ��ַ����ַ
			String adress = txtLocation.getText().trim();
			// ������������Ϊ���ɼ�
			int type = DataBase.GATHER;
			// ��ȡ�û�����
			String user = txtName.getText().trim();
			// ��ȡip��ַ
			String ip = txtIP.getText().trim();
			// ������־����
			int logType = rbLogin.isSelected() ? LogRec.LOG_IN : LogRec.LOG_OUT;
			// �����ݷ�װ����־����
			log = new LogRec(id, time, adress, type, user, ip, logType);
			// ����־������ӵ���־�б�
			logList.add(log);
			// ��ʾ�Ի���
			JOptionPane.showMessageDialog(null, "��־�ɼ��ɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// �������ݲɼ�������
	private class GatherTransListener implements ActionListener {
		// ���ݲɼ����¼�������
		public void actionPerformed(ActionEvent e) {
			// ��ȡ����ID
			int id = Integer.parseInt(txtTransId.getText().trim());//��Ϊ�Զ���ȡ
			// ������ǰʱ��
			Date time = new Date();
			// ��ȡ��ַ����ַ
			String adress = txtAdress.getText().trim();
			// ������������Ϊ���ɼ�
			int type = DataBase.GATHER;
			// ��ȡ��������Ϣ
			String handler = txtHandler.getText().trim();
			// ��ȡ��������Ϣ
			String reciver = txtReceiver.getText().trim();
			// ������������
			int transportType = 0;
			if(rbSend.isSelected()) transportType = Transport.SENDDING;
			if(rbReceive.isSelected()) transportType = Transport.RECIEVED;
			if(rbTrans.isSelected()) transportType = Transport.TRANSPORTING;
			// �����ݰ�װ����������
			trans = new Transport(id, time, adress, type, handler, reciver,
					transportType);
			// ������������������б�
			transList.add(trans);
			// ��ʾ�Ի���
			JOptionPane.showMessageDialog(null, "�����ɼ��ɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// ���ð�ť������
	private class ResetListener implements ActionListener {
		// ���ð�ť���¼�������
		public void actionPerformed(ActionEvent e) {
			txtName.setText("");
			txtLocation.setText("");
			txtIP.setText("");
			txtAdress.setText("");
			txtHandler.setText("");
			txtReceiver.setText("");
		}
	}
	
	// ƥ����־��Ϣ������
	private class MatchLogListener implements ActionListener {
		// ����ƥ����¼�������
		public void actionPerformed(ActionEvent e) {
			LogRecAnalyse logAn = new LogRecAnalyse(logList);
			// ��־���ݹ���
			logAn.doFilter();
			// ��־���ݷ���
			matchedLogs = logAn.matchData();
			// ��ʾ�Ի���
			JOptionPane.showMessageDialog(null, "��־���ݹ��ˡ�����ƥ��ɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// ��Ҫ����ƥ��������Ϣ������
	private class MatchTransListener implements ActionListener {
		// ����ƥ����¼�������
		public void actionPerformed(ActionEvent e) {
			TransportAnalyse tranAn = new TransportAnalyse(transList);
			tranAn.doFilter();
			matchedTrans = tranAn.matchData();
			JOptionPane.showMessageDialog(null, "�������ݹ��ˡ�����ƥ��ɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	//��Ҫ���䱣�����ݵ��¼�������
	private class SaveDataListener implements ActionListener {
		// ���ݱ�����¼�������
		public void actionPerformed(ActionEvent e) {
			try{
				LogRecService logService = new LogRecService();
				logService.saveMatchLog(matchedLogs);
				//logService.saveMatchLogToDB(matchedLogs);
				logList.clear();
				JOptionPane.showMessageDialog(null,"�ɹ���","�ɹ���ʾ",JOptionPane.PLAIN_MESSAGE);
			} catch (Exception ee){
				JOptionPane.showMessageDialog(null,"����ʧ�ܣ�","�ɹ���ʾ",JOptionPane.PLAIN_MESSAGE);
				ee.printStackTrace();
			}

			// ����ƥ���������Ϣ
			//������ɹ���������ʾ��ƥ�����־�����Ա��浽�ļ������ݿ��У�",
			//��û�б���ɹ����򵯳���Ӧ�ĸ澯��ʾ��
			try{
				TransportService transportService = new TransportService();
				transportService.saveMatchedTransport(matchedTrans);
				//transportService.saveMatchTransportToDB(matchedTrans);
				transList.clear();
				JOptionPane.showMessageDialog(null,"�ɹ���","�ɹ���ʾ",JOptionPane.PLAIN_MESSAGE);
			} catch (Exception ee){
				JOptionPane.showMessageDialog(null,"����ʧ�ܣ�","�ɹ���ʾ",JOptionPane.PLAIN_MESSAGE);
				ee.printStackTrace();
			}
			// ����ƥ���������Ϣ
			//������ɹ���������ʾ��ƥ������������Ա��浽�ļ������ݿ��У�",
			//��û�б���ɹ����򵯳���Ӧ�ĸ澯��ʾ��
		}
	}


	
	//������ʾ������
	private class ShowDataListener implements ActionListener {
		// ������ʾ���¼�������
		public void actionPerformed(ActionEvent e) {
			// �л������Ŀ�ƬΪ��ʾ���ݵ����
			card.show(p, "show");
			// �Ƴ���ʾ��������е����е�ѡ�
			showPane.removeAll();
			// ˢ����־��Ϣ��
			flushMatchedLogTable();
			// ˢ��������Ϣ��
			flushMatchedTransTable();
		}
	}

	// ˢ����־ѡ�����ʾ��־��Ϣ���
	private void flushMatchedLogTable() {
		// ����tableModel��ͨ����־Ϊ������־��������1����־��0������
		MatchedTableModel logModel = new MatchedTableModel(logRecService.readLogResult(), 1);
		// ʹ��tableModel����JTable
		JTable logTable = new JTable(logModel);
		// ͨ��JTable���󴴽�JScrollPane����ʾ����
		scrollPane = new JScrollPane(logTable);
		// �����־ѡ�
		showPane.addTab("��־", scrollPane);
	}

	//��Ҫ������ʾ������Ϣ���
	private void flushMatchedTransTable() {
		//��ʾ�������ݱ��
		MatchedTableModel tranModel = new MatchedTableModel(transportService.readTranResult(), 2);
		JTable tranTable = new JTable((tranModel));
		scrollPane = new JScrollPane(tranTable);
		showPane.addTab("����", scrollPane);
	}

	private class UpdateTableThread extends Thread {
		public void run() {
			while(true) {
				showPane.removeAll();
				flushMatchedLogTable();
				flushMatchedTransTable();
				try {
					Thread.sleep(1 * 30 * 100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class SendDataListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println(matchedLogs.size());
			if(matchedLogs != null && matchedLogs.size() > 0) {
				try {
					Socket logSocket = new Socket(serverIP, 6666);
					ObjectOutputStream logoutputStream = new ObjectOutputStream(logSocket.getOutputStream());
					logoutputStream.writeObject(matchedLogs);
					logoutputStream.flush();
					logoutputStream.close();
					matchedLogs.clear();
					logList.clear();
					JOptionPane.showMessageDialog(null, "ƥ�����־�����Ѿ����͵�������", "��ʾ", JOptionPane.WARNING_MESSAGE);
				}
				catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "û��ƥ�����־����", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}

			if(matchedTrans != null && matchedTrans.size() > 0) {
				try {
					Socket TranSocket = new Socket(serverIP, 6668);
					ObjectOutputStream logoutputStream = new ObjectOutputStream(TranSocket.getOutputStream());
					logoutputStream.writeObject(matchedTrans);
					logoutputStream.flush();
					logoutputStream.close();
					matchedTrans.clear();
					transList.clear();
					JOptionPane.showMessageDialog(null, "ƥ������������Ѿ����͵�������", "��ʾ", JOptionPane.WARNING_MESSAGE);
				}
				catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "û��ƥ�����������", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}

	}


	public static void main(String[] args) {
		new MainFrame_temp();
	}
}
