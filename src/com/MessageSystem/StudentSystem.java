package com.MessageSystem;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentSystem {
    public static void main(String[] args) {
    }

    public static Boolean isNumeric(String inString){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(inString);

        if(!isNum.matches()){
            return false;
        }

        return true;
    }

}
