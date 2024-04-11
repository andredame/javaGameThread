package Threads;

import GUI.GameGUI;
import Elements.Puzzle;
import Elements.GameCharacter;
import Elements.Direction;

public class ThrowableThread extends Thread {
    private int x;
    private int y;
    private final Direction direction;
    private static final int speed = 500;
    private final Puzzle puzzle;
    private final GameGUI maze;
    private final int identificador;
    private String symbol;

    public ThrowableThread(GameGUI maze, int startX, int startY, Direction direction, Puzzle puzzle,int id,String symbol) {
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
        do {
            try {
                moveInDirection();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            GameCharacter character = hitCharacter();
            if (character != null) {
                character.lostLife();
                maze.getThreadManager().removeThread(identificador, this);
                break;
            }
            if(foundTree()){
                maze.getThreadManager().removeThread(identificador, this);
                puzzle.setLocation(x, y, '.');
                break;
            }
            maze.repaint();

        } while (isWithinBounds());
        maze.getThreadManager().removeThread(identificador, this);

    }

    public int getY() {
        return y;
    }

    
    public String getSymbol() {
        return symbol;
    }

    public boolean foundTree() {
        if( puzzle.getLocation(x, y) == 'T'){
            return true;
        }
        return false;
    }


    public void setY(int y) {
        this.y = y;
    }

    private GameCharacter hitCharacter() {
        for (Thread thread : maze.getThreadManager().getThreads().values()) {
            if (thread instanceof CharacterThread) {
                CharacterThread characterThread = (CharacterThread) thread;
                GameCharacter character = characterThread.getCharacter();
                if (character.getX() == x && character.getY() == y) {
                    return character;
                }
            }
        }
        return null;
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

    public GameGUI getMaze() {
        return maze;
    }

    
}
