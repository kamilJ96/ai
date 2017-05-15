package bleh;

/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */

import aiproj.slider.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class RandomBot implements SliderPlayer{
	private char player;
	private char opponent;
	
	private PersonalBoard b;

	private final int WEIGHT_PCES_RMVD = 10;
	private final int WEIGHT_PCES_END = 2;
	
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
	}

	//update move w.r.t other player
	@Override
	public void update(Move move) {
		if(move == null)
			return;
		
		PersonalMoves m = PersonalMoves.toPersonalMoves(move);
		Integer[] pos = {move.i, move.j};
		
		// Only update board if opponent didn't pass
		b.updateBoard(pos, opponent, m);
	}

	//the strategy
	@Override
	public Move move() {
		ArrayList<Integer> mvs = new ArrayList<Integer>();
		mvs.add(0);
		mvs.add(1);
		mvs.add(2);
		return randomMove(mvs);
	}
	
	private Move randomMove(ArrayList<Integer> mvs) {
		ArrayList<Integer[]> myPieces = this.b.getPieces(player);
		int rndMove = ThreadLocalRandom.current().nextInt(0, 3);
		int rndPiece = ThreadLocalRandom.current().nextInt(0, myPieces.size());
		Police check = new Police(myPieces.get(rndPiece)[0], myPieces.get(rndPiece)[1], b);
		
		Move m = null;
		
		if(mvs.isEmpty()) {
			return m;
		}
		if(player == 'H' && check.hCheck(PersonalMoves.H_MOVES[rndMove])){
			b.updateBoard(myPieces.get(rndPiece), player, PersonalMoves.H_MOVES[rndMove]);
			m = PersonalMoves.H_MOVES[rndMove].toMove(myPieces.get(rndPiece), PersonalMoves.H_MOVES[rndMove]);
		}
		else if(player == 'V' && check.vCheck(PersonalMoves.V_MOVES[rndMove])){
			b.updateBoard(myPieces.get(rndPiece), player, PersonalMoves.V_MOVES[rndMove]);
			m = PersonalMoves.V_MOVES[rndMove].toMove(myPieces.get(rndPiece), PersonalMoves.V_MOVES[rndMove]);
		}
		else {
			mvs.remove(rndMove);
			randomMove(mvs);
		}
		
		return m;
	}
	
	
		
	
}
