package com.MessageSystem;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUITest {

    public static void main(String[] args) {
//        new GUITest().new LoginPageClass().indexPage();
        new GUITest().new MainPageClass().mainPage();
    }

    // 登录页面类
    private class LoginPageClass extends JFrame {
        private void indexPage() {
            LoginPageClass w = new LoginPageClass();
            w.setTitle("信息管理系统");
            w.setUndecorated(false);
            w.setResizable(false);
            w.setSize(500, 500);
            w.setLocation(300, 300);

            // 菜单栏 & 选项 & 帮助
            JMenuBar jmb = new JMenuBar();
            JMenu fileMenuBar = new JMenu("选项");
            JMenu helpMenuBar = new JMenu("帮助");

            JMenuItem exitMenuItem = new JMenuItem("退出");
            JMenuItem aboutMenuItem = new JMenuItem("关于");


            exitMenuItem.addActionListener(exitAction);
            aboutMenuItem.addActionListener(aboutAction);

            helpMenuBar.add(aboutMenuItem);
            fileMenuBar.add(exitMenuItem);
            jmb.add(fileMenuBar);
            jmb.add(helpMenuBar);
            // 添加菜单
            w.setJMenuBar(jmb);

            JPanel P1 = new JPanel();

            // 系统名称
            JLabel systemname = new JLabel("信息管理系统");
            systemname.setFont(new Font("微软雅黑", Font.BOLD, 32));
            systemname.setBounds(w.getWidth() / 3, 10, 200, 50);


            // 用户名输入框
            JLabel usernameL = new JLabel("用户名：");
            usernameL.setBounds(w.getWidth() / 3 - 30, 60, 100, 50);

            JTextField usernameInput = new JTextField(20);
            usernameInput.setBounds(w.getWidth() / 3 + 50, 75, 150, 20);

            // 密码输入框
            JLabel passwordTip = new JLabel("密码：");
            passwordTip.setBounds(w.getWidth() / 3 - 30, 90, 100, 50);
            JPasswordField passwordInput = new JPasswordField(20);
            passwordInput.setBounds(w.getWidth() / 3 + 50, 105, 150, 20);

            // 提交
            JButton submit = new JButton("提交");
            submit.setBounds(w.getWidth() / 3 + 30, 200, 100, 30);
//            submit.addActionListener(submitAction);
            addActionListener(submit, usernameInput, passwordInput, w);

            P1.setLayout(null);
            P1.add(systemname);
            P1.add(usernameL);
            P1.add(usernameInput);
            P1.add(passwordTip);
            P1.add(passwordInput);
            P1.add(submit);

            w.add(P1);
            w.setVisible(true);
            w.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            w.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {
                    int i = JOptionPane.showConfirmDialog(null, "确定退出", "退出提醒",
                            JOptionPane.YES_NO_OPTION);
                    if (i == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }

                @Override
                public void windowClosed(WindowEvent e) {

                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });
        }

        // 增加登录事件
        private void addActionListener(JButton b1, JTextField nameInput, JPasswordField passwordInput, LoginPageClass w) {
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i = JOptionPane.showConfirmDialog(null, "确定提交提示？", "确定提交",
                            JOptionPane.YES_NO_OPTION);
                    if (i == JOptionPane.YES_OPTION) {
                        try {
                            Boolean loginBool = connectDB.verification(nameInput.getText(), passwordInput.getText());

                            if (loginBool) {
                                JOptionPane.showMessageDialog(null, "登录成功", "成功提示",
                                        JOptionPane.PLAIN_MESSAGE);
                                nameInput.setText("");
                                passwordInput.setText("");
//                                Thread.sleep(10000);
                                w.setVisible(false);
                                new MainPageClass().mainPage();
                            }
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }

        private ActionListener exitAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "你要退出吗？", "推出提示",
                        JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    System.exit(i);
                }
            }
        };
        private ActionListener aboutAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "开发于2020/6/23, bug联系作者", "关于",
                        JOptionPane.PLAIN_MESSAGE);
            }
        };
    }

    // 主页面类
    private class MainPageClass extends JFrame {

        //刷新页面
        private void flushPage(JTable dataShow) {
            try {
                List<String[]> studentMessageBuf = connectDB.getData();
                assert studentMessageBuf != null;

                Object[][] data = new Object[studentMessageBuf.size()][5];

                studentMessageBuf.toArray(data);

                dataShow.setModel(new DefaultTableModel(data, new String[]{"ID", "姓名", "年龄", "性别", "住址"}));
                dataShow.validate();
                dataShow.updateUI();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "刷新页面失败，请联系数据库管理员!!!\n" +
                        e.getMessage(), "刷新失败提醒", JOptionPane.ERROR_MESSAGE);
            }

        }

        private void flushPage(Object[][] data, JTable dataShow) {
            try {
                dataShow.setModel(new DefaultTableModel(data, new String[]{"ID", "姓名", "年龄", "性别", "住址"}));
                dataShow.validate();
                dataShow.updateUI();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "刷新页面失败，请联系数据库管理员!!!\n" +
                        e.getMessage(), "刷新失败提醒", JOptionPane.ERROR_MESSAGE);
            }

        }

        private void addDataPage() {
            JTextField username = new JTextField();

            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 999999999, 1);
            JSpinner ID = new JSpinner(model);

            JSpinner age = new JSpinner();

            JComboBox<String> cmb = new JComboBox<>();
