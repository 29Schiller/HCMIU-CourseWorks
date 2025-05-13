import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.*;
import org.chocosolver.solver.constraints.*;

public class SudokuModel {
    public MutableBoolean DEBUG;

    public Object animationLock = new Object();
    
    public SudokuGrid theGrid;
    public int SIZE;
    public int SLEEP;
    public int SLEEP_BETWEEN_STEPS = 3000;

    private static Stack<SudokuWorld> worldStack = new Stack<SudokuWorld>();
    
    private Solver solver = new Solver("sudoku");
    private IntVar[][] rows = VariableFactory.enumeratedMatrix("rows", 9, 9, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, solver);

    ArrayList<ArrayList<Timer>> timers = new ArrayList<ArrayList<Timer>>();
    
    public Timer lastRunningTimer;
    public boolean isAnimationPaused = false;

    public ViewController viewController;

    public static final Object WAIT_FOR_TIMER = new Object();

    public Sudoku referenceToMain;

    public boolean propagateAfterAllDifferent = false;
    public boolean runAllDifferentOnSelection = false;
    public boolean deselectAfterAllDifferent = false;
    public boolean solveInSteps = false;

    public boolean isDemoPlaying = false;

    public SudokuModel(){
	worldStack = new Stack<SudokuWorld>();
    }
    
    public SudokuModel(Sudoku referenceToMain){
	worldStack = new Stack<SudokuWorld>();

	this.referenceToMain = referenceToMain;
    }

    public void worldPush(SudokuWorld world){
	worldStack.push(world);
	redraw();
    }

    public void worldPop(){
	worldStack.pop();
	redraw();
    }

    public SudokuWorld worldPeek(){
	return worldStack.peek();
    }

    public void setGrid(SudokuGrid theGrid){
	this.theGrid = theGrid;
    }

    static boolean[][] differentThan (Var x, Var y){
	int n = x.domain.size();
	int m = y.domain.size();
	boolean[][] differentThan = new boolean[n][m];
	for (int i=0;i<n;i++)
	    for (int j=0;j<m;j++)
		differentThan[i][j] = i!=j;
	return differentThan;
    } // x != y

    public boolean propagate(){
	SudokuWorld world = worldPeek();
	ArrayList<Integer> ij1;
	ArrayList<Integer> ij2;
	
	ArrayList<Constraint> constraints = worldStack.peek().constraints;

	setCurrentStepStatusLabel("Propagating");
	if(DEBUG.getValue()){System.out.println("\n\tPropagating");}
	    
	boolean consistent = true;
	Stack<Constraint> S = new Stack<Constraint>();
	for (Constraint c : constraints){ // add all the constraints on the stack
	    S.push(c); c.flag = true;
	}
	
	while (consistent && !S.isEmpty()){
	    Constraint c = S.pop();
	    c.flag = false;

	    if (c.revise()){
		redraw();
		consistent = c.v1.domain.cardinality() > 0;
		for (Constraint cv1 : c.v1.constraints){
		    if (!cv1.flag){
			S.push(cv1); cv1.flag = true;
		    }
		}
	    }
	}

	setCurrentStepStatusLabel("Finished propagating");
	if(DEBUG.getValue()){System.out.println("\n\tFinished propagating");}
	    
	return consistent;
    }

    public void scheduleSolveFF(){
	System.out.println("Is scheduleSolve EDT? " + SwingUtilities.isEventDispatchThread());	

	ArrayList<Timer> timers81 = new ArrayList<Timer>();

	Timer timerSolveGraph = viewController.theEdges.getTimerSolve();
	timers81.add(timerSolveGraph);
	timerSolveGraph.start();

	timers.add(timers81);
	
	if(timers.size() == 1){ // if this is the only timer, start it
	    // otherwise let another timer start this one when it finishes
	    for(Timer t:timers.get(0)){
		t.start();
	    }
	} 
    }

    public SudokuModel super_getThis(){
	return this;
    }

