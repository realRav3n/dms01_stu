package com.qst.dms.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.qst.dms.entity.User;
import com.qst.dms.service.LoginService;

//ע�ᴰ��
 class RegistFrame extends JFrame {
    // �����
    private JPanel p;
    // ��ǩ
    private JLabel lblName, lblPwd, lblRePwd, lblType, lblPhone, lblAdress;
    // �û������ı���
    private JTextField txtName,txtPhone;
    // �����ȷ�����룬�����
    private JPasswordField txtPwd, txtRePwd;
    // �Ա𣬵�ѡ��ť
    private JRadioButton rbLogistics, rbUser;
    // ���ã���ѡ��
    private JCheckBox ckbRead, ckbNet, ckbSwim, ckbTour;
    // ��ַ���ı���
    private JTextArea txtAdress;
    // ѧ������Ͽ�
    private JComboBox<String> cmbDegree;
    // ȷ�Ϻ�ȡ������ť
    private JButton btnOk, btnCancle;
    // ע����û�
    private static User user;

    // �û�ҵ����
    private LoginService userService;

    // ���췽��
    public RegistFrame() {
        super("�û�ע��");

        // ʵ�����û�ҵ�������
        userService = new LoginService();

        // ���ô����icon
        ImageIcon icon = new ImageIcon("images\\dms.png");
        this.setIconImage(icon.getImage());

        // ������岼�֣����񲼾�
        p = new JPanel(new GridLayout(8, 1));
        // ʵ�������
        lblName = new JLabel("��  ��  ����");
        lblPwd = new JLabel("��        �룺");
        lblRePwd = new JLabel("ȷ�����룺");
        lblType = new JLabel("�� �� �� �ͣ�");
        lblPhone = new JLabel("��       ����");
        lblAdress = new JLabel("��       ַ��");
        //lblDegree = new JLabel("ѧ        ����");
        txtName = new JTextField(16);
        txtPwd = new JPasswordField(16);
        txtRePwd = new JPasswordField(16);
        txtPhone = new JTextField(16);
        rbLogistics = new JRadioButton("������Ա");
        rbUser = new JRadioButton("�û�");

        // �Ա�ĵ�ѡ�߼�
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbLogistics);
        bg.add(rbUser);

        //ckbRead = new JCheckBox("�Ķ�");
        //ckbNet = new JCheckBox("����");
        //ckbSwim = new JCheckBox("��Ӿ");
        //ckbTour = new JCheckBox("����");
        txtAdress = new JTextArea(3, 20);

        // ��Ͽ���ʾ��ѧ������
        //String str[] = { "Сѧ", "����", "����", "����", "˶ʿ", "��ʿ" };
        //cmbDegree = new JComboBox<String>(str);
        // ������Ͽ�ɱ༭
        //cmbDegree.setEditable(true);
        btnOk = new JButton("ȷ��");
        // ע�������������ȷ����ť
        //btnOk.addActionListener(new RegisterListener());
        btnCancle = new JButton("����");
        // ע����������������ð�ť
        btnCancle.addActionListener(new ResetListener());
        // ��������������壬Ȼ��С�����������
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(lblName);
        p1.add(txtName);
        p.add(p1);
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(lblPwd);
        p2.add(txtPwd);
        p.add(p2);
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(lblRePwd);
        p3.add(txtRePwd);
        p.add(p3);
        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4.add(lblType);
        p4.add(rbLogistics);
        p4.add(rbUser);
        p.add(p4);
        JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(lblPhone);
        p5.add(txtPhone);
        //p5.add(ckbNet);
        //p5.add(ckbSwim);
        //p5.add(ckbTour);
        p.add(p5);
        JPanel p6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p6.add(lblAdress);
        p6.add(txtAdress);
        p.add(p6);
        //JPanel p7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //p7.add(lblDegree);
        //p7.add(cmbDegree);
        //p.add(p7);
        JPanel p8 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p8.add(btnOk);
        p8.add(btnCancle);
        p.add(p8);
        // �������봰����
        this.add(p);
        // ���ô����С��λ��
        this.setSize(310, 350);
        this.setLocation(300, 300);
        // ���ô��岻�ɸı��С
        this.setResizable(false);

        // ���ô����ʼ�ɼ�
        this.setVisible(true);
    }
/*
    // �����࣬������ȷ�ϰ�ť��ҵ���߼�
    private class RegisterListener implements ActionListener {
        // ��дactionPerFormed()�������¼�������
        public void actionPerformed(ActionEvent e) {
            // ��ȡ�û����������
            String userName = txtName.getText().trim();
            String password = new String(txtPwd.getPassword());
            String rePassword = new String(txtRePwd.getPassword());
            String phone = txtPhone.getText().trim();
            // ���Ա��С���Ů����Ӧת��Ϊ��1����0��
            int userClass = Integer.parseInt(rbUser.isSelected() ? "0" : "1");
            //String hobby = (ckbRead.isSelected() ? "�Ķ�" : "")
                    //+ (ckbNet.isSelected() ? "����" : "")
                    //+ (ckbSwim.isSelected() ? "��Ӿ" : "")
                    //+ (ckbTour.isSelected() ? "����" : "");
            String address = txtAdress.getText().trim();
            //String degree = cmbDegree.getSelectedItem().toString().trim();
            // �ж��������������Ƿ�һ��
            if (password.equals(rePassword)) {
                // �����ݷ�װ��������
                user = new User(userName, password, userClass, phone,address);
                // ��������
                if (userService.saveUser(user)) {
                    // �����ʾ��Ϣ
                    //System.out.println("ע��ɹ���");
                    JOptionPane.showMessageDialog(null,"ע��ɹ���","�ɹ���ʾ",JOptionPane.PLAIN_MESSAGE);
                } else {
                    // �����ʾ��Ϣ
                    //System.out.println("ע��ʧ�ܣ�");
                    JOptionPane.showMessageDialog(null,"ע��ʧ�ܣ�","������ʾ",JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // �����ʾ��Ϣ
                //System.out.println("������������벻һ�£�");
                JOptionPane.showMessageDialog(null,"������������벻һ�£�","������ʾ",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
*/
    // �����࣬���������ð�ť
    public class ResetListener implements ActionListener {
        // ��дactionPerFormed()������������������¼�������
        public void actionPerformed(ActionEvent e) {
            // ������������롢ȷ����������
            txtName.setText("");
            txtPwd.setText("");
            txtRePwd.setText("");
            txtPhone.setText("");
            // ���õ�ѡ��ťΪδѡ��
            rbLogistics.setSelected(false);
            rbUser.setSelected(false);
            // �������еĸ�ѡ��ťΪδѡ��
            //ckbRead.setSelected(false);
            //ckbNet.setSelected(false);
            //ckbSwim.setSelected(false);
            //ckbTour.setSelected(false);
            // ��յ�ַ��
            txtAdress.setText("");
            // ������Ͽ�Ϊδѡ��״̬
            //cmbDegree.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        new RegistFrame();
    }
}
