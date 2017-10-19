package InternalNumberParser;

import NumberParser.*;
import NumberParser.Number;

public class ParseInfo
{
    public String OriginalString = null;
    public Number Number = null;
    public ParseConfig Config = null;

    public ParseInfo(NumberP numberP)
    {
        OriginalString = numberP.getOriginalString();
        Config = new ParseConfig(numberP.getParseConfig());
        Number = new Number();
    }

    public ParseInfo(ParseInfo info) 
    { 
    	this(info, ErrorTypesNumber.None);
    }
    
    public ParseInfo(ParseInfo info, ErrorTypesNumber error)
    {
        OriginalString = info.OriginalString;
        Config = new ParseConfig(info.Config);
        Number = new Number(error);
    }
}
