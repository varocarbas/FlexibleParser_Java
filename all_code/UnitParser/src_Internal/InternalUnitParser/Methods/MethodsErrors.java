package InternalUnitParser.Methods;

import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Classes.*;
import InternalUnitParser.Operations.*;
import UnitParser.*;
import UnitParser.UnitP.*;

public class MethodsErrors
{
	//Called before starting unit conversions triggered by public methods.
	static ErrorTypes PrelimaryErrorCheckConversion(UnitP original, UnitInfo targetInfo)
	{
		ErrorTypes outError = ErrorTypes.None;

		if (original == null)
		{
			outError = ErrorTypes.InvalidUnit;
		}
		else if (original.getError().getType() != ErrorTypes.None)
		{
			outError = original.getError().getType();
		}
		else if (targetInfo.Error.getType() != ErrorTypes.None)
		{
			outError = targetInfo.Error.getType();
		}

		return outError;
	}

	//Called before starting any unit conversion.
	static ErrorTypes GetConversionError(UnitInfo originalInfo, UnitInfo targetInfo)
	{
		ErrorTypes outError = ErrorTypes.None;

		if (originalInfo.Unit == Units.None || targetInfo.Unit == Units.None)
		{
			outError = ErrorTypes.InvalidUnit;
		}
		else if (originalInfo.Unit == Units.Unitless || targetInfo.Unit == Units.Unitless)
		{
			outError = ErrorTypes.InvalidUnitConversion;
		}
		else if (originalInfo.Error.getType() != ErrorTypes.None)
		{
			outError = originalInfo.Error.getType();
		}
		else if (targetInfo.Error.getType() != ErrorTypes.None)
		{
			outError = targetInfo.Error.getType();
		}
		else if (originalInfo.Type == UnitTypes.None || originalInfo.Type != targetInfo.Type)
		{
			if (originalInfo.Parts.size() == targetInfo.Parts.size())
			{
				int partMatchCount = Linq.Count
				(
					originalInfo.Parts, x -> Linq.FirstOrDefault
					(
						targetInfo.Parts, y -> y.Exponent.equals(x.Exponent) &&
						y.getUnit().equals(x.getUnit()), null
					)
					!= null
				);

				if (partMatchCount == originalInfo.Parts.size())
				{
					//In some cases, different types might be intrinsically identical (= same unit parts).
					return outError;
				}
			}
			outError = ErrorTypes.InvalidUnitConversion;
		}

		return outError;
	}

	//Called before starting unit-part conversions.
	static ErrorTypes GetUnitPartConversionError(UnitPart originalPart, UnitPart targetPart)
	{
		ErrorTypes outError = ErrorTypes.None;

		if (MethodsCommon.GetTypeFromUnitPart(originalPart) != MethodsCommon.GetTypeFromUnitPart(targetPart))
		{
			outError = ErrorTypes.InvalidUnitConversion;
		}
		else if (MethodsCommon.IsUnnamedUnit(originalPart.getUnit()) || MethodsCommon.IsUnnamedUnit(targetPart.getUnit()))
		{
			//Finding an unnamed compound here would be certainly an error.
			outError = ErrorTypes.InvalidUnitConversion;
		}

		return outError;
	}

	//Called before performing any operation.
	static ErrorTypes GetOperationError(UnitInfo unitInfo1, UnitInfo unitInfo2, Operations operation)
	{
		if (operation == Operations.None) return ErrorTypes.InvalidOperation;
		if (operation == Operations.Division && unitInfo2.Value == 0.0)
		{
			return ErrorTypes.NumericError;
		}

		for (UnitInfo info: new UnitInfo[] { unitInfo1, unitInfo2 })
		{
			if (info.Error.getType() != ErrorTypes.None)
			{
				return info.Error.getType();
			}
		}

		return ErrorTypes.None;
	}

	//Called before performing unit-unit operations.
	public static ErrorTypes GetUnitOperationError(UnitP first, UnitP second, Operations operation)
	{
		return
		(
			first.getUnit() == Units.None || second.getUnit() == Units.None ?
			ErrorTypes.InvalidUnit : 
			GetOperationError
			(
				ExceptionInstantiation.NewUnitInfo(first), 
				ExceptionInstantiation.NewUnitInfo(second), operation
			)
		);
	}

	//Called before performing unit-value/value-unit operations.
	public static ErrorTypes GetUnitValueOperationError(UnitP unitP, UnitInfo firstInfo, UnitInfo secondInfo, Operations operation)
	{
		return
		(
			//unitP always stores the information of the unit operand.
			unitP.getUnit() == Units.None ? ErrorTypes.InvalidUnit :
			GetOperationError
			(
				firstInfo, secondInfo, operation
			)
		);
	}
}
