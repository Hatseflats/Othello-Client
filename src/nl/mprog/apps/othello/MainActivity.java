package nl.mprog.apps.othello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Het hoofdscherm van de app.
 * Hier kun je switchen tussen
 * 
 * 	- Spelen tegen de computer
 * 	- Spelen tegen een andere speler
 * 	- Bekijken van replays
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onReplayClick(View view) {
		Intent intent = new Intent(this, ReplayChoiceActivity.class);
		startActivity(intent);
	}
	
	public void onPlayOnlineClick(View view) {
		Intent intent = new Intent(this, StartNetworkGameActivity.class);
		startActivity(intent);
	}
	
	public void onStartGameClick(View view) {
		Intent intent = new Intent(this, StartLocalGameActivity.class);
		startActivity(intent);
	}

}