    public boolean allDifferent(){
	ifNotAnimatingThenWait();

	referenceToMain.allDifferentButton.setEnabled(false);
	referenceToMain.deselectButton.setEnabled(false);
	
	setCurrentStepStatusLabel("Started running the all-different implementation");
	if(DEBUG.getValue()){System.out.println("Started running the all-different implementation");}

	int edges = 0;
	int n = 20; // we have 20 vertices
	int[][] A = new int[n][n];
	int[][] R = new int[n][n];
	
	for(int i=0; i<9; i++){
	    //edges from source to vars (where each var represents a sudoku cell)
	    edges++;
	    A[0][i+1] = 1;
	    R[0][i+1] = 1;
	}
	
	for(int i=0; i<9; i++){
	    //edges from values (9 circles with one number on them) to sink
	    edges++;
	    A[i + 9 + 1][19] = 1;
	    R[i + 9 + 1][19] = 1;
	}
	
	for(int i=0; i<9; i++){
	    for(int j=0; j< 9; j++){
		if(theGrid.sudokuCells3Now[i] == null){
		    return false;
		}

		// from VAR i to VALUE j
		int shown_x = theGrid.sudokuCells3Now[i].i;
		int shown_y = theGrid.sudokuCells3Now[i].j;

		int capacity = 0;
		if(worldPeek().grid[shown_x][shown_y].hasValue(j + 1)){
		    capacity = 1;
		}
		
		edges++;
		A[i+1][j + 9 + 1] = capacity;
		R[i+1][j + 9 + 1] = capacity;
	    }
	}

	ifNotAnimatingThenWait();

	setCurrentStepStatusLabel("Step 1 - Ford Fulkerson - started");
	if(DEBUG.getValue()){System.out.println("Step 1 - Ford Fulkerson - started");}

	FordFulkerson ff = new FordFulkerson(super_getThis(), A, R, 20);
	ff.DEBUG = DEBUG;
	ff.run();

	viewController.theEdges.drawMoves();
			
	viewController.theEdges.finishDrawing(false); // hide unmatched paths
	ifNotAnimatingThenWait(); 
			
	viewController.theGrid.finishDrawing(); // hide S/T + S/T edges
	ifNotAnimatingThenWait(); 

	setCurrentStepStatusLabel("Step 1 - Ford Fulkerson - finished");
	if(DEBUG.getValue()){System.out.println("Step 1 - Ford Fulkerson - finished");}
	
	if(solveInSteps){pauseAnimation();}
	ifNotAnimatingThenWait();

	int newA[][] = new int[20][20];
	int newA2[][] = new int[18][18];

	for(int i=0; i<20; i++){
	    for(int j=0; j<20; j++){
		if(i > 0 && i < 10 && A[i][j] == 1 && A[i][j] * R[j][i] == 1){
		    setCurrentStepStatusLabel("Edge i: "+(i)+" j:"+(j));
		    if(DEBUG.getValue()){System.out.println("\tEdge i: "+(i)+" j:"+(j));}
		    newA[i][j] = 1;
		}
	    }
	}

	for(int i=0; i<18; i++){
	    for(int j=0; j<18; j++){
		newA2[i][j] = A[i+1][j+1];
	    }
	}
	
	for(int i=0; i<18; i++){
	    for(int j=0; j<18; j++){
		if(newA[i+1][j+1] == 1){
		    newA2[i][j] = 0;
		    newA2[j][i] = 1;
		}
	    }
	}
	
	if(SLEEP_BETWEEN_STEPS > 0){
	    try{
		Thread.sleep(SLEEP_BETWEEN_STEPS);
	    } catch (Exception e){
		e.printStackTrace();
	    }
	}

	viewController.theEdges.makeAllEdgesGray();

	ifNotAnimatingThenWait();


	setCurrentStepStatusLabel("Step 2 - Tarjan -  started");
	if(DEBUG.getValue()){System.out.println("Step 2 - Tarjan -  started");}
	
	Tarjan tarjan = new Tarjan(super_getThis(), newA2, 18);
	tarjan.DEBUG = DEBUG;
	tarjan.run();

	setCurrentStepStatusLabel("Step 2 - Tarjan - finished");
	if(DEBUG.getValue()){System.out.println("Step 2 - Tarjan - finished");}

	setCurrentStepStatusLabel("Step 3 - Apply knowledge after running Tartajn - started");
	if(DEBUG.getValue()){System.out.println("Step 3 - Apply knowledge after running Tartajn - started");}
	
	int newA3[][] = new int[18][18];
	
	for(int u=0; u<18; u++){
	    for(int v=0; v<18; v++){
		if(newA2[u][v] == 0){
		    continue;
		}
		
		int start_component_id = tarjan.id(u);
		int finish_component_id = tarjan.id(v);
		
		if(start_component_id != finish_component_id){
		    setCurrentStepStatusLabel("Considering u: " + u + " v: " + v + " start comp: " + start_component_id + " finish comp: " + finish_component_id);
		    if(DEBUG.getValue()){System.out.println("\tConsidering u: " + u + " v: " + v + " start comp: " + start_component_id + " finish comp: " + finish_component_id);}
		    newA3[u][v] = 1;
		}
	    }
	}

	if(DEBUG.getValue()){System.out.println();}
	setCurrentStepStatusLabel("Starting coloring edges in blue & red");
	if(DEBUG.getValue()){System.out.println("\tStarting coloring edges in blue & red");}

	for(int u=0; u<18; u++){
	    for(int v=0; v<18; v++){
		if(newA3[u][v] == 0){
		    continue;
		}

		if(u<9){
		    setCurrentStepStatusLabel("Color red for edge u: " + u + " v:" + v + " (cell: " + u + " to number: " + (v-8) + ")");
		    if(DEBUG.getValue()){System.out.println("\t\tColor red for edge u: " + u + " v:" + v + " (cell: " + u + " to number: " + (v-8) + ")");}
				    
		    int shown_x = theGrid.sudokuCells3Before[u].i;
		    int shown_y = theGrid.sudokuCells3Before[u].j;

		    viewController.theEdges.edgeColors[u+1][v+1] = Color.RED;
				    
		} else if(u>8){
		    setCurrentStepStatusLabel("Color blue for edge v: " + v + " u:" + u + " (cell: " + v + " to number: " + (u-8) + ")");
		    if(DEBUG.getValue()){System.out.println("\t\tColor blue for edge v: " + v + " u:" + u + " (cell: " + v + " to number: " + (u-8) + ")");}  // switch
				    
		    int shown_x = theGrid.sudokuCells3Before[v].i;
		    int shown_y = theGrid.sudokuCells3Before[v].j;

		    viewController.theEdges.edgeColors[v+1][u+1] = Color.BLUE;
				    
		}
	    }
	}
			
	viewController.theEdges.repaint();
	setCurrentStepStatusLabel("Finished coloring edges in blue & red");
	if(DEBUG.getValue()){System.out.println("\tFinished coloring edges in blue & red");}
	
	if(SLEEP_BETWEEN_STEPS > 0){
	    try{
		if(DEBUG.getValue()){System.out.println("\n\tSleeping between steps for " + SLEEP_BETWEEN_STEPS);}
		Thread.sleep(SLEEP_BETWEEN_STEPS);
	    } catch (Exception e){
		e.printStackTrace();
	    }
	}

	if(solveInSteps){pauseAnimation();}
	ifNotAnimatingThenWait();
		
	if(DEBUG.getValue()){System.out.println();}
	setCurrentStepStatusLabel("Start applying results in the original grid");
	if(DEBUG.getValue()){System.out.println("\tStart applying results in the original grid");}
	for(int u=0; u<18; u++){
	    for(int v=0; v<18; v++){
		if(newA3[u][v] == 0){
		    continue;
		}

		ifNotAnimatingThenWait();
				
		if(u<9){
		    setCurrentStepStatusLabel("Remove value u: " + u + " v:" + v + " (cell: " + u + " to number: " + (v-8) + ")");
		    if(DEBUG.getValue()){System.out.println("\t\tRemove value u: " + u + " v:" + v + " (cell: " + u + " to number: " + (v-8) + ")");}
				    
		    int shown_x = theGrid.sudokuCells3Before[u].i;
		    int shown_y = theGrid.sudokuCells3Before[u].j;
				    
		    // delete from sudoku
		    worldPeek().grid[shown_x][shown_y].eliminateValue(v - (9-1));
				    
		    theGrid.sudokuCells3Now[u].setValuesLabel(theGrid.sudokuCells3Now[u].formatPossibleValues());
		    theGrid.sudokuCells3Before[u].setValuesLabel(theGrid.sudokuCells3Before[u].formatPossibleValues());
				    
		} else if(u>8){
		    setCurrentStepStatusLabel("Assign value v: " + v + " u:" + u + " (cell: " + v + " to number: " + (u-8) + ")");
		    if(DEBUG.getValue()){System.out.println("\t\tAssign value v: " + v + " u:" + u + " (cell: " + v + " to number: " + (u-8) + ")");} // switch
				    
		    int shown_x = theGrid.sudokuCells3Before[v].i;
		    int shown_y = theGrid.sudokuCells3Before[v].j;
				    
		    worldPeek().grid[shown_x][shown_y].setValue(u - (9-1));
				    
		    theGrid.sudokuCells3Now[v].setValuesLabel(theGrid.sudokuCells3Now[v].formatPossibleValues());
		    theGrid.sudokuCells3Before[v].setValuesLabel(theGrid.sudokuCells3Before[v].formatPossibleValues());
				    
		}
	    }
	}
			
	setCurrentStepStatusLabel("Finished applying results in the original grid");
	if(DEBUG.getValue()){System.out.println("\tFinished applying results in the original grid");}

	setCurrentStepStatusLabel("Step 3 - Apply knowledge after running Tarjan - finished");
	if(DEBUG.getValue()){System.out.println("Step 3 - Apply knowledge after running Tarjan - finished");}

	if(SLEEP_BETWEEN_STEPS > 0){
	    try{
		if(DEBUG.getValue()){System.out.println("\n\tSleeping between steps for " + SLEEP_BETWEEN_STEPS);}
		Thread.sleep(SLEEP_BETWEEN_STEPS);
	    } catch (Exception e){
		e.printStackTrace();
	    }
	}

	setCurrentStepStatusLabel("Finished running the all-different implementation");
	if(DEBUG.getValue()){System.out.println("Finished running the all-different implementation");}

	if(solveInSteps){pauseAnimation();}
	ifNotAnimatingThenWait();
		
	if(propagateAfterAllDifferent){
	    //sleep between steps only if actually having a propagation step
	    if(SLEEP_BETWEEN_STEPS > 0){
		try{
		    if(DEBUG.getValue()){System.out.println("\n\tSleeping between steps for " + SLEEP_BETWEEN_STEPS);}
		    Thread.sleep(SLEEP_BETWEEN_STEPS);
		} catch (Exception e){
		    e.printStackTrace();
		}
	    }

	    propagate();

	    if(solveInSteps){pauseAnimation();}
	    ifNotAnimatingThenWait();
	}

	if(SLEEP_BETWEEN_STEPS > 0){
	    try{
		if(DEBUG.getValue()){System.out.println("\n\tSleeping between steps for " + SLEEP_BETWEEN_STEPS);}
		Thread.sleep(SLEEP_BETWEEN_STEPS);
	    } catch (Exception e){
		e.printStackTrace();
	    }
	} else {
	    // keep this here, as this is the last step
	    try{
		if(DEBUG.getValue()){System.out.println("\n\tSleeping between steps for " + SLEEP_BETWEEN_STEPS);}
		Thread.sleep(SLEEP_BETWEEN_STEPS + 1000);
	    } catch (Exception e){
		e.printStackTrace();
	    }
	}

	referenceToMain.deselectButton.setEnabled(true);
		    
	return true; 
    }
    
