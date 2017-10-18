package InternalNumberParser.Math2Internal;

import NumberParser.*;
import NumberParser.Number;
import InternalNumberParser.*;

public class Math2RoundTruncate
{
    public static Number RoundExactInternal(Number number, int digits, RoundType type, RoundSeparator separator)
    {
        ErrorTypesNumber error = ErrorInfoNumber.GetPowTruncateError(number);
        if (!error.equals(ErrorTypesNumber.None)) return new Number(error);

        if (digits <= 0) digits = 0;
        if (digits > Math2Common.Power10Double.length - 1)
        {
            digits = Math2Common.Power10Double.length - 1;
        }

        Number outNumber = new Number(number);

        outNumber.setValue
        (
        	Math.signum(number.getValue()) * RoundInternal
        	(
        		Math.abs(outNumber.getValue()), digits, type, separator
        	)
        );

        return outNumber;
    }

    private static double RoundInternal(double d, int digits, RoundType type, RoundSeparator separator)
    {
        return
        (
            digits == 0 || separator == RoundSeparator.BeforeDecimalSeparator ?
            RoundInternalBefore(d, digits, type) : RoundInternalAfter(d, digits, type)
        );
    }

    private static double RoundInternalBefore(double d, int digits, RoundType type)
    {
        if (digits == 0) return PerformRound(d, 0, type, Math.floor(d));

        int length = Math2Common.GetIntegerLength(d);

        return
        (
            digits > length ? d : RoundExactInternal
            (
                d, length - digits, type
            )
        );
    }

    private static double RoundInternalAfterNoZeroes(double d, double d2, int digits, RoundType type)
    {
        d2 = Math2Common.DecimalPartToInteger(d2, digits);
        int length2 = Math2Common.GetIntegerLength(d2);

        return
        (
            digits >= length2 ? d : Math.floor(d) +
            (
                RoundExactInternal
                (
                	d2, length2 - digits, type
                )
                / Math2Common.Power10Double[length2]
            )
        );
    }

    private static double RoundInternalAfterZeroes(double d, int digits, RoundType type, double d2, int zeroCount)
    {
        if (digits < zeroCount)
        {
            //Cases like 0.001 with 1 digit or 0.0001 with 2 digits can reach this point.
            //On the other hand, something like 0.001 with 2 digits requires further analysis.
            return Math.floor(d) +
            (
                !type.equals(RoundType.AlwaysAwayFromZero) ? 0.0 :
                1.0 / Math2Common.Power10Double[digits]
            );
        }

        //d3 represent the double part after all the heading zeroes.
        double d3 = d2 * Math2Common.Power10Double[zeroCount];
        d3 = Math2Common.DecimalPartToInteger(d3 - Math.floor(d3), 0, true);
        int length3 = Math2Common.GetIntegerLength(d3);

        double headingBit = 0;
        digits -= zeroCount;
        if (digits == 0)
        {
            //In a situation like 0.005 with 2 digits, the number to be analysed would be
            //05 what cannot be (i.e., treated as 5, something different). That's why, in 
            //these cases, adding a heading number is required.
            headingBit = 2; //2 avoids the ...ToEven types to be misinterpreted.
            d3 = headingBit * Math2Common.Power10Double[length3] + d3;
            digits = 0;
        }

        double output =
        (
            RoundExactInternal(d3, length3 - digits, type)
            / Math2Common.Power10Double[length3]
        )
        - headingBit;

        return Math.floor(d) +
        (
            output == 0.0 ? 0.0 :
            output / Math2Common.Power10Double[zeroCount]
        );
    }

    private static double RoundInternalAfter(double d, int digits, RoundType type)
    {
        double d2 = d - Math.floor(d);
        int zeroCount = Math2Common.GetHeadingDecimalZeroCount(d2);

        return
        (
            zeroCount == 0 ? RoundInternalAfterNoZeroes(d, d2, digits, type) :
            RoundInternalAfterZeroes(d, digits, type, d2, zeroCount)
        );
    }

    private static double RoundExactInternal(double d, int remDigits, RoundType type)
    {
        double rounded = PerformRound
        (
            d, remDigits, type,
            Math.floor(d / Math2Common.Power10Double[remDigits])
        );

        double rounded2 = rounded * Math2Common.Power10Double[remDigits];
        return
        (
            rounded2 > rounded ? rounded2 :
            d //A numeric overflow occurred.
        );
    }

    private static double PerformRound(double d, int remDigits, RoundType type, double rounded)
    {
        if (type == RoundType.AlwaysToZero || type == RoundType.AlwaysAwayFromZero)
        {
            return rounded + 
            (
                type == RoundType.AlwaysAwayFromZero ? 1.0 : 0.0
            );
        }
        else
        {
            int lastDigit = (int)(d / Math2Common.Power10Double[remDigits] % 10.0);

            if (type == RoundType.AlwaysToEven)
            {
                if (lastDigit % 2 != 0) rounded += 1.0;
            }
            else
            {
                int greaterEqual = MidPointGreaterEqual(d, remDigits, rounded);

                if (greaterEqual == 1) rounded += 1.0;
                else if (greaterEqual == 0)
                {
                    if (type == RoundType.MidpointAwayFromZero || (type == RoundType.MidpointToEven && lastDigit % 2 != 0))
                    {
                        rounded += 1.0;
                    }
                }
            }
        }

        return rounded;
    }

    private static int MidPointGreaterEqual(double d, int remDigits, double rounded)
    {
        return
        (
            remDigits > 0 ?
            //There are some additional digits after the last rounded one. It can be before or after. 
            //Example: 12345.6789 being rounded to 12000 or to 12345.68.
            MidPointGreaterEqualRem(d, remDigits, rounded) :
            //No additional digits after the last rounded one and the double digits have to be considered.
            //Only before is relevant here. Example: 12345.6789 rounded to 12345 and considering .6789.
            MidPointGreaterEqualNoRem(d, rounded)
        );
    }

    private static int MidPointGreaterEqualNoRem(double d, double rounded)
    {
        double d2 = d - rounded;
        d2 = Math2Common.DecimalPartToInteger(d2 - Math.floor(d2));
        int length2 = Math2Common.GetIntegerLength(d2);
        if (length2 < 1) return 0;

        int nextDigit = (int)(d2 / Math2Common.Power10Double[length2 - 1] % 10);
        if (nextDigit != 5) return (nextDigit < 5 ? -1 : 1);
        
        while (Math.floor(d2) != d2 && d2 < Math2Common.Power10Double[Math2Common.Power10Double.length - 1])
        {
            d2 *= 10;
            length2++;
        }

        int count = length2 - 1;
        while (count > 0)
        {
            count--;
            if ((int)(d2 / Math2Common.Power10Double[count] % 10) != 0)
            {
                //Just one digit different than zero is enough to conclude that is greater.
                return 1;
            }
        }

        return 0;
    }

    private static int MidPointGreaterEqualRem(double d, int remDigits, double rounded)
    {
        int nextDigit = (int)(d / Math2Common.Power10Double[remDigits - 1] % 10);
        if (nextDigit != 5) return (nextDigit < 5 ? -1 : 1);
        
        double middle = nextDigit * Math.floor(Math2Common.Power10Double[remDigits - 1]);
        return 
        (
            d - rounded * Math.floor(Math2Common.Power10Double[remDigits]) == middle ? 0 : 1
        );
    }
}
