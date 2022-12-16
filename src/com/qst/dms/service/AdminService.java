package com.qst.dms.service;

import com.qst.dms.db.DBUtil;

import java.sql.ResultSet;

public class AdminService {
    /**
     * ����Ա�˻��еĹ���4
     * @param name ����Ա������
     * @return һ����ά���飬�����б���Ϣ
     */
    public Object[][] adminShowTransportDataByName(String name){
        DBUtil db = new DBUtil();
        Object[][] obj = new Object[0][];
        try {
            // ��ȡ���ݿ�����
            db.getConnection();
            // ʹ��PreparedStatement����sql���
            String sql = "call `get_handle_transport`('?');";
            // ���ò���
            Object[] param = new Object[]{name};
            // ִ�в�ѯ
            ResultSet rs = db.executeQuery(sql, param);
            int count=0;
            while (rs.next()) {
                // ��������е����ݷ�װ��������
                obj[count][0]=rs.getInt("transport_id");
                obj[count][1]=rs.getString("destination");
                obj[count][2]=rs.getString("recipient");
                obj[count][3]=rs.getString("recipient_phone");
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
     * ����Ա�޸�ʱ���ã��޸ĺ�����־������һ��������2��
     * @param id ���ﵥ��
     * @param tp
     * @param hd ������
     * @param nh ��һ��������
     * @param nhp ��һ�������˵绰
     * @return bool
     */
    public boolean saveLog(int id,int tp,String hd,String nh,String nhp){
        DBUtil db = new DBUtil();
        /**
         * todo:����ʱ��
         */
        try {
            String tm = null;
            // ��ȡ���ݿ�����
            db.getConnection();
            // ʹ��PreparedStatement����sql���
            String sql = "call `save_log`(?,'?',?,'?','?','?',@res);";
            // ���ò���
            Object[] param = new Object[]{id,tm,tp,hd,nh,nhp};
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
     * �÷�������admin���������Ϣ������1��
     * @param name transportName��Ʒ��
     * @param dp ������
     * @param dt Ŀ�ĵ�
     * @param r �ջ���
     * @param rp �ջ��˵绰
     * @param nh �ֻ���
     * @param nhp �ֻ��˵绰
     * @param yp ����״̬
     * @return ����ֵΪbool
     */
    public boolean saveTransport(String name,String dp,String dt,String r,String rp,String nh,String nhp,int yp){
        DBUtil db = new DBUtil();
        try {
            // ��ȡ���ݿ�����
            db.getConnection();
            // ʹ��PreparedStatement����sql���
            String sql = "call `save_transport`('?','?','?','?','?','?',?);";
            // ���ò���
            Object[] param = new Object[]{name,dp,dt,r,rp,nh,nhp,yp};
            // ִ�в�ѯ
            db.executeQuery(sql, param);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // �ر����ݿ������
            db.closeAll();
        }
        return true;
    }

    /**
     * ����Ա�˻�����3����ѯ
     * @param id �����
     * @param name �û���
     * @param tp ˭֪�������������Ѷ�
     * @return
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
