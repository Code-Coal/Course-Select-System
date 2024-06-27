package Register;

import About.About;
import Interim.Interim;
import JDBC.JDBC;

import CourseSelect.CourseSelect;
import Login.Login;
import sun.security.krb5.internal.PAData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

/*
* 学生注册界面
*
* */
public class StudentsRegisert extends JFrame {

    String textName;
    String textId;
    String textPassWord;
    String textRepassWord;
    String textSpecialty;

    JTextField jTextFieldName = new JTextField(30);//姓名文本框
    JTextField jTextFieldId = new JTextField(30);//ID文本框
    JTextField jTextFieldPassword = new JTextField(30);//密码文本框
    JTextField jTextFieldRepassword = new JTextField(30);//再次输入密码，确认密码正确
    JTextField jTextFieldSpecialty = new JTextField(30);//专业文本框

    public StudentsRegisert(){
        //初始化界面
        InitRegister();
        InitImage();
        setVisible(true);
    }

    private void InitImage() {

        //添加菜单
        addJMenu();
        //添加文本框
        addJText();
        //添加文字说明
        addWord();

        //添加注册按钮
        setEnter();

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
    private void addWord(){
        JLabel jLabelName = new JLabel("名字");
        JLabel jLabelID = new JLabel("ID");
        JLabel password = new JLabel("密码");
        JLabel rePassword = new JLabel("再次输入密码");
        JLabel specialty = new JLabel("专业");
        //设置文字位置
        jLabelName.setBounds(20,105,60,15);
        jLabelID.setBounds(20,155,60,15);
        password.setBounds(20,205,60,15);
        rePassword.setBounds(5,255,75,15);
        specialty.setBounds(20,305,60,15);

        jLabelName.setFont(new Font("Serif",Font.BOLD,15));
        jLabelID.setFont(new Font("Serif",Font.BOLD,15));
        password.setFont(new Font("Serif",Font.BOLD,15));
        rePassword.setFont(new Font("Serif",Font.BOLD,10));
        specialty.setFont(new Font("Serif",Font.BOLD,15));
        //添加到窗口
        getContentPane().add(jLabelName);
        getContentPane().add(jLabelID);
        getContentPane().add(password);
        getContentPane().add(rePassword);
        getContentPane().add(specialty);
    }
    private void addJText() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(80,100,240,230);


        //设置文本框位置
        jTextFieldName.setBounds(0,0,240,30);
        jTextFieldId.setBounds(0,50,240,30);
        jTextFieldPassword.setBounds(0,100,240,30);
        jTextFieldRepassword.setBounds(0,150,240,30);
        jTextFieldSpecialty.setBounds(0,200,240,30);

        //为文本框添加监听，获取内容
        jPanel.add(jTextFieldName);
        jPanel.add(jTextFieldId);
        jPanel.add(jTextFieldPassword);
        jPanel.add(jTextFieldRepassword);
        jPanel.add(jTextFieldSpecialty);
        this.getContentPane().add(jPanel);
    }

    private void setEnter(){

        //设置确定注册按钮
        JButton jButton = new JButton("注册");
        jButton.setBounds(150,350,100,28);
        getContentPane().add(jButton);
        //为按钮添加监听，点击后进行注册操作
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerOperate();//进行注册操作
            }
        });
    }

    private void registerOperate() {
        int errorWord= 0;//获取错误码，0：无错误，1：用户ID已存在

        getString();//获取文本框输入的内容
        if(!textPassWord.equals(textRepassWord)){//若两次输入的密码不一样，则提示输入错误
            ResetImg(1);
        }
        try {
            //返回错误码，0：正常添加，1：两次密码输入有误，2：用户ID已存在
            errorWord = JDBC.RegisterError("Student",textId);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        if(errorWord==0){//可正常添加
            try {
                JDBC.Register(textName,textId,textPassWord,textSpecialty);//将用户添加到表中
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            try {
                new Interim("student",textId);//添加成功后,显示过渡窗口，提示是否进入选课窗口
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            dispose();//关闭当前界面
        }
        else{//返回1，ID已存在
            ResetImg(2);
        }
    }

    private void InitRegister() {
        setSize(400,500);//设置界面大小
        setTitle("学生注册");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }
    private void ResetImg(int errorWord){
        //0：添加成功，1：两次密码输入有误，2：用户ID已存在
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
        jLabelError.setBounds(220,330,150,15);
        this.getContentPane().add(jLabelError);
    }

    private void getString(){
        textName=jTextFieldName.getText();
        textId=jTextFieldId.getText();
        textPassWord=jTextFieldPassword.getText();
        textRepassWord  = jTextFieldSpecialty.getText();
        textSpecialty=jTextFieldSpecialty.getText();
    }

}
