package com.qst.dms.ui;

import com.qst.dms.entity.*;
import com.qst.dms.gather.LogRecAnalyse;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;
import com.qst.dms.util.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

//������
public class MainFrame_User extends JFrame {
	// �����������
	private String serverIP;
	private JMenuBar menuBar;
	private JMenu menuOperate, menuHelp, menuMatch;
	private JMenuItem miGather, miSave, miSend,
			miShow, miExit, miCheck, miAbout, miLogMatch, miTransMatch;
	private JPanel pGather, pShowPane , pAlterStatus,pSearchData;
	private JPanel p, pLog, pTran, pLogId, pName, pLocation, pIP, pLogStatus,pCargoId, pSearchCargoId,
			pLogButton, pTransId, pCargoName, pDestination, pReceiver, pTranStatus, pResource,
			pSearchButton,pSearchTable,pShowTable,
			pTranButton,pShow,pAlter,pSearch;
	private JLabel lblLogId, lblName, lblLocation, lblIP, lblLogStatus,
			lblTransId, lblCargoName, lblDestination, lblResource,lblReceiver, lblTranStatus,lblCargoId, lblSearchCargoId;
	private JTextField txtLogId, txtName, txtLocation, txtIP, txtTransId,txtTest,
			txtGatherCargoName, txtGatherDestination, txtGatherReceiver,txtGatherResource,txtAlterCargoId,txtAlterReceiver,
			txtSearchCargoId;
	private JRadioButton rbLogin, rbLogout, rbSend, rbReceive, rbTrans;
	private JRadioButton rbStatus1,rbStatus2,rbStatus3;
	private JButton btnLogConfirm, btnLogReset, btnTranConfirm, btnTranReset,
			btnSearchConfirm, btnSearchReset,
			btnGather, btnAlter, btnSearch, btnShow;
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
	final private String[] title = {"ʱ��", "״̬", "������", "�绰����"};
	private JTable tabDemo;
	private Object[][] info =new Object[300][4];

	// ���췽��
	public MainFrame_User() {
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
		//pGather = new JPanel();
		// ������ʾ��ѡ����
		pShowPane = new JPanel();
		//�޸�״̬
		//pAlterStatus =new JPanel();
		//��ѯ
		pSearchData =new JPanel();
		// ������ѡ������ӵ���Ƭ�����
		//p.add(tpGather, "gather");
		//p.add(pGather,"gather");
		p.add(pShowPane, "show");
		//p.add(pAlterStatus,"alter");
		p.add(pSearchData,"search");

		// ���������ӵ�������
		getContentPane().add(p, BorderLayout.CENTER);

		// ��ʼ���������ݲɼ�����
		//initTransGatherGUI();
		// ��ʼ���޸Ľ���
		//initAlter();
		// ��ʼ����ѯ����
		initSearch();
		// ��ʼ�����ֿ����ʾ
		initShow();

		// ���ô����ʼ�ɼ�
		this.setVisible(true);
		// ���ô����ʼ���
		this.setSize(475, 400);
		// ���ô��ڳ�ʼ������
		this.setLocationRelativeTo(null);
		// ����Ĭ�ϵĹرհ�ť����Ϊ�˳�����
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//new UpdateTableThread().start();
		serverIP = Config.getValue("serverIP");
	}

