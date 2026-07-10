package snake;

import java.util.Random;

//🍏
public class SnakeFood {

    private Random random = new Random();
    private int xPos, yPos;
    private final int gridSize = 16;

    public SnakeFood(){
        xPos = 16 * 4;
        yPos = 16 * 4;
    }

    public int getxPos() { return this.xPos; }
    public int getyPos() { return this.yPos; }

    public void resetFood(int width, int height, int padding){
        int playableWidth = width - (padding * 4);
        int playableHeight = height - (padding * 4);

        int maxCellsX = playableWidth / gridSize;
        int maxCellsY = playableHeight / gridSize;

        xPos = padding + (random.nextInt(maxCellsX) * gridSize);
        yPos = padding + (random.nextInt(maxCellsY) * gridSize);
    }
}
