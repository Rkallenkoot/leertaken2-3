import org.junit.Before;
import org.junit.Test;
import org.junit.internal.ExactComparisonCriteria;

import static org.junit.Assert.*;

public class DobbelsteenModelTest {

    private static final int TEST_VALUE = 3;

    @Test
    public void testGetWaarde() throws Exception {
        DobbelsteenModel dobbelsteenModel = new DobbelsteenModel(TEST_VALUE);
        assertEquals(TEST_VALUE, dobbelsteenModel.getWaarde());
    }

    @Test
    public void testVerhoog() throws Exception {
        DobbelsteenModel dobbelsteenModel = new DobbelsteenModel(TEST_VALUE);
        dobbelsteenModel.verhoog();

        assertEquals(TEST_VALUE+1, dobbelsteenModel.getWaarde());
        dobbelsteenModel.verhoog();

        assertEquals(TEST_VALUE+2, dobbelsteenModel.getWaarde());
        dobbelsteenModel.verhoog();
        assertEquals(TEST_VALUE+3, dobbelsteenModel.getWaarde());

    }

    @Test
    public void textVerhoogRollover() throws Exception {
        DobbelsteenModel dobbelsteenModel = new DobbelsteenModel(6);

        dobbelsteenModel.verhoog();

        assertEquals(1, dobbelsteenModel.getWaarde());
    }

    @Test
    public void testVerlaag() throws Exception {
        DobbelsteenModel dobbelsteenModel = new DobbelsteenModel(2);
        dobbelsteenModel.verlaag();
        assertEquals(1, dobbelsteenModel.getWaarde());
    }

    @Test
    public void testVerlaagRollover() throws Exception {
        DobbelsteenModel dobbelsteenModel = new DobbelsteenModel(1);
        dobbelsteenModel.verlaag();
        assertEquals(6, dobbelsteenModel.getWaarde());
    }

    @Test
    public void testWaardeParameterConstructor() throws Exception {
        DobbelsteenModel dobbelsteenModel = new DobbelsteenModel(TEST_VALUE);
        assertEquals(TEST_VALUE, dobbelsteenModel.getWaarde());
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        DobbelsteenModel dobbelsteenModel = new DobbelsteenModel();
        assertNotNull(dobbelsteenModel);
        assertTrue(dobbelsteenModel.getWaarde() > 0);
    }

    @Test
    public void testGooi() throws Exception {
        DobbelsteenModel dobbelsteenModel = new DobbelsteenModel();
        int before = dobbelsteenModel.getWaarde();
        int tries = 13337; // Very unlikely this test wont pass if the gooi method works as excepted
        boolean passed = false;
        for (int i = 0; i < tries; i++) {
            dobbelsteenModel.gooi();
            if(before != dobbelsteenModel.getWaarde()){
                passed = true;
                break;
            }
        }
        assertTrue(passed);
    }

}