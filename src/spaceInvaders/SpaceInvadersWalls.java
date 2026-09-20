package spaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpaceInvadersWalls {

    //key, item
    private final Map<Point, Integer> wallHealthMap = new HashMap<>();

    public SpaceInvadersWalls(){

    }

    public List<Point> getWalls() {
        return new ArrayList<>(wallHealthMap.keySet());
    }

    public void checkWallCollisions(SpaceInvadersLasers laserManager){
        List<Point> lasers = laserManager.getLasers();
        List<Point> lasersEnemy = laserManager.getEnemyLasers();

        for(Point laser : lasers){
            if(wallHealthMap.containsKey(laser)){

                int currentHealth = wallHealthMap.get(laser) - 1;

                if(currentHealth <= 0){
                    wallHealthMap.remove(laser);
                } else {
                    wallHealthMap.put(laser, currentHealth);
                }
                laserManager.removeLaser(laser);
            }
        }

        for(Point laser : lasersEnemy){
            if(wallHealthMap.containsKey(laser)){
                int currentHealth = wallHealthMap.get(laser) - 1;

                if(currentHealth <= 0){
                    wallHealthMap.remove(laser);
                } else {
                    wallHealthMap.put(laser, currentHealth);
                }
                laserManager.removeEnemyLaser(laser);
            }
        }
    }

    public void reset(){
        wallHealthMap.clear();

        for(int row = 0; row < 2; row++){
            for(int colums = 0; colums < 3; colums++){
                wallHealthMap.put(new Point(16*(5+colums), 16*(21+row)), 4);
                wallHealthMap.put(new Point(16*(15+colums), 16*(21+row)), 4);
            }
        }

    }
}
