package InternalNumberParser;

import java.util.ArrayList;

import NumberParser.*;

@SuppressWarnings("serial")
public class NumberOInternal
{
	public static ArrayList<NumberD> PopulateOthers(double value, int baseTenExponent, ArrayList<NumericTypes> types)
	{
		ArrayList<NumberD> outList = new ArrayList<NumberD>();
	    if (types == null) return outList;

	    for (NumericTypes type: types)
	    {
	        outList.add
	        (
	            new NumberD(value, baseTenExponent, type)
	        );
	    }

	    return outList;
	}

	public static ArrayList<NumericTypes> CheckOtherTypes(ArrayList<NumericTypes> types)
	{
	    ArrayList<NumericTypes> output = new ArrayList<NumericTypes>();
	    if (types == null || types.size() == 0) 
	    {
	    	return output;
	    }

	    for (NumericTypes type: types)
	    {
	        if (!type.equals(NumericTypes.None) && !type.equals(NumericTypes.Double))
	        {
	        	output.add(type);
	        }
	    }
		
	    return output;
	}

	public static ArrayList<NumericTypes> GetAssociatedTypes(OtherTypes otherType)
	{
		ArrayList<NumericTypes> types = null;

	    if (otherType.equals(OtherTypes.AllTypes))
	    {
	        types = new ArrayList<NumericTypes>(Basic.AllNumericTypes);
	    }
	    else if (otherType.equals(OtherTypes.MostCommonTypes))
	    {
	        types = new ArrayList<NumericTypes>()
	        {{ 
	            add(NumericTypes.Double); 
	            add(NumericTypes.Long);
	            add(NumericTypes.Integer);
	        }};
	    }
	    else if (otherType.equals(OtherTypes.DecimalTypes))
	    {
	        types = new ArrayList<NumericTypes>()
	        {{ 
	            add(NumericTypes.Double); 
	            add(NumericTypes.Float);
	        }};
	    }
	    else if (otherType.equals(OtherTypes.IntegerTypes))
	    {
	        types = new ArrayList<NumericTypes>()
	        {{ 
	            add(NumericTypes.Long); 
	            add(NumericTypes.Integer);
	            add(NumericTypes.Short);
	            add(NumericTypes.Byte);
	            add(NumericTypes.Character);
	        }};	  
	    }
	    else if (otherType.equals(OtherTypes.BigTypes))
	    {
	        types = new ArrayList<NumericTypes>()
	        {{ 
	            add(NumericTypes.Double); 
	            add(NumericTypes.Float);
	            add(NumericTypes.Long);
	            add(NumericTypes.Integer);
	        }};	  	    	
	    }
	    else if (otherType.equals(OtherTypes.SmallTypes))
	    {
	        types = new ArrayList<NumericTypes>()
	        {{ 
	            add(NumericTypes.Short); 
	            add(NumericTypes.Byte);
	            add(NumericTypes.Character);
	        }};	  	    	
	    }
	    else types = new ArrayList<NumericTypes>();

	    return types;
	}
}
