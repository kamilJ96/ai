package bleh;

/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */

import aiproj.slider.*;
import java.util.ArrayList;

public class PersonalBot implements SliderPlayer{


	private char player;
	private char opponent;
	private PersonalBoard b;
	private ArrayList<Integer[]> myPieces, opponentPieces;
	private int passed;

	@Override
	public void init(int dimension, String board, char player) {
		this.player = player;
		this.b = new PersonalBoard(board, dimension);
		this.passed = 0;
		
		if(player == 'H'){
			this.opponent = 'V';
		}
		else{
			this.opponent = 'H';
		}
		
		this.myPieces = b.getPieces(player);
		this.opponentPieces = b.getPieces(opponent);
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
		b.updateBoard(pos, opponent, m);
	}

	//the strategy
	@Override
	public Move move() {
		// TODO Auto-generated method stub
		
		//call minimax for next move
		
		//updAate own board
		
		return null;
	}
	
	
	private int[] miniMax(int depth, char piece){
	
		// to adapt
		//	https://www3.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html#zz-1.5
		
		ArrayList<PersonalBoard> children = new ArrayList<PersonalBoard>();
		int max = b.getSize() * b.getSize();
		int min = -b.getSize() * b.getSize();
		
		int bestScore;
		int currScore;
		Integer[] bestMove = null;
		
		// If it's our turn we want to maximize our score
		if (piece == player)
			bestScore = max;
		else
			bestScore = min;
		
		//create children
		children = b.createChildren(player, b);
		
		// Check if we've reached our depth, or a terminal node
		// Else recurse to the next level
		if (depth == 0 || children.isEmpty())
			bestScore = utilFn();
		else {
			for (PersonalBoard child : children) {
				if (piece == player) {
					currScore = miniMax(depth - 1, opponent)[0];
					if (currScore > bestScore) {
						bestScore = currScore;
						bestMove = child.getMovedPiece();
					}
				}
				else {
					currScore = miniMax(depth - 1, player)[0];
					if (currScore < bestScore) {
						bestScore = currScore;
						bestMove = child.getMovedPiece();
					}
				}
			}
		}
		
		return new int[] {bestScore, bestMove[0], bestMove[1]};
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
		
	
}
