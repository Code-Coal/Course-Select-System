package Interim;

import CourseSelect.CourseSelect;
import TeacherOperate.OpenCourse;
import Login.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/*
* 过渡窗口
* 从登录窗到选课/开课窗口
* */
public class Interim extends JFrame {

    String identify;
    String id;
    public Interim(String identify,String id) throws SQLException, ClassNotFoundException {
        this.identify=identify;
        this.id=id;
        InitFace();
        InitImage();
        setVisible(true);
    }

    private void InitImage() throws SQLException, ClassNotFoundException {
        JButton jButtonEnter = new JButton("确定");
        JButton jButtonLogin = new JButton("返回登录");
        jButtonEnter.setBounds(20,70,90,20);
        jButtonLogin.setBounds(130,70,90,20);
        getContentPane().add(jButtonEnter);
        getContentPane().add(jButtonLogin);
        addString();

        jButtonEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enter();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
    }

    private void addString(){
        JLabel jLabel = new JLabel();
        if(identify.equals("student")){
            jLabel.setText("是否进入学生选课界面？");
        }
        else{
            jLabel.setText("是否进入教师开课界面？");
        }
        jLabel.setFont(new Font("Serif", Font.BOLD,15));
        jLabel.setBounds(20,20,200,20);
        getContentPane().add(jLabel);
    }
    private void back(){
        new Login();
    }
    private void enter() throws SQLException, ClassNotFoundException {
        if(identify.equals("student")){
            new CourseSelect(id);
        }
        else{
            new OpenCourse(id);
        }
        dispose();

    }
    private void InitFace(){
        setSize(240,150);
        setTitle("中原工学院学生选课系统");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }
}
