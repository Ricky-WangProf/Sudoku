/*
*
* Main Method Class to generate the sudoku
* 
* CMSC-589 Final Project -- Xuan Wang
*
*/

public class MainMethods 
{
	//Start function -- 1-9 in 81 spots
	public static void start(byte[][] sudoku)
	{
		int count = 0;
		for(count = 0; count < 729; count++) {
			sudoku[count][0] = (byte) (1 + (count % 9));	
		}
	}
	
	//GenerateSudoku function -- method to generate sudoku
	public static void generateSudoku(byte[][] sudoku, byte startstep)
	{
		java.util.Random generator = new java.util.Random(System.currentTimeMillis());
		byte step = startstep;
		int trys = 0;  //total of the tries to get a complete solution
		
		//keep random try's until have a complete random sudoku	
		do  
		{  		
			trys += 1;
			boolean noblanks = true; //Check if solvable
			step = startstep;
		
		    while((step < 81) && (noblanks))  //try a random selection run
			{
				byte number = (byte) generator.nextInt(9);     // 0 to 8
				byte position = (byte) generator.nextInt(81);  //0 to 80
				step = MainMethods.selectDefaultSudoku(sudoku,number,position,step); //do we have a step
				
				boolean standalone = false;
				
				//check to find any numbers which stand alone and must be selected
				do   
				{
					standalone = false;
					byte positioncount;  //counter for position 0 to 80
					byte numbercount;    //counter for number 0 to 8
					for(positioncount = 0; positioncount < 81; positioncount++)
					{
						byte nzeros = 0; //we start with no zero count
						for(numbercount = 0; numbercount < 9; numbercount++)
						{
							if(sudoku[positioncount * 9 + numbercount][step] == 0) {
								nzeros += 1;  //count zeros	
							}
							else {
								number = (byte) (sudoku[positioncount * 9 + numbercount][step] - 1);  //Offset to 0-8
							}
		
							if(nzeros == 9) {
								noblanks = false;  //unsolvable	
							}
						}		
						
						if((nzeros == 8) && (number < 10))
						{
							step = MainMethods.selectDefaultSudoku(sudoku,number,positioncount,step);  //we have a step
							standalone = true;
						}
					}	
				} while(standalone);	
			}
	    FinalGUI.step = step;
		} while((step != 81) && (trys < 500));  		
	}
	
	public static byte selectDefaultSudoku(byte[][] sudoku, byte number, byte position, byte step)
	{
		//end of number not possible or is selected
		if((sudoku[position*9 + number][step] == 0) || (sudoku[position*9 + number][step] > 9)) {
			return step;
		}
	
	    step += 1;
		int count = 0;
		for(count = 0; count < 729; count++) {
			sudoku[count][step] = sudoku[count][step - 1];       //copy existing to next step	
		}

		for(count = 0; count < 9; count++) {
			sudoku[position*9 + count][step] = 0;	             //Can't select any in box	
		}
		
		byte row =   (byte) (position/9);
		for(count = 0; count < 9; count++) {
			sudoku[row * 81 + count * 9 + number][step] = 0;     //horizontal row	
		}
		
		byte column =   (byte) (position%9);
		for(count = 0; count < 9; count++) {
			sudoku[column * 9 + count * 81 + number][step] = 0;  //vertical row		
		}
		
		int rowblock =  (position/27)*243;                       //row block
		column = (byte) (((position%9)/3)*27);                   //Column block
		byte numbercount;
		
		// Check 3x3 Cube
		for(numbercount = 0; numbercount < 3; numbercount++)
		{
			for(count = 0; count < 3; count++) {
				sudoku[rowblock + column + count * 9 + numbercount * 81 + number ][step] = 0;  //box of 3 x 3
			}
		}
		sudoku[position*9 + number][step] = (byte) (number + 11); //selected now 11 to 19
		
		return step;
	}	
}

