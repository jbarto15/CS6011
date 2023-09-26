import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //print out hello world
        System.out.println("Hello world!\n");

        //Next, create an array of 10 ints. Fill it with random values. Print out each one, then print out the sum of the numbers in the array.
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.add(6);
        arrayList.add(7);
        arrayList.add(8);
        arrayList.add(9);
        arrayList.add(10);

        //create a sum variable
        int sum = 0;

        //print out each arrayList value
        for (int i = 0; i < arrayList.size(); i++) {
            // print out each value in the array
            System.out.println("i = " + arrayList.get(i));
            sum += arrayList.get(i);
        }

        //print out the sum
        System.out.println(sum);

        //ask a user to input their name
        System.out.println("Please input your name: ");

        //get the input
        Scanner s = new Scanner(System.in);
        String name = s.next();

        //ask a user to input their age
        System.out.println("Please input your age: ");

        //get the input for age
        int age = s.nextInt();

        //print whether or not they are old enough to vote, and which generation they belong to (iGen, millennial, gen X, boomers, greatest generation).
        //create if statement that sees if the user is able to vote. The voting age is 18
        if (age >= 18) {
            System.out.println("You are old enough to vote.\n");
        }

        //create if statement to check if user is part of the greatest generation
        if (age > 80) {
            System.out.println("You are part of the greatest generation!\n");
        }

        //create if statement to check if user is part of the baby boomer generation
        if (age < 80 && age > 60) {
            System.out.println("You are part of the baby boomer generation!\n");
        }

        //create if statement to check if user is part of generation X
        if (age > 40 && age < 60) {
            System.out.println("You are part of generation X!\n");
        }

        //create if statement to check if user is part of millennial generation and else statement to say that anyone else is part of the iKid generation.
        if (age > 20 && age < 40) {
            System.out.println("You are part of the millennial generation!\n");
        } else if (age < 20) {
            System.out.println("You are part of the iKid generation!\n");
        }
    }
}