package nl.mprog.apps.othello;

import java.util.List;

import nl.mprog.apps.othello.game.Cell;
import nl.mprog.apps.othello.game.Computer;
import nl.mprog.apps.othello.game.Game;
import nl.mprog.apps.othello.helpers.MoveHelper;
import nl.mprog.apps.othello.persistence.ReplaysHandler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * De activity voor een lokale game tegen de computer (AI).
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class StartLocalGameActivity extends GameActivity {

	private static final int PLAYER_WHITE = 1;
	private static final int PLAYER_BLACK = 2;

	private TableLayout grid;

	private Game game;
	private Computer computer;
	private GameEndDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);
		
		grid = (TableLayout) findViewById(R.id.table);
		game = new Game();
		game.initPositions();

        init(game, grid);
		
		computer = new Computer(game, PLAYER_BLACK);
		
		createBoard();
		refreshBoard(game.getBoard());

	}

    /**
     * Makes a move
     * @param x the x coordinate of the move
     * @param y the y coordinate of the move
     */
    @Override
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

    /**
     * Get a move from the computer and make it
     * @param view
     */
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

    /**
     * Shows all available moves on the board
     * @param view
     */
	
	public void showHints(View view) {
		if (game.getCurrentPlayer() != PLAYER_WHITE) {
			return;
		}
		
		List<Cell> availableMoves = game.getBoard().getAvailableMoves(game.getCurrentPlayer());
		for (Cell cell : availableMoves) {
			showHint(cell.getX(), cell.getY());
		}
	}
	
	private void showHint(int x, int y) {
	    TableRow row = (TableRow) grid.getChildAt(y);
	    ImageView cell = (ImageView) row.getChildAt(x);
	    
	    cell.setImageResource(R.drawable.hint_white);
	}
}
