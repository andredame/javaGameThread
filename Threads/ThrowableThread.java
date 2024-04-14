package Threads;

import GUI.GameGUI;
import GUI.TileManager;
import Elements.GameCharacter;
import Elements.Direction;

public class ThrowableThread extends Thread {
    private int x;
    private int y;
    private final Direction direction;
    private static final int speed = 250;
    private final GameGUI game;
    private final int identificador;
    private String symbol;
    private TileManager tileManager;


    public ThrowableThread(GameGUI game, int startX, int startY, Direction direction,int id,String symbol,TileManager tileManager) {
        this.game = game;
        this.x = startX;
        this.y = startY;
        this.direction = direction;
        this.symbol = symbol;
        this.identificador = id;
        this.tileManager = tileManager;

    }

    public int getIdentificador() {
        return identificador;
    }

    @Override
    public void run() {
        do {
            try {
                game.repaint();
                moveInDirection();
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tileManager.foundATree(this.x, this.y)) {
                tileManager.removeTree(this.x, this.y);
                this.interrupt();
                game.getThreadManager().removeThread(identificador, this);
                break;
            }
            game.repaint();
        } while (isWithinBounds());
        game.getThreadManager().removeThread(identificador, this);
    }

    public int getY() {
        return y;
    }

    private boolean isWithinBounds() {
        return x >= 0 && x < game.maxWorldCol && y >= 0 && y < game.maxWorldRow;
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
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            default:
                break;
        }
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
    
}
