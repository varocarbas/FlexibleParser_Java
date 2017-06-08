package UnitParser;

/**All the supported units.**/
public enum Units
{
    /**No supported unit.**/  
    None,

    /**Unitless variable.**/  
    Unitless,

    /**
    Valid SI unit not included elsewhere.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/  
    ValidSIUnit,
    /**
    Valid Imperial/USCS unit not included elsewhere.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                       
    **/   
    ValidImperialUSCSUnit,
    /**
    Valid Imperial unit not included elsewhere.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                       
    **/   
    ValidImperialUnit,
    /**
    Valid USCS unit not included elsewhere.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                       
    **/   
    ValidUSCSUnit,
    /**
    Valid CGS unit not included elsewhere.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/   
    ValidCGSUnit,
    /**
    Valid unit not included elsewhere.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/    
    ValidUnit,

    //--- Length
    /**
    Metre (m). SI length unit.
    By default, binary prefixes may not be used with this unit.            
    **/  
    Metre,
    /**
    Centimetre (cm). CGS length unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/  
    Centimetre,
    /**
    Astronomical unit (AU). Length unit.
    By default, SI/binary prefixes may not be used with this unit.
    **/           
    AstronomicalUnit,
    /**
    Inch (in). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/             
    Inch,
    /**
    Foot (ft). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.
    **/             
    Foot,
    /**
    Yard (yd). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.
    **/               
    Yard,
    /**
    International mile (mi). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/              
    Mile,
    /**
    Nautical mile (M). Length unit.
    By default, SI/binary prefixes may not be used with this unit.
    **/                   
    NauticalMile,
    /**
    Thou (thou). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.
    **/                  
    Thou,
    /**
    Mil (mil). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.
    **/                  
    Mil,
    /**
    Fathom (fathom). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    Fathom,
    /**
    Rod (rd). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    Rod,
    /**
    Perch (perch). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    Perch,
    /**
    Pole (pole). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    Pole,
    /**
    Chain (ch). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/            
    Chain,
    /**
    Furlong (fur). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    Furlong,
    /**
    Link (li). Imperial/USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    Link,
    /**
    U.S. survey inch (surin). USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    SurveyInch,
    /**
    U.S. survey foot (surft). USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    SurveyFoot,
    /**
    U.S. survey yard (suryd). USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    SurveyYard,
    /**
    U.S. survey rod (surrd). USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    SurveyRod,
    /**
    U.S. survey chain (surch). USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    SurveyChain,
    /**
    U.S. survey link (surli). USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    SurveyLink,
    /**
    U.S. survey mile (surmi). USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    SurveyMile,
    /**
    U.S. survey fathom (surfathom). USCS length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    SurveyFathom,
    /**
    Ångström (Å). Length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    Angstrom,
    /**
    Fermi (f). Length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    Fermi,
    /**
    Light year (ly). Length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    LightYear,
    /**
    Parsec (pc). Length unit.
    By default, binary prefixes may not be used with this unit.            
    **/
    Parsec,
    /**
    Micron (μ). Length unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/
    Micron,

    //--- Mass
    /**
    Gram (g). SI mass unit.
    By default, binary prefixes may not be used with this unit.              
    **/
    Gram,
    /**
    Metric ton (t). Mass unit.
    By default, binary prefixes may not be used with this unit.  
    **/
    MetricTon,
    /**
    Grain (gr). Imperial/USCS mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/
    Grain,
    /**
    Drachm (dr). Imperial/USCS mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/
    Drachm,
    /**
    Ounce (oz). Imperial/USCS mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/            
    Ounce,
    /**
    Pound (lb). Imperial/USCS mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/
    Pound,
    /**
    Stone (st). Imperial/USCS mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/            
    Stone,
    /**
    Slug (sl). Imperial/USCS mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/                      
    Slug,
    /**
    Quarter (qr). Imperial mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/          
    Quarter,
    /**
    Long quarter (impqr). Imperial mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/          
    LongQuarter,
    /**
    Short quarter (uscqr). USCS mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/          
    ShortQuarter,
    /**
    Hundredweight (cwt). Imperial mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/          
    Hundredweight,
    /**
    Long hundredweight (impcwt). Imperial mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/
    LongHundredweight,
    /**
    Short hundredweight (usccwt). USCS mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/
    ShortHundredweight,
    /**
    Ton (tn). Imperial mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/            
    Ton,
    /**
    Long ton (imptn). Imperial mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/            
    LongTon,
    /**
    Short ton (usctn). USCS mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/  
    ShortTon,
    /**
    Carat (ct). Mass unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/  
    Carat,
    /**
    Dalton (Da). Mass unit.
    By default, binary prefixes may not be used with this unit.              
    **/  
    Dalton,
    /**
    Unified atomic mass unit (u). Mass unit.
    By default, binary prefixes may not be used with this unit.              
    **/  
    UnifiedAtomicMassUnit,

