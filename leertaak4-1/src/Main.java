import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by roelof on 10/03/2016.
 */
public class Main {

    public static void main(String[] args) {
        thousandThreads();
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
