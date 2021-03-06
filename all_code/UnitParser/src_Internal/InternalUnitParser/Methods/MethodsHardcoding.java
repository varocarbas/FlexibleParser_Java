package InternalUnitParser.Methods;

import InternalUnitParser.Classes.*;
import InternalUnitParser.CSharpAdaptation.*;
import InternalUnitParser.Hardcoding.*;
import UnitParser.*;

import java.util.HashMap;
import java.util.Map;

public class MethodsHardcoding
{
    //Extracts all the AllUnits information and stores it in more-usable specific collections.
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap<Units, UnitTypes> GetAllMain()
    {
    	HashMap<Units, UnitTypes> outDict = new HashMap<Units, UnitTypes>();
        HCMain.AllUnitConversionFactors = new HashMap<Units, Double>();
        HCMain.AllUnitSystems = new HashMap<Units, UnitSystems>();
        HCMain.AllUnitStrings = new HashMap<String, Units>();

        for (Map.Entry item : HCMain.AllUnits.entrySet()) 
        {
            for (Map.Entry item2 : ((HashMap<UnitSystems, HashMap<Units, Double>>)item.getValue()).entrySet()) 
            {
                for (Map.Entry item3 : ((HashMap<Units, Double>)item2.getValue()).entrySet()) 
                {
                    outDict.put((Units)item3.getKey(), (UnitTypes)item.getKey());
                    HCMain.AllUnitSystems.put((Units)item3.getKey(), (UnitSystems)item2.getKey());
                    HCMain.AllUnitConversionFactors.put((Units)item3.getKey(), (Double)item3.getValue());
                    
                    if (!MethodsCommon.IsUnnamedUnit((Units)item3.getKey()) && StoreUnitNameIsOK((Units)item3.getKey()))
                    {
                        String unitName = ((Units)item3.getKey()).toString().toLowerCase();
                        if (!HCMain.AllUnitStrings.containsKey(unitName))
                        {
                        	HCMain.AllUnitStrings.put(unitName, (Units)item3.getKey());
                        }
                    }
                }
            }
        }
        

        PopulateAllUnitStrings();
        PopulateUnitSymbols2();
        PopulateBeyondDecimalConversionFactors();

        HCMain.AllUnits = null;

        return outDict;
    }

    static void PopulateBeyondDecimalConversionFactors()
    {
    	HCMain.AllBeyondDecimalConversionFactors = new HashMap<Double, UnitInfo>();

        //Barn (b). Actual conversion factor 1E-28.
        HCMain.AllBeyondDecimalConversionFactors.put
        (
            UnitConversionFactors.Barn, ExceptionInstantiation.NewUnitInfo(1.0, -28)
        );

        //Debye (D). Actual conversion factor 3.33564095E-30.
        HCMain.AllBeyondDecimalConversionFactors.put
        (
            UnitConversionFactors.Debye, ExceptionInstantiation.NewUnitInfo(3.33564095, -30)
        );
    }

    //All the unit names are stored in AllUnitStrings, whose elements are treated case insensitively.
    //Not storing one of them would mean that all the associated String representations are always
    //treated case-sensitively to avoid confusions.
    static boolean StoreUnitNameIsOK(Units unit)
    {
        return
        (
            unit == Units.Rad //rad/radian.
            || unit == Units.Rutherford //rutherford/rod.
            || unit == Units.Gal //gal/gallon.
            ? false : true
        );
    }

    //Stores additional symbols (case does matter) for some units.
    static void PopulateUnitSymbols2()
    {
    	
        HCMain.AllUnitSymbols2 = new HashMap<String, Units>();

        HCMain.AllUnitSymbols2.put("nmi", Units.NauticalMile);
        HCMain.AllUnitSymbols2.put("ftm", Units.Fathom);
        HCMain.AllUnitSymbols2.put("th", Units.Thou);
        HCMain.AllUnitSymbols2.put("lnk", Units.Link);
        HCMain.AllUnitSymbols2.put("fm", Units.Fermi);
        HCMain.AllUnitSymbols2.put("psc", Units.Parsec);
        HCMain.AllUnitSymbols2.put("l", Units.Litre);
        HCMain.AllUnitSymbols2.put("p", Units.Pint);
        HCMain.AllUnitSymbols2.put("impp", Units.ImperialPint);
        HCMain.AllUnitSymbols2.put("uscp", Units.LiquidPint);
        AddToAllUnitStrings("liquidp", Units.LiquidPint);
        AddToAllUnitStrings("dryp", Units.DryPint);
        HCMain.AllUnitSymbols2.put("cm3", Units.CubicCentimetre);
        HCMain.AllUnitSymbols2.put("lbm", Units.Pound);
        HCMain.AllUnitSymbols2.put("car", Units.Carat);
        HCMain.AllUnitSymbols2.put("kgf", Units.Kilopond);
        HCMain.AllUnitSymbols2.put("r", Units.Revolution);
        HCMain.AllUnitSymbols2.put("stC", Units.Statcoulomb);
        HCMain.AllUnitSymbols2.put("stA", Units.Statampere);
        HCMain.AllUnitSymbols2.put("stV", Units.Statvolt);
        HCMain.AllUnitSymbols2.put("stO", Units.Statohm);
        HCMain.AllUnitSymbols2.put("stS", Units.Statsiemens);
        HCMain.AllUnitSymbols2.put("st℧", Units.Statmho);
        HCMain.AllUnitSymbols2.put("stF", Units.Statfarad);
        HCMain.AllUnitSymbols2.put("stH", Units.Stathenry);
    }