    //--- Time
    /**
    Second (s). SI time unit.
    By default, binary prefixes may not be used with this unit.              
    **/  
    Second,
    /**
    Minute (min). Time unit.
    By default, SI/binary prefixes may not be used with this unit.  
    **/  
    Minute,
    /**
    Hour (h). Time unit.
    By default, SI/binary prefixes may not be used with this unit.  
    **/  
    Hour,
    /**
    Day (d). Time unit.
    By default, SI/binary prefixes may not be used with this unit.  
    **/  
    Day,
    /**
    Shake (shake). Time unit.
    By default, SI/binary prefixes may not be used with this unit.  
    **/  
    Shake,

    //--- Area
    /**
    Square metre (m2). SI area unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    SquareMetre,
    /**
    Square centimetre (cm2). CGS area unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    SquareCentimetre,
    /**
    Are (a). Area unit.
    By default, binary prefixes may not be used with this unit.  
    **/  
    Are,
    /**
    Square inch (in2). Imperial/USCS area unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    SquareInch,
    /**
    Square foot (ft2). Imperial/USCS area unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    SquareFoot,
    /**
    Square rod (rd2). Imperial/USCS area unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.              
    **/ 
    SquareRod,
    /**
    Square perch (perch2). Imperial/USCS area unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.              
    **/  
    SquarePerch,
    /**
    Square pole (pole2). Imperial/USCS area unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.              
    **/  
    SquarePole,
    /**
    Rood (rood). Imperial/USCS area unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/  
    Rood,
    /**
    Acre (ac). Imperial/USCS area unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/  
    Acre,
    /**
    Barn (b). Area unit.
    By default, binary prefixes may not be used with this unit.              
    **/  
    Barn,
    /**
    U.S. survey acre (surac). USCS area unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/  
    SurveyAcre,

    //--- Volume
    /**
    Cubic metre (m3). SI volume unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    CubicMetre,
    /**
    Cubic centimetre (cc). CGS volume unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    CubicCentimetre,
    /**
    Litre (L). Volume unit.
    By default, binary prefixes may not be used with this unit.  
    **/  
    Litre,
    /**
    Cubic foot (ft3). Imperial/USCS volume unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    CubicFoot,
    /**
    Cubic inch (in3). Imperial/USCS volume unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    CubicInch,
    /**
    Fluid ounce (floz). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/  
    FluidOunce,
    /**
    Imperial fluid ounce (impfloz). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/  
    ImperialFluidOunce,
    /**
    USCS fluid ounce (uscfloz). USCS volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/  
    USCSFluidOunce,
    /**
    Gill (gi). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/            
    Gill,
    /**
    Imperial gill (impgi). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/            
    ImperialGill,
    /**
    USCS gill (uscgi). USCS volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/  
    USCSGill,
    /**Pint (pt). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/             
    Pint,
    /**Imperial pint (imppt). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/             
    ImperialPint,
    /**Liquid pint (liquidpt). USCS volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/
    LiquidPint,
    /**
    Dry pint (drypt). USCS volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/
    DryPint,
    /**
    Quart (qt). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/            
    Quart,
    /**
    Imperial quart (impqt). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/            
    ImperialQuart,
    /**
    Liquid quart (liquidqt). USCS volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/                        
    LiquidQuart,
    /**
    Dry quart (dryqt). USCS volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/            
    DryQuart,
    /**
    Gallon (gal). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/             
    Gallon,
    /**
    Imperial gallon (impgal). Imperial volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/             
    ImperialGallon,
    /**
    Liquid gallon (liquidgal). USCS volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/     
    LiquidGallon,
    /**
    Dry gallon (drygal). USCS volume unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/           
    DryGallon,

    //--- Angle
    /**
    Radian (rad). SI angle unit.
    By default, binary prefixes may not be used with this unit.              
    **/ 
    Radian,
    /**
    Degree (°). Angle unit.
    By default, SI/binary prefixes may not be used with this unit.  
    **/ 
    Degree,
    /**
    Arcminute ('). Angle unit.
    By default, SI/binary prefixes may not be used with this unit.  
    **/             
    Arcminute,
    /**
    Arcsecond (''). Angle unit.
    By default, SI/binary prefixes may not be used with this unit.  
    **/                         
    Arcsecond,
    /**
    Revolution (rev). Angle unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/             
    Revolution,
    /**
    Gradian (grad). Angle unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/             
    Gradian,
    /**
    Gon (gon). Angle unit.
    By default, SI/binary prefixes may not be used with this unit.              
    **/             
    Gon,

