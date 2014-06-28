package cc.co.techzealous.snowfall.utils;

public class Snowflake extends Object {

	private int size;
	private int direction;
	private int speed;
	private int sway;
	private int moveTowards;
	private int transparency;
	private int posX;
	private int posY;
	
	public Snowflake() {
		super();
	}
	
	public void setSize(int aSize) {
		size = aSize;
	}
	public int getSize() {
		return size;
	}
	
	public void setDirection(int aDirection) {
		direction = aDirection;
	}
	public int getDirection() {
		return direction;
	}
	
	public void setSpeed(int aSpeed) {
		speed = aSpeed;
	}
	public int getSpeed() {
		return speed;
	}
	
	public void setSway(int aSway) {
		sway = aSway;
	}
	public int getSway() {
		return sway;
	}
	
	public void setMoveTowards(int aMoveTowards) {
		moveTowards = aMoveTowards;
	}
	public int getMoveTowards() {
		return moveTowards;
	}
	
	public void setTransparency(int aTransparency) {
		transparency = aTransparency;
	}
	public int getTransparency() {
		return transparency;
	}
	
	public void setPosX(int aPosX) {
		posX = aPosX;
	}
	public int getPosX() {
		return posX;
	}
	
	public void setPosY(int aPosY) {
		posY = aPosY;
	}
	public int getPosY() {
		return posY;
	}
	
}
