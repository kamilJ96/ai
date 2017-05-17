package meow.bleh.hopefullynoonehasthisname;

/* Ai-Linh Tran taal
 * Kamil Jakrzewski kjakrzewski
 */

import aiproj.slider.Move;

public class ScoredMove {
	private int score;
	private Move move;
	
	public ScoredMove(int score, Move move) {
		this.score = score;
		this.move = move;
	}
	
	public void setScore(int score) { this.score = score; }
	public void setMove(Move move) { this.move = move; }
	
	public int getScore() { return this.score; }
	public Move getMove() { return this.move; }

}
