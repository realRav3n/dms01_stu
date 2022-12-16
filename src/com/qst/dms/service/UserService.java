package com.qst.dms.service;

import com.qst.dms.db.DBUtil;

import java.sql.ResultSet;

public class UserService {
    /**
     * �û��ջ�����
     * @param t_id ���ﵥ��
     * @param name �û�����
     * @return bool
     */
    public boolean userReceive(int t_id,String name){
        DBUtil db = new DBUtil();
        try {
            // ��ȡ���ݿ�����
            db.getConnection();
            // ʹ��PreparedStatement����sql���
            String sql = "call `receive`(?,'?',@res);";
            // ���ò���
            Object[] param = new Object[]{t_id,name};
            // ִ�в�ѯ
            db.executeQuery(sql, param);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // �ر����ݿ������
            db.closeAll();
        }
        return false;
    }
    /**
     * user����ʾ������Ϣ
     *  name���û���
     */
    public Object[][] userShowTransportDataByName(String name){
        DBUtil db = new DBUtil();
        Object[][] obj = new Object[0][];
        try {
            // ��ȡ���ݿ�����
            db.getConnection();
            // ʹ��PreparedStatement����sql���
            String sql = "call `get_my_transport`('?');";
            // ���ò���
            Object[] param = new Object[]{name};
            // ִ�в�ѯ
            ResultSet rs = db.executeQuery(sql, param);
            int count=0;
            while (rs.next()) {
                // ��������е����ݷ�װ��������
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
            // �ر����ݿ������
            db.closeAll();
        }
        return obj;
    }

    /**
     * ��ѯ����
     * ��Admin�е�һ����������д��
     */
    public Object[][] searchTransportDataByName(int id,String name,int tp){
        DBUtil db = new DBUtil();
        Object[][] obj = new Object[0][];
        try {
            // ��ȡ���ݿ�����
            db.getConnection();
            // ʹ��PreparedStatement����sql���
            String sql = "call `get_details`(?,'?',?,@res);";
            // ���ò���
            Object[] param = new Object[]{id,name,tp};
            // ִ�в�ѯ
            ResultSet rs = db.executeQuery(sql, param);
            int count=0;
            while (rs.next()) {
                // ��������е����ݷ�װ��������
                obj[count][0]=rs.getInt("time");
                obj[count][1]=rs.getString("transport_type");
                obj[count][2]=rs.getString("next_handle");
                obj[count][3]=rs.getString("now_handle_phone");
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // �ر����ݿ������
            db.closeAll();
        }
        return obj;
    }
}
