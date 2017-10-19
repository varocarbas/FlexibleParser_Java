package UnitParser;

/**Conversion factors relating each unit to the reference for the given type.**/
public class UnitConversionFactors
{
    //--- Length
    /**
    Metre (m) conversion factor. SI length unit.
    Reference point for all the length units.
    **/
    public final static double Metre = 1.0;
    /**Centimetre (cm) conversion factor. CGS length unit.**/
    public final static double Centimetre = 0.01;
    /**
    Astronomical unit (AU) conversion factor. Length unit.
    Source: IAU 2012.
    **/
    public final static double AstronomicalUnit = 149597870700.0; 
    /**Foot (ft) conversion factor. Imperial/USCS length unit.**/
    public final static double Foot = 0.3048;
    /**Inch (in) conversion factor. Imperial/USCS length unit.**/
    public final static double Inch = 0.0254;
    /**Yard (yd) conversion factor. Imperial/USCS length unit.**/
    public final static double Yard = 0.9144;
    /**International mile (mi) conversion factor. Imperial/USCS length unit.**/
    public final static double Mile = 1609.344;
    /**Nautical Mile (M) conversion factor. Length unit.**/
    public final static double NauticalMile = 1852.0;
    /**Thou (thou) conversion factor. Imperial/USCS length unit.**/    
    public final static double Thou = 0.0000254;
    /**Mil (mil) conversion factor. Imperial/USCS length unit.**/    
    public final static double Mil = 0.0000254;
    /**Fathom (fathom) conversion factor. Imperial/USCS length unit.**/
    public final static double Fathom = 1.8288;
    /**Rod (rd) conversion factor. Imperial/USCS length unit.**/
    public final static double Rod = 5.0292;
    /**Perch (perch) conversion factor. Imperial/USCS length unit.**/
    public final static double Perch = 5.0292;
    /**Pole (pole) conversion factor. Imperial/USCS length unit.**/
    public final static double Pole = 5.0292;
    /**Chain (ch) conversion factor. Imperial/USCS length unit.**/
    public final static double Chain = 20.1168;
    /**Furlong (fur) conversion factor. Imperial/USCS length unit.**/
    public final static double Furlong = 201.168;
    /**Link (li) conversion factor. Imperial/USCS length unit.**/
    public final static double Link = 0.201168;
    /**U.S. survey inch (surin) conversion factor. USCS length unit.**/
    public final static double SurveyInch = 0.0254000508001016002032004064;
    /**U.S. survey foot (surft) conversion factor. USCS length unit.**/
    public final static double SurveyFoot = 0.3048006096012192024384048768; //= 1.200m / 3.937;
    /**U.S. survey yard (suryd) conversion factor. USCS length unit.**/
    public final static double SurveyYard = 0.9144018288036576073152146304; //= 3m * SurveyFoot;
    /**U.S. survey rod (surrd) conversion factor. USCS length unit.**/
    public final static double SurveyRod = 5.0292100584201168402336804672; //= SurveyChain / 4;
    /**U.S. survey chain (surch) conversion factor. USCS length unit.**/
    public final static double SurveyChain = 20.116840233680467360934721869; //= 66m * SurveyFoot;
    /**U.S. survey link (surli) conversion factor. USCS length unit.**/
    public final static double SurveyLink = 0.2011684023368046736093472187; //= SurveyChain / 100;
    /**U.S. survey mile (surmi) conversion factor. USCS length unit.**/
    public final static double SurveyMile = 1609.3472186944373888747777495; //= 5280 * SurveyFoot
    /**U.S. survey fathom (surfathom) conversion factor. USCS length unit.**/
    public final static double SurveyFathom = 1.8288036576073152146304292608; //= 6m * SurveyFoot;
    /**Ångström (Å) conversion factor. Length unit.**/
    public final static double Angstrom = 1E-10;
    /**Fermi (f) conversion factor. Length unit.**/
    public final static double Fermi = 1E-15; //= SIPrefixValues.Femto;
    /**Light year (ly) conversion factor. Length unit.**/
    public final static double LightYear = 9460730472580800.0;
    /**
    Parsec (pc) conversion factor. Length unit.
    Source: IAU 2015.
    **/
    public final static double Parsec = 30856775814913672.789139379581; //= AstronomicalUnit * 648000m / MathematicalConstants.Pi;
    /**Micron (µ) conversion factor. Length unit.**/
    public final static double Micron = 1E-6; //= SIPrefixValues.Micro;

    //--- Mass
    /**
    Gram (g) conversion factor. SI mass unit.
    Reference point for all the mass units.
    **/
    public final static double Gram = 1.0;
    /**Metric Ton (t) conversion factor. Mass unit.**/
    public final static double MetricTon = 1000000.0;
    /**Grain (gr) conversion factor. Imperial/USCS mass unit.**/
    public final static double Grain = 0.06479891; //= Pound / 7000;
    /**Drachm (dr) conversion factor. Imperial/USCS mass unit.**/
    public final static double Drachm = 1.7718451953125; //= Pound / 256;
    /**Ounce (oz) conversion factor. Imperial/USCS mass unit.**/
    public final static double Ounce = 28.349523125; //= Pound / 16;
    /**Pound (lb) conversion factor. Imperial/USCS mass unit.**/
    public final static double Pound = 453.59237;
    /**Stone (st) conversion factor. Imperial/USCS mass unit.**/
    public final static double Stone = 6350.29318; //= Pound * 14;
    /**Slug (sl) conversion factor. Imperial/USCS mass unit.**/
    public final static double Slug = 14593.902937206364829396325459; //= Pound * PhysicalConstants.GravityAcceleration / Foot;  
    /**Quarter (qr). Imperial mass unit.**/
    public final static double Quarter = 12700.58636; //= Pound * 28;
    /**Long quarter (impqr) conversion factor. Imperial length unit.**/
    public final static double LongQuarter = 12700.58636; //= Pound * 28;
    /**Short quarter (uscqr) conversion factor. USCS length unit.**/
    public final static double ShortQuarter = 11339.80925; //= Pound * 25;
    /**Hundredweight (cwt) conversion factor. Imperial mass unit.**/
    public final static double Hundredweight = 50802.34544; //= Pound * 112;
    /**Long Hundredweight (impcwt) conversion factor. Imperial mass unit.**/
    public final static double LongHundredweight = 50802.34544; //= Pound * 112;
    /**Short Hundredweight (usccwt) conversion factor. USCS mass unit.**/
    public final static double ShortHundredweight = 45359.237; //= Pound * 100; 
    /**Ton (tn) conversion factor. Imperial mass unit.**/
    public final static double Ton = 1016046.9088; //= Pound * 2240;
    /**Long Ton (imptn) conversion factor. Imperial mass unit.**/
    public final static double LongTon = 1016046.9088; //= Pound * 2240;
    /**Short Ton (usctn) conversion factor. USCS mass unit.**/
    public final static double ShortTon = 907184.74; //= Pound * 2000;
    /**Carat (ct) conversion factor. Mass unit.**/  
    public final static double Carat = 0.2;
    /**Dalton (Da) conversion factor. Mass unit.**/  
    public final static double Dalton = 1.66053904E-24; //= PhysicalConstants.AtomicMassConstant * 1000;
    /**Unified atomic mass unit (u) conversion factor. Mass unit.**/  
    public final static double UnifiedAtomicMassUnit = 1.66053904E-24; //= PhysicalConstants.AtomicMassConstant * 1000;