    //--- Information
    /**Bit (bit). Information unit.**/                         
    Bit,
    /**Byte (byte). Information unit.**/   
    Byte,
    /**Nibble (nibble). Information unit.**/  
    Nibble,
    /**Quartet (quartet). Information unit.**/            
    Quartet,
    /**Octet (octet). Information unit.**/             
    Octet,

    //--- Force
    /**
    Newton (N). SI force unit.
    By default, binary prefixes may not be used with this unit.                         
    **/             
    Newton,
    /**
    Kilopond (kp). Force unit.
    By default, SI/binary prefixes may not be used with this unit.                         
    **/   
    Kilopond,
    /**
    Pound-force (lbf). Imperial/USCS force unit.
    By default, SI/binary prefixes may not be used with this unit.                        
    **/               
    PoundForce,
    /**
    Kip (kip). Force unit.
    By default, SI/binary prefixes may not be used with this unit.                        
    **/               
    Kip,
    /**
    Poundal (pdl). Imperial/USCS force unit.
    By default, SI/binary prefixes may not be used with this unit.                          
    **/                
    Poundal,
    /**
    Ounce-force (ozf). Imperial/USCS force unit.
    By default, SI/binary prefixes may not be used with this unit.                          
    **/                
    OunceForce,
    /**
    Dyne (dyn). CGS Force unit.
    By default, binary prefixes may not be used with this unit.                        
    **/              
    Dyne,

    //--- Velocity
    /**
    Metre per second (m/s). SI velocity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    MetrePerSecond,
    /**
    Centimetre per second (cm/s). CGS velocity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    CentimetrePerSecond,
    /**
    Foot per second (ft/s). Imperial/USCS velocity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    FootPerSecond,
    /**
    Inch per second (in/s). Imperial/USCS velocity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    InchPerSecond,
    /**
    Knot (kn). Velocity unit.
    By default, SI/binary prefixes may not be used.
    **/
    Knot,
    /**
    Kilometre per hour (kph). Velocity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts. 
    **/
    KilometrePerHour,
    /**
    Mile per hour (mph). Velocity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts. 
    **/
    MilePerHour,

    //--- Acceleration
    /**
    Metre per square second (m/s2). SI acceleration unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    MetrePerSquareSecond,
    /**
    Gal (Gal). CGS acceleration unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Gal,
    /**
    Foot per square second (ft/s2). Imperial/USCS acceleration unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    FootPerSquareSecond,
    /**
    Inch per square second (in/s2). Imperial/USCS acceleration unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    InchPerSquareSecond,

    //--- Energy
    /**
    Joule (J). SI energy unit.
    By default, binary prefixes may not be used with this unit.         
    **/   
    Joule,
    /**
    Electronvolt (eV). Energy unit.
    By default, binary prefixes may not be used with this unit. 
    **/            
    Electronvolt,
    /**
    Watt hour (Wh). Energy unit.
    By default, binary prefixes may not be used with this unit.         
    **/   
    WattHour,
    /**
    IT British thermal unit (BTU). Imperial/USCS energy unit.
    By default, SI/binary prefixes may not be used with this unit.             
    **/                
    BritishThermalUnit,
    /**
    Thermochemical British thermal unit (thBTU). Imperial/USCS energy unit.
    By default, SI/binary prefixes may not be used with this unit.             
    **/                
    ThermochemicalBritishThermalUnit,
    /**
    IT calorie (cal). Energy unit.
    By default, binary prefixes may not be used with this unit.            
    **/              
    Calorie,
    /**
    Thermochemical calorie (thcal). Energy unit.
    By default, binary prefixes may not be used with this unit.            
    **/              
    ThermochemicalCalorie,
    /**
    Food calorie (kcal). Energy unit.
    By default, binary prefixes may not be used with this unit.            
    **/              
    FoodCalorie,
    /**
    Erg (erg). CGS energy unit.
    By default, binary prefixes may not be used with this unit.           
    **/              
    Erg,
    /**
    EC therm (thm). Energy unit.
    By default, SI/binary prefixes may not be used with this unit.           
    **/              
    Therm,
    /**
    UK therm (ukthm). Energy unit.
    By default, SI/binary prefixes may not be used with this unit.           
    **/              
    UKTherm,
    /**
    US therm (usthm). Energy unit.
    By default, SI/binary prefixes may not be used with this unit.           
    **/              
    USTherm,

