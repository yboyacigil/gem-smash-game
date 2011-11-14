package com.yusufboyacigil.gemsmashengine.model;

/**
 *
 * @author yboyacigil
 */
public class Cell {

	private int row;
	private int col;
	private int gem = -1;
	
	public Cell(int x, int y) {
		this.row = x;
		this.col = y;
	}
	
	public Cell(int x, int y, int val) {
		this(x, y);
		this.gem = val;
	}
	
	public int row() {
		return row;
	}
	
	public int col() {
		return col;
	}
	
	public int gem() {
		return gem;
	}
	
	@Override
	public String toString() {
		if (gem == -1) return "(" + row + "," + col + ")";
		else           return "(" + row + "," + col + "):" + gem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gem;
		result = prime * result + row;
		result = prime * result + col;
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
		if (gem != other.gem)
			return false;
		if (row != other.row)
			return false;
		if (col != other.col)
			return false;
		return true;
	}

}
