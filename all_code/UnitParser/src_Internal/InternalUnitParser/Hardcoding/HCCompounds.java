package InternalUnitParser.Hardcoding;

import InternalUnitParser.Classes.*;
import UnitParser.*;

import java.util.HashMap;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class HCCompounds
{
    //Contains the definitions of all the supported compounds, understood as units formed by other units
	//and/or variations (e.g., exponents different than 1) of them.
	//In order to be as efficient as possible, AllCompounds ignores the difference between dividable and 
	//non-dividable units. For example: N is formed by kg*m/s2, exactly what this collection expects; on the
	//other hand, lbf isn't formed by the expected lb*ft/s2. In any case, note that this "faulty" format is
	//only used internally, never shown to the user.
	//NOTE: the order of the compounds within each type does matter. The first position is reserved for the main
	//fully-expanded version (e.g., mass*length/time2 for force). In the second position, the compound basic 
	//units (e.g., force) are expected to have their 1-part version (e.g., 1 force part for force).
	public static HashMap<UnitTypes, ArrayList<Compound>> AllCompounds;
    
	//Contains all the named compounds defined by the basic units for the given type/system.
	//For example, Newton is formed by kg*m/s^2, the basic mass*length/time units in SI, and that's why it belongs here.
	public static HashMap<UnitTypes, HashMap<UnitSystems, Units>> AllBasicCompounds;

    //Roughly speaking, AllNonBasicCompounds is a container of somehow exceptional situations.
    //Some of these units (e.g., centimetre) shouldn't be matched when looking for valid compounds. 
	public static ArrayList<Units> NonBasicCompoundsToSkip;

    //Contains the definition (i.e., UnitPart[] containing their defining units) of all the supported named 
    //compounds except the ones defined by the corresponding basic units (included in AllBasicCompounds).
	public static HashMap<Units, ArrayList<UnitPart>> AllNonBasicCompounds;

    //Classifies all the basic units on account of their types and systems.
    //Note that these units don't meet the most intuitive interpretation of the basic unit idea.
    //That is, they aren't just the most basic constituent of compounds (i.e., impossible to be 
    //further divided), but also compounds. The reason for this a-priori abnormal configuration
    //is that they aren't just used to model SI/CGS compounds, but also Imperial/USCS ones. 
    //For example: the SI force unit (N) can be defined on account of properly-speaking basic units,
    //but the Imperial/USCS version (lbf) cannot.
	public static HashMap<UnitTypes, HashMap<UnitSystems, BasicUnit>> AllBasicUnits;
	
	public static void Start()
	{
		AllCompounds = new HashMap<UnitTypes, ArrayList<Compound>>()
	    {
	        {
	        	{ 
	        		put
	        		(
	        			UnitTypes.Area, new ArrayList<Compound>()
	        			{{
	    					add
	    					(
	    					new Compound
	    					(
	    						new ArrayList<CompoundPart>() {{ add(new CompoundPart(UnitTypes.Length, 2)); }}
	    					)
	    					);
	    					add
	    					(							
	        				new Compound
	        				(
	        					new ArrayList<CompoundPart>() {{ add(new CompoundPart(UnitTypes.Area)); }}
	    					)
	    					);
	        			}}
	        		);
	        		put
	        		(
	                    UnitTypes.Volume, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>() {{ add(new CompoundPart(UnitTypes.Length, 3)); }}
	    					)
	    					);
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>() {{ add(new CompoundPart(UnitTypes.Volume)); }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Velocity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	    					)
	    					);
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Acceleration, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                            }}
	    					)
	    					);
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Force, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                            }}
	    					)
	    					);
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>() {{ add(new CompoundPart(UnitTypes.Force)); }}
	    					)
	    					);
	                    }}
					); 
					put
	        		(
	                    UnitTypes.Energy, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                            }}
	    					)
	    					);
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>() {{ add(new CompoundPart(UnitTypes.Energy)); }}
	    					)
	    					);
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Power, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                            }}
	    					)
	    					);
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>() {{ add(new CompoundPart(UnitTypes.Power)); }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Pressure, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, -1));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Frequency, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>() {{ add(new CompoundPart(UnitTypes.Time, -1)); }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricCharge, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent));
	                                add(new CompoundPart(UnitTypes.Time));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricVoltage, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricResistance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricResistivity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 3));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricConductance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound 
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, 2));
	                                add(new CompoundPart(UnitTypes.Time, 3));
	                                add(new CompoundPart(UnitTypes.Mass, -1));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricConductivity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound 
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, 2));
	                                add(new CompoundPart(UnitTypes.Time, 3));
	                                add(new CompoundPart(UnitTypes.Mass, -1));
	                                add(new CompoundPart(UnitTypes.Length, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricCapacitance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound 
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, 2));
	                                add(new CompoundPart(UnitTypes.Time, 4));
	                                add(new CompoundPart(UnitTypes.Mass, -1));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricInductance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricDipoleMoment, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent));
	                                add(new CompoundPart(UnitTypes.Time, 1));
	                                add(new CompoundPart(UnitTypes.Length, 1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Wavenumber, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>() {{ add(new CompoundPart(UnitTypes.Length, -1)); }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Viscosity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, -1));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.KinematicViscosity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Momentum, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.AngularVelocity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Angle));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.AngularAcceleration, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Angle));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.AngularMomentum, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MomentOfInertia, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.SolidAngle, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Angle, 2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.LuminousFlux, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.LuminousIntensity));
	                                add(new CompoundPart(UnitTypes.SolidAngle));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.LuminousEnergy, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.LuminousIntensity));
	                                add(new CompoundPart(UnitTypes.SolidAngle));
	                                add(new CompoundPart(UnitTypes.Time));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Luminance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.LuminousIntensity));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Illuminance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.LuminousIntensity));
	                                add(new CompoundPart(UnitTypes.SolidAngle));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MagneticFlux, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MagneticFieldB, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MagneticFieldH, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent));
	                                add(new CompoundPart(UnitTypes.Length, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.AbsorbedDose, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.AbsorbedDoseRate, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.EquivalentDose, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Exposure, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent));
	                                add(new CompoundPart(UnitTypes.Time));
	                                add(new CompoundPart(UnitTypes.Mass, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.CatalyticActivity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.AmountOfSubstance));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.CatalyticActivityConcentration, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.AmountOfSubstance));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                                add(new CompoundPart(UnitTypes.Length, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Jerk, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MassFlowRate, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Density, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.AreaDensity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.EnergyDensity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, -1));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.SpecificVolume, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length, 3));
	                                add(new CompoundPart(UnitTypes.Mass, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.VolumetricFlowRate, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length, 3));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.SurfaceTension, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.SpecificWeight, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ThermalConductivity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                                add(new CompoundPart(UnitTypes.Temperature, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ThermalConductance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                                add(new CompoundPart(UnitTypes.Temperature, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ThermalResistivity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Time, 3));
	                                add(new CompoundPart(UnitTypes.Temperature));
	                                add(new CompoundPart(UnitTypes.Mass, -1));
	                                add(new CompoundPart(UnitTypes.Length, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ThermalResistance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Time, 3));
	                                add(new CompoundPart(UnitTypes.Temperature));
	                                add(new CompoundPart(UnitTypes.Mass, -1));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.HeatTransferCoefficient, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                                add(new CompoundPart(UnitTypes.Temperature, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.HeatFluxDensity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Entropy, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                                add(new CompoundPart(UnitTypes.Temperature, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectricFieldStrength, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.LinearElectricChargeDensity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent));
	                                add(new CompoundPart(UnitTypes.Time));
	                                add(new CompoundPart(UnitTypes.Length, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.SurfaceElectricChargeDensity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent));
	                                add(new CompoundPart(UnitTypes.Time));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.VolumeElectricChargeDensity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent));
	                                add(new CompoundPart(UnitTypes.Time));
	                                add(new CompoundPart(UnitTypes.Length, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.CurrentDensity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectromagneticPermittivity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, 2));
	                                add(new CompoundPart(UnitTypes.Time, 4));
	                                add(new CompoundPart(UnitTypes.Mass, -1));
	                                add(new CompoundPart(UnitTypes.Length, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.ElectromagneticPermeability, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 1));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                                add(new CompoundPart(UnitTypes.ElectricCurrent, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MolarEnergy, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                                add(new CompoundPart(UnitTypes.AmountOfSubstance, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MolarEntropy, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                                add(new CompoundPart(UnitTypes.AmountOfSubstance, -1));
	                                add(new CompoundPart(UnitTypes.Temperature, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MolarVolume, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length, 3));
	                                add(new CompoundPart(UnitTypes.AmountOfSubstance, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MolarMass, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.AmountOfSubstance, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MolarConcentration, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.AmountOfSubstance));
	                                add(new CompoundPart(UnitTypes.Length, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.MolalConcentration, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.AmountOfSubstance));
	                                add(new CompoundPart(UnitTypes.Mass, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.RadiantIntensity, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                                add(new CompoundPart(UnitTypes.SolidAngle, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.Radiance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                                add(new CompoundPart(UnitTypes.SolidAngle, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.FuelEconomy, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.SoundExposure, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass, 2));
	                                add(new CompoundPart(UnitTypes.Length, -2));
	                                add(new CompoundPart(UnitTypes.Time, -3));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.SoundImpedance, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, -4));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.RotationalStiffness, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Mass));
	                                add(new CompoundPart(UnitTypes.Length, 2));
	                                add(new CompoundPart(UnitTypes.Time, -2));
	                                add(new CompoundPart(UnitTypes.Angle, -1));
	                            }}
	                        )
	                        );
	                    }}
	        		);
	        		put
	        		(
	                    UnitTypes.BitRate, new ArrayList<Compound>()
	                    {{
	    					add
	    					(
	    					new Compound
	                        (
	                            new ArrayList<CompoundPart>()
	                            {{
	                                add(new CompoundPart(UnitTypes.Information));
	                                add(new CompoundPart(UnitTypes.Time, -1));
	                            }}
	                        )
	                        );
	                    }}
					);        		
	        	}
	        }
	    };	    
	    
	    AllBasicCompounds = new HashMap<UnitTypes, HashMap<UnitSystems, Units>>()
	    {
	    	{
	    		{ 
	    			put
	    			(
	    				UnitTypes.Area, new HashMap<UnitSystems, Units>()
	    				{
	    					{ 
	    						put(UnitSystems.SI, Units.SquareMetre);
	    						put(UnitSystems.CGS, Units.SquareCentimetre);
	    						put(UnitSystems.Imperial, Units.SquareFoot);
	    					}
	    				}
	    			);
					put
					(
						UnitTypes.Volume, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CubicMetre);
	    						put(UnitSystems.CGS, Units.CubicCentimetre);
	    						put(UnitSystems.Imperial, Units.CubicFoot);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Velocity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.MetrePerSecond);
	    						put(UnitSystems.CGS, Units.CentimetrePerSecond);
	    						put(UnitSystems.Imperial, Units.FootPerSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Acceleration, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.MetrePerSquareSecond);
	    						put(UnitSystems.CGS, Units.Gal);
	    						put(UnitSystems.Imperial, Units.FootPerSquareSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Force, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Newton);
	    						put(UnitSystems.CGS, Units.Dyne);
	    						put(UnitSystems.Imperial, Units.Poundal);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Energy, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Joule);
	    						put(UnitSystems.CGS, Units.Erg);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Power, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Watt);
	    						put(UnitSystems.CGS, Units.ErgPerSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Pressure, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Pascal);
	    						put(UnitSystems.CGS, Units.Barye);
	    						put(UnitSystems.Imperial, Units.PoundforcePerSquareFoot);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Frequency, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Hertz);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricCharge, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Coulomb);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricCurrent, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Ampere);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricVoltage, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Volt);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricResistance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Ohm);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricResistivity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.OhmMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricConductance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Siemens);
	    					}
						}
					);
					put
	    			(
						UnitTypes.ElectricConductivity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.SiemensPerMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricCapacitance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Farad);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricInductance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Henry);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricDipoleMoment, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CoulombMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Wavenumber, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.ReciprocalMetre);
	    						put(UnitSystems.CGS, Units.Kayser);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Viscosity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.PascalSecond);
	    						put(UnitSystems.CGS, Units.Poise);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.KinematicViscosity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.SquareMetrePerSecond);
	    						put(UnitSystems.CGS, Units.Stokes);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Momentum, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.NewtonSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.AngularVelocity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.RadianPerSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.AngularAcceleration, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.RadianPerSquareSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.AngularMomentum, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.JouleSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.MomentOfInertia, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.KilogramSquareMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.SolidAngle, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Steradian);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.LuminousFlux, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Lumen);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.LuminousEnergy, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.LumenSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Luminance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CandelaPerSquareMetre);
	    						put(UnitSystems.CGS, Units.Stilb);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Illuminance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Lux);
	    						put(UnitSystems.CGS, Units.Phot);
	    						put(UnitSystems.Imperial, Units.FootCandle);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.MagneticFlux, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Weber);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.MagneticFieldB, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Tesla);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.AbsorbedDose, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Gray);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.AbsorbedDoseRate, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.GrayPerSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.EquivalentDose, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Sievert);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Exposure, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CoulombPerKilogram);
	    						put(UnitSystems.CGS, Units.Roentgen);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.CatalyticActivity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.Katal);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.CatalyticActivityConcentration, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.KatalPerCubicMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Jerk, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.MetrePerCubicSecond);
							}							
						}
					);
					put
	    			(
						UnitTypes.MassFlowRate, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.KilogramPerSecond);
							}                  
						}
					);
					put
	    			(
						UnitTypes.Density, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.KilogramPerCubicMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.AreaDensity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.KilogramPerSquareMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.EnergyDensity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.JoulePerCubicMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.SpecificVolume, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CubicMetrePerKilogram);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.VolumetricFlowRate, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CubicMetrePerSecond);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.SurfaceTension, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.JoulePerSquareMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.SpecificWeight, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.NewtonPerCubicMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ThermalConductivity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.WattPerMetrePerKelvin);
							}							
						}
					);
					put
	    			(
						UnitTypes.ThermalConductance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.WattPerKelvin);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ThermalResistivity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.MetreKelvinPerWatt);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ThermalResistance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.KelvinPerWatt);  
							}							
						}
					);
					put
	    			(
						UnitTypes.HeatTransferCoefficient, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.WattPerSquareMetrePerKelvin);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.HeatFluxDensity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.WattPerSquareMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Entropy, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.JoulePerKelvin);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectricFieldStrength, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.NewtonPerCoulomb);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.LinearElectricChargeDensity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CoulombPerMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.SurfaceElectricChargeDensity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CoulombPerSquareMetre);  
							}							
						}
					);
					put
	    			(
						UnitTypes.VolumeElectricChargeDensity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CoulombPerCubicMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.CurrentDensity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.AmperePerSquareMetre);  
							}							
						}
					);
					put
	    			(
						UnitTypes.ElectromagneticPermittivity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.FaradPerMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.ElectromagneticPermeability, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.HenryPerMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.MolarEnergy, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.JoulePerMole);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.MolarEntropy, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.JoulePerMolePerKelvin);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.MolarVolume, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.CubicMetrePerMole); 
							}							
						}
					);
					put
	    			(
						UnitTypes.MolarMass, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.KilogramPerMole);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.MolarConcentration, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.MolePerCubicMetre); 
							}							
						}
					);
					put
	    			(
						UnitTypes.MolalConcentration, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.MolePerKilogram);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.RadiantIntensity, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.WattPerSteradian);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.Radiance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.WattPerSteradianPerSquareMetre);
	    					}
	    				}
					);
					put
	    			(
						UnitTypes.FuelEconomy, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.InverseSquareMetre);    
	    						put(UnitSystems.Imperial, Units.MilePerGallon); 
							}
						}
					);
					put
	    			(
						UnitTypes.SoundExposure, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.SquarePascalSecond);   
							}							
						}
					);
					put
	    			(
						UnitTypes.SoundImpedance, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.PascalSecondPerCubicMetre);  
							}							
						}
					);
					put
	    			(
						UnitTypes.RotationalStiffness, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.NewtonMetrePerRadian);
							}							
						}
					);
					put
	    			(
						UnitTypes.BitRate, new HashMap<UnitSystems, Units>()
						{
	    					{
	    						put(UnitSystems.SI, Units.BitPerSecond);
	    						put(UnitSystems.Imperial, Units.BitPerSecond);
	    						put(UnitSystems.CGS, Units.BitPerSecond);
							}							
						}
					);				
	    		}
	        }
	    };
	    
	    NonBasicCompoundsToSkip = new ArrayList<Units>()
	    {{
	        add(Units.Centimetre);
	    }};
	    
	    AllNonBasicCompounds = new HashMap<Units, ArrayList<UnitPart>>()
	    {
	    	{
	            {
	                //--- Length
	            	put
	            	(
	            		Units.Centimetre, //Not exactly a compound, but included here for consistency reasons.
	                    new ArrayList<UnitPart>() {{ add(UnitPartInternal.NewUnitPart(Units.Metre, SIPrefixValues.Centi)); }}              			
	            	);
	            	
	                //--- Area
	            	put
	            	(
	                    Units.SquareInch, 
	                    new ArrayList<UnitPart>() {{ add(new UnitPart(Units.Inch, 2)); }} 
	            	);
	            	put
	            	(  
	            	    Units.SquareRod, 
	            	    new ArrayList<UnitPart>() {{ add(new UnitPart(Units.Rod, 2)); }} 
	            	);
	            	put
	            	( 
	            	    Units.SquarePerch, 
	            	    new ArrayList<UnitPart>() {{ add(new UnitPart(Units.Perch, 2)); }} 
	            	);
	            	put
	            	( 
	            	    Units.SquarePole, 
	            	    new ArrayList<UnitPart>() {{ add(new UnitPart(Units.Pole, 2)); }} 
	            	);
	            	
	            	//--- Volume
	            	put
	            	(  
	            	    Units.CubicInch, 
	            	    new ArrayList<UnitPart>() {{ add(new UnitPart(Units.Inch, 3)); }} 
	            	);
	            	
	            	//--- Velocity
	            	put
	            	( 
	            	    Units.InchPerSecond, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Inch));
	            	        add(new UnitPart(Units.Second, -1));
	            	    }} 
	            	);
	            	put
	            	( 
	            	    Units.Knot, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.NauticalMile));
	            	        add(new UnitPart(Units.Hour, -1));
	            	    }} 
	            	);
	            	put
	            	( 
	            	    Units.KilometrePerHour, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	    	add(UnitPartInternal.NewUnitPart(Units.Metre, SIPrefixValues.Kilo));
	            	        add(new UnitPart(Units.Hour, -1));
	            	    }} 
	            	);
	            	put
	            	( 
	            	    Units.MilePerHour, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Mile));
	            	        add(new UnitPart(Units.Hour, -1));
	            	    }} 
	            	);
	            	
	            	//--- Acceleration
	            	put
	            	( 
	            	    Units.InchPerSquareSecond, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Inch));
	            	        add(new UnitPart(Units.Second, -2));
	            	    }} 
	            	);
	            	
	            	//--- Energy
	            	put
	            	( 
	            	    Units.WattHour, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Metre, 2));
	            	        add(UnitPartInternal.NewUnitPart(Units.Gram, SIPrefixValues.Kilo));
	            	        add(new UnitPart(Units.Hour));
	            	        add(new UnitPart(Units.Second, -3));
	            	    }} 
	            	);
	            	
	            	//--- Pressure
	            	put
	            	( 
	            	    Units.TechnicalAtmosphere, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Kilopond));
	            	        add(UnitPartInternal.NewUnitPart(Units.Metre, SIPrefixValues.Centi, -2));
	            	    }} 
	            	);
	            	put
	            	( 
	            	    Units.PoundforcePerSquareInch, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.PoundForce));
	            	        add(new UnitPart(Units.Inch, -2));
	            	    }} 
	            	);
	            	put
	            	( 
	            	    Units.PoundforcePerSquareFoot, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.PoundForce));
	            	        add(new UnitPart(Units.Foot, -2));
	            	    }} 
	            	);
	            	put
	            	( 
	            	    Units.KipPerSquareInch, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Kip));
	            	        add(new UnitPart(Units.Inch, -2));
	            	    }} 
	            	);
	            	put
	            	( 
	            	    Units.Barye, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Dyne));
	            	        add(UnitPartInternal.NewUnitPart(Units.Metre, SIPrefixValues.Centi, -2));
	            	    }} 
	            	);
	            	
	            	//--- Angular velocity
	            	put
	            	( 
	            	    Units.RevolutionPerMinute, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Revolution));
	            	        add(new UnitPart(Units.Minute, -1));
	            	    }} 
	            	);
	            	
	            	//--- Solid Angle
	            	put
	            	( 
	            	    Units.SquareDegree, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Degree, 2));
	            	    }} 
	            	);
	            	
	            	//--- Electric Charge
	            	put
	            	( 
	            	    Units.AmpereHour, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Ampere));
	            	        add(new UnitPart(Units.Hour));
	            	    }} 
	            	);
	            	
	            	//--- Magnetic Field B
	            	put
	            	( 
	            	    Units.Gauss, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Maxwell));
	            	        add(UnitPartInternal.NewUnitPart(Units.Metre, SIPrefixValues.Centi, -2));
	            	    }} 
	            	);
	            	
	            	//--- Luminous Energy
	            	put
	            	( 
	            	    Units.Talbot, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Lumen));
	            	        add(new UnitPart(Units.Second));
	            	    }} 
	            	);
	            	
	            	//--- Luminance
	            	put
	            	( 
	            	    Units.Nit, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Candela));
	            	        add(new UnitPart(Units.Metre, -2));
	            	    }} 
	            	);
	            	
	            	//--- Fuel Economy
	            	put
	            	( 
	            	    Units.USCSMilePerGallon, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	        add(new UnitPart(Units.Mile));
	            	        add(new UnitPart(Units.LiquidGallon, -1));
	            	    }}
	            	);
	            	put
	            	( 
	            	    Units.KilometrePerLitre, 
	            	    new ArrayList<UnitPart>() 
	            	    {{ 
	            	    	add(UnitPartInternal.NewUnitPart(Units.Metre, SIPrefixValues.Kilo));
	            	        add(new UnitPart(Units.Litre, -1));
	            	    }}
	            	);           
	            }  		
	    	}
	    };
	    
	    AllBasicUnits = new HashMap<UnitTypes, HashMap<UnitSystems, BasicUnit>>()
	    {
	        {
	        	{
	        		put
	        		(
	        			UnitTypes.Length, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Metre));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Metre, SIPrefixValues.Centi));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Foot));
	        				}
	        			}
	        		);
	         		put
	        		(
	        			UnitTypes.Area, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.SquareMetre));
	        					put(UnitSystems.CGS, new BasicUnit(Units.SquareCentimetre));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.SquareFoot));
	        				}
	        			}
	        		);       		
	          		put
	        		(
	        			UnitTypes.Volume, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.CubicMetre));
	        					put(UnitSystems.CGS, new BasicUnit(Units.CubicCentimetre));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.CubicFoot));
	        				}
	        			}
	        		);
	          		put
	        		(
	        			UnitTypes.Mass, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Gram, SIPrefixValues.Kilo));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Gram));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Pound));
	        				}
	        			}
	        		);  
	          		put
	        		(
	        			UnitTypes.Time, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Second));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Second));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Second));
	        				}
	        			}
	        		); 
	          		put
	        		(
	        			UnitTypes.Force, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Newton));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Dyne));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.PoundForce));
	        				}
	        			}
	        		);
	          		put
	        		(
	        			UnitTypes.Energy, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Joule));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Erg));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.BritishThermalUnit));
	        				}
	        			}
	        		);
	          		put
	        		(
	        			UnitTypes.Power, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Watt));
	        					put(UnitSystems.CGS, new BasicUnit(Units.ErgPerSecond));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Horsepower));
	        				}
	        			}
	        		);	
	          		put
	        		(
	        			UnitTypes.Temperature, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Kelvin));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.DegreeFahrenheit));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Kelvin));
	        				}
	        			}
	        		);
	          		put
	        		(
	        			UnitTypes.Angle, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Radian));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Radian));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Radian));
	        				}
	        			}
	        		);	
	          		put
	        		(
	        			UnitTypes.SolidAngle, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Steradian));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Steradian));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Steradian));
	        				}
	        			}
	        		);	
	          		put
	        		(
	        			UnitTypes.ElectricCurrent, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Ampere));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Ampere));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Ampere));
	        				}
	        			}
	        		);
	          		put
	        		(
	        			UnitTypes.LuminousIntensity, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Candela));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Candela));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Candela));
	        				}
	        			}
	        		);
	          		put
	        		(
	        			UnitTypes.AmountOfSubstance, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Mole));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Mole));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Mole));
	        				}
	        			}
	        		);	
	          		put
	        		(
	        			UnitTypes.Information, new HashMap<UnitSystems, BasicUnit>()
	        			{
	        				{
	        					put(UnitSystems.SI, new BasicUnit(Units.Bit));
	        					put(UnitSystems.CGS, new BasicUnit(Units.Bit));
	        					put(UnitSystems.Imperial, new BasicUnit(Units.Bit));
	        				}
	        			}
	        		);				
	        	}
	        }
	    };
	}
}
