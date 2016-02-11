package nl.hanze.duo3.Helpers;

import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayHelperTest {


    @Test
    public void testCorrectSumArrays() throws Exception {
        int firstArray[] = {1,2,3};
        int secondArray[] = {4,5,6};

        int[] sum = ArrayHelper.sumArrays(firstArray, secondArray);
        assertArrayEquals(new int[]{5,7,9}, sum);
    }



}