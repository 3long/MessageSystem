package com.MessageSystem;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class connectDB {
    private static final String USER = "messageSystemAdmin";
    private static final String PASSWORD = "123456";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/studentmessage?serverTimezone=UTC";
    public static  Map<String, String> info = new HashMap<String, String>();

    static {
        info.put("ID", "id");
        info.put("姓名", "name");
        info.put("年龄", "age");
        info.put("性别", "sex");
        info.put("住址", "address");
    }


    public static void main(String[] args) {
        String[] data = {"1", "username", "123", "password", "address"};

        try {
            // 更新测试
//           Map<String, String> inData = new HashMap<String, String>();
//           int id = 2;
//           inData.put("name", "冬兵");
//
//           alterData(inData, id);
//
//           List<String[]> result = null;
//           result =  getData();
//            assert result != null;
//            String[][] buf =new String[result.size()][5];
//            System.out.println(Arrays.toString(result.toArray(buf)));


            // 查询测试
//            Map<String, String> inData = new HashMap<String, String>();
//            inData.put("id", "[0-2]");
//            List<String[]> result = selectData(inData);
//            Object[][] buf = new Object[result.size()][5];
//            result.toArray(buf);
//            for (Object[] b1 : buf){
//                for (Object b2 : b1){
//                    System.out.println(b2);
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

    // 插入数据
    public static void insertData(String[] data) throws SQLException, ClassNotFoundException {
        Connection con = getConnect();

        String sql = "INSERT INTO student (ID, NAME, AGE, SEX, ADDRESS) VALUE (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, new Integer(data[0]));
        ps.setString(2, data[1]);
        ps.setInt(3, new Integer(data[2]));
        ps.setString(4, data[3]);
        ps.setString(5, data[4]);

        boolean rs = ps.execute();
        con.close();
    }

    // 删除数据
    public static void deleteData(String[] data) throws SQLException, ClassNotFoundException {
        Connection con = getConnect();

        String sql = "DELETE FROM STUDENT WHERE ID=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, new Integer(data[0]));

        boolean rs = ps.execute();
        con.close();
    }

    //修改数据
    public static void alterData(Map<String, String> data, int id) throws SQLException, ClassNotFoundException {
        Connection con = getConnect();
        String key = (String) data.keySet().toArray()[0];
        String value = (String) data.values().toArray()[0];
//        for (String v : data.values()){
//            value = v;
//        }

        String sql = "UPDATE student SET " + key +  "=? WHERE ID=?;";

        PreparedStatement ps = con.prepareStatement(sql);

        assert key != null;
        if (key.equals("age")){
            ps.setInt(1, new Integer(value));
        }else {
            ps.setString(1, value);
        }
        ps.setInt(2, id);

        boolean rs = ps.execute();
        con.close();
    }

    // 查询数据
    public static  List<String[]> selectData(Map<String, String> inData) throws SQLException, ClassNotFoundException {
        Connection con = getConnect();

        String k = (String) inData.keySet().toArray()[0];
//        System.out.println(k);
        String v = (String) inData.values().toArray()[0];

        String sql = "SELECT * FROM STUDENT WHERE " + connectDB.info.get(k) +" REGEXP ?;";


        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, v);

        ResultSet rs = ps.executeQuery();
        List<String[]> resultList = new ArrayList<String[]>();
        while (rs.next()){
            int ID =  rs.getInt("ID");
            String name  = rs.getString("name");
            int age = rs.getInt("age");
            String sex = rs.getString("sex");
            String address = rs.getString("address");
            String[] buf = {String.valueOf(ID), name, String.valueOf(age), sex, address};
            resultList.add(buf);
        }

        con.close();
        return resultList;
    }

    // 验证用户名和密码是否错误
    static Boolean verification(String username, String password) throws Exception {
        Connection con = getConnect();

        String sql = "SELECT * FROM user ";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet result = ps.executeQuery();
//        con.close();

        List<String> nameList = new ArrayList<String>();
        List<String> passwordList = new ArrayList<String>();
        while (result.next()) {
            String us = result.getString("name");
            String pw = result.getString("password");
            nameList.add(us);
            passwordList.add(pw);
        }
        con.close();
        if (nameList.contains(username)) {
            if (passwordList.contains(password)) {
//                    System.out.println("用户存在");
                return true;
            } else {
                throw new Exception("用户密码错误");
            }
        } else {
            throw new Exception("用户名不存在");
        }
    }


    // 从数据库里获得数据
    public static List<String[]> getData(){
        try {
            Connection con = getConnect();

            String sql = "SELECT * FROM STUDENT;";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs  = ps.executeQuery();

            List<String[]> resultList = new ArrayList<String[]>();
            while (rs.next()){
                int ID =  rs.getInt("ID");
                String name  = rs.getString("name");
                int age = rs.getInt("age");
                String sex = rs.getString("sex");
                String address = rs.getString("address");
                String[] buf = {String.valueOf(ID), name, String.valueOf(age), sex, address};
                resultList.add(buf);
            }
            //关闭连接
            con.close();
            return resultList;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


}
