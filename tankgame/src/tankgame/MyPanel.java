package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;


//为了监听 键盘事件， 实现KeyListener
//为了让Panel 不停的重绘子弹，需要将 MyPanel 实现Runnable ,当做一个线程使用
public class MyPanel extends JPanel implements KeyListener,Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克，放入到Vector
    Vector<Enemy> enemyTanks = new Vector<>();
    int enemyTankSize = 7;
    // 当子弹击中坦克时，加入一个bomb对象
    Vector<Bomb> bombs = new Vector<>();

    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String key) {
        hero = new Hero(500, 500); //初始化自己坦克
        Recorder.setEnemyTanks(enemyTanks);

        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_3.gif"));

        // 播放音乐
        new AePlayWave("src\\music.wav").start();

        switch (key) {
            case "1":
                //初始化敌人坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    //创建一个敌人的坦克
                    Enemy enemy = new Enemy((100 * (i + 1)), 0);
                    //设置方向
                    enemy.setDirect(3);
                    enemy.setEnemies(enemyTanks);
                    //启动敌人坦克线程，让他动起来
                    new Thread(enemy).start();
                    //加入
                    enemyTanks.add(enemy);
                }
                break;
            case "2":
                hero = Recorder.getRecord();
                break;
            default:
                System.out.println("输入错误！");
        }
        Recorder.hero = hero;
    }

    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累积击毁敌方坦克：", 1020, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getKillNumber() + "", 1080, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色

        showInfo(g);

        //画出自己坦克-封装方法
        if (hero.isLive()) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

        //画出hero射击的子弹
        drawBullet(hero, g);

        //画出敌人的坦克, 遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出坦克
            Enemy enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                drawBullet(enemyTank, g);
            } else {
                // 如果坦克已死亡，消除并增加击杀数
                enemyTanks.remove(enemyTank);
                Recorder.incrementKill();
            }
        }

        // 画出炸弹
        drawBomb(g);
    }

    // 画出炸弹
    public void drawBomb(Graphics g) {
        if (!bombs.isEmpty()) {
            for (int i = 0; i < bombs.size(); i++) {
                Bomb bomb = bombs.get(i);
                if (!bomb.isLive()) {
                    bombs.remove(bomb);
                    continue;
                }
                if (bomb.life > 12) {
                    g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
                } else if (bomb.life > 6) {
                    g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
                } else if (bomb.life > 0){
                    g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
                }
                bomb.lifeDown();
            }
        }
    }

    // 画出子弹
    public void drawBullet(Tank tank, Graphics g) {
        for (int i = 0; i < tank.bullets.size(); i++) {
            Bullet bullet = tank.bullets.get(i);
            if (bullet != null && bullet.isLive()) {
                g.draw3DRect(bullet.x, bullet.y, 1, 1, false);
            } else {
                tank.bullets.remove(bullet);
            }
        }
    }

    //编写方法，画出坦克
    /**
     * @param x      坦克的左上角x坐标
     * @param y      坦克的左上角y坐标
     * @param g      画笔
     * @param direct 坦克方向（上下左右）
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //根据不同类型坦克，设置不同颜色
        switch (type) {
            case 0: //敌人的坦克
                g.setColor(Color.cyan);
                break;
            case 1: //我的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向，来绘制对应形状坦克
        //direct 表示方向(0: 向上 1 向右 2 向下 3 向左 )
        //
        switch (direct) {
            case 0: //表示向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮筒
                break;
            case 1: //表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出炮筒
                break;
            case 2: //表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮筒
                break;
            case 3: //表示向左
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//画出炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wdsa 键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W键
            //改变坦克的方向
            hero.setDirect(0);//
            //修改坦克的坐标 y -= 1
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//D键, 向右
            hero.setDirect(1);
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//S键
            hero.setDirect(2);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//A键
            hero.setDirect(3);
            hero.moveLeft();
        }

        //如果用户按下的是J,就发射
        if(e.getKeyCode() == KeyEvent.VK_J) {
//            System.out.println("用户按下了J, 开始射击.");
            hero.shot();
        }
        //让面板重绘
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() { //每隔 100毫秒，重绘区域, 刷新绘图区域, 子弹就移动

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < enemyTanks.size(); i++) {
                Enemy enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive() && hero.isLive()) {
                    attack(enemyTank, hero);
                    attack(hero, enemyTank);
                }
            }

            this.repaint();
        }
    }


    public void attack(Tank tank, Tank enemy) {
        for (int i = 0; i < tank.bullets.size(); i++) {
            Bullet bullet = tank.bullets.get(i);
            if (bullet != null && bullet.isLive()) {
                hitTack(bullet, enemy);
            } else {
                tank.bullets.remove(bullet);
            }
        }
    }

    public void hitTack(Bullet b, Tank enemy) {
        switch (enemy.getDirect()) {
            case 0:
            case 2:
                if (b.x > enemy.getX() && b.x < enemy.getX() + 40
                    && b.y > enemy.getY() && b.y < enemy.getY() + 60) {
                    b.die();
                    enemy.die();
                    bombs.add(new Bomb(enemy.getX(), enemy.getY()));
                }
                break;
            case 1:
            case 3:
                if (b.x > enemy.getX() && b.x < enemy.getX() + 60
                        && b.y > enemy.getY() && b.y < enemy.getY() + 40) {
                    b.die();
                    enemy.die();
                    bombs.add(new Bomb(enemy.getX(), enemy.getY()));
                }
                break;
        }
    }
}
