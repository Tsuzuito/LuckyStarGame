package snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

//🍏
public class SnakeFood {

    private final Random random = new Random();
    private final List<Point> food = new ArrayList<>();

    public SnakeFood(){

    }

    public List<Point> getFoodPosition(){ return food; }

    public void resetFood(){ food.clear(); }

    public void spawnFood(int targetCount ,int width, int height, int padding, int gridSize, List<Point> snakeBody, List<Point> walls){

        int playableWidth = width - (padding * 2);
        int playableHeight = height - (padding * 2);
        int maxCellsX = playableWidth / gridSize;
        int maxCellsY = playableHeight / gridSize;

        Point newFoodPoint;

        while (food.size() < targetCount){
            do {
                int xPos = padding + (random.nextInt(maxCellsX) * gridSize);
                int yPos = padding + (random.nextInt(maxCellsY) * gridSize);
                newFoodPoint = new Point(xPos, yPos);

                //Check if the point on the snake OR is already in the food list -> repeat the loop
            } while (snakeBody.contains(newFoodPoint) || food.contains(newFoodPoint) || walls.contains(newFoodPoint));

            //if an available spot we add a dot to the list
            food.add(newFoodPoint);

            System.out.println("Food generated successfully at: \nx: " + newFoodPoint.x + "\ny: " + newFoodPoint.y);
        }
    }
}
