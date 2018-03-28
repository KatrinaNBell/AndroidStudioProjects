package k_bproj.recieveapp;

import android.content.Context;
import android.provider.Settings;

import org.junit.Before;
import org.junit.Test;

import k_bproj.recieveapp.ScreenRet;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * ScreenChan unit test, which will execute on the development machine (host).
 *
 */
public class ScreenUnitTest {
    private ScreenRet sr;
    private MainActivity mA = new MainActivity();
    Context context = mA.getContext();

    @Before
    public void setUp() throws Exception {
        sr = new ScreenRet(context);

    }

    @Test //TEST 1
    public void testCheckNotNeg() throws NullPointerException {
        assertTrue("TEST1: check brightness > -1",sr.getBrightness()>-1 );
    }

    @Test //TEST 2
    public void testReturnBright() throws Settings.SettingNotFoundException {
        int x = Settings.System.getInt(context.getApplicationContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS);

        assertEquals("TEST2: RETURN BRIGHTNESS", x, sr.getBrightness());
    }


}