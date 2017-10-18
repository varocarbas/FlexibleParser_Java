package InternalNumberParser.Operations;

import NumberParser.*;
import NumberParser.Number;
import InternalNumberParser.*;
import InternalNumberParser.CSharpAdaptation.*;
import InternalNumberParser.Math2Internal.*;

public class OperationsManaged
{
    public static NumberP PerformArithmeticOperation
    (
    	NumberP first, NumberP second, ExistingOperations operation
    )
    {
    	return PerformArithmeticOperation
        (
        	first, second, operation, true
        );
    }
    
    public static NumberP PerformArithmeticOperation
    (
    	NumberP first, NumberP second, 
    	ExistingOperations operation, boolean baseTenToValue
    )
    {
        return new NumberP
        (
            PerformArithmeticOperation
            (
                new Number(first), new Number(second), operation
            ),
            OperationsMain.GetOperationString
            (
            	first, second, operation, 
            	first.getParseConfig().getCulture()
            ), 
            first.getParseConfig()
        );
    }

    public static NumberO PerformArithmeticOperation
    (
    	NumberO first, NumberO second, ExistingOperations operation
    )
    {
    	return PerformArithmeticOperation
    	(
    		first, second, operation, true
    	);
    }
    
    public static NumberO PerformArithmeticOperation
    (
    	NumberO first, NumberO second, 
    	ExistingOperations operation, 
    	boolean baseTenToValue
    )
    {
        return new NumberO
        (
            new NumberO
            (
            	PerformArithmeticOperation
            	(
            		new Number(first), new Number(second), operation
            	)
            ),
            LinqNP.Select(first.getOthers(), x -> x.getType())
        );
    }

    public static NumberD PerformArithmeticOperation
    (
    	NumberD first, NumberD second, ExistingOperations operation
    )
    {
    	return PerformArithmeticOperation
    	(
    		first, second, operation, true
    	);
    }
    
    public static NumberD PerformArithmeticOperation
    (
    	NumberD first, NumberD second, ExistingOperations operation, boolean baseTenToValue
    )
    {
        Number number = PerformArithmeticOperation
        (
            new Number(first), new Number(second), operation
        );

        return
        (
            !number.getError().equals(ErrorTypesNumber.None) ? 
            new NumberD(number.getError()) : new NumberD
            (
                number.getValue(), number.getBaseTenExponent(), first.getType()
            )
        );
    }

    public static Number PerformArithmeticOperation
    (
    	Number first, double second, ExistingOperations operation
    )
    {
    	return PerformArithmeticOperation(first, second, operation, true);
    }
    
    public static Number PerformArithmeticOperation
    (
    	Number first, double second, ExistingOperations operation, boolean baseTenToValue
    )
    {
        return PerformArithmeticOperation
        (
            first, new Number(second), operation
        );
    }

    public static Number MultiplyInternal(Number first, Number second)
    {
        return PerformArithmeticOperation
        (
            first, second, ExistingOperations.Multiplication, false
        );
    }

    public static NumberD MultiplyInternal(NumberD first, NumberD second)
    {
        return PerformArithmeticOperation
        (
            first, second, ExistingOperations.Multiplication, false
        );
    }

    public static Number DivideInternal(Number first, Number second)
    {
        return PerformArithmeticOperation
        (
            first, second, ExistingOperations.Division, false
        );
    }

    public static NumberD DivideInternal(NumberD first, NumberD second)
    {
        return PerformArithmeticOperation
        (
            first, second, ExistingOperations.Division, false
        );
    }

    public static Number AddInternal(Number first, Number second)
    {
        return PerformArithmeticOperation
        (
            first, second, ExistingOperations.Addition, false
        );
    }

    public static NumberD AddInternal(NumberD first, NumberD second)
    {
        return PerformArithmeticOperation
        (
            first, second, ExistingOperations.Addition, false
        );
    }

    public static NumberD SubtractInternal(NumberD first, NumberD second)
    {
        return PerformArithmeticOperation
        (
            first, second, ExistingOperations.Subtraction, false
        );
    }
    
    public static Number PerformArithmeticOperation
    (
    	Number first, Number second, ExistingOperations operation
    )
    {
    	return PerformArithmeticOperation(first, second, operation, true);
    }
    