//            cmb.addItem("--请选择--");
            cmb.addItem("男");
            cmb.addItem("女");

            JTextField address = new JTextField();

            Object[] message = {

                    "ID:", ID,

                    "用户名:", username,

                    "年龄:", age,

                    "性别:", cmb,

                    "住址:", address
            };

            int option = JOptionPane.showConfirmDialog(null, message, "请输入添加的信息", JOptionPane.OK_CANCEL_OPTION);

//            System.out.println(option);
            try {
                if (StudentSystem.isNumeric(ID.getNextValue().toString()) && StudentSystem.isNumeric(age.getNextValue().toString())) {
                    if (option > -1) {
                        int id = (int) ID.getNextValue() - 1;
                        int age_buf = (int) age.getNextValue() - 1;

                        String[] inDataBuf = new String[]{String.valueOf(id), username.getText(), String.valueOf(age_buf),
                                (String) cmb.getSelectedItem(), address.getText()};

                        try {
                            connectDB.insertData(inDataBuf);
                            JOptionPane.showConfirmDialog(null, "输入成功", "成功提示", JOptionPane.DEFAULT_OPTION);
                        } catch (Exception e) {
//                            e.printStackTrace();
                            String repot;
                            if (e.getMessage().contains("Duplicate entry")) {
                                repot = "ID: " + id + " 已存在";
                            } else {
                                repot = e.getMessage();
                            }
                            JOptionPane.showMessageDialog(null, repot, "错误提示", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "id或年龄格式不正确", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
            }


        }

        private void deleteData() {
            JTextField username = new JTextField();

            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 999999999, 1);
            JSpinner ID = new JSpinner(model);


            Object[] message = {
                    "请输入你要删除的ID:", ID
            };

            int option = JOptionPane.showConfirmDialog(null, message, "请输入要删除的信息", JOptionPane.DEFAULT_OPTION);
            if (option > -1) {
                String real_id = String.valueOf((int) ID.getNextValue() - 1);
                try {
                    if (StudentSystem.isNumeric(real_id)) {
                        connectDB.deleteData(new String[]{real_id});
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // 更新数据
        private void alterData() {

            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 999999999, 1);
            JSpinner ID = new JSpinner(model);

            JComboBox<String> cmb = new JComboBox<String>();
            cmb.addItem("姓名");
            cmb.addItem("年龄");
            cmb.addItem("性别");
            cmb.addItem("住址");

            JTextField inPutText = new JTextField();

            Object[] message = {
                    "请输入你要修改的：ID:", ID,
                    "选择要修改的列：", cmb,
                    "新值：", inPutText
            };
            int option = JOptionPane.showConfirmDialog(null, message, "请输入要修改的信息",
                    JOptionPane.DEFAULT_OPTION);
            int id = (int) ID.getNextValue() - 1;

            if (option > -1) {
                try {
                    if (StudentSystem.isNumeric(String.valueOf(id))) {
                        Map<String, String> inData = new HashMap<String, String>();

                        inData.put(connectDB.info.get(cmb.getSelectedItem()), inPutText.getText());
                        connectDB.alterData(inData, id);

                        JOptionPane.showConfirmDialog(null, "数据修改成功", "数据修改提醒",
                                JOptionPane.DEFAULT_OPTION);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private List<String[]> selectData() {
            JComboBox<String> cmb = new JComboBox<String>();
            cmb.addItem("ID");
            cmb.addItem("姓名");
            cmb.addItem("年龄");
            cmb.addItem("性别");
            cmb.addItem("住址");

            JTextField inPutText = new JTextField();

            Object[] message = {
                    "选择要查询的字段：", cmb,
                    "正则表达式：", inPutText
            };
            int option = JOptionPane.showConfirmDialog(null, message, "请输入要查询的信息",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    String info = (String) cmb.getSelectedItem();
                    String regexp = inPutText.getText();

                    Map<String, String> inData = new HashMap<>();
                    inData.put(info, regexp);

                    return connectDB.selectData(inData);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            return null;
        }

        private void mainPage() {
            MainPageClass mp = new MainPageClass();
            mp.setTitle("信息管理系统");
            mp.setSize(800, 550);
            mp.setLocation(350, 350);
            mp.setUndecorated(false);
            mp.setResizable(false);

            // 主画板
            JPanel p1 = new JPanel();

            //状态栏
            JLabel statusText = new JLabel("OK");
            mp.add(statusText, BorderLayout.SOUTH);

            // 菜单栏

            JMenuBar menuBar = new JMenuBar();
            JMenu filemenu = new JMenu("选项");


            JMenuItem exitMenuItem = new JMenuItem("退出");
            exitMenuItem.addActionListener(exitAction);
            filemenu.add(exitMenuItem);
            menuBar.add(filemenu);
            mp.setJMenuBar(menuBar);

            /***********************************************************
             *
             * 列表
             *
             ************************************************************/
            // 列表
            String[] columnNames = {"ID", "姓名", "年龄", "性别", "住址"};

            // 从MySQL获得数据
            List<String[]> studentMessageBuf = connectDB.getData();

            Object[][] rowData = new Object[studentMessageBuf.size()][5];
            studentMessageBuf.toArray(rowData);


            JTable dataShow = new JTable(rowData, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            dataShow.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 16));
            dataShow.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
            dataShow.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
            dataShow.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

            dataShow.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
            dataShow.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
            dataShow.setGridColor(Color.GRAY);                     // 网格颜色

            JScrollPane scrollBar = new JScrollPane(dataShow);

            /**
             * 功能
             * */
            JPanel gnPanel = new JPanel();
            GridLayout gnb = new GridLayout(5, 1, 10, 10);
            gnPanel.setLayout(gnb);
            JButton addButton = new JButton("添加");
            setInsertAction(addButton, dataShow);
            JButton deleteButton = new JButton("删除");
            setDeleteAction(deleteButton, dataShow);
            JButton alterButton = new JButton("修改");
            setAlterAction(alterButton, dataShow);
            JButton selectButton = new JButton("查询");
            setSelectAction(selectButton, dataShow);
            JButton resetButton = new JButton("复位");
            setResetAction(resetButton, dataShow);

            gnPanel.add(addButton);
            gnPanel.add(deleteButton);
            gnPanel.add(alterButton);
            gnPanel.add(selectButton);
            gnPanel.add(resetButton);


            p1.add(gnPanel, BorderLayout.WEST);
            p1.add(scrollBar, BorderLayout.CENTER);
            mp.add(p1);
            mp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            mp.setVisible(true);
            mp.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {
                    int i = JOptionPane.showConfirmDialog(null, "确定退出吗？", "退出提示",
                            JOptionPane.YES_NO_OPTION);

                    if (i == JOptionPane.YES_OPTION) {
                        System.exit(i);
                    }
                }

                @Override
                public void windowClosed(WindowEvent e) {

                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });
        }


        //添加数据
        private void setInsertAction(JButton insertButton, JTable dataShow) {

            insertButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addDataPage();

                    flushPage(dataShow);
                }
            });
        }

        //删除数据
        private void setDeleteAction(JButton deleteButton, JTable dataShow) {
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteData();

                    flushPage(dataShow);
                }
            });
        }

        //修改数据
        private void setAlterAction(JButton alterButton, JTable dataShow) {
            alterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    alterData();

                    flushPage(dataShow);
                }
            });
        }

        // 查询数据
        private void setSelectAction(JButton selectButton, JTable dataShow) {
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        List<String[]> result = selectData();
                        Object[][] buf = new Object[result.size()][5];
                        result.toArray(buf);

                        flushPage(buf, dataShow);
                    }
                    catch (Exception e1){
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "查询错误提示",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });


        }

        //复位
        private void setResetAction(JButton resetButton, JTable dataShow){
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flushPage(dataShow);
                }
            });
        }


        //退出事件
        private ActionListener exitAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "确定退出吗？", "退出提示",
                        JOptionPane.YES_NO_OPTION);

                if (i == JOptionPane.YES_OPTION) {
                    System.exit(i);
                }
            }
        };
    }

}
