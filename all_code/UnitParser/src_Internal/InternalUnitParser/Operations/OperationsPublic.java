package InternalUnitParser.Operations;

import java.util.ArrayList;

import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Classes.*;
import InternalUnitParser.Methods.*;
import InternalUnitParser.Parse.*;
import UnitParser.*;
import UnitParser.UnitP.*;

public class OperationsPublic
{
	public static UnitP PerformUnitOperation(UnitP first, UnitP second, Operations operation, String operationString)
	{
		ErrorTypes errorType = MethodsErrors.GetUnitOperationError(first, second, operation);
		if (errorType != ErrorTypes.None)
		{
			return new UnitP(first, errorType);
		}
		
		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo(first);
		UnitInfo secondInfo = ExceptionInstantiation.NewUnitInfo(second);
		
		if (outInfo.Unit != Units.Unitless && secondInfo.Unit != Units.Unitless)
		{
			if (operation == Operations.Addition || operation == Operations.Subtraction)
			{
				UnitInfo[] tempInfos = PerformChecksBeforeAddition(outInfo, secondInfo);

				for (UnitInfo tempInfo: tempInfos)
				{
					if (tempInfo.Error.getType() != ErrorTypes.None)
					{
						return new UnitP(first, tempInfo.Error.getType());
					}
				}
				//Only the second operator might have been modified.
				secondInfo = tempInfos[1];
			}
			else outInfo = ModifyUnitPartsBeforeMultiplication(first, secondInfo, operation);
		}
		else if (outInfo.Unit == Units.Unitless && secondInfo.Unit != Units.Unitless)
		{
			outInfo = UnitlessAndUnitBeforeOperation
			(
				outInfo, secondInfo, operation
			);
		}
		
		if (outInfo.Error.getType() != ErrorTypes.None || outInfo.Unit == Units.None)
		{
			return new UnitP
			(
				first,
				(
					outInfo.Error.getType() != ErrorTypes.None ?
					outInfo.Error.getType() : ErrorTypes.InvalidUnit
				)
			);
		}
		outInfo = Managed.PerformManagedOperationUnits(outInfo, secondInfo, operation);

		return 
		(
			outInfo.Error.getType() != ErrorTypes.None ?
			new UnitP(first, outInfo.Error.getType()) :
			new UnitP
			(
				outInfo, first, operationString, 
				(
					//Multiplication/division are likely to provoke situations requiring a correction;
					//for example, 1/(1/60) being converted into 60. On the other hand, cases like 
					//1.0 - 0.000001 shouldn't be changed (e.g., converting 0.999999 to 1.0 is wrong).
					operation == Operations.Multiplication || 
					operation == Operations.Division
				)
			)
		);
	}

	static UnitInfo UnitlessAndUnitBeforeOperation(UnitInfo outInfo, UnitInfo secondInfo, Operations operation)
	{
		outInfo = ExceptionInstantiation.NewUnitInfo
		(
			secondInfo, outInfo.Value, outInfo.BaseTenExponent, new Prefix(outInfo.Prefix)
		);

		if (operation == Operations.Division)
		{
			outInfo = MethodsCommon.GetUnitFromParts
			(
				MethodsCommon.RemoveAllUnitInformation(MethodsCommon.InverseUnit(outInfo))
			);
		}

		return outInfo;
	}

	static UnitInfo[] PerformChecksBeforeAddition(UnitInfo outInfo, UnitInfo secondInfo)
	{
		UnitInfo[] outInfos = new UnitInfo[] { outInfo, secondInfo };

		if (outInfo.Type != secondInfo.Type)
		{
			outInfos[0].Error = ExceptionInstantiation.NewErrorInfo(ErrorTypes.InvalidUnit);
		}
		else if (outInfo.Unit != secondInfo.Unit || MethodsCommon.IsUnnamedUnit(outInfo.Unit))
		{
			outInfos[1] = Conversions.ConvertUnit(secondInfo, outInfo, false);
		}

		return outInfos;
	}

