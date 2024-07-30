package tankgame;


public class Hero extends Tank {

    public Hero(int x, int y) {
        super(x, y);
    }

    public Hero() {}

    //射击
    public void shot() {
        if (!isLive() || bullets.size() == 5) return;
        Bullet bullet = null;
        //创建 Bullet 对象, 根据当前Hero对象的位置和方向来创建Bullet
        switch (getDirect()) {//得到Hero对象方向
            case 0: //向上
                bullet = new Bullet(getX() + 20, getY(), 0);
                break;
            case 1: //向右
                bullet = new Bullet(getX() + 60, getY() + 20, 1);
                break;
            case 2: //向下
                bullet = new Bullet(getX() + 20, getY() + 60, 2);
                break;
            case 3: //向左
                bullet = new Bullet(getX(), getY() + 20, 3);
                break;
        }
        bullets.add(bullet);
        //启动Bullet线程
        new Thread(bullet).start();
    }
}
