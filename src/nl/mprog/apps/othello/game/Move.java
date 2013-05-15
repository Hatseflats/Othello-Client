package nl.mprog.apps.othello.game;

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
