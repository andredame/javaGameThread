package Elements;
public class Player extends GameCharacter{
    public Player(int x, int y, Puzzle puzzle,int id){
        super(x, y, 3, 'P', puzzle,id);
    }

    @Override
    public boolean isAlive() {
        return getVidas() > 0;
    }
}