package Figures;

import java.awt.Point;
import java.util.HashSet;

import GUI.Board;
import GUI.Settings;
import javafx.scene.image.Image;

public class King extends Figure {
	
	private boolean castled;
	private final int FIGURE_VALUE = 150;
	private final int[][] POSSIBLE_MOVES;
	public King(FigureColour colour, int x, int y) {
		super(colour, x, y);
		if(super.getColor() == FigureColour.WHITE)
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/WhiteKing.png"));
		else
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/BlackKing.png"));
		POSSIBLE_MOVES = fillPossibleMoves();
		castled = false;
	}
	
	public int[][] fillPossibleMoves(){
		int[][] positionChange = {
		        {1, 1},
		        {-1, 1},
		        {1, -1},
		        {-1, -1},
		        {1, 0},
		        {-1, 0},
		        {0, -1},
		        {0, 1}
		    };
		return positionChange;
		
	}

	@Override
	public void generateLegalMove(Board board) {
		legalMoves = new HashSet<Point>();
		for(int[] point : POSSIBLE_MOVES){
			int possibleX = getPosition().getLocationX() + point[0];
			int possibleY = getPosition().getLocationY() + point[1];
			if(board.getFigureByLocation(possibleX, possibleY) == null || 
			   isOpponent(board.getFigureByLocation(possibleX, possibleY)))
						legalMoves.add(new Point(possibleX,possibleY));
		}
	}
	
	@Override
	public boolean isLegal(int x, int y, Board board) {
		if (!this.moved) {
			for (Rook rook : board.getFiguresByClass(Rook.class)) {
				if (!rook.hasMoved() && rook.getColor()== this.colour) {
					switch (rook.getPosition().getLocationX()) {
					case Settings.ZERO_VALUE:
						if (!board.isProtectedBy(this.position.getLocationX() - 1, this.position.getLocationY(),
								this.colour.opposite()) &&
								!board.isProtectedBy(this.position.getLocationX() - 2, this.position.getLocationY(),
										this.colour.opposite()))
						{
							legalMoves.add(new Point(this.position.getLocationX() - 2, this.position.getLocationY()));
							if (x == this.position.getLocationX() - 2 && y == this.position.getLocationY())
								rook.getPosition().setLocation(rook.getPosition().getLocationX() + 3,rook.getPosition().getLocationY());
						} break;
					case Settings.SIZE - 1:
						if (!board.isProtectedBy(this.position.getLocationX() + 1, this.position.getLocationY(),
								this.colour.opposite()) &&
								!board.isProtectedBy(this.position.getLocationX() + 2, this.position.getLocationY(),
										this.colour.opposite())) {
							legalMoves.add(new Point(this.position.getLocationX() + 2, this.position.getLocationY()));
							if (x == this.position.getLocationX() + 2 && y == this.position.getLocationY())
								rook.getPosition().setLocation(rook.getPosition().getLocationX() - 2,rook.getPosition().getLocationY());
						} break;

					}
				}
			}
		}
		if(!board.isProtectedBy(x,y,this.colour.opposite()))
				return legalMoves.contains(new Point(x, y));
		else 
			return false;

	}
	
	public boolean hasCastled(){
		return this.castled;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "King";
	}

}
