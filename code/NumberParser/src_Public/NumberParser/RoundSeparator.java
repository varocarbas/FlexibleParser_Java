package NumberParser;

/**
Indicates the location of the digits being rounded (i.e., before or after the decimal separator).
**/
public enum RoundSeparator
{
    /**Only the digits after the decimal separator are rounded.**/
    AfterDecimalSeparator,
    /**Only the digits before the decimal separator are rounded. The digits after the decimal separator might also be analysed (e.g., midpoint determination).**/
    BeforeDecimalSeparator
}
