package InternalUnitParser.Methods;

import InternalUnitParser.Classes.*;
import InternalUnitParser.Hardcoding.*;
import InternalUnitParser.Operations.*;
import InternalUnitParser.Parse.*;
import InternalUnitParser.CSharpAdaptation.*;
import UnitParser.*;
import UnitParser.UnitP.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

@SuppressWarnings("serial")
public class MethodsCommon
{
	public static Units GetUnitFromString(String input)
	{
		return GetUnitFromString(input, null);
	} 
	   
	public static Units GetUnitFromString(String input, ParseInfo parseInfo)
	{
		Units unit = GetUnitFromUnitSymbols(input);

		return
		(
			unit != Units.None ? unit : 
			GetUnitFromUnitStrings(input, parseInfo)
		);
	}
	
	static Units GetUnitFromUnitSymbols(String input)
	{
		return
		(
			HCUnits.AllUnitSymbols.containsKey(input) ?
			HCUnits.AllUnitSymbols.get(input) :
			GetUnitFromUnitSymbols2(input)
		);
	}
	
	static Units GetUnitFromUnitSymbols2(String input)
	{
		return
		(
			HCMain.AllUnitSymbols2.containsKey(input) ?
			HCMain.AllUnitSymbols2.get(input) : Units.None
		);
	}
  
	static Units GetUnitFromUnitStrings(String input, ParseInfo parseInfo)
	{
		String inputLower = input.toLowerCase();

		Units unit = Units.None;
		try
		{
			unit = Linq.FirstOrDefaultDict
			(
				HCMain.AllUnitStrings, x -> 
				(
					x.getKey().toLowerCase().equals(inputLower) || 
					GetUnitStringPlural(x.getKey().toLowerCase()).equals(inputLower)
				),
				new AbstractMap.SimpleEntry<String, Units>(null, Units.None)
			)
			.getValue();       	
		}
		catch(Exception e)
		{
			//This part will never be hit as far as FirstOrDefaultDict takes care of all the possible scenarios.
			//The only motivation for this try...catch is complying in the Java requirements
		}
		
		if (parseInfo != null && unit != Units.None)
		{
			//To account for somehow unlikely scenarios where non-official abbreviations are 
			//misinterpreted. For example, Gs misunderstood as grams rather than as gigasecond.
			ParseInfo temp = Parse.CheckPrefixes(parseInfo);
			if (temp.UnitInfo.Unit != Units.None)
			{
				//The unit (+ prefix) will be adequately analysed somewhere else.
				return Units.None;
			}
		}

		return unit;
	}
	
	public static boolean IsUnnamedUnit(Units unit)
	{
		return
		(
			unit == Units.ValidImperialUnit || 
			HCMain.DefaultUnnamedUnits.containsValue(unit)
		);
	}
	
	//A proper plural determination isn't required. The outputs of this method are quite secondary and
	//the supported units quite regular on this front.
	static String GetUnitStringPlural(String unitString)
	{
		if (unitString.endsWith("y"))
		{
			return CSharpOther.Substring
			(
				unitString, 0, unitString.length() - 1
			) 
			+ "ies";
		}
		unitString = unitString.replace("inches", "inch");
		unitString = unitString.replace("inch", "inches");
		unitString = unitString.replace("foot", "feet");

		return
		(
			unitString.endsWith("inches") || unitString.endsWith("feet") ?
			unitString : unitString + "s"
		);
	}
	
	public static String GetUnitString(UnitInfo unitInfo)
	{
		String outUnit = GetUnitStringIndividual(unitInfo);

		return
		(
			outUnit == "None" ?
			GetUnitStringCompound(unitInfo) : outUnit
		);
	}
  
	//Returns the string representation of the unit associated with the given compound.
	//NOTE: this function assumes that a valid compound is already in place.
	static String GetUnitStringCompound(UnitInfo unitInfo)
	{
		if (unitInfo.Unit == Units.None) return "None";
		else if (unitInfo.Unit == Units.Unitless) return "Unitless";

		String outUnitString = "";
		boolean isNumerator = true;

		for (UnitPart unitPart: unitInfo.Parts)
		{
			if (isNumerator && unitPart.Exponent < 0)
			{
				isNumerator = false;
				if (outUnitString == "") outUnitString = "1";
				outUnitString = outUnitString + "/";
			}
			else if (outUnitString != "") outUnitString = outUnitString + "*";

			String unitString = "";
			if (unitPart.getPrefix().getSymbol() != "" && !unitPart.getUnit().toString().toLowerCase().startsWith(unitPart.getPrefix().getName().toLowerCase()))
			{
				unitString = unitPart.getPrefix().getSymbol();
			}
			unitString += Linq.FirstOrDefaultDict
			(
				HCUnits.AllUnitSymbols, x -> x.getValue().equals(unitPart.getUnit()), 
				new AbstractMap.SimpleEntry<String, Units>(null, Units.None)
			)
			.getKey(); 

			Integer exponent = Math.abs(unitPart.Exponent);
			if (exponent != 1) unitString = unitString + exponent.toString();

			outUnitString = outUnitString + unitString;
		}

		return outUnitString;
	}
	
	static String GetUnitStringIndividual(UnitInfo unitInfo)
	{
		String unitString = "None";

		if (unitInfo.Unit != Units.None && unitInfo.Unit != Units.Unitless && !IsUnnamedUnit(unitInfo.Unit))
		{
			if (HCUnits.AllUnitSymbols.containsValue(unitInfo.Unit))
			{
				unitString = Linq.FirstOrDefaultDict
				(
					HCUnits.AllUnitSymbols, x -> x.getValue().equals(unitInfo.Unit), 
					new AbstractMap.SimpleEntry<String, Units>(null, Units.None) 
				)
				.getKey();
						
				if (unitInfo.Prefix.getSymbol() != "")
				{
					unitString = unitInfo.Prefix.getSymbol() + unitString;
				}
			}
		}

		return unitString;
	}
	
	public static UnitInfo RemoveAllUnitInformation(UnitInfo unitInfo)
	{
		return RemoveAllUnitInformation(unitInfo, false);
	}
	
	public static UnitInfo RemoveAllUnitInformation(UnitInfo unitInfo, boolean partsToo)
	{
		unitInfo.Unit = Units.None;
		unitInfo.System = UnitSystems.None;
		unitInfo.Type = UnitTypes.None;

		if (partsToo) unitInfo.Parts = new ArrayList<UnitPart>();

		return unitInfo;
	}   
	
	public static UnitTypes GetTypeFromUnitPart(UnitPart unitPart)
	{
		return GetTypeFromUnitPart(unitPart, false);
	}
  
	public static UnitTypes GetTypeFromUnitPart(UnitPart unitPart, boolean ignoreExponents)
	{
		return GetTypeFromUnitPart(unitPart, ignoreExponents, false);
	}
	
