package nl.hanze.duo3.Helpers;

import static org.junit.Assert.*;

import nl.hanze.duo3.Exception.ArraySizeException;
import org.junit.Test;

public class ArrayHelperTest {
    @Test
    public void testCorrectSumArrays() throws Exception {
        int firstArray[] = {1,2,3};
        int secondArray[] = {4,5,6};

        int[] sum = ArrayHelper.sumArrays(firstArray, secondArray);
        assertArrayEquals(new int[]{5,7,9}, sum);
    }
    @Test
    public void testIncorrectSumArrays() throws Exception {
        int firstArray[] = {1,2};
        int secondArray[] = {3,4,5};

        try{
            ArrayHelper.sumArrays(firstArray, secondArray);
            fail("sumArrays should've thrown ArraySizeException");
        } catch(ArraySizeException exception){
            assertNotNull(exception);
            assertEquals(2, exception.getFirstArrayLength());
            assertEquals(3, exception.getSecondArrayLength());
        }
    }
}