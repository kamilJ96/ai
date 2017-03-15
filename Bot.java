
public class Bot {

//	public final static String[] H_MOVES = { "UP", "RIGHT", "DOWN" };
//	public final static String[] V_MOVES = { "UP", "RIGHT", "LEFT" };
	
	public final static Moves[] H_MOVES = { Moves.UP, Moves.RIGHT, Moves.DOWN };
	public final static Moves[] V_MOVES = { Moves.UP, Moves.RIGHT, Moves.LEFT };

	public static void main(String[] args) {
		Board board = new Board(args);
		countLegal(board);
	}

	private static void countLegal(Board b) {
		int hCount = 0;
		int vCount = 0;
		Police check;

		for (Integer[] hPiece : b.getPieces("H")) {

//			for (String move : H_MOVES) {
			for (Moves move : H_MOVES) {
				check = new Police(hPiece[0], hPiece[1], b, move);
//				if (check.checkHMove(move))
				if (check.hCheck(move))
					hCount++;
			}
		}
				
		for (Integer[] vPiece : b.getPieces("V")) {

//			for (String move : V_MOVES) {
			for (Moves move : V_MOVES){
				check = new Police(vPiece[0], vPiece[1], b, move);
//				if (check.checkVMove(move))
				if (check.vCheck(move))
					vCount++;
			}
		}
		
	
		System.out.println(hCount);
		System.out.println(vCount);
	}
}