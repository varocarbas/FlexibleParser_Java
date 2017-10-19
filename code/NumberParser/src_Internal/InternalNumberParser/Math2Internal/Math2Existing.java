package InternalNumberParser.Math2Internal;

import java.util.ArrayList;
import java.util.Comparator;

import NumberParser.*;
import NumberParser.Number;
import InternalNumberParser.*;
import InternalNumberParser.CSharpAdaptation.*;
import InternalNumberParser.Operations.*;

@SuppressWarnings("serial")
public class Math2Existing
{
	//The Pow/Sqrt methods are expected to only be used with values supported
	//by the in-built alternatives. As far as the calculations are performed with
	//PowDecimal/PowSqrt, which can deal with values of any size, it is required to
	//check whether an error should be thrown. PowSqrtPrecheckInput focuses on 
	//analysing the input values.
	public static Number PowSqrtPrecheckInput(NumberD input)
	{
    	boolean isOK = true;
    	
    	NumberD input2 = new NumberD(input);
    	if 
    	(
    		!input2.getError().equals(ErrorTypesNumber.None)
    	)
    	{
    		isOK = false;
    	}
    	else
    	{
    		input2 = OperationsManaged.PassBaseTenToValue(input2);
        	if 
        	(
        		input2.getBaseTenExponent() > 0
        	)
        	{ isOK = false; }    		
    	}
    	
    	return (isOK ? new Number(input2) : null);
	}
	
    public static NumberD PerformOperationOneOperand(NumberD n, ExistingOperations operation)
    {
        NumberD n2 = AdaptInputsToMathMethod
        (
        	n, GetTypesOperation(operation), operation
        );
        
        if (!n2.getError().equals(ErrorTypesNumber.None)) 
        {
        	return new NumberD(n2.getError());
        }

        try
        {
            return ApplyMethod1(n2, operation);
        }
        catch (Exception e)
        {
            return new NumberD(ErrorTypesNumber.NativeMethodError);
        }
    }

    public static NumberD PerformOperationTwoOperands(NumberD n1, NumberD n2, ExistingOperations operation)
    {
    	ArrayList<NumberD> ns = CheckTwoOperands
        (
            new ArrayList<NumberD>() 
            {{ 
            	add(n1); add(n2); 
            }}
            , operation
        );
        if (!ns.get(0).getError().equals(ErrorTypesNumber.None)) 
        {
        	return ns.get(0);
        }

        try
        {
            return ApplyMethod2
            (
            	ns.get(0), ns.get(1), operation
            );
        }
        catch (Exception e)
        {
            return new NumberD
            (
            	ErrorTypesNumber.NativeMethodError
            );
        }
    }

    static ArrayList<NumberD> CheckTwoOperands(ArrayList<NumberD> ns, ExistingOperations operation)
    {
        ns = OrderTwoOperands(ns);

        for (int i = 0; i < ns.size(); i++)
        {
        	ArrayList<NumericTypes> item = null;
        	
			if (i == 0) 
			{
				item = new ArrayList<NumericTypes>
				(
					GetTypesOperation(operation)
				);
			}
			else
			{
				item = new ArrayList<NumericTypes>();
				item.add(ns.get(0).getType()); 
			}

            ns.set
            (
            	i, AdaptInputsToMathMethod
            	(
            		ns.get(i), item, operation
            	)
            );
            
            if (!ns.get(i).getError().equals(ErrorTypesNumber.None))
            {
                ArrayList<NumberD> tempArray = new ArrayList<NumberD>();
                
                tempArray.add
            	(
            		new NumberD(ns.get(i).getError())
            	); 
                
                return tempArray;
            }
        }

        return ns;
    }

    //When checking whether the input types are compatible with what the given Math method
    //expects, the order might matter.
    //Example: with double and int for a method expecting the same type, the double being analysed
    //first provoke the conclusion to always be OK (i.e., int being a different type but implicitly
    //convertible to double). On the other hand, if int was analysed first, it would be considered
    //invalid because double isn't implicitly convertible to int.
    static ArrayList<NumberD> OrderTwoOperands(ArrayList<NumberD> ns)
    {
        return OrderByDecimalAndRange(ns);
    }

	static ArrayList<NumericTypes> GetTypesOperation(ExistingOperations operation)
    {
        if 
        (
            operation == ExistingOperations.Ceiling || 
            operation == ExistingOperations.Floor || 
            operation == ExistingOperations.Truncate
        )
        { 
        	return new ArrayList<NumericTypes>() 
        	{{ 
        		add(NumericTypes.Double);
        	}}; 
        }

        if (operation == ExistingOperations.Abs || operation == ExistingOperations.Sign)
        {
            return new ArrayList<NumericTypes>() 
            {{
            	add(NumericTypes.Double); add(NumericTypes.Float); 
            	add(NumericTypes.Long); add(NumericTypes.Integer);
            	add(NumericTypes.Short);
            }};
        }

        if (operation == ExistingOperations.Max || operation == ExistingOperations.Min)
        {
        	return new ArrayList<NumericTypes>() 
            {{
            	add(NumericTypes.Double); add(NumericTypes.Float);
            	add(NumericTypes.Long); add(NumericTypes.Integer);
            	add(NumericTypes.Short); add(NumericTypes.Byte);
            }};
        }

        return new ArrayList<NumericTypes>() 
        {{
        	add(NumericTypes.Double); add(NumericTypes.Float); 	
        }};
    }

