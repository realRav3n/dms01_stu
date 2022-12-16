package com.qst.dms.service;

import java.sql.ResultSet;
import java.sql.Statement;

//import com.mysql.jdbc.Statement;
import com.qst.dms.db.DBUtil;
import com.qst.dms.entity.User;

public class LoginService {
	private static Statement statement;  //revised by yyc 20191012
	// 根据用户名查询用户

	public boolean loginCheck(String userName,String password,int userType){
		/**
		 * 在登录界面用于判断允许登录
		 */
		DBUtil db = new DBUtil();
		try {
			// 获取数据库链接
			db.getConnection();
			// 使用PreparedStatement发送sql语句
			String sql = "call `login_check`('?','?',?,@res);";
			// 设置参数
			Object[] param = new Object[]{userName,password,userType};
			// 执行查询
			ResultSet rs = db.executeQuery(sql, param);
			if (rs.next()) {
				// 将结果集中的数据封装到对象中
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库的连接
			db.closeAll();
		}
		return false;
		// 返回布尔变量
	}
	// 保存用户信息
	public boolean saveUser(User user) {
		/**
		 * 传入一个用户，尝试在数据库中创建
		 */
		// 定义一个布尔返回值，初始值为false
		boolean r = false;
		DBUtil db = new DBUtil();
		try {
			// 获取数据库连接
			db.getConnection();
			String sqltest = "SELECT * FROM user WHERE user_name='"+user.getUsername()+"'";
			ResultSet rs = db.executeQuery(sqltest,null);
			if(rs.next())
				return r;
			//end
				 // 使用PreparedStatement发送sql语句
			String save_user_sql = "call `save_user`(`?`,`?`,`?`,`?`,?,@res);";
			// 设置参数
			Object[] param = new Object[] { user.getUsername(),
					user.getPassword(),user.getAddress(),user.getPhone(),user.getUsertype()
					};
			// 判断数据是否保存成功
			if (db.executeUpdate(save_user_sql, param) > 0) {
				// 保存成功，设置返回值为true
				r = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库的连接
			db.closeAll();
		}
		// 返回
		return r;
	}
}
