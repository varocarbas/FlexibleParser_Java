package InternalUnitParser.Methods;

import InternalUnitParser.Classes.*;
import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Hardcoding.*;
import InternalUnitParser.Operations.*;
import InternalUnitParser.Parse.*;
import UnitParser.*;
import UnitParser.UnitP.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MethodsUnitP
{
	public static ParseInfo ParseInputs(ParseInfo parseInfo)
	{
		parseInfo = Parse.StartUnitParse(parseInfo);
		boolean isOK =
		(
			parseInfo.UnitInfo.Error.getType() == ErrorTypes.None &&
			parseInfo.UnitInfo.Unit != Units.None
		);
		
		if (!isOK && parseInfo.InputToParse.contains(" "))
		{
			//No intermediate spaces (within the unit) should be expected,
			//but well...
			ParseInfo parseInfo2 = new ParseInfo
			(
				parseInfo, CSharpOther.StringJoin
				(
					"", Linq.Select
					(
						CSharpOther.ArrayToArrayList
						(
							CSharpOther.SplitTryCatch(parseInfo.InputToParse, " ")
						), 
						x -> x.trim()
					)
				)
			);
			parseInfo2.UnitInfo.Error = new ErrorInfo();
			parseInfo2 = Parse.StartUnitParse(parseInfo2);

			if (parseInfo2.UnitInfo.Unit != Units.None)
			{
				parseInfo = new ParseInfo(parseInfo2);
			}
		}

		return parseInfo;
	}
	
	public static UnitInfo ParseValueAndUnit(String valueAndUnit)
	{
		UnitInfo unitInfo = ExceptionInstantiation.NewUnitInfo();
		String[] parts = CSharpOther.SplitTryCatch
		(
			valueAndUnit.trim(), " "
		);

		//Note that Parse.ParseDecimal can deal with any number (i.e., double, double or beyond double).
		if (parts.length >= 2)
		{
			unitInfo = Parse.ParseDecimal(parts[0]);

			if (unitInfo.Error.getType() == ErrorTypes.None)
			{
				unitInfo.TempString = CSharpOther.StringJoin(" ", parts, 1, parts.length - 1);
			}
		}
		else if (parts.length == 1)
		{
			unitInfo = Parse.ParseDecimal(valueAndUnit);

			if (unitInfo.Error.getType() == ErrorTypes.None)
			{
				unitInfo.TempString = "Unitless";
			}
		}

		return 
		(
			unitInfo.Error.getType() == ErrorTypes.None ? unitInfo :
			ParseValueAndUnitNoBlank(valueAndUnit)
		);
	}

	public static UnitInfo ParseValueAndUnitNoBlank(String valueAndUnit)
	{
		String valueString = CSharpOther.StringJoinChars
		(
			"", Linq.TakeWhile
			(
				CSharpOther.StringToCharacters(valueAndUnit.trim().toLowerCase()), 
				x -> Character.isDigit(x) || x.equals('e') || x.equals('-') || 
				x.equals('+') || x.equals('.') || x.equals(','), '\u0000'
			)
		);
		
		UnitInfo unitInfo = Parse.ParseDecimal(valueString);

		if (unitInfo.Error.getType() == ErrorTypes.None)
		{
			unitInfo.TempString = valueAndUnit.replace(valueString, "");
		}

		return unitInfo;
	}

	public UnitPConstructor GetUnitP2(double value, String unitString)
	{
		return GetUnitP2
		(
			value, unitString, ExceptionHandlingTypes.NeverTriggerException
		);
	}
	
	public UnitPConstructor GetUnitP2
	(
		double value, String unitString, ExceptionHandlingTypes exceptionHandling
	)
	{
		return GetUnitP2
		(
			value, unitString, exceptionHandling, PrefixUsageTypes.DefaultUsage
		);
	}
	
	public static UnitPConstructor GetUnitP2
	(
		double value, String unitString, ExceptionHandlingTypes exceptionHandling, PrefixUsageTypes prefixUsage
	)
	{
		return GetUnitP2
		(
			ExceptionInstantiation.NewUnitInfo(value, exceptionHandling, prefixUsage), unitString
		);
	}

	public static UnitPConstructor GetUnitP2(UnitInfo unitInfo, String unitString)
	{
		ParseInfo parseInfo = 
		(
			unitInfo.Error.getType() != ErrorTypes.None ?
			new ParseInfo(unitInfo) : ParseInputs
			(
				new ParseInfo(unitInfo, unitString)
			)
		);

		if (parseInfo.UnitInfo.Error.getType() == ErrorTypes.None && parseInfo.UnitInfo.Unit == Units.None)
		{
			parseInfo.UnitInfo.Error = ExceptionInstantiation.NewErrorInfo(ErrorTypes.InvalidUnit);
		}

		return new UnitPConstructor
		(
			unitString, parseInfo.UnitInfo, parseInfo.UnitInfo.Type, 
			parseInfo.UnitInfo.System, parseInfo.UnitInfo.Error.getType(), 
			unitInfo.Error.getExceptionHandling(), false, 
			(unitInfo.Value != parseInfo.UnitInfo.Value)
		);
	}
	
	public static UnitInfo ImproveUnitInfo(UnitInfo unitInfo, boolean noPrefixImprovement)
	{
		if (unitInfo.Parts.size() == 0)
		{
			if (unitInfo.Prefix.getFactor() != 1.0)
			{
				unitInfo = Managed.NormaliseUnitInfo(unitInfo);
			}

			unitInfo.Unit = Units.Unitless;
			unitInfo.Prefix = new Prefix(1.0, unitInfo.Prefix.getPrefixUsage());
		}
		else if (Math.abs(unitInfo.Value) < 1 && unitInfo.Prefix.getFactor() > 1)
		{
			unitInfo.Value = unitInfo.Value * unitInfo.Prefix.getFactor();
			unitInfo.Prefix = new Prefix(unitInfo.Prefix.getPrefixUsage());
		}

		unitInfo = RemoveUnitPartPrefixes(unitInfo);

		if (!noPrefixImprovement)
		{
			unitInfo = ImprovePrefixes(unitInfo);
		}

		return ReduceBigValueExp(unitInfo);
	}

	static UnitInfo RemoveUnitPartPrefixes(UnitInfo unitInfo)
	{
		if (unitInfo.Parts.size() == 0 || (unitInfo.Parts.size() < 2 && unitInfo.Parts.get(0).Exponent == 1) || !MethodsCommon.IsUnnamedUnit(unitInfo.Unit))
		{
			//The only cases with (uncompensated) prefixes in some unit parts which
			//might reach this point are multi-part unnamed compounds.
			return unitInfo;
		}

		UnitInfo prefixInfo = ExceptionInstantiation.NewUnitInfo(1.0);

		for (int i = 0; i < unitInfo.Parts.size(); i++)
		{
			if (unitInfo.Parts.get(i).getPrefix().getFactor() == 1.0) continue;

			if (isBasicPrefixUnit(unitInfo.Parts.get(i)))
			{
				//Better keeping the prefixes of the basic units (e.g., kg).
				continue;
			}

			prefixInfo = Managed.PerformManagedOperationUnits
			(
				prefixInfo, Managed.RaiseToIntegerExponent
				(
					unitInfo.Parts.get(i).getPrefix().getFactor(), unitInfo.Parts.get(i).Exponent
				), 
				Operations.Multiplication
			);
			unitInfo.Parts.get(i).setPrefix(new Prefix());
		}

		unitInfo = Managed.PerformManagedOperationUnits
		(
			unitInfo, prefixInfo, Operations.Multiplication
		);

		return unitInfo;
	}

	static boolean isBasicPrefixUnit(UnitPart unitPart)
	{
		for (HashMap<UnitSystems, BasicUnit> item: HCCompounds.AllBasicUnits.values())
		{
			for (BasicUnit item2: item.values())
			{
				if (item2.Unit == unitPart.getUnit() && item2.PrefixFactor == unitPart.getPrefix().getFactor())
				{
					return true;
				}
			}
		}

		return false;
	}

	static UnitInfo ReduceBigValueExp(UnitInfo unitInfo)
	{
		if (unitInfo.BaseTenExponent == 0) return unitInfo;

		double maxVal = 1000000.0;
		double minVal = 0.0001;

		int sign = (int)Math.signum(unitInfo.Value);
		double absValue = Math.abs(unitInfo.Value);

		if (unitInfo.BaseTenExponent > 0)
		{
			while (unitInfo.BaseTenExponent > 0 && absValue <= maxVal / 10)
			{
				unitInfo.BaseTenExponent -= 1;
				absValue *= 10;
			}
		}
		else
		{
			while (unitInfo.BaseTenExponent < 0 && absValue >= minVal * 10)
			{
				unitInfo.BaseTenExponent += 1;
				absValue /= 10;
			}
		}

		unitInfo.Value = sign * absValue;

		return unitInfo;
	}

	static UnitInfo ImprovePrefixes(UnitInfo unitInfo)
	{
		if (unitInfo.Unit == Units.Unitless)
		{
			return Managed.NormaliseUnitInfo(unitInfo);
		}

		double absValue = Math.abs(unitInfo.Value);
		boolean valueIsOK = (absValue >= 0.001 && absValue <= 1000.0);

		if (valueIsOK && unitInfo.BaseTenExponent == 0 && unitInfo.Prefix.getFactor() == 1.0)
		{
			return unitInfo;
		}

		PrefixTypes prefixType =
		(
			unitInfo.Prefix.getType() != PrefixTypes.None ?
			unitInfo.Prefix.getType() : PrefixTypes.SI
		);

		boolean prefixIsOK = PrefixCanBeUsedWithUnit(unitInfo, prefixType);

		if (!prefixIsOK || !valueIsOK || unitInfo.BaseTenExponent != 0)
		{
			unitInfo = Managed.NormaliseUnitInfo(unitInfo);

			if (prefixIsOK)
			{
				unitInfo = MethodsCommon.GetBestPrefixForTarget
				(
					unitInfo, unitInfo.BaseTenExponent,
					prefixType, true
				);
			}
		}

		return CompensateBaseTenExponentWithPrefix(unitInfo);
	}

	static boolean PrefixCanBeUsedWithUnit(UnitInfo unitInfo, PrefixTypes prefixType)
	{
		return 
		(
			!PrefixCanBeUsedWithUnitBasicCheck(unitInfo, prefixType) ? false :
			PrefixCanBeUsedCompound(unitInfo)
		);
	}

	//It is better to not use prefixes with some compounds in order to avoid misinterpretations.
	//For example: 1000 m2 converted into k(m2) is easily misinterpretable as km2 (i.e., (km)^2).
	static boolean PrefixCanBeUsedCompound(UnitInfo unitInfo)
	{
		boolean canBeUsed = true;

		if (MethodsCommon.UnitIsNamedCompound(unitInfo.Unit))
		{
			canBeUsed = HCPrefixes.AllCompoundsUsingPrefixes.contains(unitInfo.Unit);
		}
		else if (unitInfo.Parts.size() > 1) canBeUsed = false;

		return canBeUsed;
	}
	
	static boolean PrefixCanBeUsedWithUnitBasicCheck(UnitInfo unitInfo, PrefixTypes prefixType)
	{
		if (unitInfo.Prefix.getPrefixUsage() == PrefixUsageTypes.AllUnits) return true;

		if (prefixType == PrefixTypes.SI)
		{
			if (HCPrefixes.AllOtherSIPrefixUnits.contains(unitInfo.Unit)) return true;

			UnitSystems system =
			(
				unitInfo.System == UnitSystems.None ?
				MethodsCommon.GetSystemFromUnitInfo(unitInfo) :
				unitInfo.System
			);

			return (system == UnitSystems.SI || system == UnitSystems.CGS);
		}
		else if (prefixType == PrefixTypes.Binary)
		{
			UnitTypes type =
			(
				unitInfo.Type == UnitTypes.None ?
				MethodsCommon.GetTypeFromUnitInfo(unitInfo) :
				unitInfo.Type
			);
			return HCPrefixes.AllBinaryPrefixTypes.contains(type);
		}

		return false;
	}
	
	static UnitInfo CompensateBaseTenExponentWithPrefix(UnitInfo unitInfo)
	{
		if (unitInfo.BaseTenExponent == 0 || unitInfo.Prefix.getFactor() == 1) return unitInfo;

		UnitInfo tempInfo = Managed.NormaliseUnitInfo
		(
			ExceptionInstantiation.NewUnitInfo(unitInfo, 1.0)
		);

		tempInfo = MethodsCommon.GetBestPrefixForTarget
		(
			tempInfo, tempInfo.BaseTenExponent,
			unitInfo.Prefix.getType(), true
		);

		unitInfo = ExceptionInstantiation.NewUnitInfo
		(
			unitInfo, unitInfo.Value, tempInfo.BaseTenExponent, new Prefix(tempInfo.Prefix) 
		);

		return Managed.PerformManagedOperationValues
		(
			unitInfo, tempInfo = ExceptionInstantiation.NewUnitInfo
			(
				tempInfo, 0, new Prefix()
			),
			Operations.Multiplication
		);
	}
	
	enum RoundType { MidpointAwayFromZero, MidpointToZero };

	//The current implementation only needs the double type and that's why all this part
	//of the algorithm is declared as double. 
	//Nevertheless, the original version of this code (i.e., an improved version of the one 
	//I submitted for the CoreFX issue https://github.com/dotnet/corefx/issues/6308) was built
	//on dynamic. Thus, the current structure can be easily adapted to deal with as many types
	//as required.
	static double[] Power10Decimal = PopulateRoundPower10Array();

	static double[] PopulateRoundPower10Array()
	{
		return new double[]
		{ 
			1e0, 1e1, 1e2, 1e3, 1e4, 1e5, 1e6, 1e7, 
			1e8, 1e9, 1e10, 1e11, 1e12, 1e13, 1e14, 
			1e15, 1e16, 1e17, 1e18, 1e19, 1e20, 1e21,
			1e22, 1e23, 1e24, 1e25, 1e26, 1e27, 1e28
		};
	}
	
	//This function (+ all the related code) is a version of NumberParser's Math2.RoundExact
	//(https://github.com/varocarbas/FlexibleParser/blob/master/all_code/NumberParser/Source/Math2/Private/New/Math2_Private_New_RoundTruncate.cs).
	//Note that Math.Round cannot deal with the rounding-down expectations of ImproveFinalValue.
	static double RoundExact(double d, int digits, RoundType type)
	{
		return
		(
			d == 0.0 ? 0.0 : (d > 0.0 ? 1.0 : -1.0) * 
			RoundInternalAfter(Math.abs(d), digits, type)
		);
	}

	static double RoundInternalAfter(double d, int digits, RoundType type)
	{
		double d2 = d - Math.floor(d);
		int zeroCount = GetHeadingDecimalZeroCount(d2);

		return
		(
			zeroCount == 0 ? RoundInternalAfterNoZeroes(d, d2, digits, type) :
			RoundInternalAfterZeroes(d, digits, type, d2, zeroCount)
		);
	}

	static double RoundInternalAfterZeroes(double d, int digits, RoundType type, double d2, int zeroCount)
	{
		if (digits < zeroCount)
		{
			//Cases like 0.001 with 1 digit or 0.0001 with 2 digits can reach this point.
			//On the other hand, something like 0.001 with 2 digits requires further analysis.
			return Math.floor(d);
		}

		//d3 represent the double part after all the heading zeroes.
		double d3 = d2 * Power10Decimal[zeroCount];
		d3 = DecimalPartToInteger(d3 - Math.floor(d3), 0, true);
		int length3 = GetIntegerLength(d3);

		double headingBit = 0.0;
		digits -= zeroCount;
		if (digits == 0)
		{
			//In a situation like 0.005 with 2 digits, the number to be analysed would be
			//05 what cannot be (i.e., treated as 5, something different). That's why, in 
			//these cases, adding a heading number is required.
			headingBit = 2.0; //2 avoids the ...ToEven types to be misinterpreted.
			d3 = headingBit * Power10Decimal[length3] + d3;
			digits = 0;
		}

		double output =
		(
			RoundExactInternal(d3, length3 - digits, type)
			/ Power10Decimal[length3]
		)
		- headingBit;

		return Math.floor(d) +
		(
			output == 0.0 ? 0.0 :
			output / Power10Decimal[zeroCount]
		);
	}

	//This method expects the input value to always be positive.
	static int GetIntegerLength(double d)
	{
		if (d == 0) return 0;

		for (int i = 0; i < Power10Decimal.length - 1; i++)
		{
			if (d < Power10Decimal[i + 1]) return i + 1;
		}

		return Power10Decimal.length;
	}
	
	static double RoundInternalAfterNoZeroes(double d, double d2, int digits, RoundType type)
	{
		d2 = DecimalPartToInteger(d2, digits);
		int length2 = GetIntegerLength(d2);

		return
		(
			digits >= length2 ? d : Math.floor(d) +
			(
				RoundExactInternal(d2, length2 - digits, type)
				/ Power10Decimal[length2]
			)
		);
	}

	static double RoundExactInternal(double d, int remDigits, RoundType type)
	{
		double rounded = PerformRound
		(
			d, remDigits, type,
			Math.floor(d / Power10Decimal[remDigits])
		);

		double rounded2 = rounded * Power10Decimal[remDigits];
		return
		(
			rounded2 > rounded ? rounded2 :
			d //A numeric overflow occurred.
		);
	}

	static double PerformRound(double d, int remDigits, RoundType type, double rounded)
	{
		int greaterEqual = MidPointGreaterEqual(d, remDigits, rounded);

		if (greaterEqual == 1.0) rounded += 1.0;
		else if (greaterEqual == 0.0)
		{
			if (type == RoundType.MidpointAwayFromZero)
			{
				rounded += 1.0;
			}
		}

		return rounded;
	}

	static int MidPointGreaterEqual(double d, int remDigits, double rounded)
	{
		return
		(
			remDigits > 0 ?
			//There are some additional digits after the last rounded one. It can be before or after. 
			//Example: 12345.6789 being rounded to 12000 or to 12345.68.
			MidPointGreaterEqualRem(d, remDigits, rounded) :
			//No additional digits after the last rounded one and the double digits have to be considered.
			//Only before is relevant here. Example: 12345.6789 rounded to 12345 and considering .6789.
			MidPointGreaterEqualNoRem(d, rounded)
		);
	}

	static int MidPointGreaterEqualNoRem(double d, double rounded)
	{
		double d2 = d - rounded;
		d2 = DecimalPartToInteger(d2 - Math.floor(d2));
		int length2 = GetIntegerLength(d2);
		if (length2 < 1) return 0;

		int nextDigit = (int)(d2 / Power10Decimal[length2 - 1] % 10);
		if (nextDigit != 5) return (nextDigit < 5 ? -1 : 1);

		while (Math.floor(d2) != d2 && d2 < Power10Decimal[Power10Decimal.length - 1])
		{
			d2 *= 10;
			length2++;
		}

		int count = length2 - 1;
		while (count > 0)
		{
			count--;
			if ((int)(d2 / Power10Decimal[count] % 10) != 0)
			{
				//Just one digit different than zero is enough to conclude that is greater.
				return 1;
			}
		}

		return 0;
	}

	static int MidPointGreaterEqualRem(double d, int remDigits, double rounded)
	{
		int nextDigit = (int)(d / Power10Decimal[remDigits - 1] % 10);
		if (nextDigit != 5) return (nextDigit < 5 ? -1 : 1);

		double middle = nextDigit * Math.floor(Power10Decimal[remDigits - 1]);
		return
		(
			d - rounded * Math.floor(Power10Decimal[remDigits]) == middle ? 0 : 1
		);
	}

	static double DecimalPartToInteger(double d2)
	{
		return DecimalPartToInteger(d2, 0);
	}
		
	static double DecimalPartToInteger(double d2, int digits)
	{
		return DecimalPartToInteger(d2, digits, false);
	}
	
	//This method expects the input value to always be positive.
	static double DecimalPartToInteger(double d2, int digits, Boolean untilEnd)
	{
		if (digits + 1 >= Power10Decimal.length - 1)
		{
			d2 *= Power10Decimal[Power10Decimal.length - 1];
		}
		else
		{
			d2 *= Power10Decimal[digits + 1];
			double lastDigit = Math.floor(d2 / Power10Decimal[0] % 10);
			while (d2 < Power10Decimal[Power10Decimal.length - 3] && (untilEnd || (lastDigit > 0 && lastDigit <= 5.0)))
			{
				d2 *= 10;
				lastDigit = Math.floor(d2 / Power10Decimal[0] % 10);
			}
		}

		return d2;
	}

	//This method depends upon the double-type native precision/Math.floor and, consequently,
	//some extreme cases might be misunderstood. Example: 100000000000000000.00000000000001m
	//outputting zero because of being automatically converted into 100000000000000000m.
	//This method expects the input value to always be positive.
	static int GetHeadingDecimalZeroCount(double d)
	{
		double d2 = (d > 1.0 ? d - Math.floor(d) : d);
		if (d2 == 0) return 0;

		int zeroCount = 0;
		while (d2 <= OperationsOther.MaxValue / 10.0)
		{
			d2 *= 10.0;
			if (Math.floor(d2 / Power10Decimal[0] % 10.0) != 0.0)
			{
				return zeroCount;
			}
			zeroCount++;
		}

		return zeroCount;
	}
   

	//This method improves values which have likely been affected by the precision of the calculations.
	//For example: 1.2999999999999 actually being 1.3.
	public static double ImproveFinalValue(double value)
	{
		if (value == 0.0) return value;
		double sign = value / Math.abs(value);
		double absValue = Math.abs(value);
		int minGapDigits = 6;
		
		UnitInfo infoUp = GetTargetRoundedValue(absValue, RoundType.MidpointAwayFromZero);
		if (infoUp.Value > absValue && infoUp.BaseTenExponent >= minGapDigits)
		{
			//Performs improvements like converting 0.23459999999999999999 into 0.2346.
			value = sign * infoUp.Value;
		}
		else
		{
			UnitInfo infoDown = GetTargetRoundedValue(absValue, RoundType.MidpointToZero);
			if (infoDown.Value < absValue && infoDown.BaseTenExponent >= minGapDigits)
			{
				//Performs improvements like converting 0.23450000000000004 into 0.2345.
				value = sign * infoDown.Value;
			}
		}

		return value;
	}

	static UnitInfo GetTargetRoundedValue(double value, RoundType roundType)
	{
		//UnitInfo has a perfect format to store the returned two values (double & integer).
		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo
		(
			RoundExact(value, 1, roundType)
		);

		//Loop iterating through all the digits (up to the maximum double precision) and looking
		//for situations with many consecutive irrelevant (i.e., no effect on rounding) digits.
		Boolean started = false;
		int startCount = 0;
		int startTarget = 4;
		for (int i = 2; i < 27; i++)
		{
			double tempVal = RoundExact(value, i, roundType);

			if (!started)
			{
				if (tempVal == outInfo.Value)
				{
					startCount += 1;
					if (startCount == startTarget) started = true;
				}
				else
				{
					//Starting the analysis of consecutive irrelevant digits right away might be counter-producing.
					//Once the process is started, any exception (i.e., a non-irrelevant digit) would provoke the
					//analysis to immediately fail. That's why better delaying the analysis start until seeing some
					//consecutive digits (i.e., higher chances of finding what is expected).
					startCount = 0;
				}
			}
			else if (started)
			{
				if (tempVal != outInfo.Value) return outInfo;
				outInfo.BaseTenExponent += 1;
			}

			outInfo.Value = tempVal;
		}

		return outInfo;
	}
	
	public static ArrayList<Units> GetUnitsTypeCommon(UnitTypes type)
	{
		return
		(
			type == UnitTypes.None ? new ArrayList<Units>() :
			Linq.SelectDict
			(
				Linq.WhereDict
				(
					HCMain.AllUnitConversionFactors, 
					(
						x -> MethodsCommon.GetTypeFromUnit(x.getKey()).equals(type)
					)
				),
				x -> x.getKey()
			)
		);
	}

	public static ArrayList<Units> GetUnitsTypeAndSystemCommon(UnitTypes type, UnitSystems system)
	{
		return 
		(
			type == UnitTypes.None ? new ArrayList<Units>() :
			Linq.SelectDict
			(
				Linq.WhereDict
				(
					HCMain.AllUnitConversionFactors, 
					(
						x -> MethodsCommon.GetTypeFromUnit(x.getKey()).equals(type) && 
						UnitBelongsToSystem(x.getKey(), system)
					)
				),
				x -> x.getKey()
			)
		);
	}

	static boolean UnitBelongsToSystem(Units unit, UnitSystems targetSystem)
	{
		UnitSystems system = MethodsCommon.GetSystemFromUnit(unit);

		return
		(
			system == targetSystem ? true :
			(
				HCMain.AllMetricEnglish.get(targetSystem) == UnitSystems.Imperial &&
				HCMain.AllMetricEnglish.get(system) == UnitSystems.Imperial &&
				HCUnits.AllImperialAndUSCSUnits.contains(unit)
			)
		);
	}

	public static ArrayList<String> GetStringsUnitCommon(Units unit, boolean otherStringsToo)
	{
		return
		(
			unit == Units.None || unit == Units.Unitless || MethodsCommon.IsUnnamedUnit(unit) ?
			new ArrayList<String>() : Linq.Distinct
			(
				GetUnitStringsCommon
				(
					GetAllStringsSpecific
					(
						GetAllStrings(otherStringsToo), InputTypes.Unit, unit
					)
				)
			)	
		);
	}

	public static ArrayList<String> GetStringsTypeCommon(UnitTypes type, boolean otherStringsToo)
	{
		return
		(
			type == UnitTypes.None ? new ArrayList<String>() :
			GetUnitStringsCommon
			(
				GetAllStringsSpecific
				(
					GetAllStrings(otherStringsToo), InputTypes.Type, Units.None, type
				)
			)
		);
	}

	public static ArrayList<String> GetStringsTypeAndSystemCommon(UnitTypes type, UnitSystems system, boolean otherStringsToo)
	{
		return
		(
			type == UnitTypes.None || system == UnitSystems.None ? 
			new ArrayList<String>() :
			GetUnitStringsCommon
			(
				GetAllStringsSpecific
				(
					GetAllStrings(otherStringsToo),
					InputTypes.TypeAndSystem, 
					Units.None, type, system
				)
			)
		);
	}

	static ArrayList<Map.Entry<String, Units>> GetAllStrings(boolean otherStringsToo)
	{
		//Symbols (case matters).
		ArrayList<Map.Entry<String, Units>> allStrings = Linq.SelectDict
		(
			HCUnits.AllUnitSymbols, x -> x
		);

		allStrings.addAll
		(
			Linq.SelectDict(HCMain.AllUnitSymbols2, x -> x) 
		);

		if (otherStringsToo)
		{
			//Further Strings (case doesn't matter).
			allStrings.addAll
			(
				Linq.SelectDict(HCMain.AllUnitStrings, x -> x)
			);
		}

		return Linq.OrderBy
		(
			allStrings,	new Comparator<Map.Entry<String, Units>>()
			{
				public int compare(Map.Entry<String, Units> first, Map.Entry<String, Units> second)
				{
					return first.getValue().compareTo(second.getValue());
				}        				
			}
		); 
	}
	
	static ArrayList<Map.Entry<String, Units>> GetAllStringsSpecific
	(
		ArrayList<Map.Entry<String, Units>> allSymbols, InputTypes inputType
	)
	{
		return GetAllStringsSpecific
		(
			allSymbols, inputType, Units.None
		);
	}
	
	static ArrayList<Map.Entry<String, Units>> GetAllStringsSpecific
	(
		ArrayList<Map.Entry<String, Units>> allSymbols, InputTypes inputType, Units unit
	)
	{
		return GetAllStringsSpecific
		(
			allSymbols, inputType, unit, UnitTypes.None
		);
	}
	
	static ArrayList<Map.Entry<String, Units>> GetAllStringsSpecific
	(
		ArrayList<Map.Entry<String, Units>> allSymbols, InputTypes inputType, Units unit, UnitTypes type
	)
	{
		return GetAllStringsSpecific
		(
			allSymbols, inputType, unit, type, UnitSystems.None
		);
	}
	
	static ArrayList<Map.Entry<String, Units>> GetAllStringsSpecific
	(
		ArrayList<Map.Entry<String, Units>> allSymbols, InputTypes inputType, 
		Units unit, UnitTypes type, UnitSystems system
	)
	{
		if (inputType == InputTypes.Unit)
		{
			return Linq.Where
			(
				allSymbols, x -> x.getValue().equals(unit)
			);
		}
		else if (inputType == InputTypes.Type)
		{
			return Linq.Where
			(
				allSymbols, x -> MethodsCommon.GetTypeFromUnit(x.getValue()).equals(type)
			);        	
		}
		else if (inputType == InputTypes.TypeAndSystem)
		{
			return Linq.Where
			(
				allSymbols, x -> MethodsCommon.GetTypeFromUnit(x.getValue()).equals(type) &&
				UnitBelongsToSystem(x.getValue(), system)
			); 
		}

		return allSymbols;
	}
	
	static ArrayList<String> GetUnitStringsCommon(ArrayList<Map.Entry<String, Units>> allStrings)
	{
		return GetUnitStringsCommon(allStrings, "");
	}
	
	static ArrayList<String> GetUnitStringsCommon(ArrayList<Map.Entry<String, Units>> allStrings, String prefixAbbrev)
	{
		ArrayList<String> outList = new ArrayList<String>();

		for (Entry<String, Units> item: allStrings)
		{
			String item2 = item.getKey();

			if (prefixAbbrev != "" && !HCMain.AllUnitStrings.containsKey(item.getKey()))
			{
				item2 = prefixAbbrev + item2;
			}

			outList.add(item2);
		}

		return outList;
	}

	public static UnitP ConvertToCommon(UnitP unitP, Units targetUnit, Prefix targetPrefix)
	{
		Prefix prefix =
		(
			targetPrefix != null ? new Prefix(targetPrefix) :
			new Prefix(1.0, unitP.getUnitPrefix().getPrefixUsage())
		);

		return ConvertToCommon
		(
			//Calling UpdateMainUnitVariables is required to populate the type/system variables.
			unitP, Parse.UpdateMainUnitVariables
			(
				ExceptionInstantiation.NewUnitInfo
				(
					1.0, targetUnit, prefix, true, unitP.getError().getExceptionHandling()
				)
			)
		);
	}

	public static UnitP ConvertToCommon(UnitP original, String unitString)
	{
		ParseInfo parseInfo = Parse.StartUnitParse
		(
			new ParseInfo
			(
				ExceptionInstantiation.NewUnitInfo
				(
					original, 0.0, 0, new Prefix(original.getUnitPrefix().getPrefixUsage())
				),
				unitString
			)
		);
		
		return ConvertToCommon(original, parseInfo.UnitInfo);
	}

	public static UnitP ConvertToCommon(UnitP original, UnitInfo targetInfo)
	{
		ErrorTypes error = MethodsCommon.PrelimaryErrorCheckConversion(original, targetInfo);
		if (error != ErrorTypes.None) 
		{
			return new UnitP(original, error);
		}

		UnitInfo originalInfo = ExceptionInstantiation.NewUnitInfo(original);
		UnitInfo infoResult = Conversions.ConvertUnit(originalInfo, targetInfo, false);

		return
		(
			infoResult.Error.getType() != ErrorTypes.None ?
			new UnitP(original, infoResult.Error.getType()) :
			new UnitP
			(
				infoResult, original,
				original.getOriginalUnitString() + " => " + 
				MethodsCommon.GetUnitString(infoResult)
			)
		);
	}
	

	enum InputTypes { Unit, Type, TypeAndSystem, System }
}
