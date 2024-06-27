package Administrator;

import JDBC.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class InquirePassword extends JFrame {

    JTextField jTextFieldIdentify = new JTextField(30);
    JTextField jTextFieldName= new JTextField(30);
    JTextField jTextFieldId = new JTextField(30);
    String textIdentify;
    String textName;
    String textId;
    public InquirePassword(){
        //初始化菜单
        InitFace();
        //设置文本框等
        InitImage();
        //设置说明文字
        InitString();
        setVisible(true);
    }

    private void InitString() {
        JLabel jLabelIdentify = new JLabel("身份");
        JLabel name = new JLabel("姓名");
        JLabel jLabelId = new JLabel("ID");
        jLabelIdentify.setBounds(20,25,60,15);
        name.setBounds(20,75,60,15);
        jLabelId.setBounds(20,125,60,15);
        getContentPane().add(jLabelId);
        getContentPane().add(name);
        getContentPane().add(jLabelIdentify);
    }

    private void InitImage() {
        addJText();
        addEnter();
        //设置说明文字
        InitString();
    }

    private void addEnter() {
        JButton jButton = new JButton("确定");
        jButton.setBounds(150,230,100,20);
        getContentPane().add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textIdentify=jTextFieldIdentify.getText();
                textName=jTextFieldName.getText();
                textId=jTextFieldId.getText();
                try {
                    String back= JDBC.inquirePassword(textName,textId,textIdentify);
                    ResetImg(back);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void addJText() {
        JPanel jPanel = new JPanel();
        jPanel.setBounds(80,20,240,130);
        jPanel.setLayout(null);

        jTextFieldIdentify.setBounds(0,0,240,30);
        jTextFieldName.setBounds(0,50,240,30);
        jTextFieldId.setBounds(0,100,240,30);
        jPanel.add(jTextFieldName);
        jPanel.add(jTextFieldId);
        jPanel.add(jTextFieldIdentify);
        getContentPane().add(jPanel);
        String[] options={"学生","教师"};
        JComboBox<String> stringJComboBox = new JComboBox<>(options);
        stringJComboBox.setBounds(320,20,50,20);
        getContentPane().add(stringJComboBox);
        stringJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextFieldIdentify.setText((String) stringJComboBox.getSelectedItem());
            }
        });
    }

    private void ResetImg(String s){
        this.getContentPane().removeAll();
        InitImage();
        JLabel jLabel = new JLabel(s);
        jLabel.setBounds(200,150,200,15);
        jLabel.setFont(new Font("Serif",Font.BOLD,12));
        jLabel.setForeground(Color.RED);
        getContentPane().add(jLabel);
        this.getContentPane().repaint();
    }
    private void InitFace() {
        setSize(400,300);//设置界面大小
        setTitle("登录");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }
}
