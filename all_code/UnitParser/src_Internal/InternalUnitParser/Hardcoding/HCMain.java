package InternalUnitParser.Hardcoding;

import InternalUnitParser.Classes.*;
import InternalUnitParser.Methods.*;
import UnitParser.*;

import java.util.HashMap;


@SuppressWarnings("serial")
public class HCMain
{
	//Main classification for all the units (type, system and conversion factor).
	//This dictionary represents an easily-modifiable container of well-structured unit information.
	//After using all this information to create more specific/efficient collections, GetAllMain() deletes it.
	public static HashMap<UnitTypes, HashMap<UnitSystems, HashMap<Units, Double>>> AllUnits; 

	//HashMap mostly meant to deal with the Imperial/USCS peculiar relationship.
	public static HashMap<UnitSystems, UnitSystems> AllBasicSystems;

	//Some times, all what matters is knowing whether the system is metric (SI/CGS) or English (Imperial/USCS).
	public static HashMap<UnitSystems, UnitSystems> AllMetricEnglish;
	
	//This collection relates all the unnamed units with their associated systems.
	//There are many units which don't fit any Units enum case, the unnamed units.
	//Unnamed units avoid a huge (and not too logical) hardcoding effort.
	//Note that UnitParser supports much more units than just the members of the Units enum.
	//By bearing in mind that any combination of named units forming a supported type is also 
	//supported, the total number of supported units is way too big to even think about facing
	//it in a hardcoding-all-of-them way.
	public static HashMap<UnitSystems, Units> DefaultUnnamedUnits;
	
	//Relates all the units with their respective types.
	//The call to GetALLMain() also populates all the collections below this line.
	
	public static HashMap<Units, UnitTypes> AllUnitTypes;

	//Relates all the units with their respective systems.
	public static HashMap<Units, UnitSystems> AllUnitSystems;

	//Relates all the units with their respective conversion factors.
	public static HashMap<Units, Double> AllUnitConversionFactors;

	//Includes all the supported unit string representations (case doesn't matter).
	public static HashMap<String, Units> AllUnitStrings;

	//Includes secondary symbols for some units (case does matter).
	public static HashMap<String, Units> AllUnitSymbols2;

	//Some conversion factors are too small/big for the decimal type.
	public static HashMap<Double, UnitInfo> AllBeyondDecimalConversionFactors;  
	
