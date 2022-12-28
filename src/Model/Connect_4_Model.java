package Model;

import java.util.ArrayList;

import AI.*;
import Other.Board;
import Other.Tile;
import Other.Tile.Piece;
import View_Observer.ActionObserver;
import View_Observer.GameObserver;

public class Connect_4_Model implements Model {
	
	private ArrayList<ActionObserver> actionObservers = new ArrayList<ActionObserver>();
	private ArrayList<GameObserver> gameObservers = new ArrayList<GameObserver>();
	
	private int player = 1;
	private boolean isAI;
	private AI AItype;
	private Board board;
	private int mode = -1; //ko AI
	private int difficulty = 4;
	
	public Connect_4_Model() {
		
		//constructor model ko AI
		board = new Board();
		player = (int) (Math.random() * 2) + 1; //random người chơi đầu tiên
		this.isAI = false; //người chơi 2
	}
	
	public Connect_4_Model(int mode, int difficulty) {
		
		//constructor model có AI
		this.mode = mode;
		this.difficulty = difficulty;
		
		board = new Board();
		player = (int) (Math.random() * 2) + 1; //random người chơi đầu tiên
		this.isAI = true; //là AI
		
		switch (this.mode) {
			
			case 0:
				this.AItype = new AI_Main(board, this.difficulty, false); //minimax
				break;
				
			case 1:
				this.AItype = new AI_Main(board, this.difficulty, true); //alphabeta
				break;
				
			case 2:
				this.AItype = new AI_Dumb(board); //test
				break;
		}
	}
	
	public void setPiece(Tile tile, Piece piece) {
		
		board.setPiece(tile, piece);
		notifyGameObserver();
		notifyActionObserver();
	}
	
	public void dropPiece(Tile tile) {
		
		//nếu quân cờ đã dc thả
		if(board.dropPiece(tile, this.player)) {
			
			//Thông báo Observer update
			notifyGameObserver();
			notifyActionObserver();
		}
	}
	
	public int getMode() {
		
		return this.mode;
	}
	
	public int getDiff() {
		
		return this.difficulty;
	}
	
	public Tile getTile(int i, int j) {
		
		return board.getTile(i, j);
	}
	
	public Board getBoard() {
		
		return board;
	}
	
	public AI getAI() {
		
		return AItype;
	}
	
	public void setAI(AI AItype) {
		
		this.AItype = AItype;
	}
	
	public int getPlayer() {
		
		return player;
	}
	
	public void setPlayer(int turn) {
		
		this.player = turn;
	}
	
	public boolean isAI() {
		
		return isAI;
	}
	
	public void notifyGameObserver() {
		
		for (GameObserver o : gameObservers) {
			
			o.updateGame();
		}
	}
	
	public void notifyActionObserver() {
		
		for (ActionObserver o : actionObservers) {
			
			o.updateAction();
		}
	}

	public void registerObserver(ActionObserver o) {
		
		actionObservers.add(o);
	}
	
	public void registerObserver(GameObserver o) {
		
		gameObservers.add(o);
	}
	
	public void removeObserver(ActionObserver o) {
		
		actionObservers.remove(o);
	}
	
	public void removeObserver(GameObserver o) {
		
		gameObservers.remove(o);
	}
}
