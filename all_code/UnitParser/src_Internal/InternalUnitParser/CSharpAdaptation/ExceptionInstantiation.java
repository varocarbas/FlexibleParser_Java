package InternalUnitParser.CSharpAdaptation;

import InternalUnitParser.Classes.*;
import UnitParser.*;
import UnitParser.UnitP.*;

import java.util.ArrayList;

//Class storing methods which instantiate the used constructors of the exception-triggering (which cannot always be included inside try...catch)
//classes to avoid using the invasive throws clauses (they would have to be added virtually everywhere).
public class ExceptionInstantiation
{
	public static UnitInfo NewUnitInfo()
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo();
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(double value, int baseTenExponent)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(value, baseTenExponent);
        }
        catch(Exception e) { }
        
        return output;		
	}

	public static UnitInfo NewUnitInfo(Units unit)
	{
    	return NewUnitInfo(unit, 1.0);
	}
	
	public static UnitInfo NewUnitInfo(Units unit, double factor)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unit, factor);
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(double value, Units unit, Prefix prefix)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(value, unit, prefix);
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(double value)
	{
    	return NewUnitInfo(value, null);
	}
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitInfo);
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(double value, ExceptionHandlingTypes exceptionHandling, PrefixUsageTypes prefixUsage)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(value, exceptionHandling, prefixUsage);
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(double value, ArrayList<UnitPart> parts)
	{
    	UnitInfo output = null;

        try
        {
        	output = new UnitInfo(value);
       	
        	if (parts != null) 
        	{
        		output.Parts = new ArrayList<UnitPart>(parts);
        	}
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(ArrayList<UnitPart> parts)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo();
        	output.Parts = new ArrayList<UnitPart>(parts);
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo, double value)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitInfo);
        	output.Value = value;
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo, ErrorTypes errorType)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitInfo);
        	output.Error = new ErrorInfo(errorType);
        }
        catch(Exception e) { }
        
        return output;
	}

	public static UnitInfo NewUnitInfo(ErrorTypes errorType)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo();
        	output.Error = new ErrorInfo(errorType);
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo, Units unit)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitInfo);
        	output.Unit = unit;
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo, Units unit, double factor)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitInfo);
        	output.Unit = unit;
        	output.Prefix = new Prefix(factor);
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo, double value, int baseTenExponent)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitInfo);
        	output.Value = value;
        	output.BaseTenExponent = baseTenExponent;
        }
        catch(Exception e) { }
        
        return output;
	}	
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo, Prefix prefix)
	{
    	return NewUnitInfo(unitInfo, unitInfo.BaseTenExponent, prefix);		
	}
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo, int baseTenExponent, Prefix prefix)
	{
    	return NewUnitInfo(unitInfo, unitInfo.Value, baseTenExponent, prefix);
	}
	
	public static UnitInfo NewUnitInfo(UnitP unitP, double value, int baseTenExponent, Prefix prefix)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitP);
        	output.BaseTenExponent = baseTenExponent;
        	output.Prefix = new Prefix(prefix);
        	output.Value = value;
        }
        catch(Exception e) { }
        
        return output;		
	}
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo, double value, int baseTenExponent, Prefix prefix)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitInfo);
        	output.BaseTenExponent = baseTenExponent;
        	output.Prefix = new Prefix(prefix);
        	output.Value = value;
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(UnitInfo unitInfo, Units unit, ArrayList<UnitPart> parts)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitInfo);
        	output.Unit = unit;
        	output.Parts = new ArrayList<UnitPart>(parts);
        }
        catch(Exception e) { }
        
        return output;
	}

	public static UnitInfo NewUnitInfo(double value, Units unit, Prefix prefix, boolean getParts)
	{
    	return NewUnitInfo
    	(
    		value, unit, prefix, getParts, ExceptionHandlingTypes.AlwaysTriggerException
    	);
	}	
	
	public static UnitInfo NewUnitInfo(double value, Units unit, Prefix prefix, boolean getParts, ExceptionHandlingTypes exceptionHandling)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(value, unit, prefix, getParts, exceptionHandling);
        }
        catch(Exception e) { }
        
        return output;
	}
	
	public static UnitInfo NewUnitInfo(UnitP unitP)
	{
    	UnitInfo output = null;
    	
        try
        {
        	output = new UnitInfo(unitP);
        }
        catch(Exception e) { }
        
        return output;
	}	
	
	public static ErrorInfo NewErrorInfo(ErrorTypes errorType)
	{
    	ErrorInfo output = null;
    	
        try
        {
        	output = new ErrorInfo(errorType);
        }
        catch(Exception e) { }
        
        return output;
	}	
    
	public static ErrorInfo NewErrorInfo(ErrorTypes errorType, ExceptionHandlingTypes exceptionHandling)
	{
    	ErrorInfo output = null;
    	
        try
        {
        	output = new ErrorInfo(errorType, exceptionHandling);
        }
        catch(Exception e) { }
        
        return output;
	}		
}