package nl.mprog.apps.othello.clickhandlers;

import nl.mprog.apps.othello.StartNetworkGameActivity;
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
public class NetworkCellClickHandler implements OnClickListener{

	private StartNetworkGameActivity activity;
	private int x;
	private int y;
	
	public NetworkCellClickHandler(StartNetworkGameActivity startNetworkGameActivity, int x, int y) {
		this.activity = startNetworkGameActivity;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void onClick(View arg0) {
		activity.sendMove(x, y);
	}

}
