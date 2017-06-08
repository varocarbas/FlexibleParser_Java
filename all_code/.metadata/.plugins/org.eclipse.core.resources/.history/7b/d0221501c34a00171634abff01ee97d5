package InternalUnitParser.Operations;

import UnitParser.*;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class OperationsOther 
{
    public static String GetOperationString(UnitP first, UnitP second, Operations operation)
    {
        return ConcatenateOperationString
        (
            GetUnitPString(first), GetUnitPString(second), OperationSymbols.get(operation).get(0)
        );
    }

    public static String GetOperationString(UnitP first, Double second, Operations operation)
    {
        return ConcatenateOperationString
        (
            GetUnitPString(first), second.toString(), OperationSymbols.get(operation).get(0)
        );
    }

    public static String GetOperationString(Double first, UnitP second, Operations operation)
    {
        return ConcatenateOperationString
        (
            first.toString(), GetUnitPString(second), OperationSymbols.get(operation).get(0)
        );
    }

    private static String GetUnitPString(UnitP unitP)
    {
        return unitP.OriginalUnitString;
    }

    private static String ConcatenateOperationString(String first, String second, char operation)
    {
        return (first + " " + operation + " " + second); 
    }
    
	//NOTE: the order within each char[] collection does matter. The first element will be treated as the default
    //symbol for the given operation (e.g., used when creating a String including that operation).
	public static HashMap<Operations, ArrayList<Character>> OperationSymbols;
    
	public static ArrayList<String> UnitParseIgnored;
    
    public static final double MaxValue = 79228162514264337593543950335.0; //C# Decimal.MaxValue actual value.
    public static final double MinValue = 0.0000000000000000000000000001; //C# Decimal precision lowest limit.
    
    public static String Start()
    {
    	OperationSymbols = new HashMap<Operations, ArrayList<Character>>()
        {
            {
            	{
            		put
            		(
            			Operations.Addition, new ArrayList<Character>() 
            			{{ 
            				add('+');  
            			}}
            		);
            		put
            		(
            			Operations.Subtraction, new ArrayList<Character>() 
            			{{ 
            				add('-'); add('−'); add('—'); 
            			}}
            		);
            		put
            		(
            			Operations.Multiplication, new ArrayList<Character>() 
            			{{ 
            				add('*'); add('x'); add('X'); add('×'); add('⊗'); add('⋅'); add('·'); 
            			}}
            		);     
            		put
            		(
            			Operations.Division, new ArrayList<Character>() 
            			{{ 
            				add('/'); add('∕'); add('⁄'); add('÷'); add('|'); add('\\'); 
            			}}
            		);          		
            	}
            }
        };
    	
        UnitParseIgnored = new ArrayList<String>()
        {{
            add("."); add(","); add(":"); add(";"); add("_"); add("^"); add("+"); 
            add("#"); add("("); add(")"); add("["); add("]"); add("{"); add("}"); 
            add("="); add("!"); add("?"); add("@"); add("&");
        }};
        
    	return "";
    }
}