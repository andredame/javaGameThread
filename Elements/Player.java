package Elements;
public class Player extends GameCharacter{
    private int hasAxe = 0;
    public Player(int x, int y, Puzzle puzzle,int id){
        super(x, y, 3, 'P', puzzle,id);
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
        if(hasAxe > 0){
            hasAxe--;
        }
    }
}