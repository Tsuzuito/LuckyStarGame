package spaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersEnemies {

    private List<Point> enemies = new ArrayList<>();

    public SpaceInvadersEnemies(){
        enemies.add(new Point(16*7,16*7));
        enemies.add(new Point(16*9,16*7));
        enemies.add(new Point(16*11,16*7));
    }

    public List<Point> getEnemies(){ return enemies; }

    public void trytomove(){
        int move16 = 16;

        for(Point enemy : enemies){
            enemy.x += move16;
        }
    }

    public void trytokill(int a){
        enemies.remove(a);
    }

    public void spawnEnemies(){

    }
}
