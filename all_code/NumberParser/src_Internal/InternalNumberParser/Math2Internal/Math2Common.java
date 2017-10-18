package InternalNumberParser.Math2Internal;

import InternalNumberParser.*;
import NumberParser.*;

public class Math2Common
{
	//The differences between the original C# precision (decimal, over 26 decimal digits) and
	//the one in the current version (double, 6-8 decimal digits) are much more noticeable in all
	//the code associated with the Math2 methods. 
	//Similarly to what happens in other parts, I have preferred to keep most of the original 
	//comments, names and structures, even in cases where they aren't strictly required. 
	//That intention can logically not have a negative impact on the current piece of software 
	//and this is exactly what happens with all these resources like Power10Double.
	public static double[] Power10Double = PopulateRoundPower10Array();

    public static double[] PopulateRoundPower10Array()
    {
        return new double[]
        { 
            1e0, 1e1, 1e2, 1e3, 1e4, 1e5, 1e6, 1e7,
            1e8, 1e9, 1e10, 1e11, 1e12, 1e13, 1e14, 
            1e15, 1e16, 1e17, 1e18, 1e19, 1e20, 1e21, 
            1e22, 1e23, 1e24, 1e25, 1e26, 1e27, 1e28            
        };
    }
    
    //This method depends upon the decimal-type native precision/Math.floor and, consequently,
    //some extreme cases might be misunderstood. Example: 100000000000000000.00000000000001
    //outputting zero because of being automatically converted into 100000000000000000.
    //This method expects the input value to always be positive.
    public static int GetHeadingDecimalZeroCount(double d)
    {
        double d2 = (d > 1.0 ? d - Math.floor(d) : d);
        if (d2 == 0) return 0;

        int zeroCount = 0;
        while 
        (
        	d2 <= (double)Basic.AllNumberMinMaxPositives.get
        	(
        		NumericTypes.Double
        	)
        	.get(1) * 0.1
        )
        {
            d2 *= 10.0;
            if (Math.floor(d2 / Power10Double[0] % 10) != 0.0)
            {
                return zeroCount;
            }
            zeroCount++;
        }

        return zeroCount;
    }

    //This method expects the input value to always be positive.
    public static int GetIntegerLength(double d)
    {
        if (d == 0) return 0;

        for (int i = 0; i < Power10Double.length - 1; i++)
        {
            if (d < Power10Double[i + 1]) return i + 1;
        }

        return Power10Double.length;
    }

    public static double DecimalPartToInteger(double d2)
    {
    	return DecimalPartToInteger(d2, 0);
    }
    
    public static double DecimalPartToInteger(double d2, int digits)
    {
    	return DecimalPartToInteger(d2, digits, false);
    }
    
    //This method expects the input value to always be positive.
    public static double DecimalPartToInteger(double d2, int digits, boolean untilEnd)
    {
        if (digits + 1 >= Power10Double.length - 1)
        {
            d2 *= Power10Double[Power10Double.length - 1];
        }
        else
        {
            d2 *= Power10Double[digits + 1];
            double lastDigit = Math.floor(d2 / Power10Double[0] % 10);
            while 
            (
            	d2 < Power10Double[Power10Double.length - 3] && 
            	(untilEnd || (lastDigit > 0 && lastDigit <= 5.0))
            )
            {
                d2 *= 10;
                lastDigit = Math.floor(d2 / Power10Double[0] % 10);
            }
        }

        return d2;
    }
}
