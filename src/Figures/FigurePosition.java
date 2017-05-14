package Figures;

public class FigurePosition {
	private int locationX;
	private int locationY;
	
	public FigurePosition(int x, int y){
		locationX = x;
		locationY = y;
	}
	
	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	
	public void setLocation(int locationX, int locationY){
		this.locationX = locationX;
		this.locationY = locationY;
	}
}