    public static Number PerformArithmeticOperation
    (
    	Number first, Number second, ExistingOperations operation, boolean baseTenToValue
    )
    {
        ErrorTypesNumber error = ErrorInfoNumber.GetOperationError
        (
            first, second, operation
        );
        if (!error.equals(ErrorTypesNumber.None)) return new Number(error);

        Number outNumber = 
        (
            operation.equals(ExistingOperations.Addition) || 
            operation.equals(ExistingOperations.Subtraction) ?
            PerformManagedOperationAddition
            (
                new Number(first), new Number(second), operation
            ) : 
            PerformManagedOperationMultiplication
            (
                new Number(first), new Number(second), operation
            )
        );

        return 
        (
            baseTenToValue ? PassBaseTenToValue(outNumber, true) : outNumber
        );
    }

    static Number PerformManagedOperationAddition(Number first, Number second, ExistingOperations operation)
    {
        return PerformManagedOperationNormalisedValues
        (
            first,
            //In addition/subtraction, the normalised operands might require further modifications. That's why
            //calling a specific method rather than the generic NormaliseNumber.
            GetOperandsAddition(first, second, operation),
            operation
        );
    }

    static Number[] GetOperandsAddition(Number first, Number second, ExistingOperations operation)
    {
        Number[] operands = new Number[] 
        { 
            NormaliseNumber(first), NormaliseNumber(second) 
        };
        
        return
        (
            operands[0].getBaseTenExponent() != operands[1].getBaseTenExponent() ?
            //The addition/subtraction might not be performed right away even with normalised values.
            AdaptNormalisedValuesForAddition(operands, operation) : operands
        );
    }

    static Number[] AdaptNormalisedValuesForAddition(Number[] operands, ExistingOperations operation)
    {
        if (operands[0].getBaseTenExponent() == operands[1].getBaseTenExponent())
        {
            //Having the same BaseTenExponent means that the given operation can be performed right away.
            return operands;
        }

        int[] bigSmallI =
        (
            operands[0].getBaseTenExponent() > 
            operands[1].getBaseTenExponent() ?
            new int[] { 0, 1 } : new int[] { 1, 0 }
        );

        //Only the variable with the bigger value is modified. For example: 5*10^5 & 3*10^3 is converted
        //into 500*10^3 & 3*10^3 in order to allow the addition 500 + 3. 
        Number big2 = AdaptBiggerAdditionOperand(operands, bigSmallI, operation);

        if (!big2.getError().equals(ErrorTypesNumber.None))
        {
            return TooBigGapAddition(operands, bigSmallI, operation);
        }
        
        operands[bigSmallI[0]].setValue(big2.getValue());
        operands[bigSmallI[0]].setBaseTenExponent
        (
        	operands[bigSmallI[1]].getBaseTenExponent()
        );

        return operands;
    }

    //When adding/subtracting two numbers whose gap is bigger than the maximum decimal precision, there
    //is no need to perform any operation (no change will be observed anyway). This method takes care 
    //of these cases and returns the expected output (i.e., biggest value).
    static Number[] TooBigGapAddition(Number[] operands, int[] bigSmallI, ExistingOperations operation)
    {
        Number[] outOperands = new Number[] 
        {
            //First operand together with the numeric information (i.e., Value and BaseTenExponent) which
            //is associated with the biggest one.
            new Number(operands[0])
        };
        
    	outOperands[0].setValue(operands[bigSmallI[0]].getValue());
    	outOperands[0].setBaseTenExponent(operands[bigSmallI[0]].getBaseTenExponent());

        if (operation.equals(ExistingOperations.Subtraction) && bigSmallI[0] == 1)
        {
            outOperands[0].setValue(-1.0 * outOperands[0].getValue());
        }

        return outOperands;
    }

