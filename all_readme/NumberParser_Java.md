# NumberParser (Java)

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1028916.svg)](https://zenodo.org/record/1028916)

[Last release](https://github.com/varocarbas/FlexibleParser_Java/releases/tag/NumberParser_1.0.8.5) -- [Test program](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/Test/src/Parts/NumberParser.java) 

[https://customsolvers.com/number_parser_java/](https://customsolvers.com/number_parser_java/) (ES: [https://customsolvers.com/number_parser_java_es/](https://customsolvers.com/number_parser_java_es/))

## Introduction

The ```NumberParser``` package provides a common framework to deal with all the Java numeric types. It relies on the following four classes (NumberX):
- ```Number``` only supports the ```double``` type.
- ```NumberD``` can support any numeric type via ```Object```. 
- ```NumberO``` can support different numeric types simultaneously. 
- ```NumberP``` can parse numbers from strings. 

```Java
//1.23 (double).
Number number = new Number(1.23); 

//123 (int).
NumberD numberD = new NumberD(123);

//1.23 (double). Others: 1 (int) and '' (char).
NumberO numberO = new NumberO
(
	1.23, new ArrayList() 
	{{ 
		add(NumericTypes.Integer); 
		add(NumericTypes.Character); 
	}}
); 

//1 (long).
NumberP numberP = new NumberP
(
	"1.23", new ParseConfig(NumericTypes.Long)
);
```

## Common Features

All the NumberX classes have various characteristics in common.
- Defined according to ```getValue()``` (```double``` or ```Object```) and ```getBaseTenExponent()``` (```int```). All of them support ranges beyond [-1, 1] * 10^2147483647. 
- Static (```NumberD.Addition(numberD1, numberD2)```) and non-static (```numberD1.greaterThan(numberD2)```) support for the main arithmetic and comparison operations.
- Errors managed internally and no exceptions thrown.
- Numerous instantiating alternatives.

```Java
//12.3*10^456 (double).
Number number = new Number(12.3, 456); 

//123 (int).
NumberD numberD = 
(
	new NumberD(123).lessThan(new NumberD(new Number(456))) ?
	//123 (int)
	new NumberD(123.456, NumericTypes.Integer) :
	//123.456 (double)
	new NumberD(123.456)
);


//Error (ErrorTypesNumber.InvalidOperation) provoked when dividing by zero.
NumberO numberO = NumberO.Division
(
	new NumberO
	(
		123.0, OtherTypes.IntegerTypes
	)
	, new NumberO(0)
);

//1.234000000000e+308*10^5373 (double).
NumberP numberP = new NumberP("1234e5678");
```

## Math2 Class

This class includes all the NumberParser mathematical functionalities.

### Custom Functionalities

- ```RoundExact```/```TruncateExact``` can deal with multiple rounding/truncating scenarios not supported by the native methods.
- ```GetPolynomialFit```/```ApplyPolynomialFit``` allow to deal with second degree polynomial fits. 
- ```Factorial``` calculates the factorial of any integer number up to 100000. 

```Java
//123000 (double).
Number number = Math2.RoundExact
(
	new Number(123456.789), 3, RoundType.AlwaysToZero, 
	RoundSeparator.BeforeDecimalSeparator
);

//30 (double).
NumberD numberD = Math2.ApplyPolynomialFit
(
	Math2.GetPolynomialFit
	(
		new NumberD[] 
		{ 
			new NumberD(1), new NumberD(2), new NumberD(4) 
		}, 
		new NumberD[] 
		{ 
			new NumberD(10), new NumberD(20), new NumberD(40) 
		}
	)
	, new NumberD(3)
);

//3628800 (int).
NumberD numberD = Math2.Factorial(new NumberD(10));
```

### Native Methods
```Math2``` also includes ```NumberD```-adapted versions of a big number of ```Math``` and .NET ```System.Math``` methods. 

It also includes ```PowDecimal```\\```SqrtDecimal``` which allow to unrestrictedly use NumberX variables with ```Math.pow```\\```Math.sqrt```. Note that this Java version doesn't rely on the original C# custom implementation (detailed explanations in [varocarbas.com Project 10](https://varocarbas.com/fractional_exponentiation/)) because of only making sense within the .NET conditions (i.e., high-precision ```decimal``` type not natively supported by the in-built methods).

```Java
//1.582502898380e+14 (double).
Number number = Math2.PowDecimal
(
	new Number(123.45), 6.789101112131415161718
);

//4.8158362157911885 (double).
NumberD numberD = Math2.Log(new NumberD(123.45));
```

## Further Code Samples
The [test application](https://github.com/varocarbas/FlexibleParser_Java/blob/master/all_code/Test/src/Parts/NumberParser.java) includes a relevant number of descriptive code samples. 

## Authorship & Copyright
I, Alvaro Carballo Garcia (varocarbas), am the sole author of each single bit of this code.

Equivalently to what happens with all my other online contributions, this code can be considered public domain. For more information about my copyright/authorship attribution ideas, visit [https://customsolvers.com/copyright/](https://customsolvers.com/copyright/).