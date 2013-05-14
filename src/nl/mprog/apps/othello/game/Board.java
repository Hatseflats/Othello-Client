package nl.mprog.apps.othello.game;

import java.util.Arrays;
import java.util.LinkedList;
import static nl.mprog.apps.othello.game.State.*;

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
	
	public void setBoardCell(int x, int y, State state){
		cells[y][x].setState(state);
	}
	
	public LinkedList<Cell> getAvailableMoves(int player){
		
		LinkedList<Cell> availableMoves = new LinkedList<Cell>();
		LinkedList<Cell> playerCells = getPlayerCells(State.getEnumByInt(player));
	
		for (Cell cell : playerCells) {
			availableMoves.addAll(checkNeighbours(cell.getX(), cell.getY(), player));
		}
		
		return availableMoves;
	}
	
	private LinkedList<Cell> checkNeighbours(int x, int y, int player){
				
		LinkedList<Cell> availableMoves = new LinkedList<Cell>();
		
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
	
	private Cell checkLine(int x, int y, int colMod, int rowMod, int currentPlayer){
		
		Cell availableMove = null;
		
		int currentX = x + colMod;
		int currentY = y + rowMod;
		
		while(0 <= currentY && currentY < GRID_SIZE && 0 <= currentX && currentX < GRID_SIZE){
			
			if((currentPlayer == PLAYER_WHITE && cells[currentY][currentX].getState() == WHITE) || (currentPlayer == PLAYER_BLACK && cells[currentY][currentX].getState() == BLACK)){
				break;
			}
			
			if(cells[currentY][currentX].getState() == EMPTY){

				availableMove = cells[currentY][currentX];
				break;
			}
			
			currentX += colMod;
			currentY += rowMod;
		}
		
		return availableMove;
	}
	
	public LinkedList<Cell> flipEnemyPieces(int x, int y, int player){
		
		LinkedList<Cell> piecesToFlip = new LinkedList<Cell>();

        for ( int rowMod = -1; rowMod <= 1; rowMod++ ) {
            for ( int colMod = -1; colMod <= 1; colMod++ ) {
            	
            	if(0 <= y+rowMod && y+rowMod < GRID_SIZE && 0 <= x+colMod && x+colMod < GRID_SIZE && ( rowMod != 0 || colMod != 0 )){
            		
            		int currentX = x + colMod;
            		int currentY = y + rowMod;
            		
            		LinkedList<Cell> pieces = new LinkedList<Cell>();
            		            		
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
	
	public boolean isValidMove(int x, int y, int currentPlayer){
		for (Cell cell : getAvailableMoves(currentPlayer)) {
			if(cell.getX() == x && cell.getY() == y) return true;
		}
		
		return false;
	}
	
	public LinkedList<Cell> getPlayerCells(State state){
		LinkedList<Cell> playerCells = new LinkedList<Cell>();
		
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
