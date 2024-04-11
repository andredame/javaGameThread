package Elements;
public class Snake extends GameCharacter {

    public Snake(int x, int y, Puzzle puzzle,int id) {
        super(x, y, 3, 'S', puzzle,id);
    }

    @Override
    public boolean isAlive() {
        return getVidas() > 0;
    }
    
}
