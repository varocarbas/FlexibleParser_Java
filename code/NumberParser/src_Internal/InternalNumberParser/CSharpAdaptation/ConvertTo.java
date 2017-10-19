package InternalNumberParser.CSharpAdaptation;

import NumberParser.*;

public class ConvertTo
{
	public static double ConverToDouble(Object input)
	{
		if (input == null) return Double.NaN;

		NumericTypes type = 
		NumericTypesMethods.GetTypeFromObject
		(
			input
		);
		if (type.equals(NumericTypes.Float))
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
			return new Integer
			(
				Character.getNumericValue((char)input)
			)
			.doubleValue();
		}
		
		return 0.0;
	}
}