    //The given operation is associated with a Math method with just one argument.
    static NumberD ApplyMethod1(NumberD n, ExistingOperations operation)
    {
        try
        {
            n.setValue
            (
            	Conversions.CastDynamicToType(n.getValue(), n.getType())
            );

            if (operation == ExistingOperations.Abs)
            {
                n.setValue
                (
                	Math.abs
                	(
                		Conversions.ConvertTargetToDouble
                		(
                			n.getValue()
                		)
                	)
                );
                
                return n;
            }
            else if (operation == ExistingOperations.Ceiling)
            {
                n.setValue
                (
                	Math.ceil
                	(
                		Conversions.ConvertTargetToDouble
                    	(
                    		n.getValue()
                    	)
                	)
                );            	

                return n;
            }
            else if (operation == ExistingOperations.Floor)
            {
                n.setValue
                (
                	Math.floor
                	(
                		Conversions.ConvertTargetToDouble
                    	(
                    		n.getValue()
                    	)
                	)
                );  
                
                return n;
            }
            else if (operation == ExistingOperations.Truncate)
            {           	
                n.setValue
                (
                	Math2.Truncate(n).getValue()
                );  
                
                return n;
            }
            else if (operation == ExistingOperations.Sign)
            {
                n.setValue
                (
                	Math.signum
                	(
                		Conversions.ConvertTargetToDouble
                    	(
                    		n.getValue()
                        )
                    )
                );
                
                return n;
            }

            //The operation reaching this point matches the corresponding delegate perfectly.
            return ApplyMethod1Delegate(n, operation);
        }
        catch (Exception e) 
        { 
        	return new NumberD(ErrorTypesNumber.NativeMethodError); 
        }
    }

    //This method is a Java-adapted version of the original C# ApplyMethod1Delegate, which
    //cannot be perfectly replicated because of Java not supporting delegates.
    static NumberD ApplyMethod1Delegate(NumberD n, ExistingOperations operation)
    {
        if (operation == ExistingOperations.Acos)
        {
        	n.setValue
        	(
        		Math.acos
        		(
        			Conversions.ConvertTargetToDouble
                    (
                    	n.getValue()
                    )
        		)
        	);
        }
        else if (operation == ExistingOperations.Asin)
        {
        	n.setValue
        	(
        		Math.asin
        		(
            		Conversions.ConvertTargetToDouble
            		(
                       	n.getValue()
                    )
        		)
        	);
        }
        else if (operation == ExistingOperations.Atan)
        {
        	n.setValue
        	(
        		Math.atan
        		(
                	Conversions.ConvertTargetToDouble
                	(
                		n.getValue()
                	)
                )
        	);
        }
        else if (operation == ExistingOperations.Cos)
        {
        	n.setValue
        	(
        		Math.cos
        		(
                    Conversions.ConvertTargetToDouble
                    (
                    	n.getValue()
                    )
        		)
        	);
        }
        else if (operation == ExistingOperations.Cosh)
        {
        	n.setValue
        	(
        		Math.cosh
        		(
        			Conversions.ConvertTargetToDouble
                    (
                       	n.getValue()
                    )
        		)
        	);
        }  
        else if (operation == ExistingOperations.Exp)
        {
        	n.setValue
        	(
        		Math.exp
        		(
            		Conversions.ConvertTargetToDouble
            		(
                      	n.getValue()
            		)
        		)
        	);
        }         
        else if (operation == ExistingOperations.Log)
        {
        	n.setValue
        	(
        		Math.log
        		(
                	Conversions.ConvertTargetToDouble
                	(
                       	n.getValue()
                	)
        		)
        	);
        }        
        else if (operation == ExistingOperations.Log10)
        {
        	n.setValue
        	(
        		Math.log10
        		(
                    Conversions.ConvertTargetToDouble
                    (
                       	n.getValue()
                    )
        		)
        	);
        } 
        else if (operation == ExistingOperations.Sin)
        {
        	n.setValue
        	(
        		Math.sin
        		(
        			Conversions.ConvertTargetToDouble
        			(
                      	n.getValue()
                    )
        		)
        	);
        }
        else if (operation == ExistingOperations.Sinh)
        {
        	n.setValue
        	(
        		Math.sinh
        		(
            		Conversions.ConvertTargetToDouble
            		(
                       	n.getValue()
                    )
        		)
        	);
        }
        else if (operation == ExistingOperations.Tan)
        {
        	n.setValue
        	(
        		Math.tan
        		(
        			Conversions.ConvertTargetToDouble
        			(
                       	n.getValue()
                    )
        		)
        	);
        }
        else if (operation == ExistingOperations.Tanh)
        {
        	n.setValue
        	(
        		Math.tanh
        		(
            		Conversions.ConvertTargetToDouble
            		(
                       	n.getValue()
                    )
        		)
        	);
        }        
        
        return
        (
            ErrorInfoNumber.InputTypeIsValidNumeric
            (
            	n.getValue()
            ) 
            != null ? n : new NumberD
            (
            	ErrorTypesNumber.NativeMethodError
            )
        );
    }

