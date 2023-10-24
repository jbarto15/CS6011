public class SumRunnable implements Runnable {
   //member variables
    int maxValue_;

    int numberOfThreads_;

    int threadID_;

    //constructor
    SumRunnable(int i, int maxValue, int numberOfThreads) {
        threadID_ = i;
        maxValue_= maxValue;
        numberOfThreads_ = numberOfThreads;
    }


    @Override
    public void run() {
        int start = threadID_ * maxValue_ / numberOfThreads_;
        int end = Math.min((threadID_ + 1) * maxValue_ / numberOfThreads_, maxValue_);
        for (int i = start; i < end; i++) {
            Main.answer += i;
        }
    }
}
