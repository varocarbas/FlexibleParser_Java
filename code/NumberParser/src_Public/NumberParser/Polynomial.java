package NumberParser;

import java.util.Locale;

import InternalNumberParser.Operations.*;

/**
Stores the coefficients defining a second degree polynomial fit via y = A + B*x + C*x^2.
**/
public class Polynomial
{
    /**
    Compares the current instance against another Polynomial one.
    @param other The other Polynomial instance.
    **/
    public int CompareTo(Polynomial other)
    {
        if (this.A != other.A)
        {
            return OperationsCompareTo.CompareDynamic(this.A, other.A);
        }
        if (this.B != other.B)
        {
            return OperationsCompareTo.CompareDynamic(this.B, other.B);
        }

        return OperationsCompareTo.CompareDynamic(this.C, other.C);
    }

    /**Polynomial coefficient A, as defined by y = A + B*x + C*x^2.**/
    public NumberD A = null;

    /**Coefficient A getter.**/
    public NumberD getCoefficientA()
	{
	    return A;
	}

    /**Coefficient A setter.**/
	public void setCoefficientA(NumberD a)
	{
	     A = new NumberD(a);
	}
    /**Polynomial coefficient B, as defined by y = A + B*x + C*x^2.**/
    public NumberD B = null;

    /**Coefficient B getter.**/
    public NumberD getCoefficientB()
	{
	    return B;
	}

    /**Coefficient B setter.**/
	public void setCoefficientB(NumberD b)
	{
	     B = new NumberD(b);
	}
    
    /**Polynomial coefficient C, as defined by y = A + B*x + C*x^2.**/
    public NumberD C = null;

    /**Coefficient C getter.**/
    public NumberD getCoefficientC()
	{
	    return C;
	}

    /**Coefficient C setter.**/
	public void setCoefficientC(NumberD c)
	{
	     C = new NumberD(c);
	}
	
    public ErrorTypesNumber Error = ErrorTypesNumber.None;

    /**Error getter.**/
    public ErrorTypesNumber getError()
	{
	    return Error;
	}
    
    /**
    Initialises a new Polynomial instance.
    @param a Coefficient A in y = A + B*x + C*x^2.
    @param b Coefficient B in y = A + B*x + C*x^2.
    @param c Coefficient C in y = A + B*x + C*x^2.
    **/
    public Polynomial(NumberD a, NumberD b, NumberD c)
    {
        A = new NumberD(a);
        B = new NumberD(b);
        C = new NumberD(c);

        if 
        (
        	!A.getError().equals(ErrorTypesNumber.None) || 
        	!B.getError().equals(ErrorTypesNumber.None) || 
        	!C.getError().equals(ErrorTypesNumber.None)
        )
        {
            A = null;
            B = null;
            C = null;
            
            Error = ErrorTypesNumber.InvalidInput;
        }
    }

    /** WARNING: the purpose of this constructor in the original C# code was to be exclusively used internally. */
    public Polynomial(ErrorTypesNumber error) { Error = error; }
   	
	@Override
    /**
	Outputs the information in all the public fields (one per line).
     **/
    public String toString()
    {
        return toString(Locale.US);
    }

    /**
	Outputs the information in all the public fields (one per line).
     **/
    public String toString(Locale culture)
    {
        if (!Error.equals(ErrorTypesNumber.None)) return "Error. " + Error.toString();
        if (culture == null) culture = Locale.getDefault();

        return
        (
            A == null || B == null || C == null ? "" :
            "A: " + A.toString(culture) + System.lineSeparator() +
            "B: " + B.toString(culture) + System.lineSeparator() +  
            "C: " + C.toString(culture)
        );
    }
	
    @Override
    /**
    Determines whether the current Polynomial instance is equal to other one.
    @param obj Other variable.
     **/
    public boolean equals(Object obj)
    {
        return Equals((Polynomial)obj);
    }
    
    boolean Equals(Polynomial other)
    {
        return
        (
            other == null ? false :
            OperationsEquals.PolynomialsAreEqual
            (
            	this, other
            )
        );
    }

    @Override
    /**Returns the hash code for this Polynomial instance.**/
    public int hashCode() { return 0; }  
}
