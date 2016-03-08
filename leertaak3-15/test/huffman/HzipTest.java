package huffman;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


public class HzipTest {

    @Test
    public void tesetHuffZip(){
        String file = "TestA.dat";

        try {
            Hzip.compress(file);
        } catch (IOException e) {
            fail("Exception when compressing file");
            e.printStackTrace();
        }



    }

}