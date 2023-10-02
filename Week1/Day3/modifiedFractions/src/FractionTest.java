import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

class FractionTest {

    @Test
    void main() {
    }

    @Test
    void fractionPlus() throws Exception {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        Fraction f3 = f1.fractionPlus(f2);

        Assertions.assertEquals( f3.toString(), "5/6" );
    }

    @Test
    void fractionMinus() throws Exception {
        Fraction f1 = new Fraction(1,2 );
        Fraction f2 = new Fraction( 1, 3 );
        Fraction f3 = f1.fractionMinus( f2 );

        Assertions.assertEquals( f3.toString(), "1/6" );
    }

    @Test
    void fractionTimes() throws Exception {
        Fraction f1 = new Fraction(1,2 );
        Fraction f2 = new Fraction( 1, 3 );
        Fraction f3 = f1.fractionTimes( f2 );

        Assertions.assertEquals( f3.toString(), "1/6" );
    }

    @Test
    void fractionDividedBy() throws Exception {
        Fraction f1 = new Fraction(1,2 );
        Fraction f2 = new Fraction( 1, 3 );
        Fraction f3 = f1.fractionDividedBy( f2 );

        Assertions.assertEquals( f3.toString(), "3/2" );
    }

    @Test
    void fractionReciprocal() throws Exception {
        Fraction f1 = new Fraction(1,2 );
        Fraction f3 = f1.fractionReciprocal();

        Assertions.assertEquals( f3.toString(), "2/1" );
    }

    @Test
    void testingToString() throws Exception {
        Fraction f1 = new Fraction(1,2 );

        Assertions.assertEquals( f1.toString(), "1/2" );
    }

    @Test
    void toDouble() throws Exception {
        Fraction f1 = new Fraction(1,2 );

        Assertions.assertEquals( f1.toDouble(), 0.5 );
    }
    @Test
    void print() {
    }

    @Test
    void reduce() {

    }

    @Test
    void sort() throws Exception {
        ArrayList<Fraction> list = new ArrayList<Fraction>();
        Fraction f1 = new Fraction(4,9 );
        Fraction f2 = new Fraction( 1, 3 );
        Fraction f3 = f1.fractionDividedBy( f2 );
        list.add(f1);
        list.add(f2);
        list.add(f3);
        System.out.println("Before: " + list);
        Collections.sort(list);

        System.out.println("After: " + list);

        Assertions.assertEquals(list.toString(), "[1/3, 4/9, 4/3]");

    }
}