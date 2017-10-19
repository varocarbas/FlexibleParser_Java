package InternalUnitParser.OtherParts;

import java.lang.reflect.Method;
import java.util.ArrayList;

import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Classes.*;
import UnitParser.*;
import UnitParser.UnitP.*;

@SuppressWarnings({ "rawtypes", "serial"})
public class OtherPartsNumberParserMethods 
{
    public static UnitInfo GetUnitInfoFromNumberX(Object numberX)
    {
    	return GetUnitInfoFromNumberX(numberX, ExceptionHandlingTypes.NeverTriggerException);
    }
    
    public static UnitInfo GetUnitInfoFromNumberX
    (
    	Object numberX, ExceptionHandlingTypes exceptionHandling
    )
    {
    	return GetUnitInfoFromNumberX(numberX, exceptionHandling, PrefixUsageTypes.DefaultUsage);
    }
    
    public static UnitInfo GetUnitInfoFromNumberX
    (
    	Object numberX, ExceptionHandlingTypes exceptionHandling, PrefixUsageTypes prefixUsage
    )
    {
        UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo
        (
        	0.0, exceptionHandling, prefixUsage
        );

        if (numberX == null || !NumberXTypes.contains(numberX.getClass().getName()))
        {
            outInfo.Error = ExceptionInstantiation.NewErrorInfo
            (
            	UnitP.ErrorTypes.NumericError
            );
        }
        else
        {
        	if (GetNumberXProperty(numberX, "getError").toString() != "None")
        	{
        		outInfo.Error = ExceptionInstantiation.NewErrorInfo
                (
                	UnitP.ErrorTypes.NumericError
                );
        	}
        	
        	int baseTenxExponent = (int)GetNumberXProperty(numberX, "getBaseTenExponent");
        	Object value = GetNumberXProperty(numberX, "getValue");
 	
            OtherPartsNumberParser temp = ConvertAnyValueToDouble(value);

            if (!outInfo.Error.getType().equals(ErrorTypes.None) && temp.IsWrong) 
            {
            	outInfo.Error = ExceptionInstantiation.NewErrorInfo
            	(
            		UnitP.ErrorTypes.NumericError
            	);
            }
            else
            {
                try
                {
                    outInfo.Value = temp.Value;
                    //temp.Exponent will always be zero.
                    outInfo.BaseTenExponent = baseTenxExponent;
                }
                catch(Exception e)
                {
                    //Very unlikely but possible scenario.
                    outInfo.Error = ExceptionInstantiation.NewErrorInfo
                    (
                    	UnitP.ErrorTypes.NumericError
                    );
                }
            }
        }

        return outInfo;
    }

    static Object GetNumberXProperty(Object numberX, String methodName)
    {
		try 
		{
			Method method = numberX.getClass().getMethod(methodName);	
			
			return method.invoke(numberX);
		} 
		catch (Exception e) 
		{ 
			//This point will never be reached. 
			//This try...catch statement is forced by the Java compiler.
		}

		return null;
    }
    
    //The types have to be stored as strings to account for the eventuality of not having a reference to NumberParser.
	static ArrayList<String> NumberXTypes = new ArrayList<String>()
    {{
    	add("NumberParser.Number"); add("NumberParser.NumberD"); 
    	add("NumberParser.NumberO"); add("NumberParser.NumberP");
    }};
    
    static OtherPartsNumberParser ConvertAnyValueToDouble(Object value)
    {
        AllNumericTypes type = GetTypeFromInput(value);

        return
        (
        	type == AllNumericTypes.None ? new OtherPartsNumberParser() : 
        	new OtherPartsNumberParser
        	(
        		//Unlikely what happens in the C# version with the default type (decimal),
        		//the double type is the biggest one and, consequently, any valid number
        		//will not need to rely on Exponent/BaseTenExponent. That's why a simple
        		//conversion of the given Value property to double is enough.
        		ConvertAnyValueToDoubleInternal(value, type)
        	) 
        );	
    }

	static double ConvertAnyValueToDoubleInternal(Object input, AllNumericTypes type)
	{
    	if (type.equals(AllNumericTypes.Double))
    	{
    		return (double)input;
    	}
    	else if (type.equals(AllNumericTypes.Float))
    	{
    		return ((Float)input).doubleValue();
    	}
    	else if (type.equals(AllNumericTypes.Long))
    	{
    		return ((Long)input).doubleValue();
    	}
    	else if (type.equals(AllNumericTypes.Integer))
    	{
    		return ((Integer)input).doubleValue();
    	}
    	else if (type.equals(AllNumericTypes.Short))
    	{
    		return ((Short)input).doubleValue();
    	}
    	else if (type.equals(AllNumericTypes.Byte))
    	{
    		return ((Byte)input).doubleValue();
    	}
    	else if (type.equals(AllNumericTypes.Character))
    	{
    		return (new Integer((char)input)).doubleValue();
    	}
    	
    	return Double.NaN;
	}
    
     enum AllNumericTypes
     { 
    	 None, Double, Float, Long, Integer, Short, Byte, Character 
     }
     
     static AllNumericTypes GetTypeFromInput(Object input)
     {
    	 AllNumericTypes output = AllNumericTypes.None;
    	 if (input == null) return output;
    	 
    	 Class inClass = input.getClass();
    	 
    	 if (inClass.equals(double.class) || inClass.equals(Double.class))
    	 {
    		 output = AllNumericTypes.Double;
    	 }
    	 else if (inClass.equals(float.class) || inClass.equals(Float.class))
    	 {
    		 output = AllNumericTypes.Float;
    	 }
    	 else if (inClass.equals(long.class) || inClass.equals(Long.class))
    	 {
    		 output = AllNumericTypes.Long;
    	 }
    	 else if (inClass.equals(int.class) || inClass.equals(Integer.class))
    	 {
    		 output = AllNumericTypes.Integer;
    	 }
    	 else if (inClass.equals(short.class) || inClass.equals(Short.class))
    	 {
    		 output = AllNumericTypes.Short;
    	 }
    	 else if (inClass.equals(byte.class) || inClass.equals(Byte.class))
    	 {
    		 output = AllNumericTypes.Byte;
    	 }
    	 else if (inClass.equals(char.class) || inClass.equals(Character.class))
    	 {
    		 output = AllNumericTypes.Character;
    	 }
    	 
    	 return output;
 	}
}
