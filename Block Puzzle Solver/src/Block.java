
public class Block {
	
	private String name;
	private int mytopRow;
	private int myleftCol;
	private int mybotRow;
	private int myrightCol;
	private int myHeight;
	private int myWidth;
	private boolean canRight;
	private boolean canLeft;
	private boolean canUp;
	private boolean canDown;
	
	public Block (String s) {
		// Sets the name as the string representation of the block.
		name = s;
		
		String[] corners = s.split(" ");
		
		// Checks if the line input has four arguments passed in for the top left corner
		// and bottom right corner. If not, throw an error.
		if (corners.length != 4) {
			throw new IllegalArgumentException("Invalid number of arguments: " +
					"Board construction requires FOUR integers to be passed in");
		}
		
		// Checks if the four arguments passed in for the top left and bottom right vertices
		// are integers.
		try {
			mytopRow = Integer.parseInt(corners[0]);
			myleftCol = Integer.parseInt(corners[1]);
			mybotRow = Integer.parseInt(corners[2]);
			myrightCol = Integer.parseInt(corners[3]);
			
			// Checks if the integer inputs create a valid block.
			if (mybotRow < mytopRow) {
				throw new IllegalArgumentException("Invalid rows: bottom row is higher than top row?");
			}
			if (myrightCol < myleftCol) {
				throw new IllegalArgumentException("Invalid columns: right row is on the left of left row?");
			}
		} catch (NumberFormatException e) {
			System.err.println("One or more of the arguments passed in " +
								"for creating a block is not an integer.");
		}
		
		// Calculates the height and width of the block.
		myHeight = mybotRow - mytopRow + 1;
		myWidth = myrightCol - myleftCol + 1;
		canRight=true;
		canLeft=true;
		canUp=true;
		canDown=true;
	}
	
	//boolean? can change a variable in block? not sure how to handle 	
	void canRight(boolean isTrue){canRight=isTrue;}
	boolean canRight(){return canRight;}
	void canLeft(boolean isTrue){this.canLeft=isTrue;}
	boolean canLeft(){return this.canLeft;}
	void canUp(boolean isTrue){this.canUp=isTrue;}
	boolean canUp(){return this.canUp;}
	void canDown(boolean isTrue){this.canDown=isTrue;}
	boolean canDown(){return this.canDown;}
	
	public String getName ( ) {
		// Returns the string representation of a block object.
		// (the 4 integers that represent the top right and bottom left corners of the block)
		return name;
	}
	
	public int gettopRow ( ) {
		// Returns the integer representing the top row of the block.
		return mytopRow;
	}
	
	public int getleftCol ( ) {
		// Returns the integer representing the left column of the block.
		return myleftCol;
	}

	public int getbotRow ( ) {
		// Returns the integer representing the bottom row of the block.
		return mybotRow;
	}

	public int getrightCol ( ) {
		// Returns the integer representing the right column of the block.
		return myrightCol;
	}

	public int getHeight ( ) {
		// Returns the height of the block.
		// (position of bottom row - position of top row + 1)
		return myHeight;
	}
	
	public int getWidth ( ) {
		// Returns the width of the block.
		// (position of right row - position of left row + 1)
		return myWidth;
	}
}
