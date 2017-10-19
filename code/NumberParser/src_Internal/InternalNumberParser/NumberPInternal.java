package InternalNumberParser;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import InternalNumberParser.CSharpAdaptation.*;
import InternalNumberParser.Operations.*;
import NumberParser.*;
import NumberParser.Number;

@SuppressWarnings("serial")
public class NumberPInternal
{
    public static NumberD StartParse(ParseInfo info)
    {
        ParseInfo info2 = new ParseInfo(info);
        info2.OriginalString = RemoveValidRedundant
        (
            info2.OriginalString, 
            info2.Config.getCulture()
        );

        //The blank spaces are supported as thousands separators in any case (i.e., independently upon the ParseType
        //value). But they have to be replace with standard separators to avoid parsing problems (the native parse
        //methods don't support them).
        if (info2.OriginalString.contains(" "))
        {
            ArrayList<String> thousands = GetCultureFeature
            (
                CultureFeatures.ThousandSeparator, 
                info2.Config.getCulture()
            );

            if (thousands.size() > 0)
            {
                info2.OriginalString = CSharpOtherNP.StringJoin
                (
                    thousands.get(0), CSharpOtherNP.SplitTryCatch
                    (
                    	info2.OriginalString, " "
                    )
                );
            }
        }

        if 
        (
            info2.Config.getParseType().equals(ParseTypes.ParseThousandsStrict) ||
            info2.Config.getParseType().equals(ParseTypes.ParseOnlyTargetAndThousandsStrict)
        )
        {
            //Method determining whether the thousands separators are valid or not.
            info2 = AnalyseThousands(info2);
            if (!info2.Number.getError().equals(ErrorTypesNumber.None))
            {
                return new NumberD(info2.Number.getError());
            }
        }

        return
        (
            info2.Config.getTarget().equals(NumericTypes.Double) ? 
            new NumberD
            (
            	ParseDoubleAndBeyond
            	(
            		info2.OriginalString, 
            		info2.Config.getCulture()
            	)
            ) 
            : ParseToSpecificType
            (
            	info2.OriginalString, info2.Config
            )
        );
    }

    //Removing all the symbols associated with the current culture which, although technically valid, might
    //provoke some problems under certain parsing conditions.
    static String RemoveValidRedundant(String input, Locale culture)
    {
        CultureFeatures[] features = new CultureFeatures[]
        {
            CultureFeatures.CurrencySymbol, CultureFeatures.PercentageSymbol
        };

        String output = input;

        for (CultureFeatures feature: features)
        {
            for (String item : GetCultureFeature(feature, culture))
            {
                output = output.replace(item, "");
            }
        }

        return output;
    }

    static ParseInfo AnalyseThousands(ParseInfo info)
    {   
        ParseInfo error = new ParseInfo(info, ErrorTypesNumber.ParseError);
        ArrayList<String> separators = GetCultureFeature
        (
        	CultureFeatures.ThousandSeparator, info.Config.getCulture()
        );
        if (separators.size() == 0) return error;

        //Removing the decimal part (+ making sure that it doesn't include any thousands separator) if existing.
        String string2 = RemoveDecimalsForThousands
        (
            info.OriginalString, info.Config.getCulture(), separators
        );

        ArrayList<String> groups = CSharpOtherNP.SplitTryCatchMulti
        (
        	string2, separators
        );
        if (groups.size() < 2) return error;

        int count = 0;
        while (count < 3)
        {
            count++;
            if (ThousandsAreOK(groups, count)) 
            {
            	return info;
            }
        }

        return error;
    }