	static UnitInfo ModifyUnitPartsBeforeMultiplication(UnitP first, UnitInfo secondInfo, Operations operation)
	{
		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo(first);
		ArrayList<UnitPart> parts2 = new ArrayList<UnitPart>();
		int sign = (operation == Operations.Multiplication ? 1 : -1);

		for (UnitPart part: secondInfo.Parts)
		{
			parts2.add
			(
				new UnitPart(part) 
				{{ 
					Exponent = part.Exponent * sign; 
				}}
			);
		}

		outInfo = MethodsCommon.AddNewUnitParts(outInfo, parts2);
		
		return Parse.StartCompoundAnalysis
		(
			new ParseInfo(outInfo)
		)
		.UnitInfo;
	}
	public static UnitP PerformUnitOperation(UnitP first, double secondValue, Operations operation, String operationString)
	{
		return PerformUnitValueOperation
		(
			first, ExceptionInstantiation.NewUnitInfo(first),  
			ExceptionInstantiation.NewUnitInfo(secondValue), 
			operation, operationString
		);
	}

	public static UnitP PerformUnitOperation(double firstValue, UnitP second, Operations operation, String operationString)
	{
		//The first operand (the one defining exception handling) is a number and that's why exceptions have to be left unhandled.
		UnitP second2 = new UnitP(second, ErrorTypes.None, ExceptionHandlingTypes.AlwaysTriggerException);
		
		return PerformUnitValueOperation
		(
			second2, ExceptionInstantiation.NewUnitInfo(firstValue), 
			ExceptionInstantiation.NewUnitInfo(second), operation, operationString
		);
	}

	static UnitP PerformUnitValueOperation(UnitP unitP, UnitInfo firstInfo, UnitInfo secondInfo, Operations operation, String operationString)
	{
		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo
		(
			firstInfo, unitP.getUnit(), new ArrayList<UnitPart>(unitP.getUnitParts())
		);
		
		outInfo.Error = ExceptionInstantiation.NewErrorInfo
		(
			MethodsErrors.GetUnitValueOperationError
			(
				unitP, firstInfo, secondInfo, operation
			)
		);
		
		if (outInfo.Error.getType() == ErrorTypes.None)
		{
			outInfo = Managed.PerformManagedOperationUnits
			(
				outInfo, secondInfo, operation
			);

			if (operation == Operations.Division && secondInfo.Unit != Units.None && secondInfo.Unit != Units.Unitless)
			{
				if (outInfo.Parts.size() > 0)
				{
					//value/unit is the only scenario value-unit operation where the unit
					//information needs further analysis.

					outInfo = Parse.StartCompoundAnalysis
					(
						new ParseInfo
						(
							InversePrefix(MethodsCommon.InverseUnit(outInfo))
						)
					)
					.UnitInfo;
				}
			}
		}

		return
		(
			outInfo.Error.getType() != ErrorTypes.None ? new UnitP(unitP, outInfo.Error.getType()) :
			new UnitP
			(
				outInfo, unitP, operationString,
				(
					//Multiplication/division are likely to provoke situations requiring a correction;
					//for example, 1/(1/60) being converted into 60. On the other hand, cases like 
					//1.0 - 0.000001 shouldn't be changed (e.g., converting 0.999999 to 1.0 is wrong).
					operation == Operations.Multiplication ||
					operation == Operations.Division
				)
			)
		);
	}

	static UnitInfo InversePrefix(UnitInfo outInfo)
	{
		if (outInfo.Prefix.getFactor() == 1.0) return outInfo;

		outInfo = ExceptionInstantiation.NewUnitInfo
		(
			outInfo, new Prefix(outInfo.Prefix.getPrefixUsage())
		);

		//No need to find a new prefix.
		return Managed.PerformManagedOperationUnits
		(
			outInfo, Managed.PerformManagedOperationValues
			(
				1.0, outInfo.Prefix.getFactor(), Operations.Division
			), 
			Operations.Multiplication
		);
	}
}
