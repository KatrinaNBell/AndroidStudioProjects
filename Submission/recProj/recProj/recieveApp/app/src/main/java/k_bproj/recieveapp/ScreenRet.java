package k_bproj.recieveapp;


import android.content.Context;
import android.provider.Settings;

public class ScreenRet {
    MainActivity mA;
    Context context;

    public ScreenRet(Context c) {
        this.context = c;
    }

    public int getBrightness() {
        int oldBrightness = 0;
        try {
            oldBrightness = Settings.System.getInt(context.getApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e){
            e.printStackTrace();
        }
        return oldBrightness;
    }
}
