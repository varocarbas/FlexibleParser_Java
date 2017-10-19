package InternalNumberParser.Math2Internal;

import NumberParser.*;
import NumberParser.Number;
import InternalNumberParser.*;
import InternalNumberParser.Operations.*;

public class Math2Regression
{
    public static NumberD ApplyPolynomialFitInternal(Polynomial coefficients, NumberD xValue)
    {
        ErrorTypesNumber error = ErrorInfoNumber.ApplyPolynomialFitError
        (
        	coefficients, xValue
        );
        if (!error.equals(ErrorTypesNumber.None)) return new NumberD(error);

        if (!coefficients.A.getType().equals(xValue.getType()))
        {
            xValue = Conversions.ConvertNumberToAny
            (
            	new Number(xValue), coefficients.A.getType()
            );
        }

        //coefficients.A + coefficients.B * xValue + coefficients.C * xValue * xValue;
        return OperationsManaged.AddInternal
        (
            coefficients.A, OperationsManaged.AddInternal
            (
            	OperationsManaged.MultiplyInternal(coefficients.B, xValue),
            	OperationsManaged.MultiplyInternal
                (
                	coefficients.C, OperationsManaged.MultiplyInternal
                	(
                		xValue, xValue
                	)
                )
            )
        );
    }

    public static Polynomial GetPolynomialFitInternal(NumberD[] xValues, NumberD[] yValues)
    {
        ErrorTypesNumber error = ErrorInfoNumber.GetPolynomialFitError(xValues, yValues);
        if (!error.equals(ErrorTypesNumber.None)) return new Polynomial(error);

        NumericTypes type = xValues[0].getType();
        if (!Basic.AllDecimalTypes.contains(type))
        {
            //An integer type would provoke the subsequent calculations to fail.
            type = NumericTypes.Double;
        }
        xValues = SyncType(xValues, type);
        yValues = SyncType(yValues, type);

        //Getting the coefficients matrix generated after calculating least squares.
        GetGaussJordanCoeffs(xValues, yValues, type);

        for (int i = 0; i < 3; i++)
        {
            for (int i2 = 0; i2 < 3; i2++)
            {
                if (i != i2)
                {
                    //factor = 
                    //( 
                    //    GaussJordan.a[i, i] == 0 ? 0 : 
                    //    -1 * GaussJordan.a[i2, i] / GaussJordan.a[i, i]
                    //);

                    NumberD factor =
                    (
                        GaussJordan.a[i][i] == new NumberD(type) ? new NumberD(type) :
                        OperationsManaged.MultiplyInternal
                        (
                            new NumberD(Basic.GetNumberSpecificType(-1, type)), 
                            OperationsManaged.DivideInternal
                            (
                                GaussJordan.a[i2][i], GaussJordan.a[i][i]
                            )
                        )
                    );

                    for (int i3 = 0; i3 < 3; i3++)
                    {
                        //GaussJordan.a[i2, i3] = factor * GaussJordan.a[i, i3] + GaussJordan.a[i2, i3];
                        GaussJordan.a[i2][i3] = OperationsManaged.AddInternal
                        (
                            OperationsManaged.MultiplyInternal
                            (
                                factor, GaussJordan.a[i][i3]
                            ),
                            GaussJordan.a[i2][i3]
                        );
                    }

                    //GaussJordan.b[i2] = factor * GaussJordan.b[i] + GaussJordan.b[i2];
                    GaussJordan.b[i2] = OperationsManaged.AddInternal
                    (
                        OperationsManaged.MultiplyInternal
                        (
                        	factor, GaussJordan.b[i]
                        ),
                        GaussJordan.b[i2]
                    );
                }
            }
        }

        return new Polynomial
        (
            CalculatePolynomialCoefficient(0, type),
            CalculatePolynomialCoefficient(1, type),
            CalculatePolynomialCoefficient(2, type)
        );
    }

