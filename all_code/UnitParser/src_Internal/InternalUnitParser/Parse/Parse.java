package InternalUnitParser.Parse;

import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Classes.*;
import InternalUnitParser.Hardcoding.*;
import InternalUnitParser.Methods.*;
import InternalUnitParser.Operations.*;
import UnitParser.*;
import UnitParser.UnitP.*;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Parse
{
	public static ParseInfo StartUnitParse(ParseInfo parseInfo)
	{
		parseInfo = InitialParseActions(parseInfo);
		return
		(
			StringCanBeCompound(parseInfo.InputToParse) ?
			StartCompoundParse(parseInfo) :
			StartIndividualUnitParse(parseInfo)
		);
	}  
	
	static ParseInfo InitialParseActions(ParseInfo parseInfo)
	{
		if (parseInfo.InputToParse == null) parseInfo.InputToParse = "";
		parseInfo.InputToParse = parseInfo.InputToParse.trim();

		if (parseInfo.InputToParse.length() < 1)
		{
			if (parseInfo.UnitInfo.Error.getType() == ErrorTypes.None)
			{
				parseInfo.UnitInfo.Error = ExceptionInstantiation.NewErrorInfo(ErrorTypes.InvalidUnit);
			}

			return parseInfo;
		}

		for (String ignored: OperationsOther.UnitParseIgnored)
		{
			parseInfo.InputToParse = parseInfo.InputToParse.replace(ignored, "");
		}

		parseInfo.UnitInfo = MethodsCommon.RemoveAllUnitInformation(parseInfo.UnitInfo, true);

		return parseInfo;
	}

	static UnitInfo PopulateUnitRelatedInfo(UnitInfo unitInfo, Units unit)
	{
		unitInfo.Unit = unit;

		if (unitInfo.Unit != Units.None && unitInfo.Unit != Units.Unitless && !MethodsCommon.IsUnnamedUnit(unitInfo.Unit))
		{
			unitInfo.Type = HCMain.AllUnitTypes.get(unitInfo.Unit);
			unitInfo.System = HCMain.AllUnitSystems.get(unitInfo.Unit);
		}

		return unitInfo;
	}

	public static UnitInfo UpdateMainUnitVariables(UnitInfo unitInfo)
	{
		return UpdateMainUnitVariables(unitInfo, false);
	}
	
	static UnitInfo UpdateMainUnitVariables(UnitInfo unitInfo, boolean recalculateAlways)
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
			unitInfo.Type = MethodsCommon.GetTypeFromUnitInfo(unitInfo);
		}

		if (unitInfo.System == UnitSystems.None)
		{
			unitInfo.System = MethodsCommon.GetSystemFromUnitInfo(unitInfo);
		}

		return unitInfo;
	}

	public static UnitInfo ParseDecimal(String stringToParse)
	{
		TryParseOutput outVar = TryParseMethods.Double(stringToParse);
		
		if (outVar.IsOK)
		{
			//In some cases, double.TryParse might consider valid numbers beyond the actual scope of
			//double type. For example: 0.00000000000000000000000000000001m assumed to be zero.
			if (outVar.DoubleVal != 0.0) 
			{
				return ExceptionInstantiation.NewUnitInfo(outVar.DoubleVal);
			}
		}

		return ParseDouble(stringToParse);
	}

	static UnitInfo ParseDouble(String stringToParse)
	{
		TryParseOutput outVar = TryParseMethods.Double(stringToParse);

		return
		(
			//Bear in mind that double.TryParse might misunderstand very small numbers, that's why 0.0 is treated as an error.
			//GetInfoBeyondDouble will undoubtedly determine whether it is a real zero or not.
			outVar.IsOK && outVar.DoubleVal != 0.0 ?
			ExceptionInstantiation.NewUnitInfo(outVar.DoubleVal) : 
			GetInfoBeyondDouble(stringToParse)
		);
	}
	
	//This method complements .NET numeric-type parsing methods beyond the double range (i.e., > 1e300).
	static UnitInfo GetInfoBeyondDouble(String stringToParse)
	{
		stringToParse = stringToParse.toLowerCase();
		UnitInfo errorInfo = ExceptionInstantiation.NewUnitInfo(ErrorTypes.NumericError);
		
		TryParseOutput tempVar = null;
		
		//The double-parsing .NET format is expected. That is: only integer exponents after a
		//letter "e", what indicates before-e * 10^after-e.
		if (stringToParse.contains("e"))
		{
			String[] temp = CSharpOther.SplitTryCatch(stringToParse, "e");
			if (temp.length == 2)
			{
				if (temp[0].contains("e")) return errorInfo;
				
				UnitInfo outInfo = ParseDouble(temp[0]);
				if (outInfo.Error.getType() != ErrorTypes.None)
				{
					return errorInfo;
				}

				tempVar = TryParseMethods.Int(temp[1]);
				if (tempVar.IsOK)
				{
					outInfo.BaseTenExponent += tempVar.IntVal;
					return outInfo;
				}
			}
		}
		else
		{
			if (stringToParse.length() < 300)
			{
				tempVar = TryParseMethods.Int(stringToParse);
				if (tempVar.IsOK)
				{
					return ExceptionInstantiation.NewUnitInfo(tempVar.DoubleVal);
				}
			}
			else
			{
				tempVar = TryParseMethods.Double
				(
					CSharpOther.Substring
					(
						stringToParse, 0, 299
					)
				);

				if (tempVar.IsOK)
				{
					String remString = stringToParse.substring(299);
					if (Linq.FirstOrDefault(CSharpOther.StringToCharacters(remString), x -> Character.isDigit(x) && !x.equals(','), '\u0000') != '\u0000')
					{
						//Finding a double separator here is considered an error because it wouldn't
						//be too logical (300 digits before the double separator!). Mainly by bearing
						//in mind the exponential alternative above.
						return errorInfo;
					}
					
					int beyondCount = GetBeyondDoubleCharacterCount(stringToParse.substring(299));
					if (beyondCount < 0) return errorInfo;

					//Accounting for the differences 0.001/1000 -> 10^-3/10^3.
					int sign = (Math.abs(tempVar.DoubleVal) < 1.0 ? -1 : 1);
					if (tempVar.DoubleVal == 0.0 && sign == -1)
					{
						//This code accounts for situations like 0.00000[...]00001 where, for the aforementioned double.TryParse, startNumber is zero.
						boolean found = false;
						int length2 = (remString.length() > 299 ? 299 : remString.length());
						for (int i = 0; i < remString.length(); i++)
						{
							String bit = CSharpOther.Substring
							(
								remString, i, 1
							);
							if (bit != "0" && bit != ",")
							{
								//The default interpretation is initial_part*10^remString.size() (up to the maximum length natively supported by double). 
								//For example, 0.0000012345[...]4565879561424 is correctly understood as 0.0000012345*10^length-after-[...]. In this specific 
								//situation (i.e., initial_part understood as zero), some digits after [...] have to also be considered to form initial_part. 
								//Thus, startNumber is being redefined as all the digits (up to the maximum length natively supported by double) after the 
								//first non-zero one; and beyondCount (i.e., the associated 10-base exponent) such that it also includes all the digits since the start.
								found = true;
								tempVar.DoubleVal = Double.parseDouble
								(
									CSharpOther.Substring
									(
										remString, i, length2 - i
									)
								);
								beyondCount = 297 + length2;
								break;
							}
						}

						if (!found) return ExceptionInstantiation.NewUnitInfo(0.0);
					}

					return Managed.VaryBaseTenExponent
					(
						ExceptionInstantiation.NewUnitInfo(tempVar.DoubleVal), sign * beyondCount
					);
				}
			}
		}

		return errorInfo;
	}

	static int GetBeyondDoubleCharacterCount(String remString)
	{
		int outCount = 0;

		try
		{
			for (char item: remString.toCharArray())
			{
				if (!Character.isDigit(item) && item != ',')
				{
					return 0;
				}
				outCount = outCount + 1;
			}
		}
		catch(Exception e)
		{
			//The really unlikely scenario of hitting int.MaxValue.
			outCount = -1;
		}

		return outCount;
	}
	
	static ParseInfo StartIndividualUnitParse(ParseInfo parseInfo)
	{
		return GetIndividualUnitParts
		(
			ParseIndividualUnit(parseInfo)
		);
	}

	static ParseInfo ParseIndividualUnit(ParseInfo parseInfo)
	{
		parseInfo = PrefixAnalysis(parseInfo);
		
		if (parseInfo.UnitInfo.Unit == Units.None)
		{
			//The final unit might have already been found while analysing prefixes.
			parseInfo.UnitInfo.Unit = MethodsCommon.GetUnitFromString(parseInfo.InputToParse);
		}

		parseInfo.UnitInfo = UpdateMainUnitVariables(parseInfo.UnitInfo);

		return parseInfo;
	}

	static ParseInfo PrefixAnalysis(ParseInfo parseInfo)
	{
		parseInfo.UnitInfo.Unit = MethodsCommon.GetUnitFromString(parseInfo.InputToParse, parseInfo);

		return 
		(
			parseInfo.UnitInfo.Unit == Units.None ?
			CheckPrefixes(parseInfo) : parseInfo
		);
	}

	public static ParseInfo CheckPrefixes(ParseInfo parseInfo)
	{
		if (parseInfo.InputToParse.length() < 2) return parseInfo;

		String origString = parseInfo.InputToParse;

		parseInfo.UnitInfo.Prefix = new Prefix
		(
			parseInfo.UnitInfo.Prefix.getPrefixUsage()
		);

		ParseInfo tempSI = CheckSIPrefixes(new ParseInfo(parseInfo));
		ParseInfo tempBinary = CheckBinaryPrefixes(new ParseInfo(parseInfo));

		if (tempSI.UnitInfo.Prefix.getFactor() != 1.0)
		{
			if (tempBinary.UnitInfo.Prefix.getFactor() != 1.0)
			{
				//Both SI and binary prefixes were detected, what is an error.
				parseInfo = new ParseInfo();
				parseInfo.InputToParse = origString;
				parseInfo.UnitInfo.Error = ExceptionInstantiation.NewErrorInfo(ErrorTypes.InvalidUnit);
			}
			else parseInfo = new ParseInfo(tempSI);
		}
		else if (tempBinary.UnitInfo.Prefix.getFactor() != 1.0)
		{
			parseInfo = new ParseInfo(tempBinary);
		}

		return parseInfo;
	}
	
	static ParseInfo CheckBinaryPrefixes(ParseInfo parseInfo)
	{
		InitialisePrefixNames(PrefixTypes.Binary);

		return CheckPrefixes
		(
			parseInfo, PrefixTypes.Binary,
			HCPrefixes.AllBinaryPrefixSymbols, HCPrefixes.AllBinaryPrefixNames
		);
	}

	static ParseInfo CheckSIPrefixes(ParseInfo parseInfo)
	{
		InitialisePrefixNames(PrefixTypes.SI);

		return CheckPrefixes
		(
			parseInfo, PrefixTypes.SI,
			HCPrefixes.AllSIPrefixSymbols, HCPrefixes.AllSIPrefixNames
		);
	}
	
	static void InitialisePrefixNames(PrefixTypes prefixType)
	{
		if (prefixType == PrefixTypes.SI)
		{
			if (HCPrefixes.AllSIPrefixNames == null)
			{
				HCPrefixes.AllSIPrefixNames = (HashMap<String, String>)HCPrefixes.AllSIPrefixSymbols.entrySet().stream().collect
				(
					Collectors.toMap
					(
						x -> x.getKey(), x -> Linq.FirstOrDefaultDict
						(
							HCPrefixes.AllSIPrefixes, y -> y.getValue().equals(x.getValue()), 
							new AbstractMap.SimpleEntry<SIPrefixes, Double>(SIPrefixes.None, 0.0)
						) 
						.getKey().toString().toLowerCase()
					)
				);
			}
		}
		else if (prefixType == PrefixTypes.Binary)
		{
			if (HCPrefixes.AllBinaryPrefixNames == null)
			{
				HCPrefixes.AllBinaryPrefixNames = (HashMap<String, String>)HCPrefixes.AllBinaryPrefixSymbols.entrySet().stream().collect
				(
					Collectors.toMap
					(
						x -> x.getKey(), x -> Linq.FirstOrDefaultDict
						(
							HCPrefixes.AllBinaryPrefixes, y -> y.getValue().equals(x.getValue()), 
							new AbstractMap.SimpleEntry<BinaryPrefixes, Double>(BinaryPrefixes.None, 0.0)
						) 
						.getKey().toString().toLowerCase()
					)
				);
			}
		}
	}
	
	static ParseInfo CheckPrefixes(ParseInfo parseInfo, PrefixTypes prefixType, HashMap<String, Double> allPrefixes, HashMap<String, String> allPrefixNames)
	{
		for (Map.Entry<String, Double> prefix: allPrefixes.entrySet())
		{
			String remString = GetPrefixRemaining
			(
				parseInfo.InputToParse, allPrefixNames.get(prefix.getKey()), prefix.getKey()
			);
			if (remString == "") continue;

			ParseInfo parseInfo2 = AnalysePrefix
			(
				parseInfo, prefixType, prefix, remString
			);
			if (parseInfo2.UnitInfo.Unit != Units.None)
			{
				return parseInfo2;
			}
		}

		return parseInfo;
	}

	static String GetPrefixRemaining(String input, String prefixName, String prefixSymbol)
	{
		String remString = GetPrefixRemainingSpecial
		(
			input, prefixName, prefixSymbol
		);
		if (remString != "") return remString;

		if (input.toLowerCase().startsWith(prefixName))
		{
			//String representation. Case doesn't matter.
			remString = input.substring(prefixName.length());
		}
		else if (input.startsWith(prefixSymbol))
		{
			//Symbol. Caps matter.
			remString = input.substring(prefixSymbol.length());
		}

		return remString;
	}

	//Accounts for cases where the default check (i.e., firstly name and then symbol) fails.
	static String GetPrefixRemainingSpecial(String input, String prefixName, String prefixSymbol)
	{
		if (input.startsWith(prefixSymbol))
		{
			if (input.toLowerCase().startsWith(prefixSymbol.toLowerCase() + "bit"))
			{
				//For example: Kibit which might be misunderstood as kibi prefix (name check) and t unit.
				return "bit";
			}
		}

		return "";
	}

	static ParseInfo AnalysePrefix(ParseInfo parseInfo, PrefixTypes prefixType, Map.Entry<String, Double> prefix, String remString)
	{
		Units unit = MethodsCommon.GetUnitFromString(remString);

		if (unit != Units.None)
		{
			parseInfo.UnitInfo.Unit = unit;

			if (MethodsCommon.PrefixCanBeUsedWithUnitBasicCheck(parseInfo.UnitInfo, prefixType))
			{
				parseInfo.UnitInfo = UpdateUnitInformation
				(
					parseInfo.UnitInfo, unit, new Prefix
					(
						prefix.getValue(), parseInfo.UnitInfo.Prefix.getPrefixUsage()
					)
				);

				//Useful in case of looking for further prefixes.
				parseInfo.InputToParse = remString;
			}
		}

		return parseInfo;
	}

	static UnitInfo UpdateUnitInformation(UnitInfo unitInfo, Units unit, Prefix prefix)
	{
		unitInfo.Unit = unit;
		unitInfo.Prefix = new Prefix(prefix);
		unitInfo.Parts = MethodsCommon.GetUnitParts(unitInfo).Parts;

		return unitInfo;
	}

	static ParseInfo GetIndividualUnitParts(ParseInfo parseInfo)
	{
		if (parseInfo.UnitInfo.Unit == Units.None || parseInfo.UnitInfo.Error.getType() != ErrorTypes.None)
		{
			return parseInfo;
		}

		if (parseInfo.UnitInfo.Parts.size() == 0)
		{
			parseInfo.UnitInfo = MethodsCommon.GetUnitParts(parseInfo.UnitInfo);
		}

		if (parseInfo.UnitInfo.Parts.size() == 1)
		{
			boolean isCentimetre = parseInfo.UnitInfo.Parts.get(0).getUnit() == Units.Centimetre;
			if(!isCentimetre) isCentimetre = 
			(
				parseInfo.UnitInfo.Parts.get(0).getUnit() == Units.Metre &&
				parseInfo.UnitInfo.Parts.get(0).getPrefix().getFactor() == 0.01
			);
			
			if(isCentimetre) 
			{
				//Centimetre has a very special status: it is fully defined by centi+metre, but it also needs its
				//own unit to be the CGS basic length unit. Formally, it is a compound (formed by 1 part); practically,
				//it doesn't enjoy any of the benefits associated with this reality.
				return parseInfo;
			}

			//Parsing an individual unit might output more than 1 part.
			if (parseInfo.UnitInfo.Parts.get(0).getPrefix().getFactor() != 1.0)
			{
				//1 km should be understood as 1 metre with a kilo prefix, formed
				//by one part of a metre (no prefix). 
				parseInfo.UnitInfo.Prefix = new Prefix
				(
					parseInfo.UnitInfo.Parts.get(0).getPrefix()
				);
				parseInfo.UnitInfo.Parts.get(0).setPrefix
				(
					new Prefix
					(
						parseInfo.UnitInfo.Parts.get(0).getPrefix().getPrefixUsage()
					)
				);
			}
		}

		return parseInfo;
	}  

	static ParseInfo StartCompoundParse(ParseInfo parseInfo)
	{
		//ValidCompound isn't used when parsing individual units.
		parseInfo.ValidCompound = new StringBuilder();

		return StartCompoundAnalysis
		(
			ExtractUnitParts(parseInfo)
		);
	}

	static ParseInfo ExtractUnitParts(ParseInfo parseInfo)
	{
		StringBuilder previous = new StringBuilder();
		String origInput = parseInfo.InputToParse;

		parseInfo = UnitInDenominator(parseInfo);

		//Both strings being different would mean that a number-only numerator was removed.
		//For example: the input String 1/m is converted into m, but UpdateUnitParts treats
		//it as m-1 because of isNumerator being false.
		boolean isNumerator = (origInput == parseInfo.InputToParse);
		
		char symbol = ' ';
		ArrayList<Character> inputArray = CSharpOther.StringToCharacters(parseInfo.InputToParse);

		for (int i = 0; i < inputArray.size(); i++)
		{
			char bit = inputArray.get(i);

			if (MethodsCommon.IsCompoundDescriptive(bit, true) && !SkipCompoundDescriptive(i, inputArray, previous.toString()))
			{
				if (bit == '-')
				{
					if (MinusIsOK(inputArray, i))
					{
						previous.append(bit);
					}
					continue;
				}

				parseInfo = UpdateUnitParts
				(
					parseInfo, previous, isNumerator, symbol
				);

				if (parseInfo.UnitInfo.Error.getType() != ErrorTypes.None)
				{
					return parseInfo;
				}

				if (bit != '-')
				{
					symbol = bit;
					if (OperationsOther.OperationSymbols.get(Operations.Division).contains(bit))
					{
						isNumerator = false;
					}
				}

				previous = new StringBuilder();
			}
			else previous.append(bit);
		}
		
		return
		(
			previous.length() == 0 ? parseInfo :
			UpdateUnitParts(parseInfo, previous, isNumerator, symbol)
		);
	}

	//Confirms whether the given minus sign (-) is correct. Any-5 is right, but 5-5 is wrong.
	static boolean MinusIsOK(ArrayList<Character> inputArray, int i)
	{
		if (i > 0 && i < inputArray.size())
		{
			if (Character.isDigit(inputArray.get(i + 1)) && !Character.isDigit(inputArray.get(i - 1)))
			{
				return true;
			}
		}

		return false;
	}
	
	static boolean SkipCompoundDescriptive(int i, ArrayList<Character> inputArray, String sofar)
	{
		if (inputArray.get(i).toString().toLowerCase() == "x")
		{
			//By default, x is assumed to be a multiplication sign.
			sofar = sofar.toLowerCase();

			if (sofar.length() >= 2)
			{
				String sofar2 = CSharpOther.Substring
				(
					sofar, sofar.length() - 2, 2
				);

				if (sofar2 == "ma" || sofar2 == "lu")
				{
					//It isn't a multiplication sign, but part of the String representations
					//of maxwell or lux.
					return true;
				}
			}

			if (sofar.length() >= 1)
			{
				String sofar2 = CSharpOther.Substring
				(
					sofar, sofar.length() - 1, 1
				);

				if (sofar2 == "m" || sofar2 == "l")
				{
					if (i == inputArray.size() - 1 || MethodsCommon.IsCompoundDescriptive(inputArray.get(i + 1)))
					{
						//It isn't a multiplication sign, but part of the symbols Mx or lx.
						return true;
					}
				}
			}
		}

		return false;
	}

	//When parsing a unit, only integer exponents are considered valid numbers (e.g., m2). Inverse units 
	//(e.g., 1/m) are the exception to this rule. This method corrects the input String to avoid ExtractUnitParts
	//to trigger an error. For example: 1/m*s being converted into m-1*s-1.
	static ParseInfo UnitInDenominator(ParseInfo parseInfo)
	{
		for (Character symbol: OperationsOther.OperationSymbols.get(Operations.Division))
		{
			String[] tempArray = CSharpOther.SplitTryCatch
			(
				parseInfo.InputToParse, symbol.toString()
			);

			if (tempArray.length >= 2)
			{
				UnitInfo tempInfo = ParseDouble(tempArray[0]);
				if (tempInfo.Value != 0.0 && tempInfo.Error.getType() == ErrorTypes.None)
				{
					parseInfo.UnitInfo = Managed.PerformManagedOperationUnits
					(
						parseInfo.UnitInfo, tempInfo, Operations.Multiplication
					);
					parseInfo.InputToParse = parseInfo.InputToParse.substring
					(
						tempArray[0].length() + symbol.toString().length()
					);
					return parseInfo;
				}
			}
		}

		return parseInfo;
	}

	public static ParseInfo StartCompoundAnalysis(ParseInfo parseInfo)
	{
		if (parseInfo.UnitInfo.Error.getType() != ErrorTypes.None)
		{
			return parseInfo;
		}
		if (parseInfo.ValidCompound == null)
		{
			parseInfo.ValidCompound = new StringBuilder();
		}

		parseInfo.UnitInfo = MethodsCommon.RemoveAllUnitInformation(parseInfo.UnitInfo);

		//Knowing the initial positions of all the unit parts is important because of the defining
		//"first element rules" idea which underlies this whole approach. Such a determination isn't
		//always straightforward due to the numerous unit part modifications.
		parseInfo.UnitInfo = MethodsCommon.UpdateInitialPositions(parseInfo.UnitInfo);

		//This is the best place to determine the system before finding the unit, because the subsequent
		//unit part corrections might provoke some misunderstandings on this front (e.g., CGS named 
		//compound divided into SI basic units).
		parseInfo.UnitInfo.System = MethodsCommon.GetSystemFromUnitInfo(parseInfo.UnitInfo);

		//This is also an excellent place to correct eventual system mismatches. For example: N/pint 
		//where pint has to be converted into m3, the SI (first operand system) basic unit for volume.
		parseInfo.UnitInfo = CorrectDifferentSystemIssues(parseInfo.UnitInfo);
		
		parseInfo.UnitInfo = MethodsCommon.ImproveUnitParts(parseInfo.UnitInfo);
		
		if (parseInfo.UnitInfo.Type == UnitTypes.None)
		{
			parseInfo.UnitInfo = MethodsCommon.GetUnitFromParts(parseInfo.UnitInfo);
		}
		
		parseInfo.UnitInfo = UpdateMainUnitVariables(parseInfo.UnitInfo);
		if (parseInfo.UnitInfo.Unit == Units.None)
		{
			parseInfo.UnitInfo.Error = ExceptionInstantiation.NewErrorInfo(ErrorTypes.InvalidUnit);
		}
		else parseInfo = AnalyseValidCompoundInfo(parseInfo);
		
		return parseInfo;
	}
	   
	static UnitInfo CorrectDifferentSystemIssues(UnitInfo unitInfo)
	{
		UnitSystems basicSystem = HCMain.AllMetricEnglish.get(unitInfo.System);
		UnitInfo convertInfo = ExceptionInstantiation.NewUnitInfo(1.0);

		for (int i = 1; i < unitInfo.Parts.size(); i++)
		{
			UnitPart part = unitInfo.Parts.get(i);
			UnitTypes type = MethodsCommon.GetTypeFromUnitPart(part);
			UnitSystems system = MethodsCommon.GetSystemFromUnit(part.getUnit(), true);
			//There are two different scenarios where a conversion might occur: metric vs. English or Imperial vs. USCS.
			boolean convertEnglish = false;

			if (PartNeedsConversion(system, basicSystem, type) || (convertEnglish = PartNeedsConversionEnglish(unitInfo.System, MethodsCommon.GetSystemFromUnit(part.getUnit()))))
			{
				UnitPart targetPart = GetTargetUnitPart
				(
					unitInfo, part, type, 
					(
						convertEnglish ? unitInfo.System : basicSystem
					)
				);
				
				if (targetPart == null || targetPart.getUnit() == part.getUnit() || MethodsCommon.GetTypeFromUnitPart(targetPart) != type)
				{
					continue;
				}
				
				UnitInfo tempInfo = Conversions.AdaptUnitParts
				(
					ExceptionInstantiation.NewUnitInfo
					(
						1.0, new ArrayList<UnitPart>() 
						{{ 
							add(part); add(targetPart); 
						}}
					), 
					0, 1
				);
				
				if (tempInfo == null) continue;
				//AdaptUnitParts doesn't perform an actual conversion, just an adaptation to the target format.
				//That's why it doesn't account for the target prefix, what explains the modification below.
				tempInfo.Parts.get(0).setPrefix(new Prefix(targetPart.getPrefix()));
				
				unitInfo = UpdateNewUnitPart
				(
					unitInfo, unitInfo.Parts.get(i), 
					tempInfo.Parts.get(0)
				);
				convertInfo = Managed.PerformManagedOperationUnits
				(
					convertInfo, tempInfo, Operations.Multiplication
				);
			}
		}

		return  Managed.PerformManagedOperationUnits
		(
			unitInfo, convertInfo, Operations.Multiplication
		);
	}

	static UnitPart GetTargetUnitPart(UnitInfo unitInfo, UnitPart part, UnitTypes partType, UnitSystems targetSystem)
	{
		for (UnitPart part2: unitInfo.Parts)
		{
			if (part2.getUnit() == part.getUnit()) continue;

			if (MethodsCommon.GetSystemFromUnit(part2.getUnit()) == targetSystem && MethodsCommon.GetTypeFromUnitPart(part2, true) == partType)
			{
				//Different unit part with the same type and the target system is good enough.
				//For example: in kg/m*ft, m is a good target for ft.
				return new UnitPart(part2) 
				{{ 
					//The exponent is removed because there was a non-exponent match.
					//Cases like m3-litre never reach this point and are analysed below.
					Exponent = 1;
				}};
			}
		}

		return GetBasicUnitPartForTypeSystem(partType, targetSystem);
	}

	static boolean PartNeedsConversionEnglish(UnitSystems system1, UnitSystems system2)
	{
		return 
		(
			(system1 == UnitSystems.USCS || system2 == UnitSystems.USCS) && system1 != system2
		);
	}

	static boolean PartNeedsConversion(UnitSystems partSystem, UnitSystems basicSystem, UnitTypes partType)
	{
		if (HCOther.NeutralTypes.contains(partType)) return false;

		return
		(
			partSystem == UnitSystems.None || basicSystem == UnitSystems.None ?
			true : HCMain.AllMetricEnglish.get(partSystem) != basicSystem
		);
	}
	
	static UnitInfo UpdateNewUnitPart(UnitInfo unitInfo, UnitPart oldUnitPart, UnitPart newUnitPart)
	{
		int newPos = 0;
		Map.Entry<UnitPart, Integer> oldPos = Linq.FirstOrDefaultDict
		(
			unitInfo.InitialPositions, x -> x.getKey().equals(oldUnitPart), 
			new AbstractMap.SimpleEntry<UnitPart, Integer>(null, 0)
		);

		if (oldPos.getKey() != null)
		{
			newPos = oldPos.getValue();
			HashMap<UnitPart, Integer> allOccurrences = Linq.WhereDict
			(
				unitInfo.InitialPositions, x -> x.getValue().equals(oldPos.getValue())
			);	
					
			for (Map.Entry<UnitPart, Integer> item: allOccurrences.entrySet())
			{
				unitInfo.InitialPositions.remove(item.getKey());
			}
		}
		else if (unitInfo.InitialPositions.size() > 0)
		{
			newPos = Linq.Max
			(
				unitInfo.InitialPositions, new Comparator<Map.Entry<UnitPart, Integer>>()
				{
					public int compare(Map.Entry<UnitPart, Integer> first, Map.Entry<UnitPart, Integer> second)
					{
						return first.getValue().compareTo(second.getValue());
					}        				
				}
			)
			.getValue();
		}
		
		if (!unitInfo.InitialPositions.containsKey(newUnitPart))
		{
			unitInfo.InitialPositions.put(newUnitPart, newPos);
		}

		unitInfo.Parts.remove(oldUnitPart);
		unitInfo.Parts.add(new UnitPart(newUnitPart));

		return unitInfo;
	}

	static UnitPart GetBasicUnitPartForTypeSystem(UnitTypes type, UnitSystems system)
	{
		if (HCCompounds.AllBasicUnits.containsKey(type) && HCCompounds.AllBasicUnits.get(type).containsKey(system))
		{
			//The given type/system matches a basic unit. For example: length and SI matching metre.
			Units unit2 = HCCompounds.AllBasicUnits.get(type).get(system).Unit;
			if (MethodsCommon.UnitIsNamedCompound(unit2))
			{
				//Note that BasicUnit doesn't fully agree with the "basic unit" concept and that's why it might
				//be a compound. 
				ArrayList<UnitPart> compoundUnitParts = MethodsCommon.GetBasicCompoundUnitParts(unit2, true);
				if (compoundUnitParts.size() == 1)
				{
					//Only 1-unit-part versions are relevant. 
					//This condition should always be met as far as all the compound basic units are expected to
					//have a 1-unit-part version. For example: energy defined as an energy unit part. 
					//This theoretically-never-met condition accounts for eventual hardcoding misconducts, like 
					//faulty population of AllCompounds.
					return new UnitPart(compoundUnitParts.get(0));
				}
			}
			else
			{
				return UnitPartInternal.NewUnitPart
				(
					HCCompounds.AllBasicUnits.get(type).get(system).Unit,
					HCCompounds.AllBasicUnits.get(type).get(system).PrefixFactor,
					//Note that exponents aren't relevant to define basic units (= always 1).
					//On the other hand, they might be formed by parts where the exponent is
					//relevant; for example: Units.CubicMetre (1 part with exponent 3).
					1 
				);
			}
		}

		ArrayList<UnitPart> unitParts = MethodsCommon.GetBasicCompoundUnitParts(type, system, true);
		return
		(
			unitParts.size() != 1 ? null :
			//Type and system match a basic compound consisting in just one part (e.g., m2).
			new UnitPart(unitParts.get(0)) 
		);
	}

	//Making sure that the assumed-valid compound doesn't really hide invalid information.
	static ParseInfo AnalyseValidCompoundInfo(ParseInfo parseInfo)
	{
		if (parseInfo.InputToParse == null || parseInfo.ValidCompound.length() < 1)
		{
			//This part might be reached when performing different actions than parsing; that's why
			//InputToParse or ValidCompound might have not be populated.
			return parseInfo;
		}

		ArrayList<Character> valid = CSharpOther.StringToCharacters(parseInfo.ValidCompound.toString());
		ArrayList<Character> toCheck = Linq.Where
		(
			CSharpOther.StringToCharacters(parseInfo.InputToParse.trim()), x -> !x.equals(' ')
		);

		if (valid.size() == toCheck.size()) return parseInfo;

		double invalidCount = 0.0;
		int i2 = -1;

		for (int i = 0; i < toCheck.size(); i++)
		{
			i2 = i2 + 1;
			if (i2 > valid.size() - 1) break;

			if (!CharAreEquivalent(valid.get(i2), toCheck.get(i)))
			{
				if (!wrongCharIsOK(toCheck.get(i)))
				{
					invalidCount = invalidCount + 1.0;
				}
				i = i + 1;
			}
		}

		if (invalidCount >= 3.0 || invalidCount / valid.size() >= 0.25)
		{
			parseInfo.UnitInfo.Type = UnitTypes.None;
			parseInfo.UnitInfo.Unit = Units.None;
		}

		return parseInfo;
	}
	
	static boolean CharAreEquivalent(Character char1, Character char2)
	{
		if (char1 == char2) return true;

		return (char1.toString().toLowerCase() == char2.toString().toLowerCase());
	}

	//Certain symbols aren't considered when post-analysing the validity of the input String.
	//For example: m//////////////s is considered fine.
	static boolean wrongCharIsOK(Character charToCheck)
	{
		if (HCOther.UnitParseIgnored.contains(charToCheck.toString()))
		{
			return true;
		}

		for (Map.Entry<Operations, ArrayList<Character>> item: OperationsOther.OperationSymbols.entrySet())
		{
			if (item.getValue().contains(charToCheck))
			{
				return true;
			}
		}

		return false;
	}

	//Instrumental class whose sole purpose is easing the exponent parsing process.
	static class ParseExponent
	{
		public String AfterString;
		public int Exponent;

		public ParseExponent(String afterString)
		{
			this(afterString, 1);
		}
		public ParseExponent(String afterString, int exponent)
		{
			AfterString = afterString;
			Exponent = exponent;
		}
	}
	
	/*
	Returns the String representation of the unit associated with the given compound.
	NOTE: this function assumes that a valid compound is already in place.
	*/
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
			
			int exponent = Math.abs(unitPart.Exponent);
			if (exponent != 1) unitString = unitString + new Integer(exponent).toString();

			outUnitString = outUnitString + unitString;
		}

		return outUnitString;
	}

	static UnitInfo GetBasicCompoundType(UnitInfo unitInfo)
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
				UnitTypes type = MethodsCommon.GetTypeFromUnit(unitParts.get(0).getUnit());
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

	public static ArrayList<UnitPart> GetCompoundComparisonUnitParts(UnitInfo unitInfo, int type)
	{
		return 
		(
			type == 1 ?
			new ArrayList<UnitPart>(unitInfo.Parts) :
			GetUnitPartsForAnyUnit(unitInfo)
		);
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
			UnitTypes type2 = MethodsCommon.GetTypeFromUnitPart(part, false, true);
			if (HCCompounds.AllCompounds.containsKey(type2))
			{
				ArrayList<UnitPart> parts2 = MethodsCommon.GetUnitPartsFromBasicCompound
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

			outParts = MethodsCommon.SimplifyCompoundComparisonUnitParts(outParts).Parts;
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

	static UnitInfo SimplifyCompoundComparisonUnitParts(ArrayList<UnitPart> unitParts)
	{
		return SimplifyCompoundComparisonUnitParts(unitParts, false);
	}
	
	static UnitInfo SimplifyCompoundComparisonUnitParts(ArrayList<UnitPart> unitParts, boolean checkPrefixes)
	{
		UnitInfo outInfo = ExceptionInstantiation.NewUnitInfo
		(
			1.0, unitParts
		);

		for (int i = outInfo.Parts.size() - 1; i >= 0; i--)
		{
			for (int i2 = i - 1; i2 >= 0; i2--)
			{
				UnitTypes type1 = MethodsCommon.GetTypeFromUnit(outInfo.Parts.get(i).getUnit());
				UnitTypes type2 = MethodsCommon.GetTypeFromUnit(outInfo.Parts.get(i2).getUnit());
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

	static boolean UnitPartsMatchCompoundParts(ArrayList<UnitPart> unitParts, ArrayList<CompoundPart> compoundParts)
	{
		if (unitParts.size() != compoundParts.size()) return false;

		for (UnitPart part: unitParts)
		{
			UnitTypes type = MethodsCommon.GetTypeFromUnit(part.getUnit());
			int exponent = part.Exponent;
			if (Linq.FirstOrDefault(compoundParts, x -> x.Type.equals(type) && x.Exponent.equals(exponent), null) == null)
			{
				return false;
			}
		}
		return true;
	}

	static ParseExponent AnalysePartExponent(String input)
	{
		input = input.trim();
		char[] inputArray = input.toCharArray();
		int i2 = 0;
		
		for (int i = input.length() - 1; i >= 0; i--)
		{
			if (!Character.isDigit(inputArray[i]) && inputArray[i] != '-')
			{
				i2 = i;
				break;
			}
		}

		ParseExponent outVar = new ParseExponent(input);
		TryParseOutput tempVar = TryParseMethods.Int(input.substring(i2 + 1));
		
		if (tempVar.IsOK)
		{
			//Only integer exponents are supported.
			outVar = new ParseExponent
			(
				CSharpOther.Substring
				(
					input, 0, i2 + 1
				)	
				, tempVar.IntVal
			);
		}

		return outVar;
	}

	static ParseInfo UpdateUnitParts(ParseInfo parseInfo, StringBuilder inputSB, boolean isNumerator, char symbol)
	{
		if (inputSB.length() == 0) return parseInfo;
		String input = inputSB.toString();

		ParseExponent exponent = AnalysePartExponent(input);
		if (!isNumerator) exponent.Exponent = -1 * exponent.Exponent;

		ParseInfo parseInfo2 = StartIndividualUnitParse
		(
			new ParseInfo
			(
				0.0, exponent.AfterString,
				parseInfo.UnitInfo.Prefix.getPrefixUsage()
			)
		);

		return
		(
			parseInfo2.UnitInfo.Unit == Units.None ?
			new ParseInfo(parseInfo)
			{{ 
				UnitInfo = ExceptionInstantiation.NewUnitInfo
				(
					parseInfo.UnitInfo, ErrorTypes.InvalidUnit
				);
			}} :
			AddValidUnitPartInformation
			(
				parseInfo, parseInfo2, symbol, exponent, input
			)
		);
	}

	static ParseInfo AddValidUnitPartInformation(ParseInfo parseInfo, ParseInfo parseInfo2, char symbol, ParseExponent exponent, String input)
	{
		parseInfo.ValidCompound = AddInformationToValidCompound
		(
			parseInfo.ValidCompound, symbol, exponent, input
		);

		parseInfo.UnitInfo = MethodsCommon.AddNewUnitParts
		(
			parseInfo.UnitInfo, new ArrayList<UnitPart>() 
			{{
				add
				(
					UnitPartInternal.NewUnitPart
					(
						parseInfo2.UnitInfo.Unit, 
						parseInfo2.UnitInfo.Prefix.getFactor(),
						exponent.Exponent
					)		
				);
			}}
		);

		return parseInfo;
	}

	static StringBuilder AddInformationToValidCompound(StringBuilder validCompound, char symbol, ParseExponent exponent, String input)
	{
		if (symbol != ' ') validCompound.append(symbol);

		if (exponent.AfterString.trim().length() > 0)
		{
			validCompound.append(exponent.AfterString);
		}

		String exponent2 = input.replace(exponent.AfterString, "").trim();
		if (exponent2.length() > 0)
		{
			validCompound.append(exponent2);
		}

		return validCompound;
	}

	static boolean StringCanBeCompound(String inputToParse)
	{
		return
		(
			inputToParse.length() >= 2 && Linq.FirstOrDefault
			(
				CSharpOther.StringToCharacters(inputToParse), 
				x -> MethodsCommon.IsCompoundDescriptive(x), '\u0000'
			)
			!= '\u0000'
		);
	}
}
