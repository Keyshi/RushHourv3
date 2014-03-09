package rush.hour;
/**
 * A set of constants used in the RushHour program.
 */
public interface Constants {

    // the window's title
    public static final String TITLE = "Rush Hour";

    // dimensions of cars in squares 
    public static final int LONG = 3, SHORT = 2;

    // constants to represent directions
    public static final String HORIZONTAL = "H", VERTICAL = "V";
    public static final String LEFT = "L", RIGHT = "R";
    public static final String UP = "U", DOWN = "D";

    // dimensions in pixels
    public static final int SQUARE = 30; // the side of a square in the grid
    public static final int BORDER = 4; // the width of a border in the grid 
    public static final int SIZE = 6*SQUARE+2*BORDER; // the total size of the window (height or width)
	
	//Numero des actions associees a chaque bouton
	public static final int NEWPLAYER = 0;
	public static final int LOADPLAYER = 1;
	public static final int NEWGAME = 2;
	public static final int SAVE = 3;
	public static final int VALIDER = 4;
	
	//nom des lvl
	public static final String [] levels = {"1-beginner", "2-intermediate", "3-advanced", "4-expert", "5-grandmaster"};
}
