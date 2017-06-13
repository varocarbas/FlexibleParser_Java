package UnitParser;

/**
Contains the primary symbols, abbreviations and String representations of all the supported units.
All these strings are case sensitive.
**/
public class UnitSymbols
{
    //--- Length
    /**Metre symbol. SI length unit.**/  
    public final static String Metre = "m";
    /**Centimetre symbol. CGS length unit.**/  
    public final static String Centimetre = "cm";
    /**Astronomical unit symbol. Length unit.**/           
    public final static String AstronomicalUnit = "AU";
    /**Inch symbol. Imperial/USCS length unit.**/             
    public final static String Inch = "in";
    /**Foot symbol. Imperial/USCS length unit.**/             
    public final static String Foot = "ft";
    /**Yard symbol. Imperial/USCS length unit.**/               
    public final static String Yard = "yd";
    /**International mile symbol. Imperial/USCS length unit.**/                          
    public final static String Mile = "mi";
    /**
    Nautical mile symbol. Length unit.
    The "nm" alternative cannot be supported due to its incompatibility with the length unit nanometre.
    **/                   
    public final static String NauticalMile = "M";
    /**Thou String representation. Imperial/USCS length unit.**/                  
    public final static String Thou = "thou";
    /**Mil String representation. Imperial/USCS length unit.**/                  
    public final static String Mil = "mil";
    /**Fathom String representation. Imperial/USCS length unit.**/
    public final static String Fathom = "fathom";
    /**Rod symbol. Imperial/USCS length unit.**/
    public final static String Rod = "rd";
    /**Perch String representation. Imperial/USCS length unit.**/
    public final static String Perch = "perch";
    /**Pole String representation. Imperial/USCS length unit.**/
    public final static String Pole = "pole";
    /**Chain symbol. Imperial/USCS length unit.**/            
    public final static String Chain = "ch";
    /**Furlong symbol. Imperial/USCS length unit.**/
    public final static String Furlong = "fur";
    /**Link symbol. Imperial/USCS length unit.**/
    public final static String Link = "li";
    /**U.S. survey inch abbreviation. USCS length unit.**/
    public final static String SurveyInch = "surin";
    /**U.S. survey foot abbreviation. USCS length unit.**/
    public final static String SurveyFoot = "surft";
    /**U.S. survey yard abbreviation. USCS length unit.**/
    public final static String SurveyYard = "suryd";
    /**U.S. survey rod abbreviation. USCS length unit.**/
    public final static String SurveyRod = "surrd";
    /**U.S. survey chain abbreviation. USCS length unit.**/
    public final static String SurveyChain = "surch";
    /**U.S. survey link abbreviation. USCS length unit.**/
    public final static String SurveyLink = "surli";
    /**U.S. survey mile abbreviation. USCS length unit.**/
    public final static String SurveyMile = "surmi";
    /**U.S. survey fathom abbreviation. USCS length unit.**/
    public final static String SurveyFathom = "surfathom";
    /**Ångström symbol. Length unit.**/
    public final static String Angstrom = "Å";
    /**Fermi symbol. Length unit.**/
    public final static String Fermi = "f";
    /**Light year abbreviation. Length unit.**/
    public final static String LightYear = "ly";
    /**Parsec symbol. Length unit.**/
    public final static String Parsec = "pc";
    /**Micron symbol. Length unit.**/
    public final static String Micron = "μ";

    //--- Mass
    /**Gram symbol. SI mass unit.**/
    public final static String Gram = "g";
    /**Metric ton symbol. Mass unit.**/
    public final static String MetricTon = "t";
    /**Grain symbol. Imperial/USCS mass unit.**/
    public final static String Grain = "gr";
    /**Drachm symbol. Imperial/USCS mass unit.**/
    public final static String Drachm = "dr";
    /**Ounce symbol. Imperial/USCS mass unit.**/            
    public final static String Ounce = "oz";
    /**Pound symbol. Imperial/USCS mass unit.**/
    public final static String Pound = "lb";
    /**Stone symbol. Imperial/USCS mass unit.**/            
    public final static String Stone = "st";
    /**Slug symbol. Imperial/USCS mass unit.**/                      
    public final static String Slug = "sl";
    /**Quarter symbol. Imperial mass unit.**/          
    public final static String Quarter = "qr";
    /**Long quarter abbreviation. Imperial mass unit.**/         
    public final static String LongQuarter = "impqr";
    /**Short quarter abbreviation. USCS mass unit.**/           
    public final static String ShortQuarter = "uscqr";
    /**Hundredweight symbol. Imperial mass unit.**/          
    public final static String Hundredweight = "cwt";
    /**Long hundredweight abbreviation. Imperial mass unit.**/  
    public final static String LongHundredweight = "impcwt";
    /**Short hundredweight abbreviation. USCS mass unit.**/
    public final static String ShortHundredweight = "usccwt";
    /**Ton symbol. Imperial mass unit.**/           
    public final static String Ton = "tn";
    /**Long ton abbreviation. Imperial mass unit.**/           
    public final static String LongTon = "imptn";
    /**Short ton abbreviation. USCS mass unit.**/   
    public final static String ShortTon = "usctn";
    /**Carat symbol. Mass unit.**/  
    public final static String Carat = "ct";
    /**Dalton symbol. Mass unit.**/  
    public final static String Dalton = "Da";
    /**Unified atomic mass unit symbol. Mass unit.**/  
    public final static String UnifiedAtomicMassUnit = "u";

