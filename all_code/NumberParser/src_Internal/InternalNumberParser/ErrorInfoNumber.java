package InternalNumberParser;

import InternalNumberParser.CSharpAdaptation.*;
import NumberParser.*;
import NumberParser.Number;

@SuppressWarnings("rawtypes")
public class ErrorInfoNumber
{
	public static Object GetNumberXError(Class type, ErrorTypesNumber error)
    {
        if (type.equals(Number.class))
        {
            return new Number(error);
        }
        else if (type.equals(NumberD.class))
        {
            return new NumberD(error);
        }
        else if (type.equals(NumberO.class))
        {
            return new NumberO(error);
        }
        else if (type.equals(NumberP.class))
        {
            return new NumberP(error);
        }

        return null;
    }

    public static ErrorTypesNumber ApplyPolynomialFitError(Polynomial coefficients, NumberD xValue)
    {
        ErrorTypesNumber error = ErrorTypesNumber.None;

        if (coefficients == null || xValue == null) error = ErrorTypesNumber.InvalidInput;
        if (!coefficients.Error.equals(ErrorTypesNumber.None)) error = coefficients.Error;
        if (!xValue.getError().equals(ErrorTypesNumber.None)) error = xValue.getError();

        return error;
    }

    public static ErrorTypesNumber GetPolynomialFitError(NumberD[] xValues, NumberD[] yValues)
    {
        ErrorTypesNumber error = ErrorTypesNumber.None;

        if 
        (
        	xValues == null || xValues.length == 0 || 
        	yValues == null || yValues.length == 0
        )
        {
            error = ErrorTypesNumber.InvalidInput;
        }
        else if (xValues.length != yValues.length)
        {
            error = ErrorTypesNumber.InvalidOperation;
        }

        return error;
    }

    public static ErrorTypesNumber GetFactorialError(NumberD n)
    {
        ErrorTypesNumber error =
        (
            Conversions.ConvertTargetToDouble(n.getValue()) < 0.0 ? 
            ErrorTypesNumber.InvalidOperation :
            GetOperandError(n, NumericTypes.Long)
        );
        if (!error.equals(ErrorTypesNumber.None)) return error;

        return
        (
        	n.greaterOrEqualThan(new NumberD(100000.0)) ? 
        	ErrorTypesNumber.InvalidOperation : ErrorTypesNumber.None
        );
    }

    public static ErrorTypesNumber GetPowTruncateError(Number number)
    {
        return
        (
            number == null || !number.getClass().equals(Number.class) ?
            ErrorTypesNumber.InvalidInput : ErrorTypesNumber.None
        );
    }

    public static ErrorTypesNumber GetOperationError(Object first, Object second, ExistingOperations operation)
    {
        ErrorTypesNumber error = GetOperandsError(first, second);
        if (!error.equals(ErrorTypesNumber.None)) return error;

        NumberX.MainVars mainVars2 = NumberX.GetMainVars(second);
        double val2 = Conversions.ConvertTargetToDouble(mainVars2.Value);
        
        return
        (
            (
            	operation.equals(ExistingOperations.Division) || 
            	operation.equals(ExistingOperations.Modulo)
            ) 
            && val2 == 0.0 ? 
            ErrorTypesNumber.InvalidOperation : ErrorTypesNumber.None
        );
    }

    public static ErrorTypesNumber GetOperandsError(Object first, Object second)
    {
    	return GetOperandsError(first, second, null);
    }
    
    public static ErrorTypesNumber GetOperandsError(Object first, Object second, NumericTypes target)
    {
        ErrorTypesNumber error = GetOperandError(first, target);

        return
        (
            error.equals(ErrorTypesNumber.None) ? GetOperandError(second, target) : error
        );
    }

    public static ErrorTypesNumber GetOperandError(Object input)
    {
    	return GetOperandError(input, null);
    }
    
    public static ErrorTypesNumber GetOperandError(Object input, NumericTypes target)
    {
        NumberX.MainVars mainVars = NumberX.GetMainVars(input);
        
    	NumberD input2 = 
    	(
    		input == null || !Basic.AllNumberClassTypes.contains
    		(
    			input.getClass()
    		) 
    		? null : new NumberD(mainVars.Value, mainVars.BaseTenExponent)
    	);
    	
        if (input2 == null || input2.getValue() == null)
        {
            return ErrorTypesNumber.InvalidInput;
        }
        if (!input2.getError().equals(ErrorTypesNumber.None)) 
        {
        	return input2.getError();
        }

        NumericTypes type = NumericTypesMethods.GetTypeFromObject
        (
        	input2.getValue()
        );
        if (type.equals(NumericTypes.Double) || type.equals(NumericTypes.Float))
        {
            ErrorTypesNumber error = GetFloatingTypeError(input2.getValue(), type);
            if (!error.equals(ErrorTypesNumber.None)) 
            {
            	return ErrorTypesNumber.InvalidInput;
            }
        }

        return
        (
            target != null && !Basic.InputInsideTypeRange(input, target) ?
            ErrorTypesNumber.NativeMethodError : ErrorTypesNumber.None
        );
    }

    public static ErrorTypesNumber GetConvertToAnyError(Number number, NumericTypes target)
    {
        if (number == null || !Basic.AllNumericTypes.contains(target))
        {
            return ErrorTypesNumber.InvalidInput;
        }

        return number.getError();
    }

    public static ErrorTypesNumber GetFloatingTypeError(Object value, NumericTypes type)
    {
        if (value == null) return ErrorTypesNumber.InvalidInput;

        if (type.equals(NumericTypes.Double))
        {
        	Double value2 = (Double)value;
            if (value2.isInfinite() || value2.isNaN())
            {
                return ErrorTypesNumber.InvalidInput;
            }
        }
        else 
        {
        	Float value2 = (Float)value;
        	if (value2.isInfinite() || value2.isNaN())
            {
                return ErrorTypesNumber.InvalidInput;
            }
        }

        return ErrorTypesNumber.None;
    }

    public static Class InputTypeIsValidNumericOrNumberX(Object input)
    {
        return InputTypeIsOK
        (
            input, InputType.NumericAndNumberClass
        );
    }

    public static NumericTypes InputTypeIsValidNumeric(Object input)
    {
        return NumericTypesMethods.GetTypeFromClass
        (
        	InputTypeIsOK
        	(
        		input, InputType.Numeric
        	)
        );
    }

    private static Class InputTypeIsOK(Object input, InputType inputType)
    {
		Class type = (input == null ? null : input.getClass());
		if (type == null) return null;

		if (inputType.equals(InputType.NumberClass))
		{
			return
			(
				Basic.AllNumberClassTypes.contains(type) ? type : null
			);
		}
		
		NumericTypes type2 = NumericTypesMethods.GetTypeFromClass(type);
		if (!type2.equals(NumericTypes.None))
		{
			if (type2.equals(NumericTypes.Double) || type2.equals(NumericTypes.Float))
			{
				if (!GetFloatingTypeError(input, type2).equals(ErrorTypesNumber.None))
				{
					return null;
				}
			}
			
			return type;
		}

		return
		(
			inputType.equals(InputType.NumericAndNumberClass) &&
			Basic.AllNumberClassTypes.contains(type) ? type : null
		);
    }

    public enum InputType { Numeric, NumberClass, NumericAndNumberClass }
}
