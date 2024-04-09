package Elements;
public abstract class Character{
    private int x;
    private int y;
    private int vidas;
    private char symbol;
    public Character(int x, int y, int vidas, char symbol){
        this.x = x;
        this.y = y;
        this.vidas = vidas;
        this.symbol = symbol;
    }
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
    public int getVidas() {
        return vidas;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public char getSymbol() {
        return symbol;
    }
}