package Figures;

public enum FigureColour {
	WHITE,
	BLACK;
	
	public FigureColour opposite(){
		switch(this){
		case BLACK : return WHITE;
		default : return BLACK;
		}
	}
}
