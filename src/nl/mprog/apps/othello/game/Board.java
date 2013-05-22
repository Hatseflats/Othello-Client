package nl.mprog.apps.othello.game;

import static nl.mprog.apps.othello.game.State.BLACK;
import static nl.mprog.apps.othello.game.State.EMPTY;
import static nl.mprog.apps.othello.game.State.WHITE;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {
	
	private static final int GRID_SIZE = 8;
	
	private static final int PLAYER_WHITE = 1;
	private static final int PLAYER_BLACK = 2;
	
	private Cell[][] cells;
	
	public Board (){
		cells = new Cell[8][8];
		
		for (int y = 0; y < GRID_SIZE; y++) {
			for (int x = 0; x < GRID_SIZE; x++) {
				cells[y][x] = new Cell(x, y);
			}
		}		
	}
	
	public void setBoardCell(int x, int y, State state) {
		cells[y][x].setState(state);
	}

    /**
     * Gets all available moves for a certain player
     * @param player the player of which you want the available moves
     * @return a List object containing cells with all possible moves for a certain player
     */
	
	public List<Cell> getAvailableMoves(int player) {
		List<Cell> availableMoves = new ArrayList<Cell>();
		List<Cell> playerCells = getPlayerCells(State.getEnumByInt(player));
	
		for (Cell cell : playerCells) {
			availableMoves.addAll(checkNeighbours(cell.getX(), cell.getY(), player));
		}
		
		return availableMoves;
	}

    /**
     * Checks the neighbours of a cell, used to determine all possible moves
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param player the player who owns the cell
     * @return a list with all moves made available from a certain cell on the board
     */
	
	private List<Cell> checkNeighbours(int x, int y, int player){
		List<Cell> availableMoves = new ArrayList<Cell>();
		
        for ( int rowMod = -1; rowMod <= 1; rowMod++ ) {
            for ( int colMod = -1; colMod <= 1; colMod++ ) {
            	
            	if(0 <= y+rowMod && y+rowMod < GRID_SIZE && 0 <= x+colMod && x+colMod < GRID_SIZE && ( rowMod != 0 || colMod != 0 )){
            		if((cells[y+rowMod][x+colMod].getState() == BLACK && player == PLAYER_WHITE) || (cells[y+rowMod][x+colMod].getState() == WHITE && player == PLAYER_BLACK )){
            			Cell cell  = checkLine(x+colMod, y+rowMod, colMod, rowMod, player);
            			
            			if(cell != null){
            				availableMoves.add(cell);
            			}
            		}
            	}
            }
        }
        
        return availableMoves;
	}

    /**
     * Checks all cells in a line starting at a certain cell, used to determine all possible moves.
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param colMod the x axis modifier for the line to check
     * @param rowMod the y axis modifier for the line to check
     * @param currentPlayer the player who owns the cell
     * @return a Null object if no move is found, a cell if a valid move is found
     */

	private Cell checkLine(int x, int y, int colMod, int rowMod, int currentPlayer) {
		int currentX = x + colMod;
		int currentY = y + rowMod;
		
		while (0 <= currentY && currentY < GRID_SIZE && 0 <= currentX && currentX < GRID_SIZE) {
			if ((currentPlayer == PLAYER_WHITE && cells[currentY][currentX].getState() == WHITE) || (currentPlayer == PLAYER_BLACK && cells[currentY][currentX].getState() == BLACK)) {
				return null;
			}
			
			if(cells[currentY][currentX].getState() == EMPTY){
				return cells[currentY][currentX];
			}
			
			currentX += colMod;
			currentY += rowMod;
		}
		
		return null;
	}

    /**
     * Returns a list with the pieces a move will flip
     * @param x the x coordinate of the move
     * @param y the y coordinate of the move
     * @param player the player who makes the move
     * @return a list with all pieces that will change colors
     */
	
	public List<Cell> flipEnemyPieces(int x, int y, int player){
		List<Cell> piecesToFlip = new ArrayList<Cell>();

        for ( int rowMod = -1; rowMod <= 1; rowMod++ ) {
            for ( int colMod = -1; colMod <= 1; colMod++ ) {
            	
            	if(0 <= y+rowMod && y+rowMod < GRID_SIZE && 0 <= x+colMod && x+colMod < GRID_SIZE && ( rowMod != 0 || colMod != 0 )){
            		
            		int currentX = x + colMod;
            		int currentY = y + rowMod;
            		
            		List<Cell> pieces = new ArrayList<Cell>();
            		            		
            		while(0 <= currentY && currentY < GRID_SIZE && 0 <= currentX && currentX < GRID_SIZE && cells[currentY][currentX].getState() != EMPTY ){
            			
            			if(player == PLAYER_WHITE ){
            				if(cells[currentY][currentX].getState() == BLACK){
            					pieces.add(cells[currentY][currentX]);
            				} else {
            					piecesToFlip.addAll(pieces);
            					break;
            				}
            			} else if(player == PLAYER_BLACK ){
             				if(cells[currentY][currentX].getState() == WHITE){
            					pieces.add(cells[currentY][currentX]);
             				} else {
            					piecesToFlip.addAll(pieces);
            					break;
            				}
            			}
            			
            			currentX += colMod;
            			currentY += rowMod;	
            		}
            	}
            }
        }
        
        return piecesToFlip;
	}

    /**
     * checks if a move is valid
     * @param x the x coordinate of the move
     * @param y the y coordinate of the move
     * @param currentPlayer the player who makes the move
     * @return the boolean true if the move is valid, false if invalid
     */

	public boolean isValidMove(int x, int y, int currentPlayer){
		for (Cell cell : getAvailableMoves(currentPlayer)) {
			if(cell.getX() == x && cell.getY() == y) return true;
		}
		
		return false;
	}

    /**
     * Returns a list containing all cells that are in a certain state
     * @param state a State object for which to get the cells
     * @return a list object with all cells that are in a certain state
     */

	public List<Cell> getPlayerCells(State state){
		List<Cell> playerCells = new ArrayList<Cell>();
		
		for (int j = 0; j < GRID_SIZE; j++) {
			for (int i = 0; i < GRID_SIZE; i++) {
				if(cells[j][i].getState() == state){
					playerCells.add(cells[j][i]);
				}
			}
		}

		return playerCells;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
		
}
