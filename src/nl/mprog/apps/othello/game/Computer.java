package nl.mprog.apps.othello.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Computer {

	public Game game;
	public int player;


	public Computer(Game game, int player) {
		this.game = game;
		this.player = player;
	}
	
	public Cell getMove(){
		Board board = this.game.getBoard();
		
		LinkedList<Cell> availableMoves = board.getAvailableMoves(player);

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
