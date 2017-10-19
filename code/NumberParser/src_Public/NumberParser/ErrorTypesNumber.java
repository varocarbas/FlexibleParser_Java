package NumberParser;

/**Contains all the supported error types.**/
public enum ErrorTypesNumber
{
    /**No error.**/
    None,
    /**Error provoked by not matching the expected input format (e.g., null string).**/
    InvalidInput,
    /**Error provoked by not matching the expected conditions of certain mathematical operation (e.g., division by zero).**/
    InvalidOperation,
    /**Error provoked by not matching the expected conditions of the corresponding native System.Math method.**/
    NativeMethodError,
    /**Error provoked by a calculation outputting a value outside the supported range (i.e., BaseTenExponent outside the int range).**/
    NumericOverflow,
    /**Error provoked by a string not containing valid numeric information under the given conditions.**/
    ParseError
}