	public static UnitTypes GetTypeFromUnitPart(UnitPart unitPart, boolean ignoreExponents, boolean simplestApproach)
	{
		UnitPart unitPart2 = new UnitPart(unitPart);

		if (simplestApproach)
		{
			//This is reached when calling from parts of the code likely to provoke an infinite loop. 
			UnitTypes type2 = GetTypeFromUnit(unitPart2.getUnit());
			
			if (type2 == UnitTypes.Length)
			{
				if (unitPart2.Exponent == 2)
				{
					type2 = UnitTypes.Area;
				}
				else if (unitPart2.Exponent == 3)
				{
					type2 = UnitTypes.Volume;
				}
			}

			return type2;
		}

		//When comparing unit part types, the exponent is often irrelevant. For example: in the compound
		//kg*m4, looking for m4 would yield no match (unlikely looking just for m).
		if (ignoreExponents) unitPart2.Exponent = 1;

		//Negative exponents do not affect type determination. For example, a unit consisting
		//in just the part m-1 is wavenumber (negative exponent being relevant), but is expected
		//to be treated as length (-1 doesn't matter) because of being used in internal calculations.
		unitPart2.Exponent = Math.abs(unitPart2.Exponent);
		UnitInfo unitInfo = null;
		try
		{
			unitInfo = new UnitInfo(unitPart2.getUnit(), unitPart2.getPrefix().getFactor());        	
		}
		catch(Exception e)
		{ 
			//This part will never be reached because all the calculations are caught/managed (= no uncontrolled exceptions). 
			//The whole point of this try-catch is to comply with the Java compiler requirements without adding a throws declaration.
		}
		unitInfo.Parts = new ArrayList<UnitPart>() {{ add(unitPart2); }};

		UnitTypes outType = GetTypeFromUnitInfo(unitInfo);
		return 
		(
			outType == UnitTypes.None && unitPart2.Exponent != 1 ?
			//To account for cases like kg4 within compounds, expected to be understood as kg.
			GetTypeFromUnitPart(new UnitPart(unitPart2) {{ this.Exponent = 1; }}) : outType
		);
	}

	public static UnitTypes GetTypeFromUnitInfo(UnitInfo unitInfo)
	{
		UnitTypes outType = UnitTypes.None;
		if (unitInfo.Parts.size() > 1 || Linq.FirstOrDefault(unitInfo.Parts, x -> !x.Exponent.equals(1), null) != null)   
		{
			outType = GetBasicCompoundType(unitInfo).Type;
		}
		
		return
		(
			outType != UnitTypes.None ?
			outType : GetTypeFromUnit(unitInfo.Unit)
		);
	}

	public static UnitTypes GetTypeFromUnit(Units unit)
	{
		return
		(
			unit == Units.None || unit == Units.Unitless || IsUnnamedUnit(unit) ?
			UnitTypes.None : HCMain.AllUnitTypes.get(unit)
		);
	}
	
	public static UnitSystems GetSystemFromUnit(Units unit)
	{
		return GetSystemFromUnit(unit, false, false);    	
	}
	
	public static UnitSystems GetSystemFromUnit(Units unit, boolean getBasicVersion)
	{
		return GetSystemFromUnit(unit, getBasicVersion, false);
	}
	
	public static UnitSystems GetSystemFromUnit(Units unit, boolean getBasicVersion, boolean getImperialAndUSCS)
	{
		if (unit == Units.None || unit == Units.Unitless)
		{
			return UnitSystems.None;
		}

		UnitSystems system = HCMain.AllUnitSystems.get((Units)unit);

		if (getImperialAndUSCS)
		{
			if (system == UnitSystems.Imperial && HCUnits.AllImperialAndUSCSUnits.contains(unit))
			{
				system = UnitSystems.ImperialAndUSCS;
			}
		}

		return 
		(
			getBasicVersion ? HCMain.AllBasicSystems.get(system) : system
		);
	}  
	
	public static HashMap<UnitPart, Integer> GetInitialPositions(ArrayList<UnitPart> unitParts)
	{
		HashMap<UnitPart, Integer> outDict = new HashMap<UnitPart, Integer>();

		for (int i = 0; i < unitParts.size(); i++)
		{
			if (!outDict.containsKey(unitParts.get(i)))
			{
				outDict.put(unitParts.get(i), i);
			}
		}

		return outDict;
	}
	
	public static UnitInfo GetBasicCompoundType(UnitInfo unitInfo)
	{
		for (Map.Entry<UnitTypes, ArrayList<Compound>> allCompound: HCCompounds.AllCompounds.entrySet())
		{
			for (Compound compound: allCompound.getValue())
			{
				unitInfo = UnitPartsMatchCompound
				(
					unitInfo, compound.Parts, allCompound.getKey()
				);

				if (unitInfo.Type != UnitTypes.None)
				{
					return unitInfo;
				}
			}
		}

		return unitInfo;
	}

	public static UnitInfo UpdateMainUnitVariables(UnitInfo unitInfo)
	{
		return UpdateMainUnitVariables(unitInfo, false);
	}
	
	public static UnitInfo UpdateMainUnitVariables(UnitInfo unitInfo, boolean recalculateAlways)
	{
		if (unitInfo.Unit == Units.None || unitInfo.Unit == Units.Unitless)
		{
			return unitInfo;
		}

		if (recalculateAlways)
		{
			unitInfo.Type = UnitTypes.None;
			unitInfo.System = UnitSystems.None;
		}

		if (unitInfo.Type == UnitTypes.None)
		{
			unitInfo.Type = GetTypeFromUnitInfo(unitInfo);
		}

		if (unitInfo.System == UnitSystems.None)
		{
			unitInfo.System = GetSystemFromUnitInfo(unitInfo);
		}

		return unitInfo;
	}
	
	static UnitInfo UnitPartsMatchCompound(UnitInfo unitInfo, ArrayList<CompoundPart> compoundParts, UnitTypes compoundType)
	{
		int count = 0;
		while (count < 2)
		{
			count = count + 1;
			ArrayList<UnitPart> unitParts = GetCompoundComparisonUnitParts(unitInfo, count);
			if (UnitPartsMatchCompoundParts(unitParts, compoundParts))
			{
				unitInfo.Type = compoundType;
				return unitInfo;
			}
			else if (count == 2 && unitParts.size() == 1 && unitParts.get(0).Exponent == 1)
			{
				UnitTypes type = GetTypeFromUnit(unitParts.get(0).getUnit());
				if (type != UnitTypes.None)
				{
					//The modifications in GetCompoundComparisonUnitParts generated an individual unit.
					//It might not be recognised anywhere else, so better taken care of it here.
					unitInfo.Unit = HCMain.DefaultUnnamedUnits.get(unitInfo.System);
					unitInfo.Type = type;

					return unitInfo;
				}
			}
		}

		return unitInfo;
	}

	static boolean UnitPartsMatchCompoundParts(ArrayList<UnitPart> unitParts, ArrayList<CompoundPart> compoundParts)
	{
		if (unitParts.size() != compoundParts.size()) return false;

		for (UnitPart part: unitParts)
		{
			UnitTypes type = GetTypeFromUnit(part.getUnit());
			int exponent = part.Exponent;
			if (Linq.FirstOrDefault(compoundParts, x -> x.Type.equals(type) && x.Exponent.equals(exponent), null) == null)
			{
				return false;
			}
		}
		
		return true;
	}
	
	static ArrayList<UnitPart> GetCompoundComparisonUnitParts(UnitInfo unitInfo, int type)
	{
		return 
		(
			type == 1 ?
			new ArrayList<UnitPart>(unitInfo.Parts) :
			GetUnitPartsForAnyUnit(unitInfo)
		);
	}
	
	static ArrayList<UnitPart> GetBasicCompoundUnitParts(UnitTypes type, UnitSystems system)
	{
		return GetBasicCompoundUnitParts(type, system, false);
	}
	
	public static ArrayList<UnitPart> GetBasicCompoundUnitParts(UnitTypes type, UnitSystems system, boolean onePartCompound)
	{
		if (HCCompounds.AllBasicCompounds.containsKey(type) && HCCompounds.AllBasicCompounds.get(type).containsKey(system))
		{
			return GetCompoundUnitParts
			(
					HCCompounds.AllBasicCompounds.get(type).get(system), true,
					type, system, onePartCompound
			);
		}

		return new ArrayList<UnitPart>();
	}

	public static ArrayList<UnitPart> GetBasicCompoundUnitParts(Units unit)
	{
		return GetBasicCompoundUnitParts(unit, false);
	}
	
