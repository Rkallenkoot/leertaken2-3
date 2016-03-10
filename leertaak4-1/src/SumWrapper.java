/**
 * Created by roelof on 10/03/2016.
 */
public class SumWrapper {

    private int sum;

    public SumWrapper(int sum) {
        this.sum = sum;
    }

    public synchronized void increment(){
        System.out.println(sum);
        sum++;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString(){
        return String.valueOf(getSum());
    }
}

