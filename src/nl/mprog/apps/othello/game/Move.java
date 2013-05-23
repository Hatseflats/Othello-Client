package nl.mprog.apps.othello.game;

/**
 * De model dat een move representeerd. Zo kun je makkelijk een hele game
 * opslaan als een lijst van moves.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class Move {
	
	private final int x;
	private final int y;
	
	public Move(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
