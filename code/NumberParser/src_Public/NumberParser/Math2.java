package NumberParser;

import java.util.ArrayList;

import InternalNumberParser.*;
import InternalNumberParser.CSharpAdaptation.*;
import InternalNumberParser.Math2Internal.*;
import InternalNumberParser.Operations.*;

/**Math2 contains all the mathematical resources of NumberParser**/
@SuppressWarnings("serial")
public class Math2
{
    //---------------------------- All the public existing methods (mixture of Java and .NET).

	/**
    NumberD-adapted version of Math.abs.
    @param n Input value.
    **/
    public static NumberD Abs(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Abs
        );
    }

    /**
    NumberD-adapted version of Math.acos.
    @param n Input values.
    **/
    public static NumberD Acos(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Acos
        );
    }

    /**
    NumberD-adapted version of Math.asin.
    @param n Input value.
    **/
    public static NumberD Asin(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Asin
        );
    }

    /**
    NumberD-adapted version of Math.atan.
    @param n Input value.
    **/
    public static NumberD Atan(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Atan
        );
    }

    /**
    NumberD-adapted version of Math.atan2.
    @param n1 First value.
    @param n2 Second value.
    **/
    public static NumberD Atan2(NumberD n1, NumberD n2)
    {
        return Math2Existing.PerformOperationTwoOperands
        (
        	n1, n2 , ExistingOperations.Atan2
        );
    }

    /**
    NumberD-adapted version of .NET System.Math.BigMul.
    @param n1 First value to multiply.
    @param n2 Second value to multiply.
    **/
    public static NumberD BigMul(NumberD n1, NumberD n2)
    {
        try
        {
            NumberD n12 = Math2Existing.AdaptInputsToMathMethod
            (
            	n1, Basic.GetSmallIntegers(), ExistingOperations.BigMul
            );
            if (!n12.getError().equals(ErrorTypesNumber.None))
            {
                return new NumberD(n12.getError());
            }

			NumberD n22 = Math2Existing.AdaptInputsToMathMethod
            (
            	n2, new ArrayList<NumericTypes>() 
            	{{ 
            		add(n12.getType()); 
            	}}, 
            	ExistingOperations.BigMul
            );
            if (!n22.getError().equals(ErrorTypesNumber.None))
            {
                return new NumberD(n22.getError());
            }

            return new NumberD
            (
            	(long)n12.getValue() * 
            	(long)n22.getValue()
            );
        }
        catch (Exception e)
        {
            return new NumberD
            (
            	ErrorTypesNumber.NativeMethodError
            );
        }
    }

    /**
    NumberD-adapted version of Math.ceil.
    @param n Input value.
    **/
    public static NumberD Ceiling(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Ceiling
        );
    }

    /**
    NumberD-adapted version of Math.cos.
    @param n Input value.
    **/
    public static NumberD Cos(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Cos
        );
    }

    /**
    NumberD-adapted version of Math.cosh.
    @param n Input value.
    **/
    public static NumberD Cosh(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Cosh
        );
    }

    public static NumberD DivRemOutput = null;
    /**
    NumberD-adapted version of .NET System.Math.DivRem.
    @param n1 Dividend.
    @param n2 Divisor.
    **/
    public static NumberD DivRem(NumberD n1, NumberD n2)
    {
        NumberD n12 = Math2Existing.AdaptInputsToMathMethod
        (
        	n1, Basic.GetSmallIntegers(), ExistingOperations.DivRem
        );
        if (!n12.getError().equals(ErrorTypesNumber.None))
        {
        	DivRemOutput = new NumberD(n12.getError());
            return new NumberD(n12.getError());
        }
        
        ArrayList<NumericTypes> tempArray = new ArrayList<NumericTypes>();
        tempArray.add(n12.getType());
        NumberD n22 = Math2Existing.AdaptInputsToMathMethod
        (
        	n2, tempArray, ExistingOperations.DivRem
        );
        if 
        (
        	!n22.getError().equals(ErrorTypesNumber.None) || 
        	(double)n22.getValue() == 0.0
        )
        {
        	DivRemOutput = new NumberD(n22.getError());
            return new NumberD(n22.getError());
        }

        n12 = OperationsManaged.PassBaseTenToValue(n12);
        n22 = OperationsManaged.PassBaseTenToValue(n22);

        NumberD outNumber = null;
        try
        {
            if (n12.getType().equals(NumericTypes.Long))
            {
            	long val1 = (long)n12.getValue();
            	long val2 = (long)n22.getValue(); 
                
                outNumber = new NumberD(val1 / val2);
                DivRemOutput = new NumberD(val1 % val2);
            }
            else
            {
            	int val1 = (int)n12.getValue();
            	int val2 = (int)n22.getValue(); 
                
                outNumber = new NumberD(val1 / val2);
                DivRemOutput = new NumberD(val1 % val2);
            }
        }
        catch(Exception e)
        {
            outNumber = new NumberD(ErrorTypesNumber.NativeMethodError);
            DivRemOutput = new NumberD(ErrorTypesNumber.NativeMethodError);
        }

        return outNumber;
    }

    /**
    NumberD-adapted version of Math.exp.
    @param n Value to which e will be raised.
    **/
    public static NumberD Exp(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Exp
        );
    }

    /**
    NumberD-adapted version of Math.floor.
    @param n Input value.
    **/
    public static NumberD Floor(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Floor
        );
    }

    /**
    NumberD-adapted version of Math.IEEEremainder.
    @param n1 Dividend.
    @param n2 Divisor.
    **/
    public static NumberD IEEERemainder(NumberD n1, NumberD n2)
    {
        return Math2Existing.PerformOperationTwoOperands
        (
        	n1, n2, ExistingOperations.IEEERemainder
        );
    }

    /**
    NumberD-adapted version of Math.log.
    @param n Value whose base-n logarithm will be calculated.
    **/
    public static NumberD Log(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Log
        );
    }

    /**
    NumberD-adapted version of .NET System.Math.Log.
    @param n1 Value whose logarithm will be calculated.
    @param n2 Base of the logarithm.
    **/
    public static NumberD Log(NumberD n1, NumberD n2)
    {
        return Math2Existing.PerformOperationTwoOperands
        (
        	n1, n2, ExistingOperations.Log
        );
    }

    /**
    NumberD-adapted version of Math.log10.
    @param n Value whose base-10 logarithm will be calculated.
    **/
    public static NumberD Log10(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Log10
        );
    }

    /**
    NumberD-adapted version of Math.max.
    @param n1 First value.
    @param n2 Second value.
    **/
    public static NumberD Max(NumberD n1, NumberD n2)
    {
        return Math2Existing.PerformOperationTwoOperands
        (
        	n1, n2, ExistingOperations.Max
        );
    }

    /**
    NumberD-adapted version of Math.min.
    @param n1 First value.
    @param n2 Second value.
    **/
    public static NumberD Min(NumberD n1, NumberD n2)
    {
        return Math2Existing.PerformOperationTwoOperands
        (
        	n1, n2, ExistingOperations.Min
        );
    }

    /**
    NumberD-adapted version of Math.pow.
    @param n1 Base.
    @param n2 Exponent.
    **/
    public static NumberD Pow(NumberD n1, NumberD n2)
    {
    	Number n12 = Math2Existing.PowSqrtPrecheckInput(n1);
    	Number n22 = Math2Existing.PowSqrtPrecheckInput(n2);

    	boolean isOK = (n12 != null && n22 != null);
    	Number output = null;
    	if (isOK)
    	{
    		output = Math2.PowDecimal(n12, n22.getValue());
    		if 
    		(
    			!output.getError().equals(ErrorTypesNumber.None) || 
    			output.greaterThan(new Number(Double.MAX_VALUE)) ||
    			output.lessThan(new Number(Double.MIN_VALUE))
    		) 
    		{ isOK = false; }
    	}

    	return 
    	(
    		isOK ? new NumberD(output) :
    		new NumberD(ErrorTypesNumber.NativeMethodError)
    	);
    }

    /**
    NumberD-adapted version of .NET System.Math.Round.
    @param n Input value.
    **/
    public static NumberD Round1(NumberD n, MidpointRounding mode)
    {
        return n;
    }

    /**
    NumberD-adapted version of .NET System.Math.Round.
    @param n Input value.
    @param decimals Number of decimal places.
    **/
    public static NumberD Round2(NumberD n, int decimals)
    {
        return Round(n, decimals, MidpointRounding.ToEven);
    }

    /**
    NumberD-adapted version of .NET System.Math.Round.
    @param n Input value.
    @param mode Midpoint rounding mode.
    **/
    public static NumberD Round3(NumberD n, MidpointRounding mode)
    {
        return Round(n, 0, mode);
    }

    /**
    NumberD-adapted version of .NET System.Math.Round.
    @param n Input value.
    @param decimals Number of decimal places.
    @param mode Midpoint rounding mode.
    **/
    public static NumberD Round(NumberD n, int decimals, MidpointRounding mode)
    {
        try
        {
            NumberD n2 = Math2Existing.AdaptInputsToMathMethod
            (
                n, Basic.AllDecimalTypes, ExistingOperations.Round
            );

            return new NumberD
            (
            	RoundExact
            	(
            		new Number(n2), 
            		decimals, SystemMath.GetNewRoundItemFromExisting
            		(
            			mode
            		), 
            		RoundSeparator.AfterDecimalSeparator
            	)
            );
        }
        catch(Exception e)
        {
            return new NumberD(ErrorTypesNumber.NativeMethodError);
        }
    }

    /**
    NumberD-adapted version of Math.signum.
    @param n Input value.
    **/
    public static NumberD Sign(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Sign
        );
    }

    /**
    NumberD-adapted version of Math.sin.
    @param n Input value.
    **/
    public static NumberD Sin(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Sin
        );
    }

    /**
    NumberD-adapted version of Math.sinh.
    @param n Input value.
    **/
    public static NumberD Sinh(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Sinh
        );
    }

    /**
    NumberD-adapted version of Math.sqrt.
    @param n Input value.
    **/
    public static NumberD Sqrt(NumberD n)
    {
    	Number n2 = Math2Existing.PowSqrtPrecheckInput(n);

    	boolean isOK = (n2 != null);
    	Number output = null;
    	if (isOK)
    	{
    		output = Math2.SqrtDecimal(n2);
    		if 
    		(
    			!output.getError().equals(ErrorTypesNumber.None) || 
    			output.greaterThan(new Number(Double.MAX_VALUE)) ||
    			output.lessThan(new Number(Double.MIN_VALUE))
    		) 
    		{ isOK = false; }
    	}
    	
    	return 
    	(
    		isOK ? new NumberD(output) :
    		new NumberD(ErrorTypesNumber.NativeMethodError)
    	);
    }

    /**
    NumberD-adapted version of Math.tan.
    @param n Input value.
    **/
    public static NumberD Tan(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Tan
        );
    }

    /**
    NumberD-adapted version of System.tanh.
    @param n Input value.
    **/
    public static NumberD Tanh(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Tanh
        );
    }

    /**
    NumberD-adapted version of .NET System.Math.Truncate.
    @param n Input value.
    **/
    public static NumberD Truncate(NumberD n)
    {
        return Math2Existing.PerformOperationOneOperand
        (
        	n, ExistingOperations.Truncate
        );
    }
    
    //---------------------------- All the public new methods.

    /**
    Returns the dependent variable (y), as defined by y = A + B*x + C*x^2.
    @param polynomial Coefficients (A, B, C) defining the given polynomial fit.
    @param x Independent variable (x).
    **/
    public static NumberD ApplyPolynomialFit(Polynomial polynomial, NumberD x)
    {
        return Math2Regression.ApplyPolynomialFitInternal(polynomial, x);
    }

    /**
    Determines (least squares) the best polynomial fit for the input x/y sets.
    @param x Array containing all the independent variable (x) values. It has to contain the same number of elements than y.
    @param y Array containing all the dependent variable (y) values. It has to contain the same number of elements than x.
    **/
    public static Polynomial GetPolynomialFit(NumberD[] x, NumberD[] y)
    {
        return Math2Regression.GetPolynomialFitInternal(x, y);
    }

    /**
    Calculates the factorial of input value.
    @param n Input value. It has to be smaller than 100000.
    **/
    public static NumberD Factorial(NumberD n)
    {
        return Math2Other.FactorialInternal(n);
    }

    /**
    Calculates the square root of the input value.
    It does NOT rely on the custom approach of the original C# version, but on Math.sqrt.
    To know about this, read the top comment in InternalNumberParser.Math2Internal.Math2PowSqrt
    @param n Input value.
    **/
    public static Number SqrtDecimal(Number n)
    {
        return Math2PowSqrt.PowSqrtInternal(n, 0.5);
    }

    /**
    Raises the input value to the exponent.
    It does NOT rely on the custom approach of the original C# version, but on Math.pow.
    To know about this, read the top comment in InternalNumberParser.Math2Internal.Math2PowSqrt
    @param n Input value.
    @param exponent Exponent.
    **/
    public static Number PowDecimal(Number n, double exponent)
    {
        return Math2PowSqrt.PowSqrtInternal(n, exponent);
    }

    /**
    Truncates the input value as instructed.
    @param n Input value.
    **/
    public static Number TruncateExact(Number n)
    {
        return TruncateExact(n, 0);
    }

    /**
    Truncates the input value as instructed.
    @param n Input value.
    @param decimals Number of decimal positions in the result.
    **/
    public static Number TruncateExact(Number n, int decimals)
    {
        return RoundExact
        (
            n, decimals, RoundType.AlwaysToZero, 
            RoundSeparator.AfterDecimalSeparator
        );
    }

    /**
    Rounds the input value as instructed.
    @param n Input value.
    **/
    public static Number RoundExact(Number n)
    {
        return RoundExact(n, 0);
    }

    /**
    Rounds the input value as instructed.
    @param n Input value.
    @param digits Number of digits to be considered when rounding.
    **/
    public static Number RoundExact(Number n, int digits)
    {
        return RoundExact
        (
            n, digits, RoundType.MidpointToEven, 
            RoundSeparator.AfterDecimalSeparator
        );
    }

    /**
    Rounds the input value as instructed.
    @param n Input value.
    @param type Type of rounding.
    **/
    public static Number RoundExact(Number n, RoundType type)
    {
        return RoundExact
        (
            n, 0, type, RoundSeparator.AfterDecimalSeparator
        );
    }

    /**
    Rounds the input value as instructed.
    @param n Input value.
    @param digits Number of digits to be considered.
    @param type Type of rounding.
    **/
    public static Number RoundExact(Number n, int digits, RoundType type)
    {
        return RoundExact
        (
            n, digits, type, RoundSeparator.AfterDecimalSeparator
        );
    }

    /**
    Rounds the input value as instructed.
    @param n Input value.
    @param digits Number of digits to be considered.
    @param separator Location of the digits to be rounded (before or after the decimal separator).
    **/
    public static Number RoundExact(Number n, int digits, RoundSeparator separator)
    {
        return RoundExact
        (
            n, digits, RoundType.MidpointToEven, separator
        );
    }

    /**
    Rounds the input value as instructed.
    @param n Input value.
    @param digits Number of digits to be considered.
    @param type Type of rounding.
    @param separator Location of the digits to be rounded (before or after the decimal separator).
    **/
    public static Number RoundExact
    (
    	Number n, int digits, RoundType type, RoundSeparator separator
    )
    {
        return Math2RoundTruncate.RoundExactInternal
        (
            n, digits, type, separator
        );
    }

    /**Decimal version of Math.PI. First 28 decimal digits with no rounding.**/
    public static final double PI = 3.1415926535897932384626433832;
    /**Decimal version of Math.E. First 28 decimal digits with no rounding.**/
    public static final double E = 2.7182818284590452353602874713;    
}
