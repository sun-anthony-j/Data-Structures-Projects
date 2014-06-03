import java.util.*;


class Proof {
	
	private TheoremSet myTheorems=null;
	private LineNumber nextLine;
	private ArrayList<LineStatement> myStatements;
	private LineStatement currentStatement;
	private boolean justStarting = true;

	 Proof (TheoremSet theorems) {
		//Proof constructor for proofs using theorems
		nextLine = new LineNumber();
		myTheorems=theorems;
		myStatements=new ArrayList<LineStatement>();
	}
	 Proof () {
		//Proof constructor for proofs without theorems
		nextLine = new LineNumber();
		myStatements=new ArrayList<LineStatement>();
	}

	 LineNumber nextLineNumber () {
		//returns the next line number
		return nextLine;
	}
	
	 static Object[] parser(String x) throws IllegalLineException{
		// Parses the user statement by splitting it up by the whitespace and creates a 
		// Statement object with all relevant.
		Scanner scan = new Scanner(x);
		Scanner length = new Scanner(x);
		int len = 0;
		// Determines the length of our Object[] based on the number of elements scanned in.
		while (length.hasNext()) {
			len++;
			length.next();
		}
		length.close();
		
		// Creates an object array with the determined length.
		Object[] ParsedLineData = new Object[len];
		if (!scan.hasNext()) {
			scan.close();
			throw new IllegalLineException ("Input must contain something other than whitespace.");
		}
		// Checks the first statement, the reason.
		String reason = scan.next();
		
		// First checks for print, which must be alone.
		if (reason.equals("print")) {
			if (scan.hasNext()) {
				scan.close();
				throw new IllegalLineException ("Nothing can appear after a print statement.");
			}
		}
		// Stores it in the data structure.
		ParsedLineData[0] = reason;
		
		for (int i = 1; scan.hasNext(); i++) {
			ParsedLineData[i] = scan.next();
		}
		scan.close();
		return ParsedLineData;
	}
	
	
	 void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		// Fist parses the data.
		Object[] ParsedLineData = (Object[]) parser (x);
		String first = (String)ParsedLineData[0];
		Boolean firstmatch=false;
		
		// Checks for the special case of print, which prints the whole proof so far.
		if (first.equals("print")) {
			System.out.println(this.toString());
			return;
		}
		
		// If not a print statement, create an expression from the last statement in the parser.
		Expression expression = new Expression((String)ParsedLineData[ParsedLineData.length - 1]);
	
		
		// Modus ponens, Modus tollens, and contradiction handling.
		if (first.equals("mp") || first.equals("mt") || first.equals("co")) {
			if (ParsedLineData.length == 4) {
				try {
					currentStatement = new LineStatement(first, new LineNumber((String) ParsedLineData[1]), new LineNumber((String) ParsedLineData[2]), expression, nextLine);
				} catch (NumberFormatException ex) {
					System.out.println("At least one argument passed in as LineNumbers (" + ParsedLineData[1] + " " + ParsedLineData[2] + ") were not properly formatted.");
					return;
				}
				nextLine=nextLine.next();
				firstmatch=true;
			} else {
				throw new IllegalLineException("Incorrect number of inputs for " + first);
			}
		}
		
		// Repeat and implication construction handling.
		if (first.equals("repeat") || first.equals("ic")) {
			if (ParsedLineData.length == 3) {
					currentStatement = new LineStatement(first,new LineNumber((String) ParsedLineData[1]), expression, nextLine);
					nextLine=nextLine.next();
					firstmatch=true;
			} else {
				throw new IllegalLineException("Incorrect number of inputs for " + first);
			}	
		}
		
		// Show and assume handling.
		if (first.equals("show") || first.equals("assume")) {
			if (ParsedLineData.length == 2) {
				currentStatement = new LineStatement(first, expression, nextLine);
				if (first.equals("show")) {
					if (justStarting == true) {
						nextLine=nextLine.next();
						justStarting = false;
						firstmatch=true;
					} else {
						nextLine=nextLine.innerNumber();
						firstmatch=true;
					}
				} else {
					nextLine=nextLine.next();
					firstmatch=true;
				}
			} else {
				throw new IllegalLineException("Incorrect number of inputs for " + first);
			}
		}
		// Theorem handling.
		if (myTheorems!=null){
			if (myTheorems.contains(first)) {
				currentStatement = new LineStatement(first, myTheorems.get(first), expression, nextLine);
				firstmatch=true;
			} 
		}
		
		if (firstmatch==false) { 
			throw new IllegalLineException ("First word does not match any inference or theorem name");
		}
		
		// Stores all statements, as they are created.
		myStatements.add(currentStatement);
		
