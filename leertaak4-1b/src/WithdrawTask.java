/**
 * Created by roelof on 10/03/2016.
 */
public class WithdrawTask implements Runnable {

    private Account account;

    public WithdrawTask(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        while (true) {
            account.withdraw((int) (Math.random() * 10) + 1);
        }
    }

}
