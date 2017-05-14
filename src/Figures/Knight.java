package Figures;

import java.awt.Point;
import java.util.HashSet;

import GUI.Board;
import javafx.scene.image.Image;

public class Knight extends Figure {
	
	private final int FIGURE_VALUE = 3;
	private final int[][] POSSIBLE_MOVES;
	
	public Knight(FigureColour colour, int x, int y) {
		super(colour, x, y);
		if(super.getColor() == FigureColour.WHITE)
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/WhiteKnight.png"));
		else
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/BlackKnight.png"));
		POSSIBLE_MOVES = fillPossibleMoves();
	}
	
	public int[][] fillPossibleMoves(){
		int[][] positionChange = {
		        {-2, 1},
		        {-1, 2},
		        {1, 2},
		        {2, 1},
		        {2, -1},
		        {1, -2},
		        {-1, -2},
		        {-2, -1}
		    };
		return positionChange;
	}
	
	public void generateLegalMove(Board board){
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
	public String toString() {
		// TODO Auto-generated method stub
		return "Knight";
	}
	

}
