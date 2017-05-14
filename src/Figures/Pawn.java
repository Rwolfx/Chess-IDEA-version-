package Figures;

import java.awt.Point;
import java.util.HashSet;

import GUI.Board;
import javafx.scene.image.Image;

public class Pawn extends Figure{
	
	private final int FIGURE_VALUE = 1;
	private final int WHITE_START = 6;
	private final int BLACK_START = 1;
	
	public Pawn(FigureColour colour, int x, int y) {
		super(colour, x, y);
		if(super.getColor() == FigureColour.WHITE)
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/WhitePawn.png"));
		else
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/BlackPawn.png"));
	}

	@Override
	public void generateLegalMove(Board board) {
		legalMoves = new HashSet<Point>();
		int currX = this.getPosition().getLocationX();
		int currY = this.getPosition().getLocationY();
		if(this.getColor() == FigureColour.WHITE){
			Figure target = board.getFigureByLocation(currX,currY - 1);
			if(target == null){
				legalMoves.add(new Point(currX,currY-1));
				target = board.getFigureByLocation(currX, currY-2);
				if(target == null && currY == WHITE_START) legalMoves.add(new Point(currX,currY -2));
			}
			target = board.getFigureByLocation(currX-1,currY - 1);
			if(target != null && target.getColor() != this.getColor()) legalMoves.add(new Point(currX-1, currY-1));
			target = board.getFigureByLocation(currX+1,currY - 1);
			if(target != null && target.getColor() != this.getColor()) legalMoves.add(new Point(currX+1, currY-1));
		}else{
			Figure target = board.getFigureByLocation(currX,currY + 1);
			if(target == null){
				legalMoves.add(new Point(currX,currY+1));
				target = board.getFigureByLocation(currX, currY  +2);
				if(target == null && currY == BLACK_START) legalMoves.add(new Point(currX,currY  + 2));
			}
			target = board.getFigureByLocation(currX-1,currY + 1);
			if(target != null && target.getColor() != this.getColor()) legalMoves.add(new Point(currX-1, currY+1));
			target = board.getFigureByLocation(currX+1,currY + 1);
			if(target != null && target.getColor() != this.getColor()) legalMoves.add(new Point(currX+1, currY+1));
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Pawn";
	}
	
	

}
