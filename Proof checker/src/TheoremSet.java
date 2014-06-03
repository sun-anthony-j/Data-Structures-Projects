import java.util.TreeMap;

class TheoremSet {
	private TreeMap<String,Expression> TheoremMap;
	// Parses a .txt file and creates expression trees associated with the Theorem.
	// Constructor for TheoremSet that creates the map
	TheoremSet( ){
		TheoremMap=new TreeMap<String,Expression>();
			
		} 
	// give access to the private map.
	void put(String theoremName,Expression theorem){
		TheoremMap.put((String)theoremName, (Expression) theorem);
	}

	Expression get(String key) throws IllegalLineException{
		return TheoremMap.get(key);
	}
	Boolean contains(String key){
		return TheoremMap.containsKey(key);
	}
	
}