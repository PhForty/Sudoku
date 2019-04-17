package ssolver;

import java.util.Scanner;

public class ConsoleUI {
	Board board = new Board();
	//https://trello.com/b/iqEvUCkR/sudokusolver
	public ConsoleUI() {
		System.out.println("----- SudokuSolver -----");
		while(true) {
		System.out.println("(1): Sudoku lösen");
		System.out.println("(2): Sudoku generieren");
		System.out.println("(3): Exit");
		Scanner sc = new Scanner(System.in);
		String nextRow;
		nextRow = sc.nextLine();
		switch(nextRow) {
		case "1":
			board.newBoardManual();
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
			break;
		case "2":
			boolean repeat = true;
			boolean valid;
			logic lo = new logic();
			do {
			board.newBoardAutoFull();
			//if it is solvable it is complete/valid
			valid = lo.solve(board);
			if(valid&&board.isCorrect()) {
				repeat = false;
			}
			}while(repeat);
			break;
		case "3":
			System.out.println("Bye");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid Input");
			break;			
		}
		
	}
	}
	
	public static void main(String[] args) {
		ConsoleUI CG = new ConsoleUI();
	}
}