	public static ArrayList<UnitPart> GetBasicCompoundUnitParts(Units unit, boolean onePartCompound)
	{
		return GetCompoundUnitParts
		(
			unit, true, UnitTypes.None,
			UnitSystems.None, onePartCompound
		);
	}
	
	static ArrayList<UnitPart> GetCompoundUnitParts(Units unit, boolean basicCompound)
	{
		return GetCompoundUnitParts(unit, basicCompound, UnitTypes.None);
	}
	
	static ArrayList<UnitPart> GetCompoundUnitParts(Units unit, boolean basicCompound, UnitTypes type)
	{
		return GetCompoundUnitParts(unit, basicCompound, type, UnitSystems.None);
	}
	
	static ArrayList<UnitPart> GetCompoundUnitParts(Units unit, boolean basicCompound, UnitTypes type, UnitSystems system)
	{
		return GetCompoundUnitParts(unit, basicCompound, type, system, false);
	}
	
	static ArrayList<UnitPart> GetCompoundUnitParts(Units unit, boolean basicCompound, UnitTypes type, UnitSystems system, boolean onePartCompound)
	{
		ArrayList<UnitPart> unitParts = new ArrayList<UnitPart>();

		if (basicCompound)
		{
			if (type == UnitTypes.None)
			{
				type = GetTypeFromUnit(unit);
				system = GetSystemFromUnit(unit);
			}

			if (HCCompounds.AllBasicCompounds.containsKey(type) && HCCompounds.AllBasicCompounds.get(type).containsKey(system))
			{
				
			}
			if (HCCompounds.AllBasicCompounds.containsKey(type) && HCCompounds.AllBasicCompounds.get(type).containsKey(system))
			{
				for (Compound compound: HCCompounds.AllCompounds.get(type))
				{
					if (onePartCompound && compound.Parts.size() > 1)
					{
						//AllCompounds includes various versions for each compound. onePartCompound being true
						//means that only 1-part compounds are relevant.
						continue;
					}
					//When onePartCompound is false, the primary/most-expanded version is expected.
					//In AllCompounds, this version is always located in the first position for the given type.

					for (CompoundPart compoundPart: compound.Parts)
					{
						BasicUnit basicUnit = HCCompounds.AllBasicUnits.get(compoundPart.Type).get(system);

						unitParts.add
						(
							UnitPartInternal.NewUnitPart
							(
								basicUnit.Unit, basicUnit.PrefixFactor,
								compoundPart.Exponent
							)
						);
					}

					return unitParts;
				}
			}
		}
		else if (HCCompounds.AllNonBasicCompounds.containsKey(unit))
		{
			unitParts.addAll(new ArrayList<UnitPart>(HCCompounds.AllNonBasicCompounds.get(unit)));
		}

		return unitParts;
	}
	
	static ArrayList<UnitPart> GetUnitPartsForAnyUnit(UnitInfo unitInfo)
	{
		ArrayList<UnitPart> outParts = new ArrayList<UnitPart>();

		for (UnitPart part: unitInfo.Parts)
		{
			//Under these specific conditions, GetTypeFromUnit is good enough on account of the fact that the
			//exponent is irrelevant. 
			//For example: m3 wouldn't go through this part (type 1 match) and the exponent doesn't define litre.
			//Note that these parts aren't actually correct in many cases, just compatible with the AllCompounds format.
			UnitTypes type2 = GetTypeFromUnitPart(part, false, true);
			if (HCCompounds.AllCompounds.containsKey(type2))
			{
				ArrayList<UnitPart> parts2 = GetUnitPartsFromBasicCompound
				(
						HCCompounds.AllCompounds.get(type2).get(0),
						unitInfo.System, (int)Math.signum(part.Exponent)
				);
				
				outParts.addAll
				(
					AddExponentInformationToParts2(parts2, part)
				);
			}
			else outParts.add(new UnitPart(part));

			outParts = SimplifyCompoundComparisonUnitParts(outParts).Parts;
		}

		return outParts;
	}
	
	static ArrayList<UnitPart> GetUnitPartsFromBasicCompound(Compound compound, UnitSystems system)
	{
		return GetUnitPartsFromBasicCompound(compound, system, 1);
	}
	
	public static ArrayList<UnitPart> GetUnitPartsFromBasicCompound(Compound compound, UnitSystems system, int sign)
	{
		ArrayList<UnitPart> outParts = new ArrayList<UnitPart>();
		if (system == UnitSystems.None) system = UnitSystems.SI;

		for (CompoundPart compoundPart: compound.Parts)
		{
			BasicUnit basic = HCCompounds.AllBasicUnits.get(compoundPart.Type).
			get(HCMain.AllBasicSystems.get(system));
			outParts.add
			(
				UnitPartInternal.NewUnitPart
				(
					basic.Unit, basic.PrefixFactor,
					sign * compoundPart.Exponent
				)
			);
		}

		return outParts;
	}
	
	static ArrayList<UnitPart> AddExponentInformationToParts2(ArrayList<UnitPart> parts2, UnitPart part)
	{
		if (part.Exponent == 1) return parts2;

		//pint^2 converted into (ft3)^2 represents a descriptive example of the kind of situations
		//which reach this point. That is, part.Exponent doesn't participate in the definition of 
		//the given unit/part. The definitory exponents are already stored in parts2.

		for (int i = 0; i < parts2.size(); i++)
		{
			parts2.get(i).Exponent *= part.Exponent;
		}

		return parts2;
	}
	
	public static UnitInfo SimplifyCompoundComparisonUnitParts(ArrayList<UnitPart> unitParts)
	{
		return SimplifyCompoundComparisonUnitParts(unitParts, false);
	}
	
	public static UnitInfo SimplifyCompoundComparisonUnitParts(ArrayList<UnitPart> unitParts, boolean checkPrefixes)
	{
		UnitInfo outInfo = null;
		try
		{
			outInfo = new UnitInfo(1.0) 
			{{ 
				Parts = new ArrayList<UnitPart>(unitParts); 
			}};
		
		}
		catch(Exception e)
		{ 
			//This part will never be reached because all the calculations are caught/managed (= no uncontrolled exceptions). 
			//The whole point of this try-catch is to comply with the Java compiler requirements without adding a throws declaration.
		}

		for (int i = outInfo.Parts.size() - 1; i >= 0; i--)
		{
			for (int i2 = i - 1; i2 >= 0; i2--)
			{
				UnitTypes type1 = GetTypeFromUnit(outInfo.Parts.get(i).getUnit());
				UnitTypes type2 = GetTypeFromUnit(outInfo.Parts.get(i2).getUnit());
				if (type1 == type2)
				{
					if (outInfo.Parts.get(i).getUnit() != outInfo.Parts.get(i2).getUnit())
					{
						//This method is only called to perform basic unit matching; more specifically, finding
						//the (dividable) compounds best matching the non-dividable ones. No direct conversions
						//will be performed among the outputs of this function, that's why the exact units aren't
						//that important. For example: when dealing with rood/rod, the only output which matters
						//is the resulting type (i.e., length). It doesn't matter if it is rod or ft or other unit.
						outInfo.Parts.get(i).setUnit(outInfo.Parts.get(i2).getUnit());
					}
					else if (checkPrefixes && (outInfo.Parts.get(i).getPrefix().getFactor() != 1.0 || outInfo.Parts.get(i2).getPrefix().getFactor() != 1.0))
					{
						//Reaching here means that the returned information will be used in an intermediate conversion.
						//In such a scenario, unit part prefixes might become error sources.
						outInfo = Managed.PerformManagedOperationUnits
						(
							outInfo, Managed.RaiseToIntegerExponent
							(
								outInfo.Parts.get(i).getPrefix().getFactor(),
								outInfo.Parts.get(i).Exponent
							),
							Operations.Multiplication
						);

						outInfo.Parts.get(i).setPrefix(new Prefix());

						outInfo = Managed.PerformManagedOperationUnits
						(
							outInfo, Managed.RaiseToIntegerExponent
							(
								outInfo.Parts.get(i2).getPrefix().getFactor(),
								outInfo.Parts.get(i2).Exponent
							),
							Operations.Multiplication
						);
						
						outInfo.Parts.get(i2).setPrefix(new Prefix());
					}

					outInfo.Parts.get(i).Exponent += outInfo.Parts.get(i2).Exponent;

					if (outInfo.Parts.get(i).Exponent == 0)
					{
						outInfo.Parts.remove(i);
					}
					outInfo.Parts.remove(i2);

					if (outInfo.Parts.size() == 0 || i > outInfo.Parts.size() - 1)
					{
						i = outInfo.Parts.size();
						break;
					}
				}
			}
		}

		for (int i = outInfo.Parts.size() - 1; i >= 0; i--)
		{
			if (outInfo.Parts.get(i).Exponent == 0)
			{
				outInfo.Parts.remove(i);
			}
		}

		return outInfo;
	}
	
