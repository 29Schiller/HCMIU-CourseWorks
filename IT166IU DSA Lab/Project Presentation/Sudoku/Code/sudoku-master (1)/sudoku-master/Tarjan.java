import java.util.*;
import java.awt.Color;

public class Tarjan {
    public MutableBoolean DEBUG;
    
    SudokuModel theModel;
    int[][] A;         // adjacency matrix, will be used only for displaying output
    int n;             // number of vertices in the graph
    int index = 0;     // the index of the  current node discovered in the depth-first search
    int count = 0;

    Stack<Integer> S;  // stack for algo
    boolean[] stacked; // visited?, used in bfs
    
    int[] id, low;

    public ArrayList<Color> universityColors = new ArrayList<Color>();
    public ArrayList<Color> universityFontColors = new ArrayList<Color>();
    
    Tarjan (SudokuModel theModel, int[][] A, int n){
	this.theModel = theModel;

	// INIT UNIVERSITY COLORS
	// from http://www.gla.ac.uk/myglasgow/staff/brandguidelines/components/colourpalette/
	
	universityColors.add(new Color(132, 189, 0, 255)); // Lawn
	universityColors.add(new Color(0, 181, 209, 255)); // Turquoise
	universityColors.add(new Color(255, 220, 54, 255)); // Sunshine
	universityColors.add(new Color(255, 185, 72, 255)); // Pumpkin
	universityColors.add(new Color(149, 18, 114, 255)); // Thistle
	universityColors.add(new Color(179, 12, 0, 255)); // Pillarbox
	universityColors.add(new Color(91, 77, 148, 255)); // Lavender

	universityColors.add(new Color(154, 185, 173, 255)); // Aquamarine
	universityColors.add(new Color(79, 89, 97, 255)); // Slate
	universityColors.add(new Color(210, 120, 171, 255)); // Rose
	universityColors.add(new Color(181, 144, 121, 255)); // Mocha
	universityColors.add(new Color(0, 81, 51, 255)); // Forest
	universityColors.add(new Color(0, 157, 236, 255)); // Cobalt
	universityColors.add(new Color(125, 34, 57, 255)); // Burgundy
	universityColors.add(new Color(154, 58, 6, 255)); // Rust
	universityColors.add(new Color(82, 71, 59, 255)); // Sandstone
	universityColors.add(new Color(0, 56, 101, 255)); // University Blue
	universityColors.add(new Color(91, 83, 125, 255)); // Heather

	universityFontColors.add(Color.BLACK); // Lawn
	universityFontColors.add(Color.BLACK); // Turquoise
	universityFontColors.add(Color.BLACK); // Sunshine
	universityFontColors.add(Color.BLACK); // Pumpkin
	universityFontColors.add(Color.WHITE); // Thistle
	universityFontColors.add(Color.WHITE); // Pillarbox
	universityFontColors.add(Color.WHITE); // Lavender

	universityFontColors.add(Color.BLACK); // Aquamarine
	universityFontColors.add(Color.WHITE); // Slate
	universityFontColors.add(Color.WHITE); // Rose
	universityFontColors.add(Color.BLACK); // Mocha
	universityFontColors.add(Color.WHITE); // Forest
	universityFontColors.add(Color.BLACK); // Cobalt
	universityFontColors.add(Color.WHITE); // Burgundy
	universityFontColors.add(Color.WHITE); // Rust
	universityFontColors.add(Color.WHITE); // Sandstone
	universityFontColors.add(Color.WHITE); // University Blue
	universityFontColors.add(Color.WHITE); // Heather

	// END INIT UNIVERSITY COLORS
	
	this.A = A;
	this.n = n;

	S = new Stack<Integer>();
	stacked = new boolean[n];
	
	id = new int[n];
	low = new int[n];

	for (int v = 0; v < n; v++) {
            if(!stacked[v]){
		dfs(v);
	    }
        }
    } 

    void dfs(int u){
	stacked[u] = true;

	low[u] = index;
	index = index + 1;
	int min = low[u];
	
	S.push(u);
	
	for(int i=0; i<A[u].length; i++){
	    int w = A[u][i];

	    if(w == 1 && u!=i){
		if(!stacked[i]){
		    dfs(i);
		}
		
		if(low[i] < min){
		    min = low[i];
		}
	    }
	}

	if(min < low[u]){
	    low[u] = min;
	    return;
	}
	
	int w;
	
	do{
	    w = S.pop();
	    id[w] = count;
	    low[w] = n;
	} while (w != u);
	
	count = count + 1;
    }

    // Returns the number of strongly connected components
    public int count() {
        return count;
    }
    
    // Checks if u and w are in the same component
    public boolean stronglyConnected(int u, int w) {
	return id[u] == id[w];
    }

    // Returns the id of the strongly connected component containing the vertex
    public int id(int v) {
        return id[v];
    }
    
    public ArrayList<ArrayList<Integer>> run() {
	// number of strongly connected components
        int scc_count = count();
	theModel.setCurrentStepStatusLabel(scc_count + " strongly connected components");
        if(DEBUG.getValue()){System.out.println("\t" + scc_count + " strongly connected components");}

	ArrayList<ArrayList<Integer>> components = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < scc_count; i++) {
            components.add(new ArrayList<Integer>());
        }
	
        for (int v = 0; v < n; v++) {
            components.get(id[v]).add(v);
        }
	
	// Print the strongly connected components
        for (int i = 0; i < scc_count; i++) {
	    if(DEBUG.getValue()){System.out.print("\tComponent " + i + ": ");}
	    
            for (int v : components.get(i)) {

                if(DEBUG.getValue()){System.out.print(v + " ");}
		if(v < 9){
		    // dealing with a var on the left
		    theModel.theGrid.sudokuCells3Now[v].setBackground(universityColors.get(i));
		    theModel.theGrid.sudokuCells3Now[v].setFontColor(universityFontColors.get(i));
		} else if(v > 8){
		    // dealing with a circle on the right
		    theModel.theGrid.valueCircles[v-9 + 1].setCircleColor(universityColors.get(i));
		    theModel.theGrid.valueCircles[v-9 + 1].setFontColor(universityFontColors.get(i));
		}

		theModel.viewController.theEdges.repaint();
	    }
	    
            if(DEBUG.getValue()){System.out.println();}
        }

	return components;
    }
    
}
