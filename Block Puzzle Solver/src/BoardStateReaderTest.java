import static org.junit.Assert.*;

import org.junit.Test;


public class BoardStateReaderTest {

	@Test
	public void testConstructor() {
		String[] args = new String[2];
		args[0]="Test_Boards/1x1.txt";
		args[1]="Test_Boards/1x1.goal.txt";
		BoardStateReader easyTest1x1 = new BoardStateReader(args);
		//System.out.println(easyTest1x1.startingBoard().toString());
		assertTrue(easyTest1x1.startingBoard().toString().equals("\n0 0 0 0   "));
		//System.out.println(easyTest1x1.goalBoard().toString());
		assertTrue(easyTest1x1.goalBoard().toString().equals("\n0 0 0 0   "));
	}
	@Test
	public void test1x2OneBlock(){
		String[] args = new String[2];
		args[0]="Test_Boards/1x2.one.block.txt";
		args[1]="Test_Boards/1x2.one.block.goal.txt";
		BoardStateReader easyTest1x2 = new BoardStateReader(args);
		// System.out.println(easyTest1x2.startingBoard().toString());
		assertTrue(easyTest1x2.startingBoard().toString().equals("\n0 0 0 1   0 0 0 1   "));
	}
	@Test
	public void test1x2TwoBlock(){
		String[] args = new String[2];
		args[0]="Test_Boards/1x2.two.blocks.txt";
		args[1]="Test_Boards/1x2.two.blocks.goal.txt";
		BoardStateReader easyTest1x2 = new BoardStateReader(args);
		//System.out.println(easyTest1x2.startingBoard().toString());
		assertTrue(easyTest1x2.startingBoard().toString().equals("\n0 0 0 0   0 1 0 1   "));
	}

}
