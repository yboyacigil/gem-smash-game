package com.yusufboyacigil.gemsmashengine.util;

import com.yusufboyacigil.gemsmashengine.model.Board;

/**
 *
 * @author yboyacigil
 */
public class BoardPrinter {

	public static String build(Board board) {
		StringBuilder builder = new StringBuilder();
		builder.append(" ");
		for(int i=0; i < board.width(); i++) {
			builder.append("   ").append(i);
		}
		builder.append("\n");
		
		builder.append("  ");
		for(int i=0; i < board.width(); i++) {
			builder.append(" ---");
		}
		builder.append("\n");
		
		
		for(int i=0; i < board.height(); i++) {
			int[] row = board.row(i);
			builder.append(i).append(" ");
			for(int j = 0; j < row.length; j++) {
				builder.append("| ").append(row[j]).append(" ");
			}
			builder.append("|\n");

			builder.append("  ");
			for(int k=0; k < board.width(); k++) {
				builder.append(" ---");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public static void print(Board board) {
		System.out.println(build(board));
	}
	
}
