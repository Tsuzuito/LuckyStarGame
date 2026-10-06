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

    public void wallGenerator(int panelWidth, int panelHeight, int padding, int gridSize) {
        int wallLength = random.nextInt(12) + 6;

        int cols = (panelWidth - 2 * padding) / gridSize;
        int rows = (panelHeight - 2 * padding) / gridSize;

        int maxX = padding + (cols - 1) * gridSize;
        int maxY = padding + (rows - 1) * gridSize;

        int startX = padding + random.nextInt(cols) * gridSize;
        int startY = padding + random.nextInt(rows) * gridSize;

        walls.add(new Point(startX, startY));

        for(int i = 0; i < wallLength - 1; i++) {
            Point prevPoint = walls.get(walls.size() - 1);
            int newX = prevPoint.x;
            int newY = prevPoint.y;

            int setDirection = random.nextInt(4);
            boolean moved = false;

            switch (setDirection) {
                case 0: {
                    // Up
                    if (prevPoint.y - gridSize >= padding) {
                        newY -= gridSize;
                        moved = true;
                    }
                    break;
                }
                case 1: {
                    // Right
                    if (prevPoint.x + gridSize <= maxX) {
                        newX += gridSize;
                        moved = true;
                    }
                    break;
                }
                case 2: {
                    // Down
                    if (prevPoint.y + gridSize <= maxY) {
                        newY += gridSize;
                        moved = true;
                    }
                    break;
                }
                case 3: {
                    // Left
                    if (prevPoint.x - gridSize >= padding) {
                        newX -= gridSize;
                        moved = true;
                    }
                    break;
                }
            }
            // add wall if its really generated
            if (moved) {
                walls.add(new Point(newX, newY));
            }
        }
    }
}