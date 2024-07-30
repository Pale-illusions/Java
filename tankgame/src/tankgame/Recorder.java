package tankgame;

import java.io.*;
import java.util.Vector;

// 记录相关信息，与文件交互
public class Recorder {
    private static int killNumber = 0;
    private static final String filePath = "src\\myRecord.dat";
    private static Vector<Enemy> enemyTanks = new Vector<>();
    public static Hero hero;

    public static void setEnemyTanks(Vector<Enemy> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static void record() throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeInt(killNumber);
        out.writeInt(enemyTanks.size());
        out.writeObject(hero);
        for (int i = 0; i < enemyTanks.size(); i++) {
            Enemy enemy = enemyTanks.get(i);
            if (enemy.isLive()) {
                out.writeObject(enemy);
            }
        }
        out.flush();
        out.close();
    }

    public static Hero getRecord() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(filePath));
            killNumber = in.readInt();
            int n = in.readInt();
            hero = (Hero) in.readObject();
            for (int i = 0; i < hero.bullets.size(); i++) {
                new Thread(hero.bullets.get(i)).start();
            }
            for (int i = 0; i < n; i++) {
                Enemy enemy = (Enemy) in.readObject();
                enemyTanks.add(enemy);
                new Thread(enemy).start();
                for (int j = 0; j < enemy.bullets.size(); j++) {
                    new Thread(enemy.bullets.get(j)).start();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return hero;
    }

    public static int getKillNumber() {
        return killNumber;
    }

    public static void setKillNumber(int killNumber) {
        Recorder.killNumber = killNumber;
    }

    public static void incrementKill() {
        killNumber++;
    }
}