	public static UnitSystems GetSystemFromUnitInfo(UnitInfo unitInfo)
	{
		UnitSystems outSystem = UnitSystems.None;

		//Neutral types are meant to avoid misunderstandings like s*ft being understood as SI.
		ArrayList<UnitSystems> neutralSystems = new ArrayList<UnitSystems>();
		boolean allNeutral = true;

		for (UnitPart part: unitInfo.Parts)
		{
			Units partUnit = part.getUnit();

			UnitTypes partType = GetTypeFromUnit(partUnit);
			UnitSystems system2 = GetSystemFromUnit(partUnit);

			if (HCOther.NeutralTypes.contains(partType)) neutralSystems.add(system2);
			else
			{
				allNeutral = false;
				if (outSystem == UnitSystems.None) outSystem = system2;
				else if (outSystem != system2) break;
			}
		}

		if (outSystem == UnitSystems.None && allNeutral && neutralSystems.size() > 0)
		{ 
			//When all the units are neutral, their defining system cannot be ignored.
			outSystem = Linq.FirstOrDefault
			(
				Linq.GroupByJustCountAndOrderByDescending
				(
					neutralSystems, new Comparator<UnitSystems>()
					{
						public int compare(UnitSystems first, UnitSystems second)
						{
							return first.compareTo(second);
						}        				
					}
				), 
				UnitSystems.None
			);		            
		}

		return
		(
			outSystem != UnitSystems.None ?
			outSystem : GetSystemFromUnit(unitInfo.Unit)
		);
	}
	
	//Called before performing any operation.
	public static ErrorTypes GetOperationError(UnitInfo unitInfo1, UnitInfo unitInfo2, Operations operation)
	{
		if (operation == Operations.None) return ErrorTypes.InvalidOperation;
		if (operation == Operations.Division && unitInfo2.Value == 0.0)
		{
			return ErrorTypes.NumericError;
		}

		for (UnitInfo info: new ArrayList<UnitInfo>() {{ add(unitInfo1); add(unitInfo2); }})
		{
			if (info.Error.getType() != ErrorTypes.None)
			{
				return info.Error.getType();
			}
		}

		return ErrorTypes.None;
	}
	
	public static UnitInfo GetUnitParts(UnitInfo unitInfo)
	{
		if (HCUnits.AllNonDividableUnits.contains(unitInfo.Unit))
		{
			unitInfo.Parts = new ArrayList<UnitPart>();
			unitInfo.Parts.add(new UnitPart(unitInfo.Unit, 1));
			
			return unitInfo;
		}

		unitInfo = ExpandUnitParts(unitInfo);
		if (unitInfo.Parts.size() > 1)
		{
			unitInfo = SimplifyUnitParts(unitInfo);
		}

		return UpdateInitialPositions(unitInfo);
	}
	
	public static UnitInfo ImproveUnitParts(UnitInfo unitInfo)
	{
		//GetUnitParts isn't just meant to get parts, but also to expand/simplify them.
		//That's why calling this method is one of the first steps when improving/analysing
		//a compound regardless of the fact that unit parts are already present.
		return MethodsCommon.GetUnitParts(unitInfo);
	}
	
	public static UnitInfo GetUnitFromParts(UnitInfo unitInfo)
	{
		unitInfo = GetIndividualUnitFromParts(unitInfo);

		return
		(
			unitInfo.Unit != Units.None ? unitInfo :
			GetCompoundUnitFromParts(unitInfo)
		);
	}
	
	static UnitInfo GetIndividualUnitFromParts(UnitInfo unitInfo)
	{
		if (unitInfo.Parts.size() == 1 && unitInfo.Parts.get(0).Exponent == 1.0)
		{
			if (HCMain.AllUnitConversionFactors.containsKey(unitInfo.Parts.get(0).getUnit()))
			{
				unitInfo.Unit = unitInfo.Parts.get(0).getUnit();
				unitInfo.Type = HCMain.AllUnitTypes.get(unitInfo.Unit);
				unitInfo.System = HCMain.AllUnitSystems.get(unitInfo.Unit);
				unitInfo = Managed.PerformManagedOperationUnits
				(
					unitInfo, unitInfo.Parts.get(0).getPrefix().getFactor(), Operations.Multiplication
				);
				unitInfo.Parts.get(0).setPrefix(new Prefix());
			}
		}

		return unitInfo;
	}
	
	public static UnitInfo UpdateInitialPositions(UnitInfo unitInfo)
	{
		if (unitInfo.Parts.size() < 1) return unitInfo;

		int newPos =
		(
			unitInfo.InitialPositions.size() > 0 ? Linq.Max
			(
				unitInfo.InitialPositions, new Comparator<Map.Entry<UnitPart, Integer>>()
				{
					public int compare(Map.Entry<UnitPart, Integer> first, Map.Entry<UnitPart, Integer> second)
					{
						return first.getValue().compareTo(second.getValue());
					}        				
				}
			)
			.getValue() : 0
		);

		//Some unit part modifications get a bit too messy and InitialPositions isn't always updated properly.
		//This is a simple and reliable way to account for eventual problems on this front.
		for (UnitPart part: unitInfo.Parts)
		{
			if (!unitInfo.InitialPositions.containsKey(part))
			{
				newPos = newPos + 1;
				unitInfo.InitialPositions.put(part, newPos);
			}
		}
		
		unitInfo.Parts = Linq.OrderBy
		(
			unitInfo.Parts, new Comparator<UnitPart>()
			{
				public int compare(UnitPart first, UnitPart second)
				{
					//Original C# code:
					//unitInfo.Parts.OrderBy
			        //(
					//     x => unitInfo.InitialPositions.First(y => y.Key == x).Value
					//)
					int result = Linq.FirstOrDefaultDict
					(
						unitInfo.InitialPositions, x -> x.getKey().equals(first), null
					)
					.getValue().compareTo
					(
						Linq.FirstOrDefaultDict
						(
								unitInfo.InitialPositions, x -> x.getKey().equals(second), null
						)
						.getValue()
					);

					return
					(
						result != 0 ? result ://Original C# code -> .ThenByDescending(x => x.Exponent)
						new Integer(second.Exponent).compareTo(first.Exponent)		
					);
				}        				
			}
		);
		
		return unitInfo;
	}
	
