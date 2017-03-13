


public class Police {

	private int currentX, currentY, nextX, nextY;
	private Board b;
	
	public Police(int currentX, int currentY, int nextX, int nextY, Board b){
		this.currentX = currentX;
		this.currentY = currentY;
		this.nextX = nextX;
		this.nextY = nextY;
		this.b = b;
	}
	
	public boolean hLegal(){
		return(singleStep() && ((!blocked() && (nextX-currentX) >= 0 ) || 
				(hAtEndEdge() && nextX == currentX)));
	}
	
	public boolean vLegal(){
		return(singleStep() && ((!blocked() && (nextY-currentY) >= 0 ) || 
				(vAtEndEdge() && nextY == currentY)));
	}
	
	public boolean hWin(){
		boolean won = true;
		
		for(int i = 0; i < b.getSize(); i++){
			won &= b.getBoard()[i][b.getSize()-1].equals("H");
			if(!won){
				return won;
			}
		}
		return won;
	}
	
	public boolean vWin(){
		boolean won = true;
		
		for(int i = 0; i < b.getSize(); i++){
			won &= b.getBoard()[b.getSize()-1][i].equals("V");
			if(!won){
				return won;
			}
		}
		return won;
	}
	
	private boolean blocked(){
		return !(b.getBoard()[nextY][nextX].equals("+"));
	}
	
	@SuppressWarnings("unused")
	private boolean hAtStartEdge(){
		return currentX == 0;
	}
	
	private boolean hAtEndEdge(){
		return currentX == b.getSize()-1;
	}
	
	@SuppressWarnings("unused")
	private boolean vAtStartEdge(){
		return currentY == 0;
	}
	
	private boolean vAtEndEdge(){
		return currentY == b.getSize()-1;
	}
	
	private boolean singleStep(){
		return ((Math.abs(currentX - nextX) == 1 && currentY == nextY) ||
				(Math.abs(currentY - nextY) == 1 && currentX == nextX));
	}
}
