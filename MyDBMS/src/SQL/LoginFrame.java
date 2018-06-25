package SQL;

import com.sun.scenario.effect.Color4f;
import myDatabase.ConstantValues;
import myDatabase.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static test.FrameTest.cacheOutput;
import static test.FrameTest.userManager;

public class LoginFrame extends Frame implements ActionListener{
    JPanel p=null;
    JTextField t1=null;
    JPasswordField t2 =null;
    JButton login = null;
    JButton CommandMode =null;
    JButton ProgramMode = null;
    JTextField textField = null;
    Icon icon = null;
    JLabel label = null;
    boolean hasLogining =false;
    public LoginFrame(){
        super("MyDBMS");
        p = new JPanel();
        p.setLayout(null);
        label = new JLabel();
        icon = new ImageIcon(this.getClass().getResource("/ImageSource/background.jpg"));
      //  icon = new ImageIcon("src/ImageSource/background.jpg");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setIcon(icon);
        label.setSize(400,80);
        Label user = new Label("UserName: ");
        user.setLocation(10,150);
        user.setSize(100,25);
        user.setFont(new Font("微软雅黑",Font.BOLD,16));
        p.add(user);

        Label password = new Label("PassWord: ");
        password.setLocation(10,180);
        password.setSize(100,25);
        password.setFont(new Font("微软雅黑",Font.BOLD,16));
        p.add(password);

        t1 = new JTextField();
        t1.setSize(220,25);
        t1.setLocation(120,150);
        t1.setFont(new Font("微软雅黑",Font.BOLD,16));

        t2 = new JPasswordField();
        t2.setLocation(120,180);
        t2.setSize(220,25);
        t2.setFont(new Font("微软雅黑",Font.BOLD,16));

        login = new JButton("登陆");
        login.setFont(new Font("微软雅黑",Font.PLAIN,16));
        login.setLocation(140,280);
        login.setSize(100,25);
        login.addActionListener(this);
//        CommandMode = new Button("CommandMode");
//        CommandMode.setLocation(150,280);
//        CommandMode.setSize(100,25);
//        CommandMode.addActionListener(this);
//        ProgramMode = new Button("ProgramMode");
//        ProgramMode.setLocation(260,280);
//        ProgramMode.setSize(100,25);
//        ProgramMode.addActionListener(this);


        textField = new JTextField("显示登陆结果");
        textField.setHorizontalAlignment(JTextField.CENTER );
        textField.setLocation(10,230);
        textField.setSize(330,25);
        textField.setFont(new Font("微软雅黑",Font.BOLD,16));
        Color color = new Color(255,255,196);
        p.setBackground(color);
        p.add(t1);
        p.add(t2);
        p.add(login);
        p.add(textField);
//        p.add(CommandMode);
        p.add(label);
//        p.add(ProgramMode);
        this.add(p);
        this.setSize(ConstantValues.FrameWight,ConstantValues.FrameHigh);
        this.setLocation(100,100);
        this.setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==login){
            if(userManager.LoginCheck(t1.getText(),t2.getText())){
                hasLogining = true;
//              cacheOutput = "登陆成功";
                cacheOutput = "         欢迎使用MyDBMS";
//                cacheOutput+="\n   (每次输入SQL语句前先clear一下)";
                ExcuteMyDBMS newFrame = new ExcuteMyDBMS();
                this.dispose();
            }else {
                cacheOutput = "用户不存在或与密码不符";
            }
            textField.setText(cacheOutput);
        }
//        }else if(e.getSource()==CommandMode&&hasLogining){
//            cacheOutput = "         欢迎使用MyDBMS";
//            cacheOutput+="\n   (每次输入SQL语句前先clear一下)";
//            ExcuteMyDBMS newFrame = new ExcuteMyDBMS();
//            this.dispose();
//        }else if((e.getSource()==ProgramMode||e.getSource()==CommandMode)&&!hasLogining){
//            cacheOutput = "请先登录，才能使用系统";
//        }else if(e.getSource()==ProgramMode&&hasLogining){
//            Util.ProgramMode();
//            cacheOutput = "结果已经输出到当前目录的Result.txt";
//        }

    }
}
