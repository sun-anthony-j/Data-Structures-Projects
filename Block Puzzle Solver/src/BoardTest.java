import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

public class BoardTest {

	@Test
	public void testCanMove() {
		System.out.println("CanMove Test: ");
		String[] args = new String[2];
		args[0]="Test_Boards/2x1.one.block.txt";
		args[1]="Test_Boards/2x1.one.block.goal.txt";
		BoardStateReader easyTest2x1OneBlock = new BoardStateReader(args);
		System.out.println(easyTest2x1OneBlock.startingBoard().toString());
		easyTest2x1OneBlock.startingBoard().canMove(0,0);
		Block cur = easyTest2x1OneBlock.startingBoard().myBlocks()[0][0];
		System.out.println("CanDown: " + easyTest2x1OneBlock.startingBoard().myBlocks()[0][0].canDown());
		System.out.println("CanUp: " + easyTest2x1OneBlock.startingBoard().myBlocks()[0][0].canUp());
		System.out.println("CanRight: " + easyTest2x1OneBlock.startingBoard().myBlocks()[0][0].canRight());
		System.out.println("CanLeft: " + easyTest2x1OneBlock.startingBoard().myBlocks()[0][0].canLeft());
		assertTrue(true);
	}
	@Test
	public void testshiftBoards() {
		System.out.println("ShiftBoards Test: ");
		String[] args = new String[2];
		args[0]="Test_Boards/2x1.one.block.txt";
		args[1]="Test_Boards/2x1.one.block.goal.txt";
		BoardStateReader easyTest2x1OneBlock = new BoardStateReader(args);
		System.out.println(easyTest2x1OneBlock.startingBoard().toString());
		easyTest2x1OneBlock.startingBoard().canMove(0,0);
		Board[] testBoards = easyTest2x1OneBlock.startingBoard().shiftBoards(easyTest2x1OneBlock.startingBoard().myBlocks()[0][0]);
		System.out.println(testBoards[3].toString());
		assertTrue(true);
	}
	@Test
	public void testnewBoards(){
		System.out.println("NewBoards Test: ");
		String[] args = new String[2];
		args[0]="Test_Boards/2x1.one.block.txt";
		args[1]="Test_Boards/2x1.one.block.goal.txt";
		BoardStateReader easyTest2x1OneBlock = new BoardStateReader(args);
		System.out.println(easyTest2x1OneBlock.startingBoard().toString());
		Board testBoard = easyTest2x1OneBlock.startingBoard();
		Stack<Board> testStack = testBoard.newBoards();
			System.out.println("testStack.pop(): " + testStack.pop());
		
		assertTrue(true);
	}
	@Test
	public void testnewRandomBoard(){
		System.out.println("newRandomBoard() Test: ");
		String[] args = new String[2];
		args[0]="Test_Boards/2x1.one.block.txt";
		args[1]="Test_Boards/2x1.one.block.goal.txt";
		BoardStateReader easyTest2x1OneBlock = new BoardStateReader(args);
		Board goal = easyTest2x1OneBlock.startingBoard().newRandomBoard(easyTest2x1OneBlock.startingBoard());
		System.out.println(goal.toString());
		assertTrue(true);
	}

}
