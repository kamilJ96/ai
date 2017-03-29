import java.util.ArrayList;

public class Board {

	private String[][] board;
	private int size;

	private ArrayList<Integer[]> hPieces;
	private ArrayList<Integer[]> vPieces;

	public Board(String[] args) {
		hPieces = new ArrayList<Integer[]>();
		vPieces = new ArrayList<Integer[]>();

		this.size = Integer.parseInt(args[0]);
		this.board = populate(args);
	}

	private String[][] populate(String[] args) {
		String[][] board = new String[size][size];
		int x = 0;
		int y = size;
		int pos = 0;

		for (String s : args) {
			// Disregard the first line in args

			// Move to the next row
			// populate board from higher count since bottom-left = (0,0)
			// and top-right = (N-1, N-1)
			if (pos % size == 0) {
				y--;
				x = 0;
			}

			board[y][x] = s;

			// Keep track of where each piece is on the board
			if (s.equals("H")) {
				Integer[] coords = { x, y };
				hPieces.add(coords);
			} else if (s.equals("V")) {
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

	public String[][] getBoard() {
		return this.board;
	}

	public String getCell(int x, int y) {
		return board[y][x];
	}

	public ArrayList<Integer[]> getPieces(String player) {
		if (player.equals("H"))
			return hPieces;
		return vPieces;
	}

}
