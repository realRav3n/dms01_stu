package com.qst.dms.service;

import java.sql.ResultSet;
import java.sql.Statement;

//import com.mysql.jdbc.Statement;
import com.qst.dms.db.DBUtil;
import com.qst.dms.entity.User;

public class LoginService {
	private static Statement statement;  //revised by yyc 20191012
	// �����û�����ѯ�û�

	public boolean loginCheck(String userName,String password,int userType){
		/**
		 * �ڵ�¼���������ж������¼
		 */
		DBUtil db = new DBUtil();
		try {
			// ��ȡ���ݿ�����
			db.getConnection();
			// ʹ��PreparedStatement����sql���
			String sql = "call `login_check`('?','?',?,@res);";
			// ���ò���
			Object[] param = new Object[]{userName,password,userType};
			// ִ�в�ѯ
			ResultSet rs = db.executeQuery(sql, param);
			if (rs.next()) {
				// ��������е����ݷ�װ��������
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �ر����ݿ������
			db.closeAll();
		}
		return false;
		// ���ز�������
	}
	// �����û���Ϣ
	public boolean saveUser(User user) {
		/**
		 * ����һ���û������������ݿ��д���
		 */
		// ����һ����������ֵ����ʼֵΪfalse
		boolean r = false;
		DBUtil db = new DBUtil();
		try {
			// ��ȡ���ݿ�����
			db.getConnection();
			String sqltest = "SELECT * FROM user WHERE user_name='"+user.getUsername()+"'";
			ResultSet rs = db.executeQuery(sqltest,null);
			if(rs.next())
				return r;
			//end
				 // ʹ��PreparedStatement����sql���
			String save_user_sql = "call `save_user`(`?`,`?`,`?`,`?`,?,@res);";
			// ���ò���
			Object[] param = new Object[] { user.getUsername(),
					user.getPassword(),user.getAddress(),user.getPhone(),user.getUsertype()
					};
			// �ж������Ƿ񱣴�ɹ�
			if (db.executeUpdate(save_user_sql, param) > 0) {
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
