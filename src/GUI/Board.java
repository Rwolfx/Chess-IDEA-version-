package GUI;
import static Figures.FigureColour.BLACK;
import static Figures.FigureColour.WHITE;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import Figures.Bishop;
import Figures.Figure;
import Figures.FigureColour;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Board {
	private ArrayList<Figure> figures;
	private final int COLUMNS = 8;
	private final int RAWS = 8;
	private Image boardImg;	
	private Tile[][] tiles;
	
	public Board(){
		tiles = new Tile[RAWS][COLUMNS];
		initTiles(tiles);
		figures = new ArrayList<Figure>();
		fillFigures();
		boardImg = new Image(getClass().getResourceAsStream("/Resource/Chess_Board.png"));
		
	}
	
	private void fillFigures(){
		// white side
		figures.add(new Rook(BLACK,0,0));
		figures.add(new Knight(BLACK,1,0));
		figures.add(new Bishop(BLACK,2,0));
		figures.add(new Queen(BLACK,3,0));
		figures.add(new King(BLACK,4,0));
		figures.add(new Bishop(BLACK,5,0));
		figures.add(new Knight(BLACK,6,0));
		figures.add(new Rook(BLACK,7,0));
		figures.add(new Pawn(BLACK,0,1));
		figures.add(new Pawn(BLACK,1,1));
		figures.add(new Pawn(BLACK,2,1));
		figures.add(new Pawn(BLACK,3,1));
		figures.add(new Pawn(BLACK,4,1));
		figures.add(new Pawn(BLACK,5,1));
		figures.add(new Pawn(BLACK,6,1));
		figures.add(new Pawn(BLACK,7,1));
		//black side
		figures.add(new Rook(WHITE,0,7));
		figures.add(new Knight(WHITE,1,7));
		figures.add(new Bishop(WHITE,2,7));
		figures.add(new Queen(WHITE,3,7));
		figures.add(new King(WHITE,4,7));
		figures.add(new Bishop(WHITE,5,7));
		figures.add(new Knight(WHITE,6,7));
		figures.add(new Rook(WHITE,7,7));
		figures.add(new Pawn(WHITE,0,6));
		figures.add(new Pawn(WHITE,1,6));
		figures.add(new Pawn(WHITE,2,6));
		figures.add(new Pawn(WHITE,3,6));
		figures.add(new Pawn(WHITE,4,6));
		figures.add(new Pawn(WHITE,5,6));
		figures.add(new Pawn(WHITE,6,6));
		figures.add(new Pawn(WHITE,7,6));
	}
	
	public void update(GraphicsContext gc){
		gc.drawImage(boardImg, Settings.MARGIN_LEFT, Settings.MARGIN_TOP);
		for(Figure figure : figures){
			if(figure.isAlive())
				figure.draw(gc);
		}
	}
	
	public Figure getFigureByLocation(int locationX, int locationY){
		for(Figure figure : figures){
			if(figure.getPosition().getLocationX() == locationX && figure.getPosition().getLocationY() == locationY && figure.isAlive()) return figure;
		}
		return null;
	}
	public ArrayList<Figure> getFigures(){
		return figures;
	}
	
	private void initTiles(Tile[][] tiles){
		for(int i = 0 ;i < tiles.length;i++){
			for(int j = 0; j < tiles[0].length ;j++){
				tiles[i][j] = new Tile(i,j);
			}
		}
	}
	
	public boolean isProtectedBy(int locationX, int locationY, FigureColour colour){
		switch(colour){
		case BLACK : return tiles[locationX][locationY].isBlackProtected();
		default : return tiles[locationX][locationY].isWhiteProtected();
		}
	}
	
	public static boolean inBounds(Point p){
		return p.x >= Settings.ZERO_VALUE && p.y >= Settings.ZERO_VALUE && p.x < Settings.SIZE && p.y < Settings.SIZE;
	}
	public void updateProtected(HashSet<Point> whiteProtected, HashSet<Point> blackProtected ){
		tiles = new Tile[RAWS][COLUMNS];
		initTiles(tiles);
		for(Point whitePoint : whiteProtected){
			if(inBounds(whitePoint))
				tiles[whitePoint.x][whitePoint.y].protectByWhite();
		}
		for(Point blackPoint : blackProtected){
			if(inBounds(blackPoint))
				tiles[blackPoint.x][blackPoint.y].protectByBlack();
		}
	}
	public <T extends Figure> List<T> getFiguresByClass(Class<T> figureClass){
		return figures.stream()
				.filter(figure -> figure.getClass() == figureClass)
				.map(figure -> (T)figure)
				.collect(Collectors.toList());
		
	}
	
	
	
}
