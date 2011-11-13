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
			if (board.cell(c.x(), c.y()) == gem) {
				board.setCell(c.x(), c.y(), 0);
			} else {
				throw new IllegalStateException("Cell at: " + c + " has value of: " + board.cell(c.x(), c.y()) + " other than: " + gem);
			}
		}
		// then replace smashed cells
		for(int i=0; i < board.width(); i++) {
			for(int j=0; j < board.height(); j++) {
				int g = board.cell(j, i);
				if (g > 0) continue;
				int k = j + 1;
				while(board.cell(k, i) == 0) {
					k++;
				}
				if (k < board.height()) {
					board.setCell(j, i, board.cell(k, i));
					board.setCell(k, i, 0);
					
					Cell pac = new Cell(j, i, board.cell(j,i));
					Cell cellToRemove = null;
					for(Cell c: potentialAdjacentCells) {
						if (c.val() == pac.val() && c.y() == pac.y() && c.x() == pac.x() - 1)
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
 