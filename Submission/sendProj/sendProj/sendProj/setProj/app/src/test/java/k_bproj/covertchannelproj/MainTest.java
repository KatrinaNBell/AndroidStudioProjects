package k_bproj.covertchannelproj;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;


public class MainTest {
    private MainActivity mC;

    @Before
    public void setUp() throws Exception {
        mC = new MainActivity();
    }

    @Test
    public void testEncode() throws Exception {
        assertEquals("TEST1: aserting to find equvelent", "[B<B@606d8acf", mC.toEncode("Hello"));
    }
}
