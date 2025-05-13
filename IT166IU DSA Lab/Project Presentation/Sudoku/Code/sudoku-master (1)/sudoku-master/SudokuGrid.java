import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class SudokuGrid extends JPanel implements ActionListener{
    public MutableBoolean DEBUG;
    
    public  float DELTA = 5f;

    public ArrayList<Timer> timersToTheRight = new ArrayList<Timer>(); // to the right
    public ArrayList<Timer> timersToTheLeft = new ArrayList<Timer>(); // to the left
    public float alpha = 1f;
    private float fadeOutMinimum = 0f;

    private int square_x = 0;
    private int square_y = 0;

    private int offset_y;
    private int distanceBetweenCells_y;
    
    public SudokuCell sudokuCells[][]; // the grid made of 81 cells
    public SudokuCell sudokuCells2[][]; // the grid made of 81 cells ready to be moved
    public SudokuCell sudokuCells3Before[]; // only references to the initial position of current 9 moving cells
    public SudokuCell sudokuCells3Now[]; // only references to the current 9 moving cells
    public SudokuCell sudokuCells3BeforeLeft[]; // only references to the initial position of current 9 moving cells
    public SudokuCell sudokuCells3NowLeft[]; // only references to the current 9 moving cells

    public GridBagConstraints c;

    public SudokuModel theModel;

    private boolean movingToTheRight = true;

    public static int removedCellGoingToTheLeft[];

    public Circle valueCircles[];

    public boolean lastSelectionWasRow = false;
    public boolean lastSelectionWasColumn = false;
    public boolean lastSelectionWasBlock = false;
    public int lastSelectionNumber = 0;

    public SudokuGrid(SudokuModel model, int rows, int columns){
	super(null);
	setModel(model);
	
	this.setLayout(null);

	this.setPreferredSize(new Dimension(1150, 550-4));

	sudokuCells = new SudokuCell[rows][columns];
	sudokuCells2 = new SudokuCell[rows][columns];
	sudokuCells3Before = new SudokuCell[rows];
	sudokuCells3Now = new SudokuCell[rows];

	sudokuCells3BeforeLeft = new SudokuCell[rows];
	sudokuCells3NowLeft = new SudokuCell[rows];

	removedCellGoingToTheLeft = new int[rows];

	valueCircles = new Circle[rows + 2];
	
	offset_y = (getPreferredSize().height - 9 * 50)/2;
	distanceBetweenCells_y = (getPreferredSize().height - 9 * 50)/(9-1);

	for(int i=0; i<rows; i++){
	    for(int j=0; j<columns;j++){
		sudokuCells[i][j] = new SudokuCell(i,j);
		sudokuCells[i][j].addMouseListener(new SudokuCellListener());

		sudokuCells[i][j].setModel(this.theModel);

		sudokuCells2[i][j] = new SudokuCell(i,j);
		sudokuCells2[i][j].addMouseListener(new SudokuCellListener());

		sudokuCells2[i][j].setModel(this.theModel);
		sudokuCells2[i][j].setVisible(false);
		
		add(sudokuCells[i][j], square_x, square_y + offset_y);
		    
		square_x +=50;
	    }

	    square_x = 0;
	    square_y += 50;
	}

	square_x = 0; // reset to default
	square_y = offset_y; // reset to default

	for(int i=0; i<11; i++){
	    String value = Integer.toString(i);
	    int circle_x = 1000;
	    int circle_y = (i-1) * 50 + (i-1)*distanceBetweenCells_y;
	    if(i == 0){
		value = "S";
		circle_x = 500;
		circle_y = (5-1) * 50 + (5-1)*distanceBetweenCells_y;
	    } else if(i == 10){
		value = "T";
		circle_x = 1100;
		circle_y = (5-1) * 50 + (5-1)*distanceBetweenCells_y;
	    }
	    
	    valueCircles[i] = new Circle(theModel, value);
	    valueCircles[i].setAlphaZero();
	    add(valueCircles[i], circle_x, circle_y);
	}

    }
    
    public void add (JPanel jp, int x, int y){
	jp.setLocation(x, y);
	jp.setBounds(new Rectangle(new Point(x, y), new Dimension(50, 50)));
	add(jp);
	jp.setVisible(true);
	repaint();
	
    }

    public void setModel(SudokuModel model){
	this.theModel = model;
    }

    public ArrayList<Timer> moveColumn(int column, boolean movingToTheRight){
	this.lastSelectionWasRow = false;
	this.lastSelectionWasColumn = true;
	this.lastSelectionWasBlock = false;
	this.lastSelectionNumber = column;

	theModel.viewController.hideWelcomeScreen();

	this.movingToTheRight = movingToTheRight;

	Timer thisTimer = null;
	
	if(movingToTheRight == true){
	    thisTimer = getTimerMovingRight();
	    for(int i=0; i<9; i++){
		sudokuCells3Before[i] = sudokuCells[i][column];
		sudokuCells3Now[i] = sudokuCells2[i][column];
     
	    }
	
	    for(int i=0; i<9; i++){
		sudokuCells3Now[i].setAlphaZero();
		sudokuCells3Now[i].setNoBoldBorder();
		sudokuCells3Now[i].setNotInTheGrid();

		add(sudokuCells3Now[i], sudokuCells[i][column].getX(), sudokuCells[i][column].getY());

	    }
	} else if(movingToTheRight == false){
	    thisTimer = getTimerMovingLeft();
	    
	    for(int i=0; i<9; i++){
		sudokuCells3BeforeLeft[i] = sudokuCells[i][column];
		sudokuCells3NowLeft[i] = sudokuCells2[i][column];
		
	    }
	    
	    for(int i=0; i<9; i++){
		sudokuCells3NowLeft[i].setNoBoldBorder();
		sudokuCells3NowLeft[i].setNotInTheGrid();
	    }
	    
	}
	
	ArrayList<Timer> timers1 = new ArrayList<Timer>();
	timers1.add(thisTimer);
	theModel.timers.add(timers1);
	if(theModel.timers.size() == 1){
	    for(Timer t:theModel.timers.get(0)){
		t.start();
	    }
	}

	return timers1;
    }

    public ArrayList<Timer> moveRow(int row, boolean movingToTheRight){
	this.lastSelectionWasRow = true;
	this.lastSelectionWasColumn = false;
	this.lastSelectionWasBlock = false;
	this.lastSelectionNumber = row;
	
	theModel.viewController.hideWelcomeScreen();

	this.movingToTheRight = movingToTheRight;

	Timer thisTimer = null;
	
	if(movingToTheRight == true){
	    thisTimer = getTimerMovingRight();
	    for(int i=0; i<9; i++){
		sudokuCells3Before[i] = sudokuCells[row][i];
		sudokuCells3Now[i] = sudokuCells2[row][i];
     
	    }
	
	    for(int i=0; i<9; i++){
		sudokuCells3Now[i].setAlphaZero();
		sudokuCells3Now[i].setNoBoldBorder();
		sudokuCells3Now[i].setNotInTheGrid();

		add(sudokuCells3Now[i], sudokuCells[row][i].getX(), sudokuCells[row][i].getY());

	    }
	} else if(movingToTheRight == false){
	    thisTimer = getTimerMovingLeft();
	    
	    for(int i=0; i<9; i++){
		sudokuCells3BeforeLeft[i] = sudokuCells[row][i];
		sudokuCells3NowLeft[i] = sudokuCells2[row][i];
		
	    }
	    
	    for(int i=0; i<9; i++){
		sudokuCells3NowLeft[i].setNoBoldBorder();
		sudokuCells3NowLeft[i].setNotInTheGrid();
	    }
	    
	}

	ArrayList<Timer> timers1 = new ArrayList<Timer>();
	timers1.add(thisTimer);
	theModel.timers.add(timers1);
	if(theModel.timers.size() == 1){
	    for(Timer t:theModel.timers.get(0)){
		t.start();
	    }
	}

	return timers1;
    }

    public ArrayList<Timer> moveBlock(int block, boolean movingToTheRight){
	this.lastSelectionWasRow = false;
	this.lastSelectionWasColumn = false;
	this.lastSelectionWasBlock = true;
	this.lastSelectionNumber = block;
	
	int x = 0;
	int y = 0;

	if(block == 0){
	    x = 0; y = 0;
	}

	if(block == 1){
	    x = 3; y = 0;
	}

	if(block == 2){
	    x = 6; y = 0;
	}

	if(block == 3){
	    x = 0; y = 3;
	}

	if(block == 4){
	    x = 3; y = 3;
	}

	if(block == 5){
	    x = 6; y = 3;
	}
	
	if(block == 6){
	    x = 0; y = 6;
	}

	if(block == 7){
	    x = 3; y = 6;
	}

	if(block == 8){
	    x = 6; y = 6;
	}

	theModel.viewController.hideWelcomeScreen();

	this.movingToTheRight = movingToTheRight;

	Timer thisTimer = null;
	
	if(movingToTheRight == true){
	    thisTimer = getTimerMovingRight();
	    for(int i=y, k=0; i<y+3; i++){
		for(int j=x; j<x+3 && k<9; j++, k++){
		    sudokuCells3Before[k] = sudokuCells[i][j];
		    sudokuCells3Now[k] = sudokuCells2[i][j];
		    
		    sudokuCells3Now[k].setAlphaZero();
		    sudokuCells3Now[k].setNoBoldBorder();
		    sudokuCells3Now[k].setNotInTheGrid();
		    
		    add(sudokuCells3Now[k], sudokuCells[i][j].getX(), sudokuCells[i][j].getY());
		}
	    }
	} else if(movingToTheRight == false){
	    thisTimer = getTimerMovingLeft();
	    
	    for(int i=y, k=0; i<y+3; i++){
		for(int j=x; j<x+3 && k<9; j++, k++){
		    sudokuCells3BeforeLeft[k] = sudokuCells[i][j];
		    sudokuCells3NowLeft[k] = sudokuCells2[i][j];
		    
		    sudokuCells3NowLeft[k].setNoBoldBorder();
		    sudokuCells3NowLeft[k].setNotInTheGrid();
		}
	    }
	}
	
	ArrayList<Timer> timers1 = new ArrayList<Timer>();
	timers1.add(thisTimer);
	theModel.timers.add(timers1);
	if(theModel.timers.size() == 1){
	    for(Timer t:theModel.timers.get(0)){
		t.start();
	    }
	} 

	return timers1;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	if(theModel.isAnimationPaused){
	    return;
	}
	
	Timer thisTimer = ((Timer)e.getSource());

	theModel.stopAllTimersOnDiffLevelComparedTo(thisTimer);
	
	theModel.lastRunningTimer = thisTimer;
	
	for(int i=0; i<9; i++){

	    float x1,x2,y1,y2,m,x;
	    x1 = x2 = y1 = y2 = m = x = 0;
	    if(timersToTheRight.contains(thisTimer)){
		sudokuCells3Now[i].setAlphaOne();

		x1 = sudokuCells3Before[i].getX(); //from
		x2 = 600; //to
		
		y1 = sudokuCells3Before[i].getY(); //from
		y2 = i * 50 + i * distanceBetweenCells_y; //to
		
		m = (y2-y1)/(x2-x1);
		
		x = sudokuCells3Now[i].getX() + DELTA;

		if(x >= 450){
		    sudokuCells3Now[i].setOpaque(true);
		}
		
		if(x >= 600){
		    x = 600;
		    
		    boolean didAllFinish = true;
		    for(int i2=0; i2<9; i2++){
			if(sudokuCells3Now[i2].getX() < 600){
			    didAllFinish = false;
			}
		    }
		    
		    if(didAllFinish){
			timersToTheRight.remove(thisTimer);
			
			thisTimer.stop();
			theModel.removeTimer(thisTimer);

			setAlphaOneEdgesCircles();
			
			theModel.startNextTimers();
		    }
		}
	    } else if(timersToTheLeft.contains(thisTimer)) {
		setAlphaZeroEdgesCircles();

		x1 = sudokuCells3BeforeLeft[i].getX(); //from
		x2 = 600; //to
		
		y1 = sudokuCells3BeforeLeft[i].getY(); //from
		y2 = i * 50 + i * distanceBetweenCells_y; //to
		
		m = (y2-y1)/(x2-x1);

		if(x <= 450){
		    sudokuCells3Now[i].setOpaque(false);
		}
		
	        if(i > 0 && 600 - sudokuCells3NowLeft[i-1].getX() < 60){
		    break;
		}
		x = sudokuCells3NowLeft[i].getX() - DELTA;
		
		if(x < sudokuCells3BeforeLeft[i].getX()){
		    x = sudokuCells3BeforeLeft[i].getX();

		    sudokuCells3Now[i].setAlphaZero();
		    removedCellGoingToTheLeft[i] = 1;
		    int count = 0;
		    for(int i3=0; i3<9; i3++){
			if(removedCellGoingToTheLeft[i3] == 1){
			    count++;
			}
		    }
		    
		    if(count == 9){

			for(int i3=0; i3<9; i3++){
			    sudokuCells3Before[i3] = null;
			    sudokuCells3Now[i3] = null;
			}

			timersToTheLeft.remove(thisTimer);
			
			thisTimer.stop();
			theModel.removeTimer(thisTimer);
			
			theModel.startNextTimers();
			
		    }
		}
	    }

	    // the equation of the line
	    float b = y1 - m*x1;
	    float y = m * x + b;
	    
	    if(timersToTheRight.contains(thisTimer)){
		sudokuCells3Now[i].setLocation((int)x, (int)y);
		sudokuCells3Now[i].repaint();
	    } else if(timersToTheLeft.contains(thisTimer)){
		sudokuCells3NowLeft[i].setLocation((int)x, (int)y);
		sudokuCells3NowLeft[i].repaint();
	    }
	}
	    
	repaint();
    }

    public void setAlphaOneEdgesCircles(){
	theModel.viewController.theEdges.repaint();
	theModel.viewController.theEdges.setVisible(true);
    }
    
    public void setAlphaZeroEdgesCircles(){
	theModel.viewController.theEdges.setVisible(false);
	
	for(int i = 0; i < 11; i++){
	    valueCircles[i].setAlphaZero();
	}
    }

    public Timer getTimerMovingRight(){
	Timer timer1 = new Timer(10, null);
	timer1.setInitialDelay(100);
        timer1.addActionListener(this);
	timer1.setCoalesce(false);

	timersToTheRight.add(timer1);
	
	return timer1;
    }

    public Timer getTimerMovingLeft(){
	Timer timer2 = new Timer(10, null);
	timer2.setInitialDelay(100);
        timer2.addActionListener(this);
	timer2.setCoalesce(false);

	timersToTheLeft.add(timer2);
	
	return timer2;	
    }

    public void finishDrawing(){
	valueCircles[0].setAlphaZero();
	valueCircles[10].setAlphaZero();
    }
}
