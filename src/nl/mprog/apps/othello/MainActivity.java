package nl.mprog.apps.othello;

import nl.mprog.apps.othello.clickhandlers.StartGameClickHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StartGameClickHandler startGameClickHandler = new StartGameClickHandler(this);
		View startGame = findViewById(R.id.startgame);
		startGame.setOnClickListener(startGameClickHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
