package com.qst.dms.ui;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


public class JTableDemo extends AbstractTableModel 
		{        
		       private Vector TableData;//������ű�����ݵ����Ա�
		       String[][] TableDataArray;
		       private Vector TableColumn;//�����б���        
		       
		       public JTableDemo()
		       {
		           TableData = new Vector();
		          //��ʾ����
		          // String[] columnNames= {"����1","����2","����3"};
		           TableColumn= new Vector(); 
//		           TableColumn.add(0, "�û���");
//		           TableColumn.add(1, "�û�ID");
//		           TableColumn.add(2, "��¼״̬");
		           TableColumn.add("ID");
		           TableColumn.add("�û���");
		           TableColumn.add("��ַ");
		           
		           String []Line1 = {"001","test1","wuhan"};
		           //��1��
		           String []Line2 = {"002","test2","nanjing"};
		           //��2��
		           String []Line3 = {"003","test3","shanghai"};
		           
		           //�����ݹҵ����Ա��γɶ�ά�����ݱ��γ�ӳ��
		           TableData.add(Line1);
		           TableData.add(Line2);
		           TableData.add(Line3);    
	       }
		       @Override
		       public int getRowCount()
		       {
		           //���ر������������TableData�Ϲҵ�String�������
		           return TableData.size();
		    	   
		       }
		       
		       @Override
		       public int getColumnCount()
		       {
		           //���ر��������ֱ�ӷ���TableTitle.size()
		           return TableColumn.size();
		       }
		       
		       @Override
		       public Object getValueAt(int rowIndex, int columnIndex)
		       {
		            
		           //��ȡ��Ӧ����λ�õ�ֵ������������
		           //��ȡÿһ�ж�Ӧ��String[]����
		           String LineTemp[] = (String[])this.TableData.get(rowIndex);
		           //��ȡ���� Ӧ������
		           return LineTemp[columnIndex];
		       }
		  
		       @Override
		       public boolean isCellEditable(int rowIndex, int columnIndex)
		       {
		           //�������ʽ����ÿ����Ԫ��ı༭���Ե�
		           //�������AbstractTableModel�Ѿ�ʵ��
		           //������������Ϊǰ����Ϊ����༭״̬
		           if(rowIndex<3&&columnIndex<3){
		               return true;
		           }
		           else{
		               return false;
		           }
		       }
		       @Override
		       public void setValueAt(Object aValue, int rowIndex, int columnIndex)
		       {
		           //����Ԫ������ݷ����ı��ʱ����øú������赥Ԫ�������
		           //�����Ƿ���TableData��,�޸����ݾ����޸ĵ�
		           //TableData�е����ݣ��������ǽ����ڴ˴���TableData�Ķ�Ӧ�����޸ļ���              
		           ((String[])this.TableData.get(rowIndex))[columnIndex]=(String)aValue;
		           //Ȼ����ú���fireTableCellUpdated(int row,int col);����һ�±���Ӧλ�õ�������ʾ������ɶԱ��������޸ģ�
		           this.fireTableCellUpdated(rowIndex, columnIndex);
		       }
		       public String getColumnName(int name)
		       {
		    	   //������һ������������ABC��������
		           return (String)TableColumn.get(name);
		            
		       }
	
		//����һ��Dialog��ʾһ��

		    public static void main(String[] args)
		    {               
		    	JTableDemo myModel = new JTableDemo();
		        JTable table = new JTable(myModel);
		        JFrame jf = new JFrame();
		        JScrollPane jsp = new JScrollPane(table);//�����JTable����JScrollPane�У�����Ĭ�ϲ���ʾ�б���
		        jf.getContentPane().add(jsp);
		        jf.setBounds(0, 0, 500, 500);
		        jf.setVisible(true);
		        //�������ǿ��Լ���ֱ����table����Ӧ��Ԫ�����޸����ݺ󣬴˵�Ԫ���ֵ�᲻��仯
		        Scanner in = new Scanner(System.in);
		        int m=in.nextInt();
		        int n=in.nextInt();
		        String a=(String)myModel.getValueAt(m,n);
		        System.out.println(a);
		    }
		}

		




