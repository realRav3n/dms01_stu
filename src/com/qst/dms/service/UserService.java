package com.qst.dms.service;

import com.qst.dms.db.DBUtil;

import java.sql.ResultSet;

public class UserService {
    /**
     * 用户收货界面
     * @param t_id 货物单号
     * @param name 用户名称
     * @return bool
     */
    public boolean userReceive(int t_id,String name){
        DBUtil db = new DBUtil();
        try {
            // 获取数据库链接
            db.getConnection();
            // 使用PreparedStatement发送sql语句
            String sql = "call `receive`(?,'?',@res);";
            // 设置参数
            Object[] param = new Object[]{t_id,name};
            // 执行查询
            db.executeQuery(sql, param);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库的连接
            db.closeAll();
        }
        return false;
    }
    /**
     * user的显示所有信息
     *  name是用户名
     */
    public Object[][] userShowTransportDataByName(String name){
        DBUtil db = new DBUtil();
        Object[][] obj = new Object[0][];
        try {
            // 获取数据库链接
            db.getConnection();
            // 使用PreparedStatement发送sql语句
            String sql = "call `get_my_transport`('?');";
            // 设置参数
            Object[] param = new Object[]{name};
            // 执行查询
            ResultSet rs = db.executeQuery(sql, param);
            int count=0;
            while (rs.next()) {
                // 将结果集中的数据封装到对象中
                obj[count][0]=rs.getInt("transport_id");
                obj[count][1]=rs.getString("transport_name");
                obj[count][2]=rs.getString("now_handle");
                obj[count][3]=rs.getString("now_handle_phone");
                obj[count][4]=rs.getString("transport_type");
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库的连接
            db.closeAll();
        }
        return obj;
    }

    /**
     * 查询功能
     * 和Admin中的一样，我懒得写了
     */
    public Object[][] searchTransportDataByName(int id,String name,int tp){
        DBUtil db = new DBUtil();
        Object[][] obj = new Object[0][];
        try {
            // 获取数据库链接
            db.getConnection();
            // 使用PreparedStatement发送sql语句
            String sql = "call `get_details`(?,'?',?,@res);";
            // 设置参数
            Object[] param = new Object[]{id,name,tp};
            // 执行查询
            ResultSet rs = db.executeQuery(sql, param);
            int count=0;
            while (rs.next()) {
                // 将结果集中的数据封装到对象中
                obj[count][0]=rs.getInt("time");
                obj[count][1]=rs.getString("transport_type");
                obj[count][2]=rs.getString("next_handle");
                obj[count][3]=rs.getString("now_handle_phone");
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库的连接
            db.closeAll();
        }
        return obj;
    }
}