    //--- Power
    /**
    Watt (W). SI power unit.
    By default, binary prefixes may not be used with this unit.            
    **/              
    Watt,
    /**
    Erg per second (erg/s). CGS power unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/              
    ErgPerSecond,
    /**
    Mechanical horsepower (hp). Imperial/USCS power unit.
    By default, SI/binary prefixes may not be used with this unit.             
    **/              
    Horsepower,
    /**
    Metric horsepower (hpM). Power unit.
    By default, SI/binary prefixes may not be used with this unit. 
    **/    
    MetricHorsepower,
    /**
    Boiler horsepower (hpS). Power unit.
    By default, SI/binary prefixes may not be used with this unit. 
    **/    
    BoilerHorsepower,
    /**
    Electric horsepower (hpE). Power unit.
    By default, SI/binary prefixes may not be used with this unit. 
    **/    
    ElectricHorsepower,
    /**
    Ton of refrigeration (TR). Power unit.
    By default, SI/binary prefixes may not be used with this unit.            
    **/              
    TonOfRefrigeration,

    //--- Pressure
    /**
    Pascal (Pa). SI pressure unit.
    By default, binary prefixes may not be used with this unit.             
    **/    
    Pascal,
    /**
    Atmosphere (atm). Pressure unit.
    By default, SI/binary prefixes may not be used with this unit. 
    **/            
    Atmosphere,
    /**
    Technical atmosphere (at). Pressure unit.
    By default, SI/binary prefixes may not be used with this unit. 
    **/            
    TechnicalAtmosphere,
    /**
    Bar (bar). Pressure unit.
    By default, binary prefixes may not be used with this unit. 
    **/            
    Bar,
    /**
    Pound-force per square inch (psi). Imperial/USCS pressure unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.             
    **/                 
    PoundforcePerSquareInch,
    /**
    Pound-force per square foot (psf). Imperial/USCS pressure unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.             
    **/                 
    PoundforcePerSquareFoot,
    /**
    Millimetre of mercury (mmHg). Pressure unit.
    By default, SI/binary prefixes may not be used with this unit.             
    **/               
    MillimetreOfMercury,
    /**
    Inch of mercury 32 °F (inHg32). Pressure unit.
    By default, SI/binary prefixes may not be used with this unit.             
    **/               
    InchOfMercury32F,
    /**
    Inch of mercury 60 °F (inHg60). Pressure unit.
    By default, SI/binary prefixes may not be used with this unit.             
    **/               
    InchOfMercury60F,
    /**
    Barye (Ba). CGS pressure unit.
    By default, binary prefixes may not be used with this unit.             
    **/               
    Barye,
    /**
    Torr (Torr). Pressure unit.
    By default, binary prefixes may not be used with this unit.             
    **/               
    Torr,
    /**
    Kip per square inch (ksi). Pressure unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.             
    **/               
    KipPerSquareInch,

    //--- Frequency
    /**
    Hertz (Hz). SI frequency unit.
    By default, binary prefixes may not be used with this unit. 
    **/    
    Hertz,
    /**
    Cycle per second (cps). Frequency unit.
    By default, SI/binary prefixes may not be used with this unit.             
    **/                
    CyclePerSecond,

