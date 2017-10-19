package NumberParser;

/**
Indicates the type of rounding, as defined by the way in which the last digit is being rounded.
**/
public enum RoundType
{
    /**When a number is halfway between two others, it is rounded to the number which is further from zero.**/
    MidpointAwayFromZero,
    /**When a number is halfway between two others, it is rounded to the number which is even.**/
    MidpointToEven,
    /**When a number is halfway between two others, it is rounded to the number which is closer to zero.**/
    MidpointToZero,
    /**A number is always rounded to the number which is further from zero.**/           
    AlwaysToEven,
    /**A number is always rounded to the number which is closer to zero.**/     
    AlwaysAwayFromZero,
    /**A number is always rounded to the even number.**/ 
    AlwaysToZero
}