    //--- Time
    /**
    Second (s) conversion factor. SI time unit.
    Reference point for all the time units.
    **/
    public final static double Second = 1.0;
    /**Minute (min) conversion factor. Time unit.**/
    public final static double Minute = 60.0;
    /**Hour (h) conversion factor. Time unit.**/
    public final static double Hour = 3600.0;
    /**Day (d) conversion factor. Time unit.**/
    public final static double Day = 86400.0;
    /**Shake (shake) conversion factor. Time unit.**/
    public final static double Shake = 1E-8; 

    //--- Area
    /**
    Square metre (m2) conversion factor. SI area unit.
    Reference point for all the area units.
    **/
    public final static double SquareMetre = 1.0; 
    /**Square centimetre (cm2) conversion factor. CGS area unit.**/
    public final static double SquareCentimetre = 0.0001; //= Centimetre * Centimetre;
    /**Are (a) conversion factor. Area unit.**/
    public final static double Are = 100.0;
    /**Square foot (ft2) conversion factor. Imperial/USCS area unit.**/
    public final static double SquareFoot = 0.09290304; //= Foot * Foot;
    /**Square inch (in2) conversion factor. Imperial/USCS area unit.**/
    public final static double SquareInch = 0.00064516; //= Inch * Inch;
    /**Square rod (rod2) conversion factor. Imperial/USCS area unit.**/
    public final static double SquareRod = 25.29285264; //= Rod * Rod;
    /**Square perch (perch2) conversion factor. Imperial/USCS area unit.**/
    public final static double SquarePerch = 25.29285264; //= Perch * Perch;
    /**Square pole (pole2) conversion factor. Imperial/USCS area unit.**/
    public final static double SquarePole = 25.29285264; //= Pole * Pole;
    /**Rood (rood) conversion factor. Imperial/USCS area unit.**/
    public final static double Rood = 1011.7141056; //= Furlong * Rod;
    /**Acre (ac) conversion factor. Imperial/USCS area unit.**/
    //Conversion factor delivered by any of the following calculations:
    //10m * Chain * Chain;
    //160m * Rod * Rod; 
    //4840m * Yard * Yard; 
    //43560m * Foot * Foot;
    public final static double Acre = 4046.8564224;
    /**
    Barn (b) conversion factor. Area unit.
    The double type cannot deal directly with the actual conversion factor (1E-28m).
    **/
    public final static double Barn = -1.0;
    /**Survey acre (surac) conversion factor. USCS area unit.**/
    //Conversion factor including up to the last digit which the following calculations have in common:
    //10m * SurveyChain * SurveyChain;
    //160m * SurveyRod * SurveyRod; 
    //4840m * SurveyYard * SurveyYard; 
    //43560m * SurveyFoot * SurveyFoot;
    public final static double SurveyAcre = 4046.87260987425200656852926; 

    //--- Volume
    /**
    Cubic metre (m3) conversion factor. SI volume unit.
    Reference point for all the volume units.
    **/
    public final static double CubicMetre = 1.0;
    /**Cubic centimetre (cm3) conversion factor. CGS Volume unit.**/
    public final static double CubicCentimetre = 0.000001; //= Centimetre * Centimetre * Centimetre;
    /**Litre (L) conversion factor. Volume unit.**/
    public final static double Litre = 0.001;
    /**Cubic foot (ft3) conversion factor. Imperial/USCS Volume unit.**/
    public final static double CubicFoot = 0.028316846592; //= Foot * Foot * Foot;
    /**Cubic inch (in3) conversion factor. Imperial/USCS Volume unit.**/
    public final static double CubicInch = 0.000016387064; //= Inch * Inch * Inch;
    /**Fluid ounce (floz) conversion factor. Imperial volume unit.**/
    public final static double FluidOunce = 0.0000284130625; //= Pint / 20;
    /**Imperial fluid ounce (impfloz) conversion factor. Imperial volume unit.**/
    public final static double ImperialFluidOunce = 0.0000284130625; //= ImperialPint / 20;
    /**USCS fluid ounce (uscfloz) conversion factor. USCS volume unit.**/
    public final static double USCSFluidOunce = 0.0000295735295625; //= LiquidPint / 16;
    /**Gill (gi) conversion factor. Imperial volume unit.**/
    public final static double Gill = 0.0001420653125; //= Pint / 4;
    /**Imperial gill (impgi) conversion factor. Imperial volume unit.**/
    public final static double ImperialGill = 0.0001420653125; //= ImperialPint / 4;
    /**USCS gill (uscgi) conversion factor. USCS volume unit.**/
    public final static double USCSGill = 0.00011829411825; //= LiquidPint / 4;
    /**Pint (pt) conversion factor. Imperial volume unit.**/
    public final static double Pint = 0.00056826125; //= Gallon / 8;
    /**Imperial pint (imppt) conversion factor. Imperial volume unit.**/
    public final static double ImperialPint = 0.00056826125; //= ImperialGallon / 8;
    /**Liquid pint (liquidpt) conversion factor. USCS volume unit.**/
    public final static double LiquidPint = 0.000473176473; //= LiquidGallon / 8;
    /**Dry pint (drypt) conversion factor. USCS volume unit.**/
    public final static double DryPint = 0.0005506104713575; //= DryGallon / 8;
    /**Quart (qt) conversion factor. Imperial volume unit.**/
    public final static double Quart = 0.0011365225; //= Gallon / 4;
    /**Imperial quart (impqt) conversion factor. Imperial volume unit.**/
    public final static double ImperialQuart = 0.0011365225; //= ImperialGallon / 4;
    /**Liquid quart (liquidqt) conversion factor. USCS volume unit.**/
    public final static double LiquidQuart = 0.000946352946; //= LiquidGallon / 4;
    /**Dry quart (dryqt) conversion factor. USCS volume unit.**/
    public final static double DryQuart = 0.001101220942715; //= DryGallon / 4;
    /**Gallon (gal) conversion factor. Imperial volume unit.**/
    public final static double Gallon = 0.00454609; //= 4.54609m * Litre;
    /**Imperial gallon (impgal) conversion factor. Imperial volume unit.**/
    public final static double ImperialGallon = 0.00454609; //= 4.54609m * Litre;
    /**Liquid gallon (liquidgal) conversion factor. USCS volume unit.**/
    public final static double LiquidGallon = 0.003785411784; //= 3.785411784m * Litre;
    /**Dry gallon (drygal) conversion factor. USCS volume unit.**/
    public final static double DryGallon = 0.00440488377086; //268.8025m * CubicInch;

