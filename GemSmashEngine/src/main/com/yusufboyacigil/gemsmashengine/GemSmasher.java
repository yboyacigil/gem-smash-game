package com.yusufboyacigil.gemsmashengine;

import java.util.HashSet;
import java.util.Set;

import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.model.Cell;

/**
 *
 * @author yboyacigil
 */
public class GemSmasher {
	
	public static Set<Cell> smash(int gem, Board board, Set<Cell> cellSet) {
		Set<Cell> potentialAdjacentCells = new HashSet<Cell>(); 
			
		// first smash cells that are known adjacent
		for(Cell c: cellSet) {
			if (board.cell(c.row(), c.col()) == gem) {
				board.setCell(c.row(), c.col(), 0);
			} else {
				throw new IllegalStateException("Cell at: " + c + " has value of: " + board.cell(c.row(), c.col()) + " other than: " + gem);
			}
		}
		// then replace smashed cells
		for(int col=0; col < board.width(); col++) {
			for(int row=0; row < board.height(); row++) {
				int g = board.cell(row, col);
				if (g > 0) continue;
				int k = row + 1;
				while(board.cell(k, col) == 0) {
					k++;
				}
				if (k < board.height()) {
					board.setCell(row, col, board.cell(k, col));
					board.setCell(k, col, 0);
					
					Cell pac = new Cell(row, col, board.cell(row,col));
					Cell cellToRemove = null;
					for(Cell c: potentialAdjacentCells) {
						if (c.gem() == pac.gem() && c.col() == pac.col() && c.row() == pac.row() - 1)
							cellToRemove = c;
							
					}
					if (cellToRemove != null) {
						potentialAdjacentCells.remove(cellToRemove);
					}
					potentialAdjacentCells.add(pac);
				}
			}
		}
		
		return potentialAdjacentCells;
	}

}
 