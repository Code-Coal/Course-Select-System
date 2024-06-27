package About;

import javax.swing.*;

public class About extends JFrame {
    public About(){
        System.out.println("进入ABOUT里");
        InitFace();
        InitInage();
        setVisible(true);
    }

    private void InitInage() {
        //清空图片
        getContentPane().removeAll();
        JLabel jLabelImg = new JLabel(new ImageIcon("image\\扫雷\\logo.png"));
        jLabelImg.setBounds(25,30,350,74);
        getContentPane().add(jLabelImg);
        //添加文字
        JLabel jlwechat1 = new JLabel("狗熊岭设计局® 设计");
        jlwechat1.setBounds(50,165,200,20);
        getContentPane().add(jlwechat1);

        JLabel jlwechat2 = new JLabel("版本：1.2.0");
        jlwechat2.setBounds(50,195,200,20);
        getContentPane().add(jlwechat2);

        JLabel jlwechat3 = new JLabel("更新时间：2024-6-22");
        jlwechat3.setBounds(50,225,200,20);
        getContentPane().add(jlwechat3);
        //刷新
        getContentPane().repaint();

    }

    private void InitFace() {
        setSize(400,400);//设置界面大小
        setTitle("关于");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setLayout(null);
    }
}
