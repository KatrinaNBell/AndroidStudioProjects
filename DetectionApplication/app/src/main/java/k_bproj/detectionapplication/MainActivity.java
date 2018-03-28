package k_bproj.detectionapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button analyseB;
    ProgressBar pb;
    TextView resSusp;
    TextView resExp;
    TextView resAcc;
    TextView resTime;
    SeekBar sk;
    Button blockB;
    Button stopB;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_SETTINGS = 0;
    public final Timer t = new Timer();
    public int[] freq;
    public int sliderVal = 0;
    public int suspicions = 0;
    public int exploits = 0;
    private DetectBright db = new DetectBright(this);


    public SharedPreferences sharedPref;

    //String sharedPrefFile = "k_bproj.detectionapplication";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        analyseB = (Button)findViewById(R.id.analyseBut);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        resSusp = (TextView) findViewById(R.id.resSusp);
        resExp = (TextView) findViewById(R.id.resExp);
        resAcc = (TextView) findViewById(R.id.resAcc);
        resTime = (TextView) findViewById(R.id.resTime);
        sk = (SeekBar) findViewById(R.id.seekBar);
        blockB = (Button)findViewById(R.id.blockButton);
        stopB = (Button)findViewById(R.id.stopB);

        resSusp.setText("0");
        resExp.setText("0");
        resAcc.setText("-%");


        sharedPref = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);

       // sharedPref = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        //PreferenceManager.getDefaultSharedPreferences().getString("MYLABEL", "defaultStringIfNothingFound");
        checkPermissions();

        String cookieName = sharedPref.getString("Date", "22:37:36 2018-03-18");
        resTime.setText(cookieName);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                //t1.setTextSize(progress);
                sliderVal = progress;
                //Toast.makeText(getApplicationContext(), String.valueOf(progress), Toast.LENGTH_LONG).show();

            }
        });

        analyseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    pb.setProgress(0);
                    freq = new int[21];

                    pb.setMax((sliderVal*60));

                    //Log.d("prog", Integer.toString(sk.getProgress()));
                    //pb.setMax(sk.getProgress() * 60);
                    //bright = new int[300];

                if (sliderVal == 0){
                    Toast.makeText(getApplicationContext(), "Please choose a minutes value greater than 0.", Toast.LENGTH_LONG).show();
                } else {
                    timer();


                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat dat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
                    String formattedDate = dat.format(cal.getTime());
                    resTime.setText(formattedDate);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(formattedDate, "Date");
                    editor.apply();
                }


                    //SharedPreferences.Editor editor = sharedPref.edit();
                    //editor.putInt(getString(R.string.saved_high_score_key), newHighScore);
                    //editor.commit();

                }


        });
        final Timer tim = new Timer();

        blockB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Random rand = new Random();

                final int len = rand.nextInt(120) + 60;
                System.out.println(len);
                tim.scheduleAtFixedRate(new TimerTask() {
                    int count = 0;

                    @Override
                    public void run() {
                        if(count != len){
                            final int i = rand.nextInt(255) + 0;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        WindowManager.LayoutParams layout = getWindow().getAttributes();
                                        layout.screenBrightness = (float) i;
                                        getWindow().setAttributes(layout);

                                        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, i);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            count += 1;
                            System.out.println(count);
                        } else {
                            tim.cancel();
                        }

                    }
                }, 0, 500);

            }
        });

        stopB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tim.cancel();
                final Timer tim = new Timer();
            }
        });


    }

    public void timer(){
            //int res = 0;
            //temp = j * temp;
            //might need to intialise the timer again
            final int totalTime = 60*sliderVal;
            t.scheduleAtFixedRate(new TimerTask() {
                int Brightness = 0;
                int x = 0;
                int y = 0;

                @Override
                public void run() {
                    if (x == totalTime) {
                        calcBound(freq);
                        t.cancel();
                    } else if (x % 20 == 0 && x != 0){
                        calcBound(freq);
                        freq = new int[21];
                        System.out.println(x);
                        x += 1;
                        y = 0;
                        pb.setProgress(x);
                    } else {
                        //bright[x] = getBrightness();
                        if (Brightness == db.getBrightness()){
                            freq[y] = 0;
                        } else {
                            freq[y] = 1;
                            Brightness = db.getBrightness();
                        }
                        System.out.println(x);
                        x += 1;
                        y += 1;
                        //pb.setProgress((temp*20) + x);
                        pb.setProgress(x);
                    }
                }
            }, 0, 1010);

    }

    public void calcBound(int[] x){
        int total = 0;
        //suspicions = 0;
        //exploits = 0;

        for(int i = 0;i < x.length; i++){
            total = total + x[i];
        }
        //total = (total / sliderVal) * 10;

        if (total < 8 && total >= 5){
            //increment suspicion
            suspicions = suspicions + 1;
        }

        if (total >= 8){
            //is it bigger than bound
            exploits = exploits + 1;
        }


        String s = Integer.toString(suspicions);
        System.out.println("suspicions are " + s);
        resSusp.setText(Integer.toString(suspicions));

        String s1 = Integer.toString(exploits);
        System.out.println("exploits are " + s1);
        resExp.setText(Integer.toString(exploits));

    }

    public void checkPermissions(){
        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:k_bproj.detectionapplication"));
            startActivity(intent);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_SETTINGS)) {
                System.out.println(" ");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, MY_PERMISSIONS_REQUEST_WRITE_SETTINGS);
            }
        }

        try{
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        } catch(Exception e){
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
            System.out.println("Permission granted");
        } else {
            System.out.println("Currently have permission");
        }

    }


}
