package InternalUnitParser.Classes;

import InternalUnitParser.Methods.*;
import InternalUnitParser.Operations.*;
import UnitParser.*;
import UnitParser.UnitP.*;

import java.util.ArrayList;
import java.util.HashMap;


//Class storing all the unit-related information. 
//It includes the main numeric variables (Value, Prefix.Factor & BaseTenExponent) and, consequently,
//is also the main class when dealing with managed operations (i.e., error-free-ly dealing with numbers of any size).
//This class is part of the internal/private code of the original C# version and users aren't expected to rely on it at all.
public class UnitInfo
{
	public Double Value;
	public Units Unit;
	public UnitSystems System;
	public UnitTypes Type;
	public Prefix Prefix;
	public ArrayList<UnitPart> Parts;
	public ErrorInfo Error;
	public String TempString;
	public Integer BaseTenExponent;
	//Collection storing the positions of the unit parts as input by the user. 
	public HashMap<UnitPart, Integer> InitialPositions;

	public UnitInfo() throws Exception { this(0.0); }

	public UnitInfo(Units unit) throws Exception { this(unit, 1.0); }
	
	public UnitInfo(Units unit, Double prefixFactor) throws Exception { this(0.0, unit, new Prefix(prefixFactor)); }
	
	public UnitInfo(Double value, Units unit, Prefix prefix, boolean getParts) throws Exception
	{
		this(value, unit, prefix, getParts, ExceptionHandlingTypes.NeverTriggerException);
	}
	
	public UnitInfo(Double value, Units unit, Prefix prefix) throws Exception
	{
		this(value, unit, prefix, true, ExceptionHandlingTypes.NeverTriggerException);
	}
	
	public UnitInfo
	(
		Double value, Units unit, Prefix prefix, boolean getParts, 
		ExceptionHandlingTypes exceptionHandling
	) 
	throws Exception
	{
		PopulateVariables
		(
			value, unit, prefix, (getParts ? null : new ArrayList<UnitPart>()),
			new HashMap<UnitPart, Integer>(), 0, UnitTypes.None, UnitSystems.None,
			new ErrorInfo(ErrorTypes.None, exceptionHandling)
		);
	}
	
	public UnitInfo(UnitInfo unitInfo) throws Exception
	{
		this(unitInfo, ErrorTypes.None);
	}
	
	public UnitInfo(UnitInfo unitInfo, ErrorTypes error) throws Exception
	{
		if (unitInfo == null) unitInfo = new UnitInfo();
	
		PopulateVariables
		(
			unitInfo.Value, unitInfo.Unit, unitInfo.Prefix, 
			unitInfo.Parts, MethodsCommon.GetInitialPositions(unitInfo.Parts),
			unitInfo.BaseTenExponent, unitInfo.Type, unitInfo.System,
			(error != ErrorTypes.None ? new ErrorInfo(error) : unitInfo.Error)
		);
	}
	
	public UnitInfo(Double value, ExceptionHandlingTypes exceptionHandling, PrefixUsageTypes prefixUsage) throws Exception
	{
		UnitInfo unitInfo = new UnitInfo(value);
	
		PopulateVariables
		(
			unitInfo.Value, unitInfo.Unit, new Prefix(unitInfo.Prefix.getFactor(), prefixUsage),
			unitInfo.Parts, MethodsCommon.GetInitialPositions(unitInfo.Parts),
			unitInfo.BaseTenExponent, unitInfo.Type, unitInfo.System, 
			new ErrorInfo(ErrorTypes.None, exceptionHandling)
		);
	}
	
	public UnitInfo(UnitP unitP) throws Exception
	{
		if (unitP == null)
		{
			Prefix = new Prefix();
			Parts = new ArrayList<UnitPart>();
			Error = new ErrorInfo();
			return;
		}
	
		ArrayList<UnitPart> unitParts = new ArrayList<UnitPart>();
		if (unitP.getUnitParts() != null && unitP.getUnitParts().size() > 0)
		{
			unitParts = new ArrayList<UnitPart>(unitP.getUnitParts());
		}
	
		PopulateVariables
		(
			unitP.getValue(), unitP.getUnit(), unitP.getUnitPrefix(), unitParts,
			MethodsCommon.GetInitialPositions(unitParts), unitP.getBaseTenExponent(),
			unitP.getUnitType(), unitP.getUnitSystem(), unitP.getError()
		);
	}
	
