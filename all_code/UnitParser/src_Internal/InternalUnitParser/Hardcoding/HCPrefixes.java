package InternalUnitParser.Hardcoding;

import UnitParser.*;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class HCPrefixes
{
    //Relates the SI prefixes (SIPrefixes enum) with their values (constants in the SIPrefixValues class).
    public static HashMap<SIPrefixes, Double> AllSIPrefixes;

    //Relates the SI prefix strings (SIPrefixSymbols enum) with their values (constants in the SIPrefixValues class).
    public static HashMap<String, Double> AllSIPrefixSymbols;

    //Relates the binary prefixes (BinaryPrefixes enum) with their values (constants in the BinaryPrefixValues class).
    public static HashMap<BinaryPrefixes, Double> AllBinaryPrefixes;

    //Relates the binary prefix strings (BinaryPrefixSymbols enum) with their values (constants in the BinaryPrefixValues class).
    public static HashMap<String, Double> AllBinaryPrefixSymbols;

    //Contains all the units outside the SI-prefix-supporting systems (i.e., UnitSystems.SI & UnitSystems.CGS)
    //which do support SI prefixes by default.
	public static ArrayList<Units> AllOtherSIPrefixUnits;

    //By default, global prefixes aren't used with compounds to avoid misunderstandings. For example: 1000 m^2 converted
    //into k m^2 confused as km2. This collection includes all the compounds which might use prefixes.
	public static ArrayList<Units> AllCompoundsUsingPrefixes;

    //Includes all the unit types which support binary prefixes by default.
    public static ArrayList<UnitTypes> AllBinaryPrefixTypes;

    //All the collections below this line are secondary/used for very specific purposes. That's why they 
    //are only populated when being used for the first time.

    //The full names of all the SI/binary prefixes.
    public static HashMap<String, String> AllSIPrefixNames, AllBinaryPrefixNames;

    //Various collections used in prefix-related calculations.
    public static ArrayList<Double> BigSIPrefixValues, SmallSIPrefixValues, BigBinaryPrefixValues, SmallBinaryPrefixValues;
    
	public static void Start()
	{
		AllBinaryPrefixTypes = new ArrayList<UnitTypes>()
	    {{
	    	add(UnitTypes.Information); add(UnitTypes.BitRate); add(UnitTypes.SymbolRate);
	    }};
	    
	    AllCompoundsUsingPrefixes = new ArrayList<Units>()
	    {{
	        //--- Area
			add(Units.Rood); add(Units.Acre); add(Units.SurveyAcre); 
	        
	        //--- Volume
	        add(Units.FluidOunce); add(Units.ImperialFluidOunce); add(Units.USCSFluidOunce); add(Units.Gill); 
	        add(Units.ImperialGill); add(Units.USCSGill); add(Units.Pint); add(Units.ImperialPint); add(Units.LiquidPint); 
	        add(Units.DryPint); add(Units.Quart); add(Units.ImperialQuart); add(Units.LiquidQuart); add(Units.DryQuart); 
	        add(Units.Gallon); add(Units.LiquidGallon); add(Units.DryGallon); 
	       
	        //--- Velocity
	        add(Units.Knot); 

	        //--- Acceleration
	        add(Units.Gal); 

	        //--- Force
	        add(Units.Newton); add(Units.Dyne); add(Units.PoundForce); add(Units.Poundal); add(Units.OunceForce); 
	        
	        //--- Energy
	        add(Units.Joule); add(Units.Erg); add(Units.WattHour); add(Units.Calorie); add(Units.ThermochemicalCalorie);  
	        add(Units.FoodCalorie); add(Units.BritishThermalUnit); add(Units.ThermochemicalBritishThermalUnit);  
	        add(Units.Therm); add(Units.UKTherm); add(Units.USTherm); 
	        
	        //--- Power
	        add(Units.Watt); add(Units.Horsepower); add(Units.MetricHorsepower); add(Units.ElectricHorsepower);  
	        add(Units.BoilerHorsepower); add(Units.TonOfRefrigeration); 

	        //--- Pressure
	        add(Units.Pascal); add(Units.Barye); add(Units.Atmosphere); add(Units.TechnicalAtmosphere); add(Units.Bar); 
	        add(Units.Torr); 

	        //--- Frequency
	        add(Units.Hertz); 

	        //--- Electric Charge
	        add(Units.AmpereHour); 

	        //--- Electric Current
	        add(Units.Ampere); add(Units.Statampere); add(Units.Abampere); add(Units.Biot); 

	        //--- Electric Voltage
	        add(Units.Volt); add(Units.Statvolt); add(Units.Abvolt);  

	        //--- Electric Resitance
	        add(Units.Ohm); add(Units.Statohm); add(Units.Abohm);  

	        //--- Electric Conductance
	        add(Units.Siemens); add(Units.Mho); add(Units.Gemmho); add(Units.Statsiemens); add(Units.Statmho); 
	        add(Units.Absiemens); add(Units.Abmho); 

	        //--- Electric Capacitance
	        add(Units.Farad); add(Units.Statfarad); add(Units.Abfarad); 

	        //--- Electric Inductance
	        add(Units.Henry); add(Units.Stathenry); add(Units.Abhenry); 

	        //--- Electric Dipole Moment
	        add(Units.Debye); 

	        //--- Wavenumber
	        add(Units.Kayser); 

	        //--- Viscosity
	        add(Units.Poise); 

	        //--- Kinematic Viscosity
	        add(Units.Stokes); 

	        //--- Solid Angle
	        add(Units.Steradian); 

	        //--- Luminous Flux
	        add(Units.Lumen); 

	        //--- Luminous Energy
	        add(Units.Talbot); 

	        //--- Luminance
	        add(Units.Stilb); add(Units.Nit); add(Units.Lambert); add(Units.FootLambert); 

	        //--- Illuminance
	        add(Units.Lux); add(Units.Phot); add(Units.FootCandle); 

	        //--- Magnetic Flux
	        add(Units.Weber); add(Units.Maxwell); 

	        //--- Magnetic Field H
	        add(Units.Oersted); 

	        //--- Magnetic Field B
	        add(Units.Tesla); add(Units.Gauss); 

	        //--- Absorbed Dose
	        add(Units.Gray); add(Units.Rad); 

	        //--- Equivalent Dose
	        add(Units.Sievert); add(Units.REM); 

	        //--- Exposure
	        add(Units.Roentgen); 
	       
	        //--- Catalytic Activity
	        add(Units.Katal); 
	       
	        //--- Bit Rate
	        add(Units.BitPerSecond);     
	    }};
	    
	    AllOtherSIPrefixUnits = new ArrayList<Units>()
	    {{
	        //--- Length
	        add(Units.Parsec);

	        //--- Mass
	        add(Units.MetricTon); add(Units.Dalton); add(Units.UnifiedAtomicMassUnit);
	        
	        //--- Area
	        add(Units.Are); add(Units.Barn);
	        
	        //--- Volume
	        add(Units.Litre);
	        
	        //--- Information
	        add(Units.Bit); add(Units.Byte); add(Units.Nibble); add(Units.Quartet); add(Units.Octet);
	        
	        //--- Energy
	        add(Units.Electronvolt); add(Units.WattHour); add(Units.Calorie); add(Units.ThermochemicalCalorie);
	        
	        //--- Pressure
	        add(Units.Bar); add(Units.Torr);
	        
	        //--- Electric Charge
	        add(Units.AmpereHour);

	        //--- Logarithmic
	        add(Units.Bel); add(Units.Neper);
	        
	        //--- Radioactivity
	        add(Units.Curie);
	        
	        //--- Bit Rate
	        add(Units.BitPerSecond);

	        //--- Symbol Rate
	        add(Units.Baud);
	    }};
	    
	    AllBinaryPrefixSymbols = new HashMap<String, Double>()
	    {
			{
				put(BinaryPrefixSymbols.Kibi, BinaryPrefixValues.Kibi); 
				put(BinaryPrefixSymbols.Mebi, BinaryPrefixValues.Mebi);
				put(BinaryPrefixSymbols.Gibi, BinaryPrefixValues.Gibi); 
				put(BinaryPrefixSymbols.Tebi, BinaryPrefixValues.Tebi);
				put(BinaryPrefixSymbols.Pebi, BinaryPrefixValues.Pebi); 
				put(BinaryPrefixSymbols.Exbi, BinaryPrefixValues.Exbi);
				put(BinaryPrefixSymbols.Zebi, BinaryPrefixValues.Zebi); 
				put(BinaryPrefixSymbols.Yobi, BinaryPrefixValues.Yobi);
			}
	    };
	    
	    AllBinaryPrefixes = new HashMap<BinaryPrefixes, Double>()
	    {
			{	
				put(BinaryPrefixes.Kibi, BinaryPrefixValues.Kibi); 
				put(BinaryPrefixes.Mebi, BinaryPrefixValues.Mebi);
				put(BinaryPrefixes.Gibi, BinaryPrefixValues.Gibi); 
				put(BinaryPrefixes.Tebi, BinaryPrefixValues.Tebi);
				put(BinaryPrefixes.Pebi, BinaryPrefixValues.Pebi); 
				put(BinaryPrefixes.Exbi, BinaryPrefixValues.Exbi);
				put(BinaryPrefixes.Zebi, BinaryPrefixValues.Zebi); 
				put(BinaryPrefixes.Yobi, BinaryPrefixValues.Yobi);
			}
	    };
	    
	    AllSIPrefixSymbols =  new HashMap<String, Double>()
	    {
			{	
				put(SIPrefixSymbols.Yotta, SIPrefixValues.Yotta); 
				put(SIPrefixSymbols.Zetta, SIPrefixValues.Zetta);
				put(SIPrefixSymbols.Exa, SIPrefixValues.Exa); 
				put(SIPrefixSymbols.Peta, SIPrefixValues.Peta);
				put(SIPrefixSymbols.Tera, SIPrefixValues.Tera);
				put(SIPrefixSymbols.Giga, SIPrefixValues.Giga);
				put(SIPrefixSymbols.Mega, SIPrefixValues.Mega);
				put(SIPrefixSymbols.Kilo, SIPrefixValues.Kilo);
				put(SIPrefixSymbols.Hecto, SIPrefixValues.Hecto); 
				put(SIPrefixSymbols.Deca, SIPrefixValues.Deca);
				put(SIPrefixSymbols.Deci, SIPrefixValues.Deci); 
				put(SIPrefixSymbols.Centi, SIPrefixValues.Centi);
				put(SIPrefixSymbols.Milli, SIPrefixValues.Milli); 
				put(SIPrefixSymbols.Micro, SIPrefixValues.Micro);
				put(SIPrefixSymbols.Nano, SIPrefixValues.Nano); 
				put(SIPrefixSymbols.Pico, SIPrefixValues.Pico);
				put(SIPrefixSymbols.Femto, SIPrefixValues.Femto); 
				put(SIPrefixSymbols.Atto, SIPrefixValues.Atto);
				put(SIPrefixSymbols.Zepto, SIPrefixValues.Zepto); 
				put(SIPrefixSymbols.Yocto, SIPrefixValues.Yocto);
			}
	    };
	    
	    AllSIPrefixes = new HashMap<SIPrefixes, Double>()
	    {
			{
				put(SIPrefixes.Yotta, SIPrefixValues.Yotta); 
				put(SIPrefixes.Zetta, SIPrefixValues.Zetta);
				put(SIPrefixes.Exa, SIPrefixValues.Exa); 
				put(SIPrefixes.Peta, SIPrefixValues.Peta);
				put(SIPrefixes.Tera, SIPrefixValues.Tera); 
				put(SIPrefixes.Giga, SIPrefixValues.Giga);
				put(SIPrefixes.Mega, SIPrefixValues.Mega); 
				put(SIPrefixes.Kilo, SIPrefixValues.Kilo);
				put(SIPrefixes.Hecto, SIPrefixValues.Hecto); 
				put(SIPrefixes.Deca, SIPrefixValues.Deca);
				put(SIPrefixes.Deci, SIPrefixValues.Deci); 
				put(SIPrefixes.Centi, SIPrefixValues.Centi);
				put(SIPrefixes.Milli, SIPrefixValues.Milli); 
				put(SIPrefixes.Micro, SIPrefixValues.Micro);
				put(SIPrefixes.Nano, SIPrefixValues.Nano); 
				put(SIPrefixes.Pico, SIPrefixValues.Pico);
				put(SIPrefixes.Femto, SIPrefixValues.Femto); 
				put(SIPrefixes.Atto, SIPrefixValues.Atto);
				put(SIPrefixes.Zepto, SIPrefixValues.Zepto); 
				put(SIPrefixes.Yocto, SIPrefixValues.Yocto);
			}
	    };
	}
}
