import java.util.*;

public class SudokuWorld{
    public Var[][] grid = new Var[9][9];
    public ArrayList<Constraint> constraints = new ArrayList<Constraint>();
    public SudokuGrid sudokuGrid;
    
    SudokuWorld(){
	for(int i = 0; i<9; i++){
	    for(int j=0; j<9; j++){
		this.grid[i][j] = new Var(i + "" + j, 9);
	    }
	}
    }

    SudokuWorld(Var[][] grid){
	this.grid = grid;
    }

    SudokuWorld(SudokuWorld oldSW){
	// make the grid
        this();

	// link the same old GUI
	this.sudokuGrid = oldSW.sudokuGrid;

	// instantiate the grid with the old values
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		this.grid[i][j].domain = (BitSet)oldSW.grid[i][j].domain.clone();
	    }
	}

	// add the constraints
	for(Constraint c  : oldSW.constraints){
	    this.constraints.add(new Constraint(this, c));
	}
	
    }

    public Var getVarByName(String name){
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		if(this.grid[i][j].name.compareTo(name) == 0){
		    return this.grid[i][j];
		}
	    }
	}

	return null;
    }

    public ArrayList<Integer> getIJFromVar(Var v){
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		if(grid[i][j] == v){
		    return new ArrayList<Integer>(Arrays.asList(new Integer[]{i, j}));
		}
	    }
	}

	return null;
    }
}
