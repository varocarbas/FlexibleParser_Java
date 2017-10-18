package NumberParser;

import java.util.Locale;

import InternalNumberParser.*;
import InternalNumberParser.CSharpAdaptation.*;
import InternalNumberParser.Operations.*;
import InternalNumberParser.OtherParts.*;

/**
NumberD extends the limited decimal-only range of Number by supporting all the numeric types.
It is implicitly convertible to Number, NumberO, NumberP and all the numeric types.
**/
@SuppressWarnings("rawtypes")
public class NumberD implements Comparable<NumberD>
{
    /**Numeric variable storing the primary value.**/
    private Object Value = 0.0;

    /**Value getter.**/
    public Object getValue()
	{
	    return Value;
	}  

    /**Value setter.**/
	public void setValue(Object value)
	{
        NumericTypes type = ErrorInfoNumber.InputTypeIsValidNumeric(value);

        if (type.equals(NumericTypes.None)) 
        {
        	setValue(null);
        }
        else
        {
            Value = value;

            if 
            (
            	CompareNumeric.BothEqual
            	(
            		Value, Conversions.CastDynamicToType(0, type), type)
            	) 
            { 
            	setBaseTenExponent(0); 
            }
        }

        if (!Type.equals(type)) setType(type);
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
    
    /**Numeric type of the Value property.**/
    private NumericTypes Type = NumericTypes.None;

    /**Type getter.**/
    public NumericTypes getType() 
	{
	    return Type;
	}  

    /**Type setter.**/
    public void setType(NumericTypes type)
	{
        if (Value != null && !type.equals(NumericTypes.None))
        {
            if (NumericTypesMethods.GetTypeFromClass(Value.getClass()).equals(type)) 
            {
            	Type = type;
            }
            else
            {
                NumberD tempVar = new NumberD
                (
                	Value, BaseTenExponent, type, false
                );

                if (tempVar.getError().equals(ErrorTypesNumber.None))
                {
                    Type = type;
                    setBaseTenExponent(tempVar.getBaseTenExponent());
                    setValue(tempVar.getValue());
                }
                //else -> The new type is wrong and can be safely ignored.
            }
        }
	}  
 
    /**Readonly member of the ErrorTypesNumber enum which best suits the current conditions.**/
    private ErrorTypesNumber Error = ErrorTypesNumber.None;

    /**Error getter.**/
    public ErrorTypesNumber getError() 
	{
	    return Error;
	}  
    
    /**
    Initialises a new NumberD instance.
    @param type Type to be assigned to the dynamic Value property. Only numeric types are valid.
    **/
    public NumberD(NumericTypes type)
    {
        setValue(Basic.GetNumberSpecificType(0, type));
        setType(type);
    }

    /**
    Initialises a new NumberD instance.
    @param value Main value to be used. Only numeric variables are valid.
    @param baseTenExponent Base-ten exponent to be used.
    **/
    public NumberD(Object value, int baseTenExponent)
    {
        NumericTypes type = ErrorInfoNumber.InputTypeIsValidNumeric(value);

        if (type == null)
        {
            Error = ErrorTypesNumber.InvalidInput;
        }
        else
        {
            //To avoid problems with the automatic actions triggered by some setters, it is better 
            //to always assign values in this order (i.e., first BaseTenExponent, then Value and 
            //finally Type).
            setBaseTenExponent(baseTenExponent);
            setValue(value);
            setType(type);
        }
    }

    /**
    Initialises a new NumberD instance.
    @param value Main value to be used. Only numeric variables are valid.
    @param type Type to be assigned to the dynamic Value property. Only numeric types are valid.
    **/
    public NumberD(Object value, NumericTypes type)
    {
        NumberD numberD = CSharpOtherNP.ExtractValueAndTypeInfo(value, 0, type);

        if (!numberD.getError().equals(ErrorTypesNumber.None))
        {
            Error = numberD.getError();
        }
        else
        {
            setBaseTenExponent(numberD.getBaseTenExponent());
            setValue(numberD.getValue());
            setType(type);
        }
    }

    /**
    Initialises a new NumberD instance.
    @param value Main value to be used. Only numeric variables are valid.
    @param baseTenExponent Base-ten exponent to be used.
    @param type Type to be assigned to the dynamic Value property. Only numeric types are valid.
    **/
    public NumberD(Object value, int baseTenExponent, NumericTypes type)
    {
        NumberD numberD = CSharpOtherNP.ExtractValueAndTypeInfo
        (
            value, baseTenExponent, type
        );

        if (!numberD.Error.equals(ErrorTypesNumber.None))
        {
            Error = numberD.getError();
        }
        else
        {
            setBaseTenExponent(numberD.getBaseTenExponent());
            setValue(numberD.getValue());
            setType(type);
        }
    }

    /**
    Initialises a new NumberD instance.
    @param input Variable whose information will be used. Only NumberD, Number, NumberO, NumberP, numeric and UnitParser's UnitP variables are valid.
	**/
	public NumberD(Object input)
    {
    	Class type = ErrorInfoNumber.InputTypeIsValidNumericOrNumberX(input);

        if (type == null)
        {
            Number number = OtherPartsUnitParserMethods.GetNumberFromUnitP(input);

            if (number.getError().equals(ErrorTypesNumber.None))
            {
            	setBaseTenExponent(number.getBaseTenExponent());
                setValue(number.getValue());
                setType(NumericTypes.Double);
            }
            else Error = ErrorTypesNumber.InvalidInput;
        }
        else
        {
            if (Basic.AllNumberClassTypes.contains(type))
            {
            	NumberX.MainVars mainVars = NumberX.GetMainVars(input);
            	setBaseTenExponent(mainVars.BaseTenExponent);
            	setValue(mainVars.Value);

            	Type =
                (
                    type.equals(NumberD.class) ? ((NumberD)input).getType() : 
                    NumericTypesMethods.GetTypeFromObject(Value)
                );
                Error = mainVars.Error;
            }
            else
            {
                setValue(input);
                if (Value != null)
                {
                	 setType
                	 (
                		 NumericTypesMethods.GetTypeFromObject(Value)
                	 );
                }
            }
        }
    }
    
    //---------------------------- Private NumberD constructors.

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */ 
    public NumberD(double value)
    {
        setValue(value);
        setType(NumericTypesMethods.GetTypeFromClass(double.class));
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public NumberD(float value)
    {
    	setValue(value);
        setType(NumericTypesMethods.GetTypeFromClass(float.class));
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public NumberD(long value)
    {
    	setValue(value);
        setType(NumericTypesMethods.GetTypeFromClass(long.class));       
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public NumberD(int value)
    {
    	setValue(value);
        setType(NumericTypesMethods.GetTypeFromClass(int.class));
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public NumberD(short value)
    {
    	setValue(value);
        setType(NumericTypesMethods.GetTypeFromClass(short.class));
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public NumberD(byte value)
    {
    	setValue(value);
        setType(NumericTypesMethods.GetTypeFromClass(byte.class));
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public NumberD(char value)
    {
    	setValue(value);
        setType(NumericTypesMethods.GetTypeFromClass(char.class));
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public NumberD() { Error = ErrorTypesNumber.None; }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public NumberD(ErrorTypesNumber error) { Error = error; }

    NumberD(Object value, int baseTenExponent, NumericTypes type, boolean assignType)
    {
        NumberD numberD = CSharpOtherNP.ExtractValueAndTypeInfo
        (
            value, baseTenExponent, type
        );

        if (!numberD.Error.equals(ErrorTypesNumber.None))
        {
            Error = numberD.Error;
        }
        else
        {
            setBaseTenExponent(numberD.BaseTenExponent);
            setValue(numberD.getValue());
            if (assignType)
            {
                //Condition to avoid an infinite loop when calling this constructor from the Type property setter.
                setType(type);
            }
        }
    }

    //---------------------------- Static methods of NumberD emulating all the operator overloads of the original C# code.
       
    /**
    Adds two NumberD variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberD variable resulting from adding first and second.   
    **/
    public static NumberD Addition(NumberD first, NumberD second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Addition
        );   	
    }

    /**
    Subtracts two NumberD variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberD variable resulting from subtracting first and second.   
    **/
    public static NumberD Subtraction(NumberD first, NumberD second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Subtraction
        );   	
    }
    
    /**
    Multiplies two NumberD variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberD variable resulting from multiplying first and second.   
    **/
    public static NumberD Multiplication(NumberD first, NumberD second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Multiplication
        );   	
    }
    
    /**
    Divides two NumberD variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberD variable resulting from dividing first and second.   
    **/
    public static NumberD Division(NumberD first, NumberD second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Division
        );   	
    }
    
    /**
    Calculates the modulo of two NumberD variables.          
    @param first First operand.
    @param second Second operand.
    @return NumberD variable resulting from calculating the module of first and second.   
    **/
    public static NumberD Modulo(NumberD first, NumberD second)
    {
        return OperationsManaged.PerformArithmeticOperation
        (
            first, second, ExistingOperations.Modulo
        );   	
    }

   //---------------------------- Non-static and overloaded methods of NumberD emulating all the comparison overloads in the original C# code.
       
    /**
    Determines whether the current NumberD instance is greater than other.
    @param other Other variable.
     **/
    public boolean greaterThan(NumberD other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) == 1;
    }

    /**
    Determines whether the current NumberD instance is greater or equal than other.
    @param other Other variable.
     **/
    public boolean greaterOrEqualThan(NumberD other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) >= 0;
    }

    /**
    Determines whether the current NumberD instance is smaller than other.
    @param other Other variable.
     **/
    public boolean lessThan(NumberD other)
    {
    	return OperationsCompareTo.CompareDouble(this, other) == -1;
    }

    /**
    Determines whether the current NumberD instance is smaller or equal than other.
    @param other Other variable.
     **/
    public boolean lessOrEqualThan(NumberD other)
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

        NumberD numberD = OperationsManaged.PassBaseTenToValue(this, true);
        return OperationsOther.PrintNumberXInfo
        (
            numberD.getValue(), numberD.getBaseTenExponent(), Type, culture
        );
    }
    
	@Override
    /**
    Compares the current instance against another NumberD one.
    @param other The other NumberD instance.
     **/
    public int compareTo(NumberD other)
    {
        return OperationsCompareTo.CompareDouble(this, other);
    }

    @Override
    /**
    Determines whether the current NumberD instance is equal to other one.
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
            	this, (NumberD)other
            )
        );
    }

    @Override
    /**Returns the hash code for this NumberD instance.**/
    public int hashCode() { return 0; }  
}
