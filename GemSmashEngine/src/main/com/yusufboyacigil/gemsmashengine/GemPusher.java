package com.yusufboyacigil.gemsmashengine;

import com.yusufboyacigil.gemsmashengine.model.Basket;
import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.model.Cell;

/**
 *
 * @author yboyacigil
 */
public class GemPusher {
	
	private static final PushResult EMPTY_PUSH_RESULT = new PushResult();

	public static PushResult push(int col, Board board, Basket basket) {
		if (col >= board.width()) {
			return EMPTY_PUSH_RESULT;
		}
		
		if (basket.isEmpty()) {
			return EMPTY_PUSH_RESULT;
		}
		
		int numGems = 0;
		int row = 0;
		for(;row < board.height(); row++) {
			if (board.cell(row, col) > 0) {
				continue;
			}
			while(row < board.height() && !basket.isEmpty()) {
				basket.take(1);
				board.setCell(row, col, basket.gem());
				row++;
				numGems++;
			}
			break;
		}
		if (numGems == 0) {
			return EMPTY_PUSH_RESULT;
		}
		return new PushResult(basket.gem(), numGems, new Cell(--row, col));
	}
	
	public static class PushResult {
		private int gem;
		private int numGems;
		private Cell inspectCell;
		
		public PushResult() {}
		
		public PushResult(int gem, int numGems, Cell inspectCell) {
			this.gem = gem;
			this.numGems = numGems;
			this.inspectCell = inspectCell;
		}
		
		public int gem() {
			return this.gem;
		}
		
		public int numGems() {
			return this.numGems;
		}
		
		public Cell inspectCell() {
			return this.inspectCell;
		}
		
		@Override
		public String toString() {
			return "[gem: " + gem + ", numGems: " + numGems + " inspectCell: " + inspectCell + "]";
		}
		
	}

}
