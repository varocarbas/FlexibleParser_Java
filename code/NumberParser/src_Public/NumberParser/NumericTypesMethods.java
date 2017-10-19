package NumberParser;

/**Class including all the methods performing the main actions associated with the NumericTypes enum.**/
@SuppressWarnings("rawtypes")
public class NumericTypesMethods
{
    /**
    Determines NumericTypes item matching the given variable.          
    @param input Numeric variable whose type will be determined.
    @return Item of the NumericTypes enum best matching the input variable.   
    **/
	public static NumericTypes GetTypeFromObject(Object input)
	{
		return
		(
			input == null ? NumericTypes.None :
			GetTypeFromClass(input.getClass())
		);
	}
	
    /**
    Determines the NumericTypes item matching the given class.          
    @param input Class whose type will be determined.
    @return Item of the NumericTypes enum best matching the input class.   
    **/
	public static NumericTypes GetTypeFromClass(Class input)
	{
		NumericTypes output = NumericTypes.None;
		if (input == null) return output;
		
		if (input.equals(double.class) || input.equals(Double.class))
		{
			output = NumericTypes.Double; 
		}
		else if (input.equals(float.class) || input.equals(Float.class))
		{
			output = NumericTypes.Float; 
		}
		else if (input.equals(long.class) || input.equals(Long.class))
		{
			output = NumericTypes.Long; 
		}
		else if (input.equals(int.class) || input.equals(Integer.class))
		{
			output = NumericTypes.Integer; 
		}
		else if (input.equals(short.class) || input.equals(Short.class))
		{
			output = NumericTypes.Short; 
		}
		else if (input.equals(byte.class) || input.equals(Byte.class))
		{
			output = NumericTypes.Byte; 
		}
		else if (input.equals(char.class) || input.equals(Character.class))
		{
			output = NumericTypes.Character; 
		}
		
		return output;
	}
}