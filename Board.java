
public class Board {

	private String[][] board;
	private int size;
	
	public Board(String[] arg){
		this.size = Integer.parseInt(arg[0]);
		this.board = populate(arg);
	}
	
	private String[][] populate(String[] arg){
		String[][] board = new String[this.size][this.size];
		int i = this.size - 1;
		int j = this.size - 1;
		int pos = 0;
		
		for(String s : arg){
			if (pos == 0){
				pos++;
				continue;
			}
			
			//flip the board around so that the coordinate system matches that
			//of the specs
			if(pos%this.size == 0){
				i++;
				j = 0;
			}
			
			board[i][j] = s;
			j++;
			pos++;
		}
		
		
		return board;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public String[][] getBoard(){
		return this.board;
	}
	
}
