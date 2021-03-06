package InternalUnitParser.Hardcoding;

import UnitParser.*;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class HCUnits
{
	//Relates the primary string representations (constants in the UnitSymbols class) with the corresponding
	//unit (element of the Units enum).
    public static HashMap<String, Units> AllUnitSymbols;
    
    //Includes all the units with compound (= dividable by default) types which cannot be divided.
	public static ArrayList<Units> AllNonDividableUnits;

    //English-system units which are identical in both Imperial and USCS.
	public static ArrayList<Units> AllImperialAndUSCSUnits;  
    
	public static void Start()
	{
		AllUnitSymbols = new HashMap<String, Units>()
	    {
	        //--- Unitless
	        { put("unitless", Units.Unitless); }

	        //--- Length
	        { put(UnitSymbols.Metre, Units.Metre); }
	        { put(UnitSymbols.Centimetre, Units.Centimetre); }
	        { put(UnitSymbols.AstronomicalUnit, Units.AstronomicalUnit); }      
	        { put(UnitSymbols.Inch, Units.Inch); }           
	        { put(UnitSymbols.Foot, Units.Foot); }             
	        { put(UnitSymbols.Yard, Units.Yard); }            
	        { put(UnitSymbols.Mile, Units.Mile); }              
	        { put(UnitSymbols.NauticalMile, Units.NauticalMile); }                     
	        { put(UnitSymbols.Thou, Units.Thou); }         
	        { put(UnitSymbols.Mil, Units.Mil); }          
	        { put(UnitSymbols.Fathom, Units.Fathom); }  
	        { put(UnitSymbols.Rod, Units.Rod); }
	        { put(UnitSymbols.Perch, Units.Perch); }
	        { put(UnitSymbols.Pole, Units.Pole); }
	        { put(UnitSymbols.Chain, Units.Chain); }         
	        { put(UnitSymbols.Furlong, Units.Furlong); } 
	        { put(UnitSymbols.SurveyInch, Units.SurveyInch); } 
	        { put(UnitSymbols.SurveyFoot, Units.SurveyFoot); } 
	        { put(UnitSymbols.SurveyYard, Units.SurveyYard); } 
	        { put(UnitSymbols.SurveyRod, Units.SurveyRod); } 
	        { put(UnitSymbols.SurveyChain, Units.SurveyChain); }  
	        { put(UnitSymbols.SurveyLink, Units.SurveyLink); }
	        { put(UnitSymbols.SurveyMile, Units.SurveyMile); } 
	        { put(UnitSymbols.SurveyFathom, Units.SurveyFathom); }
	        { put(UnitSymbols.Link, Units.Link); } 
	        { put(UnitSymbols.Angstrom, Units.Angstrom); } 
	        { put(UnitSymbols.Fermi, Units.Fermi); } 
	        { put(UnitSymbols.LightYear, Units.LightYear); } 
	        { put(UnitSymbols.Parsec, Units.Parsec); } 
	        { put(UnitSymbols.Micron, Units.Micron); } 

	        //--- Mass
	        { put(UnitSymbols.Gram, Units.Gram); } 
	        { put(UnitSymbols.MetricTon, Units.MetricTon); } 
	        { put(UnitSymbols.Grain, Units.Grain); } 
	        { put(UnitSymbols.Drachm, Units.Drachm); }
	        { put(UnitSymbols.Ounce, Units.Ounce); }          
	        { put(UnitSymbols.Pound, Units.Pound); }  
	        { put(UnitSymbols.Stone, Units.Stone); }           
	        { put(UnitSymbols.Slug, Units.Slug); }      
	        { put(UnitSymbols.Quarter, Units.Quarter); }                
	        { put(UnitSymbols.LongQuarter, Units.LongQuarter); }          
	        { put(UnitSymbols.ShortQuarter, Units.ShortQuarter); }    
	        { put(UnitSymbols.Hundredweight, Units.Hundredweight); }        
	        { put(UnitSymbols.LongHundredweight, Units.LongHundredweight); }   
	        { put(UnitSymbols.ShortHundredweight, Units.ShortHundredweight); } 
	        { put(UnitSymbols.Ton, Units.Ton); }   
	        { put(UnitSymbols.LongTon, Units.LongTon); }            
	        { put(UnitSymbols.ShortTon, Units.ShortTon); } 
	        { put(UnitSymbols.Carat, Units.Carat); } 
	        { put(UnitSymbols.Dalton, Units.Dalton); } 
	        { put(UnitSymbols.UnifiedAtomicMassUnit, Units.UnifiedAtomicMassUnit); } 

	        //--- Time
	        { put(UnitSymbols.Second, Units.Second); } 
	        { put(UnitSymbols.Minute, Units.Minute); }  
	        { put(UnitSymbols.Hour, Units.Hour); }    
	        { put(UnitSymbols.Day, Units.Day); }  
	        { put(UnitSymbols.Shake, Units.Shake); } 

	        //--- Area
	        { put(UnitSymbols.SquareMetre, Units.SquareMetre); } 
	        { put(UnitSymbols.SquareCentimetre, Units.SquareCentimetre); } 
	        { put(UnitSymbols.Are, Units.Are); } 
	        { put(UnitSymbols.SquareFoot, Units.SquareFoot); }
	        { put(UnitSymbols.SquareInch, Units.SquareInch); }
	        { put(UnitSymbols.SquareRod, Units.SquareRod); } 
	        { put(UnitSymbols.SquarePerch, Units.SquarePerch); } 
	        { put(UnitSymbols.SquarePole, Units.SquarePole); }  
	        { put(UnitSymbols.Rood, Units.Rood); } 
	        { put(UnitSymbols.Acre, Units.Acre); } 
	        { put(UnitSymbols.SurveyAcre, Units.SurveyAcre); } 
	        { put(UnitSymbols.Barn, Units.Barn); }  

	        //--- Volume
	        { put(UnitSymbols.CubicMetre, Units.CubicMetre); } 
	        { put(UnitSymbols.CubicCentimetre, Units.CubicCentimetre); } 
	        { put(UnitSymbols.Litre, Units.Litre); } 
	        { put(UnitSymbols.CubicFoot, Units.CubicFoot); }
	        { put(UnitSymbols.CubicInch, Units.CubicInch); }
	        { put(UnitSymbols.FluidOunce, Units.FluidOunce); }   
	        { put(UnitSymbols.ImperialFluidOunce, Units.ImperialFluidOunce); }  
	        { put(UnitSymbols.USCSFluidOunce, Units.USCSFluidOunce); }
	        { put(UnitSymbols.Gill, Units.Gill); }   
	        { put(UnitSymbols.ImperialGill, Units.ImperialGill); }          
	        { put(UnitSymbols.USCSGill, Units.USCSGill); } 
	        { put(UnitSymbols.Pint, Units.Pint); }  
	        { put(UnitSymbols.ImperialPint, Units.ImperialPint); }             
	        { put(UnitSymbols.LiquidPint, Units.LiquidPint); }  
	        { put(UnitSymbols.DryPint, Units.DryPint); } 
	        { put(UnitSymbols.Quart, Units.Quart); }  
	        { put(UnitSymbols.ImperialQuart, Units.ImperialQuart); }           
	        { put(UnitSymbols.LiquidQuart, Units.LiquidQuart); }                        
	        { put(UnitSymbols.DryQuart, Units.DryQuart); }  
	        { put(UnitSymbols.Gallon, Units.Gallon); }          
	        { put(UnitSymbols.ImperialGallon, Units.ImperialGallon); }            
	        { put(UnitSymbols.LiquidGallon, Units.LiquidGallon); }      
	        { put(UnitSymbols.DryGallon, Units.DryGallon); }           

	        //--- Angle
	        { put(UnitSymbols.Radian, Units.Radian); } 
	        { put(UnitSymbols.Degree, Units.Degree); } 
	        { put(UnitSymbols.Arcminute, Units.Arcminute); }             
	        { put(UnitSymbols.Arcsecond, Units.Arcsecond); }                          
	        { put(UnitSymbols.Revolution, Units.Revolution); }             
	        { put(UnitSymbols.Gradian, Units.Gradian); }    
	        { put(UnitSymbols.Gon, Units.Gon); }
	        
	        //--- Information
	        { put(UnitSymbols.Bit, Units.Bit); }     
	        { put(UnitSymbols.Byte, Units.Byte); }  
	        { put(UnitSymbols.Nibble, Units.Nibble); }  
	        { put(UnitSymbols.Quartet, Units.Quartet); }          
	        { put(UnitSymbols.Octet, Units.Octet); }           

	        //--- Force
	        { put(UnitSymbols.Newton, Units.Newton); }  
	        { put(UnitSymbols.Kilopond, Units.Kilopond); }   
	        { put(UnitSymbols.PoundForce, Units.PoundForce); }            
	        { put(UnitSymbols.Kip, Units.Kip); }              
	        { put(UnitSymbols.Poundal, Units.Poundal); }                   
	        { put(UnitSymbols.OunceForce, Units.OunceForce); }                
	        { put(UnitSymbols.Dyne, Units.Dyne); }            

	        //--- Velocity
	        { put(UnitSymbols.MetrePerSecond, Units.MetrePerSecond); }           
	        { put(UnitSymbols.CentimetrePerSecond, Units.CentimetrePerSecond); }  
	        { put(UnitSymbols.FootPerSecond, Units.FootPerSecond); } 
	        { put(UnitSymbols.InchPerSecond, Units.InchPerSecond); } 
	        { put(UnitSymbols.Knot, Units.Knot); } 
	        { put(UnitSymbols.KilometrePerHour, Units.KilometrePerHour); } 
	        { put(UnitSymbols.MilePerHour, Units.MilePerHour); } 

	        //--- Acceleration
	        { put(UnitSymbols.MetrePerSquareSecond, Units.MetrePerSquareSecond); }
	        { put(UnitSymbols.Gal, Units.Gal); } 
	        { put(UnitSymbols.FootPerSquareSecond, Units.FootPerSquareSecond); } 
	        { put(UnitSymbols.InchPerSquareSecond, Units.InchPerSquareSecond); } 

	        //--- Energy
	        { put(UnitSymbols.Joule, Units.Joule); }   
	        { put(UnitSymbols.Electronvolt, Units.Electronvolt); }            
	        { put(UnitSymbols.WattHour, Units.WattHour); }       
	        { put(UnitSymbols.BritishThermalUnit, Units.BritishThermalUnit); }             
	        { put(UnitSymbols.ThermochemicalBritishThermalUnit, Units.ThermochemicalBritishThermalUnit); }               
	        { put(UnitSymbols.Calorie, Units.Calorie); }            
	        { put(UnitSymbols.ThermochemicalCalorie, Units.ThermochemicalCalorie); }                
	        { put(UnitSymbols.FoodCalorie, Units.FoodCalorie); }               
	        { put(UnitSymbols.Erg, Units.Erg); }                
	        { put(UnitSymbols.Therm, Units.Therm); }                  
	        { put(UnitSymbols.UKTherm, Units.UKTherm); }               
	        { put(UnitSymbols.USTherm, Units.USTherm); }              

	        //--- Power
	        { put(UnitSymbols.Watt, Units.Watt); }          
	        { put(UnitSymbols.ErgPerSecond, Units.ErgPerSecond); }   
	        { put(UnitSymbols.Horsepower, Units.Horsepower); }               
	        { put(UnitSymbols.MetricHorsepower, Units.MetricHorsepower); }       
	        { put(UnitSymbols.BoilerHorsepower, Units.BoilerHorsepower); }     
	        { put(UnitSymbols.ElectricHorsepower, Units.ElectricHorsepower); }      
	        { put(UnitSymbols.TonOfRefrigeration, Units.TonOfRefrigeration); }             

	        //--- Pressure
	        { put(UnitSymbols.Pascal, Units.Pascal); }    
	        { put(UnitSymbols.Atmosphere, Units.Atmosphere); }     
	        { put(UnitSymbols.TechnicalAtmosphere, Units.TechnicalAtmosphere); }        
	        { put(UnitSymbols.Bar, Units.Bar); }             
	        { put(UnitSymbols.PoundforcePerSquareInch, Units.PoundforcePerSquareInch); }       
	        { put(UnitSymbols.PoundforcePerSquareFoot, Units.PoundforcePerSquareFoot); }              
	        { put(UnitSymbols.MillimetreOfMercury, Units.MillimetreOfMercury); }             
	        { put(UnitSymbols.InchOfMercury32F, Units.InchOfMercury32F); }              
	        { put(UnitSymbols.InchOfMercury60F, Units.InchOfMercury60F); }               
	        { put(UnitSymbols.Barye, Units.Barye); }              
	        { put(UnitSymbols.Torr, Units.Torr); }               
	        { put(UnitSymbols.KipPerSquareInch, Units.KipPerSquareInch); }                

	        //--- Frequency
	        { put(UnitSymbols.Hertz, Units.Hertz); }                   
	        { put(UnitSymbols.CyclePerSecond, Units.CyclePerSecond); }              

	        //--- Electric Charge
	        { put(UnitSymbols.Coulomb, Units.Coulomb); } 
	        { put(UnitSymbols.AmpereHour, Units.AmpereHour); }
	        { put(UnitSymbols.Franklin, Units.Franklin); } 
	        { put(UnitSymbols.Statcoulomb, Units.Statcoulomb); } 
	        { put(UnitSymbols.ESUOfCharge, Units.ESUOfCharge); } 
	        { put(UnitSymbols.Abcoulomb, Units.Abcoulomb); } 
	        { put(UnitSymbols.EMUOfCharge, Units.EMUOfCharge); } 

	        //--- Electric Current
	        { put(UnitSymbols.Ampere, Units.Ampere); }  
	        { put(UnitSymbols.Statampere, Units.Statampere); } 
	        { put(UnitSymbols.ESUOfCurrent, Units.ESUOfCurrent); } 
	        { put(UnitSymbols.Abampere, Units.Abampere); } 
	        { put(UnitSymbols.EMUOfCurrent, Units.EMUOfCurrent); } 
	        { put(UnitSymbols.Biot, Units.Biot); } 

	        //--- Electric Voltage
	        { put(UnitSymbols.Volt, Units.Volt); }  
	        { put(UnitSymbols.Statvolt, Units.Statvolt); } 
	        { put(UnitSymbols.ESUOfElectricPotential, Units.ESUOfElectricPotential); } 
	        { put(UnitSymbols.Abvolt, Units.Abvolt); } 
	        { put(UnitSymbols.EMUOfElectricPotential, Units.EMUOfElectricPotential); } 

	        //--- Electric Resistance 
	        { put(UnitSymbols.Ohm, Units.Ohm); }  
	        { put(UnitSymbols.Statohm, Units.Statohm); } 
	        { put(UnitSymbols.ESUOfResistance, Units.ESUOfResistance); } 
	        { put(UnitSymbols.Abohm, Units.Abohm); } 
	        { put(UnitSymbols.EMUOfResistance, Units.EMUOfResistance); } 
	        
	        //--- Electric Resistivity 
	        { put(UnitSymbols.OhmMetre, Units.OhmMetre); }  

	        //--- Electric Conductance
	        { put(UnitSymbols.Siemens, Units.Siemens); }  
	        { put(UnitSymbols.Mho, Units.Mho); } 
	        { put(UnitSymbols.Gemmho, Units.Gemmho); } 
	        { put(UnitSymbols.Statsiemens, Units.Statsiemens); } 
	        { put(UnitSymbols.Statmho, Units.Statmho); } 
	        { put(UnitSymbols.Absiemens, Units.Absiemens); } 
	        { put(UnitSymbols.Abmho, Units.Abmho); } 

	        //--- Electric Conductivity
	        { put(UnitSymbols.SiemensPerMetre, Units.SiemensPerMetre); }  

	        //--- Electric Capacitance
	        { put(UnitSymbols.Farad, Units.Farad); }  
	        { put(UnitSymbols.Statfarad, Units.Statfarad); } 
	        { put(UnitSymbols.ESUOfCapacitance, Units.ESUOfCapacitance); } 
	        { put(UnitSymbols.Abfarad, Units.Abfarad); } 
	        { put(UnitSymbols.EMUOfCapacitance, Units.EMUOfCapacitance); } 

	        //--- Electric Inductance
	        { put(UnitSymbols.Henry, Units.Henry); }  
	        { put(UnitSymbols.Stathenry, Units.Stathenry); } 
	        { put(UnitSymbols.ESUOfInductance, Units.ESUOfInductance); } 
	        { put(UnitSymbols.Abhenry, Units.Abhenry); } 
	        { put(UnitSymbols.EMUOfInductance, Units.EMUOfInductance); } 

	        //--- Electric Dipole Moment
	        { put(UnitSymbols.CoulombMetre, Units.CoulombMetre); }  
	        { put(UnitSymbols.Debye, Units.Debye); }  

	        //--- Temperature
	        { put(UnitSymbols.Kelvin, Units.Kelvin); }  
	        { put(UnitSymbols.DegreeCelsius, Units.DegreeCelsius); } 
	        { put(UnitSymbols.DegreeFahrenheit, Units.DegreeFahrenheit); }            
	        { put(UnitSymbols.DegreeRankine, Units.DegreeRankine); }    

	        //--- Wavenumber
	        { put(UnitSymbols.ReciprocalMetre, Units.ReciprocalMetre); } 
	        { put(UnitSymbols.Kayser, Units.Kayser); }  

	        //--- Viscosity
	        { put(UnitSymbols.PascalSecond, Units.PascalSecond); }  
	        { put(UnitSymbols.Poise, Units.Poise); }  

	        //--- Kinematic Viscosity
	        { put(UnitSymbols.SquareMetrePerSecond, Units.SquareMetrePerSecond); }  
	        { put(UnitSymbols.Stokes, Units.Stokes); }  

	        //--- Amount of Substance
	        { put(UnitSymbols.Mole, Units.Mole); }                 
	        { put(UnitSymbols.PoundMole, Units.PoundMole); } 
	        
	        //--- Momentum
	        { put(UnitSymbols.NewtonSecond, Units.NewtonSecond); }   
	        
	        //--- Angular Velocity
	        { put(UnitSymbols.RadianPerSecond, Units.RadianPerSecond); }
	        { put(UnitSymbols.RevolutionPerMinute, Units.RevolutionPerMinute); }  
	        
	        //--- Angular Acceleration
	        { put(UnitSymbols.RadianPerSquareSecond, Units.RadianPerSquareSecond); }
	        
	        //--- Angular Momentum
	        { put(UnitSymbols.JouleSecond, Units.JouleSecond); }
	        
	        //--- Moment of Inertia
	        { put(UnitSymbols.KilogramSquareMetre, Units.KilogramSquareMetre); }

	        //--- Solid Angle
	        { put(UnitSymbols.Steradian, Units.Steradian); }                
	        { put(UnitSymbols.SquareDegree, Units.SquareDegree); }                 

	        //--- Luminous Intensity
	        { put(UnitSymbols.Candela, Units.Candela); }                

	        //--- Luminous Flux
	        { put(UnitSymbols.Lumen, Units.Lumen); }                  

	        //--- Luminous Energy
	        { put(UnitSymbols.LumenSecond, Units.LumenSecond); }   
	        { put(UnitSymbols.Talbot, Units.Talbot); } 

	        //--- Luminance
	        { put(UnitSymbols.CandelaPerSquareMetre, Units.CandelaPerSquareMetre); }                
	        { put(UnitSymbols.Nit, Units.Nit); }
	        { put(UnitSymbols.Stilb, Units.Stilb); }
	        { put(UnitSymbols.Lambert, Units.Lambert); }
	        { put(UnitSymbols.FootLambert, Units.FootLambert); }

	        //--- Illuminance
	        { put(UnitSymbols.Lux, Units.Lux); }               
	        { put(UnitSymbols.Phot, Units.Phot); }   
	        { put(UnitSymbols.FootCandle, Units.FootCandle); }

	        //--- Logarithmic
	        { put(UnitSymbols.Bel, Units.Bel); }                       
	        { put(UnitSymbols.Neper, Units.Neper); } 

	        //--- Magnetic Flux
	        { put(UnitSymbols.Weber, Units.Weber); }                        
	        { put(UnitSymbols.Maxwell, Units.Maxwell); }                          

	        //--- Magnetic Field B
	        { put(UnitSymbols.Tesla, Units.Tesla); }
	        { put(UnitSymbols.Gauss, Units.Gauss); }                      

	        //--- Magnetic Field H
	        { put(UnitSymbols.AmperePerMetre, Units.AmperePerMetre); } 
	        { put(UnitSymbols.Oersted, Units.Oersted); }                       

	        //--- Radioactivity
	        { put(UnitSymbols.Becquerel, Units.Becquerel); } 
	        { put(UnitSymbols.Curie, Units.Curie); }
	        { put(UnitSymbols.DisintegrationsPerSecond, Units.DisintegrationsPerSecond); }
	        { put(UnitSymbols.DisintegrationsPerMinute, Units.DisintegrationsPerMinute); }
	        { put(UnitSymbols.Rutherford, Units.Rutherford); }

	        //--- Absorbed Dose
	        { put(UnitSymbols.Gray, Units.Gray); }
	        { put(UnitSymbols.Rad, Units.Rad); }
	        
	        //--- Absorbed Dose Rate
	        { put(UnitSymbols.GrayPerSecond, Units.GrayPerSecond); }

	        //--- Equivalent Dose
	        { put(UnitSymbols.Sievert, Units.Sievert); }
	        { put(UnitSymbols.REM, Units.REM); }

	        //--- Exposure
	        { put(UnitSymbols.CoulombPerKilogram, Units.CoulombPerKilogram); }
	        { put(UnitSymbols.Roentgen, Units.Roentgen); }

	        //--- Catalytic Activity
	        { put(UnitSymbols.Katal, Units.Katal); } 

	        //--- Catalytic Activity Concentration
	        { put(UnitSymbols.KatalPerCubicMetre, Units.KatalPerCubicMetre); } 

	        //--- Jerk
	        { put(UnitSymbols.MetrePerCubicSecond, Units.MetrePerCubicSecond); }

	        //--- Mass Flow Rate
	        { put(UnitSymbols.KilogramPerSecond, Units.KilogramPerSecond); }

	        //--- Density
	        { put(UnitSymbols.KilogramPerCubicMetre, Units.KilogramPerCubicMetre); }

	        //--- Area Density
	        { put(UnitSymbols.KilogramPerSquareMetre, Units.KilogramPerSquareMetre); }

	        //--- Energy Density
	        { put(UnitSymbols.JoulePerCubicMetre, Units.JoulePerCubicMetre); }

	        //--- Specific Volume
	        { put(UnitSymbols.CubicMetrePerKilogram, Units.CubicMetrePerKilogram); }

	        //--- Volumetric Flow Rate
	        { put(UnitSymbols.CubicMetrePerSecond, Units.CubicMetrePerSecond); }

	        //--- Surface Tension
	        { put(UnitSymbols.JoulePerSquareMetre, Units.JoulePerSquareMetre); }

	        //--- Specific Weight
	        { put(UnitSymbols.NewtonPerCubicMetre, Units.NewtonPerCubicMetre); }

	        //--- Thermal Conductivity
	        { put(UnitSymbols.WattPerMetrePerKelvin, Units.WattPerMetrePerKelvin); }

	        //--- Thermal Conductance
	        { put(UnitSymbols.WattPerKelvin, Units.WattPerKelvin); }

	        //--- Thermal Resistivity
	        { put(UnitSymbols.MetreKelvinPerWatt, Units.MetreKelvinPerWatt); }

	        //--- Thermal Resistance
	        { put(UnitSymbols.KelvinPerWatt, Units.KelvinPerWatt); }

	        //--- Heat Transfer Coefficient
	        { put(UnitSymbols.WattPerSquareMetrePerKelvin, Units.WattPerSquareMetrePerKelvin); }

	        //--- Heat Flux Density
	        { put(UnitSymbols.WattPerSquareMetre, Units.WattPerSquareMetre); }

	        //--- Entropy
	        { put(UnitSymbols.JoulePerKelvin, Units.JoulePerKelvin); }

	        //--- Electric Field Strength
	        { put(UnitSymbols.NewtonPerCoulomb, Units.NewtonPerCoulomb); }
	        { put(UnitSymbols.VoltPerMetre, Units.VoltPerMetre); }

	        //--- Linear Electric Charge Density
	        { put(UnitSymbols.CoulombPerMetre, Units.CoulombPerMetre); }

	        //--- Surface Electric Charge Density
	        { put(UnitSymbols.CoulombPerSquareMetre, Units.CoulombPerSquareMetre); }

	        //--- Volume Electric Charge Density
	        { put(UnitSymbols.CoulombPerCubicMetre, Units.CoulombPerCubicMetre); }

	        //--- Current Density
	        { put(UnitSymbols.AmperePerSquareMetre, Units.AmperePerSquareMetre); }

	        //--- Electromagnetic Permittivity
	        { put(UnitSymbols.FaradPerMetre, Units.FaradPerMetre); }

	        //--- Electromagnetic Permeability
	        { put(UnitSymbols.HenryPerMetre, Units.HenryPerMetre); }

	        //--- Molar Energy
	        { put(UnitSymbols.JoulePerMole, Units.JoulePerMole); }

	        //--- Molar Entropy
	        { put(UnitSymbols.JoulePerMolePerKelvin, Units.JoulePerMolePerKelvin); }

	        //--- Molar Volume
	        { put(UnitSymbols.CubicMetrePerMole, Units.CubicMetrePerMole); }

	        //--- Molar Mass
	        { put(UnitSymbols.KilogramPerMole, Units.KilogramPerMole); }

	        //--- Molar Concentration
	        { put(UnitSymbols.MolePerCubicMetre, Units.MolePerCubicMetre); }

	        //--- Molal Concentration
	        { put(UnitSymbols.MolePerKilogram, Units.MolePerKilogram); }

	        //--- Radiant Intensity
	        { put(UnitSymbols.WattPerSteradian, Units.WattPerSteradian); }

	        //--- Radiance
	        { put(UnitSymbols.WattPerSteradianPerSquareMetre, Units.WattPerSteradianPerSquareMetre); }

	        //--- Fuel Economy
	        { put(UnitSymbols.InverseSquareMetre, Units.InverseSquareMetre); }
	        { put(UnitSymbols.MilePerGallon, Units.MilePerGallon); }
	        { put(UnitSymbols.ImperialMilePerGallon, Units.ImperialMilePerGallon); }
	        { put(UnitSymbols.USCSMilePerGallon, Units.USCSMilePerGallon); }
	        { put(UnitSymbols.KilometrePerLitre, Units.KilometrePerLitre); }

	        //--- Sound Exposure
	        { put(UnitSymbols.SquarePascalSecond, Units.SquarePascalSecond); }

	        //--- Sound Impedance
	        { put(UnitSymbols.PascalSecondPerCubicMetre, Units.PascalSecondPerCubicMetre); }

	        //--- Rotational Stiffness
	        { put(UnitSymbols.NewtonMetrePerRadian, Units.NewtonMetrePerRadian); }

	        //--- Bit Rate
	        { put(UnitSymbols.BitPerSecond, Units.BitPerSecond); }

	        //--- Symbol Rate
	        { put(UnitSymbols.Baud, Units.Baud); }
	    };	
	    
	    AllNonDividableUnits = new ArrayList<Units>()
	    {{
	        //--- Area
	        add(Units.Are); add(Units.Rood); add(Units.Acre); add(Units.Barn); add(Units.SurveyAcre);
	        
	        //--- Volume
			add(Units.Litre); add(Units.FluidOunce); add(Units.ImperialFluidOunce); add(Units.USCSFluidOunce); add(Units.Gill);
			add(Units.ImperialGill); add(Units.USCSGill); add(Units.Pint); add(Units.ImperialPint); add(Units.LiquidPint);
			add(Units.DryPint); add(Units.Quart); add(Units.ImperialQuart); add(Units.LiquidQuart); add(Units.DryQuart);
			add(Units.Gallon); add(Units.ImperialGallon); add(Units.LiquidGallon); add(Units.DryGallon);
	        
	        //--- Force
			add(Units.Kilopond); add(Units.PoundForce); add(Units.Kip); add(Units.OunceForce);
	        
	        //--- Energy
			add(Units.Electronvolt); add(Units.BritishThermalUnit); add(Units.ThermochemicalBritishThermalUnit);
			add(Units.Calorie); add(Units.ThermochemicalCalorie); add(Units.FoodCalorie); add(Units.Therm); add(Units.UKTherm);
			add(Units.USTherm); 
	                    
	        //--- Power
			add(Units.Horsepower); add(Units.MetricHorsepower); add(Units.BoilerHorsepower); add(Units.ElectricHorsepower);
			add(Units.TonOfRefrigeration);
	                    
	        //--- Pressure
			add(Units.Atmosphere); add(Units.TechnicalAtmosphere); add(Units.Bar); add(Units.MillimetreOfMercury); add(Units.InchOfMercury32F); 
			add(Units.InchOfMercury60F); add(Units.Torr);
	                    
	        //--- Amount of substance
			add(Units.PoundMole);

	        //Note that all the electricity/magnetism CGS compounds are considered non-dividable.
	        //This is because of its multi-system peculiarities and what it entails.
	        //These are old units where the dividable-compound icing isn't expected to be that important.
	        //Compounds which might be defined without a direct reliance on electricity/magnetism units are
	        //dividable. For example, G (= Mx/cm2).
	        
	        //--- Electric Charge
			add(Units.Franklin); add(Units.Statcoulomb); add(Units.ESUOfCharge); add(Units.Abcoulomb); add(Units.EMUOfCharge);

	        //--- Electric Voltage
			add(Units.ESUOfElectricPotential); add(Units.Statvolt); add(Units.EMUOfElectricPotential); add(Units.Abvolt);

	        //--- Electric Resistance 
			add(Units.Statohm); add(Units.ESUOfResistance); add(Units.Abohm); add(Units.EMUOfResistance);

	        //--- Electric Conductance
			add(Units.Gemmho); add(Units.Statsiemens); add(Units.Statmho); add(Units.Absiemens); add(Units.Abmho);

	        //--- Electric Capacitance
			add(Units.Statfarad); add(Units.ESUOfCapacitance); add(Units.Abfarad); add(Units.EMUOfCapacitance);

	        //--- Electric Inductance
			add(Units.Stathenry); add(Units.ESUOfInductance); add(Units.Abhenry); add(Units.EMUOfInductance);

	        //--- Electric Dipole Moment
			add(Units.Debye);

	        //--- Luminance
			add(Units.Lambert); add(Units.FootLambert);

	        //--- Magnetic Flux
			add(Units.Maxwell);

	        //--- Magnetic Field H
			add(Units.Oersted);

	        //--- Absorbed Dose
			add(Units.Rad);

	        //--- Equivalent Dose
			add(Units.REM);

	        //--- Exposure
			add(Units.Roentgen);
	    }};
	    
	    AllImperialAndUSCSUnits = new ArrayList<Units>()
	    {{
	        //--- Length
	        add(Units.Inch); add(Units.Foot); add(Units.Yard); add(Units.Mile); add(Units.Thou); 
	        add(Units.Mil); add(Units.Fathom); add(Units.Rod); add(Units.Perch); add(Units.Pole); 
	        add(Units.Chain); add(Units.Furlong); add(Units.Link); 

	        //--- Mass
	    	add(Units.Grain); add(Units.Drachm); add(Units.Ounce); add(Units.Pound); add(Units.Stone);
	    	add(Units.Slug); 

	        //--- Area
	    	add(Units.SquareInch); add(Units.SquareFoot); add(Units.SquareRod); add(Units.SquarePerch);
	    	add(Units.SquarePole); add(Units.Rood); add(Units.Acre);

	        //--- Volume
	    	add(Units.CubicInch); add(Units.CubicFoot);
	        
	        //--- Force
	    	add(Units.PoundForce); add(Units.Poundal); add(Units.OunceForce);
	                    
	        //--- Velocity
	    	add(Units.FootPerSecond); add(Units.InchPerSecond); add(Units.MilePerHour);
	                    
	        //--- Acceleration
	    	add(Units.FootPerSquareSecond); add(Units.InchPerSquareSecond); 
	                    
	        //--- Energy
	    	add(Units.BritishThermalUnit); add(Units.ThermochemicalBritishThermalUnit);
	                    
	        //--- Power
	    	add(Units.Horsepower); 
	                    
	        //--- Pressure
	    	add(Units.PoundforcePerSquareInch); add(Units.PoundforcePerSquareFoot);
	                    
	        //--- Temperature
	    	add(Units.DegreeFahrenheit); add(Units.DegreeRankine);
	        
	        //--- Luminance
	    	add(Units.FootLambert);

	        //--- Illuminance
	    	add(Units.FootCandle);
	    }};
	}
}
