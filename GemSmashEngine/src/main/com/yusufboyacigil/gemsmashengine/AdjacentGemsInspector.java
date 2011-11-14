package com.yusufboyacigil.gemsmashengine;

import java.util.HashSet;
import java.util.Set;

import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.model.Cell;

/**
 *
 * @author yboyacigil
 */
public class AdjacentGemsInspector {
	
	private static enum EDir {
		UP, RIGHT, LEFT, DOWN;
		
		public EDir[] next() {
			switch(this) {
				case UP    : return new EDir[] {UP, RIGHT, LEFT};
				case RIGHT : return new EDir[] {UP, RIGHT, DOWN};
				case LEFT  : return new EDir[] {UP, LEFT, DOWN};
				case DOWN  : return new EDir[] {RIGHT, LEFT, DOWN};
				default    :  throw new IllegalStateException("No such direction: " + this);
			}
		}
	}
	
	public static Set<Cell> inspect(int gem, Cell start, Board board) {
		Set<Cell> coordSet = new HashSet<Cell>();

		int c = 1;
		for(int k=1; k < 3; k++) {
			if (board.cell(start.row() - k, start.col()) != gem) return coordSet;
			c++;
		}
		if (c < 3) {
			return coordSet;
		}
		
		coordSet.add(start);
		deepInspect(gem, start, board, coordSet, EDir.UP, EDir.RIGHT, EDir.LEFT);
		
		return coordSet;
	}

	private static void deepInspect(int gem, Cell start, Board board, Set<Cell> cellSet, EDir... directions) {
		for(EDir d: directions) {
			Cell moved = move(start, d);
			if (cellSet.contains(moved)) continue;
			if (board.cell(moved.row(), moved.col()) == gem) {
				cellSet.add(moved);
				deepInspect(gem, moved, board, cellSet, d.next());
			}
		}
	}

	private static Cell move(Cell start, EDir d) {
		switch(d) {
			case UP    : return new Cell(start.row() - 1, start.col());
			case RIGHT : return new Cell(start.row(),     start.col() + 1);
			case LEFT  : return new Cell(start.row(),     start.col() - 1);
			case DOWN  : return new Cell(start.row() + 1, start.col());
			default    :  throw new IllegalStateException("No such direction: " + d);
		}
	}

}
 