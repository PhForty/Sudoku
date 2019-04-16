package ssolver;

import java.util.Arrays;

public class logic {
	//the youtube channel "Cracking the Cryptic" provides good content
	//future addition: https://www.youtube.com/watch?v=4GVyBiFUNws (min 3:00 onwards, "pairs", seems important for hard ones)
		//the information they give can be considered "safe solution" (they are clear to the outside, just not to themselves)
		//somewhere around the end all the pairs can be resolved
		//theoretically the concept of pairs also applies to groups of 3, 4 or even more...
	//future addition: remodeling (start with possible solutions 1-9 and cut them out, step by step)?
	//x-wing
	boolean endless;
	int iterationCounter = 0;
	public boolean solve(Board board) {
		//as long as the board is not done, the algorithm continues iterative
		boolean isSolved = true;
		endless = false;
		while(!board.isFinished()) {
			iterationCounter++;
			//finds all possible solutions
			findSolutions(board);
			//writes the ones down, that are 100% likely, returns whether anything could be done
			endless = !fillSolutionsGood(board);
//			board.printBoard();
//			System.out.println("///////////////////////////////");
			//if fillSolutions returned "false", it means that nothing was changed, therefore resulting in an endless board
			if(endless) {
				isSolved = false;
				break;
			}
		}
		return isSolved;
	}
	