    static Number AdaptBiggerAdditionOperand(Number[] operands, int[] bigSmallI, ExistingOperations operation)
    {
        int gapExponent = 
        (
        	operands[bigSmallI[0]].getBaseTenExponent() - 
        	operands[bigSmallI[1]].getBaseTenExponent()
        );

        if (gapExponent >= 27)
        {
            //The difference between both inputs is bigger than (or, at least, very close to) the maximum decimal value/precision;
            //what makes this situation calculation unworthy and the first operand to be returned as the result.
            //Note that the error below these lines is just an easy way to tell the calling function about this eventuality.
            return new Number
            (
                ErrorTypesNumber.NumericOverflow
            );
        }

        //PerformArithmeticOperationValues is used to make sure that the resulting numeric information is stored
        //in Value (if possible).
        Number big2 = PerformArithmeticOperationValues
        (
        	Math2PowSqrt.PowSqrtInternal(new Number(10.0), gapExponent, false),
            new Number(operands[bigSmallI[0]].getValue()), 
            ExistingOperations.Multiplication
        );

        boolean isWrong =
        (
            !big2.getError().equals(ErrorTypesNumber.None) || big2.getBaseTenExponent() != 0 ?

            //The value of the bigger input times 10^(gap between BaseTenExponent of inputs) is too big. 
            isWrong = true :

            //Overflow-check very unlikely to trigger an error.              
            AreAdditionFinalValuesWrong
            (
                operands[0].getValue(), operands[1].getValue(), operation
            )
        );

        return
        (
            isWrong ?
            //This error is just an easy way to let the calling function know about the fact that no
            //calculation has been performed (too big gap). This isn't a properly-speaking error and
            //that's why it will not be notified to the user.
            new Number(ErrorTypesNumber.InvalidInput) :
            //Returning the new big value. For example: with 5*10^4 & 3*10^2, 500 would be returned.
            new Number(operands[bigSmallI[0]])
            {{
                setValue(big2.getValue());
            }}
        );
    }

    static boolean AreAdditionFinalValuesWrong(double val1, double val2, ExistingOperations operation)
    {
        boolean isWrong = false;

        try
        {
            val1 = val1 + val2 *
            (
                operation == ExistingOperations.Addition ? 1 : -1
            );
        }
        catch(Exception e) { isWrong = true; }

        return isWrong;
    }

    static Number PerformManagedOperationMultiplication(Number first, Number second, ExistingOperations operation)
    {
        return PerformManagedOperationNormalisedValues
        (
            first, new Number[] 
            { 
                NormaliseNumber(first), NormaliseNumber(second) 
            },
            operation
        );
    }

    static Number PerformManagedOperationNormalisedValues(Number outInfo, Number[] normalised, ExistingOperations operation)
    {
        return
        (
            normalised.length == 1 ?
            //There is just one operand when the difference between both of them is too big.
            outInfo = normalised[0] :
            PerformManagedOperationTwoOperands(outInfo, normalised, operation)
        );
    }

    static Number PerformManagedOperationTwoOperands(Number outNumber, Number[] normalised, ExistingOperations operation)
    {
        if (!outNumber.getError().equals(ErrorTypesNumber.None)) return new Number(outNumber);

        Number tempNumber = PerformArithmeticOperationValues
        (
            normalised[0], normalised[1], operation
        );
        if (!tempNumber.getError().equals(ErrorTypesNumber.None)) return tempNumber;


        outNumber.setBaseTenExponent(tempNumber.getBaseTenExponent());
        outNumber.setValue(tempNumber.getValue());

        return outNumber;
    }

    static Number PerformArithmeticOperationValues(Number first, Number second, ExistingOperations operation)
    {
        if (first.getValue() == 0.0 || second.getValue() == 0.0)
        {
            if 
            (
            	operation.equals(ExistingOperations.Multiplication) || 
            	operation.equals(ExistingOperations.Division)
            )
            {
                //Dividing by zero scenarios are taken care of somewhere else.
                return new Number(first) {{ setValue(0.0); }};
            }
        }

        Number output = new Number(first);
        Number first2 = new Number(first);
        Number second2 = new Number(second);

        boolean isWrong = false;
        try
        {
            if (operation.equals(ExistingOperations.Addition))
            {
                output.setValue(output.getValue() + second2.getValue());
            }
            else if (operation.equals(ExistingOperations.Subtraction))
            {
                output.setValue(output.getValue() - second.getValue());
            }
            else
            {
                //The reason for checking whether BaseTenExponent is inside/outside the int range before performing 
                //the operation (rather than going ahead and eventually catching the resulting exception) isn't just
                //being quicker, but also the only option in many situations. Note that an addition/subtraction between
                //two int variables whose result is outside the int range might not trigger an exception (+ random 
                //negative value as output).
                Number tempVar = (Number)OperationsOther.VaryBaseTenExponent
                (
                    output, second.getBaseTenExponent(), 
                    operation.equals(ExistingOperations.Division)
                );
                if (!tempVar.getError().equals(ErrorTypesNumber.None)) return tempVar;

                if (operation.equals(ExistingOperations.Multiplication))
                {
                    output.setValue(output.getValue() * second2.getValue());
                    output.setBaseTenExponent
                    (
                    	output.getBaseTenExponent() + second2.getBaseTenExponent()
                    );
                }
                else if (operation.equals(ExistingOperations.Division))
                {
                    if (second.getValue() == 0.0)
                    {
                        return new Number(ErrorTypesNumber.InvalidOperation);
                    }
                    output.setValue(output.getValue() / second2.getValue());
                    output.setBaseTenExponent
                    (
                    	output.getBaseTenExponent() - 
                    	second2.getBaseTenExponent()
                    );
                }
            }
        }
        catch(Exception e) { isWrong = true; }

        return
        (
            //An exception might not be triggered despite of dealing with numbers outside decimal precision.
            //For example: 0.00000000000000000001m * 0.0000000000000000000001m can plainly output 0m. 
            isWrong || 
            (
            	(
            		operation.equals(ExistingOperations.Multiplication) || 
            		operation.equals(ExistingOperations.Division)
            	) 
            	&& output.getValue() == 0.0
            ) 
            ? OperationValuesManageError(first2, second2, operation) : output
        );
    }

