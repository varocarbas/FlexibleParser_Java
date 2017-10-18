package NumberParser;

import java.util.Locale;

import InternalNumberParser.*;
import InternalNumberParser.Operations.*;
import InternalNumberParser.OtherParts.*;

/**
Number is the simplest and lightest NumberX class.
It is implicitly convertible to NumberD, NumberO, NumberP and all the numeric types.
**/
public class Number implements Comparable<Number>
{
    /**Double variable storing the primary value.**/
    private double Value = 0.0;

    /**Value getter.**/
    public double getValue()
	{
	    return Value;
	}

    /**Value setter.**/
	public void setValue(double value)
	{
	     Value = value;
	     if (value == 0) 
	     {
	    	 setBaseTenExponent(0);
	     }
	}

    /**Base-ten exponent complementing the primary value.**/
	private int BaseTenExponent = 0; 
    
    /**BaseTenExponent getter.**/
	public int getBaseTenExponent()
	{
	    return BaseTenExponent;
	}
	
    /**BaseTenExponent setter.**/	
	public void setBaseTenExponent(int baseTenExponent)
	{
		BaseTenExponent = baseTenExponent;
	}
    
    /**Error.**/
	private ErrorTypesNumber Error = ErrorTypesNumber.None;
    
    /**Error getter.**/
	public ErrorTypesNumber getError()
	{
	    return Error;
	}
	
    /**Initialises a new Number instance.**/
	public Number() { }

    /**
    Initialises a new Number instance.
    @param value Main value to be used.
    **/
	public Number(double value)
    {
    	this(value, 0);
    }

    /**
    Initialises a new Number instance.
    @param value Main value to be used. 
    @param baseTenExponent Base-ten exponent to be used. 
    **/
	public Number(double value, int baseTenExponent)
    {
        //To avoid problems with the automatic actions triggered by some setters, it is better 
        //to always assign values in this order (i.e., first BaseTenExponent and then Value).
        setBaseTenExponent(baseTenExponent);
        setValue(value);
    }

	ErrorTypesNumber PopulateNumberX(Object numberX)
	{
		Number tempVar = Constructors.ExtractDynamicToNumber(numberX);
		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			return tempVar.getError();
		}

		setBaseTenExponent(tempVar.getBaseTenExponent());
		setValue(tempVar.getValue());

