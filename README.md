# FlexibleParser (Java)

**NOTE:** this is the conversion to Java of the original C# code stored in the [FlexibleParser repository](https://github.com/varocarbas/FlexibleParser). 

FlexibleParser is a group of multi-purpose parsing libraries based upon the following ideas:

- Intuitive, adaptable and easy to use.
- Pragmatic, but aiming for the maximum accuracy and correctness.
- Overall compatible and easily automatable. 
- Formed by independent JARs managing specific situations.

## Parts

At the moment, FlexibleParser is formed by the following independent parts:

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1025468.svg)](https://zenodo.org/record/1025468) [UnitParser](https://customsolvers.com/unit_parser_java/) ([last release](https://customsolvers.com/downloads/flexible_parser_java/unit_parser/), [readme file](https://customsolvers.com/downloads/flexible_parser_java/unit_parser/UnitParser_Java.pdf), [test program](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/Test/src/Parts/UnitParser.java))<br/>
It allows to easily deal with a wide variety of situations involving units of measurement.
Among its most salient features are: user-defined exception triggering and gracefully managing numeric values of any size.


[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1028916.svg)](https://zenodo.org/record/1028916) [NumberParser](https://customsolvers.com/number_parser_java/) ([last release](https://customsolvers.com/downloads/flexible_parser_java/number_parser/), [readme file](https://customsolvers.com/downloads/flexible_parser_java/number_parser/NumberParser_Java.pdf), [test program](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/Test/src/Parts/NumberParser.java))<br/>
It provides a common framework for all the Java numeric types. Main features: exceptions managed internally; beyond-double-range support; custom mathematical functionalities.


## Authorship & Copyright

I, Alvaro Carballo Garcia (varocarbas), am the sole author of each single bit of this code.

Equivalently to what happens with all my other online contributions, this code can be considered public domain. For more information about my copyright/authorship attribution ideas, visit [https://customsolvers.com/copyright/](https://customsolvers.com/copyright/).