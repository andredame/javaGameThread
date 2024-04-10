package Thread;

import GUI.Maze;
import Elements.Puzzle;
import Elements.GameCharacter;
import Elements.Direction;

public class ThrowableThread extends Thread {
    private int x;
    private int y;
    private final Direction direction;
    private static final int speed = 10;
    private final Puzzle puzzle;
    private final Maze maze;
    private final int identificador;
    private String symbol;

    public ThrowableThread(Maze maze, int startX, int startY, Direction direction, Puzzle puzzle,int id,String symbol) {
        this.maze = maze;
        this.x = startX;
        this.y = startY;
        this.direction = direction;
        this.puzzle = puzzle;
        this.symbol = symbol;
        this.identificador = id;
    }

    public int getIdentificador() {
        return identificador;
    }

    @Override
    public void run() {
        while (isWithinBounds()) {
            moveInDirection();
            System.out.println("ThrowableThread: " + x + " " + y);
            maze.repaint();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        
        }

    }
    public int getY() {
        return y;
    }

    
    public String getSymbol() {
        return symbol;
    }


    public void setY(int y) {
        this.y = y;
    }

    private void moveInDirection() {
        switch (direction) {
            case UP:
                x--;
                break;
            case DOWN:
                x++;
                break;
            case LEFT:
                y--;
                break;
            case RIGHT:
                y++;
                break;
            default:
                break;
        }
    }

    private boolean isWithinBounds() {
        return x >= 0 && x < puzzle.getHeight() && y >= 0 && y < puzzle.getWidth();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Direction getDirection() {
        return direction;
    }

    public static int getSpeed() {
        return speed;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public Maze getMaze() {
        return maze;
    }

    
}
