package k_bproj.myanalysis;

import android.Manifest;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView tvFreq;
    TextView tvBright;
    ProgressBar pb;
    Button stopB;
    Button startB;
    Boolean clicked = false;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_SETTINGS = 0;
    public int[] freq;
    public int[] bright;
    public float resF = 0;
    public float resB = 0;
    public final Timer t = new Timer();
    private Analyse ana = new Analyse(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFreq = (TextView)findViewById(R.id.resultBox);
        tvBright = (TextView)findViewById(R.id.brightnessAv);
        pb = (ProgressBar)findViewById(R.id.progressBar);
        stopB = (Button)findViewById(R.id.stopButton);
        startB = (Button)findViewById(R.id.startBut);


        int maxValue = pb.getMax();
        int progressVal = pb.getProgress();
        pb.setMax(180);


        getPermissions();



        startB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                freq = new int[300];
                System.out.println("freq");

                bright = new int[300];
                System.out.println("bright");

                toTime();
                System.out.println("toTime");



            }
        });

        stopB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                t.cancel();
                pb.setProgress(0);
                final Timer t = new Timer();

            }
        });

    }

    public void toTime(){

        //int freqC = 0;

        t.scheduleAtFixedRate(new TimerTask() {
            //int freqC = 0;
            int Brightness = 0;
            int x = 0;
            @Override
            public void run() {
                if (x == 180) {
                    t.cancel();
                    calc();
                } else {
                    bright[x] = ana.getBrightness();

                    if (Brightness == ana.getBrightness()){
                        freq[x] = 0;
                    } else {
                        //freqC +=1;
                        freq[x] = 1;
                        Brightness = ana.getBrightness();
                    }
                    System.out.println(x);
                    x += 1;
                    pb.setProgress(x);
                }
            }
        }, 0, 1010);

    }

    public void calc(){
        int totalF = 0;
        int totalB = 0;

        for(int i = 0;i < freq.length; i++){
            totalF = totalF + freq[i];
            totalB = totalB + bright[i];
            System.out.println("totalF = " + totalF);
            System.out.println("totalB = " + totalB);
        }

        resF = totalF/9;
        resB = totalB/180;
        String temp1 = Float.toString(resF);
        String temp2 = Float.toString(resB);


       // tvFreq.setText(Integer.toString(resF), 0, 3);
        tvFreq.setText(temp1);

        System.out.println("resf = " + temp1);
        System.out.println("resb = " + temp2);

        tvBright.setText(temp2);
    }

    public void getPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
            System.out.println("Permission granted");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
            System.out.println("Currently have permission");
        }
    }


}
