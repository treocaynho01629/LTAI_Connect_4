package AI;

import Other.Board;
import Other.Tile;
import Other.Tile.Piece;

public class AI_Dumb implements AI { //AI để test th
	
	private Board board;
	
	public AI_Dumb(Board board){
		
		this.board = board;
	}
	
	private Tile randomMove() {
		
		int possibilities = 0;
		
		for (int i = 0; i < 7; i++) {
			
			if (board.getTile(i, 0).getPiece() == Piece.NONE) {
				
				possibilities++;
			}
		}
		
		return board.getTile((int)(Math.random() * (possibilities - 1)) + 0, 0);
	}

	public Tile makeMove() {
		
		return randomMove();
	}
}
