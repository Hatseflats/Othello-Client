package nl.mprog.apps.othello;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;
import com.esotericsoftware.kryonet.Client;
import nl.mprog.apps.othello.game.Board;
import nl.mprog.apps.othello.game.Cell;
import nl.mprog.apps.othello.game.Game;
import nl.mprog.apps.othello.network.ConnectionAsyncTask;
import nl.mprog.apps.othello.network.DisconnectAsyncTask;
import nl.mprog.apps.othello.network.MoveAsyncTask;

import java.util.List;

/**
 * De activity voor het spelen van een network game tegen een andere speler.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class StartNetworkGameActivity extends GameActivity {

    private TableLayout grid;
	private Game game;
	
	private String networkgameId;
	private String playerId;
	private int playerColor;
	private Client client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_online);
		
		grid = (TableLayout) findViewById(R.id.table);
		
		game = new Game();
		game.initPositions();

        init(game, grid);

        playerId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

		new ConnectionAsyncTask(this).execute(playerId);		
	}
	
	@Override
	protected void onStop(){
		if(client != null)new DisconnectAsyncTask(client).execute();

		super.onStop();
	}
	
	public void onEnemyDisconnect(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		gameEnd();
	}
	
	public String getNetworkgameId() {
		return networkgameId;
	}

	public void setNetworkgame_id(String networkgame_id) {
		this.networkgameId = networkgame_id;
	}

	public int getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(int playerColor) {
		this.playerColor = playerColor;
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

    @Override
	public void makeMove(int x, int y){
		if (game.makeMove(x, y)) {
			refreshBoard(game.getBoard());
			
			if(game.getCurrentPlayer() == playerColor){
				toggleClickable(true);
			} else {
				toggleClickable(false);
			}
	
			if(game.checkVictoryConditions()){
                gameEnd();
			}

            MoveAsyncTask moveTask = new MoveAsyncTask(client, networkgameId, playerColor);
            moveTask.execute(x, y);

        }
	}
	
    @Override
	public void showHints(View view) {
		if (game.getCurrentPlayer() != playerColor) {
			return;
		}
		
		List<Cell> availableMoves = game.getBoard().getAvailableMoves(game.getCurrentPlayer());
		for (Cell cell : availableMoves) {
			showHint(cell.getX(), cell.getY());
		}
	}

    @Override
    protected void refreshBoard(Board board) {
        Cell[][] cells = board.getCells();

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                changeCellImage(cell.getX(), cell.getY(), cell.getState());
            }
        }

        Button whitePieceCount = (Button) findViewById(R.id.whitecount);
        Button blackPieceCount = (Button) findViewById(R.id.blackcount);
        Button turn = (Button) findViewById(R.id.makecomputermove);

        whitePieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_WHITE)));
        blackPieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_BLACK)));

        if(game.getCurrentPlayer() == playerColor){
            turn.setText(R.string.yourmove);
        } else {
            turn.setText(R.string.enemymove);
        }
    }
}
