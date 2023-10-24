public class MyRunnable implements Runnable {

    @Override
    public void run() {
        for (int count = 0; count < 100; count++) {
            if (count % 10 == 0) {
                System.out.print(" Hello number " + count + "from thread #" + Thread.currentThread().threadId() + "\n");

            } else {
                System.out.print(" Hello number " + count + "from thread #" + Thread.currentThread().threadId());
            }
        }
    }
}
