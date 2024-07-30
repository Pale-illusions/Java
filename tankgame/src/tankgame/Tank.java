package tankgame;


import java.io.Serializable;
import java.util.Vector;

public class Tank implements Serializable {
    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标
    private int direct = 1;//坦克方向 0 上1 右 2下 3左
    private int speed = 5;
    private boolean isLive = true;


    Vector<Bullet> bullets = new Vector<>();


    public boolean isLive() {
        return isLive;
    }

    public void die() {
        this.isLive = false;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //上右下左移动方法
    public void moveUp() {
        if (y - speed > 0) {
            y -= speed;
        }
    }
    public void moveRight() {
        if (x + 80 < 1000) {
            x += speed;
        }
    }
    public void moveDown() {
        if (y + 100 < 750) {
            y += speed;
        }
    }
    public void moveLeft() {
        if (x - speed > 0) {
            x -= speed;
        }
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tank() {}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
