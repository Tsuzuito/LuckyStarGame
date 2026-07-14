package snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SnakeLogic {

    private List<Point> snake = new ArrayList<>();
    private final int gridSize = 16;
    private final int padding = 16 * 4;

    private int panelWidth = 800;
    private int panelHeight = 600;

    private SnakeFood food;
    private boolean isGameOver = false;
    public int tickCounter = 0;

    public int score = 0;

    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction direction = Direction.RIGHT;

    public SnakeLogic(){
        food = new SnakeFood();
        reset();
    }

    public List<Point> getSnake(){ return snake; }
    public SnakeFood getFood() { return food; }

    public void updateDimensions(int width, int height) {
        this.panelWidth = width;
        this.panelHeight = height;
    }

    public void setDirectionUP(){
        if(direction != Direction.DOWN) this.direction = Direction.UP;
    }
    public void setDirectionDOWN(){
        if(direction != Direction.UP) this.direction = Direction.DOWN;
    }
    public void setDirectionLEFT(){
        if(direction != Direction.RIGHT) this.direction = Direction.LEFT;
    }
    public void setDirectionRIGHT(){
        if(direction != Direction.LEFT) this.direction = Direction.RIGHT;
    }

    public boolean isGameOver(){ return isGameOver; }

    public void reset(){
        snake.clear();
        snake.add(new Point(padding + gridSize * 3, padding + gridSize * 3));
        direction = Direction.RIGHT;
        isGameOver = false;
        score = 0;

        tickCounter = 0;

        food.resetFood(panelWidth, panelHeight, padding);
    }



    public void tick(){
        //debug
        System.out.println("\ntick: " + tickCounter);
        tickCounter++;

        Point head = snake.get(0);
        Point newHead = switch(direction){
            case RIGHT -> new Point(head.x + gridSize, head.y);
            case LEFT  -> new Point(head.x - gridSize, head.y);
            case UP    -> new Point(head.x, head.y - gridSize);
            case DOWN  -> new Point(head.x, head.y + gridSize);
        };

        snake.add(0, newHead);

        //food check
        if(newHead.x == food.getxPos() && newHead.y == food.getyPos()){
            food.resetFood(panelWidth, panelHeight, padding);
            score++;
        } else {
            snake.remove(snake.size() - 1);
        }

        //border check
        if(newHead.x < padding || newHead.y < padding || newHead.x >= (panelWidth - padding) || newHead.y >= (panelHeight - padding - 16)) {
            isGameOver = true;
            System.out.println("out of bounds");
        }

        //eat yourself check
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).equals(newHead)) {
                isGameOver = true;
                System.out.println("eat yourself");
                break;
            }
        }
    }
}
