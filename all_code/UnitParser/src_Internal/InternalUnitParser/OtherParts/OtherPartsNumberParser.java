package InternalUnitParser.OtherParts;

/**
Class emulating NumberParser's NumberX (i.e., NumberParser's Number, NumberD, NumberO or NumberP)
in its most basic configuration, by only including the public properties which are relevant 
(i.e., used by the methods in OtherPartsNumberParserMethods).
*/
public class OtherPartsNumberParser 
{
    public double Value;
    public int Exponent;
    public boolean IsWrong;

    public OtherPartsNumberParser()
    {
        IsWrong = true;
    }

    public OtherPartsNumberParser(double value)
    {
    	this(value, 0);
    }
    
    public OtherPartsNumberParser(double value, int exponent)
    {
        Value = value;
        Exponent = exponent;
    }
}
