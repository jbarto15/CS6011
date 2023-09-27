import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FractionTest {

    @Test
    void main() {
    }

    @Test
    void fractionPlus() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        Fraction f3 = f1.fractionPlus(f2);

        Assertions.assertEquals( f3.toString(), "5/6" );
    }

    @Test
    void fractionMinus() {
        Fraction f1 = new Fraction(1,2 );
        Fraction f2 = new Fraction( 1, 3 );
        Fraction f3 = f1.fractionMinus( f2 );

        Assertions.assertEquals( f3.toString(), "1/6" );
    }

    @Test
    void fractionTimes() {
        Fraction f1 = new Fraction(1,2 );
        Fraction f2 = new Fraction( 1, 3 );
        Fraction f3 = f1.fractionTimes( f2 );

        Assertions.assertEquals( f3.toString(), "1/6" );
    }

    @Test
    void fractionDividedBy() {
        Fraction f1 = new Fraction(1,2 );
        Fraction f2 = new Fraction( 1, 3 );
        Fraction f3 = f1.fractionDividedBy( f2 );

        Assertions.assertEquals( f3.toString(), "3/2" );
    }

    @Test
    void fractionReciprocal() {
        Fraction f1 = new Fraction(1,2 );
        Fraction f3 = f1.fractionReciprocal();

        Assertions.assertEquals( f3.toString(), "2/1" );
    }

    @Test
    void testingToString() {
        Fraction f1 = new Fraction(1,2 );

        Assertions.assertEquals( f1.toString(), "1/2" );
    }

    @Test
    void toDouble() {
        Fraction f1 = new Fraction(1,2 );

        Assertions.assertEquals( f1.toDouble(), 0.5 );
    }
    @Test
    void print() {
    }

    @Test
    void reduce() {

    }
}