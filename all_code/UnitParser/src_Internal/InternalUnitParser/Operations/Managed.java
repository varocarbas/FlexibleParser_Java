package InternalUnitParser.Operations;

import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Classes.*;
import InternalUnitParser.Methods.*;
import UnitParser.*;
import UnitParser.UnitP.*;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Managed
{
	public static UnitInfo PerformManagedOperationUnits(UnitInfo firstInfo, double second, Operations operation)
	{
		return PerformManagedOperationUnits
		(
			firstInfo, ExceptionInstantiation.NewUnitInfo(second), operation
		);
	}

	public static UnitInfo PerformManagedOperationUnits(double first, UnitInfo secondInfo, Operations operation)
	{
		return PerformManagedOperationUnits
		(
			ExceptionInstantiation.NewUnitInfo(first), secondInfo, operation
		);
	}

	//This method should always be used when dealing with random UnitInfo variables because it accounts for all the
	//possible scenarios. On the other hand, with simple operations (e.g., random UnitInfo & numeric type) it might
	//be better to use PerformManagedOperationValues. 
	public static UnitInfo PerformManagedOperationUnits(UnitInfo firstInfo, UnitInfo secondInfo, Operations operation)
	{
		ErrorTypes errorType = MethodsCommon.GetOperationError
		(
			firstInfo, secondInfo, operation
		);
		if (errorType != ErrorTypes.None)
		{
			return ExceptionInstantiation.NewUnitInfo(firstInfo, errorType);
		}

		return
		(
			operation == Operations.Addition || operation == Operations.Subtraction ?
			PerformManagedOperationAddition(firstInfo, secondInfo, operation) :
			PerformManagedOperationMultiplication(firstInfo, secondInfo, operation)
		);
	}

	static UnitInfo PerformManagedOperationAddition(UnitInfo firstInfo, UnitInfo secondInfo, Operations operation)
	{   
		return PerformManagedOperationNormalisedValues
		(
			//After being normalised, the operands might require further modifications.
			firstInfo, GetOperandsAddition(firstInfo, secondInfo, operation), operation
		);
	}

	static ArrayList<UnitInfo> GetOperandsAddition(UnitInfo firstInfo, UnitInfo secondInfo, Operations operation)
	{
		ArrayList<UnitInfo> operands2 = new ArrayList<UnitInfo>() 
		{{
			add(ExceptionInstantiation.NewUnitInfo(firstInfo)); add(ExceptionInstantiation.NewUnitInfo(secondInfo));
		}};

		if (operands2.get(0).BaseTenExponent != operands2.get(1).BaseTenExponent || operands2.get(0).Prefix.getFactor() != operands2.get(1).Prefix.getFactor())
		{
			//The addition/subtraction might not be performed right away even with normalised values.
			//For example: 5 and 6 from 5*10^2 and 6*10^7 cannot be added right away.
			
			ArrayList<UnitInfo> operandArgs = new ArrayList<UnitInfo>();
			operandArgs.add(NormaliseUnitInfo(operands2.get(0))); 
			operandArgs.add(NormaliseUnitInfo(operands2.get(1))); 
			
			operands2 = AdaptNormalisedValuesForAddition
			(
				operandArgs, operation
			);
		}

		return operands2;
	}
	
	static ArrayList<UnitInfo> AdaptNormalisedValuesForAddition(ArrayList<UnitInfo> unitInfos2, Operations operation)
	{
		if (unitInfos2.get(0).BaseTenExponent == unitInfos2.get(1).BaseTenExponent)
		{
			//Having the same BaseTenExponent values means that the given operation can be performed right away.
			return unitInfos2;
		}

		int[] bigSmallI = 
		(
			unitInfos2.get(0).BaseTenExponent > unitInfos2.get(1).BaseTenExponent ?
			new int[] { 0, 1 } : new int[] { 1, 0 }
		);

		//Only the variable with the bigger value is modified. For example: 5*10^5 & 3*10^3 is converted
		//into 500*10^3 & 3*10^3 in order to allow the addition 500 + 3. 
		UnitInfo big2 = AdaptBiggerAdditionOperand(unitInfos2, bigSmallI, operation);
		if (big2.Error.getType() != ErrorTypes.None)
		{
			return TooBigGapAddition(unitInfos2, bigSmallI, operation);
		}

		unitInfos2.set
		(
			bigSmallI[0], ExceptionInstantiation.NewUnitInfo
			(
				unitInfos2.get(bigSmallI[0]), big2.Value, unitInfos2.get(bigSmallI[1]).BaseTenExponent
			)
		);
 
		return unitInfos2;
	}

	//When adding/subtracting two numbers whose gap is bigger than the maximum double range, there
	//is no need to perform any operation (i.e., no change will be observed because of being outside
	//the maximum supported precision). This method takes care of these cases and returns the expected
	//output (i.e., biggest value).
	static ArrayList<UnitInfo> TooBigGapAddition(ArrayList<UnitInfo> unitInfos2, int[] bigSmallI, Operations operation)
	{
		ArrayList<UnitInfo> outInfos = new ArrayList<UnitInfo>() 
		{{
			//First operand (i.e., one whose information defines the operation) together with the
			//numeric information (i.e., just Value and BaseTenExponent because both are normalised)
			//which is associated with the biggest one.
			add
			(
				ExceptionInstantiation.NewUnitInfo
				(
					unitInfos2.get(0), unitInfos2.get(bigSmallI[0]).Value, 
					unitInfos2.get(bigSmallI[0]).BaseTenExponent
				)
			);
		}};

		if (operation == Operations.Subtraction && bigSmallI[0] == 1)
		{
			outInfos.get(0).Value = -1.0 * outInfos.get(0).Value;
		}

		if (outInfos.get(0).Unit == Units.Unitless)
		{
			outInfos.get(0).Unit = unitInfos2.get(bigSmallI[1]).Unit;
		}

		return outInfos;
	}

	static UnitInfo AdaptBiggerAdditionOperand(ArrayList<UnitInfo> unitInfos2, int[] bigSmallI, Operations operation)
	{
		int gapExponent = unitInfos2.get(bigSmallI[0]).BaseTenExponent - unitInfos2.get(bigSmallI[1]).BaseTenExponent;
		if (gapExponent >= 27)
		{
			//The difference between both inputs is bigger than (or, at least, very close to) the maximum double value/precision;
			//what makes this situation calculation unworthy and the first operand to be returned as the result.
			//Note that the error below these lines is just an easy way to tell the calling function about this eventuality.
			return ExceptionInstantiation.NewUnitInfo(unitInfos2.get(0), ErrorTypes.InvalidOperation);
		}

		//PerformManagedOperationValues is used to make sure that the resulting numeric information is stored
		//in Value (if possible).
		UnitInfo big2 = PerformManagedOperationValues
		(
			RaiseToIntegerExponent(10.0, gapExponent), unitInfos2.get(bigSmallI[0]).Value, 
			Operations.Multiplication                
		);

		boolean isWrong = 
		(
			big2.Error.getType() != ErrorTypes.None || big2.BaseTenExponent != 0 ?
			
			//The value of the bigger input times 10^(gap between BaseTenExponent of inputs) is too big. 
			isWrong = true :
			
			//Overflow-check very unlikely to trigger an error. In fact, with properly normalised variables,
			//triggering an error would be plainly impossible.               
			AreAdditionFinalValuesWrong
			(
				unitInfos2.get(0).Value, unitInfos2.get(1).Value, operation
			)
		);

		
		UnitInfo output = null;

		if (isWrong)
		{
			output = ExceptionInstantiation.NewUnitInfo(unitInfos2.get(0), ErrorTypes.InvalidOperation);
		}
		else
		{
			output = ExceptionInstantiation.NewUnitInfo(unitInfos2.get(bigSmallI[0]), big2.Value);
		}

		return output;
	}

	static boolean AreAdditionFinalValuesWrong(double val1, double val2, Operations operation)
	{
		boolean isWrong = false;

		try
		{
			val1 = val1 + val2 *
			(
				operation == Operations.Addition ? 1 : -1
			);
		}
		catch (Exception e) { isWrong = true; }

		return isWrong;
	}

	static UnitInfo PerformManagedOperationMultiplication(UnitInfo firstInfo, UnitInfo secondInfo, Operations operation)
	{
		return PerformManagedOperationNormalisedValues
		(
			firstInfo, new ArrayList<UnitInfo>() 
			{{ 
				add(NormaliseUnitInfo(firstInfo));
				add(NormaliseUnitInfo(secondInfo)); 
			}},
			operation
		);
	}

	public static UnitInfo RaiseToIntegerExponent(double baseValue, int exponent)
	{
		return RaiseToIntegerExponent(ExceptionInstantiation.NewUnitInfo(baseValue), exponent);
	}

	public static UnitInfo RaiseToIntegerExponent(UnitInfo baseInfo, int exponent)
	{
		if (exponent <= 1 && exponent >= 0)
		{
			baseInfo.Value = (exponent == 0 ? 1.0 : baseInfo.Value);
			return baseInfo;
		}

		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo(baseInfo);

		for (int i = 1; i < Math.abs(exponent); i++)
		{
			outInfo = PerformManagedOperationValues
			(
				outInfo, baseInfo, Operations.Multiplication
			);
			if (outInfo.Error.getType() != ErrorTypes.None) return outInfo;
		}

		return
		(
			exponent < 0 ?
			PerformManagedOperationValues(ExceptionInstantiation.NewUnitInfo(1.0), outInfo, Operations.Division) :
			outInfo
		);
	}

	static UnitInfo PerformManagedOperationNormalisedValues(UnitInfo outInfo, ArrayList<UnitInfo> normalisedInfos, Operations operation)
	{
		return
		(
			normalisedInfos.size() == 1 ?
			//There is just one operand when the difference between both of them is too big.
			normalisedInfos.get(0) :
			PerformManagedOperationTwoOperands(outInfo, normalisedInfos, operation)
		);
	}

	static UnitInfo PerformManagedOperationTwoOperands(UnitInfo outInfo, ArrayList<UnitInfo> normalisedInfos, Operations operation)
	{
		UnitInfo outInfoNormalised = PerformManagedOperationValues
		(
			normalisedInfos.get(0), normalisedInfos.get(1), operation
		);

		if (outInfo.Error.getType() != ErrorTypes.None)
		{
			return ExceptionInstantiation.NewUnitInfo(outInfo, ErrorTypes.NumericError);
		}

		outInfo.BaseTenExponent = outInfoNormalised.BaseTenExponent;
		outInfo.Value = outInfoNormalised.Value;
		//Normalised means no prefixes.
		outInfo.Prefix = new Prefix(outInfo.Prefix.getPrefixUsage()); 

		return outInfo;
	}

	public static UnitInfo PerformManagedOperationValues(double firstValue, double secondValue, Operations operation)
	{
		return PerformManagedOperationValues
		(
			ExceptionInstantiation.NewUnitInfo(firstValue), ExceptionInstantiation.NewUnitInfo(secondValue), operation
		);
	}

	static UnitInfo PerformManagedOperationValues(UnitInfo firstInfo, double secondValue, Operations operation)
	{
		return PerformManagedOperationValues
		(
			firstInfo, ExceptionInstantiation.NewUnitInfo(secondValue), operation
		);
	}

	//This method might be used to perform full operations (not just being the last calculation step) instead
	//of the default one (PerformManagedOperationUnits) for simple cases. That is: ones not dealing with the
	//complex numeric reality (Value, Prefix and BaseTenExponent) which makes a pre-analysis required.
	//Note that, unlikely what happens with PerformMangedOperationUnits, the outputs of this method aren't
	//normalised (= primarily stored under Value), what is useful in certain contexts.
	//NOTE: this function assumes that both inputs are normalised, what means that no prefix information is expected.
	//It might also be used with non-normalised inputs, but their prefix information would be plainly ignored.
	public static UnitInfo PerformManagedOperationValues(UnitInfo firstInfo, UnitInfo secondInfo, Operations operation)
	{
		if (firstInfo.Value == 0.0 || secondInfo.Value == 0.0)
		{
			if (operation == Operations.Multiplication || operation == Operations.Division)
			{
				//Dividing by zero scenarios are taken into account somewhere else.
				return ExceptionInstantiation.NewUnitInfo(firstInfo, 0.0);
			}
		}

		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo(firstInfo);
		UnitInfo firstInfo0 = ExceptionInstantiation.NewUnitInfo(firstInfo);
		UnitInfo secondInfo0 = ExceptionInstantiation.NewUnitInfo(secondInfo);

		boolean isWrong = false;
		try
		{
			if (operation == Operations.Addition)
			{
				outInfo.Value += secondInfo0.Value;
			}
			else if (operation == Operations.Subtraction)
			{
				outInfo.Value -= secondInfo.Value;
			}
			else
			{
				//The reason for checking whether BaseTenExponent is inside/outside the int range before performing 
				//the operation (rather than going ahead and eventually catching the resulting exception) isn't just
				//being quicker, but also the only option in many situations. Note that an addition/subtraction between
				//two int variables whose result is outside the int range might not trigger an exception (+ random 
				//negative value as output).
				if 
				(
					VaryBaseTenExponent
					(
						outInfo, secondInfo0.BaseTenExponent, operation == Operations.Division
					)
					.Error.getType() != ErrorTypes.None)
				{
					return new UnitInfo(outInfo, ErrorTypes.InvalidOperation);
				}

				if (operation == Operations.Multiplication)
				{
					outInfo.Value *= secondInfo.Value;
					outInfo.BaseTenExponent += secondInfo.BaseTenExponent;
				}
				else if (operation == Operations.Division)
				{
					if (secondInfo.Value == 0.0)
					{
						UnitInfo output =  new UnitInfo(outInfo);
						output.Error = new ErrorInfo(ErrorTypes.NumericError);
						return output;
					}
					outInfo.Value /= secondInfo.Value;
					outInfo.BaseTenExponent -= secondInfo.BaseTenExponent;
				}
			}
		}
		catch (Exception e) { isWrong = true; }

		return
		(
			//An error might not be triggered despite of dealing with numbers outside double precision.
			//For example: 0.00000000000000000001m * 0.0000000000000000000001m can output 0.0 without triggering an error. 
			isWrong || ((operation == Operations.Multiplication || operation == Operations.Division) && outInfo.Value == 0.0) ?
			OperationValuesManageError(firstInfo0, secondInfo0, operation) : outInfo
		);
	}

	static UnitInfo OperationValuesManageError(UnitInfo outInfo, UnitInfo secondInfo, Operations operation)
	{
		if (operation != Operations.Multiplication && operation != Operations.Division)
		{
			//This condition should never be true on account of the fact that the pre-modifications performed before
			//adding/subtracting should avoid erroneous situations.
			return ExceptionInstantiation.NewUnitInfo(outInfo, ErrorTypes.InvalidOperation);
		}

		UnitInfo secondInfo2 = ConvertValueToBaseTen(secondInfo.Value);
		outInfo = VaryBaseTenExponent(outInfo, secondInfo2.BaseTenExponent, operation == Operations.Division);
		if (Math.abs(secondInfo2.Value) == 1.0 || outInfo.Error.getType() != ErrorTypes.None) return outInfo;
		
		try
		{
			outInfo = PerformManagedOperationUnits
			(
				outInfo, secondInfo2.Value, operation
			);
		}
		catch(Exception e)
		{
			//Very unlikely scenario on account of the fact that Math.abs(secondInfo2.Value)
			//lies within the 0.1-10.0 range.
			UnitInfo arg1 = ExceptionInstantiation.NewUnitInfo(outInfo);
			arg1.Value = secondInfo2.Value;
			arg1.BaseTenExponent = 0;
			
			UnitInfo arg2 = ExceptionInstantiation.NewUnitInfo();
			arg2.Value = outInfo.Value;
			arg2.BaseTenExponent = outInfo.BaseTenExponent;
			
			outInfo = OperationValuesManageError(arg1, arg2, operation);
		}

		return outInfo;
	}

	static UnitInfo ConvertValueToBaseTen(double value)
	{
		value = Math.abs(value);
		return FromValueToBaseTenExponent
		(
			 ExceptionInstantiation.NewUnitInfo(value), Math.abs(value), false
		);
	}

	public static UnitInfo ConvertBaseTenToValue(UnitInfo unitInfo)
	{
		if (unitInfo.BaseTenExponent == 0) return unitInfo;

		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo(unitInfo);
		boolean decrease = unitInfo.BaseTenExponent > 0;
		int sign = (int)Math.signum(outInfo.Value);
		double absValue = Math.abs(outInfo.Value);

		while (outInfo.BaseTenExponent != 0.0)
		{
			if (decrease)
			{
				if (absValue >= OperationsOther.MaxValue / 10.0) break;
				absValue *= 10.0;
				outInfo.BaseTenExponent -= 1;
			}
			else
			{
				if (absValue <= OperationsOther.MinValue * 10.0) break;
				absValue /= 10.0;
				outInfo.BaseTenExponent += 1;
			}
		}

		outInfo.Value = sign * absValue;

		return outInfo;
	}

	public static UnitInfo NormaliseUnitInfo(UnitInfo unitInfo)
	{
		if (unitInfo.Value == 0 && unitInfo.Prefix.getFactor() == 1.0)
		{
			return unitInfo;
		}
		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo(unitInfo);

		if (outInfo.Prefix.getFactor() != 1)
		{
			outInfo = FromValueToBaseTenExponent
			(
				outInfo, outInfo.Prefix.getFactor(), true
			);
			outInfo.Prefix = new Prefix(outInfo.Prefix.getPrefixUsage());
		}
		if (outInfo.Value == 0.0) return outInfo;

		outInfo = FromValueToBaseTenExponent
		(
			outInfo, outInfo.Value, false
		);

		return outInfo;
	}

	static UnitInfo FromValueToBaseTenExponent(UnitInfo outInfo, double value, boolean isPrefix)
	{
		if (value == 0.0) return outInfo;

		double valueAbs = Math.abs(value);
		boolean decrease = (valueAbs > 1.0);
		if (!isPrefix)
		{
			outInfo.Value = outInfo.Value / valueAbs;
		}

		while (valueAbs != 1.0)
		{
			if ((valueAbs < 10.0 && valueAbs > 1.0) || (valueAbs > 0.1 && valueAbs < 1.0))
			{
				if (!isPrefix) outInfo.Value = value;
				else
				{
					outInfo = PerformManagedOperationValues
					(
						outInfo, value, Operations.Multiplication
					);
				}

				return outInfo;
			}

			if (decrease)
			{
				value /= 10.0;
				outInfo.BaseTenExponent += 1;
			}
			else
			{
				value *= 10.0;
				outInfo.BaseTenExponent -= 1;
			}

			valueAbs = Math.abs(value);
		}

		return outInfo;
	}

	
	public static UnitInfo VaryBaseTenExponent(UnitInfo info, int baseTenIncrease)
	{
		return VaryBaseTenExponent(info, baseTenIncrease, false);
	}
	
	//Method used to vary BaseTenExponent without provoking unhandled exceptions (i.e., bigger than Integer.MAX_VALUE).
	static UnitInfo VaryBaseTenExponent(UnitInfo info, int baseTenIncrease, boolean isDivision)
	{
		long val1 = info.BaseTenExponent;
		long val2 = baseTenIncrease;

		if (isDivision)
		{
			//Converting a negative value into positive might provoke an overflow error for the int type
			//(e.g., Math.abs(Integer.MAIN_VALUE)). Converting both variables to long is a quick and effective
			//way to avoid this problem.
			val2 *= -1;
		}
		
		UnitInfo outInfo = null;
		
		if ((val2 > 0 && val1 > Integer.MAX_VALUE - val2) || (val2 < 0 && val1 < Integer.MIN_VALUE - val2))
		{
			outInfo = ExceptionInstantiation.NewUnitInfo(info, ErrorTypes.NumericError);
		}
		else
		{
			outInfo = ExceptionInstantiation.NewUnitInfo(info);
			outInfo.BaseTenExponent = (int)(val1 + val2);
		}
		
		return outInfo;
	}
}