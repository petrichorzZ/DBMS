package SQL;

import myDatabase.ConstantValues;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import static test.FrameTest.loginFrame;
import static test.FrameTest.userManager;
import static test.FrameTest.cacheOutput;
public class ExcuteMyDBMS extends Frame implements ActionListener {
    JTextArea textArea = null;
    JPanel panel = null;
    ScrollPane scrollPane = null;
    JButton submit = null;
    JButton clear = null;
    JButton serial = null;
    JButton read = null;
    public ExcuteMyDBMS(){
        super("MyDBMS");
        textArea = new JTextArea(cacheOutput);
        textArea.setFont(new Font("楷体",Font.PLAIN,20));
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = textArea.getText();
                if(text.endsWith(";\n")){
                    exect();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_C&&e.isControlDown()){
                    cacheOutput = "";
                    textArea.setText(cacheOutput);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        panel = new JPanel();
        scrollPane = new ScrollPane();
        scrollPane.add(textArea,BorderLayout.CENTER);
        submit = new JButton("执行");
        submit.addActionListener(this);
        clear = new JButton("清屏");
        clear.addActionListener(this);
//        serial = new JButton("serial");
//        serial.addActionListener(this);
//        read = new JButton("read");
//        read.addActionListener(this);
        panel.add(clear);
        panel.add(submit);
//        panel.add(serial);
//        panel.add(read);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(panel,BorderLayout.SOUTH);
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
        if(e.getSource()==submit){
            exect();
        }else if(e.getSource()==clear){
            cacheOutput = "";
            textArea.setText(cacheOutput);
        }else if(e.getSource()==serial){
            Util.Serial();
            textArea.setText(cacheOutput);
        }else if(e.getSource()==read){
            Util.Read();
            textArea.setText(cacheOutput);
        }
    }

    public void exect(){
        String string = Util.DeleteRepeatSpaces(textArea.getText());
        if(string.equalsIgnoreCase("exit")||string.equalsIgnoreCase("quit")){
            cacheOutput = "欢迎再次使用";
            System.out.println(cacheOutput);
            textArea.setText(cacheOutput);
            new LoginFrame();
            this.dispose();
        }else {
            Util.ParseSQL(string);
            textArea.setText(cacheOutput);
        }
    }
}