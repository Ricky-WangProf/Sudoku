import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
*
* Display Class to generate the UI, This class has a nested class called Button, it is used for generating all the button used in the game. (INIT, EASY, MEDIUM, HARD)
* 
* CMSC-589 Final Project -- Xuan Wang
*
*/


public class Display extends JPanel implements ActionListener //create display panel
{
	/*
	 *Default serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	private int GameDisplayWidth = 557; //Width of Sudoku
    private int GameDisplayHeight = 580; //Height of Sudoku
    private int ButtonPanelWidth = 200; //Width of Button Panel
    private final Color ButtonPanelColor = new Color(255, 255, 255);  //Button Panel Color
    private final Color GamePanelColor = new Color(255, 255, 255);  //Game Panel Color
    private final Color BlankCubeColor = new Color(255, 0, 0);  //Set to RED if the cube is empty 
   
    public Display()   //construct the sudoku display panel
    {       
    	//mouse listener -- clicks on this panel
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e) 
            {           	
                selectNumber(e.getX(),e.getY());       	
            }
        });
        
        this.setLayout(new BorderLayout());
        
        JPanel BtnPanel = new JPanel();   
        BtnPanel.setPreferredSize(new Dimension(ButtonPanelWidth,200));
        BtnPanel.setBackground(ButtonPanelColor);     
        
        FlowLayout FL = new FlowLayout();
        FL.setVgap(55);
        FL.setHgap(100); 
        BtnPanel.setLayout(FL);	
        
        Button INIT = new Button("Enter Your Sudoku", "INIT");
        Button EASY = new Button("Easy", "EASY");
        Button MEDIUM = new Button("Medium", "MEDIUM");
        Button HARD = new Button("Hard", "HARD");          
        Button GOBACK = new Button("Go Back", "GOBACK");
        Button SOLUTION = new Button("SOLUTION", "SOLUTION");
        
        SOLUTION.setForeground(new Color(0, 204, 0));
        SOLUTION.setBorder(BorderFactory.createBevelBorder(0, new Color(0, 204, 0), new Color(0, 204, 0))); 
	       
        INIT.addActionListener(this);
        EASY.addActionListener(this);
        MEDIUM.addActionListener(this);
        HARD.addActionListener(this);
        GOBACK.addActionListener(this);
        SOLUTION.addActionListener(this);
        
        BtnPanel.add(INIT);
        BtnPanel.add(EASY);
        BtnPanel.add(MEDIUM);
        BtnPanel.add(HARD);
        BtnPanel.add(GOBACK);
        BtnPanel.add(SOLUTION);
        
        this.add(BtnPanel,BorderLayout.WEST); //add button panel to the display panel       
    }
    
    private void selectNumber(int x, int y)
    {
    	int NumberPosition[] = {3,63,124,187,248,309,372,433,494};
    	final byte pSNumberY = 19;
    	
    	//exit method if the mouse not in the sudoku area
    	if( x < ButtonPanelWidth + NumberPosition[0]) {
    		return; 
    	}

    	x -= ButtonPanelWidth - NumberPosition[0];  //reset x value
    	
    	byte count;
    	byte Xposition = 0; //the position of the selected box 0 - 8 in X
    	
    	//find X position
    	for(count = 0; count < 9; count++)
    	{
    		if(x > NumberPosition[count]) {
    			Xposition = count;
    		}
    	}
    	
    	byte Yposition = 0;
    	
    	//check Y position
    	for(count = 0; count < 9; count++) 
    	{
    		if(y > NumberPosition[count]) {
    			Yposition = count; 
    		}
    	}
    	
    	byte position = (byte) (Xposition + Yposition*9); // Get number position
    	
    	byte Xnumber = 0; 
    	x -=  NumberPosition[Xposition]; 
    	//Get X number
    	for(count = 0; count < 3; count++) 
    	{
    		if(x >  pSNumberY*count) {
    			Xnumber = count;  		
    		}
    	}
    	
    	byte Ynumber = 0; 
    	y -=  NumberPosition[Yposition];  
    	//Get Y number
    	for(count = 0; count < 3; count++) 
    	{
    		if(y >  pSNumberY*count) {
    			Ynumber = count;  
    		}
    	}
    	byte number = (byte) (Xnumber + Ynumber*3);
    	
    	FinalGUI.step = MainMethods.selectDefaultSudoku(FinalGUI.sudoku, number, position, FinalGUI.step);
        repaint(ButtonPanelWidth,0, GameDisplayWidth,GameDisplayHeight);  //Repaint the panel
                 
    }
    
    protected void paintComponent(Graphics g)  //called whenever the display panel needs painting
    {
     	final byte NumberX = 11;  //the X offset for the string selected display
     	final byte NumberY = 54;  //the Y offset for the string selected display
     	final byte blanksize = 59;  //the size of the can't select square
     	final byte pNumberX = 4;  //the X offset for the string pencil display
     	final byte pNumberY = 18;  //the Y offset for the string pencil display
     	final byte pSNumberX = 20;  //the X spacing for the string pencil display
     	final byte pSNumberY = 19;  //the Y spacing for the string pencil display

     	int BigLines[] = {0, 184, 369, 554, 577};  //block of 3 x 3 numbers  3 pixels wide
     	int SmallLines[] = {62, 123, 247, 308, 432, 493};  //each number   1 pixel wide
     	int NumberPosition[] = {3,63,124,187,248,309,372,433,494}; //number display
     	Font fontSelected = new Font("SansSerif", Font.ROMAN_BASELINE, 70);  //selected number
        Font fontPencil = new Font("SansSerif", Font.ROMAN_BASELINE, 20);  //pencil lines
     	
        super.paintComponent(g); //paint the component's JPanel     
        g.setColor(GamePanelColor);
        g.setFont(fontPencil);
        
      //horizontal lines
        byte count;  //counter for position 0 to 80
        for(count = 0; count < 5; count++) {
            g.fillRect(0, BigLines[count], GameDisplayWidth + ButtonPanelWidth, 3);
        }

        for(count = 0; count < 6; count++) {
            g.drawLine(0, SmallLines[count], GameDisplayWidth + ButtonPanelWidth, SmallLines[count]);
        }

      //vertical lines
        g.fillRect(BigLines[0] + ButtonPanelWidth , 0, 3, GameDisplayHeight);
        g.fillRect(BigLines[1] + ButtonPanelWidth , 0, 3, GameDisplayHeight);
        g.fillRect(BigLines[2] + ButtonPanelWidth , 0, 3, GameDisplayHeight);
        g.fillRect(BigLines[3] + ButtonPanelWidth , 0, 3, GameDisplayHeight);
        for(count = 0; count < 6; count++) {
            g.drawLine(SmallLines[count] + ButtonPanelWidth, 0, SmallLines[count] + ButtonPanelWidth, GameDisplayHeight);	
        }

        byte numbercount;
        
        for(numbercount = 0; numbercount < 81; numbercount++)
        {
	        g.setColor(GamePanelColor);  //reset color to DB
	        byte zeros = 0; //count the number of zeros in the number(9 pencils numbers)
	        byte outercount;  //outside counter
	        // Check 9 outercount
	        for(outercount = 0; outercount < 3; outercount++)
	        {
	        	// Check 3 number count
		        for(count = 0; count < 3; count++)
		        {
			        byte pencilnumber = FinalGUI.sudoku[count + outercount*3 + numbercount*9][ FinalGUI.step];
			        if(pencilnumber > 0)    //do we display this letter
			        {
				        if(pencilnumber < 10)
				        {
					        g.setFont(fontPencil);
					        g.drawString(String.valueOf(pencilnumber ), NumberPosition[numbercount%9] + (count*pSNumberX) + pNumberX + ButtonPanelWidth, NumberPosition[numbercount/9] + outercount*pSNumberY + pNumberY);
				        } //draw the pencil number
				        else
				        {
					        g.setFont(fontSelected);
					        g.drawString(String.valueOf(pencilnumber - 10), NumberPosition[numbercount%9] + ButtonPanelWidth + NumberX, NumberPosition[numbercount/9] + NumberY);
				        } //draw the selected number
			        }
			        else {
			        	zeros += 1; //have a zero don't display and count it        	
			        }
		        }
	        }
	        
	        if(zeros == 9)
	        {
	        	g.setColor(BlankCubeColor);  //show purple square can't select any number
	        	g.fillRect(NumberPosition[numbercount%9] + ButtonPanelWidth, NumberPosition[numbercount/9], blanksize, blanksize);
	        }
        }
    } 

	@Override
	public void actionPerformed(ActionEvent e)  //call method for push button selected
	{		
		if(e.getActionCommand() == "INIT") {
			FinalGUI.step = 0;   //nothing is selected	
		}
		else if(e.getActionCommand() == "EASY")
		{
			MainMethods.generateSudoku(FinalGUI.sudoku, (byte) 0);
			FinalGUI.step = 50;  //need to fill in 31 steps
		}
		else if(e.getActionCommand() == "MEDIUM")
		{
			MainMethods.generateSudoku(FinalGUI.sudoku, (byte) 0);
			FinalGUI.step = 25;  //need to fill in 46 steps
		}
		else if(e.getActionCommand() == "HARD")
		{
			MainMethods.generateSudoku(FinalGUI.sudoku, (byte) 0);
			FinalGUI.step = 10;  //need to fill in 71 steps
		}	
		else if(e.getActionCommand() == "SOLUTION")
		{
			MainMethods.generateSudoku(FinalGUI.sudoku, FinalGUI.step);  //Show solution
		}
		else if(e.getActionCommand() == "GOBACK")
		{
			if(FinalGUI.step > 0) {
				FinalGUI.step -= 1; //go back 1 step			
			}
		}
	    
		repaint(ButtonPanelWidth,0, GameDisplayWidth,GameDisplayHeight);  //repaint the panel
	}
	
	/*
	 * Nested Class for Button
	 */
	
	public class Button extends JButton   //create a JButton with some fixed properties
	{
		/*
		 *Default serialVersionUID 
		 */
		private static final long serialVersionUID = 1L;
		private final Color BtnBorder = new Color(0, 0, 0);
		private final Color BtnBackground = new Color(255, 255, 255);
		
		//Initialize the attribute for button
	    public Button(String action, String command) 
	    {
	       super(action);  //construct button
	       this.setBackground(BtnBackground);
	       this.setForeground(BtnBorder);
	       this.setBorder(BorderFactory.createBevelBorder(0, BtnBorder, BtnBorder));       
	       this.setActionCommand(command);        
	    }    

		public Dimension getPreferredSize() 
	    {
	        return new Dimension(130,30); 
	    }  
	}

}
 
 
 
 


