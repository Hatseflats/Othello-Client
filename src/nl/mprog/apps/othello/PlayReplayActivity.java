package nl.mprog.apps.othello;

import java.util.List;

import nl.mprog.apps.othello.game.Board;
import nl.mprog.apps.othello.game.Cell;
import nl.mprog.apps.othello.game.Game;
import nl.mprog.apps.othello.game.Move;
import nl.mprog.apps.othello.game.State;
import nl.mprog.apps.othello.helpers.MoveHelper;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * De activity voor het afspelen van een replay.
 *
 * @author Marten
 * @author Sebastiaan
 */
public class PlayReplayActivity extends GameActivity {

    private static final int GRID_SIZE = 8;

    private TableLayout grid;

    private Game game;

    private List<Move> moves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_replay);
        // Show the Up button in the action bar.
//		setupActionBar();

        moves = MoveHelper.stringToMoveList(getIntent().getStringExtra("moves"));

        grid = (TableLayout) findViewById(R.id.table);
        game = new Game();
        game.initPositions();

        init(game, grid);

        createBoard();
        refreshBoard(game.getBoard());
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.play_replay, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void nextMove(View view) {
        Move move = moves.get(0);
        moves.remove(0);
        makeMove(move.getX(), move.getY());
        ((TextView) findViewById(R.id.currentturn)).setText("Last move was: (" + move.getX() + ", " + move.getY() + ")");

        if (moves.size() == 0) {
            Toast.makeText(this, "Game has ended!", 5).show();
            TextView textView = (TextView) findViewById(R.id.nextmove);
            textView.setText("GAME HAS ENDED!");
            textView.setEnabled(false);
        }
    }

    @Override
    public void refreshBoard(Board board) {
        Cell[][] cells = board.getCells();

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                changeCellImage(cell.getX(), cell.getY(), cell.getState());
            }
        }
    }

    @Override
    public void makeMove(int x, int y) {
        if (game.makeMove(x, y)) {
            refreshBoard(game.getBoard());
        }
    }
}
