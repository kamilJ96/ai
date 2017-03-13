
public class Bot {

	
	public static void main (String[] args){
		Board board = new Board(args);
		countLegal(board);
	}
	
	private static void countLegal(Board b){
		int hCount = 0;
		int vCount = 0;
		Police check;
		
		for(int i = 0 ; i < b.getSize(); i++){
			for(int j = 0; j < b.getSize(); j++){
				
				//checking all legal moves for H
				if((b.getBoard()[i][j]).equals("H")){
					check = new Police(i, j, i+1, j, b);
					if (check.hLegal()) hCount++;
					
					check = new Police(i, j, i, j-1, b);
					if (check.hLegal()) hCount++;
					
					check = new Police(i, j, i, j+1, b);
					if (check.hLegal()) hCount++;
					
				}
				
				//checking all legal moves for H
				else if((b.getBoard()[i][j]).equals("V")){
					
					check = new Police(i, j, i-1, j, b);
					if (check.hLegal()) vCount++;

					check = new Police(i, j, i+1, j, b);
					if (check.hLegal()) vCount++;

					check = new Police(i, j, i, j+1, b);
					if (check.hLegal()) vCount++;
				}
			}
		}
		
		System.out.println(hCount);
		System.out.println(vCount);
	}
}
