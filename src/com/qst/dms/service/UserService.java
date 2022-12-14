package com.qst.dms.service;

import java.sql.ResultSet;
import java.sql.Statement;

//import com.mysql.jdbc.Statement;
import com.qst.dms.db.DBUtil;
import com.qst.dms.entity.User;

public class UserService {
	private static Statement statement;  //revised by yyc 20191012
	// �����û�����ѯ�û�
	public User findUserByName(String userName) {
		DBUtil db = new DBUtil();
		User user = null;
		try {
			// ��ȡ���ݿ�����
			db.getConnection();
			// ʹ��PreparedStatement����sql���
			String sql = "SELECT * FROM userdetails WHERE username=?";
			// ���ò���
			Object[] param = new Object[] { userName };
			// ִ�в�ѯ
			ResultSet rs = db.executeQuery(sql, param);
			if (rs.next()) {
				// ��������е����ݷ�װ��������
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getString(5),rs.getString(6)
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �ر����ݿ������
			db.closeAll();
		}
		// �����û�����
		return user;
	}

	// �����û���Ϣ
	public boolean saveUser(User user) {
		// ����һ����������ֵ����ʼֵΪfalse
		boolean r = false;
		DBUtil db = new DBUtil();
		try {
			// ��ȡ���ݿ�����
			db.getConnection();
			//start revised by yyc 20191012
//			String sqltest = "SELECT * FROM userdetails WHERE username=user";
//			ResultSet rs = statement.executeQuery(sqltest);
			
			//start revised by yyc 20191126
			String sqltest = "SELECT * FROM userdetails WHERE username='"+user.getUsername()+"'";
			ResultSet rs = db.executeQuery(sqltest,null);
			if(rs.next())
				return r;
			//end
				 // ʹ��PreparedStatement����sql���
			String sql = "INSERT INTO userdetails(username,password,sex,hobby,address,degree) VALUES (?,?,?,?,?,?)";
			// ���ò���
			Object[] param = new Object[] { user.getUsername(),
					user.getPassword(), user.getUserClass(),
					user.getAddress()};
			// �ж������Ƿ񱣴�ɹ�
			if (db.executeUpdate(sql, param) > 0) {
				// ����ɹ������÷���ֵΪtrue
				r = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �ر����ݿ������
			db.closeAll();
		}
		// ����
		return r;
	}
}