    static NumberD ApplyMethod2(NumberD n1, NumberD n2, ExistingOperations operation)
    {
        try
        {
            n1.setValue
            (
            	Conversions.CastDynamicToType
            	(
            		n1.getValue(), n1.getType()
            	)
            );
            
            n2.setValue
            (
            	Conversions.CastDynamicToType
            	(
            		n2.getValue(), n2.getType()
            	)
            );            

            if (operation == ExistingOperations.Min)
            {
            	n1.setValue
            	(
            		Math.min
            		(
            			Conversions.ConvertTargetToDouble
                    	(
                    		n1.getValue()
                        ), 
                    	Conversions.ConvertTargetToDouble
                    	(
                    		n2.getValue()
                        )
            		)
            	);

                return n1;
            }
            else if (operation == ExistingOperations.Max)
            {
            	n1.setValue
            	(
            		Math.max
            		(
                		Conversions.ConvertTargetToDouble
                        (
                        	n1.getValue()
                        ), 
                        Conversions.ConvertTargetToDouble
                        (
                        	n2.getValue()
                        )
            		)
            	);
            	
                return n1;
            }

            //The operation reaching this point matches the corresponding delegate perfectly.
            return ApplyMethod2Delegate(n1, n2, operation);
        }
        catch (Exception e) 
        { 
        	return new NumberD(ErrorTypesNumber.NativeMethodError); 
        }
    }

    //This method is a Java-adapted version of the original C# ApplyMethod1Delegate, which
    //cannot be perfectly replicated because of Java not supporting delegates.
    static NumberD ApplyMethod2Delegate(NumberD n1, NumberD n2, ExistingOperations operation)
    {
        if (operation == ExistingOperations.Atan2)
        {
        	n1.setValue
        	(
        		Math.atan2
        		(
                	Conversions.ConvertTargetToDouble
                	(
                       	n1.getValue()
                    ), 
                	Conversions.ConvertTargetToDouble
                	(
                       	n2.getValue()
                    )
        		)
        	);
        }
        else if (operation == ExistingOperations.IEEERemainder)
        {
        	n1.setValue
        	(
        		Math.IEEEremainder
        		(
                    Conversions.ConvertTargetToDouble
                    (
                       	n1.getValue()
                    ), 
                    Conversions.ConvertTargetToDouble
                    (
                       	n2.getValue()
                    )
        		)
        	);
        }
        else if (operation == ExistingOperations.Log)
        {
        	n1.setValue
        	(
        		Math.log
        		(
        			Conversions.ConvertTargetToDouble
        			(
        				n1.getValue()
        			)
        		) 
        		/
        		Math.log
        		(
        			Conversions.ConvertTargetToDouble
        			(
        				n2.getValue()
        			)
        		)
        	);
        }

        return
        (
            ErrorInfoNumber.InputTypeIsValidNumeric
            (
            	n1.getValue()
            ) 
            != null ? n1 : new NumberD
            (
            	ErrorTypesNumber.NativeMethodError
            )
        );
    }

    //Method adapting the input variable to the requirements of the given Math method.
    //If relying on the original type isn't possible, a conversion to double (i.e., biggest-range
    //type which is supported by all the Math methods when reaching here) would be performed.
    public static NumberD AdaptInputsToMathMethod(NumberD n, ArrayList<NumericTypes> targets, ExistingOperations operation)
    {
        if (n == null) return new NumberD(ErrorTypesNumber.InvalidInput);
        if (!n.getError().equals(ErrorTypesNumber.None)) return new NumberD(n.getError());
        if (ErrorInfoNumber.InputTypeIsValidNumeric(n.getValue()) == null)
        {
            return new NumberD(ErrorTypesNumber.InvalidInput);
        }

        NumberD n2 = AdaptInputToMathMethod2(new NumberD(n), targets, operation);
        if (!n2.getError().equals(ErrorTypesNumber.None)) return n2;           

        if (n2.getBaseTenExponent() != 0) 
        {
        	n2 = OperationsManaged.PassBaseTenToValue((NumberD)n2);
        }

        if (n2.getBaseTenExponent() != 0)
        {
            if (!n2.getType().equals(NumericTypes.Double))
            {
                n2.setValue
                (
                	Conversions.ConvertToDoubleInternal(n2.getValue())
                );
                n2 = OperationsManaged.PassBaseTenToValue(n2);
            }

            if (n2.getBaseTenExponent() != 0)
            {
                //The value is outside the maximum supported range by the given Math method.
                n2 = new NumberD(ErrorTypesNumber.NativeMethodError);
            }
        }

        return n2;
    }

