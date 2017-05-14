package Engine;

import java.awt.Point;
import java.util.ArrayList;

import Figures.FigureColour;

public class MoveState {
	
	
	private ArrayList<Point[]> moveList;
	private FigureColour currMove;
	
	public MoveState(){
		moveList = new ArrayList<Point[]>();
		currMove = FigureColour.WHITE;
	}
	
	public void addMove(Point a, Point b){
		Point[] res = {a,b};
		moveList.add(res);
	}
	
	public ArrayList<Point[]> getHistory(){
		return moveList;
	}
	
	public boolean isCheck(FigureColour Colour){
		return true;
	}
	
	public FigureColour getColour(){
		return currMove;
	}
	
	public void changeColour(){
		if(currMove == FigureColour.WHITE) currMove = FigureColour.BLACK;
		else currMove = FigureColour.WHITE;
	}
}
