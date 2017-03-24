
public class Bot {

	// Legal Horizontal piece moves
	public final static Moves[] H_MOVES = { Moves.UP, Moves.RIGHT, Moves.DOWN };
	// Legal Vertical piece moves
	public final static Moves[] V_MOVES = { Moves.UP, Moves.RIGHT, Moves.LEFT };

	public static void main(String[] args) {
		Board board = new Board(args);
		countLegal(board);
	}

	private static void countLegal(Board b) {
		int hCount = 0;
		int vCount = 0;
		Police check;

		// For each piece, iterate through its possible moves and check if each is legal 
		for (Integer[] hPiece : b.getPieces("H")) {
			for (Moves move : H_MOVES) {
				check = new Police(hPiece[0], hPiece[1], b, move);
				if (check.hCheck(move))
					hCount++;
			}
		}

		for (Integer[] vPiece : b.getPieces("V")) {
			for (Moves move : V_MOVES) {
				check = new Police(vPiece[0], vPiece[1], b, move);
				if (check.vCheck(move))
					vCount++;
			}
		}

		System.out.println(hCount);
		System.out.println(vCount);
	}
}