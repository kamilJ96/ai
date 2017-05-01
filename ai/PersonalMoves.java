/* Ai-Linh Tran taal
 * Kamil Jakrzewski kjakrzewski */

import aiproj.slider.*;


public enum PersonalMoves {
	UP (0, 1), 
	DOWN (0, -1), 
	LEFT (-1, 0), 
	RIGHT (1, 0);
	
	private final int x;
	private final int y;
	
	PersonalMoves(int x, int y){
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
	
	public static PersonalMoves toPersonalMoves(Move m){
		
		if(m.d == Move.Direction.UP){
			return PersonalMoves.UP;
		}
		if(m.d == Move.Direction.DOWN){
			return PersonalMoves.DOWN;
		}
		if(m.d == Move.Direction.LEFT){
			return PersonalMoves.LEFT;
		}
		if(m.d == Move.Direction.RIGHT){
			return PersonalMoves.RIGHT;
		}
		
		return null;
	}
	
	public Move toMove(Integer[] pos, PersonalMoves pm){
		Move m;
		Move.Direction dir = null;
	
		if(pm == PersonalMoves.UP){
			dir = Move.Direction.UP;
		}
		else if(pm == PersonalMoves.DOWN){
			dir = Move.Direction.DOWN;
		}
		else if(pm == PersonalMoves.LEFT){
			dir = Move.Direction.LEFT;
		}
		else if(pm == PersonalMoves.RIGHT){
			dir = Move.Direction.RIGHT;
		}
		
		m = new Move(pos[0], pos[1], dir);
		return m;
	}
}
