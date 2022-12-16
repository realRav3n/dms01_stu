package com.qst.dms.ui;

import com.qst.dms.entity.User;
import com.qst.dms.service.UserService;

import java.awt.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class LoginFrame {
    public static void main(String[] args) {
        login r = new login();
        r.login();
    }
}
class  login extends JFrame {
    JPanel p=new JPanel();

    Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
    int x=size.height/2;
    int y=size.width/2;
    int width=400,height=300;
    public void login(){
        this.setTitle("��¼����");
        this.setBounds(y-250,x-150,width,height);
        p = new JPanel(new GridLayout(8, 1));
        p.setVisible(true);
        JLabel jlUser=new JLabel("�û���:");
        JLabel JlPassword=new JLabel("��    ��:");
        JLabel jlType = new JLabel("�û�����:");
        JRadioButton rbLogistics, rbUser;
        JTextField userField=new JTextField(15);
        JPasswordField passwordField=new JPasswordField(15);
        rbLogistics = new JRadioButton("������Ա");
        rbUser = new JRadioButton("�û�");

        JButton btnLogin = new JButton("��¼");
        JButton btnClose = new JButton("����");
        JButton btnRegister = new JButton("ע��");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbLogistics);
        bg.add(rbUser);

        JPanel p0= new JPanel(new FlowLayout(FlowLayout.CENTER));//����
        p.add(p0);
        //�û���
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p1.add(jlUser);
        p1.add(userField);
        p1.setVisible(true);
        p.add(p1);
        JPanel p01= new JPanel(new FlowLayout(FlowLayout.CENTER));//����
        p.add(p01);
        //����
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2.add(JlPassword);
        p2.add(passwordField);
        p2.setVisible(true);
        p.add(p2);
        //�û�����
        JPanel p03= new JPanel(new FlowLayout(FlowLayout.CENTER));//����
        p.add(p03);
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p3.add(jlType);
        p3.add(rbLogistics);
        p3.add(rbUser);
        p3.setVisible(true);
        p.add(p3);

        JPanel p02= new JPanel(new FlowLayout(FlowLayout.CENTER));//����
        p.add(p02);
        //һ�Ű�ť
        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p4.add(btnLogin);
        p4.add(btnClose);
        p4.add(btnRegister);
        p.add(p4);

        this.add(p);
        // ���ô����С��λ��
        // ���ô��岻�ɸı��С
        this.setResizable(true);

        // ���ô����ʼ�ɼ�
        this.setVisible(true);

        btnLogin.addActionListener(e -> {
            User user1 = new UserService().findUserByName(userField.getText());
            int tmpUserClass = Integer.parseInt(rbUser.isSelected() ? "0" : "1");
            //0 ���û�, 1 ������
            if(user1 == null || tmpUserClass != user1.getUserClass()) {
                JOptionPane.showMessageDialog(null, "�޴��û�,������ע�ᣡ");
            }
            else {
                if(passwordField.equals(user1.getPassword())) {
                    JOptionPane.showMessageDialog(null, "��¼�ɹ���");
                    if(tmpUserClass == 0)
                        new MainFrame_User();
                    else
                        new MainFrame_Admin();
                }
                else {
                    JOptionPane.showMessageDialog(null, "����������������룡");
                }
            }
        });
        btnRegister.addActionListener(
                e -> {
                    new RegistFrame();
                });
        btnClose.addActionListener(e -> {
            userField.setText("");
            passwordField.setText("");
        });
    }
}
