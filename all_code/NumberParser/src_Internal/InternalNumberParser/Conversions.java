package InternalNumberParser;

import NumberParser.*;
import NumberParser.Number;
import InternalNumberParser.CSharpAdaptation.*;
import InternalNumberParser.Operations.*;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Conversions
{
    public static NumberD ConvertNumberToAny(Number number, NumericTypes target)
    {    	
        ErrorTypesNumber error = ErrorInfoNumber.GetConvertToAnyError(number, target);
        if (!error.equals(ErrorTypesNumber.None)) return new NumberD(error);
    	
        NumberD outNumber = new NumberD(number);
		ArrayList<Double> minMax = new ArrayList<Double>()
        {{
            add
            (
            	ConvertToDoubleInternal
            	(
            		Basic.AllNumberMinMaxs.get(target).get(0)
            	)
            );
            add
            (
            	ConvertToDoubleInternal
            	(
            		Basic.AllNumberMinMaxs.get(target).get(1)
            	)
            );
        }};

        if 
        (
        	ConvertTargetToDouble(outNumber.getValue()) < minMax.get(0) || 
        	ConvertTargetToDouble(outNumber.getValue()) > minMax.get(1)
        )
        {
            outNumber = new NumberD
            (
            	AdaptValueToTargetRange(outNumber, target, minMax)
            );   
        }

        outNumber.setValue
        (
        	CSharpOtherNP.ConvertDoubleToTarget
        	(
        		ConvertTargetToDouble
        		(
        			//This call to Math.round is required to emulate the in-built behaviour of
        			//the decimal type in the original C# version.
        			Math.round
        			(
        				ConvertTargetToDouble
        				(
        					outNumber.getValue()
        				)
        			)
        		), 
        		target
        	)
        );

        return outNumber;
    }

    private static Number AdaptValueToTargetRange(NumberD outNumber, NumericTypes target, ArrayList<Double> minMax)
    {    	
    	double value = Conversions.ConvertTargetToDouble
    	(
    		outNumber.getValue()
    	);
        if (value == 0.0) return new Number(outNumber);

        double targetValue =
        (
        	value < minMax.get(0) ? 
            minMax.get(0) : minMax.get(1)
        );

        if (value < 0.0 && Basic.AllUnsignedTypes.contains(target))
        {
            outNumber.setValue(Math.abs(value));

            if (value > minMax.get(1))
            {
                //The resulting unsigned value (its absolute value, because the result of converting -123
                //to a zero-based scale is 123) is outside the range of the given type and that's it needs
                //further adaptation.
                targetValue = minMax.get(1);
            }
            else return new Number(outNumber);
        }

        return ModifyValueToFitType
        (
        	new Number(outNumber), target, targetValue
        );
    }

	static Number ModifyValueToFitType(Number number, NumericTypes target, double targetValue)
	{
		double sign = 1.0;
		double value = number.getValue();
		
		if (value < 0)
		{
			sign = -1.0;
			number.setValue(value * sign);
		}

		if (!Basic.AllDecimalTypes.contains(target))
		{
			value = Conversions.ConvertTargetToDouble
			(
				Math2.Round3
				(
					new NumberD(number), 
					MidpointRounding.AwayFromZero
				)
				.getValue()
			);
		}
		else value = number.getValue();
		
		targetValue = Math.abs(targetValue);
		boolean increase = (value < targetValue);
		int baseTenExponent = number.getBaseTenExponent();
		
        //Apparently, the precision problems of the (Java) Double type appear more easily with
        //divisions. That's why I am including here two variations: to rely on multiplications
		//in all the possible scenarios.
        double varMult = 10.0;
        double varDiv = 0.1;
		
		while (true)
		{
			if (value == targetValue) break;
			else
			{
				if (increase)
				{
					if 
					(
						value > (double)Basic.AllNumberMinMaxPositives.get
						(
							NumericTypes.Double
						)
						.get(1) * varDiv
					)
					{ break; }

					value *= varMult;
					baseTenExponent--;
					if (value > targetValue) break;
				}
				else
				{
					if
					(
						value < (double)Basic.AllNumberMinMaxPositives.get
						(
							NumericTypes.Double
						)
						.get(0) * varMult
					)
					{ break; }

					value *= varDiv;
					baseTenExponent++;
					if (value < targetValue) break;
				}
			}
		}

		value *= sign;
		number.setValue(value);
		number.setBaseTenExponent(baseTenExponent);
		
		return number;
	}

	static double ConvertTargetToDoubleInternal(Object input, NumericTypes type)
	{
    	if (type.equals(NumericTypes.Double))
    	{
    		return (double)input;
    	}
    	else if (type.equals(NumericTypes.Float))
    	{
    		return ((Float)input).doubleValue();
    	}
    	else if (type.equals(NumericTypes.Long))
    	{
    		return ((Long)input).doubleValue();
    	}
    	else if (type.equals(NumericTypes.Integer))
    	{
    		return ((Integer)input).doubleValue();
    	}
    	else if (type.equals(NumericTypes.Short))
    	{
    		return ((Short)input).doubleValue();
    	}
    	else if (type.equals(NumericTypes.Byte))
    	{
    		return ((Byte)input).doubleValue();
    	}
    	else if (type.equals(NumericTypes.Character))
    	{
    		return (new Integer((char)input)).doubleValue();
    	}
    	
    	return Double.NaN;
	}
	
    public static double ConvertTargetToDouble(Object input)
    {
    	NumericTypes type = NumericTypesMethods.GetTypeFromObject(input);
    	
    	return
    	(
    		type.equals(NumericTypes.None) ? 0.0 :
    		ConvertTargetToDoubleInternal(input, type)	
    	);		
    }

    //Note that double is used as a backup type in quite a few situations. 
    //This method complements the in-built Convert.ToDouble method by accounting for situations like
    //char type variables.
    public static double ConvertToDoubleInternal(Object value)
    {
    	NumericTypes type = ErrorInfoNumber.InputTypeIsValidNumeric(value);
    	
        return
        (
        	type.equals(NumericTypes.None) ? Double.NaN :
        	ConvertTargetToDoubleInternal(value, type)
        );
    }

    //When calling methods with different overloads (e.g., some of the System.Math ones),
    //the Object variables need to be cast to a specific type.
    //This method assumes that input can be directly cast to the target numeric type.
    //NOTE: due to the differences between Java and C# (language in which this method was
    //created in the original code), all the casts are replaced with conversions.
    public static Object CastDynamicToType(Object input, NumericTypes target)
    {
    	return CSharpOtherNP.CastConvertNumericObjectToTarget(input, target);
    }
    
    //This method assumes that numberD is a non-null valid NumberD variable.
    public static Number ConvertNumberDToNumber(NumberD numberD)
    {
        Number outNumber = ConvertAnyValueToDouble(numberD.getValue());

        return (Number)OperationsOther.VaryBaseTenExponent
        (
            outNumber, numberD.getBaseTenExponent()
        );
    }

    //This method assumes that both value and type refer to valid numeric types.
    public static Number ConvertAnyValueToDouble(Object value)
    {
    	if (ErrorInfoNumber.InputTypeIsValidNumeric(value).equals(NumericTypes.None))
    	{
    		return new Number(ErrorTypesNumber.InvalidInput);
    	}
    	
    	Number output =	new Number();
    	output.setValue(ConvertToDoubleInternal(value));
    	
    	return output;
    }
    
	static Number ConvertFloatingToDouble2
	(
		Object value, NumericTypes type, Object[] minMax, 
		Object step, Number outNumber
	)
	{
		if (type.equals(NumericTypes.Double))
		{
			double value2 = (double)value;
			double step2 = (double)step;
			double[] minMax2 = new double[] 
			{
				(double)minMax[0], (double)minMax[1]
			};
			
			while (Math.abs(value2) < minMax2[0])
			{
				outNumber.setBaseTenExponent
				(
					outNumber.getBaseTenExponent() - 1
				);
				value2 *= step2;
			};

			while (Math.abs(value2) > minMax2[1])
			{
				outNumber.setBaseTenExponent
				(
					outNumber.getBaseTenExponent() + 1
				);
				value2 /= step2;
			};

			outNumber.setValue(value2);				
		}
		else
		{
			float value2 = (float)value;
			float step2 = (float)step;
			float[] minMax2 = new float[] 
			{
				(float)minMax[0], (float)minMax[1]
			};
			
			while (Math.abs(value2) < minMax2[0])
			{
				outNumber.setBaseTenExponent
				(
					outNumber.getBaseTenExponent() - 1
				);
				value2 *= step2;
			};

			while (Math.abs(value2) > minMax2[1])
			{
				outNumber.setBaseTenExponent
				(
					outNumber.getBaseTenExponent() + 1
				);
				value2 /= step2;
			};

			outNumber.setValue((double)value2);				
		}
			
		return outNumber;
	}
	
	//This method expects value to be a valid double variable.
	public static Number ConvertDoubleToNumber(double value)
	{
		Number outNumber = new Number();
		if (value == 0.0) return outNumber;

		double step = 10.0;

		double[] minMax = new double[] 
		{
			(double)Basic.AllNumberMinMaxPositives.get
			(
				NumericTypes.Double
			)
			.get(0), (double)Basic.AllNumberMinMaxPositives.get
			(
				NumericTypes.Double
			)
			.get(1)
		};

		//Note that value can only be double or float and these types cannot provoke an overflow when 
		//using Math.Abs. Additionally, using Operations.AbsInternal right away isn't possible here 
		//because of provoking an infinite loop.
		while (Math.abs(value) < minMax[0])
		{
			outNumber.setBaseTenExponent
			(
				outNumber.getBaseTenExponent() - 1
			);
			value *= step;
		};
		
		while (Math.abs(value) > minMax[1])
		{
			outNumber.setBaseTenExponent
			(
				outNumber.getBaseTenExponent() + 1
			);
			value /= step;
		}
		
		outNumber.setValue(value);
		
		return outNumber;
	}	
}
