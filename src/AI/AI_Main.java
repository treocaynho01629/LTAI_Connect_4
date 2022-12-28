package AI;

import java.util.ArrayList;

import Other.Board;
import Other.Tile;
import Other.Tile.Piece;

public class AI_Main implements AI { //AI chính

	private Board board; //bàn cờ
	private int maxDepth; //số nước đi AI đoán trc
	private boolean alphaBeta = true; //có cắt tỉa alpha beta ko
	private static final int PLAYER = 1;
	private static final int AI = 2;
	
	public AI_Main(Board board, int maxDepth) {
		
		this.board = board;
		this.maxDepth = maxDepth;
	}
	
	public AI_Main(Board board, int maxDepth, boolean alphaBeta) {
		
		this.board = board;
		this.maxDepth = maxDepth;
		this.alphaBeta = alphaBeta;
	}
	
	public Tile alphaBeta(Board board) {
		
		//muốn AI thắng thì max() | min() AI luôn tìm cách thua
		int bestMove = max(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE)[0]; //lấy cột move [1] trong (int[].length = 2) được trả về
		return board.getTiles()[bestMove][0];
	}
	
	public Tile miniMax(Board board) {
		
		//muốn AI thắng thì max() | min() AI luôn tìm cách thua
		int bestMove = max(board, 0)[0]; //lấy cột move [1] trong (int[].length = 2) được trả về
		return board.getTiles()[bestMove][0];
	}
	
	public int[] max(Board board, int depth, int alpha, int beta) {
		
		int[] max = new int[2]; //int[0] là move, int[1] là value của move
		
		if (depth == maxDepth || board.isTerminal()) { //depth cuối cùng || kết thúc r thì chỉ cần trả value để so sánh
			
			if (board.isTerminal()) { //nếu bàn cờ đã kết thúc
				
				if (board.isWin() && board.getWinner() == AI) { //người thắng là AI >> trả về value cực lớn để depth trước so sánh
					
					max[0] = -1;
					max[1] = Integer.MAX_VALUE;
					return max;
				} else if (board.isWin() && board.getWinner() == PLAYER){ //người chơi thắng >> trả value cực bé để depth trước so sánh
					
					max[0] = -1;
					max[1] = Integer.MIN_VALUE;
					return max;
				} else { //hoà >> value 0
					
					//draw
					max[0] = -1;
					max[1] = 0;
					return max;
				}
			} else { //ko thì lấy value hiện tại của bàn cờ
				
				max[0] = -1;
				max[1] = board.getValue();
				return max;
			}
		}
		
		//xem số nước đi có thể đi được tỏng bàn cờ
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		
		for (int i = 0; i < 7; i++) {
			
			if (board.getTiles()[i][0].getPiece() == Piece.NONE) {
				
				possibilities.add(i);
			}
		}
		
		//gán value tạm thời
		int maxValue = Integer.MIN_VALUE;
		int maxMove = possibilities.get((int)(Math.random() * 100) % possibilities.size()); //random trong các nước có thể đi
		
		//xét các nước
		for (int i = 0; i < possibilities.size(); i++) {
			
			board.dropPiece(board.getTiles()[possibilities.get(i)][0], depth); //thả quân vào bàn
			Tile prev = board.getPrevTile(); //set quân vừa đi vào bàn
			int[] child = min(board, depth + 1, alpha, beta); //xét child min với depth tăng 1
			
			//so sánh value hiện tại với child (nếu hơn >> thay thế)
			if (maxValue < child[1]) {
				
				maxValue = child[1];
				maxMove = possibilities.get(i);
			}
			
			//debug
//			board.debug();
//			System.out.println("max " + maxValue);
			
			prev.setPiece(Piece.NONE); //reset quân cờ vừa xét vào = ô trống
			
			//cắt tỉa alpha beta
			alpha = Math.max(alpha, maxValue);
			if (alpha >= beta) {
				
				break;
			}
		}
		
		max[0] = maxMove;
		max[1] = maxValue;
		return max;
	}
	
	public int[] max(Board board, int depth) {
		
		int[] max = new int[2]; //int[0] là move, int[1] là value của move
		
		if (depth == maxDepth || board.isTerminal()) {
			
			if (board.isTerminal()) {
				
				if (board.isWin() && board.getWinner() == AI) {
					
					max[0] = -1;
					max[1] = Integer.MAX_VALUE;
					return max;
				} else if (board.isWin() && board.getWinner() == PLAYER){
					
					max[0] = -1;
					max[1] = Integer.MIN_VALUE;
					return max;
				} else {
					
					//draw
					max[0] = -1;
					max[1] = 0;
					return max;
				}
			} else {
				
				max[0] = -1;
				max[1] = board.getValue();
				return max;
			}
		}
		
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		
		for (int i = 0; i < 7; i++) {
			
			if (board.getTiles()[i][0].getPiece() == Piece.NONE) {
				
				possibilities.add(i);
			}
		}
		
		int maxValue = Integer.MIN_VALUE;
		int maxMove = possibilities.get((int)(Math.random() * 100) % possibilities.size());
		
		for (int i = 0; i < possibilities.size(); i++) {
			
			board.dropPiece(board.getTiles()[possibilities.get(i)][0], depth);
			Tile prev = board.getPrevTile();
			int[] child = min(board, depth + 1);
			
			if (maxValue < child[1]) {
				
				maxValue = child[1];
				maxMove = possibilities.get(i);
			}
			
			//debug
//			board.debug();
//			System.out.println("max " + maxValue);
			
			prev.setPiece(Piece.NONE); //reset bàn cờ sau khi đã kiểm tra max
		}
		
		max[0] = maxMove;
		max[1] = maxValue;
		return max;
	}
	
