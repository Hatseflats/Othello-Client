package tests;

import junit.framework.TestCase;
import nl.mprog.apps.othello.game.Cell;
import nl.mprog.apps.othello.game.Computer;
import nl.mprog.apps.othello.game.Game;

public class GameTest extends TestCase {
	
	public void testMakeMove() {
		Game game = new Game();
		game.initPositions();
		assertTrue(game.makeMove(2, 4));
		assertTrue(game.makeMove(2, 3));
		assertTrue(game.makeMove(1, 2));
		assertFalse(game.makeMove(0, 0));
		
		assertTrue(game.getMoves().size() == 3);
		
		assertFalse(game.checkVictoryConditions());
	}
	
	public void testComputer() {
		Game game = new Game();
		game.initPositions();
		
		game.makeMove(2, 4);
		
		Computer computer = new Computer(game, 2);
		Cell cell = computer.getMove();
		
		assertTrue(game.makeMove(cell.getX(), cell.getY()));
	}

}
