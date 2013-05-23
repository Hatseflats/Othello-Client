package nl.mprog.apps.othello;

import java.util.List;

import nl.mprog.apps.othello.game.Board;
import nl.mprog.apps.othello.game.Cell;
import nl.mprog.apps.othello.game.Game;
import nl.mprog.apps.othello.game.Move;
import nl.mprog.apps.othello.helpers.MoveHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * De activity voor het afspelen van een replay.
 *
 * @author Marten
 * @author Sebastiaan
 */
public class PlayReplayActivity extends GameActivity {

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

        Button whitePieceCount = (Button) findViewById(R.id.whitecount);
        Button blackPieceCount = (Button) findViewById(R.id.blackcount);

        whitePieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_WHITE)));
        blackPieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_BLACK)));
    }

    @Override
    public void makeMove(int x, int y) {
        if (game.makeMove(x, y)) {
            refreshBoard(game.getBoard());
        }
    }
}
