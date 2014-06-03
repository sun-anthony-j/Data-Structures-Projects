import java.util.*;

 class LineNumber {
	
	// Our class dealing with LineNumber is essentially an iterator using
	// an ArrayList as a data structure to deal with moving in and out of 
	// subproofs. Ultimately, ArrayLists proved to be the easiest way due 
	// to their ability to change sizes, which made it very easy to deal with
	// them this way.
	
	private ArrayList<Integer> lineNumbers;
	
	 LineNumber ( ) {
		// Starts the line numbers at 1.
		lineNumbers = new ArrayList<Integer>();
		lineNumbers.add(1);
		//out System.out.println(this.toString());
	}
	
	 LineNumber ( LineNumber input) {
		//Copies the line number
		this(input.toString());
		//out System.out.println(this.toString());
	}
	
	 LineNumber (String str) {
		// Given a string (for example, "1.4.3"), returns a Line Number object
		// with that line number.
		lineNumbers = new ArrayList<Integer>();
		String[] numbers = str.split("\\.");
		for (int i = 0; i < numbers.length; i++) {
			lineNumbers.add(Integer.parseInt(numbers[i]));
		}
		//out System.out.println(this.toString());
	}

	 LineNumber next ( ) {
		// Moves to the next line number.
		LineNumber output = new LineNumber(this);
		Integer temp = output.lineNumbers.get(output.lineNumbers.size()-1);
		output.lineNumbers.set((output.lineNumbers.size()-1), (temp + 1));
		return output;
	}
	
	 LineNumber innerNumber ( ) {
		// Moves into the next inner subproof.
		LineNumber output = new LineNumber(this);
		output.lineNumbers.add(1);
		return output;		
	}
	
	 LineNumber outerNumber ( ) {
		// Moves out of the current subproof into the next outer proof.
		// Note to team:
		// Error may need to be handled here if you try to call outerNumber on a
		// LineNumber that is already at the size of 1 (the outermost line number).
		// Currently, this method will remove the last number anyway.
		LineNumber output=new LineNumber(this);
		output.lineNumbers.remove(output.lineNumbers.size() - 1);
		if (output.lineNumbers.size() >= 1) {
			output.lineNumbers.set(output.lineNumbers.size() - 1, 
			output.lineNumbers.get(output.lineNumbers.size() - 1) + 1);
		}
		return output;
	}
	
	 LineNumber subproofCheck ( ) {
		// Returns the LineNumber of the previous show statement.
		if (lineNumbers.size()==1){
			return new LineNumber("1");
		}
		LineNumber output = new LineNumber(this);
		output.lineNumbers.remove(lineNumbers.size() - 1);
		return output;
	}
	
	 public String toString ( ) {
		// Returns a string of the current line number.
		// Note to team:
		// I'm not sure what way you guys would want this or whether or not
		// it would be useful. Let me know how/what way you would want this 
		// implemented, if at all.
		String str = lineNumbers.get(0).toString();
		for (int i = 1; i < lineNumbers.size(); i++) {
			str = str + "." + lineNumbers.get(i).toString();
		}
		return str;
	}
	
	 int size ( ) {
		// Size method used for testing an edge case.
		return this.lineNumbers.size();
	}
}