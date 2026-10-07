package snake;

import sound.SoundManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeLogic {

    private final Random random = new Random();

    private final List<Point> snake = new ArrayList<>();
    private final int gridSize = 16;
    private final int padding = 16 * 4;

    private final int panelWidth = 800;
    private final int panelHeight = 600;

    private final SnakeFood food;
    private final int foodTargetCount = 2;

    private final SnakeWalls walls;

    private boolean isGameOver = false;
    public int tickCounter = 0;

    private int score = 0;

    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction direction = Direction.RIGHT;

    public SnakeLogic(){
        food = new SnakeFood();
        walls = new SnakeWalls();

    }

    public List<Point> getSnake(){ return snake; }
    public SnakeFood getFood() { return food; }
    public SnakeWalls getWalls() { return walls; }
    public int getScore(){ return score; }

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
        walls.resetWalls();
        food.resetFood();

        snake.add(new Point(gridSize * 23, gridSize * 18));

        direction = Direction.RIGHT;
        isGameOver = false;
        score = 0;
        tickCounter = 0;

        for(int wallsCount = 0; wallsCount < random.nextInt(12)+8; wallsCount++){
            walls.wallGenerator(panelWidth, panelHeight, padding, gridSize, snake.getFirst());
        }

        food.spawnFood(foodTargetCount, panelWidth, panelHeight, padding, gridSize, snake, walls.getWalls());
    }

    public void tick(){
        tickCounter++;

        Point head = snake.get(0);
        Point newHead = switch(direction){
            case RIGHT -> new Point(head.x + gridSize, head.y);
            case LEFT  -> new Point(head.x - gridSize, head.y);
            case UP    -> new Point(head.x, head.y - gridSize);
            case DOWN  -> new Point(head.x, head.y + gridSize);
        };

        snake.add(0, newHead);

        Point eatenFood = null;
        for(Point f : food.getFoodPosition()){
            if(newHead.equals(f)){
                eatenFood = f;
                break;
            }
        }

        if(eatenFood != null){
            food.getFoodPosition().remove(eatenFood);
            SoundManager.playSound("/pop.wav");
            score++;

        } else {
            snake.remove(snake.size() - 1);
        }

        food.spawnFood(foodTargetCount, panelWidth, panelHeight, padding, gridSize, snake, walls.getWalls());

        //border check
        if(newHead.x < padding || newHead.y < padding || newHead.x >= (panelWidth - padding) || newHead.y >= (panelHeight - padding - 16)) {
            isGameOver = true;
            System.out.println("out of bounds");
        }

        //walls collision check
        for (Point wall : walls.getWalls()) {
            if (newHead.equals(wall)) {
                isGameOver = true;
                System.out.println("hit wall");
                break;
            }
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
