package nl.mprog.apps.othello.helpers;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.apps.othello.game.Move;

/**
 * Helper voor het converteren van een move array naar string en andersom.
 * 
 * @author Marten
 * @author Sebastiaan
 *
 */
public class MoveHelper {
	
	public static String movesToStringFormat(List<Move> moves) {
		String res = "";
		for (Move move : moves) {
			res += move.getX() +","+ move.getY() +";";
		}
		return res;
	}
	
	public static List<Move> stringToMoveList(String moves) {
		List<Move> list = new ArrayList<Move>();
		
		String[] split = moves.split(";");
		
		for (String s : split) {
			if (s.equals("")) continue;
			String[] moveSplit = s.split(",");
			Move move = new Move(Integer.parseInt(moveSplit[0]), Integer.parseInt(moveSplit[1]));
			list.add(move);
		}
		
		return list;
	}

}
