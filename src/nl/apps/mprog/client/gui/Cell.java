package nl.apps.mprog.client.gui;

import android.view.View;
import android.widget.Button;

public class Cell {

	public int x;
	public int y;
	public Button view;
	public State state;
	
	public Cell(int x, int y, View view) {
		this.x = x;
		this.y = y;
		this.view = (Button) view;
		this.state = State.EMPTY;
	}
	
	public void changeState(State s){
        switch (s) {
	        case LIGHT:
	        	view.setText("L");
	            break;
	                
	        case DARK:
	        	view.setText("D");
	            break;
	                     
	        case EMPTY:
	        	view.setText("E");
	        	break;
        }
    }

}
