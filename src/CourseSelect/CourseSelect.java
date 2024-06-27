package CourseSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Map;
import Inquire.StudentsInquire;

import About.About;
import Inquire.StudentsInquire;
import JDBC.*;
import Login.Login;

/*
* 学生选课界面
* 显示学生所选课程
* 显示学生可选课程*/
public class CourseSelect extends JFrame {


    String id;//学生ID号
    JButton jButton = new JButton("确定");
    String textCourse;
    JTextField jTextFieldSelect = new JTextField(30);

    public CourseSelect(String id) throws SQLException, ClassNotFoundException {

        this.id=id;
        InitFace();
        //添加菜单
        addJMenu();
        InitImage();
//        ResetImg();
        setVisible(true);
    }

    private void InitImage() throws SQLException, ClassNotFoundException {

        //显示已选课程
        showHasChoice();

        //显示可选课程
        showCouldChoice();

        //显示确定按钮
        showEnter();
    }

    private void addJMenu(){//***********************添加菜单***************************************
        JMenuBar jMenuBar = new JMenuBar();
        JMenu  me= new JMenu("菜单");
        JMenu about = new JMenu("关于");
        JMenuItem reLogin = new JMenuItem("重新登录");
        JMenuItem back = new JMenuItem("返回查询界面");
        me.add(reLogin);
        jMenuBar.add(me);
        jMenuBar.add(about);
        me.add(back);
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
                    new StudentsInquire(id);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

    }
    private void showEnter() {


        jButton.setBounds(125,300,100,20);
        getContentPane().add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textCourse=jTextFieldSelect.getText();//点击确定时获取文本框里的内容
                System.out.println(textCourse+"**************************************");

                try {
                    if(JDBC.inspectCourse(id,textCourse)){//检查选择课程是否重复
                        System.out.println("选课未重复**********--------------");
                        //未重复
                        try {
                            JDBC.addStudent(textCourse,id);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            ResetImg(0);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else{//选课重复
                        System.out.println("选课重复***********----------------");
                        try {
                            ResetImg(1);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private void ResetImg(int errorWord) throws SQLException, ClassNotFoundException {//0：选课成功，1：选课失败

        this.getContentPane().removeAll();

        InitImage();

        JLabel jLabelError = new JLabel();
        System.out.println(errorWord);
        if(errorWord==0){
            jLabelError.setText("选课成功！");
        }
        else{
            jLabelError.setText("课程选择重复！");
        }
        jLabelError.setBounds(200,150,150,15);
        jLabelError.setFont(new Font("Serif",Font.BOLD,15));
        jLabelError.setForeground(Color.RED);
        getContentPane().add(jLabelError);
        this.getContentPane().repaint();
    }

    private void showCouldChoice() throws SQLException, ClassNotFoundException {
        JLabel jLabelCouldChoice = new JLabel("可选课程：");
        jLabelCouldChoice.setBounds(20,150,100,15);
        jLabelCouldChoice.setFont(new Font("Serif",Font.BOLD,15));
        jLabelCouldChoice.setForeground(Color.BLACK);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(50,200,240,30);

        jTextFieldSelect.setBounds(0,0,240,30);

        String[] allCourse=new String[20];


        Connection conn = JDBC.CollectMySQL();
        String course="select * from Course";
        Statement stmt=conn.createStatement();
        ResultSet rs = stmt.executeQuery(course);

        int idx=0;
        while (rs.next()){
            allCourse[idx]=rs.getString("claname");
            System.out.println(allCourse[idx]);
            idx++;
        }
        JComboBox<String> stringJComboBox = new JComboBox<>(allCourse);

        stringJComboBox.setBounds(290,200,50,30);
        stringJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextFieldSelect.setText((String) stringJComboBox.getSelectedItem());
            }
        });
        jPanel.add(jTextFieldSelect);
        getContentPane().add(jPanel);
        getContentPane().add(stringJComboBox);
        getContentPane().add(jLabelCouldChoice);
    }

    private void showHasChoice() throws SQLException, ClassNotFoundException {
        //显示标题
        JLabel jLabelHasChoice = new JLabel("已选课程：");
        jLabelHasChoice.setBounds(20,100,100,15);
        jLabelHasChoice.setFont(new Font("Serif",Font.BOLD,15));
        jLabelHasChoice.setForeground(Color.BLACK);
        getContentPane().add(jLabelHasChoice);

        Connection conn = JDBC.CollectMySQL();
        String course="select * from Relation where id='"+id+"'";
        Statement stmt=conn.createStatement();
        ResultSet rs = stmt.executeQuery(course);
        String[] allCourse=new String[20];

        int idx=0;//
        while (rs.next()){
            allCourse[idx]=rs.getString("claname");
            idx++;
        }
        if(idx==0){
            JLabel jLabelNotCourse = new JLabel("你还未选课程");
            jLabelNotCourse.setBounds(100,100,100,15);
            jLabelNotCourse.setFont(new Font("Serif",Font.BOLD,15));
            jLabelNotCourse.setForeground(Color.BLACK);
            getContentPane().add(jLabelNotCourse);
        }
        else{
            JComboBox<String> stringJComboBox = new JComboBox<>(allCourse);
            stringJComboBox.setBounds(100,100,100,30);
            this.getContentPane().add(stringJComboBox);
        }

    }

    private void InitFace() {
        setSize(400,400);//设置界面大小
        setTitle("选课");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }

}
