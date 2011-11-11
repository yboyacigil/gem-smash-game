package com.yusufboyacigil.gemsmashengine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import com.yusufboyacigil.gemsmashengine.model.Board;
import com.yusufboyacigil.gemsmashengine.util.BoardPrinter;

/**
 *
 * @author yboyacigil
 */
public class BoardPrinterTest {
	
	private static final int BOARD_HEIGHT = 10;
	private static final int BOARD_WIDTH = 5;
	private static final Random RANDOM = new Random();
	
	@Test
	public void test() throws Exception {
		Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
		BoardPrinter.print(board);

		for(int i=0; i < 20; i++) {
			System.out.println("***");
			board.pushRow(newRandomRow());
			BoardPrinter.print(board);
			if (i < BOARD_HEIGHT - 1) {
				assertFalse(board.isFilledUp());
			} else {
				assertTrue(board.isFilledUp());
			}
			Thread.sleep(1000);
		}
	}

	private int[] newRandomRow() {
		int[] b = new int[BOARD_WIDTH];
		for(int i=0; i < b.length; i++) {
			int v = RANDOM.nextInt(6);
			b[i] =  v==0?1:v;
		}
		return b;
	}

}
