package com.fitbitsample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.fitbitsample.AirQualityIndex;
import com.fitbitsample.FitbitHR;
import com.fitbitsample.FitbitUser;
import com.fitbitsample.JSONParser;
import com.fitbitsample.MyService;
import com.fitbitsample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fitbitsample.WeatherClient;
import com.fitbitsample.constant.PrefConstants;
import com.fitbitsample.fragment.LoginFragment;
import com.fitbitsample.preference.AppPreference;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.json.JSONException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class AQP extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,SensorEventListener{

    public GoogleApiClient apiClient;
    private static final int PERMISSION_REQUEST_CODE=10;
    BroadcastReceiver myReceiver;
    int TIMER = 5000;
    TextView aqiPersonalized;
    TextView aqi;
    TextView io;
    TextView activity;
    TextView pHR;
    TextView hr;
    TextView gender;
    TextView age;
    Button startButton;
    Button syncButton;
    Button stopButton;
    Intent intent;
    Intent fitbitIntent;
    String userID="";
    public static Context c;
    DateTimeFormatter FULL_ISO_DATE_FORMAT = DateTimeFormatter. ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    LocalDateTime localDateTime;
    LocalDateTime lastSyncTime;

    public double light_val, mag_val,acc_val;
    SensorManager sensorManager;
    private final int CODE_PERMISSIONS = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_q_p);
        aqiPersonalized=findViewById(R.id.airIndex);
        aqi=findViewById(R.id.airIndex2);
        io=findViewById(R.id.io);
        activity=findViewById(R.id.activity);
        pHR=findViewById(R.id.pHR);
        hr=findViewById(R.id.HR);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        startButton=findViewById(R.id.start);
        syncButton=findViewById(R.id.sync);
        stopButton=findViewById(R.id.stop);
        stopButton.setEnabled(false);
        aqiPersonalized.setMovementMethod(new ScrollingMovementMethod());
        aqi.setMovementMethod(new ScrollingMovementMethod());
        fitbitIntent=getIntent();
        gender.setText(fitbitIntent.getStringExtra("GENDER"));
        age.setText(fitbitIntent.getStringExtra("AGE"));
        hr.setText(fitbitIntent.getStringExtra("HR"));
        userID=fitbitIntent.getStringExtra("USERID");
        c = getApplicationContext();
        localDateTime = LocalDateTime.parse(LocalDateTime.now().format(FULL_ISO_DATE_FORMAT), FULL_ISO_DATE_FORMAT);
        lastSyncTime= LocalDateTime.parse(fitbitIntent.getStringExtra("LASTSYNC"), FULL_ISO_DATE_FORMAT);
        if(Duration.between(lastSyncTime, localDateTime).toMinutes() <= 15){
            syncButton.setEnabled(true);
        }else {
            syncButton.setEnabled(false);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                age.setEnabled(false);
                gender.setEnabled(false);

                //Check if the user has granted permission to access Location

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AQP.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_CODE);
                }
                else{
                    if(checkGooglePlayServicesAvailable(AQP.this)){
                        apiClient = new GoogleApiClient.Builder(AQP.this)
                                .addApi(ActivityRecognition.API)
                                .addConnectionCallbacks(AQP.this)
                                .addOnConnectionFailedListener(AQP.this)
                                .build();

                        apiClient.connect();
                    }

                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aqiPersonalized.setText("");
                aqi.setText("");
                aqiPersonalized.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                aqi.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                age.setEnabled(true);
                gender.setEnabled(true);
                stopService(intent);
            }
        });

        syncButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new FitbitAsyncTask().execute(AppPreference.getInstance().getString(PrefConstants.FULL_AUTHORIZATION));
            }
        });


        //Display output computed by the service on screen
        myReceiver = new BroadcastReceiver() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onReceive(Context context, Intent intent) {
                int res;
                String category="";
                aqiPersonalized.setText("");
                aqi.setText("");
                activity.setText("");
                pHR.setText("");
                pHR.setText(intent.getDoubleExtra("HR",0)+ " BPM");
                activity.append(intent.getStringExtra("ACTIVITY"));
                HashMap<String, Double> resultMap = (HashMap<String, Double>) intent.getSerializableExtra(MyService.LOCAL_BROADCAST_EXTRA);
                AirQualityIndex airQualityIndex = (AirQualityIndex) intent.getSerializableExtra("airQualityIndex");

                assert airQualityIndex != null;
                for(int i = 0; i<airQualityIndex.getPollutantList().size(); i++){
                    aqi.append("Pollutant: "+airQualityIndex.getPollutantList().get(i).getPollutant());
                    aqi.append("\n");
                    res=airQualityIndex.getPollutantList().get(i).getAqi();
                    Spannable word = new SpannableString("AQI: "+res);
                    if(res>0 && res<=50){
                        word.setSpan(new ForegroundColorSpan(Color.GREEN), 5, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else if(res>=51 && res<=100){
                        word.setSpan(new ForegroundColorSpan(Color.YELLOW), 5, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else if(res>=101 && res<=150){
                        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AQP.this,R.color.orange)), 5, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else if(res>=151 && res<=200){
                        word.setSpan(new ForegroundColorSpan(Color.RED), 5, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else if(res>=201 && res<=300){
                        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AQP.this,R.color.purple)), 5, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else if(res>=301 && res<=400){
                        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AQP.this,R.color.maroon)), 5, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else if(res>=401 && res<=500){
                        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AQP.this,R.color.maroon)), 5, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    aqi.append(word);
                    aqi.append("\n");
                    aqi.append("Category: "+airQualityIndex.getPollutantList().get(i).getCategory());
                    aqi.append("\n");
                    aqi.append("\n");

                }

                assert resultMap != null;
                for(Map.Entry<String,Double> entry:resultMap.entrySet()){
                    aqiPersonalized.append("Pollutant: "+entry.getKey());
                    aqiPersonalized.append("\n");
                    Spannable word = new SpannableString("Personalized AQI: "+(int) Math. round(entry.getValue()));
                     res=(int)Math.round(entry.getValue());
                    if(res>0 && res<=50){
                        word.setSpan(new ForegroundColorSpan(Color.GREEN), 18, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        category="Good";
                    } else if(res>=51 && res<=100){
                        word.setSpan(new ForegroundColorSpan(Color.YELLOW), 18, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        category="Moderate";
                    }else if(res>=101 && res<=150){
                        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AQP.this,R.color.orange)), 18, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        category="Unhealthy for Sensitive Groups";
                    }else if(res>=151 && res<=200){
                        word.setSpan(new ForegroundColorSpan(Color.RED), 18, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        category="Unhealthy";
                    }else if(res>=201 && res<=300){
                        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AQP.this,R.color.purple)), 18, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        category="Very Unhealthy";
                    }else if(res>=301 && res<=400){
                        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AQP.this,R.color.maroon)), 18, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        category="Hazardous";
                    }else if(res>=401 && res<=500){
                        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AQP.this,R.color.maroon)), 18, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        category="Hazardous";
                    }
                    aqiPersonalized.append(word);
                    aqiPersonalized.append("\n");
                    aqiPersonalized.append("Category : "+category);
                    aqiPersonalized.append("\n");
                    aqiPersonalized.append("\n");

                }

                /*aqiPersonalized.setText("Personalized AQI: "+Double.toString((int)Math.round(result)));
                aqiPersonalized.append("\n");
                aqiPersonalized.append("WindData Speed:  "+intent.getDoubleExtra("WIND_SPEED",0) +" mph");
                aqiPersonalized.append("\n");
                aqiPersonalized.append("WindData Direction:  "+intent.getDoubleExtra("WIND_DIRECTION",0)+"°");
                aqiPersonalized.append("\n");
                aqiPersonalized.append("User Speed:  "+intent.getDoubleExtra("USER_SPEED",0)+" mph");
                aqiPersonalized.append("\n");
                aqiPersonalized.append("User Direction:  "+intent.getDoubleExtra("USER_DIRECTION",0)+"°");
                aqiPersonalized.append("\n");
                aqiPersonalized.append("PM:  "+intent.getDoubleExtra("PM",0)+ " μg/m3");
                aqiPersonalized.append("\n");*/




            }
        };

        String[] neededPermissions = new String[]{
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, neededPermissions, CODE_PERMISSIONS);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);



    }


    /* Start MyService in background to calculate AQI,
     activity recognized, age and gender are passed inside Intent*/
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        intent = new Intent( this, MyService.class );
        intent.putExtra("GENDER",""+gender.getText());
        intent.putExtra("AGE",""+age.getText());
        intent.putExtra("HR",""+hr.getText());
        intent.putExtra("USERID",userID);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(apiClient, TIMER, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, new IntentFilter(MyService.LOCAL_BROADCAST_NAME));

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_GAME);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        if(intent!=null){
            stopService(intent);
        }
        /*if(!AppPreference.getInstance().getString(PrefConstants.FULL_AUTHORIZATION).equals("")){
            AppPreference.getInstance().putString(PrefConstants.FULL_AUTHORIZATION,"");
            AppPreference.getInstance().putBoolean(PrefConstants.IS_CODE_RECEIVED, false);
            AppPreference.getInstance().putBoolean(PrefConstants.HAVE_AUTHORIZATION, false);
        }*/

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;
        int sensorType = event.sensor.getType();
        //StringBuilder sb = null;

        switch (sensorType)
        {
            case Sensor.TYPE_MAGNETIC_FIELD:
                //sb = new StringBuilder();
                mag_val = Math.sqrt(values[0]*values[0]+values[1]*values[1]+values[2]*values[2]);
                break;

            case Sensor.TYPE_LIGHT:
                //sb = new StringBuilder();
                light_val = values[0];

                break;

            case Sensor.TYPE_ACCELEROMETER:
                //sb = new StringBuilder();
                acc_val = Math.sqrt(values[0]*values[0]+values[1]*values[1]+values[2]*values[2])/10;

                break;
        }

        //Log.i("Light"," "+light_val);

        if(light_val>900){
            //aqi.setText(" OUTDOOR"+"\n"+"Light: "+light_val+"\n"+"acc: "+acc_val+"\n"+"mag: "+mag_val);
            io.setText(" OUTDOOR");
        }
        else{
            if(light_val ==0){
                //aqi.setText(" OUTDOOR_NIGHT"+"\n"+"Light: "+light_val+"\n"+"acc: "+acc_val+"\n"+"mag: "+mag_val);
                io.setText(" INDOOR");
            }

            else if(light_val <600 ){
                //aqi.setText(" INDOOR"+"\n"+"Light: "+light_val+"\n"+"acc: "+acc_val+"\n"+"mag: "+mag_val);
                io.setText(" INDOOR");
            }

            /*else if (light_val > 0 && light_val < 200 && mag_val < 35){
                aqi.setText(" OUTDOOR"+"\n"+"Light: "+light_val+"\n"+"acc: "+acc_val+"\n"+"mag: "+mag_val);
            }*/
            /*
            if(mag_val>30){
                if(acc_val>1.50){
                    aqi.setText(" OUTDOOR_NIGHT"+"\n"+"Light: "+light_val+"\n"+"acc: "+acc_val+"\n"+"mag: "+mag_val);
                }
            }*/
            else{
                //aqi.setText(" OUTDOOR"+"\n"+"Light: "+light_val+"\n"+"acc: "+acc_val+"\n"+"mag: "+mag_val);
                io.setText(" OUTDOOR");
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    public boolean checkGooglePlayServicesAvailable(Activity activity) {
        int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(activity);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(activity, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        if(requestCode==10){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    //startService(new Intent(this,MyService.class));
                    if(checkGooglePlayServicesAvailable(AQP.this)){
                        apiClient = new GoogleApiClient.Builder(AQP.this)
                                .addApi(ActivityRecognition.API)
                                .addConnectionCallbacks(AQP.this)
                                .addOnConnectionFailedListener(AQP.this)
                                .build();

                        apiClient.connect();
                    }
                }
            }else{
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    class FitbitAsyncTask extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params)  {

            String hearRateData = ( (new WeatherClient()).getFinalHeartRate(params[0]));
            try {
                return ( (new WeatherClient()).sendFinalHeartRate(
                        JSONParser.getHeartRateWithUserID(new String[]{hearRateData,userID})
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(AQP.this, result,
                    Toast.LENGTH_LONG).show();
        }
    }




}

