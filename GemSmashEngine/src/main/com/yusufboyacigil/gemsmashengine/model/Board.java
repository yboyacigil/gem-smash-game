package com.yusufboyacigil.gemsmashengine.model;

/**
 *
 * @author yboyacigil
 */
public class Board {
	
	int [][] data;

	public Board(int width, int height) {
		data = new int[height][width];
	}
	
	public int width() { return data[0].length; }
	
	public int height() { return data.length; }
	
	public int[] row(int rownum) { return data[rownum]; }
	
	public int cell(int i, int j) { 
		if (i >= 0 && i < data.length && j >= 0 && j < data[0].length)
			return data[i][j];
		return -1;
	}

	public void pushRow(int[] row) {
		for(int i=data.length - 1; i > 0; i--) {
			data[i] = data[i-1];
		}
		data[0] = row;
	}
	
	public boolean isFilledUp() {
		int[] lastRow = data[data.length - 1];
		for(int i=0; i < lastRow.length; i++) {
			if (lastRow[i] > 0) {
				return true;
			}
		}
		return false;
	}

	public void setCell(int i, int j, int v) {
		if (i >= 0 && i < data.length && j >= 0 && j < data[0].length) {
			data[i][j] = v;
		}
	}
	
	public void fill(int[][] data) {
		if (this.data[0].length != data[0].length) {
			throw new IllegalArgumentException("Width mismatch! (" + this.data[0].length + "!=" + data[0].length);
		}
		for(int i=0; i < data.length; i++) {
			if (i >= this.data.length) break;
			this.data[i] = data[i];
		}
	}
}
