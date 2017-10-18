package InternalNumberParser.Operations;

import InternalNumberParser.Conversions;
import InternalNumberParser.CSharpAdaptation.*;
import NumberParser.*;

public class OperationsCompareTo
{
	static NumberX.MainVars[] tempVars = null;
	
    //This method is called from CompareTo of Number/NumberO instances.
    public static int CompareDouble(Object thisVar, Object other)
    {
		int tempInt = ComparePreanalysis(thisVar, other);
		if (tempInt != -2) return tempInt;

        return
        (
        	tempVars[0].BaseTenExponent == tempVars[1].BaseTenExponent ?
            ((Double)Conversions.ConvertTargetToDouble(tempVars[0].Value)).compareTo
            (Conversions.ConvertTargetToDouble(tempVars[1].Value)) :
            ((Integer)tempVars[0].BaseTenExponent).compareTo
            (
            	tempVars[1].BaseTenExponent
            )
        );
    }

    //This method is called from CompareTo of NumberD/NumberP instances. 
    //It always relies on the same common type (i.e., decimal) to avoid different-type-Value problems.
    public static int CompareDynamic(Object thisVar, Object other)
    {
		int tempInt = ComparePreanalysis(thisVar, other);
		if (tempInt != -2) return tempInt;
		
        NumberD[] adapted = ComparedInstancesToDouble();

        return
        (
            adapted[0].getBaseTenExponent() == adapted[1].getBaseTenExponent() ?
            ((Double)Conversions.ConvertTargetToDouble(adapted[0].getValue())).compareTo
            ((Double)Conversions.ConvertTargetToDouble(adapted[1].getValue())) :
            ((Integer)adapted[0].getBaseTenExponent()).compareTo
            (
            	(Integer)adapted[1].getBaseTenExponent()
            )
        );
    }

    //Both arguments are non-null NumberD/NumberP instances.
    static NumberD[] ComparedInstancesToDouble()
    {    	
        //Decimal is the most precise type and NumberParser is an eminently-precision-focused library.
        //The decimal range is notably smaller than the one of other types (e.g., double) and, consequently,
        //that decision might provoke (avoidable) errors. In any case, the scenarios provoking those errors
        //happen under so extreme conditions (i.e., over/under +-10^2147483647) that cannot justify the reliance
        //on a less precise type.
        return new NumberD[]
        {
            new NumberD
            (
            	Conversions.CastDynamicToType
            	(
            		tempVars[0].Value, NumericTypes.Double
            	),
            	tempVars[0].BaseTenExponent, NumericTypes.Double
            ),
            new NumberD
            (
            	Conversions.CastDynamicToType
            	(
            		tempVars[1].Value, NumericTypes.Double
            	),
            	tempVars[1].BaseTenExponent, NumericTypes.Double
            )
        };
    }
    
	static int ComparePreanalysis(Object thisVar, Object other)
	{
		if (thisVar == null || other == null)
		{
			if (thisVar == null && other == null) return 0;

			return (thisVar == null ? -1 : 1);
		}

		
		tempVars = new NumberX.MainVars[]
		{
			NumberX.GetMainVars(thisVar), NumberX.GetMainVars(other)
		};
			
		if 
		(
			!tempVars[0].Error.equals(ErrorTypesNumber.None) || 
			!tempVars[1].Error.equals(ErrorTypesNumber.None)
		)
		{
			if (tempVars[0].Error.equals(tempVars[1].Error)) return 0;

			return 
			(
				!tempVars[0].Error.equals(ErrorTypesNumber.None) ? -1 : 1
			);
		}

		return -2;
	}
}