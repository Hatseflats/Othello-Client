package nl.mprog.apps.othello.clickhandlers;

import nl.mprog.apps.othello.StartGameActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartGameClickHandler implements OnClickListener {
	
	private Context context;
	
	public StartGameClickHandler(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(context, StartGameActivity.class);
		context.startActivity(intent);
	}

}
