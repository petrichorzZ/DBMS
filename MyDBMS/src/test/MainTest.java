package test;

import SQL.Util;
import myDatabase.User;
import myDatabase.UserManager;

import java.util.Scanner;

public class MainTest {
    //public static final String filePath = "C:/Users/Administrator/Desktop/数据库数据.txt";
    //public static UserManager userManager  = new UserManager();
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String string= scanner.nextLine();
            if(string.equalsIgnoreCase("exit")){
                break;
            }
            Util.ParseSQL(string);
        }
     //   Util.Serial();
    }
}
