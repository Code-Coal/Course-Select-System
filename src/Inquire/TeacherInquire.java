package Inquire;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import JDBC.*;
import TeacherOperate.ModifyScore;
import TeacherOperate.OpenCourse;
import Login.*;
import About.About;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeacherInquire extends JFrame {
    String id;//教师编号

    public TeacherInquire(String id) throws SQLException, ClassNotFoundException {
        this.id=id;
        InitFace();
        InitImage();
        setVisible(true);
    }

    private void InitImage() throws SQLException, ClassNotFoundException {
        //显示所有已选该课程的学生

        addJMenu();
        String course=JDBC.teacherCourse(id);
        System.out.println(course);
        if(course.equals("你还未开课")){
            //显示没有学生选择该课程
            JLabel jLabel = new JLabel("还没有学生选择你的课程！");
            jLabel.setBounds(50,80,300,20);
            jLabel.setFont(new Font("Serif",Font.BOLD,20));
            jLabel.setForeground(Color.RED);
            getContentPane().add(jLabel);

            SetEnter();
        }
        else {
            showStudent(course);
        }


    }

    private void showStudent(String course) throws ClassNotFoundException, SQLException {
        Connection conn = JDBC.CollectMySQL();
        String student="select * from Relation r,Course c,Student s where c.teacherId='"+id+"' and r.claname=c.claname and r.id=s.id";
        Statement stmt=conn.createStatement();
        ResultSet rs = stmt.executeQuery(student);
        int flag=0;

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("学号");
        model.addColumn("姓名");
        model.addColumn("分数");
        while (rs.next()){
            if(flag==0){//先显示标题
                //显示所开设课程
                addTopString(course);

            }
            model.addRow(new Object[]{rs.getString("s.id"),rs.getString("s.name"),rs.getString("score")});
            System.out.println("打印选课");

            flag++;
        }
        JTable jTable = new JTable(model);
        jTable.setMaximumSize(new Dimension(400,200));
        JScrollPane scollPane = new JScrollPane(jTable);
        scollPane.setMaximumSize(new Dimension(400,200));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scollPane,BorderLayout.CENTER);
        //添加对表中更改数据的监听
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if(e.getType()==TableModelEvent.UPDATE){
                    int row=e.getFirstRow();
                    int colum=e.getColumn();

                    String columName=model.getColumnName(colum);//获取列名
                    Object data = model.getValueAt(row, colum);
                    Object id = model.getValueAt(row, 0);
                    try {
                        updataDatabase(id,columName,data);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    private void updataDatabase(Object studentId, String columName, Object data) throws SQLException, ClassNotFoundException {
        if(columName.equals("分数")){

            JDBC.ModifyScore((String) studentId,(String) data,id);
        }
        else{
            ResetImg();
        }
    }

    private void addJMenu(){//***********************添加菜单***************************************
        JMenuBar jMenuBar = new JMenuBar();
        JMenu  me= new JMenu("菜单");
        JMenu about = new JMenu("关于");
        JMenuItem reLogin = new JMenuItem("重新登录");
        JMenuItem modify = new JMenuItem("直接修改成绩");
        me.add(reLogin);
        me.add(modify);
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
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModifyScore(id);
                dispose();
            }
        });

    }
    private void addTopString(String course) throws SQLException, ClassNotFoundException {
        //显示该教师所开设的课程
        //显示开设课程
        JLabel jLabelOpenCourse = new JLabel("开设课程：");
        jLabelOpenCourse.setBounds(20,300,100,15);
        getContentPane().add(jLabelOpenCourse);
        //显示所开课程
        JLabel jLabelCourse = new JLabel(course);
        jLabelCourse.setBounds(100,300,100,15);

        getContentPane().add(jLabelCourse);

    }


    private void SetEnter() {//*************添加修改成绩按钮


        JButton jButtonOpenCourse = new JButton("开设课程->");

        jButtonOpenCourse.setBounds(60,300,100,20);

        jButtonOpenCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!JDBC.getTeacherOpenCourse(id)) {//该教师未开课
                        new OpenCourse(id);
                        dispose();
                    }
                    else{
                        ResetImg();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        this.getContentPane().add(jButtonOpenCourse);
    }

    private void ResetImg(){
        JLabel jLabel = new JLabel("已开课！");
        jLabel.setBounds(305,80,200,30);
        jLabel.setFont(new Font("Serif",Font.BOLD,13));
        jLabel.setForeground(Color.RED);
        getContentPane().add(jLabel);
        getContentPane().repaint();//刷新

    }

    private void InitFace() {
        setSize(400,400);
        setTitle("教师查询");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }

}
