package Thread;

import GUI.Maze;
import Elements.Puzzle;
import Elements.GameCharacter;
import Elements.Direction;

public class ThrowableThread extends Thread {
    private int x;
    private int y;
    private final Direction direction;
    private static final int speed = 100;
    private final char symbol; // Símbolo para desenhar o tiro ou machado
    private final Puzzle puzzle;
    private final Maze maze;

    public ThrowableThread(Maze maze, int startX, int startY, Direction direction, char symbol, Puzzle puzzle ) {
        this.maze = maze;
        this.x = startX;
        this.y = startY;
        this.direction = direction;
        this.symbol = symbol;
        this.puzzle = puzzle;
    }

    @Override
    public void run() {
        while (isWithinBounds()) {
            moveInDirection();

            // if (checkCollision()) {
            //     break;
            // }
            maze.repaint();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    // private boolean checkCollision() {
    //     for (GameCharacter character : maze.getCharacters()) {
    //         if (character.getX() == x && character.getY() == y) {
    //             character.lostLife();
    //             return true;
    //         }
    //     }
    //     return false;
    // }
}
