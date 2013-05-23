package nl.mprog.apps.othello;

import java.util.Map.Entry;

import nl.mprog.apps.othello.clickhandlers.OpenReplayClickHandler;
import nl.mprog.apps.othello.persistence.ReplaysHandler;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;

/**
 * De super class voor alle activities waar een othello spel gespeeld wordt.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class ReplayChoiceActivity extends Activity {
	
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_replay_choice);
		// Show the Up button in the action bar.
//		setupActionBar();
		layout = (LinearLayout) findViewById(R.id.replays);
		
		ReplaysHandler handler = new ReplaysHandler(this);
		
		for (Entry<String, String> entry : handler.getReplays().entrySet()) {
			addReplay(entry);
		}
	}
	
	private void addReplay(Entry<String, String> entry) {
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 25);
		
		OpenReplayClickHandler handler = new OpenReplayClickHandler(this, entry.getValue());
		
		Button button = new Button(this);
		button.setLayoutParams(lp);
		button.setBackgroundDrawable(getResources().getDrawable(R.drawable.mainbuttonborder));
		button.setText(entry.getKey());
		button.setTextColor(Color.parseColor("#ffffff"));
		button.setOnClickListener(handler);
		layout.addView(button);
	}

}