    //--- Time
    /**Second symbol. SI time unit.**/  
    public final static String Second = "s";
    /**
    Minute. Time unit.
    The "m" alternative cannot be supported due to its incompatibility with the length unit metre.
    **/  
    public final static String Minute = "min";
    /**Hour symbol. Time unit.**/  
    public final static String Hour = "h";
    /**Day symbol. Time unit.**/  
    public final static String Day = "d";
    /**Shake String representation. Time unit.**/  
    public final static String Shake = "shake";

    //--- Area
    /**Square metre symbol. SI area unit.**/  
    public final static String SquareMetre = "m2";
    /**Square centimetre symbol. CGS area unit.**/  
    public final static String SquareCentimetre = "cm2";
    /**Are symbol. Area unit.**/  
    public final static String Are = "a";
    /**Square foot symbol. Imperial/USCS area unit.**/  
    public final static String SquareFoot = "ft2";
    /**Square inch symbol. Imperial/USCS area unit.**/  
    public final static String SquareInch = "in2";
    /**Square rod symbol. Imperial/USCS area unit.**/  
    public final static String SquareRod = "rd2";
    /**Square perch String representation. Imperial/USCS area unit.**/  
    public final static String SquarePerch = "perch2";
    /**Square pole String representation. Imperial/USCS area unit.**/  
    public final static String SquarePole = "pole2";
    /**Rood astring representation. Imperial/USCS area unit.**/  
    public final static String Rood = "rood";
    /**Acre symbol. Imperial/USCS area unit.**/  
    public final static String Acre = "ac";
    /**Barn symbol. Area unit.**/  
    public final static String Barn = "b";
    /**U.S. survey acre abbreviation. USCS area unit.**/  
    public final static String SurveyAcre = "surac";

    //--- Volume
    /**Cubic metre symbol. SI volume unit.**/  
    public final static String CubicMetre = "m3";
    /**Cubic centimetre abbreviation. CGS volume unit.**/  
    public final static String CubicCentimetre = "cc";
    /**Litre symbol. Volume unit.**/  
    public final static String Litre = "L";
    /**Cubic foot symbol. Imperial/USCS volume unit.**/  
    public final static String CubicFoot = "ft3";
    /**Cubic inch symbol. Imperial/USCS volume unit.**/  
    public final static String CubicInch = "in3";
    /**Fluid ounce symbol. Imperial volume unit.**/  
    public final static String FluidOunce = "floz";
    /**Imperial fluid ounce abbreviation. Imperial volume unit.**/  
    public final static String ImperialFluidOunce = "impfloz";
    /**USCS fluid ounce abbreviation. USCS volume unit.**/  
    public final static String USCSFluidOunce = "uscfloz";
    /**Gill symbol. Imperial volume unit.**/            
    public final static String Gill = "gi";
    /**Imperial gill abbreviation. Imperial volume unit.**/            
    public final static String ImperialGill = "impgi";
    /**USCS gill abbreviation. USCS volume unit.**/  
    public final static String USCSGill = "uscgi";
    /**Pint symbol. Imperial volume unit.**/             
    public final static String Pint = "pt";
    /**Imperial pint abbreviation. Imperial volume unit.**/             
    public final static String ImperialPint = "imppt";
    /**Liquid pint abbreviation. USCS volume unit.**/
    public final static String LiquidPint = "liquidpt";
    /**Dry pint abbreviation. USCS volume unit.**/
    public final static String DryPint = "drypt";
    /**Quart symbol. Imperial volume unit.**/            
    public final static String Quart = "qt";
    /**Imperial quart abbreviation. Imperial volume unit.**/            
    public final static String ImperialQuart = "impqt";
    /**Liquid quart abbreviation. USCS volume unit.**/                        
    public final static String LiquidQuart = "liquidqt";
    /**Dry quart abbreviation. USCS volume unit.**/            
    public final static String DryQuart = "dryqt";
    /**Gallon symbol. Imperial volume unit.**/             
    public final static String Gallon = "gal";
    /**Imperial gallon abbreviation. Imperial volume unit.**/             
    public final static String ImperialGallon = "impgal";
    /**Liquid gallon abbreviation. USCS volume unit.**/     
    public final static String LiquidGallon = "liquidgal";
    /**Dry gallon abbreviation. USCS volume unit.**/           
    public final static String DryGallon = "drygal";