    //--- Electric Charge
    /**
    Coulomb (C). SI electric charge unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Coulomb,
    /**
    AmpereHour (Ah). Electric charge unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    AmpereHour,
    /**
    Franklin (Fr). CGS-Gaussian/CGS-ESU electric charge unit.
    By default, binary prefixes may not be used with this unit.            
    **/
    Franklin,
    /**
    Statcoulomb (statC). CGS-Gaussian/CGS-ESU electric charge unit.
    By default, binary prefixes may not be used with this unit.                         
    **/
    Statcoulomb,
    /**
    Electrostatic unit of charge (ESUcha). CGS-Gaussian/CGS-ESU electric charge unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    ESUOfCharge,
    /**
    Abcoulomb (abC). CGS-EMU electric charge unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Abcoulomb,
    /**
    Electromagnetic unit of charge (EMUcha). CGS-EMU electric charge unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    EMUOfCharge,

    //--- Electric Current
    /**
    Ampere (A). SI electric current unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Ampere,
    /**
    Statampere (statA). CGS-Gaussian/CGS-ESU electric current unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Statampere,
    /**
    Electrostatic unit of current (ESUcur). CGS-Gaussian/CGS-ESU electric current unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    ESUOfCurrent,
    /**
    Abampere (abA). CGS-EMU electric current unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Abampere,
    /**
    Electromagnetic unit of current (EMUcur). CGS-EMU electric current unit.
    By default, binary prefixes may not be used with this unit.              
    **/
    EMUOfCurrent,
    /**
    Biot (Bi). CGS-EMU electric current unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Biot,

    //--- Electric Voltage
    /**
    Volt (V). SI electric voltage unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Volt,
    /**
    Electrostatic unit of electric potential (ESUpot). CGS-Gaussian/CGS-ESU electric voltage unit.
    By default, binary prefixes may not be used with this unit.              
    **/
    ESUOfElectricPotential,
    /**
    Statvolt (statV). CGS-Gaussian/CGS-ESU electric voltage unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Statvolt,
    /**
    Electromagnetic unit of electric potential (EMUpot). CGS-EMU electric voltage unit.
    By default, binary prefixes may not be used with this unit.              
    **/
    EMUOfElectricPotential,
    /**
    Abvolt (abV). CGS-EMU electric voltage unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Abvolt,

    //--- Electric Resistance 
    /**
    Ohm (Ω). SI electric resistance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Ohm,
    /**
    Statohm (statΩ). CGS-Gaussian/CGS-ESU electric resistance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Statohm,
    /**
    Electrostatic unit of resistance (ESUres). CGS-Gaussian/CGS-ESU electric resistance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    ESUOfResistance,
    /**
    Abohm (abΩ). CGS-EMU electric resistance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Abohm,
    /**
    Electromagnetic unit of resistance (EMUres). CGS-EMU electric resistance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    EMUOfResistance,

    //--- Electric Resistivity 
    /**
    Ohm metre (Ω*m). SI electric resistivity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.  
    **/
    OhmMetre,

    //--- Electric Conductance
    /**
    Siemens (S). SI electric conductance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Siemens,
    /**
    Mho (℧). SI electric conductance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Mho,
    /**
    Gemmho (gemmho). Electric conductance unit.
    By default, SI/binary prefixes may not be used with this unit.             
    **/
    Gemmho,
    /**
    Statsiemens (statS). CGS-Gaussian/CGS-ESU electric resistance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Statsiemens,
    /**
    Statmho (stat℧). CGS-Gaussian/CGS-ESU electric resistance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Statmho,
    /**
    Absiemens (abS). CGS-EMU electric resistance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Absiemens,
    /**
    Abmho (ab℧). CGS-EMU electric resistance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Abmho,

    //--- Electric Conductivity
    /**
    Siemens per metre (S/m). SI electric conductivity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.  
    **/
    SiemensPerMetre,

    //--- Electric Capacitance
    /**
    Farad (F). SI electric capacitance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Farad,
    /**
    Statfarad (statF). CGS-Gaussian/CGS-ESU electric capacitance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Statfarad,
    /**
    Electrostatic unit of capacitance (ESUcap). CGS-Gaussian/CGS-ESU electric capacitance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    ESUOfCapacitance,
    /**
    Abfarad (abF). CGS-EMU electric capacitance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Abfarad,
    /**
    Electromagnetic unit of capacitance (EMUcap). CGS-EMU electric capacitance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    EMUOfCapacitance,

    //--- Electric Inductance
    /**
    Henry (H). SI electric inductance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Henry,
    /**
    Stathenry (statH). CGS-Gaussian/CGS-ESU electric inductance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Stathenry,
    /**
    Electrostatic unit of inductance (ESUind). CGS-Gaussian/CGS-ESU electric inductance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    ESUOfInductance,
    /**
    Abhenry (abH). CGS-EMU electric inductance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Abhenry,
    /**
    Electromagnetic unit of inductance (EMUind). CGS-EMU electric inductance unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    EMUOfInductance,

    //--- Electric Dipole Moment
    /**
    Coulomb metre (C*m). SI electric moment unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    CoulombMetre,
    /**
    Debye (D). CGS-Gaussian electric dipole moment unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Debye,

    //--- Temperature
    /**
    Kelvin (K). SI temperature unit.
    By default, binary prefixes may not be used with this unit.             
    **/
    Kelvin,
    /**
    Degree Celsius (°C). SI temperature unit.
    By default, binary prefixes may not be used with this unit.                     
    **/
    DegreeCelsius,
    /**
    Degree Fahrenheit (°F). Imperial/USCS temperature unit.
    By default, SI/binary prefixes may not be used with this unit.                        
    **/            
    DegreeFahrenheit,
    /**
    Degree Rankine (°R). Imperial/USCS temperature unit.
    By default, SI/binary prefixes may not be used with this unit.                         
    **/   
    DegreeRankine,

