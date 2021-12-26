
public class DFS_State {
	
private String pathtaken;
private int depth;
private byte [][] cellNode;
	public DFS_State(byte[][] CellNode) {
	    this.cellNode = CellNode;
	}
	
	public DFS_State(byte[][] cellNode, String pathtaken) {
	    this.cellNode = cellNode;
	    this.pathtaken = pathtaken;
	}
	
	public DFS_State(byte[][] cellNode, String pathtaken, int depth) {
	    this.cellNode = cellNode;
	    this.pathtaken = pathtaken;
	    this.depth = depth;
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

	public byte[][] getCellNode() {
		return cellNode;
	}

	public void setCellNode(byte[][] cellNode) {
		this.cellNode = cellNode;
	}
	
	public void printCell() {
		System.out.println("State path "+pathtaken);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(cellNode[i][j]+" ");
            }                
            System.out.println();
        }
        System.out.println("-----");
	}
	
	public boolean isOfSameState(DFS_State s) {
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(cellNode[i][j] != s.getCellNode()[i][j])
					return false;
			}
		}
		return true;
	}


}
