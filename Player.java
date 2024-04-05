public class Player {
    private int x;
    private int y;
    private final char symbol = '☹';
    private int vidas;
    private int Ammo;

    public void setAmmo(int ammo) {
		Ammo = ammo;
	}

	public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.vidas = 3;
        this.Ammo =0;
    }

    public boolean hasAmmo() {
        return Ammo>0;
    }

    public void reloadGun(){        
        this.Ammo=3;
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
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }


}
