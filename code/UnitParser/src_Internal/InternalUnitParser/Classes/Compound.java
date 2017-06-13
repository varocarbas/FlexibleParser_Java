package InternalUnitParser.Classes;

import java.util.ArrayList;

public class Compound
{
    public ArrayList<CompoundPart> Parts;

    public Compound(ArrayList<CompoundPart> arrayList)
    {
        Parts = new ArrayList<CompoundPart>(arrayList);
    }
}
