package bleh;

/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */

import aiproj.slider.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PersonalBot implements SliderPlayer {
	private char player;
	private char opponent;

	private PersonalBoard b;

	private final int WEIGHT_PCES_RMVD = 10;
	private final int WEIGHT_PCES_END = 2;
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
		if (bestMove.getMove() != null) {
			Integer[] move = { bestMove.getMove().i, bestMove.getMove().j };
			b.updateBoard(move, player, PersonalMoves.toPersonalMoves(bestMove.getMove()));
		}
		return bestMove.getMove();
	}

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

		// Check if we've reached our depth, or a terminal node, and get the
		// score for the board
		if (depth == 0 || pieces.size() == 0) {
			bestScore = evalBoard();
			bestMove.setScore(bestScore);
			return bestMove;
		} else {
			for (Integer[] p : pieces) {
				// Go through each piece and generate a list of possible moves
				children = b.genMoves(p, piece, b);
				for (PersonalMoves child : children) {
					// For each move, update the board and check the score of the move
//					System.out.println("Moving " + piece + " {" + p[0] + ", " + p[1] + "} " + child.name());
					b.updateBoard(p, piece, child);
//					b.printPieces();

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
//					System.out.println("Reverting " + piece + " {" + p[0] + ", " + p[1] + "} " + child.name());
					b.rollback(p, piece, child);
//					b.printPieces();
					if (alpha >= beta)
						break;
				}
			}
		}

		return bestMove;
	}

	private int evalBoard() {
		int score = 0;
		int numPieces = b.getSize() - 1;

		// Add to our score for every piece we've removed from the board
		score += (numPieces - b.getPieces(player).size()) * WEIGHT_PCES_RMVD;
		score -= (numPieces - b.getPieces(opponent).size()) * WEIGHT_PCES_RMVD;

		// Add to our score for every piece that's closer to the goal state
		// The closer they are to their respective end, the more score it gets
		// Possibly just use the row or column index of the piece as its score

		for (Integer[] pos : b.getPieces(player)) {
			score += score(pos, player);
		}

		for (Integer[] pos : b.getPieces(opponent)) {
			score -= score(pos, opponent);
		}

		return score;
	}

	private int score(Integer[] piece, char player) {
		// weigh value by distance traveled in direction of winning
		if (player == 'V') {
			return piece[1] * piece[1];
		} else {
			return piece[0] * piece[0];
		}
	}

	private ArrayList<Integer[]> copyPieces(ArrayList<Integer[]> pieces) {
		ArrayList<Integer[]> copy = new ArrayList<Integer[]>();

		for (Integer[] piece : pieces) {
			Integer[] copyPiece = Arrays.copyOf(piece, 2);
			copy.add(copyPiece);
		}

		return copy;
	}

}
