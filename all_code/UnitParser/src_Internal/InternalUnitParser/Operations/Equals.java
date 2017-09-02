package InternalUnitParser.Operations;

import InternalUnitParser.Classes.*;
import InternalUnitParser.CSharpAdaptation.*;
import UnitParser.*;
import UnitParser.UnitP.*;

import java.util.ArrayList;

public class Equals
{
	public static boolean UnitPVarsAreEqual(UnitP first, UnitP second)
	{
		return
		(
			UnitPNonUnitInfoAreEqual(first, second) &&
			UnitPUnitInfoAreEqual(first, second)
		);
	}

	static boolean UnitPNonUnitInfoAreEqual(UnitP first, UnitP second)
	{
		return 
		(
			first.getError().equals(second.getError())
		);
	}

	static boolean UnitPUnitInfoAreEqual(UnitP first, UnitP second)
	{
		return UnitInfosAreEqual
		(
			ExceptionInstantiation.NewUnitInfo
			(
				first.getValue(), first.getUnit(), first.getUnitPrefix()
			),
			ExceptionInstantiation.NewUnitInfo
			(
				second.getValue(), second.getUnit(), second.getUnitPrefix()
			)
		);
	}
	
	public static boolean UnitInfosAreEqual(UnitInfo firstInfo, UnitInfo secondInfo)
	{
		return UnitInfosAreEqual(firstInfo, secondInfo, false);
	}
	
	public static boolean UnitInfosAreEqual(UnitInfo firstInfo, UnitInfo secondInfo, boolean ignoreValues)
	{
		if (firstInfo.Error.Type == ErrorTypes.None || secondInfo.Error.Type == ErrorTypes.None)
		{
			return firstInfo.Error.Type == secondInfo.Error.Type;
		}

		UnitInfo firstInfo2 = Managed.NormaliseUnitInfo(firstInfo);
		UnitInfo secondInfo2 = Managed.NormaliseUnitInfo(secondInfo);
		
		return
		(
			(ignoreValues || UnitInfoValuesAreEqual(firstInfo2, secondInfo2)) &&
			UnitInfoUnitsAreEqual(firstInfo2, secondInfo2)
		);
	}

	//This method assumes that both inputs are normalised.
	static boolean UnitInfoValuesAreEqual(UnitInfo firstInfo, UnitInfo secondInfo)
	{
		return 
		(
			firstInfo.BaseTenExponent == secondInfo.BaseTenExponent &&
			firstInfo.Value == secondInfo.Value
		);
	}

	public static boolean UnitInfoUnitsAreEqual(UnitInfo firstUnit, UnitInfo secondUnit)
	{
		return UnitPartListsAreEqual(firstUnit.Parts, secondUnit.Parts);
	}

	//This method expects fully expanded/simplified unit parts.
	static boolean UnitPartListsAreEqual(ArrayList<UnitPart> firstParts, ArrayList<UnitPart> secondParts)
	{
		if (firstParts.size() != secondParts.size()) return false;

		for (UnitPart firstPart: firstParts)
		{
			if (Linq.FirstOrDefault(secondParts, x -> x.equals(firstPart), null) == null)
			{
				return false;
			}
		}

		return true;
	}

	public static boolean PrefixesAreEqual(Prefix first, Prefix second)
	{
		return 
		(
			first.getType() == second.getType() && 
			first.getFactor() == second.getFactor()
		);
	}

	public static boolean UnitPartsAreEqual(UnitPart first, UnitPart second)
	{
		return
		(
			first.Exponent == second.Exponent &&
			first.getUnit() == second.getUnit() && 
			first.getPrefix().getFactor() == second.getPrefix().getFactor()
		);
	}

	public static boolean ErrorsAreEqual(ErrorInfo first, ErrorInfo second)
	{
		return
		(
			first.ExceptionHandling == second.ExceptionHandling &&
			first.Type == second.Type
		);
	}

	public static boolean CompoundPartsAreEqual(CompoundPart first, CompoundPart second)
	{
		return
		(
			first.Exponent == second.Exponent && 
			first.Type == second.Type
		);
	}
}