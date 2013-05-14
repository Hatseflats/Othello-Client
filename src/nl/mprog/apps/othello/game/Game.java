package nl.mprog.apps.othello.game;

import static nl.mprog.apps.othello.game.State.BLACK;
import static nl.mprog.apps.othello.game.State.WHITE;

import java.util.ArrayList;
import java.util.LinkedList;
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
	
	public boolean makeMove(int x, int y){
		
		if(!board.isValidMove(x, y, currentPlayer)) return false;
		
		moves.add(new Move(x, y));
		State state = State.getEnumByInt(currentPlayer);
		
		LinkedList<Cell> piecesToFlip = board.flipEnemyPieces(x, y, currentPlayer);
		
		for (Cell cell : piecesToFlip) {
			board.setBoardCell(cell.getX(), cell.getY(), state);
		}
		
		board.setBoardCell(x, y, state);
		
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
	
	public boolean checkVictoryConditions(){

		if(board.getAvailableMoves(PLAYER_WHITE).size() + board.getAvailableMoves(PLAYER_BLACK).size() == 0){
			return true;
		}
		
		return false;
	}
	
	public int getPieceCount(int player){
		if(player == PLAYER_WHITE){
			return board.getPlayerCells(WHITE).size();
		} else if(player == PLAYER_BLACK) {
			return board.getPlayerCells(BLACK).size();
		} else {
			return 0;
		}
	}
	
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
