import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Dictionary;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Sudoku{
    public static MutableBoolean DEBUG = new MutableBoolean(false);
    
    public final int SIZE = 9;
    public final int SLEEP = 1;
	
    public SudokuModel theModel;

    public JButton openFileButton;
    public JButton pauseButton;
    public JButton playButton;
    public JButton playPauseButton;
    public JButton nextStepButton;
    public JButton propagateButton;
    public JButton allDifferentButton;
    public JButton selectButton;
    public JButton deselectButton;
    public JButton showDemoButton;
    public JButton backtrackButton;
    public JButton choco3Button;

    public JMenuBar jMenuBar;
    
    public JMenu fileMenu;
    public JMenuItem openMenuItem;
    public JMenuItem resetFileMenuItem;
    public JMenuItem exitMenuItem;

    public JMenu settingsMenu;
    public JMenuItem solveInStepsTrueMenuItem;
    public JMenuItem solveInStepsFalseMenuItem;
    public JMenuItem setNormalSpeedMenuItem;
    public JMenuItem setHighSpeedMenuItem;
    public JMenuItem setPropagateOnMenuItem;
    public JMenuItem setPropagateOffMenuItem;
    public JMenuItem setRunAllDifferentWhenSelectingOnMenuItem;
    public JMenuItem setRunAllDifferentWhenSelectingOffMenuItem;
    public JMenuItem setDeselectAfterAllDifferentOnMenuItem;
    public JMenuItem setDeselectAfterAllDifferentOffMenuItem;
    
    public JLabel currentStepStatusLabel;

    public JSlider speedSlider;

    public String filePath = new String();

    private Sudoku(){
	theModel = new SudokuModel();
	theModel.referenceToMain = this;
	theModel.DEBUG = DEBUG;
	theModel.SIZE = SIZE;
	theModel.SLEEP = SLEEP;

	showGUI();
			
	if(DEBUG.getValue()){System.out.println("GUI loaded");}
	
	File workingDirectory = new File(System.getProperty("user.dir"));
		    
	try {
	    // demo file

	    filePath = workingDirectory.getAbsolutePath() + "/puzzles/lockedset.txt";
	    theModel.readFromFile(filePath);
	} catch (FileNotFoundException ex) {
	    ex.printStackTrace();
	}
    }
    
    public void showGUI(){

	JFrame mainFrame = new JFrame("sudo ku");
	mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	mainFrame.setSize(1250, 725);
	mainFrame.setLocationRelativeTo(null);
	
	mainFrame.setResizable(false);

	jMenuBar = new javax.swing.JMenuBar();
	
	fileMenu = new javax.swing.JMenu("File");
	openMenuItem = new javax.swing.JMenuItem("Open");
	resetFileMenuItem = new javax.swing.JMenuItem("Reset file");
	exitMenuItem = new javax.swing.JMenuItem("Exit");
	exitMenuItem.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
		    System.exit(0);
		}
	    });
	fileMenu.add(openMenuItem);
	fileMenu.add(resetFileMenuItem);
	fileMenu.add(exitMenuItem);

	settingsMenu = new javax.swing.JMenu("Settings");
	solveInStepsTrueMenuItem = new javax.swing.JMenuItem("Solve in steps");
	solveInStepsFalseMenuItem = new javax.swing.JMenuItem("Do not solve in steps");
	setNormalSpeedMenuItem = new javax.swing.JMenuItem("Set normal speed");
	setHighSpeedMenuItem = new javax.swing.JMenuItem("Set high speed");
	setPropagateOnMenuItem = new javax.swing.JMenuItem("Set propagate on");
	setPropagateOffMenuItem = new javax.swing.JMenuItem("Set propagate off");
	setRunAllDifferentWhenSelectingOnMenuItem = new javax.swing.JMenuItem("Run allDifferent when selecting on");
	setRunAllDifferentWhenSelectingOffMenuItem = new javax.swing.JMenuItem("Run allDifferent when selecting off");
	setDeselectAfterAllDifferentOnMenuItem = new javax.swing.JMenuItem("Deselect row after allDifferent finishes on");
	setDeselectAfterAllDifferentOffMenuItem = new javax.swing.JMenuItem("Deselect row after allDifferent finishes off");
	
	settingsMenu.add(solveInStepsTrueMenuItem);
	settingsMenu.add(solveInStepsFalseMenuItem);
	settingsMenu.add(new JSeparator(JSeparator.HORIZONTAL));
	settingsMenu.add(setNormalSpeedMenuItem);
	settingsMenu.add(setHighSpeedMenuItem);
	settingsMenu.add(new JSeparator(JSeparator.HORIZONTAL));
	settingsMenu.add(setPropagateOnMenuItem);
	settingsMenu.add(setPropagateOffMenuItem);
	settingsMenu.add(new JSeparator(JSeparator.HORIZONTAL));
	settingsMenu.add(setRunAllDifferentWhenSelectingOnMenuItem);
	settingsMenu.add(setRunAllDifferentWhenSelectingOffMenuItem);
	settingsMenu.add(new JSeparator(JSeparator.HORIZONTAL));
	settingsMenu.add(setDeselectAfterAllDifferentOnMenuItem);
	settingsMenu.add(setDeselectAfterAllDifferentOffMenuItem);

	
	jMenuBar.add(fileMenu);
	jMenuBar.add(settingsMenu);

	openMenuItem.addActionListener(new MouseListener());
	resetFileMenuItem.addActionListener(new MouseListener());
	solveInStepsTrueMenuItem.addActionListener(new MouseListener());
	solveInStepsFalseMenuItem.addActionListener(new MouseListener());
	setNormalSpeedMenuItem.addActionListener(new MouseListener());
	setHighSpeedMenuItem.addActionListener(new MouseListener());
	setPropagateOnMenuItem.addActionListener(new MouseListener());
	setPropagateOffMenuItem.addActionListener(new MouseListener());
	setRunAllDifferentWhenSelectingOnMenuItem.addActionListener(new MouseListener());
	setRunAllDifferentWhenSelectingOffMenuItem.addActionListener(new MouseListener());
	setDeselectAfterAllDifferentOnMenuItem.addActionListener(new MouseListener());
	setDeselectAfterAllDifferentOffMenuItem.addActionListener(new MouseListener());
	
	mainFrame.setJMenuBar(jMenuBar);
	
	JPanel theGridPlaceHolder = new JPanel();
	theGridPlaceHolder.setSize(1250, 725);

	ViewController vc = new ViewController(theModel);
	
	theGridPlaceHolder.add(vc);
	mainFrame.add(theGridPlaceHolder);

	JPanel buttonHolders = new JPanel();
	buttonHolders.setPreferredSize(new Dimension(1265, 35));

	openFileButton = new JButton("Open");
	openFileButton.addActionListener(new MouseListener());
	buttonHolders.add(openFileButton);

	playPauseButton = new JButton("Pause");
	playPauseButton.addActionListener(new MouseListener());
	buttonHolders.add(playPauseButton);

	nextStepButton = new JButton("Next step");
	nextStepButton.addActionListener(new MouseListener());
	buttonHolders.add(nextStepButton);

	propagateButton = new JButton("Propagate");
	propagateButton.addActionListener(new MouseListener());
	buttonHolders.add(propagateButton);
	
	allDifferentButton = new JButton("AllDifferent");
	allDifferentButton.addActionListener(new MouseListener());
	buttonHolders.add(allDifferentButton);

	selectButton = new JButton();
	deselectButton = new JButton("Deselect row/column/block");
	deselectButton.addActionListener(new MouseListener());
	buttonHolders.add(deselectButton);
	
	showDemoButton = new JButton("Show demo");
	showDemoButton.addActionListener(new MouseListener());
	buttonHolders.add(showDemoButton);

	backtrackButton = new JButton("Backtrack Solve");
	backtrackButton.addActionListener(new MouseListener());
	buttonHolders.add(backtrackButton);

	choco3Button = new JButton("Choco3 Solve");
	choco3Button.addActionListener(new MouseListener());
	buttonHolders.add(choco3Button);
	    
	currentStepStatusLabel = new JLabel();
	currentStepStatusLabel.setPreferredSize(new Dimension(1215, 25));
	currentStepStatusLabel.setFont(new Font("Serif", Font.PLAIN, 14));
	currentStepStatusLabel.setForeground(Color.BLACK);
	currentStepStatusLabel.setVerticalAlignment(SwingConstants.BOTTOM);
	currentStepStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
	currentStepStatusLabel.setText("Please select a row, column or block");
	theGridPlaceHolder.add(currentStepStatusLabel);
	
	theGridPlaceHolder.add(buttonHolders);
	
	speedSlider = new JSlider(0, 100);
	speedSlider.setPreferredSize(new Dimension(1215, 45));
	speedSlider.addChangeListener(new MyChangeListener());
	speedSlider.setSnapToTicks(true);
	speedSlider.setMajorTickSpacing(10);
	speedSlider.setMinorTickSpacing(1);
	speedSlider.setPaintTicks(true);

	Dictionary<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
	labelTable.put(new Integer(0), new JLabel("Slow"));
	labelTable.put(new Integer(100), new JLabel("Fast"));

	speedSlider.setLabelTable(labelTable);
	
	speedSlider.setPaintLabels(true);

	theGridPlaceHolder.add(speedSlider);

	playPauseButton.setEnabled(false);
	allDifferentButton.setEnabled(false);
	selectButton.setEnabled(true);
	deselectButton.setEnabled(false);
		
	mainFrame.setVisible(true);
    }
    
    public static void main(String args[]){

	if(args.length > 0){
	    if(args[0].compareTo("debug") == 0 || args[0].compareTo("DEBUG") == 0){
		DEBUG.setValue(true);
		if(DEBUG.getValue()){System.out.println("Debug enabled");}	
	    }
	}
		
	new Sudoku();
    }

    public void setAnimationSpeed(float SPEED){

	// SPEED Inverse
	// Used for inversely proportional values

	float SPEED_INVERSE = 100 - SPEED;
	
	// Values for minimum speed are stored in _MIN
	// Values for maximum speed are stored in _MAX

	int SLEEP_MIN = 1;                            // sleep thread when drawing
	int SLEEP_MAX = 0;                            // sleep thread when drawing
	int SLEEP_BETWEEN_STEPS_MIN = 3000;           // sleep thread when drawing
	int SLEEP_BETWEEN_STEPS_MAX = 0;              // sleep thread when drawing
	int SLEEP_THE_EDGES_MIN = 500;                // sleep thread when drawing
	int SLEEP_THE_EDGES_MAX = 0;                  // sleep thread when drawing
	int SLEEP_BETWEEN_STEPS_THE_EDGES_MIN = 3000; // sleep thread when drawing
	int SLEEP_BETWEEN_STEPS_THE_EDGES_MAX = 0;    // sleep thread when drawing
	float DELTA_SUDOKU_CELLS_MIN = 0.01f;         // for fade out/fade in alpha
	float DELTA_SUDOKU_CELLS_MAX = 1f;            // for fade out/fade in alpha
	float DELTA_THE_GRID_MIN = 0.5f;              // move cells right by delta
	float DELTA_THE_GRID_MAX = 200f;              // move cells right by delta
	float DELTA_THE_EDGES_MIN = 0.01f;            // for fade out/fade in alpha
	float DELTA_THE_EDGES_MAX = 1f;               // for fade out/fade in alpha
	float DELTA_VALUE_CIRCLES_MIN = 0.01f;        // for fade out/fade in alpha
	float DELTA_VALUE_CIRCLES_MAX = 1f;           // for fade out/fade in alpha
	
	int SLEEP = Math.round(SPEED_INVERSE / 100 * (SLEEP_MIN - SLEEP_MAX)) + SLEEP_MAX;
	int SLEEP_BETWEEN_STEPS = Math.round(SPEED_INVERSE / 100 * (SLEEP_BETWEEN_STEPS_MIN - SLEEP_BETWEEN_STEPS_MAX)) + SLEEP_BETWEEN_STEPS_MAX;
	int SLEEP_THE_EDGES = Math.round(SPEED_INVERSE / 100 * (SLEEP_THE_EDGES_MIN - SLEEP_THE_EDGES_MAX)) + SLEEP_THE_EDGES_MAX;
	int SLEEP_BETWEEN_STEPS_THE_EDGES = Math.round(SPEED_INVERSE / 100 * (SLEEP_BETWEEN_STEPS_THE_EDGES_MIN - SLEEP_BETWEEN_STEPS_THE_EDGES_MAX)) + SLEEP_BETWEEN_STEPS_THE_EDGES_MAX;
	float DELTA_SUDOKU_CELLS = SPEED / 100 * (DELTA_SUDOKU_CELLS_MAX - DELTA_SUDOKU_CELLS_MIN) + DELTA_SUDOKU_CELLS_MIN;
	float DELTA_THE_GRID = SPEED / 100 * (DELTA_THE_GRID_MAX - DELTA_THE_GRID_MIN) + DELTA_THE_GRID_MIN;
	float DELTA_THE_EDGES = SPEED / 100 * (DELTA_THE_EDGES_MAX - DELTA_THE_EDGES_MIN) + DELTA_THE_EDGES_MIN;
	float DELTA_VALUE_CIRCLES = SPEED / 100 * (DELTA_VALUE_CIRCLES_MAX - DELTA_VALUE_CIRCLES_MIN) + DELTA_VALUE_CIRCLES_MIN;

	theModel.SLEEP = SLEEP;
	theModel.SLEEP_BETWEEN_STEPS = SLEEP_BETWEEN_STEPS;
	theModel.viewController.theEdges.SLEEP = SLEEP_THE_EDGES;
	theModel.viewController.theEdges.SLEEP_BETWEEN_STEPS = SLEEP_BETWEEN_STEPS_THE_EDGES;

	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		theModel.viewController.theGrid.sudokuCells[i][j].DELTA = DELTA_SUDOKU_CELLS;
	    }
	}
	    
	theModel.viewController.theGrid.DELTA = DELTA_THE_GRID;

	theModel.viewController.theEdges.DELTA = DELTA_THE_EDGES;
	
	for(int i=0; i<11; i++){
	    theModel.viewController.theGrid.valueCircles[i].DELTA = DELTA_VALUE_CIRCLES;
	}

    }
    
    private class MyChangeListener implements ChangeListener{
	
	public void stateChanged(ChangeEvent e) {
	    JSlider slider = (JSlider) e.getSource();
	    int sliderValue = slider.getValue();

	    setAnimationSpeed(sliderValue);
	}
    }
    
    private class MouseListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {

	    // Handle set normal speed  menu item
	    if (e.getSource() == solveInStepsTrueMenuItem) {
		if(DEBUG.getValue()){System.out.println("solveInSteps True MenuItem");}
		theModel.setSolveInSteps(true);
	    }

	    // Handle set high speed menu item
	    if (e.getSource() == solveInStepsFalseMenuItem) {
		if(DEBUG.getValue()){System.out.println("solveInSteps False MenuItem");}
		theModel.setSolveInSteps(false);
	    }

	    // Handle set normal speed  menu item
	    if (e.getSource() == setNormalSpeedMenuItem) {
		setAnimationSpeed(0);
	    }

	    // Handle set high speed menu item
	    if (e.getSource() == setHighSpeedMenuItem) {
		setAnimationSpeed(100);
	    }
	    
	    // Handle set propagate on menu item
	    if (e.getSource() == setPropagateOnMenuItem) {
		System.out.println("Propagate on");
		theModel.setPropagateAfterAllDifferent(true);
	    }

	    // Handle set propagate off menu item
	    if (e.getSource() == setPropagateOffMenuItem) {
		System.out.println("Propagate off");
		theModel.setPropagateAfterAllDifferent(false);
	    }

	    // Handle set run all different after making a selection on menu item
	    if (e.getSource() == setRunAllDifferentWhenSelectingOnMenuItem) {
		System.out.println("AllDifferent on selection on");
		theModel.setRunAllDifferentOnSelection(true);
	    }

	    // Handle set run all different after making a selection on menu item
	    if (e.getSource() == setRunAllDifferentWhenSelectingOffMenuItem) {
		System.out.println("AllDifferent on selection off");
		theModel.setRunAllDifferentOnSelection(false);
	    }

	    // Handle deselect after alldifferent finishies running on menu item
	    if (e.getSource() == setDeselectAfterAllDifferentOnMenuItem) {
		System.out.println("Deselect after alldifferent on");
		theModel.setDeselectAfterAllDifferent(true);
	    }

	    // Handle deselect after alldifferent finishies running off menu item
	    if (e.getSource() == setDeselectAfterAllDifferentOffMenuItem) {
		System.out.println("Deselect after alldifferent off");
		theModel.setDeselectAfterAllDifferent(false);
	    }
	    
	    // Handle open button action
	    if (e.getSource() == openFileButton || e.getSource() == openMenuItem) {
		if(!selectButton.isEnabled()){
		    theModel.setCurrentStepStatusLabel("Please make sure you deselect first and no operation is currently playing");
		    if(DEBUG.getValue()){System.out.println("Please make sure you deselect first and no operation is currently playing");}
		    
		    return;
		}
				
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter(".txt - plain text files", "txt"));
	
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
	
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    File file = fc.getSelectedFile();

		    try {
			filePath = file.getAbsolutePath();
			theModel.readFromFile(filePath);
		    } catch (FileNotFoundException ex) {
			ex.printStackTrace();
		    }
		} else {
		    if(DEBUG.getValue()){System.out.println("Open command canceled by the user");}
		}
	    }

	    // Handle open button action
	    if (e.getSource() == resetFileMenuItem) {
		if(!selectButton.isEnabled()){
		    theModel.setCurrentStepStatusLabel("Please make sure you deselect first and no operation is currently playing");
		    if(DEBUG.getValue()){System.out.println("Please make sure you deselect first and no operation is currently playing");}
		    
		    return;
		}
		
		try {
		    theModel.readFromFile(filePath);
		} catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		}
	    }

	    // Handle backtrack solve button
	    if (e.getSource() == backtrackButton) {
		// escape from EDT
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
			    theModel.solveUsingBacktracking(theModel.worldPeek());
			    theModel.redraw();
			}			
		    });
		t.start();
	    }

	    // Handle next move button
	    if (e.getSource() == nextStepButton) {
		// escape from EDT
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
			    theModel.nextAnimation();
			}			
		    });
		t.start();
	    }

	    // Handle propagate button
	    if (e.getSource() == propagateButton) {
		// escape from EDT
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
			    theModel.propagate();
			}			
		    });
		t.start();
	    }
	    
	    // Handle playPauseButton button
	    if (e.getSource() == playPauseButton) {
		if(theModel.isAnimationPaused == false){
		    //pause
		    playPauseButton.setText("Play");
		    // escape from EDT
		    Thread t = new Thread(new Runnable() {
			    @Override
			    public void run() {
				theModel.pauseAnimation();
			    }			
			});
		    t.start();
		} else if(theModel.isAnimationPaused == true){
		    //play
		    playPauseButton.setText("Pause");
		    // escape from EDT
		    Thread t = new Thread(new Runnable() {
			    @Override
			    public void run() {
				theModel.playAnimation();
			    }			
			});
		    t.start();
		}
	    }
	    
	    // Handle solve choco3 button
	    if (e.getSource() == choco3Button) {
		// escape from EDT
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
			    theModel.solveUsingChoco3();
			    theModel.redraw();
			}});
		
		th.start();
	    }

	    // Handle allDifferent button
	    if (e.getSource() == allDifferentButton){
		// escape from EDT
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
			    theModel.allDifferent();
			}});
		
		th.start();
	    }
	    
	    // Handle deselect button
	    if (e.getSource() == deselectButton){
		// escape from EDT
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
			    theModel.deselect();
			}});
		
		th.start();
	    }

	    // Handle showDemoButton button
	    if (e.getSource() == showDemoButton){
		playPauseButton.setEnabled(true);

		// escape from EDT
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
			    String fileName = new String();
			    
			    if (filePath.length() == 13) {
				fileName = filePath;
			    }
			    else if (filePath.length() > 13) {
				fileName = filePath.substring(filePath.length() - 13);
			    }

			    if(fileName.compareTo("lockedset.txt") != 0){
				theModel.setCurrentStepStatusLabel("Please make sure you are running the lockedset.txt demo file");
				if(DEBUG.getValue()){System.out.println("Please make sure you are running the lockedset.txt demo file");}
				return;
			    }

			    if(!selectButton.isEnabled()){
				theModel.setCurrentStepStatusLabel("Please make sure you deselect first");
				if(DEBUG.getValue()){System.out.println("Please make sure you deselect first");}
				
				return;
			    }

			    theModel.isDemoPlaying = true;
			    
			    theModel.setCurrentStepStatusLabel("Started running demo");
			    if(DEBUG.getValue()){System.out.println("Started running demo");}

			    allDifferentButton.setEnabled(false);
			    deselectButton.setEnabled(false);
			    showDemoButton.setEnabled(false);
			    
			    // sequence of steps to show the demo
			    theModel.select(0, 1); // 1st, type 1 for column
			    theModel.allDifferent();
			    theModel.deselect();
			    
			    theModel.select(4, 0); // 5th, type 0 for row
			    theModel.allDifferent();
			    theModel.deselect();
			    
			    theModel.select(5, 0); // 6th, type 0 for row
			    theModel.allDifferent();
			    theModel.deselect();
			    
			    theModel.select(3, 2); // 4th, type 2 for block
			    theModel.allDifferent();
			    theModel.deselect();
			    
			    theModel.select(0, 1); // 1st, type 1 for column
			    theModel.allDifferent();
			    
			    theModel.fadeInGridNow();
			    
			    deselectButton.setEnabled(true);

			    theModel.isDemoPlaying = false;
						    
			    theModel.setCurrentStepStatusLabel("Finished running demo");
			    if(DEBUG.getValue()){System.out.println("Finished running demo");}
			    
			}});
		
		th.start();
	    }
	}
    }
    
}
