/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */

import aiproj.slider.*;
import java.util.ArrayList;

public class PersonalBot implements SliderPlayer{


	private char player;
	private char opponent;
	private PersonalBoard b;
	private ArrayList<Integer[]> myPieces;
	private ArrayList<Integer[]> opponentPieces;

	@Override
	public void init(int dimension, String board, char player) {
		this.player = player;
		this.b = new PersonalBoard(board, dimension);
		
		if(player == 'H'){
			this.opponent = 'V';
		}
		else{
			this.opponent = 'H';
		}
		
		this.myPieces = b.getPieces(player);
		this.opponentPieces = b.getPieces(opponent);
		countLegal(b);
		
	}

	//update move w.r.t other player
	@Override
	public void update(Move move) {
		PersonalMoves m = PersonalMoves.toPersonalMoves(move);
		for(Integer[] p : opponentPieces){
			if(p[0] == move.i && p[1] == move.j){
				//might have some indexing problems
				b.setCell(move.i, move.j, '+');
				p[0] += m.getX();
				p[1] += m.getY();
				b.setCell(p[0], p[1], opponent);
				break;
			}
		}
		
	}

	//the strategy
	@Override
	public Move move() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean canMove(PersonalBoard b){
		Police check;
		
		if(player == 'H'){
			// For each piece, iterate through its possible moves and check if each is legal 
			for (Integer[] hPiece : myPieces) {
				for (PersonalMoves move : PersonalMoves.H_MOVES) {
					check = new Police(hPiece[0], hPiece[1], b);
					if (check.hCheck(move))
						return true;
				}
			}
		}
		else{
			// For each piece, iterate through its possible moves and check if each is legal 
			for (Integer[] hPiece : myPieces) {
				for (PersonalMoves move : PersonalMoves.V_MOVES) {
					check = new Police(hPiece[0], hPiece[1], b);
					if (check.hCheck(move))
						return true;
				}
			}
		}
		
		return false;
	}
	
	private void countLegal(PersonalBoard b) {
		int hCount = 0;
		int vCount = 0;
		Police check;

		// For each piece, iterate through its possible moves and check if each is legal 
		for (Integer[] hPiece : myPieces) {
			for (PersonalMoves move : PersonalMoves.H_MOVES) {
				check = new Police(hPiece[0], hPiece[1], b);
				if (check.hCheck(move))
					hCount++;
			}
		}

		for (Integer[] vPiece : opponentPieces) {
			for (PersonalMoves move : PersonalMoves.V_MOVES) {
				check = new Police(vPiece[0], vPiece[1], b);
				if (check.vCheck(move))
					vCount++;
			}
		}

		System.out.println(hCount);
		System.out.println(vCount);
	}
}
