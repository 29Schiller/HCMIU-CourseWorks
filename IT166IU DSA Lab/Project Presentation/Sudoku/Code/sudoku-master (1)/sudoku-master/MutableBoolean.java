class MutableBoolean
{
    boolean value;
    
    MutableBoolean(boolean value)
    {
	this.value = value;
    }

    void setValue(boolean value){ this.value = value;}
    boolean getValue(){ return value;}
}
