package GUI;

public class Tile {
	
	private int x;
	private int y;
	private boolean protectedByWhite;
	private boolean protectedByBlack;
	
	public Tile(int x, int y){
		this.x= x;
		this.y = y;
		protectedByWhite = false;
		protectedByBlack = false;
	}
	
	public void protectByWhite(){
		this.protectedByWhite = true;
	}
	
	public void protectByBlack(){
		this.protectedByBlack = true;
	}
	
	public boolean isWhiteProtected(){
		return this.protectedByWhite;
	}
	
	public boolean isBlackProtected(){
		return this.protectedByBlack;
	}
}
