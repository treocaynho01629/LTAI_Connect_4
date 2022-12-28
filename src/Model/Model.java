package Model;

import AI.AI;
import Other.Board;
import Other.Tile;
import Other.Tile.Piece;
import View_Observer.ActionObserver;
import View_Observer.GameObserver;

public interface Model {
	
	void setPiece(Tile tile, Piece piece);
	void dropPiece(Tile tile);
	void registerObserver(ActionObserver o);
	void registerObserver(GameObserver o);
	void removeObserver(ActionObserver o);
	void removeObserver(GameObserver o);
	Board getBoard();
	Tile getTile(int i, int j);
	int getPlayer();
	void setPlayer(int turn);
	AI getAI();
	void setAI(AI AItype);
	boolean isAI();
	int getMode();
	int getDiff();
}
