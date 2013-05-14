package nl.mprog.apps.othello;

import nl.mprog.apps.othello.clickhandlers.CellClickHandler;
import nl.mprog.apps.othello.game.Board;
import nl.mprog.apps.othello.game.Cell;
import nl.mprog.apps.othello.game.Computer;
import nl.mprog.apps.othello.game.Game;
import nl.mprog.apps.othello.game.State;
import nl.mprog.apps.othello.helpers.MoveHelper;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class StartLocalGameActivity extends Activity {
	
	private static final int GRID_SIZE = 8;
	
	private static final int RESULT_ID = 1337;
	
	private static final int PLAYER_WHITE = 1;
	private static final int PLAYER_BLACK = 2;
	
	private TableLayout grid;
	
	private Game game;
	private Computer computer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);
		
		grid = (TableLayout) findViewById(R.id.table);
		game = new Game();
		game.initPositions();
		
		computer = new Computer(game, PLAYER_BLACK);
		
		createBoard();
		refreshBoard(game.getBoard());
		
		// 		Show the Up button in the action bar.
		//		setupActionBar();
	}

	public void refreshBoard(Board board) {
		Cell[][] cells = board.getCells();
		
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				changeCellImage(cell.getX(), cell.getY(), cell.getState());
			}
		}
		
		TextView whitePieceCount = (TextView) findViewById(R.id.whitepieces);
		TextView blackPieceCount = (TextView) findViewById(R.id.blackpieces);
		TextView turn = (TextView) findViewById(R.id.turn);
		
		whitePieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_WHITE)));
		blackPieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_BLACK)));
		
		if(game.getCurrentPlayer() == PLAYER_WHITE){
			turn.setText(R.string.turn_player_white);
		} else {
			turn.setText(R.string.turn_player_black);
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
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		//		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_game, menu);
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
	
	public void makeMove(int x, int y){
		if(game.makeMove(x, y)){
			
			refreshBoard(game.getBoard());
			
			if(game.getCurrentPlayer() == PLAYER_BLACK){
				TextView button = (TextView) findViewById(R.id.makecomputermove);
				button.setClickable(true);
				toggleClickable(false);
			}
			

			if(game.checkVictoryConditions()){
				gameEnd();
				return;
			}
			
		}
	}
	
	public void computerMove(View view){
		if(game.getCurrentPlayer() == PLAYER_BLACK){
			Cell computercell = computer.getMove();

			makeMove(computercell.getX(), computercell.getY());
			
			if(game.getCurrentPlayer() == PLAYER_WHITE){
				TextView button = (TextView) findViewById(R.id.makecomputermove);
				button.setClickable(false);
				toggleClickable(true);
			}
		}
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
					
				imageView.setOnClickListener(new CellClickHandler(this, i, j));
				
			}
			
			grid.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		}
	}
	
	private void gameEnd() {
		Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
		boolean gameWon = false;
		
		int whitePieces = game.getPieceCount(PLAYER_WHITE);
		int blackPieces = game.getPieceCount(PLAYER_BLACK);
		
		if(whitePieces > blackPieces ){
			gameWon = true;
			Toast.makeText(this, "White wins!", Toast.LENGTH_SHORT).show();
		} else if(whitePieces == blackPieces){
			Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Black wins!", Toast.LENGTH_SHORT).show();
		}

		Intent intent = new Intent(this, GameEndActivity.class);
		intent.putExtra("gameWon", gameWon);
		intent.putExtra("moves", MoveHelper.movesToStringFormat(game.getMoves()));
		startActivityForResult(intent, RESULT_ID);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_ID) {
			if (resultCode == RESULT_OK) {
				finish();
			}
		}
	}
}
