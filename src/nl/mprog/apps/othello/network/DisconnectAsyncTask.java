package nl.mprog.apps.othello.network;

import android.os.AsyncTask;
import com.esotericsoftware.kryonet.Client;

public class DisconnectAsyncTask extends AsyncTask<Void, Void, Void> {
	
	private final Client client;

	public DisconnectAsyncTask(Client client) {
		this.client = client;
	}

    // Stops the client, disconnecting it from the server
	protected Void doInBackground(Void... params) {
		
		client.stop();
		
		return null;
	}
	
	public void onPostExecute(){
		
	}

}
