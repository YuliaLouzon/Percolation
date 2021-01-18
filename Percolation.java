import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int counter = 0;
  private final int gridSize;
  private final WeightedQuickUnionUF uf;
  private final int top = 0;
  private int bottom;
  private boolean[] opened;

	// creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if (n <= 0) {
    		throw new IllegalArgumentException();
    	}
    	this.gridSize = n;
    	this.uf = new WeightedQuickUnionUF(n*n + 2);
    	this.bottom = gridSize*gridSize + 1;
    	this.opened = new boolean[gridSize*gridSize + 2];
    	this.opened[0] = true;
    	this.opened[gridSize*gridSize + 1] = true;
    	
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	isLegalIndex(row, col);
    	int index = this.twoToOne(row, col);
    	if (opened[index]) {
    		return;
    	}
    	opened[index] = true;
    	counter++;
    	if ((row > 1) && this.isOpen(row - 1, col)) {
    		int otherIndex = this.twoToOne(row - 1, col);
    		this.uf.union(index, otherIndex);	
    	}

    	if ((row < gridSize) && this.isOpen(row + 1, col)) {
    		int otherIndex = this.twoToOne(row + 1, col);
    		this.uf.union(index, otherIndex);
    	}
    		
    	if ((col < gridSize) && this.isOpen(row, col + 1)) {
    		int otherIndex = this.twoToOne(row, col + 1);
    		this.uf.union(index, otherIndex);	
    	}
    	
    	if ((col > 1) && this.isOpen(row, col - 1)) {
    		int otherIndex = this.twoToOne(row, col - 1);
    		this.uf.union(index, otherIndex);
    	}
    	if (row == 1) {
    		this.uf.union(index, top);
    	}
    	
    	if (row == gridSize) {
    		this.uf.union(index, bottom);
    	}
    }

    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	isLegalIndex(row, col);
    	int index = this.twoToOne(row, col);
    	if (opened[index]) {
    		return true;
    	}
		return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	isLegalIndex(row, col);
    	int index = this.twoToOne(row, col);
    	
    	int first = this.uf.find(index);
    	int second = this.uf.find(top);
    	return (first == second);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
		return counter;
    }

    // does the system percolate?
    public boolean percolates() {
    	int first = this.uf.find(top);
    	int second = this.uf.find(bottom);
    	return (first == second);
    }
    
    private int twoToOne(int r, int c) {
    	int index = (r - 1)*gridSize + c;
    	return index;
    }
    
    private boolean isLegalIndex(int row, int col) {
    	if (row > gridSize || col > gridSize || row <= 0 || col <= 0) {
    		throw new IllegalArgumentException("the index is out of bounds");
    	}
    	return true;
    }
    
}
