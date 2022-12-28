package Test;

import java.util.ArrayList;

import Other.Board;
import Other.Tile;
import Other.Tile.Piece;

public class Test {
	
	//for test stuff only
	
	public static void dropPiece(Tile[][] tiles, Tile tile, int player) {
		
		int i = tile.getI();
		int j = 0; //cho quân cờ lên cao nhất
		
		if (tiles[i][0].getPiece() != Piece.NONE) {
			
			System.err.println("Cột " + (i + 1) + " đã đầy");
			return;
		}
		
		while (j < tiles[0].length - 1 && tiles[i][j + 1].getPiece() == Piece.NONE) {
			
			j++;
		}
		
		switch (player) {
		
		case 1:
			
//			tiles[i][j] = new Tile(i, j, Piece.BLUE);
			break;
			
		case 2:
			
//			tiles[i][j] = new Tile(i, j, Piece.RED);
			break;
		}
		
	}
	
	public static int testMax(Board board, int depth) {
		
		board.debug();
		int value = -1;
		int player = 0;
		
		if (depth % 2 == 0) {
			
			player = 2;
		} else {
			
			player = 1;
		}
		
		if (depth == 2) {
			
			return 0;
		} else {
			
			ArrayList<Integer> possibilities = new ArrayList<Integer>();
			
			for (int i = 0; i < 7; i++) {
				
				if (board.getTiles()[i][0].getPiece() == Piece.NONE) {
					
					possibilities.add(i);
				}
			}
			
			for (int i = 0; i < possibilities.size(); i++) {
				
				board.dropPiece(board.getTiles()[possibilities.get(i)][0], player);
				Tile prev = board.getPrevTile();
				value = testMin(board, depth + 1);
				prev.setPiece(Piece.NONE);
			}
		}
		
		return value;
	}
	
	public static int testMin(Board board, int depth) {
		
		board.debug();
		int value = -1;
		int player = 0;
		
		if (depth % 2 == 0) {
			
			player = 2;
		} else {
			
			player = 1;
		}
		
		if (depth == 2) {
			
			return 0;
		} else {
			
			ArrayList<Integer> possibilities = new ArrayList<Integer>();
			
			for (int i = 0; i < 7; i++) {
				
				if (board.getTiles()[i][0].getPiece() == Piece.NONE) {
					
					possibilities.add(i);
				}
			}
			
			for (int i = 0; i < possibilities.size(); i++) {
				
				board.dropPiece(board.getTiles()[possibilities.get(i)][0], player);
				Tile prev = board.getPrevTile();
				value = testMax(board, depth + 1);
				prev.setPiece(Piece.NONE);
			}
		}
		
		return value;
	}
	
//	public static void main(String[] args) {
//		
//		Board board = new Board();
//		board.dropPiece(new Tile(1, 1), 1);
////		board.debug();
////		
////		long st = System.currentTimeMillis(); //start time
////		
////		Board test = new Board(board);
//////		test.dropPiece(new Tile(2, 2), 2);
////		dropPiece(test.getTiles(), new Tile(2, 2), 2);
//////		test.getTiles()[2][5] = new Tile(2, 5, Piece.RED);
////		test.debug();
////		board.debug();
////		
////		long et = System.currentTimeMillis(); //end time
////	
////		System.out.println("Clone in: " + (et - st) + "ms");
////		
////		int[][] arr = new int[7][6];
////		arr[0][0] = 69;
////		int[][] clone = arr.clone();
////		clone[1][1] = 420;
////		
////		System.out.println(arr[1][1]);
////		
////		for (int i = 0; i < 7; i++) {
////			
////			for (int j = 0; j < 6; j++) {
////				
////				System.out.print(arr[i][j] + " ");
////			}
////			
////			System.out.println("");
////		}
//		
////		testMax(board, 0);
//		
//		int test = (int)(Math.random() * 100) % 1;
//		System.out.println(test);
//	}
}
