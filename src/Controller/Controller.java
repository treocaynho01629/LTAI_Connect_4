package Controller;

import Other.Board;
import Other.Tile;
import Other.Tile.Piece;
import View_Observer.ActionObserver;
import View_Observer.GameObserver;

public interface Controller {

	void registerObserver(ActionObserver o);
	void registerObserver(GameObserver o);
	void removeObserver(ActionObserver o);
	void removeObserver(GameObserver o);
	Board getBoard();
	Tile getTile(int i, int j);
	int getTurn();
	void setTurn(int i);
	boolean isWin();
	boolean isDraw();
	void setPiece(Tile tile, Piece piece);
	void dropPiece(Tile tile);
}
