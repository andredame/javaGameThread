package Elements;

import GUI.GameGUI;

import java.awt.Graphics;

public class Player extends GameCharacter{
    private int hasAxe = 0;
    public final int screenX;
    public final int screenY;
    private final int speed = 5;

    public Player(int worldX, int worldY, int id,GameGUI gui){
        super(worldX, worldY, 5, 'P',id,gui);
        screenX=gui.SCREEN_WIDTH/2 - (gui.TILE_SIZE/2);
        screenY=gui.SCREEN_HEIGHT/2 - (gui.TILE_SIZE/2);
    }

    public int getSpeed(){
        return speed;
    }

    @Override
    public boolean isAlive() {
        return getVidas() > 0;
    }

    public void setHasAxe(int hasAxe) {
        this.hasAxe = hasAxe;
    }

    public int hasAxe() {
        return hasAxe;
    }

    public void throwAxe(){
        System.out.println(hasAxe);
        if(hasAxe > 0){
            hasAxe--;
        }
    }

    public void update(){
        draw(this.getGui().getGraphics());
    }

    public void draw(Graphics gui){
        gui.drawImage(super.getGui().tileManager.getTileImage(String.valueOf(super.getSymbol())), screenX, screenY, super.getGui().TILE_SIZE, super.getGui().TILE_SIZE, null);
    }

    public void addVida(){
        if(getVidas() < 5){
            setVidas(getVidas()+1);
        }
    }

}