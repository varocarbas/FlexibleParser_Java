package UnitParser;

import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Hardcoding.*;
import InternalUnitParser.Operations.*;

import java.util.AbstractMap;

/**Contains the main information associated with unit prefixes.**/
public class Prefix implements Comparable<Prefix>
{
    /**Name of the unit prefix.**/
    public final String Name;
    /**Symbol of the unit prefix.**/
    public final String Symbol;
    /**Multiplying factor associated with the unit prefix.**/
    public final double Factor;
    /**Type of the unit prefix.**/
    public final PrefixTypes Type;
    /**Usage conditions of the unit prefix.**/
    public final PrefixUsageTypes PrefixUsage;
    
    static String StartHardcoding = CSharpOther.StartHarcoding();
    
    /**Initialises a new Prefix instance.**/
    public Prefix() 
    { 
        Name = "None";
        Symbol = "";
        Factor = 1.0;   
        Type = PrefixTypes.None;
        PrefixUsage = PrefixUsageTypes.DefaultUsage;
    }

    /**
    Initialises a new Prefix instance.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used.
    **/
    public Prefix(PrefixUsageTypes prefixUsage) 
    { 
    	PrefixUsage = prefixUsage; 
        Name = "None";
        Symbol = "";
        Factor = 1.0;   
        Type = PrefixTypes.None;
    }

    /**
    Initialises a new Prefix instance.
    @param factor Multiplying factor to be used.
    **/
    public Prefix(double factor)
    {
    	this(factor, PrefixUsageTypes.DefaultUsage);
    }
    /**
    Initialises a new Prefix instance.
    @param factor Multiplying factor to be used.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used.
    **/
    public Prefix(double factor, PrefixUsageTypes prefixUsage)
    {
        PrefixUsage = prefixUsage;
        Type = GetType(factor, "");

        if (Type != PrefixTypes.None)
        {
            Factor = factor;
            Name = GetName(Type, Factor);
            
            Symbol = Linq.FirstOrDefaultDict
            (
                Type == PrefixTypes.SI ? HCPrefixes.AllSIPrefixSymbols : 
                HCPrefixes.AllBinaryPrefixSymbols, x -> x.getValue().equals(new Double(factor)),
                new AbstractMap.SimpleEntry<String, Double>(null, 0.0)
            )
            .getKey();
        }
        else 
        {
        	Factor = 1.0;
        	Symbol = "";
        	Name = "";
        }
    }
    
    /**
    Initialises a new Prefix instance.
    @param symbol Symbol (case does matter) defining the current prefix.
    **/
    public Prefix(String symbol)
    {
    	this(symbol, PrefixUsageTypes.DefaultUsage);
    }
    
    /**
    Initialises a new Prefix instance.
    @param symbol Symbol (case does matter) defining the current prefix.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used.
    **/
    public Prefix(String symbol, PrefixUsageTypes prefixUsage)
    {
        PrefixUsage = prefixUsage;
        Type = GetType(1.0, symbol);

        if (Type != PrefixTypes.None)
        {
            Symbol = symbol;
            Factor =
            (
                Type == PrefixTypes.SI ?
                HCPrefixes.AllSIPrefixSymbols.get(symbol) :
                HCPrefixes.AllBinaryPrefixSymbols.get(symbol)
            );
            Name = GetName(Type, Factor);
        }
        else 
        {
        	Factor = 1.0;
        	Symbol = "";
        	Name = "";
        }
    }

    /**
    Initialises a new Prefix instance.
    @param prefix Prefix variable whose information will be used.
    **/
    public Prefix(Prefix prefix)
    {
        if (prefix == null) prefix = new Prefix();

        Name = prefix.Name;
        Symbol = prefix.Symbol;
        Factor = prefix.Factor;
        Type = prefix.Type;
        PrefixUsage = prefix.PrefixUsage;
    }

    static PrefixTypes GetType(double factor, String symbol)
    {
        PrefixTypes outType = PrefixTypes.None;
        if (factor == 1.0 && symbol == "") return outType;

        if (factor != 1.0)
        {
            if (HCPrefixes.AllSIPrefixes.containsValue(factor))
            {
                outType = PrefixTypes.SI;
            }
            else if (HCPrefixes.AllBinaryPrefixes.containsValue(factor))
            {
                outType = PrefixTypes.Binary;
            }
        }
        else
        {
            if (HCPrefixes.AllSIPrefixSymbols.containsKey(symbol))
            {
                outType = PrefixTypes.SI;
            }
            else if (HCPrefixes.AllBinaryPrefixSymbols.containsKey(symbol))
            {
                outType = PrefixTypes.Binary;
            }
        }

        return outType;
    }

    static String GetName(PrefixTypes type, double factor)
    {
        return
        (
            type == PrefixTypes.SI ?
            Linq.FirstOrDefaultDict
            (
            	HCPrefixes.AllSIPrefixes, x -> x.getValue().equals(new Double(factor)), 
            	new AbstractMap.SimpleEntry<SIPrefixes, Double>(SIPrefixes.None, 0.0)
            ) :
            Linq.FirstOrDefaultDict
            (
            	HCPrefixes.AllBinaryPrefixes, x -> x.getValue().equals(new Double(factor)),
                new AbstractMap.SimpleEntry<BinaryPrefixes, Double>(BinaryPrefixes.None, 0.0)            	
            )
        )
        .getKey().toString();
    }

	@Override
    /**
    Compares the current instance against another Prefix one.
    @param other The other Prefix instance.
	**/
	public int compareTo(Prefix other)
	{
        return new Double(this.Factor).compareTo(other.Factor);
	}

    @Override
    /**
    Determines whether the current Prefix instance is equal to other one.
    @param obj Other variable.
	**/
    public boolean equals(Object obj)
    {
        return Equals((Prefix)obj);
    }

    boolean Equals(Prefix other)
    {
        return
        (
            other == null ? false :
            Equals.PrefixesAreEqual(this, other)
        );
    }

    @Override
    /**Returns the hash code for this Prefix instance.**/
    public int hashCode() { return 0; }
}
