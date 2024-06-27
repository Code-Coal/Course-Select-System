package Administrator;

import TeacherOperate.ModifyScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administrator extends JFrame {
    public Administrator(){
        InitFace();
        InitImage();
        setVisible(true);
    }
    private void InitImage() {

        addEnter();
        addString();

    }

    private void addString() {
        JLabel jLabel = new JLabel("进入管理员界面");
        jLabel.setBounds(20,20,200,20);
        jLabel.setFont(new Font("Serif",Font.BOLD,20));
        jLabel.setForeground(Color.BLACK);
        getContentPane().add(jLabel);
    }


    private void addEnter() {
        JButton jButtonModifyScore = new JButton("修改成绩");
        jButtonModifyScore.setBounds(20,100,100,20);
        getContentPane().add(jButtonModifyScore);
        JButton jButtonInquire = new JButton("查询密码");
        jButtonInquire.setBounds(150,100,100,20);
        getContentPane().add(jButtonInquire);
        JButton jButtonDelete = new JButton("注销账户");
        jButtonDelete.setBounds(280,100,100,20);
        getContentPane().add(jButtonDelete);
        jButtonInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new InquirePassword();
                dispose();
            }
        });
        jButtonModifyScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdministratorCourse();
                dispose();
            }
        });
        jButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new DelectUser();
                dispose();
            }
        });
    }

    private void InitFace() {
        setSize(400,200);//设置界面大小
        setTitle("管理员界面");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }
}
