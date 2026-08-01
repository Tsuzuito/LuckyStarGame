package spaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersLogic {

    private final int GRID_SIZE = 16;
    private final int BOARD_WIDTH = 800;
    private final int PLAYER_SPEED = 16;
    private final int SHOOT_COOLDOWN = 3;

    private int playerXpos = 400;
    private final int playerYpos = 400;

    private final List<Point> lasers = new  ArrayList<>();

    private boolean isMovingRight = false, isMovingLeft = false;

    private int timeSinceLastShot = SHOOT_COOLDOWN;
    public int tickCounter = 0;

    public SpaceInvadersLogic(){

    }

    public int getPlayerXpos(){ return playerXpos; }
    public int getPlayerYpos(){ return playerYpos; }

    public List<Point> getLasers(){ return new ArrayList<>(lasers); }

    public void setMovingRight(){ isMovingRight = true; }
    public void setMovingLeft(){ isMovingLeft = true; }

    public void stopMovingRight(){ isMovingRight = false; }
    public void stopMovingLeft(){ isMovingLeft = false; }

    public void shootFire(){
        if(timeSinceLastShot >= SHOOT_COOLDOWN){
            lasers.add(new Point(playerXpos, playerYpos));
            timeSinceLastShot = 0;
        }

    }

    public void tick(){
        tickCounter++;
        timeSinceLastShot++;

        if(isMovingRight && playerXpos + PLAYER_SPEED < BOARD_WIDTH - GRID_SIZE){ playerXpos += PLAYER_SPEED; }
        if(isMovingLeft && playerXpos - PLAYER_SPEED >= 0){ playerXpos -= PLAYER_SPEED; }

        for(Point laser : lasers){
            laser.y -= GRID_SIZE;
        }
        lasers.removeIf(laser -> laser.y < 0);
    }
}
