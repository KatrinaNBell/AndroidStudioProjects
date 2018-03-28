package k_bproj.covertchannelproj;

import android.provider.Settings;

import android.content.Context;

public final class ScreenChan {
    MainActivity mA;
    Context context;

    public ScreenChan(Context c) {
        this.context = c;
    }

    public void setScreenMode(int i) {
        if (i==0){
            try{
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            try{
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public int checkScreenMode(){
        int mode = -1;
        try {
            mode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            return 1;
        } else {
            return 0;
        }
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
