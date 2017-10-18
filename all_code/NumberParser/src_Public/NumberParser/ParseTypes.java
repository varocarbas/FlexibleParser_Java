package NumberParser;

/**Determines the main rules to be applied when parsing the string input at NumberP instantiation.**/
public enum ParseTypes
{
  /**All the strings are parsed without any restriction.**/
  ParseAll,
  /**Only strings which fit within the range of the target type are acceptable.**/
  ParseOnlyTarget,
  /**
  Invalid thousands separators trigger an error. Any valid configuration, supported by the given culture or not, is acceptable.
  Supported configurations for thousands separators: standard (groups of 3), Indian (first group of 3 and then groups of 2) and Chinese (groups of 4). Additionally to the group separators for the given culture, blank spaces are also supported.        
  **/
  ParseThousandsStrict,
  /**
  Only strings which fit within the range of the target type and include valid thousands separators (any configuration) are acceptable.
  Supported configurations for thousands separators: standard (groups of 3), Indian (first group of 3 and then groups of 2) and Chinese (groups of 4). Additionally to the group separators for the given culture, blank spaces are also supported.        
  **/
  ParseOnlyTargetAndThousandsStrict
}
