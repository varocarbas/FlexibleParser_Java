package InternalUnitParser.Classes;

import InternalUnitParser.Operations.*;
import UnitParser.*;

public class CompoundPart
{
    public UnitTypes Type;
    public Integer Exponent;

    public CompoundPart(UnitTypes type)
    {
        this(type, 1);
    }
    
    public CompoundPart(UnitTypes type, int exponent)
    {
        Type = type;
        Exponent = exponent;
    }

    public boolean Equals(CompoundPart other)
    {
        return
        (
            other == null ? false :
            Equals.CompoundPartsAreEqual(this, other)
        );
    }

    @Override
    public boolean equals(Object obj)
    {
        return Equals((CompoundPart)obj);
    }

    @Override
    public int hashCode() { return 0; }
}