    //--- Angle
    /**
    Radian (rad) conversion factor. SI angle unit.
    Reference point for all the angle units.
    **/
    public final static double Radian = 1.0;
    /**Degree (°) conversion factor. Angle unit.**/
    public final static double Degree = 0.0174532925199432957692369077; //= MathematicalConstants.Pi / 180;
    /**Revolution (rev) conversion factor. Angle unit.**/
    public final static double Revolution = 6.283185307179586476925286766; //= 2m * MathematicalConstants.Pi;
    /**Arcminute (') conversion factor. Angle unit.**/
    public final static double Arcminute = 0.0002908882086657215961539485; //= Degree / 60;
    /**Arcsecond ('') conversion factor. Angle unit.**/
    public final static double Arcsecond = 0.0000048481368110953599358991; //= Arcminute / 60;
    /**Gradian (grad) conversion factor. Angle unit.**/
    public final static double Gradian = 0.0157079632679489661923132169; //= Revolution / 400;
    /**Gon (gon) conversion factor. Angle unit.**/
    public final static double Gon = 0.0157079632679489661923132169; //= Revolution / 200;

    //--- Information
    /**
    Bit (bit) conversion factor. Information unit.
    Reference point for all the information units.
    **/
    public final static double Bit = 1.0;
    /**Nibble (nibble) conversion factor. Information unit.**/
    public final static double Nibble = 4.0;
    /**Quartet (quartet) conversion factor. Information unit.**/
    public final static double Quartet = 4.0;
    /**Byte (byte) conversion factor. Information unit.**/
    public final static double Byte = 8.0;
    /**Octet (octet) conversion factor. Information unit.**/
    public final static double Octet = 8.0;

    //--- Force
    /**
    Newton (N) conversion factor. SI force unit.
    Reference point for all the force units.
    **/
    public final static double Newton = 1.0;
    /**Kilopond (kp) conversion factor. Force unit.**/
    public final static double Kilopond = 9.80665; //= PhysicalConstants.GravityAcceleration;
    /**Pound-force (lbf) conversion factor. Imperial/USCS force unit.**/
    public final static double PoundForce = 4.4482216152605; //= Pound * PhysicalConstants.GravityAcceleration / 1000;
    /**Kip (kip) conversion factor. Force unit.**/
    public final static double Kip = 4448.2216152605; //= 1000m * PoundForce;
    /**Poundal (pdl) conversion factor. Imperial/USCS force unit.**/
    public final static double Poundal = 0.138254954376;
    /**Ounce-force (ozf) conversion factor. Imperial/USCS force unit.**/
    public final static double OunceForce = 0.27801385095378125; //= PoundForce / 16m
    /**Dyne (dyn) conversion factor. Force unit.**/
    public final static double Dyne = 1E-5; //= Centimetre / SIPrefixValues.Kilo;

    //--- Velocity
    /**
    Metre per second (m/s) conversion factor. SI velocity unit.
    Reference point for all the velocity units.
    **/
    public final static double MetrePerSecond = 1.0;
    /**Centimetre per second (cm/s) conversion factor. CGS velocity unit.**/
    public final static double CentimetrePerSecond = 0.01; //= Centimetre;
    /**Foot per second (ft/s) conversion factor. Imperial/USCS velocity unit.**/
    public final static double FootPerSecond = 0.3048; //= Foot;
    /**Inch per second (in/s) conversion factor. Imperial/USCS velocity unit.**/
    public final static double InchPerSecond = 0.0254; //= Inch;
    /**Kilometre per hour (kph) conversion factor. Velocity unit.**/
    public final static double KilometrePerHour = 0.2777777777777777777777777778; //= SIPrefixValues.Kilo / Hour;
    /**Knot (kn) conversion factor. Velocity unit.**/
    public final static double Knot = 0.5144444444444444444444444444; //= NauticalMile / Hour;
    /**Mile per hour (mph) conversion factor. Imperial/USCS Velocity unit.**/
    public final static double MilePerHour = 0.44704; //= Mile / Hour;

    //--- Acceleration
    /**
    Metre per square second (m/s2) conversion factor. SI acceleration unit.
    Reference point for all the acceleration units.
    **/
    public final static double MetrePerSquareSecond = 1.0;
    /**Gal (Gal) conversion factor. CGS acceleration unit.**/
    public final static double Gal = 0.01; //= Centimetre;
    /**Foot per square second (ft/s2) conversion factor. Imperial/USCS acceleration unit.**/
    public final static double FootPerSquareSecond = 0.3048; //= Foot;
    /**Inch per square second (in/s2) conversion factor. Imperial/USCS acceleration unit.**/
    public final static double InchPerSquareSecond = 0.0254; //= Inch;

