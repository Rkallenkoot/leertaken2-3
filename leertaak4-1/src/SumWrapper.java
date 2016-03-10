/**
 * Created by roelof on 10/03/2016.
 */
public class SumWrapper {

    private static final Object object = new Object();

    private int sum;

    public SumWrapper(int sum) {
        this.sum = sum;
    }

    public void increment(){
        synchronized (object){
            System.out.println(sum);
            sum++;
        }
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString(){
        return String.valueOf(getSum());
    }
}

