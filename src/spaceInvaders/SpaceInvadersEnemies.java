package spaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersEnemies {

    private final List<Point> enemiesType0 = new ArrayList<>();
    private final List<Point> enemiesType1 = new ArrayList<>();

    public SpaceInvadersEnemies(){

    }

    public List<Point> getEnemiesType0(){ return enemiesType0; }
    public List<Point> getEnemiesType1(){ return enemiesType1; }

    public void moveEnemiesRight(){
        int moveSpeedRight = 16;

        for(Point enemy : enemiesType0){
            enemy.x += moveSpeedRight;
        }
        for(Point enemy : enemiesType1){
            enemy.x += moveSpeedRight;
        }
    }

    public void moveEnemiesLeft(){
        int moveSpeedRight = -16;

        for(Point enemy : enemiesType0){
            enemy.x += moveSpeedRight;
        }
        for(Point enemy : enemiesType1){
            enemy.x += moveSpeedRight;
        }
    }

    public void enemySpawner(int x, int y, int spacing, int count, byte type){
        int startX = 16 * x;
        int startY = 16 * y;
        int step = 16*spacing;

        switch (type){
            case (0):{
                for (int i = 0; i < count; i++) {
                    enemiesType0.add(new Point(startX + (i * step), startY));
                }
                break;
            }
            case (1):{
                for (int i = 0; i < count; i++) {
                    enemiesType1.add(new Point(startX + (i * step), startY));
                }
                break;
            }
        }
    }

    public void killEnemyInType0(int a){ enemiesType0.remove(a); }
    public void killEnemyInType1(int a){ enemiesType1.remove(a); }
    public void killAllEnemies(){ enemiesType0.clear(); enemiesType1.clear(); }

    public void spawnEnemies(){
        //x,y,spacing,count,type
        enemySpawner(7,7,2,10,(byte)0);
        enemySpawner(7,9,2,10,(byte)1);
    }
}
