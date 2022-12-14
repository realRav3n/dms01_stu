package com.qst.dms.ui;

import com.qst.dms.entity.User;
import com.qst.dms.service.UserService;

import java.awt.*;
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
import javax.swing.*;


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
    int width=500,height=300;
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

        //user.setBounds(100,25,50,50);
        //user.setVisible(true);
        //p.add(user);

        //password.setBounds(100,75,50,50);
        //password.setVisible(true);
        //p.add(password);

        //userfield.setBounds(150,40,220,25);
        //userfield.setVisible(true);
        //p.add(userfield);

        //passwordfield.setBounds(150,90,220,25);
        //passwordfield.setVisible(true);
        //p.add(passwordfield);

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(jlUser);
        p1.add(userField);
        p1.setVisible(true);
        p.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(JlPassword);
        p2.add(passwordField);
        p2.setVisible(true);
        p.add(p2);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(jlType);
        p3.add(rbLogistics);
        p3.add(rbUser);
        p3.setVisible(true);
        p.add(p3);

        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p4.add(btnLogin);
        p4.add(btnClose);
        p4.add(btnRegister);
        p.add(p4);

        //login.setVisible(true);
        //login.setBounds(80,150,75,40);
        //p.add(login);

        //close.setVisible(true);
        //close.setBounds(200,150,75,40);
        //p.add(close);

        //register.setVisible(true);
        //register.setBounds(320, 150, 75, 40);
        //p.add(register);

        this.add(p);
        // ���ô����С��λ��
        // ���ô��岻�ɸı��С
        this.setResizable(true);

        // ���ô����ʼ�ɼ�
        this.setVisible(true);

        btnLogin.addActionListener(e -> {
            User user1 = new UserService().findUserByName(userField.getText());
            if(user1 == null) {
                JOptionPane.showMessageDialog(null, "�޴��û�,������ע�ᣡ");
            }
            else {
                if(passwordField.getText().equals(user1.getPassword())) {
                    JOptionPane.showMessageDialog(null, "��¼�ɹ���");
                    new MainFrame_temp();
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
