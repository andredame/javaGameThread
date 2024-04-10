package Elements;

public class LumberJack extends GameCharacter  {
    public LumberJack(int x, int y,Puzzle puzzle,int id){
        super(x, y, 1, 'X', puzzle,id);
    }


    @Override
    public boolean isAlive() {
        return getVidas() > 0;
    }



}
