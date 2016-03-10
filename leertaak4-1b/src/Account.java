public class Account {

    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println("Deposit " + amount + "\t\t\t\t\t" + getBalance());
        notifyAll();
    }

    public synchronized void withdraw(int amount) {
        while (balance < amount) {
            System.out.println("\t\t\tWait for a deposit");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        balance -= amount;
        System.out.println("\t\t\tWithdraw " + amount + "\t\t" + getBalance());
    }

}
