package nl.mprog.apps.othello.network;

import nl.mprog.apps.othello.network.Network.Move;
import android.os.AsyncTask;

import com.esotericsoftware.kryonet.Client;

public class MoveAsyncTask extends AsyncTask<Integer, Void, Void>  {
	
	private Client client;
	private String networkgameId;
	private int colorId;

	public MoveAsyncTask(Client client, String networkgameId, int colorId){
		this.client = client;
		this.networkgameId = networkgameId;
		this.colorId = colorId;	
	}

    // send a move to the server
	protected Void doInBackground(Integer... arg0) {
		Move move = new Move();
		move.x = arg0[0];
		move.y = arg0[1];
		move.gameId = networkgameId;
		move.colorId = colorId;
		
		client.sendTCP(move);
		
		return null;
	}
}
