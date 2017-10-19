package InternalNumberParser.Operations;

import java.util.Locale;

import NumberParser.*;
import InternalNumberParser.*;
import InternalNumberParser.CSharpAdaptation.*;

@SuppressWarnings("rawtypes")
public class OperationsOther
{
    public static String PrintNumberXInfo(Object value, Integer baseTenExponent, NumericTypes type, Locale culture)
    {
        String outString = OperationsOther.PrintDynamicValue(value, culture) +
        (
            baseTenExponent != 0 ? "*10^" + baseTenExponent.toString() : ""
        );

        if (type != null)
        {
            outString += " (" + type.toString() + ")";
        }

        return outString;
    }

    public static Object VaryBaseTenExponent(Object input, int baseTenIncrease)
    {
    	return VaryBaseTenExponent(input, baseTenIncrease, false);
    }
    
    //This method assumes that input is a valid instance of Number, NumberD, NumberO or NumberP. 
    public static Object VaryBaseTenExponent(Object input, int baseTenIncrease, boolean isDivision)
    {
    	NumberX.MainVars mainVars = NumberX.GetMainVars(input);
        long val1 = mainVars.BaseTenExponent;
        long val2 = baseTenIncrease;

        if (isDivision)
        {
            //Converting a negative value into positive might provoke an overflow error for the int type
            //(e.g., Math.Abs(int.MinValue)). Converting both variables to long is a quick and effective
            //way to avoid this problem.
            val2 *= -1;
        }

        return
        (
            (val2 > 0 && val1 > Integer.MAX_VALUE - val2) || 
            (val2 < 0 && val1 < Integer.MIN_VALUE - val2) ?
            ErrorInfoNumber.GetNumberXError
            (
            	input.getClass(), ErrorTypesNumber.NumericOverflow
            ) :
            Constructors.InitialiseNumberX
            (
            	input.getClass(), mainVars.Value, (int)(val1 + val2)
            )
        );
    }

    //This method assumes that the input culture isn't null.
    public static String PrintDynamicValue(Object value, Locale culture)
    {
        if (value == null) return "";
        NumericTypes type = NumericTypesMethods.GetTypeFromObject(value);
        if (type.equals(NumericTypes.None)) return "";

        String output = CSharpOtherNP.NumericValueToString
        (
        		value, culture, type
        );

        return
        (
            type.equals(NumericTypes.Character) ? 
            "'" + output + "'" : output
        );
    }

    public static NumberD AbsInternal(Object input)
    {
		Class type = ErrorInfoNumber.InputTypeIsValidNumericOrNumberX(input);

        return 
        (
            type == null ? 
            new NumberD(ErrorTypesNumber.InvalidInput) : 
            AbsInternalValue(new NumberD(input))
        );
    }

    static NumberD AbsInternalValue(NumberD numberD)
    {
        if (Basic.AllUnsignedTypes.contains(numberD.getType()))
        {
            return numberD;
        }

        if (IsSpecialAbsCase(numberD.getValue(), numberD.getType()))
        {
            //In certain cases, the minimum value of a type is bigger than the
            //maximum one. That's why a modification is required before calculating
            //the absolute value.
            if (numberD.getBaseTenExponent() == Integer.MAX_VALUE)
            {
                return new NumberD(ErrorTypesNumber.InvalidInput);
            }

            numberD.setValue
            (
            	Basic.GetNumberSpecificType
            	(
            		Conversions.ConvertTargetToDouble
            		(
            			numberD.getValue()
            		) 
            		/ 10.0, numberD.getType()
                )
            	
            );
            numberD.setBaseTenExponent
            (
            	numberD.getBaseTenExponent() + 1
            );
        }

        
        numberD.setValue
        (	
        	OperationsManaged.PerformArithmeticOperationDynamicVariables
        	(
        		numberD.getValue(), Conversions.CastDynamicToType
        		(
        			Conversions.ConvertTargetToDouble
        			(
        				numberD.getValue()
        			) 
        			< 0.0 ? -1 : 1, numberD.getType()
        		), 
        		ExistingOperations.Multiplication
        	)
        );

        return numberD;
    }

    static boolean IsSpecialAbsCase(Object value, NumericTypes type)
    {
        return 
        (
            (
            	type.equals(NumericTypes.Integer) && 
            	value == Basic.AllNumberMinMaxs.get(type).get(0)
            ) 
            || 
            (
            	type.equals(NumericTypes.Long) && 
            	value == Basic.AllNumberMinMaxs.get(type).get(0)
            ) 
            || 
            (
            	type.equals(NumericTypes.Short) && 
            	value == Basic.AllNumberMinMaxs.get(type).get(0)
            )
        );
    }
}
