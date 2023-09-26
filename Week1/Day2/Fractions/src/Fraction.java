import javax.print.DocFlavor;

public class Fraction {
    long _numerator;
    long _denominator;


    public static void main(String[] args) {

    }

    //Fraction() - The default constructor, which sets the value of the fraction to 0/1. (Note, a constructor is similar to a method, but not actually a method.)
    public Fraction() {
        _numerator = 0;
        _denominator = 1;
    }

    //Fraction( long n, long d ) - A constructor which sets the value of the fraction to a specific numerator (n) and denominator (d)
    public Fraction(long n, long d) {
        //add a reduce in the constructor so the object is always reduced
        if (n < 0 && d > 0) {
            _numerator = n;
            _denominator = d;
        } else if (n > 0 && d < 0) {
            _numerator = n * -1;
            _denominator = d * -1;
        } else if (n < 0 && d < 0) {
            _numerator = n * -1;
            _denominator = d * -1;
        } else {
            _numerator = n;
            _denominator = d;
        }
        this.reduce();
    }

    //Fraction plus( Fraction rhs ) - Returns a new fraction that is the result of the right hand side (rhs) fraction added to this fraction
    public Fraction fractionPlus(Fraction rhs) {
        //multiply the denominators to get a common denominator
        long commonDenominator = _denominator * rhs._denominator;

        //multiply the numerators by the other fractions denominator
        long fraction1NewNumerator = _numerator * rhs._denominator;
        long fraction2NewNumerator = rhs._numerator * _denominator;

        //add the numerators together
        long addedNumerator = fraction1NewNumerator + fraction2NewNumerator;

        //create new fraction object
        Fraction addedFraction = new Fraction(addedNumerator, commonDenominator);

        return addedFraction;
    }

    //Fraction minus(Fraction rhs) - Returns a new fraction that is the result of the right hand side (rhs) fraction subtracted from this fraction.
    public Fraction fractionMinus(Fraction rhs) {
        //multiply the denominators to get a common denominator
        long commonDenominator = _denominator * rhs._denominator;

        //multiply the numerators by the other fractions denominator
        long fraction1NewNumerator = _numerator * rhs._denominator;
        long fraction2NewNumerator = rhs._numerator * _denominator;

        //subtract the numerators together
        long subtractedNumerator = fraction1NewNumerator - fraction2NewNumerator;

        //create new fraction object
        Fraction subtractedFraction = new Fraction(subtractedNumerator, commonDenominator);

        return subtractedFraction;
    }

    //Fraction times(Fraction rhs) - Returns a new fraction that is the result of this fraction multiplied by the right hand side (rhs) fraction
    public Fraction fractionTimes(Fraction rhs) {
        //multiply the numerators together
        long multipliedNumerators = _numerator * rhs._numerator;
        //multiply the denominators
        long multipliedDenominators = _denominator * rhs._denominator;

        //create a new fraction object
        Fraction multipliedFraction = new Fraction(multipliedNumerators, multipliedDenominators);

        return multipliedFraction;
    }

    //Fraction dividedBy(Fraction rhs) - Returns a new fraction that is the result of this fraction divided by the right hand side (rhs) fraction
    public Fraction fractionDividedBy(Fraction rhs) {
        //create object to store the fractionReciprocal in
        Fraction storeReciprocal = new Fraction();

        //take the reciprocal of the rhs
        storeReciprocal = rhs.fractionReciprocal();

        //multiply the numerators together
        long multipliedNumerators = _numerator * storeReciprocal._numerator;
        //multiply the denominators
        long multipliedDenominators = _denominator * storeReciprocal._denominator;

        //create a new fraction object
        Fraction dividedByFraction = new Fraction(multipliedNumerators, multipliedDenominators);

        System.out.println("Division: " + dividedByFraction._numerator + "/" + dividedByFraction._denominator);

        return dividedByFraction;

    }

    //Fraction reciprocal() - Returns a new fraction that is the reciprocal of this fraction.
    public Fraction fractionReciprocal() {
        //create new fraction to return
        Fraction reciprocalFraction = new Fraction(_denominator, _numerator);

        return reciprocalFraction;
    }

    //String toString() - Returns a string representing this fraction. The string should have the format: "N/D", where N is the numerator, and D is the denominator. This method should always print the reduced form of the fraction. If the fraction is negative, the sign should be displayed on the numerator, e.g., "-1/2" not "1/-2". Note: once you implement this, you will be able to print a Fraction with println... more on how this works later
    public String toString() {
        String numeratorAsString = String.valueOf(_numerator);
        String denominatorAsString = String.valueOf(_denominator);
        String result = numeratorAsString + "/" + denominatorAsString;

        return result;
    }


    //double toDouble() - Returns a (double precision) floating point number that is the approximate value of this fraction, printed as a real number.
    public double toDouble() {
        double numeratorAsDouble = (double)_numerator;
        double denominatorAsDouble = (double)_denominator;

        double result = numeratorAsDouble / denominatorAsDouble;

        return result;
    }


    //print method for fraction
    public void print() {
        System.out.println(_numerator + "/" + _denominator);
    }

    //reduce function
    private void reduce() {
        //call the GCD and store that value in a variable
        long numeratorDividedByGCD = _numerator / this.GCD();
        long denominatorDividedByGCD = _denominator / this.GCD();
        _numerator = numeratorDividedByGCD;
        _denominator = denominatorDividedByGCD;
    }

    //long GCD() - Returns the greatest common divisor of this fraction's numerator and denominator. This is a helper method for the reduce method, below
    private long GCD() {
        long gcd = _numerator;
        long remainder = _denominator;
        while (remainder != 0) {
            long temp = remainder;
            remainder = gcd % remainder;
            gcd = temp;
        }
        return gcd;
    }
}