    //--- Energy
    /**
    Joule (J) conversion factor. SI energy unit.
    Reference point for all the energy units.
    **/
    public final static double Joule = 1.0;
    /**Electronvolt (eV) conversion factor. Energy unit.**/
    public final static double Electronvolt = 1.6021766208E-19; //= PhysicalConstants.Electronvolt;
    /**Watt hour (Wh) conversion factor. Energy unit.**/
    public final static double WattHour = 3600.0; //= Hour;
    /**IT calorie (cal) conversion factor. Energy unit.**/
    public final static double Calorie = 4.1868;
    /**Thermochemical calorie (thcal) conversion factor. Energy unit.**/
    public final static double ThermochemicalCalorie = 4.184;
    /**Food calorie (kcal) conversion factor. Energy unit.**/
    public final static double FoodCalorie = 4186.8;
    /**IT British thermal unit (BTU) conversion factor. Imperial/USCS energy unit.**/
    public final static double BritishThermalUnit = 1055.05585262;
    /**Thermochemical British thermal unit (thBTU) conversion factor. Imperial/USCS energy unit.**/
    public final static double ThermochemicalBritishThermalUnit = 1054.3502644888888888888888889; //= BritishThermalUnit * ThermochemicalCalorie / Calorie;            
    /**Erg (erg) conversion factor. CGS Energy unit.**/
    public final static double Erg = 1E-7; //Dyne * Centimetre;
    /**EC therm (thm) conversion factor. Energy unit.**/              
    public final static double Therm = 105505600.0;
    /**UK therm (ukthm) conversion factor. Energy unit.**/              
    public final static double UKTherm = 105505585.257348;
    /**US therm (usthm) conversion factor. Energy unit.**/              
    public final static double USTherm = 105480400.0;

    //--- Power
    /**
    Watt (W) conversion factor. SI power unit.
    Reference point for all the power units.
    **/
    public final static double Watt = 1.0;
    /**Erg per second (erg/s) conversion factor. CGS power unit.**/
    public final static double ErgPerSecond = 1E-7; //= Erg;
    /**Mechanical horsepower (hp) conversion factor. Imperial/USCS power unit.**/
    public final static double Horsepower = 745.69987158227022; //= 550m * PoundForce * Foot;
    /**Metric horsepower (hpM) conversion factor. Power unit.**/
    public final static double MetricHorsepower = 735.49875; //= 75m * PhysicalConstants.GravityAcceleration;
    /**Boiler horsepower (hpS) conversion factor. Power unit.**/
    public final static double BoilerHorsepower = 9809.5;
    /**Electric horsepower (hpE) conversion factor. Power unit.**/
    public final static double ElectricHorsepower = 746.0;
    /**Ton of Refrigeration (TR) conversion factor. Power unit.**/
    public final static double TonOfRefrigeration = 3516.8528420666666666666666667; //= 12000m * BritishThermalUnit / Hour;

    //--- Pressure
    /**
    Pascal (Pa) conversion factor. SI pressure unit.
    Reference point for all the pressure units.
    **/
    public final static double Pascal = 1.0;
    /**Atmosphere (atm) conversion factor. Pressure unit.**/
    public final static double Atmosphere = 101325.0; //= PhysicalConstants.StandardAtmosphere;
    /**Technical atmosphere (at) conversion factor. Pressure unit.**/
    public final static double TechnicalAtmosphere = 98066.5; //= Kilopond / SquareCentimetre;
    /**Bar (bar) conversion factor. Pressure unit.**/
    public final static double Bar = 100000.0; //= 1E6m * Dyne / SquareCentimetre;
    /**Pound-force per square inch (psi) conversion factor. Imperial/USCS pressure unit.**/
    public final static double PoundforcePerSquareInch = 6894.7572931683613367226734453; //= PoundForce / SquareInch;
    /**Pound-force per square foot (psf) conversion factor. Imperial/USCS pressure unit.**/
    public final static double PoundforcePerSquareFoot = 47.880258980335842616129676704; //= PoundForce / SquareFoot;
    /**Millimetre of Mercury (mmHg) conversion factor. Pressure unit.**/
    public final static double MillimetreOfMercury = 133.322387415;
    /**Inch of Mercury at 32 °F (inHg32) conversion factor. Pressure unit.**/
    public final static double InchOfMercury32F = 3386.38;
    /**Inch of Mercury at 60 °F (inHg60) conversion factor. Pressure unit.**/
    public final static double InchOfMercury60F = 3376.85;
    /**Barye (Ba) conversion factor. CGS pressure unit.**/
    public final static double Barye = 0.1; //= Dyne / SquareCentimetre;
    /**Torr (Torr) conversion factor. Pressure unit.**/
    public final static double Torr = 133.32236842105263157894736842; //= PhysicalConstants.StandardAtmosphere / 760;
    /**Kip per square inch (ksi) conversion factor. Pressure unit.**/
    public final static double KipPerSquareInch = 6894757.2931683613367226734453; //= Kip / SquareInch;

    //--- Frequency
    /**
    Hertz (Hz) conversion factor. SI frequency unit.
    Reference point for all the frequency units.
    **/
    public final static double Hertz = 1.0;
    /**Cycle per second (cps) conversion factor. Frequency unit.**/
    public final static double CyclePerSecond = 1.0;

    //--- Electric Charge
    /**
    Coulomb (C) conversion factor. SI electric charge unit.
    Reference point for all the electric charge units.
    **/
    public final static double Coulomb = 1.0;
    /**AmpereHour (Ah) conversion factor. Electric charge unit.**/
    public final static double AmpereHour = 3600.0;
    /**Franklin (Fr) conversion ratio. CGS-Gaussian/CGS-ESU electric charge unit.**/
    public final static double Franklin = 0.0000000003335640951981520496; //= 1m / PhysicalConstants.SpeedOfLight / 10;
    /**Statcoulomb (statC) conversion ratio. CGS-Gaussian/CGS-ESU electric charge unit.**/
    public final static double Statcoulomb = 0.0000000003335640951981520496; //= 1m / PhysicalConstants.SpeedOfLight / 10;
    /**Electrostatic unit of charge (ESUcha) conversion ratio. CGS-Gaussian/CGS-ESU electric charge unit.**/
    public final static double ESUOfCharge = 0.0000000003335640951981520496; //= 1m / PhysicalConstants.SpeedOfLight / 10;
    /**Abcoulomb (abC) conversion ratio. CGS-EMU electric charge unit.**/
    public final static double Abcoulomb = 10.0;
    /**Electromagnetic unit of charge (EMUcha) conversion ratio. CGS-EMU electric charge unit.**/
    public final static double EMUOfCharge = 10.0;

