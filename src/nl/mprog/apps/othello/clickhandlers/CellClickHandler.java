package nl.mprog.apps.othello.clickhandlers;

import nl.mprog.apps.othello.GameActivity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * De clickhandler voor alle cellen in het bord.
 * Zodra er op een cell geklikt wordt, wordt de 
 * makeMove() methode aangeroepen van de bijbehorende activity.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class CellClickHandler implements OnClickListener{

	private GameActivity activity;
	private int x;
	private int y;
	
	public CellClickHandler(GameActivity gameActivity, int x, int y) {
		this.activity = gameActivity;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void onClick(View arg0) {
		activity.makeMove(x, y);
	}

}
