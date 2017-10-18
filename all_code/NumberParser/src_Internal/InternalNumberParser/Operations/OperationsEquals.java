package InternalNumberParser.Operations;

import java.util.ArrayList;

import NumberParser.*;
import NumberParser.Number;
import InternalNumberParser.*;
import InternalNumberParser.CSharpAdaptation.*;


public class OperationsEquals
{
    public static boolean NumberXsAreEqualBasic(Object first, Object second)
    {
        if (first == null || second == null) 
        {
        	return (first == null && second == null);
        }

        NumberX.MainVars first2 = NumberX.GetMainVars(first);
        NumberX.MainVars second2 = NumberX.GetMainVars(second);        
        if (!first2.Type.equals(second2.Type) || !first2.Error.equals(second2.Error)) 
        {
        	return false;
        }

        if (first2.BaseTenExponent != 0 || second2.BaseTenExponent != 0)
        {
        	first2 = NumberX.GetMainVars
        	(
        		OperationsManaged.PassBaseTenToValue
                (
                	new NumberD(first2.Value, first2.BaseTenExponent)
                )
        	);

        	second2 = NumberX.GetMainVars
        	(
        		OperationsManaged.PassBaseTenToValue
                (
                	new NumberD(second2.Value, second2.BaseTenExponent)
                )
        	);
        }

        return ValuesAreEqual(first2, second2);
    }

    public static boolean NumberXsAreEqual(Number first, Number second)
    {
        return NumberXsAreEqualBasic(first, second);
    }

    public static boolean NumberXsAreEqual(NumberD first, NumberD second)
    {
        return 
        (
            first.getType().equals(second.getType()) && 
            NumberXsAreEqualBasic(first, second)    
        );
    }

    public static boolean NumberXsAreEqual(NumberO first, NumberO second)
    {
        return
        (
            NumberXsAreEqualBasic(first, second) && 
            OthersAreEqual(first.getOthers(), second.getOthers())
        );
    }

    private static boolean OthersAreEqual(ArrayList<NumberD> first, ArrayList<NumberD> second)
    {
        if (first.size() != second.size()) return false;

        for (NumberD item1: first)
        {
            NumberD item2 = LinqNP.FirstOrDefault
            (
            	second, x -> x.getType().equals(item1.getType()), null
            );
            if (item2 == null) return false;
        }

        return true;
    }

    public static boolean NumberXsAreEqual(NumberP first, NumberP second)
    {
        return
        (
            NumberXsAreEqualBasic(first, second) && 
            first.getOriginalString().equals(second.getOriginalString()) &&
            ParseConfigsAreEqual(first.getParseConfig(), second.getParseConfig())
        );
    }

    public static boolean ParseConfigsAreEqual(ParseConfig first, ParseConfig second)
    {
        return
        (
            first.getTarget().equals(second.getTarget()) &&
            first.getCulture().equals(second.getCulture()) && 
            first.getParseType().equals(second.getParseType())
        );
    }

    public static boolean PolynomialsAreEqual(Polynomial first, Polynomial second)
    {
        return
        (
            first.Error.equals(second.Error) && NumberXsAreEqual(first.A, second.A) &&
            NumberXsAreEqual(first.B, second.B) && NumberXsAreEqual(first.C, second.C) 
        );
    }

    private static boolean ValuesAreEqual(NumberX.MainVars first, NumberX.MainVars second)
    {
        return
        (
            first.Value.equals(second.Value) &&
            (
                ErrorInfoNumber.InputTypeIsValidNumeric(first.Value).equals
                (
                	ErrorInfoNumber.InputTypeIsValidNumeric(second.Value) 
                ) 
            )
            && first.BaseTenExponent == second.BaseTenExponent
        );
    }
}
