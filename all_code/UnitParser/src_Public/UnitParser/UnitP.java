package UnitParser;

import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Classes.*;
import InternalUnitParser.Methods.*;
import InternalUnitParser.Operations.*;
import InternalUnitParser.OtherParts.OtherPartsNumberParserMethods;
import InternalUnitParser.Parse.*;

import java.util.ArrayList;

/**Basic UnitParser class containing all the information about units and values.**/
public class UnitP implements Comparable<UnitP>
{
	/**Member of the Units enum which best suits the current conditions.**/
	private Units Unit;
	
	/**
	Unit getter.
	@return Current value of the private Units variable Unit. 
	**/
	public Units getUnit()
	{
	    return Unit;
	}  
	
	/**Member of the UnitTypes enum which best suits the current conditions.**/
	private UnitTypes UnitType;
	
	/**
	UnitType getter.
	@return Current value of the private UnitTypes variable UnitType. 
	**/
	public UnitTypes getUnitType()
	{
	    return UnitType;
	}  
	
	/**Member of the UnitSystems enum which best suits the current conditions.**/
	private UnitSystems UnitSystem; 
	
	/**
	UnitSystem getter.
	@return Current value of the private UnitSystems variable UnitSystem. 
	**/
	public UnitSystems getUnitSystem()
	{
	    return UnitSystem;
	}  
	
	/**Prefix information affecting all the unit parts.**/
	private Prefix UnitPrefix;
	
	/**
	UnitPrefix getter.
	@return Current value of the private Prefix variable UnitPrefix. 
	**/
	public Prefix getUnitPrefix()
	{
	    return 
	    (
	    	UnitPrefix == null ? null :
	    	new Prefix(UnitPrefix)
	    );
	}  
	
	/**List containing the basic unit parts which define the current unit.**/
	private ArrayList<UnitPart> UnitParts;
	
	/**
	UnitParts getter.
	@return Current value of the private ArrayList<UnitPart> variable UnitParts. 
	**/
	public ArrayList<UnitPart> getUnitParts()
	{
	    return 
	    (
	    	UnitParts == null ? 
	    	new ArrayList<UnitPart>() : 
	    	new ArrayList<UnitPart>(UnitParts)
	    );
	}  
	
	/**String variable including the unit information which was input at variable instantiation.**/
	private String OriginalUnitString;
	
	/**
	OriginalUnitString getter.
	@return Current value of the private String variable OriginalUnitString. 
	**/
	public String getOriginalUnitString()
	{
	    return OriginalUnitString;
	}  
	
	/**String variable containing the symbol(s) best describing the current unit.**/
	private String UnitString;
	
	/**
	UnitString getter.
	@return Current value of the private String variable UnitString. 
	**/
	public String getUnitString()
	{
	    return UnitString;
	}  
	
	/**String variable including both numeric and unit information associated with the current conditions.**/
	private String ValueAndUnitString;
	
	/**
	ValueAndUnitString getter.
	@return Current value of the private String variable ValueAndUnitString. 
	**/
	public String getValueAndUnitString()
	{
	    return ValueAndUnitString;
	}  
	
	/**Base-ten exponent used when dealing with too small/big numeric values.**/
	private Integer BaseTenExponent;
	
	/**
	BaseTenExponent getter.
	@return Current value of the private Integer variable BaseTenExponent. 
	**/
	public Integer getBaseTenExponent()
	{
	    return BaseTenExponent;
	}  
	
	/**ErrorInfo variable containing all the error- and exception-related information.**/
	private ErrorInfo Error;
	
	/**
	Error getter.
	@return Current value of the private ErrorInfo variable Error. 
	**/
	public ErrorInfo getError()
	{
	    return Error;
	} 
	
	/**double variable storing the primary numeric information under the current conditions.**/
	private Double Value;
	
	/**
	Value getter.
	@return Current value of the private Double variable Value. 
	**/
	public Double getValue()
	{
	    return Value;
	} 
	
	/**
	Value setter.
	@param value New value for the private Double variable Value.  
	**/
	public void setValue(Double value)
	{
	    Value = value;
	} 
	
	static String StartHardcoding = CSharpOther.StartHarcoding();
    
    /**
    Initialises a new UnitP instance.
    @param value Numeric value to be used.
    @param unitString Unit information to be parsed.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used.
    **/
    public UnitP(Double value, String unitString, ExceptionHandlingTypes exceptionHandling, PrefixUsageTypes prefixUsage)
    {
        UnitPConstructor unitP2 = MethodsUnitP.GetUnitP2(value, unitString, exceptionHandling, prefixUsage);
        
        OriginalUnitString = unitP2.OriginalUnitString;
        Value = unitP2.Value;
        BaseTenExponent = unitP2.UnitInfo.BaseTenExponent;
        Unit = unitP2.UnitInfo.Unit;
        UnitType = unitP2.UnitType;
        UnitSystem = unitP2.UnitSystem;
        UnitPrefix = new Prefix(unitP2.UnitInfo.Prefix);
        UnitParts = unitP2.UnitInfo.Parts;
        UnitString = unitP2.UnitString;
        ValueAndUnitString = unitP2.ValueAndUnitString;
        //If applicable, this instantiation would trigger an exception right away.
        Error = ExceptionInstantiation.NewErrorInfo(unitP2.ErrorType, unitP2.ExceptionHandling);
    }
    