    //--- Angle
    /**Radian symbol. SI angle unit.**/ 
    public final static String Radian = "rad";
    /**Degree symbol. Angle unit.**/ 
    public final static String Degree = "°";
    /**Arcminute symbol. Angle unit.**/             
    public final static String Arcminute = "'";
    /**Arcsecond symbol. Angle unit.**/                         
    public final static String Arcsecond = "''";
    /**Revolution abbreviation. Angle unit.**/             
    public final static String Revolution = "rev";
    /**Gradian symbol. Angle unit.**/             
    public final static String Gradian = "grad";
    /**Gon symbol. Angle unit.**/             
    public final static String Gon = "gon";

    //--- Information
    /**
    Bit String representation. Information unit.
    The "b" alternative cannot be supported due to its incompatibility with the area unit barn.
    **/                         
    public final static String Bit = "bit";
    /**
    Byte String representation. Information unit.
    The "B" alternative cannot be supported due to its incompatibility with the logarithmic unit bel.
    **/  
    public final static String Byte = "byte";
    /**Nibble String representation. Information unit.**/  
    public final static String Nibble = "nibble";
    /**Quartet String representation. Information unit.**/            
    public final static String Quartet = "quartet";
    /**Octet String representation. Information unit.**/             
    public final static String Octet = "octet";

    //--- Force
    /**Newton symbol. SI force unit.**/             
    public final static String Newton = "N";
    /**Kilopond symbol. Force unit.**/   
    public final static String Kilopond = "kp";
    /**Pound-force symbol. Imperial/USCS force unit.**/               
    public final static String PoundForce = "lbf";
    /**Kip symbol. Force unit.**/               
    public final static String Kip = "kip";
    /**Poundal symbol. Imperial/USCS force unit.**/                
    public final static String Poundal = "pdl";
    /**Ounce-force symbol. Imperial/USCS force unit.**/                
    public final static String OunceForce = "ozf";
    /**Dyne symbol. CGS Force unit.**/              
    public final static String Dyne = "dyn";

    //--- Velocity
    /**Metre per second symbol. SI velocity unit.**/  
    public final static String MetrePerSecond = "m/s";
    /**Centimetre per second symbol. CGS velocity unit.**/  
    public final static String CentimetrePerSecond = "cm/s";
    /**Foot per second symbol. Imperial/USCS velocity unit.**/  
    public final static String FootPerSecond = "ft/s";
    /**Inch per second symbol. Imperial/USCS velocity unit.**/  
    public final static String InchPerSecond = "in/s";
    /**Knot symbol. Velocity unit.**/
    public final static String Knot = "kn";
    /**Kilometre per hour abbreviation. Velocity unit.**/
    public final static String KilometrePerHour = "kph";
    /**Mile per hour abbreviation. Velocity unit.**/
    public final static String MilePerHour = "mph";

    //--- Acceleration
    /**Metre per square second symbol. SI acceleration unit.**/  
    public final static String MetrePerSquareSecond = "m/s2";
    /**Gal symbol. CGS acceleration unit.**/
    public final static String Gal = "Gal";
    /**Foot per square second symbol. Imperial/USCS acceleration unit.**/  
    public final static String FootPerSquareSecond = "ft/s2";
    /**Inch per square second symbol. Imperial/USCS acceleration unit.**/  
    public final static String InchPerSquareSecond = "in/s2";

    //--- Energy
    /**Joule symbol. SI energy unit.**/   
    public final static String Joule = "J";
    /**Electronvolt symbol. Energy unit.**/            
    public final static String Electronvolt = "eV";
    /**Watt hour abbreviation. Energy unit.**/   
    public final static String WattHour = "Wh";
    /**IT British thermal unit abbreviation. Imperial/USCS energy unit.**/                
    public final static String BritishThermalUnit = "BTU";
    /**Thermochemical British thermal unit abbreviation. Imperial/USCS energy unit.**/                
    public final static String ThermochemicalBritishThermalUnit = "thBTU";
    /**IT calorie symbol. Energy unit.**/              
    public final static String Calorie = "cal";
    /**Thermochemical calorie abbreviation. Energy unit.**/              
    public final static String ThermochemicalCalorie = "thcal";
    /**Food calorie symbol. Energy unit.**/              
    public final static String FoodCalorie = "kcal";
    /**Erg symbol. CGS energy unit.**/              
    public final static String Erg = "erg";
    /**EC therm symbol. Energy unit.**/              
    public final static String Therm = "thm";
    /**UK therm abbreviation. Energy unit.**/              
    public final static String UKTherm = "ukthm";
    /**US therm abbreviation. Energy unit.**/              
    public final static String USTherm = "usthm";

