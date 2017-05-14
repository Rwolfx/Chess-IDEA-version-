package Figures;

import java.awt.Point;
import java.util.HashSet;

import GUI.Board;
import javafx.scene.image.Image;

public class Queen extends Figure {
	
	private final int FIGURE_VALUE = 9;
	
	public Queen(FigureColour colour, int x, int y) {
		super(colour, x, y);
		if(super.getColor() == FigureColour.WHITE)
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/WhiteQueen.png"));
		else
			super.img = new Image(getClass().getResourceAsStream("/Resource/Piece/BlackQueen.png"));
	}

	@Override
	public void generateLegalMove(Board board) {
		legalMoves = new HashSet<Point>();
		Figure fakeRook = new Rook(this.getColor(), this.getPosition().getLocationX(), this.getPosition().getLocationY());
		Figure fakeBishop = new Bishop(this.getColor(), this.getPosition().getLocationX(), this.getPosition().getLocationY());
		fakeRook.generateLegalMove(board);
		legalMoves.addAll(fakeRook.getLegalMoves());
		fakeBishop.generateLegalMove(board);
		legalMoves.addAll(fakeBishop.getLegalMoves());
		fakeBishop = null;
		fakeRook = null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Queen";
	}
}
