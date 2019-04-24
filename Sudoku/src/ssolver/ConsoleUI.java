package ssolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.Scanner;

public class ConsoleUI {
	Board board = new Board();
	//https://trello.com/b/iqEvUCkR/sudokusolver
	public ConsoleUI()  throws IOException{
		System.out.println("----- SudokuSolver -----");
		
		menu();
	}
	public void menu() throws IOException {
		while(true) {
			System.out.println("(1): Sudoku lösen");
		System.out.println("(2): Sudoku generieren");
		System.out.println("(3): Exit");
		InputStreamReader r = new InputStreamReader(System.in);    
		BufferedReader sc = new BufferedReader(r);
		int nextRow;
		try {
			//nextRow is the ASCII-value of 1/2/3!
			nextRow = sc.read();
			sc.readLine();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("error with input (try/catch");
			nextRow = 0;
		}
		switch(nextRow) {
		case 49:
			board.newBoardManual(sc);
			board.printBoard();
			System.out.println("///////////////////////////////");
			double start = System.currentTimeMillis();
			Logic l = new Logic();
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
		case 50:
			board.newBoardAutoFull();
			board.printBoard();
			break;
		case 51:
			System.out.println("Bye");
			System.exit(0);
			break;
//		case 52:
//			String testString = "Geheime Option 4!";
//			System.out.println(testString);
//			int k = 4;
//			testString = testString.replace(Integer.toString(k), "");
//			System.out.println(testString);
//			break;
		default:
			System.out.println("Invalid Input");
			break;		
		}
		}
	}
	
	public static void main(String[] args) throws IOException {
		ConsoleUI CG = new ConsoleUI();
	}
}