	static UnitInfo SimplifyUnitParts(UnitInfo unitInfo)
	{
		if (unitInfo.Parts.size() < 1) return unitInfo;

		//When having more than one part of the same type, a conversion (+ later removal) is performed.
		//By default, the part with the higher index is kept. That's why it is better to reorder the parts
		//such that the ones belonging to systems more likely to be preferred are kept.

		unitInfo.Parts = Linq.OrderBy
		(
			unitInfo.Parts, new Comparator<UnitPart>()
			{
				public int compare(UnitPart first, UnitPart second)
				{
					//Original C# code:
					//unitInfo.Parts.OrderBy
			        //(
					//	x => GetUnitSystem(x.Unit) == UnitSystems.None
					//)        			
					int result = new Boolean(UnitP.GetUnitSystem(first.getUnit()) == UnitSystems.None).compareTo
					(
						UnitP.GetUnitSystem(second.getUnit()) == UnitSystems.None
					);
					
					return
					(
						result != 0 ? result ://Original C# code -> .ThenBy(x => AllMetricEnglish[GetUnitSystem(x.Unit)] == UnitSystems.Imperial)
						new Boolean(HCMain.AllMetricEnglish.get(UnitP.GetUnitSystem(first.getUnit())) == UnitSystems.Imperial).compareTo
						(
							(HCMain.AllMetricEnglish.get(UnitP.GetUnitSystem(second.getUnit())) == UnitSystems.Imperial)
						)	
					);
				}        				
			}
		);

		for (int i = unitInfo.Parts.size() - 1; i >= 0; i--)
		{
			if (unitInfo.Parts.get(i).getUnit() == Units.None || unitInfo.Parts.get(i).getUnit() == Units.Unitless)
			{
				continue;
			}

			//Bear in mind that this point can also be reached while extracting the parts of a known unit.
			if (unitInfo.Unit == Units.None)
			{
				//Checking non-basic compounds is very quick (+ can avoid some of the subsequent analyses).
				//Additionally, some of these compounds wouldn't be detected in case of performing a full
				//simplification. For example: in Wh, all the time parts would be converted into hour or second
				//and, consequently, recognised as other energy unit (joule or unnamed one).
				unitInfo = GetNonBasicCompoundUnitFromParts(unitInfo);
				if (unitInfo.Type != UnitTypes.None)
				{
					return unitInfo;
				}
			}

			for (int i2 = i - 1; i2 >= 0; i2--)
			{
				boolean remove = false;
				if (unitInfo.Parts.get(i).getUnit() == unitInfo.Parts.get(i2).getUnit())
				{
					remove = true;
				}
				else
				{
					UnitInfo tempInfo = Conversions.AdaptUnitParts(unitInfo, i, i2);
					if (tempInfo != null)
					{
						remove = true;
						unitInfo = tempInfo;
					}
				}

				if (remove)
				{
					unitInfo = SimplifyUnitPartsRemove(unitInfo, i, i2);
				}

				if (unitInfo.Parts.size() == 0 || i > unitInfo.Parts.size() - 1)
				{
					i = unitInfo.Parts.size();
					break;
				}
			}
		}

		if (unitInfo.Parts.size() == 0) unitInfo.Unit = Units.Unitless;

		return unitInfo;
	}
   
	static UnitInfo SimplifyUnitPartsRemove(UnitInfo unitInfo, int i, int i2)
	{
		if (unitInfo.Parts.get(i).getPrefix().getFactor() == unitInfo.Parts.get(i2).getPrefix().getFactor())
		{
			unitInfo.Parts.get(i).Exponent += unitInfo.Parts.get(i2).Exponent;
		}
		else unitInfo = UpdateDifferentPrefixParts(unitInfo, i, i2);

		if (unitInfo.Parts.get(i).Exponent == 0)
		{
			unitInfo = RemoveUnitPart(unitInfo, unitInfo.Parts.get(i));
		}

		return RemoveUnitPart(unitInfo, unitInfo.Parts.get(i2));
	}
	
	static UnitInfo UpdateDifferentPrefixParts(UnitInfo unitInfo, int i, int i2)
	{
		//Example: m/cm is converted into 0.01 m/m where 0.01 isn't stored as a part but globally.
		UnitInfo newInfo = GetNewPrefixUnitPart(unitInfo, i, i2);
		newInfo = Managed.PerformManagedOperationUnits
		(
			newInfo, unitInfo.Prefix.getFactor(),
			Operations.Multiplication
		);

		unitInfo = Managed.PerformManagedOperationValues
		(
			unitInfo, newInfo, Operations.Multiplication
		);

		unitInfo.Parts.get(i).setPrefix(new Prefix(unitInfo.Parts.get(i).getPrefix().getPrefixUsage()));
		unitInfo.Parts.get(i).Exponent = unitInfo.Parts.get(i).Exponent + unitInfo.Parts.get(i2).Exponent;

		return unitInfo;
	}
	
	static UnitInfo GetNewPrefixUnitPart(UnitInfo unitInfo, int i, int i2)
	{
		UnitInfo info1 = Managed.RaiseToIntegerExponent
		(
			unitInfo.Parts.get(i).getPrefix().getFactor(),
			unitInfo.Parts.get(i).Exponent
		);

		UnitInfo info2 = Managed.RaiseToIntegerExponent
		(
			unitInfo.Parts.get(i2).getPrefix().getFactor(),
			unitInfo.Parts.get(i2).Exponent
		);

		return Managed.PerformManagedOperationUnits(info1, info2, Operations.Multiplication);
	}
	  
	public static UnitInfo UnitPartToUnitInfo(UnitPart unitPart)
	{
		return UnitPartToUnitInfo(unitPart, 1.0);
	}
	
	public static UnitInfo UnitPartToUnitInfo(UnitPart unitPart, double value)
	{
		Units unit = unitPart.getUnit();
		UnitInfo outUnitInfo = ExceptionInstantiation.NewUnitInfo(value, unit, unitPart.getPrefix());
		outUnitInfo = UpdateMainUnitVariables(outUnitInfo);

		UnitTypes type = GetTypeFromUnitPart(unitPart);

		if (GetTypeFromUnit(unit) != type)
		{
			HashMap<Units, UnitPart> potentials = new HashMap<Units, UnitPart>();
			for (Map.Entry<Units, UnitTypes> compoundUnit: Linq.WhereDict(HCMain.AllUnitTypes, x -> x.getValue().equals(type) && MethodsCommon.UnitIsNamedCompound(x.getKey())).entrySet())
			{
				//AllUnitTypes.Where(x => x.Value == type && UnitIsNamedCompound(x.Key)))
				Units unit2 = compoundUnit.getKey();

				int count = 0;
				while (count < 2)
				{
					count = count + 1;
					ArrayList<UnitPart> unitParts = GetCompoundUnitParts(unit2, count == 2);
					if (unitParts.size() == 1)
					{
						potentials.put(unit2, unitParts.get(0));
						break;
					}
				}
			}

			for (Map.Entry<Units, UnitPart> potential: potentials.entrySet())
			{
				if (MethodsCommon.UnitPartsAreEquivalent(potential.getValue(), unitPart))
				{
					outUnitInfo.Unit = potential.getKey();
					outUnitInfo = UpdateMainUnitVariables(outUnitInfo, true);
					return outUnitInfo;
				}
			}
		}

		return outUnitInfo;
	}
	
	public static boolean UnitIsNamedCompound(Units unit)
	{
		return
		(
			UnitIsNonBasicCompound(unit) ? true :
			UnitIsBasicCompound(unit)
		);
	}
	
	static boolean UnitIsBasicCompound(Units unit)
	{
		for (Map.Entry<UnitTypes, HashMap<UnitSystems, Units>> item: HCCompounds.AllBasicCompounds.entrySet())
		{
			if (item.getValue().containsValue(unit))
			{
				return true;
			}
		}

		return false;
	}

