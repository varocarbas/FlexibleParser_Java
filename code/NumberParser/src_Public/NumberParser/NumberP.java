package NumberParser;

import java.util.Locale;

import InternalNumberParser.*;
import InternalNumberParser.Operations.*;
import InternalNumberParser.OtherParts.OtherPartsUnitParserMethods;

/**
NumberP is the only NumberX class parsing string inputs.
It is implicitly convertible to Number, NumberD, NumberO and string.
**/
public class NumberP implements Comparable<NumberP>
{
	/**Readonly primary value under the current conditions.**/
	private Object Value = 0.0;

	/**Value getter.**/
    public Object getValue()
	{
	    return Value;
	}
	
	/**Readonly Base-ten exponent under the current conditions.**/
	private int BaseTenExponent = 0;
	
    /**BaseTenExponent getter.**/
    public int getBaseTenExponent()
	{
	    return BaseTenExponent;
	}
	
	/**Readonly string variable including the original input to be parsed.**/
	private String OriginalString = null;
	
    /**OriginalString getter.**/
    public String getOriginalString()
	{
	    return OriginalString;
	}
    
	/**Readonly ParseConfig variable defining the current parsing configuration.**/
	private ParseConfig Config = new ParseConfig();
	
    /**ParseConfig getter.**/
    public ParseConfig getParseConfig()
	{
	    return Config;
	}
    
	/**Readonly member of the ErrorTypesNumber enum which best suits the current conditions.**/
	private ErrorTypesNumber Error = ErrorTypesNumber.None;
	
    /**Error getter.**/
    public ErrorTypesNumber getError()
	{
	    return Error;
	}
    
	/**
	Initialises a new NumberP instance.
	@param input String variable whose contents will be parsed.
	**/ 
	public NumberP(String input)
	{
		this
		(
			input, new ParseConfig()
		);
	}

