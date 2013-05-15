package nl.mprog.apps.othello.game;

import static nl.mprog.apps.othello.game.State.*;

public class Cell {
	
	private int x, y;
	private State state;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		state = EMPTY;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
