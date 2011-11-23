package com.yusufboyacigil.gemsmashengine.model;

import java.io.Serializable;

/**
 *
 * @author yboyacigil
 */
public class Board implements Serializable {
	
	private static final long serialVersionUID = 1L;

	int [][] data;

	public Board(int width, int height) {
		data = new int[height][width];
	}
	
	public int width() { return data[0].length; }
	
	public int height() { return data.length; }
	
	public int[] row(int row) { return data[row]; }
	
	public int cell(int row, int col) { 
		if (row >= 0 && row < data.length && col >= 0 && col < data[0].length)
			return data[row][col];
		return -1;
	}

	public void pushRow(int[] row) {
		for(int r=data.length - 1; r > 0; r--) {
			data[r] = data[r-1];
		}
		data[0] = row;
	}
	
	public boolean isFilledUp() {
		int[] lastRow = data[data.length - 1];
		for(int row=0; row < lastRow.length; row++) {
			if (lastRow[row] > 0) {
				return true;
			}
		}
		return false;
	}

	public void setCell(int row, int col, int gem) {
		if (row >= 0 && row < data.length && col >= 0 && col < data[0].length) {
			data[row][col] = gem;
		}
	}
	
	public void fill(int[][] data) {
		if (this.data[0].length != data[0].length) {
			throw new IllegalArgumentException("Width mismatch! (" + this.data[0].length + "!=" + data[0].length);
		}
		for(int row=0; row < data.length; row++) {
			if (row >= this.data.length) break;
			this.data[row] = data[row];
		}
	}
	
}
