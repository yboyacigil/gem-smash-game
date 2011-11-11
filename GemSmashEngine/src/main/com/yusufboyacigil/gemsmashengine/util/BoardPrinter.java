package com.yusufboyacigil.gemsmashengine.util;

import com.yusufboyacigil.gemsmashengine.model.Board;

/**
 *
 * @author yboyacigil
 */
public class BoardPrinter {

	public static void print(Board board) {
		System.out.print(" ");
		for(int i=0; i < board.width(); i++) {
			System.out.print("   " + i);
		}
		System.out.println();
		
		System.out.print("  ");
		for(int i=0; i < board.width(); i++) {
			System.out.print(" ---");
		}
		System.out.println();
		
		
		for(int i=0; i < board.height(); i++) {
			int[] row = board.row(i);
			System.out.print(i + " ");
			for(int j = 0; j < row.length; j++) {
				System.out.print("| " + row[j] + " ");
			}
			System.out.println("|");

			System.out.print("  ");
			for(int k=0; k < board.width(); k++) {
				System.out.print(" ---");
			}
			System.out.println();
		}
		
	}
	
}
