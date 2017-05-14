package GUI;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

import Engine.MoveState;
import Figures.Figure;
import Figures.FigureColour;
import Figures.King;
import Figures.Pawn;
import Figures.Queen;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Chess extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Chess");
		Group root = new Group();
		Canvas canvas = new Canvas(Settings.WIDTH, Settings.HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.show();
		Board board = new Board();
		board.update(gc);
		canvas.setOnMousePressed(new TileHandler(board, gc));
	}

}

class TileHandler implements EventHandler<MouseEvent> {

	private ArrayList<Figure> figures;
	private Figure activeFigure;
	private Board board;
	private GraphicsContext gc;
	private int blockX = Settings.MARGIN_LEFT + Settings.SIDE + Settings.MARGIN_RIGHT;
	private int blockY = Settings.TEXT_MARGIN_TOP;
	private int checkBlockX = Settings.MARGIN_LEFT + Settings.SIDE + Settings.MARGIN_RIGHT;
	private int checkBlockY = 2 * Settings.TEXT_MARGIN_TOP;
	MoveState moveState;

	public TileHandler(Board board, GraphicsContext gc) {

		this.gc = gc;
		this.board = board;
		this.figures = board.getFigures();
		activeFigure = null;
		moveState = new MoveState();

	}

	@Override
	public void handle(MouseEvent event) {
		updateLegalMoves();
		int currTileX = (int) (event.getX() - Settings.MARGIN_LEFT) / (Settings.GRID_VALUE);
		int currTileY = (int) (event.getY() - Settings.MARGIN_TOP) / (Settings.GRID_VALUE);
		Figure selectedFigure = board.getFigureByLocation(currTileX, currTileY);
		if ((currTileX >= 0 && currTileX < 8) && (currTileY >= 0 && currTileY < 8)) {
			if (activeFigure == null) {
				if (selectedFigure != null && selectedFigure.getColor() == moveState.getColour())
					activeFigure = selectedFigure;
			} else {
				activeFigure.generateLegalMove(board);
				if (activeFigure.isLegal(currTileX, currTileY, board)) {
					Figure targetFigure = board.getFigureByLocation(currTileX, currTileY);
					if (targetFigure != null && targetFigure.isAlive()
							&& targetFigure.getColor() != activeFigure.getColor()) {
						targetFigure.die();
					}
					int prevLocationX = activeFigure.getPosition().getLocationX();
					int prevLocationY = activeFigure.getPosition().getLocationY();
					activeFigure.getPosition().setLocation(currTileX, currTileY);
					if (isChecked()) {
						activeFigure.getPosition().setLocation(prevLocationX, prevLocationY);
						if (targetFigure != null)
							targetFigure.ressurect();
					} else {
						if(activeFigure.getClass() == Pawn.class){
							switch(activeFigure.getColor()){
							case WHITE :
								if(activeFigure.getPosition().getLocationY() == 0) {
									figures.add(new Queen(FigureColour.WHITE,
											activeFigure.getPosition().getLocationX(),
											activeFigure.getPosition().getLocationY()));
									board.getFigures().remove(activeFigure);
								}
								
								break;
							case BLACK : 
								if(activeFigure.getPosition().getLocationY() ==7) {
									figures.add(new Queen(FigureColour.BLACK,
											activeFigure.getPosition().getLocationX(),
											activeFigure.getPosition().getLocationY()));
									board.getFigures().remove(activeFigure);
								}
								
								break;
							}
							
						}
						activeFigure.moved();
						moveState.changeColour();
						moveState.addMove(new Point(activeFigure.getPosition().getLocationX(),
								activeFigure.getPosition().getLocationY()), new Point(currTileX, currTileY));
						updateLegalMoves();
						if (isChecked())
							displayCheck(moveState.getColour());
						else
							unCheck();
						if (isMated(moveState.getColour()))
							displayMate(moveState.getColour());
					}
				}
				activeFigure = null;
			}
			if (activeFigure == null) {
				displayNotSelected();
			} else {
				displaySelected();
			}
		}
		board.update(gc);

	}

	private void displayNotSelected() {
		gc.setFill(Color.WHITE);
		gc.fillRect(blockX, blockY - Settings.TEXT_HEIGHT, blockX, blockY);
		gc.setFill(Color.BLACK);
		gc.fillText("Figure is not selected", blockX, blockY);
	}

	private void displaySelected() {
		char chessSymb = (char) ('a' + activeFigure.getPosition().getLocationX());
		gc.setFill(Color.WHITE);
		gc.fillRect(blockX, blockY - Settings.TEXT_HEIGHT, blockX, blockY);
		gc.setFill(Color.BLACK);
		gc.fillText("Currently selected : " + chessSymb + "" + (activeFigure.getPosition().getLocationY() + 1)
				+ " : " +  activeFigure.toString(), blockX, blockY);
	}

	private void updateLegalMoves() {
		HashSet<Point> whiteLegal = new HashSet<Point>();
		HashSet<Point> blackLegal = new HashSet<Point>();
		for (Figure figure : board.getFigures()) {
			if (figure != null && figure.isAlive() && figure.getColor() == FigureColour.WHITE) {
				figure.generateLegalMove(board);
				whiteLegal.addAll(figure.getLegalMoves() == null ? new ArrayList<Point>() : figure.getLegalMoves());
			}
		}
		for (Figure figure : board.getFigures()) {
			if (figure != null && figure.isAlive() && figure.getColor() == FigureColour.BLACK) {
				figure.generateLegalMove(board);
				blackLegal.addAll(figure.getLegalMoves() == null ? new ArrayList<Point>() : figure.getLegalMoves());
			}
		}
		board.updateProtected(whiteLegal, blackLegal);
	}

	public boolean isChecked() {
		updateLegalMoves();
		Figure kingPiece = null;
		for (Figure figure : board.getFigures()) {
			if (figure.getClass() == King.class && figure.getColor() == this.moveState.getColour())
				kingPiece = figure;
		}
		return (board.isProtectedBy(kingPiece.getPosition().getLocationX(), kingPiece.getPosition().getLocationY(),
				kingPiece.getColor().opposite()));
	}

	private void displayCheck(FigureColour colour) {
		gc.setFill(Color.BLACK);
		gc.fillText(colour + " checked !", checkBlockX, checkBlockY);
	}

	private void displayMate(FigureColour colour) {
		gc.setFill(Color.RED);
		gc.fillText(colour + " was mated ! " + colour.opposite() + " wins !", blockX, Settings.HEIGHT / 2);
	}

	private void unCheck() {
		gc.setFill(Color.WHITE);
		gc.fillRect(checkBlockX, checkBlockY - Settings.TEXT_HEIGHT, Settings.WIDTH - checkBlockX,
				Settings.HEIGHT - checkBlockY);
	}

	private boolean isMated(FigureColour colour) {
		boolean isMated = true;
		for (Figure figure : figures) {
			figure.generateLegalMove(board);
			if (figure != null && figure.isAlive() && figure.getColor() == colour) {
				for (Point point : figure.getLegalMoves()) {
					if (Board.inBounds(point)) {
						int prevLocationX = figure.getPosition().getLocationX();
						int prevLocationY = figure.getPosition().getLocationY();
						figure.getPosition().setLocation(point.x, point.y);
						Figure targetFigure = board.getFigureByLocation(point.x, point.y);
						if(targetFigure != null) targetFigure.die();
						if (!isChecked())
							isMated = false;
						if(targetFigure != null) targetFigure.ressurect();
						figure.getPosition().setLocation(prevLocationX, prevLocationY);
					}
				}
			}
		}
		return isMated;
	}
}