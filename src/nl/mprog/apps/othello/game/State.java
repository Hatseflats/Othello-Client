package nl.mprog.apps.othello.game;

public enum State {
	EMPTY(0),
	WHITE(1),
	BLACK(2);
	
	private final int state;
	
	State(int state){
		this.state = state;
	}
	
	public int state(){
		return state;
	}
		
	public static State getEnumByInt(int i){
		switch(i){
			case(1) :
				return WHITE;
			case(2):
				return BLACK;
			default:
				return EMPTY;
		}
	}
}