    //--- Electric Current
    /**
    Ampere (A) conversion factor. SI electric current unit.
    Reference point for all the electric current units.
    **/
    public final static double Ampere = 1.0;
    /**Statampere (statA) conversion factor. CGS-Gaussian/CGS-ESU electric current unit.**/
    public final static double Statampere = 0.0000000003335640951981520496; //= 1m / PhysicalConstants.SpeedOfLight / 10;
    /**Electrostatic unit of current (ESUcur) conversion factor. CGS-Gaussian/CGS-ESU electric current unit.**/
    public final static double ESUOfCurrent = 0.0000000003335640951981520496; //= 1m / PhysicalConstants.SpeedOfLight / 10;
    /**Abampere (abA) conversion factor. CGS-EMU electric current unit.**/
    public final static double Abampere = 10.0;
    /**Biot (Bi) conversion factor. CGS-EMU electric current unit.**/
    public final static double Biot = 10.0;
    /**Electromagnetic unit of current (EMUcur) conversion factor. CGS-EMU electric current unit.**/
    public final static double EMUOfCurrent = 10.0;

    //--- Electric Voltage
    /**
    Volt (V) conversion factor. SI electric voltage unit.
    Reference point for all the electric voltage units.
    **/
    public final static double Volt = 1.0;
    /**Statvolt (statV) conversion factor. CGS-Gaussian/CGS-ESU voltage unit.**/
    public final static double Statvolt = 299.792458; //= PhysicalConstants.SpeedOfLight / 1E6;
    /**Electrostatic unit of electric potential (ESUpot) conversion factor. CGS-Gaussian/CGS-ESU voltage unit.**/
    public final static double ESUOfElectricPotential = 299.792458; //= PhysicalConstants.SpeedOfLight / 1E6;
    /**Abvolt (abV) conversion factor. CGS-EMU voltage unit.**/
    public final static double Abvolt = 1E-8;
    /**Electromagnetic unit of electric potential (EMUpot) conversion factor. CGS-EMU voltage unit.**/
    public final static double EMUOfElectricPotential = 1E-8;

    //--- Electric Resistance
    /**
    Ohm (O) conversion factor. SI electric resistance unit.
    Reference point for all the electric resistance units.
    **/
    public final static double Ohm = 1.0;
    /**Statohm (statO) conversion factor. CGS-Gaussian/CGS-ESU electric resistance unit.**/
    public final static double Statohm = 8.987551787E11;
    /**Electrostatic unit of resistance (ESUres) conversion factor. CGS-Gaussian/CGS-ESU electric resistance unit.**/
    public final static double ESUOfResistance = 8.987551787E11;
    /**Abohm (abO) conversion factor. CGS-EMU electric resistance unit.**/
    public final static double Abohm = 1E-9;
    /**Electromagnetic unit of resistance (EMUres) conversion factor. CGS-EMU electric resistance unit.**/
    public final static double EMUOfResistance = 1E-9;

    //--- Electric Resistivity
    /**
    Ohm metre (O*m) conversion factor. SI electric resistivity unit.
    Reference point for all the electric resistivity units.
    **/
    public final static double OhmMetre = 1.0;

    //--- Electric Conductance
    /**
    Siemens (S) conversion factor. SI electric conductance unit.
    Reference point for all the electric conductance units.
    **/
    public final static double Siemens = 1.0;
    /**Mho (?) conversion factor. SI electric conductance unit.**/
    public final static double Mho = 1.0;
    /**Gemmho (gemmho) conversion factor. SI electric conductance unit.<p>Reference point for all the electric conductance units.**/
    public final static double Gemmho = 0.000001;
    /**Statsiemens (statS) conversion factor. CGS-Gaussian/CGS-ESU electric conductance unit.**/
    public final static double Statsiemens = 1.1126500560536184E-12; //= 1E5m / PhysicalConstants.SpeedOfLight / PhysicalConstants.SpeedOfLight
    /**Statmho (stat?) conversion factor. CGS-Gaussian/CGS-ESU electric conductance unit.**/
    public final static double Statmho = 1.1126500560536184E-12; //= 1E5m / PhysicalConstants.SpeedOfLight / PhysicalConstants.SpeedOfLight
    /**Absiemens (abS) conversion factor. CGS-EMU electric conductance unit.**/
    public final static double Absiemens = 1E9;
    /**Abmho (ab?) conversion factor. CGS-EMU electric conductance unit.**/
    public final static double Abmho = 1E9;

    //--- Electric Conductivity
    /**
    Siemens per metre (S/m) conversion factor. SI electric conductivity unit.
    Reference point for all the electric conductivity units.
    **/
    public final static double SiemensPerMetre = 1.0;

    //--- Electric Capacitance
    /**Farad (F) conversion factor. SI electric capacitance unit.<p>Reference point for all the electric capacitance units.**/
    public final static double Farad = 1.0;
    /**Statfarad (statF) conversion factor. CGS-Gaussian/CGS-ESU electric capacitance unit.**/
    public final static double Statfarad = 1.1126500560536184E-12; //= 1E5m / PhysicalConstants.SpeedOfLight / PhysicalConstants.SpeedOfLight
    /**Electrostatic unit of capacitance (ESUcap) conversion factor. CGS-Gaussian/CGS-ESU electric capacitance unit.**/
    public final static double ESUOfCapacitance = 1.1126500560536184E-12; //= 1E5m / PhysicalConstants.SpeedOfLight / PhysicalConstants.SpeedOfLight
    /**Abfarad (abF) conversion factor. CGS-EMU electric capacitance unit.**/
    public final static double Abfarad = 1E9;
    /**Electromagnetic unit of capacitance (EMUcap) conversion factor. CGS-EMU electric resistance unit.**/
    public final static double EMUOfCapacitance = 1E9;

    //--- Electric Inductance
    /**Henry (H) conversion factor. SI electric inductance unit.<p>Reference point for all the electric inductance units.**/
    public final static double Henry = 1.0;
    /**Stathenry (statH) conversion factor. CGS-Gaussian/CGS-ESU electric inductance unit.**/
    public final static double Stathenry = 8.987551787E11;
    /**Electrostatic unit of inductance (ESUind) conversion factor. CGS-Gaussian/CGS-ESU electric inductance unit.**/
    public final static double ESUOfInductance = 8.987551787E11;
    /**Abhenry (abH) conversion factor. CGS-EMU electric inductance unit.**/
    public final static double Abhenry = 1E-9;
    /**Electromagnetic unit of inductance (EMUind) conversion factor. CGS-EMU electric inductance unit.**/
    public final static double EMUOfInductance = 1E-9;

    //--- Electric Dipole Moment
    /**
    Coulomb Metre (C*m) conversion factor. SI electric dipole unit.
    Reference point for all the electric dipole moment units.
    **/   
    public final static double CoulombMetre = 1.0;
    /**
    Debye (D) conversion factor. CGS-Gaussian electric dipole moment unit.
    The double type cannot deal directly with the actual conversion factor (3.33564095E-30m).  
    **/
    public final static double Debye = -2.0;