	public UnitInfo(Double value) throws Exception
	{
		this(value, 0);
	}
	
	public UnitInfo(Double value, int baseTenExponent) throws Exception
	{
		PopulateVariables(value, Units.None, new Prefix(), null, null, baseTenExponent);
	}
	
	void PopulateVariables
	(
		Double value, Units unit, Prefix prefix, ArrayList<UnitPart> parts, HashMap<UnitPart, Integer> initialPositions
	) 
	throws Exception
	{
		PopulateVariables(value, unit, prefix, parts, initialPositions, 0);
	}
	
	void PopulateVariables
	(
		Double value, Units unit, Prefix prefix, ArrayList<UnitPart> parts, HashMap<UnitPart, Integer> initialPositions,
		int baseTenExponent
	) 
	throws Exception
	{
		PopulateVariables(value, unit, prefix, parts, initialPositions, baseTenExponent, UnitTypes.None);
	}
	
	void PopulateVariables
	(
		Double value, Units unit, Prefix prefix, ArrayList<UnitPart> parts, HashMap<UnitPart, Integer> initialPositions,
		int baseTenExponent, UnitTypes type
	) 
	throws Exception
	{
		PopulateVariables(value, unit, prefix, parts, initialPositions, baseTenExponent, type, UnitSystems.None);
	}
	
	void PopulateVariables
	(
		Double value, Units unit, Prefix prefix, ArrayList<UnitPart> parts,
		HashMap<UnitPart, Integer> initialPositions, int baseTenExponent,
		UnitTypes type, UnitSystems system
	) 
	throws Exception
	{
		PopulateVariables(value, unit, prefix, parts, initialPositions, baseTenExponent, type, system, null);
	}
	
	void PopulateVariables
	(
		Double value, Units unit, Prefix prefix, ArrayList<UnitPart> parts,
		HashMap<UnitPart, Integer> initialPositions, int baseTenExponent,
		UnitTypes type, UnitSystems system, ErrorInfo errorInfo
	) 
	throws Exception
	{
		Value = value;
		BaseTenExponent = baseTenExponent;
		Unit = unit;
		Prefix = new Prefix(prefix);	
		if (parts == null)
		{
			Parts = new ArrayList<UnitPart>();
			UnitInfo tempInfo = MethodsCommon.GetUnitParts
			(
				new UnitInfo(1.0, Unit, Prefix, false)
			);	

			if (tempInfo.Value != 1 || tempInfo.BaseTenExponent != 0)
			{
				//While getting the parts, some automatic conversions might occur and
				//the associated values have to be accounted for.
				tempInfo = Managed.NormaliseUnitInfo(tempInfo);
				BaseTenExponent += tempInfo.BaseTenExponent;
				try
				{
					Value *= tempInfo.Value;
				}
				catch(Exception e)
				{
					try
					{
						//Very unlikely scenario, but possible.
						Value /= 10;
						BaseTenExponent += 1;
						Value *= tempInfo.Value;
					}
					catch(Exception e2)
					{
						//The only way to reach this point is in case that the maximum
						//BaseTenExponent (i.e., int.MaxValue) has been reached. There
						//is only one possible output for such an scenario: an error.
						errorInfo = new ErrorInfo(ErrorTypes.NumericError);
					}
				}
			}
			Parts = tempInfo.Parts;
			InitialPositions = MethodsCommon.GetInitialPositions(Parts);
		}
		else
		{
			InitialPositions = new HashMap<UnitPart, Integer>(initialPositions);
			Parts = new ArrayList<UnitPart>(parts);
		}
		System = system;
		Type = type;
		TempString = "";
		Error = new ErrorInfo(errorInfo);	
	}
}