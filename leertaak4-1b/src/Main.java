import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by roelof on 10/03/2016.
 */
public class Main {

    public static void main(String[] args) {
        Account account = new Account();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(new DepositTask(account));
        pool.execute(new WithdrawTask(account));
        pool.shutdown();

        System.out.println("Deposit Task\t\tWithdraw Task\t\tBalance");
    }
}
