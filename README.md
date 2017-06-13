# UnitParser (Java) 

[Master source code](https://github.com/varocarbas/FlexibleParser_Java/tree/master/all_code/UnitParser) -- [Test program](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/Test/src/Parts/UnitParser.java) 

[https://customsolvers.com/unit_parser_java/](https://customsolvers.com/unit_parser_java/) (ES: [https://customsolvers.com/unit_parser_java_es/](https://customsolvers.com/unit_parser_java_es/))

## Introduction
The main class is called ```UnitP``` (```UnitParser``` package). It can be instantiated in many different ways.

```Java
//1 N.
UnitP unitP = new UnitP("1 N"); 

//1 N.
unitP = new UnitP(1.0, UnitSymbols.Newton);

//1 N.
unitP = new UnitP(1.0, "nEwTon");

//1 N.
unitP = new UnitP(1.0, Units.Newton);
```

```UnitP``` can be seen as an abstract concept including many specific types ([full list](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/UnitParser/src_Public/UnitParser/UnitTypes.java)). Same-type variables can be added/subtracted. Different-type variables can be multiplied/divided, but only in case of generating a valid-type output.

```Java
//2 N.
unitP = UnitP.Addition(new UnitP("1 N"), new UnitP(1.0, Units.Newton));

//1 J.
unitP = UnitP.Multiplication(new UnitP("1 N"), new UnitP("1 m"));

//Error not triggering an exception. 
//The output unit N*m^2 doesn't match any supported type.
unitP = UnitP.Multiplication
(
	UnitP.Multiplication
	(
		new UnitP("1 N"), new UnitP("1 m")
	), 
	new UnitP("1 m")
); 
```

### Main Variable Information
```UnitP``` variables are defined according to various ```final``` fields populated at instantiation.

```Unit``` - Corresponding [Units](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/UnitParser/src_Public/UnitParser/Units.java) member.<br>
```UnitType``` - Corresponding [UnitTypes](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/UnitParser/src_Public/UnitParser/UnitTypes.java) member.<br>
```UnitSystem``` - Corresponding [UnitSystems](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/UnitParser/src_Public/UnitParser/UnitSystems.java) member.<br>
```UnitParts``` - Defining parts of the given unit.<br>
```UnitPrefix``` - [Supported prefix](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/UnitParser/src_Public/UnitParser/Prefix.java) affecting all the unit parts.<br>
```BaseTenExponent``` - Base-ten exponent used when dealing with too small/big values.<br>
```Error``` - Variable storing all the error- and exception-related information.

## General Rules

All the functionalities are based upon the following ideas:
- In case of incompatibility, the first element is always preferred.
- By default, the formally-correct alternative is preferred. Some required modifications might be performed.
- By default, all the errors are managed internally.

```Java
//1.3048.
unitP = UnitP.Addition(new UnitP("1 m"), new UnitP("1 ft")); 

//Error not triggering an exception. 
//The parser expects "km" or a full-name-based version like "KiLom".
unitP = new UnitP("1 Km"); 

//999999.9999999001*10^19 YSt.
unitP = UnitP.Multiplication
(
	999999999999999999999999999999999999.9,
	new UnitP("9999999999999 St")
); 
```

## Unit String Parsing Format

The unit string parsing part is quite flexible, but there are some basic rules.
- String multi-part units are expected to be exclusively formed by units, multiplication/division signs and integer exponents.
- Only one division sign is expected. The parser understands that all what lies before/after it is the numerator/denominator.

```Java
//1 W.
unitP = new UnitP("1 J*J/s*J2*J-1*s*s-1");

//Error not triggering an exception. 
//The parser understands "J*J/(s*J2*s*J*s)", what doesn't represent a supported type.
unitP = new UnitP("1 J*J/(s*J2*s)*J*s");
```

## Numeric Support
Formally, only the ```double``` type is supported. Practically, ```UnitP``` variables implement a mixed system delivering beyond-```double```-range support. 

```Java
//7.891011 ft.
unitP = UnitP.Multiplication(new UnitP("1 ft"), 7.891011);

//1.213141516 Gs.
unitP = UnitP.Multiplication(new UnitP("1 s"), 1213141516.0);

//0.0003094346047382587*10^-752 ym.
unitP = UnitP.Division
(
	UnitP.Division
	(
		UnitP.Division
		(
			UnitP.Multiplication
			(
				0.0000000000000000000000000000000000000000000000001,
				new UnitP(0.000000000000000000001, "ym2")
			),
			new UnitP("999999999999999999999 Ym")
		),
		Double.MAX_VALUE
	),
	Double.MAX_VALUE
);
```

## Further Code Samples
The [test application](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/Test/src/Parts/UnitParser.java) includes a relevant number of descriptive code samples. 

## Authorship & Copyright

I, Alvaro Carballo Garcia (varocarbas), am the sole author of each single bit of this code.

Equivalently to what happens with all my other online contributions, this code can be considered public domain. For more information about my copyright/authorship attribution ideas, visit the corresponding pages of my sites:
- https://customsolvers.com/copyright/<br/> 
ES: https://customsolvers.com/copyright_es/
- https://varocarbas.com/copyright/<br/>ES: https://varocarbas.com/copyright_es/
