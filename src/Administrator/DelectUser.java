package Administrator;

import JDBC.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DelectUser extends JFrame {

    String textIdentify;
    String textName;
    String textId;
    String textPassword;
    JTextField jTextFieldIdentify = new JTextField(30);
    JTextField jTextFieldName= new JTextField(30);
    JTextField jTextFieldId = new JTextField(30);
    JTextField jTextFieldPassword = new JTextField(30);
    public DelectUser(){
        InitFace();
        InitImage();
        setVisible(true);
    }

    private void InitImage() {
        addJText();
        addString();
        addEnter();
    }

    private void addEnter(){
        JButton jButton = new JButton("确认删除");
        jButton.setBounds(150,300,100,20);
        getContentPane().add(jButton);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTextString();
                try {
                    ResetImg(JDBC.loginName(textName,textId,textPassword,textIdentify));
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    private void ResetImg(int error) {
        this.getContentPane().removeAll();
        InitImage();
        String s;
        if(error==0){
            s="删除成功";
        }
        else if(error==1){
            s="密码错误";
        }
        else if(error==2){
            s="不存在账户";
        }
        else if(error==3){
            s="名字或ID输入错误";
        }
        else{
            s="删除失败";
        }
        JLabel jLabel = new JLabel(s);
        jLabel.setBounds(200,220,100,15);
        jLabel.setFont(new Font("Serif",Font.BOLD,15));
        jLabel.setForeground(Color.RED);
    }

    private void getTextString() {
        textId=jTextFieldId.getText();
        textName=jTextFieldName.getText();
        textPassword=jTextFieldPassword.getText();
        textIdentify=jTextFieldIdentify.getText();
    }

    private void addJText() {
        JPanel jPanel = new JPanel();
        jPanel.setBounds(80,20,240,180);
        jPanel.setLayout(null);

        jTextFieldIdentify.setBounds(0,0,240,30);
        jTextFieldName.setBounds(0,50,240,30);
        jTextFieldId.setBounds(0,100,240,30);
        jTextFieldPassword.setBounds(0,150,240,30);
        jPanel.add(jTextFieldName);
        jPanel.add(jTextFieldId);
        jPanel.add(jTextFieldIdentify);
        jPanel.add(jTextFieldPassword);
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

    private void addString() {
        JLabel jLabelIdentify = new JLabel("身份");
        JLabel name = new JLabel("姓名");
        JLabel jLabelId = new JLabel("ID");
        JLabel jLabelPassword = new JLabel("密码");
        jLabelIdentify.setBounds(20,25,60,15);
        name.setBounds(20,75,60,15);
        jLabelId.setBounds(20,125,60,15);
        jLabelPassword.setBounds(20,175,60,15);
        getContentPane().add(jLabelPassword);
        getContentPane().add(jLabelId);
        getContentPane().add(name);
        getContentPane().add(jLabelIdentify);
    }

    private void InitFace() {
        setSize(400,400);//设置界面大小
        setTitle("登录");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);

    }
}
