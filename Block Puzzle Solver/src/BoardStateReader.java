import java.util.*;
public class BoardStateReader {
	 private Board startingBoard;
	 private Board goalBoard;
	 private String debugOption;
	 
	// The first command-line argument, if specified, is the debugging option
    // The second command-line argument(or first if no debugging) is the name of
    // a file from which to read the starting board.
    // The third command-line argument (or second if no debugging) is the name of
    // a file from which to read the goal board.
    
    BoardStateReader (String [ ] args) {
        if (args.length > 3){
        	System.err.println ("*** bad Board File: Cannot take more then 3 arguments.");
            System.exit (1);
        }
        if (args.length < 2 ){
        	System.err.println ("*** bad Board File: Need at least 2 arguments.");
            System.exit (1);
        }
        // if there are all three arguments. This implies there is a debugging argument at the front.
    	if (args.length == 3) {
    		debugOption=args[0];
    		makeStartBoard(args[1]);
    		makeGoalBoard(args[2]);
    		
    	}
    	// if there is no debugging command at the beginning.
    	if (args.length == 2){
    		makeStartBoard(args[0]);
    		makeGoalBoard(args[1]);
    	}
    		
    }
    Board goalBoard(){return this.goalBoard;}
    Board startingBoard(){return this.startingBoard;}
    void makeStartBoard(String arg){
    	// Creates the first board state based on the .txt file name passed in.
    	InputSource boardStart = new InputSource(arg);
    	String curStartLine = boardStart.readLine();
    	Scanner startLineScanner=new Scanner(curStartLine);
    	int myLength = Integer.parseInt(startLineScanner.next());
    	int myWidth = Integer.parseInt(startLineScanner.next());
    	startLineScanner.close();
    	startingBoard=new Board(myLength, myWidth);
    	curStartLine=boardStart.readLine();
    	while(curStartLine!=null){
    		this.startingBoard.addBlock(curStartLine);
    		curStartLine=boardStart.readLine();
    	}
    }
    void makeGoalBoard(String arg){
    	// Creates the goal board state based on the .txt file name passed in.
    	InputSource boardGoal = new InputSource(arg);
    	goalBoard=new Board(startingBoard.myLength(),startingBoard.myWidth());
    	String curGoalLine = boardGoal.readLine();
    	while(curGoalLine!=null){
    		goalBoard.addBlock(curGoalLine);
    		curGoalLine = boardGoal.readLine();
    	}
    }

	public static void main (String [ ] args) {
		// First creates a BoardStateReader object and populates it according to the command line arguments passed in.
		// A Solver object is then created. That object is responsible for then finding a solution path for the game.
        BoardStateReader checker = new BoardStateReader (args);
        if(args.length==3){
        	Solver mySolve = new Solver(checker.debugOption,checker.startingBoard,checker.goalBoard);
        }
        else if(args.length==2){
        	Solver mySolve = new Solver(checker.startingBoard,checker.goalBoard);
        }
        else{
        	System.err.println("There can only be 2 or 3 arguments.");
        }
    }
}



