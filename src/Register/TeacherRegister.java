package Register;

import About.About;
import Interim.Interim;
import JDBC.*;
import Login.Login;
import TeacherOperate.OpenCourse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class TeacherRegister extends JFrame {

    String textName;
    String textId;
    String textPassWord;
    String textRepassWord;

    JTextField jTextFieldName = new JTextField(30);
    JTextField jTextFieldId = new JTextField(30);
    JTextField jTextFieldPassword = new JTextField(30);
    JTextField jTextFieldRepassword = new JTextField(30);
    public TeacherRegister(){
        InitRegister();
        InitImage();
//        ResetImg(2);
        setVisible(true);
    }

    private void InitImage() {
        //添加菜单
        addJMenu();
        setJText();
        addString();

        setEneter();
    }

    private void addJMenu(){//***********************添加菜单***************************************
        JMenuBar jMenuBar = new JMenuBar();
        JMenu  me= new JMenu("菜单");
        JMenu about = new JMenu("关于");
        JMenuItem reLogin = new JMenuItem("重新登录");
        me.add(reLogin);
        jMenuBar.add(me);
        jMenuBar.add(about);
        setJMenuBar(jMenuBar);
        reLogin.addActionListener(new ActionListener() {//点击返回登录按钮，进入登录窗口
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new About();
                dispose();
            }
        });

    }
    private void addString(){
        JLabel jLabelName = new JLabel("姓名");
        JLabel id = new JLabel("ID");
        JLabel password = new JLabel("密码");
        JLabel rePassword = new JLabel("再次输入密码");
        jLabelName.setBounds(20,105,60,15);
        id.setBounds(20,155,60,20);
        password.setBounds(20,205,60,15);
        rePassword.setBounds(5,255,85,15);

        jLabelName.setFont(new Font("Serif",Font.BOLD,15));
        id.setFont(new Font("Serif",Font.BOLD,15));
        password.setFont(new Font("Serif",Font.BOLD,15));
        rePassword.setFont(new Font("Serif",Font.BOLD,10));

        getContentPane().add(jLabelName);
        getContentPane().add(id);
        getContentPane().add(password);
        getContentPane().add(rePassword);
    }
    private void setJText() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(80,100,240,180);


        //设置文本框位置
        jTextFieldName.setBounds(0,0,240,30);
        jTextFieldId.setBounds(0,50,240,30);
        jTextFieldPassword.setBounds(0,100,240,30);
        jTextFieldRepassword.setBounds(0,150,240,30);


        jPanel.add(jTextFieldName);
        jPanel.add(jTextFieldId);
        jPanel.add(jTextFieldPassword);
        jPanel.add(jTextFieldRepassword);
        this.getContentPane().add(jPanel);
    }

    private void setEneter(){
        JButton jButton = new JButton("注册");
        jButton.setBounds(150,300,100,28);
        this.getContentPane().add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    registerOperate();//注册操作
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void registerOperate() throws SQLException, ClassNotFoundException {
        int errorWord;//获取错误码，0：无错误，1：用户ID已存在

        getString();//获取文本框输入的内容
        System.out.println("点击按钮");
        System.out.println(textName);
        System.out.println(textId);
        System.out.println(textPassWord);
        System.out.println(textRepassWord);
        if(!textPassWord.equals(textRepassWord)){//若两次输入密码不同，则返回1
            ResetImg(1);
        }
        try {
            errorWord = JDBC.RegisterError("Teacher",textId);//
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(errorWord);
        if(errorWord==0){//可正常添加
            try {
                JDBC.Register(textName,textId,textPassWord);//将用户添加到表中
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
//            new OpenCourse(textId);
            new Interim("teacher",textId);
            dispose();
        }
        else{//返回1，ID已存在
            ResetImg(2);
        }
    }

    private void InitRegister() {
        setSize(400,500);//设置界面大小
        setTitle("教师注册");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }
    private void ResetImg(int errorWord) {
        //1：两次密码输入有误，2：用户ID已存在
        this.getContentPane().removeAll();//清空窗口
        InitImage();
        String stringError;
        if(errorWord==1){
            stringError="两次密码输入不同！";
        }
        else{
            stringError="该用户已存在！";
        }
        JLabel jLabelError = new JLabel(stringError);
        jLabelError.setBounds(220,280,150,20);
        this.getContentPane().add(jLabelError);

        this.getContentPane().repaint();
    }
    private void getString(){
        textName=jTextFieldName.getText();
        textId=jTextFieldId.getText();
        textPassWord=jTextFieldPassword.getText();
        textRepassWord=jTextFieldRepassword.getText();
    }

}