    static NumberD AdaptInputToMathMethod2(NumberD n2, ArrayList<NumericTypes> targets, ExistingOperations operation)
    {
        if (!targets.contains(n2.getType()))
        {
            NumberD n3 = AdaptInputToMathMethodImplicit(n2, targets);
            if (n3 != null) return n3;

            if 
            (
            	!n2.getType().equals(NumericTypes.Double) && 
            	!operation.equals(ExistingOperations.DivRem) && 
            	!operation.equals(ExistingOperations.BigMul)
            )
            {
                //Except DivRem and BigMul, all the Math methods accept double as argument.
                n2.setValue
                (
                	Conversions.ConvertToDoubleInternal(n2.getValue())	
                );
            }
            else return new NumberD(ErrorTypesNumber.NativeMethodError);
        }
        
        if 
        (
        	!n2.getType().equals(NumericTypes.Double) && 
        	!operation.equals(ExistingOperations.DivRem) && 
        	!operation.equals(ExistingOperations.BigMul)
        )
        {
            //Except DivRem and BigMul, all the Math methods accept double as argument.
            if (!targets.contains(n2.getType()))
            {
                n2.setValue
                (
                	Conversions.ConvertToDoubleInternal(n2.getValue())	
                );
            }
        }

        return n2;
    }

    private static NumberD AdaptInputToMathMethodImplicit(NumberD n, ArrayList<NumericTypes> targets)
    {
        if (!Basic.AllDecimalTypes.contains(n.getType()))
        {
        	NumericTypes target = LinqNP.FirstOrDefault
            (
            	OrderByDecimalAndRangeTypes(targets), null
            );
            
            if (Basic.AllDecimalTypes.contains(target))
            {
                //n is an integer and one of the targets is decimal. This means that an implicit
                //conversion is possible and, consequently, no further analysis is required to
                //conclude that the current input scenario is valid.
                return Conversions.ConvertNumberToAny
                (
                	new Number(n), target
                );
            }
        }

        return null;
    }

    static int CompareTypes(NumericTypes first, NumericTypes second)
    {
		Boolean val01 = Basic.AllDecimalTypes.contains(first);
		Boolean val02 = Basic.AllDecimalTypes.contains(second);

		//.OrderByDescending
		//(
		//	x => Basic.AllDecimalTypes.Contains(x.Type)
		//)
		//in the original C# code.
		int res = val01.compareTo(val02);
		if (res != 0) return res;

		Double val11 = 
		(
			Conversions.ConvertTargetToDouble
			(
				Basic.AllNumberMinMaxs.get(first).get(1)
			) 
			- 
			Conversions.ConvertTargetToDouble
			(
				Basic.AllNumberMinMaxs.get(first).get(0)
			)
		);

		Double val12 =
		(
			Conversions.ConvertTargetToDouble
			(
				Basic.AllNumberMinMaxs.get(second).get(1)
			)
			- 
			Conversions.ConvertTargetToDouble
			(
				Basic.AllNumberMinMaxs.get(second).get(0)
			)
		);

		//.ThenByDescending
		//(
		//    x => Convert.ToDouble(Basic.AllNumberMinMaxs[x.Type][1]) - 
		//    Convert.ToDouble(Basic.AllNumberMinMaxs[x.Type][0])
		//)
		//in the original C# code.
		return val11.compareTo(val12);    	
    }
    
    static ArrayList<NumberD> OrderByDecimalAndRange(ArrayList<NumberD> ns)
    {
    	return LinqNP.OrderByDescending
    	(
    		ns, new Comparator<NumberD>()
    		{
    			public int compare(NumberD first, NumberD second)
    			{
    				return CompareTypes
    				(
    					first.getType(), second.getType()
    				);
    			}        				
    		}
    	);
    }
    
    static ArrayList<NumericTypes> OrderByDecimalAndRangeTypes(ArrayList<NumericTypes> types)
    {
    	return LinqNP.OrderByDescending
    	(
    		types, new Comparator<NumericTypes>()
    		{
    			public int compare(NumericTypes first, NumericTypes second)
    			{
    				return CompareTypes(first, second);
    			}        				
    		}
    	);
    }    
}