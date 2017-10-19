package InternalNumberParser.Math2Internal;

import NumberParser.*;
import InternalNumberParser.*;
import InternalNumberParser.Operations.*;

public class Math2Other
{
    public static NumberD FactorialInternal(NumberD n)
    {
        ErrorTypesNumber error = ErrorInfoNumber.GetFactorialError(n);
        if (!error.equals(ErrorTypesNumber.None)) return new NumberD(error);
        if (Conversions.ConvertTargetToDouble(n.getValue()) <= 1.0) 
        {
        	return new NumberD(Conversions.CastDynamicToType(1, n.getType()));
        }

        NumericTypes type = n.getType();
        if (!type.equals(NumericTypes.Long) && !type.equals(NumericTypes.Integer))
        {
            n.setValue((long)n.getValue());
        }
        n = OperationsManaged.PassBaseTenToValue(n);

        //At this point, output.BaseTenExponent has to be zero.
        NumberD output = new NumberD(1, n.getType());
        double i = 2.0;
        double val = Conversions.ConvertTargetToDouble(n.getValue());

        while (i <= val)
        {
            output = OperationsManaged.MultiplyInternal
            (
            	output, new NumberD(i)
            );
            i++;
        }

        return output;
    }
}
