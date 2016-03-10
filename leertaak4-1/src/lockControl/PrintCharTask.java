package lockControl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintCharTask implements Runnable {

    private final Lock lock = new ReentrantLock();
    private Condition ready = lock.newCondition();

    private int threadNumber;
    private char printChar;
    private int count;

    public static int NEXT_THREAD;


    public PrintCharTask(char printChar, int count) {
        this.printChar = printChar;
        this.count = count;
    }

    public PrintCharTask(char printChar, int count, int threadNumber){
        this(printChar, count);
        this.threadNumber= threadNumber;
    }


    @Override
    public void run() {
        lock.lock();
        try {
            while(threadNumber < NEXT_THREAD){
                System.out.println("Wait for bami: " + threadNumber + " nextThread: " + NEXT_THREAD);
                ready.await();
            }
            for (int i = 0; i < count; i++) {
                System.out.println(printChar);
            }
            PrintCharTask.NEXT_THREAD = threadNumber-1;
            ready.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