	static boolean UnitIsNonBasicCompound(Units unit)
	{
		return HCCompounds.AllNonBasicCompounds.containsKey(unit);
	}
	
	static boolean UnitPartsAreEquivalent(UnitPart unitPart1, UnitPart unitPart2)
	{
		if (unitPart1.getUnit() == unitPart2.getUnit() && unitPart1.getPrefix() == unitPart2.getPrefix())
		{
			if (Math.abs(unitPart1.Exponent) == Math.abs(unitPart2.Exponent))
			{
				//Exponent sign isn't too relevant in quite a few matching UnitPart scenarios.
				//For example: standalone kg and the one in L/kg.
				return true;
			}
		}

		return false;
	}
	
	static UnitInfo GetNonBasicCompoundUnitFromParts(UnitInfo unitInfo)
	{
		for (Map.Entry<Units, ArrayList<UnitPart>> compound: new HashMap<Units, ArrayList<UnitPart>>(HCCompounds.AllNonBasicCompounds).entrySet())
		{
			if (HCCompounds.NonBasicCompoundsToSkip.contains(compound.getKey()))
			{
				continue;
			}

			if (compound.getValue().size() == unitInfo.Parts.size())
			{
				ArrayList<UnitPart> targetParts = compound.getValue();
				if (UnitPartsMatchCompoundUnitParts(unitInfo, targetParts, true))
				{
					unitInfo = PopulateUnitRelatedInfo(unitInfo, compound.getKey());
					if (!UnitPartsMatchCompoundUnitParts(unitInfo, targetParts))
					{
						//Some prefixes differ from the basic configuration.
						unitInfo = AdaptPrefixesToMatchBasicCompound(unitInfo, targetParts);
					}

					return unitInfo;
				}
			}
		}

		return unitInfo;
	}
	
	static UnitInfo AdaptPrefixesToMatchBasicCompound(UnitInfo unitInfo, ArrayList<UnitPart> basicUnitParts)
	{
		UnitInfo allPrefixes = ExceptionInstantiation.NewUnitInfo(1.0);
		HashMap<Units, Double> basicPrefixes = (HashMap<Units, Double>) basicUnitParts.stream().collect
		(
			Collectors.toMap(x -> x.getUnit(), x -> x.getPrefix().getFactor())
		);
		
		for (UnitPart part: unitInfo.Parts)
		{
			if (Linq.FirstOrDefault(basicUnitParts, x -> x.equals(part), null) == null)
			{
				UnitInfo prefixRem = Managed.PerformManagedOperationValues
				(
					part.getPrefix().getFactor(), basicPrefixes.get(part.getUnit()), Operations.Division
				);

				prefixRem = Managed.RaiseToIntegerExponent(prefixRem, part.Exponent);

				allPrefixes = Managed.PerformManagedOperationValues
				(
					allPrefixes, prefixRem, Operations.Multiplication
				);

				part.setPrefix(new Prefix(basicPrefixes.get(part.getUnit())));
			}
		}

		if (allPrefixes.Value != 1.0) 
		{
			unitInfo = Managed.PerformManagedOperationUnits
			(
				unitInfo, allPrefixes, Operations.Multiplication
			);
		}

		return unitInfo;
	}
	
	static UnitInfo PopulateUnitRelatedInfo(UnitInfo unitInfo, Units unit)
	{
		unitInfo.Unit = unit;

		if (unitInfo.Unit != Units.None && unitInfo.Unit != Units.Unitless && !IsUnnamedUnit(unitInfo.Unit))
		{
			unitInfo.Type = HCMain.AllUnitTypes.get(unitInfo.Unit);
			unitInfo.System = HCMain.AllUnitSystems.get(unitInfo.Unit);
		}

		return unitInfo;
	}
	
	static boolean UnitPartsMatchCompoundUnitParts(UnitInfo unitInfo, ArrayList<UnitPart> basicUnitParts)
	{
		return UnitPartsMatchCompoundUnitParts(unitInfo, basicUnitParts, false);
	}
	
	static boolean UnitPartsMatchCompoundUnitParts(UnitInfo unitInfo, ArrayList<UnitPart> basicUnitParts, boolean noPrefixes)
	{
		for (UnitPart basic: basicUnitParts)
		{
			if (noPrefixes)
			{
				if (Linq.FirstOrDefault(unitInfo.Parts, x -> x.getUnit().equals(basic.getUnit()) && x.Exponent.equals(basic.Exponent), null) == null)
				{
					return false;
				}
			}
			else if (Linq.FirstOrDefault(unitInfo.Parts, x -> x.equals(basic), null) == null)
			{
				return false;
			}
		}

		return true;
	}
	
	static UnitInfo ExpandUnitParts(UnitInfo unitInfo)
	{
		if (unitInfo.Parts.size() == 0) unitInfo = GetPartsFromUnit(unitInfo);

		if (unitInfo.InitialPositions == null || unitInfo.InitialPositions.size() == 0)
		{
			unitInfo.InitialPositions = GetInitialPositions(unitInfo.Parts);
		}

		for (int i = unitInfo.Parts.size() - 1; i >= 0; i--)
		{
			UnitInfo infoPart = ExceptionInstantiation.NewUnitInfo
			(
				0.0, unitInfo.Parts.get(i).getUnit(),
				unitInfo.Parts.get(i).getPrefix(), false
			);

			if (IsDividable(infoPart))
			{
				unitInfo = AddCompoundParts(unitInfo, infoPart, i);
			}
		}

		return unitInfo;
	} 
	
	static UnitInfo AddCompoundParts(UnitInfo unitInfo, UnitInfo partInfo, int i)
	{
		return
		(
			HCCompounds.AllNonBasicCompounds.containsKey(partInfo.Unit) ?
			ExpandNonBasicCompoundToUnitPart(unitInfo, partInfo, i) :
			ExpandBasicCompoundToUnitPart(unitInfo, partInfo, i)
		);
	}
	
	static UnitInfo ExpandBasicCompoundToUnitPart(UnitInfo unitInfo, UnitInfo partInfo, int i)
	{
		UnitTypes nonDividableType =
		(
			HCUnits.AllNonDividableUnits.contains(unitInfo.Parts.get(i).getUnit()) ?
			partInfo.Type : UnitTypes.None
		);

		for (int i2 = 0; i2 < HCCompounds.AllCompounds.get(partInfo.Type).size(); i2++)
		{
			Compound compound = HCCompounds.AllCompounds.get(partInfo.Type).get(i2);
			if (HCCompounds.AllCompounds.get(partInfo.Type).get(i2).Parts.size() < 2)
			{
				//The whole point here is expanding.
				continue;
			}

			if (nonDividableType == UnitTypes.None || Linq.FirstOrDefault(compound.Parts, x -> x.Type.equals(nonDividableType), null) != null)
			{
				unitInfo = AddExpandedUnitPart
				(
					unitInfo, i, GetUnitPartsFromBasicCompound
					(
						HCCompounds.AllCompounds.get(partInfo.Type).get(i2),
						HCMain.AllBasicSystems.get(unitInfo.System)
					)
				);
				break;
			}
		}

		return unitInfo;
	}
	
