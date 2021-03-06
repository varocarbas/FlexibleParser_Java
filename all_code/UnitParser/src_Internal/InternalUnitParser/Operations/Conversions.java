package InternalUnitParser.Operations;

import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Classes.*;
import InternalUnitParser.Hardcoding.*;
import InternalUnitParser.Methods.*;
import UnitParser.*;
import UnitParser.UnitP.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Conversions
{
	//List of types whose conversion requires more than just applying a conversion factor.
	static ArrayList<UnitTypes> SpecialConversionTypes = new ArrayList<UnitTypes>()
	{{
		add(UnitTypes.Temperature);
	}};
	
	public static UnitInfo AdaptUnitParts(UnitInfo unitInfo, int i, int i2)
	{
		ArrayList<UnitPart> parts2 = GetUnitPartsConversion
		(
			new UnitPart(unitInfo.Parts.get(i)), new UnitPart(unitInfo.Parts.get(i2))
		);
		if (parts2 == null) return null;

		UnitInfo tempInfo = ConvertUnitPartToTarget
		(
			ExceptionInstantiation.NewUnitInfo(1.0), new UnitPart(parts2.get(0)),
			new UnitPart(parts2.get(1))
		);

		//Firstly, note that GetUnitPartsConversion might have affected the exponent. For example: in m4/L2,
		//both exponents have to be modified to reach the convertible m3/L. Secondly, bear in mind that
		//parts2 exponents are always positive.
		
		int sign = (int)Math.signum(unitInfo.Parts.get(i).Exponent);
		int exponent = sign * unitInfo.Parts.get(i).Exponent / parts2.get(0).Exponent;
		int outExponent = exponent;
		if (exponent != 1.0)
		{
			tempInfo = Managed.RaiseToIntegerExponent(tempInfo, exponent);
		}
		
		if (Math.abs(unitInfo.Parts.get(i).Exponent) > Math.abs(parts2.get(0).Exponent * exponent))
		{
			exponent = sign * unitInfo.Parts.get(i).Exponent - parts2.get(0).Exponent * exponent;
			if (exponent > 0)
			{
				//Accounting for cases like m4 converted to litre where 1 metre is left uncompensated.
				UnitPart newPart = UnitPartInternal.NewUnitPart
				(
					parts2.get(0).getUnit(), parts2.get(0).getPrefix().getFactor(), sign * exponent
				);
				unitInfo.Parts.add(newPart);

				if (!unitInfo.InitialPositions.containsKey(newPart))
				{
					unitInfo.InitialPositions.put
					(
						newPart, Linq.Max
						(
								unitInfo.InitialPositions, new Comparator<Map.Entry<UnitPart, Integer>>()
								{
									public int compare(Map.Entry<UnitPart, Integer> first, Map.Entry<UnitPart, Integer> second)
									{
										return first.getValue().compareTo(second.getValue());
									}        				
								}
						)
						.getValue() + 1
					);
				}
			}
		}

		if (sign == -1) 
		{
			tempInfo = Managed.PerformManagedOperationUnits(1.0, tempInfo, Operations.Division);
		}
		outExponent = sign * outExponent;

		unitInfo = Managed.PerformManagedOperationUnits(unitInfo, tempInfo, Operations.Multiplication);
		unitInfo.Parts.get(i).setUnit(parts2.get(1).getUnit());
		//outExponent indicates the number of times which the target exponent is
		//repeated to match the original unit. For example: in L to m3, the final
		//unit is m and outExponent is 1 (= the original unit contains 1 target 
		//unit/exponent), but the output exponent should be 3 (1 * target exponent).
		unitInfo.Parts.get(i).Exponent = outExponent * parts2.get(1).Exponent;

		return unitInfo;
	}
	
	static UnitInfo ConvertUnitPartToTarget(UnitInfo outInfo, UnitPart originalPart, UnitPart targetPart)
	{
		return ConvertUnitPartToTarget(outInfo, originalPart, targetPart, true);
	}
	
	static UnitInfo ConvertUnitPartToTarget(UnitInfo outInfo, UnitPart originalPart, UnitPart targetPart, boolean isInternal)
	{
		ErrorTypes errorType = GetUnitPartConversionError(originalPart, targetPart);
		if (errorType != ErrorTypes.None)
		{
			outInfo.Error = ExceptionInstantiation.NewErrorInfo(errorType);
			return outInfo;
		}
		UnitPart originalPart2 = new UnitPart(originalPart);
		UnitPart targetPart2 = new UnitPart(targetPart);

		int exponent2 = 1;
		if (originalPart2.Exponent == targetPart2.Exponent)
		{
			if (!isInternal)
			{
				//In the internal calculations, exponents might be relevant or not when performing
				//a unit part conversion; this issue is being managed by the code calling this function.
				//With conversions delivered to the user, exponents have to be brought into account.
				//NOT: isInternal isn't passed to PerformConversion because the associated modifications
				//only make sense for the main prefix (not what this is about).
				exponent2 = Math.abs(targetPart2.Exponent);
			}
			//In this situation, exponents don't need to be considered and it is better ignoring them during
			//the conversion to avoid problems.
			//For example: the part m2 has associated a specific unit (SquareMetre), but it might be converted
			//into units which don't have one, like ft2.
			originalPart2.Exponent = 1;
			targetPart2.Exponent = 1;
		}
		//Different exponents cannot be removed. For example: conversion between litre and m3, where the exponent
		//does define the unit. 

		UnitInfo info2 = PerformConversion
		(
			AdaptPartToConversion(originalPart2, originalPart.Exponent),
			AdaptPartToConversion(targetPart2, targetPart.Exponent), true,
			//The original part being in the denominator means that the output value has to be inverted.
			//Note that this value is always expected to modify the main value (= in the numerator).
			//This is the only conversion where such a scenario is being considered; but the information
			//is passed to PerformConversion anyway to ensure the highest accuracy. Even the decimal type
			//can output noticeable differences in cases like 1/(val1/val2) vs. val2/val1.
			originalPart.Exponent / Math.abs(originalPart.Exponent) == -1
		);
		
		return
		(
			info2.Error.getType() != ErrorTypes.None ? info2 :
			Managed.PerformManagedOperationUnits
			(
				outInfo, 
				(
					exponent2 == 1 ? info2 :
					Managed.RaiseToIntegerExponent(info2, exponent2)
				),
				Operations.Multiplication
			)
		);
	}

	static UnitInfo PerformConversion(UnitInfo originalInfo, UnitInfo targetInfo)
	{
		return PerformConversion(originalInfo, targetInfo, true, false);
	}
	
	static UnitInfo PerformConversion(UnitInfo originalInfo, UnitInfo targetInfo, boolean isInternal)
	{
		return PerformConversion(originalInfo, targetInfo, isInternal, false);
	}
	
	static UnitInfo PerformConversion(UnitInfo originalInfo, UnitInfo targetInfo, boolean isInternal, boolean inverseOutputs)
	{
		ErrorTypes errorType = GetConversionError(originalInfo, targetInfo);
		if (errorType != ErrorTypes.None)
		{
			return ExceptionInstantiation.NewUnitInfo(originalInfo, errorType);
		}

		UnitInfo targetInfo2 = NormaliseTargetUnit(targetInfo, isInternal);
		UnitInfo outInfo = AccountForTargetUnitPrefixes(originalInfo, targetInfo2);

		boolean convertIt =
		(
			outInfo.Unit != targetInfo2.Unit ||
			(
				MethodsCommon.IsUnnamedUnit(originalInfo.Unit) &&
				MethodsCommon.IsUnnamedUnit(targetInfo.Unit)
			)
		);

		if (convertIt)
		{
			UnitInfo tempInfo =
			(
				UnitsCanBeConvertedDirectly(outInfo, targetInfo2) ?
				ConvertUnitValue(outInfo, targetInfo2, inverseOutputs) :
				PerformUnitPartConversion(outInfo, targetInfo2, isInternal)
			);
			if (tempInfo.Error.getType() != ErrorTypes.None)
			{
				return tempInfo;
			}

			outInfo = ExceptionInstantiation.NewUnitInfo(targetInfo);
			outInfo.Prefix = new Prefix(outInfo.Prefix.getPrefixUsage());
			outInfo.Value = tempInfo.Value;
			outInfo.BaseTenExponent = tempInfo.BaseTenExponent;

			if (!isInternal)
			{
				//When the target unit has a prefix, the conversion is adapted to it (e.g., lb to kg is 0.453).
				//Such an output isn't always desirable (kg isn't a valid unit, but g + kilo); that's why the output
				//value has to be multiplied by the prefix when reaching this point (i.e., result delivered to the user).
				outInfo = Managed.PerformManagedOperationUnits
				(
					outInfo, targetInfo.Prefix.getFactor(), Operations.Multiplication
				);
			}
		}

		return outInfo;
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
	
	static UnitInfo AdaptPartToConversion(UnitPart unitPart, int exponent)
	{
		UnitInfo outInfo = MethodsCommon.UnitPartToUnitInfo(unitPart);
		if (outInfo.Unit == Units.Centimetre)
		{
			//To avoid inconsistencies with individual unit conversions.
			outInfo.Unit = Units.Metre;
			outInfo.BaseTenExponent -= 2; 
		}

		if (unitPart.getPrefix().getFactor() != 1 && exponent != 1)
		{
			UnitInfo prefixInfo = Managed.RaiseToIntegerExponent
			(
				unitPart.getPrefix().getFactor(), exponent
			);

			outInfo.Prefix = new Prefix();
			outInfo = Managed.PerformManagedOperationUnits
			(
				outInfo, prefixInfo, Operations.Multiplication
			);
		}

		return outInfo;
	}
	
	static ArrayList<UnitPart> GetUnitPartsConversion(UnitPart part1, UnitPart part2)
	{
		ArrayList<UnitPart> unitParts = new ArrayList<UnitPart>()
		{{
			//Exponent signs will be managed at a later stage.
			add(new UnitPart(part1) {{ Exponent = Math.abs(part1.Exponent); }});
			add(new UnitPart(part2) {{ Exponent = Math.abs(part2.Exponent); }});
		}};

		ArrayList<UnitPart> tempParts = GetUnitPartsConversionSameType(unitParts);
		if (tempParts != null) return tempParts;

		int count = 0;
		while (count < 2)
		{
			count = count + 1;
			int i = 0;
			int i2 = 1;
			if (count == 2)
			{
				i = 1;
				i2 = 0;
			}

			for (int i11 = unitParts.get(i).Exponent; i11 > 0; i11--)
			{
				unitParts.get(i).Exponent = i11;
				for (int i22 = unitParts.get(i2).Exponent; i22 > 0; i22--)
				{
					unitParts.get(i2).Exponent = i22;
					tempParts = GetUnitPartsConversionSameType(unitParts);
					if (tempParts != null)
					{
						//Accounts for scenarios on the lines of m3/L2.
						return tempParts;
					}
				}
			}
		}

		return null;
	}

	static ArrayList<UnitPart> GetUnitPartsConversionSameType(ArrayList<UnitPart> unitParts)
	{
		if (MethodsCommon.GetTypeFromUnitInfo(ExceptionInstantiation.NewUnitInfo(unitParts.get(0).getUnit(), 1.0)) == MethodsCommon.GetTypeFromUnitInfo(ExceptionInstantiation.NewUnitInfo(unitParts.get(1).getUnit(), 1.0)))
		{
			unitParts.get(0).Exponent = 1;
			unitParts.get(1).Exponent = 1;

			return unitParts;
		}
		else if (MethodsCommon.GetTypeFromUnitPart(unitParts.get(0)) == MethodsCommon.GetTypeFromUnitPart(unitParts.get(1)))
		{
			return unitParts;
		}
		else if (MethodsCommon.GetTypeFromUnitPart(unitParts.get(0), true) == MethodsCommon.GetTypeFromUnitPart(unitParts.get(1), true))
		{
			//Cases like m2/ft3 recognised as length. Exponents are being managed later.
			unitParts.get(0).Exponent = 1;
			unitParts.get(1).Exponent = 1;

			return unitParts;
		}

		return null;
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
				int partMatchCount = (int)originalInfo.Parts.stream().filter
				(
					x -> Linq.FirstOrDefault
					(
						targetInfo.Parts, y -> y.Exponent.equals(x.Exponent) && y.getUnit().equals(x.getUnit()), null
					)
					!= null
				)
				.count();
				
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
	
	static UnitInfo PerformUnitPartConversion(UnitInfo convertInfo, UnitInfo target)
	{
		return PerformUnitPartConversion(convertInfo, target, true);
	}
	
	static UnitInfo PerformUnitPartConversion(UnitInfo convertInfo, UnitInfo target, boolean isInternal)
	{
		ConversionItems conversionItems = GetAllUnitPartDict(convertInfo.Parts, target.Parts);

		if (conversionItems.ConvertInfo != null)
		{
			convertInfo = Managed.PerformManagedOperationUnits
			(
				convertInfo, conversionItems.ConvertInfo, Operations.Multiplication
			);
		}

		return
		(
			conversionItems.OutDict.size() == 0 ?  
			ExceptionInstantiation.NewUnitInfo(convertInfo, ErrorTypes.InvalidUnitConversion) 
			: ConvertAllUnitParts
			(
				convertInfo, conversionItems.OutDict, isInternal
			)
		);
	}
	
	static UnitInfo ConvertAllUnitParts(UnitInfo convertInfo, HashMap<UnitPart, UnitPart> allParts)
	{
		return ConvertAllUnitParts(convertInfo, allParts, true);
	}
	
	static UnitInfo ConvertAllUnitParts(UnitInfo convertInfo, HashMap<UnitPart, UnitPart> allParts, boolean isInternal)
	{
		for (Map.Entry<UnitPart, UnitPart> item: allParts.entrySet())
		{
			convertInfo = ConvertUnitPartToTarget
			(
				convertInfo, item.getKey(), item.getValue(), isInternal
			);

			if (convertInfo.Error.getType() != ErrorTypes.None)
			{
				return convertInfo;
			}
		}

		return convertInfo;
	}
	
	static ConversionItems GetAllUnitPartDict(ArrayList<UnitPart> originals, ArrayList<UnitPart> targets)
	{
		return GetAllUnitPartDict(originals, targets, true);
	}
	
	//Relates all the original/target unit parts between each other in order to facilitate the subsequent unit conversion.
	static ConversionItems GetAllUnitPartDict(ArrayList<UnitPart> originals, ArrayList<UnitPart> targets, boolean findCommonPartMatches)
	{
		ConversionItems conversionItems = new ConversionItems(originals, targets);

		for (UnitPart original: originals)
		{
			UnitTypes type = MethodsCommon.GetTypeFromUnitPart(original);

			UnitPart target = Linq.FirstOrDefault
			(
				targets, x -> MethodsCommon.GetTypeFromUnitPart(x).equals(type), null
			);
			if (target == null || target.getUnit() == Units.None) continue;

			conversionItems.OutDict.put(original, target);
		}

		if (conversionItems.OutDict.size() < Math.max(originals.size(), targets.size()))
		{
			if (findCommonPartMatches)
			{
				conversionItems = GetUnitPartDictInCommon
				(
					new ConversionItems(originals, targets)
				);
			}
			else conversionItems.OutDict = new HashMap<UnitPart, UnitPart>();
		}

		return conversionItems;
	}

	//In some cases, the GetAllUnitPartDict approach doesn't work. For example: BTU/s and W. 
	//Note that ignoring the dividable/non-dividable differences right away (useful in other situations) isn't good here.
	//The only solution is looking for common parts to both units (always the case, by bearing in mind that have the same type). 
	//In the aforementioned example of BTU/s to W, the two pairs BTU-J and s-s will be returned.
	static ConversionItems GetUnitPartDictInCommon(ConversionItems conversionItems)
	{
		conversionItems = GetNonDividableUnitPartDictInCommon(conversionItems);
		
		if ((conversionItems.Originals.size() == conversionItems.Targets.size() && conversionItems.Originals.size() == 0))
		{
			//No originals/targets left would mean that no further analysis is required.
			//Not having found anything (conversionItems.OutDict empty) is OK on account of the fact that
			//unmatched non-dividables might have been converted into a dividable version.
			return conversionItems;
		}

		//Trying to match the remaining parts (i.e., individual units not matching any non-dividable compound).
		ConversionItems conversionItems2 = GetAllUnitPartDict
		(
			conversionItems.Originals, conversionItems.Targets, false
		);
		if (conversionItems2.OutDict.size() == 0) return conversionItems2;

		for (Map.Entry<UnitPart, UnitPart> item2: conversionItems2.OutDict.entrySet())
		{
			conversionItems.OutDict.put(item2.getKey(), item2.getValue());
		}

		return conversionItems;
	}

	//Method looking for proper matches for each non-dividable compound. For example, BTU might be
	//matched with kg*m/s2 (= N).
	static ConversionItems GetNonDividableUnitPartDictInCommon(ConversionItems conversionItems)
	{
		int count = 0;
		while (count < 2 && (conversionItems.Originals.size() > 0 && conversionItems.Targets.size() > 0))
		{
			count = count + 1;
			if (count == 1)
			{
				conversionItems.Others = conversionItems.Targets;
				conversionItems.NonDividables = GetNonDividableParts(conversionItems.Originals);
			}
			else
			{
				conversionItems.Others = conversionItems.Originals;
				conversionItems.NonDividables = GetNonDividableParts(conversionItems.Targets);
			}

			for (int i = conversionItems.NonDividables.size() - 1; i >= 0; i--)
			{
				if (conversionItems.Others.size() == 0) return new ConversionItems();

				conversionItems = MatchNonDividableParts(conversionItems, i);
				if (conversionItems.TempPair.getKey() == null || conversionItems.TempPair.getKey().getUnit() == Units.None)
				{
					//After not having found a direct match for the given non-dividable, an indirect
					//approach (via its type) will be tried.
					conversionItems = ReplaceUnmatchedNonDividable
					(
						conversionItems, count, i
					);

					if (conversionItems.Originals.size() == 0) return conversionItems;

					continue;
				}

				//A proper match for the non-divididable part was found.
				//That is, a set of common parts in others which also form a valid compound. 
				conversionItems = UpdateConversionItems(conversionItems, count, i);
			}
		}

		return conversionItems;
	}
	
	static ConversionItems UpdateConversionItems(ConversionItems conversionItems, int count, int i)
	{
		return UpdateOutDict
		(
			RemoveNonDividable(conversionItems, count, i), count
		);
	}
	
	static ConversionItems UpdateOutDict(ConversionItems conversionItems, int count)
	{
		Map.Entry<UnitPart, UnitPart> tempPair =
		(
			count == 1 ? 
			new AbstractMap.SimpleEntry<UnitPart, UnitPart>
			(
				conversionItems.TempPair.getKey(), conversionItems.TempPair.getValue()
			) 
			: new AbstractMap.SimpleEntry<UnitPart, UnitPart>
			(
				conversionItems.TempPair.getValue(), conversionItems.TempPair.getKey()
			)
		);

		conversionItems.OutDict.put(tempPair.getKey(), tempPair.getValue());

		return conversionItems;
	}
	
	static ConversionItems RemoveNonDividable(ConversionItems conversionItems, int count, int i)
	{
		if (conversionItems.NonDividables.size() - 1 < i) return conversionItems;

		conversionItems.NonDividables.remove(i);

		//Originals & Targets are automatically updated with any modification in Others.
		//Not the case with NonDividables, that's why having to call this method.
		return RemoveNonDividableOriginals(conversionItems, count);
	}
	
	static ConversionItems RemoveNonDividableOriginals(ConversionItems conversionItems, int count)
	{
		ArrayList<UnitPart> nonOriginals =
		(
			count == 1 ? conversionItems.Originals : conversionItems.Targets
		);

		for (int i = nonOriginals.size() - 1; i >= 0; i--)
		{
			if (!HCUnits.AllNonDividableUnits.contains(nonOriginals.get(i).getUnit())) continue;

			if (!conversionItems.NonDividables.contains(nonOriginals.get(i)))
			{
				nonOriginals.remove(i);
			}
		}

		return conversionItems;
	}
	
	//Method trying to match each item of conversionItems.NonDividables with parts of conversionItems.Others. 
	//Bear in mind that the goal isn't just looking for sets of unit parts defining the given type; they have 
	//also to be associated with a valid compound unit. For example: when trying to match the force unit lbf,
	//lb*ft/s2 wouldn't be a good match; these parts do define a force, but not a supported unit. A good match
	//would be kg*m/s2, which also defines the supported unit newton. 
	//It is necessary to find a supported unit because this is the way to get a conversion factor; just converting 
	//the parts would output a wrong result. In the aforementioned example, lb*ft/s2 doesn't equal lbf (otherwise,
	//lbf would be defined as a compound precisely formed by these parts).
	static ConversionItems MatchNonDividableParts(ConversionItems conversionItems, int i)
	{
		conversionItems.TempPair = new AbstractMap.SimpleEntry<UnitPart, UnitPart>(null, null);

		UnitPart matchedPart = Linq.FirstOrDefault
		(
			conversionItems.Others, x -> x.getUnit().equals(conversionItems.NonDividables.get(i).getUnit()), null
		);
				
		if (matchedPart != null)
		{
			conversionItems.Others.remove(matchedPart);
			conversionItems.TempPair = new AbstractMap.SimpleEntry<UnitPart, UnitPart>
			(
				conversionItems.NonDividables.get(i), matchedPart
			);

			return conversionItems;
		}

		ConversionItems origConversionItems = new ConversionItems(conversionItems);

		ArrayList<UnitPart> parts2 = new ArrayList<UnitPart>();
		for (CompoundPart nonPart: HCCompounds.AllCompounds.get(MethodsCommon.GetTypeFromUnitPart(conversionItems.NonDividables.get(i))).get(0).Parts)
		{
			matchedPart = Linq.FirstOrDefault
			(
				conversionItems.Others, x -> nonPart.Type.equals(MethodsCommon.GetTypeFromUnit(x.getUnit())), null
			); 
			
			if (matchedPart == null)
			{
				return origConversionItems;
			}

			//Exponent of the corresponding conversionItems.Others part after having
			//removed the current nonPart. This exponent doesn't have to match the one
			//of the associated type (i.e., the one being stored in parts2).
			int exponent = matchedPart.Exponent -
			(
				(int)Math.signum(conversionItems.NonDividables.get(i).Exponent) * nonPart.Exponent
			);

			parts2.add
			(
				new UnitPart(matchedPart.getUnit(), nonPart.Exponent)
			);
			conversionItems.Others.remove(matchedPart);

			if (exponent != 0)
			{
				conversionItems.Others.add
				(
					UnitPartInternal.NewUnitPart
					(
						matchedPart.getUnit(), 
						matchedPart.getPrefix().getFactor(),
						exponent 
					)
				);
			}
		}

		UnitInfo tempInfo = MethodsCommon.GetCompoundUnitFromParts
		(
			MethodsCommon.GetPartsFromUnit
			(
				ExceptionInstantiation.NewUnitInfo(parts2)
			)
		);

		if (tempInfo.Unit == Units.None)
		{
			//Condition avoiding situations like assuming that lb*ft/s2 & lbf are identical.
			return origConversionItems;            
		}

		conversionItems.TempPair = new AbstractMap.SimpleEntry<UnitPart, UnitPart>
		(
			conversionItems.NonDividables.get(i), UnitPartInternal.NewUnitPart
			(
				tempInfo.Unit, tempInfo.Prefix.getFactor(),
				conversionItems.NonDividables.get(i).Exponent
			)
		);

		return conversionItems;
	}
	
	static ArrayList<UnitPart> GetNonDividableParts(ArrayList<UnitPart> iniList)
	{
		ArrayList<UnitPart> outList = Linq.Where
		(
			iniList, x -> HCUnits.AllNonDividableUnits.contains(x.getUnit())
		);

		HashMap<UnitPart, UnitTypes> types = new HashMap<UnitPart, UnitTypes>();
		for (int i = outList.size() - 1; i >= 0; i--)
		{
			UnitTypes type = MethodsCommon.GetTypeFromUnitPart(outList.get(i));
			if (!HCCompounds.AllCompounds.containsKey(type))
			{
				//Theoretically, this shouldn't ever happen because AllNonDividableUnits
				//is expected to only contain compounds. On the other hand, an error here
				//wouldn't be that weird and, in any case, not too influential. That's why
				//this in-the-safest-scenario check is acceptable.
				outList.remove(i);
			}
			else types.put(outList.get(i), type);
		}

		return Linq.OrderByDescending
		(
			outList, new Comparator<UnitPart>()
			{
				//Original C# code: x -> HCCompounds.AllCompounds.get(types[x]).get(0).Parts.Count
				public int compare(UnitPart first, UnitPart second)
				{
					return new Integer(HCCompounds.AllCompounds.get(types.get(first)).get(0).Parts.size()).compareTo
					(
						HCCompounds.AllCompounds.get(types.get(second)).get(0).Parts.size()
					);
				}
			}
		);
	}

	static ConversionItems ReplaceUnmatchedNonDividable(ConversionItems conversionItems, int count, int i)
	{
		UnitTypes type = MethodsCommon.GetTypeFromUnitPart(conversionItems.NonDividables.get(i), false, true);

		if 
		(
			!HCCompounds.AllBasicCompounds.containsKey(type) || 
			!(HCCompounds.AllBasicCompounds.get(type).containsKey(UnitSystems.SI) || 
			HCCompounds.AllBasicCompounds.get(type).containsKey(UnitSystems.CGS))
		)
		{
			//A hardcoding mistake is the most likely reason for having reached this point. 
			//Firstly, all the non-dividable units are supposed to be compounds. On the other hand, all the compounds
			//are expected to be represented by, at least, one unit (included in AllBasicCompounds). Although it is
			//possible to have a type with no SI units, such a case shouldn't get here. Note that non-dividable are
			//expected to be defined as opposed to an existing dividable alternative (e.g., SI compound in that type).
			return new ConversionItems();
		}

		Units targetUnit =
		(
			HCCompounds.AllBasicCompounds.get(type).containsKey(UnitSystems.SI) ?
			HCCompounds.AllBasicCompounds.get(type).get(UnitSystems.SI) :
			HCCompounds.AllBasicCompounds.get(type).get(UnitSystems.CGS)
		);

		return RemoveNonDividable
		(
			PerformNonDividableReplacement
			(
				conversionItems, count, i, targetUnit
			), 
			count, i
		);
	}

	static ConversionItems PerformNonDividableReplacement(ConversionItems conversionItems, int count, int i, Units targetUnit)
	{
		conversionItems.ConvertInfo = ConvertUnit
		(
			MethodsCommon.UnitPartToUnitInfo(conversionItems.NonDividables.get(i)),
			MethodsCommon.UpdateMainUnitVariables(ExceptionInstantiation.NewUnitInfo(targetUnit)), false
		);

		if (conversionItems.ConvertInfo.Error.getType() != ErrorTypes.None)
		{
			return new ConversionItems();
		}

		ArrayList<UnitPart> list =
		(
			count == 1 ? 
			conversionItems.Originals :
			conversionItems.Targets                
		);

		list.remove(conversionItems.NonDividables.get(i));
		list.addAll(MethodsCommon.GetBasicCompoundUnitParts(targetUnit));

		UnitInfo tempInfo = MethodsCommon.SimplifyCompoundComparisonUnitParts(list, true);
		list = new ArrayList<UnitPart>(tempInfo.Parts);
		//The simplification actions might have increased the output value via prefix compensation.
		conversionItems.ConvertInfo = Managed.PerformManagedOperationUnits
		(
			conversionItems.ConvertInfo, tempInfo, Operations.Multiplication
		);
		
		if (count == 1)
		{
			conversionItems.Originals = new ArrayList<UnitPart>(list);
		}
		else
		{
			conversionItems.Targets = new ArrayList<UnitPart>(list);
		}

		return conversionItems;
	}
	
	static UnitInfo ConvertUnit(UnitInfo originalInfo, UnitInfo targetInfo)
	{
		return ConvertUnit(originalInfo, targetInfo, true);
	}
	
	public static UnitInfo ConvertUnit(UnitInfo originalInfo, UnitInfo targetInfo, boolean isInternal)
	{
		return PerformConversion
		(
			originalInfo, targetInfo, isInternal
		);
	}
	
	//This function expects targetInfo2 to be normalised.
	static UnitInfo AccountForTargetUnitPrefixes(UnitInfo originalInfo, UnitInfo targetInfo2)
	{
		int newExponent = GetBaseTenExponentIncreasePrefixes
		(
			originalInfo, targetInfo2.BaseTenExponent
		);
		
		return Managed.NormaliseUnitInfo
		(
			//Prefix.Factor is already included in newExponent.
			ExceptionInstantiation.NewUnitInfo
			(
				originalInfo, newExponent, new Prefix(originalInfo.Prefix.getPrefixUsage())
			)
		);
	}
	
	static int GetBaseTenExponentIncreasePrefixes(UnitInfo originalInfo, int targetInfo2Exp)
	{
		//targetInfo2 is being normalised by only accounting for the value information which is 
		//relevant to the conversion (i.e., the prefix).
		UnitInfo originalTemp = ExceptionInstantiation.NewUnitInfo(originalInfo, 1.0, 0);
		originalTemp = Managed.NormaliseUnitInfo(originalTemp);

		return originalTemp.BaseTenExponent - targetInfo2Exp;
	}
	
	static UnitInfo NormaliseTargetUnit(UnitInfo targetInfo, boolean isInternal)
	{
		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo(targetInfo);

		if (isInternal)
		{
			//The only relevant part of the target unit value should ideally be the prefix.
			//Such an assumption isn't always compatible with external conversions; in these cases,
			//the target unit might have been parsed (+ auto-converted) and, consequently, other
			//values might have to be also considered.
			outInfo.Value = 1.0;
			outInfo.BaseTenExponent = 0;
		}

		return Managed.NormaliseUnitInfo(outInfo);
	}
	
	//Determines whether the conversion might be performed directly. That is: by only considering the
	//main unit information (i.e., Units enum member), rather than its constituent parts.
	static boolean UnitsCanBeConvertedDirectly(UnitInfo original, UnitInfo target)
	{
		if (original.Unit != Units.None && original.Unit != Units.Unitless && !MethodsCommon.IsUnnamedUnit(original.Unit))
		{
			if (target.Unit != Units.None && target.Unit != Units.Unitless && !MethodsCommon.IsUnnamedUnit(target.Unit))
			{
				return true;
			}
		}

		return false;
	}
	   
	static UnitInfo ConvertUnitValue(UnitInfo original, UnitInfo target)
	{
		return ConvertUnitValue(original, target, false);
	}
	
	//The prefixes of both units are being managed before reaching this point.
	static UnitInfo ConvertUnitValue(UnitInfo original, UnitInfo target, boolean inverseOutputs)
	{
		if
		(
			original.Unit == Units.None || original.Unit == Units.Unitless ||
			target.Unit == Units.Unitless || target.Unit == Units.Unitless
		)
		{
			original.Error = ExceptionInstantiation.NewErrorInfo(ErrorTypes.InvalidUnit);
			return original;
		}

		//Both units have the same type.
		if (original.Type != UnitTypes.None && SpecialConversionTypes.contains(original.Type))
		{
			UnitInfo tempInfo = ConvertUnitValueSpecial(original, target);
			return 
			(
				inverseOutputs ? 
				Managed.PerformManagedOperationUnits(1.0, tempInfo, Operations.Division) : 
				tempInfo	
			);
		}
		else
		{
			UnitInfo original2 = GetUnitConversionFactor(original.Unit);        	
			UnitInfo target2 = GetUnitConversionFactor(target.Unit);
			UnitInfo convFactor =
			(
				inverseOutputs ?
				Managed.PerformManagedOperationUnits(target2, original2, Operations.Division) :
				Managed.PerformManagedOperationUnits(original2, target2, Operations.Division)                	
			);
			
			return Managed.PerformManagedOperationUnits
			(
				original, convFactor, Operations.Multiplication
			);
		}       
	}
	
	static UnitInfo GetUnitConversionFactor(Units unit)
	{
		return
		(
			HCMain.AllUnitConversionFactors.get(unit) < 0 ?
			HCMain.AllBeyondDecimalConversionFactors.get(HCMain.AllUnitConversionFactors.get(unit)) :
			ExceptionInstantiation.NewUnitInfo(HCMain.AllUnitConversionFactors.get(unit))
		);
	}
	
	//Takes care of conversions which do not rely on conversion factors.
	static UnitInfo ConvertUnitValueSpecial(UnitInfo original, UnitInfo target)
	{
		UnitInfo convertInfo = ExceptionInstantiation.NewUnitInfo(original);

		if (original.Type == UnitTypes.Temperature)
		{
			convertInfo = ConvertTemperature(convertInfo, target.Unit);
		}

		return convertInfo;
	}
	
	static UnitInfo ConvertTemperature(UnitInfo outInfo, Units targetUnit)
	{
		if (targetUnit == Units.Kelvin)
		{
			outInfo = ConvertTemperatureToKelvin(outInfo);
		}
		else if (outInfo.Unit == Units.Kelvin)
		{
			outInfo = ConvertTemperatureFromKelvin
			(
				ExceptionInstantiation.NewUnitInfo(outInfo, targetUnit)
			);
		}
		else
		{
			outInfo = ConvertTemperatureToKelvin(ExceptionInstantiation.NewUnitInfo(outInfo));
			outInfo = ConvertTemperatureFromKelvin(ExceptionInstantiation.NewUnitInfo(outInfo, targetUnit));
		}

		return outInfo;
	}

	static UnitInfo ConvertTemperatureToKelvin(UnitInfo outInfo)
	{
		if (outInfo.Unit == Units.DegreeCelsius)
		{
			outInfo = Managed.PerformManagedOperationUnits(outInfo, 273.15, Operations.Addition);
		}
		else if (outInfo.Unit == Units.DegreeFahrenheit)
		{
			outInfo = Managed.PerformManagedOperationUnits
			(
				Managed.PerformManagedOperationUnits
				(
					outInfo, 459.67, Operations.Addition
				), 
				1.8, Operations.Division
			);
		}
		else if (outInfo.Unit == Units.DegreeRankine)
		{
			outInfo = Managed.PerformManagedOperationUnits
			(
				outInfo, 1.8, Operations.Division
			);
		}

		return outInfo;
	}

	static UnitInfo ConvertTemperatureFromKelvin(UnitInfo outInfo)
	{
		if (outInfo.Unit == Units.DegreeCelsius)
		{
			outInfo = Managed.PerformManagedOperationUnits
			(
				outInfo, 273.15, Operations.Subtraction
			);
		}
		else if (outInfo.Unit == Units.DegreeFahrenheit)
		{
			outInfo = Managed.PerformManagedOperationUnits
			(
				Managed.PerformManagedOperationUnits
				(
					1.8, outInfo, Operations.Multiplication
				), 
				 459.67, Operations.Subtraction
			);  
		}
		else if (outInfo.Unit == Units.DegreeRankine)
		{
			outInfo = Managed.PerformManagedOperationUnits
			(
				1.8, outInfo, Operations.Multiplication
			);
		}
		
		return outInfo;
	}    
}
