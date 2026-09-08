package spaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpaceInvadersLogic {

    private final int GRID_SIZE = 16;
    private final int BOARD_WIDTH = 800;
    private final int PLAYER_SPEED = 16;
    private final int SHOOT_COOLDOWN = 3;

    private int playerXpos = 400;
    private final int playerYpos = 400;

    private final List<Point> lasers = new  ArrayList<>();
    private final List<Point> enemies;
    private boolean isGameOver = false;

    public int score = 0;

    private int moveWaitTime = 0;

    SpaceInvadersEnemies enemyManager = new SpaceInvadersEnemies();

    private boolean isMovingRight = false, isMovingLeft = false, isShooting = false;

    private int timeSinceLastShot = 0;
    public int tickCounter = 0;

    public SpaceInvadersLogic(){
        this.enemies = enemyManager.getEnemies();
    }

    public int getPlayerXpos(){ return playerXpos; }
    public int getPlayerYpos(){ return playerYpos; }

    public List<Point> getLasers(){ return new ArrayList<>(lasers); }
    public List<Point> getEnemies(){ return new ArrayList<>(enemies); }

    public void setMovingRight(){ isMovingRight = true; }
    public void setMovingLeft(){ isMovingLeft = true; }

    public void stopMovingRight(){ isMovingRight = false; }
    public void stopMovingLeft(){ isMovingLeft = false; }

    public void setShootFire(){ isShooting = true; }
    public void stopShootFire(){ isShooting = false; }

    public boolean isGameOver(){ return isGameOver; }

    public void tick(){
        //debug
        System.out.println("\ntick: " + tickCounter);
        tickCounter++;
        moveWaitTime++;

        if(isMovingRight && playerXpos + PLAYER_SPEED < BOARD_WIDTH - GRID_SIZE) { playerXpos += PLAYER_SPEED; }
        if(isMovingLeft && playerXpos - PLAYER_SPEED >= 0) { playerXpos -= PLAYER_SPEED; }

        timeSinceLastShot++;
        if(isShooting){
            if(timeSinceLastShot >= SHOOT_COOLDOWN){
                lasers.add(new Point(playerXpos, playerYpos));
                timeSinceLastShot = 0;
            }
        }

        if(moveWaitTime >= 10){
            enemyManager.trytomove();
            moveWaitTime = 0;
        }

        for(Point laser : lasers){
            laser.y -= GRID_SIZE;
        }

        //enemies collision check
        //Creates iterator object for laser list. Allow remove elements while loop
        Iterator<Point> laserIterator = lasers.iterator();
        while(laserIterator.hasNext()){
            //use next laser (shift to right)
            Point laser = laserIterator.next();

            for(int i = 0; i < enemies.size(); i++){
                Point enemy = enemies.get(i);

                if(laser.equals(enemy)){
                    enemyManager.trytokill(i);
                    laserIterator.remove();
                    score++;
                    break;
                }
            }
        }

        lasers.removeIf(laser -> laser.y < 0);
    }
}
