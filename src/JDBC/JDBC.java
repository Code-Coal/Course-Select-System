package JDBC;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.*;
/*处理数据*/


public  class JDBC {
    public static Connection CollectMySQL() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/CourseSelectionSystem";
        String username="root";
        String password="APTX4869";
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
    public static int loginName(String name,String id,String loginPassword,String identify) throws ClassNotFoundException, SQLException {

        Connection conn = CollectMySQL();

        String tableName;
        if(identify.equals("学生")){
            tableName="Student";
        }
        else{
            tableName="Teacher";
        }
        String s="select * from "+tableName+" where id='"+id+"'";
        Statement stmt=conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        if(rs.next()){//若有该ID
            String stringPassword = rs.getString("password");
            String stringName = rs.getString("name");
            if(stringPassword.equals(loginPassword)&&stringName.equals(name)){//名字正确且密码正确
                return 0;
            }
            else if ((!stringPassword.equals(loginPassword))&&stringName.equals(name)){//密码错误，返回1
                return 1;
            }
            else if(!stringName.equals(name)){//通过ID获取的名字和输入的名字不符，名字或账户输入错误
                return 3;
            }
        }
        return 2;//若未找到ID，则返回2，不存在该账户
    }

    public static int RegisterError(String identify,String id) throws SQLException, ClassNotFoundException {
        Connection conn = CollectMySQL();
        String s="select * from "+identify+" where id='"+id+"'";
        Statement stmt=conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        if(rs.next()){
            return 1;
        }
        return 0;
    }
    public static void Register(String name,String id,String passwprd,String specialty) throws SQLException, ClassNotFoundException {//学生注册

        Connection conn = CollectMySQL();
        String s="insert into student values('"+name+"','"+id+"','"+specialty+"','"+passwprd+"')";
        Statement stmt=conn.createStatement();
        stmt.executeUpdate(s);
    }
    public static void Register(String name,String id,String password) throws SQLException, ClassNotFoundException {//教师注册
        Connection conn = CollectMySQL();
        String s="insert into Teacher (name,id,password) values('"+name+"','"+id+"','"+password+"')";
        Statement stmt=conn.createStatement();
        stmt.executeUpdate(s);

    }
    public static int OpenCourseError(String courseName) throws SQLException, ClassNotFoundException {//1：课程名字已存在

        Connection conn = CollectMySQL();
        String s="select * from course where claname='"+courseName+"'";
        Statement stmt=conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        if(rs.next()){
            return 1;
        }
        return 0;
    }
    public static boolean getStudent(String studentName,String teacherId,String studentid) throws SQLException, ClassNotFoundException {//通过教师ID找到课程，再找到是否存在该学生

        //通过ID和学生名字获取是否存在该学生，避免重名情况影响改成绩
        Connection conn = CollectMySQL();
        String sqlId="select * from Relation r,Teacher t ,Student s where r.claname=t.claname and r.id='"+studentid+"' and t.id='"+teacherId+"' and s.name='"+studentName+"'";
        Statement stmtCourse=conn.createStatement();
        ResultSet rsId = stmtCourse.executeQuery(sqlId);

        if (rsId.next()){//若能通过
            return true;
        }
        return false;

    }