    //--- Power
    /**Watt symbol. SI power unit.**/              
    public final static String Watt = "W";
    /**Erg per second symbol. CGS power unit.**/              
    public final static String ErgPerSecond = "erg/s";
    /**Mechanical horsepower symbol. Imperial/USCS power unit.**/              
    public final static String Horsepower = "hp";
    /**Metric horsepower abbreviation. Power unit.**/    
    public final static String MetricHorsepower = "hpM";
    /**Boiler horsepower abbreviation. Power unit.**/    
    public final static String BoilerHorsepower = "hpS";
    /**Electric horsepower abbreviation. Power unit.**/    
    public final static String ElectricHorsepower = "hpE";
    /**Ton of refrigeration abbreviation. Power unit.**/              
    public final static String TonOfRefrigeration = "TR";

    //--- Pressure
    /**Pascal symbol. SI pressure unit.**/    
    public final static String Pascal = "Pa";
    /**Atmosphere symbol. Pressure unit.**/            
    public final static String Atmosphere = "atm";
    /**Technical atmosphere symbol. Pressure unit.**/            
    public final static String TechnicalAtmosphere = "at";
    /**Bar symbol. Pressure unit.**/            
    public final static String Bar = "bar";
    /**Pound-force per square inch abbreviation. Imperial/USCS pressure unit.**/                 
    public final static String PoundforcePerSquareInch = "psi";
    /**Pound-force per square foot abbreviation. Imperial/USCS pressure unit.**/                 
    public final static String PoundforcePerSquareFoot = "psf";
    /**Millimetre of mercury symbol. Pressure unit.**/               
    public final static String MillimetreOfMercury = "mmHg";
    /**Inch of mercury 32 °F abbreviation. Pressure unit.**/               
    public final static String InchOfMercury32F = "inHg32";
    /**Inch of mercury 60 °F abbreviation. Pressure unit.**/               
    public final static String InchOfMercury60F = "inHg60";
    /**Barye symbol. CGS pressure unit.**/               
    public final static String Barye = "Ba";
    /**Torr symbol. Pressure unit.**/               
    public final static String Torr = "Torr";
    /**Kip per square inch abbreviation. Pressure unit.**/               
    public final static String KipPerSquareInch = "ksi";

    //--- Frequency
    /**Hertz symbol. SI frequency unit.**/    
    public final static String Hertz = "Hz";
    /**Cycle per second abbreviation. Frequency unit.**/                
    public final static String CyclePerSecond = "cps";

    //--- Electric Charge
    /**Coulomb symbol. SI electric charge unit.**/
    public final static String Coulomb = "C";
    /**Ampere hour symbol. Electric charge unit.**/
    public final static String AmpereHour = "Ah";
    /**Franklin symbol. CGS-Gaussian/CGS-ESU electric charge unit.**/
    public final static String Franklin = "Fr";
    /**Statcoulomb symbol. CGS-Gaussian/CGS-ESU electric charge unit.**/
    public final static String Statcoulomb = "statC";
    /**Electrostatic unit of charge abbreviation. CGS-Gaussian/CGS-ESU electric charge unit.**/
    public final static String ESUOfCharge = "ESUcha";
    /**
    Abcoulomb symbol. CGS-EMU electric charge unit.
    The "aC" alternative cannot be supported due to its incompatibility with the electric charge unit attocoulomb.           
    **/
    public final static String Abcoulomb = "abC";
    /**Electromagnetic unit of charge abbreviation. CGS-EMU electric charge unit.**/
    public final static String EMUOfCharge = "EMUcha";

    //--- Electric Current
    /**Ampere symbol. SI electric current unit.**/
    public final static String Ampere = "A";
    /**Statampere symbol. CGS-Gaussian/CGS-ESU electric current unit.**/
    public final static String Statampere = "statA";
    /**Electrostatic unit of current abbreviation. CGS-Gaussian/CGS-ESU electric current unit.**/
    public final static String ESUOfCurrent = "ESUcur";
    /**
    Abampere symbol. CGS-EMU electric current unit.
    The "aA" alternative cannot be supported due to its incompatibility with the electric current unit attoampere.           
    **/
    public final static String Abampere = "abA";
    /**Electromagnetic unit of current abbreviation. CGS-EMU electric current unit.**/
    public final static String EMUOfCurrent = "EMUcur";
    /**Biot symbol. CGS-EMU electric current unit.**/
    public final static String Biot = "Bi";

