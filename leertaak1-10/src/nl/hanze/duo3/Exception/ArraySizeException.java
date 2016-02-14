package nl.hanze.duo3.Exception;

public class ArraySizeException extends Exception {

    private int firstArrayLength;
    private int secondArrayLength;

    public ArraySizeException(int firstArrayLength, int secondArrayLength){
        super("Array Sizes do not match");
        this.firstArrayLength = firstArrayLength;
        this.secondArrayLength = secondArrayLength;
    }

    public int getFirstArrayLength() {
        return firstArrayLength;
    }

    public int getSecondArrayLength() {
        return secondArrayLength;
    }
}

