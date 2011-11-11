package com.yusufboyacigil.gemsmashengine;

import java.util.Set;

import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.model.Coord;

/**
 *
 * @author yboyacigil
 */
public class GemSmasher {
	
	public static void smash(int gem, Board board, Set<Coord> gemCoords) {
		for(Coord c: gemCoords) {
			if (board.cell(c.x(), c.y()) == gem) {
				board.setCell(c.x(), c.y(), 0);
			} else {
				throw new IllegalStateException("Cell at: " + c + " has value of: " + board.cell(c.x(), c.y()) + " other than: " + gem);
			}
		}
		
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
				}
			}
		}
	}
	

}
 