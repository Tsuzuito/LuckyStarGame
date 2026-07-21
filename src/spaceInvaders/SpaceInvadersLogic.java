package spaceInvaders;

import snake.SnakeLogic;


public class SpaceInvadersLogic {

    private final int gridSize = 16;
    private int playerXpos = 400, playerYpos = 400;

    private boolean isMovingRight = false;
    private boolean isMovingLeft = false;


    public SpaceInvadersLogic(){

    }

    public int getPlayerXpos(){
        return playerXpos;
    }
    public int getPlayerYpos(){
        return playerYpos;
    }

    public void setMovingRight(){ isMovingRight = true; }
    public void setMovingLeft(){ isMovingLeft = true; }

    public void stopMovingRight(){ isMovingRight = false; }
    public void stopMovingLeft(){ isMovingLeft = false; }




    public void tick(){

        if(isMovingRight == true){
            playerXpos+=16;
        }
        if(isMovingLeft == true){
            playerXpos-=16;
        }

    }
}
