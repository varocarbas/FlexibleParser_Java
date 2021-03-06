package InternalUnitParser.CSharpAdaptation;

import InternalUnitParser.Classes.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//Class including Java custom versions for all the System.Linq methods used in the original C# code.
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class Linq 
{
	public static <X> int Count(ArrayList<X> input, Predicate<X> filter)
	{
		if (input == null || filter == null) return 0;
		
		return (int)input.stream().filter(filter).count();
	}
	
	public static <X> ArrayList<X> Distinct(ArrayList<X> input)
	{
		if (input == null || input.size() < 2) return input;
		
		return (ArrayList<X>) input.stream().distinct().collect
		(
			Collectors.toList()
		);
	}
	
	public static <X> ArrayList<X> Select(ArrayList<X> input, Function<X, X> filter)
	{
		if (input == null || filter == null) return input;
		
		return (ArrayList<X>)input.stream().map(filter).collect(Collectors.toList());
	}

	public static <X, Y, Z> ArrayList<Z> SelectDict(HashMap<X, Y> input, Function<Map.Entry<X, Y>, Z> filter)
	{
		if (input == null || input.entrySet() == null || filter == null) 
		{
			return new ArrayList<Z>();
		}
				
		return (ArrayList<Z>)input.entrySet().stream().map(filter).collect(Collectors.toList());
	}
	
	public static <X> ArrayList<X> TakeWhile(ArrayList<X> input, Predicate<X> filter, X defaultVal)
	{
		if (input == null || filter == null) return input;
		
		ArrayList<X> output = new ArrayList<X>();
		ArrayList<X> input2 = Where(input, filter);
		
		for (X item: input)
		{
			if (!input2.contains(item)) break;
			
			output.add(item);
		}
		
		if (output.size() < 1)
		{
			output.add(defaultVal);
		}
		
		return output;
	}
	
	public static <X, Y> Map.Entry<X, Y> Max(HashMap<X, Y> input, Comparator<Map.Entry<X, Y>> comparator)
	{
		if (input == null || input.entrySet() == null || input.size() < 2)
		{
			return FirstOrDefaultDict(input, null);
		}
		
		return (Map.Entry<X, Y>)FirstOrDefault
		(
			OrderByDescending(new ArrayList(input.entrySet()), comparator), null
		);	
	}
	
	public static <X> ArrayList<X> GroupByJustCountAndOrderByDescending(ArrayList<X> input, Comparator<X> comparator)
	{
		if (input == null || comparator == null) return input;
		
		return (ArrayList<X>)OrderByDescending
		(
			(ArrayList<XAndIntegerClass>)GroupByJustCount(input, comparator), new Comparator<XAndIntegerClass>()
			{
				public int compare(XAndIntegerClass first, XAndIntegerClass second)
				{
					return first.Count.compareTo(second.Count);
				}        				
			}
		)
		.stream().map(x -> x.Input).collect(Collectors.toList());
	}
	
	static <X> ArrayList<XAndIntegerClass> GroupByJustCount(ArrayList<X> input, Comparator<X> comparator)
	{
		ArrayList<XAndIntegerClass> output = new ArrayList<XAndIntegerClass>()
		{{ 
			add(new XAndIntegerClass(input.get(0), 1)); 
		}};
		if (input == null || input.size() < 2) return output;  

		ArrayList<X> sorted = OrderBy(input, comparator);
		
		X last = sorted.get(0);
		Integer count = 1;
		
		for (int i = 1; i < sorted.size(); i++) 
		{
			X current = sorted.get(i);
			
		    if (current == last) count++;
		    else 
		    {
		    	output.add(new XAndIntegerClass(current, count));
		    	count = 1;
		    	last = current;
		    }
		}
		
		if (count > 1) 
		{
			output.add(new XAndIntegerClass(last, count));
		}
		
		return output;
	}
	
	public static <X> ArrayList<X> OrderBy(ArrayList<X> input, Comparator<X> comparator)
	{
		return PerformSorting(input, comparator, true);
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
	
	public static <X, Y> Map.Entry<X, Y> FirstOrDefaultDict(HashMap<X, Y> input, Map.Entry<X, Y> defaultVal)
	{
		return FirstOrDefaultDict(input, null, defaultVal);
	}
	
	public static <X, Y> Map.Entry<X, Y> FirstOrDefaultDict(HashMap<X, Y> input, Predicate<Map.Entry<X, Y>> filter, Map.Entry<X, Y> defaultVal)
	{
		if (input == null || input.entrySet() == null) return defaultVal;
		
    	return GetFirstDict
    	(
    		new HashMap<X, Y>(WhereDict(input, filter)), defaultVal
    	);   
	}
	
	public static <X, Y> HashMap<X, Y> WhereDict(HashMap<X, Y> input, Predicate<Map.Entry<X, Y>> filter)
	{
		if (input == null || input.keySet() == null || filter == null) return input;

		try
		{
			return (HashMap<X, Y>)input.entrySet().stream().filter(filter).collect
			(
				Collectors.toMap(x -> x.getKey(), x -> x.getValue())
			);			
		}
		catch(Exception e) { }
		
		return input;
	}
	
	static <X, Y> Map.Entry<X, Y> GetFirstDict(HashMap<X, Y> input, Map.Entry<X, Y> defaultVal) 
	{
		if (input == null || input.entrySet() == null) return defaultVal;
		
		try
		{
			for (Map.Entry<X, Y> entry : input.entrySet()) 
			{
				return entry;
			}		
		}
		catch (Exception e) { }
		
		return defaultVal;
	}
}
