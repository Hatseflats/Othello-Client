package nl.mprog.apps.othello.network;

import nl.mprog.apps.othello.network.Network.Move;
import android.os.AsyncTask;

import com.esotericsoftware.kryonet.Client;

public class MoveAsyncTask extends AsyncTask<Integer, Void, String>  {
	
	private Client client;
	private String networkgameId;
	private int colorId;

	public MoveAsyncTask(Client client, String networkgameId, int colorId){
		this.client = client;
		this.networkgameId = networkgameId;
		this.colorId = colorId;	
	}
	
	protected String doInBackground(Integer... arg0) {
		Move move = new Move();
		move.x = arg0[0];
		move.y = arg0[1];
		move.gameId = networkgameId;
		move.colorId = colorId;
		
		client.sendTCP(move);
		
		return "";
	}
	
	public void onPostExecute(){
		
	}

}
