
package com.qst.dms.service;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.qst.dms.db.DBUtil;
import com.qst.dms.entity.*;

public class TransportService {
	// 物流数据采集
	public Transport inputTransport() {
		Transport trans = null;

		// 建立一个从键盘接收数据的扫描器
		Scanner scanner = new Scanner(System.in);
		try {
			// 提示用户输入ID标识
			System.out.println("请输入ID标识：");
			// 接收键盘输入的整数
			int id = scanner.nextInt();
			// 获取当前系统时间
			Date nowDate = new Date();
			// 提示用户输入地址
			System.out.println("请输入地址：");
			// 接收键盘输入的字符串信息
			String address = scanner.next();
			// 数据状态是“采集”
			int type = DataBase.GATHER;

			// 提示用户输入登录用户名
			System.out.println("请输入货物经手人：");
			// 接收键盘输入的字符串信息
			String handler = scanner.next();
			// 提示用户输入主机IP
			System.out.println("请输入 收货人:");
			// 接收键盘输入的字符串信息
			String reciver = scanner.next();
			// 提示用于输入物流状态
			System.out.println("请输入物流状态：1发货中，2送货中，3已签收");
			// 接收物流状态
			int transportType = scanner.nextInt();
			// 创建物流信息对象
			//trans = new Transport(id, nowDate, address, type, handler, reciver,
			//		transportType);
		} catch (Exception e) {
			System.out.println("采集的日志信息不合法");
		}
		// 返回物流对象
		return trans;
	}

	// 物流信息输出
	public void showTransport(Transport... transports) {
		for (Transport e : transports) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}

	// 匹配的物流信息输出，可变参数
	public void showMatchTransport(MatchedTransport... matchTrans) {
		for (MatchedTransport e : matchTrans) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}



	// 匹配的物流信息输出，参数是集合
	public void showMatchTransport(ArrayList<MatchedTransport> matchTrans) {
		for (MatchedTransport e : matchTrans) {
			if (e != null) {
				System.out.println(e.toString());
			}
		}
	}

