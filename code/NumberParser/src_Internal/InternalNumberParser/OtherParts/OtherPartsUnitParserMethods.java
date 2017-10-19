package InternalNumberParser.OtherParts;

import InternalNumberParser.Operations.*;
import NumberParser.*;
import NumberParser.Number;

/**
Class including all the required resources to extract the main information of UnitP 
(i.e., the main UnitParser class) variables without including a proper definition of that class.
**/
public class OtherPartsUnitParserMethods 
{
	public static Number GetNumberFromUnitP(Object unitP)
	{
		if (unitP == null || !unitP.getClass().getName().equals("UnitParser.UnitP"))
		{
			return new Number(ErrorTypesNumber.InvalidInput);
		}
		
		OtherPartsUnitParser tempVar = new OtherPartsUnitParser
		(
			unitP
		);

		return
		(
			!tempVar.Error.Type.toString().equals("None") ? new Number
			(
				ErrorTypesNumber.InvalidInput
			) 
			: UnitPToNumber(tempVar)
		);
	}

	private static Number UnitPToNumber(OtherPartsUnitParser unitP)
	{
		return
		(
			OperationsManaged.MultiplyInternal
			( 
				new Number(unitP.UnitPrefix.Factor), 
				new Number(unitP.Value, unitP.BaseTenExponent)
			)
		);
	}
}