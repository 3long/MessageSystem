package com.MessageSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUITest {
    public static void main(String[] args) {
        windows.test();
    }

    private static class windows extends JFrame{
        private static void test(){
            windows w = new windows();

            // 菜单栏
            JMenuBar jmb = new JMenuBar();
            JMenu fileMenuBar = new JMenu("文件（F）");
            JMenuItem exitMenuItem = new JMenuItem("退出");
            exitMenuItem.addActionListener(exitAction);
            fileMenuBar.add(exitMenuItem);
            jmb.add(fileMenuBar);



            // 添加组件
            w.setJMenuBar(jmb);

            w.setTitle("测试");
            w.setSize(500, 500);
            w.setLocation(300, 300);
            w.setVisible(true);
            w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        private static ActionListener exitAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
                JOptionPane.showMessageDialog(null, "你要退出吗？", "推出提示", JOptionPane.YES_NO_OPTION);

            }
        };
    }

}
