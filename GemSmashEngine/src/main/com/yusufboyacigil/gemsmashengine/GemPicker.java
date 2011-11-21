package com.yusufboyacigil.gemsmashengine;

import com.yusufboyacigil.gemsmashengine.model.Basket;
import com.yusufboyacigil.gemsmashengine.model.Board;

/**
 * Picks gems of the same value into basket from tip of given 
 * column of the board.
 * 
 * @author yboyacigil
 */
public class GemPicker {
	
	public static int pick(int col, Board board, Basket basket) {
		// check we're out of index
		if (col >= board.width()) {
			return 0;
		}
		
		int c = 0;
		for (int i=board.height()-1; i > 0; i--) {
			if (board.cell(i, col) == 0) continue;
			int gem = board.cell(i, col);
			if (basket.isEmpty()) basket.setGem(gem);
			if (basket.cannotPut(gem)) return 0;
			do {
				board.setCell(i, col, 0);
				c++;
				i--;
			} while(board.cell(i, col) == gem);
			basket.put(gem, c);
			break;
		}
		
		return c;
	}

}
