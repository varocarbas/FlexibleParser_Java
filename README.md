# FlexibleParser (Java)

[![Build Status](https://travis-ci.org/varocarbas/FlexibleParser_Java.svg?branch=master)](https://travis-ci.org/varocarbas/FlexibleParser_Java)

**NOTE:** this is the conversion to Java of the original C# code stored in the [FlexibleParser repository](https://github.com/varocarbas/FlexibleParser). 

FlexibleParser is a multi-purpose parsing library based upon the following ideas:

- Intuitive, adaptable and easy to use.
- Pragmatic, but aiming for the maximum accuracy and correctness.
- Overall compatible and easily automatable. 
- Formed by independent JARs managing specific situations.

## Parts

At the moment, FlexibleParser is formed by the following independent parts:

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1025468.svg)](https://zenodo.org/record/1025468) [UnitParser](https://customsolvers.com/unit_parser_java/) ([last release](https://github.com/varocarbas/FlexibleParser_Java/releases/tag/UnitParser_1.0.9.1), [readme file](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_readme/UnitParser_Java.md), [test program](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/Test/src/Parts/UnitParser.java))<br/>
It allows to easily deal with a wide variety of situations involving units of measurement.
Among its most salient features are: user-defined exception triggering and gracefully managing numeric values of any size.


[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1028916.svg)](https://zenodo.org/record/1028916) [NumberParser](https://customsolvers.com/number_parser_java/) ([last release](https://github.com/varocarbas/FlexibleParser_Java/releases/tag/UnitParser_1.0.8.5), [readme file](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_readme/NumberParser_Java.md), [test program](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/Test/src/Parts/UnitParser.java))<br/>
It provides a common framework for all the Java numeric types. Main features: exceptions managed internally; beyond-double-range support; custom mathematical functionalities.


## Authorship & Copyright

I, Alvaro Carballo Garcia (varocarbas), am the sole author of each single bit of this code.

Equivalently to what happens with all my other online contributions, this code can be considered public domain. For more information about my copyright/authorship attribution ideas, visit the corresponding pages of my sites:
- https://customsolvers.com/copyright/<br/> 
ES: https://customsolvers.com/copyright_es/
- https://varocarbas.com/copyright/<br/>
ES: https://varocarbas.com/copyright_es/
