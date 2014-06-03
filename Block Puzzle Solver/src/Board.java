import java.util.Stack;
import java.util.Random;


public class Board {
	
	private Block[][] myBlocks;
	private int myLength; //number of rows
	private int myWidth; //number of columns
	
	public Board ( ) { 
		myBlocks = new Block[1][1];
	}
	
	public Board (int length, int width) {
		// For JUnit testing purposes, allows us to make a board passing in our desired
		// blocks, length, and width.
		myBlocks = new Block[length][width];
		myLength = length;
		myWidth = width;
	}
	public Board (Block[][] blocks, int length, int width) {
		// For JUnit testing purposes, allows us to make a board passing in our desired
		// blocks, length, and width.
		myBlocks = blocks;
		myLength = length;
		myWidth = width;
	}
	int myLength(){
		return this.myLength;
	}
	int myWidth(){
		return this.myWidth;
	}
	Block[][] myBlocks(){
		return this.myBlocks;
	}
	public void addBlock (String block) {
		// Takes in a string representation of a block and adds the block to our board.
		// NEEDS ERROR HANDLING TO SEE IF THE BLOCK IS WITHIN BOUNDARIES OF THE BOARD. THROW ERRORS.
		Block currentBlock = new Block(block);
		myBlocks[currentBlock.gettopRow()][currentBlock.getleftCol()]=currentBlock;
		// Add pointer to block in all spots it fills.
		for(int i=0; i<currentBlock.getHeight();i++){
			for(int j=0; j<currentBlock.getWidth();j++){
				myBlocks[currentBlock.gettopRow()+i][currentBlock.getleftCol()+j]=currentBlock;
			}
			
		}
	}
	public void canMove(int row, int col){
		// sets the blocks instance variables for if it can move.
			//top
			Block current = this.myBlocks[row][col];
			
			if(current == null){return;}
			
			int left=current.getleftCol();
			int right=current.getrightCol();
			while(left<=right){
				if(current.gettopRow()==0 || hasBlock(current.gettopRow()-1, left)){
					this.myBlocks[row][col].canUp(false);
					System.out.println("ping 1");
					break;
				}
				left++;
			}
			//right
			int bot=current.getbotRow();
			int top=current.gettopRow();
			while(bot<=top){
				if(current.getrightCol()==this.myWidth-1  || hasBlock(bot,current.getrightCol()+1)){
					this.myBlocks[row][col].canRight(false);
					System.out.println("ping 2");
					break;
				}
				bot++;
			}
			//bottom
			left=current.getleftCol();
			right=current.getrightCol();
			while(left<=right){
				if(current.getbotRow()==this.myLength-1 || hasBlock(current.getbotRow()+1, left)){
					this.myBlocks[row][col].canDown(true);
					System.out.println("ping 3");
					break;
				}
				left++;
			}
			//left
			bot=current.getbotRow();
			top=current.gettopRow();
			while(bot<=top){
				if(current.getleftCol()==0 || hasBlock(bot,current.getleftCol()-1)){
					this.myBlocks[row][col].canLeft(true);
					System.out.println("ping 4");
					break;
				}
				bot++;
			}	
		}
	void canMoveAll(){
		for(int i=0;i<this.myLength;i++){
			for(int j=0;j<this.myWidth;j++){
				if(hasBlock(i,j)){
					this.canMove(i, j);
				}
			}
		}
	}
	boolean hasBlock(int row,int col){
		if(this.myBlocks[row][col]!=null){
			return true;
		}
		else{return false;}
	}
	Board newRandomBoard(Board currentBoard){
		Random randomGenerator = new Random();
		int randomRow = randomGenerator.nextInt(currentBoard.myLength);
		int randomCol = randomGenerator.nextInt(currentBoard.myWidth);
		currentBoard.canMove(randomRow, randomCol);
		Block curBlock = currentBoard.myBlocks[randomRow][randomCol];
		
		while(curBlock==null || ((curBlock.canDown() && curBlock.canLeft() && curBlock.canUp() && curBlock.canRight()) != false) ){
			randomRow = randomGenerator.nextInt(currentBoard.myLength);
			randomCol = randomGenerator.nextInt(currentBoard.myWidth);
			currentBoard.canMove(randomRow, randomCol);
			curBlock = currentBoard.myBlocks[randomRow][randomCol];	
			System.out.println("ping 5");
		}
		System.out.println("is the block null?: "+ currentBoard.myBlocks[randomRow][randomCol].getName());
		currentBoard.canMoveAll();
		Board[] possibleMoves = currentBoard.shiftBoards(currentBoard.myBlocks[randomRow][randomCol]);
		int ranPosMove = randomGenerator.nextInt(4);
		while(possibleMoves[ranPosMove]==null){
			ranPosMove = randomGenerator.nextInt(4);
			System.out.println("ping 6");
		}
		Board ranBoard = possibleMoves[ranPosMove];
		return ranBoard;
	}
	
