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

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.replay, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
