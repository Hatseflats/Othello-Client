package nl.mprog.apps.othello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onReplayClick(View view) {
		Intent intent = new Intent(this, ReplayActivity.class);
		startActivity(intent);
	}
	
	public void onlayOnlineClick(View view) {
		Intent intent = new Intent(this, StartNetworkGameActivity.class);
		startActivity(intent);
	}
	
	public void onStartGameClick(View view) {
		Intent intent = new Intent(this, StartLocalGameActivity.class);
		startActivity(intent);
	}

}
