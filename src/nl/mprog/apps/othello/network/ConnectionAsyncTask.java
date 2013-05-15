package nl.mprog.apps.othello.network;

import java.io.IOException;

import nl.mprog.apps.othello.StartNetworkGameActivity;
import nl.mprog.apps.othello.network.Network.Connect;
import nl.mprog.apps.othello.network.Network.GameData;
import nl.mprog.apps.othello.network.Network.Move;
import android.app.Activity;
import android.os.AsyncTask;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ConnectionAsyncTask extends AsyncTask<String, Void, Client> {
	
	private static final String IP = "84.107.84.132";
	private static final int PORT = 54555;
	
	private Client client;
	private StartNetworkGameActivity activity;
	private String gameId;
	private int playerColor;
	private boolean finished;
	
	public ConnectionAsyncTask(Activity activity){
		this.activity = ( StartNetworkGameActivity ) activity;
		this.finished = false; // ... android
	}
	
	protected Client doInBackground(final String... arg0) {
		client = new Client();
		client.start();
		
		Network.register(client);
				
		client.addListener(new Listener() {
		    public void connected (Connection connection) {
		        Connect player = new Connect();
		        player.id = arg0[0];
		        client.sendTCP(player);
		        
		        return;
		    }
		    public void received (Connection connection, Object object) {
		        if (object instanceof GameData){
		        	
		        	GameData data = (GameData) object;
		        	playerColor = data.playerColor;
		        	gameId = data.gameId;
		        	
		        	System.out.println(playerColor);
		        	System.out.println(gameId);
		        	
		        	finished = true;
		        			        	
		        	return;
		        }
		    }
		});
		
		try {
		    client.connect(5000, IP, PORT);
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
		
		while(!finished){
			  try {
			        Thread.sleep(1000); // lol android         
			    } catch (InterruptedException e) {
			       e.printStackTrace();
			    }
		}
		
		return client;
	}
	
	public void onPostExecute(Client result){
		
		activity.setPlayer_color(playerColor);
		activity.setNetworkgame_id(gameId);
		activity.initGame(result);
		
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
		    }
		});
		
	}

}