    static String RemoveDecimalsForThousands
    (
    	String input, Locale culture, ArrayList<String> separators
    )
    {
        ArrayList<String> decimals = GetCultureFeature
        (
        	CultureFeatures.DecimalSeparator, culture
        );
        ArrayList<String> tempVar = CSharpOtherNP.SplitTryCatchMulti
        (
        	input, decimals
        );
        if (tempVar.size() != 2) 
        {
        	return 
        	(
        		tempVar.size() > 2 ? "" : input
        	);
        }

        if 
        (
        	LinqNP.FirstOrDefault
        	(
        		CSharpOtherNP.StringToCharacters(input), 
        		(
        			x -> x == ' ' || separators.contains
        			(
        				x.toString()
                	)
                )
        		, '\0'
        	)
        	!= '\0'
        )
        { return ""; }

    	TryParseOutputNP tempVar2 = TryParseMethodsNP.Integer
    	(
    		tempVar.get(1), culture
    	);
    	
    	return 
    	(
    		!tempVar2.IsOK ? "" : tempVar.get(0)
    	);
    }

    static boolean ThousandsAreOK(ArrayList<String> groups, int type)
    {
        int target = 3; //Standard -> groups of 3.
        if (type == 3)
        {
            //Chinese -> groups of 4.
            target = 4;
        }

        //The last group (i = 0) doesn't need to be analysed (undefined size for all the types).
        for (int i = groups.size() - 1; i > 0; i--)
        {
            if (target != 0 && groups.get(i).length() != target) 
            {
            	return false;
            }

            if (type == 2)
            {
                //Indian -> first group of 3; the remaining groups of 2.
                target = 2;
            }
        }

        return true;
    }

    static NumberD ParseToSpecificType(String input, ParseConfig config)
    {
    	NumericTypes type = config.getTarget();
    	
    	boolean precheckIt = false;
    	double val0 = 0.0;
    	if (!type.equals(NumericTypes.None))
    	{
    		TryParseOutputNP tempVar = TryParseMethodsNP.Double
    		(
    			input, config.getCulture()
    		);
    		
    		if (tempVar.IsOK)
    		{
        		if (type.equals(NumericTypes.Double))
        		{
        			return new NumberD(tempVar.DoubleVal);
        		}
        		
    			precheckIt = true;
    			val0 = tempVar.DoubleVal;        		
    		}
    	}

    	if (precheckIt)
    	{
			if 
			(
				val0 >= Conversions.ConvertTargetToDouble
				(
					Basic.AllNumberMinMaxs.get(type).get(0)
				)
			)
			{
				if 
				(
					val0 <= Conversions.ConvertTargetToDouble
					(
						Basic.AllNumberMinMaxs.get(type).get(1)
					)
				)
				{ return new NumberD(val0, type); }
			}
    	}

        return 
        (
            config.getParseType().equals(ParseTypes.ParseAll) || 
            config.getParseType().equals(ParseTypes.ParseThousandsStrict) ?
            //The input string cannot be directly parsed to the target type.
            //Under these conditions, there is a second chance: relying on ParseAnyMain which parses any string
            //fitting the Number range (+ the more permissive parsing rules of this library) and adapts its output
            //to the target type.
            ParseAnyMain(input, config) :
            new NumberD(ErrorTypesNumber.NativeMethodError)
        );
    }
    
    static boolean InputIsCultureFeature
    (
    	String input, CultureFeatures feature, Locale culture
    )
    {
        for (String item: GetCultureFeature(feature, culture))
        {
            if (item.equals(input)) return true;
        }

        return false;
    }

	static ArrayList<String> GetDecimalGroupSeparators(NumberFormat numberFormat, boolean isGroup)
    {
    	DecimalFormatSymbols tempVar = 
    	(
    		(DecimalFormat)numberFormat
    	)
    	.getDecimalFormatSymbols();
    	
    	ArrayList<String> output = new ArrayList<String>()
    	{{
    		add
    		(
    			new Character
    			(
    				isGroup ? tempVar.getGroupingSeparator() : 
    				tempVar.getDecimalSeparator()
    			)
    			.toString()
    		);
    	}};

    	if (isGroup) return output;
    	
    	String tempVar2 = new Character
    	(
    		tempVar.getMonetaryDecimalSeparator()		
    	)
    	.toString();
    	
    	if (output.get(0) != tempVar2)
    	{
    		output.add(tempVar2);
    	}
    	
    	return output;
    }
	