	public void findSolutions(Board tempBoard) {
		fSColumns(tempBoard);
		fSRows(tempBoard);
		fSSquares(tempBoard);
	}
	//determines all possible values for the cells according to the rows
	//TODO improve, by making it, so that the "findSolutionX" can be called in arbitrary order (copy from row)
	public void fSColumns(Board tempBoard) {
		Integer[] possibleSolutions = new Integer[9];
		for (int i = 0; i<9; i++) {
			//for every new row, possibleSolutions is filled again with all possible Solutions
			for(int aS = 0; aS< 9; aS++) {
				possibleSolutions[aS] = (aS+1);
			}
			//finds all possible solutions
			for(int j = 0; j<9; j++) {
				//if the value is 0, it is ignored
				if(!Character.toString(tempBoard.completeBoard[j][i].charAt(0)).equals("0")) {
					//tests whether the number was already found in the row(if so, then board is faulty!)
					if(possibleSolutions[Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(0)-1)]==0) {
						endless= true;
					}
					//jeder Integer aus der entsprechenden Zeile streicht einen aus "possibleSolutions", bis nur noch die fehlenden übrig sind
					possibleSolutions[Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(0)-1)]=0;
				}
			}
			//extracts the numbers 1-9 and puts them in a string
			String shortSol = "";
			for(int s = 0; s<possibleSolutions.length; s++) {
				if(possibleSolutions[s]!=0)
					shortSol = shortSol + possibleSolutions[s];
			}
			//attaches abbreviated version of possibleSolutions
			for(int j = 0; j<9; j++) {
				tempBoard.completeBoard[j][i] = tempBoard.completeBoard[j][i].charAt(0) + shortSol;
			}
			
		}
	}
	
	//determines all possible values for the cells according to the rows
	public void fSRows(Board tempBoard) {
		Integer[] possibleSolutions = new Integer[9];
		for (int i = 0; i<9; i++) {
			//for every new column, possibleSolutions is filled again with all
			for(int aS = 0; aS< 9; aS++) {
				possibleSolutions[aS] = (aS+1);
			}
			//finds all possible solutions
			for(int j = 0; j<9; j++) {
				//if the value is 0, it is ignored
				if(!Character.toString(tempBoard.completeBoard[i][j].charAt(0)).equals("0")) {
					//tests whether the number was already found in the row(if so, then board is faulty!)
					if(possibleSolutions[Character.getNumericValue(tempBoard.completeBoard[i][j].charAt(0)-1)]==0) {
						endless = true;
					}
					//jeder Integer aus der entsprechenden Zeile streicht einen aus "possibleSolutions", bis nur noch die fehlenden übrig sind
					possibleSolutions[Character.getNumericValue(tempBoard.completeBoard[i][j].charAt(0)-1)]=0;
				}
			}
			//extracts the numbers 1-9 and puts them in a string
			String shortSol = "";
			for(int s = 0; s<possibleSolutions.length; s++) {
				if(possibleSolutions[s]!=0)
					shortSol = shortSol + possibleSolutions[s];
			}
		
			//attaches abbreviated and refined version of possibleSolutions
			String newSol = "";
			for(int j = 0; j<9; j++) {
				//cut out last solution:
				String temp = tempBoard.completeBoard[i][j].substring(1);
				//compares both strings, keeps the double ones
				newSol = "";
				for (int a = 0; a<temp.length();a++) {
					// if current character is doubled, it will be added to new Solution
					if (shortSol.contains(Character.toString(temp.charAt(a)))) {
						newSol = newSol + temp.charAt(a);
					}
				}
				//new solution overwrites old one
				tempBoard.completeBoard[i][j] = tempBoard.completeBoard[i][j].charAt(0) + newSol;
			}
		}
	}
	
	//determines all possible values for the cells according to the squares
	public void fSSquares(Board tempBoard) {
		Integer[] possibleSolutions = new Integer[9];
		//traverse through the top left corners of every square (i and j)
		//within that (x and y): traverse through every field of 3x3 square
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				for(int aS = 0; aS< 9; aS++) {
					possibleSolutions[aS] = (aS+1);
				}
				for(int x = 0; x<3; x++) {
					for (int y = 0; y<3; y++) {
						//if the value is 0, it is ignored
						if(!Character.toString(tempBoard.completeBoard[(i*3)+x][(j*3)+y].charAt(0)).equals("0")) {
							//tests whether the number was already found in the row(if so, then board is faulty!)
							if(possibleSolutions[Character.getNumericValue(tempBoard.completeBoard[(i*3)+x][(j*3)+y].charAt(0)-1)]==0) {
								endless = true;
							}
							//jeder Integer aus der entsprechenden Zeile streicht einen aus "possibleSolutions", bis nur noch die fehlenden übrig sind
							possibleSolutions[Character.getNumericValue(tempBoard.completeBoard[(i*3)+x][(j*3)+y].charAt(0)-1)]=0;
						}
					}
				}
				//extracts the numbers 1-9 and puts them in a string
				String shortSol = "";
				for(int s = 0; s<possibleSolutions.length; s++) {
					if(possibleSolutions[s]!=0)
						shortSol = shortSol + possibleSolutions[s];
				}
				
				for(int x = 0; x<3; x++) {
					for (int y = 0; y<3; y++) {
						//cuts out last solution:
						String temp = tempBoard.completeBoard[(i*3)+x][(j*3)+y].substring(1);
						//compares both strings, keeps the double ones
						String newSol = "";
						for (int a = 0; a<temp.length();a++) {
							// if current character is doubled, it will be added to new Solution
							if (shortSol.contains(Character.toString(temp.charAt(a)))) {
								newSol = newSol + temp.charAt(a);
							}
						}
						//new solution overwrites old one:
						tempBoard.completeBoard[(i*3)+x][(j*3)+y] = tempBoard.completeBoard[(i*3)+x][(j*3)+y].charAt(0) + newSol;
					}
				}
				
			}
		}
	}
	//writes all save solutions (those where only one possible solutions exists/ it is explicit) in the board
	//returns whether anything has been done (to prevent infinite loops)
	public boolean fillSolutionsGood(Board tempBoard) {
		boolean anythingChanged = false;
		//if any "helper" method filled something in, anything has changed
		//i cant do all three at once, it makes the solution wrong. Just one at a time
		if(fillSCGood(tempBoard)) {
			anythingChanged = true;
		} else if(fillSRGood(tempBoard)) {
			anythingChanged = true;
		} else if(fillSSGood(tempBoard)) {
			anythingChanged = true;
		}
		return anythingChanged;
	}
	//fill Solutions Columns for "Good"
	public boolean fillSCGood(Board tempBoard) {
		boolean anythingChanged = false;
		Integer[] distinctNum = new Integer[9];
		
		//traverse columns
		for(int i = 0; i <9; i++) {
			
			//make array empty
			for(int a = 0; a < 9; a++) {
				distinctNum[a] = 0;
			}
			
			//fill array with distinct numbers for the columns
			for(int j = 0; j<9; j++) {
				//go through every column character (start with 1, because first isnt possible solution), if the field is empty
				if(tempBoard.completeBoard[j][i].charAt(0)=='0') {
					for(int k = 1; k<tempBoard.completeBoard[j][i].length(); k++) {
						//if number didn't appear up until now: (and field is still open)
						if(distinctNum[Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(k))-1] == 0) {
							//write the number in the corresponding spot (e.g. number 5 to index 4)
							distinctNum[Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(k))-1] = Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(k));
						} 
						//else write -1
						else {
							distinctNum[Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(k))-1] = -1;
						}
					}
				}
			}
			
			//fill board in distinct solutions from array
			for(int j = 0; j<9; j++) {
				//only check if field is empty
				if(tempBoard.completeBoard[j][i].charAt(0)=='0') {
					for(int dN = 0; dN<9; dN++) {
						//write number if it is unique and appears in the current cells solutions
						if(distinctNum[dN] >= 1 && tempBoard.completeBoard[j][i].contains(Integer.toString(distinctNum[dN]))) {
							tempBoard.completeBoard[j][i] = Integer.toString(distinctNum[dN]);
							anythingChanged = true;
						} //else { //wenn es gebraucht wird, nach der aktuellen For-Schleife durchführen!!
							//board.completeBoard[j][i] = Character.toString(board.completeBoard[j][i].charAt(0));
						//}
					}
				}
			}
		}
		return anythingChanged;
	}
	
	//fill Solutions Rows for "Good"
	public boolean fillSRGood(Board tempBoard) {
		boolean anythingChanged = false;
		Integer[] distinctNum = new Integer[9];
		//traverse rows
				for(int j = 0; j <9; j++) {
					
					//empty distinctNum
					for(int a = 0; a < 9; a++) {
						distinctNum[a] = 0;
					}
					
					//repeat the same for the rows
					for(int i = 0; i<9; i++) {
						//go through every column character (start with 1, because first isnt possible solution)
						//TODO Error: If-statement should be uncommented, but it does produce weird results then
						if(tempBoard.completeBoard[j][i].charAt(0) == '0') {
							System.out.println("i: "+i + "charAt(0): "+tempBoard.completeBoard[j][i].charAt(0));
							for(int k = 1; k<tempBoard.completeBoard[j][i].length(); k++) {
								//if number didn't appear up until now:
								if(distinctNum[Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(k))-1] == 0) {
									//write the number in the corresponding spot (e.g. number 5 to index 4)
									distinctNum[Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(k))-1] = Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(k));
								} //else write -1
								else {
									distinctNum[Character.getNumericValue(tempBoard.completeBoard[j][i].charAt(k))-1] = -1;
								}
							}
						}
					}
					
					//fill in distinct solutions from array
					for(int i = 0; i<9; i++) {
						if(tempBoard.completeBoard[j][i].charAt(0)=='0') {
							for(int dN = 0; dN<9; dN++) {
								//write number if it is unique and appears in the current cell
								if(distinctNum[dN] >= 1 && tempBoard.completeBoard[j][i].contains(Integer.toString(distinctNum[dN]))) {
									tempBoard.completeBoard[j][i] = Integer.toString(distinctNum[dN]);
									anythingChanged = true;
								}//TODO the three lines below should be commented out, but as above: very weird results 
//								else {
//									tempBoard.completeBoard[j][i] = Character.toString(tempBoard.completeBoard[j][i].charAt(0));
//								}
							}
						}
					}
					System.out.println(Arrays.toString(distinctNum));
				}
				return anythingChanged;
	}
	
	//fill Solutions Squares for "Good"
	public boolean fillSSGood(Board tempBoard) {
		boolean anythingChanged = false;
		Integer[] distinctNum = new Integer[9];
		//traverse squares
				for (int i = 0; i<3; i++) {
					for (int j = 0; j<3; j++) {
						
						//clear distinctNum for every square
						for(int a = 0; a < 9; a++) {
							distinctNum[a] = 0;
						}
						//traverse the 3x3 field of cells (i*3+x and j*3+y) to fill distinctNum
						for(int x = 0; x<3; x++) {
							for (int y = 0; y<3; y++) {
								//traverse possible solutions in the current cell
								if(tempBoard.completeBoard[i*3+x][j*3+y].charAt(0)=='0') {
									for(int k = 1; k<tempBoard.completeBoard[i*3+x][j*3+y].length(); k++) {
										//if number didn't appear up until now:
										if(distinctNum[Character.getNumericValue(tempBoard.completeBoard[i*3+x][j*3+y].charAt(k))-1] == 0) {
											//write the number in the corresponding spot (e.g. number 5 to index 4)
											distinctNum[Character.getNumericValue(tempBoard.completeBoard[i*3+x][j*3+y].charAt(k))-1] = Character.getNumericValue(tempBoard.completeBoard[i*3+x][j*3+y].charAt(k));
										} //else write -1
										else {
											distinctNum[Character.getNumericValue(tempBoard.completeBoard[i*3+x][j*3+y].charAt(k))-1] = -1;
										}
									}
								}
							}
						}
						
						//traverse 3x3 field of cells again, to write solutions
						for(int x = 0; x<3; x++) {
							for (int y = 0; y<3; y++) {
								//traverse distinctNum Array (for every single cell)
								if(tempBoard.completeBoard[i*3+x][j*3+y].charAt(0)=='0') {
									for(int dN = 0; dN<9; dN++) {
										//write number if it is unique and appears in the current cell
										if(distinctNum[dN] >= 1 && tempBoard.completeBoard[i*3+x][j*3+y].contains(Integer.toString(distinctNum[dN]))&& tempBoard.completeBoard[i*3+x][j*3+y].charAt(0)=='0') {
											System.out.println("Lösung Square: "+ Integer.toString(distinctNum[dN]));
											tempBoard.completeBoard[i*3+x][j*3+y] = Integer.toString(distinctNum[dN]);
											anythingChanged = true;
										} //else {
										//	board.completeBoard[i*3+x][j*3+y] = Character.toString(board.completeBoard[i*3+x][j*3+y].charAt(0));
										//}
									}
								}
							}
						}
					}
				}
				return anythingChanged;
	}
	
	//the "bad" one, which just fills in the real perfect solutions
	public boolean fillSolutionsTrivial(Board tempBoard) {
		boolean anythingChanged = false;
		for(int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				//if there is only one possible solution (and it is not solved yet): solve it
				if(tempBoard.completeBoard[i][j].length()==2 && Character.toString(tempBoard.completeBoard[i][j].charAt(0)).equals("0")) {
					//fill in the solution
					tempBoard.completeBoard[i][j] = Character.toString(tempBoard.completeBoard[i][j].charAt(1));
					anythingChanged = true;
				} else {
					//clears everything possible solution, when more than one have been found
					tempBoard.completeBoard[i][j] = Character.toString(tempBoard.completeBoard[i][j].charAt(0));
				}
			}
		}
		return anythingChanged;
	}
	
	

}
