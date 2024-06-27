package Login;

import About.About;
import Administrator.Administrator;
import Inquire.StudentsInquire;
import Inquire.TeacherInquire;
import JDBC.JDBC;
import Register.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/*
设置登录界面
*/
public class Login extends JFrame {

    String textName;
    String textId;
    String textPassword;
    String textIdentify;
    JTextField jTextFieldName = new JTextField(30);
    JTextField jTextFieldId = new JTextField(30);
    JTextField jTextFieldIdentify = new JTextField(30);
    JPasswordField jPasswordField=new JPasswordField(30);

    public Login() {
        //初始化界面
        InitLogin();
        //设置
        InitImage();
//        ResetImg(2);
        setVisible(true);
    }

    private void InitImage() {

        //添加菜单
        addJMenu();
        //添加文本框及选项
        addJText();
        //添加说明文字
        addWord();
        //添加登录按钮
        addEnter();


    }

    private void addJMenu(){//***********************添加菜单***************************************
        JMenuBar jMenuBar = new JMenuBar();
        JMenu  me= new JMenu("菜单");
        JMenu about = new JMenu("关于");
        JMenuItem reLogin = new JMenuItem("刷新登录窗口");
        me.add(reLogin);
        jMenuBar.add(me);
        jMenuBar.add(about);

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

        setJMenuBar(jMenuBar);
    }
    private void addWord() {
        JLabel jLabelIdentify = new JLabel("身份");
        JLabel jLabelName = new JLabel("姓名");
        JLabel jLabelID = new JLabel("ID");
        JLabel jLabelPassword = new JLabel("密码");
        jLabelIdentify.setBounds(20,105,60,15);
        jLabelName.setBounds(20,155,60,15);
        jLabelID.setBounds(20,205,60,15);
        jLabelPassword.setBounds(20,255,60,15);
        jLabelName.setFont(new Font("Serif",Font.BOLD,15));
        jLabelID.setFont(new Font("Serif",Font.BOLD,15));
        jLabelIdentify.setFont(new Font("Serif",Font.BOLD,15));
        jLabelPassword.setFont(new Font("Serif",Font.BOLD,15));

        getContentPane().add(jLabelName);
        getContentPane().add(jLabelID);
        getContentPane().add(jLabelPassword);
        getContentPane().add(jLabelIdentify);

    }

    private void addJText() {
        JPanel jPanel = new JPanel();//添加文本框，方便放置文本框
        jPanel.setLayout(null);//设置手动布局
        //设置位置
        jPanel.setBounds(80,100,300,180);

        //设置文本框位置，相对JP容器的位置
        jTextFieldIdentify.setBounds(0,0,240,30);
        jTextFieldName.setBounds(0,50,240,30);
        jTextFieldId.setBounds(0,100,240,30);
//        jTextFieldPassword.setBounds(0,150,240,30);
        jPasswordField.setBounds(0,150,240,30);
        //将文本框添加到容器中
        jPanel.add(jTextFieldName);
        jPanel.add(jTextFieldId);
        jPanel.add(jPasswordField);
//        jPanel.add(jTextFieldPassword);
        jPanel.add(jTextFieldIdentify);

        //设置选项
        String[] options={"学生","教师"};
        JComboBox<String> stringJComboBox = new JComboBox<>(options);
        //设置选项位置
        stringJComboBox.setBounds(270,100,50,30);

        //当用户选择身份时，将身份显示在文本框中
        stringJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextFieldIdentify.setText((String) stringJComboBox.getSelectedItem());
                textIdentify=jTextFieldIdentify.getText();//获取文本框中内容
            }
        });


        //将容器添加到界面中
        this.getContentPane().add(stringJComboBox);
        this.getContentPane().add(jPanel);
    }

    private void addEnter() {
        JButton jButtonLogin = new JButton("登录");
        jButtonLogin.setBounds(50,300,100,36);
        jButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginOperate();//点击登录按钮后的系列操作

            }

        });

        //添加注册按钮
        JButton jButtonRegister = new JButton("注册");
        jButtonRegister.setBounds(250,300,100,36);
        jButtonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
                dispose();
            }
        });
        //将按钮添加到窗口
        this.getContentPane().add(jButtonLogin);
        this.getContentPane().add(jButtonRegister);
    }

    private void loginOperate() {
        System.out.println("点击登录按钮");//************
        int errorWord;

        //获取文本框中的内容
        addText();

        judgeAdministrator();
        try {//获取返回的码，1：密码错误，2：账户不存在,3：姓名或id输入错误
            errorWord= JDBC.loginName(textName,textId,textPassword,textIdentify);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if(errorWord==0){//errod=0,若该用户在数据库中，且正常登录
            if(textIdentify.equals("学生")){//若身份是学生，则进入学生查询窗口
                try {
                    new StudentsInquire(textId);//进入学生查询窗口
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();//关闭该窗口
            }
            else{//若身份是老师，则直接进入教师查询窗口
                try {
                    new TeacherInquire(textId);//进入教师查询窗口
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();//关闭当前窗口
            }
        }
        else{//若不在数据库中
            ResetImg(errorWord);//显示错误原因
        }
    }

    private void judgeAdministrator() {
        if(textPassword.equals("363636")&&textName.equals("jzy")&&textId.equals("000001")){
            new Administrator();
            dispose();
        }
    }

    private void addText(){
        //获取文本框中的内容
        textName=jTextFieldName.getText();
        textId=jTextFieldId.getText();
        textIdentify=jTextFieldIdentify.getText();
        textPassword=jPasswordField.getText();
    }
    public void ResetImg(int errorWord){//出现登录错误
        this.getContentPane().removeAll();//清空图片
        InitImage();//重新设置图片

        //添加错误问题
        // 错误码，1：密码错误，2：账户不存在,3：姓名或id输入错误
        String Errorstring;//错误原因
        if(errorWord==1){
            Errorstring="密码错误！";
        }
        else if(errorWord==3){//当姓名与ID对照有误
            Errorstring="姓名或ID错误！";
        }
        else{//若未检测出账户
            Errorstring="无账户,请注册账户！";

        }

        JLabel jLabelPasswordError = new JLabel(Errorstring);
        //设置字体
        jLabelPasswordError.setFont(new Font("Serif",Font.BOLD,15));
        jLabelPasswordError.setForeground(Color.RED);
        //设置位置
        jLabelPasswordError.setBounds(220,280,150,15);

        this.getContentPane().add(jLabelPasswordError);
        getContentPane().repaint();//刷新

    }


    private void InitLogin() {
        setSize(400,400);//设置界面大小
        setTitle("登录");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);

    }


}
