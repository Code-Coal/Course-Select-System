package Administrator;

import JDBC.JDBC;
import TeacherOperate.ModifyScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdministratorCourse extends JFrame {
    String textCourseName;
    JTextField jTextField = new JTextField(30);
    public AdministratorCourse(){
        InitFace();
        InitImage();
        setVisible(true);
    }

    private void InitImage() {
        addJText();
        addString();
        addEnter();
    }

    private void addString() {
        JLabel course = new JLabel("课程");
        course.setBounds(20,25,60,15);
        course.setFont(new Font("Serif",Font.BOLD,15));
        getContentPane().add(course);
    }

    private void addEnter() {
        JButton jButtonEnter = new JButton("确定");
        jButtonEnter.setBounds(150,120,100,20);
        getContentPane().add(jButtonEnter);
        jButtonEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operator();
            }
        });
    }

    private void operator() {
        textCourseName=jTextField.getText();
        try {
            if(JDBC.OpenCourseError(textCourseName)==1){//返回1，即课程名字已存在
                try {
                    String teacherId = JDBC.getTeacherId(textCourseName);
                    new ModifyScore(teacherId);
                    dispose();
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

    private void addJText() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(80,20,240,30);

        jTextField.setBounds(0,0,240,30);
        jPanel.add(jTextField);
        getContentPane().add(jPanel);

    }

    private void InitFace() {
        setSize(400,200);//设置界面大小
        setTitle("管理员界面");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }

}
