package nl.mprog.apps.othello.clickhandlers;

import nl.mprog.apps.othello.StartLocalGameActivity;
import nl.mprog.apps.othello.game.Board;
import android.view.View;
import android.view.View.OnClickListener;

public class CellClickHandler implements OnClickListener{

	private StartLocalGameActivity activity;
	private int x;
	private int y;
	
	public CellClickHandler(StartLocalGameActivity startLocalGameActivity, int x, int y) {
		this.activity = startLocalGameActivity;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void onClick(View arg0) {
		this.activity.makeMove(x, y);
	}

}
