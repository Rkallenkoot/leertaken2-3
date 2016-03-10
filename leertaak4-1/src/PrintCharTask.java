
public class PrintCharTask  implements Runnable {

    private char charToPrint;
    private int count;

    public PrintCharTask() {
        this('1', 42);
    }

    public PrintCharTask(char charToPrint, int count) {
        this.charToPrint = charToPrint;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println(charToPrint);
        }
    }

}
