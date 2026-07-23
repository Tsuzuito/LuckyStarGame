package spaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersLogic {

    private final int gridSize = 16;
    private int playerXpos = 400, playerYpos = 400;
    private List<Point> lasers = new ArrayList<>();

    private boolean isMovingRight = false, isMovingLeft = false;

    public SpaceInvadersLogic(){

    }

    public int getPlayerXpos(){
        return playerXpos;
    }
    public int getPlayerYpos(){
        return playerYpos;
    }

    public List<Point> getLasers(){ return lasers; }

    public void setMovingRight(){ isMovingRight = true; }
    public void setMovingLeft(){ isMovingLeft = true; }

    public void stopMovingRight(){ isMovingRight = false; }
    public void stopMovingLeft(){ isMovingLeft = false; }

    public void shootFire(){
        System.out.println("pew");
        int shootPointX = this.playerXpos;
        int shootPointY = this.playerYpos;

        lasers.add(new Point(shootPointX, shootPointY));
    }

    public void tick(){
        if(isMovingRight){ playerXpos+=16; }
        if(isMovingLeft){ playerXpos-=16; }

        for(Point laser : lasers){
            laser.y -= gridSize;
        }
        lasers.removeIf(laser -> laser.y < 0);
    }
}
