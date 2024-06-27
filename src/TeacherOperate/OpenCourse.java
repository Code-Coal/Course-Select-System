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

public class OpenCourse extends JFrame {

    String textClazzName;
    String textFeature;

    String textRange;
    String teacherId;

    JTextField jTextFieldClazzName = new JTextField(30);
    JTextField jTextFieldFeature = new JTextField(30);//输入课程性质（必修，选修）
    JTextField jTextFieldRange = new JTextField(30);
    public OpenCourse(String id){
        teacherId=id;
        InitFace();
        InitImage();
//        ResetImg(2);
        setVisible(true);
    }

    private void InitImage() {
        //添加菜单
        addJMenu();
        //添加文本框
        addJText();
        //添加文字说明
        addString();
        //添加确定按钮
        setEnter();

    }

    private void addJMenu(){//***********************添加菜单***************************************
        JMenuBar jMenuBar = new JMenuBar();
        JMenu  me= new JMenu("菜单");
        JMenu about = new JMenu("关于");
        JMenuItem reLogin = new JMenuItem("重新登录");
        JMenuItem back = new JMenuItem("返回查询");
        me.add(back);
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
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TeacherInquire(teacherId);
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
        JLabel clazzName = new JLabel("课程名字");
        JLabel feature = new JLabel("性质");
        JLabel score = new JLabel("分数");
        clazzName.setBounds(10,105,80,15);
        feature.setBounds(20,155,60,15);
        score.setBounds(20,205,60,15);

        clazzName.setFont(new Font("Serif",Font.BOLD,15));
        feature.setFont(new Font("Serif",Font.BOLD,15));
        score.setFont(new Font("Serif",Font.BOLD,15));

        getContentPane().add(clazzName);
        getContentPane().add(feature);
        getContentPane().add(score);

    }
    private void addJText() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(80,100,240,130);

        String[] options={"必修","选修"};
        JComboBox<String> stringJComboBox = new JComboBox<>(options);

        jTextFieldClazzName.setBounds(0,0,240,30);
        jTextFieldFeature.setBounds(0,50,240,30);
        jTextFieldRange.setBounds(0,100,240,30);
        stringJComboBox.setBounds(320,150,50,20);

        //为文本框添加监听

        stringJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextFieldFeature.setText((String) stringJComboBox.getSelectedItem());
                textFeature=jTextFieldFeature.getText();
            }
        });
        //添加到窗口
        jPanel.add(jTextFieldClazzName);
        jPanel.add(jTextFieldFeature);
        jPanel.add(jTextFieldRange);
        this.getContentPane().add(jPanel);
        this.getContentPane().add(stringJComboBox);
    }

    private void setEnter(){
        JButton jButton = new JButton("开课");
        jButton.setBounds(150,250,100,50);
        this.getContentPane().add(jButton);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCourseOperate();
            }
        });
    }

    private void openCourseOperate() {
        //获取文本框输入
        getString();
        int errorWord= 0;
        try {
            errorWord = JDBC.OpenCourseError(textClazzName);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        if(errorWord==0){
            try {
                JDBC.OpenCourse(textClazzName,textFeature,textRange,teacherId);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            //**************开课完成***************

        }
        ResetImg(errorWord);
    }

    private void getString(){
        textClazzName=jTextFieldClazzName.getText();
        textRange=jTextFieldRange.getText();
    }

    private void InitFace() {
        setSize(400,400);//设置界面大小
        setTitle("中原工学院学生选课系统");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }

    private void ResetImg(int errorWord){//1：课程名字已存在
        this.getContentPane().removeAll();
        InitImage();
        String stringError;
        if(errorWord==0){
            stringError="开课成功";
        }
        else{
            stringError="课程名字已存在";
        }

        JLabel jLabelError = new JLabel(stringError);
        jLabelError.setBounds(220,230,150,15);
        this.getContentPane().add(jLabelError);
        this.getContentPane().repaint();
    }

}