	public Stack<Board> newBoards(){
		Board copy = new Board(this.copy(),this.myLength,this.myWidth);
		Stack<Board> newBoards= new Stack<Board>();
		Board[] curArray = new Board[4];

		for(int i=0;i<copy.myLength;i++){
			for(int j=0;j<copy.myWidth;j++){
				if(copy.myBlocks[i][j]!=null){
					//makes the new boards
					copy.canMove(i, j);
					curArray=copy.shiftBoards(copy.myBlocks[i][j]);
					//save the new boards in the Stack
					for(int n=0;n<4;n++){
						if(curArray[n]!=null){
							newBoards.push(curArray[n]);
						}
					}
					// erases the old block position
					for(int leg=0; leg<copy.myBlocks[i][j].getHeight()-1;leg++){
						for(int wid=0; wid<copy.myBlocks[i][j].getWidth()-1;wid++){
							copy.myBlocks[copy.myBlocks[i][j].gettopRow()+leg][copy.myBlocks[i][j].getleftCol()+wid]=null;
						}
					}
				}
			}
		}
		return newBoards;
	}
	
	public Board[] shiftBoards(Block start){
		//called somewhere, moves block if canMove passes
		Board left=null;
		Board right = null;
		Board up = null;
		Board down = null;
		if (this.myBlocks[start.gettopRow()][start.getleftCol()].canLeft()){
			left=new Board(this.shift(start,0,-1), this.myLength, this.myWidth);
			left.canMoveAll();
		}
		if (this.myBlocks[start.gettopRow()][start.getleftCol()].canRight()){
			right=new Board(this.shift(start,0,1), this.myLength, this.myWidth);
			right.canMoveAll();
		}
		if (this.myBlocks[start.gettopRow()][start.getleftCol()].canUp()){
			up=new Board(this.shift(start,-1,0), this.myLength, this.myWidth);
			up.canMoveAll();
		}	
		if (this.myBlocks[start.gettopRow()][start.getleftCol()].canDown()){
			down=new Board(this.shift(start,1,0), this.myLength, this.myWidth);
			down.canMoveAll();
		}
		Board[] boards= new Board[4];
			boards[0]=left;
			boards[1]=right;
			boards[2]=up;
			boards[3]=down;
					
		return boards;
	}
	Block[][] shift(Block start,int leg, int wid){
		Block[][] copy = this.copy();
		int left = start.getleftCol()+wid;
		int bot = start.getbotRow()+leg;
		int top = start.gettopRow()+leg;
		int right = start.getrightCol()+wid;
		String posString = top + " "+ left + " " + bot + " " + right;
		Block shiftedBlock = new Block(posString);
		// erases the old block position
		for(int i=0; i<start.getHeight();i++){
			for(int j=0; j<start.getWidth();j++){
				copy[start.gettopRow()+i][start.getleftCol()+j]=null;
			}
		}
		// writes in the new block position
			for(int i=+leg; i<start.getHeight()+leg;i++){
				for(int j=+wid; j<start.getWidth()+wid;j++){
					copy[start.gettopRow()+i][start.getleftCol()+j]=shiftedBlock;
				}
			}
			
		return copy;
	}
	Block[][] copy(){
		Block[][] myCopy = new Block[this.myLength][this.myWidth];
		for(int i=0; i<this.myLength;i++){
			for(int j=0; j<this.myWidth;j++){
				myCopy[i][j] = this.myBlocks[i][j];
			}
		}
		return myCopy;	
	}
	public String toString(){
		String curString= "";
		for(int i=0;i<this.myLength;i++){
			curString+="\n";
			for(int j=0;j<this.myWidth;j++){
				if(this.myBlocks[i][j]!=null){	
				curString+= this.myBlocks[i][j].getName()+"   ";
				}
				else{
					curString+="  blank  ";
				}
				}
			}
		
		return curString;
	}

}
