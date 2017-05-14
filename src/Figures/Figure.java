package Figures;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

import GUI.Board;
import GUI.Settings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Figure {
	
	private boolean alive;
	protected FigureColour colour;
	protected Image img;
	protected HashSet<Point> legalMoves;
	protected FigurePosition position;
	protected boolean moved;
	
	public Figure(FigureColour colour,int x, int y){
		moved = false;
		this.colour = colour;
		position = new FigurePosition(x,y);
		alive = true;
	}
	
	public boolean hasMoved(){
		return this.moved;
	}
	public void moved(){
		this.moved = true;
	}
	
	public void draw(GraphicsContext gc){
		gc.drawImage(img, this.position.getLocationX() * Settings.GRID_VALUE + Settings.MARGIN_LEFT,
						  this.position.getLocationY() * Settings.GRID_VALUE + Settings.MARGIN_TOP);
	}
	
	public FigureColour getColor(){
		return colour;
	}
	
	public FigurePosition getPosition(){
		return position;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isLegal(int x, int y,Board board){
		if(legalMoves.contains(new Point(x,y))) return true;
		else return false;
	}
	
	public abstract void generateLegalMove(Board board);
	
	public boolean isOpponent(Figure figure){
		if(figure.getColor() != this.getColor()) return true;
		else return false;
	}
	
	public void die(){
		this.alive = false;
	}
	
	public void ressurect(){
		this.alive = true;
	}
	public HashSet<Point> getLegalMoves(){
		return this.legalMoves;
	}
	
}