    //--- Wavenumber
    /**
    Reciprocal metre (1/m). SI wavenumber unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    ReciprocalMetre,
    /**
    Kayser (kayser). CGS wavenumber unit.
    By default, binary prefixes may not be used with this unit.                         
    **/
    Kayser,

    //--- Viscosity
    /**
    Pascal second (Pa*s). SI viscosity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    PascalSecond,
    /**
    Poise (P). CGS viscosity unit.
    By default, binary prefixes may not be used with this unit.                         
    **/
    Poise,

    //--- Kinematic Viscosity
    /**
    Square metre per second (m2/s). SI kinematic viscosity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    SquareMetrePerSecond,
    /**
    Stokes (St). CGS kinematic viscosity unit.
    By default, binary prefixes may not be used with this unit.                         
    **/
    Stokes,

    //--- Amount of Substance
    /**
    Mole (mol). SI amount of substance unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Mole,
    /**
    Pound-mole (lbmol). Amount of substance unit.
    By default, SI/binary prefixes may not be used with this unit.                         
    **/
    PoundMole,

    //--- Momentum
    /**
    Newton second (N*s). SI momentum unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    NewtonSecond,

    //--- Angular Velocity
    /**
    Radian per second (rad/s). SI angular velocity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    RadianPerSecond,
    /**
    Revolution per minute (rpm). Angular velocity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.           
    **/             
    RevolutionPerMinute,

    //--- Angular Acceleration
    /**
    Radian per square second (rad/s2). SI angular acceleration unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    RadianPerSquareSecond,

    //--- Angular Momentum
    /**
    Joule second (J*s). SI angular momentum unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    JouleSecond,

    //--- Moment of Inertia
    /**
    Kilogram square metre (kg*m2). SI moment of inertia unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    KilogramSquareMetre,

    //--- Solid Angle
    /**
    Steradian (sr). SI solid angle unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Steradian,
    /**
    Square degree (deg2). Solid angle unit.
    By default, SI/binary prefixes may not be used with this unit.                         
    **/                 
    SquareDegree,

    //--- Luminous Intensity
    /**
    Candela (cd). SI luminous intensity unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Candela,

    //--- Luminous Flux
    /**
    Lumen (lm). SI luminous flux unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Lumen,

    //--- Luminous Energy
    /**
    Lumen second (lm*s). Luminous energy unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    LumenSecond,
    /**
    Talbot (talbot). Luminous energy unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Talbot,

    //--- Luminance
    /**
    Candela per square metre (cd/m2). SI luminance unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    CandelaPerSquareMetre,
    /**
    Nit (nt). Luminance unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Nit,
    /**
    Stilb (sb). CGS Luminance unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Stilb,
    /**
    Lambert (lambert). CGS Luminance unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Lambert,
    /**
    Foot-lambert (ftL). Imperial/USCS Luminance unit.
    By default, SI/binary prefixes may not be used with this unit.                         
    **/                 
    FootLambert,

    //--- Illuminance
    /**
    Lux (lx). SI illuminance unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Lux,
    /**
    Phot (ph). CGS illuminance unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                 
    Phot,
    /**
    Foot-candle (fc). Imperial/USCS illuminance unit.
    By default, SI/binary prefixes may not be used with this unit.                         
    **/                 
    FootCandle,

    //--- Logarithmic
    /**
    Bel (B). Logarithmic unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                        
    Bel,
    /**
    Neper (Np). Logarithmic unit.
    By default, binary prefixes may not be used with this unit.                         
    **/      
    Neper,

