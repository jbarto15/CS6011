import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    //1. First, create a static int variable in your class named answer. All the threads will add numbers to this variable.
    public static int answer;

    public static Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            for (int count = 0; count < 100; count++) {
                if (count % 10 == 0) {
                    System.out.print(" Hello number " + count + " from thread #" + Thread.currentThread().threadId() + "\n");

                } else {
                    System.out.print(" Hello number " + count + " from thread #" + Thread.currentThread().threadId());
                }
            }
        }
    };

    //say hello function
    public static void sayHello() throws InterruptedException {
        try {
            //1. create an array of threads
            ArrayList<Thread> threadArray = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Thread mythread = new Thread(myRunnable);
                threadArray.add(mythread);
                //2. set each thread to run a function that counts to 100
                //3. start all the threads in array
                mythread.start();
            }

            for (Thread thread : threadArray) {
                //4. join all the threads
                thread.join();
            }
        } catch (IllegalThreadStateException e) {
            System.out.println("Error Message: " + e.getMessage());
        }


    }

    //2. Second, create a static function badSum().
    public static void badSum() throws InterruptedException {
        //3. set answer to 0
        answer = 0;
        //4. set a variable maxValue to 40000
        int maxValue = 100;
        //5. create an array of threads (start with 1 thread in your array to test, then bump it up once you have it working)
        ArrayList<Thread> threads = new ArrayList<>();
        int numberOfThreads = 3;

        for (int i = 0; i < numberOfThreads; i++) {
            SumRunnable sum = new SumRunnable(i, maxValue, numberOfThreads);
            threads.add(new Thread(sum));
            //6. For each thread, i, that thread should add to answer the numbers from i*maxValue/numThreads to Math.min((i+1)*maxValue/numThreads, maxValue). (The minimum is necessary in case maxValue isn't evenly divisible by the number of threads.) Note, if you use an anonymous (lambda) function and try to access i inside of it, java gets skittish here and won't let you write that code exactly. You'll have to store i in a final int variable and use that variable inside your thread's run() method. You can also write a class that implements runnable and take the thread index, i, as a constructor parameter and store it in a member variable. Specifically: for(int i = 0; i < numThreads; i++){ final int finalI = i; //make a thread that uses finalI inside it's runnable}
        }

        //7. Start the threads.
        for (Thread t: threads) {
            t.start();
        }

        //8. Join the threads.
        for (Thread t: threads) {
            t.join();
        }

        //9. Print the computed answer along with the correct answer - you can determine the correct answer using a clever formula that the mathematician Euler gave us: (maxValue * (maxValue - 1) / 2). (Note: this formula works when we loop from count = 0 to count < maxValue. If you include maxValue in your sum, the parenthesized part should be +1 instead of -1)
        System.out.println("Answer: " + answer);


        System.out.println("Real Answer: " + (maxValue * (maxValue - 1) / 2));
    }



    public static void main(String[] args) throws InterruptedException {
        //sayHello();
        badSum();
    }
}



//<--------------------------Questions--------------------->
//1. What happens? Do all the threads run in order?
    //When printing each thread, all the threads do not run in order
    //each thread will print it's number at different intervals


//2. Run your program a couple of times - does the same thread always print the 1st hello? The last hello?
    //No the same thread does not always print the first hello. And the last hello isn't always printed by the same thread

//3. What's happening? Why do the results seem random?
    //When using one thread, we get the right answer but when you do multiple threads it seems like the we're getting a random answer each time.
    //The results seem random because each thread is adding to our answer variable at different times. This causes the answer to be different each time because we don't know when each thread is finished and adding to the answer variable


//4. What do you see? Why?
    //When switching the max value to 100, the answer for all the threads is equal to the real answer we got using Euler's format.
    //This is because we have such a smaller number. With 100 the CPU can assign this to just one thread which does all the calculations faster than breaking it up
    //... to several threads to do the calculations. Since it's only on one thread, we get the real answer.