    //--- Wavenumber
    /**
    Reciprocal metre (1/m) conversion factor. SI wavenumber unit.
    Reference point for all the wavenumber units.
    **/   
    public final static double ReciprocalMetre = 1.0;
    /**Kayser (kayser) conversion factor. CGS wavenumber unit.**/
    public final static double Kayser = 100.0;

    //--- Viscosity
    /**
    Pascal second (Pa*s) conversion factor. SI viscosity unit.
    Reference point for all the viscosity units.
    **/   
    public final static double PascalSecond = 1.0;
    /**Poise (P) conversion factor. CGS viscosity unit.**/
    public final static double Poise = 0.1;

    //--- Kinematic Viscosity
    /**
    Square metre per second (m2/s) conversion factor. SI kinematic viscosity unit.
    Reference point for all the kinematic viscosity units.
    **/   
    public final static double SquareMetrePerSecond = 1.0;
    /**Stokes (St) conversion factor. CGS kinematic viscosity unit.**/
    public final static double Stokes = 0.0001; //= SquareCentimetre;

    //--- Amount of Substance
    /**
    Mole (mol) conversion factor. SI amount of substance unit.
    Reference point for all the amount of substance units.
    **/   
    public final static double Mole = 1.0;
    /**Pound-mole (lbmol) conversion factor. Amount of substance unit.**/
    public final static double PoundMole = 453.59237;

    //--- Momentum
    /**
    Newton second (N*s) conversion factor.
    Reference point for all the momentum units.
    **/    
    public final static double NewtonSecond = 1.0;

    //--- Angular Velocity
    /**
    Radian per second (rad/s) conversion factor.
    Reference point for all the angular velocity units.
    **/  
    public final static double RadianPerSecond = 1.0;
    /**Revolution per minute (rpm) conversion factor. Angular velocity unit.**/
    public final static double RevolutionPerMinute = 0.1047197551196597746154214461; //= Revolution / Minute;

    //--- Angular Acceleration
    /**
    Radian per square second (rad/s2) conversion factor.
    Reference point for all the angular acceleration units.
    **/  
    public final static double RadianPerSquareSecond = 1.0;

    //--- Angular Momentum
    /**
    Joule second (J*s) conversion factor.
    Reference point for all the angular momentum units.
    **/  
    public final static double JouleSecond = 1.0;

    //--- Moment of Inertia
    /**
    Kilogram square metre (kg*m2) conversion factor.
    Reference point for all the moment of inertia units.
    **/  
    public final static double KilogramSquareMetre = 1.0;

    //--- Solid Angle
    /**
    Steradian (sr) conversion factor. SI solid angle unit.
    Reference point for all the solid angle units.
    **/                 
    public final static double Steradian = 1.0;
    /**Square degree (deg2) conversion factor. Solid angle unit.**/                 
    public final static double SquareDegree = 0.0003046174197867085993467435; //= MathematicalConstants.Pi * MathematicalConstants.Pi / 180m / 180;

    //--- Luminous Intensity
    /**
    Candela (cd) conversion factor. SI luminous intensity unit.
    Reference point for all the luminous intensity units.
    **/                 
    public final static double Candela = 1.0;

    //--- Luminous Flux
    /**
    Lumen (lm) conversion factor. SI luminous flux unit.
    Reference point for all the luminous flux units.
    **/                 
    public final static double Lumen = 1.0;

    //--- Luminous Energy
    /**
    Lumen second (lm*s) conversion factor. Luminous energy unit.
    Reference point for all the luminous energy units.
    **/                 
    public final static double LumenSecond = 1.0;
    /**
    Talbot (talbot) conversion factor. Luminous energy unit.
    Reference point for all the luminous energy units.
    **/                 
    public final static double Talbot = 1.0;

    //--- Luminance
    /**
    Candela per square metre (cd/m2) conversion factor.
    Reference point for all the luminance units.
    **/                 
    public final static double CandelaPerSquareMetre = 1.0;
    /**Nit (nt) conversion factor. Luminance unit*/                 
    public final static double Nit = 1.0;
    /**Stilb (sb) conversion factor. CGS luminance unit*/                 
    public final static double Stilb = 1E4;
    /**Lambert (lambert) conversion factor. CGS luminance unit.**/                 
    public final static double Lambert = 3183.098861837906715377675268; //= 1m / MathematicalConstants.Pi / UnitConversionFactors.Centimetre / UnitConversionFactors.Centimetre;
    /**Foot-lambert (ftL) conversion factor. Imperial/USCS luminance unit.**/                 
    public final static double FootLambert = 3.4262590996353905269167459623; //= 1m / MathematicalConstants.Pi / UnitConversionFactors.Foot / UnitConversionFactors.Foot;

    //--- Illuminance
    /**
    Lux (lx) conversion factor. SI illuminance unit.
    Reference point for all the illuminance units.
    **/                 
    public final static double Lux = 1.0;
    /**Phot (ph) conversion factor. CGS illuminance unit.**/                 
    public final static double Phot = 1E4;
    /**Foot-candle (fc) conversion factor. Imperial/USCS illuminance unit.**/                 
    public final static double FootCandle = 10.763910416709722308333505556; //= 1m / UnitConversionFactors.Foot / UnitConversionFactors.Foot;

    //--- Logarithmic
    /**
    Bel (B) conversion factor. Logarithmic unit.
    Reference point for all the logarithmic units.
    **/                        
    public final static double Bel = 1.0;
    /**Neper (Np) conversion factor. Logarithmic unit.**/      
    public final static double Neper = 0.8685889638065035; //= 2.0 / Math.Log(10);

    //--- Magnetic Flux
    /**
    Weber (Wb) conversion factor. SI magnetic flux unit.
    Reference point for all the magnetic flux units.
    **/
    public final static double Weber = 1.0;
    /**Maxwell (Mx) conversion factor. CGS-Gaussian/CGS-EMU magnetic flux unit.**/                         
    public final static double Maxwell = 1E-8;

    //--- Magnetic Field B
    /**
    Tesla (T) conversion factor. SI magnetic field B unit.
    Reference point for all the magnetic field B units.
    **/
    public final static double Tesla = 1.0;
    /**Gauss (G) conversion factor. CGS-Gaussian/CGS-EMU magnetic field B unit.**/                         
    public final static double Gauss = 1E-4;

