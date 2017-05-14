package Figures;

import java.awt.Point;
import java.util.HashSet;

import GUI.Board;
import GUI.Settings;
import javafx.scene.image.Image;

public class Bishop extends Figure{
	
	private final int FIGURE_VALUE = 3;

	public Bishop(FigureColour colour, int x, int y) {
		super(colour, x, y);
		if(super.getColor() == FigureColour.WHITE)
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/WhiteBishop.png"));
		else
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/BlackBishop.png"));
	}
	
	@Override
	public void generateLegalMove(Board board) {
		int row = this.getPosition().getLocationY();
	    int column = this.getPosition().getLocationX();
	    legalMoves = new HashSet<Point>();
	    //down positive
	    for (int j = column + 1, i = row + 1; j < Settings.SIZE && i < Settings.SIZE; j++, i++) {
	    	Figure figure = board.getFigureByLocation(j,i);
	        if (figure == null) {
	            legalMoves.add(new Point(j,i));
	        } else if (isOpponent(figure)) {
	            legalMoves.add(new Point(j,i));
	            break;
	        } else {
	            break;
	        }
	    }
	    //up positive
	    for (int j = column - 1, i = row + 1; j > -1 && i < Settings.SIZE; j--, i++) {
	    	Figure figure = board.getFigureByLocation(j, i);
	        if (figure == null) {
	            legalMoves.add(new Point(j,i));
	        } else if (isOpponent(figure)) {
	            legalMoves.add(new Point(j,i));
	            break;
	        } else {
	            break;
	        }
	    }
	    //up negative
	    for (int j = column - 1, i = row - 1; j > -1 && i > -1; j--, i--) {
	    	Figure figure = board.getFigureByLocation(j,i);
	        if (figure == null) {
	            legalMoves.add(new Point(j,i));
	        } else if (isOpponent(figure)) {
	            legalMoves.add(new Point(j,i));
	            break;
	        } else {
	            break;
	        }
	    }
	    //down negative
	    for (int j = column + 1, i = row - 1; j < Settings.SIZE && i > -1; j++, i--) {
	    	Figure figure = board.getFigureByLocation(j,i);
	        if (figure == null) {
	            legalMoves.add(new Point(j,i));
	        } else if (isOpponent(figure)) {
	            legalMoves.add(new Point(j,i));
	            break;
	        } else {
	            break;
	        }
	    }
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Bishop";
	}

}
