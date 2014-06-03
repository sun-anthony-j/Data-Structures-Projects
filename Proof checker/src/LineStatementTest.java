import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Scanner;

import junit.framework.TestCase;


public class LineStatementTest extends TestCase {
	//Show and assume are implicitly tested
	//If a line fails, the runtime should crash.
	//Bad lines are in a try/catch block.
	
	public void testMT() throws IllegalLineException, IllegalInferenceException{
	
		System.out.println("testMT");
		Proof testMT = new Proof();
		testMT.extendProof("show ((q=>p)=>p)");
		testMT.extendProof("assume (q=>p)");
		testMT.extendProof("show p");
		testMT.extendProof("assume ~p");
		try{
			testMT.extendProof("mt 2 2 ~q");
		}catch (IllegalLineException e) {
            System.out.println (e.getMessage());
        }catch (IllegalInferenceException e) {
            System.out.println (e.getMessage());
        }
		try{
			testMT.extendProof("mt 2 3.1 q");
		}catch (IllegalLineException e) {
            System.out.println (e.getMessage());
        }catch (IllegalInferenceException e) {
            System.out.println (e.getMessage());
        }
		testMT.extendProof("mt 2 3.1 ~q");
		testMT.extendProof("mt 3.1 2 ~q");
	}
	
	public void testMP() throws IllegalLineException, IllegalInferenceException{
		System.out.println("testMP");
		Proof testMP = new Proof();
		testMP.extendProof("show ((~q=>p)=>p)");
		testMP.extendProof("assume (~q=>p)");
		testMP.extendProof("show q");
		testMP.extendProof("assume ~q");
		try{
			testMP.extendProof("mp 2 2 p");
		}catch (IllegalLineException e) {
            System.out.println (e.getMessage());
        }catch (IllegalInferenceException e) {
            System.out.println (e.getMessage());
        }
		try{
			testMP.extendProof("mp 2 3.1 q");
		}catch (IllegalLineException e) {
            System.out.println (e.getMessage());
        }catch (IllegalInferenceException e) {
            System.out.println (e.getMessage());
        }
		testMP.extendProof("mp 2 3.1 p");
		testMP.extendProof("mp 3.1 2 p");
	}
	
	public void testICandCO() throws IllegalLineException, IllegalInferenceException{
		System.out.println("testIC and CO");
		Proof testIC = new Proof();
		testIC.extendProof("show (p=>(~p=>q))");
		testIC.extendProof("assume p");
		testIC.extendProof("show (~p=>q)");
		testIC.extendProof("assume ~p");
		testIC.extendProof("co 2 3.1 (~p=>q)");
		testIC.extendProof("co 3.1 2 (~p=>q)");
		testIC.extendProof("co 3.1 2 a");
		try{
			testIC.extendProof("co 2 2 q");
		}catch (IllegalLineException e) {
            System.out.println (e.getMessage());
        }catch (IllegalInferenceException e) {
            System.out.println (e.getMessage());
        }
		testIC.extendProof("ic 3 (p=>(~p=>q))");
		testIC.extendProof("ic 3 (a=>(~p=>q))");
		try{
			testIC.extendProof("ic 3 (p=>(p=>q))");
		}catch (IllegalLineException e) {
            System.out.println (e.getMessage());
        }catch (IllegalInferenceException e) {
            System.out.println (e.getMessage());
        }
	}
	
	public void testRepeat() throws IllegalLineException, IllegalInferenceException{
		System.out.println("testrepeat");
		Proof testRepeat = new Proof();
		testRepeat.extendProof("show (p=>p)");
		testRepeat.extendProof("show (p=>p)");
		testRepeat.extendProof("assume p");
		testRepeat.extendProof("ic 2.1 (p=>p)");
		try{
			testRepeat.extendProof("repeat 1 (p=>p)");
		}catch (IllegalLineException e) {
            System.out.println (e.getMessage());
        }catch (IllegalInferenceException e) {
            System.out.println (e.getMessage());
        }
		try{
			testRepeat.extendProof("repeat 2 q");
		}catch (IllegalLineException e) {
            System.out.println (e.getMessage());
        }catch (IllegalInferenceException e) {
            System.out.println (e.getMessage());
        }
		testRepeat.extendProof("repeat 2 (p=>p)");
		/*
		REMOVED. Cannot load theorems in JUnit.
		Testing moved to Proof test files.
		
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
		*/
	}
}
