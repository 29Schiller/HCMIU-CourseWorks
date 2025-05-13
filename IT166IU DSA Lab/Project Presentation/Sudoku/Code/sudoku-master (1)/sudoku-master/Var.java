import java.util.*;

public class Var {

    String name;                       // name
    BitSet domain;                     // domain of values
    ArrayList<Constraint> constraints; // participating constraints

    Var (String name,int n){
	this.name   = name;
	domain      = new BitSet(n);
	constraints = new ArrayList<Constraint>();
	domain.set(0,n,true);
    }

    public int getValue(){
	int value = -1; // assume no unique set value
	int count = 0;
	int lastIndex = -1;
	for(int i=domain.nextSetBit(0);i>=0;i=domain.nextSetBit(i+1)){
	    count++;
	    lastIndex = i;
	}

	if(count == 1){
	    value = lastIndex + 1;
	}

	return value;
    }

    public ArrayList<Integer> getPossibleValues(){
	ArrayList<Integer> possibleValues = new ArrayList<Integer>();
	
	for(int i=domain.nextSetBit(0);i>=0;i=domain.nextSetBit(i+1)){
	    possibleValues.add(i+1);
	}
	
	return possibleValues;
    }
    
    public void setValue(int value){ // takes for example 9, which will be index 8
	for(int i=domain.nextSetBit(0);i>=0;i=domain.nextSetBit(i+1)){
	    if(value-1!=i){
		domain.clear(i);
	    }
	    if(value-1==i){
		domain.set(i);
	    }
	}
    }

    public void eliminateAll(){
	for(int i=domain.nextSetBit(0);i>=0;i=domain.nextSetBit(i+1)){
	    domain.clear(i);
	}
    }
    
    public void eliminateValue(int value){
	domain.clear(value-1);
    }

    public void addValue(int value){
	domain.set(value-1);
    }
    
    public void setName(String name){
	this.name = name;
    }

    public boolean hasValue(int value){
	return domain.get(value-1);
    }

    public String toString(){
	String s = name +":";
	for (int i=domain.nextSetBit(0);i>=0;i=domain.nextSetBit(i+1)) s = s + " " + i;
	for (Constraint c : constraints) s = s +" ("+ c +")";
	return s;
    }
}
