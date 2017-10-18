package InternalNumberParser;

import NumberParser.*;
import NumberParser.Number;
import InternalNumberParser.CSharpAdaptation.*;

@SuppressWarnings("rawtypes")
public class Constructors
{
	public static Object InitialiseNumberX(Class type, Object value, int baseTenExponent)
    {
        if (type.equals(Number.class))
        {
            return new Number
            (
            	Conversions.ConvertTargetToDouble(value), baseTenExponent
            );
        }
        else if (type.equals(NumberD.class))
        {
            return new NumberD(value, baseTenExponent);
        }
        else if (type.equals(NumberO.class))
        {
            return new NumberO
            (
            	Conversions.ConvertTargetToDouble(value), baseTenExponent
            );
        }
        else if (type.equals(NumberP.class))
        {
            return new NumberP(value, baseTenExponent);
        }

        return null;
    }

	//This method expects numberX to be a valid NumberX type 
	//(i.e., Number, NumberD, NumberO or NumberP).
    public static Number ExtractDynamicToNumber(Object numberX)
    {
        if (numberX == null) 
        {
        	return new Number(ErrorTypesNumber.InvalidInput);
        }
        
    	NumberX.MainVars mainVars = NumberX.GetMainVars(numberX);

        if (!mainVars.Error.equals(ErrorTypesNumber.None))
        {
        	return new Number(mainVars.Error);
        }

        return new Number
        (
        	Conversions.ConvertTargetToDouble
        	(
        		mainVars.Value
        	), 
        	mainVars.BaseTenExponent
        );
    }

	//This method expects numberX to be a valid NumberX type 
	//(i.e., Number, NumberD, NumberO or NumberP).
    public static NumberD ExtractDynamicToNumberD(Object numberX)
    {
        if (numberX == null) 
        {
        	return new NumberD(ErrorTypesNumber.InvalidInput);
        }
        
        NumberX.MainVars mainVars = NumberX.GetMainVars(numberX);
        
        if (!mainVars.Error.equals(ErrorTypesNumber.None))
        {
        	return new NumberD(mainVars.Error);
        }
        
        NumberD output = new NumberD();
        
        output.setValue(mainVars.Value);
        output.setBaseTenExponent(mainVars.BaseTenExponent);

        return output;
    }
}
