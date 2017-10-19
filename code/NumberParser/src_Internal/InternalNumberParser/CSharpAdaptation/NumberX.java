package InternalNumberParser.CSharpAdaptation;

import NumberParser.*;
import NumberParser.Number;

//This class is meant to somehow account for the implicit conversions among all the NumberX classes
//in the original C# code.
@SuppressWarnings("rawtypes")
public class NumberX
{
	public enum NumberXTypes { None, Number, NumberD, NumberO, NumberP }
	
	public static class MainVars
	{
		public int BaseTenExponent;
		public Object Value;
		public ErrorTypesNumber Error;
		public NumberXTypes Type = NumberXTypes.None;
	}

	public static MainVars GetMainVars(Object numberX)
	{
		return 
		(
			numberX == null ? new MainVars() : GetMainVars
			(
				numberX, numberX.getClass()
			)	
		);
	}
	
	public static MainVars GetMainVars(Object numberX, Class type)
	{
		MainVars output = new MainVars();

		if (type.equals(Number.class))
		{		
			Number numberX2 = (Number)numberX;
			
			output.Type = NumberXTypes.Number;
			output.Value = numberX2.getValue();	
			output.BaseTenExponent = numberX2.getBaseTenExponent();	
			output.Error = numberX2.getError();
		}
		else if (type.equals(NumberD.class))
		{
			NumberD numberX2 = (NumberD)numberX;
			
			output.Type = NumberXTypes.NumberD;
			output.Value = numberX2.getValue();	
			output.BaseTenExponent = numberX2.getBaseTenExponent();	
			output.Error = numberX2.getError();
		}
		else if (type.equals(NumberO.class))
		{
			NumberO numberX2 = (NumberO)numberX;
			
			output.Type = NumberXTypes.NumberO;
			output.Value = numberX2.getValue();	
			output.BaseTenExponent = numberX2.getBaseTenExponent();	
			output.Error = numberX2.getError();
		}
		else if (type.equals(NumberP.class))
		{
			NumberP numberX2 = (NumberP)numberX;
			
			output.Type = NumberXTypes.NumberP;
			output.Value = numberX2.getValue();	
			output.BaseTenExponent = numberX2.getBaseTenExponent();	
			output.Error = numberX2.getError();
		}
		
		return output;		
	}
}
