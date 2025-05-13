import java.util.*;
import java.awt.Color;

public class FordFulkerson {
    public MutableBoolean DEBUG;
    
    int[][] A;         // adjacency matrix, will be used only for displaying output
    int[][] R;         //  residual graph
    int n;             // order of graph
    int[] pred;        // predecessor of a vertex, describes augmented path
    boolean[] visited; // visited?, used in bfs
    Queue<Integer> Q;  // for bfs

    SudokuModel theModel;
    ArrayList<TripletIIB> moveSteps;

    boolean finishedGreedyMatch = false;
    int lastVarVisited = 0;

    int skippedTwoGreensCount = 0;
    
    FordFulkerson (SudokuModel theModel, int[][] A, int[][] R, int n){
	this.theModel = theModel;

	this.A = A;
	this.R = R;
	this.n = n;

	pred = new int[n];
	visited = new boolean[n];
	Q = new LinkedList<Integer>();
    } 

    boolean bfs(){
	Q.clear();
	Arrays.fill(pred,-1);
	Arrays.fill(visited,false);
	Q.add(0);
	visited[0] = true;
	
	while (!Q.isEmpty()){
	    int v = Q.remove();

	    if (v == n-1){
		return true; // path from source (0) to sink (n-1) found
	    }
	    
	    for (int w=0;w<n;w++){
		if (!visited[w] && R[v][w] > 0){
		    pred[w] = v;
		    Q.add(w);
		    visited[w] = true;
		}
	    }
	}
	
	return false;	
    }
    //
    // using breadth first search, find an augmenting path from source to sink
    // - returns true if augmenting path found
    // - augmenting path is traced backwards from sink to source via predecessor
    //   links in array pred
    //

    int minCost(){
	int minCost = Integer.MAX_VALUE;
	int v = n-1;
	while (pred[v] != -1){minCost = Math.min(minCost,R[pred[v]][v]); v = pred[v];}
	return minCost;
    }	
    //
    // return the smallest residual flow in the augmenting path found by bfs
    //

    void updateR(){
	int f = minCost();
	int v = n-1;

	moveSteps = new ArrayList<TripletIIB>();
	
	while (pred[v] != -1){
	    int u = pred[v];

	    // BEGIN GREEDY MATCH CODE
	    
	    if(u>0 && u<10){
		if((u < lastVarVisited && !finishedGreedyMatch) || u == 9){
		    finishedGreedyMatch = true;
		    theModel.setCurrentStepStatusLabel("Greedy match finished");
		    if(DEBUG.getValue()){System.out.println("\tGreedy match finished");}
		    theModel.viewController.theEdges.drawMovesNow();

		    if(theModel.solveInSteps){theModel.pauseAnimation();}
		    theModel.ifNotAnimatingThenWait();
		} else {
		    lastVarVisited = u;
		}
	    }

	    // END GREEDY MATCH CODE
	    
	    R[u][v] = R[u][v] - f; // residual capacity
	    R[v][u] = R[v][u] + f; // augmented flow
		    
	    if(u<v){
		if(finishedGreedyMatch && skippedTwoGreensCount>1){
		    moveSteps.add(new TripletIIB(u, v, Color.GREEN));
		}
	    } else {
		if(finishedGreedyMatch){
		    moveSteps.add(new TripletIIB(v, u, Color.RED));
		}
	    }

	    if(finishedGreedyMatch){
		skippedTwoGreensCount++;
	    }
	    
	    v = u;
	}

	Collections.reverse(moveSteps);
	theModel.viewController.theEdges.storeMoves(moveSteps);
    }
    //
    // having computed an augmenting path via bfs update flows and residues
    //

    void fordFulkerson(){
	while (bfs()){
	    if(finishedGreedyMatch){
		theModel.viewController.theEdges.drawPath(displayPath());
	    } else {
		theModel.viewController.theEdges.drawPathNow(displayPath());
	    }

	    updateR();
	}
    }
    //
    // while there is an augmenting path (bfs is true and path is in the array pred) 
    // update the residual graph
    //

    ArrayList<Integer> displayPath(){
        ArrayList<Integer> path = new ArrayList<Integer>();
	
	int i = n-1;
	while (i != -1){
	    path.add(i);
	    i = pred[i];
	}

	Collections.reverse(path);

	return path;
    }
    //
    // display augmenting path held in pred array
    //
    
    public void run() {
	this.fordFulkerson();
    }
}
