import java.util.TreeMap;

class Expression {

	private TreeMap<String, String> VariableMap;
	private TreeNode myRoot;
	
	 Expression ( )  {
		myRoot = null;
	}
	
	 Expression (TreeNode t) {
		myRoot = t;
	}
	
	 Expression(String s) throws IllegalLineException{
		// check to see if Expression contains one or more empty spaces.
		if (s.contains(" ")){  		
			throw new IllegalLineException("Expression \"" +s +"\" cannot contain spaces");
		}
		
		/* Uppercase handling that was removed due to InputSource forcing everything to go to lowercase.
		for (int count=0;count<s.length();count++){
	 		//check to see if Expression contains an Uppercase, ASCII code for uppercase characters
			if (Character.isUpperCase(s.charAt(count))){
				throw new IllegalLineException("Expression \"" +s +"\" should not have UPPERCASE letters.");
			}
		}
		*/
		myRoot=ExpressionTree(s);
	}
	
	
	private TreeNode ExpressionTree(String s) throws IllegalLineException {
		if (s.equals("")){  
			throw new IllegalLineException("Expression or subexpression \"" +s +"\" is blank");
		}
		if (s.charAt(0)!='('){
			// ASCII code for checking for lowercase characters.
			if (s.length()==1 && s.charAt(0)>96 && s.charAt(0)<123){
				return new TreeNode(s);
			}else if (s.length()==1){     
				throw new IllegalLineException("The expression or a subexpression \"" +s +"\" is not a lowercase variable");
			}else if (s.length()==2){         
				// Checks to see if expression is in form ~(lowercase variable).
				// If not, then throw exception.
				if (s.charAt(0)=='~' && s.charAt(1)>96 && s.charAt(1)<123){
					return new TreeNode(s.charAt(0),null,new TreeNode(s.charAt(1)));
					}
				else{						
					throw new IllegalLineException("The expression or subexpression \"" +s +"\" is not in the form ~expression");
				}
				// The expression or subexpression should never be longer than ~p,
	    		// or 2 if it is not in the form ~(expression).
	    	} else if (s.charAt(0) == '~') {
	    		return new TreeNode(s.charAt(0), null, ExpressionTree(s.substring(1, s.length())));
				// The expression or subexpression should never be longer than ~p,
	    		// or 2 if it is not in the form ~(expression) or ~~~~~(expression).
			}else if (s.length()>2){
				throw new IllegalLineException("Subexpression \"" +s +"\" needs to be parenthesized or subexpression \"" +s +"\" is not in form ~expression");
			}else{
				//Should never get to this spot
				return new TreeNode(null);
			}
		} else if (s.charAt(1) == '~' && s.charAt(2) == '(') {
    		return new TreeNode(s.charAt(1), null, ExpressionTree(s.substring(2, s.length()-2)));
		} else {
			// Checks to see if expression beings and ends with a parentheses, if so, this is very similar to the exprTree
			// method that we did in our lab section, except we have an extra variable "addedlength" to account for 
			// the operand =>, which is of length 2 rather than 1.  We account for this in the 3 substrings that we return.
			if (s.charAt(s.length()-1) !=')'){
				throw new IllegalLineException("\"" + s +"\"" +"needs a closing parenthesis!");
			}
			//Nesting keeps track of parenthesis and makes sure the inputted expression is
			//closed.  If nesting is not equal to 0 it means that there is either a missing ")"
			//in the expression or that there is a extra "(".  If Nesting is ever equal to 0
			//then we check the character to see if it is an operand, and set MainOp equal to 
			//the character index if it is the case.  Our addedlength variable is used to account
			//for cases where the operand is =>, since that changes the substrings that we split
			//our expression into and also change the position of the operand.  The loop will never
			//finish in a correct expression, as it should break when the main operand is found.
			int nesting=0;
			int MainOp=0;
			int addedlength=0;
			for (int k=1;k<s.length();k++){
				if (s.charAt(k)=='('){
					nesting++;
				}
				if (s.charAt(k)==')'){
					nesting--;
				}
				
				if (s.charAt(k)=='|' || s.charAt(k)=='&'){
					if (nesting==0){
						MainOp = k;
						break;
					}
				}
				if (s.charAt(k)=='=' && s.charAt(k+1)=='>'){
					if (nesting==0){
						MainOp=k;
						addedlength=1;
						break;
					}
				}
			}
			if (MainOp==0){
				throw new IllegalLineException("No operand  or an incorrect operand(not &, |, or =>) was found in the expression \"" +s);
			}
			if (MainOp<2){
				throw new IllegalLineException("Expression or subexpression \"" +s +"\" cannot start with an operand");
			}
			if (nesting!=0){
				throw new IllegalLineException("Missing a closing parenthesis inside expression \"" +s);
			}
			//recursively call the method again for each "subexpression" inside the main expression
			String subexp1 = s.substring (1, MainOp);
		    String subexp2 = s.substring (MainOp+1+addedlength, s.length()-1);
		    String operand = s.substring (MainOp,MainOp+1+addedlength);
	        return new TreeNode(operand, ExpressionTree(subexp1), ExpressionTree(subexp2));
		}	
	}

	

