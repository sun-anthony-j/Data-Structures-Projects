import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Scanner;

import junit.framework.TestCase;


public class ProofTest extends TestCase {
	public void testParse() throws IllegalLineException, IllegalInferenceException{
		Proof testProof = new Proof();
		
		try {
			Object[] a = testProof.parser("show (q=>q)");
			assertTrue(a[0].toString().equals("show"));
			assertTrue(a[1].toString().equals("(q=>q)"));
		} catch (IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		try {
			Object[] b = testProof.parser("mp 3.2 3.1 ~a");
			assertTrue(b[0].toString().equals("mp"));
			assertTrue(b[1].toString().equals("3.2"));
			assertTrue(b[2].toString().equals("3.1"));
			assertTrue(b[3].toString().equals("~a"));
			
		} catch (IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		try {
			Object[] c = testProof.parser("mt 3.2.1 3.1 ~q");
			assertTrue(c[0].toString().equals("mt"));
			assertTrue(c[1].toString().equals("3.2.1"));
			assertTrue(c[2].toString().equals("3.1"));
			assertTrue(c[3].toString().equals("~q"));
			
		} catch (IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		try {
			Object[] d = testProof.parser("print");
			assertTrue(d[0].toString().equals("print"));
		} catch (IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		try {
			Object[] e = testProof.parser("ic 3 (a=>~~a)");
			assertTrue(e[0].toString().equals("ic"));
			assertTrue(e[1].toString().equals("3"));
			assertTrue(e[2].toString().equals("(a=>~~a)"));
		} catch (IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		try {
			Object[] f = testProof.parser("co 2 3.3 ~~a");
			assertTrue(f[0].toString().equals("co"));
			assertTrue(f[1].toString().equals("2"));
			assertTrue(f[2].toString().equals("3.3"));
			assertTrue(f[3].toString().equals("~~a"));
		} catch (IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		try {
			Object[] g = testProof.parser("repeat 3.1 (p=>q)");
			assertTrue(g[0].toString().equals("repeat"));	
			assertTrue(g[1].toString().equals("3.1"));	
			assertTrue(g[2].toString().equals("(p=>q)"));	
		} catch (IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		try {
			Object[] h = testProof.parser("assume ~a");
			assertTrue(h[0].toString().equals("assume"));	
			assertTrue(h[1].toString().equals("~a"));
		} catch (IllegalLineException ex) {
			fail(ex.getMessage());
		}
		
		// This test fails as it should.
		//	try {
		//		Object[] x = testProof.parser("print 1.3.3");
		//	} catch (IllegalLineException ex) {
		//		fail(ex.getMessage());
		//	}
	}
	
	public void testExtendProof() throws IllegalLineException, IllegalInferenceException{
		System.out.println("testExtendProof");
		Proof testProof = new Proof();
		testProof.extendProof("show (q=>q)");
		//System.out.println(testProof.toString());
		testProof.extendProof("assume q");
		//System.out.println(testProof.toString());
		testProof.extendProof("ic 2 (q=>q)");
		//System.out.println(testProof.toString());
		assertTrue(testProof.isComplete());
		
		// Tests a longer proof.
		Proof testProof2 = new Proof();
		testProof2.extendProof("show (((p=>q)=>q)=>((q=>p)=>p))"); // Testing show
		//System.out.println(testProof2.toString());
		testProof2.extendProof("assume ((p=>q)=>q)");
		//System.out.println(testProof2.toString());
		testProof2.extendProof("show ((q=>p)=>p)");
		//System.out.println(testProof2.toString());
		testProof2.extendProof("assume (q=>p)"); // Testing assume
		//System.out.println(testProof2.toString());
		testProof2.extendProof("show p");
		//System.out.println(testProof2.toString());
		testProof2.extendProof("assume ~p");
		//System.out.println(testProof2.toString());
		testProof2.extendProof("mt 3.2.1 3.1 ~q"); // Testing MT
		//System.out.println(testProof2.toString());
		testProof2.extendProof("mt 2 3.2.2 ~(p=>q)");
		//System.out.println(testProof2.toString());
		testProof2.extendProof("show (p=>q)");
		//System.out.println(testProof2.toString());
		testProof2.extendProof("assume p");
		//System.out.println(testProof2.toString());
		testProof2.extendProof("co 3.2.4.1 3.2.1 (p=>q)"); // Testing contradiction
		//System.out.println(testProof2.toString());
		testProof2.extendProof("co 3.2.4 3.2.3 p");
		//System.out.println(testProof2.toString());
		testProof2.extendProof("ic 3.2 ((q=>p)=>p)"); // Testing IC
		//System.out.println(testProof2.toString());
		testProof2.extendProof("ic 3 (((p=>q)=>q)=>((q=>p)=>p))");
		//System.out.println(testProof2.toString());
		testProof2.extendProof("print");
		assertTrue(testProof2.isComplete());
		
		// Tests repeat
		Proof testProof3 = new Proof();
		testProof3.extendProof("show (p=>p)");
		//System.out.println(testProof2.toString());
		testProof3.extendProof("show (p=>p)");
		//System.out.println(testProof2.toString());
		testProof3.extendProof("assume p");
		//System.out.println(testProof2.toString());
		testProof3.extendProof("ic 2.1 (p=>p)");
		//System.out.println(testProof2.toString());
		testProof3.extendProof("repeat 2 (p=>p)");
		//System.out.println(testProof2.toString());
		assertTrue(testProof3.isComplete());
		System.out.println(testProof3.toString());
	}

	public void testIsComplete() throws IllegalLineException, IllegalInferenceException{
		System.out.println("testIsComplete");
		Proof testProof1 = new Proof();
		System.out.println("testProof1:");
		testProof1.extendProof("show (q=>q)");
		testProof1.extendProof("assume q");
		testProof1.extendProof("ic 2 (q=>q)");
		//System.out.println(testProof1.toString());
		System.out.println("testProof2:");
		assertTrue(testProof1.isComplete());
		Proof testProof2 = new Proof();
		testProof2.extendProof("show (q=>q)");
		testProof2.extendProof("show (q=>q)");
		testProof2.extendProof("assume q");
		assertFalse(testProof2.isComplete());
		testProof2.extendProof("ic 2.1 (q=>q)");
		//System.out.println(testProof2.toString());
		assertFalse(testProof2.isComplete());
		testProof2.extendProof("repeat 2 (q=>q)");
		//System.out.println(testProof2.toString());
		assertTrue(testProof2.isComplete());
	
	}
	public void testToString() throws IllegalLineException, IllegalInferenceException{
		System.out.println("testToString");
		Proof testProof = new Proof();
		testProof.extendProof("show (q=>q)");
		//System.out.println(testProof.toString());
		testProof.extendProof("assume q");
		//System.out.println(testProof.toString());
		testProof.extendProof("ic 2 (q=>q)");
		//System.out.println(testProof.toString());
		String strSame ="1 show (q=>q)\n2 assume q\n3 ic 2 (q=>q)\n";
		//System.out.println(strSame);
		assertTrue(testProof.toString().equals(strSame));
	}
	public void testLineStatement() throws IllegalLineException, IllegalInferenceException{
		
		//Show and assume are implicitly tested
		System.out.println("testMT");
		Proof testProof = new Proof();
		testProof.extendProof("show ((q=>p)=>p)");
		testProof.extendProof("assume (q=>p)");
		testProof.extendProof("show p");
		testProof.extendProof("assume ~p");
		testProof.extendProof("mt 2 3.1 ~q");
		
		System.out.println("testMP");
		Proof testMP = new Proof();
		testMP.extendProof("show ((~q=>p)=>p)");
		testMP.extendProof("assume (~q=>p)");
		testMP.extendProof("show q");
		testMP.extendProof("assume ~q");
		testMP.extendProof("mp 2 3.1 p");
		
		
		System.out.println("testIC and CO");
		Proof testIC = new Proof();
		testIC.extendProof("show (p=>(~p=>q))");
		testIC.extendProof("assume p");
		testIC.extendProof("show (~p=>q)");
		testIC.extendProof("assume ~p");
		testIC.extendProof("co 2 3.1 (~p=>q)");
		testIC.extendProof("ic 3 (p=>(~p=>q))");
		
		System.out.println("testrepeat");
		Proof testRepeat = new Proof();
		testRepeat.extendProof("show (p=>p)");
		testRepeat.extendProof("show (p=>p)");
		testRepeat.extendProof("assume p");
		testRepeat.extendProof("ic 2.1 (p=>p)");
		testRepeat.extendProof("repeat 2 (p=>p)");
		
		System.out.println("testTheorem");
		Proof testTheorem = new Proof();
		testTheorem.extendProof("show (~~x=>x)");
		testTheorem.extendProof("and1 ((x&y)=>x)");
		testTheorem.extendProof("and1 (((a|b)&~c)=>(a|b))");
		testTheorem.extendProof("and2 (((a|b)&~c)=>~c)");
		System.out.println(testTheorem.toString());
		try{testTheorem.extendProof("and1 (((a|b)&~c)=>~c)");
			}catch (IllegalLineException e) {
				System.out.println (e.getMessage());
            }catch (IllegalInferenceException e) {
                System.out.println (e.getMessage());
            }
		try{testTheorem.extendProof("and3 (((a|b)&~c)=>~c)");
		}catch (IllegalLineException e) {
			System.out.println (e.getMessage());
        }catch (IllegalInferenceException e) {
            System.out.println (e.getMessage());
        }
		testTheorem.extendProof("dn (~~x=>x)");
		

		
		
		/*
		testProof.extendProof("show (a=>~~a)");
		//System.out.println(testProof.toString());
		testProof.extendProof("assume a");
		//System.out.println(testProof.toString());
		testProof.extendProof("show ~~a");
		testProof.extendProof("assume ~~~a");
		testProof.extendProof("dn (~~~a+>~a)");
		testProof.extendProof("mp 3.2 3.1 ~a");
		testProof.extendProof("co 2 3.3 ~~a");
		testProof.extendProof("ic 3 (a=>~~a)");
		//System.out.println(testProof.toString());
		//String strSame ="1 show (q=>p)\n2 assume q\n3 ic 2 (q=>p)\n";
		//System.out.println(strSame);
		//assertTrue(testProof.toString().equals(strSame));
		
		*/
	}
}