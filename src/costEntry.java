
public class costEntry {
	private int hn;
	private int gn;
	private int fn;
	
    public costEntry(int hn, int gn) {
        this.hn = hn;
        this.gn = gn;
        this.fn=this.gn+this.hn;
    }

	public int getHn() {
		return hn;
	}

	public void setHn(int hn) {
		this.hn = hn;
	}

	public int getGn() {
		return gn;
	}

	public void setGn(int gn) {
		this.gn = gn;
	}

	public int getFn() {
		return fn;
	}

	public void setFn(int fn) {
		this.fn = fn;
	}
    

}