    //--- Electric Voltage
    /**Volt symbol. SI electric voltage unit.**/
    public final static String Volt = "V";
    /**Electrostatic unit of electric potential abbreviation. CGS-Gaussian/CGS-ESU electric voltage unit.**/
    public final static String ESUOfElectricPotential = "ESUpot";
    /**Statvolt symbol. CGS-Gaussian/CGS-ESU electric voltage unit.**/
    public final static String Statvolt = "statV";
    /**Electromagnetic unit of electric potential abbreviation. CGS-EMU electric voltage unit.**/
    public final static String EMUOfElectricPotential = "EMUpot";
    /**
    Abvolt symbol. CGS-EMU electric voltage unit.
    The "aV" alternative cannot be supported due to its incompatibility with the electric voltage unit attovolt.            
    **/
    public final static String Abvolt = "abV";

    //--- Electric Resistance 
    /**Ohm symbol. SI electric resistance unit.**/
    public final static String Ohm = "Ω";
    /**Statohm symbol. CGS-Gaussian/CGS-ESU electric resistance unit.**/
    public final static String Statohm = "statΩ";
    /**Electrostatic unit of resistance abbreviation. CGS-Gaussian/CGS-ESU electric resistance unit.**/
    public final static String ESUOfResistance = "ESUres";
    /**
    Abohm symbol. CGS-EMU electric resistance unit.
    The "aΩ" alternative cannot be supported due to its incompatibility with the electric resistance unit attoohm.           
    **/
    public final static String Abohm = "abΩ";
    /**Electromagnetic unit of resistance abbreviation. CGS-EMU electric resistance unit.**/
    public final static String EMUOfResistance = "EMUres";

    //--- Electric Resistivity 
    /**Ohm metre symbol. SI electric resistivity unit.**/
    public final static String OhmMetre = "Ω*m";

    //--- Electric Conductance
    /**Siemens symbol. SI electric conductance unit.**/
    public final static String Siemens = "S";
    /**Mho symbol. SI electric conductance unit.**/
    public final static String Mho = "℧";
    /**Gemmho String representation. Electric conductance unit.**/
    public final static String Gemmho = "gemmho";
    /**Statsiemens symbol. CGS-Gaussian/CGS-ESU electric resistance unit.**/
    public final static String Statsiemens = "statS";
    /**Statmho symbol. CGS-Gaussian/CGS-ESU electric resistance unit.**/
    public final static String Statmho = "stat℧";
    /**
    Absiemens symbol. CGS-EMU electric resistance unit.
    The "aS" alternative cannot be supported due to its incompatibility with the electric conductance unit attosiemens.      
    **/
    public final static String Absiemens = "abS";
    /**
    Abmho symbol. CGS-EMU electric resistance unit.
    The "a℧" alternative cannot be supported due to its incompatibility with the electric conductance unit attomho.         
    **/
    public final static String Abmho = "ab℧";

    //--- Electric Conductivity
    /**Siemens per metre symbol. SI electric conductivity unit.**/
    public final static String SiemensPerMetre = "S/m";
    
    //--- Electric Capacitance
    /**Farad symbol. SI electric capacitance unit.**/
    public final static String Farad = "F";
    /**Statfarad symbol. CGS-Gaussian/CGS-ESU electric capacitance unit.**/
    public final static String Statfarad = "statF";
    /**Electrostatic unit of capacitance abbreviation. CGS-Gaussian/CGS-ESU electric capacitance unit.**/
    public final static String ESUOfCapacitance = "ESUcap";
    /**
    Abfarad symbol. CGS-EMU electric capacitance unit.
    The "aF" alternative cannot be supported due to its incompatibility with the electric capacitance unit attofarad.        
    **/
    public final static String Abfarad = "abF";
    /**Electromagnetic unit of capacitance abbreviation. CGS-EMU electric capacitance unit.**/
    public final static String EMUOfCapacitance = "EMUcap";

    //--- Electric Inductance
    /**Henry symbol. SI electric inductance unit.**/
    public final static String Henry = "H";
    /**Stathenry symbol. CGS-Gaussian/CGS-ESU electric inductance unit.**/
    public final static String Stathenry = "statH";
    /**Electrostatic unit of inductance abbreviation. CGS-Gaussian/CGS-ESU electric inductance unit.**/
    public final static String ESUOfInductance = "ESUind";
    /**
    Abhenry symbol. CGS-EMU electric inductance unit.
    The "aH" alternative cannot be supported due to its incompatibility with the electric inductance unit attohenry.         
    **/
    public final static String Abhenry = "abH";
    /**Electromagnetic unit of inductance abbreviation. CGS-EMU electric inductance unit.**/
    public final static String EMUOfInductance = "EMUind";

    //--- Electric Dipole Moment
    /**Coulomb metre symbol. SI electric dipole moment unit.**/  
    public final static String CoulombMetre = "C*m";
    /**Debye symbol. CGS-Gaussian electric dipole moment unit.**/
    public final static String Debye = "D";

