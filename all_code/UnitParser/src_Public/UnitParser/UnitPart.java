package UnitParser;

import InternalUnitParser.CSharpAdaptation.CSharpOther;
import InternalUnitParser.Operations.*;

/**Contains the main information associated with the constituent parts of each unit.**/
public class UnitPart
{
    /**Unit associated with the current part.**/
    public Units Unit;
    /**Prefix information associated with the current part.**/
    public Prefix Prefix;
    /**Exponent associated with the current part.**/
    public Integer Exponent;

    static String StartHardcoding = CSharpOther.StartHarcoding();
    
    /**Initialises a new UnitPart instance.
    @param unit Member of the Units enum to be used.
    @param prefix Prefix variable whose information will be used.
    **/
    public UnitPart(Units unit, Prefix prefix)
    {
        this(unit, prefix, 1);
    }
    
    /**Initialises a new UnitPart instance.
    @param unit Member of the Units enum to be used.
    @param prefix Prefix variable whose information will be used.
    @param exponent Integer exponent to be used.
    **/
    public UnitPart(Units unit, Prefix prefix, int exponent)
    {
        Unit = unit;
        Prefix = new Prefix(prefix);
        Exponent = exponent;
    }
    
    /**
    Initialises a new UnitPart instance.
    @param unit Member of the Units enum to be used.
    **/
    public UnitPart(Units unit)
    {
        this(unit, 1);
    }
    
    /**
    Initialises a new UnitPart instance.
    @param unit Member of the Units enum to be used.
    @param exponent Integer exponent to be used.
    **/
    public UnitPart(Units unit, int exponent)
    {
        Unit = unit;
        Prefix = new Prefix(1.0);
        Exponent = exponent;
    }
    
    /**
    Initialises a new UnitPart instance.
    @param unitPart UnitPart variable whose information will be used.
    **/
    public UnitPart(UnitPart unitPart)
    {
        if (unitPart == null) unitPart = new UnitPart(Units.None);

        Unit = unitPart.Unit;
        Prefix = new Prefix(unitPart.Prefix);
        Exponent = unitPart.Exponent;
    }

    /**
    Determines whether the current UnitPart instance is equal to other one
    @param obj Other variable.
    **/
    @Override
    public boolean equals(Object obj)
    {
        return Equals((UnitPart)obj);
    }

    boolean Equals(UnitPart other)
    {
        return
        (
            other == null ? false : 
            Equals.UnitPartsAreEqual(this, other)
        );
    }
    
    @Override
    /**Returns the hash code for this UnitPart instance.**/
    public int hashCode() { return 0; }
}
