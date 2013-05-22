package nl.mprog.apps.othello;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * De popup die getoond wordt nadat het spel is afgelopen.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class GameEndDialog extends Dialog {
	
	private final int gameCode;
	private final Context context;

	public GameEndDialog(Context context, int gameCode) {
		super(context);
		this.context = context;
		this.gameCode = gameCode;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout=(RelativeLayout) LayoutInflater.from(context).inflate(R.layout.dialog_game_end, null);
		setContentView(layout);

		if (gameCode == 1) {
			setTitle(R.string.game_won);
		} else if(gameCode == 2) {
			setTitle(R.string.game_lost);
		} else {
			setTitle(R.string.game_draw);
		}
		
		TextView replayName = (TextView) findViewById(R.id.replayname);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		replayName.setText(dateFormat.format(date));
	}

}
