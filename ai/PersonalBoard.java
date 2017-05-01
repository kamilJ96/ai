/* Kamil Jakrzewski kjakrzewski
 * Ai-Linh Tran taal */
import java.util.ArrayList;

public class PersonalBoard {

//	private String[][] board;
	private char[][] board;
	private int size;

	private ArrayList<Integer[]> hPieces;
	private ArrayList<Integer[]> vPieces;

//	public PersonalBoard(ArrayList<String> args, int size) {
//		hPieces = new ArrayList<Integer[]>();
//		vPieces = new ArrayList<Integer[]>();
//
//		this.size = size;
//		this.board = populate(args);
//	}
//
//	private String[][] populate(ArrayList<String> args) {
//		String[][] board = new String[size][size];
//		int x = 0;
//		int y = size;
//		int pos = 0;
//
//		for (String s : args) {
//			// Disregard the first line in args
//
//			// Move to the next row
//			// populate board from higher count since bottom-left = (0,0)
//			// and top-right = (N-1, N-1)
//			if (pos % size == 0) {
//				y--;
//				x = 0;
//			}
//
//			board[y][x] = s;
//
//			// Keep track of where each piece is on the board
//			if (s.equals("H")) {
//				Integer[] coords = { x, y };
//				hPieces.add(coords);
//			} else if (s.equals("V")) {
//				Integer[] coords = { x, y };
//				vPieces.add(coords);
//			}
//
//			x++;
//			pos++;
//		}
//
//		return board;
//	}
	
	public PersonalBoard(String args, int size) {
		hPieces = new ArrayList<Integer[]>();
		vPieces = new ArrayList<Integer[]>();

		this.size = size;
		this.board = populate(args);
	}

	private char[][] populate(String args) {
		char[][] board = new char[size][size];
		char[] argsAsChar;
		char s;
		int x = 0;
		int y = size;
		int pos = 0;

		for(int i=0; i<args.length(); i++){

			// Move to the next row
			// populate board from higher count since bottom-left = (0,0)
			// and top-right = (N-1, N-1)
			if (pos % size == 0) {
				y--;
				x = 0;
			}

			argsAsChar = args.toCharArray();
			s = argsAsChar[i];
			
			if(s == '\n'|| s == ' ') continue;
			
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

//	public String[][] getBoard() {
//		return this.board;
//	}
//
//	public String getCell(int x, int y) {
//		return board[y][x];
//	}
	
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
	
	public void makeMove(int[] piece, PersonalMoves dir, char player){
		board[piece[0]][piece[1]] = '+';
		board[piece[0]+dir.getX()][piece[1]+dir.getY()] = player;
		
		//update pieces list
		if(player =='H'){
			for(Integer[] p : hPieces){
				if(p[0] == piece[0] && p[1] == piece[1]){
					p[0] += dir.getX();
					p[1] += dir.getY();
					break;
				}
			}
		}
		else{
			for(Integer[] p : vPieces){
				if(p[0] == piece[0] && p[1] == piece[1]){
					p[0] += dir.getX();
					p[1] += dir.getY();
					break;
				}				
			}
		}
		
		
	}

}