    //--- Temperature
    /**Kelvin symbol. SI temperature unit.**/
    public final static String Kelvin = "K";
    /**Degree Celsius symbol. SI temperature unit.**/
    public final static String DegreeCelsius = "°C";
    /**Degree Fahrenheit symbol. Imperial/USCS temperature unit.**/            
    public final static String DegreeFahrenheit = "°F";
    /**Degree Rankine symbol. Imperial/USCS temperature unit.**/   
    public final static String DegreeRankine = "°R";

    //--- Wavenumber
    /**Reciprocal metre symbol. SI wavenumber unit.**/  
    public final static String ReciprocalMetre = "1/m";
    /**
    Kayser String representation. CGS wavenumber unit.
    The "K" alternative cannot be supported due to its incompatibility with the temperature unit kelvin.            
    **/
    public final static String Kayser = "kayser";

    //--- Viscosity
    /**Pascal second symbol. SI viscosity unit.**/  
    public final static String PascalSecond = "Pa*s";
    /**Poise symbol. CGS viscosity unit.**/
    public final static String Poise = "P";

    //--- Kinematic Viscosity
    /**Square metre per second symbol. SI kinematic viscosity unit.**/  
    public final static String SquareMetrePerSecond = "m2/s";
    /**Stokes symbol. CGS kinematic viscosity unit.**/
    public final static String Stokes = "St";

    //--- Amount of Substance
    /**Mole symbol. SI amount of substance unit.**/                 
    public final static String Mole = "mol";
    /**Pound-mole abbreviation. Amount of substance unit.**/
    public final static String PoundMole = "lbmol";

    //--- Momentum
    /**Newton second symbol. SI momentum unit.**/  
    public final static String NewtonSecond = "N*s";

    //--- Angular Velocity
    /**Radian per second symbol. SI angular velocity unit.**/  
    public final static String RadianPerSecond = "rad/s";
    /**Revolution per minute abbreviation. Angular velocity unit.**/             
    public final static String RevolutionPerMinute = "rpm";

    //--- Angular Acceleration
    /**Radian per square second symbol. SI angular acceleration unit.**/  
    public final static String RadianPerSquareSecond = "rad/s2";

    //--- Angular Momentum
    /**Joule second symbol. SI angular momentum unit.**/  
    public final static String JouleSecond = "J*s";

    //--- Moment of Inertia
    /**Kilogram square metre symbol. SI moment of inertia unit.**/  
    public final static String KilogramSquareMetre = "kg*m2";

    //--- Solid Angle
    /**Steradian symbol. SI solid angle unit.**/                 
    public final static String Steradian = "sr";
    /**Square degree abbreviation. Solid angle unit.**/                 
    public final static String SquareDegree = "deg2";

    //--- Luminous Intensity
    /**Candela symbol. SI luminous intensity unit.**/                 
    public final static String Candela = "cd";

    //--- Luminous Flux
    /**Lumen symbol. SI luminous flux unit.**/                 
    public final static String Lumen = "lm";

    //--- Luminous Energy
    /**Lumen second symbol. SI luminous energy unit.**/                 
    public final static String LumenSecond = "lm*s";
    /**
    Talbot String representation. Luminous energy unit.
    The "T" alternative cannot be supported due to its incompatibility with the magnetic field B unit tesla.            
    **/                 
    public final static String Talbot = "talbot";

    //--- Luminance
    /**Candela per square metre symbol. SI luminance unit.**/                 
    public final static String CandelaPerSquareMetre = "cd/m2";
    /**Nit abbreviation. Luminance unit.**/                 
    public final static String Nit = "nt";
    /**Stilb symbol. CGS luminance unit.**/                 
    public final static String Stilb = "sb";
    /**
    Lambert String representation. CGS luminance unit.
    The "L" alternative cannot be supported due to its incompatibility with the volume unit litre.                   
    **/                 
    public final static String Lambert = "lambert";
    /**Foot-lambert abbreviation. Imperial/USCS luminance unit.**/                 
    public final static String FootLambert = "ftL";

    //--- Illuminance
    /**Lux symbol. SI illuminance unit.**/                 
    public final static String Lux = "lx";
    /**Phot symbol. CGS illuminance unit.**/                 
    public final static String Phot = "ph";
    /**Foot-candle abbreviation. Imperial/USCS illuminance unit.**/                 
    public final static String FootCandle = "fc";

    //--- Logarithmic
    /**Bel symbol. Logarithmic unit.**/                        
    public final static String Bel = "B";
    /**Neper symbol. Logarithmic unit.**/      
    public final static String Neper = "Np";

    //--- Magnetic Flux
    /**Weber symbol. SI magnetic flux unit.**/                         
    public final static String Weber = "Wb";
    /**
    Maxwell symbol. CGS-Gaussian/CGS-EMU magnetic flux unit.**/                         
    public final static String Maxwell = "Mx";

