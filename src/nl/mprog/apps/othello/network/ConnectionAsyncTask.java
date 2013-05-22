package nl.mprog.apps.othello.network;

import java.io.IOException;

import nl.mprog.apps.othello.StartNetworkGameActivity;
import nl.mprog.apps.othello.network.Network.Connect;
import nl.mprog.apps.othello.network.Network.GameData;
import nl.mprog.apps.othello.network.Network.Move;
import nl.mprog.apps.othello.network.Network.Error;
import android.app.Activity;
import android.os.AsyncTask;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ConnectionAsyncTask extends AsyncTask<String, Void, Client> {
	
	private Client client;
	private StartNetworkGameActivity activity;
	private String gameId;
	private int playerColor;
	private boolean finished;
	
	public ConnectionAsyncTask(Activity activity){
		this.activity = ( StartNetworkGameActivity ) activity;
		this.finished = false;
	}

    // Connect to the server
	protected Client doInBackground(final String... arg0) {
		client = new Client();
		client.start();
		
		Network.register(client);
				
		client.addListener(new Listener() {
		    public void connected (Connection connection) { // send the player id to the server
		        Connect player = new Connect();
		        player.id = arg0[0];
		        client.sendTCP(player);
		        
		        return;
		    }
		    public void received (Connection connection, Object object) { // found a game, save the player color and game id and finish the task
		        if (object instanceof GameData){
		        	
		        	GameData data = (GameData) object;
		        	playerColor = data.playerColor;
		        	gameId = data.gameId;
		        
		        	finished = true;
		        			        	
		        	return;
		        }
		    }
		});
		
		try { // attempt to connect to the server
		    client.connect(5000, Network.IP, Network.PORT);
		  } catch (IOException e) {
		    e.printStackTrace();
		  }

        // wait untill a game has been made
		while(!finished){
			  try {
			        Thread.sleep(1000);  
			    } catch (InterruptedException e) {
			       e.printStackTrace();
			    }
		}
		
		return client;
	}
	
	public void onPostExecute(Client result){
		
		activity.setPlayerColor(playerColor);
		activity.setNetworkgame_id(gameId);
		activity.initGame(result);
		
		// connection to server succeeded, add listeners for game and disconnects
				
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				
		        if (object instanceof Move){
		        	
		        	final Move move = (Move) object;
		        	
		        	activity.runOnUiThread(new Runnable() {
		        	    public void run() {
				        	activity.makeMove(move.x, move.y);
		        	    }
		        	});
		        		
		        }
		        if(object instanceof Error){
		        	final Error error = (Error) object;
		        	
		        	activity.runOnUiThread(new Runnable() {
		        	    public void run() {
				        	activity.onEnemyDisconnect(error.message);
		        	    }
		        	});
		        }
		    }
		});
		
	}

}