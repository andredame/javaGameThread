public class Axe {

    private boolean throwing;
    private int vidas;
    private int x;
    private int y;
    

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

    public Axe(){
        this.vidas=5;
        this.throwing=true;
    }

    public boolean isThrowing() {
        return throwing;
    }

    public void setThrowing(boolean throwing) {
        this.throwing = throwing;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    

}
