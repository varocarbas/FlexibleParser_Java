package InternalUnitParser.Classes;

import UnitParser.*;

public class BasicUnit
{
    public Units Unit;
    public double PrefixFactor;

    public BasicUnit(Units unit)
    {
        this(unit, 1.0);
    }
    
    public BasicUnit(Units unit, double prefixFactor)
    {
        Unit = unit;
        PrefixFactor = prefixFactor;
    }
}
