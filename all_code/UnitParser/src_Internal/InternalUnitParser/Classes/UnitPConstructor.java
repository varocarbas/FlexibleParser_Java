package InternalUnitParser.Classes;

import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Methods.*;
import UnitParser.*;
import UnitParser.UnitP.*;

//Class helping to deal with the relevant number of constructors including quite a few readonly variables.
public class UnitPConstructor
{
    public Double Value;
    public String OriginalUnitString, UnitString, ValueAndUnitString;
    public UnitTypes UnitType;
    public UnitSystems UnitSystem;
    public UnitInfo UnitInfo;
    public ErrorTypes ErrorType;
    public ExceptionHandlingTypes ExceptionHandling;

    public UnitPConstructor(String originalUnitString, UnitInfo unitInfo) 
    {
    	this(originalUnitString, unitInfo, UnitTypes.None, UnitSystems.None, unitInfo.Error.getType());
    }
    
    public UnitPConstructor(String originalUnitString, UnitInfo unitInfo, UnitTypes unitType)
    {
    	this(originalUnitString, unitInfo,  unitType, UnitSystems.None);
    }
    
    public UnitPConstructor
    (
    	String originalUnitString, UnitInfo unitInfo, 
    	UnitTypes unitType, UnitSystems unitSystem
    )
    {
    	this(originalUnitString, unitInfo, unitType, unitSystem, ErrorTypes.None);
    }
    
    public UnitPConstructor
    (
        String originalUnitString, UnitInfo unitInfo, UnitTypes unitType, 
        UnitSystems unitSystem, ErrorTypes errorType
    )
    {
    	this
        (
            originalUnitString, unitInfo, unitType, unitSystem, 
            errorType, ExceptionHandlingTypes.NeverTriggerException
        );
    }
    
    public UnitPConstructor
    (
        String originalUnitString, UnitInfo unitInfo, UnitTypes unitType, 
        UnitSystems unitSystem, ErrorTypes errorType, ExceptionHandlingTypes exceptionHandling
    )
    {
    	this
        (
            originalUnitString, unitInfo, unitType, unitSystem, 
            errorType, exceptionHandling, false
        );
    }
    
    public UnitPConstructor
    (
        String originalUnitString, UnitInfo unitInfo, UnitTypes unitType, 
        UnitSystems unitSystem, ErrorTypes errorType,
        ExceptionHandlingTypes exceptionHandling, boolean noPrefixImprovement
    )
    {
    	this
        (
            originalUnitString, unitInfo, unitType, 
            unitSystem, errorType, exceptionHandling, 
            noPrefixImprovement, true
        );
    }
    
    public UnitPConstructor
    (
        String originalUnitString, UnitInfo unitInfo, UnitTypes unitType, 
        UnitSystems unitSystem, ErrorTypes errorType,
        ExceptionHandlingTypes exceptionHandling,
        boolean noPrefixImprovement, boolean improveFinalValue
    )
    {
        OriginalUnitString =
        (
            originalUnitString == null ? "" :
            originalUnitString.trim()
        );
        ErrorType = errorType;
        ExceptionHandling = exceptionHandling;

        if (ErrorType != ErrorTypes.None)
        {
            UnitInfo = ExceptionInstantiation.NewUnitInfo();
        }
        else
        {
            UnitInfo = MethodsUnitP.ImproveUnitInfo(unitInfo, noPrefixImprovement);

            UnitType =
            (
                UnitInfo.Type != UnitTypes.None ? UnitInfo.Type :
                MethodsCommon.GetTypeFromUnitInfo(UnitInfo)
            );
            UnitSystem =
            (
                UnitInfo.System != UnitSystems.None && UnitInfo.System != UnitSystems.Imperial ?
                UnitInfo.System : MethodsCommon.GetSystemFromUnit(UnitInfo.Unit, false, true)
            );
            if (UnitSystem == UnitSystems.Imperial && UnitInfo.Unit == Units.ValidImperialUSCSUnit)
            {
                UnitInfo.Unit = Units.ValidImperialUnit;
            }
            UnitString = MethodsCommon.GetUnitString(UnitInfo);

            Value =
            (
                improveFinalValue ?
                //Values like 1.999999 are assumed to be a not-that-good version of 2.0 + some precision loss.
                //This assumption doesn't hold every time (e.g., input value which wasn't part of any operation).
                MethodsUnitP.ImproveFinalValue(UnitInfo.Value) :
                UnitInfo.Value
            );

            ValueAndUnitString = Value.toString() +
            (
                UnitInfo.BaseTenExponent != 0 ?
                "*10^" + UnitInfo.BaseTenExponent.toString() : ""
            )
            + " " + UnitString;
        }
    }
}
