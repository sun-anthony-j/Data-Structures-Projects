import static org.junit.Assert.*;

import org.junit.Test;


public class ExpressionTest {

	
	
		//These tests have correctly constructed Expressions and should pass

	@Test
	public void testCorrectExpressions(){
		
		String s;
		Expression e;
		
		//Test primitive expressions
		s = new String("p");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}
		

		s = new String("~p");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}

		//Test parenthesized expressions that enclose &, |, and =>
		s = new String("(p&q)");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}

		s = new String("(p|q)");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		s = new String("~(p|q)");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}

		s = new String("(p=>q)");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}

		// Two further test-directly from "proofs" in background section
		s = new String("(((p=>q)=>q)=>((q=>p)=>p))");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}

		s = new String("((p=>q)=>q)");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}

		
		// More examples involving ~
		s = new String("~~~~~~~~~~~~~~~~~~~~~~~~~~~p");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		
		s = new String("~~~~~~(p&q)");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		
		s = new String("~((a&b)=>~~b)");
		try {
			e = new Expression(s);
		} catch(IllegalLineException ex) {
			fail(ex.getMessage());
		}
	}
	
		//These tests contain incorrectly construed Expressions
	

	public void testIncorrectExpressions(){
		
		String s;
		Expression e;
		

		//empty string
		s=new String("");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		//empty space
		s=new String(" ");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		//space inside expression at beginning
		s=new String(" p");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		//space inside expression at end
		s=new String("p ");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		/*
		//capital letter inside expression. REMOVED DUE TO BEING HANDLED BY InputSource.
		s=new String("P");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		s=new String("Q");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		s=new String("(P|Q");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		*/
		
		//not parenthesized expression with an operator
		s=new String("p&q");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		s=new String("p|q)");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("(p|q");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("p=>q");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		//invalid operator
		s=new String("(p+q)");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		s=new String("(p-q)");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		
		//Misconstructed exoressiouns
		s=new String("&");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("bc");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("p&");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("&p");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("(p&)");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("(&p)");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		s=new String("pp");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("ppp");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("~pp");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("~&");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		s=new String("~~");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		//incorrect parentheses when using subexpressions
		s=new String("(((p=>q)=>q)=>q=>p=>p");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		s=new String("(((p=>q)=>q)=>((q=>p)=>p)");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
		
		s=new String("(p=>q=>q)");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		s = new String("~p=>~q");
		try {
			e = new Expression(s);
			fail();
		} catch(IllegalLineException ex) {
			System.out.println(ex);
		}
		
	}
	
	@Test 
	public void testgetRight(){
		
		Expression x,y;
		
		try{
			x = new Expression("(p=>q)");
			y = new Expression("q");
			assertTrue(x.getRight().equals(y));
			
			x = new Expression("q");
			y = new Expression("q");
			assertTrue(x.getRight().equals(y.getRight()));
			
			x = new Expression("~q");
			y = new Expression("q");
			x = x.getRight();
			System.out.println(x);
			System.out.println(y);
			assertTrue(x.equals(y));
			
			x = new Expression("(p=>q)");
			y = new Expression("(p=>q)");
			assertTrue(x.equals(y));
			
		}catch(IllegalLineException e){
			fail(e.getMessage());
		}
	}
	
	@Test 
	public void testgetLeft(){
		
		Expression x,y;
		
		try{
			x = new Expression("(p=>q)");
			y = new Expression("p");
			assertTrue(x.getLeft().equals(y));
			
			x = new Expression("q");
			y = new Expression("q");
			assertTrue(x.getLeft().equals(y.getLeft()));
			
			x = new Expression("~q");
			y = new Expression();
			assertTrue(x.getLeft().equals(y));
			
			x = new Expression("(p=>q)");
			y = new Expression("(p=>q)");
			assertTrue(x.getLeft().equals(y.getLeft()));
			
		}catch(IllegalLineException e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testNot(){
		Expression x,y;
		
		try{
			x=new Expression("a");
			y=new Expression("~a");
			assertTrue(x.not().equals(y));
			
			y=new Expression("~~a");
			assertTrue(x.not().not().equals(y));
			
			x=new Expression("(p=>q)");
			y=new Expression("~(p=>q)");
			assertTrue(x.not().equals(y));
			
			
		}catch (IllegalLineException e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testEquals(){
		
		Expression x,y;
		
		try{
			x = new Expression("p");
			y = new Expression("p");
			assertTrue(x.equals(y));

			x= new Expression("((p&q)=>~~~~q)");
			y =new Expression("((p&q)=>~~~~q)");
			assertTrue(x.equals(y));
			
			x = new Expression();
			y = new Expression();
			assertTrue(x.equals(y));
			
			x = new Expression("(~p=>q)");
			y = new Expression("(~p=>q)");
			assertTrue(x.equals(y));
			
			x = new Expression("(~p=>q)");
			y = new Expression("(~p=>~q)");
			assertFalse(x.equals(y));
			
			x = new Expression("~~p");
			y = new Expression("~p");
			assertFalse(x.equals(y));
		
			x = new Expression("~q");
			y = new Expression("q");
			System.out.println(y);
			System.out.println(x.getRight());
			assertTrue(x.getRight().equals(y));
			
			//Should be q for both, shows that expressions don't have to be the same
			x = new Expression("(~p&q)");
			y = new Expression("(p&q)");
			assertTrue(x.getRight().equals(y.getRight()));
			
			x = new Expression("(p&q)");
			y = new Expression("(p&a)");
			assertFalse(x.getRight().equals(y.getRight()));
			
			//Should be p for both, shows that expressions don't have to be the same
			x = new Expression("(p&q)");
			y = new Expression("(p&~q)");
			assertTrue(x.getLeft().equals(y.getLeft()));
			
			x = new Expression("(~p&q)");
			y = new Expression("(p&q)");
			assertFalse(x.getLeft().equals(y.getLeft()));
			
			
			//Tests to see if expressions returned by not method work with equals
			x= new Expression ("a");
			y= x.not();
			assertFalse(x.equals(y));
			
			
			x= new Expression ("~a");
			assertTrue(x.equals(y));
			}
		
			
			catch(IllegalLineException e){
				fail(e.getMessage());
		}
	}
}
