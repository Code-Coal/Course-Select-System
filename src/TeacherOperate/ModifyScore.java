package TeacherOperate;

import About.About;
import Inquire.TeacherInquire;
import JDBC.JDBC;
import Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class ModifyScore extends JFrame {
    String id;//教师ID
    String textName;
    String textScoer;
    String textID;
    JTextField jTextFieldName = new JTextField(30);
    JTextField jTextFieldScore = new JTextField(30);
    JTextField jTextFieldID=new JTextField(30);
    public ModifyScore(String id){
        this.id=id;
        Initface();
        InitImage();
//        ResetImg(0);
        setVisible(true);
    }

    private void InitImage() {
        //添加菜单
        addJMenu();
        //添加文本框
        addJText();
        //添加文字说明
        addString();
        //添加确定图片
        addEnter();


    }

    private void addJMenu(){//***********************添加菜单***************************************
        JMenuBar jMenuBar = new JMenuBar();
        JMenu  me= new JMenu("菜单");
        JMenu about = new JMenu("关于");
        JMenuItem reLogin = new JMenuItem("重新登录");
        JMenuItem back = new JMenuItem("返回查询界面");

        me.add(reLogin);
        me.add(back);
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
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TeacherInquire(id);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }
    private void addString(){
        JLabel name = new JLabel("学生姓名");
        JLabel score = new JLabel("成绩");
        JLabel jLabelID = new JLabel("学生ID");
        name.setBounds(20,155,60,15);
        jLabelID.setBounds(20,205,60,15);
        score.setBounds(20,255,60,15);
        //设置字体
        name.setFont(new Font("Serif",Font.BOLD,13));
        score.setFont(new Font("Serif",Font.BOLD,15));
        jLabelID.setFont(new Font("Serif",Font.BOLD,15));
        //添加到窗口
        getContentPane().add(jLabelID);
        getContentPane().add(name);
        getContentPane().add(score);

    }
    private void addEnter(){

        JButton jButton = new JButton("确认修改");
        jButton.setBounds(150,300,100,20);
        getContentPane().add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n=getJText();//获取返回值
                    System.out.println(textName);
                    System.out.println(textScoer);
                    System.out.println(n);
                    if( n!=0){
                        return;
                    }
                    //**********************************通过ID修改学生成绩
                    if(JDBC.ModifyScore(textID,textScoer,id)){//显示修改成功
                        //*******************error
                        System.out.println("修改成功");
                        ResetImg(0);
                    }
                    else{
                        ResetImg(3);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private int  getJText(){
        textName=jTextFieldName.getText();
        textScoer=jTextFieldScore.getText();
        textID=jTextFieldID.getText();

        //当输入学生名字时，在数据库中搜索是否有该学生，若未检测到该学生，则返回1，提示未检测到学生
        try {
            if(!JDBC.getStudent(textName,id,textID)){//返回1，未检测到学生
                System.out.println("未检测到学生");
            ResetImg(1);
                return 1;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("开始成绩范围");
        //检测成绩是否在合理范围内
        try {
            if(!JDBC.getScoreRange(id,textScoer)){
                ResetImg(2);
                return 2;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return 0;
    }

    private void addJText() {
        //显示三个文本框，第一个输入学生名字，第二个输入学生ID，第三个输入学生成绩
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(80,150,300,130);


        jTextFieldName.setBounds(0,0,240,30);
        jTextFieldID.setBounds(0,50,240,30);
        jTextFieldScore.setBounds(0,100,240,30);

        jPanel.add(jTextFieldName);
        jPanel.add(jTextFieldID);
        jPanel.add(jTextFieldScore);

        this.getContentPane().add(jPanel);

    }

    private void ResetImg(int errorWord){
        this.getContentPane().removeAll();//清空屏幕

        System.out.println("刷新");
        InitImage();
        //显示错误原因，1：未检测到该学生，2：成绩不符合格式
        //显示更改成功，0
        String stringError;//错误语句
        JLabel jLabelError = new JLabel();
        if(errorWord==0){
            stringError="成绩更改成功！";
        }
        else if(errorWord==1){
            stringError="未检测到该学生！";
        }
        else if(errorWord==2){
            stringError="成绩不符合格式！";
        }
        else{
            stringError="添加失败！";
        }

        jLabelError.setText(stringError);
        jLabelError.setBounds(220,280,150,20);
        jLabelError.setFont(new Font("Seirf",Font.BOLD,14));
        jLabelError.setForeground(Color.RED);

        this.getContentPane().add(jLabelError);

        getContentPane().repaint();//刷新
    }

    private void Initface() {
        setSize(400,400);
        setTitle("中原工学院学生选课系统");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }

}
