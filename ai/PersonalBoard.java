package meow.bleh.hopefullynoonehasthisname;

/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class PersonalBoard {

	private char[][] board;
	private int size;
	private String args;

	private ArrayList<Integer[]> hPieces;
	private ArrayList<Integer[]> vPieces;

	private final int WEIGHT_CELL_BLOCK = 2;
	private final int WEIGHT_CELL_FREE = 5;

	public PersonalBoard(String args, int size) {
		hPieces = new ArrayList<Integer[]>();
		vPieces = new ArrayList<Integer[]>();

		this.size = size;
		this.args = args;
		this.board = populate(args);
	}

	private char[][] populate(String args) {
		char[][] board = new char[size][size];
		Scanner scan = new Scanner(args);
		int x = 0;
		int y = size;
		int pos = 0;

		while (scan.hasNext()) {
			// Move to the next row
			// populate board from higher count since bottom-left = (0,0)
			// and top-right = (N-1, N-1)
			if (pos % size == 0) {
				y--;
				x = 0;
			}

			char s = scan.next().charAt(0);
			board[y][x] = s;
			// Keep track of where each piece is on the board
			if (s == 'H') {
				Integer[] coords = { x, y };
				hPieces.add(coords);
			} else if (s == 'V') {
				Integer[] coords = { x, y };
				vPieces.add(coords);
			}

			x++;
			pos++;
		}

		scan.close();

		return board;
	}

	public int getSize() {
		return this.size;
	}

	public String getArgs() {
		return this.args;
	}

	public char[][] getBoard() {
		return this.board;
	}

	public char getCell(int x, int y) {
		return board[y][x];
	}

	public void setCell(int x, int y, char value) {
		board[y][x] = value;
	}

	public ArrayList<Integer[]> getPieces(char player) {
		if (player == 'H')
			return hPieces;
		return vPieces;
	}

	/** Updates the board after making a move */
	public void updateBoard(Integer[] pos, char player, PersonalMoves m) {
		Iterator<Integer[]> pieceIter;
		if (player == 'H')
			pieceIter = hPieces.iterator();
		else
			pieceIter = vPieces.iterator();

		while (pieceIter.hasNext()) {
			Integer[] p = pieceIter.next();
			if (p[0] == pos[0] && p[1] == pos[1]) {
				p[0] += m.getX();
				p[1] += m.getY();

				// Update the previous cell, and only the next cell if the
				// players piece is still on the board
				if (p[0] < this.size && p[1] < this.size)
					this.setCell(p[0], p[1], player);
				else
					pieceIter.remove();
				this.setCell(pos[0], pos[1], '+');
				break;
			}
		}

	}

	/** Generates a list of possible moves for a given piece */
	public ArrayList<PersonalMoves> genMoves(Integer[] p, char player, PersonalBoard b) {
		Police check;
		ArrayList<PersonalMoves> children = new ArrayList<PersonalMoves>();

		if (player == 'H') {
			for (PersonalMoves m : PersonalMoves.H_MOVES) {
				check = new Police(p[0], p[1], b);
				if (check.hCheck(m))
					children.add(m);
			}
		} else {
			for (PersonalMoves m : PersonalMoves.V_MOVES) {
				check = new Police(p[0], p[1], b);
				if (check.vCheck(m)) {
					children.add(m);
				}
			}
		}
		return children;
	}

	/** Reverts a given move for a given piece */
	public void rollback(Integer[] piece, char player, PersonalMoves move) {
		Integer[] movedPiece = new Integer[2];
		movedPiece[0] = piece[0] + move.getX();
		movedPiece[1] = piece[1] + move.getY();

		// Remove the moved piece (if it's still on the board) and free the cell
		if ((movedPiece[0] >= 0 && movedPiece[1] >= 0) && (movedPiece[0] < this.size && movedPiece[1] < this.size)) {
			if (player == 'H') {
				for (Integer[] p : hPieces) {
					if (movedPiece[0] == p[0] && movedPiece[1] == p[1]) {
						hPieces.remove(p);
						break;
					}
				}
			} else {
				for (Integer[] p : vPieces) {
					if (movedPiece[0] == p[0] && movedPiece[1] == p[1]) {
						vPieces.remove(p);
						break;
					}
				}
			}
			this.setCell(movedPiece[0], movedPiece[1], '+');
		}

		// Add the old piece back and change the cell to the piece
		Integer[] oldPiece = Arrays.copyOf(piece, 2);
		if (player == 'H')
			hPieces.add(oldPiece);
		else
			vPieces.add(oldPiece);

		this.setCell(piece[0], piece[1], player);
	}

	/**
	 * Scores higher for a given players pieces that blocks an opponent's goal
	 * state move, and for having a free cell for that piece
	 * 
	 * @param player
	 * @return score
	 */
	public int checkBlocked(char player) {
		int score = 0;

		if (player == 'H') {
			for (Integer[] piece : hPieces) {
				if (piece[1] - 1 > 0 && getCell(piece[0], piece[1] - 1) == 'V') {
					score += WEIGHT_CELL_BLOCK;
				} else if (piece[0] + 1 < size && getCell(piece[0] + 1, piece[1]) != '+') {
					score -= WEIGHT_CELL_FREE;
				}
			}
		} else {
			for (Integer[] piece : vPieces) {
				if (piece[0] - 1 > 0 && getCell(piece[0] - 1, piece[1]) == 'H') {
					score += WEIGHT_CELL_BLOCK;
				} else if (piece[1] + 1 < size && getCell(piece[0], piece[1] + 1) == 'B') {
					score -= WEIGHT_CELL_FREE;
				}
			}
		}

		return score;
	}
}
