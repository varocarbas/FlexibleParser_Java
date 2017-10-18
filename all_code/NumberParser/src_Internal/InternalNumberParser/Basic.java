package InternalNumberParser;

import InternalNumberParser.CSharpAdaptation.*;
import NumberParser.*;
import NumberParser.Number;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class Basic
{
    //Positive min./max. values for all the supported numeric types to behave accurately. 
    //This is useful when dealing with NumberX variables, to determine the points where BaseTenExponent
    //has to be brought into picture to complement Value.
	public static HashMap<NumericTypes, ArrayList<Object>> AllNumberMinMaxPositives = 
	new HashMap<NumericTypes, ArrayList<Object>>()
    {
    	{ 
    		put(NumericTypes.Double, new ArrayList() {{ add(1e-308); add(Double.MAX_VALUE); }});      		
    		put(NumericTypes.Float, new ArrayList() {{ add(1e-37); add(Float.MAX_VALUE); }});      		
    		put(NumericTypes.Long, new ArrayList() {{ add(1); add(Long.MAX_VALUE); }});     	    		    
    		put(NumericTypes.Integer, new ArrayList() {{ add(1); add(Integer.MAX_VALUE); }});      
    		put(NumericTypes.Short, new ArrayList() {{ add(1); add(Short.MAX_VALUE); }});        
    		put(NumericTypes.Character, new ArrayList() {{ add(1); add(Character.MAX_VALUE); }});       
    		put(NumericTypes.Byte, new ArrayList() {{ add(1); add(Byte.MAX_VALUE); }});      		
    	}
    };
    
    //Min./max. values for all the supported numeric types.
    public static HashMap<NumericTypes, ArrayList<Object>> AllNumberMinMaxs = 
    new HashMap<NumericTypes, ArrayList<Object>>()
    {
    	{
    		put(NumericTypes.Double, new ArrayList<Object>() {{ add(Double.MIN_VALUE); add(Double.MAX_VALUE); }});    		
    		put(NumericTypes.Float, new ArrayList<Object>() {{ add(Float.MIN_VALUE); add(Float.MAX_VALUE); }});    
    		put(NumericTypes.Long, new ArrayList<Object>() {{ add(Long.MIN_VALUE); add(Long.MAX_VALUE); }});     
    		put(NumericTypes.Integer, new ArrayList<Object>() {{ add(Integer.MIN_VALUE); add(Integer.MAX_VALUE); }});     
    		put(NumericTypes.Short, new ArrayList<Object>() {{ add(Short.MIN_VALUE); add(Short.MAX_VALUE); }});    
    		put(NumericTypes.Character, new ArrayList<Object>() {{ add(Character.MIN_VALUE); add(Character.MAX_VALUE); }});
    		put(NumericTypes.Byte, new ArrayList<Object>() {{ add(Byte.MIN_VALUE); add(Byte.MAX_VALUE); }});       		
    	}
    };

	public static ArrayList<NumericTypes> AllNumericTypes = new ArrayList<NumericTypes>()
    {{
        add(NumericTypes.Double); add(NumericTypes.Float); add(NumericTypes.Long); add(NumericTypes.Integer); 
        add(NumericTypes.Short); add(NumericTypes.Character); add(NumericTypes.Byte);
    }};

    public static ArrayList<NumericTypes> AllUnsignedTypes = new ArrayList<NumericTypes>()
    {{
    	add(NumericTypes.Byte); add(NumericTypes.Character);
    }};
       
    public static ArrayList<Class> AllNumberClassTypes = new ArrayList<Class>()
    {{
        add(Number.class); add(NumberD.class); add(NumberO.class); add(NumberP.class);
    }};
    
    public static ArrayList<NumericTypes> AllDecimalTypes = new ArrayList<NumericTypes>()
    {{
        add(NumericTypes.Double); add(NumericTypes.Float);
    }};

    //Returns all the types whose ranges are equal or smaller than int.
    public static ArrayList<NumericTypes> GetSmallIntegers()
    {   	
        return LinqNP.Where
        (
        	AllNumericTypes, 
        	(
        		x -> x != NumericTypes.Long && 
        		!AllDecimalTypes.contains(x)
        	)
        );
    }

    //The purpose of this function is to easily create simple values for a given type.
    //It isn't prepared to deal with range incompatibilities between different types.
    //Sample inputs: 0, 1 or -1.
    public static Object GetNumberSpecificType(Object value, NumericTypes target)
    {
    	NumericTypes type = ErrorInfoNumber.InputTypeIsValidNumeric(value);
        if (type.equals(NumericTypes.None) || type.equals(target)) return value;

        return Conversions.CastDynamicToType(value, target);
    }

    //This method assumes that input and type are valid numeric types.
    public static boolean InputInsideTypeRange(Object input, NumericTypes type)
    {
        double value = Conversions.ConvertToDoubleInternal
        (
        	new NumberD(input).getValue()
        );
        
        ArrayList<Double> minMax = new ArrayList<Double>()
        {{
            add
            (
            	ConvertTo.ConverToDouble
            	(
            		Basic.AllNumberMinMaxPositives.get(type).get(0)
            	)
            );
            add
            (
            	ConvertTo.ConverToDouble
            	(
            		Basic.AllNumberMinMaxPositives.get(type).get(1)
            	)
            );
        }};
        
        return 
        (
        	value >= minMax.get(0) && value <= minMax.get(1)
        );
    }
}
