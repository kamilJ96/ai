
public class Bot {

	public final static String[] H_MOVES = { "UP", "RIGHT", "DOWN" };
	public final static String[] V_MOVES = { "UP", "RIGHT", "LEFT" };

	public static void main(String[] args) {
		Board board = new Board(args);
		countLegal(board);
	}

	private static void countLegal(Board b) {
		int hCount = 0;
		int vCount = 0;
		Police check;

		for (Integer[] hPiece : b.getPieces("H")) {
			check = new Police(hPiece[0], hPiece[1], b);
			for (String move : H_MOVES) {
				if (check.checkHMove(move))
					hCount++;
			}
		}
				
		for (Integer[] vPiece : b.getPieces("V")) {
			check = new Police(vPiece[0], vPiece[1], b);
			for (String move : V_MOVES) {
				if (check.checkVMove(move))
					vCount++;
			}
		}
		
		System.out.println(hCount);
		System.out.println(vCount);
	}
}