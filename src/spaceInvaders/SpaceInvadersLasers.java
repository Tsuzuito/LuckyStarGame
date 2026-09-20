package spaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SpaceInvadersLasers {

    private final List<Point> lasers = new ArrayList<>();
    private final List<Point> enemyLasers = new ArrayList<>();

    private final int GRID_SIZE = 16;
    private final int SHOOT_COOLDOWN = 3;
    private int timeSinceLastShot = 0;
    private Random random = new Random();

    public SpaceInvadersLasers(){

    }

    public List<Point> getLasers(){
        return new ArrayList<>(lasers);
    }

    public List<Point> getEnemyLasers(){
        return new ArrayList<>(enemyLasers);
    }

    public void removeLaser(Point laser) {
        lasers.remove(laser);
    }

    public void removeEnemyLaser(Point laser) {
        enemyLasers.remove(laser);
    }

    public void tryShoot(int playerXpos, int playerYpos, boolean isShooting){
        timeSinceLastShot++;
        if(isShooting && timeSinceLastShot >= SHOOT_COOLDOWN){
            lasers.add(new Point(playerXpos, playerYpos));
            timeSinceLastShot = 0;
        }
    }

    public void updatePositions(){
        for(Point laser : lasers){
            laser.y -= GRID_SIZE;
        }
        lasers.removeIf(laser -> laser.y < 0);

        for(Point enemyLaser: enemyLasers){
            enemyLaser.y +=  GRID_SIZE;
        }
        enemyLasers.removeIf(enemyLasers -> enemyLasers.y > 600);
    }

    public int checkEnemyCollisions(SpaceInvadersEnemies enemyManager){
        int destroyedCount = 0;
        List<Point> enemies = enemyManager.getEnemies();

        //enemies collision check
        //Creates iterator object for laser list. Allow remove elements while loop
        Iterator<Point> laserIterator = lasers.iterator();
        while(laserIterator.hasNext()){
            //use next laser (shift to right)
            Point laser = laserIterator.next();

            for(int i = 0; i < enemies.size(); i++){
                Point enemy = enemies.get(i);

                if(laser.equals(enemy)){
                    enemyManager.killEnemy(i);
                    laserIterator.remove();
                    destroyedCount++;
                    break;
                }
            }
        }

        return destroyedCount;
    }

    public void spawnEnemyLaser(List<Point> enemies){

        if(random.nextInt(10) == 1){
            enemyLasers.add(new Point(enemies.get(random.nextInt(enemies.size() ) ) ) );
        }
    }

    public void reset(){
        lasers.clear();
        enemyLasers.clear();
        timeSinceLastShot = 0;
    }
}
