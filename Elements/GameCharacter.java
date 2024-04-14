package Elements;

import GUI.GameGUI;

public abstract class GameCharacter{
    public int worldX;
    public int worldY;
    private int vidas;
    private char symbol;
    
    private int id;
    private GameGUI gui;
    
    
    public GameCharacter(int worldX, int worldY, int vidas, char symbol,int id,GameGUI gui){
        this.worldX = worldX;
        this.worldY = worldY;
        this.vidas = vidas;
        this.symbol = symbol;
        
        this.id = id;
        this.gui=gui;
    }
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
    public int getVidas() {
        return vidas;
    }
    public void setX(int worldX) {
        this.worldX = worldX;
    }
    public void setY(int worldY) {
        this.worldY = worldY;
    }
    public int getX() {
        return worldX;
    }
    public int getY() {
        return worldY;
    }
    public char getSymbol() {
        return symbol;
    }

    public GameGUI getGui() {
        return gui;
    }
    public void setGui(GameGUI gui) {
        this.gui = gui;
    }

    
    public void lostLife() {
        vidas--;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public abstract boolean isAlive();

}