package Controller;

import Model.Model;
import Other.Board;
import Other.Tile;
import Other.Tile.Piece;
import View_Observer.ActionObserver;
import View_Observer.Connect_4_View;
import View_Observer.Connect_4_View_Test;
import View_Observer.Connect_4_View_Test_2;
import View_Observer.GameObserver;

public class Connect_4_Controller implements Controller {
	
	private Model model;
	private Connect_4_View view;
	
	//Test
//	private Connect_4_View_Test_2 view;
	
	public Connect_4_Controller(Model model) {
		
		this.model = model;
		this.view = new Connect_4_View(this, model);
		view.createView();
//		new Connect_4_View_Test(this, model).createView(); //Test
		
		//Test
//		this.view = new Connect_4_View_Test_2(this, model);
//		view.createView();
//		view.createTest();
	}
	
	public void setPiece(Tile tile, Piece piece) {
		
		model.setPiece(tile, piece);
	}
	
	public void dropPiece(Tile tile) {
		
		model.dropPiece(tile);
	}
	
	public Board getBoard(){
		
		return model.getBoard();
	}
	
	public Tile getTile(int i, int j) {
		
		return model.getTile(i, j);
	}
	
	public int getTurn() {
		
		return model.getPlayer();
	}
	
	public void setTurn(int player) {
		
		model.setPlayer(player);
	}
	
	public void registerObserver(ActionObserver o) {
		
		model.registerObserver(o);
	}
	
	public void registerObserver(GameObserver o) {
		
		model.registerObserver(o);
	}
	
	public void removeObserver(ActionObserver o) {
		
		model.removeObserver(o);
	}
	
	public void removeObserver(GameObserver o) {
		
		model.removeObserver(o);
	}
	
	public boolean isWin() {
		
		return model.getBoard().isWin();
	}
	
	public boolean isDraw() {
		
		return model.getBoard().isDraw();
	}
}
