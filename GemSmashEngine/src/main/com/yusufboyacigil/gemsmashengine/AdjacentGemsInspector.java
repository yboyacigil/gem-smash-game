package com.yusufboyacigil.gemsmashengine;

import java.util.HashSet;
import java.util.Set;

import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.model.Coord;

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
	
	public static Set<Coord> inspect(int gem, Coord start, Board board) {
		Set<Coord> coordSet = new HashSet<Coord>();

		int c = 1;
		for(int k=1; k < 3; k++) {
			if (board.cell(start.x() - k, start.y()) != gem) return coordSet;
			c++;
		}
		if (c < 3) {
			return coordSet;
		}
		
		coordSet.add(start);
		deepInspect(gem, start, board, coordSet, EDir.UP, EDir.RIGHT, EDir.LEFT);
		
		return coordSet;
	}

	private static void deepInspect(int gem, Coord start, Board board, Set<Coord> coordSet, EDir... directions) {
		for(EDir d: directions) {
			Coord moved = move(start, d);
			if (coordSet.contains(moved)) continue;
			if (board.cell(moved.x(), moved.y()) == gem) {
				coordSet.add(moved);
				deepInspect(gem, moved, board, coordSet, d.next());
			}
		}
	}

	private static Coord move(Coord start, EDir d) {
		switch(d) {
			case UP    : return new Coord(start.x() - 1, start.y());
			case RIGHT : return new Coord(start.x(),     start.y() + 1);
			case LEFT  : return new Coord(start.x(),     start.y() - 1);
			case DOWN  : return new Coord(start.x() + 1, start.y());
			default    :  throw new IllegalStateException("No such direction: " + d);
		}
	}

}
 