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

//主窗口
public class MainFrame_User extends JFrame {
	// 声明界面组件
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
	// 声明日志对象
	private LogRec log;
	// 声明物流对象
	private Transport trans;
	// 声明日志列表
	private ArrayList<LogRec> logList;
	// 声明物流列表
	private ArrayList<Transport> transList;
	// 声明匹配日志列表
	private ArrayList<MatchedLogRec> matchedLogs;
	// 声明匹配物流列表
	private ArrayList<MatchedTransport> matchedTrans;
	// 声明日志业务对象
	private LogRecService logRecService;
	// 声明物流业务对象
	private TransportService transportService;
	private JButton btnNewButton;
	final private String[] title = {"时间", "状态", "接收人", "电话号码"};
	private JTable tabDemo;
	private Object[][] info =new Object[300][4];

	// 构造方法
	public MainFrame_User() {
		// 调用父类的构造方法
		super("Q-DMS系统客户端");

		// 设置窗体的icon
		ImageIcon icon = new ImageIcon("images\\dms.png");
		this.setIconImage(icon.getImage());

		// 列表、业务对象初始化
		logList = new ArrayList<LogRec>();
		transList = new ArrayList<Transport>();
		matchedLogs = new ArrayList<MatchedLogRec>();
		matchedTrans = new ArrayList<MatchedTransport>();
		logRecService = new LogRecService();
		transportService = new TransportService();

		// 初始化菜单
		initMenu();
		// 初始化工具栏
		initToolBar();

		// 设置主面板为CardLayout卡片布局
		card = new CardLayout();
		p = new JPanel();
		p.setLayout(card);
		// 数据采集的选项卡面板
		//pGather = new JPanel();
		// 数据显示的选项卡面板
		pShowPane = new JPanel();
		//修改状态
		//pAlterStatus =new JPanel();
		//查询
		pSearchData =new JPanel();
		// 将两个选项卡面板添加到卡片面板中
		//p.add(tpGather, "gather");
		//p.add(pGather,"gather");
		p.add(pShowPane, "show");
		//p.add(pAlterStatus,"alter");
		p.add(pSearchData,"search");

		// 将主面板添加到窗体中
		getContentPane().add(p, BorderLayout.CENTER);

		// 初始化物流数据采集界面
		//initTransGatherGUI();
		// 初始化修改界面
		//initAlter();
		// 初始化查询界面
		initSearch();
		// 初始化经手快递显示
		initShow();

		// 设置窗体初始可见
		this.setVisible(true);
		// 设置窗体初始最大化
		this.setSize(475, 400);
		// 设置窗口初始化居中
		this.setLocationRelativeTo(null);
		// 设置默认的关闭按钮操作为退出程序
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//new UpdateTableThread().start();
		serverIP = Config.getValue("serverIP");
	}

