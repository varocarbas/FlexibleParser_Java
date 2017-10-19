package NumberParser;

/**Determines the group of numeric types to be considered at NumberO instantiation.**/
public enum OtherTypes
{
  /**No types.**/
  None,
  /**All the numeric types.**/
  AllTypes,
  /**Only the following types: decimal, double, long and int.**/
  MostCommonTypes,
  /**Only the following types: long, ulong, int, uint, short, ushort, char, sbyte and byte.**/      
  IntegerTypes,
  /**Only the following types: decimal, double and float.**/           
  DecimalTypes,
  /**Only the following types: decimal, double, float, long, ulong, int and uint.**/                   
  BigTypes,
  /**Only the following types: short, ushort, sbyte, byte and char.**/
  SmallTypes
}

