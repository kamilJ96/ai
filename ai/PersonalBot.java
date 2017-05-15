package bleh;

/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */

import aiproj.slider.*;
import java.util.ArrayList;

public class PersonalBot implements SliderPlayer{
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
		PersonalMoves m = PersonalMoves.toPersonalMoves(move);
		Integer[] pos = {move.i, move.j};
		
		// Only update board if opponent didn't pass
		if(move.d != null)
			b.updateBoard(pos, opponent, m);
	}

	//the strategy
	@Override
	public Move move() {
		ScoredMove bestMove = miniMax(4, this.player);
		
		return bestMove.getMove();
	}
	
	
	private ScoredMove miniMax(int depth, char piece){
	
		// to adapt
		//	https://www3.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html#zz-1.5
		
		ArrayList<PersonalBoard> children = new ArrayList<PersonalBoard>();
		int max = Integer.MAX_VALUE;
		int min = Integer.MIN_VALUE;
		
		int bestScore;
		int currScore;
		ScoredMove bestMove = new ScoredMove(0, null);
		
		// If it's our turn we want to maximize our score
		if (piece == player)
			bestScore = min;
		else
			bestScore = max;
		
		//create children
		children = b.createChildren(player, b);
		
		// Check if we've reached our depth, or a terminal node
		// Else recurse to the next level
		if (depth == 0 || children.isEmpty())
			bestScore = evalBoard(b, piece);
		else {
			for (PersonalBoard child : children) {
				if (piece == player) {
					currScore = miniMax(depth - 1, opponent).getScore();
					if (currScore > bestScore) {
						bestScore = currScore;
						bestMove.setMove(child.getMove());
					}	
				}
				else {
					currScore = miniMax(depth - 1, player).getScore();
					if (currScore < bestScore) {
						bestScore = currScore;
						bestMove.setMove(child.getMove());
					}
				}
			}
		}
		
		bestMove.setScore(bestScore);
		return bestMove;
	}
	
	private int evalBoard(PersonalBoard b, char piece) {
		int score = 0;
		int numPieces = b.getSize() - 1;
		
		// Add to our score for every piece we've removed from the board
		score += (numPieces - b.getPieces(piece).size()) * WEIGHT_PCES_RMVD;
		
		// Add to our score for every piece that's closer to the goal state
		// The closer they are to their respective end, the more score it gets
		// Possibly just use the row or column index of the piece as its score
		
		return score;
	}
		
	
}