    //--- Magnetic Field H
    /**
    Ampere per metre (A/m) conversion factor. SI magnetic field H unit.
    Reference point for all the magnetic field H units.
    **/ 
    public final static double AmperePerMetre = 1.0;
    /**Oersted (Oe) conversion factor. CGS-Gaussian/CGS-EMU magnetic field H unit.**/                         
    public final static double Oersted = 79.57747154594766788444188169; //= 1000m / MathematicalConstants.Pi / 4;

    //--- Radioactivity
    /**
    Becquerel (Bq) conversion factor. SI radioactivity unit.
    Reference point for all the radioactivity units.
    **/
    public final static double Becquerel = 1.0;
    /**Curie (Ci) conversion factor. Radioactivity unit.**/ 
    public final static double Curie = 3.7E10;
    /**Disintegrations per second (dps) conversion factor. Radioactivity unit.**/ 
    public final static double DisintegrationsPerSecond = 1.0;
    /**Disintegrations per minute (dpm) conversion factor. Radioactivity unit.**/ 
    public final static double DisintegrationsPerMinute = 0.0166666666666666666666666667; //= 1m / Minute;
    /**Rutherford (Rd) conversion factor. Radioactivity unit.**/ 
    public final static double Rutherford = 1E6; //= SIPrefixes.Mega;

    //--- Absorbed Dose
    /**
    Gray (Gy) conversion factor. SI absorbed dose unit.
    Reference point for all the absorbed dose units.
    **/ 
    public final static double Gray = 1.0;
    /**Rad (Rad) conversion factor. CGS absorbed dose unit.**/ 
    public final static double Rad = 0.01;

    //--- Absorbed Dose Rate
    /**
    Gray per second (Gy/s) conversion factor. SI absorbed dose rate unit.
    Reference point for all the absorbed dose rate units.
    **/ 
    public final static double GrayPerSecond = 1.0;

    //--- Equivalent Dose
    /**
    Sievert (Sv) conversion factor. SI equivalent dose unit.
    Reference point for all the equivalent dose units.
    **/ 
    public final static double Sievert = 1.0;
    /**Roentgen equivalent in man (rem) conversion factor. CGS equivalent dose unit.**/ 
    public final static double REM = 0.01;

    //--- Exposure
    /**
    Coulomb per kilogram (C/kg) conversion factor. SI exposure unit.
    Reference point for all the exposure units.
    **/ 
    public final static double CoulombPerKilogram = 1.0;
    /**Roentgen (R) conversion factor. CGS exposure unit.**/ 
    public final static double Roentgen = 0.000258;

    //--- Catalytic Activity
    /**
    Katal (kat) conversion factor. SI catalytic activity unit.
    Reference point for all the catalytic activity units.
    **/ 
    public final static double Katal = 1.0;

    //--- Catalytic Activity Concentration
    /**
    Katal per cubic metre (kat/m3) conversion factor. SI catalytic activity concentration unit.
    Reference point for all the catalytic activity concentration units.            
    **/ 
    public final static double KatalPerCubicMetre = 1.0;

    //--- Jerk
    /**
    Metre per cubic second (m/s3) conversion factor. SI jerk unit.
    Reference point for all the jerk units.
    **/ 
    public final static double MetrePerCubicSecond = 1.0;

    //--- Mass Flow Rate
    /**
    Kilogram per second (kg/s) conversion factor. SI mass flow rate unit.
    Reference point for all the mass flow rate units.          
    **/ 
    public final static double KilogramPerSecond = 1.0;

    //--- Density
    /**
    Kilogram per cubic metre (kg/m3) conversion factor. SI density unit.
    Reference point for all the density units.            
    **/ 
    public final static double KilogramPerCubicMetre = 1.0;

    //--- Area Density
    /**
    Kilogram per square metre (kg/m2) conversion factor. SI area density unit.
    Reference point for all the area density units.            
    **/ 
    public final static double KilogramPerSquareMetre = 1.0;

    //--- Energy Density
    /**
    Joule per cubic metre (J/m3) conversion factor. SI energy density unit.
    Reference point for all the energy density units.            
    **/ 
    public final static double JoulePerCubicMetre = 1.0;

    //--- Specific Volume
    /**
    Cubic metre per kilogram (m3/kg) conversion factor. SI specific volume unit.
    Reference point for all the specific volume units.            
    **/ 
    public final static double CubicMetrePerKilogram = 1.0;

    //--- Volumetric Flow Rate
    /**
    Cubic metre per second (m3/s) conversion factor. SI volumetric flow rate unit.
    Reference point for all the volumetric flow rate units.                
    **/ 
    public final static double CubicMetrePerSecond = 1.0;

    //--- Surface Tension
    /**
    Joule per square metre (J/m2) conversion factor. SI surface tension unit.
    Reference point for all the surface tension units.            
    **/ 
    public final static double JoulePerSquareMetre = 1.0;

    //--- Specific Weight
    /**
    Newton per cubic metre (N/m3) conversion factor. SI specific weight unit.
    Reference point for all the specific weight units.            
    **/ 
    public final static double NewtonPerCubicMetre = 1.0;

    //--- Thermal Conductivity
    /**
    Watt per metre per kelvin (W/m*K) conversion factor. SI thermal conductivity unit.
    Reference point for all the thermal conductivity units.            
    **/ 
    public final static double WattPerMetrePerKelvin = 1.0;

    //--- Thermal Conductance
    /**
    Watt per kelvin (W/K) conversion factor. SI thermal conductance unit.
    Reference point for all the thermal conductance units.            
    **/ 
    public final static double WattPerKelvin = 1.0;

    //--- Thermal Resistivity
    /**
    Metre kelvin per watt (m*K/W) conversion factor. SI thermal resistivity unit.
    Reference point for all the thermal resistivity units.             
    **/ 
    public final static double MetreKelvinPerWatt = 1.0;

    //--- Thermal Resistance
    /**
    Kelvin per watt (K/W) conversion factor. SI thermal resistance unit.
    Reference point for all the thermal resistance units.             
    **/ 
    public final static double KelvinPerWatt = 1.0;

    //--- Heat Transfer Coefficient
    /**
    Watt per square metre per kelvin (W/m2*K) conversion factor. SI heat transfer coefficient unit.
    Reference point for all the heat transfer coefficient units.             
    **/ 
    public final static double WattPerSquareMetrePerKelvin = 1.0;

