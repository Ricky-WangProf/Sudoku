import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
*
* Main Class to display
* 
* CMSC-589 Final Project -- Xuan Wang
*
*/

public class FinalGUI 
{
	public static byte[][] sudoku = new byte[729][82];  //global array for sudoku solution
	public static byte step = 0; //global variable for solution step
	
	private static final int WindowWidth = 763; //its 777 pixels wide
	private static final int WindowHeight = 586; //its 636 pixels high
		    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Show(); 
            }			
        });  //end of run()
    }//end of main
    
    public static void Show() 
	 {
    	MainMethods.start(sudoku); //start array at step 0 has no numbers selected
    	   
		    JFrame f = new JFrame("CMSC - 589 -- Sudoku -- Xuan Wang");
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        f.setResizable(false);  //not to be resized
		    f.setSize(WindowWidth, WindowHeight);  //size fixed by size of display and borders
		    f.setLocation(0, 0); //start top left
		    f.setLayout(new BorderLayout());  //north south east west and center		   

	        Display dp =new  Display();
	        dp.setBackground(Color.BLACK);  //set the background of the sudoku display black
	        f.add(dp, BorderLayout.CENTER);  //add the sudoku display panel
	      
	        f.setVisible(true);	
	 }//end of show gui method
    
}//end of MySudoku class

