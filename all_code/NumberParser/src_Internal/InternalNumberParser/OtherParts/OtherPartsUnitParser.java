package InternalNumberParser.OtherParts;

import java.lang.reflect.Method;

/**
Class emulating UnitParser's UnitP in its most basic configuration, by only including the
public properties which are relevant (i.e., used by the methods in OtherPartsUnitParserMethods).
*/
public class OtherPartsUnitParser 
{
	public ErrorClass Error;
	public PrefixClass UnitPrefix;
	public double Value;
	public int BaseTenExponent;
	
	public class ErrorClass
	{
		public String Type = "None";
		
		public ErrorClass(Object unitP) throws Exception
		{
			if (unitP != null) 
			{
				Object tempVar = GetUnitPProperty(unitP, "getError");
				Type = GetUnitPProperty(tempVar, "getType").toString();
			}
		}
	}
	
	public class PrefixClass
	{
		public double Factor = 1.0;
		
		public PrefixClass(Object unitP) throws Exception
		{
			if (unitP != null) 
			{
				Object tempVar = GetUnitPProperty(unitP, "getUnitPrefix");
				if (tempVar != null)
				{
					Factor = (double)GetUnitPProperty(tempVar, "getFactor");					
				}				
			}
		}
	}
	
	//This constructor expects a non-null UnitParser's UnitP instance.
	public OtherPartsUnitParser(Object unitP)
	{
		try 
		{
			Error = new ErrorClass(unitP);
			if (!Error.Type.equals("None")) return;
			UnitPrefix = new PrefixClass(unitP);
			Value = (double)GetUnitPProperty(unitP, "getValue");		
			BaseTenExponent = (int)GetUnitPProperty(unitP, "getBaseTenExponent");				
		} 
		catch (Exception e) 
		{ 
			//This point will never be reached. 
			//This try...catch statement is forced by the Java compiler.
		}
	}
	
    static Object GetUnitPProperty(Object numberX, String methodName)
    {
		try 
		{
			Method method = numberX.getClass().getMethod(methodName);	
			
			return method.invoke(numberX);
		} 
		catch (Exception e) 
		{ 
			//This point will never be reached. 
			//This try...catch statement is forced by the Java compiler.
		}

		return null;
    }
}