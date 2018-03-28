package k_bproj.covertchannelproj;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import java.util.Timer;
import java.util.TimerTask;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {
    Button sendButton;
    EditText editSendtxt;
    byte[] encode;
    String letters;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_SETTINGS = 0;


    ScreenChan sChan = new ScreenChan(this);

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = (Button)findViewById(R.id.buttonSending);
        editSendtxt = (EditText)findViewById(R.id.textSend);

        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:k_bproj.covertchannelproj"));
            startActivity(intent);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_SETTINGS)) {
                System.out.println(" ");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, MY_PERMISSIONS_REQUEST_WRITE_SETTINGS);
            }
        }
        sChan.setScreenMode(0);
        if(sChan.checkScreenMode()==1) {
            System.out.println("the mode is on automatic");
        } else {
            System.out.println("the mode is on manual");
        }



        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.v("textSend", editSendtxt.getText().toString());
                String new1 = editSendtxt.getText().toString();
                System.out.println("entered data is: " + new1);
                byte[] asciiEncode = toEncode(new1);

                String check = toDecode(asciiEncode);

                Log.d("", "decode = " + check);
                toTime(asciiEncode);
            }
        }

        );
    }


    public Context getContext() {
        return this;
    }

    /**
     * Encoding the string
     * @param s
     * @return
     */
    public byte[] toEncode(String s) {
        encode = s.getBytes(StandardCharsets.US_ASCII);
        return encode;
    }

    /**
     * Decoding the byte array
     * @param enc
     * @return
     */
    public String toDecode(byte[] enc){
        letters = new String(enc);
        return letters;
    }

    /**
     * changing the volume
     * @param input
     */
    public void toSend(int input){
        final boolean op = true;
        final int i = input;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{

                        int bright = sChan.getBrightness();
                        System.out.println("Current Bright = " + bright);
                        System.out.println("input = " + i);

                        WindowManager.LayoutParams layout = getWindow().getAttributes();
                        layout.screenBrightness = (float) i / 255;
                        getWindow().setAttributes(layout);

                        Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, i);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
    }

    /**
     * creating timer to run the sending process
     * @param input
     */
    public void toTime(final byte[] input){
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                i += 1;
                if (i >= input.length) {
                    t.cancel();
                    System.out.println("Fine");
                } else {
                    toSend(input[i]);
                }
            }
        }, 0, 1010);

        toSend(input[0]);
    }
}