    //--- Heat Flux Density
    /**
    Watt per square metre (W/m2) conversion factor. SI heat flux density unit.
    Reference point for all the heat flux density units.             
    **/ 
    public final static double WattPerSquareMetre = 1.0;

    //--- Entropy
    /**
    Joule per kelvin (J/K) conversion factor. SI entropy unit.
    Reference point for all the entropy units.  
    **/ 
    public final static double JoulePerKelvin = 1.0;

    //--- Electric Field Strength
    /**
    Newton per coulomb (N/C) conversion factor. SI Electric Field Strength unit.
    Reference point for all the Electric Field Strength units.              
    **/ 
    public final static double NewtonPerCoulomb = 1.0;
    /**
    Volt per metre (V/m) conversion factor. SI Electric Field Strength unit.              
    **/ 
    public final static double VoltPerMetre = 1.0;

    //--- Linear Electric Charge Density
    /**
    Coulomb per metre (C/m) conversion factor. SI linear electric charge density unit.
    Reference point for all the linear electric charge units.              
    **/ 
    public final static double CoulombPerMetre = 1.0;

    //--- Surface Electric Charge Density
    /**
    Coulomb per square metre (C/m2) conversion factor. SI surface electric charge density unit.
    Reference point for all the surface electric charge units.              
    **/ 
    public final static double CoulombPerSquareMetre = 1.0;

    //--- Volume Electric Charge Density
    /**
    Coulomb per cubic metre (C/m3) conversion factor. SI volume electric charge density unit.
    Reference point for all the volume electric charge density units.              
    **/ 
    public final static double CoulombPerCubicMetre = 1.0;

    //--- Current Density
    /**
    Ampere per square metre (A/m2) conversion factor. SI current density unit.
    Reference point for all the current density units.            
    **/ 
    public final static double AmperePerSquareMetre = 1.0;

    //--- Electromagnetic Permittivity
    /**
    Farad per metre (F/m) conversion factor. SI electromagnetic permittivity unit.
    Reference point for all the electromagnetic permittivity units.              
    **/ 
    public final static double FaradPerMetre = 1.0;

    //--- Electromagnetic Permeability
    /**
    Henry per metre (H/m) conversion factor. SI electromagnetic permeability unit.
    Reference point for all the electromagnetic permeability units.              
    **/ 
    public final static double HenryPerMetre = 1.0;

    //--- Molar Energy
    /**
    Joule per mole (J/mol) conversion factor. SI molar energy unit.
    Reference point for all the molar energy units.              
    **/ 
    public final static double JoulePerMole = 1.0;

    //--- Molar Entropy
    /**
    Joule per mole per kelvin (J/mol*K) conversion factor. SI molar entropy unit.
    Reference point for all the molar entropy units.              
    **/ 
    public final static double JoulePerMolePerKelvin = 1.0;

    //--- Molar Volume
    /**
    Cubic metre per mole (m3/mol) conversion factor. SI molar volume unit.
    Reference point for all the molar volume units.   
    **/ 
    public final static double CubicMetrePerMole = 1.0;

    //--- Molar Mass
    /**
    Kilogram per mole (kg/mol) conversion factor. SI molar mass unit.
    Reference point for all the molar mass units.   
    **/ 
    public final static double KilogramPerMole = 1.0;

    //--- Molar Concentration
    /**
    Mole per cubic metre (mol/m3) conversion factor. SI molar concentration unit.
    Reference point for all the molar concentration units.   
    **/ 
    public final static double MolePerCubicMetre = 1.0;

    //--- Molal Concentration
    /**
    Mole per kilogram (mol/kg) conversion factor. SI molal concentration unit.
    Reference point for all the molal concentration units.   
    **/ 
    public final static double MolePerKilogram = 1.0;

    //--- Radiant Intensity
    /**
    Watt per steradian (W/sr) conversion factor. SI radiant intensity unit.
    Reference point for all the radiant intensity units.              
    **/ 
    public final static double WattPerSteradian = 1.0;

    //--- Radiance
    /**
    Watt per steradian per square metre (W/sr*m2) conversion factor. SI radiance unit.
    Reference point for all the radiance units.              
    **/ 
    public final static double WattPerSteradianPerSquareMetre = 1.0;

    //--- Fuel Economomy
    /**
    Inverse square metre (1/m2) conversion factor. SI fuel economy unit.
    Reference point for all the fuel economy units.            
    **/ 
    public final static double InverseSquareMetre = 1.0;
    /**Mile per gallon (mpg) conversion factor. Imperial fuel economy unit.**/ 
    public final static double MilePerGallon = 354006.18993464713633034101833; //= Mile / Gallon;
    /**Imperial mile per gallon (impmpg) conversion factor. Imperial fuel economy unit.**/ 
    public final static double ImperialMilePerGallon = 354006.18993464713633034101833; //= Mile / Gallon;
    /**USCS mile per gallon (uscmpg) conversion factor. USCS fuel economy unit.**/ 
    public final static double USCSMilePerGallon = 425143.70743027200340114965944; //= Mile / LiquidGallon;
    /**Kilometre per litre (km/L) conversion factor. Fuel economy unit.**/ 
    public final static double KilometrePerLitre = 1000000.0; //= SIPrefixValues.Kilo / Litre;

    //--- Sound Exposure
    /**
    Square pascal second (Pa2*s) conversion factor. SI sound exposure unit.
    Reference point for all the sound exposure units.            
    **/ 
    public final static double SquarePascalSecond = 1.0;

    //--- Sound Impedance
    /**
    Pascal second per cubic metre (Pa*s/m3) conversion factor. SI sound impedance unit.
    Reference point for all the sound impedance units.          
    **/ 
    public final static double PascalSecondPerCubicMetre = 1.0;

    //--- Rotational Stiffness
    /**
    Newton metre per radian (N*m/rad) conversion factor. SI rotational stiffness unit.
    Reference point for all the rotational stiffness units.            
    **/ 
    public final static double NewtonMetrePerRadian = 1.0;

    //--- Bit Rate
    /**
    Bit per second (bit/s) conversion factor. Bit rate unit.
    Reference point for all the bit rate units.           
    **/ 
    public final static double BitPerSecond = 1.0;

    //--- Symbol Rate
    /**
    Baud (Bd) conversion factor. Symbol rate unit.
    Reference point for all the symbol rate units.           
    **/ 
    public final static double Baud = 1.0;
}