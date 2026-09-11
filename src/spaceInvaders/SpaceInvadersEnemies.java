package spaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersEnemies {

    private List<Point> enemies = new ArrayList<>();

    public SpaceInvadersEnemies(){

    }

    public List<Point> getEnemies(){ return enemies; }

    public void moveEnemiesRight(){
        int moveSpeedRight = 16;

        for(Point enemy : enemies){
            enemy.x += moveSpeedRight;
        }
    }

    public void moveEnemiesLeft(){
        int moveSpeedRight = -16;

        for(Point enemy : enemies){
            enemy.x += moveSpeedRight;
        }
    }

    public void enemySpawner(int x, int y, int spacing, int count){
        int startX = 16 * x;
        int startY = 16 * y;
        int step = 16*spacing;

        for (int i = 0; i < count; i++) {
            enemies.add(new Point(startX + (i * step), startY));
        }
    }

    public void killEnemy(int a){ enemies.remove(a); }
    public void killAllEnemies(){ enemies.clear(); }

    public void spawnEnemies(){
        //x,y,spacing,count
        enemySpawner(7,7,2,10);
        enemySpawner(7,9,2,10);
    }
}
