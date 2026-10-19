package spaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static sound.SoundManager.playSound;

public class SpaceInvadersLogic {

    private final int GRID_SIZE = 16;
    private final int BOARD_WIDTH = 800;
    private final int PLAYER_SPEED = 16;

    private int playerXpos = 16*23;
    private final int playerYpos = 16*25;

    private final List<Point> enemiesType0;
    private final List<Point> enemiesType1;
    private boolean isGameOver = false;
    private boolean isEnemiesMovingReverse = false;

    private int moveWaitTime = 0;
    private boolean isMovingRight = false, isMovingLeft = false, isShooting = false;

    SpaceInvadersEnemies enemyManager = new SpaceInvadersEnemies();
    SpaceInvadersLasers laserManager = new SpaceInvadersLasers();
    SpaceInvadersWalls wallManager = new SpaceInvadersWalls();


    public int score = 0;

    public int tickCounter = 0;

    public SpaceInvadersLogic(){
        this.enemiesType0 = enemyManager.getEnemiesType0();
        this.enemiesType1 = enemyManager.getEnemiesType1();
    }

    public int getPlayerXpos(){ return playerXpos; }
    public int getPlayerYpos(){ return playerYpos; }

    public List<Point> getEnemiesType0(){ return new ArrayList<>(enemiesType0); }
    public List<Point> getEnemiesType1(){ return new ArrayList<>(enemiesType1); }

    public List<Point> getEnemyLasers(){ return laserManager.getEnemyLasers(); }
    public List<Point> getLasers(){ return laserManager.getLasers(); }
    public List<Point> getWalls(){ return wallManager.getWalls(); }

    public void setMovingRight(){ isMovingRight = true; }
    public void setMovingLeft(){ isMovingLeft = true; }

    public void stopMovingRight(){ isMovingRight = false; }
    public void stopMovingLeft(){ isMovingLeft = false; }

    public void setShootFire(){ isShooting = true; }
    public void stopShootFire(){ isShooting = false; }

    public boolean getIsGameOver(){ return isGameOver; }

    public void reset(){
        enemyManager.killAllEnemies();
        laserManager.reset();
        wallManager.reset();

        tickCounter = 0;
        score = 0;
        moveWaitTime = 0;

        playerXpos = 16*23;
        enemyManager.spawnEnemies();
        isGameOver = false;
    }

    public void tick(){
        tickCounter++;
        moveWaitTime++;

        if(isMovingRight && playerXpos + PLAYER_SPEED < BOARD_WIDTH) { playerXpos += PLAYER_SPEED; }
        if(isMovingLeft && playerXpos - PLAYER_SPEED >= 0) { playerXpos -= PLAYER_SPEED; }

        laserManager.tryShoot(playerXpos, playerYpos, isShooting);
        laserManager.updatePositions();

        wallManager.checkWallCollisions(laserManager);

        for(int i = 0; i < enemyManager.getEnemiesType0().size(); i++){
            if(enemiesType0.get(i).x >= BOARD_WIDTH - GRID_SIZE){
                isEnemiesMovingReverse = true;
                break;
            } else if (enemiesType0.get(i).x <= 0) {
                isEnemiesMovingReverse = false;
                break;
            }
        }

        for(int i = 0; i < enemyManager.getEnemiesType1().size(); i++){
            if(enemiesType1.get(i).x >= BOARD_WIDTH - GRID_SIZE){
                isEnemiesMovingReverse = true;
                break;
            } else if (enemiesType1.get(i).x <= 0) {
                isEnemiesMovingReverse = false;
                break;
            }
        }

        if (moveWaitTime >= 10) {
            if (isEnemiesMovingReverse) {
                enemyManager.moveEnemiesLeft();
            } else {
                enemyManager.moveEnemiesRight();
            }
            playSound("/pop.wav");
            moveWaitTime = 0;
        }
        int destroyed = laserManager.checkEnemyCollisions(enemyManager);
        score += destroyed;

        laserManager.spawnEnemyLaser(enemyManager.getEnemiesType0());
        laserManager.spawnEnemyLaser(enemyManager.getEnemiesType1());

        Point playerPosTemp = new Point(playerXpos, playerYpos);
        for(Point enemyLasers : laserManager.getEnemyLasers()){
            if(enemyLasers.equals(playerPosTemp)){
                isGameOver = true;
                break;
            }
        }

        if(enemiesType0.isEmpty() && enemiesType1.isEmpty()){
            isGameOver = true;
        }
    }
}
