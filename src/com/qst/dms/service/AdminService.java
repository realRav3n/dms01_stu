package com.qst.dms.service;

import com.qst.dms.db.DBUtil;

import java.sql.ResultSet;

public class AdminService {
    /**
     * 管理员账户中的功能4
     * @param name 管理员的姓名
     * @return 一个二维数组，其中有表单信息
     */
    public Object[][] adminShowTransportDataByName(String name){
        DBUtil db = new DBUtil();
        Object[][] obj = new Object[0][];
        try {
            // 获取数据库链接
            db.getConnection();
            // 使用PreparedStatement发送sql语句
            String sql = "call `get_handle_transport`('?');";
            // 设置参数
            Object[] param = new Object[]{name};
            // 执行查询
            ResultSet rs = db.executeQuery(sql, param);
            int count=0;
            while (rs.next()) {
                // 将结果集中的数据封装到对象中
                obj[count][0]=rs.getInt("transport_id");
                obj[count][1]=rs.getString("destination");
                obj[count][2]=rs.getString("recipient");
                obj[count][3]=rs.getString("recipient_phone");
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
     * 管理员修改时调用，修改后，在日志中新增一个（功能2）
     * @param id 货物单号
     * @param tp
     * @param hd 经手人
     * @param nh 下一个经手人
     * @param nhp 下一个经手人电话
     * @return bool
     */
    public boolean saveLog(int id,int tp,String hd,String nh,String nhp){
        DBUtil db = new DBUtil();
        /**
         * todo:创建时间
         */
        try {
            String tm = null;
            // 获取数据库链接
            db.getConnection();
            // 使用PreparedStatement发送sql语句
            String sql = "call `save_log`(?,'?',?,'?','?','?',@res);";
            // 设置参数
            Object[] param = new Object[]{id,tm,tp,hd,nh,nhp};
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
     * 该方法用于admin添加物流信息（功能1）
     * @param name transportName物品名
     * @param dp 发货人
     * @param dt 目的地
     * @param r 收货人
     * @param rp 收货人电话
     * @param nh 持货人
     * @param nhp 持货人电话
     * @param yp 货物状态
     * @return 返回值为bool
     */
    public boolean saveTransport(String name,String dp,String dt,String r,String rp,String nh,String nhp,int yp){
        DBUtil db = new DBUtil();
        try {
            // 获取数据库链接
            db.getConnection();
            // 使用PreparedStatement发送sql语句
            String sql = "call `save_transport`('?','?','?','?','?','?',?);";
            // 设置参数
            Object[] param = new Object[]{name,dp,dt,r,rp,nh,nhp,yp};
            // 执行查询
            db.executeQuery(sql, param);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库的连接
            db.closeAll();
        }
        return true;
    }

    /**
     * 管理员账户功能3，查询
     * @param id 货物号
     * @param name 用户名
     * @param tp 谁知道啊，看不几把懂
     * @return
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
