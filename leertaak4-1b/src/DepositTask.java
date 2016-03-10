/**
 * Created by roelof on 10/03/2016.
 */
public class DepositTask implements Runnable {

    private Account account;

    public DepositTask(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        try { // Purposely delay it to let the withdraw method proceed
            while (true) {
                account.deposit((int) (Math.random() * 10) + 1);
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
