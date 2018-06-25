package test;

import SQL.LoginFrame;
import SQL.Util;
import myDatabase.UserManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class FrameTest {
    public static UserManager userManager = new UserManager();
    public static String cacheOutput;
    public static LoginFrame loginFrame;
    public static void main(String[] args){
        Util.Read();
        loginFrame = new LoginFrame();
    }
}
