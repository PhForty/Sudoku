package ssolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Board {
	// 2dimensional array for representing the board:
	// at first, you choose the column, then the row
	// jedes Stringobjekt aus c* entspricht einer Zelle (9 Objekte für 9 Zeilen)
	// der erste Char repräsentiert Wert (0 für leer), die darauffolgenden mögliche
	// Werte
	String[] c1 = new String[9];
	String[] c2 = new String[9];
	String[] c3 = new String[9];
	String[] c4 = new String[9];
	String[] c5 = new String[9];
	String[] c6 = new String[9];
	String[] c7 = new String[9];
	String[] c8 = new String[9];
	String[] c9 = new String[9];
	String[][] completeBoard = { c1, c2, c3, c4, c5, c6, c7, c8, c9 };

	public Board() {

		clear();
	}

	// fills the board with 0s, to have a clear state
	public void clear() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				completeBoard[r][c] = "0";
			}
		}
	}

	// prints the current Board in the console
	public void printBoard() {
		// iterate through every row and column
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				// prints the line with a "|" for readability every few lines
				System.out.print(completeBoard[r][c].charAt(0) + " ");
				if (c == 2 || c == 5) {
					System.out.print("| ");
				}
			}
			// breaks the line for the new row (and makes a separator every few lines)
			if (r == 2 || r == 5) {
				System.out.println();
				System.out.print("------+-------+------");
			}
			System.out.println();
		}
	}

	// returns whether the Sudoku is solved or not
	public boolean isFinished() {
		// assumption: We are finished. Then test for contradiction
		boolean finished = true;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				// if at any point, the solution is not found, "finished" is false and the inner
				// loop breaks
				if (completeBoard[r][c].charAt(0) == '0') {
					finished = false;
					break;
				}
			}
			// if inner loop was broken, outer will to
			if (finished == false) {
				break;
			}
		}
		return finished;
	}

	// returns whether board has no mistakes
	public boolean isCorrect() {
		boolean correct = true;
		int[] allNum = new int[9];
		// check columns:

		for (int i = 0; i < 9; i++) {
			// if allNum contains a "-1", the corresponding number is already contained
			for (int a = 1; a < 10; a++) {
				allNum[a - 1] = a;
			}
			for (int j = 0; j < 9; j++) {
				// ignore "0"s
				if (Character.getNumericValue(completeBoard[i][j].charAt(0)) != 0) {
					// if number in the field exists in allNum: delete it from allNum
					if (allNum[Character.getNumericValue(completeBoard[i][j].charAt(0)) - 1] != -1) {
						allNum[Character.getNumericValue(completeBoard[i][j].charAt(0)) - 1] = -1;
					}
					// if number was already in the line return error
					else {
						correct = false;
						break;
					}
				}
			}
		}

		// check rows:
		for (int j = 0; j < 9; j++) {
			// if allNum contains a "-1", the corresponding number is already contained
			for (int a = 1; a < 10; a++) {
				allNum[a - 1] = a;
			}
			for (int i = 0; i < 9; i++) {
				// ignore "0"s
				if (Character.getNumericValue(completeBoard[i][j].charAt(0)) != 0) {
					// if number in the field exists in allNum: delete it from allNum
					if (allNum[Character.getNumericValue(completeBoard[i][j].charAt(0)) - 1] != -1) {
						allNum[Character.getNumericValue(completeBoard[i][j].charAt(0)) - 1] = -1;
					}
					// if number was already in the line return error
					else {
						correct = false;
						break;
					}
				}
			}
		}
		// check squares:
		// traverse the 9 squares
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				// reset the allNum-Array
				for (int a = 1; a < 10; a++) {
					allNum[a - 1] = a;
				}
				// traverse the square internally
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						// ignore "0"s
						if (Character.getNumericValue(completeBoard[y * 3 + i][x * 3 + j].charAt(0)) != 0) {
							// if number in the field exists in allNum: delete it from allNum
							if (allNum[Character.getNumericValue(completeBoard[y * 3 + i][x * 3 + j].charAt(0))
									- 1] != -1) {
								allNum[Character.getNumericValue(completeBoard[y * 3 + i][x * 3 + j].charAt(0))
										- 1] = -1;
							}
							// if number was already in the line return error
							else {
								correct = false;
								break;
							}
						}
					}
				}
			}
		}

		return correct;
	}

	// makes a new board, based on user input via console
	public void newBoardManual(BufferedReader sc) {
		try {
			clear();
			
			String nextRow;
			for (int i = 0; i < 9; i++) {
				System.out.println("Bitte die Zeile " + (i + 1) + " eingeben (z.B.: 010450007)");
				nextRow = sc.readLine();
				
				while (syntaxFlaws(nextRow)) {
					System.out.println("ERROR: Zeile " + (i + 1) + " fehlerhaft. Bitte wiederholen.");
					nextRow = sc.readLine();
				}
				// writes given line in array
				for (int j = 0; j < 9; j++) {
					completeBoard[i][j] = Character.toString(nextRow.charAt(j));
				}

			}
			System.out.println("Das eingegebene Board sieht so aus:");
			printBoard();
			System.out.println("Wollen Sie ein anderes Board eingeben? (y/n)");
			String answer = sc.readLine();
			if (answer.equals("y")) {
				System.out.println("Das Board wird überschrieben.");
				newBoardManual(sc);
			} else {
				System.out.println("Das Board wurde gespeichert.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//TODO funktioniert nicht, muss rekursiv aufgerufen werden, da es passieren kann, 
	//das es sich selbst zubaut...
	public void newBoardAutoFull() {
		// STEP 0: fill 3 independent squares (top left, middle and bottom right) at random
		// STEP 1: Repeat 1 and 2 9x (for every single number)
		// STEP 2: for every square left (6), repeat 3 and 4
			// STEP 3: use findSolutionRows/Columns method
			// STEP 4: take one of the possible Solutions and fill it in the square
		// randomizing
			// first rand: random number between 0-2/3-5/6-8 (twice) for the coordinates
			// second rand: save all possible coordinates, then generate a random number, to choose from one
		clear();
		// STEP0
		Random rand = new Random();
		boolean done = false;
		int x;
		int y;
		for (int j = 1; j < 10; j++) {
			while (!done) {
				x = rand.nextInt(3) + 0;
				y = rand.nextInt(3) + 0;
				if (Character.getNumericValue(completeBoard[x][y].charAt(0)) == 0) {
					completeBoard[x][y] = Integer.toString(j);
					done = true;
					break;
				} else {
					done = false;
				}
			}
			done = false;
			while (!done) {
				x = rand.nextInt(3) + 3;
				y = rand.nextInt(3) + 3;
				if (Character.getNumericValue(completeBoard[x][y].charAt(0)) == 0) {
					completeBoard[x][y] = Integer.toString(j);
					done = true;
				} else {
					done = false;
				}
			}
			done = false;
			while (!done) {
				x = rand.nextInt(3) + 6;
				y = rand.nextInt(3) + 6;
				if (Character.getNumericValue(completeBoard[x][y].charAt(0)) == 0) {
					completeBoard[x][y] = Integer.toString(j);
					done = true;
				} else {
					done = false;
				}
			}
			done = false;
		}
		
		// STEP1
		Logic lo = new Logic();
		boolean repeat = true;
		boolean valid;
		
		while(repeat) {
		for (int i = 1; i < 10; i++) {
			//STEP2
			Logic l = new Logic();
				//STEP3
				//STEP4
				//traverse through square, save every solution as coordinates in list
				ArrayList<String> pool = new ArrayList<>();
				String coordinates;
				int locationIndex;
				String temp;
				for(int h = 0; h<6; h++) {
					//above switch
					l.fSColumns(this);
					l.fSRows(this);
					for(int o = 0; o<3; o++) {
						for(int p = 0; p<3; p++) {
					//switch
					switch(h) {
					case 0:
						//top middle square
						if(this.completeBoard[3+o][0+p].contains(Integer.toString(i))&& this.completeBoard[3+o][0+p].charAt(0)=='0') {
							coordinates = Integer.toString((3+o))+Integer.toString((0+p));
							pool.add(coordinates);
						}
						break;
					case 1:
						//top right square
						if(this.completeBoard[6+o][0+p].contains(Integer.toString(i))&& this.completeBoard[6+o][0+p].charAt(0)=='0') {
							coordinates = Integer.toString((6+o))+Integer.toString((0+p));
							pool.add(coordinates);
						}
						break;
					case 2:
						//middle left square
						if(this.completeBoard[0+o][3+p].contains(Integer.toString(i))&& this.completeBoard[0+o][3+p].charAt(0)=='0') {
							coordinates = Integer.toString((0+o))+Integer.toString((3+p));
							pool.add(coordinates);
						}
						break;
					case 3:
						//middle right square
						if(this.completeBoard[6+o][3+p].contains(Integer.toString(i))&& this.completeBoard[6+o][3+p].charAt(0)=='0') {
							coordinates = Integer.toString((6+o))+Integer.toString((3+p));
							pool.add(coordinates);
						}
						break;
					case 4:
						//bottom left square
						if(this.completeBoard[0+o][6+p].contains(Integer.toString(i))&& this.completeBoard[0+o][6+p].charAt(0)=='0') {
							coordinates = Integer.toString((0+o))+Integer.toString((6+p));
							pool.add(coordinates);
						}
						break;
					case 5:
						//bottom middle square
						if(this.completeBoard[3+o][6+p].contains(Integer.toString(i)) && this.completeBoard[3+o][6+p].charAt(0)=='0') {
							coordinates = Integer.toString((3+o))+Integer.toString((6+p));
							pool.add(coordinates);
						}
						break;
					}
					//below switch
						}
					}
					if(pool.size()>1) {
						locationIndex = rand.nextInt(pool.size()-1);
						temp = pool.get(locationIndex);
						this.completeBoard[Integer.parseInt(temp.substring(0,1))][Integer.parseInt(temp.substring(1,2))]=Integer.toString(i);
						} 
						else if(pool.size()==1){
							this.completeBoard[Integer.parseInt(pool.get(0).substring(0, 1))][Integer.parseInt(pool.get(0).substring(1, 2))]=Integer.toString(i);
						}
					pool.clear();
				}
//				printBoard();
//				System.out.println("////////////////////////");
		}
		//needed, so this repeats itself until a valid solution appears
		valid = lo.solve(this);
		if(valid&&this.isCorrect()) {
			repeat = false;
		}
		}
		

	}

	// checks if user inputs fits following format: String, with 9 integers
	public boolean syntaxFlaws(String s) {
		boolean flaw = false;
		if (s.length() == 9) {
			for (int i = 0; i < 9; i++) {
				if (!Character.isDigit(s.charAt(i))) {
					flaw = true;
					break;
				}
			}
		} else {
			flaw = true;
		}
		return flaw;
	}
}
