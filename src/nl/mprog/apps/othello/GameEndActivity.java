package nl.mprog.apps.othello;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import nl.mprog.apps.othello.persistence.ReplaysHandler;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameEndActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_end);
		// Show the Up button in the action bar.
//		setupActionBar();
		
		boolean won = getIntent().getBooleanExtra("gameWon", false);
		TextView textView = (TextView) findViewById(R.id.gameendtext);
		if (won) {
			textView.setText("Congratulations, you won the game!");
		} else {
			textView.setText("Too bad, you lost the game!");
		}
		
		TextView replayName = (TextView) findViewById(R.id.replayname);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		replayName.setText(dateFormat.format(date));
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
		getMenuInflater().inflate(R.menu.game_end, menu);
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
	
	public void saveReplay(View view) {
		String name = ((TextView) findViewById(R.id.replayname)).getText().toString();
		String movesString = getIntent().getStringExtra("moves");
		ReplaysHandler handler = new ReplaysHandler(this);
		handler.addReplay(name, movesString);
		
		((Button) view).setEnabled(false);
		((Button) view).setText("REPLAY SAVED!");
		
		Toast.makeText(this, "Replay saved!", 5).show();
	}
	
	public void backToMainscreen(View view) {
		setResult(RESULT_OK);
		finish();
	}

}
