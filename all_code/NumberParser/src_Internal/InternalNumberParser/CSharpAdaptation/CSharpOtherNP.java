package InternalNumberParser.CSharpAdaptation;

import java.util.ArrayList;
import java.util.Locale;

import InternalNumberParser.*;
import InternalNumberParser.Operations.OperationsOther;
import NumberParser.*;
import NumberParser.Number;

@SuppressWarnings("serial")
public class CSharpOtherNP
{
    //Method delivering the most adequate NumberD instance by eventually compensating the
	//Java/C# differences on the implicit conversion of numeric types front.
    public static NumberD ExtractValueAndTypeInfo(Object value, int baseTenExponent, NumericTypes type)
    {
        NumericTypes typeValue = ErrorInfoNumber.InputTypeIsValidNumeric(value);
        if (typeValue.equals(NumericTypes.None))
        {
            return new NumberD(ErrorTypesNumber.InvalidInput);
        }
        
        return
        (
            typeValue.equals(type) ? new NumberD(value, baseTenExponent) :
            (NumberD)OperationsOther.VaryBaseTenExponent
            (
                Conversions.ConvertNumberToAny
                (
                    Conversions.ConvertAnyValueToDouble(value), type
                ),
                baseTenExponent
            )
        );
    }	
    
	public static String SpecificDoubleOrNumberXToString(Object input, Locale culture)
	{
		if (input == null) return "";

		NumericTypes type = NumericTypesMethods.GetTypeFromObject
		(
			input
		);
		
		return
		(
			!type.equals(NumericTypes.None) ? NumericValueToString
			(
				Conversions.ConvertTargetToDouble
				(
					input
				)
				, culture, type
			) 
			: SpecificNumberXToString(input, culture)	
		);
	}
	
	public static String SpecificNumberXToString(Object input)
	{
		return SpecificNumberXToString(input, Locale.US);
	}
	
	static String SpecificNumberXToString(Object input, Locale culture)
	{
		if (input == null) return null;
		
		if (input.getClass().equals(Number.class))
		{
			return ((Number)input).toString(culture);
		}
		else if (input.getClass().equals(NumberD.class))
		{
			return ((NumberD)input).toString(culture);
		}
		else if (input.getClass().equals(NumberO.class))
		{
			return ((NumberO)input).toString(culture);
		}	
		else if (input.getClass().equals(NumberP.class))
		{
			return ((NumberP)input).toString(culture);
		}
		
		return null;
	}

	//Method emulating the .NET numeric-type ToString() method by focusing on the three 
	//categories which are relevant here: decimal/exponential, integer and character.
	public static String NumericValueToString(Object input, Locale culture, NumericTypes type)
	{
		Object input2 = input;
		
		NumericTypes type0 = NumericTypesMethods.GetTypeFromObject(input);
		if (!type0.equals(type))
		{
			input2 = CSharpOtherNP.CastConvertNumericObjectToTarget(input, type);
		}
		
		if (type.equals(NumericTypes.Character))
		{
			return ((Character)input2).toString();
		}
		
		return 
		(
			Basic.AllDecimalTypes.contains(type) ? 
			DecimalTypeToString(input2, culture, type) : 
			IntegerTypeToString(input2, type)
		);
	}
	
	//This method expects a valid instance of any integer numeric type except Character.
	static String IntegerTypeToString(Object input, NumericTypes type)
	{
		Long input2 = 0l;

		if (type.equals(NumericTypes.Long))
		{
			input2 = (long)input;
		}
		else if (type.equals(NumericTypes.Integer))
		{
			input2 = ((Integer)input).longValue();
		}
		else if (type.equals(NumericTypes.Short))
		{
			input2 = ((Short)input).longValue();
		}
		else if (type.equals(NumericTypes.Byte))
		{
			input2 = ((Byte)input).longValue();
		}

		return input2.toString();	
	}
	
	//This method expects a valid instance of any decimal numeric type except.
	static String DecimalTypeToString(Object input, Locale culture, NumericTypes type)
	{
		String format = "%.12e"; 
		double input2 = 0.0;
		
		if (type.equals(NumericTypes.Double))
		{
			input2 = (double)input;
		}
		else 
		{
			input2 = ((Float)input).doubleValue();
		}
		
		if 
		(
			input2 == 0.0 || 
			(
				input2 >= 0.0000001 && 
				input2 <= 1000000
			)
		)
		{ format = "%s"; }

		return 
		(
			culture == null ? String.format(format, input) : 
			String.format(culture, format, input)	
		);
	}
	
