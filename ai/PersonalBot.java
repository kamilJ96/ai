package bleh;

/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */

import aiproj.slider.*;
import java.util.ArrayList;

public class PersonalBot implements SliderPlayer {
	private char player;
	private char opponent;

	private PersonalBoard b;

	private final int WEIGHT_PCES_RMVD = 10;
	private final int WEIGHT_PCES_END = 2;
	private final int MINIMAX_DEPTH = 4;

	@Override
	public void init(int dimension, String board, char player) {
		this.player = player;
		this.b = new PersonalBoard(board, dimension);

		if (player == 'H') {
			this.opponent = 'V';
		} else {
			this.opponent = 'H';
		}
	}

	// update move w.r.t other player
	@Override
	public void update(Move move) {
		if (move == null)
			return;

		PersonalMoves m = PersonalMoves.toPersonalMoves(move);
		Integer[] pos = { move.i, move.j };

		// Only update board if opponent didn't pass
		b.updateBoard(pos, opponent, m);
	}

	// the strategy
	@Override
	public Move move() {
		ScoredMove bestMove = miniMax(MINIMAX_DEPTH, this.player, 100 * b.getSize() * b.getSize(),
				-100 * b.getSize() * b.getSize());
		Integer[] move = { bestMove.getMove().i, bestMove.getMove().j };
		b.updateBoard(move, player, PersonalMoves.toPersonalMoves(bestMove.getMove()));

		return bestMove.getMove();
	}

	private ScoredMove miniMax(int depth, char piece, int alpha, int beta){
	
		// to adapt
		//	https://www3.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html#zz-1.5
		
		ArrayList<PersonalBoard> children = new ArrayList<PersonalBoard>();
		ArrayList<Integer[]> pieces = new ArrayList<Integer[]>();
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
		pieces = b.getPieces(piece);
		//children = b.createChildren(player, b);
		
		// Check if we've reached our depth, or a terminal node
		// Else recurse to the next level
		if (depth == 0)
			bestScore = evalBoard(b, piece);
		else {
			for (Integer[] p : pieces) {
				children = b.genMoves(p, piece, b);
				for (PersonalBoard child : children) {
					if (piece == player) {
						currScore = miniMax(depth - 1, opponent, alpha, beta).getScore();
	//					if (currScore > bestScore) {
	//					bestScore = currScore;
						if(currScore > alpha) {
							alpha = currScore;
							bestMove.setMove(child.getMove());
						}	
					}
					else {
						currScore = miniMax(depth - 1, player, alpha, beta).getScore();
	//					if (currScore < bestScore) {
	//						bestScore = currScore;
						if(currScore < beta) {
							beta = currScore;
							bestMove.setMove(child.getMove());
						}
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
