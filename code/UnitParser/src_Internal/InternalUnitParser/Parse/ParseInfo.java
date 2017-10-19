package InternalUnitParser.Parse;

import InternalUnitParser.Classes.*;
import InternalUnitParser.CSharpAdaptation.*;
import UnitParser.*;

//Stores all the information which might be required at any point while performing
//parsing actions.
public class ParseInfo
{
    public UnitInfo UnitInfo;   
    public String InputToParse;
    //ValidCompound isn't used when parsing individual units.
    public StringBuilder ValidCompound;

    public ParseInfo() 
    {
    	this((UnitInfo)null);
    }

    public ParseInfo(UnitInfo unitInfo)
    {
    	this(unitInfo, "");
    }
    
    public ParseInfo(UnitInfo unitInfo, String inputToParse)
    {
        UnitInfo = ExceptionInstantiation.NewUnitInfo(unitInfo);
        InputToParse = inputToParse;
    }

    public ParseInfo(ParseInfo parseInfo)
    {
    	this(parseInfo, "");
    }
    
    public ParseInfo(ParseInfo parseInfo, String inputToParse)
    {
        if (parseInfo == null) parseInfo = new ParseInfo();

        UnitInfo = ExceptionInstantiation.NewUnitInfo(parseInfo.UnitInfo);
        InputToParse = 
        (
            inputToParse != "" ? inputToParse : 
            parseInfo.InputToParse
        );
    }

    public ParseInfo(double value, String inputToParse)
    {
    	this(value, inputToParse, PrefixUsageTypes.DefaultUsage, 1);
    }
    
    public ParseInfo(double value, String inputToParse, PrefixUsageTypes prefixUsage)
    {
    	this(value, inputToParse, prefixUsage, 1);
    }
    
    public ParseInfo
    (
        double value, String inputToParse, PrefixUsageTypes prefixUsage, int exponent
    )
    {
        UnitInfo = ExceptionInstantiation.NewUnitInfo
        (
            value, Units.None, new Prefix(prefixUsage)
        );
        InputToParse = inputToParse;
    }
}