    static ArrayList<String> GetCultureFeature(CultureFeatures feature, Locale culture)
    {
        if (culture == null) return new ArrayList<String>();

        NumberFormat numberFormat = NumberFormat.getInstance(culture);
        
        if (feature.equals(CultureFeatures.DecimalSeparator))
        {
        	return GetDecimalGroupSeparators(numberFormat, false);
            //All the decimal (or thousands) separators associated with a given culture are (almost?) always 
            //identical. In any case, associating a given character to certain subtype (number/currency/percentage)
            //would go against the overall attitude of this library (i.e., parsing as restrictionless as possible).
            //That's why all of the separators are treated indistinctively.
        }
        else if (feature.equals(CultureFeatures.ThousandSeparator))
        {
        	return GetDecimalGroupSeparators(numberFormat, true);
        }
        else if (feature.equals(CultureFeatures.CurrencySymbol))
        {
            return new ArrayList<String>()
            {{ 
                add
                (
                	numberFormat.getCurrency().getSymbol()
                );
            }};
        }
        else if (feature.equals(CultureFeatures.PercentageSymbol))
        {
        	ArrayList<String> output = new ArrayList<String>();
        	output.add("%"); 
        	output.add("‰");
        	
        	return output;
        }
        
        return new ArrayList<String>();
    }
    
    static NumberD ParseAnyMain(String input, ParseConfig config)
    {
        return Conversions.ConvertNumberToAny
        (
        	ParseDoubleAndBeyond(input, config.getCulture()), config.getTarget()
        );
    }

    static Number ParseDoubleAndBeyond(String input, Locale culture)
    {
        TryParseOutputNP tempVar = TryParseMethodsNP.Double(input, culture);

        return
        (
        	tempVar.IsOK && tempVar.DoubleVal != 0.0 ?
            Conversions.ConvertDoubleToNumber(tempVar.DoubleVal) : 
            ParseBeyondDouble(input, culture)
        );
    }

