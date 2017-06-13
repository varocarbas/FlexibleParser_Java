package InternalUnitParser.Classes;

//Accessory class used by Linq.GroupByJustCount.
public class XAndIntegerClass<X>
{
	public X Input;
	public Integer Count;
	
	public XAndIntegerClass(X input, Integer count)
	{
		Input = input;
		Count = count;
	}
}