	public static void Start()
	{
		AllUnits = new HashMap<UnitTypes, HashMap<UnitSystems, HashMap<Units, Double>>>()
	    {   
	        {
	            put
	            (
	                UnitTypes.None, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>() 
	                {
	                    {
	                        put
	                        (
	                            UnitSystems.SI, new HashMap<Units, Double>()
	                            {
	                                { put(Units.ValidSIUnit, 1.0); }
	                            }
	                        );
	                        put
	                        (
	                            UnitSystems.Imperial, new HashMap<Units, Double>()
	                            {
	                                { put(Units.ValidImperialUSCSUnit, 1.0); put(Units.ValidImperialUnit, 1.0); }
	                            }
	                        );
	                        put
	                        (
	                            UnitSystems.USCS, new HashMap<Units, Double>()
	                            {
	                                { put(Units.ValidUSCSUnit, 1.0); }
	                            }
	                        ); 
	                        put
	                        (
	                            UnitSystems.CGS, new HashMap<Units, Double>()
	                            {
	                                { put(Units.ValidCGSUnit, 1.0); }
	                            }
	                        ); 
	                        put
	                        (
	                            UnitSystems.None, new HashMap<Units, Double>()
	                            {
	                                { put(Units.ValidUnit, 1.0); put(Units.Unitless, 1.0); }
	                            }
	                        );                         
	                    }
	                }
	            );           
	            put
	            (
	                UnitTypes.Length, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    (  
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Metre, UnitConversionFactors.Metre);
	                            }
	                        }
	                    );
	                    put
	                    ( 
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Centimetre, UnitConversionFactors.Centimetre);
	                            }
	                        }
	                    );
	                    put
	                    ( 
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Foot, UnitConversionFactors.Foot); 
	                            put(Units.Thou, UnitConversionFactors.Thou); 
	                            put(Units.Mil, UnitConversionFactors.Mil); 
	                            put(Units.Inch, UnitConversionFactors.Inch);  
	                            put(Units.Yard, UnitConversionFactors.Yard); 
	                            put(Units.Fathom, UnitConversionFactors.Fathom); 
	                            put(Units.Rod, UnitConversionFactors.Rod); 
	                            put(Units.Perch, UnitConversionFactors.Perch); 
	                            put(Units.Pole, UnitConversionFactors.Pole); 
	                            put(Units.Chain, UnitConversionFactors.Chain); 
	                            put(Units.Furlong, UnitConversionFactors.Furlong); 
	                            put(Units.Mile, UnitConversionFactors.Mile);
	                            put(Units.Link, UnitConversionFactors.Link);
	                            }
	                        }
	                    );
	                    put
	                    ( 
	                        UnitSystems.USCS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.SurveyInch, UnitConversionFactors.SurveyInch); 
	                            put(Units.SurveyFoot, UnitConversionFactors.SurveyFoot); 
	                            put(Units.SurveyYard, UnitConversionFactors.SurveyYard); 
	                            put(Units.SurveyRod, UnitConversionFactors.SurveyRod); 
	                            put(Units.SurveyChain, UnitConversionFactors.SurveyChain); 
	                            put(Units.SurveyLink, UnitConversionFactors.SurveyLink); 
	                            put(Units.SurveyMile, UnitConversionFactors.SurveyMile);
	                            put(Units.SurveyFathom, UnitConversionFactors.SurveyFathom);
	            				} 
	                        }
	                    );
	                    put
	                    ( 
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.AstronomicalUnit, UnitConversionFactors.AstronomicalUnit);
	                            put(Units.NauticalMile, UnitConversionFactors.NauticalMile);
	                            put(Units.Angstrom, UnitConversionFactors.Angstrom);
	                            put(Units.Fermi, UnitConversionFactors.Fermi);
	                            put(Units.LightYear, UnitConversionFactors.LightYear);
	                            put(Units.Parsec, UnitConversionFactors.Parsec);
	                            put(Units.Micron, UnitConversionFactors.Micron);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Mass, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    (  
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Gram, UnitConversionFactors.Gram);
	                            }
	                        }
	                    );
	                    put
	                    ( 
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Pound, UnitConversionFactors.Pound); 
	                            put(Units.Grain, UnitConversionFactors.Grain); 
	                            put(Units.Drachm, UnitConversionFactors.Drachm); 
	                            put(Units.Ounce, UnitConversionFactors.Ounce); 
	                            put(Units.Stone, UnitConversionFactors.Stone); 
	                            put(Units.Slug, UnitConversionFactors.Slug);
	                            put(Units.Quarter, UnitConversionFactors.Quarter);
	                            put(Units.LongQuarter, UnitConversionFactors.LongQuarter);
	                            put(Units.Hundredweight, UnitConversionFactors.Hundredweight);
	                            put(Units.LongHundredweight, UnitConversionFactors.LongHundredweight);
	                            put(Units.Ton, UnitConversionFactors.Ton);
	                            put(Units.LongTon, UnitConversionFactors.LongTon);
	                            }
	                        }
	                    );
	                    put
	                    ( 
	                        UnitSystems.USCS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.ShortQuarter, UnitConversionFactors.ShortQuarter);
	                            put(Units.ShortHundredweight, UnitConversionFactors.ShortHundredweight); 
	                            put(Units.ShortTon, UnitConversionFactors.ShortTon);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None,
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.MetricTon, UnitConversionFactors.MetricTon);
	                            put(Units.Dalton, UnitConversionFactors.Dalton);
	                            put(Units.UnifiedAtomicMassUnit, UnitConversionFactors.UnifiedAtomicMassUnit);
	                            put(Units.Carat, UnitConversionFactors.Carat);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Time, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    (  
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Second, UnitConversionFactors.Second);
	                            }
	                        }
	                    );
	                    put
	                    ( 
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Minute, UnitConversionFactors.Minute); 
	                            put(Units.Hour, UnitConversionFactors.Hour);
	                            put(Units.Day, UnitConversionFactors.Day);
	                            put(Units.Shake, UnitConversionFactors.Shake);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Area, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                	{
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.SquareMetre, UnitConversionFactors.SquareMetre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.SquareCentimetre, UnitConversionFactors.SquareCentimetre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.SquareFoot, UnitConversionFactors.SquareFoot);
	                            put(Units.SquareInch, UnitConversionFactors.SquareInch);
	                            put(Units.SquareRod, UnitConversionFactors.SquareRod);
	                            put(Units.SquarePerch, UnitConversionFactors.SquarePerch);
	                            put(Units.SquarePole, UnitConversionFactors.SquarePole); 
	                            put(Units.Rood, UnitConversionFactors.Rood); 
	                            put(Units.Acre, UnitConversionFactors.Acre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.USCS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.SurveyAcre, UnitConversionFactors.SurveyAcre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Are, UnitConversionFactors.Are);
	                            put(Units.Barn, UnitConversionFactors.Barn);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Volume, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CubicMetre, UnitConversionFactors.CubicMetre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CubicCentimetre, UnitConversionFactors.CubicCentimetre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CubicFoot, UnitConversionFactors.CubicFoot);
	                            put(Units.CubicInch, UnitConversionFactors.CubicInch);
	                            put(Units.FluidOunce, UnitConversionFactors.FluidOunce);
	                            put(Units.ImperialFluidOunce, UnitConversionFactors.ImperialFluidOunce);
	                            put(Units.Gill, UnitConversionFactors.Gill);
	                            put(Units.ImperialGill, UnitConversionFactors.ImperialGill);
	                            put(Units.Pint, UnitConversionFactors.Pint);
	                            put(Units.ImperialPint, UnitConversionFactors.ImperialPint);
	                            put(Units.Quart, UnitConversionFactors.Quart);
	                            put(Units.ImperialQuart, UnitConversionFactors.ImperialQuart);
	                            put(Units.Gallon, UnitConversionFactors.Gallon);
	                            put(Units.ImperialGallon, UnitConversionFactors.ImperialGallon);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.USCS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.USCSFluidOunce, UnitConversionFactors.USCSFluidOunce);
	                            put(Units.USCSGill, UnitConversionFactors.USCSGill);
	                            put(Units.LiquidPint, UnitConversionFactors.LiquidPint);
	                            put(Units.DryPint, UnitConversionFactors.DryPint);
	                            put(Units.LiquidQuart, UnitConversionFactors.LiquidQuart);
	                            put(Units.DryQuart, UnitConversionFactors.DryQuart);
	                            put(Units.LiquidGallon, UnitConversionFactors.LiquidGallon);
	                            put(Units.DryGallon, UnitConversionFactors.DryGallon);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Litre, UnitConversionFactors.Litre);
	                            }
	                        }
	                   );
	                   }
	                }
	            );
	            put
	            (
	                UnitTypes.Angle, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Radian, UnitConversionFactors.Radian);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Degree, UnitConversionFactors.Degree); 
	                            put(Units.Arcminute, UnitConversionFactors.Arcminute); 
	                            put(Units.Arcsecond, UnitConversionFactors.Arcsecond);
	                            put(Units.Revolution, UnitConversionFactors.Revolution);
	                            put(Units.Gradian, UnitConversionFactors.Gradian);
	                            put(Units.Gon, UnitConversionFactors.Gon);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Information, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Bit, UnitConversionFactors.Bit); 
	                            put(Units.Byte, UnitConversionFactors.Byte);
	                            put(Units.Nibble, UnitConversionFactors.Nibble); 
	                            put(Units.Quartet, UnitConversionFactors.Quartet);
	                            put(Units.Octet, UnitConversionFactors.Octet);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Force, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Newton, UnitConversionFactors.Newton);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.PoundForce, UnitConversionFactors.PoundForce);
	                            put(Units.Poundal, UnitConversionFactors.Poundal);
	                            put(Units.OunceForce, UnitConversionFactors.OunceForce);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Dyne, UnitConversionFactors.Dyne);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Kilopond, UnitConversionFactors.Kilopond);
	                            put(Units.Kip, UnitConversionFactors.Kip);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Velocity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.MetrePerSecond, UnitConversionFactors.MetrePerSecond);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CentimetrePerSecond, UnitConversionFactors.CentimetrePerSecond);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.FootPerSecond, UnitConversionFactors.FootPerSecond);
	                            put(Units.InchPerSecond, UnitConversionFactors.InchPerSecond);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Knot, UnitConversionFactors.Knot);
	                            put(Units.KilometrePerHour, UnitConversionFactors.KilometrePerHour);
	                            put(Units.MilePerHour, UnitConversionFactors.MilePerHour);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Acceleration, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.MetrePerSquareSecond, UnitConversionFactors.MetrePerSquareSecond);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Gal, UnitConversionFactors.Gal);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.FootPerSquareSecond, UnitConversionFactors.FootPerSquareSecond);
	                            put(Units.InchPerSquareSecond, UnitConversionFactors.InchPerSquareSecond);
	                            }
	                        }
	                   );
	                   }
	                }
	            );
	            put
	            (
	                UnitTypes.Energy, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Joule, UnitConversionFactors.Joule);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.BritishThermalUnit, UnitConversionFactors.BritishThermalUnit);
	                            put(Units.ThermochemicalBritishThermalUnit, UnitConversionFactors.ThermochemicalBritishThermalUnit);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Erg, UnitConversionFactors.Erg);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Electronvolt, UnitConversionFactors.Electronvolt);
	                            put(Units.WattHour, UnitConversionFactors.WattHour); 
	                            put(Units.Calorie, UnitConversionFactors.Calorie);
	                            put(Units.ThermochemicalCalorie, UnitConversionFactors.ThermochemicalBritishThermalUnit);
	                            put(Units.FoodCalorie, UnitConversionFactors.FoodCalorie);
	                            put(Units.Therm, UnitConversionFactors.Therm);
	                            put(Units.UKTherm, UnitConversionFactors.UKTherm);
	                            put(Units.USTherm, UnitConversionFactors.USTherm);
	            				}                       
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Power, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Watt, UnitConversionFactors.Watt);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.ErgPerSecond, UnitConversionFactors.ErgPerSecond);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Horsepower, UnitConversionFactors.Horsepower);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.MetricHorsepower, UnitConversionFactors.MetricHorsepower);
	                            put(Units.ElectricHorsepower, UnitConversionFactors.ElectricHorsepower);
	                            put(Units.BoilerHorsepower, UnitConversionFactors.BoilerHorsepower);
	                            put(Units.TonOfRefrigeration, UnitConversionFactors.TonOfRefrigeration);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Pressure, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Pascal, UnitConversionFactors.Pascal);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Barye, UnitConversionFactors.Barye);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.PoundforcePerSquareInch, UnitConversionFactors.PoundforcePerSquareInch);
	                            put(Units.PoundforcePerSquareFoot, UnitConversionFactors.PoundforcePerSquareFoot);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Bar, UnitConversionFactors.Bar); 
	                            put(Units.Atmosphere, UnitConversionFactors.Atmosphere);
	                            put(Units.TechnicalAtmosphere, UnitConversionFactors.TechnicalAtmosphere);
	                            put(Units.MillimetreOfMercury, UnitConversionFactors.MillimetreOfMercury);
	                            put(Units.InchOfMercury32F, UnitConversionFactors.InchOfMercury32F);
	                            put(Units.InchOfMercury60F, UnitConversionFactors.InchOfMercury60F);
	                            put(Units.Torr, UnitConversionFactors.Torr);
	                            put(Units.KipPerSquareInch, UnitConversionFactors.KipPerSquareInch);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Frequency, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Hertz, UnitConversionFactors.Hertz);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CyclePerSecond, UnitConversionFactors.CyclePerSecond);
	            				}
	                         }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricCharge, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Coulomb, UnitConversionFactors.Coulomb);
	                            put(Units.AmpereHour, UnitConversionFactors.AmpereHour);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Franklin, UnitConversionFactors.Franklin);
	                            put(Units.Statcoulomb, UnitConversionFactors.Statcoulomb);
	                            put(Units.ESUOfCharge, UnitConversionFactors.ESUOfCharge);
	                            put(Units.Abcoulomb, UnitConversionFactors.Abcoulomb);
	                            put(Units.EMUOfCharge, UnitConversionFactors.EMUOfCharge);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricCurrent, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Ampere, UnitConversionFactors.Ampere);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Statampere, UnitConversionFactors.Statampere);
	                            put(Units.ESUOfCurrent, UnitConversionFactors.ESUOfCurrent);
	                            put(Units.Abampere, UnitConversionFactors.Abampere);
	                            put(Units.Biot, UnitConversionFactors.Biot);
	                            put(Units.EMUOfCurrent, UnitConversionFactors.EMUOfCurrent);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricVoltage, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Volt, UnitConversionFactors.Volt);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Statvolt, UnitConversionFactors.Statvolt);
	                            put(Units.ESUOfElectricPotential, UnitConversionFactors.ESUOfElectricPotential);
	                            put(Units.Abvolt, UnitConversionFactors.Abvolt);
	                            put(Units.EMUOfElectricPotential, UnitConversionFactors.EMUOfElectricPotential);
	            				}
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricResistance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Ohm, UnitConversionFactors.Ohm);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS,
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Statohm, UnitConversionFactors.Statohm);
	                            put(Units.ESUOfResistance, UnitConversionFactors.ESUOfResistance);
	                            put(Units.Abohm, UnitConversionFactors.Abohm);
	                            put(Units.EMUOfResistance, UnitConversionFactors.EMUOfResistance);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricResistivity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.OhmMetre, UnitConversionFactors.OhmMetre);
	                            }
	                        }
	                   );
	                   }
	               }
	            );
	            put
	            (
	                UnitTypes.ElectricConductance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Siemens, UnitConversionFactors.Siemens);
	                            put(Units.Mho, UnitConversionFactors.Mho);
	            				}
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS,
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Statmho, UnitConversionFactors.Statmho);
	                            put(Units.Statsiemens, UnitConversionFactors.Statsiemens);
	                            put(Units.Abmho, UnitConversionFactors.Abmho);
	                            put(Units.Absiemens, UnitConversionFactors.Absiemens);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Gemmho, UnitConversionFactors.Gemmho);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricConductivity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.SiemensPerMetre, UnitConversionFactors.SiemensPerMetre);
	                            }
	                        }
	                   );
	                   }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricCapacitance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Farad, UnitConversionFactors.Farad);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS,  
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Statfarad, UnitConversionFactors.Statfarad);
	                            put(Units.ESUOfCapacitance, UnitConversionFactors.ESUOfCapacitance);
	                            put(Units.Abfarad, UnitConversionFactors.Abfarad);
	                            put(Units.EMUOfCapacitance, UnitConversionFactors.EMUOfCapacitance);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricInductance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Henry, UnitConversionFactors.Henry);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS,
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Stathenry, UnitConversionFactors.Stathenry);
	                            put(Units.ESUOfInductance, UnitConversionFactors.ESUOfInductance);
	                            put(Units.Abhenry, UnitConversionFactors.Abhenry);
	                            put(Units.EMUOfInductance, UnitConversionFactors.EMUOfInductance);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricDipoleMoment, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI,
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CoulombMetre, UnitConversionFactors.CoulombMetre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS,
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Debye, UnitConversionFactors.Debye);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Temperature, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	            				{
	                            //Mere Placeholders. Temperature conversions are managed through a function.
	                            put(Units.Kelvin, 1.0);
	                            put(Units.DegreeCelsius, 1.0);
	            				} 
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.DegreeFahrenheit, 1.0);
	                            put(Units.DegreeRankine, 1.0);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Wavenumber, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                   {
	                   put
	                   (
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.ReciprocalMetre, UnitConversionFactors.ReciprocalMetre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Kayser, UnitConversionFactors.Kayser);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Viscosity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.PascalSecond, UnitConversionFactors.PascalSecond);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Poise, UnitConversionFactors.Poise);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.KinematicViscosity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.SquareMetrePerSecond, UnitConversionFactors.SquareMetrePerSecond);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Stokes, UnitConversionFactors.Stokes);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.AmountOfSubstance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Mole, UnitConversionFactors.Mole);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.PoundMole, UnitConversionFactors.PoundMole);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Momentum, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.NewtonSecond, UnitConversionFactors.NewtonSecond);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.AngularVelocity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.RadianPerSecond, UnitConversionFactors.RadianPerSecond);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None,
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.RevolutionPerMinute, UnitConversionFactors.RevolutionPerMinute);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.AngularAcceleration, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.RadianPerSquareSecond, UnitConversionFactors.RadianPerSquareSecond);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.AngularMomentum, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.JouleSecond, UnitConversionFactors.JouleSecond);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MomentOfInertia, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.KilogramSquareMetre, UnitConversionFactors.KilogramSquareMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.SolidAngle, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Steradian, UnitConversionFactors.Steradian);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.SquareDegree, UnitConversionFactors.SquareDegree);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.LuminousIntensity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Candela, UnitConversionFactors.Candela);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.LuminousFlux, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Lumen, UnitConversionFactors.Lumen);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.LuminousEnergy, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.LumenSecond, UnitConversionFactors.LumenSecond);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Talbot, UnitConversionFactors.Talbot);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Luminance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CandelaPerSquareMetre, UnitConversionFactors.CandelaPerSquareMetre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Stilb, UnitConversionFactors.Stilb);
	                            put(Units.Lambert, UnitConversionFactors.Lambert);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.FootLambert, UnitConversionFactors.FootLambert);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Nit, UnitConversionFactors.Nit);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Illuminance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Lux, UnitConversionFactors.Lux);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.FootCandle, UnitConversionFactors.FootCandle);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Phot, UnitConversionFactors.Phot);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Logarithmic, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Bel, UnitConversionFactors.Bel);
	                            put(Units.Neper, UnitConversionFactors.Neper);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MagneticFlux, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Weber, UnitConversionFactors.Weber);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Maxwell, UnitConversionFactors.Maxwell);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MagneticFieldB, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Tesla, UnitConversionFactors.Tesla);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Gauss, UnitConversionFactors.Gauss);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MagneticFieldH, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.AmperePerMetre, UnitConversionFactors.AmperePerMetre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Oersted, UnitConversionFactors.Oersted);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Radioactivity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Becquerel, UnitConversionFactors.Becquerel);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Curie, UnitConversionFactors.Curie);
	                            put(Units.DisintegrationsPerMinute, UnitConversionFactors.DisintegrationsPerMinute);
	                            put(Units.DisintegrationsPerSecond, UnitConversionFactors.DisintegrationsPerSecond);
	                            put(Units.Rutherford, UnitConversionFactors.Rutherford);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.AbsorbedDose, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Gray, UnitConversionFactors.Gray);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Rad, UnitConversionFactors.Rad);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.AbsorbedDoseRate, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.GrayPerSecond, UnitConversionFactors.GrayPerSecond);
	                            }
	                        }
	                   );
	                   }
	                }
	            );
	            put
	            (
	                UnitTypes.EquivalentDose, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Sievert, UnitConversionFactors.Sievert);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.REM, UnitConversionFactors.REM);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Exposure, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CoulombPerKilogram, UnitConversionFactors.CoulombPerKilogram);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.CGS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Roentgen, UnitConversionFactors.Roentgen);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.CatalyticActivity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Katal, UnitConversionFactors.Katal);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.CatalyticActivityConcentration, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.KatalPerCubicMetre, UnitConversionFactors.KatalPerCubicMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Jerk, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.MetrePerCubicSecond, UnitConversionFactors.MetrePerCubicSecond);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MassFlowRate, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.KilogramPerSecond, UnitConversionFactors.KilogramPerSecond);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Density, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.KilogramPerCubicMetre, UnitConversionFactors.KilogramPerCubicMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.AreaDensity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.KilogramPerSquareMetre, UnitConversionFactors.KilogramPerSquareMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.EnergyDensity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.JoulePerCubicMetre, UnitConversionFactors.JoulePerCubicMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.SpecificVolume, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CubicMetrePerKilogram, UnitConversionFactors.CubicMetrePerKilogram);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.VolumetricFlowRate, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CubicMetrePerSecond, UnitConversionFactors.CubicMetrePerSecond);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.SurfaceTension, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.JoulePerSquareMetre, UnitConversionFactors.JoulePerSquareMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.SpecificWeight, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.NewtonPerCubicMetre, UnitConversionFactors.NewtonPerCubicMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ThermalConductivity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.WattPerMetrePerKelvin, UnitConversionFactors.WattPerMetrePerKelvin);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ThermalConductance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.WattPerKelvin, UnitConversionFactors.WattPerKelvin);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ThermalResistivity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.MetreKelvinPerWatt, UnitConversionFactors.MetreKelvinPerWatt);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ThermalResistance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.KelvinPerWatt, UnitConversionFactors.KelvinPerWatt);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.HeatTransferCoefficient, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.WattPerSquareMetrePerKelvin, UnitConversionFactors.WattPerSquareMetrePerKelvin);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.HeatFluxDensity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.WattPerSquareMetre, UnitConversionFactors.WattPerSquareMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Entropy, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.JoulePerKelvin, UnitConversionFactors.JoulePerKelvin);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectricFieldStrength, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.NewtonPerCoulomb, UnitConversionFactors.NewtonPerCoulomb);
	                            put(Units.VoltPerMetre, UnitConversionFactors.VoltPerMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.LinearElectricChargeDensity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CoulombPerMetre, UnitConversionFactors.CoulombPerMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.SurfaceElectricChargeDensity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CoulombPerSquareMetre, UnitConversionFactors.CoulombPerSquareMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.VolumeElectricChargeDensity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CoulombPerCubicMetre, UnitConversionFactors.CoulombPerCubicMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.CurrentDensity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.AmperePerSquareMetre, UnitConversionFactors.AmperePerSquareMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectromagneticPermittivity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.FaradPerMetre, UnitConversionFactors.FaradPerMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.ElectromagneticPermeability, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.HenryPerMetre, UnitConversionFactors.HenryPerMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MolarEnergy, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.JoulePerMole, UnitConversionFactors.JoulePerMole);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MolarEntropy, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.JoulePerMolePerKelvin, UnitConversionFactors.JoulePerMolePerKelvin);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MolarVolume, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.CubicMetrePerMole, UnitConversionFactors.CubicMetrePerMole);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MolarMass, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.KilogramPerMole, UnitConversionFactors.KilogramPerMole);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MolarConcentration, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.MolePerCubicMetre, UnitConversionFactors.MolePerCubicMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.MolalConcentration, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.MolePerKilogram, UnitConversionFactors.MolePerKilogram);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.RadiantIntensity, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.WattPerSteradian, UnitConversionFactors.WattPerSteradian);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.Radiance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.WattPerSteradianPerSquareMetre, UnitConversionFactors.WattPerSteradianPerSquareMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.FuelEconomy, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.InverseSquareMetre, UnitConversionFactors.InverseSquareMetre);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.Imperial, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.MilePerGallon, UnitConversionFactors.MilePerGallon);
	                            put(Units.ImperialMilePerGallon, UnitConversionFactors.ImperialMilePerGallon);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.USCS, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.USCSMilePerGallon, UnitConversionFactors.USCSMilePerGallon);
	                            }
	                        }
	                    );
	                    put
	                    (
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.KilometrePerLitre, UnitConversionFactors.KilometrePerLitre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.SoundExposure, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.SquarePascalSecond, UnitConversionFactors.SquarePascalSecond);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.SoundImpedance, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.PascalSecondPerCubicMetre, UnitConversionFactors.PascalSecondPerCubicMetre);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.RotationalStiffness, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.SI, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.NewtonMetrePerRadian, UnitConversionFactors.NewtonMetrePerRadian);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.BitRate, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.BitPerSecond, UnitConversionFactors.BitPerSecond);
	                            }
	                        }
	                    );
	                    }
	                }
	            );
	            put
	            (
	                UnitTypes.SymbolRate, 
	                new HashMap<UnitSystems, HashMap<Units, Double>>()
	                {
	                    {
	                    put
	                    ( 
	                        UnitSystems.None, 
	                        new HashMap<Units, Double>()
	                        {
	                            {
	                            put(Units.Baud, UnitConversionFactors.Baud);
	                            }
	                        }
	                    );
	                    }
	                }
	            );            
	        }  
	    };	
	    
	    AllBasicSystems =  new HashMap<UnitSystems, UnitSystems>()
	    {
	        { put(UnitSystems.SI, UnitSystems.SI); }
	        { put(UnitSystems.CGS, UnitSystems.CGS); }
	        { put(UnitSystems.Imperial, UnitSystems.Imperial); }
	        { put(UnitSystems.USCS, UnitSystems.Imperial); }
	        { put(UnitSystems.ImperialAndUSCS, UnitSystems.Imperial); }
	        { put(UnitSystems.None, UnitSystems.None); }
	    };
	    
	    AllMetricEnglish = new HashMap<UnitSystems, UnitSystems>()
	    {
	        { put(UnitSystems.SI, UnitSystems.SI); }
	        { put(UnitSystems.CGS, UnitSystems.SI); }
	        { put(UnitSystems.Imperial, UnitSystems.Imperial); }
	        { put(UnitSystems.USCS, UnitSystems.Imperial); }
	        { put(UnitSystems.ImperialAndUSCS, UnitSystems.Imperial); }
	        { put(UnitSystems.None, UnitSystems.None); }
	    };

	    DefaultUnnamedUnits = new HashMap<UnitSystems, Units>()
	    {
	        { put(UnitSystems.SI, Units.ValidSIUnit); }
	        { put(UnitSystems.CGS, Units.ValidCGSUnit); }
	        { put(UnitSystems.Imperial, Units.ValidImperialUSCSUnit); }
	        { put(UnitSystems.USCS, Units.ValidUSCSUnit); }
	        { put(UnitSystems.None, Units.ValidUnit); }
	    };
	    
		AllUnitTypes = MethodsHardcoding.GetAllMain();
	}      
}
