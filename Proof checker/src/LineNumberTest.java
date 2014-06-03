import junit.framework.TestCase;

public class LineNumberTest extends TestCase {

	public void testConstructor ( ) {
		// Testing string convertor constructor.
		LineNumber x = new LineNumber("1.4.3");
		assertEquals("1.4.3", x.toString());
		LineNumber y = new LineNumber("1.4.3.4.5");
		assertEquals("1.4.3.4.5", y.toString());
		LineNumber z = new LineNumber("1.4.3.2.2.4.3.3");
		assertEquals("1.4.3.2.2.4.3.3", z.toString());
		
		// Testing regular constructor with no argument.
		LineNumber a = new LineNumber();
		assertEquals("1", a.toString());
		a = a.next();
		assertEquals("2", a.toString());
		a = a.next();
		assertEquals("3", a.toString());
		a = a.next();
		assertEquals("4", a.toString());
	}
	
	public void testNext ( ) {
		// Testing our next method with various cases of moving into inner
		// and outer classes.
		LineNumber x = new LineNumber("1.4.3");
		assertEquals("1.4.3", x.toString());
		x = x.next();
		assertEquals("1.4.4", x.toString());
		
		// Testing the outer number and then calling next to check for proper
		// functioning.
		x = x.outerNumber();
		assertEquals("1.5", x.toString());
		x = x.next();
		assertEquals("1.6", x.toString());
		x = x.outerNumber();
		assertEquals("2", x.toString());
		
		// Testing next method normally.
		x = x.next();
		assertEquals("3", x.toString());
		x = x.next();
		assertEquals("4", x.toString());
		x = x.next();
		assertEquals("5", x.toString());
		x = x.next();
		assertEquals("6", x.toString());
		x = x.next();
		assertEquals("7", x.toString());
		x = x.next();
		assertEquals("8", x.toString());
		x = x.next();
		assertEquals("9", x.toString());
		x = x.next();
		assertEquals("10", x.toString());
		
		// Testing next method on big number.
		LineNumber eff = new LineNumber("99.99");
		assertEquals("99.99", eff.toString());
		eff = eff.next();
		assertEquals("99.100", eff.toString());
		eff = eff.next();
		assertEquals("99.101", eff.toString());
	}
	
	public void testOuter ( ) {
		// Tests the outer method, where we move out after subproofs,
		// going all the way back out.
		LineNumber z = new LineNumber("1.4.3.2.2.4.3.3");
		assertEquals("1.4.3.2.2.4.3.3", z.toString());
		z = z.outerNumber();
		assertEquals("1.4.3.2.2.4.4", z.toString());
		z = z.outerNumber();
		assertEquals("1.4.3.2.2.5", z.toString());
		z = z.outerNumber();
		assertEquals("1.4.3.2.3", z.toString());
		z = z.outerNumber();
		assertEquals("1.4.3.3", z.toString());
		z = z.outerNumber();
		assertEquals("1.4.4", z.toString());
		z = z.outerNumber();
		assertEquals("1.5", z.toString());
		z = z.outerNumber();
		assertEquals("2", z.toString());
		
		// Testing border case of trying to move out of a subproof when there
		// are no subproofs.
		z = z.outerNumber();
		assertEquals(0, z.size());
	}
	
	public void testInner ( ) {
		// Tests the inner method, where we move in for subproofs.
		LineNumber x = new LineNumber();
		assertEquals("1", x.toString());
		x = x.innerNumber();
		assertEquals("1.1", x.toString());
		x = x.innerNumber();
		assertEquals("1.1.1", x.toString());
		x = x.innerNumber();
		assertEquals("1.1.1.1", x.toString());
		x = x.innerNumber();
		assertEquals("1.1.1.1.1", x.toString());
		x = x.innerNumber();
		assertEquals("1.1.1.1.1.1", x.toString());
		x = x.innerNumber();
		assertEquals("1.1.1.1.1.1.1", x.toString());
		
		// Checking to see if the case works properly when they are not just all ones.
		x = x.next();
		assertEquals("1.1.1.1.1.1.2", x.toString());
		x = x.innerNumber();
		assertEquals("1.1.1.1.1.1.2.1", x.toString());
		
		// Bigger number cases.
		LineNumber g = new LineNumber("4.9.13");
		g = g.innerNumber();
		assertEquals("4.9.13.1", g.toString());
	}
	
	public void testSubproof ( ) {
		// Tests the subproof method, where we return the Line Number where the 
		// subproof was started for the current statement.
		LineNumber x = new LineNumber("1.4.4");
		// For instance, if we wanted to check whether the subproof would be completed
		// with this line, we would check if the expression for 1.4.4 matches the
		// expression from 1.4
		LineNumber z = x.subproofCheck();
		assertEquals("1.4", z.toString());
		
		// Testing a bigger case.
		LineNumber g = new LineNumber("1.4.4.4.4.4.4.4.4.4.4.4.4");
		LineNumber h = g.subproofCheck();
		assertEquals("1.4.4.4.4.4.4.4.4.4.4.4", h.toString());
		
		// Trying to test a subproof when the LineNumber is currently not in a subproof
		// should return the first line number.
		LineNumber a = new LineNumber("5");
		LineNumber b = a.subproofCheck();
		assertEquals("1", b.toString());
	}

	public void testString ( ) {
		// toString method is already tested throughout the Junit file, but just in case:
		LineNumber g = new LineNumber("1.4.4.4.4.4.4.4.4.4.4.4.4");
		assertEquals("1.4.4.4.4.4.4.4.4.4.4.4.4", g.toString());
		LineNumber a = new LineNumber("5");
		assertEquals("5", a.toString());
	}

}