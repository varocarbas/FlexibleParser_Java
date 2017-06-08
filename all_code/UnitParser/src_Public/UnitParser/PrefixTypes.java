package UnitParser;

/**Types of unit prefixes.**/
public enum PrefixTypes
{
    /**No unit prefix.**/
    None,

    /**
    Refers to all the International System of Units prefixes.
    By default, these prefixes may only be used with SI, CGS or related units.
    **/
    SI,
    /**
    Refers to all the binary prefixes.
    By default, these prefixes may only be used with information or related units.
    **/
    Binary
}