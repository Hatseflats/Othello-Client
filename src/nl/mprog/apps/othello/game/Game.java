package nl.mprog.apps.othello.game;

import static nl.mprog.apps.othello.game.State.BLACK;
import static nl.mprog.apps.othello.game.State.WHITE;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private Board board;
	
	private int currentPlayer;
	
	private static final int PLAYER_WHITE = 1;
	private static final int PLAYER_BLACK = 2;
	private List<Move> moves;

	public Game(){
		board = new Board();
		moves = new ArrayList<Move>();
		
		currentPlayer = PLAYER_WHITE;
	}

    /**
     * Handles the logic of making a move
     * @param x the x coordinate of the move
     * @param y the y coordinate of the move
     * @return true if the move has been made, false if the move is invalid
     */
	
	public boolean makeMove(int x, int y){
		if(!board.isValidMove(x, y, currentPlayer)) return false;
		
		moves.add(new Move(x, y));
		State state = State.getEnumByInt(currentPlayer);
		
		List<Cell> piecesToFlip = board.flipEnemyPieces(x, y, currentPlayer);
		
		for (Cell cell : piecesToFlip) {
			board.setBoardCell(cell.getX(), cell.getY(), state);
		}
		
		board.setBoardCell(x, y, state);

        // if the opponent cant make any moves the player can make another move
		if(currentPlayer == PLAYER_WHITE){
			if(board.getAvailableMoves(PLAYER_BLACK).size() != 0){
				currentPlayer = PLAYER_BLACK;
			}
		} else {
			if(board.getAvailableMoves(PLAYER_WHITE).size() != 0){
				currentPlayer = PLAYER_WHITE;
			}
		}
				
		return true;
	}


    /**
     * If no one can make a move, the game is over.
     * @return true if the game has ended, false if the game still is in progress
     */
	public boolean checkVictoryConditions(){

		if(board.getAvailableMoves(PLAYER_WHITE).size() + board.getAvailableMoves(PLAYER_BLACK).size() == 0){
			return true;
		}
		
		return false;
	}

    /**
     * returns the amount of pieces a certain player has.
     * @param player the player for which to return the amount of pieces
     * @return the amount of pieces owned by player
     */
	
	public int getPieceCount(int player){
		if(player == PLAYER_WHITE){
			return board.getPlayerCells(WHITE).size();
		} else if(player == PLAYER_BLACK) {
			return board.getPlayerCells(BLACK).size();
		} else {
			return 0;
		}
	}

    /**
     * Initializes a game by setting the beginning pieces
     */
	
	public void initPositions(){
		board.setBoardCell(3, 3, WHITE);
		board.setBoardCell(4, 4, WHITE);
		board.setBoardCell(3, 4, BLACK);
		board.setBoardCell(4, 3, BLACK);
	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public List<Move> getMoves() {
		return moves;
	}
}