    //Populates all the unit String representations which aren't symbols (case doesn't matter).
    static void PopulateAllUnitStrings()
    {
        Units unit = Units.Unitless;
        AddToAllUnitStrings("ul", unit);

        unit = Units.Metre;
        AddToAllUnitStrings("meter", unit);
        AddToAllUnitStrings("mtr", unit);

        unit = Units.Centimetre;
        AddToAllUnitStrings("centimeter", unit);

        unit = Units.AstronomicalUnit;
        AddToAllUnitStrings("ua", unit);

        unit = Units.SurveyInch;
        AddToAllUnitStrings("usin", unit);

        unit = Units.SurveyFoot;
        AddToAllUnitStrings("usft", unit);

        unit = Units.SurveyYard;
        AddToAllUnitStrings("usyd", unit);

        unit = Units.SurveyRod;
        AddToAllUnitStrings("usrd", unit);

        unit = Units.SurveyChain;
        AddToAllUnitStrings("usch", unit);

        unit = Units.SurveyLink;
        AddToAllUnitStrings("usli", unit);

        unit = Units.SurveyMile;
        AddToAllUnitStrings("usmi", unit);

        unit = Units.SurveyFathom;
        AddToAllUnitStrings("usfathom", unit);

        unit = Units.MetricTon;
        AddToAllUnitStrings("tonne", unit);

        unit = Units.Drachm;
        AddToAllUnitStrings("dram", unit);

        unit = Units.LongQuarter;
        AddToAllUnitStrings("longqr", unit);
        AddToAllUnitStrings("ukqr", unit);

        unit = Units.ShortQuarter;
        AddToAllUnitStrings("shortqr", unit);
        AddToAllUnitStrings("usqr", unit);

        unit = Units.LongHundredweight;
        AddToAllUnitStrings("longcwt", unit);
        AddToAllUnitStrings("ukcwt", unit);

        unit = Units.ShortHundredweight;
        AddToAllUnitStrings("shortcwt", unit);
        AddToAllUnitStrings("uscwt", unit);

        //Plural support is automatically added to all the String representations (e.g., the ones added here), 
        //but not to the symbols. For example: the aforementioned mtr reference already includes mtrs.
        unit = Units.Gram;
        AddToAllUnitStrings("gs", unit);

        unit = Units.Pound;
        AddToAllUnitStrings("lbs", unit);

        unit = Units.LongTon;
        AddToAllUnitStrings("longtn", unit);
        AddToAllUnitStrings("uktn", unit);

        unit = Units.ShortTon;
        AddToAllUnitStrings("shorttn", unit);
        AddToAllUnitStrings("ustn", unit);

        unit = Units.UnifiedAtomicMassUnit;
        AddToAllUnitStrings("amu", unit);

        unit = Units.Second;
        AddToAllUnitStrings("sec", unit);

        unit = Units.Hour;
        AddToAllUnitStrings("hr", unit);

        unit = Units.SquareMetre;
        AddToAllUnitStrings("squaremeter", unit);
        AddSqCuToAllUnitStrings(new String[] { "m" }, unit);
        
        unit = Units.SquareCentimetre;
        AddToAllUnitStrings("squarecentimeter", unit);
        AddSqCuToAllUnitStrings(new String[] { "cm" }, unit);

        unit = Units.SquareFoot;
        AddSqCuToAllUnitStrings(new String[] { "ft" }, unit);

        unit = Units.SquareInch;
        AddSqCuToAllUnitStrings(new String[] { "in" }, unit);

        unit = Units.SquareRod;
        AddSqCuToAllUnitStrings(new String[] { "rd" }, unit);

        unit = Units.SquarePerch;
        AddSqCuToAllUnitStrings(new String[] { "perch" }, unit);

        unit = Units.SquarePole;
        AddSqCuToAllUnitStrings(new String[] { "pole" }, unit);

        unit = Units.CubicMetre;
        AddToAllUnitStrings("cubicmeter", unit);
        AddSqCuToAllUnitStrings(new String[] { "m" }, unit, false);

        unit = Units.CubicCentimetre;
        AddToAllUnitStrings("cubiccentimeter", unit);
        AddSqCuToAllUnitStrings(new String[] { "cm" }, unit, false);

        unit = Units.CubicFoot;
        AddSqCuToAllUnitStrings(new String[] { "ft" }, unit, false);

        unit = Units.CubicInch;
        AddSqCuToAllUnitStrings(new String[] { "in" }, unit, false);

        unit = Units.Litre;
        AddToAllUnitStrings("liter", unit);
        AddToAllUnitStrings("ltr", unit);

        unit = Units.ImperialFluidOunce;
        AddToAllUnitStrings("ukfloz", unit);

        unit = Units.USCSFluidOunce;
        AddToAllUnitStrings("usfloz", unit);

        unit = Units.ImperialGill;
        AddToAllUnitStrings("ukgi", unit);

        unit = Units.USCSGill;
        AddToAllUnitStrings("usgi", unit);

        unit = Units.ImperialPint;
        AddToAllUnitStrings("ukpt", unit);
        AddToAllUnitStrings("ukp", unit);

        unit = Units.LiquidPint;
        AddToAllUnitStrings("uspt", unit);
        AddToAllUnitStrings("usp", unit);

        unit = Units.ImperialQuart;
        AddToAllUnitStrings("ukqt", unit);

        unit = Units.LiquidQuart;
        AddToAllUnitStrings("usqt", unit);

        unit = Units.ImperialGallon;
        AddToAllUnitStrings("ukgal", unit);

        unit = Units.LiquidGallon;
        AddToAllUnitStrings("usgal", unit);

        unit = Units.Degree;
        AddToAllUnitStrings("deg", unit);

        unit = Units.Arcsecond;
        AddToAllUnitStrings("arcsec", unit);

        unit = Units.Arcminute;
        AddToAllUnitStrings("arcmin", unit);

        unit = Units.BritishThermalUnit;
        AddToAllUnitStrings("btu", unit);

        unit = Units.ThermochemicalBritishThermalUnit;
        AddToAllUnitStrings("thbtu", unit);

        unit = Units.Therm;
        AddToAllUnitStrings("ecthm", unit);

        unit = Units.TonOfRefrigeration;
        AddToAllUnitStrings("tr", unit);
        AddToAllUnitStrings("rt", unit);

        unit = Units.DegreeCelsius;
        AddToAllUnitStrings("degC", unit);

        unit = Units.DegreeFahrenheit;
        AddToAllUnitStrings("degF", unit);

        unit = Units.DegreeRankine;
        AddToAllUnitStrings("degR", unit);

        unit = Units.ReciprocalMetre;
        AddToAllUnitStrings("inversemetre", unit);
        AddToAllUnitStrings("inversemeter", unit);
        AddToAllUnitStrings("reciprocalmeter", unit);

        unit = Units.InverseSquareMetre;
        AddToAllUnitStrings("reciprocalsquaremetre", unit);
        AddToAllUnitStrings("inversesquaremeter", unit);
        AddToAllUnitStrings("reciprocalsquaremeter", unit);

        unit = Units.ImperialMilePerGallon;
        AddToAllUnitStrings("ukmpg", unit);

        unit = Units.USCSMilePerGallon;
        AddToAllUnitStrings("usmpg", unit);

        unit = Units.BitPerSecond;
        AddToAllUnitStrings("bps", unit);
    }
    
    static void AddSqCuToAllUnitStrings(String[] symbols, Units unit)
    {
    	AddSqCuToAllUnitStrings(symbols, unit, true);
    }

    static void AddSqCuToAllUnitStrings(String[] symbols, Units unit, boolean square)
    {
        String addition = (square ? "sq" : "cu");

        for (String symbol: symbols)
        {
            AddToAllUnitStrings(addition + symbol, unit); 
        }
    }

    static void AddToAllUnitStrings(String item, Units unit)
    {
        if (!HCMain.AllUnitStrings.containsKey(item))
        {
        	HCMain.AllUnitStrings.put(item, unit);
        }
    }
}