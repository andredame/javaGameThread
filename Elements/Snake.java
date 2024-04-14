package Elements;


import GUI.GameGUI;

import java.awt.Graphics;

public class Snake extends GameCharacter {

    public Snake(int x, int y, int id,GameGUI gui) {
        super(x, y, 1, 'S',id,gui);
    }

    @Override
    public boolean isAlive() {
        return getVidas() > 0;
    }

    public void draw(Graphics gui) {
        gui.drawImage(getGui().tileManager.getTileImage(String.valueOf(getSymbol())), getX(), getY(), getGui().TILE_SIZE, getGui().TILE_SIZE, null);
    }
    
    
}
