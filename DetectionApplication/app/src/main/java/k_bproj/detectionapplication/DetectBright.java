package k_bproj.detectionapplication;

import android.content.Context;
import android.provider.Settings;

import java.util.Random;

public class DetectBright {
    MainActivity mA;
    Context context;

    public DetectBright(Context c) {
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
