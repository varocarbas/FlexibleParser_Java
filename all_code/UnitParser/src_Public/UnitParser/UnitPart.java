package UnitParser;

import InternalUnitParser.CSharpAdaptation.CSharpOther;
import InternalUnitParser.Operations.*;

/**Contains the main information associated with the constituent parts of each unit.**/
public class UnitPart
{
	/**Unit associated with the current part.**/
	private Units Unit;
	
	/**
	Units getter.
	@return Current value of the private Units variable Unit. 
	**/
	public Units getUnit()
	{
	    return Unit;
	} 
	
	/**
	Unit setter.
	@param unit New value for the private Units variable Unit.  
	**/
	public void setUnit(Units unit)
	{
	    Unit = unit;
	} 
	
	/**Prefix information associated with the current part.**/
	private Prefix Prefix;
	
	/**
	Prefix getter.
	@return Current value of the private Prefix variable Prefix. 
	**/
	public Prefix getPrefix()
	{
	    return 
	    (
	    	Prefix == null ? null :
	    	new Prefix(Prefix)
	    );
	} 
	
	/**
	Prefix setter.
	@param prefix New value for the private Prefix variable Prefix.  
	**/
	public void setPrefix(Prefix prefix)
	{
	    Prefix = 
	    (
	    	prefix == null ? null :
	    	new Prefix(prefix)
	    );
	} 
	
	/**Exponent associated with the current part.**/
	public Integer Exponent;
	
	/**
	Exponent getter.
	@return Current value of the private Integer variable Exponent. 
	**/
	public Integer getExponent()
	{
		return Exponent;
	} 
	
	/**
	Exponent setter.
	@param exponent New value for the private Integer variable Exponent.  
	**/
	public void setExponent(Integer exponent)
	{
		Exponent = exponent;
	} 
	
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
