package Threads;

import Elements.GameCharacter;
import GUI.GameGUI;
import GUI.TileManager;

public class CharacterThread extends Thread {
    
    private GameCharacter character;
    private GameGUI game;
    private int identificador;
    private TileManager tileManager;
    private boolean canTakeDamage = true;

    public CharacterThread(GameGUI game, GameCharacter character, int id, TileManager tileManager) {
        this.character = character;
        this.game = game;
        this.identificador = id;
        this.tileManager = tileManager;
    }

    public int getIdentificador() {
        return identificador;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public void setCharacter(GameCharacter character) {
        this.character = character;
    }

    @Override
    public void run() {
        while (character.isAlive()) {
            
            moveCharacter();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.repaint();
        }
    }

    private void moveCharacter() {
        int moveX = 0;
        int moveY = 0;
        int direction = (int) (Math.random() * 4);
        switch (direction) {
            case 0: 
                moveX = -1;
                break;
            case 1: 
                moveX = 1;
                break;
            case 2: 
                moveY = -1;
                break;
            case 3: 
                moveY = 1;
                break;
            default:
                break;
        } 
        if (tileManager.isWalkable(character.getX() + moveX, character.getY() + moveY) && (game.getPlayer().getX() != character.getX() + moveX || game.getPlayer().getY() != character.getY() + moveY)) {
            character.setX(character.getX() + moveX);
            character.setY(character.getY() + moveY);                 
        }

        if(character instanceof Elements.Snake){
            if(isAdjacentToPlayer()){
                takeDamage();
            }     
        }
    }

    private boolean isAdjacentToPlayer(){
        int playerX = game.getPlayer().getX() / game.TILE_SIZE;
        int playerY = game.getPlayer().getY() / game.TILE_SIZE;
        int characterX = character.getX() ;
        int characterY = character.getY();
        return Math.abs(playerX - characterX) + Math.abs(playerY - characterY) == 1;
    }

    private void takeDamage() {
        if (canTakeDamage) {
            game.getPlayer().lostLife(); 
            canTakeDamage = false; 
            new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        canTakeDamage = true;
                    }
                }, 
                5000 
            );
        }
    }

}