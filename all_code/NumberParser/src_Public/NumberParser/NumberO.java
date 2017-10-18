package NumberParser;

import java.util.ArrayList;
import java.util.Locale;

import InternalNumberParser.*;
import InternalNumberParser.Operations.*;
import InternalNumberParser.OtherParts.OtherPartsUnitParserMethods;

/**
NumberO is the only NumberX class dealing with different numeric types at the same time.
It is implicitly convertible to Number, NumberD, NumberP and all the numeric types.
**/
public class NumberO implements Comparable<NumberO>
{
	ArrayList<NumericTypes> _OtherTypes = null;
	
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
	     
	    if (Value == 0) BaseTenExponent = 0;
		Others = NumberOInternal.PopulateOthers
		(
			Value, BaseTenExponent, 
			NumberOInternal.CheckOtherTypes
			(
				_OtherTypes
			)
		);
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
		Others = NumberOInternal.PopulateOthers
		(
			Value, baseTenExponent, NumberOInternal.CheckOtherTypes
			(
				_OtherTypes
			)
		);
	}
    
	/**Collection including all the other numeric types associated with the current conditions.**/
	private ArrayList<NumberD> Others = new ArrayList<NumberD>();

    /**Others getter.**/
    public final ArrayList<NumberD> getOthers()
	{
	    return Others;
	}
    
	/**Readonly member of the ErrorTypesNumber enum which best suits the current conditions.**/
	private ErrorTypesNumber Error = ErrorTypesNumber.None;

    /**Error getter.**/
    public ErrorTypesNumber getError() 
	{
	    return Error;
	}  
    
	/**Initialises a new NumberO instance.
	public NumberO() { this(0.0); }

	/**
	Initialises a new NumberO instance.
	@param numberO  NumberO variable whose information will be used.
	**/ 
	public NumberO(NumberO numberO) 
	{
		this
		(
			numberO, (ArrayList<NumericTypes>)null
		); 
	}

	/**
	Initialises a new NumberO instance.
	@param numberO  NumberO variable whose information will be used.  
	@param otherType  Member of the OtherTypes enum determining the types to be considered.  
	**/
	public NumberO(NumberO numberO, OtherTypes otherType)
	{
		this
		(
			numberO, NumberOInternal.GetAssociatedTypes
			(
				otherType
			)
		);
	}

	/**
	Initialises a new NumberO instance.
	@param numberO  NumberO variable whose information will be used.  
	@param otherTypes  Collection containing the types to be considered.
	**/
	public NumberO(NumberO numberO, ArrayList<NumericTypes> otherTypes)
	{
		NumberD tempVar = Constructors.ExtractDynamicToNumberD(numberO);

		if (!tempVar.getError().equals(ErrorTypesNumber.None)) 
		{
			Error = tempVar.getError();
		}
		else
		{
			//To avoid problems with the automatic actions triggered by some setters, 
			//it is better to always assign values in this order (i.e., first _OtherTypes/Others, 
			//then BaseTenExponent and finally Value).
			if (otherTypes != null) 
			{
				_OtherTypes = new ArrayList<NumericTypes>(otherTypes);
			}
			else
			{
				Others = new ArrayList<NumberD>
				(
					numberO.Others
				);
			}
			
			setBaseTenExponent(tempVar.getBaseTenExponent());
			setValue
			(
				Conversions.ConvertTargetToDouble
				(
					tempVar.getValue()
				)
			);
		}
	}

	/**
	Initialises a new NumberO instance.
	@param number  Number variable whose information will be used.
	**/  
	public NumberO(Number number)
	{
		NumberD tempVar = Constructors.ExtractDynamicToNumberD(number);
		
		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			setBaseTenExponent(tempVar.getBaseTenExponent());
			setValue
			(
				Conversions.ConvertTargetToDouble
				(
					tempVar.getValue()
				)
			);
		}
	}

	/**
	Initialises a new NumberO instance.
	@param numberD  NumberD variable whose information will be used.
	**/
	public NumberO(NumberD numberD)
	{
		Number tempVar = Constructors.ExtractDynamicToNumber(numberD);

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			setBaseTenExponent(tempVar.getBaseTenExponent());
			setValue(tempVar.getValue());
		}
	}

	/**
	Initialises a new NumberO instance.
	@param numberP  NumberP variable whose information will be used.
	**/
	public NumberO(NumberP numberP)
	{
		Number tempVar = Constructors.ExtractDynamicToNumber(numberP);

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			setBaseTenExponent(tempVar.getBaseTenExponent());
			setValue(tempVar.getValue());
		}
	}

	/**
	Initialises a new NumberO instance.
	@param value  Main value to be used.  
	@param otherType  Member of the OtherTypes enum determining the types to be considered.  
	**/
	public NumberO(double value, OtherTypes otherType)
	{
		this
		(
			value, 0, otherType, ErrorTypesNumber.None
		);		
	}

	/**
	Initialises a new NumberO instance.
	@param value  Main value to be used.  
	@param otherTypes  Collection containing the types to be considered.  
	**/
	public NumberO(double value, ArrayList<NumericTypes> otherTypes)
	{
		this
		(
			value, 0, otherTypes, ErrorTypesNumber.None
		);		
	}
	
	/**
	Initialises a new NumberO instance.
	@param value  Main value to be used.  
	@param baseTenExponent  Base-ten exponent to be used.  
	@param otherType  Member of the OtherTypes enum determining the types to be considered. 
	**/ 
	public NumberO(double value, int baseTenExponent, OtherTypes otherType)
	{
		this
		(
			value, baseTenExponent, otherType, ErrorTypesNumber.None
		);		
	}

	/**
	Initialises a new NumberO instance.
	@param value  Main value to be used.  
	@param baseTenExponent  Base-ten exponent to be used.  
	@param otherTypes  Collection containing the types to be considered.
	**/  
	public NumberO(double value, int baseTenExponent, ArrayList<NumericTypes> otherTypes)
	{
		this
		(
			value, baseTenExponent, otherTypes, ErrorTypesNumber.None
		);		
	}
    
    /**
    Initialises a new NumberO instance.
    @param unitP  UnitParser's UnitP variable to be used.
    **/ 
    public NumberO(Object unitP)
    {
        Number tempVar = OtherPartsUnitParserMethods.GetNumberFromUnitP(unitP);
		
		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			Error = tempVar.getError();
		}
		else
		{
			setBaseTenExponent(tempVar.getBaseTenExponent());
			setValue
			(
				Conversions.ConvertTargetToDouble
				(
					tempVar.getValue()
				)
			);
		}
    }
    
    //---------------------------- Private NumberO constructors.
	
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(double value)
	{
		Number tempVar = Conversions.ConvertAnyValueToDouble(value);

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			//The double type can deliver erroneous values (e.g., NaN or infinity) which are stored as errors.
			Error = tempVar.getError();
		}
		else
		{
			//BaseTenExponent needs also to be considered because the double range is bigger than the decimal one. 
			_OtherTypes = NumberOInternal.GetAssociatedTypes(OtherTypes.AllTypes);
			setBaseTenExponent(tempVar.getBaseTenExponent());
			setValue(tempVar.getValue());
		}
	}

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(float value)
	{
		Number tempVar = Conversions.ConvertAnyValueToDouble(value);

		if (!tempVar.getError().equals(ErrorTypesNumber.None))
		{
			//The float type can deliver erroneous values (e.g., NaN or infinity) which are stored as errors.
			Error = tempVar.getError();
		}
		else
		{
			//BaseTenExponent needs also to be considered because the float range is bigger than the decimal one. 
			_OtherTypes = NumberOInternal.GetAssociatedTypes(OtherTypes.AllTypes);
			setBaseTenExponent(tempVar.getBaseTenExponent());
			setValue(tempVar.getValue());
		}
	}

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(long value)
	{
		this
		(
			Conversions.ConvertAnyValueToDouble(value).getValue(), 
			0, OtherTypes.AllTypes, ErrorTypesNumber.None
		);		
	}

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(int value) 
	{
		this
		(
			Conversions.ConvertAnyValueToDouble(value).getValue(),
			0, OtherTypes.AllTypes, ErrorTypesNumber.None
		);		
	}

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(short value)
	{
		this
		(
			Conversions.ConvertAnyValueToDouble(value).getValue(),
			0, OtherTypes.AllTypes, ErrorTypesNumber.None
		);		
	}
	
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(byte value)
	{
		this
		(
			Conversions.ConvertAnyValueToDouble(value).getValue(),
			0, OtherTypes.AllTypes, ErrorTypesNumber.None
		);		
	}

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(char value)
	{
		this
		(
			Conversions.ConvertAnyValueToDouble(value).getValue(),
			0, OtherTypes.AllTypes, ErrorTypesNumber.None
		);		
	}

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(double value, int baseTenExponent)
	{
		this
		(
			value, baseTenExponent, 
			OtherTypes.AllTypes, 
			ErrorTypesNumber.None
		);		
	}

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(ErrorTypesNumber error) { Error = error; }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	public NumberO(double value, int baseTenExponent, OtherTypes otherType, ErrorTypesNumber error)
	{
		this 
		(
			value, baseTenExponent, 
			NumberOInternal.GetAssociatedTypes
			(
				otherType
			), 
			error
		);		
	}
	
	/** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
	NumberO
	(
		double value, int baseTenExponent, 
		ArrayList<NumericTypes> otherTypes, 
		ErrorTypesNumber error
	)
	{
		_OtherTypes = NumberOInternal.CheckOtherTypes
		(
			otherTypes
		);
		setBaseTenExponent(baseTenExponent);
		setValue(value);
		Error = error;
	}
	
    
    //---------------------------- Static methods of NumberO emulating all the operator overloads of the original C# code.
       
    /**
    Adds two NumberO variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberO variable resulting from adding first and second.   
    **/
    public static NumberO Addition(NumberO first, NumberO second)
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
    @return NumberO variable resulting from subtracting first and second.   
    **/
    public static NumberO Subtraction(NumberO first, NumberO second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Subtraction
        );   	
    }
    
    /**
    Multiplies two NumberO variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberO variable resulting from multiplying first and second.   
    **/
    public static NumberO Multiplication(NumberO first, NumberO second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Multiplication
        );   	
    }
    
    /**
    Divides two NumberO variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberO variable resulting from dividing first and second.   
    **/
    public static NumberO Division(NumberO first, NumberO second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Division
        );   	
    }
    
    /**
    Calculates the modulo of two NumberO variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberO variable resulting from calculating the module of first and second.   
    **/
    public static NumberO Modulo(NumberO first, NumberO second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Modulo
        );   	
    }

   //---------------------------- Non-static and overloaded methods of NumberO emulating all the comparison overloads in the original C# code.
       
    /**
    Determines whether the current NumberO instance is greater than other.
    @param other Other variable.
     **/
    public boolean greaterThan(NumberO other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) == 1;
    }

    /**
    Determines whether the current NumberO instance is greater or equal than other.
    @param other Other variable.
     **/
    public boolean greaterOrEqualThan(NumberO other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) >= 0;
    }

    /**
    Determines whether the current NumberO instance is smaller than other.
    @param other Other variable.
     **/
    public boolean lessThan(NumberO other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) == -1;
    }

    /**
    Determines whether the current NumberO instance is smaller or equal than other.
    @param other Other variable.
     **/
    public boolean lessOrEqualThan(NumberO other)
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

		Number number = OperationsManaged.PassBaseTenToValue
		(
			new Number(this), true
		);
		String output = OperationsOther.PrintNumberXInfo
		(
			number.getValue(), number.getBaseTenExponent(), 
			NumericTypes.Double, culture
		);

		for (NumberD other: Others)
		{
			output += System.lineSeparator() + OperationsOther.PrintNumberXInfo
			(
				other.getValue(), other.getBaseTenExponent(), 
				other.getType(), culture
			);
		}

		return output;
    }
    
	@Override
    /**
    Compares the current instance against another NumberO one.
    @param other The other NumberO instance.
     **/
    public int compareTo(NumberO other)
    {
        return OperationsCompareTo.CompareDouble(this, other);
    }

    @Override
    /**
    Determines whether the current NumberO instance is equal to other one.
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
            	this, (NumberO)other
            )
        );
    }

    @Override
    /**Returns the hash code for this NumberO instance.**/
    public int hashCode() { return 0; }  	
}
