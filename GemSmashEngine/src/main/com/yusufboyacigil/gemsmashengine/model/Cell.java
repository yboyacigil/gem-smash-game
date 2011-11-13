package com.yusufboyacigil.gemsmashengine.model;

/**
 *
 * @author yboyacigil
 */
public class Cell {

	private int x;
	private int y;
	private int val = -1;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Cell(int x, int y, int val) {
		this(x, y);
		this.val = val;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public int val() {
		return val;
	}
	
	@Override
	public String toString() {
		if (val == -1) return "(" + x + "," + y + ")";
		else           return "(" + x + "," + y + "):" + val;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + val;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (val != other.val)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