    //--- Magnetic Field B
    /**Tesla symbol. SI magnetic field B unit.**/ 
    public final static String Tesla = "T";
    /**Gauss symbol. CGS-Gaussian/CGS-EMU magnetic field B unit.**/                         
    public final static String Gauss = "G";

    //--- Magnetic Field H
    /**Ampere per metre symbol. SI magnetic field H unit.**/                 
    public final static String AmperePerMetre = "A/m";
    /**Oersted symbol. CGS-Gaussian/CGS-EMU magnetic field H unit.**/                         
    public final static String Oersted = "Oe";

    //--- Radioactivity
    /**Becquerel symbol. SI radioactivity unit.**/ 
    public final static String Becquerel = "Bq";
    /**Curie symbol. Radioactivity unit.**/ 
    public final static String Curie = "Ci";
    /**Disintegrations per second abbreviation. Radioactivity unit.**/ 
    public final static String DisintegrationsPerSecond = "dps";
    /**Disintegrations per minute abbreviation. Radioactivity unit.**/ 
    public final static String DisintegrationsPerMinute = "dpm";
    /**Rutherford symbol. Radioactivity unit.**/ 
    public final static String Rutherford = "Rd";

    //--- Absorbed Dose
    /**Gray symbol. SI absorbed dose unit.**/ 
    public final static String Gray = "Gy";
    /**
    Rad String representation. CGS absorbed dose unit.
    The "rad" alternative cannot be supported due to its incompatibility with the angle unit radian.            
    **/ 
    public final static String Rad = "Rad";

    //--- Absorbed Dose Rate
    /**Gray per second symbol. SI absorbed dose rate unit.**/ 
    public final static String GrayPerSecond = "Gy/s";

    //--- Equivalent Dose
    /**Sievert symbol. SI equivalent dose unit.**/ 
    public final static String Sievert = "Sv";
    /**Roentgen equivalent in man symbol. CGS equivalent dose unit.**/ 
    public final static String REM = "rem";

    //--- Exposure
    /**Coulomb per kilogram. SI exposure unit.**/ 
    public final static String CoulombPerKilogram = "C/kg";
    /**Roentgen. CGS exposure unit.**/ 
    public final static String Roentgen = "R";

    //--- Catalytic Activity
    /**Katal symbol. SI catalytic activity unit.**/ 
    public final static String Katal = "kat";

    //--- Catalytic Activity Concentration
    /**Katal per cubic metre symbol. SI catalytic activity concentration unit.**/ 
    public final static String KatalPerCubicMetre = "kat/m3";

    //--- Jerk
    /**Metre per cubic second symbol. SI jerk unit.**/ 
    public final static String MetrePerCubicSecond = "m/s3";

    //--- Mass Flow Rate
    /**Kilogram per second symbol. SI mass flow rate unit.**/ 
    public final static String KilogramPerSecond = "kg/s";

    //--- Density
    /**Kilogram per cubic metre symbol. SI density unit.**/ 
    public final static String KilogramPerCubicMetre = "kg/m3";

    //--- Area Density
    /**Kilogram per square metre symbol. SI area density unit.**/ 
    public final static String KilogramPerSquareMetre = "kg/m2";

    //--- Specific Volume
    /**Cubic metre per kilogram symbol. SI specific volume unit.**/ 
    public final static String CubicMetrePerKilogram = "m3/kg";

    //--- Volumetric Flow Rate
    /**Cubic metre per second symbol. SI volumetric flow rate unit.**/ 
    public final static String CubicMetrePerSecond = "m3/s";

    //--- Surface Tension
    /**Joule per square metre symbol. SI surface tension unit.**/ 
    public final static String JoulePerSquareMetre = "J/m2";

    //--- Specific Weight
    /**Newton per cubic metre symbol. SI specific weight unit.**/ 
    public final static String NewtonPerCubicMetre = "N/m3";

    //--- Thermal Conductivity
    /**Watt per metre per kelvin symbol. SI thermal conductivity unit.**/ 
    public final static String WattPerMetrePerKelvin = "W/m*K";

    //--- Thermal Conductance
    /**Watt per kelvin symbol. SI thermal conductance unit.**/ 
    public final static String WattPerKelvin = "W/K";

    //--- Thermal Resistivity
    /**Metre kelvin per watt symbol. SI thermal resistivity unit.**/ 
    public final static String MetreKelvinPerWatt = "m*K/W";

    //--- Thermal Resistance
    /**Kelvin per watt symbol. SI thermal resistance unit.**/ 
    public final static String KelvinPerWatt = "K/W";

