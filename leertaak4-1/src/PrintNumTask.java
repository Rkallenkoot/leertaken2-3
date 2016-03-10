/**
 * Created by roelof on 10/03/2016.
 */
public class PrintNumTask implements Runnable {

    private int lastNum;

    public PrintNumTask() {
        this(42);
    }

    public PrintNumTask(int lastNum) {
        this.lastNum = lastNum;
    }

    @Override
    public void run() {
        for (int i = 1; i <= lastNum; i++) {
            System.out.println(" " + i);
        }
    }
}
