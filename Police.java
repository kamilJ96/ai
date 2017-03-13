
public class Police {

	private int currentX, currentY, nextX, nextY;
	private Board b;

	public Police(int currentX, int currentY, int nextX, int nextY, Board b) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.nextX = nextX;
		this.nextY = nextY;
		this.b = b;
	}

	public Police(int currX, int currY, Board b) {
		currentX = currX;
		currentY = currY;
		this.b = b;
	}

	public boolean checkHMove(String move) {
		int x = 0;
		int y = 0;

		if (move == "UP") {
			if (currentY - 1 == -1)
				return false;
			y = -1;
		} else if (move == "DOWN") {
			if (currentY + 1 == b.getSize())
				return false;
			y = 1;
		} else { // moving right
			if (currentX + 1 == b.getSize())
				return true;
			x = 1;
		}

		String nextCell = b.getCell(currentX + x, currentY + y);
		if (nextCell.equals("+"))
			return true;
		return false;
	}
	
	public boolean checkVMove(String move) {
		int x = 0;
		int y = 0;
		
		if (move == "UP") {
			if (currentY - 1 == -1)
				return true;
			y = -1;
		} else if (move == "RIGHT") {
			if (currentX + 1 == b.getSize()) {
				return false;
		}
			x = 1;
		} else { // moving left
			if (currentX - 1 == -1)
				return false;
			x = -1;
		}

		String nextCell = b.getCell(currentX + x, currentY + y);
		if (nextCell.equals("+"))
			return true;
		return false;
	}

	public boolean hLegal() {
		return (singleStep() && ((!isBlocked() && (nextX - currentX) >= 0) || (hAtEndEdge() && nextX == currentX)));
	}

	public boolean vLegal() {
		return (singleStep() && ((!isBlocked() && (nextY - currentY) >= 0) || (vAtEndEdge() && nextY == currentY)));
	}

	public boolean hWin() {
		boolean won = true;

		for (int i = 0; i < b.getSize(); i++) {
			won &= b.getCell(i, b.getSize() - 1).equals("H");
			if (!won) {
				return won;
			}
		}
		return won;
	}

	public boolean vWin() {
		boolean won = true;

		for (int i = 0; i < b.getSize(); i++) {
			won &= b.getCell(b.getSize() - 1, i).equals("V");
			if (!won) {
				return won;
			}
		}
		return won;
	}

	private boolean isBlocked() {
		return !b.getCell(nextY, nextX).equals("+");
	}

	@SuppressWarnings("unused")
	private boolean hAtStartEdge() {
		return currentX == 0;
	}

	private boolean hAtEndEdge() {
		return currentX == b.getSize() - 1;
	}

	@SuppressWarnings("unused")
	private boolean vAtStartEdge() {
		return currentY == 0;
	}

	private boolean vAtEndEdge() {
		return currentY == b.getSize() - 1;
	}

	private boolean singleStep() {
		return ((Math.abs(currentX - nextX) == 1 && currentY == nextY)
				|| (Math.abs(currentY - nextY) == 1 && currentX == nextX));
	}
}