	public static ArrayList<String> SplitTryCatch(String input, String separator)
	{
		if (input == null || separator == null) 
		{
			return new ArrayList<String>() 
			{{ 
				add(input); 
			}};
		}
		
		try
		{
			return ArrayToArrayList
			(
				input.split(separator)
			);
		}
		catch(Exception e) { }
		
		return new ArrayList<String>() 
		{{ 
			add(input); 
		}};
	}

	public static String Substring(String input, int startI, int length)
	{
		return
		(
			length < 0 ? input.substring(startI) :
			input.substring(startI, startI + length)
		);
	}
	
	public static ArrayList<String> SplitTryCatchMulti(String input, ArrayList<String> separators)
	{
		ArrayList<String> output = new ArrayList<String>() 
		{{ 
			add(input); 
		}};
		
		if (input == null || separators == null || separators.size() < 1) 
		{
			return output;
		}
		
		for (String separator: separators)
		{
			for (int i = output.size() - 1; i >= 0; i--)
			{
				ArrayList<String> tempArray = SplitTryCatch
				(
					output.get(i), separator
				);
				
				if (tempArray.size() > 1)
				{
					output.remove(i);
					output.addAll(i, tempArray);
				}
			}
		}
		
		return output;
	}
	
	public static ArrayList<Character> StringToCharacters(String input)
	{
		ArrayList<Character> chars = new ArrayList<Character>();
		if (input == null) return chars;
		
		for (char item: input.toCharArray())
		{
			chars.add(item);
		}
		
		return chars;
	}
	
	static <X> ArrayList<X> ArrayToArrayList(X[] input)
	{
		ArrayList<X> output = new ArrayList<X>();
		
		for (X item: input)
		{
			output.add(item);
		}
		
		return output;
	}
	
	public static String StringJoin(String separator, ArrayList<String> parts)
	{
		return StringJoinInternal
		(
			separator, parts, null, 0, parts.size()
		);
	}
	
	static String StringJoinInternal
	(
		String separator, ArrayList<String> parts, 
		ArrayList<Character> parts2, int start, int length
	)
	{
		if (parts == null && parts2 == null) return null;
		
		int total = 
		(
			parts == null ? parts2.size() : parts.size()
		);
		if (total == 1)
		{
			return 
			(
				parts == null ? parts2.get(0).toString() : parts.get(0)
			);
		}

		String outString = StringJoinInternalBit(parts, parts2, start, "");
		
		for (int i = start + 1; i < length; i++)
		{
			outString += StringJoinInternalBit(parts, parts2, i, separator);
		}
		
		return outString;		
	}
	
	static String StringJoinInternalBit
	(
		ArrayList<String> parts, ArrayList<Character> parts2, int i, String separator
	)
	{
		String bit = "";
		
		if (parts == null)
		{
			if (parts2.size() >= i - 1)
			{
				bit = parts2.get(i).toString();				
			}
		}
		else if (parts.size() >= i - 1)
		{
			bit = parts.get(i);				
		}
		
		return separator + bit;
	}	
	
	public static Object CastConvertNumericObjectToTarget(Object input, NumericTypes target)
	{
    	NumericTypes type0 = NumericTypesMethods.GetTypeFromObject(input);
    	if (type0.equals(NumericTypes.None)) return input;
    	
    	if (type0.equals(NumericTypes.Double))
    	{
    		return ConvertDoubleToTarget((double)input, target);
    	}
    	else if (type0.equals(NumericTypes.Float))
    	{
    		return ConvertFloatToTarget((float)input, target);
    	}
    	else if (type0.equals(NumericTypes.Long))
    	{
    		return ConvertLongToTarget((long)input, target);
    	}
    	else if (type0.equals(NumericTypes.Integer))
    	{
    		return ConvertIntegerToTarget((int)input, target);
    	}
    	else if (type0.equals(NumericTypes.Short))
    	{
    		return ConvertShortToTarget((short)input, target);
    	}
    	else if (type0.equals(NumericTypes.Byte))
    	{
    		return ConvertByteToTarget((byte)input, target);
    	}
    	else if (type0.equals(NumericTypes.Character))
    	{
    		return ConvertCharacterToTarget((char)input, target);
    	}    	
    	
    	return input;		
	}
	
    public static Object ConvertCharacterToTarget(Character value, NumericTypes type)
    {
    	if (type.equals(NumericTypes.Character)) return value;
    	
    	Integer value2 = new Integer(value);
    	
    	if (type.equals(NumericTypes.Double))
        {
        	return value2.doubleValue();
        }
    	else if (type.equals(NumericTypes.Float))
        {
        	return value2.floatValue();
        }
        else if (type.equals(NumericTypes.Long))
        {
        	return value2.longValue();
        }
        else if (type.equals(NumericTypes.Integer))
        {
            return value2.intValue();
        }
        else if (type.equals(NumericTypes.Short))
        {
            return value2.shortValue();
        }
        else if (type.equals(NumericTypes.Byte))
        {
        	return value2.byteValue();
        }

        return 0;
    }
	