		// Checks if subproof is complete and moves out of subproof if true.
		if (!currentStatement.linenumber.subproofCheck().toString().equals("1") &&
			currentStatement.getaccessible() &&
			currentStatement.linenumber != currentStatement.linenumber.subproofCheck() &&
			currentStatement.exp.equals(getLine(currentStatement.linenumber.subproofCheck()).exp)) {
				getLine(currentStatement.linenumber.subproofCheck()).proved();
				nextLine=nextLine.outerNumber();
		}
		
	}
	
	// Synthesizes our line, theorem, and accepted logic data structures to print all legal proof steps so far.
	 LineStatement getLine (LineNumber check) throws IllegalLineException {
		// Returns the statement given a line number.
		Iterator<LineStatement> iter = myStatements.iterator();
		while (iter.hasNext()) {
			LineStatement current = iter.next();
			if (current.getLineNumber().toString().equals(check.toString())) {
				return current;
			}
		} throw new IllegalLineException("Line number"+ check.toString() +"not found.");
	}

	 class LineStatement {
		//This is a proof line. It contains all the logic information, and all the information to reconstruct the input string.
		
		//These are Statement instance variables. They are not all used depending on the type of statement.
		private String type;
		private Expression E1;
		private Expression E2;
		private Expression inferred;
		private Expression exp;
		private LineNumber logicline1;
		private LineNumber logicline2;
		private LineNumber linenumber;
		private String theoremname;
		private boolean accessible=true;
		
		//These are getter methods.
		 boolean getaccessible(){return accessible;}
		 String gettype(){return type;}
		 Expression getE1(){return E1;}
		 Expression getE2(){return E2;}
		 Expression getinferred(){return inferred;}
		 Expression getexp(){	return exp;}
		 LineNumber getLineNumber(){return linenumber;}
		//This is the setter method for setting show statements and proved and accessible.
		 void proved(){accessible=true;}
		 //This is the method that returns the string of the line.
		 public String toString(){
			String output;
			output = linenumber.toString();
			if (type.equals("theorem")){
				output=output+" "+theoremname;
			}else{output=output+" "+type;}
			if (logicline1 != null){
				output=output+" "+logicline1.toString();
			}
			if (logicline2 != null){
				output=output+" "+logicline2.toString();
			}
			output=output+" "+exp.toString();
			return output;
		}
		
		
		 LineStatement (String s, LineNumber logicline1, LineNumber logicline2, Expression exp, LineNumber line) throws IllegalLineException, IllegalInferenceException{
			//LineStatement constructor for inference types mt,mp,co. Should only be called for these inference type by the extendsProof method.			
			//Assigns values for instance variables.
			type=s;
			this.logicline1=logicline1;
			this.logicline2=logicline2;
			E1=getLine(logicline1).getexp();
			E2=getLine(logicline2).getexp();
			this.linenumber=line;
			this.exp=exp;
			//Checks to see if unaccessible show statements are being referenced and throws the proper exception if true.
			if (getLine(logicline1).accessible==false){
				throw new IllegalInferenceException ("show statement at" +logicline1.toString()+ "not proven");
			}
			if (getLine(logicline2).accessible==false){
				throw new IllegalInferenceException ("show statement at" +logicline2.toString()+ "not proven");
			}
			
			if (s.equals("mt")){
				//Handling for inference type modus tollens.
				 if (E1.getItem().equals("=>")){
				 	if (E2.getItem().equals("~")){
					 	if(E1.getRight().equals(E2.getRight())){
				 			inferred=E1.getLeft().not();
				 			if (inferred.equals(exp)){
								return;
							}else{throw new IllegalInferenceException ("logic infers"+inferred.toString()+",but input inference is"+ exp.toString());}
				 		}
				 	}
				 }
				 if (E2.getItem().equals("=>")){
					 if (E1.getItem().equals("~")){
				 		if(E2.getRight().equals(E1.getRight())){
				 			inferred=E2.getLeft().not();
				 			if (inferred.equals(exp)){
								return;
							}else{throw new IllegalInferenceException ("logic infers"+inferred.toString()+",but input inference is"+ exp.toString());}
				 		}
					 }else {throw new IllegalInferenceException ("modus tollens input error; statements at inputted line numbers do not fit logic criteria");}
				 }else{ throw new IllegalInferenceException ("modus tollens input error; statements at inputted line numbers do not fit logic criteria");}			
			
			} else if (s.equals("mp")) {
				//Handling for inference type modus ponens.
				if (E1.getItem().equals( "=>")){
				 	if(E1.getLeft().equals(E2)){
				 		inferred=E1.getRight();
				 		if (inferred.equals(exp)){
							return;
						}else{throw new IllegalInferenceException ("logic infers"+inferred.toString()+",but input inference is"+ exp.toString());}
					}
				 }
				 if (E2.getItem().equals("=>")){
				 	if(E2.getLeft().equals(E1)){
				 		inferred=E2.getRight();
				 		if (inferred.equals(exp)){
							return;
						}else{throw new IllegalInferenceException ("logic infers"+inferred.toString()+",but input inference is"+ exp.toString());}
			 		}else{ throw new IllegalInferenceException("modus ponens input error; statements at inputted line numbers do not fit logic criteria");}
				 }else{ throw new IllegalInferenceException("modus ponens input error; statements at inputted line numbers do not fit logic criteria");}
			
			}else if (s.equals("co")){
				//Handling for inference type contradiction.
				if (E1.equals(E2.not())||E2.equals(E1.not())){
					inferred=exp;
					if (inferred.equals(exp)){
						return;
					}else{throw new IllegalInferenceException ("logic infers"+inferred.toString()+",but input inference is"+ exp.toString());}
			 	}else{ throw new IllegalInferenceException("Contradiction input error; statements at inputted line numbers do not fit logic criteria");}			
			}else{ throw new IllegalLineException ("extendproof statement constructor usage error"); }
		}
		
		 LineStatement (String s, LineNumber logicline1, Expression exp, LineNumber line) throws IllegalLineException, IllegalInferenceException{
			//Statement constructor for inference types repeat and implication construction. Should only be called for these inference type by the extendsProof method.			
			//Assigns values for instance variables.
			type=s;
			E1=getLine(logicline1).getexp();
			this.exp=exp;
			linenumber=line;
			this.logicline1=logicline1;
			//Checks to see if unaccessible show statements are being referenced and throws the proper exception if true.
			if (getLine(logicline1).accessible==false){
				throw new IllegalInferenceException ("show statement at " +logicline1.toString()+ " not proven");
			}
			if (s.equals("repeat")){
				//Handling for inference type repeat.
				if (E1.equals(exp)){
					return;
				}else{ throw new IllegalInferenceException ("repeat input error; statements at inputted line numbers do not fit logic critera"); }
			}else if (s.equals("ic")){
				//Handling for inference type implication construction.
				if (exp.getItem().equals("=>") && exp.getRight().equals(E1)){
				 	return;
				} else{ throw new IllegalInferenceException ("implication contradiction input error; statements at inputted line numbers do not fit logic critera"); }
			}else{ throw new IllegalLineException ("extendproof statement constructor usage error"); }
		}
		
		 LineStatement (String s, Expression exp, LineNumber line) throws IllegalLineException, IllegalInferenceException{
			//Statement constructor for inference types repeat and implication construction. Should only be called for these inference type by the extendsProof method.			
			//Assigns values for instance variables.
			type=s;
			this.exp=exp;
			this.linenumber=line;
			if (s.equals("show")){
				//Handling for show.
				accessible=false; //Sets show statements as unaccessible
				return;
			}else if (s.equals("assume")){
				//Handling for inference type assume.
				if (myStatements.get(myStatements.size()-1).gettype().equals("show")){
					if (myStatements.get(myStatements.size()-1).exp.getItem().equals("=>")){
						if (exp.equals(myStatements.get(myStatements.size()-1).exp.getLeft())){
							return;
						}
					}
					if (exp.equals(myStatements.get(myStatements.size()-1).exp.not())){
						return;
					}else{throw new IllegalInferenceException ("assumption input error; assumption does not follow from previous show statement");}
				}else{throw new IllegalInferenceException ("assumption input error; assumption must follow directly after show statement");}
			}	
		}
		
		 LineStatement (String theoremname, Expression E1, Expression exp, LineNumber line) throws IllegalInferenceException{
			//Filling in instance variables
			type="theorem";
			this.theoremname=theoremname;
			this.exp=exp;
			this.linenumber=line;
			this.E1=E1;
			//Checks if theorem is applicable to the inputed expression
			if (!E1.theoremEqual(exp)){
				throw new IllegalInferenceException ("theorem"+theoremname+"does not apply to inputted expression: "+exp.toString());
			}
		}
	}

	 
	 public String toString(){
		//Calls toString for every statement in the current proof.
		String StatementLine = "";
		for(int j=0;j<myStatements.size();j++){
			StatementLine+= myStatements.get(j).toString()+"\n";
		}
		return StatementLine;  
	}
	

	 boolean isComplete ( ) {
		// Checks if the line completes the proof.
		// Compare last line of expression to first expression. if equal and legal, it isComplete.
		if(myStatements.size()==1){
			return false;
		}
		if(myStatements.get(myStatements.size()-1).linenumber.size()<=1){
			if (myStatements.get(0).getexp().equals(myStatements.get(myStatements.size()-1).getexp())){
				return myStatements.get(myStatements.size()-1).getaccessible();
			}}	
		return false;
	}
		
}