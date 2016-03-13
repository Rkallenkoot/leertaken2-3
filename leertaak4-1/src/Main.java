import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
//        thousandThreads();
        eersteOpgave();
    }

    private static void eersteOpgave() {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (int i = 1; i <= 4; i++) {
            int nextThread = i == 4 ? 4 : i+1;
            lockControl.PrintCharTask task = new lockControl.PrintCharTask(Character.forDigit(i, 10), 2, i);
            lockControl.PrintCharTask.NEXT_THREAD = nextThread;
            pool.execute(task);
        }

        pool.shutdown();

    }

    private static void thousandThreads(){
        SumWrapper wrapper = new SumWrapper(0);
        ExecutorService pool = Executors.newFixedThreadPool(1000);

        for (int i = 0; i < 1000; i++) {
            pool.execute(new SumTask(wrapper));
        }
        pool.shutdown();
    }

}
