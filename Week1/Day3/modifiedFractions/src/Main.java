//import Fraction;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Hello world!");
            Fraction f1 = new Fraction(2, 3);
            Fraction f2 = new Fraction(2, 1);
            System.out.println(f1);//calling to String
        } catch (Exception e) {
            System.out.println("Failed due to: "  + e.getMessage());
        }
    }
}
