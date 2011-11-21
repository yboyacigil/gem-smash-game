package com.yusufboyacigil.gemsmashengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.yusufboyacigil.gemsmashengine.model.Basket;
import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.util.BoardPrinter;

/**
 *
 * @author yboyacigil
 */
@RunWith(NameAwareRunner.class)
public class GemPickerTest {

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
		
		// empty basket
		Basket basket = new Basket();
		int c = GemPicker.pick(0, board, basket);
		assertEquals(3, c);
		assertEquals(3, basket.numGems());
		assertEquals(1, basket.gem());
		System.out.println("Basket: " + basket);
		
		assertEquals(0, board.cell(0, 0));
		assertEquals(0, board.cell(1, 0));
		assertEquals(0, board.cell(2, 0));
		BoardPrinter.print(board);
		
		// basket full of 3 gems of value of 1
		c = GemPicker.pick(1, board, basket);
		assertEquals(0, c);
		assertEquals(3, basket.numGems());
		assertEquals(1, basket.gem());
		System.out.println("Basket: " + basket);
		
		assertEquals(5, board.cell(3, 1));
		BoardPrinter.print(board);
		
		// Try to pick with a new basket
		basket = new Basket();
		c = GemPicker.pick(4, board, basket);
		assertEquals(1, c);
		assertEquals(1, basket.numGems());
		assertEquals(4, basket.gem());
		System.out.println("Basket: " + basket);
		
		assertEquals(0, board.cell(3, 4));
		BoardPrinter.print(board);
		
		c = GemPicker.pick(3, board, basket);
		assertEquals(1, c);
		assertEquals(2, basket.numGems());
		assertEquals(4, basket.gem());
		System.out.println("Basket: " + basket);
		
		assertEquals(0, board.cell(3, 3));
		BoardPrinter.print(board);

		c = GemPicker.pick(2, board, basket);
		assertEquals(1, c);
		assertEquals(3, basket.numGems());
		assertEquals(4, basket.gem());
		System.out.println("Basket: " + basket);
		
		assertEquals(0, board.cell(3, 2));
		BoardPrinter.print(board);

	}

}
