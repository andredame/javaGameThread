package Elements;

import GUI.GameGUI;

public class LumberJack extends GameCharacter  {
    public LumberJack(int x, int y,int id,GameGUI gui){
        super(x, y, 3, 'X',id,gui);
    }


    @Override
    public boolean isAlive() {
        return getVidas() > 0;
    }



}