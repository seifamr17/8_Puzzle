import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AStarAgent {
	
	private Set<AStar_State> explored;
    private AStar_State initialState;
    private byte[][] goalCell;
    private PriorityQueue<AStar_State> frontier;
    private int frontierSize;
    private HashMap<AStar_State,AStar_State> StateMap;
    private String heuristic;
    private ArrayList<AStar_State> pathStates;
    private long startTimeAStar;
    private long endTimeAStar;
    private long totalTimeAStar;
    private int maxDepth=0;
    
    public AStarAgent(byte[][] cell,byte[][] goalCell,String heuristic) throws InterruptedException, IOException {
        this.initialState = new AStar_State(cell,"Initial",0);
        this.goalCell = goalCell;
        this.frontier=new PriorityQueue<AStar_State>();
        this.frontierSize = 0;
        this.explored=new HashSet<AStar_State>();
        this.StateMap=new HashMap<AStar_State,AStar_State>();
        this.heuristic=heuristic;
        this.pathStates = new ArrayList<>();
        this.AStar_Work();
    }

    private boolean AStar_Work() throws InterruptedException, IOException
    {
        startTimeAStar=System.nanoTime();
        int hn;
        int gn=0;
        hn=Evaluate_hn(initialState);
        costEntry initialStateCost=new costEntry(hn, gn);
        initialState.setCost(initialStateCost);
        frontier.add(initialState);
        frontierSize++;     
        while(!frontier.isEmpty())
        {
            AStar_State state=frontier.poll();
            frontierSize--;
            explored.add(state);
            System.out.println();
            state.printCell();
            //here or the depth of the states in froniter
            if(state.getDepth()>maxDepth){
                maxDepth=state.getDepth();
            }
            
            if (goalTest(state))
            {
                endTimeAStar = System.nanoTime();
                totalTimeAStar = endTimeAStar - startTimeAStar;
                printPathToGoal(state);
                printSearchDepth();
                printNumberOfVisitedNodes();
                System.out.println("SUCCESS");
                printOutputFile();
                return true;
            }
            expand(state);
        }
        System.out.println("FAIL");
        return false;
    }

	private boolean goalTest(AStar_State state) {
        byte[][] Cell = state.getCellNode();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Cell[i][j] != goalCell[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void printPathToGoal(AStar_State goalState) {
        pathStates.add(goalState);
        //Find Parent of each state in the path in order to generate path to goal
        AStar_State parent = StateMap.get(goalState);
        while (parent != null) {
            pathStates.add(parent);
            parent = StateMap.get(parent);
        }
        System.out.println("Path To Goal As Shown");
        for (int i = pathStates.size() - 1; i >= 0; i--) {
            pathStates.get(i).printCell();
        }
    }
    
    private void printSearchDepth()
    {
        System.out.println("Search Depth For Astar Agent = "+(maxDepth));
    }
    
    private void printNumberOfVisitedNodes()
    {
        System.out.println("Number Of Visited Nodes in AStar "+heuristic+"= "
                +explored.size());
    }
	private void expand(AStar_State state) throws InterruptedException{
		int zeroIndex=10;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(state.getCellNode()[i][j]==0){
                zeroIndex=(3*i)+j;
                }
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

	private void slideUp(int zeroRow, int zeroColumn, AStar_State state) {
		byte [][]cell = new byte[3][3];
		costEntry entryCost;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cell[i][j]=state.getCellNode()[i][j];
			}
		}
		byte temp = cell[zeroRow-1][zeroColumn];
		cell[zeroRow][zeroColumn] = temp;
		cell[zeroRow-1][zeroColumn]=0;
		AStar_State neighbor = new AStar_State(cell, "Up");
		int hn= Evaluate_hn(neighbor);
		entryCost = new costEntry(hn, state.getCost().getGn()+1);
		neighbor.setCost(entryCost);
		addInFrontier(state,neighbor);
		
	}

	private void slideLeft(int zeroRow, int zeroColumn, AStar_State state) {
		byte [][]cell = new byte[3][3];
		costEntry entryCost;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cell[i][j]=state.getCellNode()[i][j];
			}
		}
		byte temp = cell[zeroRow][zeroColumn-1];
		cell[zeroRow][zeroColumn] = temp;
		cell[zeroRow][zeroColumn-1]=0;
		AStar_State neighbor = new AStar_State(cell, "Left");
		int hn= Evaluate_hn(neighbor);
		entryCost = new costEntry(hn, state.getCost().getGn()+1);
		neighbor.setCost(entryCost);
		addInFrontier(state,neighbor);
		
	}

	private void slideRight(int zeroRow, int zeroColumn, AStar_State state) {
		byte [][]cell = new byte[3][3];
		costEntry entryCost;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cell[i][j]=state.getCellNode()[i][j];
			}
		}
		byte temp = cell[zeroRow][zeroColumn+1];
		cell[zeroRow][zeroColumn] = temp;
		cell[zeroRow][zeroColumn+1]=0;
		AStar_State neighbor = new AStar_State(cell, "Right");
		int hn= Evaluate_hn(neighbor);
		entryCost = new costEntry(hn, state.getCost().getGn()+1);
		neighbor.setCost(entryCost);
		addInFrontier(state,neighbor);
		
	}

	private void slideDown(int zeroRow, int zeroColumn, AStar_State state) {
		byte [][]cell = new byte[3][3];
		costEntry entryCost;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				cell[i][j]=state.getCellNode()[i][j];
			}
		}
		byte temp = cell[zeroRow+1][zeroColumn];
		cell[zeroRow][zeroColumn] = temp;
		cell[zeroRow+1][zeroColumn]=0;
		AStar_State neighbor = new AStar_State(cell, "Down");
		int hn= Evaluate_hn(neighbor);
		entryCost = new costEntry(hn, state.getCost().getGn()+1);
		neighbor.setCost(entryCost);
		addInFrontier(state,neighbor);
		
	}
	private void addInFrontier(AStar_State state, AStar_State neighbor) {
		boolean foundInFrontier = false;
		boolean foundInExplored = false;
		AStar_State temp,temp2;
		AStar_State prevInFrontier = null;
		PriorityQueue<AStar_State> tempFrontier = new PriorityQueue<AStar_State>();
		//Check if exists in frontier
		for(int i=0;i<frontierSize;i++) {
			temp = frontier.poll();
			tempFrontier.add(temp);
			if(temp.isOfSameState(neighbor)) {
				prevInFrontier = temp;
				foundInFrontier = true;
			}
		}
		while(!tempFrontier.isEmpty()) {
			frontier.add(tempFrontier.poll());
		}
		//Check if exists in explored
		Object []exploredArray = explored.toArray();
		for(int i=0;i<exploredArray.length;i++) {
			temp2 = (AStar_State)exploredArray[i];
			if(temp2.isOfSameState(neighbor))
				foundInExplored = true;
		}
		neighbor.setDepth(state.getDepth()+1);
		if(!foundInExplored && !foundInFrontier) {
			StateMap.put(neighbor, state);
			frontier.add(neighbor);
			frontierSize++;
		}
		else if(!foundInExplored && foundInFrontier) {
			if(prevInFrontier.getCost().getFn() > neighbor.getCost().getFn()) {
				StateMap.remove(prevInFrontier);
				StateMap.put(neighbor, state);
				frontier.remove(prevInFrontier);
				frontier.add(neighbor);
			}
		}
		
	}

	private int Evaluate_hn(AStar_State state) {
		byte [][] cellNode = state.getCellNode();
		int hn1=0,hn2=0;
		int hn;
        int targetElementColumn;
        int targetElementRow;
        
        for(int i=0;i<3;i++) {
        	for(int j=0;j<3;j++) {
        		targetElementColumn=0;
        		targetElementRow=0;
        		if(cellNode[i][j]==0) {
        			continue;
        		}	
        		int element = cellNode[i][j];
        		while(element !=0) {
        			if(element-3 >=0) {
        				targetElementRow = targetElementRow+1;
        				element = element -3;
        			}
        			else {
        				targetElementColumn = element;
        				element=0;
        			}
        		}
        		hn1=hn1+(int)(Math.abs(targetElementRow-i)+Math.abs(targetElementColumn-j));//Manhattan Distance
                hn2=hn2+(int)(Math.sqrt(Math.pow((targetElementRow-i),2)+Math.pow((targetElementColumn-j),2)));//Euclidean Distance
        		
        	}
        }
        if(heuristic.equalsIgnoreCase("Manhattan"))
        {
            hn=hn1;
        }
        else 
        {
            hn=hn2;
        }
    return hn;
	}
    private void printOutputFile() throws IOException{
        File f;
        if(heuristic.equalsIgnoreCase("Manhattan")){
           f = new File("AstarMoutput.txt"); 
        }else{
            f = new File("AstarEoutput.txt");
        }        
        FileWriter fw = new FileWriter(f);
        BufferedWriter bf = new BufferedWriter(fw);
        
        String pathtogoal="Path to Goal =";
        for (int i = pathStates.size() - 1; i >= 0; i--) {
            pathtogoal=pathtogoal+" "+pathStates.get(i).getPathtaken();
        }
        bf.write(pathtogoal);
        bf.newLine();
        
        String pathCost="Path Cost For Astar Agent = "+(pathStates.size()-1);
        bf.write(pathCost);
        bf.newLine();
        
        String visitedNodes="Number Of Visited/Expanded Nodes in Astar = "+explored.size();        
        bf.write(visitedNodes);
        bf.newLine();
        
        //recalculate depth of dfs
        String searchdepth="Search Depth For Astar Agent = "+(maxDepth);
        bf.write(searchdepth);
        bf.newLine();
        
        String runningtime="Time for Astar(nanoseconds)="+totalTimeAStar;
        bf.write(runningtime);
        bf.newLine();
        
        String runningtime2="Time for Astar(seconds)="+ (TimeUnit.SECONDS.convert(totalTimeAStar,TimeUnit.NANOSECONDS));
        bf.write(runningtime2);
        bf.newLine();
        
        bf.close();
    }

	public ArrayList<AStar_State> getPathStates() {
		return pathStates;
	}

	public void setPathStates(ArrayList<AStar_State> pathStates) {
		this.pathStates = pathStates;
	}

	public long getTotalTimeAStar() {
		return totalTimeAStar;
	}

	public void setTotalTimeAStar(long totalTimeAStar) {
		this.totalTimeAStar = totalTimeAStar;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
    

}
