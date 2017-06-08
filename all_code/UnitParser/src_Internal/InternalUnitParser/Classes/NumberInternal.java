package InternalUnitParser.Classes;

//Class used for operations among NumberX (i.e., NumberParser's Number, NumberD, NumberO or NumberP)
//which emulates their only properties which are relevant here.
public class NumberInternal
{
    public double Value;
    public int Exponent;
    public boolean IsWrong;

    public NumberInternal()
    {
        IsWrong = true;
    }
    
    public NumberInternal(double value)
    {
    	this(value, 0);
    }
    
    public NumberInternal(double value, int exponent)
    {
        Value = value;
        Exponent = exponent;
    }
}