    public static boolean getScoreRange(String teacherId,String score) throws SQLException, ClassNotFoundException {//查找输入的成绩是否在合理范围
        Connection conn = CollectMySQL();
        String s="select * from Course where teacherId='"+teacherId+"'";
        Statement stmt=conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        if(rs.next()){
            int range=rs.getInt("ScoreRange");
            //将字符串成绩转换为int类型
            String match="[0-9]{1,4}";
            if(score.matches(match)){//判断成绩是否符合正则表达式
                int sum=0;
                int n=1;
                for(int i=0;i<score.length();i++){//将字符串转换为int型
                    sum+=(score.charAt(i)-'0')*n;
                    n*=10;
                }
                if(sum>=0&&sum<=range){//判断成绩是否在范围内
                    return true;
                }
            }

        }
        return false;
    }
    public static boolean ModifyScore(String studentId,String modifyScore,String teacherId) throws SQLException, ClassNotFoundException {//更改学生成绩，修改成功返回true
        String claname=new String();

        //将输入的数字转换为int类型
        int sum=0;
        int n=1;
        for(int i=0;i<modifyScore.length();i++){//将字符串转换为int型
            sum+=(modifyScore.charAt(modifyScore.length()-i-1)-'0')*n;
            n*=10;
        }

        Connection conn = CollectMySQL();
        //通过教师ID获取课程名字
        Statement stmtname = conn.createStatement();
        String clanamesql="select * from Teacher where id='"+teacherId+"'";
        ResultSet rscal = stmtname.executeQuery(clanamesql);
        if(rscal.next()){
            claname=rscal.getString("claname");
        }

        System.out.println(claname);
        System.out.println(sum);

        String sql="update Relation set score='"+sum+"' where id='"+studentId+"' and claname='"+claname+"'";
        Statement stmtsql = conn.createStatement();
        stmtsql.executeUpdate(sql);

        return true;
    }

    public static void OpenCourse(String clazzName,String feature,String range,String teacherId) throws SQLException, ClassNotFoundException {//开课
        Connection conn = CollectMySQL();
        String addTeacher="update Teacher set claname='"+clazzName+"' where id='"+teacherId+"'";
        String addCourse="insert into Course values('"+clazzName+"','"+teacherId+"','"+range+"','"+feature+"')";
        Statement stmt=conn.createStatement();
        stmt.executeUpdate(addTeacher);
        stmt.executeUpdate(addCourse);
    }

    public static int CourseCondition(String id) throws SQLException, ClassNotFoundException {
        Connection conn = JDBC.CollectMySQL();
        String sql="select * from Relation where id='"+id+"'";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if(rs.next()){
            return 0;
        }
        return 1;

    }
    public static boolean inspectCourse(String id,String claname) throws SQLException, ClassNotFoundException {
        Connection conn = CollectMySQL();

        String sql="select * from relation where id='"+id+"' and claname='"+claname+"'";
        Statement stmt=conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){//若搜索到课程信息，则该学生已选择该课程，返回false

            return false;
        }
        //若未检测到课程信息，则该学生未选择过该课程，返回true

        return true;
    }
    public static void addStudent(String claname,String id) throws SQLException, ClassNotFoundException {//添加学生
        Connection conn = CollectMySQL();
        String s="insert into relation (claname,id)values ('"+claname+"','"+id+"')";
        Statement stmt=conn.createStatement();
        stmt.executeUpdate(s);

    }
    public static boolean getTeacherOpenCourse(String id) throws SQLException, ClassNotFoundException {
        //检测教师是否已经开课，若已开课返回true，未开课返回false
        Connection conn = CollectMySQL();
        String s="select * from Course where teacherId='"+id+"'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        if(rs.next()){
            return true;
        }
        return false;
    }

    public static String teacherCourse(String id) throws SQLException, ClassNotFoundException {//返回教师所开设课程
        Connection conn = CollectMySQL();
        String sql="select * from Course where teacherId='"+id+"'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
            System.out.println("开课");
            return rs.getString("claname");

        }
        return "你还未开课";

    }
    public static String getTeacherId(String courseName) throws SQLException, ClassNotFoundException {
        Connection conn = CollectMySQL();
        Statement stmt = conn.createStatement();
        String sql="select * from Course where claname='"+courseName+"'";
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
            System.out.println("返回课程");
            return rs.getString("teacherId");

        }
        return "";//因已经检测过课程名称有效性，不需要返回错误值

    }
    public static String inquirePassword(String name,String id,String identify) throws SQLException, ClassNotFoundException {//查询学生密码
        Connection conn = CollectMySQL();
        Statement stmt = conn.createStatement();
        String sql;
        if(identify.equals("学生")){
            sql="select * from Student where id='"+id+"'";
        }
        else{
            sql="select * from Teacher where id='"+id+"'";
        }
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
            String stringName = rs.getString("name");
            if(stringName.equals(name)){
                return "用户密码："+rs.getString("password");
            }
            else{
                return "账号或姓名有误";
            }
        }
        return "无该账号";
    }

}