	 void print ( ) {
		//Prints out Expression in the form of a tree.  The left children 
		//appear at the bottom of the console while the right children are 
		//more upward,  The root of the tree is on the left.
	    if (myRoot != null) {
	        printHelper (myRoot, 0);
	    }
	}
		
	 static final String indent1 = "    ";
		
	 static void printHelper (TreeNode root, int indent) {
		//Helper method for print method
		if (root.myRight != null) {
			printHelper (root.myRight, indent + 1);
		}
	    println (root.myItem, indent);
	    if (root.myLeft != null) {
	    	printHelper (root.myLeft, indent + 1);
	    }
	}
			
	 static void println (Object obj, int indent) {
		//As the name suggests, prints on next line, used to get
		//correct indentation for the trees.
	    for (int k=0; k<indent; k++) {
	        System.out.print (indent1);
	    }
	    System.out.println (obj);
	}
	

	 boolean equals(Expression a){
		// Recursive method to test for equality between Trees.
		return equalsHelper(this.myRoot, a.myRoot);
	}
	
    private boolean equalsHelper(TreeNode ourTree, TreeNode check) {
    	//Helper method for Expression, traverse each node of the tree,
    	//turning the item at the current node into a string and using
    	//the String.equals() method to check for equality.
        if (ourTree == check) {
                return true;
        }
        if (ourTree == null || check == null) {
                return false;
        }
        return ourTree.myItem.toString().equals(check.myItem.toString()) &&
                   equalsHelper(ourTree.myLeft, check.myLeft) &&
                   equalsHelper(ourTree.myRight, check.myRight);
    }
    
     String getItem ( ) {
    	//getter method that returns the object at the current TreeNode.
    	return this.myRoot.myItem.toString();
    }
    
     Expression getLeft ( ) {
    	//getter method that returns the left child of a TreeNode
    	Expression x = new Expression();
    	x.myRoot = this.myRoot.myLeft;
    	return x;
    }
    
     Expression getRight ( ) {
    	//getter method that returns the right child of a TreeNode.
    	Expression x = new Expression();
    	x.myRoot = this.myRoot.myRight;
    	return x;
    }
    
     public String toString ( ) {
    	//Method that returns a string representing the Expression, traverses the tree inorder
    	//and returns String and visiting each node.  
    	return toStringHelper(this.myRoot);
    }
    
	private static String toStringHelper (TreeNode t) {
		//Helper method for toString method
		if (t == null) {
			return "";
		} else if (t.myRight==null && t.myLeft==null) {
			return ""+ t.myItem;
		} else if (t.myItem.equals('~')) {
			return "~"+toStringHelper(t.myRight);
		} else {
			return "(" + toStringHelper(t.myLeft) + t.myItem + toStringHelper(t.myRight) + ")"; 
		}
	}
	
     boolean theoremEqual(Expression exp){
    	VariableMap = new TreeMap<String,String>();
    	return theoremEqualHelper(this, exp);
    }
    
    private boolean theoremEqualHelper(Expression theorem, Expression exp){
    	if (theorem.getItem().equals("~") ||
    		theorem.getItem().equals("=>") ||
    		theorem.getItem().equals("&") ||
    		theorem.getItem().equals("|")){
    		if (theorem.getItem().equals(exp.getItem())){
    			if(theorem.getItem().toString().equals("~")){
    				return theoremEqualHelper(theorem.getRight(), exp.getRight());
    			}else{
    				return (theoremEqualHelper(theorem.getLeft(), exp.getLeft())&theoremEqualHelper(theorem.getRight(), exp.getRight()));
    			}
    		}
    	}else{
    		if (VariableMap.containsKey(theorem.toString())){
    			return VariableMap.get(theorem.toString()).equals(exp.toString());
    		}else{
    			VariableMap.put(theorem.toString(),exp.toString());
    			return true;
    		}
    	} return false;
    }
    
	 Expression not ( )throws IllegalLineException {
		return new Expression("~" + this.toString());
	}
	// This code gets put inside the Expression class.

	private static class TreeNode {
		
		private Object myItem;
		private TreeNode myLeft;
		private TreeNode myRight;
		
		 TreeNode (Object obj) {
			myItem = obj;
			myLeft = myRight = null;
		}
		
		 TreeNode (Object obj, TreeNode left, TreeNode right) {
			myItem = obj;
			myLeft = left;
			myRight = right;
		}
	}	
}