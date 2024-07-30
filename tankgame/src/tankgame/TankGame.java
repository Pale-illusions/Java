package tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;


public class TankGame extends JFrame {

    //定义MyPanel
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame hspTankGame01 = new TankGame();
    }

    public TankGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入选择 1: 新游戏 2: 继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        //将mp 放入到Thread ,并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板(就是游戏的绘图区域)

        this.setSize(1300, 750);
        this.addKeyListener(mp);//让JFrame 监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.record();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
    }
}