    public static Object ConvertByteToTarget(Byte value, NumericTypes type)
    {
    	if (type.equals(NumericTypes.Double))
        {
        	return value.doubleValue();
        }
    	else if (type.equals(NumericTypes.Float))
        {
        	return value.byteValue();
        }
        else if (type.equals(NumericTypes.Long))
        {
        	return value.longValue();
        }
        else if (type.equals(NumericTypes.Integer))
        {
            return value.intValue();
        }
        else if (type.equals(NumericTypes.Short))
        {
            return value.shortValue();
        }
        else if (type.equals(NumericTypes.Byte))
        {
        	return value;
        }
        else if (type.equals(NumericTypes.Character))
        {
        	return (char)(value.intValue());
        }

        return 0;
    }
	
    public static Object ConvertShortToTarget(Short value, NumericTypes type)
    {
    	if (type.equals(NumericTypes.Double))
        {
        	return value.doubleValue();
        }
    	else if (type.equals(NumericTypes.Float))
        {
        	return value.floatValue();
        }
        else if (type.equals(NumericTypes.Long))
        {
        	return value.longValue();
        }
        else if (type.equals(NumericTypes.Integer))
        {
            return value.intValue();
        }
        else if (type.equals(NumericTypes.Short))
        {
            return value;
        }
        else if (type.equals(NumericTypes.Byte))
        {
        	return value.byteValue();
        }
        else if (type.equals(NumericTypes.Character))
        {
        	return (char)(value.intValue());
        }

        return 0;
    }
	
    public static Object ConvertIntegerToTarget(Integer value, NumericTypes type)
    {
    	if (type.equals(NumericTypes.Double))
        {
        	return value.doubleValue();
        }
    	else if (type.equals(NumericTypes.Float))
        {
        	return value.floatValue();
        }
        else if (type.equals(NumericTypes.Long))
        {
        	return value.longValue();
        }
        else if (type.equals(NumericTypes.Integer))
        {
            return value;
        }
        else if (type.equals(NumericTypes.Short))
        {
            return value.shortValue();
        }
        else if (type.equals(NumericTypes.Byte))
        {
        	return value.byteValue();
        }
        else if (type.equals(NumericTypes.Character))
        {
        	return (char)(value.intValue());
        }

        return 0;
    }
    
    public static Object ConvertLongToTarget(Long value, NumericTypes type)
    {
    	if (type.equals(NumericTypes.Double))
        {
        	return value.doubleValue();
        }
    	else if (type.equals(NumericTypes.Float))
        {
        	return value.floatValue();
        }
        else if (type.equals(NumericTypes.Long))
        {
        	return value;
        }
        else if (type.equals(NumericTypes.Integer))
        {
            return value.intValue();
        }
        else if (type.equals(NumericTypes.Short))
        {
            return value.shortValue();
        }
        else if (type.equals(NumericTypes.Byte))
        {
        	return value.byteValue();
        }
        else if (type.equals(NumericTypes.Character))
        {
        	return (char)(value.intValue());
        }

        return 0;
    }
    
    public static Object ConvertFloatToTarget(Float value, NumericTypes type)
    {
    	if (type.equals(NumericTypes.Double))
        {
        	return value.doubleValue();
        }
    	else if (type.equals(NumericTypes.Float))
        {
        	return value;
        }
        else if (type.equals(NumericTypes.Long))
        {
        	return value.longValue();
        }
        else if (type.equals(NumericTypes.Integer))
        {
            return value.intValue();
        }
        else if (type.equals(NumericTypes.Short))
        {
            return value.shortValue();
        }
        else if (type.equals(NumericTypes.Byte))
        {
        	return value.byteValue();
        }
        else if (type.equals(NumericTypes.Character))
        {
        	return (char)(value.intValue());
        }

        return 0;
    }
	
    public static Object ConvertDoubleToTarget(Double value, NumericTypes type)
    {
    	if (type.equals(NumericTypes.Double))
        {
        	return value;
        }
    	else if (type.equals(NumericTypes.Float))
        {
        	return value.floatValue();
        }
        else if (type.equals(NumericTypes.Long))
        {
        	return value.longValue();
        }
        else if (type.equals(NumericTypes.Integer))
        {
            return value.intValue();
        }
        else if (type.equals(NumericTypes.Short))
        {
            return value.shortValue();
        }
        else if (type.equals(NumericTypes.Byte))
        {
        	return value.byteValue();
        }
        else if (type.equals(NumericTypes.Character))
        {
        	return (char)(value.intValue());
        }

        return 0;
    }
}