    //--- Magnetic Flux
    /**
    Weber (Wb). SI magnetic flux unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                         
    Weber,
    /**
    Maxwell (Mx). CGS-Gaussian/CGS-EMU magnetic flux unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                         
    Maxwell,

    //--- Magnetic Field B
    /**
    Tesla (T). SI magnetic field B unit.
    By default, binary prefixes may not be used with this unit.                         
    **/ 
    Tesla,
    /**
    Gauss (G). CGS-Gaussian/CGS-EMU magnetic field B unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                         
    Gauss,

    //--- Magnetic Field H
    /**
    Ampere per metre (A/m). SI magnetic field H unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.                         
    **/                 
    AmperePerMetre,
    /**
    Oersted (Oe). CGS-Gaussian/CGS-EMU magnetic field H unit.
    By default, binary prefixes may not be used with this unit.                         
    **/                         
    Oersted,

    //--- Radioactivity
    /**
    Becquerel (Bq). SI radioactivity unit.
    By default, binary prefixes may not be used with this unit.                         
    **/ 
    Becquerel,
    /**
    Curie (Ci). Radioactivity unit.
    By default, binary prefixes may not be used with this unit.             
    **/ 
    Curie,
    /**
    Disintegrations per second (dps). Radioactivity unit.
    By default, SI/binary prefixes may not be used with this unit.                         
    **/ 
    DisintegrationsPerSecond,
    /**
    Disintegrations per minute (dpm). Radioactivity unit.
    By default, SI/binary prefixes may not be used with this unit.                         
    **/ 
    DisintegrationsPerMinute,
    /**
    Rutherford (Rd). Radioactivity unit.
    By default, SI/binary prefixes may not be used with this unit.                         
    **/ 
    Rutherford,

    //--- Absorbed Dose
    /**
    Gray (Gy). SI absorbed dose unit.
    By default, binary prefixes may not be used with this unit.                         
    **/ 
    Gray,
    /**
    Rad (Rad). CGS absorbed dose unit.
    By default, binary prefixes may not be used with this unit.                         
    **/ 
    Rad,

    //--- Absorbed Dose Rate
    /**
    Gray per second (Gy/s). SI absorbed dose rate unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    GrayPerSecond,

    //--- Equivalent Dose
    /**
    Sievert (Sv). SI equivalent dose unit.
    By default, binary prefixes may not be used with this unit.                         
    **/ 
    Sievert,
    /**
    Roentgen equivalent in man (rem). CGS equivalent dose unit.
    By default, binary prefixes may not be used with this unit.                         
    **/ 
    REM,

    //--- Exposure
    /**
    Coulomb per kilogram (C/kg). SI exposure unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    CoulombPerKilogram,
    /**
    Roentgen (R). CGS exposure unit.
    By default, binary prefixes may not be used with this unit.                         
    **/ 
    Roentgen,

    //--- Catalytic Activity
    /**
    Katal (kat). SI catalytic activity unit.
    By default, binary prefixes may not be used with this unit.            
    **/ 
    Katal,

