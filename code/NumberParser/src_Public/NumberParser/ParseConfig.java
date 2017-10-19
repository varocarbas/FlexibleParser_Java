package NumberParser;

import java.util.Locale;

import InternalNumberParser.Operations.*;

/**ParseConfig defines the way in which the ParseConfig parsing actions are being performed.**/
public class ParseConfig
{
	/**Locale variable to be used while parsing. Its default value is Locale.getDefault().**/
	private Locale Culture = Locale.getDefault();
	
    /**Culture getter.**/
    public Locale getCulture()
	{
	    return Culture;
	}
    
    /**Culture setter.**/
	public void setCulture(Locale culture)
	{
		Culture = culture;
	}

	/**Member of the ParseTypes enum defining the parsing type. Its default value is ParseTypes.ParseAll.**/
	private ParseTypes ParseType = ParseTypes.ParseAll;
	
    /**ParseType getter.**/
    public ParseTypes getParseType()
	{
	    return ParseType;
	}
    
    /**ParseType setter.**/
	public void setParseType(ParseTypes parseType)
	{
		ParseType = parseType;
	}
	
	/**Numeric type targeted by the parsing actions. Its default value is NumericTypes.Double.**/		  
	private NumericTypes Target = NumericTypes.Double;
	
    /**Target getter.**/
    public NumericTypes getTarget()
	{
	    return Target;
	}
    
    /**Target setter.**/
	public void setTarget(NumericTypes target)
	{
		Target = target;
	}
	
	/**Initialises a new ParseConfig instance.**/ 
	public ParseConfig() { }

	/**
	Initialises a new ParseConfig instance.
	@param target Type variable defining the numeric type targeted by the parsing actions.
	**/   
	public ParseConfig(NumericTypes target)
	{
		Target =
		(
			target.equals(NumericTypes.None) ?
			NumericTypes.Double : target
		);
	}

	/**
	Initialises a new ParseConfig instance.
	@param config ParseConfig variable whose information will be used.
	**/  
	public ParseConfig(ParseConfig config)
	{
		if (config == null)
		{
			config = new ParseConfig();
			Culture = config.Culture;
			ParseType = config.ParseType;
			Target = config.Target;
			
			return;
		}

		Culture = 
		(
			config.Culture != null ? 
			config.Culture : Locale.getDefault()
		);
		
		ParseType = config.ParseType;
		Target =
		(
			!config.Target.equals(NumericTypes.None) ? 
			config.Target : NumericTypes.Double
		);
	}

	@Override
    /**
	Outputs the information in all the public fields (one per line).
     **/
    public String toString()
    {
		String output = "Culture: " + Culture.getDisplayName() + System.lineSeparator();
		
		output += "ParseType: " + ParseType.toString() + System.lineSeparator();
		output += "Target: " + (Target != null ? Target.toString() : "nothing");

		return output;
    }
	
    @Override
    /**
    Determines whether the current ParseConfig instance is equal to other one.
    @param obj Other variable.
     **/
    public boolean equals(Object obj)
    {
        return Equals((ParseConfig)obj);
    }
    
    boolean Equals(ParseConfig other)
    {
        return
        (
            other == null ? false :
            OperationsEquals.ParseConfigsAreEqual(this, other)
        );
    }

    @Override
    /**Returns the hash code for this ParseConfig instance.**/
    public int hashCode() { return 0; }   
}