    public boolean solveUsingBacktracking(SudokuWorld sw){
	for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++){
		if (sw.grid[i][j].getValue() != -1) {
		    continue;
		}
		
		for (int num = 1; num <= SIZE; num++) {
		    SudokuWorld newWorld = new SudokuWorld(worldStack.peek());
		    newWorld.grid[i][j].setValue(num);
		    worldPush(newWorld);
		    
		    if (propagate()){
			if (solveUsingBacktracking(newWorld)) {
			    return true;
			} else {
			    worldPop();
			}
		    } else {
			worldPop();
		    }
		}
		
		return false;
	    }
	}
	
	return true;
    }

    public void solveUsingChoco3() {
	redraw();
	postConstraintsChoco3(solver);
	redraw();

	solver.findSolution();
	saveChoco3ToModel();
    }

    public void saveChoco3ToModel(){
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		IntVar[] row = rows[i];
		IntVar col = row[j];

		// delete all known values and set them again
		// with the data from choco3
		worldStack.peek().grid[i][j].eliminateAll();
		
		for(int k=0; k<SIZE; k++){
		    if(col.contains(k+1)){
			worldStack.peek().grid[i][j].addValue(k+1);
		    }
		}
	    }
	}
    }

    public void postConstraints(){
	SudokuWorld world =  worldStack.peek();
	
	// Not equal constraint between each case of a row
	for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++) {
		for (int k = j; k < SIZE; k++) {
		    if (k != j) {
			Constraint c = new Constraint(world.grid[i][j], differentThan(world.grid[i][j], world.grid[i][k]), world.grid[i][k], ""+i+""+j+"!="+i+""+k);
			world.constraints.add(c);

			Constraint c1 = new Constraint(world.grid[i][k], differentThan(world.grid[i][k], world.grid[i][j]), world.grid[i][j], ""+i+""+k+"!="+i+""+j);
			world.constraints.add(c1);
		    }
		}
	    }
	}
    
	// Not equal constraint between each case of a column
	for (int j = 0; j < SIZE; j++) {
	    for (int i = 0; i < SIZE; i++) {
		for (int k = 0; k < SIZE; k++) {
		    if (k != i) {
			Constraint c = new Constraint(world.grid[i][j], differentThan(world.grid[i][j], world.grid[k][j]), world.grid[k][j], ""+i+""+j+"!="+k+""+j);
			world.constraints.add(c);

			Constraint c1 = new Constraint(world.grid[k][j], differentThan(world.grid[k][j], world.grid[i][j]), world.grid[i][j], ""+k+""+j+"!="+i+""+j);
			world.constraints.add(c1);
		    }
		}
	    }
	}
	
	// Not equal constraint between each case of a sub region
	for (int ci = 0; ci < SIZE; ci += 3) {
	    for (int cj = 0; cj < SIZE; cj += 3) {
		// Extraction of disequality of a sub region
		for (int i = ci; i < ci + 3; i++) {
		    for (int j = cj; j < cj + 3; j++) {
			for (int k = ci; k < ci + 3; k++) {
			    for (int l = cj; l < cj + 3; l++) {
				if (k != i || l != j) {
				    Constraint c = new Constraint(world.grid[i][j], differentThan(world.grid[i][j], world.grid[k][l]), world.grid[k][l], ""+i+""+j+"!="+k+""+l);
				    world.constraints.add(c);

				    Constraint c1 = new Constraint(world.grid[k][l], differentThan(world.grid[k][l], world.grid[i][j]), world.grid[i][j], ""+k+""+l+"!="+i+""+j);
				    world.constraints.add(c1);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    public void postConstraintsChoco3(Solver solver){
	int index = 0;
	
	// Row constraints
	for (int i = 0; i < SIZE; i++) {
	    solver.post(ICF.alldifferent(rows[i],"AC"));
	}
	
	// Column constraints
	IntVar[] columnVars = new IntVar[9];
	index = 0;
	
	for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++) {
		columnVars[index] = rows[j][i];
		index++;
	    }
	    solver.post(ICF.alldifferent(columnVars,"AC"));
	    columnVars = new IntVar[9];
	    index = 0;
	}
	
	// Block constraints
	IntVar[] blockVars = new IntVar[9];
	index = 0;
	
	for(int i=0; i<SIZE; i+=3){
	    for(int j=0; j<SIZE; j+=3){
		for(int k=i; k<i+3; k++){
		    for(int l=j; l<j+3; l++){
			blockVars[index] = rows[k][l];
			index++;
		    }
		}
		solver.post(ICF.alldifferent(blockVars, "AC"));
		blockVars = new IntVar[9];
		index = 0;
	    }
	}
	
	// Populate choco3 with the given values
	for(int i=0; i<SIZE; i++){
	    for(int j=0; j<SIZE; j++){
		if(worldPeek().grid[i][j].getValue() != -1){
		    try{
			rows[i][j].instantiateTo(worldPeek().grid[i][j].getValue(),null);
		    } catch (Exception e){
			e.printStackTrace();
		    }
		}
	    }
	}
    }

    public SudokuWorld readFromFile(String fileName) throws FileNotFoundException {
	solver = new Solver("sudoku");
	rows = VariableFactory.enumeratedMatrix("rows", 9, 9, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, solver);

	SudokuWorld world = new SudokuWorld();
	
	Scanner sc = new Scanner(new FileReader(fileName));
	
	for(int i = 0; i < 9; i++) {
	    if (sc.hasNextLine()) {
		for (int j = 0; j < 9; j++) {
		    if (sc.hasNextInt()) {
			int value = sc.nextInt();
			if(value != 0){
			    world.grid[i][j].setValue(value);
			    world.grid[i][j].setName(i+""+j);
			}
		    }
		}
	    }
	}

	sc.close();

	worldPush(world);
	redraw();

	postConstraints();
	
	return world;
    }

    public void redraw(){
	// Separate loop so we can just redraw everything after knowing the values
	
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		this.theGrid.sudokuCells[i][j].setValuesLabel(this.theGrid.sudokuCells[i][j].formatPossibleValues());
		if(this.theGrid.sudokuCells2[i][j] != null){
		    this.theGrid.sudokuCells2[i][j].setValuesLabel(this.theGrid.sudokuCells2[i][j].formatPossibleValues());
		}
	    }
	}

	///////////////////////////////////////
	try {
	    Thread.sleep(SLEEP);
	} catch(InterruptedException ex) {
	    Thread.currentThread().interrupt();
	}
	///////////////////////////////////////
    }

    public ArrayList<Timer> fadeOutAllExceptRow(int row){
	ArrayList<Timer> timers81 = new ArrayList<Timer>();

	viewController.hideWelcomeScreen();
	
	// wait for it to finish
	for(int i=0; i<9; i++){
	    if(i == row){
		// skip the row (it was an exception from fading out)
		continue;
	    }
	    for(int j=0; j<9; j++){
		Timer timerFadeOut = theGrid.sudokuCells[i][j].getTimerFadeOut();
		timers81.add(timerFadeOut);
		theGrid.sudokuCells[i][j].fadeOutALittle();
		timerFadeOut.start();
	    }
	}

	//////// adding the circles
	for(int i = 0; i <11; i++){
	    Timer timerFadeOut = theGrid.valueCircles[i].getTimerFadeOut();
	    timers81.add(timerFadeOut);
	    theGrid.valueCircles[i].fadeOut();
	    timerFadeOut.start();
	}
	//////// end adding the circles

	/////// adding lines
	Timer timerFadeOut = viewController.theEdges.getTimerFadeOut();
	timers81.add(timerFadeOut);
	viewController.theEdges.fadeOut();
	timerFadeOut.start();
	/////// end adding the lines

	timers.add(timers81);
	
	if(timers.size() == 1){ // if this is the only timer, start it
	    // otherwise let another timer start this one when it finishes
	    for(Timer t:timers.get(0)){
		t.start();
	    }
	}

	return timers81;
    }

    public ArrayList<Timer> fadeOutAllExceptColumn(int column){
	ArrayList<Timer> timers81 = new ArrayList<Timer>();

	viewController.hideWelcomeScreen();
	
	// wait for it to finish
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		if(j == column){
		    // skip the column (it was an exception from fading out)
		    continue;
		}
		Timer timerFadeOut = theGrid.sudokuCells[i][j].getTimerFadeOut();
		timers81.add(timerFadeOut); //timer 1 = fadeout
		theGrid.sudokuCells[i][j].fadeOutALittle();
		timerFadeOut.start();
	    }
	}

	//////// adding the circles
	for(int i = 0; i <11; i++){
	    Timer timerFadeOut = theGrid.valueCircles[i].getTimerFadeOut();
	    timers81.add(timerFadeOut);
	    theGrid.valueCircles[i].fadeOut();
	    timerFadeOut.start();
	}
	//////// end adding the circles

	/////// adding lines
	Timer timerFadeOut = viewController.theEdges.getTimerFadeOut();
	timers81.add(timerFadeOut);
	viewController.theEdges.fadeOut();
	timerFadeOut.start();
	/////// end adding the lines
	
	timers.add(timers81);
	
	if(timers.size() == 1){ // if this is the only timer, start it
	    // otherwise let another timer start this one when it finishes
	    for(Timer t:timers.get(0)){
		t.start();
	    }
	}

	return timers81;
    }

    public ArrayList<Timer> fadeOutAllExceptBlock(int block){
	ArrayList<Timer> timers81 = new ArrayList<Timer>();

	viewController.hideWelcomeScreen();

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
	
	// wait for it to finish
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		if((y<=i && i<y+3) && (x<=j && j<x+3)){
		    // skip the block (it was an exception from fading out)
		    continue;
		}

		Timer timerFadeOut = theGrid.sudokuCells[i][j].getTimerFadeOut();
		timers81.add(timerFadeOut); //timer 1 = fadeout
		theGrid.sudokuCells[i][j].fadeOutALittle();
		timerFadeOut.start();
	    }
	}

	//////// adding the circles
	for(int i = 0; i <11; i++){
	    Timer timerFadeOut = theGrid.valueCircles[i].getTimerFadeOut();
	    timers81.add(timerFadeOut);
	    theGrid.valueCircles[i].fadeOut();
	    timerFadeOut.start();
	}
	//////// end adding the circles

	/////// adding lines
	Timer timerFadeOut = viewController.theEdges.getTimerFadeOut();
	timers81.add(timerFadeOut);
	viewController.theEdges.fadeOut();
	timerFadeOut.start();
	/////// end adding the lines

	timers.add(timers81);
	
	if(timers.size() == 1){ // if this is the only timer, start it
	    // otherwise let another timer start this one when it finishes
	    for(Timer t:timers.get(0)){
		t.start();
	    }
	}

	return timers81;
    }    

    public ArrayList<Timer> fadeIn(){
	ArrayList<Timer> timers81 = new ArrayList<Timer>();
	
	// wait for it to finish
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		Timer timerFadeIn = theGrid.sudokuCells[i][j].getTimerFadeIn();
		timers81.add(timerFadeIn);
		theGrid.sudokuCells[i][j].fadeIn();
		timerFadeIn.start();
	    }
	}

	//////// adding the circles
	for(int i = 0; i <11; i++){
	    Timer timerFadeIn = theGrid.valueCircles[i].getTimerFadeIn();
	    timers81.add(timerFadeIn);
	    theGrid.valueCircles[i].fadeIn();
	    timerFadeIn.start();
	}
	//////// end adding the circles
	
	/////// adding lines
	Timer timerFadeIn = viewController.theEdges.getTimerFadeIn();
	timers81.add(timerFadeIn);
	viewController.theEdges.fadeIn();
	timerFadeIn.start();
	/////// end adding the lines

	timers.add(timers81);
	
	if(timers.size() == 1){ // if this is the only timer, start it
	    // otherwise let another timer start this one when it finishes
	    for(Timer t:timers.get(0)){
		t.start();
	    }
	}

	return timers81;
    }

    public void fadeInGridNow(){
	ifNotAnimatingThenWait();
	
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		theGrid.sudokuCells[i][j].setAlphaOne();
	    }
	}
    }
    
    public ArrayList<Timer> fadeInGraph(){
	ArrayList<Timer> timers81 = new ArrayList<Timer>();

	//////// adding the circles
	for(int i = 0; i <11; i++){
	    Timer timerFadeIn = theGrid.valueCircles[i].getTimerFadeIn();
	    timers81.add(timerFadeIn);
	    theGrid.valueCircles[i].fadeIn();
	    timerFadeIn.start();
	}
	//////// end adding the circles
	
	/////// adding lines
	Timer timerFadeIn = viewController.theEdges.getTimerFadeIn();
	timers81.add(timerFadeIn);
	viewController.theEdges.fadeIn();
	timerFadeIn.start();
	/////// end adding the lines

	timers.add(timers81);
	
	if(timers.size() == 1){ // if this is the only timer, start it
	    // otherwise let another timer start this one when it finishes
	    for(Timer t:timers.get(0)){
		t.start();
	    }
	}

	return timers81;
    }

    public void fadeInGraphNow(){
	for(int i = 0; i <11; i++){
	    theGrid.valueCircles[i].setAlphaOne();
	}
	
	viewController.theEdges.setAlphaOne();
    }
    
    public ArrayList<Integer> getPossibleValues(int i, int j){
	ArrayList<Integer> possibleValues = new ArrayList<Integer>();
	if(worldPeek() != null){
	    if(worldPeek().grid[i][j] != null){
		possibleValues = worldPeek().grid[i][j].getPossibleValues();
	    }
	}
	
	return possibleValues;
    }

    public void stopAllTimersOnDiffLevelComparedTo(Timer t){
	for(ArrayList<Timer> sameTimeTimers:timers){
	    if(!sameTimeTimers.contains(t)){
		for(Timer t2:sameTimeTimers){
		    t2.stop();
		}
	    }
	}
    }

    public void removeTimer(Timer t){
	for(ArrayList<Timer> sameTimeTimers:timers){
	    if(sameTimeTimers.contains(t)){
		sameTimeTimers.remove(t);
	    }
	}
    }

    public void startNextTimers(){
	// First, clear the empty levels

	ArrayList<ArrayList<Timer>> valuesToRemove = new ArrayList<ArrayList<Timer>>();
	
	Iterator<ArrayList<Timer>> it = timers.iterator();
	while (it.hasNext()) {
	    ArrayList<Timer> sameTimeTimers = it.next(); 
	    if (sameTimeTimers.isEmpty()) {
		valuesToRemove.add(sameTimeTimers);
	    }
	}

	timers.removeAll(valuesToRemove);

	for(ArrayList<Timer> sameTimeTimers : valuesToRemove){
	    synchronized (sameTimeTimers) {
		sameTimeTimers.notifyAll();
	    }
	}

	if(valuesToRemove.size() == 0){
	    // we have removed a fading cell for example, but others still exist
	    // i.e. the level still has something
	    return;
	}

	if(!timers.isEmpty()){
	    for(Timer t:timers.get(0)){
		t.start();
	    }
	}
    }

    public void nextAnimation(){
	isAnimationPaused = false;
	synchronized(animationLock){
	    animationLock.notify();
	}

	// Play the concurrentTimers that contain the last running timer that was paused
	System.out.println("Next step play");
	for(ArrayList<Timer> concurrentTimers:timers){
	    if(concurrentTimers.contains(lastRunningTimer)){
		for(Timer t:concurrentTimers){
		    t.start();
		}
	    }
	}
    }
    
    public void pauseAnimation(){		
	isAnimationPaused = true;

	// Pause the concurrentTimers that contain the last running timer
	System.out.println("Pause");
	for(ArrayList<Timer> concurrentTimers:timers){
	    if(concurrentTimers.contains(lastRunningTimer)){
		for(Timer t:concurrentTimers){
		    t.stop();
		}
	    }
	}
    }

    public void playAnimation(){
	isAnimationPaused = false;
	synchronized(animationLock){
	    animationLock.notify();
	}

	// Play the concurrentTimers that contain the last running timer that was paused
	System.out.println("Play");
	for(ArrayList<Timer> concurrentTimers:timers){
	    if(concurrentTimers.contains(lastRunningTimer)){
		for(Timer t:concurrentTimers){
		    t.start();
		}
	    }
	}
    }

    public void ifNotAnimatingThenWait(){
	if(isAnimationPaused){
	    synchronized(animationLock){
		try{
		    animationLock.wait();
		} catch (Exception e){
		    e.printStackTrace();
		}
	    }
	}
    }

    public ArrayList<Timer> moveColumn(int column, boolean movingToTheRight){
	return theGrid.moveColumn(column, movingToTheRight);
    }

    public ArrayList<Timer> moveRow(int row, boolean movingToTheRight){
	return theGrid.moveRow(row, movingToTheRight);
    }

    public ArrayList<Timer> moveBlock(int block, boolean movingToTheRight){
	return theGrid.moveBlock(block, movingToTheRight);
    }

    public void setViewController(ViewController vc){
	this.viewController = vc;
    }

    public void select(int number, int selectionType){
	ifNotAnimatingThenWait();

	if(!referenceToMain.selectButton.isEnabled()){
	    setCurrentStepStatusLabel("Please make sure you deselect first");
	    if(DEBUG.getValue()){System.out.println("Please make sure you deselect first");}

	    return;
	}
		
	referenceToMain.selectButton.setEnabled(false);
	
	ArrayList<Timer> sameLevelTimers = new ArrayList<Timer>();
	
	if(selectionType == 0){ //row
	    sameLevelTimers = fadeOutAllExceptRow(number);
	}
	
	if(selectionType == 1){ //column
	    sameLevelTimers = fadeOutAllExceptColumn(number);
	}
	
	if(selectionType == 2){ //block
	    sameLevelTimers = fadeOutAllExceptBlock(number);
	}

	ifNotAnimatingThenWait();

	synchronized (sameLevelTimers) {
	    try {
		sameLevelTimers.wait();
	    } catch (InterruptedException ex) {
	    }
	}

	setCurrentStepStatusLabel("Faded out before actually moving to the right");
	if(DEBUG.getValue()){System.out.println("Faded out before actually moving to the right");}
	

	if(selectionType == 0){ //row
	    sameLevelTimers = moveRow(number, true);
	}
	    
	if(selectionType == 1){ //column
	    sameLevelTimers = moveColumn(number, true);
	}
	    
	if(selectionType == 2){ //block
	    sameLevelTimers = moveBlock(number, true);
	}
	    
	ifNotAnimatingThenWait();
	
	synchronized (sameLevelTimers) {
	    try {
		sameLevelTimers.wait();
	    } catch (InterruptedException ex) {
	    }
	}

	for (int i = 0; i < 9; i++) {
	    for (int j = 0; j < 9; j++) {
		this.theGrid.sudokuCells[i][j].setOpaque(true);
	    }
	}
	
	setCurrentStepStatusLabel("Moving row, column or block to the right finished");
	if(DEBUG.getValue()){System.out.println("Moving row, column or block to the right finished");}

	viewController.theEdges.loadColorsFromModel();
		
	if(SLEEP == 0 && SLEEP_BETWEEN_STEPS == 0){
	    // notice that now doesn't return a timer level
	    // therefore don't synchronize below
	    fadeInGraphNow();
		
	} else {
	    sameLevelTimers = fadeInGraph();
	}
	    
	ifNotAnimatingThenWait();
	
	if(!(SLEEP == 0 && SLEEP_BETWEEN_STEPS == 0)){
	    // notice that now doesn't return a timer level
	    // therefore don't sync on it
	    synchronized (sameLevelTimers) {
		try {
		    sameLevelTimers.wait();
		} catch (InterruptedException ex) {
		}
	    }
	}

	setCurrentStepStatusLabel("Fading graph finished");
	if(DEBUG.getValue()){System.out.println("Fading graph finished");}

	if(!isDemoPlaying){
	    referenceToMain.allDifferentButton.setEnabled(true);
	    referenceToMain.deselectButton.setEnabled(true);
	}

	if(solveInSteps){pauseAnimation();}
	ifNotAnimatingThenWait();
    }

    public void deselect(){
	ifNotAnimatingThenWait();

	referenceToMain.deselectButton.setEnabled(false);
	referenceToMain.allDifferentButton.setEnabled(false);
	
	for (int i = 0; i < 9; i++) {
	    for (int j = 0; j < 9; j++) {
		this.theGrid.sudokuCells[i][j].setOpaque(false);
	    }
	}
	
	ArrayList<Timer> sameLevelTimers = new ArrayList<Timer>();
			
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		// initially all vars have no background with black text
		theGrid.sudokuCells2[i][j].setBackground(null);
		theGrid.sudokuCells2[i][j].setFontColor(Color.BLACK);
	    }
	}
			
	if(theGrid.lastSelectionWasRow){
	    sameLevelTimers = theGrid.moveRow(theGrid.lastSelectionNumber, false);
	}
			
	if(theGrid.lastSelectionWasColumn){
	    sameLevelTimers = theGrid.moveColumn(theGrid.lastSelectionNumber, false);
	}
			
	if(theGrid.lastSelectionWasBlock){
	    sameLevelTimers = theGrid.moveBlock(theGrid.lastSelectionNumber, false);
	}


	ifNotAnimatingThenWait();

	synchronized (sameLevelTimers) {
	    try {
		sameLevelTimers.wait();
	    } catch (InterruptedException ex) {
	    }
	}

	setCurrentStepStatusLabel("Moving row, column or block to the left finished");
	if(DEBUG.getValue()){System.out.println("Moving row, column or block to the left finished");}
	

	sameLevelTimers = fadeIn();

	ifNotAnimatingThenWait();
	
	synchronized (sameLevelTimers) {
	    try {
		sameLevelTimers.wait();
	    } catch (InterruptedException ex) {
	    }
	}

	setCurrentStepStatusLabel("Faded in after moving left");
	if(DEBUG.getValue()){System.out.println("Faded in after moving left");}
		    
	viewController.theEdges.setVisible(false);
	
	viewController.theEdges.edgeColors  = new Color[20][20];

	for(int i=0; i<20; i++){
	    for(int j=0; j<20; j++){
		//initially all edges are gray
		viewController.theEdges.edgeColors[i][j] = Color.LIGHT_GRAY;
	    }
	}
				    
	for(int i=1; i<10; i++){
	    // initially all circles have a black background with white text
	    theGrid.valueCircles[i].setCircleColor(Color.BLACK);
	    theGrid.valueCircles[i].setFontColor(Color.WHITE);
	}
				    
	viewController.theEdges.moves = new ArrayList<TripletIIB>();
	timers = new ArrayList<ArrayList<Timer>>();
	theGrid.removedCellGoingToTheLeft = new int[9];
				    
	viewController.theEdges.setVisible(true);

	setCurrentStepStatusLabel("Finished cleaning after moving left");
	if(DEBUG.getValue()){System.out.println("Finished cleaning after moving left");}

	referenceToMain.allDifferentButton.setEnabled(false);

	if(!isDemoPlaying){
	    referenceToMain.deselectButton.setEnabled(false);
	    referenceToMain.showDemoButton.setEnabled(true);
	}

	referenceToMain.selectButton.setEnabled(true);
    }

    public void setPropagateAfterAllDifferent(boolean value){
	propagateAfterAllDifferent = value;
    }

    public void setRunAllDifferentOnSelection(boolean value){
	runAllDifferentOnSelection = value;
    }

    public void setDeselectAfterAllDifferent(boolean value){
	deselectAfterAllDifferent = value;
    }

    public void setSolveInSteps(boolean value){
	solveInSteps = value;
    }
    
    public void setCurrentStepStatusLabel(String value){
	referenceToMain.currentStepStatusLabel.setText(value);
    }

    public static int getMaxFittingFontSize(String text, Font font, Component comp, int compWidth, int compHeight){	
	int stringWidth = comp.getFontMetrics(font).stringWidth(text);
	double widthRatio = (double)compWidth / (double)stringWidth;
	int newFontSize = (int)(font.getSize() * widthRatio);
	int fontSizeToUse = Math.min(newFontSize, compHeight);

	return (int)(fontSizeToUse / 1.75);
    }
}
