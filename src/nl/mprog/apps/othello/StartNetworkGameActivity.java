package nl.mprog.apps.othello;

import nl.mprog.apps.othello.clickhandlers.NetworkCellClickHandler;
import nl.mprog.apps.othello.game.Board;
import nl.mprog.apps.othello.game.Cell;
import nl.mprog.apps.othello.game.Game;
import nl.mprog.apps.othello.game.State;
import nl.mprog.apps.othello.interfaces.GameActivity;
import nl.mprog.apps.othello.network.ConnectionAsyncTask;
import nl.mprog.apps.othello.network.MoveAsyncTask;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.esotericsoftware.kryonet.Client;

public class StartNetworkGameActivity extends Activity implements GameActivity {

	private static final int GRID_SIZE = 8;
	
	private TableLayout grid;
	private Game game;
	
	private String networkgameId;
	private int playerColor;
	private Client client;
	
	private static final int PLAYER_WHITE = 1;
	private static final int PLAYER_BLACK = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_online);
		// Show the Up button in the action bar.
		setupActionBar();
		
		grid = (TableLayout) findViewById(R.id.table);
		
		game = new Game();
		game.initPositions();
		
		String android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID); 
				
		new ConnectionAsyncTask(this).execute(android_id);
		
	}
	
	public String getNetworkgameId() {
		return networkgameId;
	}

	public void setNetworkgame_id(String networkgame_id) {
		this.networkgameId = networkgame_id;
	}

	public int getPlayer_color() {
		return playerColor;
	}

	public void setPlayer_color(int player_color) {
		this.playerColor = player_color;
	}

	public void initGame(Client client){
		this.client = client;
		
		View view = findViewById(R.id.gamesearchbar);
		((RelativeLayout)view.getParent()).removeView(view);
		
		View scores = findViewById(R.id.scores);
		scores.setVisibility(View.VISIBLE);
		
		createBoard();
		refreshBoard(game.getBoard());
		
		if(playerColor == PLAYER_BLACK){ // lock ui, white always goes first
			toggleClickable(false);
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
		getMenuInflater().inflate(R.menu.play_online, menu);
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
	
	
	private void createBoard() {
		for (int j = 0; j < GRID_SIZE; j++) {
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			for (int i = 0; i < GRID_SIZE; i++) {
				LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				lp.setMargins(1, 1, 1, 1);
				
				ImageView imageView = new ImageView(this);
				imageView.setLayoutParams(lp);
				imageView.setBackgroundColor(Color.parseColor("#046e00"));
				imageView.setMinimumWidth(45);
				imageView.setMaxWidth(60);
				imageView.setMinimumHeight(45);
				imageView.setMaxHeight(60);
				imageView.setScaleType(ScaleType.CENTER_INSIDE);

				tr.addView(imageView);
					
				imageView.setOnClickListener(new NetworkCellClickHandler(this, i, j));
				
			}
			
			grid.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		}
	}
	
	public void refreshBoard(Board board) {
		for (Cell[] row : board.getCells()) {
			for (Cell cell : row) {
				changeCellImage(cell.getX(), cell.getY(), cell.getState());
			}
		}
		
		TextView whitePieceCount = (TextView) findViewById(R.id.whitepieces);
		TextView blackPieceCount = (TextView) findViewById(R.id.blackpieces);
		TextView turn = (TextView) findViewById(R.id.turn);
		
		whitePieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_WHITE)));
		blackPieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_BLACK)));
		
		if(game.getCurrentPlayer() == playerColor){
			turn.setText("Your turn");
		} else {
			turn.setText("Opponents turn");
		}
	}
	
	public void toggleClickable(boolean clickable){
		for (int i = 0; i < GRID_SIZE; i++) {
		    TableRow row = (TableRow) grid.getChildAt(i);
			for (int j = 0; j < GRID_SIZE; j++) {
			    ImageView cell = (ImageView) row.getChildAt(j);
			    cell.setClickable(clickable);
			}
		}
	}
	
	public void sendMove(int x, int y) {
		if( makeMove(x, y) ) {
			System.out.println(networkgameId + " " + playerColor);
			MoveAsyncTask moveTask = new MoveAsyncTask(client, networkgameId, playerColor);
			moveTask.execute(x, y);
		}
	}
	
	public boolean makeMove(int x, int y){
		if (game.makeMove(x, y)) {
			refreshBoard(game.getBoard());
			
			if(game.getCurrentPlayer() == playerColor){
				toggleClickable(true);
			} else {
				toggleClickable(false);
			}
	
			if(game.checkVictoryConditions()){
			}
			
			return true;
		} 
		return false;
	}
			
	private void changeCellImage(int x, int y, State state){
	    TableRow row = (TableRow) grid.getChildAt(y);
	    ImageView cell = (ImageView) row.getChildAt(x);
	    
        switch (state) {
	        case WHITE:
	        	cell.setImageResource(R.drawable.white_small);
	        	break;
	        case BLACK:
	        	cell.setImageResource(R.drawable.black_small);
	            break;
	        case EMPTY:
	        	cell.setImageResource(0);
	        	break;
	    }
	}
	

}
