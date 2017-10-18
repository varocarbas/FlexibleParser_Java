package InternalNumberParser.Math2Internal;

import NumberParser.*;
import NumberParser.Number;
import InternalNumberParser.*;
import InternalNumberParser.Operations.*;

//This class contains a very limited version of the code in the original C# version dealing with
//PowDecimal/SqrtDecimal. 
//Note that the custom parts (Newton-Raphson + my improvements) don't make too much sense in this
//specific context. My intention when developing all that was to allow the very high precision 
//(accurately dealing with up to 27-28 decimal positions!) of the .NET decimal type to be fully
//maximised, as opposed to what the in-built Pow/Sqrt methods do (i.e., accounting for the much less
//precise double type, accurately dealing with up to 7-9 decimal positions). As far as this Java
//implementation has to forcibly rely on the double type, the point of that custom approach was 
//pretty much lost. 
//In any case, I did try to adapt that original code to the double/Java peculiarities mainly to be 
//consistent with my intention of creating an as-similar-to-C#-as-possible Java version; but soon I
//realised about the low applicability of that original approach to these new conditions. Having fully
//redeveloped my custom approach just to deliver a similar precision to what the in-built methods already
//do wouldn't have made too much sense. That's why I decided to implement the current version which is
//just a simple wrapper allowing to use Math.pow/Math.sqrt with Number variables.
public class Math2PowSqrt
{
    public static Number PowSqrtInternal(Number number, double exponent)
    {        
        return PowSqrtInternal(number, exponent, true);
    }
    
    public static Number PowSqrtInternal(Number number, double exponent, boolean showUser)
    {
        ErrorTypesNumber error = ErrorInfoNumber.GetPowTruncateError(number);
        if (!error.equals(ErrorTypesNumber.None)) return new Number(error);
        
        boolean isSqrt = (exponent == 0.5);
        boolean expIsInteger = (exponent == Math.floor(exponent));

        Number tempVar = PowSqrtPreCheck(number, exponent, expIsInteger);
        if (tempVar != null) return tempVar;

        Number outNumber = OperationsManaged.PassBaseTenToValue(number);

        //The BaseTenExponent is calculated independently in order to avoid operations with too big values.
        //Note that this BaseTenExponent represents all the additional information on top of the maximum
        //double range. During the calculations, the outNumber variable will be normalised and, consequently,
        //a new BaseTenExponent will be used.
        int baseTenExp = outNumber.getBaseTenExponent(); 
        outNumber.setBaseTenExponent(0);

        //Better simplifying the intermediate calculations as much as possible by ignoring all the signs.
        double sign =
        (
            exponent % 2 == 0 ? 1.0 : Math.signum(outNumber.getValue())
        );
        outNumber.setValue(Math.abs(outNumber.getValue()));

        double sign2 = Math.signum(exponent);
        exponent = Math.abs(exponent);

        if (isSqrt) 
        {
        	outNumber = OperationsManaged.MultiplyInternal
        	(
        		new Number(sign), new Number(Math.sqrt(outNumber.getValue()))
        	);
        }
        else
        {
            outNumber = new Number(Math.pow(outNumber.getValue(), exponent));

            outNumber.setValue(sign * outNumber.getValue());

            if (sign2 == -1)
            {
                outNumber = OperationsManaged.DivideInternal
                (
                	new Number(1.0), outNumber
                );
            }
        }

        if (baseTenExp != 0)
        {
            outNumber = OperationsManaged.MultiplyInternal
            (
                outNumber, RaiseBaseTenToExponent(baseTenExp, exponent)
            );
        }
        
        return OperationsManaged.PassBaseTenToValue(outNumber, showUser);
    }

    static Number PowSqrtPreCheck(Number number, double exponent, boolean expIsInteger)
    {
        if (number.getValue() < 0.0 && !expIsInteger)
        {
            return new Number(ErrorTypesNumber.InvalidOperation);
        }
        if 
        (
        	number.equals(new Number(1.0)) || 
        	number.equals(new Number(0.0)) || exponent == 1.0
        ) 
        { return new Number(number); }
        
        if (exponent == 0.0) return new Number(1.0);
        if (number.equals(new Number(-1.0))) 
        {
        	return new Number(exponent % 2 == 0 ? 1.0 : -1.0);
        }

        return null;
    }

    static Number RaiseBaseTenToExponent(int existingBaseTen, double exponent)
    {
        double tempBaseTen = existingBaseTen * exponent;
        if 
        (
        	(existingBaseTen < 0 && tempBaseTen < Integer.MIN_VALUE - existingBaseTen) || 
        	(existingBaseTen > 0 && tempBaseTen > Integer.MAX_VALUE - existingBaseTen)
        )
        { return new Number(ErrorTypesNumber.NumericOverflow); }

        Number outNumber = new Number(1.0, (int)tempBaseTen);
        return
        (
            outNumber.getBaseTenExponent() == tempBaseTen ? 
            outNumber : OperationsManaged.MultiplyInternal
            (
                outNumber, new Number
                (
                	Math.pow
                	(
                		10.0, tempBaseTen - 
                		outNumber.getBaseTenExponent()
                	)
                )
            )
        );
    }
}
