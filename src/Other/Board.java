package Other;

import Other.Tile;
import Other.Tile.Piece;

public class Board {

	private Tile[][] tiles;
	
	private Tile prevTile; //tile vừa được thêm vào bàn cờ
	private int winner; //người thắng
	private int turn; //sỐ lượt đi
	
	public Board() {
		
		this.prevTile = null; 
		this.winner = 0;
		this.tiles = new Tile[7][6]; //bàn cờ 7x6
		this.turn = 0;
		
		for (int j = 0; j < 6; j++) {
			
			for (int i = 0; i < 7; i++) {
				
				tiles[i][j] = new Tile(i, j);
			}
		}
	}
	
	public void setPiece(Tile tile, Piece piece) {
		
		tile.setPiece(piece);
	}
	
	public boolean dropPiece(Tile tile, int playerOrDepth) {
		
		//function thả quân cờ vào bàn (cột)
		int i = tile.getI();
		int j = 0; //cho quân cờ lên cao nhất
		int player = playerOrDepth % 2; //tính lượt của người chơi dựa theo player hoặc depth (1 là player 1, 0 là player 2)
		
		if (tiles[i][0].getPiece() != Piece.NONE) {
			
			//kiểm tra nếu cột của bàn cờ đã đầy >> ko thêm quân mới
			return false; //thả quân thất bại
		}
		
		while (j < tiles[0].length - 1 && tiles[i][j + 1].getPiece() == Piece.NONE) {
			
			//kiểm tra quân cờ đã chạm đáy chưa | nếu chưa >> giảm xuống 1 tile đến khi chạm đáy
			//chiều bàn cờ tăng dần sang phải và tăng dần xuống đáy >> j++
			j++;
		}
		
		switch (player) {
		
		case 1:
			tiles[i][j].setPiece(Piece.P1); //nếu 1 >> player 1 >> quân XANH
			break;
			
		case 0:
			tiles[i][j].setPiece(Piece.P2); //nếu 0 >> player 2 >> quân ĐỎ
			break;
		}
		
		this.prevTile = tiles[i][j]; //set nước đi vừa đi
		this.turn++; //tăng lượt
		return true; //trả về true nếu thả quân thành công
	}
	
	public int getTopEmpty(int i) {
		
		for (int j = 5; j >= 0; j--) {
			
			if (tiles[i][j].getPiece() == Piece.NONE) {
				
				return j;
			}
		}
		
		return -1;
	}
	
	public boolean isTerminal() {
		
		//kiểm tra kết thúc
		if(isWin()) {
			
			return true;
		} else {
			
			return isDraw();
		}
	}
	