    static Number OperationValuesManageError(Number outNumber, Number second, ExistingOperations operation)
    {
        Number tempVar = OperationValuesManageErrorPreAnalysis(outNumber, second, operation);
        if (tempVar != null) return tempVar;

        Number second2 = new Number(Math.abs(second.getValue()));
        second2 = FromValueToBaseTenExponent
        (
        	second2.getValue(), second2.getBaseTenExponent()
        );

        outNumber = (Number)OperationsOther.VaryBaseTenExponent
        (
            outNumber, 
            (
            	operation.equals(ExistingOperations.Multiplication) ? 1 : -1
            ) 
            * second2.getBaseTenExponent()
        );

        if (Math.abs(second2.getValue()) == 1.0) return outNumber;

        try
        {
            outNumber = PerformArithmeticOperation
            (
            	outNumber, second2.getValue(), operation
            );
        }
        catch (Exception e)
        {
        	Number outNumber1 = new Number(outNumber);
        	outNumber1.setValue(second2.getValue());
        	outNumber1.setBaseTenExponent(0);

        	Number outNumber2 = new Number();
        	outNumber2.setValue(outNumber.getValue());
        	outNumber2.setBaseTenExponent
        	(
        		outNumber.getBaseTenExponent()
        	);

            outNumber = OperationValuesManageError
            (
            	outNumber1, outNumber2, operation
            );
        }

        return outNumber;
    }

    static Number OperationValuesManageErrorPreAnalysis(Number outNumber, Number second, ExistingOperations operation)
    {
        if (!operation.equals(ExistingOperations.Multiplication) && !operation.equals(ExistingOperations.Division))
        {
            //This condition should never be true on account of the fact that the pre-modifications performed before
            //adding/subtracting should avoid erroneous situations.
            return new Number(ErrorTypesNumber.NumericOverflow);
        }

        //Accounting for some limit cases which might reach this point and provoke and infinite set of recursive calls.
        if 
        (
        	operation.equals(ExistingOperations.Multiplication) && Math.abs
        	(
        		outNumber.getBaseTenExponent() + second.getBaseTenExponent()
        	) 
        	== Integer.MAX_VALUE
        )
        {
            return new Number(ErrorTypesNumber.NumericOverflow);
        }
        
        if 
        (
        	operation.equals(ExistingOperations.Division) && 
        	(
        		outNumber.getBaseTenExponent() - 
        		second.getBaseTenExponent()
        	) 
        	== Integer.MIN_VALUE
        )
        {
            return new Number(ErrorTypesNumber.NumericOverflow);
        }

        return null;
    }

    //This method is called when performing an arithmetic operation between two variables of a random
    //numeric type (i.e., dynamic numeric variable) and makes sure that the output type matches the 
    //one of the inputs. Note that, in some cases, an operation between two variables of the same type
    //might output a different one (e.g., sbyte * sbyte = int).
    //This method assumes that both inputs are non-null and belong to the same numeric type.
    static Object PerformArithmeticOperationDynamicVariables(Object var1, Object var2, ExistingOperations operation)
    {
        return Conversions.CastDynamicToType
        (
            PerformArithmeticOperationDynamicVariablesValues
            (
                var1, var2, operation
            ), 
            NumericTypesMethods.GetTypeFromObject(var1)
        );
    }
    
