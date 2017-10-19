package InternalNumberParser.CSharpAdaptation;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import NumberParser.NumericTypes;

public class TryParseMethodsNP
{
	public static TryParseOutputNP Double(String input, Locale culture)
	{
		return DecimalType
		(
			input, culture, NumericTypes.Double
		);
	}
	
	public static TryParseOutputNP Float(String input, Locale culture)
	{
		return DecimalType
		(
			input, culture, NumericTypes.Float
		);
	}
	
	public static TryParseOutputNP DecimalType
	(
		String input, Locale culture, NumericTypes numericType
	)
	{
		TryParseOutputNP output = new TryParseOutputNP();
		
		try 
		{
			boolean isWrong = false;
			Number number = ParseCommon(input, culture);
	    	output.IsOK = true;
	    	
			if (numericType.equals(NumericTypes.Double))
			{
			    output.DoubleVal = number.doubleValue();
			    Double val = new Double(output.DoubleVal);	

			    if (val.isNaN() || val.isInfinite())
			    {
			    	isWrong = true;
			    }
			}
			else if (numericType.equals(NumericTypes.Float))
			{
			    output.FloatVal = number.floatValue();
			    Float val = new Float(output.FloatVal);	
			    
			    if (val.isNaN() || val.isInfinite())
			    {
			    	isWrong = true;
			    }
			}
		    
		    if (input != null && (input.contains("e") || isWrong))
		    {		    	
		    	ArrayList<String> tempVar = CSharpOtherNP.SplitTryCatch
		    	(
		    		input.toLowerCase(), "e"
		    	);

		    	if (tempVar.size() == 2)
		    	{
		    		if 
		    		(
		    			LinqNP.FirstOrDefault
		    			(
		    				CSharpOtherNP.StringToCharacters
		    				(
		    					tempVar.get(1)
		    				), 
		    				x -> !Character.isDigit(x), '\u0000'
		    			) 
		    			!= '\u0000'
		    		)
		    		{
		    			//Emulating the conditions of the original C# version which doesn't support decimal exponents.
		    			output.IsOK = false;
		    		}
		    	}
		    }
		}
		catch (Exception e) { output.IsOK = false; }

		return output;
	}
	
	public static TryParseOutputNP Integer(String input, Locale culture)
	{
		return IntegerType
		(
			input, culture, NumericTypes.Integer
		);
	}
	
	public static TryParseOutputNP Long(String input, Locale culture)
	{
		return IntegerType
		(
			input, culture, NumericTypes.Long
		);
	}
	
	public static TryParseOutputNP Short(String input, Locale culture)
	{
		return IntegerType
		(
			input, culture, NumericTypes.Short
		);
	}

	public static TryParseOutputNP Byte(String input, Locale culture)
	{
		return IntegerType
		(
			input, culture, NumericTypes.Byte
		);
	}
	
	public static TryParseOutputNP Character(String input, Locale culture)
	{
		return IntegerType
		(
			input, culture, NumericTypes.Character
		);
	}
	
	static TryParseOutputNP IntegerType(String input, Locale culture, NumericTypes numericType)
	{
		TryParseOutputNP output = new TryParseOutputNP();

		try 
		{
			Number number = ParseCommon(input, culture);
			
			if (numericType.equals(NumericTypes.Long))
			{
			    output.LongVal = number.longValue();				
			}
			else if (numericType.equals(NumericTypes.Integer))
			{
			    output.IntegerVal = number.intValue();				
			}
			else if (numericType.equals(NumericTypes.Short))
			{
			    output.ShortVal = number.shortValue();				
			}
			else if (numericType.equals(NumericTypes.Byte))
			{
				output.ByteVal = number.byteValue();				
			}			
			else if (numericType.equals(NumericTypes.Character))
			{
			    output.CharacterVal = (char)number.intValue();				
			}
			
		    output.IsOK = true;
		}
		catch (Exception e) { }
		
		return output;
	}
	
	static Number ParseCommon(String input, Locale culture) throws Exception 
	{
		if 
		(
			input == null || LinqNP.FirstOrDefault
			(
				CSharpOtherNP.StringToCharacters(input.toLowerCase()), 
				x -> !Character.isDigit(x) && 
				x != '.' && x != ',' && x != '-' && x != '+', '\u0000'
			) 
			!= '\u0000'
		)
		{
			//This is required to ensure a full compatibility with the behaviour of the original C# version.
			throw new Exception(); 
		}
		
		return NumberFormat.getInstance
		(
			culture != null ? culture : Locale.US
		)
		.parse(input);
	}	
}
