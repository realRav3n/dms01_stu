package com.qst.dms.entity;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.table.AbstractTableModel;

public class MatchedTableModel extends AbstractTableModel {

	// ʹ��ResultSet������TableModel
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	// ��־λ��������־��������1����־��0������
	private int sign;

	public MatchedTableModel(ResultSet rs, int sign) {
		this.rs = rs;
		this.sign = sign;
		try {
			rsmd = rs.getMetaData();
		} catch (Exception e) {
			rsmd = null;
		}
	}

	// ��ȡ�������
	public int getRowCount() {
		try {
			rs.last();
			// System.out.println(count);
			return rs.getRow();
		} catch (Exception e) {
			return 0;
		}
	}

	// ��ȡ��������
	public int getColumnCount() {
		try {
			// System.out.println(rsmd.getColumnCount());
			return rsmd.getColumnCount();
		} catch (Exception e) {
			return 0;
		}
	}

	// ��ȡָ��λ�õ�ֵ
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			rs.absolute(rowIndex + 1);
			return rs.getObject(columnIndex + 1);
		} catch (Exception e) {
			return null;
		}
	}

	// ��ȡ��ͷ��Ϣ
	public String getColumnName(int column) {
		String[] logArray = { "�û���", "��½ʱ��", "�ǳ�ʱ��", "��½�ص�" };
		String[] tranArray = { "����ID", "�ɼ�ʱ��", "Ŀ�ĵ�", "״̬", "������", "�ջ���",
				"��������" };
		return sign == 1 ? logArray[column] : tranArray[column];
	}
}
