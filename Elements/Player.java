package Elements;
public class Player extends Character{
    public Player(int x, int y, Puzzle puzzle){
        super(x, y, 3, 'P', puzzle);
    }

    @Override
    public boolean isAlive() {
        return getVidas() > 0;
    }
}