    private static Number ParseBeyondDouble(String input, Locale culture)
    {
        String stringToParse = input.trim().toLowerCase();

        //The numeric-parsing .NET format is expected. That is: only integer exponents after the letter "e", what 
        //has to be understood as before-e * 10^after-e.
        if (stringToParse.contains("e"))
        {
            double[] tempVals = ExponentialPartsAnalysis
            (
                stringToParse, culture
            );

            if (tempVals != null)
            {
                return (Number)OperationsOther.VaryBaseTenExponent
                (
                    Conversions.ConvertDoubleToNumber(tempVals[0]), (int)tempVals[1]
                );
            }
        }
        else
        {
            if (stringToParse.length() < 300)
            {
                TryParseOutputNP tempVar = TryParseMethodsNP.Double
                (
                	stringToParse, culture
                );
                
                if (tempVar.IsOK)
                {
                    return Conversions.ConvertDoubleToNumber
                    (
                    	tempVar.DoubleVal
                    );
                }
            }
            else
            {
                TryParseOutputNP tempVar = TryParseMethodsNP.Double
                (
                	stringToParse.substring(0, 299), culture
                );            	

                if (tempVar.IsOK)
                {
                    String remString = stringToParse.substring(299);
                    
                    boolean isOK = LinqNP.FirstOrDefault
                    (
                    	CSharpOtherNP.StringToCharacters(remString), 
                    	x -> !Character.isDigit(x) && !InputIsCultureFeature
                    	(
                    		x.toString(), CultureFeatures.ThousandSeparator, culture
                    	)
                    	, '\0'
                    	
                    )
                    != '\0';
                    
                    if (isOK)
                    {
                        //Finding a decimal separator here is considered an error because it wouldn't
                        //be too logical (300 digits before the decimal separator!). Mainly by bearing
                        //in mind the exponential alternative above.
                        return new Number(ErrorTypesNumber.InvalidInput);
                    }

                    int beyondCount = GetBeyondDoubleCharacterCount
                    (
                        stringToParse.substring(299), culture
                    );

                    if (beyondCount <= 0)
                    {
                        return new Number
                        (
                            beyondCount == 0 ? 
                            ErrorTypesNumber.InvalidInput : 
                            ErrorTypesNumber.NumericOverflow
                        );
                    }

                    //Accounting for the differences 0.001/1000 -> 10^-3/10^3.
                    int sign = 
                    (
                    	Math.abs(tempVar.DoubleVal) < 1.0 ? -1 : 1
                    );

                    if (tempVar.DoubleVal == 0.0 && sign == -1)
                    {
                        //This code accounts for situations like 0.00000[...]00001 where, 
                    	//for the aforementioned double.TryParse, tempVar.DoubleVal is zero.
                        boolean found = false;
                        int length2 = 
                        (
                        	remString.length() > 299 ? 
                        	299 : remString.length()
                        );
                        
                        char[] remString2 = remString.toCharArray();
                        for (int i = 0; i < remString2.length; i++)
                        {
                            if 
                            (
                            	remString2[i] != '0' && !InputIsCultureFeature
                            	(
                            		((Character)remString2[i]).toString(), 
                            		CultureFeatures.ThousandSeparator, culture
                            	)
                            )
                            {
                            	
                                //The default interpretation is initial_part*10^remString.Length (up to the maximum length natively supported by double). 
                                //For example, 0.0000012345[...]4565879561424 is correctly understood as 0.0000012345*10^length-after-[...]. In this specific 
                                //situation (i.e., initial_part understood as zero), some digits after [...] have to also be considered to form initial_part. 
                                //Thus, tempVar.DoubleVal is being redefined as all the digits (up to the maximum length natively supported by double) after the 
                                //first non-zero one; and beyondCount (i.e., the associated 10-base exponent) such that it also includes all the digits since the start.
                                found = true;
                                tempVar.DoubleVal = TryParseMethodsNP.Double
                                (
                                	CSharpOtherNP.Substring(remString, i, length2 - i), culture
                                )
                                .DoubleVal;
                                beyondCount = 297 + length2;
                                break;
                            }
                        }

                        if (!found) return new Number(0.0);
                    }

                    return (Number)OperationsOther.VaryBaseTenExponent
                    (
                        Conversions.ConvertDoubleToNumber
                        (
                        	tempVar.DoubleVal
                        ), 
                        sign * beyondCount
                    );
                }
            }
        }

        return new Number(ErrorTypesNumber.ParseError);
    }

    //This method is only called after having confirmed that the given input contains "e".
    private static double[] ExponentialPartsAnalysis(String input, Locale culture)
    {
        ArrayList<String> tempStrings = CSharpOtherNP.SplitTryCatch(input, "e");
        if (tempStrings.size() != 2) return null;

        double[] outVals = new double[2];
        //Standard double.TryParse is fine because the input string isn't expected to fulfill the NumberP rules, but the
        //standard .NET ones (i.e., double + "e" + int).
        //NOTE: TryParseMethodsNP.Double emulates the double.TryParse behaviour.
        TryParseOutputNP tempVar = TryParseMethodsNP.Double
        (
        	tempStrings.get(0), culture
        );
        if (!tempVar.IsOK) return null;
        outVals[0] = tempVar.DoubleVal;
        
        tempVar = TryParseMethodsNP.Double
        (
        	tempStrings.get(1), culture
        );
        if 
        (
        	!tempVar.IsOK || Math.abs(tempVar.DoubleVal) > 
        	Conversions.ConvertTargetToDouble(Integer.MAX_VALUE)
        )
        { return null; }
        outVals[1] = tempVar.DoubleVal;
        
        return outVals;
    }

    private static int GetBeyondDoubleCharacterCount(String remString, Locale culture)
    {
        int outCount = 0;

        try
        {
            for (Character item: remString.toCharArray())
            {
                if 
                (
                	!Character.isDigit(item) && !InputIsCultureFeature
                	(
                		item.toString(), CultureFeatures.ThousandSeparator, culture
                	)
                )
                { return 0; }
                
                outCount = outCount + 1;
            }
        }
        catch(Exception e)
        {
            //The really unlikely scenario of hitting Integer.MAX_VALUE.
            outCount = -1;
        }

        return outCount;
    }
}
