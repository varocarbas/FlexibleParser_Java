package InternalNumberParser.CSharpAdaptation;

import NumberParser.*;

//Class including emulations of the .NET in-built System.Math methods used at some point of the code by relying
//on the corresponding System.Math2 versions.
public class SystemMath
{
	public static RoundType GetNewRoundItemFromExisting(MidpointRounding input)
	{
		return
		(
			input.equals(MidpointRounding.ToEven) 
			? RoundType.MidpointToEven 
			: RoundType.MidpointAwayFromZero
		);
	}
}

