package Register;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Register extends JFrame {

    String textIdentify;
    public Register(){
        //
        InitFace();
        InitImage();
        setVisible(true);
    }

    private void InitImage() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        String[] options={"学生","教师"};
        JComboBox<String> stringJComboBox = new JComboBox<>(options);
        JTextField jTextFieldIdentify = new JTextField(30);
        //设置位置
        jPanel.setBounds(60,70,240,30);
        jTextFieldIdentify.setBounds(0,0,240,30);
        stringJComboBox.setBounds(300,70,50,30);
        jPanel.add(jTextFieldIdentify);
        getContentPane().add(jPanel);
        getContentPane().add(stringJComboBox);
       stringJComboBox.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               jTextFieldIdentify.setText((String) stringJComboBox.getSelectedItem());
               textIdentify=jTextFieldIdentify.getText();//获取文本框中内容
           }
       });

        setEnter();
    }
    private void setEnter(){
        JButton jButton = new JButton("确定");
        jButton.setBounds(150,110,100,30);
        getContentPane().add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textIdentify.equals("学生")){
                    new StudentsRegisert();
                }
                else{
                    new TeacherRegister();
                }
                dispose();
            }
        });
    }

    private void InitFace() {
        setSize(400,200);//设置界面大小
        setTitle("注册");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }

}
