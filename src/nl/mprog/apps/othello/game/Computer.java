package nl.mprog.apps.othello.game;

import java.util.List;

public class Computer {

	private Game game;
	private int player;


	public Computer(Game game, int player) {
		this.game = game;
		this.player = player;
	}
	
	public Cell getMove(){
		Board board = game.getBoard();
		
		List<Cell> availableMoves = board.getAvailableMoves(player);

		int highestCount = 0;
		int highestKey = 0;
		
		for (int i = 0; i < availableMoves.size(); i++) {
			Cell cell = availableMoves.get(i);
			
			int currentCount = board.flipEnemyPieces(cell.getX(), cell.getY(), player).size();
			
			if(currentCount > highestCount){
				highestCount = currentCount;
				highestKey = i;
			}
		}
		
		return availableMoves.get(highestKey);
	}
	
}