    static NumberD[] SyncType(NumberD[] array, NumericTypes type)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (!array[i].getType().equals(type))
            {
                array[i] = Conversions.ConvertNumberToAny
                (
                	new Number(array[i]), type
                );
            }
        }

        return array;
    }

    static NumberD CalculatePolynomialCoefficient(int i, NumericTypes type)
    {
        return
        (
            GaussJordan.a[i][i] == new NumberD(type) ? 
            new NumberD(type) : OperationsManaged.DivideInternal
            (
            	GaussJordan.b[i], GaussJordan.a[i][i]
            )
        );
    }

    static void GetGaussJordanCoeffs
    (
    	NumberD[] xValues, NumberD[] yValues, NumericTypes type
    )
    {
        LeastSquares.StartLeastSquares(type);

        for (int i = 0; i < xValues.length; i++)
        {
            NumberD curX2 = new NumberD
            (
            	OperationsManaged.MultiplyInternal
            	(
            		xValues[i], xValues[i]
            	)
            );

            LeastSquares.sumX1 = OperationsManaged.AddInternal
            (
            	LeastSquares.sumX1, xValues[i]
            );
            LeastSquares.sumX2 = OperationsManaged.AddInternal
            (
            	LeastSquares.sumX2, curX2
            );            
            LeastSquares.sumX12 = OperationsManaged.AddInternal
            (
            	LeastSquares.sumX12, OperationsManaged.MultiplyInternal
                (
                	xValues[i], curX2
                )
            );    
            LeastSquares.sumX1Y = OperationsManaged.AddInternal
            (
            	LeastSquares.sumX1Y, OperationsManaged.MultiplyInternal
                (
                	xValues[i], yValues[i]
                )
            ); 
            LeastSquares.sumX22 = OperationsManaged.AddInternal
            (
            	LeastSquares.sumX22, OperationsManaged.MultiplyInternal
                (
                	curX2, curX2
                )
            ); 
            LeastSquares.sumX2Y = OperationsManaged.AddInternal
            (
            	LeastSquares.sumX2Y, OperationsManaged.MultiplyInternal
                (
                	curX2, yValues[i]
                )
            );
            LeastSquares.sumY = OperationsManaged.AddInternal
            (
            	LeastSquares.sumY, yValues[i]
            );
        }

        //a/b arrays emulating the matrix storing the least square outputs, as defined by:
        // a[0, 0]   a[0, 1]  a[0, 2]  | b[0]
        // a[1, 0]   a[1, 1]  a[1, 2]  | b[1]
        // a[2, 0]   a[2, 1]  a[2, 2]  | b[2]
        GaussJordan.StartGaussJordan();
        GaussJordan.a[0][0] = new NumberD(xValues.length);
        GaussJordan.a[0][1] = LeastSquares.sumX1;
        GaussJordan.a[0][2] = LeastSquares.sumX2;
        GaussJordan.a[1][0] = LeastSquares.sumX1;
        GaussJordan.a[1][1] = LeastSquares.sumX2;
        GaussJordan.a[1][2] = LeastSquares.sumX12;
        GaussJordan.a[2][0] = LeastSquares.sumX2;
        GaussJordan.a[2][1] = LeastSquares.sumX12;
        GaussJordan.a[2][2] = LeastSquares.sumX22;

        GaussJordan.b[0] = LeastSquares.sumY;
        GaussJordan.b[1] = LeastSquares.sumX1Y;
        GaussJordan.b[2] = LeastSquares.sumX2Y;
    }

    static class GaussJordan
    {
        public static NumberD[][] a;
        public static NumberD[] b;

        public static void StartGaussJordan()
        {
            a = new NumberD[3][3];
            b = new NumberD[3];
        }
    }

    static class LeastSquares
    {
        public static NumberD sumX1, sumX2, sumX12, sumX1Y, sumX22, sumX2Y, sumY;

        public static void StartLeastSquares(NumericTypes type)
        {
            sumX1 = new NumberD(type);
            sumX2 = new NumberD(type);
            sumX12 = new NumberD(type);
            sumX1Y = new NumberD(type);
            sumX22 = new NumberD(type);
            sumX2Y = new NumberD(type);
            sumY = new NumberD(type);
        }
    }
}
