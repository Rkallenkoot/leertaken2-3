package lockControl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintCharTask implements Runnable {

    private final Lock lock = new ReentrantLock();

    private char printChar;
    private int count;


    public PrintCharTask(char printChar, int count) {
        this.printChar = printChar;
        this.count = count;
    }


    @Override
    public void run() {
        lock.lock();
        try {
            for (int i = 0; i < count; i++) {
                System.out.println(printChar);
            }
        } finally {
            lock.unlock();
        }
    }
}
