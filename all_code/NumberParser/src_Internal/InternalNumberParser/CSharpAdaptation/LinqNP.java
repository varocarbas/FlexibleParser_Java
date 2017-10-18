package InternalNumberParser.CSharpAdaptation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//Class including Java custom versions for all the System.Linq methods used in the original C# code.
public class LinqNP
{
	public static <X, Y> ArrayList<Y> Select(ArrayList<X> input, Function<X, Y> filter)
	{
		if (input == null || filter == null) return null;
		
		return (ArrayList<Y>)input.stream().map(filter).collect(Collectors.toList());
	}
	
	public static <X> ArrayList<X> Where(ArrayList<X> input, Predicate<X> filter)
	{
		if (input == null || filter == null) return input;

		try
		{
			return (ArrayList<X>)input.stream().filter(filter).collect
			(
				Collectors.toList()
			);
		}
		catch(Exception e) { }
		
		return input;
	}
	
	public static <X> X FirstOrDefault(ArrayList<X> input, X defaultVal)
	{
		return FirstOrDefault(input, null, defaultVal);
	}
	
	public static <X> X FirstOrDefault(ArrayList<X> input, Predicate<X> filter, X defaultVal)
	{
		if (input == null) return defaultVal;
		
    	return GetFirst
    	(
    		new ArrayList<X>(Where(input, filter)), defaultVal
    	);   
	}
	
	static <X> X GetFirst(ArrayList<X> input, X defaultVal)
	{
		if (input == null) return defaultVal;
		
		try
		{
			for (X item : input) 
			{
				return item;
			}			
		}
		catch (Exception e) { }
		
		return defaultVal;
	}
	
	public static <X> ArrayList<X> OrderByDescending(ArrayList<X> input, Comparator<X> comparator)
	{
		return PerformSorting(input, comparator, false);
	}

	static <X> ArrayList<X> PerformSorting(ArrayList<X> input, Comparator<X> comparator, boolean ascending)
	{
		if (input == null || comparator == null) return input;
			
		ArrayList<X> output = new ArrayList<X>(input);
		if (output.size() < 2) return output;
		
		output.sort(comparator);
		if (!ascending) Collections.reverse(output);
		
		return output;
	}
}