	public boolean isWin() {
		
		//kiểm tra game End chưa >> duyệt cả bảng 7 x 6 
		for (int j = 0; j < 6; j++) {
			
			for (int i = 0; i < 7; i++) {
				
				//isConnection cos giá trị >= 100 là win r
				if (tiles[i][j].getPiece() != Piece.NONE && isConnection(i, j) >= 100) {
					
					Piece winner = tiles[i][j].getPiece();
					
					switch (winner) {
					
					case P1:
						
						this.winner = 1;
						return true;
					case P2:
						
						this.winner = 2;
						return true;
					default:
						
						break;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean isDraw() {
		
		//kiểm tra bàn cờ đầy ~ hoà
		for (int j = 0; j < 6; j++) {
			
			for (int i = 0; i < 7; i++) {
				
				if (tiles[i][j].getPiece() == Piece.NONE) {
					
					return false;
				}
			}
		}
		
		return true;
	}
	
	public int getValue() { //cho heuristic
		
		//tính value của bàn cờ
		int value = 0; //value > 0 >> có lợi, value >= 100 >> AI thắng, value <= -100 >> AI thua
		
		//xét cả bàn cờ 7 x 6
		for (int j = 0; j < 6; j++) {
			
			for (int i = 0; i < 7; i++) {
				
				int empty = 0; //đếm ô trống của cột giữa
				
				if (tiles[i][j].getPiece() != Piece.NONE) {
					
					if (i == 3) { //nếu là cột giữa >> đếm ô trống của cột để ưu tiên tăng value
						
						for (int k = 0; k < 6; k++) {
							
							if (tiles[i][k].getPiece() == Piece.NONE) {
								
								empty++;
							}
						}
					}
					
					if (tiles[i][j].getPiece() == Piece.P2) {
						
						//nếu là AI (RED) >> tăng value
						value += isConnection(i, j) + (empty * 3);
					} else {
						
						//nếu là người chơi >> giảm value
						value -= isConnection(i, j);
					}
				}
			}
		}
		
		return value;
	}
	
//	private int isSevenTrap(int i, int j) { //bỏ (AI heuristic đủ để dự đoán trap 7 r :v)
//		
//		//evaluate
//		int value = 70;
//		Piece color = tiles[i][j].getPiece(); // lấy màu đang xét
//		
//		if (i < 4 && j > 1) { // 7 xuôi
//			
//			if (tiles[i][j].getPiece() != color || tiles[i + 1][j - 1].getPiece() != color || tiles[i + 2][j - 2].getPiece() != color 
//					|| tiles[i][j - 2].getPiece() != color || tiles[i + 1][j - 2].getPiece() != color
//					|| tiles[i + 3][j - 2].getPiece() != Piece.NONE) {
//				
//				return 0;
//			}
//		} else if (i > 2 && j > 1) { // 7 ngược
//			
//			if (tiles[i][j].getPiece() != color || tiles[i - 1][j - 1].getPiece() != color || tiles[i - 2][j - 2].getPiece() != color 
//					|| tiles[i][j - 2].getPiece() != color || tiles[i - 1][j - 2].getPiece() != color
//					|| tiles[i - 3][j - 2].getPiece() != Piece.NONE) {
//				
//				return 0;
//			}
//		}
//		
//		return value;
//	}
	
	private int isConnection(int i, int j) {
		
		//evaluate
		int value = 0;
		
		value += lineOfFour(i, j, -1, -1);
	    value += lineOfFour(i, j, -1, 0);
	    value += lineOfFour(i, j, -1, 1);
	    value += lineOfFour(i, j, 0, -1);
		//duyệt theo 4 hàng: ngang - dọc - chéo lên - chéo xuống
	    value += (lineOfFour(i, j, 0, 1)); //h = 0, c = 1 >> duyệt dưới lên 
	    value += (lineOfFour(i, j, 1, -1)); //h = 1, c = -1 >> duyệt chéo xuống trái sang phải
	    value += (lineOfFour(i, j, 1, 0)); //h = 1, c = 0 >> duyệt trái sang phải
	    value += (lineOfFour(i, j, 1, 1)); //h = 1, c = 1 >> duyệt chéo lên trái sang phải
	    
	    return value;
	}
	
	private int lineOfFour(int i, int j, int h, int c) {
		
		//kiểm tra 4 ô liên tục có trùng | h để tính hàng, c để tính cột
		int value = 0;
		int count = 1; //số ô trùng màu
		Piece color = tiles[i][j].getPiece(); // lấy màu đang xét
		
		for (int k = 1; k < 4; k++) {
			
			if (i + h * k < 0 || j + c * k < 0 || i + h * k >= 7 || j + c * k >= 6) {
				
				//nếu quân cờ nằm ngoài bàn cờ >> false
				return value = 0;
			}
			if (tiles[i + h * k][j + c * k].getPiece() == color) {
				
				//trùng màu
				count++;
			} else if (tiles[i + h * k][j + c * k].getPiece() != Piece.NONE) {
				
				//khác màu >> hàng kiểm tra không còn giá trị gì >> 0
				return 0;
			} else if (tiles[i + h * k][j + c * k].getPiece() == Piece.NONE) {
				
				//trống >> ko làm j
			}
		}
		
		switch (count) {
		
		case 4:
			return value = 100; //4 ô win
		case 3:
			if (color == Piece.P2) {
				return value = 5; //3 ô +5 value cho AI
			} else {
				return value = 4; //3 ô +4 value cho ng chơi
			}
		case 2:
			return value = 2; //2 ô +2 value
		}
		
		return value;
	}
	
	public void connectFour() {
		
		Piece winPiece = Piece.NONE;
		
		if (winner == 1) {
			
			winPiece = Piece.P1;
		} else {
			
			winPiece = Piece.P2;
		}
		
		
		for (int j = 0; j < 6; j++) {
			
			for (int i = 0; i < 7; i++) {
				
				if (tiles[i][j].getPiece() == winPiece && isConnection(i, j) >= 100) {
					
					if (lineOfFour(i, j, 0, 1) >= 100) {
						
						for (int k = 0; k < 4; k++) {
							
							tiles[i + 0 * k][j + 1 * k].setPiece(Piece.WIN);
						}
					} else if (lineOfFour(i, j, 1, -1) >= 100) {
						
						for (int k = 0; k < 4; k++) {
							
							tiles[i + 1 * k][j + -1 * k].setPiece(Piece.WIN);
						}
					} else if (lineOfFour(i, j, 1, 0) >= 100) {
						
						for (int k = 0; k < 4; k++) {
							
							tiles[i + 1 * k][j + 0 * k].setPiece(Piece.WIN);
						}
					} else if (lineOfFour(i, j, 1, 1) >= 100) {
						
						for (int k = 0; k < 4; k++) {
							
							tiles[i + 1 * k][j + 1 * k].setPiece(Piece.WIN);
						}
					}
				}
			}
		}
	}
	
	public Tile[][] getTiles(){
		
		return tiles;
	}
	
	public Tile getTile(int i, int j) {
		
		return tiles[i][j];
	}
	
	public Tile getPrevTile() {
		
		return prevTile;
	}
	
	public void setPrevTile(Tile prevTile) {
		
		this.prevTile = prevTile;
	}
	
	public int getWinner() {
		
		return winner;
	}
	
	public int getTurns() {
		
		return turn;
	}
	
	public void setTurns(int turn) {
		
		this.turn = turn;
	}
	
	public void debug() {
		
		System.out.println("|\\O==============w==============O/|");
		
		for (int j = 0; j < 6; j++) {
			
			for (int i = 0; i < 7; i++) {
				
				Piece color = tiles[i][j].getPiece();
					
				switch (color) {
				
					case NONE:
						System.out.print("[   ]");
						break;
					case P2:
						System.out.print("[ 2 ]");
						break;
					case P1:
						System.out.print("[ 1 ]");
						break;
					default:
						break;
				}
			}
			
			System.out.println();
		}
		
		System.out.println("===================================");

	}
}