    static Object PerformArithmeticOperationDynamicVariablesValues(Object var1, Object var2, ExistingOperations operation)
    {
    	double var11 = Conversions.ConvertTargetToDouble(var1);
    	double var22 = Conversions.ConvertTargetToDouble(var2);
    	
        if (operation.equals(ExistingOperations.Multiplication))
        {
            return Conversions.CastDynamicToType
            (
            	var11 * var22, 
            	NumericTypesMethods.GetTypeFromObject
            	(
            		var1
            	)
            );
        }
        else if (operation.equals(ExistingOperations.Division))
        {
            return Conversions.CastDynamicToType
            (
            	var11 / var22, 
            	NumericTypesMethods.GetTypeFromObject
            	(
            		var1
            	)
            );
        }
        else if (operation.equals(ExistingOperations.Addition))
        {
            return Conversions.CastDynamicToType
            (
            	var11 + var22, 
            	NumericTypesMethods.GetTypeFromObject
            	(
            		var1
            	)
            );
        }
        else if (operation.equals(ExistingOperations.Subtraction))
        {
            return Conversions.CastDynamicToType
            (
            	var11 - var22, 
            	NumericTypesMethods.GetTypeFromObject
            	(
            		var1
            	)
            );
        }

        return null;
    }

    public static NumberD PassBaseTenToValue(NumberD input)
    {
    	return PassBaseTenToValue(input, false);
    }
    
    public static NumberD PassBaseTenToValue(NumberD input, boolean showUser)
    {
        NumberD output = new NumberD(input);
        if (input.getBaseTenExponent() == 0) return output;

        output = OperationsOther.AbsInternal(output);
        if (!output.getError().equals(ErrorTypesNumber.None))
        {
            //Although it is extremely unlikely, calculating the absolute value might provoke to go beyond the
            //maximum supported range (i.e., BaseTenExponent outside the int range).
            return new NumberD(ErrorTypesNumber.InvalidOperation);
        }

        if (showUser && Conversions.ConvertTargetToDouble(output.getValue()) < 1.0)
        {
            //The opposite to passing to value, but what is expected anyway.
            return NormaliseNumber(input);
        }

        boolean decrease = input.getBaseTenExponent() > 0;
        double sign = Conversions.ConvertTargetToDouble
        (
        	PerformArithmeticOperationDynamicVariables
        	(
        		Conversions.ConvertTargetToDouble(input.getValue()), 
        		Conversions.ConvertTargetToDouble(output.getValue()), 
        		ExistingOperations.Division
        	)
        );
        
        //Apparently, the precision problems of the (Java) Double type appear more easily with
        //divisions. That's why I am including here two variations, rather than one like in the C#
        //version: to rely on multiplications in all the possible scenarios.
        double varMult = 10.0;
        double varDiv = 0.1;
        double one = 1.0;
        
        while (output.getBaseTenExponent() != 0)
        {
            if (decrease)
            {
                if 
                (
                	Conversions.ConvertTargetToDouble(output.getValue()) > 
                	Conversions.ConvertTargetToDouble
                	(
                		Basic.AllNumberMinMaxPositives.get
                		(
                			output.getType()
                		)
                		.get(1)
                	)
                	* varDiv
                ) 
                { break; }
                
                output.setValue
                (
                	PerformArithmeticOperationDynamicVariables
                	(
                		output.getValue(), varMult, 
                		ExistingOperations.Multiplication
                	)
                );
                
                output.setBaseTenExponent
                (
                	output.getBaseTenExponent() - 1
                );
            }
            else
            {
                if 
                (
                	(
                		showUser && Conversions.ConvertTargetToDouble
                		(output.getValue()) * varDiv < one
                	) 
                	|| 
                	Conversions.ConvertTargetToDouble(output.getValue()) < 
                	Conversions.ConvertTargetToDouble
                	(
                		Basic.AllNumberMinMaxPositives.get
                		(
                			output.getType()
                		)
                		.get(0)
                	)
                	* varMult
                )
                { break; }
                
                output.setValue
                (
                	PerformArithmeticOperationDynamicVariables
                	(
                		output.getValue(), varDiv, 
                		ExistingOperations.Multiplication
                	)
                );
                
                output.setBaseTenExponent
                (
                	output.getBaseTenExponent() + 1
                );
            }
        }

        output.setValue
        (
        	PerformArithmeticOperationDynamicVariables
        	(
        		output.getValue(), sign, 
        		ExistingOperations.Multiplication
        	)
        );

        return output;
    }