	// 匹配物流信息保存，参数是集合
	public void saveMatchedTransport(ArrayList<MatchedTransport> matchTrans) {
		FileWriter fw = null;
		try {
			File f = new File("D:/大学课程/大二上/面向对象与多线程/第一次/dms01_stu/MatchedTransports.txt");
			fw = new FileWriter(f, true);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		for (MatchedTransport e : matchTrans) {
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

	// 读匹配物流信息保存，参数是集合
	public ArrayList<MatchedTransport> readMatchedTransport() {
		ArrayList<MatchedTransport> matchTrans = new ArrayList<>();
		// 创建一个ObjectInputStream对象输入流，并连接文件输入流，读MatchedTransports.txt文件中
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				"MatchedTransports.txt"))) {
			MatchedTransport matchTran;
			// 循环读文件中的对象
			while ((matchTran = (MatchedTransport) ois.readObject()) != null) {
				// 将对象添加到泛型集合中
				matchTrans.add(matchTran);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return matchTrans;
	}

	public boolean checkBuplicate(ArrayList<Integer> check, int id) {
		for(Integer x : check) {
			if(id == x) return false;
		}
		return true;
	}

	public void saveMatchTransportToDB(ArrayList<MatchedTransport> matchTrans) {
		DBUtil db = new DBUtil();
		boolean flag1 = false, flag2 = false, flag3 = false;
		try {
			ArrayList<Integer> check = new ArrayList<>();
			ArrayList<MatchedTransport> transports = readMatchedTransportFromDB();
			for (MatchedTransport e: transports) {
				if (e != null) {
					check.add(e.getReceive().getId());
					check.add(e.getSend().getId());
					check.add(e.getTrans().getId());
				}
			}
			db.getConnection();
			for(MatchedTransport matchedtransport : matchTrans) {
				Transport send = matchedtransport.getSend();
				Transport trans = matchedtransport.getTrans();
				Transport receive = matchedtransport.getReceive();
				String sql = "INSERT INTO gather_transport(time, address, type, handler, reciver, transportType) VALUES(?,?,?,?,?,?)";
				Object[] param = new Object[]{
						new Timestamp(send.getTime().getTime()),
						send.getAddress(), send.getType(), send.getHandler(),
						send.getReciver(), send.getTransportType()};
				if(checkBuplicate(check, send.getId())) {
					flag1 = true;
					//db.executeUpdate(sql, param);
					//check.add(send.getId());
					int sendKey = db.executeSQLAndReturnPrimaryKey(sql, param);
					send.setId(sendKey);
				}
				else {
					flag1 = false;
				}
				param = new Object[]{
						new Timestamp(trans.getTime().getTime()),
						trans.getAddress(), trans.getType(), trans.getHandler(),
						trans.getReciver(), trans.getTransportType()};
				if(checkBuplicate(check, trans.getId())) {
					//db.executeUpdate(sql, param);
					//check.add(trans.getId());
					int transKey = db.executeSQLAndReturnPrimaryKey(sql, param);
					send.setId(transKey);
					flag2 = true;
				}
				else {
					flag2 = false;
				}
				param = new Object[]{
						new Timestamp(receive.getTime().getTime()),
						receive.getAddress(), receive.getType(), receive.getHandler(),
						receive.getReciver(), receive.getTransportType()};
				if(checkBuplicate(check, receive.getId())) {
					//db.executeUpdate(sql, param);
					//check.add(receive.getId());
					int receiveKey = db.executeSQLAndReturnPrimaryKey(sql, param);
					send.setId(receiveKey);
					flag3 = true;
				}
				else {
					flag3 = false;
				}
				sql = "INSERT INTO matched_transport(sendid, transid, receiveid) VALUES(?, ?, ?)";
				param = new Object[] {send.getId(), trans.getId(), receive.getId()};
				if(flag1 == true && flag2 == true && flag3 == true) db.executeUpdate(sql, param);
			}
			db.closeAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  ArrayList<MatchedTransport> readMatchedTransportFromDB() {
		ArrayList<MatchedTransport> matchedTransports = new ArrayList<MatchedTransport>();
		DBUtil db = new DBUtil();
		try {
			db.getConnection();
			String sql = "SELECT s.id, s.time, s.address, s.type, s.handler, s.reciver, s.transporttype, "
					+ "t.id, t.time, t.address, t.type, t.handler, t.reciver, t.transporttype, "
					+ "r.id, r.time, r.address, r.type, r.handler, r.reciver, r.transporttype "
					+ "FROM matched_transport m, gather_transport s, gather_transport t, gather_transport r "
					+ "WHERE m.sendid = s.id AND m.transid = t.id AND m.receiveid = r.id";
			ResultSet rs = db.executeQuery(sql, null);
			while(rs.next()) {
				Transport send = new Transport(rs.getInt(1), rs.getDate(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7));
				Transport trans = new Transport(rs.getInt(8), rs.getDate(9), rs.getString(10),
						rs.getInt(11), rs.getString(12), rs.getString(13), rs.getInt(14));
				Transport receive = new Transport(rs.getInt(15), rs.getDate(16), rs.getString(17),
						rs.getInt(18), rs.getString(19), rs.getString(20), rs.getInt(21));
				MatchedTransport matchedtransport = new MatchedTransport(send, trans, receive);
				matchedTransports.add(matchedtransport);
			}
			db.closeAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return matchedTransports;
	}

	public ResultSet readTranResult() {
		DBUtil db = new DBUtil();
		ResultSet rs=null;
		try {
			// 获取数据库链接
			Connection conn=db.getConnection();
			// 查询匹配日志，设置ResultSet可以使用除了next()之外的方法操作结果集
			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT * from gather_transport";
			rs = st.executeQuery(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
}