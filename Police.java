
public class Police {

	private int currentX, currentY;
	private Board b;


	public Police(int currX, int currY, Board b) {
		currentX = currX;
		currentY = currY;
		this.b = b;
	}


	public boolean hCheck(Moves m){
		int x = m.getX();
		int y = m.getY();
		String nextCell;
		
		
		if(m == Moves.RIGHT && hEndEdge()){
			return true;
		}
		
		if ((m == Moves.RIGHT && !hEndEdge()) || (m == Moves.UP && !vEndEdge())
		    || (m == Moves.DOWN && !vStartEdge())){
			 nextCell = b.getCell(currentX + x, currentY + y);
			if(nextCell.equals("+")){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean vCheck(Moves m){
		int x = m.getX();
		int y = m.getY();
		String nextCell;
		
		if(m == Moves.UP && vEndEdge()){
			return true;
		}
		
		if ((m == Moves.RIGHT && !hEndEdge()) || (m == Moves.LEFT && !hStartEdge())
		    || (m == Moves.UP && !vEndEdge())){
			 nextCell = b.getCell(currentX + x, currentY + y);
			if(nextCell.equals("+")){
				return true;
			}
		}
		    
		return false;
	}
	

	public boolean hLegal() {
		return (singleStep() && ((!isBlocked() && (nextX - currentX) >= 0) 
				|| (hEndEdge() && nextX == currentX)) || 
				(vEndEdge() && (nextY - currentY >= 0)) ||
				(vStartEdge() && (nextY - currentY <= 0)));
	}

	public boolean vLegal() {
		return (singleStep() && ((!isBlocked() && (nextY - currentY) >= 0)
				|| (vEndEdge() && nextY == currentY) ||
				(hEndEdge() && (nextX - currentX >= 0)) ||
				(hStartEdge() && (nextX - currentX <= 0))));
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

	private boolean singleStep() {
		return ((Math.abs(currentX - nextX) == 1 && currentY == nextY)
				|| (Math.abs(currentY - nextY) == 1 && currentX == nextX));
	}
}
