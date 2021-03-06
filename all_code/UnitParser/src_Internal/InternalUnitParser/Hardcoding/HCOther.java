package InternalUnitParser.Hardcoding;

import UnitParser.*;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class HCOther
{
    //While determining the system from the contituent parts, some units might be misinterpreted. 
    //For example: in ft/s, assuming SI (because of s) would be wrong. This collection avoids these
    //problems by including the types to be ignored in these analyses, the neutral types.
	public static ArrayList<UnitTypes> NeutralTypes;

    //List of types whose conversion requires more than just applying a conversion factor.
    public static ArrayList<UnitTypes> SpecialConversionTypes;
    
    //Characters which are ignored while parsing units. For example: m or m. or (m) are identical.
    //NOTE: the decimal separator characters would have to be removed from this list in order to
    //start supporting decimal exponents.
    public static ArrayList<String> UnitParseIgnored;
    
	public static void Start()
	{
		NeutralTypes = new ArrayList<UnitTypes>()
	    {{
	        add(UnitTypes.Time); add(UnitTypes.Angle); add(UnitTypes.SolidAngle);
	        add(UnitTypes.ElectricCurrent); add(UnitTypes.AmountOfSubstance);
	        add(UnitTypes.Temperature);
	    }};	
	    
	    SpecialConversionTypes = new ArrayList<UnitTypes>()
	    {{
	        add(UnitTypes.Temperature);
	    }};
	    
	    UnitParseIgnored = new ArrayList<String>()
	    {{
	        add("."); add(","); add(":"); add(";"); add("_"); add("^"); add("+"); 
	        add("#"); add("("); add(")"); add("["); add("]"); add("{"); add("}"); 
	        add("="); add("!"); add("?"); add("@"); add("&");
	    }};
	}
}
