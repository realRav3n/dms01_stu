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
        this.setTitle("登录界面");
        this.setBounds(y-250,x-150,width,height);
        p = new JPanel(new GridLayout(8, 1));
        p.setVisible(true);
        JLabel jlUser=new JLabel("用户名:");
        JLabel JlPassword=new JLabel("密    码:");
        JLabel jlType = new JLabel("用户类型:");
        JRadioButton rbLogistics, rbUser;
        JTextField userField=new JTextField(15);
        JPasswordField passwordField=new JPasswordField(15);
        rbLogistics = new JRadioButton("物流人员");
        rbUser = new JRadioButton("用户");

        JButton btnLogin = new JButton("登录");
        JButton btnClose = new JButton("重置");
        JButton btnRegister = new JButton("注册");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbLogistics);
        bg.add(rbUser);

        JPanel p0= new JPanel(new FlowLayout(FlowLayout.CENTER));//空行
        p.add(p0);
        //用户名
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p1.add(jlUser);
        p1.add(userField);
        p1.setVisible(true);
        p.add(p1);
        JPanel p01= new JPanel(new FlowLayout(FlowLayout.CENTER));//空行
        p.add(p01);
        //密码
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2.add(JlPassword);
        p2.add(passwordField);
        p2.setVisible(true);
        p.add(p2);
        //用户类型
        JPanel p03= new JPanel(new FlowLayout(FlowLayout.CENTER));//空行
        p.add(p03);
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p3.add(jlType);
        p3.add(rbLogistics);
        p3.add(rbUser);
        p3.setVisible(true);
        p.add(p3);

        JPanel p02= new JPanel(new FlowLayout(FlowLayout.CENTER));//空行
        p.add(p02);
        //一排按钮
        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p4.add(btnLogin);
        p4.add(btnClose);
        p4.add(btnRegister);
        p.add(p4);

        this.add(p);
        // 设置窗体大小和位置
        // 设置窗体不可改变大小
        this.setResizable(true);

        // 设置窗体初始可见
        this.setVisible(true);

        btnLogin.addActionListener(e -> {
            User user1 = new UserService().findUserByName(userField.getText());
            int tmpUserClass = Integer.parseInt(rbUser.isSelected() ? "0" : "1");
            //0 存用户, 1 存物流
            if(user1 == null || tmpUserClass != user1.getUserClass()) {
                JOptionPane.showMessageDialog(null, "无此用户,建议先注册！");
            }
            else {
                if(passwordField.equals(user1.getPassword())) {
                    JOptionPane.showMessageDialog(null, "登录成功！");
                    if(tmpUserClass == 0)
                        new MainFrame_User();
                    else
                        new MainFrame_Admin();
                }
                else {
                    JOptionPane.showMessageDialog(null, "密码错误，请重新输入！");
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
