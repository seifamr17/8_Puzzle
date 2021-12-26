
public class AStar_State implements Comparable<AStar_State>{
	
	private byte[][] cellNode;
	private String pathtaken;
	private int depth;
	private costEntry cost;
	
	public AStar_State(byte[][] CellNode) {
        this.cellNode = CellNode;
    }

    public AStar_State(byte[][] cellNode, String pathtaken) {
        this.cellNode = cellNode;
        this.pathtaken = pathtaken;
    }

    public AStar_State(byte[][] cellNode, String pathtaken, int depth) {
        this.cellNode = cellNode;
        this.pathtaken = pathtaken;
        this.depth = depth;
    }
    
    public void printCell(){
        System.out.println("State path "+pathtaken);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(cellNode[i][j]+" ");
            }                
            System.out.println();
        }
        System.out.println("-----");
        System.out.println("g(n) = "+cost.getGn());
        System.out.println("h(n) = "+cost.getHn());
        System.out.println("F(n) = g(n) + h(n) = "+cost.getFn());
    }

	public byte[][] getCellNode() {
		return cellNode;
	}

	public void setCellNode(byte[][] cellNode) {
		this.cellNode = cellNode;
	}

	public String getPathtaken() {
		return pathtaken;
	}

	public void setPathtaken(String pathtaken) {
		this.pathtaken = pathtaken;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public costEntry getCost() {
		return cost;
	}

	public void setCost(costEntry cost) {
		this.cost = cost;
	}

	@Override
	public int compareTo(AStar_State o) {
		return cost.getFn()-o.getCost().getFn();
	}
    public boolean isOfSameState(AStar_State u) {                      
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (getCellNode()[i][j] != u.getCellNode()[i][j]) {
                    return false;
                }
            }
        }        
        return true;
    }
    

}
