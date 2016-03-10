/**
 * Created by roelof on 10/03/2016.
 */
public class Account {

    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println("Deposit " + amount +
                "\t\t\t\t\t" + getBalance());
        notifyAll();
    }

    public synchronized void withdraw(int amount) {
        try {
            while (balance < amount)
                wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        balance -= amount;
        System.out.println("\t\t\tWithdraw " + amount +
                "\t\t" + getBalance());
    }

}
