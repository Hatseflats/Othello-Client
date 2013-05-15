package nl.mprog.apps.othello.clickhandlers;

import nl.mprog.apps.othello.StartNetworkGameActivity;
import android.view.View;
import android.view.View.OnClickListener;

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