	// ��ʼ���˵��ķ���
	private void initMenu() {
		// ��ʼ���˵����
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		menuOperate = new JMenu("����");
		menuBar.add(menuOperate);

		miExit = new JMenuItem("�˳�ϵͳ");
		// ע�����
		miExit.addActionListener(e -> {
			// ��ʾȷ�϶Ի��򣬵�ѡ��YES_OPTIONʱ�˳�ϵͳ
			if (JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ�˳�ϵͳ��", "�˳�ϵͳ",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				// �˳�ϵͳ
				System.exit(0);
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

		// ��Ӵ���ͼ��Ĺ�������ť--->�����¿��
		//ImageIcon gatherIcon = new ImageIcon("images\\gatherData.png");
		//btnGather = new JButton("�����¿��", gatherIcon);
		// ע�����
		//btnGather.addActionListener(new gatherListener());
		//toolBar.add(btnGather);

		// ��Ӵ���ͼ��Ĺ�������ť--->���״̬�޸�
		//ImageIcon matchIcon = new ImageIcon("images\\matchData.png");
		//btnAlter = new JButton("���״̬�޸�", matchIcon);
		// ע�����
		//btnAlter.addActionListener(new alterDataListener());
		//toolBar.add(btnAlter);

		ImageIcon matchIconTrans = new ImageIcon("images\\matchData.png");
		btnSearch = new JButton("��ѯ���", matchIconTrans);
		btnSearch.addActionListener(new searchDataListener());
		toolBar.add(btnSearch);

		// ��Ӵ���ͼ��Ĺ�������ť--->���ֿ����ʾ
		ImageIcon saveIcon = new ImageIcon("images\\saveData.png");
		btnShow = new JButton("���ֿ����ʾ", saveIcon);
		// ע�����
		btnShow.addActionListener(new showDataListener());
		toolBar.add(btnShow);
		/*
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
		 */
	}

	// ��ʼ���������ݲɼ�����ķ���
	private void initTransGatherGUI() {
		pTran = new JPanel();
		pGather.add(pTran);
		pTran.setLayout(new BoxLayout(pTran, BoxLayout.Y_AXIS));

		pCargoName = new JPanel();
		pTran.add(pCargoName);

		pResource = new JPanel();
		pTran.add(pResource);

		pDestination = new JPanel();
		pTran.add(pDestination);

		pReceiver = new JPanel();
		pTran.add(pReceiver);

		lblCargoName = new JLabel("��Ʒ���ƣ�");
		pCargoName.add(lblCargoName);
		txtGatherCargoName = new JTextField();
		txtGatherCargoName.setPreferredSize(new Dimension(100, 20));
		pCargoName.add(txtGatherCargoName);

		lblResource=new JLabel("�����أ�");
		pResource.add(lblResource);
		txtGatherResource =new JTextField();
		txtGatherResource.setPreferredSize(new Dimension(100, 20));
		pResource.add(txtGatherResource);

		lblDestination = new JLabel("Ŀ�ĵأ�");
		pDestination.add(lblDestination);
		txtGatherDestination = new JTextField();
		txtGatherDestination.setPreferredSize(new Dimension(100, 20));
		pDestination.add(txtGatherDestination);


		lblReceiver = new JLabel("�ջ��ˣ�");
		pReceiver.add(lblReceiver);
		txtGatherReceiver = new JTextField();
		txtGatherReceiver.setPreferredSize(new Dimension(110, 20));
		pReceiver.add(txtGatherReceiver);

		pTranStatus = new JPanel();
		pTran.add(pTranStatus);

		pTranButton = new JPanel();
		pTran.add(pTranButton);

		btnTranConfirm = new JButton("ȷ��");
		btnTranConfirm.addActionListener(new GatherConfirmListener());
		pTranButton.add(btnTranConfirm);

		btnTranReset = new JButton("����");
		btnTranReset.addActionListener(new ResetGatherListener());
		pTranButton.add(btnTranReset);
	}
	private void initAlter() {
		pAlter = new JPanel();
		pAlterStatus.add(pAlter);
		pAlter.setLayout(new BoxLayout(pAlter, BoxLayout.Y_AXIS));

		pCargoId =new JPanel();
		pAlter.add(pCargoId);

		pReceiver =new JPanel();
		pAlter.add(pReceiver);

		JPanel pCargoStatus =new JPanel();
		pAlter.add(pCargoStatus);

		lblCargoId=new JLabel("��ݵ��ţ�");
		pCargoId.add(lblCargoId);
		txtAlterCargoId = new JTextField();
		txtAlterCargoId.setPreferredSize(new Dimension(100, 20));
		pCargoId.add(txtAlterCargoId);


		lblReceiver = new JLabel("���͸���");
		pReceiver.add(lblReceiver);
		txtAlterReceiver = new JTextField();
		txtAlterReceiver.setPreferredSize(new Dimension(110, 20));
		pReceiver.add(txtAlterReceiver);

		ButtonGroup bg = new ButtonGroup();
		rbStatus1 = new JRadioButton("δ����");
		rbStatus2 = new JRadioButton("������");
		rbStatus3 = new JRadioButton("�ѷ���");
		pCargoStatus.add(rbStatus1);
		pCargoStatus.add(rbStatus2);
		pCargoStatus.add(rbStatus3);
		bg.add(rbStatus1);
		bg.add(rbStatus2);
		bg.add(rbStatus3);


		pTranStatus = new JPanel();
		pAlter.add(pTranStatus);

		pTranButton = new JPanel();
		pAlter.add(pTranButton);

		btnTranConfirm = new JButton("ȷ��");
		btnTranConfirm.addActionListener(new AlterConfirmListener());
		pTranButton.add(btnTranConfirm);

		btnTranReset = new JButton("����");
		btnTranReset.addActionListener(new ResetAlterListener());
		pTranButton.add(btnTranReset);
	}
	private void initSearch() {
		pSearch = new JPanel();
		pSearchData.add(pSearch);
		pSearch.setLayout(new BoxLayout(pSearch, BoxLayout.Y_AXIS));

		pSearchCargoId = new JPanel();
		pSearch.add(pSearchCargoId);

		lblSearchCargoId = new JLabel("��ݵ���: ");
		pSearchCargoId.add(lblSearchCargoId);
		txtSearchCargoId = new JTextField();
		txtSearchCargoId.setPreferredSize(new Dimension(100, 20));
		pSearchCargoId.add(txtSearchCargoId);

		pSearchButton = new JPanel();
		pSearch.add(pSearchButton);

		btnSearchConfirm = new JButton("ȷ��");
		btnSearchConfirm.addActionListener(new SearchConfirmListener());
		pSearchButton.add(btnSearchConfirm);

		btnSearchReset = new JButton("����");
		btnSearchReset.addActionListener(new ResetSearchListener());
		pSearchButton.add(btnSearchReset);
		//demo
		info = new Object[][]{
				new Object[]{"������", 29, "Ů",1},
				new Object[]{"�ո�����", 56, "��",2},
				new Object[]{"���", 35, "��",3},
				new Object[]{"Ū��", 18, "Ů",4},
				new Object[]{"��ͷ", 2, "��",5}
		};
		pSearchTable =new JPanel();
		tabDemo =new JTable(info,title);
		pSearch.add(new JScrollPane(tabDemo));
	}
	private void initShow() {
		pShow = new JPanel();
		pShowPane.add(pShow);
		pShow.setLayout(new BoxLayout(pShow, BoxLayout.Y_AXIS));
		info = new Object[][]{
				new Object[]{"������", 29, "Ů",1},
				new Object[]{"�ո�����", 56, "��",2},
				new Object[]{"���", 35, "��",3},
				new Object[]{"Ū��", 18, "Ů",4},
				new Object[]{"��ͷ", 2, "��",5}
		};
		pShowTable =new JPanel();
		tabDemo =new JTable(info,title);
		pShowTable.add(new JScrollPane(tabDemo));
		pShow.add(pShowTable);
		/**
		 * todo: ͨ��forѭ���ӷ�����Ϣ�ж�ȡ��Ϣ�������ά����
		 */

	}

	private class gatherListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// �л������Ŀ�ƬΪ�ɼ����
			card.show(p, "gather");
		}
	}

	private class alterDataListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// �л������Ŀ�ƬΪ�ɼ����
			card.show(p, "alter");
		}
	}

	private class searchDataListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// �л������Ŀ�ƬΪ�ɼ����
			card.show(p, "search");
		}
	}

	private class showDataListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// �л������Ŀ�ƬΪ�ɼ����
			card.show(p, "show");
		}
	}


	// �������ݲɼ�������
	private class GatherConfirmListener extends ConfirmListener implements ActionListener {
		// ���ݲɼ����¼�������
		public void actionPerformed(ActionEvent e) {
			// ��ȡ����ID
			//int id = Integer.parseInt(txtTransId.getText().trim());//��Ϊ�Զ���ȡ
			// ������ǰʱ��
			Date time = new Date();
			// ��ȡĿ�ĵ�
			String cargoName = txtGatherCargoName.getText().trim();
			// ������������Ϊ���ɼ�
			int type = DataBase.GATHER;
			// ��ȡ��������Ϣ
			String resource = txtGatherResource.getText().trim();
			//��ȡĿ�ĵ���Ϣ
			String destination =txtGatherDestination.getText().trim();
			// ��ȡ��������Ϣ
			String receiver = txtGatherReceiver.getText().trim();
			// ������������Ϊ������
			int transportType = Transport.SENDDING;
			//System.out.println(id);
			System.out.println(time+cargoName+type+resource+destination+receiver+transportType);
			// �����ݰ�װ����������
			/*
			trans = new Transport(id, time, " ",type, cargoName, resource,destination,receiver,
					transportType);
			// ������������������б�
			transList.add(trans);

			 */
			/**
			 * todo����Ҫд�����ݿ���д�����ݵĴ���
			 */
			// ��ʾ�Ի���
			JOptionPane.showMessageDialog(null, "�����ɼ��ɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class AlterConfirmListener extends ConfirmListener implements ActionListener {
		// ���ݲɼ����¼�������
		public void actionPerformed(ActionEvent e) {
			// ��ȡ����ID
			//int id = Integer.parseInt(txtTransId.getText().trim());//��Ϊ�Զ���ȡ
			// ������ǰʱ��
			Date time = new Date();
			// ��ȡĿ�ĵ�
			String cargoId =txtAlterCargoId.getText().trim();

			// ������������Ϊ���ɼ�
			// ��ȡ��������Ϣ
			String receiver = txtAlterReceiver.getText().trim();
			// ������������Ϊ������
			int transportType = -1;
			if(rbStatus1.isSelected()) transportType=1;
			if(rbStatus2.isSelected()) transportType=2;
			if (rbStatus3.isSelected()) transportType=3;
			//System.out.println(id);
			System.out.println(time+cargoId+receiver+transportType);
			// �����ݰ�װ����������
			/*
			trans = new Transport(id, time, " ",type, cargoName, resource,destination,receiver,
					transportType);
			// ������������������б�
			transList.add(trans);

			 */
			// ��ʾ�Ի���
			/**
			 * todo�����ݿ���û���򱨴�
			 */
			JOptionPane.showMessageDialog(null, "���״̬�޸ĳɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class SearchConfirmListener extends ConfirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			/**
			 * todo�����ݿ���û���򱨴�
			 * todo: �������ݿ�����ؿ����Ϣ����ʾ��ѯ��Ϣ
			 */
			info = new Object[][]{
					new Object[]{"������", 29, "Ů",1},
					new Object[]{"�ո�����", 56, "��",2},
					new Object[]{"���", 35, "��",3},
					new Object[]{"Ū��", 18, "Ů",4},
			};
			pShowTable =new JPanel();
			tabDemo =new JTable(info,title);
			pShowTable.add(new JScrollPane(tabDemo));
			pShow.add(pShowTable);
			String cargoId =txtSearchCargoId.getText().trim();
			System.out.println(cargoId);
		}

	}

	private class ConfirmListener implements ActionListener {
		// ���ݲɼ����¼�������
		public void actionPerformed(ActionEvent e) {
			// ��ȡ����ID
			//int id = Integer.parseInt(txtTransId.getText().trim());//��Ϊ�Զ���ȡ
			// ������ǰʱ��
			System.out.println("ȷ��");
		}
	}
	// ���ð�ť������
	private class ResetGatherListener implements ActionListener {
		// ���ð�ť���¼�������
		public void actionPerformed(ActionEvent e) {
			txtGatherCargoName.setText("");
			txtGatherResource.setText("");
			txtGatherDestination.setText("");
			txtGatherReceiver.setText("");
		}
	}

	private class ResetAlterListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			txtAlterCargoId.setText("");
			txtAlterReceiver.setText("");
		}
	}
	private class ResetSearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			txtSearchCargoId.setText("");
		}
	}
	private class ResetShowListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

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
			System.out.println(2);
			card.show(p, "show");
			/*TransportAnalyse tranAn = new TransportAnalyse(transList);
			tranAn.doFilter();
			matchedTrans = tranAn.matchData();
			JOptionPane.showMessageDialog(null, "�������ݹ��ˡ�����ƥ��ɹ���", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);

			 */
		}
	}

	//��Ҫ���䱣�����ݵ��¼�������

	//������ʾ������

	// ˢ����־ѡ�����ʾ��־��Ϣ���
	private void flushMatchedLogTable() {
		// ����tableModel��ͨ����־Ϊ������־��������1����־��0������
		MatchedTableModel logModel = new MatchedTableModel(logRecService.readLogResult(), 1);
		// ʹ��tableModel����JTable
		JTable logTable = new JTable(logModel);
		// ͨ��JTable���󴴽�JScrollPane����ʾ����
		scrollPane = new JScrollPane(logTable);
		// �����־ѡ�
		pShowPane.add("��־", scrollPane);
	}

	//��Ҫ������ʾ������Ϣ���
	private void flushMatchedTransTable() {
		//��ʾ�������ݱ��
		MatchedTableModel tranModel = new MatchedTableModel(transportService.readTranResult(), 2);
		JTable tranTable = new JTable((tranModel));
		scrollPane = new JScrollPane(tranTable);
		pShowPane.add("����", scrollPane);
	}

	private class UpdateTableThread extends Thread {
		public void run() {
			while(true) {
				pShowPane.removeAll();
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
		new MainFrame_User();
	}
}
