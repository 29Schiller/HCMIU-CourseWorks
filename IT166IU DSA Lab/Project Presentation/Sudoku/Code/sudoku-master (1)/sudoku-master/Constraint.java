import java.util.*;

public class Constraint {

    String name; 
    Var v1;
    boolean[][] relation;
    Var v2;
    boolean flag;

    Constraint (Var v1,boolean[][] relation,Var v2,String name){
	this.v1       = v1;
	this.relation = relation;
	this.v2       = v2;
	this.name     = name;
	flag          = false;
	v2.constraints.add(this); // adds it only to the second variable
    }

    Constraint (SudokuWorld sw, Constraint oldC){
	this.v1 = sw.getVarByName(oldC.v1.name);
	this.relation = oldC.relation.clone();
	this.v2 = sw.getVarByName(oldC.v2.name);
	this.name = new String(oldC.name);
	flag = false;
	v2.constraints.add(this);
    }
    
    public boolean revise(){ // reminder, only first domain is checked
	// only the last one changes, afaik
	boolean change = false;
	BitSet dom1 = v1.domain;
	BitSet dom2 = v2.domain;
	//Returns the index of the first bit that is set to true that occurs on or
	//after the specified starting index. If no such bit exists then -1 is returned.
	//To iterate over the true bits in a BitSet, use the following loop: 
	for (int i=dom1.nextSetBit(0);i>=0;i=dom1.nextSetBit(i+1)){
	    boolean consistent = false;
	    // when consistent is true, !consistent is false and breaks one loop
	    for (int j=dom2.nextSetBit(0);j>=0 && !consistent;j=dom2.nextSetBit(j+1)){
		consistent = relation[i][j];
	    }
	    // still part of the first loop
	    if (!consistent){v1.domain.set(i,false); change = true;}
	}

	return change;
    }

    public String toString(){
	return v1.name +" "+ name +" "+ v2.name;
    }
}