	// 初始化菜单的方法
	private void initMenu() {
		// 初始化菜单组件
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		menuOperate = new JMenu("操作");
		menuBar.add(menuOperate);

		miExit = new JMenuItem("退出系统");
		// 注册监听
		miExit.addActionListener(e -> {
			// 显示确认对话框，当选择YES_OPTION时退出系统
			if (JOptionPane.showConfirmDialog(null, "您确定要退出系统吗？", "退出系统",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				// 退出系统
				System.exit(0);
			}
		});
		menuOperate.add(miExit);

		menuHelp = new JMenu("帮助");
		menuBar.add(menuHelp);

		miCheck = new JMenuItem("查看帮助");
		// 注册监听
		miCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 显示消息对话框
				JOptionPane.showMessageDialog(null,
						"本系统实现数据的采集、过滤分析匹配、保存、发送及显示功能", "帮助",
						JOptionPane.QUESTION_MESSAGE);
			}
		});
		menuHelp.add(miCheck);

		miAbout = new JMenuItem("关于系统");
		// 注册监听
		miAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 显示消息对话框
				JOptionPane.showMessageDialog(null,
						"开发人：", "自行补充",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		menuHelp.add(miAbout);
	}

	// 初始化工具栏的方法
	private void initToolBar() {
		// 创建工具栏
		toolBar = new JToolBar();
		// 将工具栏添加到窗体北部（上面）
		getContentPane().add(toolBar, BorderLayout.NORTH);

		// 添加带有图标的工具栏按钮--->创建新快递
		//ImageIcon gatherIcon = new ImageIcon("images\\gatherData.png");
		//btnGather = new JButton("创建新快递", gatherIcon);
		// 注册监听
		//btnGather.addActionListener(new gatherListener());
		//toolBar.add(btnGather);

		// 添加带有图标的工具栏按钮--->快递状态修改
		//ImageIcon matchIcon = new ImageIcon("images\\matchData.png");
		//btnAlter = new JButton("快递状态修改", matchIcon);
		// 注册监听
		//btnAlter.addActionListener(new alterDataListener());
		//toolBar.add(btnAlter);

		ImageIcon matchIconTrans = new ImageIcon("images\\matchData.png");
		btnSearch = new JButton("查询快递", matchIconTrans);
		btnSearch.addActionListener(new searchDataListener());
		toolBar.add(btnSearch);

		// 添加带有图标的工具栏按钮--->经手快递显示
		ImageIcon saveIcon = new ImageIcon("images\\saveData.png");
		btnShow = new JButton("经手快递显示", saveIcon);
		// 注册监听
		btnShow.addActionListener(new showDataListener());
		toolBar.add(btnShow);
		/*
		// 添加带有图标的工具栏按钮--->发送数据
		ImageIcon sendIcon = new ImageIcon("images\\sendData.png");
		btnSend = new JButton("发送数据", sendIcon);
		btnSend.addActionListener(new SendDataListener());
//		btnSend.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
		toolBar.add(btnSend);

		// 添加带有图标的工具栏按钮--->显示数据
		ImageIcon showIcon = new ImageIcon("images\\showData.png");
		btnShow = new JButton("显示数据", showIcon);
		btnShow.addActionListener(new ShowDataListener());
		toolBar.add(btnShow);
		 */
	}

	// 初始化物流数据采集界面的方法
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

		lblCargoName = new JLabel("物品名称：");
		pCargoName.add(lblCargoName);
		txtGatherCargoName = new JTextField();
		txtGatherCargoName.setPreferredSize(new Dimension(100, 20));
		pCargoName.add(txtGatherCargoName);

		lblResource=new JLabel("发货地：");
		pResource.add(lblResource);
		txtGatherResource =new JTextField();
		txtGatherResource.setPreferredSize(new Dimension(100, 20));
		pResource.add(txtGatherResource);

		lblDestination = new JLabel("目的地：");
		pDestination.add(lblDestination);
		txtGatherDestination = new JTextField();
		txtGatherDestination.setPreferredSize(new Dimension(100, 20));
		pDestination.add(txtGatherDestination);


		lblReceiver = new JLabel("收货人：");
		pReceiver.add(lblReceiver);
		txtGatherReceiver = new JTextField();
		txtGatherReceiver.setPreferredSize(new Dimension(110, 20));
		pReceiver.add(txtGatherReceiver);

		pTranStatus = new JPanel();
		pTran.add(pTranStatus);

		pTranButton = new JPanel();
		pTran.add(pTranButton);

		btnTranConfirm = new JButton("确认");
		btnTranConfirm.addActionListener(new GatherConfirmListener());
		pTranButton.add(btnTranConfirm);

		btnTranReset = new JButton("重置");
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

		lblCargoId=new JLabel("快递单号：");
		pCargoId.add(lblCargoId);
		txtAlterCargoId = new JTextField();
		txtAlterCargoId.setPreferredSize(new Dimension(100, 20));
		pCargoId.add(txtAlterCargoId);


		lblReceiver = new JLabel("发送给：");
		pReceiver.add(lblReceiver);
		txtAlterReceiver = new JTextField();
		txtAlterReceiver.setPreferredSize(new Dimension(110, 20));
		pReceiver.add(txtAlterReceiver);

		ButtonGroup bg = new ButtonGroup();
		rbStatus1 = new JRadioButton("未发货");
		rbStatus2 = new JRadioButton("发货中");
		rbStatus3 = new JRadioButton("已发货");
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

		btnTranConfirm = new JButton("确认");
		btnTranConfirm.addActionListener(new AlterConfirmListener());
		pTranButton.add(btnTranConfirm);

		btnTranReset = new JButton("重置");
		btnTranReset.addActionListener(new ResetAlterListener());
		pTranButton.add(btnTranReset);
	}
	private void initSearch() {
		pSearch = new JPanel();
		pSearchData.add(pSearch);
		pSearch.setLayout(new BoxLayout(pSearch, BoxLayout.Y_AXIS));

		pSearchCargoId = new JPanel();
		pSearch.add(pSearchCargoId);

		lblSearchCargoId = new JLabel("快递单号: ");
		pSearchCargoId.add(lblSearchCargoId);
		txtSearchCargoId = new JTextField();
		txtSearchCargoId.setPreferredSize(new Dimension(100, 20));
		pSearchCargoId.add(txtSearchCargoId);

		pSearchButton = new JPanel();
		pSearch.add(pSearchButton);

		btnSearchConfirm = new JButton("确认");
		btnSearchConfirm.addActionListener(new SearchConfirmListener());
		pSearchButton.add(btnSearchConfirm);

		btnSearchReset = new JButton("重置");
		btnSearchReset.addActionListener(new ResetSearchListener());
		pSearchButton.add(btnSearchReset);
		//demo
		info = new Object[][]{
				new Object[]{"李清照", 29, "女",1},
				new Object[]{"苏格拉底", 56, "男",2},
				new Object[]{"李白", 35, "男",3},
				new Object[]{"弄玉", 18, "女",4},
				new Object[]{"虎头", 2, "男",5}
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
				new Object[]{"李清照", 29, "女",1},
				new Object[]{"苏格拉底", 56, "男",2},
				new Object[]{"李白", 35, "男",3},
				new Object[]{"弄玉", 18, "女",4},
				new Object[]{"虎头", 2, "男",5}
		};
		pShowTable =new JPanel();
		tabDemo =new JTable(info,title);
		pShowTable.add(new JScrollPane(tabDemo));
		pShow.add(pShowTable);
		/**
		 * todo: 通过for循环从返回信息中读取信息并填入二维数组
		 */

	}

	private class gatherListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 切换主面板的卡片为采集面板
			card.show(p, "gather");
		}
	}

	private class alterDataListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 切换主面板的卡片为采集面板
			card.show(p, "alter");
		}
	}

	private class searchDataListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 切换主面板的卡片为采集面板
			card.show(p, "search");
		}
	}

	private class showDataListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 切换主面板的卡片为采集面板
			card.show(p, "show");
		}
	}


	// 物流数据采集监听类
	private class GatherConfirmListener extends ConfirmListener implements ActionListener {
		// 数据采集的事件处理方法
		public void actionPerformed(ActionEvent e) {
			// 获取物流ID
			//int id = Integer.parseInt(txtTransId.getText().trim());//改为自动获取
			// 创建当前时间
			Date time = new Date();
			// 获取目的地
			String cargoName = txtGatherCargoName.getText().trim();
			// 设置数据类型为：采集
			int type = DataBase.GATHER;
			// 获取发货地信息
			String resource = txtGatherResource.getText().trim();
			//获取目的地信息
			String destination =txtGatherDestination.getText().trim();
			// 获取接收人信息
			String receiver = txtGatherReceiver.getText().trim();
			// 设置物流类型为发送中
			int transportType = Transport.SENDDING;
			//System.out.println(id);
			System.out.println(time+cargoName+type+resource+destination+receiver+transportType);
			// 将数据包装成物流对象
			/*
			trans = new Transport(id, time, " ",type, cargoName, resource,destination,receiver,
					transportType);
			// 将物流对象放入物流列表
			transList.add(trans);

			 */
			/**
			 * todo：需要写向数据库中写入数据的代码
			 */
			// 显示对话框
			JOptionPane.showMessageDialog(null, "物流采集成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class AlterConfirmListener extends ConfirmListener implements ActionListener {
		// 数据采集的事件处理方法
		public void actionPerformed(ActionEvent e) {
			// 获取物流ID
			//int id = Integer.parseInt(txtTransId.getText().trim());//改为自动获取
			// 创建当前时间
			Date time = new Date();
			// 获取目的地
			String cargoId =txtAlterCargoId.getText().trim();

			// 设置数据类型为：采集
			// 获取接收人信息
			String receiver = txtAlterReceiver.getText().trim();
			// 设置物流类型为发送中
			int transportType = -1;
			if(rbStatus1.isSelected()) transportType=1;
			if(rbStatus2.isSelected()) transportType=2;
			if (rbStatus3.isSelected()) transportType=3;
			//System.out.println(id);
			System.out.println(time+cargoId+receiver+transportType);
			// 将数据包装成物流对象
			/*
			trans = new Transport(id, time, " ",type, cargoName, resource,destination,receiver,
					transportType);
			// 将物流对象放入物流列表
			transList.add(trans);

			 */
			// 显示对话框
			/**
			 * todo：数据库中没有则报错
			 */
			JOptionPane.showMessageDialog(null, "快递状态修改成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class SearchConfirmListener extends ConfirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			/**
			 * todo：数据库中没有则报错
			 * todo: 返回数据库中相关快递信息至显示查询信息
			 */
			info = new Object[][]{
					new Object[]{"李清照", 29, "女",1},
					new Object[]{"苏格拉底", 56, "男",2},
					new Object[]{"李白", 35, "男",3},
					new Object[]{"弄玉", 18, "女",4},
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
		// 数据采集的事件处理方法
		public void actionPerformed(ActionEvent e) {
			// 获取物流ID
			//int id = Integer.parseInt(txtTransId.getText().trim());//改为自动获取
			// 创建当前时间
			System.out.println("确认");
		}
	}
	// 重置按钮监听类
	private class ResetGatherListener implements ActionListener {
		// 重置按钮的事件处理方法
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
	// 匹配日志信息监听类
	private class MatchLogListener implements ActionListener {
		// 数据匹配的事件处理方法
		public void actionPerformed(ActionEvent e) {
			LogRecAnalyse logAn = new LogRecAnalyse(logList);
			// 日志数据过滤
			logAn.doFilter();
			// 日志数据分析
			matchedLogs = logAn.matchData();
			// 显示对话框
			JOptionPane.showMessageDialog(null, "日志数据过滤、分析匹配成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// 需要补充匹配物流信息监听类
	private class MatchTransListener implements ActionListener {
		// 数据匹配的事件处理方法
		public void actionPerformed(ActionEvent e) {
			System.out.println(2);
			card.show(p, "show");
			/*TransportAnalyse tranAn = new TransportAnalyse(transList);
			tranAn.doFilter();
			matchedTrans = tranAn.matchData();
			JOptionPane.showMessageDialog(null, "物流数据过滤、分析匹配成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);

			 */
		}
	}

	//需要补充保存数据的事件处理方法

	//数据显示监听类

	// 刷新日志选项卡，显示日志信息表格
	private void flushMatchedLogTable() {
		// 创建tableModel，通过标志为区分日志和物流：1，日志；0，物流
		MatchedTableModel logModel = new MatchedTableModel(logRecService.readLogResult(), 1);
		// 使用tableModel创建JTable
		JTable logTable = new JTable(logModel);
		// 通过JTable对象创建JScrollPane，显示数据
		scrollPane = new JScrollPane(logTable);
		// 添加日志选项卡
		pShowPane.add("日志", scrollPane);
	}

	//需要补充显示物流信息表格
	private void flushMatchedTransTable() {
		//显示物流数据表格
		MatchedTableModel tranModel = new MatchedTableModel(transportService.readTranResult(), 2);
		JTable tranTable = new JTable((tranModel));
		scrollPane = new JScrollPane(tranTable);
		pShowPane.add("物流", scrollPane);
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
					JOptionPane.showMessageDialog(null, "匹配的日志数据已经发送到服务器", "提示", JOptionPane.WARNING_MESSAGE);
				}
				catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "没有匹配的日志数据", "提示", JOptionPane.WARNING_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "匹配的物流数据已经发送到服务器", "提示", JOptionPane.WARNING_MESSAGE);
				}
				catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "没有匹配的物流数据", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}

	}


	public static void main(String[] args) {
		new MainFrame_User();
	}
}
