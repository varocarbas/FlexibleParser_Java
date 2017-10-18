package InternalNumberParser.Operations;

import java.util.ArrayList;
import java.util.Locale;

import NumberParser.*;
import NumberParser.Number;
import InternalNumberParser.*;
import InternalNumberParser.CSharpAdaptation.CSharpOtherNP;
import InternalNumberParser.CSharpAdaptation.LinqNP;

public class OperationsMain
{
    public static NumberD PerformOtherOperationD(NumberD first, NumberD second, ExistingOperations operation)
    {
    	return PerformOtherOperationD(first, second, operation, true);
    }
    
    public static NumberD PerformOtherOperationD(NumberD first, NumberD second, ExistingOperations operation, boolean checkError)
    {
        return new NumberD
        (
            PerformOtherOperation
            (
                new Number(first), new Number(second), operation, checkError
            )
        );
    }

    public static NumberO PerformOtherOperationO(NumberO first, NumberO second, ExistingOperations operation)
    {
    	return PerformOtherOperationO(first, second, operation, true);
    }
    
    public static NumberO PerformOtherOperationO(NumberO first, NumberO second, ExistingOperations operation, boolean checkError)
    {
        return new NumberO
        (
        	new NumberO
        	(
        		PerformOtherOperation
            	(
            		new Number(first), new Number(second), operation, checkError
            	)
            ),
            (ArrayList<NumericTypes>)LinqNP.Select
            (
            	first.getOthers(), x -> x.getType()
            )
        );
    }

    public static NumberP PerformOtherOperationP(NumberP first, NumberP second, ExistingOperations operation)
    {
    	return PerformOtherOperationP(first, second, operation, true);
    }
    
    public static NumberP PerformOtherOperationP(NumberP first, NumberP second, ExistingOperations operation, boolean checkError)
    {
        return new NumberP
        (
            PerformOtherOperation
            (
                new Number(first), new Number(second), operation, checkError
            ), 
            GetOperationString
            (
                first, second, operation, first.getParseConfig().getCulture()
            ),
            first.getParseConfig()
        );
    }

    public static Number PerformOtherOperation(Number first, Number second, ExistingOperations operation)
    {
    	return PerformOtherOperation(first, second, operation, true);
    }
    
    public static Number PerformOtherOperation(Number first, Number second, ExistingOperations operation, boolean checkError)
    {
        if (checkError)
        {
            ErrorTypesNumber error = ErrorInfoNumber.GetOperationError
            (
                first, second, operation
            );
            if (!error.equals(ErrorTypesNumber.None)) return new Number(error);
        }

        Number first2 = OperationsManaged.PassBaseTenToValue(new Number(first));
        Number second2 = OperationsManaged.PassBaseTenToValue(new Number(second));

        Number numberOut = new Number();

        if (operation.equals(ExistingOperations.Modulo))
        {
            if (first2.getBaseTenExponent() == 0 && second2.getBaseTenExponent() == 0)
            {
                return new Number(first2.getValue() % second2.getValue());
            }
            
            numberOut = new Number
            (
            	OperationsManaged.SubtractInternal
            	(
            		new NumberD(first2), OperationsManaged.MultiplyInternal
            		(
            			Math2.Floor
                    	(
                    		new NumberD
                    		(
                    			OperationsManaged.DivideInternal
                    			(
                    				first2, second2
                    			)            					
                    		)
                    	), 
                    	new NumberD(second2)
                    )
            	)            		
            );
        }
        else
        {
            numberOut.setValue
            (
            	GetComparisonResult
            	(
            		first2, second2, operation
            	)
            );
        }

        return OperationsManaged.PassBaseTenToValue(numberOut);
    }

    static double GetComparisonResult(Number first, Number second, ExistingOperations operation)
    {
        if (first.getBaseTenExponent() != second.getBaseTenExponent())
        {
            if (operation.equals(ExistingOperations.GreaterOrEqual))
            {
                if (first.getBaseTenExponent() >= second.getBaseTenExponent()) 
                {
                	return 1.0;
                }
            }
            else if (operation.equals(ExistingOperations.Greater))
            {
                if (first.getBaseTenExponent() > second.getBaseTenExponent())
                {
                	return 1.0;
                }
            }
            else if (operation.equals(ExistingOperations.SmallerOrEqual))
            {
                if (first.getBaseTenExponent() <= second.getBaseTenExponent()) 
                {
                	return 1.0;
                }
            }
            else if (operation.equals(ExistingOperations.Smaller))
            {
                if (first.getBaseTenExponent() < second.getBaseTenExponent()) 
                {
                	return 1.0;
                }
            }
        }
        else
        {
            if (operation.equals(ExistingOperations.GreaterOrEqual))
            {
                if (first.getValue() >= second.getValue()) 
                {
                	return 1.0;
                }
            }
            else if (operation.equals(ExistingOperations.Greater))
            {
                if (first.getValue() > second.getValue())
                {
                	return 1.0;
                }
            }
            else if (operation.equals(ExistingOperations.SmallerOrEqual))
            {
                if (first.getValue() <= second.getValue())
                {
                	return 1.0;
                }
            }
            else if (operation.equals(ExistingOperations.Smaller))
            {
                if (first.getValue() < second.getValue())
                {
                	return 1.0;
                }
            }
        }

        return 0.0;
    }

    public static String GetOperationString
    (
    	Object first, Object second, ExistingOperations operation, Locale culture
    )
    {
        return
        (
            //It doesn't matter whether first/second are NumberX variables or values, because all the NumberX classes
            //have their own ToString(CultureInfo) methods.
        	CSharpOtherNP.SpecificDoubleOrNumberXToString(first, culture)
        	 + " " + GetOperationSymbol(operation) + " " + 
            CSharpOtherNP.SpecificDoubleOrNumberXToString(second, culture) 
        );
    }

    static String GetOperationSymbol(ExistingOperations operation)
    {
        if (operation.equals(ExistingOperations.Addition)) return "+";
        if (operation.equals(ExistingOperations.Subtraction)) return "-";
        if (operation.equals(ExistingOperations.Multiplication)) return "*";
        if (operation.equals(ExistingOperations.Division)) return "/";
        if (operation.equals(ExistingOperations.GreaterOrEqual)) return ">=";
        if (operation.equals(ExistingOperations.Greater)) return ">";
        if (operation.equals(ExistingOperations.SmallerOrEqual)) return "<=";
        if (operation.equals(ExistingOperations.Smaller)) return "<";
        if (operation.equals(ExistingOperations.Modulo)) return "%";

        return "";
    }
}
