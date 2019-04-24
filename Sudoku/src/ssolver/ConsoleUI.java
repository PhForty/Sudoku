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
		String nextRow;
		try {
			nextRow = sc.readLine();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("error with input (try/catch");
			nextRow = "0";
		}
		switch(nextRow.toLowerCase()) {
		case "eins":
		case "1":
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
		case "zwei":
		case "2":
			board.newBoardAutoFull();
			board.printBoard();
			break;
		case "drei":
		case "3":
			System.out.println("Bye");
			System.exit(0);
			break;
//		case "vier":
//		case "4":
//			String testString = "Geheime Option 4!";
//			System.out.println(testString);
//			int k = 4;
//			testString = testString.replace(Integer.toString(k), "");
//			System.out.println(testString);
//			break;
		default:
			System.out.println("Fehler, bitte erneut wählen");
			break;		
		}
		}
	}
	
	public static void main(String[] args) throws IOException {
		ConsoleUI CG = new ConsoleUI();
	}
}
