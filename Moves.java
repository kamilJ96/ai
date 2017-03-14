
public enum Moves {
	UP (0, 1), 
	DOWN (0, -1), 
	LEFT (-1, 0), 
	RIGHT (1, 0);
	
	private final int x;
	private final int y;
	
	Moves(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int[] delta(){
		int[] del = {x, y};
		return del;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