    //--- Catalytic Activity Concentration
    /**
    Katal per cubic metre (kat/m3). SI catalytic activity concentration unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    KatalPerCubicMetre,

    //--- Jerk
    /**
    Metre per cubic second (m/s3). SI jerk unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    MetrePerCubicSecond,

    //--- Mass Flow Rate
    /**
    Kilogram per second (kg/s). SI mass flow rate unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    KilogramPerSecond,

    //--- Density
    /**
    Kilogram per cubic metre (kg/m3). SI density unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    KilogramPerCubicMetre,

    //--- Area Density
    /**
    Kilogram per square metre (kg/m2). SI area density unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    KilogramPerSquareMetre,

    //--- Energy Density
    /**
    Joule per cubic metre (J/m3). SI energy density unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    JoulePerCubicMetre,

    //--- Specific Volume
    /**
    Cubic metre per kilogram (m3/kg). SI specific volume unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    CubicMetrePerKilogram,

    //--- Volumetric Flow Rate
    /**
    Cubic metre per second (m3/s). SI volumetric flow rate unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    CubicMetrePerSecond,

    //--- Surface Tension
    /**
    Joule per square metre (J/m2). SI surface tension unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    JoulePerSquareMetre,

    //--- Specific Weight
    /**
    Newton per cubic metre (N/m3). SI specific weight unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    NewtonPerCubicMetre,

    //--- Thermal Conductivity
    /**
    Watt per metre per kelvin (W/m*K). SI thermal conductivity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    WattPerMetrePerKelvin,

    //--- Thermal Conductance
    /**
    Watt per kelvin (W/K). SI thermal conductance unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    WattPerKelvin,

    //--- Thermal Resistivity
    /**
    Metre kelvin per watt (m*K/W). SI thermal resistivity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    MetreKelvinPerWatt,

    //--- Thermal Resistance
    /**
    Metre kelvin per watt (K/W). SI thermal resistance unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    KelvinPerWatt,

    //--- Heat Transfer Coefficient
    /**
    Watt per square metre per kelvin (W/m2*K). SI heat transfer coefficient unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    WattPerSquareMetrePerKelvin,

    //--- Heat Flux Density
    /**
    Watt per square metre (W/m2). SI heat flux density unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    WattPerSquareMetre,

    //--- Entropy
    /**
    Joule per kelvin (J/K). SI entropy unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    JoulePerKelvin,

    //--- Electric Field Strength
    /**
    Newton per coulomb (N/C). SI electric field strength unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    NewtonPerCoulomb,
    /**
    Volt per metre (V/m). SI electric field strength unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    VoltPerMetre,

    //--- Linear Electric Charge Density
    /**
    Coulomb per metre (C/m). SI linear electric charge density unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    CoulombPerMetre,

    //--- Surface Electric Charge Density
    /**
    Coulomb per square metre (C/m2). SI surface electric charge density unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    CoulombPerSquareMetre,

    //--- Volume Electric Charge Density
    /**
    Coulomb per cubic metre (C/m3). SI volume electric charge density unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    CoulombPerCubicMetre,

    //--- Current Density
    /**
    Ampere per square metre (A/m2). SI current density unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    AmperePerSquareMetre,

    //--- Electromagnetic Permittivity
    /**
    Farad per metre (F/m). SI electromagnetic permittivity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    FaradPerMetre,

    //--- Electromagnetic Permeability
    /**
    Henry per metre (H/m). SI electromagnetic permeability unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    HenryPerMetre,

    //--- Molar Energy
    /**
    Joule per mole (J/mol). SI molar energy unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    JoulePerMole,

    //--- Molar Entropy
    /**
    Joule per mole per kelvin (J/mol*K). SI molar entropy unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    JoulePerMolePerKelvin,

    //--- Molar Volume
    /**
    Cubic metre per mole (m3/mol). SI molar volume unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    CubicMetrePerMole,

    //--- Molar Mass
    /**
    Kilogram per mole (kg/mol). SI molar mass unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    KilogramPerMole,

    //--- Molar Concentration
    /**
    Mole per cubic metre (mol/m3). SI molar concentration unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    MolePerCubicMetre,

    //--- Molal Concentration
    /**
    Mole per kilogram (mol/kg). SI molal concentration unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    MolePerKilogram,

    //--- Radiant Intensity
    /**
    Watt per steradian (W/sr). SI radiant intensity unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    WattPerSteradian,

    //--- Radiance
    /**
    Watt per steradian per square metre (W/sr*m2). SI radiance unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    WattPerSteradianPerSquareMetre,

    //--- Fuel Economomy
    /**
    Inverse square metre (1/m2). SI fuel economy unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    InverseSquareMetre,
    /**
    Mile per gallon (mpg). Imperial fuel economy unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    MilePerGallon,
    /**
    Imperial mile per gallon (impmpg). Imperial fuel economy unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    ImperialMilePerGallon,
    /**
    USCS mile per gallon (uscmpg). USCS fuel economy unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.          
    **/     
    USCSMilePerGallon,
    /**
    Kilometre per litre (km/L). Fuel economy unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.          
    **/     
    KilometrePerLitre,

    //--- Sound Exposure
    /**
    Square pascal second (Pa2*s). SI sound exposure unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    SquarePascalSecond,

    //--- Sound Impedance
    /**
    Pascal second per cubic metre (Pa*s/m3). SI sound impedance unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    PascalSecondPerCubicMetre,

    //--- Rotational Stiffness
    /**
    Newton metre per radian (N*m/rad). SI rotational stiffness unit.
    No prefix may be used with this unit. This restriction doesn't apply to its constituent parts.            
    **/ 
    NewtonMetrePerRadian,

    //--- Bit Rate
    /**Bit per second (bit/s). Bit rate unit.**/ 
    BitPerSecond,

    //--- Symbol Rate
    /**Baud (Bd). Symbol rate unit.**/ 
    Baud,
}