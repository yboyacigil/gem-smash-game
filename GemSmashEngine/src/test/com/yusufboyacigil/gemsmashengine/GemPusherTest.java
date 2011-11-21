package com.yusufboyacigil.gemsmashengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.yusufboyacigil.gemsmashengine.GemPusher.PushResult;
import com.yusufboyacigil.gemsmashengine.model.Basket;
import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.model.Cell;
import com.yusufboyacigil.gemsmashengine.util.BoardPrinter;

/**
 *
 * @author yboyacigil
 */
@RunWith(NameAwareRunner.class)
public class GemPusherTest {

	private static final int BOARD_HEIGHT = 10;
	private static final int BOARD_WIDTH = 5;
	
	@Test
	public void test() {

		Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
		board.fill(new int[][] {
			{1, 3, 3, 5, 4},
			{1, 4, 1, 1, 3},
			{1, 1, 1, 1, 5},
			{0, 5, 4, 4, 4}
		});
		BoardPrinter.print(board);
		
		Basket basket = new Basket();
		assertEquals(0, GemPusher.push(board.width(), board, basket).numGems());
		assertEquals(0, GemPusher.push(0, board, basket).numGems());
		
		basket.setGem(1);
		basket.put(1, 2);
		PushResult pr = GemPusher.push(0, board, basket);
		assertEquals(2, pr.numGems());
		assertEquals(0, basket.numGems());
		assertEquals(1, board.cell(3, 0));
		assertEquals(1, board.cell(4, 0));
		assertEquals(new Cell(4,0), pr.inspectCell());
		BoardPrinter.print(board);
		
		basket.put(1, 6);
		pr = GemPusher.push(0, board, basket);
		assertEquals(5, pr.numGems());
		assertEquals(1, basket.numGems());
		assertEquals(1, board.cell(5, 0));
		assertEquals(1, board.cell(6, 0));
		assertEquals(1, board.cell(7, 0));
		assertEquals(1, board.cell(8, 0));
		assertEquals(1, board.cell(9, 0));
		assertEquals(new Cell(9,0), pr.inspectCell());
		BoardPrinter.print(board);
	
		
		
	
	}

}
