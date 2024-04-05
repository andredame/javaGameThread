public class Bullet {
    private static final int BULLET_SPEED=3;
    private static final int BULLET_SIZE = 5;
	private int bulletX, bulletY;

    
  

    public int getBULLET_SPEED() {
		return BULLET_SPEED;
	}
	public static int getBulletSize() {
		return BULLET_SIZE;
	}
    
	public int getBulletX() {
		return bulletX;
	}
	public void setBulletX(int bulletX) {
		this.bulletX = bulletX;
	}
	public int getBulletY() {
		return bulletY;
	}
	public void setBulletY(int bulletY) {
		this.bulletY = bulletY;
	}

    

}
