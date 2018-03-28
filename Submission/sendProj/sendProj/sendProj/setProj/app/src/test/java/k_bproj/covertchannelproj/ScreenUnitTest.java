package k_bproj.covertchannelproj;

import android.content.Context;
import android.provider.Settings;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import k_bproj.covertchannelproj.ScreenChan;

/**
 * ScreenChan unit test, which will execute on the development machine (host).
 *
 */
public class ScreenUnitTest {
    private ScreenChan sC;
    private MainActivity aC;
    Context context = aC.getContext();

    @Before
    public void setUp() throws Exception {
    sC = new ScreenChan(context);

    }

    @Test //TEST 1
    public void testCheckNotNeg() throws NullPointerException {
        assertTrue("TEST1: check brightness > -1",sC.getBrightness()>-1 );
    }

    @Test //TEST 2
    public void testReturnBright() throws Settings.SettingNotFoundException {
        int x = Settings.System.getInt(context.getApplicationContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS);
        assertEquals("TEST2: RETURN BRIGHTNESS", x, sC.getBrightness());
    }

    @Test //TEST 3
    public void testSetScreen() throws Settings.SettingNotFoundException{
        //if mode is automatic, it is 1, if it's manual is it 0
        sC.setScreenMode(1);
        assertEquals("TEST3",1, sC.checkScreenMode());


        //assertEquals(Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC), sC.setScreenMode(10));
    }




}