	public int[] min(Board board, int depth, int alpha, int beta) { //ngược với max
		
		int[] min = new int[2];
		
		if (depth == maxDepth || board.isTerminal()) {
			
			if (board.isTerminal()) {
				
				if (board.isWin() && board.getWinner() == AI) {
					
					//win
					min[0] = -1;
					min[1] = Integer.MAX_VALUE;
					return min;
				} else if (board.isWin() && board.getWinner() == PLAYER){
					
					min[0] = -1;
					min[1] = Integer.MIN_VALUE;
					return min;
				} else {
					
					//draw
					min[0] = -1;
					min[1] = 0;
					return min;
				}
			} else {
				
				min[0] = -1;
				min[1] = board.getValue();
				return min;
			}
		}
		
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		
		for (int i = 0; i < 7; i++) {
			
			if (board.getTiles()[i][0].getPiece() == Piece.NONE) {
				
				possibilities.add(i);
			}
		}
		
		int minValue = Integer.MAX_VALUE;
		int minMove = possibilities.get((int)(Math.random() * 100) % possibilities.size());
		
		for (int i = 0; i < possibilities.size(); i++) {
			
			board.dropPiece(board.getTiles()[possibilities.get(i)][0], depth);
			Tile prev = board.getPrevTile();
			int[] child = max(board, depth + 1, alpha, beta);
			
			if (minValue > child[1]) {
				
				minValue = child[1];
				minMove = possibilities.get(i);
			}
			
			//debug
//			board.debug();
//			System.out.println("min " + minValue);
			
			prev.setPiece(Piece.NONE); //reset bàn cờ khi đã kiểm tra min
			
			//cắt tỉa alpha beta
			beta = Math.min(beta, minValue);
			if (alpha >= beta) {
				
				break;
			}
		}
		
		min[0] = minMove;
		min[1] = minValue;
		return min;
	}
	
	public int[] min(Board board, int depth) {
		
		int[] min = new int[2];
		
		if (depth == maxDepth || board.isTerminal()) {
			
			if (board.isTerminal()) {
				
				if (board.isWin() && board.getWinner() == AI) {
					
					//win
					min[0] = -1;
					min[1] = Integer.MAX_VALUE;
					return min;
				} else if (board.isWin() && board.getWinner() == PLAYER){
					
					min[0] = -1;
					min[1] = Integer.MIN_VALUE;
					return min;
				} else {
					
					//draw
					min[0] = -1;
					min[1] = 0;
					return min;
				}
			} else {
				
				min[0] = -1;
				min[1] = board.getValue();
				return min;
			}
		}
		
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		
		for (int i = 0; i < 7; i++) {
			
			if (board.getTiles()[i][0].getPiece() == Piece.NONE) {
				
				possibilities.add(i);
			}
		}
		
		int minValue = Integer.MAX_VALUE;
		int minMove = possibilities.get((int)(Math.random() * 100) % possibilities.size());
		
		for (int i = 0; i < possibilities.size(); i++) {
			
			board.dropPiece(board.getTiles()[possibilities.get(i)][0], depth);
			Tile prev = board.getPrevTile();
			int[] child = max(board, depth + 1);
			
			if (minValue > child[1]) {
				
				minValue = child[1];
				minMove = possibilities.get(i);
			}
			
			//debug
//			board.debug();
//			System.out.println("min " + minValue);
			
			prev.setPiece(Piece.NONE); //reset bàn cờ khi đã kiểm tra min
		}
		
		min[0] = minMove;
		min[1] = minValue;
		return min;
	}
	
	public Tile pickBestMove(Board board) { //Để test điểm
		
		int bestValue = 0;
		
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		
		for (int i = 0; i < 7; i++) {
			
			if (board.getTiles()[i][0].getPiece() == Piece.NONE) {
				
				possibilities.add(i);
			}
		}
		
		Tile bestMove = board.getTiles()[(int)(Math.random() * 100) % possibilities.size()][0];
		
		for (int i = 0; i < possibilities.size(); i++) {
			
			board.dropPiece(board.getTiles()[possibilities.get(i)][0], AI);
			Tile prev = board.getPrevTile();
			int value = board.getValue();
			board.debug();
			System.out.println(value);
			if (value >= bestValue) {
				
				bestValue = value;
				bestMove = board.getTiles()[possibilities.get(i)][0];
			}
			prev.setPiece(Piece.NONE);
		}
		
		return bestMove;
	}
	
	public Tile makeMove() {
		
		System.gc(); //dọn dẹp hệ thống trước khi đo
		
		//st - et = thời gian để minimax/alphabeta			sm - em = bộ nhớ sử dụng
		long sm = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); //start mem
		long st = System.currentTimeMillis(); //start time
		Tile move;
		
		if (alphaBeta) {
			
			move = alphaBeta(board);
			long em = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); //end mem
			long et = System.currentTimeMillis(); //end time
			System.out.println("AlphaBeta in: " + (et - st) + " ms - Memory used: " + ((em - sm) / 1024) + " KB used - Move: " + move.getI() + ", " + move.getJ());
		} else {
			
			move = miniMax(board);
			long em = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); //end mem
			long et = System.currentTimeMillis(); //end time
			System.out.println("MiniMax in: " + (et - st) + " ms - Memory used: " + ((em - sm) / 1024) + " KB used - Move: " + move.getI() + ", " + move.getJ());
		}
		
		return move;
	}

}
