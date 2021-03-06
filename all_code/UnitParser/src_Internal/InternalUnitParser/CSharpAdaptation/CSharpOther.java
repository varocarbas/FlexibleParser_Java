package InternalUnitParser.CSharpAdaptation;

import InternalUnitParser.Hardcoding.*;
import InternalUnitParser.Operations.*;

import java.util.ArrayList;

//Class storing a set of miscellaneous methods used to ease the conversion from the original C# code.
//Note that all the adaptations of the used System.Linq methods are included in the class InternalUnitParser.Linq.
public class CSharpOther
{
	public static String StartHarcoding()
	{
		HCPrefixes.Start();
		HCUnits.Start();
		HCMain.Start();
		HCCompounds.Start();
		HCOther.Start();
		OperationsOther.Start();

		return "";
	}

	public static String Substring(String input, int startI, int length)
	{
		return
		(
			length < 0 ? input.substring(startI) :
			input.substring(startI, startI + length)
		);
	}
	
	public static String[] SplitTryCatch(String input, String separator)
	{
		String[] output = new String[] { input };
		
		if (input == null || separator == null) return output;
		
		try
		{
			output = input.split(separator);
		}
		catch(Exception e) { }
		
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
	
	public static <X> ArrayList<X> ArrayToArrayList(X[] input)
	{
		ArrayList<X> output = new ArrayList<X>();
		
		for (X item: input)
		{
			output.add(item);
		}
		
		return output;
	}
	
	public static String StringJoin(String separator, String[] parts, int start, int length)
	{
		return StringJoin
		(
			separator, ArrayToArrayList(parts), start, length
		);
	}
	
	public static String StringJoin(String separator, String[] parts)
	{
		return StringJoin
		(
			separator, ArrayToArrayList(parts)
		);
	}
	
	public static String StringJoin(String separator, ArrayList<String> parts)
	{
		return StringJoinInternal
		(
			separator, parts, null, 0, parts.size()
		);
	}
	
	public static String StringJoin(String separator, ArrayList<String> parts, int start, int length)
	{
		return StringJoinInternal
		(
			separator, parts, null, start, length
		);
	}
	
	public static String StringJoinChars(String separator, ArrayList<Character> parts2)
	{
		return StringJoinInternal
		(
			separator, null, parts2, 0, (parts2 == null ? 0 : parts2.size())
		);
	}
	
	static String StringJoinInternal(String separator, ArrayList<String> parts, ArrayList<Character> parts2, int start, int length)
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
	
	static String StringJoinInternalBit(ArrayList<String> parts, ArrayList<Character> parts2, int i, String separator)
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
}
