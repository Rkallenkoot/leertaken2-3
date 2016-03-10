/**
 * Created by roelof on 10/03/2016.
 */
public class SumTask implements Runnable {

    SumWrapper wrapper;

    public SumTask(SumWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void run() {
        this.wrapper.increment();
    }
}
