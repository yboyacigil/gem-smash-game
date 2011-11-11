package com.yusufboyacigil.gemsmashengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class AdjacentGemsInspectorTest {
	private static final int BOARD_HEIGHT = 10;
	private static final int BOARD_WIDTH = 5;
	
	@Test
	public void testNotEnoughGemsAreAdjacent() {
		Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);

		board.pushRow(new int[] {1, 2, 2, 4 ,5});
		board.pushRow(new int[] {1, 1, 1, 4 ,5});
		board.pushRow(new int[] {2, 1, 2, 4 ,5});
		board.pushRow(new int[] {1, 2, 2, 4 ,5});
		BoardPrinter.print(board);
	
		Set<Cell> result = AdjacentGemsInspector.inspect(1, new Cell(3, 0), board);
		assertEquals(0, result.size());
	}	
		
	@Test
	public void testOneRowOfAdjacentGems() {
		Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);

		board.pushRow(new int[] {1, 2, 2, 4 ,5});
		board.pushRow(new int[] {1, 3, 4, 4 ,5});
		board.pushRow(new int[] {1, 3, 2, 4 ,5});
		board.pushRow(new int[] {2, 2, 2, 4 ,5});
		BoardPrinter.print(board);
	
		Set<Cell> result = AdjacentGemsInspector.inspect(1, new Cell(3, 0), board);
		System.out.println("Adjacent gems: " + result);
		assertEquals(3, result.size());
		
		for(int i=0; i < board.height(); i++) {
			for(int j = 0; j < board.width(); j++) {
				if (board.cell(i, j) == 1) {
					assertTrue(result.contains(new Cell(i,j)));
				}
			}
		}
	}

	@Test
	public void test() {
		Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);

		board.pushRow(new int[] {1, 2, 2, 4 ,5});
		board.pushRow(new int[] {1, 1, 1, 4 ,5});
		board.pushRow(new int[] {1, 1, 2, 4 ,5});
		board.pushRow(new int[] {1, 2, 2, 4 ,5});
		BoardPrinter.print(board);
	
		Set<Cell> result = AdjacentGemsInspector.inspect(1, new Cell(3, 0), board);
		System.out.println("Adjacent gems: " + result);
		assertEquals(7, result.size());
		
		for(int i=0; i < board.height(); i++) {
			for(int j = 0; j < board.width(); j++) {
				if (board.cell(i, j) == 1) {
					assertTrue(result.contains(new Cell(i,j)));
				}
			}
		}
	}

	@Test
	public void test2() {
		Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);

		board.pushRow(new int[] {1, 2, 1, 1 ,1});
		board.pushRow(new int[] {1, 1, 1, 4 ,1});
		board.pushRow(new int[] {1, 3, 2, 4 ,1});
		board.pushRow(new int[] {1, 2, 2, 4 ,5});
		BoardPrinter.print(board);
	
		Set<Cell> result = AdjacentGemsInspector.inspect(1, new Cell(3, 0), board);
		System.out.println("Adjacent gems: " + result);
		assertEquals(11, result.size());
		
		for(int i=0; i < board.height(); i++) {
			for(int j = 0; j < board.width(); j++) {
				if (board.cell(i, j) == 1) {
					assertTrue(result.contains(new Cell(i,j)));
				}
			}
		}
	}
	
}
