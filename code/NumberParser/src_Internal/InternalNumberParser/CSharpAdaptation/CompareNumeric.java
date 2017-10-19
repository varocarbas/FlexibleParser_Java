package InternalNumberParser.CSharpAdaptation;

import InternalNumberParser.Conversions;
import NumberParser.*;

public class CompareNumeric
{
	public static boolean FirstGreaterOrEqual(Object first, Object second, NumericTypes type)
	{
		if (first == null || second == null)
		{
			return 
			(
				CompareToNulls(first, second) <= 0
			);
		}

		if (type.equals(NumericTypes.Double))
		{
			return 
			(
				CompareToDouble(first, second) <= 0
			);
		}
		else if (type.equals(NumericTypes.Float))
		{
			return 
			(
				CompareToFloat(first, second) <= 0
			);
		}
		else if (type.equals(NumericTypes.Long))
		{
			return 
			(
				CompareToLong(first, second) <= 0
			);
		}
		else if (type.equals(NumericTypes.Integer))
		{
			return 
			(
				CompareToInteger(first, second) <= 0
			);
		}
		else if (type.equals(NumericTypes.Short))
		{
			return 
			(
				CompareToShort(first, second) <= 0
			);
		}
		else if (type.equals(NumericTypes.Byte))
		{
			return 
			(
				CompareToByte(first, second) <= 0
			);
		}
		else if (type.equals(NumericTypes.Character))
		{
			return 
			(
				CompareToCharacter(first, second) <= 0
			);
		}
		
		return false;
	}
	
	public static boolean BothEqual(Object first, Object second, NumericTypes type)
	{
		if (first == null || second == null)
		{
			return 
			(
				CompareToNulls(first, second) == 0
			);
		}

		if (type.equals(NumericTypes.Double))
		{
			return 
			(
				CompareToDouble(first, second) == 0
			);
		}
		else if (type.equals(NumericTypes.Float))
		{
			return 
			(
				CompareToFloat(first, second) == 0
			);
		}
		else if (type.equals(NumericTypes.Long))
		{
			return 
			(
				CompareToLong(first, second) == 0
			);
		}
		else if (type.equals(NumericTypes.Integer))
		{
			return 
			(
				CompareToInteger(first, second) == 0
			);
		}
		else if (type.equals(NumericTypes.Short))
		{
			return 
			(
				CompareToShort(first, second) == 0
			);
		}
		else if (type.equals(NumericTypes.Byte))
		{
			return 
			(
				CompareToByte(first, second) == 0
			);
		}
		else if (type.equals(NumericTypes.Character))
		{
			return 
			(
				CompareToCharacter(first, second) == 0
			);
		}
		
		return false;
	}
	
	static int CompareToNulls(Object first, Object second)
	{
		int output = 0;
		
		if (first == null)
		{
			output = (second == null ? 0 : 1);
		}
		else if (second == null) output = -1;
		
		return output;
	}
	
	public static int CompareToDouble(Object first, Object second)
	{
		if (first == null || second == null)
		{
			return CompareToNulls(first, second);
		}		
		
		Double first2 = Conversions.ConvertTargetToDouble(first);
		Double second2 = Conversions.ConvertTargetToDouble(second);
		
		return first2.compareTo(second2);
	}
	
	public static int CompareToFloat(Object first, Object second)
	{
		if (first == null || second == null)
		{
			return CompareToNulls(first, second);
		}	
		
		Float first2 = (Float)first;
		Float second2 = (Float)second;
		
		return first2.compareTo(second2);
	}
	
	public static int CompareToLong(Object first, Object second)
	{
		if (first == null || second == null)
		{
			return CompareToNulls(first, second);
		}	
		
		Long first2 = (Long)first;
		Long second2 = (Long)second;
		
		return first2.compareTo(second2);
	}	
	
	public static int CompareToInteger(Object first, Object second)
	{
		if (first == null || second == null)
		{
			return CompareToNulls(first, second);
		}	
		
		Integer first2 = (Integer)first;
		Integer second2 = (Integer)second;
		
		return first2.compareTo(second2);
	}
	
	public static int CompareToShort(Object first, Object second)
	{
		if (first == null || second == null)
		{
			return CompareToNulls(first, second);
		}	
		
		Short first2 = (Short)first;
		Short second2 = (Short)second;
		
		return first2.compareTo(second2);
	}
	
	public static int CompareToByte(Object first, Object second)
	{
		if (first == null || second == null)
		{
			return CompareToNulls(first, second);
		}	
		
		Byte first2 = (Byte)first;
		Byte second2 = (Byte)second;
		
		return first2.compareTo(second2);
	}	
	
	public static int CompareToCharacter(Object first, Object second)
	{
		if (first == null || second == null)
		{
			return CompareToNulls(first, second);
		}	
		
		Character first2 = (Character)first;
		Character second2 = (Character)second;
		
		return first2.compareTo(second2);
	}		
}
