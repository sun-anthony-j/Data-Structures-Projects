import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;


public class SearchTree {
	HashSet<Board> BoardSet;
	DefaultTreeModel StartSearch;
	DefaultMutableTreeNode startroot;
	Board start;
	Board end;
	//Board SearchSolution;
	Object[] SolutionPath;
	boolean solved;
	
	public SearchTree (Board start, Board end){
		this.start=start;
		this.end=end;
		DefaultMutableTreeNode startroot= new DefaultMutableTreeNode(start);
		StartSearch= new DefaultTreeModel(startroot);
		BoardSet= new HashSet<Board>(1000);
		//int is size of hashmap. Set this to the some optimum later
		BoardSet.add(start);
		solved= false;
		//SearchSolution=null;
		
	}
	
	private void childmaker (DefaultMutableTreeNode input){
		Board board= (Board) input.getUserObject();
		Stack<Board> boardstack = board.newBoards();
		while (!boardstack.empty()){
			input.add(new DefaultMutableTreeNode(boardstack.pop()));
		}
		
	}
	
	private void childsort (DefaultMutableTreeNode input){
		
	}
	
	public boolean RunSearch(DefaultMutableTreeNode SearchItem){
		Board SearchBoard = (Board) SearchItem.getUserObject();
		
		childmaker(SearchItem);
		//childsort (SearchItem);
		
		
		
		int j = SearchItem.getChildCount();
		for (int i=0; i<j ; i++){
			DefaultMutableTreeNode temp=(DefaultMutableTreeNode) SearchItem.getChildAt(i);
			Board board= (Board) temp.getUserObject();
			if (Solver.isDone(board, end)){
				solved=true;
				SolutionPath=temp.getUserObjectPath();
				return true;
			}else{
			RunSearch((DefaultMutableTreeNode) SearchItem.getChildAt(i));
			}
		}
		return false;
	}
	
	public Object[] getSolution(){
		if (!solved){
			//System.out.print("Solution not found yet")
			//throw new IllegalStateException ("No solution found yet");
		}
		return SolutionPath;
	}
	
}
