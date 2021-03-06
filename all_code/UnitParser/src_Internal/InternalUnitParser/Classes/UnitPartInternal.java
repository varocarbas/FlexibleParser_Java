package InternalUnitParser.Classes;

import UnitParser.Prefix;
import UnitParser.UnitPart;
import UnitParser.Units;

//Class mirroring UnitPart to deal with the internal/private constructors of this class in the original C# code. Users aren't expected to rely on this class at all.
public class UnitPartInternal
{
    public static UnitPart NewUnitPart(Units unit, double prefixFactor)
    {
    	return NewUnitPart(unit, prefixFactor, 1);
    }
    
    public static UnitPart NewUnitPart(Units unit, double prefixFactor, int exponent)
    {
    	return new UnitPart(unit, new Prefix(prefixFactor), exponent);
    }
}
