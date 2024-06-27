package Inquire;

import About.About;
import Login.Login;
import sun.management.jdp.JdpJmxPacket;

import JDBC.*;
import CourseSelect.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentsInquire extends JFrame {

    String id;
    public StudentsInquire(String id) throws SQLException, ClassNotFoundException {
        this.id=id;
        InitFace();
        InitImage();
        setVisible(true);
    }

    private void InitImage() throws SQLException, ClassNotFoundException {

        //添加菜单
        addJMenu();

        if (0 == JDBC.CourseCondition(id)){//获取学生当前选课情况，0：已选课，1：未选课

            JLabel jLabelCourse = new JLabel("课程");
            jLabelCourse.setBounds(20,20,150,20);
            jLabelCourse.setFont(new Font("Serif",Font.BOLD,15));
            getContentPane().add(jLabelCourse);

            JLabel jLabelScore = new JLabel("分数");
            jLabelScore.setBounds(140,20,100,20);
            jLabelScore.setFont(new Font("Serif",Font.BOLD,15));
            getContentPane().add(jLabelScore);

            Connection conn = JDBC.CollectMySQL();
            String course="select * from Relation where id='"+id+"'";
            Statement stmt=conn.createStatement();
            ResultSet rs = stmt.executeQuery(course);

            int idx=0;
            while (rs.next()){
                String s=rs.getString("claname");
                JLabel jLabel = new JLabel(s);
                jLabel.setBounds(20,40+idx*30,200,30);
                getContentPane().add(jLabel);

                String stringScore=rs.getString("score");
                JLabel jLabelCourseScore = new JLabel(stringScore);
                jLabelCourseScore.setBounds(140,40+idx*30,200,30);
                getContentPane().add(jLabelCourseScore);
                idx++;
            }


        }
        else{//未选课
            //提示该学生未选课
            JLabel jLabelNoSelect = new JLabel("你还未选课！");
            jLabelNoSelect.setBounds(20,20,200,30);
            jLabelNoSelect.setFont(new Font("Serif",Font.BOLD,20));
            jLabelNoSelect.setForeground(Color.RED);
            getContentPane().add(jLabelNoSelect);

        }

        //添加选课按钮，按下进入选课窗口
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
    private void setEnter() {//**********************error**********************************8
        JButton jButtonSelect = new JButton("选课");
        jButtonSelect.setBounds(250,40,100,20);
        getContentPane().add(jButtonSelect);
        jButtonSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CourseSelect(id);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();

            }
        });
    }


    private void InitFace() {
        setSize(400,400);//设置界面大小
        setTitle("学生查询");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }


}
