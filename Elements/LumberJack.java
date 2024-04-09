package Elements;

public class LumberJack extends Character  {
    public LumberJack(int x, int y,Puzzle puzzle){
        super(x, y, 1, 'X', puzzle);
    }


    @Override
    public boolean isAlive() {
        return getVidas() > 0;
    }



}
