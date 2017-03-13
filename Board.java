
public class Board {

	private String[][] board;
	private int size;

	public Board(String[] args) {
		this.size = Integer.parseInt(args[0]);
		this.board = populate(args);
	}

	private String[][] populate(String[] args) {
		String[][] board = new String[size][size];
		int x = 0;
		int y = 0;
		int pos = 0;

		for (String s : args) {
			// Disregard the first line in args
			if (pos == 0) {
				pos++;
				continue;
			}

			// Move to the next row
			if (pos % (size + 1) == 0) {
				y++;
				x = 0;
			}

			board[y][x] = s;
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

}
