package nl.apps.mprog.client.network;

import java.io.IOException;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import android.os.AsyncTask;

public class NetworkConnection extends AsyncTask {

	@Override
	protected Object doInBackground(Object... arg0) {
		Client client = new Client();
		client.start();
		
		Kryo kryo = client.getKryo();
		kryo.register(Request.class);
		kryo.register(Response.class);
		
		try {
			client.connect(5000, "84.107.84.132", 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Request request = new Request();
		request.text = "Here is the request!";
		client.sendTCP(request);
		
		client.addListener(new Listener() {
			   public void received (Connection connection, Object object) {
			      if (object instanceof Response) {
			         Response response = (Response)object;
			         System.out.println(response.text);
			      }
			   }
		});
		
		return null;
	}

}
