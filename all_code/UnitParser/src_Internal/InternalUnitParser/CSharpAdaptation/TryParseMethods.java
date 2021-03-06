package InternalUnitParser.CSharpAdaptation;

import java.text.NumberFormat;
import java.util.Locale;

public class TryParseMethods
{
	public static TryParseOutput Double(String input)
	{
		TryParseOutput output = new TryParseOutput();
		
		try 
		{
		    output.DoubleVal = ParseCommon(input).doubleValue();
		    Double val = new Double(output.DoubleVal);
		    output.IsOK = true;
		    
		    if (input != null && (input.contains("e") || !(val.isNaN() || val.isInfinite())))
		    {
		    	String[] tempVar = CSharpOther.SplitTryCatch(input.toLowerCase(), "e");
		    	if (tempVar.length == 2)
		    	{
		    		if 
		    		(
		    			Linq.FirstOrDefault
		    			(
		    				CSharpOther.StringToCharacters(tempVar[1]), x -> !Character.isDigit(x), '\u0000'
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
	
	public static TryParseOutput Int(String input)
	{
		TryParseOutput output = new TryParseOutput();
		if (input.contains("."))
		{
			//This is required to ensure a full compatibility with the behaviour of the original C# version.
			return output;
		}
		
		try 
		{
		    output.IntVal = ParseCommon(input).intValue();
		    output.IsOK = true;
		}
		catch (Exception e) { }
		
		return output;
	}
	
	static Number ParseCommon(String input) throws Exception 
	{
		if 
		(
			input == null || Linq.FirstOrDefault
			(
				CSharpOther.StringToCharacters(input.toLowerCase()), x -> !Character.isDigit(x) && 
				x != '.' && x != ',' && x != '-' && x != '+', '\u0000'
			) 
			!= '\u0000'
		)
		{
			//This is required to ensure a full compatibility with the behaviour of the original C# version.
			throw new Exception(); 
		}

		return NumberFormat.getInstance(Locale.US).parse(input);
	}
}