	static UnitInfo AddExpandedUnitPart(UnitInfo unitInfo, int i, ArrayList<UnitPart> compoundUnitParts)
	{
		//The prefix of the original unit is added to the first unit part.
		boolean firstTime = true;
		ArrayList<UnitPart> newParts = new ArrayList<UnitPart>();

		for (UnitPart part: compoundUnitParts)
		{
			UnitInfo newPrefixInfo = ExceptionInstantiation.NewUnitInfo(Units.None, part.getPrefix().getFactor());

			if (firstTime && unitInfo.Parts.get(i).getPrefix().getFactor() != 1.0)
			{
				//Finding the most adequate new prefix isn't required at this point.
				newPrefixInfo = Managed.PerformManagedOperationValues
				(
					unitInfo.Parts.get(i).getPrefix().getFactor(), part.getPrefix().getFactor(),
					Operations.Multiplication
				);

				if (newPrefixInfo.Value != 1 || newPrefixInfo.BaseTenExponent != 0)
				{
					unitInfo = Managed.PerformManagedOperationUnits
					(
						unitInfo, newPrefixInfo, Operations.Multiplication
					);
				}
			}

			newParts.add
			(
				UnitPartInternal.NewUnitPart
				(
					part.getUnit(), newPrefixInfo.Prefix.getFactor(),
					part.Exponent * unitInfo.Parts.get(i).Exponent
				)
			);

			firstTime = false;
		}

		return AddNewUnitParts(unitInfo, newParts, i);
	}
	
	public static UnitInfo AddNewUnitParts(UnitInfo unitInfo, ArrayList<UnitPart> newParts)
	{
		return AddNewUnitParts(unitInfo, newParts, -1);
	}
	
