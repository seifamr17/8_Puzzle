import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class DfsAgent {
	
	private Set<DFS_State> explored;
	private DFS_State initialState;
	private byte[][] goalCell;
	private Stack<DFS_State> frontier;
	private HashMap<DFS_State, DFS_State> StateMap;
    private ArrayList<DFS_State> pathStates;
    private int frontierSize;
    private long startTimeDFS;
    private long endTimeDFS;
    private long totalTimeDFS;
    private int maxDepth=0;
    
    public ArrayList<DFS_State> getPathStates() {
        return pathStates;
    }
    public DfsAgent(byte[][] cell,byte[][] goalCell) throws IOException {
        this.initialState = new DFS_State(cell,"Initial",0);
        this.goalCell = goalCell;
        this.frontier = new Stack<DFS_State>();
        this.frontierSize = 0;
        this.explored = new HashSet<DFS_State>();
        //Map each child state to its parent State
        this.StateMap = new HashMap<DFS_State, DFS_State>();
        this.pathStates = new ArrayList<>();
        DFS_Work();
        
    }
	private boolean DFS_Work() throws IOException {
        startTimeDFS = System.nanoTime();
        frontier.push(initialState);
        frontierSize++;
        while(!frontier.isEmpty()) {
        	DFS_State state = frontier.pop();
        	frontierSize--;
        	explored.add(state);
        	System.out.println();
            state.printCell();
        	if(state.getDepth()>maxDepth)
        		maxDepth = state.getDepth();
        	if(reach_limit())
        		break;
        	if(goalTest(state)) {
        		endTimeDFS   = System.nanoTime();
                totalTimeDFS = endTimeDFS - startTimeDFS;
        		this.printPathToGoal(state);
                this.printSearchDepth();
                this.printNumberOfVisitedNodes();
                System.out.println("SUCCESS");
                printOutputFile();
                return true;		
        	}
        	else
        		expand(state);       	
        }
		System.out.println("FAIL or Depth limit reached");
		return false;
	}
	private void printNumberOfVisitedNodes() {
        System.out.println("Number Of Visited Nodes in DFS = "+explored.size());	
	}
	private void printSearchDepth() {
		System.out.println("Search Depth For DFS Agent = "+(maxDepth));
		
	}
	private void printPathToGoal(DFS_State goalState) {
		pathStates.add(goalState);
		//Find Parent of each state in the path in order to generate path to goal
		DFS_State parent = StateMap.get(goalState);
		while(parent != null) {
			pathStates.add(parent);
			parent = StateMap.get(parent);
		}
        System.out.println("Path To Goal As Shown");
        for (int i = pathStates.size() - 1; i >= 0; i--) {
            pathStates.get(i).printCell();
        }
			
	}
	private boolean goalTest(DFS_State state) {
		byte[][] Cell = state.getCellNode();
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(Cell[i][j] != goalCell[i][j])
					return false;
			}
		}
		return true;
	}
	private void expand(DFS_State state) {
		int zeroIndex =10; // Index to mark the empty spot to move
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(state.getCellNode()[i][j]==0)
					zeroIndex = (3*i)+j;
			}
		}
		if (zeroIndex == 0) {
            slideDown(0, 0, state);
            slideRight(0, 0, state);
        } else if (zeroIndex == 1) {
            slideDown(0, 1, state);
            slideRight(0, 1, state);
            slideLeft(0, 1, state);
        } else if (zeroIndex == 2) {      
            slideDown(0, 2, state);
            slideLeft(0, 2, state); 
        } else if (zeroIndex == 3) {
            slideDown(1, 0, state);
            slideRight(1, 0, state);
            slideUp(1, 0, state);
        } else if (zeroIndex == 4) {
            slideRight(1, 1, state);
            slideUp(1, 1, state);
            slideDown(1, 1, state);
            slideLeft(1, 1, state);
        } else if (zeroIndex == 5) {  
            slideLeft(1, 2, state);
            slideDown(1, 2, state);
            slideUp(1, 2, state);
        } else if (zeroIndex == 6) {
            slideRight(2, 0, state);
            slideUp(2, 0, state);
        } else if (zeroIndex == 7) {
            slideUp(2, 1, state);
            slideRight(2, 1, state);
            slideLeft(2, 1, state);
        } else if (zeroIndex == 8) {
            slideLeft(2, 2, state);
            slideUp(2, 2, state);
        }
		
	}
	private void slideUp(int zeroRow, int zeroColumn, DFS_State state) {
		byte [][] cell = new byte [3][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cell[i][j] = state.getCellNode()[i][j];
			}
		}
		DFS_State neighbor;
		byte temp = cell[zeroRow-1][zeroColumn];
		cell[zeroRow][zeroColumn] = temp;
		cell[zeroRow-1][zeroColumn]=0;
		neighbor = new DFS_State(cell, "Up");
		//Add in frontier
        addInFrontier(state, neighbor);
		
	}
	private void slideLeft(int zeroRow, int zeroColumn, DFS_State state) {
		byte [][] cell = new byte [3][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cell[i][j] = state.getCellNode()[i][j];
			}
		}
		DFS_State neighbor;
		byte temp = cell[zeroRow][zeroColumn-1];
		cell[zeroRow][zeroColumn] = temp;
		cell[zeroRow][zeroColumn-1]=0;
		neighbor = new DFS_State(cell, "Left");
		//Add in frontier
        addInFrontier(state, neighbor);
		
	}
	private void slideRight(int zeroRow, int zeroColumn, DFS_State state) {
		byte [][] cell = new byte [3][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cell[i][j] = state.getCellNode()[i][j];
			}
		}
		DFS_State neighbor;
		byte temp = cell[zeroRow][zeroColumn+1];
		cell[zeroRow][zeroColumn] = temp;
		cell[zeroRow][zeroColumn+1]=0;
		neighbor = new DFS_State(cell, "Right");
		//Add in frontier
        addInFrontier(state, neighbor);
		
	}
	private void slideDown(int zeroRow, int zeroColumn, DFS_State state) {
		byte [][] cell = new byte [3][3];
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cell[i][j] = state.getCellNode()[i][j];
			}
		}
		DFS_State neighbor;
		byte temp = cell[zeroRow+1][zeroColumn];
		cell[zeroRow][zeroColumn] = temp;
		cell[zeroRow+1][zeroColumn]=0;
		neighbor = new DFS_State(cell, "Down");
		//Add in frontier
        addInFrontier(state, neighbor);
		
	}
	private void addInFrontier(DFS_State state, DFS_State neighbor) {
		boolean found = false;
		DFS_State temp,temp2;
		Stack<DFS_State>tempfrontier = new Stack<DFS_State>();
		//Check if not in frontier
		for(int i=0;i<frontierSize;i++) {
			temp = frontier.pop();
			tempfrontier.push(temp);
			if(temp.isOfSameState(neighbor))
				found = true;
		}
		while(!tempfrontier.isEmpty()) {
			frontier.push(tempfrontier.pop());
		}
		//Check if not in explored
		Object[] exploredArray = explored.toArray();
		for(int i=0;i<exploredArray.length;i++) {
			temp2 = (DFS_State)exploredArray[i];
			if(temp2.isOfSameState(neighbor))
				found = true;
		}
		neighbor.setDepth(state.getDepth()+1);
        //If not in both frontier or explored ADD It to the frontier
		if(!found) {
			StateMap.put(neighbor, state);
			frontier.push(neighbor);
			frontierSize++;
		}
		
	}
	
    private void printOutputFile() throws IOException{
    	File f = new File("DFSoutput.txt");
        FileWriter fw = new FileWriter(f);
        BufferedWriter bf = new BufferedWriter(fw);
        
        String pathtogoal="Path to Goal =";
        for (int i = pathStates.size() - 1; i >= 0; i--) {
            pathtogoal=pathtogoal+" "+pathStates.get(i).getPathtaken();
        }
        bf.write(pathtogoal);
        bf.newLine();
        
        String pathCost="Path Cost For DFS Agent = "+(pathStates.size()-1);
        bf.write(pathCost);
        bf.newLine();
        
        String visitedNodes="Number Of Visited/Expanded Nodes in DFS = "+explored.size();        
        bf.write(visitedNodes);
        bf.newLine();
        
        //recalculate depth of dfs
        String searchdepth="Search Depth For DFS Agent = "+(maxDepth);
        bf.write(searchdepth);
        bf.newLine();
        
        String runningtime="Time for DFS(nanoseconds)="+totalTimeDFS;
        bf.write(runningtime);
        bf.newLine();
        
        String runningtime2="Time for DFS(seconds)="+ (TimeUnit.SECONDS.convert(totalTimeDFS,TimeUnit.NANOSECONDS));
        bf.write(runningtime2);
        bf.newLine();
        
        bf.close();
    }
    private boolean reach_limit() {
    	if(maxDepth >= 10000)
    		return true;
    	return false;
    		
    }
    


}
