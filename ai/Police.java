/* Ai-Linh Tran taal
 * Kamil Jakrzewski kjakrzewski
 * This is our checker class.  It determines legal moves, and whatever else
 * requires checking. Ignore the commented out methods at the bottom :) */


public class Police {

	private int currentX, currentY;
	private Board b;
	Moves m;

	public Police(int currX, int currY, Board b, Moves m) {
		currentX = currX;
		currentY = currY;
		this.b = b;
		this.m = m;
	}

	public boolean hCheck(Moves m) {
		int x = m.getX();
		int y = m.getY();
		String nextCell;

		// Check for when a H piece wants to move off the right-side
		//of the board
		if (m == Moves.RIGHT && hEndEdge()) {
			return true;
		}

		// Make sure the H piece isn't trying to move off the board in the 
		//vertical direction
		if ((m == Moves.RIGHT) || (m == Moves.UP && !vEndEdge()) ||
				(m == Moves.DOWN && !vStartEdge())) {
			nextCell = b.getCell(currentX + x, currentY + y);
			if (nextCell.equals("+")) {
				return true;
			}
		}

		return false;
	}

	public boolean vCheck(Moves m) {
		int x = m.getX();
		int y = m.getY();
		String nextCell;

		// Check for when a V piece wants to move up and off the board
		if (m == Moves.UP && vEndEdge()) {
			return true;
		}

		// Make sure the V piece isn't trying to move off the board 
		//in the horizontal direction
		if ((m == Moves.UP) || (m == Moves.RIGHT && !hEndEdge()) || 
				(m == Moves.LEFT && !hStartEdge())) {
			nextCell = b.getCell(currentX + x, currentY + y);
			if (nextCell.equals("+")) {
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

//	public boolean hLegal() {
//	return (singleStep() && ((!isBlocked() && (nextX - currentX) >= 0) 
//	|| (hEndEdge() && nextX == currentX)) ||
//	(vEndEdge() && (nextY - currentY >= 0)) ||
//	(vStartEdge() && (nextY - currentY <= 0)));
//	}
//	
//	public boolean vLegal() {
//	return (singleStep() && ((!isBlocked() && (nextY - currentY) >= 0)
//	|| (vEndEdge() && nextY == currentY) ||
//	(hEndEdge() && (nextX - currentX >= 0)) ||
//	(hStartEdge() && (nextX - currentX <= 0))));
//	}
//
//	public boolean hWin() {
//		boolean won = true;
//
//		for (int i = 0; i < b.getSize(); i++) {
//			won &= b.getCell(i, b.getSize() - 1).equals("H");
//			if (!won) {
//				return won;
//			}
//		}
//		return won;
//	}
//
//	public boolean vWin() {
//		boolean won = true;
//
//		for (int i = 0; i < b.getSize(); i++) {
//			won &= b.getCell(b.getSize() - 1, i).equals("V");
//			if (!won) {
//				return won;
//			}
//		}
//		return won;
//	}
//
//	@SuppressWarnings("unused")
//	private boolean nextCellBlocked() {
//		return !b.getCell(currentX + m.getX(), currentY + m.getY()).equals("+");
//	}
//
//	
//	 private boolean singleStep() {
//	 return ((Math.abs(currentX - nextX) == 1 && currentY == nextY)
//	 || (Math.abs(currentY - nextY) == 1 && currentX == nextX));
//	 }

}