    /**
    Initialises a new UnitP instance.
    @param valueAndUnit String containing the value and unit information to be parsed.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used.    
    **/   
    public UnitP(String valueAndUnit, ExceptionHandlingTypes exceptionHandling, PrefixUsageTypes prefixUsage)
    {
        ErrorTypes parsingError = 
        (
            valueAndUnit == null ? ErrorTypes.NumericParsingError : ErrorTypes.None
        );

        UnitInfo unitInfo = ExceptionInstantiation.NewUnitInfo(0.0, exceptionHandling, prefixUsage);
        
        String unitString = "";

        if (parsingError == ErrorTypes.None)
        {
            UnitInfo tempInfo = MethodsUnitP.ParseValueAndUnit(valueAndUnit);
            
            if (tempInfo.Error.Type == ErrorTypes.None)
            {
                unitString = tempInfo.TempString;
                unitInfo.Value = tempInfo.Value;
                unitInfo.BaseTenExponent = tempInfo.BaseTenExponent;
            }
            else parsingError = tempInfo.Error.Type;
        }

        if (parsingError != ErrorTypes.None && !valueAndUnit.contains(" "))
        {
            //valueAndUnit is assumed to only contain unit information.
            parsingError = ErrorTypes.None;
            unitInfo.Value = 1.0;
            unitString = valueAndUnit;
        }

        UnitPConstructor unitP2 = MethodsUnitP.GetUnitP2(unitInfo, unitString);

        OriginalUnitString = unitP2.OriginalUnitString;
        Value = unitP2.Value;
        BaseTenExponent = unitP2.UnitInfo.BaseTenExponent;
        Unit = unitP2.UnitInfo.Unit;
        UnitType = unitP2.UnitType;
        UnitSystem = unitP2.UnitSystem;
        UnitPrefix = new Prefix(unitP2.UnitInfo.Prefix.getFactor(), prefixUsage);
        UnitParts = unitP2.UnitInfo.Parts;
        UnitString = unitP2.UnitString;
        ValueAndUnitString = unitP2.ValueAndUnitString;
        //If applicable, this instantiation would trigger an exception right away.
        Error = ExceptionInstantiation.NewErrorInfo
        (
            (parsingError != ErrorTypes.None ? parsingError : unitP2.ErrorType),
            unitP2.ExceptionHandling
        );
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Value = 1.0
    Unit = Units.Unitless
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    PrefixUsage = PrefixUsageTypes.DefaultUsage    
    **/
    public UnitP()
    {
    	this(1.0);
    }
    
    public UnitP(Double value)
    { 
    	this(value, Units.Unitless);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Value = 1m
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param unit Member of the Units enum to be used.
    **/
    public UnitP(Units unit)
    { 
    	this(1.0, unit);
    }

    /**
    Initialises a new UnitP instance.
    @param unitP variable whose information will be used.
    **/
    public UnitP(UnitP unitP) 
    {
        Value = unitP.Value;
        BaseTenExponent = unitP.BaseTenExponent;
        Unit = unitP.Unit;
        UnitType = unitP.UnitType;
        UnitSystem = unitP.UnitSystem;
        UnitPrefix = new Prefix(unitP.UnitPrefix);
        UnitParts = new ArrayList<UnitPart>(unitP.UnitParts);
        UnitString = unitP.UnitString;
        OriginalUnitString = unitP.OriginalUnitString;
        ValueAndUnitString = unitP.ValueAndUnitString;
        Error = new ErrorInfo(unitP.Error);
    }

    /**
    Initialises a new UnitP instance.
    @param value Numeric value to be used.
    @param unit Member of the Units enum to be used.
    @param prefix Prefix variable whose information will be used.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used. 
    **/
    public UnitP(Double value, Units unit, Prefix prefix, ExceptionHandlingTypes exceptionHandling)
    {
        ErrorTypes errorType = 
        (
            unit == Units.None || MethodsCommon.IsUnnamedUnit(unit) ?
            ErrorTypes.InvalidUnit : ErrorTypes.None
        );

        UnitInfo tempInfo = null;
        if (errorType == ErrorTypes.None)
        {
            //Getting the unit parts associated with the given unit.
            tempInfo = ExceptionInstantiation.NewUnitInfo(value, unit, prefix);

            if (tempInfo.Error.Type == ErrorTypes.None)
            {
                tempInfo = MethodsUnitP.ImproveUnitInfo(tempInfo, false);
            }
            else errorType = tempInfo.Error.Type;
        }

        if (errorType != ErrorTypes.None)
        {
            Value = 0.0;
            BaseTenExponent = 0;
            UnitPrefix = new Prefix(prefix.getPrefixUsage());
            UnitParts = new ArrayList<UnitPart>();
            UnitString = null;
            OriginalUnitString = null;
            Unit = Units.None;
            UnitSystem = UnitSystems.None;
            UnitType = UnitTypes.None;
            ValueAndUnitString = "";
        }
        else
        {
            Value = tempInfo.Value;
            BaseTenExponent = tempInfo.BaseTenExponent;
            Unit = unit;
            UnitType = MethodsCommon.GetTypeFromUnit(Unit);
            UnitSystem = MethodsCommon.GetSystemFromUnit(Unit);
            UnitPrefix = new Prefix(prefix);
            UnitParts = tempInfo.Parts;
            UnitString = MethodsCommon.GetUnitString(tempInfo);
            OriginalUnitString = UnitString;
            ValueAndUnitString = Value.toString() + " " + UnitString;
        }

        //If applicable, this instantiation would trigger an exception right away.
        Error = ExceptionInstantiation.NewErrorInfo
        (
            errorType, exceptionHandling
        );
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param value Numeric value to be used.
    @param unitString String containing the unit information to be parsed.
    **/
    public UnitP(Double value, String unitString)
    { 
    	this
        (
            value, unitString, ExceptionHandlingTypes.NeverTriggerException, 
            PrefixUsageTypes.DefaultUsage
        ); 
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param value Numeric value to be used.
    @param unitString String containing the unit information to be parsed.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used.
    **/
    public UnitP
    (
        Double value, String unitString, ExceptionHandlingTypes exceptionHandling
    ) 
    { 
    	this (value, unitString, exceptionHandling, PrefixUsageTypes.DefaultUsage);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    @param value Numeric value to be used.
    @param unitString String containing the unit information to be parsed.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used. 
    **/   
    public UnitP(Double value, String unitString, PrefixUsageTypes prefixUsage) 
    { 
    	this(value, unitString, ExceptionHandlingTypes.NeverTriggerException, prefixUsage);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param valueAndUnit String containing the value and unit information to be parsed.
    **/
    public UnitP(String valueAndUnit)
    { 
    	this(valueAndUnit, ExceptionHandlingTypes.NeverTriggerException, PrefixUsageTypes.DefaultUsage);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param valueAndUnit String containing the value and unit information to be parsed.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used. 
    **/          
    public UnitP(String valueAndUnit, ExceptionHandlingTypes exceptionHandling)
    { 
    	this(valueAndUnit, exceptionHandling, PrefixUsageTypes.DefaultUsage); 
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    @param valueAndUnit String containing the value and unit information to be parsed.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used.    
    **/      
    public UnitP(String valueAndUnit, PrefixUsageTypes prefixUsage)
    { 
    	this(valueAndUnit, ExceptionHandlingTypes.NeverTriggerException, prefixUsage);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    @param value Numeric value to be used.
    @param unit Member of the Units enum to be used.
    @param prefix Prefix variable whose information will be used.
    **/
    public UnitP(Double value, Units unit, Prefix prefix)
    { 
    	this(value, unit, prefix, ExceptionHandlingTypes.NeverTriggerException);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param value Numeric value to be used.
    @param unit Member of the Units enum to be used.
    **/
    public UnitP(Double value, Units unit)
    { 
    	this(value, unit, new Prefix(), ExceptionHandlingTypes.NeverTriggerException);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param value Numeric value to be used.
    @param unit Member of the Units enum to be used.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used. 
    **/
    public UnitP(Double value, Units unit, ExceptionHandlingTypes exceptionHandling)
    { 
    	this(value, unit, new Prefix(), exceptionHandling);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    UnitPrefix = null
    @param value Numeric value to be used.
    @param unit Member of the Units enum to be used.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used. 
    **/
    public UnitP(Double value, Units unit, PrefixUsageTypes prefixUsage)
    { 
    	this(value, unit, new Prefix(), ExceptionHandlingTypes.NeverTriggerException);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Unit = Units.Unitless
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param numberX NumberParser's Number, NumberD, NumberO or NumberP variable to be used.
    **/
    public UnitP(Object numberX) 
    {
        UnitInfo unitInfo = OtherPartsNumberParserMethods.GetUnitInfoFromNumberX(numberX);
        if (unitInfo.Error.Type == ErrorTypes.None)
        {
            unitInfo.Unit = Units.Unitless;
        }
        UnitPConstructor unitP2 = new UnitPConstructor("", unitInfo);

        Value = unitP2.Value;
        BaseTenExponent = unitP2.UnitInfo.BaseTenExponent;
        Unit = unitP2.UnitInfo.Unit;
        UnitType = unitP2.UnitType;
        UnitSystem = unitP2.UnitSystem;
        UnitPrefix = new Prefix(unitP2.UnitInfo.Prefix);
        UnitParts = new ArrayList<UnitPart>(unitP2.UnitInfo.Parts);
        UnitString = unitP2.UnitString;
        ValueAndUnitString = unitP2.ValueAndUnitString;
        OriginalUnitString = "";
        //If applicable, this instantiation would trigger an exception right away.
        Error = ExceptionInstantiation.NewErrorInfo(unitP2.ErrorType, unitP2.ExceptionHandling);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param numberX NumberParser's Number, NumberD, NumberO or NumberP variable to be used.
    @param unitString String containing the unit information to be parsed.
    **/
    public UnitP(Object numberX, String unitString)
    {
    	this(numberX, unitString, ExceptionHandlingTypes.NeverTriggerException);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param numberX NumberParser's Number, NumberD, NumberO or NumberP variable to be used.
    @param unitString String containing the unit information to be parsed.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used.
    **/
    public UnitP(Object numberX, String unitString, ExceptionHandlingTypes exceptionHandling)
    {
    	this(numberX, unitString, exceptionHandling, PrefixUsageTypes.DefaultUsage);
    }

    /**
    Initialises a new UnitP instance.
    @param numberX NumberParser's Number, NumberD, NumberO or NumberP variable to be used.
    @param unitString String containing the unit information to be parsed.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used.
    **/
    public UnitP
    (
        Object numberX, String unitString, 
        ExceptionHandlingTypes exceptionHandling,
        PrefixUsageTypes prefixUsage
    )
    {
    	
        UnitPConstructor unitP2 = MethodsUnitP.GetUnitP2
        (
        	OtherPartsNumberParserMethods.GetUnitInfoFromNumberX(numberX, exceptionHandling, prefixUsage), unitString
        );

        Value = unitP2.Value;
        BaseTenExponent = unitP2.UnitInfo.BaseTenExponent;
        Unit = unitP2.UnitInfo.Unit;
        UnitType = unitP2.UnitType;
        UnitSystem = unitP2.UnitSystem;
        UnitPrefix = new Prefix(unitP2.UnitInfo.Prefix);
        UnitParts = new ArrayList<UnitPart>(unitP2.UnitInfo.Parts);
        UnitString = unitP2.UnitString;
        ValueAndUnitString = unitP2.ValueAndUnitString;
        OriginalUnitString = "";
        //If applicable, this instantiation would trigger an exception right away.
        Error = ExceptionInstantiation.NewErrorInfo(unitP2.ErrorType, unitP2.ExceptionHandling);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param numberX NumberParser's Number, NumberD, NumberO or NumberP variable to be used.
    @param unit Member of the Units enum to be used.
    **/
    public UnitP(Object numberX, Units unit)
    {
    	this(numberX, unit, null);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    Error.ExceptionHandling = ExceptionHandlingTypes.NeverTriggerException
    @param numberX NumberParser's Number, NumberD, NumberO or NumberP variable to be used.
    @param unit Member of the Units enum to be used.
    @param prefix Prefix variable whose information will be used.
    **/
    public UnitP(Object numberX, Units unit, Prefix prefix)
    {
    	this(numberX, unit, prefix, ExceptionHandlingTypes.NeverTriggerException);
    }

    /**
    Initialises a new UnitP instance.
    Automatically assigned values:
    PrefixUsage = PrefixUsageTypes.DefaultUsage
    @param numberX NumberParser's Number, NumberD, NumberO or NumberP variable to be used.
    @param unit Member of the Units enum to be used.
    @param prefix Prefix variable whose information will be used.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used.
    **/
    public UnitP
    (
        Object numberX, Units unit, Prefix prefix, 
        ExceptionHandlingTypes exceptionHandling
    )
    {
    	this(numberX, unit, prefix, exceptionHandling, PrefixUsageTypes.DefaultUsage);
    }
    
    /**
    Initialises a new UnitP instance.
    @param numberX NumberParser's Number, NumberD, NumberO or NumberP variable to be used.
    @param unit Member of the Units enum to be used.
    @param prefix Prefix variable whose information will be used.
    @param exceptionHandling Member of the ExceptionHandlingTypes enum to be used.
    @param prefixUsage Member of the PrefixUsageTypes enum to be used.
    **/
    public UnitP
    (
        Object numberX, Units unit, Prefix prefix, 
        ExceptionHandlingTypes exceptionHandling, 
        PrefixUsageTypes prefixUsage
    )
    {
        if (prefix == null) prefix = new Prefix();

        ErrorTypes errorType =
        (
            unit == Units.None || MethodsCommon.IsUnnamedUnit(unit) ?
            ErrorTypes.InvalidUnit : ErrorTypes.None
        );

        UnitInfo tempInfo = null;
        if (errorType == ErrorTypes.None)
        {
            tempInfo = OtherPartsNumberParserMethods.GetUnitInfoFromNumberX
            (
                numberX, ExceptionHandlingTypes.NeverTriggerException, prefix.getPrefixUsage()
            );

            if (tempInfo.Error.Type == ErrorTypes.None)
            {
                //Getting the unit parts associated with the given unit.
                tempInfo.Unit = unit;
                tempInfo.Prefix = new Prefix(prefix);
                tempInfo.Parts = new ArrayList<UnitPart>
                (
                	MethodsCommon.GetPartsFromUnit(tempInfo).Parts
                );

                if (tempInfo.Error.Type == ErrorTypes.None)
                {
                    tempInfo = MethodsUnitP.ImproveUnitInfo(tempInfo, false);
                }
            }

            errorType = tempInfo.Error.Type;
        }

        if (errorType != ErrorTypes.None)
        {
            Value = 0.0;
            BaseTenExponent = 0;
            UnitPrefix = new Prefix(prefix.getPrefixUsage());
            UnitParts = new ArrayList<UnitPart>();
            ValueAndUnitString = "";
            UnitType = UnitTypes.None;
            UnitSystem = UnitSystems.None;
            UnitString = "";
            Unit = Units.None;
            OriginalUnitString = "";
        }
        else
        {
            Value = tempInfo.Value;
            BaseTenExponent = tempInfo.BaseTenExponent;
            Unit = unit;
            UnitType = MethodsCommon.GetTypeFromUnit(Unit);
            UnitSystem = MethodsCommon.GetSystemFromUnit(Unit);
            UnitPrefix = new Prefix(prefix);
            UnitParts = new ArrayList<UnitPart>(tempInfo.Parts);
            UnitString = MethodsCommon.GetUnitString(tempInfo);
            OriginalUnitString = UnitString;
            ValueAndUnitString = Value.toString() + " " + UnitString;
        }
        
        //If applicable, this instantiation would trigger an exception right away.
        Error = ExceptionInstantiation.NewErrorInfo
        (
            errorType, ExceptionHandlingTypes.NeverTriggerException
        );
    }
    
    //---------------------------- Private UnitP constructors.
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP(UnitP unitP, Double value, int baseTenExponent)
    {
        Value = value;
        BaseTenExponent = baseTenExponent;
        Unit = unitP.Unit;
        UnitType = unitP.UnitType;
        UnitSystem = unitP.UnitSystem;
        UnitPrefix = new Prefix(unitP.UnitPrefix);
        UnitParts = new ArrayList<UnitPart>(unitP.UnitParts);
        UnitString = unitP.UnitString;
        OriginalUnitString = unitP.Value.toString() +
        (
            unitP.BaseTenExponent != 0 ?
            "*10^" + unitP.BaseTenExponent.toString() : ""
        );
        ValueAndUnitString = Value.toString() +
        (
            BaseTenExponent != 0 ?
            "*10^" + BaseTenExponent.toString() : ""
        ) + " " + UnitString;
        Error = new ErrorInfo(unitP.Error);
    }
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP(ParseInfo parseInfo)
    {
    	this(parseInfo, "");
    }
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP(ParseInfo parseInfo, String originalUnitString)
    {
    	this(parseInfo, originalUnitString, UnitSystems.None);
    }
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP
    (
        ParseInfo parseInfo, String originalUnitString, UnitSystems system
    )
    {
    	this(parseInfo, originalUnitString, system, ExceptionHandlingTypes.NeverTriggerException);
    }
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP
    (
        ParseInfo parseInfo, String originalUnitString, UnitSystems system, ExceptionHandlingTypes exceptionHandling
    )
    {
    	this(parseInfo, originalUnitString, system, exceptionHandling, PrefixUsageTypes.DefaultUsage);
    }
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP
    (
        ParseInfo parseInfo, String originalUnitString, UnitSystems system,
        ExceptionHandlingTypes exceptionHandling, PrefixUsageTypes prefixUsage
    )
    {
    	this
    	(
    		parseInfo, originalUnitString, system, exceptionHandling, prefixUsage, false
    	);
    }
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP
    (
        ParseInfo parseInfo, String originalUnitString, UnitSystems system,
        ExceptionHandlingTypes exceptionHandling, PrefixUsageTypes prefixUsage, 
        boolean noPrefixImprovement
    )
    {
    	this
    	(
    		parseInfo, originalUnitString, system, exceptionHandling, 
    		prefixUsage, noPrefixImprovement, true
    	);
    }
   
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */    
    public UnitP
    (
        ParseInfo parseInfo, String originalUnitString, UnitSystems system,
        ExceptionHandlingTypes exceptionHandling, PrefixUsageTypes prefixUsage, 
        boolean noPrefixImprovement, boolean improveFinalValue
    )
    {
        UnitPConstructor unitP2 = new UnitPConstructor
        (
            originalUnitString, parseInfo.UnitInfo, parseInfo.UnitInfo.Type, parseInfo.UnitInfo.System,
            ErrorTypes.None, exceptionHandling, noPrefixImprovement, improveFinalValue
        );

        OriginalUnitString = unitP2.OriginalUnitString;
        Value = unitP2.Value;
        BaseTenExponent = unitP2.UnitInfo.BaseTenExponent;
        Unit = unitP2.UnitInfo.Unit;
        UnitType = unitP2.UnitType;
        UnitSystem =
        (
            system != UnitSystems.None ?
            system : unitP2.UnitSystem
        );
        UnitPrefix = new Prefix(unitP2.UnitInfo.Prefix);
        UnitParts = unitP2.UnitInfo.Parts;
        UnitString = unitP2.UnitString;
        ValueAndUnitString = unitP2.ValueAndUnitString;
        //If applicable, this instantiation would trigger an exception right away.
        Error = ExceptionInstantiation.NewErrorInfo
        (
        	unitP2.ErrorType, unitP2.ExceptionHandling
        );
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP(UnitP unitP, ErrorTypes errorType)
    {
    	this(unitP, errorType, ExceptionHandlingTypes.NeverTriggerException);
    }
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP
    (
        UnitP unitP, ErrorTypes errorType, ExceptionHandlingTypes exceptionHandling
    )
    {
        if (unitP == null) unitP = new UnitP();

        UnitPConstructor unitP2 = new UnitPConstructor
        (
            unitP.OriginalUnitString, ExceptionInstantiation.NewUnitInfo(unitP),
            UnitTypes.None, UnitSystems.None, errorType,
            (
                exceptionHandling != ExceptionHandlingTypes.NeverTriggerException ?
                exceptionHandling : unitP.Error.ExceptionHandling
            )
        );

        if (unitP2.ErrorType != ErrorTypes.None)
        {
            Value = 0.0;
            BaseTenExponent = 0;
            UnitPrefix = new Prefix(unitP2.UnitInfo.Prefix.getPrefixUsage());
            UnitParts = new ArrayList<UnitPart>();
            Unit = Units.None;
            UnitType = UnitTypes.None;
            UnitSystem = UnitSystems.None;
            OriginalUnitString = "";
            ValueAndUnitString = "";
            UnitString = "";
        }
        else
        {
            OriginalUnitString = unitP2.OriginalUnitString;
            Value = unitP2.Value;
            BaseTenExponent = unitP2.UnitInfo.BaseTenExponent;
            Unit = unitP2.UnitInfo.Unit;
            UnitType = unitP2.UnitType;
            UnitSystem = unitP2.UnitSystem;
            UnitPrefix = new Prefix(unitP2.UnitInfo.Prefix);
            UnitParts = unitP2.UnitInfo.Parts;
            UnitString = unitP2.UnitString;
            ValueAndUnitString = unitP2.ValueAndUnitString;
        }

        //If applicable, this instantiation would trigger an exception right away.
        Error = ExceptionInstantiation.NewErrorInfo
        (
        	unitP2.ErrorType, unitP2.ExceptionHandling
        );
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP(UnitInfo unitInfo, UnitP unitP, boolean noPrefixImprovement) 
    {
    	this
    	(
    		new ParseInfo(unitInfo), unitP.OriginalUnitString, unitP.UnitSystem,
    		unitP.Error.ExceptionHandling, unitP.UnitPrefix.getPrefixUsage(), noPrefixImprovement
    	);    	
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP(UnitInfo unitInfo, UnitP unitP)
    {
    	this(unitInfo, unitP, "");   	
    }
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP(UnitInfo unitInfo, UnitP unitP, String originalUnitString)
    {
    	this(unitInfo, unitP, originalUnitString, true);   	
    }
    
    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public UnitP
    (
        UnitInfo unitInfo, UnitP unitP, String originalUnitString, boolean improveFinalValue
    )
    {
    	this
        (
            new ParseInfo(unitInfo), originalUnitString, UnitSystems.None,
            unitP.Error.ExceptionHandling, unitInfo.Prefix.getPrefixUsage(), 
            false, improveFinalValue
        );
    }
    
    
    //---------------------------- Public static methods.
    
    /**
    Converts the input unit into the target one. Different unit types will trigger an error.
    targetPrefix = null
    @param unitP variable whose unit will be converted.
    @param targetUnit Conversion target unit.
    @return UnitP variable containing the information resulting from converting unitP to targetUnit.
    **/
    public static UnitP ConvertTo(UnitP unitP, Units targetUnit)
    {
    	return ConvertTo(unitP, targetUnit, null);
    }
    
    /**
    Converts the input unit into the target one. Different unit types will trigger an error.
    @param unitP variable whose unit will be converted.
    @param targetUnit Conversion target unit.
    @param targetPrefix Prefix of the conversion target unit.
    @return UnitP variable containing the information resulting from converting unitP to targetUnit and targetPrefix.   
    **/
    public static UnitP ConvertTo(UnitP unitP, Units targetUnit, Prefix targetPrefix)
    {
        return MethodsUnitP.ConvertToCommon(unitP, targetUnit, targetPrefix);
    }

    /**
    Converts the input unit into the target one. Different unit types will trigger an error.
    @param unitP variable whose unit will be converted.
    @param targetUnitString String representation of the conversion target unit.
    @return UnitP variable containing the information resulting from converting unitP to targetUnitString.   
    **/
    public static UnitP ConvertTo(UnitP unitP, String targetUnitString)
    {
        return MethodsUnitP.ConvertToCommon(unitP, targetUnitString);
    }

    /**
    Returns the String representations associated with the input unit.
    otherStringsToo = false
    @param unit Unit whose String representations will be returned.  
    @return ArrayList including all the primary string representations associated with unit.         
    **/
    public static final ArrayList<String> GetStringsForUnit(Units unit)
    {
        return GetStringsForUnit(unit, false);
    }
    
    /**
    Returns the String representations associated with the input unit.
    @param unit Unit whose String representations will be returned.  
    @param otherStringsToo When true, all the supported String representations (case doesn't matter) other than symbols (case matters) are also included.  
    @return ArrayList including all the (primary or primary and secondary) string representations associated with unit.     
    **/
    public static final ArrayList<String> GetStringsForUnit(Units unit, boolean otherStringsToo)
    {
        return MethodsUnitP.GetStringsUnitCommon(unit, otherStringsToo);
    }

    /**
    Returns the String representations associated with the input unit type.
    otherStringsToo = false
    @param unitType Type of the unit String representations to be returned. 
    @return ArrayList including all the primary string representations associated with all the units related to unitType.    
    **/
    public static final ArrayList<String> GetStringsForType(UnitTypes unitType)
    {
        return MethodsUnitP.GetStringsTypeCommon(unitType, false);
    }
    
    /**
    Returns the String representations associated with the input unit type.
    @param unitType Type of the unit String representations to be returned.  
    @param otherStringsToo When true, all the supported String representations (case doesn't matter) other than symbols (case matters) are also included.  
    @return ArrayList including all the (primary or primary and secondary) string representations associated with all the units related to unitType. 
    **/
    public static final ArrayList<String> GetStringsForType(UnitTypes unitType, boolean otherStringsToo)
    {
        return MethodsUnitP.GetStringsTypeCommon(unitType, otherStringsToo);
    }

    /**
    Returns the String representations associated with the input unit type and system.
    otherStringsToo = false
    @param unitType Type of the unit String representations to be returned.  
    @param unitSystem System of the unit String representations to be returned. 
    @return ArrayList including all the primary string representations associated with all the units related to unitType and unitSystem.          
    **/ 
    public static final ArrayList<String> GetStringsForTypeAndSystem(UnitTypes unitType, UnitSystems unitSystem)
    {
        return MethodsUnitP.GetStringsTypeAndSystemCommon(unitType, unitSystem, false);
    }
    
    /**
    Returns the String representations associated with the input unit type and system.
    @param unitType Type of the unit String representations to be returned.  
    @param unitSystem System of the unit String representations to be returned.          
    @param otherStringsToo When true, all the supported String representations (case doesn't matter) other than symbols (case matters) are also included. 
    @return ArrayList including all the (primary or primary and secondary) string representations associated with all the units related to unitType and unitSystem. 
    **/ 
    public static final ArrayList<String> GetStringsForTypeAndSystem(UnitTypes unitType, UnitSystems unitSystem, boolean otherStringsToo)
    {
        return MethodsUnitP.GetStringsTypeAndSystemCommon(unitType, unitSystem, otherStringsToo);
    }

    /**
    Returns the members of the Units enum which are associated with the input unit type.
    @param unitType Type of the units to be returned.
    @return ArrayList including all the units associated with unitType.       
    **/
    public static final ArrayList<Units> GetUnitsForType(UnitTypes unitType)
    {
        return MethodsUnitP.GetUnitsTypeCommon(unitType);
    }

    /**
    Returns the members of the Units enum which are associated with the input unit type and system.
    @param unitType Type of the units to be returned.  
    @param unitSystem System of the units to be returned.  
    @return ArrayList variable including all the units associated with unitType and unitSystem.    
    **/
    public static final ArrayList<Units> GetUnitsForTypeAndSystem(UnitTypes unitType, UnitSystems unitSystem)
    {
        return MethodsUnitP.GetUnitsTypeAndSystemCommon(unitType, unitSystem);
    }

    /**
    Returns the member of the UnitTypes enum which is associated with the input unit.
    @param unit Unit whose type will be returned.  
    @return UnitTypes variable associated with unit.    
    **/
    public static UnitTypes GetUnitType(Units unit)
    {
        return MethodsCommon.GetTypeFromUnit(unit);
    }

    /**
    Returns the member of the UnitSystems enum which is associated with the input unit.
    @param unit Unit whose system will be returned. 
    @return UnitSystems variable associated with unit.  
    **/
    public static UnitSystems GetUnitSystem(Units unit)
    {
        return MethodsCommon.GetSystemFromUnit(unit, false, true);
    }

    /**
    Removes the global prefix of the input UnitP variable.
    @param unitP variable whose prefix will be removed. 
    @return UnitP variable containing all the unitP information without relying on the global prefix (i.e., UnitP.UnitPrefix.Factor = 1.0).   
    **/
    public static UnitP RemoveGlobalPrefix(UnitP unitP)
    {
        return new UnitP
        (
            Managed.NormaliseUnitInfo
            (
            	ExceptionInstantiation.NewUnitInfo(unitP)
            ), 
            unitP, true
        );
    }

    /**
    Transfers all the base-ten exponent information to the Value field (if possible).  
    @param unitP variable whose base-ten exponent will be removed. 
    @return UnitP variable containing all the unitP information by reducing the global base-ten exponent (i.e., UnitP.BaseTenExponent = 0) value as much as possible.      
    **/ 
    public static UnitP RemoveBaseTen(UnitP unitP)
    {
        UnitInfo tempInfo = Managed.ConvertBaseTenToValue
        (
        	ExceptionInstantiation.NewUnitInfo(unitP)
        );

        return new UnitP(unitP, tempInfo.Value, tempInfo.BaseTenExponent);
    }

    
    //---------------------------- Public non-static methods.
    
    /**
    Converts the current unit into the target one. Different unit types will trigger an error.
    targetPrefix = null
    @param targetUnit Conversion target unit.
    @return Current instance converted to targetUnit.    
    **/
    public UnitP ConvertCurrentUnitTo(Units targetUnit)
    {
        return MethodsUnitP.ConvertToCommon(this, targetUnit, null);
    }
    
    /**
    Converts the current unit into the target one. Different unit types will trigger an error.
    @param targetUnit Conversion target unit.
    @param targetPrefix Prefix of the conversion target unit.
    @return Current instance converted to targetUnit and targetPrefix.  
    **/
    public UnitP ConvertCurrentUnitTo(Units targetUnit, Prefix targetPrefix)
    {
        return MethodsUnitP.ConvertToCommon(this, targetUnit, targetPrefix);
    }

    /**
    Converts the current unit into the target one. Different unit types will trigger an error.
    @param targetUnitString String representation of the conversion target unit.
    @return Current instance converted to targetUnitString.  
    **/
    public UnitP ConvertCurrentUnitTo(String targetUnitString)
    {
        return MethodsUnitP.ConvertToCommon(this, targetUnitString);
    }

    /**
    Returns the String representations associated with the current unit.
    otherStringsToo = false
    @return ArrayList including all the primary string representations associated with the current instance unit.             
    **/
    public final ArrayList<String> GetStringsForCurrentUnit()
    {
        return GetStringsForCurrentUnit(false);
    }
    
    /**
    Returns the String representations associated with the current unit.
    @param otherStringsToo When true, all the supported String representations (case doesn't matter) other than symbols (case matters) are also included.  
    @return ArrayList including all the (primary or primary and secondary) string representations associated with the current instance unit.    
    **/
    public final ArrayList<String> GetStringsForCurrentUnit(boolean otherStringsToo)
    {
        return MethodsUnitP.GetStringsUnitCommon(Unit, otherStringsToo);
    }

    /**
    Returns the String representations associated with the current unit type.
    otherStringsToo = false
    @return ArrayList including all the primary string representations associated with the current instance type.       
    **/       
    public final ArrayList<String> GetStringsForCurrentType()
    {
        return GetStringsForCurrentType(false);
    }
    
    /**
    Returns the String representations associated with the current unit type.
    @param otherStringsToo When true, all the supported String representations (case doesn't matter) other than symbols (case matters) are also included. 
    @return ArrayList including all the (primary or primary and secondary) string representations associated with the current instance type.   
    **/       
    public final ArrayList<String> GetStringsForCurrentType(boolean otherStringsToo)
    {
        return MethodsUnitP.GetStringsTypeCommon(UnitType, otherStringsToo);
    }

    /**
    Returns the String representations associated with the current unit type and system.
    otherStringsToo = false
    @return ArrayList including all the primary string representations associated with the current instance type and system.   
    **/
    public final ArrayList<String> GetStringsForCurrentTypeAndSystem()
    {
        return GetStringsForCurrentTypeAndSystem(false);
    }
    
    /**
    Returns the String representations associated with the current unit type and system.
    @param otherStringsToo When true, all the supported String representations (case doesn't matter) other than symbols (case matters) are also included.  
    @return ArrayList including all the (primary or primary and secondary) string representations associated with the current instance type and system.   
    **/
    public final ArrayList<String> GetStringsForCurrentTypeAndSystem(boolean otherStringsToo)
    {
        return MethodsUnitP.GetStringsTypeAndSystemCommon
        (
        	UnitType, UnitSystem, otherStringsToo
        );
    }

    /**
    Returns the members of the Units enum which are associated with the current unit type. 
    @return ArrayList including all the units associated with the current instance type.   
    **/
    public final ArrayList<Units> GetUnitsForCurrentType()
    {
        return MethodsUnitP.GetUnitsTypeCommon(UnitType);
    }

    /**
    Returns the members of the Units enum which are associated with the current unit type and system.
    @return ArrayList including all the units associated with the current instance type and system.  
    **/
    public final ArrayList<Units> GetUnitsForCurrentTypeAndSystem()
    {
        return MethodsUnitP.GetUnitsTypeAndSystemCommon(UnitType, UnitSystem);
    }

    /**
    Returns the member of the UnitTypes enum which is associated with the current unit.
    @return UnitTypes variable associated with the current instance.    
    **/
    public UnitTypes GetCurrentUnitType()
    {
        return MethodsCommon.GetTypeFromUnit(Unit);
    }

    /**
    Returns the member of the UnitSystems enum which is associated with the current unit.  
    @return UnitSystems variable associated with the current instance.      
    **/
    public UnitSystems GetCurrentUnitSystem()
    {
        return MethodsCommon.GetSystemFromUnit(Unit, false, true);
    }

    /**
    Removes the global prefix of the current UnitP variable.
    @return Removes the global prefix (i.e., UnitP.UnitPrefix.Factor = 1.0) from the current instance.         
    **/
    public UnitP RemoveCurrentGlobalPrefix()
    {
        return new UnitP
        (
            Managed.NormaliseUnitInfo
            (
            	ExceptionInstantiation.NewUnitInfo(this)
            ), 
            this, true
        );
    }

    /**
    Transfers all the base-ten exponent information to the Value field (if possible).
    @return Reduces the global base-ten exponent (i.e., UnitP.BaseTenExponent = 0) value of the current instance as much as possible.         
    **/
    public UnitP RemoveCurrentBaseTen()
    {
        UnitInfo tempInfo = Managed.ConvertBaseTenToValue
        (
        	ExceptionInstantiation.NewUnitInfo(this)
        );
        
        return new UnitP(this, tempInfo.Value, tempInfo.BaseTenExponent);
    }

    
    //---------------------------- Public error-related classes.
    /**Contains the main information associated with errors and exceptions.**/
    public static class ErrorInfo
    {
		/**Error type.**/
		private UnitP.ErrorTypes Type;
		/**
		Error type getter.
		@return Current error type. 
		**/
		public UnitP.ErrorTypes getType()
		{
		    return Type;
		}  
		
		/**Exception handling type.**/
		private UnitP.ExceptionHandlingTypes ExceptionHandling;
		/**
		Exception handling type getter.
		@return Current exception handling type. 
		**/
		public UnitP.ExceptionHandlingTypes getExceptionHandling()
		{
		    return ExceptionHandling;
		}  
		
		/**Error message.**/
		private String Message;  
		/**
		Error message getter.
		@return Current error message. 
		**/
		public String getMessage()
		{
		    return Message;
		}  
        
        /**Initialises a new ErrorInfo instance.**/
        public ErrorInfo() 
        {
            Type = UnitP.ErrorTypes.None;
            ExceptionHandling = UnitP.ExceptionHandlingTypes.AlwaysTriggerException;
            Message = "";      
        }
        
        /**
        Initialises a new ErrorInfo instance.
        @param errorInfo ErrorInfo variable whose information will be used.
    	**/
        public ErrorInfo(ErrorInfo errorInfo) 
        {
            if (errorInfo == null) errorInfo = new ErrorInfo();

            Type = errorInfo.Type;
            ExceptionHandling = errorInfo.ExceptionHandling;
            Message = errorInfo.Message;
        }
        
        /**Initialises a new ErrorInfo instance.
        @param type Member of the UnitP.ErrorTypes enum to be used.
        **/    
        public ErrorInfo(UnitP.ErrorTypes type) throws Exception
        {
        	this(type, UnitP.ExceptionHandlingTypes.NeverTriggerException);
        }
        
        /**Initialises a new ErrorInfo instance.
        @param type Member of the UnitP.ErrorTypes enum to be used.
        @param exceptionHandling Member of the UnitP.ExceptionHandlingTypes enum to be used.
        **/
        public ErrorInfo(UnitP.ErrorTypes type, UnitP.ExceptionHandlingTypes exceptionHandling) throws Exception
        {
            Type = type;
            ExceptionHandling = exceptionHandling;
            Message = GetMessage(type);

            if (type != UnitP.ErrorTypes.None && ExceptionHandling == UnitP.ExceptionHandlingTypes.AlwaysTriggerException)
            {
                throw new Exception(Message);
            }  
        } 
        
        private String GetMessage(UnitP.ErrorTypes type)
        {
            String outString = "";

            if (type == UnitP.ErrorTypes.InvalidOperation)
            {
                outString = "Invalid Operation. Some operands are incompatible among them or refer to invalid units.";
            }
            else if (type == UnitP.ErrorTypes.InvalidUnit)
            {
                outString = "Invalid Input. The input string doesn't match any supported unit.";
            }
            else if (type == UnitP.ErrorTypes.NumericError)
            {
                outString = "Numeric Error. An invalid mathematical operation has been performed.";
            }
            else if (type == UnitP.ErrorTypes.NumericParsingError)
            {
                outString = "Numeric Parsing Error. The input doesn't match the expected number + space + unit format.";
            }
            else if (type == UnitP.ErrorTypes.InvalidUnitConversion)
            {
                outString = "Invalid Unit Conversion. The unit conversion cannot be performed with these inputs.";
            }

            return outString;
        }

        @Override
        /**
        Determines whether the current ErrorInfo instance is equal to other one.
        @param obj Other variable.
        **/
        public boolean equals(Object obj)
        {
            return Equals((ErrorInfo)obj);
        }
        
        boolean Equals(ErrorInfo other)
        {
            return
            (
                other == null ? false :
                Equals.ErrorsAreEqual(this, other)
            );
        }

        @Override
        /**Returns the hash code for this ErrorInfo instance.**/
        public int hashCode() { return 0; }
    }
    
    /**Contains all the supported error types.**/
    public enum ErrorTypes 
    {
        /**No error.**/
        None,
        
        /**
        Associated with invalid operations between UnitP variables not provoked by numeric errors.
        Examples: addition of UnitP variables with different types; multiplication of UnitP variables outputting an unsupported unit.
        **/
        InvalidOperation,
        /**
        Associated with faulty instantiations of UnitP variables.
        Examples: relying on an unsupported input string; including prefixes not supported in that specific context.
        **/
        InvalidUnit,
        /**
        Associated with invalid operations provoked by numeric overflow or arithmetic errors.
        Examples: multiplication of too big values; division by zero.
        **/
        NumericError,
        /**
        Associated with errors triggered when parsing numeric inputs.
        Example: new UnitP("no-number unit") rather than new UnitP("number unit").
        **/
        NumericParsingError,
        /**
        Associated with invalid or unsupported conversions.
        Example: UnitP("1 m/s").ConvertCurrentUnitTo("m/s2").
        **/
        InvalidUnitConversion,
    }
    
    /**Determines whether errors trigger an exception or not.**/
    public enum ExceptionHandlingTypes
    {
    	/**Errors never trigger an exception. Equivalent to standard .NET TryParse methods.**/
    	NeverTriggerException,
    	/**Error always trigger an exception. Equivalent to standard .NET Parse methods.**/
    	AlwaysTriggerException
    }
    
    
    //---------------------------- Public arithmetic operations emulating operator overloads in the original C# code.
       
    /**
    Adds two UnitP variables by giving preference to the configuration of the first operand.
    Different unit types will trigger an error.
    @param first Augend. In case of incompatibilities, its configuration would prevail.
    @param second Addend.
    @return UnitP variable resulting from adding first and second.
    **/
    public static UnitP Addition(UnitP first, UnitP second)
    {
        return OperationsPublic.PerformUnitOperation
        (
            first, second, Operations.Addition,
            OperationsOther.GetOperationString(first, second, Operations.Addition)
        );
    }

    /**
    Subtracts two UnitP variables by giving preference to the configuration of the first operand.
    Different unit types will trigger an error.       
    @param first Minuend. In case of incompatibilities, its configuration would prevail.
    @param second Subtrahend.
    @return UnitP variable resulting from subtracting first and second.
    **/
    public static UnitP Subtraction(UnitP first, UnitP second)
    {
        return OperationsPublic.PerformUnitOperation
        (
            first, second, Operations.Subtraction,
            OperationsOther.GetOperationString(first, second, Operations.Subtraction)
        );
    }

    /**
    Multiplies two UnitP variables by giving preference to the configuration of the first operand.
    Different unit types will trigger an error.     
    @param first Multiplicand. In case of incompatibilities, its configuration would prevail.
    @param second Multiplier.
    @return UnitP variable resulting from multiplying first and second.    
    **/
    public static UnitP Multiplication(UnitP first, UnitP second)
    {
        return OperationsPublic.PerformUnitOperation
        (
            first, second, Operations.Multiplication,
            OperationsOther.GetOperationString(first, second, Operations.Multiplication)                
        );
    }

    /**
    Multiplies the value of a UnitP variable by a double one.
    Eventual errors will be managed as defined in first.ExceptionHandling.        
    @param first Multiplicand.
    @param second Multiplier.
    @return UnitP variable resulting from multiplying first and second.   
    **/
    public static UnitP Multiplication(UnitP first, double second)
    {
        return OperationsPublic.PerformUnitOperation
        (
            first, second, Operations.Multiplication, 
            OperationsOther.GetOperationString(first, second, Operations.Multiplication)
        );
    }

    /**
    Multiplies a double variable by the value of a UnitP one.
    Eventual errors will be managed as defined by the double type.
    @param first Multiplicand.
    @param second Multiplier.
    @return UnitP variable resulting from multiplying first and second.   
    **/
    public static UnitP Multiplication(double first, UnitP second)
    {
        return OperationsPublic.PerformUnitOperation
        (
            first, second, Operations.Multiplication,
            OperationsOther.GetOperationString(first, second, Operations.Multiplication)
        );
    }

    /**
    Divides two UnitP variables by giving preference to the configuration of the first operand.
    Different unit types will trigger an error.        
    @param first Dividend. In case of incompatibilities, its configuration would prevail.
    @param second Divisor.
    @return UnitP variable resulting from dividing first and second.   
    **/
    public static UnitP Division(UnitP first, UnitP second)
    {
        return OperationsPublic.PerformUnitOperation
        (
            first, second, Operations.Division,
            OperationsOther.GetOperationString(first, second, Operations.Division)                
        );
    }

    /**
    Divides the value of a UnitP variable by a double one.
    Eventual errors will be managed as defined in first.ExceptionHandling.      
    @param first Dividend.
    @param second Divisor.
    @return UnitP variable resulting from dividing first and second.   
    **/
    public static UnitP Division(UnitP first, double second)
    {
        return OperationsPublic.PerformUnitOperation
        (
            first, second, Operations.Division, 
            OperationsOther.GetOperationString(first, second, Operations.Division)
        );
    }

    /**
    Divides a double variable by the value of a UnitP one.
    Eventual errors will be managed as defined by the double type.            
    @param first Dividend.
    @param second Divisor.
    @return UnitP variable resulting from dividing first and second.   
    **/
    public static UnitP Division(double first, UnitP second)
    {
        return OperationsPublic.PerformUnitOperation
        (
            first, second, Operations.Division,
            OperationsOther.GetOperationString(first, second, Operations.Division)
        );
    }

	@Override
    /**
    Compares the current instance against another UnitP one.
    @param other The other UnitP instance.
     **/
    public int compareTo(UnitP other)
    {
        return
        (
            this.BaseTenExponent == other.BaseTenExponent ? 
            new Double(this.Value * this.UnitPrefix.getFactor()).compareTo
            (
            	other.Value * other.UnitPrefix.getFactor()
            ) :
            this.BaseTenExponent.compareTo(other.BaseTenExponent)
        );
    }

    @Override
    /**
    Determines whether the current UnitP instance is equal to other one.
    @param obj Other variable.
     **/
    public boolean equals(Object obj)
    {
        return Equals((UnitP)obj);
    }
    
    boolean Equals(UnitP other)
    {
        return
        (
            other == null ? false :
            Equals.UnitPVarsAreEqual(this, other)
        );
    }

    @Override
    /**Returns the hash code for this UnitP instance.**/
    public int hashCode() { return 0; }
}
