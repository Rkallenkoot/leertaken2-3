package nl.hanze.duo3.Helpers;


import nl.hanze.duo3.Exception.ArraySizeException;

public class ArrayHelper {

    public static int[] sumArrays(int[] firstArray, int[] secondArray) throws ArraySizeException {
        if(firstArray.length != secondArray.length){
            throw new ArraySizeException(firstArray.length, secondArray.length);
        }
        int[] sum = new int[firstArray.length];
        for (int i = 0; i < firstArray.length; i++) {
            sum[i] = firstArray[i] + secondArray[i];
        }
        return sum;
    }

}
