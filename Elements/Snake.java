package Elements;
public class Snake extends Character {

    public Snake(int x, int y, Puzzle puzzle) {
        super(x, y, 1, 'S', puzzle);
    }

    @Override
    public boolean isAlive() {
        return getVidas() > 0;
    }
    
}
