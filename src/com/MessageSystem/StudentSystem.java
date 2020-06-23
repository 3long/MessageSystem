package com.MessageSystem;

import java.sql.SQLException;
import java.util.Scanner;

public class StudentSystem {
    public static void main(String[] args) {
       StudentSystem sdsm = new StudentSystem();

       sdsm.addData();
    }

    public void deleteData(){
        Scanner sc = new Scanner(System.in);

        System.out.print("请输入ID：");
        String id = sc.next();


    }

    private void addData(){
        Scanner sc = new Scanner(System.in);

        System.out.print("请输入ID：");
        String id = sc.next();

        System.out.print("请输入用户名：");
        String username = sc.next();

        System.out.print("请输入密码：");
        String password = sc.next();

        System.out.print("请输入年龄：");
        String age = sc.next();

        System.out.print("请输入地址：");
        String address = sc.next();

        String[] data = {id, username, age, password, address};

        try {
            connectDB.insertData(data);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
