import java.util.*;

public class Solver {

	private LinkedList<Board> SolutionPath;
	private boolean printMoves = false;
	private boolean printNumber = false;
	private int numberofMoves = 0;
	private boolean runtime = false;

	public Solver (String debug, Board start, Board goal) {

		// Error handling:
		if (!debug.startsWith("-o")) {
			throw new IllegalArgumentException("Your debugging argument must start with -o followed by corresponding codes. For help, please use -ooptions as your debugging argument.");
		}

		// Parses the rest of the statement after "-o".
		String option = debug.substring(2);

		// Options menu. MORE OPTIONS STILL NEEDED.
		if (option.equals("options")) {
			System.out.println("Debugging Options:");
			System.out.println("***********************************");
			System.out.println("Your debugging options are:");
			System.out.println("m: Prints all of the moves that were considered.");
			System.out.println("n: Prints the number of moves taken.");
			System.out.println("p: Show a graphical representation of the solver.");
			System.out.println("r: Show the runtime of the solver");
			System.out.println("***********************************");
			System.out.println("How to use the debugging options:");
			System.out.println("Pass in debugging argument as follows:");
			System.out.println("If you want to print all of the moves considered,");
			System.out.println("the number of moves taken, and the runtime, simply pass in:");
			System.out.println("-omnr");
			System.out.println("The order of the letters following the o do no matter.");

		// Dealing with options. Don't know how this is working yet.
		} else {		
			for (int i = 0; i < option.length(); i++) {
				String current = option.substring(i, i+1);
				if (current.equals("m")) {
					// How to handle printing all moves considered.
					printMoves = true;
				} if (current.equals("n")) {
					// How to handle printing all moves considered.
					printNumber = true;
				} if (current.equals("p")) {
					// How to handle showing the graphical representation.
				} if (current.equals("r")) {
					// How to handle runtime calculation.
					runtime = true;
				} else {
					System.out.println("No such debug option: " + current + ".");
				}
			}
		}

		// Debugging option: STARTING RUNTIME TIMER.
		long startTime = System.currentTimeMillis();

		// Initializing the regular solver.
		Solver mySolver = new Solver(start, goal);

		// Debugging option: ENDING RUNTIME TIMER.
		if (runtime == true) {
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);
		}
		// Debugging option: PRINTING NUMBER OF MOVES.
		if (printNumber == true) {
			System.out.println(numberofMoves);
		}
	}

	static boolean isDone(Board currBoard, Board goalBoard) {
		// Iterates through each block in the goal board and checks the current board if the blocks
		// match. If every goal block is in the right location in the current board, return true.
		Block[][] check = goalBoard.myBlocks();
		
		// Iterates through each block in the goal board.
		for (int i = 0; i < goalBoard.myLength(); i++) {
			for (int j = 0; j < goalBoard.myWidth(); j++) {
					if (check[i][j] != null) {
						if (currBoard.hasBlock(i,j)) {
							// Checks if the blocks are not equal and returns false.
							if (currBoard.myBlocks()[i][j].getHeight() != check[i][j].getHeight() ||
								currBoard.myBlocks()[i][j].getWidth() != check[i][j].getWidth()) {
								return false;
							}
						// If the current Board does not contain the goal block, return false.
						} else {
							return false;
						}
					}
				}
			}
		// If everything passes, return true.
		return true;
	}

	// static Board newRandomBoard (Board currentBoard)
	
	public Solver (Board start, Board goal) {
		
		SolutionPath = new LinkedList<Board>();
		start.canMoveAll();
		Board currentBoard = start;
		
		outerloop:
			
		while (true) {
			long startTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - startTime < 5) {
				System.out.println("ping 7");
				SolutionPath.add(currentBoard);
				currentBoard = currentBoard.newRandomBoard(currentBoard);
				currentBoard.canMoveAll();
				System.out.println(currentBoard.toString());
				if (isDone(currentBoard, goal)) {
					System.out.println("finished mofo");
					break outerloop;
				}
			}
			SolutionPath = new LinkedList<Board>();
		}
		
		// Debugging option: Print each move taken.
		if (true) {
			for (int i = 0; i < SolutionPath.size(); i++) {
				System.out.println(SolutionPath.get(i).toString());
			}
		}	
	}


}