	/**
	Initialises a new NumberP instance.
	@param input String variable whose contents will be parsed. 	  
	@param config ParseConfig variable whose information will be used.
	**/   
	public NumberP(String input, ParseConfig config)
	{
		if (input == null || input.trim().length() < 1)
		{
			Error = ErrorTypesNumber.InvalidInput;
			return;
		}

		OriginalString = input;
		Config = new ParseConfig(config);

		//NumberD is lighter than NumberP and contains all what matters here (i.e., Object-type Value and BaseTenExponent).
		NumberD tempVar = NumberPInternal.StartParse(new ParseInfo(this));

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			BaseTenExponent = tempVar.getBaseTenExponent();
			Value = tempVar.getValue();
		}
	}

	/**
	Initialises a new NumberP instance.
	@param numberP NumberP variable whose information will be used.
	**/	  
	public NumberP(NumberP numberP)
	{
		if (numberP == null)
		{
			Error = ErrorTypesNumber.InvalidInput;
		}
		else
		{
			BaseTenExponent = numberP.BaseTenExponent;
			Value = numberP.Value;
			OriginalString = numberP.OriginalString;
			Config = new ParseConfig(numberP.Config);
			Error = numberP.Error;
		}
	}

	/**
	Initialises a new NumberP instance.
	@param number Number variable whose information will be used.
	**/ 	   
	public NumberP(Number number)
	{
		Number tempVar = Constructors.ExtractDynamicToNumber(number);

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			BaseTenExponent = number.getBaseTenExponent();
			Value = number.getValue();
			Config = new ParseConfig(NumericTypes.Double);
		}
	}

	/**
	Initialises a new NumberP instance.
	@param numberD NumberD variable whose information will be used.
	**/ 	
	public NumberP(NumberD numberD)
	{
		NumberD tempVar = Constructors.ExtractDynamicToNumberD(numberD);

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			BaseTenExponent = numberD.getBaseTenExponent();
			Value = numberD.getValue();
			Config = new ParseConfig(numberD.getType());
		}
	}

	/**
	Initialises a new NumberP instance.
	@param numberO NumberO variable whose information will be used.
	**/	
	public NumberP(NumberO numberO)
	{
		Number tempVar = Constructors.ExtractDynamicToNumber(numberO);

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			BaseTenExponent = numberO.getBaseTenExponent();
			Value = numberO.getValue();
			Config = new ParseConfig(NumericTypes.Double);
		}
	}
    
    /**
    Initialises a new NumberP instance.
    @param unitP UnitParser's UnitP variable to be used.
    **/
    public NumberP(Object unitP)
    {
        Number tempVar = OtherPartsUnitParserMethods.GetNumberFromUnitP(unitP);

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			BaseTenExponent = tempVar.getBaseTenExponent();
			Value = tempVar.getValue();
			Config = new ParseConfig(NumericTypes.Double);
		}
    }
    
    //---------------------------- Private NumberP constructors.

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberP(Number number, String originalString, ParseConfig config)
	{
		Number tempVar = Constructors.ExtractDynamicToNumber(number);

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			BaseTenExponent = number.getBaseTenExponent();
			Value = number.getValue();
			OriginalString = originalString;
			Config = new ParseConfig(config);
		}
	}

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberP(Object value, int baseTenExponent)
	{
		BaseTenExponent = baseTenExponent;
		Value = value;
	}

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberP(ErrorTypesNumber error) { Error = error; }
	
    
    //---------------------------- Static methods of NumberP emulating all the operator overloads of the original C# code.
       
    /**
    Adds two NumberP variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberP variable resulting from adding first and second.   
    **/
    public static NumberP Addition(NumberP first, NumberP second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Addition
        );   	
    }

    /**
    Subtracts two NumberO variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberP variable resulting from subtracting first and second.   
    **/
    public static NumberP Subtraction(NumberP first, NumberP second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Subtraction
        );   	
    }
    
    /**
    Multiplies two NumberP variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberP variable resulting from multiplying first and second.   
    **/
    public static NumberP Multiplication(NumberP first, NumberP second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Multiplication
        );   	
    }
    
    /**
    Divides two NumberP variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberP variable resulting from dividing first and second.   
    **/
    public static NumberP Division(NumberP first, NumberP second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Division
        );   	
    }
    
    /**
    Calculates the modulo of two NumberP variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberP variable resulting from calculating the module of first and second.   
    **/
    public static NumberP Modulo(NumberP first, NumberP second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Modulo
        );   	
    }

   //---------------------------- Non-static and overloaded methods of NumberP emulating all the comparison overloads in the original C# code.
       
    /**
    Determines whether the current NumberP instance is greater than other.
    @param other Other variable.
     **/
    public boolean greaterThan(NumberP other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) == 1;
    }

    /**
    Determines whether the current NumberP instance is greater or equal than other.
    @param other Other variable.
     **/
    public boolean greaterOrEqualThan(NumberP other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) >= 0;
    }

    /**
    Determines whether the current NumberP instance is smaller than other.
    @param other Other variable.
     **/
    public boolean lessThan(NumberP other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) == -1;
    }

    /**
    Determines whether the current NumberP instance is smaller or equal than other.
    @param other Other variable.
     **/
    public boolean lessOrEqualThan(NumberP other)
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

		NumberD numberD = OperationsManaged.PassBaseTenToValue
		(
			new NumberD(this), true
		);

		return OperationsOther.PrintNumberXInfo
		(
			numberD.getValue(), numberD.getBaseTenExponent(), null, culture
		)
		+ " (" + OriginalString + ")" + System.lineSeparator() + Config.toString();
    }
    
	@Override
    /**
    Compares the current instance against another NumberP one.
    @param other The other NumberP instance.
     **/
    public int compareTo(NumberP other)
    {
        return OperationsCompareTo.CompareDouble(this, other);
    }

    @Override
    /**
    Determines whether the current NumberP instance is equal to other one.
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
            	this, (NumberP)other
            )
        );
    }

    @Override
    /**Returns the hash code for this NumberO instance.**/
    public int hashCode() { return 0; }  
}