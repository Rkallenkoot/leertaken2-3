import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by roelof on 10/03/2016.
 */
public class Main {

    public static void main(String[] args) {
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//
//        pool.execute(new PrintCharTask('a', 10000));
//        pool.execute(new PrintNumTask(10000));
//
//        pool.shutdown();

        thousandThreads();
    }


    private static void thousandThreads(){
        Integer sum = 0;
        ExecutorService pool = Executors.newFixedThreadPool(1000);

        for (int i = 0; i <= 1000; i++) {
            pool.execute(new SumTask(sum));
        }

        System.out.println(sum);
        pool.shutdown();
    }

    private static class SumTask implements Runnable {

        private Integer sum;

        public SumTask(Integer sum) {
            this.sum = sum;
        }

        @Override
        public void run() {
            sum = sum+1;
        }
    }
}
