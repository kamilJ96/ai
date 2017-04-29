/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */
import java.util.ArrayList;
import java.util.Scanner;

public class PersonalBot {

	// Legal Horizontal piece moves
	public final static PersonalMoves[] H_MOVES = { PersonalMoves.UP, PersonalMoves.RIGHT, PersonalMoves.DOWN };
	// Legal Vertical piece moves
	public final static PersonalMoves[] V_MOVES = { PersonalMoves.UP, PersonalMoves.RIGHT, PersonalMoves.LEFT };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<String> conf = new ArrayList<String>();
		String[] confArray;
		PersonalBoard board;
		int size;
		String piece;
		
		size = sc.nextInt();
		
		while(sc.hasNext()){
			piece = sc.next();
			conf.add(piece);
		}

		sc.close();
		
//		size = Integer.parseInt(conf.get(0));
//		conf.remove(0);
//		confArray = new String[conf.size()];
//		conf.toArray(confArray);
		board = new PersonalBoard(conf, size);
		countLegal(board);
	}

	private static void countLegal(PersonalBoard b) {
		int hCount = 0;
		int vCount = 0;
		Police check;

		// For each piece, iterate through its possible moves and check if each is legal 
		for (Integer[] hPiece : b.getPieces("H")) {
			for (PersonalMoves move : H_MOVES) {
				check = new Police(hPiece[0], hPiece[1], b, move);
				if (check.hCheck(move))
					hCount++;
			}
		}

		for (Integer[] vPiece : b.getPieces("V")) {
			for (PersonalMoves move : V_MOVES) {
				check = new Police(vPiece[0], vPiece[1], b, move);
				if (check.vCheck(move))
					vCount++;
			}
		}

		System.out.println(hCount);
		System.out.println(vCount);
	}
}