    public static Number PassBaseTenToValue(Number input)
    {
    	return PassBaseTenToValue(input, false);
    }
    
    public static Number PassBaseTenToValue(Number input, boolean showUser)
    {
        Number output = new Number(input);
        if (input.getBaseTenExponent() == 0) return output;

        double absValue = Math.abs(output.getValue());

        if (showUser && absValue < 1.0)
        {
            //The opposite to passing to value, but what is expected anyway.
            return NormaliseNumber(output);
        }

        boolean decrease = output.getBaseTenExponent() > 0;
        double sign = output.getValue() / absValue;
        output.setValue(absValue);

        while (output.getBaseTenExponent() != 0)
        {
            if (decrease)
            {
                if 
                (
                	output.getValue() > 
                	(double)Basic.AllNumberMinMaxPositives.get
                	(
                		NumericTypes.Double
                	)
                	.get(1) / 10.0
                )
                { break; }
                
                output.setValue(output.getValue() * 10.0);
                output.setBaseTenExponent(output.getBaseTenExponent() - 1);
            }
            else
            {
                if 
                (
                	(showUser && output.getValue() / 10.0 < 1.0) || 
                	output.getValue() < (double)Basic.AllNumberMinMaxPositives.get
                	(
                		NumericTypes.Double
                	)
                	.get(0) * 10.0
                )
                { break; }
                
                output.setValue(output.getValue() / 10.0);
                output.setBaseTenExponent(output.getBaseTenExponent() + 1);
            }
        }

        output.setValue(output.getValue() * sign);

        return output;
    }

    static NumberD NormaliseNumber(NumberD numberD)
    {
        if (numberD.getBaseTenExponent() == Integer.MAX_VALUE)
        {
            return new NumberD(numberD);
        }

        Number tempVar = new Number(numberD);
        tempVar = FromValueToBaseTenExponent
        (
            tempVar.getValue(), tempVar.getBaseTenExponent(), 
            Basic.AllDecimalTypes.contains(numberD.getType())
        );

        return new NumberD
        (
            tempVar.getValue(), tempVar.getBaseTenExponent(), numberD.getType()
        );
    }

    static Number NormaliseNumber(Number number)
    {
        Number outNumber = new Number(number);
        if (outNumber.getBaseTenExponent() == Integer.MAX_VALUE) 
        {
        	return outNumber;
        }

        return FromValueToBaseTenExponent
        (
            outNumber.getValue(), outNumber.getBaseTenExponent()
        );
    }

    static Number FromValueToBaseTenExponent(double value, int baseTenExponent)
    {
    	return FromValueToBaseTenExponent(value, baseTenExponent, true);
    }
    
    static Number FromValueToBaseTenExponent(double value, int baseTenExponent, boolean decimals)
    {
        Number outNumber = new Number(value, baseTenExponent);
        if (value == 0.0) return outNumber;

        double valueAbs = Math.abs(value);
        boolean decrease = (valueAbs > 1.0);

        while 
        (
        	valueAbs != 1.0 && 
        	(
        		(decrease && outNumber.getBaseTenExponent() <= Integer.MAX_VALUE - 1) || 
        		(!decrease && outNumber.getBaseTenExponent() >= Integer.MIN_VALUE + 1)
        	)
        )
        {
            if ((decrease && valueAbs < 10.0) || (!decrease && valueAbs >= 1.0))
            {
                return outNumber;
            }

            value = (decrease ? value / 10.0 : value * 10.0);
            if (!decimals && Math.floor(value) != value)
            {
                return outNumber;
            }

            outNumber.setValue(value);
            outNumber.setBaseTenExponent
            (
            	outNumber.getBaseTenExponent() +
            	(decrease ? 1 : -1)
            );

            valueAbs = Math.abs(value);
        }

        return outNumber;
    }
}