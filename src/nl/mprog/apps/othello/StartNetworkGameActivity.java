package nl.mprog.apps.othello;

import nl.mprog.apps.othello.game.Game;
import nl.mprog.apps.othello.network.ConnectionAsyncTask;
import nl.mprog.apps.othello.network.DisconnectAsyncTask;
import nl.mprog.apps.othello.network.MoveAsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.esotericsoftware.kryonet.Client;

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

	public void sendMove(int x, int y) {

        if(!game.getBoard().isValidMove(x, y, game.getCurrentPlayer())) return;

		makeMove(x, y);
        MoveAsyncTask moveTask = new MoveAsyncTask(client, networkgameId, playerColor);
        moveTask.execute(x, y);

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
		}
	}
}
