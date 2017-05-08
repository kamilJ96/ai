/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */

import aiproj.slider.*;
import java.util.ArrayList;

public class PersonalBot implements SliderPlayer{


	private char player;
	private char opponent;
	private PersonalBoard b;
	private ArrayList<Integer[]> myPieces, opponentPieces;
	private PersonalMoves[] myMoves, opponentMoves ;
	private int passed;

	@Override
	public void init(int dimension, String board, char player) {
		this.player = player;
		this.b = new PersonalBoard(board, dimension);
		this.passed = 0;
		
		if(player == 'H'){
			this.opponent = 'V';
			this.myMoves = PersonalMoves.H_MOVES;
			this.opponentMoves = PersonalMoves.V_MOVES;
		}
		else{
			this.opponent = 'H';
			this.myMoves = PersonalMoves.H_MOVES;
			this.opponentMoves = PersonalMoves.V_MOVES;
		}
		
		this.myPieces = b.getPieces(player);
		this.opponentPieces = b.getPieces(opponent);
		countLegal(b);
		
	}

	//update move w.r.t other player
	@Override
	public void update(Move move) {
		PersonalMoves m = PersonalMoves.toPersonalMoves(move);
		Integer[] pos = {move.i, move.j};
		//check if opponent passed
		if(move.d == null){
			passed++;
			return;
		}
		//possible that we passed before, so set back to 0
		passed = 0;
		
		//update board
		b = updateBoard(pos, opponentPieces, opponent, m, b);
		
	}

	//the strategy
	@Override
	public Move move() {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	
	private int[] miniMax(){
	
		// to adapt
		//	https://www3.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html#zz-1.5
		
		ArrayList<PersonalBoard> children = new ArrayList<PersonalBoard>();
		
		
		//create children
		for(PersonalMoves m : myMoves){
			
		}
		return null;
	}
	

	
	private PersonalBoard genNextBoard(Integer[] pos, PersonalMoves m, PersonalBoard b){
	
		Police check = new Police(pos[0], pos[1], b);
		//check if opponent passed
		if(!(player == 'H' && check.hCheck(m)) ||
				!(player == 'V' && check.vCheck(m))){
			passed++;
			return null;
		}
		//possible that we passed before, so set back to 0
		passed = 0;
		
		//update board
		b = updateBoard(pos, myPieces, player, m, b);

		
		return b;
	}
	
	//simple utility function
	//want own player to be max and opponent to be min
	//if opponent has more moves to win, then own player is max
	private int utilFn(){
		int hToEnd = 0;
		int vToEnd = 0;
		int val = 0;
		
		if(player == 'H'){
			for(Integer[] pos : myPieces){
				hToEnd += pos[0];
			}
			for(Integer[] pos : opponentPieces){
				vToEnd += pos[1];
			}
			val = vToEnd - hToEnd;
		}
		else{
			for(Integer[] pos : opponentPieces){
				hToEnd += pos[0];
			}
			for(Integer[] pos : myPieces){
				vToEnd += pos[1];
			}
			val = hToEnd - vToEnd;
		}
		
		
		return val;
	}
	
	

	//to update board after own move
	private void nextState(Move move, PersonalMoves m){
		
		
	}
	
	
	private PersonalBoard updateBoard(Integer[] pos, ArrayList<Integer[]> pieces, char player,
			PersonalMoves m, PersonalBoard b){
		for(Integer[] p : pieces){
			if(p[0] == pos[0] && p[1] == pos[1]){
				//might have some indexing problems
				b.setCell(pos[0], pos[1], '+');
				p[0] += m.getX();
				p[1] += m.getY();
				b.setCell(p[0], p[1], player);
				break;
			}
		}
		return b;
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
