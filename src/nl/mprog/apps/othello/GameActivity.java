package nl.mprog.apps.othello;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import nl.mprog.apps.othello.clickhandlers.CellClickHandler;
import nl.mprog.apps.othello.game.Board;
import nl.mprog.apps.othello.game.Cell;
import nl.mprog.apps.othello.game.Game;
import nl.mprog.apps.othello.game.State;
import nl.mprog.apps.othello.helpers.MoveHelper;
import nl.mprog.apps.othello.persistence.ReplaysHandler;

/**
 * De super class voor alle activities waar een othello spel gespeeld wordt.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public abstract class GameActivity extends Activity {

    private static final int GRID_SIZE = 8;

    protected static final int PLAYER_WHITE = 1;
    protected static final int PLAYER_BLACK = 2;

    private TableLayout grid;
    private Game game;
    private GameEndDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void init(Game game, TableLayout grid){
        this.game = game;
        this.grid = grid;
    }

    protected void refreshBoard(Board board) {
        Cell[][] cells = board.getCells();

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                changeCellImage(cell.getX(), cell.getY(), cell.getState());
            }
        }

        TextView whitePieceCount = (TextView) findViewById(R.id.whitepieces);
        TextView blackPieceCount = (TextView) findViewById(R.id.blackpieces);
        TextView turn = (TextView) findViewById(R.id.turn);

        whitePieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_WHITE)));
        blackPieceCount.setText(String.valueOf(game.getPieceCount(PLAYER_BLACK)));

        if(game.getCurrentPlayer() == PLAYER_WHITE){
            turn.setText(R.string.turn_player_white);
        } else {
            turn.setText(R.string.turn_player_black);
        }
    }

    protected void toggleClickable(boolean clickable){

        for (int i = 0; i < GRID_SIZE; i++) {

            TableRow row = (TableRow) grid.getChildAt(i);

            for (int j = 0; j < GRID_SIZE; j++) {
                ImageView cell = (ImageView) row.getChildAt(j);

                cell.setClickable(clickable);

            }
        }
    }

    protected void changeCellImage(int x, int y, State state){
        TableRow row = (TableRow) grid.getChildAt(y);
        ImageView cell = (ImageView) row.getChildAt(x);

        switch (state) {
            case WHITE:
                cell.setImageResource(R.drawable.white_small);
                break;
            case BLACK:
                cell.setImageResource(R.drawable.black_small);
                break;
            case EMPTY:
                cell.setImageResource(0);
                break;
        }
    }

    protected void createBoard() {
        for (int j = 0; j < GRID_SIZE; j++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int i = 0; i < GRID_SIZE; i++) {
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
                lp.setMargins(1, 1, 1, 1);

                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(lp);
                imageView.setBackgroundColor(Color.parseColor("#046e00"));
                imageView.setMinimumWidth(45);
                imageView.setMaxWidth(60);
                imageView.setMinimumHeight(45);
                imageView.setMaxHeight(60);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                tr.addView(imageView);

                imageView.setOnClickListener(new CellClickHandler(this, i, j));

            }

            grid.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    public abstract void makeMove(int x, int y);

    public void saveReplay(View view) {
        String name = ((TextView) dialog.findViewById(R.id.replayname)).getText().toString();
        ReplaysHandler handler = new ReplaysHandler(this);
        handler.addReplay(name, MoveHelper.movesToStringFormat(game.getMoves()));

        ((Button) view).setEnabled(false);
        ((Button) view).setText("REPLAY SAVED!");

        Toast.makeText(this, "Replay saved!", 5).show();
    }

    public void backToMainscreen(View view) {
        finish();
    }

    protected void gameEnd() {
        Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
        int gameCode = -1;

        int whitePieces = game.getPieceCount(PLAYER_WHITE);
        int blackPieces = game.getPieceCount(PLAYER_BLACK);

        if(whitePieces > blackPieces ){
            gameCode = 1;
            Toast.makeText(this, "White wins!", Toast.LENGTH_SHORT).show();
        } else if(whitePieces == blackPieces){
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Black wins!", Toast.LENGTH_SHORT).show();
            gameCode = 2;
        }

        dialog = new GameEndDialog(this, gameCode);
        dialog.show();
    }

}