	static UnitInfo AddNewUnitParts(UnitInfo unitInfo, ArrayList<UnitPart> newParts, int i)
	{
		if (newParts.size() < 1) return unitInfo;

		int i2 = i;
		if (i2 == -1)
		{
			i2 =
			(
				unitInfo.InitialPositions.size() == 0 ? 0 : Linq.Max
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

		for (UnitPart part: newParts)
		{
			unitInfo.Parts.add(part);
			if (!unitInfo.InitialPositions.containsKey(part))
			{
				unitInfo.InitialPositions.put(part, i2);
			}
		}

		return
		(
			i == -1 ? unitInfo : //i == -1 means that old parts aren't being replaced.
			RemoveUnitPart(unitInfo, unitInfo.Parts.get(i))
		);
	}   
	
	static UnitInfo RemoveUnitPart(UnitInfo unitInfo, UnitPart part)
	{
		unitInfo.InitialPositions.remove(part);
		unitInfo.Parts.remove(part);
		
		return unitInfo;
	}
	
	static UnitInfo ExpandNonBasicCompoundToUnitPart(UnitInfo unitInfo, UnitInfo partInfo, int i)
	{
		return AddExpandedUnitPart
		(
			unitInfo, i,
			new ArrayList<UnitPart>(HCCompounds.AllNonBasicCompounds.get(partInfo.Unit))
		);
	}
	
	public static UnitInfo GetPartsFromUnit(UnitInfo unitInfo)
	{
		if (unitInfo.Unit == Units.None || unitInfo.Unit == Units.Unitless || IsUnnamedUnit(unitInfo.Unit))
		{
			return unitInfo;
		}
		
		unitInfo = GetPartsFromUnitCompound(unitInfo);
	   
		if (unitInfo.Parts.size() < 1)
		{
			unitInfo.Parts = new ArrayList<UnitPart>();
			unitInfo.Parts.add(new UnitPart(unitInfo.Unit, 1));
		}

		return unitInfo;
	}
	
	static UnitInfo GetPartsFromUnitCompound(UnitInfo unitInfo)
	{
		//Always better to start compound analyses with the more-to-the-point non-basic ones.
		unitInfo = GetPartsFromUnitCompoundNonBasic(ExceptionInstantiation.NewUnitInfo(unitInfo));
		if (unitInfo.Parts.size() > 0) return unitInfo;

		if (unitInfo.Type == UnitTypes.None)
		{
			unitInfo.Type = GetTypeFromUnitInfo(unitInfo);
		}
		if (unitInfo.System == UnitSystems.None)
		{
			unitInfo.System = GetSystemFromUnit(unitInfo.Unit);
		}

		return
		(
			GetBasicCompoundUnitInfo(unitInfo).Unit == unitInfo.Unit ?
			GetPartsFromUnitCompoundBasic(unitInfo) :
			unitInfo
		);
	}

	static UnitInfo GetPartsFromUnitCompoundBasic(UnitInfo unitInfo)
	{
		if (unitInfo.System == UnitSystems.None || !HCCompounds.AllCompounds.containsKey(unitInfo.Type))
		{
			return unitInfo;
		}

		unitInfo.Parts.addAll
		(
			GetUnitPartsFromBasicCompound
			(
				HCCompounds.AllCompounds.get(unitInfo.Type).get(0), unitInfo.System
			)
		);

		return unitInfo;
	}

	static UnitInfo GetPartsFromUnitCompoundNonBasic(UnitInfo unitInfo)
	{
		if (HCCompounds.AllNonBasicCompounds.containsKey(unitInfo.Unit))
		{
			unitInfo.Parts.addAll(new ArrayList<UnitPart>(HCCompounds.AllNonBasicCompounds.get(unitInfo.Unit)));
		}

		return unitInfo;
	}
	
	public static UnitInfo GetCompoundUnitFromParts(UnitInfo unitInfo)
	{
		//Better starting with the quicker non-basic-compound check.
		unitInfo = GetNonBasicCompoundUnitFromParts(unitInfo);

		return
		(
			unitInfo.Unit != Units.None ? unitInfo :
			GetBasicCompoundUnitInfo(unitInfo)
		);
	}
	
	static UnitInfo GetBasicCompoundUnitInfo(UnitInfo unitInfo)
	{
		if (unitInfo.Parts.size() < 1) return unitInfo;

		if (unitInfo.Type == UnitTypes.None)
		{
			unitInfo.Type = GetTypeFromUnitInfo(unitInfo);
		}

		return
		(
			unitInfo.Type == UnitTypes.None ? unitInfo :
			GetBasicCompoundUnit(unitInfo)
		);
	}

	static UnitInfo GetBasicCompoundUnit(UnitInfo unitInfo)
	{
		if (!HCCompounds.AllBasicCompounds.containsKey(unitInfo.Type))
		{
			return unitInfo;
		}

		unitInfo.Unit = HCMain.DefaultUnnamedUnits.get(unitInfo.System);

		UnitSystems system2 = unitInfo.System;
		if (system2 == UnitSystems.None)
		{
			system2 = UnitSystems.SI;
		}

		Units basicCompound =
		(
			HCCompounds.AllBasicCompounds.get(unitInfo.Type).containsKey(system2) ?
			HCCompounds.AllBasicCompounds.get(unitInfo.Type).get(system2) :
			Units.None
		);

		if (basicCompound == Units.None) return unitInfo;

		UnitSystems basicSystem = HCMain.AllBasicSystems.get(system2);
		ArrayList<UnitPart> basicUnitParts = GetBasicCompoundUnitParts(unitInfo.Type, basicSystem);
		if (basicUnitParts.size() != unitInfo.Parts.size()) return unitInfo;

		if (UnitPartsMatchCompoundUnitParts(unitInfo, basicUnitParts, true))
		{
			unitInfo = PopulateUnitRelatedInfo(unitInfo, basicCompound);
			if (!UnitPartsMatchCompoundUnitParts(unitInfo, basicUnitParts))
			{
				//Some prefixes differ from the basic configuration.
				unitInfo = AdaptPrefixesToMatchBasicCompound(unitInfo, basicUnitParts);
			}
		}

		return unitInfo;
	}
	
	static boolean IsDividable(UnitInfo unitInfo)
	{
		unitInfo.Type = GetTypeFromUnitInfo(unitInfo);

		return
		(
			!HCUnits.AllNonDividableUnits.contains(unitInfo.Unit) &&
			(
				HCCompounds.AllCompounds.containsKey(unitInfo.Type) ||
				HCCompounds.AllNonBasicCompounds.containsKey(unitInfo.Unit)
			)
		);
	}
	
	public static boolean IsCompoundDescriptive(char bit)
	{
		return IsCompoundDescriptive(bit, false);
	} 
	
	public static boolean IsCompoundDescriptive(char bit, boolean ignoreNumbers)
	{
		return
		(
			(!ignoreNumbers && Character.isDigit(bit)) || bit == '-' ||
			OperationsOther.OperationSymbols.get(Operations.Multiplication).contains(bit) ||
			OperationsOther.OperationSymbols.get(Operations.Division).contains(bit)
		);
	}
	
	public static boolean PrefixCanBeUsedWithUnitBasicCheck(UnitInfo unitInfo, PrefixTypes prefixType)
	{
		if (unitInfo.Prefix.getPrefixUsage() == PrefixUsageTypes.AllUnits) return true;

		if (prefixType == PrefixTypes.SI)
		{
			if (HCPrefixes.AllOtherSIPrefixUnits.contains(unitInfo.Unit)) return true;

			UnitSystems system =
			(
				unitInfo.System == UnitSystems.None ?
				GetSystemFromUnitInfo(unitInfo) :
				unitInfo.System
			);

			return (system == UnitSystems.SI || system == UnitSystems.CGS);
		}
		else if (prefixType == PrefixTypes.Binary)
		{
			UnitTypes type =
			(
				unitInfo.Type == UnitTypes.None ?
				GetTypeFromUnitInfo(unitInfo) :
				unitInfo.Type
			);
			return HCPrefixes.AllBinaryPrefixTypes.contains(type);
		}

		return false;
	}
	
	static UnitInfo GetBestPrefixForTarget(UnitInfo unitInfo, int targetExponent, PrefixTypes prefixType)
	{
		return GetBestPrefixForTarget
		(
			unitInfo, targetExponent, prefixType, false
		);
	}
	
	//This method assumes a normalised UnitInfo variable (i.e., without prefixes).
	static UnitInfo GetBestPrefixForTarget(UnitInfo unitInfo, int targetExponent, PrefixTypes prefixType, boolean modifyBaseTenExponent)
	{
		if (targetExponent == 0 || prefixType == PrefixTypes.None) return unitInfo;

		if (targetExponent > 24) targetExponent = 24;
		else if(targetExponent < -24) targetExponent = -24;

		UnitInfo targetInfo = Managed.RaiseToIntegerExponent(10, targetExponent);

		for (double prefixFactor: GetAllPrefixFactors(targetExponent, prefixType))
		{
			if ((targetExponent > 0 && targetInfo.Value >= prefixFactor) || (targetExponent < 0 && targetInfo.Value <= prefixFactor))
			{
				unitInfo = IncludeRemainingTargetPrefix
				(
					unitInfo, targetInfo, ExceptionInstantiation.NewUnitInfo(prefixFactor)
				);
				unitInfo.Prefix = new Prefix(prefixFactor, unitInfo.Prefix.getPrefixUsage());
				break;
			}
		}

		return
		(
			modifyBaseTenExponent ?
			Managed.VaryBaseTenExponent(unitInfo, -targetExponent) :
			unitInfo
		);
	}   

	static UnitInfo IncludeRemainingTargetPrefix(UnitInfo unitInfo, UnitInfo expectedTarget, UnitInfo actualTarget)
	{
		UnitInfo remInfo = Managed.NormaliseUnitInfo
		(
			Managed.PerformManagedOperationValues
			(
				expectedTarget, actualTarget, Operations.Division
			)
		);

		return Managed.PerformManagedOperationValues(unitInfo, remInfo, Operations.Multiplication);
	}
	
	static ArrayList<Double> GetAllPrefixFactors(int targetExponent, PrefixTypes prefixType)
	{
		ArrayList<Double> allPrefixes = null;

		InitialiseBigSmallPrefixValues(prefixType);
		if (prefixType == PrefixTypes.SI)
		{
			allPrefixes =
			(
				targetExponent > 0 ? HCPrefixes.BigSIPrefixValues : HCPrefixes.SmallSIPrefixValues
			);
		}
		else
		{
			allPrefixes =
			(
				targetExponent > 0 ? HCPrefixes.BigBinaryPrefixValues : HCPrefixes.SmallBinaryPrefixValues
			);
		}
		
		return allPrefixes;
	}
	
	static void InitialiseBigSmallPrefixValues(PrefixTypes prefixType)
	{
		if (prefixType == PrefixTypes.SI)
		{
			if (HCPrefixes.BigSIPrefixValues == null)
			{
				HCPrefixes.BigSIPrefixValues = Linq.OrderByDescending
				(
					Linq.SelectDict
					(
						Linq.WhereDict
						(
							HCPrefixes.AllSIPrefixes, x -> new Integer(x.getValue().compareTo(1.0)).equals(1)
						), 
						x -> x.getValue()
					), 
					new Comparator<Double>()
					{
						public int compare(Double first, Double second)
						{
							return first.compareTo(second);
						}        				
					}
				);
			}
			if (HCPrefixes.SmallSIPrefixValues == null)
			{
				HCPrefixes.SmallSIPrefixValues = Linq.OrderBy
				(
					Linq.SelectDict
					(
						Linq.WhereDict
						(
							HCPrefixes.AllSIPrefixes, x -> new Integer(x.getValue().compareTo(1.0)).equals(-1)
						), 
						x -> x.getValue()
					), 
					new Comparator<Double>()
					{
						public int compare(Double first, Double second)
						{
							return first.compareTo(second);
						}        				
					}
				);                
			}
		}
		else if(prefixType == PrefixTypes.Binary)
		{
			if (HCPrefixes.BigBinaryPrefixValues == null)
			{
				HCPrefixes.BigBinaryPrefixValues = Linq.OrderByDescending
				(
					Linq.SelectDict
					(
						Linq.WhereDict
						(
							HCPrefixes.AllBinaryPrefixes, x -> new Integer(x.getValue().compareTo(1.0)).equals(1)
						), 
						x -> x.getValue()
					), 
					new Comparator<Double>()
					{
						public int compare(Double first, Double second)
						{
							return first.compareTo(second);
						}        				
					}
				);             	
			}
			if (HCPrefixes.SmallSIPrefixValues == null)
			{
				HCPrefixes.SmallBinaryPrefixValues = Linq.OrderBy
				(
					Linq.SelectDict
					(
						Linq.WhereDict
						(
							HCPrefixes.AllBinaryPrefixes, x -> new Integer(x.getValue().compareTo(1.0)).equals(-1)
						), 
						x -> x.getValue()
					), 
					new Comparator<Double>()
					{
						public int compare(Double first, Double second)
						{
							return first.compareTo(second);
						}        				
					}
				);             	
			}
		}
	}
	
	//Called before starting unit conversions triggered by public methods.
	public static ErrorTypes PrelimaryErrorCheckConversion(UnitP original, UnitInfo targetInfo)
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
	
	public static UnitInfo InverseUnit(UnitInfo unitInfo)
	{
		if (unitInfo.Unit == Units.Unitless || unitInfo.Unit == Units.None || unitInfo.Parts.size() == 0)
		{
			return unitInfo;
		}
		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo(unitInfo);

		for (int i = 0; i < outInfo.Parts.size(); i++)
		{
			outInfo.Parts.set
			(
				i, UnitPartInternal.NewUnitPart
				(
					outInfo.Parts.get(i).getUnit(), outInfo.Parts.get(i).getPrefix().getFactor(),
					-1 * outInfo.Parts.get(i).Exponent
				)
			);
		}

		return outInfo;
	}
}
