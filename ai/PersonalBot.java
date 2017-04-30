/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */

import aiproj.slider.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonalBot implements SliderPlayer{

	// Legal Horizontal piece moves
	public final static PersonalMoves[] H_MOVES = { PersonalMoves.UP, PersonalMoves.RIGHT, PersonalMoves.DOWN };
	// Legal Vertical piece moves
	public final static PersonalMoves[] V_MOVES = { PersonalMoves.UP, PersonalMoves.RIGHT, PersonalMoves.LEFT };


	@Override
	public void init(int dimension, String board, char player) {
		PersonalBoard b;
		String piece;
		
		
		b = new PersonalBoard(board, dimension);
		countLegal(b);
		
	}

	@Override
	public void update(Move move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Move move() {
		// TODO Auto-generated method stub
		return null;
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
