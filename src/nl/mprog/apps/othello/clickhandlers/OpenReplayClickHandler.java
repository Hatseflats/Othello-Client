package nl.mprog.apps.othello.clickhandlers;

import nl.mprog.apps.othello.PlayReplayActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class OpenReplayClickHandler implements OnClickListener {
	
	private final Context context;
	private final String name;
	private final String moves;
	
	public OpenReplayClickHandler(final Context context, final String name, final String moves) {
		this.context = context;
		this.name = name;
		this.moves = moves;
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(context, PlayReplayActivity.class);
		intent.putExtra("moves", moves);
		context.startActivity(intent);
	}

}
