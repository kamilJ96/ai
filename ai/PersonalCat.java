package meow.bleh.hopefullynoonehasthisname;

/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */

import aiproj.slider.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class PersonalCat implements SliderPlayer {
	private char player;
	private char opponent;

	private PersonalBoard b;

	private final int WEIGHT_PCES_RMVD = 10;
	private final int MINIMAX_DEPTH = 7;

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
		ScoredMove bestMove = miniMax(MINIMAX_DEPTH, this.player, Integer.MIN_VALUE, Integer.MAX_VALUE);
		// Update the board if our move isn't a pass
		if (bestMove.getMove() != null) {
			Integer[] move = { bestMove.getMove().i, bestMove.getMove().j };
			b.updateBoard(move, player, PersonalMoves.toPersonalMoves(bestMove.getMove()));
		}
		return bestMove.getMove();
	}

	/** Does minimax with pruning based on the board */
	private ScoredMove miniMax(int depth, char piece, int alpha, int beta) {
		ArrayList<PersonalMoves> children = new ArrayList<PersonalMoves>();

		// Get a new list of all the players pieces
		ArrayList<Integer[]> pieces = copyPieces(b.getPieces(piece));

		int bestScore;
		int currScore;

		// We want to get a high score, opponent a low score
		if (piece == player)
			bestScore = beta;
		else
			bestScore = alpha;

		ScoredMove bestMove = new ScoredMove(0, null);

		// Check if we've reached our depth, assume children will always be
		// created
		if (depth == 0) {
			bestScore = evalBoard();
			bestMove.setScore(bestScore);
			return bestMove;
		} else {
			for (Integer[] p : pieces) {
				// Go through each piece and generate a list of possible moves
				children = b.genMoves(p, piece, b);
				for (PersonalMoves child : children) {
					// For each move, update the board and check the score of
					// the move
					b.updateBoard(p, piece, child);

					if (piece == player) {
						currScore = miniMax(depth - 1, opponent, alpha, beta).getScore();
						if (currScore > alpha) {
							alpha = currScore;
							bestMove.setMove(child.toMove(p, child));
						}
					} else {
						currScore = miniMax(depth - 1, player, alpha, beta).getScore();
						if (currScore < beta) {
							beta = currScore;
							bestMove.setMove(child.toMove(p, child));
						}
					}

					// Revert the move to preserve board state
					b.rollback(p, piece, child);

					// Prune if condition true
					if (alpha >= beta)
						break;
				}
			}
		}

		return bestMove;
	}

	/**
	 * Evaluation function for minimax, increases score for optimal states for
	 * our player and decreases score for optimal states for opponent
	 * 
	 * @return
	 */
	private int evalBoard() {
		int score = 0;
		int numPieces = b.getSize() - 1;

		// Add to our score for every piece we've removed from the board
		// Negate the score for every opponent piece removed
		score += (numPieces - b.getPieces(player).size()) * WEIGHT_PCES_RMVD;
		score -= (numPieces - b.getPieces(opponent).size()) * WEIGHT_PCES_RMVD;

		// Add to our score for every piece that's closer to the goal state
		// The closer they are to their respective end, the more score it gets
		for (Integer[] pos : b.getPieces(player))
			score += score(pos, player) * 100;
		for (Integer[] pos : b.getPieces(opponent))
			score -= score(pos, opponent);

		// Check for desired blocks of other player and if each piece is
		// surrounded by free cells
		score += b.checkBlocked(player);
		score -= b.checkBlocked(opponent);

		return score;
	}

	/** Utility function that increases score of a piece by its distance to the goal state */
	private int score(Integer[] piece, char player) {
		if (player == 'V')
			return piece[1] * piece[1];
		else
			return piece[0] * piece[0];
	}

	/** Returns a new random list (deep copy) of a given players pieces. */
	private ArrayList<Integer[]> copyPieces(ArrayList<Integer[]> pieces) {
		ArrayList<Integer[]> copy = new ArrayList<Integer[]>();
		ArrayList<Integer> index = new ArrayList<Integer>();

		for (Integer i = 0; i < pieces.size(); i++)
			index.add(i);

		// Randomly add each piece to the new list
		for (int i = 0; i < pieces.size(); i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, index.size());
			Integer[] copyPiece = Arrays.copyOf(pieces.get(index.get(randomNum)), 2);
			
			copy.add(copyPiece);
			index.remove(randomNum);
		}

		return copy;
	}

}

