package com.qst.dms.service;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.net.InetAddress;
import java.io.FileNotFoundException;

import com.qst.dms.db.DBUtil;
import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;

//��־ҵ����
public class LogRecService {
	// ��־���ݲɼ�
	public LogRec inputLog() {
		LogRec log = null;
		// ����һ���Ӽ��̽������ݵ�ɨ����
		Scanner scanner = new Scanner(System.in);
		try {
			// ��ʾ�û�����ID��ʶ
			System.out.println("������ID��ʶ��");
			// ���ռ������������
			int id = scanner.nextInt();
			// ��ȡ��ǰϵͳʱ��
			Date nowDate = new Date();
			// ��ʾ�û������ַ
			System.out.println("�������ַ��");
			// ���ռ���������ַ�����Ϣ
			String address = scanner.next();
			// ����״̬�ǡ��ɼ���
			int type = DataBase.GATHER;
			// ��ʾ�û������¼�û���
			System.out.println("������ ��½�û�����");
			// ���ռ���������ַ�����Ϣ
			String user = scanner.next();
			// ��ʾ�û���������IP
			System.out.println("������ ����IP��");
			// ���ռ���������ַ�����Ϣ
			//String ip = scanner.next();
			System.out.println("�Զ���ȡip��ַ��");
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();
			System.out.println(ip);
			// ��ʾ�û������¼״̬���ǳ�״̬
			System.out.println("�������¼״̬:1�ǵ�¼��0�ǵǳ�");
			int logType = scanner.nextInt();
			// ������־����
			log = new LogRec(id, nowDate, address, type, user, ip, logType);
		} catch (Exception e) {
			System.out.println("�ɼ�����־��Ϣ���Ϸ�");
		}
		// ������־����
		return log;
	}

	// ��־��Ϣ���
	public void showLog(LogRec... logRecs) {
		for (LogRec e : logRecs) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}

	}
	public void showMatchLog(ArrayList<MatchedLogRec> matchLogs) {
		for (MatchedLogRec e : matchLogs) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}

	// ƥ����־��Ϣ������ɱ����
	public void showMatchLog(MatchedLogRec... matchLogs) {
		for (MatchedLogRec e : matchLogs) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}

	// ƥ����־��Ϣ���,�����Ǽ���

	public void saveMatchLog(ArrayList<MatchedLogRec> matchLogs) {
		FileWriter fw = null;
		try {
			File f = new File("D:/��ѧ�γ�/�����/�����������߳�/��һ��/dms01_stu/MatchLogs.txt");
			fw = new FileWriter(f, true);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		for (MatchedLogRec e : matchLogs) {
			if (e != null) {
				pw.println(e.toString());
			}
		}
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	public ArrayList<MatchedLogRec> readMatchLog() {
		ArrayList<MatchedLogRec> matchLogs = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("D:/��ѧ�γ�/�����/�����������߳�/��һ��/dms01_stu/MatchLogs.txt"))) {
			MatchedLogRec matchlog;
			while(true) {
				try{
					matchlog = (MatchedLogRec) ois.readObject();
					matchLogs.add(matchlog);
				}
				catch (Exception ex) {
					break;
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return matchLogs;
	}

	public boolean checkBuplicate(ArrayList<Integer> check, int id) {
		for(Integer x : check) {
			if(id == x) return false;
		}
		return true;
	}


	public void saveMatchLogToDB(ArrayList<MatchedLogRec> matchLogs) {
		boolean flag1, flag2;
		DBUtil db = new DBUtil();
		try {
			ArrayList<Integer> check = new ArrayList<>();
			db.getConnection();

			ArrayList<MatchedLogRec> logList = readMatchedLogFromDB();
//			for (MatchedLogRec e : logList) {
//				if (e != null) {
//					check.add(e.getLogin().getId());
//					check.add(e.getLogout().getId());
//				}
//			}
			for(MatchedLogRec matchedLogRec : matchLogs) {
				LogRec login = matchedLogRec.getLogin();
				LogRec logout = matchedLogRec.getLogout();
				String sql = "INSERT INTO gather_logrec(time,address,type,username,ip,logtype) VALUES(?,?,?,?,?,?)";
				Object[] param;
				if(checkBuplicate(check, login.getId()) == true) {
					param = new Object[]{
						new Timestamp(login.getTime().getTime()),
						login.getAddress(), login.getType(), login.getUser(),
						login.getIp(), login.getLogType()};
					int loginKey = db.executeSQLAndReturnPrimaryKey(sql, param);
					login.setId(loginKey);
					//db.executeUpdate(sql, param);
					//check.add(login.getId());
					flag1 = true;
				}
				else {
					flag1 = false;
				}
				if(checkBuplicate(check, logout.getId()) == true) {
					param = new Object[]{
						new Timestamp(logout.getTime().getTime()),
						logout.getAddress(), logout.getType(), logout.getUser(),
						logout.getIp(), logout.getLogType()};
					int logoutKey = db.executeSQLAndReturnPrimaryKey(sql, param);
					logout.setId(logoutKey);
					//db.executeUpdate(sql, param);
					//check.add(logout.getId());
					flag2 = true;
				}
				else {
					flag2 = false;
				}
				sql = "INSERT INTO matched_logrec(loginid, logoutid) VALUES(?, ?)";
				param = new Object[] {login.getId(), logout.getId()};
				if(flag1 == true && flag2 == true)  db.executeUpdate(sql, param);

			}
			db.closeAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  ArrayList<MatchedLogRec> readMatchedLogFromDB() {
		ArrayList<MatchedLogRec> matchedLogRecs = new ArrayList<MatchedLogRec>();
		DBUtil db = new DBUtil();
		try {
			db.getConnection();
			String sql = "SELECT i.id, i.time, i.address, i.type, i.username, i.ip, i.logtype,"
					+ "o.id, o.time, o.address, o.type, o.username, o.ip, o.logtype "
					+ "FROM matched_logrec m, gather_logrec i, gather_logrec o "
					+ "WHERE m.loginid = i.id AND m.logoutid = o.id";
			ResultSet rs = db.executeQuery(sql, null);
			while(rs.next()) {
				LogRec login = new LogRec(rs.getInt(1), rs.getDate(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7));
				LogRec logout = new LogRec(rs.getInt(8), rs.getDate(9), rs.getString(10),
						rs.getInt(11), rs.getString(12), rs.getString(13), rs.getInt(14));
				MatchedLogRec matchedLog = new MatchedLogRec(login, logout);
				matchedLogRecs.add(matchedLog);
			}
			db.closeAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return matchedLogRecs;
	}

	public ResultSet readLogResult() {
		DBUtil db = new DBUtil();
		ResultSet rs=null;
		try {
			// ��ȡ���ݿ�����
			Connection conn=db.getConnection();
			// ��ѯƥ����־������ResultSet����ʹ�ó���next()֮��ķ������������
			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			//String sql = "SELECT * from gather_logrec";
			String sql = "SELECT i.username, i.time, o.time, o.address "
					+ "FROM matched_logrec m, gather_logrec i, gather_logrec o "
					+ "WHERE m.loginid = i.id AND m.logoutid = o.id";
			rs = st.executeQuery(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	private class readLogResultFromSocket extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private InputStream is = null;
		private FileOutputStream fos = null;
		private OutputStream os = null;

		public ResultSet readLogResultFromSocket() {
			ResultSet rs=null;
			try {

			}catch (Exception e) {
				e.printStackTrace();
			}
			return rs;
		}
	}



}
