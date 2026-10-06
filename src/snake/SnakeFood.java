package snake;

import java.awt.*;
import java.util.Random;
import java.util.List;

//🍏
public class SnakeFood {

    //rewrite food to support multiple food points at the same time

    private Random random = new Random();
    private int xPos, yPos;
    private final int gridSize = 16;

    public SnakeFood(){

    }

    public int getxPos() { return this.xPos; }
    public int getyPos() { return this.yPos; }

//    public Point getPosition(){
//        return new Point(this.xPos, this.yPos);
//    }

    public void resetFood(int width, int height, int padding, List<Point> snakeBody){
        int playableWidth = width - (padding * 2);
        int playableHeight = height - (padding * 2);

        int maxCellsX = playableWidth / gridSize;
        int maxCellsY = playableHeight / gridSize;


        Point temp;
        do{
            this.xPos = padding + (random.nextInt(maxCellsX) * gridSize);
            this.yPos = padding + (random.nextInt(maxCellsY) * gridSize);

            temp = new Point(xPos, yPos);
        } while(snakeBody.contains(temp));
        System.out.println("Food generated successfully at:  \nx: " + this.xPos + "\ny: " + this.yPos);
    }
}
