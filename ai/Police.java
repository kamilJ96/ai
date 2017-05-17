package meow.bleh.hopefullynoonehasthisname;

/* Ai-Linh Tran taal
 * Kamil Jakrzewski kjakrzewski
 * This is our checker class.  It determines legal moves, and whatever else
 * requires checking. Ignore the commented out methods at the bottom :) */


public class Police {

	private int currentX, currentY;
	private PersonalBoard b;
	PersonalMoves m;

	public Police(int currX, int currY, PersonalBoard b) {
		currentX = currX;
		currentY = currY;
		this.b = b;
	}
	
	public boolean hCheck(PersonalMoves m) {
		int x = m.getX();
		int y = m.getY();
		char nextCell;

		// Check for when a H piece wants to move off the right-side of the board
		if (m == PersonalMoves.RIGHT && hEndEdge()) {
			return true;
		}

		// Make sure the H piece isn't trying to move off the board in the vertical direction
		if ((m == PersonalMoves.RIGHT) || (m == PersonalMoves.UP && !vEndEdge()) ||
				(m == PersonalMoves.DOWN && !vStartEdge())) {
			nextCell = b.getCell(currentX + x, currentY + y);
			if (nextCell== '+') {
				return true;
			}
		}

		return false;
	}

	public boolean vCheck(PersonalMoves m) {
		int x = m.getX();
		int y = m.getY();
		char nextCell;

		// Check for when a V piece wants to move up and off the board
		if (m == PersonalMoves.UP && vEndEdge()) {
			return true;
		}

		// Make sure the V piece isn't trying to move off the board in the horizontal direction
		if ((m == PersonalMoves.UP) || (m == PersonalMoves.RIGHT && !hEndEdge()) || 
				(m == PersonalMoves.LEFT && !hStartEdge())) {
			nextCell = b.getCell(currentX + x, currentY + y);
			if (nextCell== '+') {
				return true;
			}
		}

		return false;
	}

	private boolean hStartEdge() {
		return currentX == 0;
	}

	private boolean hEndEdge() {
		return currentX == b.getSize() - 1;
	}

	private boolean vStartEdge() {
		return currentY == 0;
	}

	private boolean vEndEdge() {
		return currentY == b.getSize() - 1;
	}
}