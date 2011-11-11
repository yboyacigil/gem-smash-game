package com.yusufboyacigil.gemsmashengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.model.Cell;
import com.yusufboyacigil.gemsmashengine.util.BoardPrinter;

/**
 *
 * @author yboyacigil
 */
@RunWith(NameAwareRunner.class)
public class GemSmasherTest {

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
		
		Set<Cell> cellSet = AdjacentGemsInspector.inspect(1, new Cell(2, 0), board);
		assertEquals(8, cellSet.size());
		System.out.println("Adjacent gems: " + cellSet);
		
		GemSmasher.smash(1, board, cellSet);
		for(int i=0; i < board.height(); i++) {
			for(int j=0; j < board.width(); j++) {
				if (board.cell(i, j) == 1) fail("Smash failed at (" + i + ", " + j + ")");
			}
		}
		assertEquals(0, board.cell(0, 0));
		assertEquals(0, board.cell(1, 0));
		assertEquals(0, board.cell(2, 0));
		assertEquals(0, board.cell(3, 0));
		assertEquals(5, board.cell(2, 1));
		assertEquals(0, board.cell(3, 1));
		assertEquals(4, board.cell(1, 2));
		assertEquals(4, board.cell(1, 3));
		assertEquals(0, board.cell(2, 2));
		assertEquals(0, board.cell(2, 3));
		assertEquals(0, board.cell(3, 2));
		assertEquals(0, board.cell(3, 3));
		
		BoardPrinter.print(board);
	
	}

}
 