    //--- Heat Transfer Coefficient
    /**Watt per square metre per kelvin symbol. SI heat transfer coefficient unit.**/ 
    public final static String WattPerSquareMetrePerKelvin = "W/m2*K";

    //--- Heat Flux Density
    /**Watt per square metre symbol. SI heat flux density unit.**/ 
    public final static String WattPerSquareMetre = "W/m2";

    //--- Entropy
    /**Joule per kelvin symbol. SI entropy unit.**/ 
    public final static String JoulePerKelvin = "J/K";

    //--- Electric Field Strength
    /**Newton per coulomb symbol. SI electric field strength unit.**/ 
    public final static String NewtonPerCoulomb = "N/C";
    /**Volt per metre symbol. SI electric field strength unit.**/ 
    public final static String VoltPerMetre = "V/m";

    //--- Linear Electric Charge Density
    /**Coulomb per metre symbol. SI linear electric charge density unit.**/ 
    public final static String CoulombPerMetre = "C/m";

    //--- Surface Electric Charge Density
    /**Coulomb per square metre symbol. SI surface electric charge density unit.**/ 
    public final static String CoulombPerSquareMetre = "C/m2";

    //--- Volume Electric Charge Density
    /**Coulomb per cubic metre symbol. SI volume electric charge density unit.**/ 
    public final static String CoulombPerCubicMetre = "C/m3";

    //--- Current Density
    /**Ampere per square metre symbol. SI current density unit.**/ 
    public final static String AmperePerSquareMetre = "A/m2";

    //--- Energy Density
    /**Joule per cubic metre symbol. SI energy density unit.**/ 
    public final static String JoulePerCubicMetre = "J/m3";

    //--- Electromagnetic Permittivity
    /**Farad per metre symbol. SI electromagnetic permittivity unit.**/ 
    public final static String FaradPerMetre = "F/m";

    //--- Electromagnetic Permeability
    /**Henry per metre symbol. SI electromagnetic permeability unit.**/ 
    public final static String HenryPerMetre = "H/m";

    //--- Molar Energy
    /**Joule per mole symbol. SI molar energy unit.**/ 
    public final static String JoulePerMole = "J/mol";

    //--- Molar Entropy
    /**Joule per mole per kelvin symbol. SI molar entropy unit.**/ 
    public final static String JoulePerMolePerKelvin = "J/mol*K";

    //--- Molar Volume
    /**Cubic metre per mole symbol. SI molar volume unit.**/ 
    public final static String CubicMetrePerMole = "m3/mol";

    //--- Molar Mass
    /**Kilogram per mole symbol. SI molar mass unit.**/ 
    public final static String KilogramPerMole = "kg/mol";

    //--- Molar Concentration
    /**Mole per cubic metre symbol. SI molar concentration unit.**/ 
    public final static String MolePerCubicMetre = "mol/m3";

    //--- Molal Concentration
    /**Mole per kilogram symbol. SI molal concentration unit.**/ 
    public final static String MolePerKilogram = "mol/kg";

    //--- Radiant Intensity
    /**Watt per steradian symbol. SI radiant intensity unit.**/ 
    public final static String WattPerSteradian = "W/sr";

    //--- Radiance
    /**Watt per steradian per square metre symbol. SI radiance unit.**/ 
    public final static String WattPerSteradianPerSquareMetre = "W/sr*m2";
    
    //--- Fuel Economomy
    /**Inverse square metre symbol. SI fuel economy unit.**/ 
    public final static String InverseSquareMetre = "1/m2";
    /**Mile per gallon abbreviation. Imperial fuel economy unit.**/ 
    public final static String MilePerGallon = "mpg";
    /**Imperial mile per gallon abbreviation. Imperial fuel economy unit.**/ 
    public final static String ImperialMilePerGallon = "impmpg";
    /**USCS mile per gallon abbreviation. USCS fuel economy unit.**/ 
    public final static String USCSMilePerGallon = "uscmpg";
    /**Kilometre per litre symbol. Fuel economy unit.**/ 
    public final static String KilometrePerLitre = "km/L";

    //--- Sound Exposure
    /**Square pascal second symbol. SI sound exposure unit.**/ 
    public final static String SquarePascalSecond = "Pa2*s";

    //--- Sound Impedance
    /**Pascal second per cubic metre symbol. SI sound impedance unit.**/ 
    public final static String PascalSecondPerCubicMetre = "Pa*s/m3";

    //--- Rotational Stiffness
    /**Newton metre per radian symbol. SI rotational stiffness unit.**/ 
    public final static String NewtonMetrePerRadian = "N*m/rad";

    //--- Bit Rate
    /**Bit per second symbol. Bit rate unit.**/ 
    public final static String BitPerSecond = "bit/s";

    //--- Symbol Rate
    /**Baud. Symbol rate unit.**/ 
    public final static String Baud = "Bd";
}

