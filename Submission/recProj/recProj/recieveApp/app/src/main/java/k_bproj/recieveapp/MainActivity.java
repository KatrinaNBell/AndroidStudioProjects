package k_bproj.recieveapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button check;
    TextView receivP;
    Button clear;
    int bright;
    String concat;
    private ScreenRet sr = new ScreenRet(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        check = (Button)findViewById(R.id.checkButton);
        receivP = (TextView)findViewById(R.id.dataReceiv2);
        clear = (Button)findViewById(R.id.clearButton);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
            System.out.println("Permission granted");
        } else {
            System.out.println("Currently have permission");
        }


        check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                runMonitor();
            }} );



        clear.setOnClickListener(new View.OnClickListener() {
            int num = 0;
            @Override
            public void onClick(View view){
                receivP = (TextView)findViewById(R.id.dataReceiv2);
                receivP.setText(" ");
                runMonitor();
            }
        } );
        runMonitor();

       // BroadcastReceiver br = new MyBroadcastReceiver();
       // Intent intent = new Intent();
       //intent.setAction("broadcast.screen.channel");
       // intent.putExtra("data","Notice me senpai!");
       // sendBroadcast(intent);


        //check.setOnClickListener(new View.OnClickListener(){
          //  @Override
            //public void onClick(View view){
                //measure screen brightness
                //
              //  toTime();
            //}
        //}
        //);

    }

    public int sameCount = -1;
    public boolean finalCounter = true;
    public int sameCheck = 10;

    public void runMonitor() {
        int monitor = 0;

        monitor = sr.getBrightness();


        while (true) {
            if (monitor == sr.getBrightness()) {
                // Do nothing
            } else {
                //toDisplay(sr.getBrightness());
                toTime();
                break;
            }
//           receivP.setText(" ");
        }
    }

    /**
     * ToTime method runs through every second to check whether the previous letter is the same as
     * last, once it's reached 5 it stop transfering data
     */
    public void toTime(){
        final Timer t = new Timer();
        receivP = (TextView)findViewById(R.id.dataReceiv2);
        t.scheduleAtFixedRate(new TimerTask() {
            int i = -1;


            @Override
            public void run() {
                if (i == -1) {
                        sameCheck = sr.getBrightness();
                    System.out.println("samecheck is " + sameCheck);
                    i++;
                }

                if (finalCounter == false) {
                    t.cancel();
                } else{
                    try{
                        bright = sr.getBrightness();
                        System.out.println("bright = " + bright);
                        if (bright == sameCheck && sameCount < 4) {
                            sameCount += 1;
                        } else if (sameCount >= 3) {
                            finalCounter = false;
                        } else {
                            sameCount = 0;
                            try{
                                sameCheck = sr.getBrightness();
                            } catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(sameCount);
                    toDisplay(bright);
                }
            }
        }, 0, 1000);

    }

    public Context getContext() {
        return this;
    }

    public void toDisplay(int b){
        char temp =  (char)b;
        concat = concat + temp;
        receivP.setText("" + concat);

    }

    public void removeExtra(byte[] b) {
    //      b.size() = sizeof(b[]) - 4;
    }
}
