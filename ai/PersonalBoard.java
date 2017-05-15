package bleh;

/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import aiproj.slider.Move;

public class PersonalBoard {

	private char[][] board;
	private int size;
	private String args;
	private int miniMaxVal;
	private Integer[] movedPiece;
	private PersonalMoves dir;
	private Move moveMade;

	private ArrayList<Integer[]> hPieces;
	private ArrayList<Integer[]> vPieces;

	public PersonalBoard(String args, int size) {
		hPieces = new ArrayList<Integer[]>();
		vPieces = new ArrayList<Integer[]>();

		this.size = size;
		this.args = args;
		this.board = populate(args);
		this.miniMaxVal = 0;
	}
	
	//copy constructor
	public PersonalBoard(PersonalBoard b) {
		this(b.getArgs(), b.getSize());
		
	}

	private char[][] populate(String args) {
		char[][] board = new char[size][size];
		Scanner scan = new Scanner(args);
		int x = 0;
		int y = size;
		int pos = 0;

		while (scan.hasNext()){
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
		System.out.println("setting pos {" + x + ", " + y + "} to " + value);
		board[y][x] = value;
	}

	public ArrayList<Integer[]> getPieces(char player) {
		if (player == 'H')
			return hPieces;
		return vPieces;
	}
	
	public int getMiniMaxVal(){
		return miniMaxVal;
	}
	
	public void setMiniMaxVal(int val){
		miniMaxVal = val;
	}
	
	public void setMovedPiece(Integer[] piece){
		movedPiece = piece;
	}
	
	public void setMove(Move m){
		this.moveMade = m;
	}
	
	public Move getMove(){
		return moveMade;
	}
	
	public Integer[] getMovedPiece(){
		return movedPiece;
	}
	
	public void setDir(PersonalMoves m){
		dir = m;
	}
	
	public PersonalMoves getDir(){
		return dir;
	}

	
	public void updateBoard(Integer[] pos, char player, PersonalMoves m){
        Iterator<Integer[]> pieceIter;
        if (player == 'H')
            pieceIter = hPieces.iterator();
        else
            pieceIter = vPieces.iterator();

        while (pieceIter.hasNext()) {
            Integer[] p = pieceIter.next();
            if(p[0] == pos[0] && p[1] == pos[1]){
                System.out.println("pos = {"+pos[0]+","+pos[1]+"}");

                p[0] += m.getX();
                p[1] += m.getY();
                System.out.println("pos after changing p = {"+pos[0]+","+pos[1]+"}");

                
                // Update the previous cell, and only the next cell if the player's
                // piece is still on the board
                if (p[0] < this.size && p[1] < this.size)	
                    this.setCell(p[0], p[1], player);
                else
                    pieceIter.remove();
                this.setCell(pos[0], pos[1], '+');
                break;
            }
        }


    }
	
	/** Generate a list of possible moves for each piece */
	public ArrayList<PersonalBoard> createChildren(char player, PersonalBoard b) {
		Police check;
		ArrayList<PersonalBoard> children = new ArrayList<PersonalBoard>();

		if (player == 'H') {
			for (Integer[] p : hPieces) {
				for (PersonalMoves m : PersonalMoves.H_MOVES) {
					check = new Police(p[0], p[1], b);
					if (check.hCheck(m)) {
						System.out.println("Move H at {" + p[0] + "," + p[1] + "}");
						PersonalBoard newBoard = b;
						newBoard.updateBoard(p, player, m);
						newBoard.setMove(m.toMove(p, m));
						children.add(newBoard);
					}
				}
			}
		} else {
			for (Integer[] p : vPieces) {
				for (PersonalMoves m : PersonalMoves.V_MOVES) {
					check = new Police(p[0], p[1], b);
					if (check.vCheck(m)) {
						PersonalBoard newBoard = b;
						newBoard.updateBoard(p, player, m);
						newBoard.setMove(m.toMove(p, m));
						children.add(newBoard);
					}
				}
			}
		}

		return children;
	}
}
