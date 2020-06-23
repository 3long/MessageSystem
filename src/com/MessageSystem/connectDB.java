package com.MessageSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class connectDB {
    private static final String USER = "root";
    private static final String PASSWORD = "1024";
    private static final String URL = "jdbc:mysql://localhost:3306/studentmessage?serverTimezone=UTC";

    public static void main(String[] args) {
        String[] data = {"1", "username", "123", "password", "address"};

        try {
            insertData(data);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

    public static void insertData(String[] data) throws SQLException, ClassNotFoundException {
        Connection con = getConnect();

        String sql = "INSERT INTO USER (ID, NAME, AGE, PASSWORD, ADDRESS) VALUE (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, new Integer(data[0]));
        ps.setString(2, data[1]);
        ps.setInt(3, new Integer(data[2]));
        ps.setString(4, data[3]);
        ps.setString(5, data[4]);

        boolean rs = ps.execute();

        System.out.println("数据插入成功");

    }
}
