package UnitParser;

/**Contains all the versions of the supported systems of units.**/
public enum UnitSystems
{
    /**Refers to units not belonging to any supported system of units.**/
    None,
    /**Refers to units belonging to the International System of Units.**/
    SI,
    /**Refers to units belonging to the British Imperial System of Units.**/                                
    Imperial,
    /**Refers to units belonging to the U.S. Customary System of Units.**/                        
    USCS,
    /**Refers to units belonging to both Imperial and USC systems.**/                        
    ImperialAndUSCS,
    /**
    Refers to units belonging to the Centimetre-gram-second System of Units.
    Includes all the electric subsystems (e.g., CGS-Gaussian, CGS-ESU or CGS-EMU).
    **/     
    CGS
}

