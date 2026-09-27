package snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeWalls {

    private final Random random = new Random();
    private final List<Point> walls = new ArrayList<>();

    public SnakeWalls(){

    }

    public List<Point> getWalls(){ return walls; }

    public void resetWalls(){ walls.clear(); }

    public void wallGenerator(int panelWidth, int panelHeight, int padding, int gridSize){
        int wallLength = random.nextInt(12) + 2;

        int cols = (panelWidth - 2 * padding) / gridSize;
        int rows = (panelHeight - 2 * padding) / gridSize;

        int startX = padding + random.nextInt(cols) * gridSize;
        int startY = padding + random.nextInt(rows) * gridSize;

        Point firstPoint = new Point(startX, startY);

        walls.add(firstPoint);

        System.out.println("wall starts at: x: " + walls.get(0).x);
        System.out.println("wall starts at: y: " + walls.get(0).y);

        for(int i = 0; i < wallLength - 1; i++){
            Point prevPoint = walls.get(walls.size() - 1);
            int newX = prevPoint.x;
            int newY = prevPoint.y;

            int setDirection = random.nextInt(4);
            switch (setDirection) {
                case 0: {
                    // Up
                    if (prevPoint.y - gridSize >= padding) {
                        newY -= gridSize;
                    }
                    break;
                }
                case 1: {
                    // Right
                    if (prevPoint.x + gridSize <= panelWidth - padding - gridSize) {
                        newX += gridSize;
                    }
                    break;
                }
                case 2: {
                    // Down
                    if (prevPoint.y + gridSize <= panelHeight - padding - gridSize * 2) {
                        newY += gridSize;
                    }
                    break;
                }
                case 3: {
                    // Left
                    if (prevPoint.x - gridSize >= padding) {
                        newX -= gridSize;
                    }
                    break;
                }
            }

            walls.add(new Point(newX, newY));
        }
    }
}
