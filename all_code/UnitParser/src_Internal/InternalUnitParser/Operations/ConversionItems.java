package InternalUnitParser.Operations;

import InternalUnitParser.Classes.*;
import UnitParser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConversionItems
{
    public ArrayList<UnitPart> Originals, Targets, NonDividables, Others;
    public HashMap<UnitPart, UnitPart> OutDict;
    public Map.Entry<UnitPart, UnitPart> TempPair;
    public UnitInfo ConvertInfo;

    public ConversionItems(ConversionItems conversionItems)
    {
        Originals = new ArrayList<UnitPart>(conversionItems.Originals);
        Targets = new ArrayList<UnitPart>(conversionItems.Targets);
        OutDict = new HashMap<UnitPart, UnitPart>(conversionItems.OutDict);
        NonDividables = new ArrayList<UnitPart>(conversionItems.NonDividables);
        Others = new ArrayList<UnitPart>(conversionItems.Others);
    }

    public ConversionItems()
    {
    	this(new ArrayList<UnitPart>(), new ArrayList<UnitPart>());
    }
    public ConversionItems(ArrayList<UnitPart> originals, ArrayList<UnitPart> targets)
    {
        Originals = new ArrayList<UnitPart>(originals);
        Targets = new ArrayList<UnitPart>(targets);
        OutDict = new HashMap<UnitPart, UnitPart>();
        NonDividables = new ArrayList<UnitPart>();
        Others = new ArrayList<UnitPart>();
    }
}