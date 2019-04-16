package ssolver;

public class ConsoleUI {
	Board board = new Board();
	//https://trello.com/b/iqEvUCkR/sudokusolver
	public ConsoleUI() {
		System.out.println("----- SudokuSolver -----");
		board.newBoardAutoFull();
		board.printBoard();
		double start = System.currentTimeMillis();
		logic l = new logic();
		boolean solved = l.solve(board);
		double end = System.currentTimeMillis();
		
		//prints the final status (and technical details)
		if(solved && board.isCorrect()) {
			System.out.println("The board is solved:");
		} else if (!solved && board.isCorrect()){
			System.out.println("I can only solve the board so far. For now, this is to hard");
		} else if (!board.isCorrect()) {
			System.out.println("ERROR, i've made a mistake.");	
		}
		System.out.println("It took "+ (end-start)/1000 + "s and " + l.iterationCounter + " iterations, to come to this point:");
		board.printBoard();
	}
	
	public static void main(String[] args) {
		ConsoleUI CG = new ConsoleUI();
	}
}
