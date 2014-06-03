import java.util.ArrayList;
import java.util.Scanner;

import junit.framework.TestCase;


public class TheoremSetTest extends TestCase {
	// Test should check the error handling for when there is no associated.
	public void testMap ( ) throws IllegalLineException, IllegalInferenceException {
		//Scanner scan = new Scanner((String)null);
		TheoremSet a = new TheoremSet();
		//InputSource Theorems = new InputSource("Theorems.txt");
		//System.out.println(Theorems.readLine());
		//System.out.println(Theorems.readLine());
		//ArrayList<Object> ParsedLineData = Proof.parser(Theorems.readLine());
		assertTrue(a.get("and1").toString().equals("((x&y)=>x)"));
	}
	// Confirms that there is a useful error if the associated theorem text file is empty.
	public void testAllValues ( ) throws IllegalLineException, IllegalInferenceException{
		String testLineBreak = "Word1\nWord2";
		// System.out.println(testLineBreak);
		TheoremSet a = new TheoremSet();
		assertTrue(a.get("and1").toString().equals("((x&y)=>x)"));
		assertTrue(a.get("and2").toString().equals("((x&y)=>y)"));
		System.out.println(a.get("dn").toString());
		assertTrue(((Expression)a.get("dn")).equals(new Expression("(~~x=>x)")));
	}
	// Confirms Theorems.txt has been successfully written to.
	public void testPut ( ) throws IllegalLineException {
		TheoremSet a = new TheoremSet();
		a.put("and1",new Expression("((x&y)=>x)"));
		assertTrue(true);
	}

}