		return ErrorTypesNumber.None;
	}
	
    /**
    Initialises a new Number instance.
    @param number Number variable whose information will be used.
    **/ 
	public Number(Number number)
    {
		Error = PopulateNumberX(number);
    }

    /**
    Initialises a new Number instance.
    @param numberD NumberD variable whose information will be used. 
    **/
    public Number(NumberD numberD)
    {
		Error = PopulateNumberX(numberD);
    }

    /**
    Initialises a new Number instance.
    @param numberO NumberO variable whose information will be used. 
    **/
    public Number(NumberO numberO)
    {
		Error = PopulateNumberX(numberO);
    }

    /**
    Initialises a new Number instance.
    @param numberP NumberP variable whose information will be used. 
    **/
    public Number(NumberP numberP)
    {
		Error = PopulateNumberX(numberP);
    }
    
    /**
    Initialises a new Number instance.
    @param unitP UnitParser's UnitP variable to be used. 
    **/
    public Number(Object unitP)
    {
        Number tempVar = OtherPartsUnitParserMethods.GetNumberFromUnitP(unitP);

		Error = PopulateNumberX(tempVar);
    }
    
    //---------------------------- Private Number constructors.

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public Number(float value)
    {
    	Number number = Conversions.ConvertAnyValueToDouble(value);

        setValue(number.getValue());
        Error = number.getError(); //float variable can trigger an error (e.g., NaN or infinity).
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public Number(long value)
    {
        setValue(Conversions.ConvertAnyValueToDouble(value).Value);
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public Number(int value)
    {
        setValue(Conversions.ConvertAnyValueToDouble(value).Value);
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public Number(short value)
    {
        setValue(Conversions.ConvertAnyValueToDouble(value).Value);
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public Number(byte value)
    {
        setValue(Conversions.ConvertAnyValueToDouble(value).Value);
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public Number(char value)
    {
        setValue(Conversions.ConvertAnyValueToDouble(value).Value);
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public Number(ErrorTypesNumber error) { Error = error; }
    
    
    //---------------------------- Static methods of Number emulating all the operator overloads of the original C# code.
       
    /**
    Adds two Number variables.          
    @param first First operand.
    @param second Second operand.
    @return Number variable resulting from adding first and second.   
    **/
    public static Number Addition(Number first, Number second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Addition
        );   	
    }

    /**
    Subtracts two Number variables.          
    @param first First operand.
    @param second Second operand.
    @return Number variable resulting from subtracting first and second.   
    **/
    public static Number Subtraction(Number first, Number second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Subtraction
        );   	
    }
    
    /**
    Multiplies two Number variables.          
    @param first First operand.
    @param second Second operand.
    @return Number variable resulting from multiplying first and second.   
    **/
    public static Number Multiplication(Number first, Number second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Multiplication
        );   	
    }
    
    /**
    Divides two Number variables.          
    @param first First operand.
    @param second Second operand.
    @return Number variable resulting from dividing first and second.   
    **/
    public static Number Division(Number first, Number second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Division
        );   	
    }
    
    /**
    Calculates the modulo of two Number variables.          
    @param first First operand.
    @param second Second operand.
    @return Number variable resulting from calculating the module of first and second.   
    **/
    public static Number Modulo(Number first, Number second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Modulo
        );   	
    }

   //---------------------------- Non-static and overloaded methods of Number emulating all the comparison overloads in the original C# code.
       
    /**
    Determines whether the current Number instance is greater than other.
    @param other Other variable.
     **/
    public boolean greaterThan(Number other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) == 1;
    }

    /**
    Determines whether the current Number instance is greater or equal than other.
    @param other Other variable.
     **/
    public boolean greaterOrEqualThan(Number other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) >= 0;
    }

    /**
    Determines whether the current Number instance is smaller than other.
    @param other Other variable.
     **/
    public boolean lessThan(Number other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) == -1;
    }

    /**
    Determines whether the current Number instance is smaller or equal than other.
    @param other Other variable.
     **/
    public boolean lessOrEqualThan(Number other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) <= 0;
    }
    
	@Override
    /**
	Outputs an error or "Value*10^BaseTenExponent" (BaseTenExponent different than zero sample).
     **/
    public String toString()
    {
        return toString(Locale.US);
    }

    /**
    Outputs an error or "Value*10^BaseTenExponent" (BaseTenExponent different than zero sample).
    @param culture Culture.
     **/
	public String toString(Locale culture)
    {
        if (!Error.equals(ErrorTypesNumber.None)) return "Error. " + Error.toString();
        if (culture == null) culture = Locale.US;

        Number number = OperationsManaged.PassBaseTenToValue(this, true);
        return OperationsOther.PrintNumberXInfo
        (
            number.getValue(), number.getBaseTenExponent(), null, culture
        );
    }
    
	@Override
    /**
    Compares the current instance against another Number one.
    @param other The other Number instance.
     **/
    public int compareTo(Number other)
    {
        return OperationsCompareTo.CompareDouble(this, other);
    }

    @Override
    /**
    Determines whether the current Number instance is equal to other one.
    @param obj Other variable.
     **/
    public boolean equals(Object obj)
    {
        return Equals(obj);
    }
    
    boolean Equals(Object other)
    {
        return
        (
            other == null ? false : 
            OperationsEquals.NumberXsAreEqual
            (
            	this, (Number)other
            )
        );
    }

    @Override
    /**Returns the hash code for this Number instance.**/
    public int hashCode() { return 0; }   
 }
