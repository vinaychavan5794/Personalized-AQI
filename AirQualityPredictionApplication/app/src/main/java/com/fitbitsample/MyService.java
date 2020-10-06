package com.fitbitsample;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MyService extends Service implements LocationListener{
    private LocationManager locManager;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    double lat_old = 0.0;
    double lon_old = 0.0;
    double lat_new;
    double lon_new;
    double userSpeed = 0.0;
    String longitude;
    String latitude;
    double userDirection;
    double windSpeed;
    double windDirection;
    double pm2_5;
    double pm10;
    double co;
    double no2;
    double so2;
    double o3;
    double hbAi;
    String activityRec;
    String activityPerformed;
    Double aqiHi;
    Double aqiLo;
    Double bpHi;
    Double bpLo;
    int hbI=0;
    double intermediateResult;
    String age="";
    String gender="";
    String userID="";
    double result;
    double result1;
    Handler uiHandler=new Handler();
    public static String LOCAL_BROADCAST_NAME = "LOCAL_ACT_RECOGNITION";
    public static String LOCAL_BROADCAST_EXTRA = "RESULT";
    AirQualityIndex airQualityIndex ;
    PM pm;
    HashMap<String,Double> aqiMap;

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public int onStartCommand(final Intent intent, int flags, int startId) {
        airQualityIndex=new AirQualityIndex();
        pm=new PM();
        aqiMap=new HashMap<>();

        if(gender.equals("")&&age.equals("")){
            age=intent.getStringExtra("AGE");
            hbI=Integer.parseInt(intent.getStringExtra("HR"));
            userID=intent.getStringExtra("USERID");

            if(intent.getStringExtra("GENDER").equals("MALE")){
                gender="M";

            } else {
                gender="F";

            }

        }

        activityRecognition(intent);
        Runnable r = new Runnable() {
            public void run() {
                location();
                if(latitude!=null){
                    new JSONWeatherTask().execute(new String[]{latitude+"&lon="+longitude,latitude+"&longitude="+longitude});

                }
                uiHandler.postDelayed(this,5000);
            }
        };

        /*Runnable is posted after every 5 secs to calculate user's speed and direction,
        * windData speed and direction, pm2.5 and predicted heart rate */

        uiHandler.postDelayed(r,5000);
        return START_STICKY;
    }

    public void activityRecognition(Intent intent){
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );
        }
    }

    /*This function calculates personalised AQI using the AQI formula*/
    public HashMap<String,Double> calculateAQI(AirQualityIndex airQualityIndex,PM pm){
        for (int i=0;i<airQualityIndex.getPollutantList().size();i++){
            switch (airQualityIndex.getPollutantList().get(i).getPollutant()) {
                case "PM2.5":
                    aqiMap.put(airQualityIndex.getPollutantList().get(i).getPollutant()+"\nConc : "+Math.round(pm.getPm25())+" μg/m3", calculatePM25AQI(pm));
                    break;
                case "PM10":
                    aqiMap.put(airQualityIndex.getPollutantList().get(i).getPollutant()+"\nConc : "+Math.round(pm.getPm10())+" μg/m3", calculatePM10AQI(pm));
                    break;
                case "CO":
                    aqiMap.put(airQualityIndex.getPollutantList().get(i).getPollutant()+"\nConc : "+Math.round(pm.getCo())+" ppm", calculateCOAQI(pm));
                    break;
                case "NO2":
                    aqiMap.put(airQualityIndex.getPollutantList().get(i).getPollutant()+"\nConc : "+Math.round(pm.getNo2())+" ppb", calculateNO2AQI(pm));
                    break;
                case "SO2":
                    aqiMap.put(airQualityIndex.getPollutantList().get(i).getPollutant()+"\nConc : "+Math.round(pm.getSo2())+" ppb", calculateSO2AQI(pm));
                    break;
                case "O3":
                    aqiMap.put(airQualityIndex.getPollutantList().get(i).getPollutant()+"\nConc : "+Math.round(pm.getO3())+" ppm", calculateO3AQI(pm));
                    break;
            }
        }

        return aqiMap;
    }

    public Double calculatePM25AQI(PM pm){
        pm2_5=pm.getPm25();
        if(pm2_5>0 && pm2_5<=12){
            bpHi=12.0;
            bpLo=0.0;
            aqiHi=50.0;
            aqiLo=0.0;
        } else if(pm2_5>=12.1 && pm2_5<=35.4){
            bpHi=35.4;
            bpLo=12.1;
            aqiHi=100.0;
            aqiLo=51.0;
        }else if(pm2_5>=35.5 && pm2_5<=55.4){
            bpHi=55.4;
            bpLo=35.5;
            aqiHi=150.0;
            aqiLo=101.0;
        }else if(pm2_5>=55.5 && pm2_5<=150.4){
            bpHi=150.4;
            bpLo=55.5;
            aqiHi=200.0;
            aqiLo=151.0;
        }else if(pm2_5>=150.5 && pm2_5<=250.4){
            bpHi=250.4;
            bpLo=150.5;
            aqiHi=300.0;
            aqiLo=201.0;
        }else if(pm2_5>=250.5 && pm2_5<=350.4){
            bpHi=350.4;
            bpLo=250.5;
            aqiHi=400.0;
            aqiLo=301.0;
        }else {
            bpHi=500.4;
            bpLo=350.5;
            aqiHi=500.0;
            aqiLo=401.0;
        }
        intermediateResult=hbAi/hbI;
        intermediateResult=intermediateResult*pm2_5;
        intermediateResult=intermediateResult-bpLo;
        result=aqiHi-aqiLo;
        result1=bpHi-bpLo;
        result=result/result1;
        result=result*intermediateResult;
        result=result+ aqiLo;
        return result;
    }

    public Double calculatePM10AQI(PM pm){
        pm10=pm.getPm10();
        if(pm10>0 && pm10<=54.0){
            bpHi=54.0;
            bpLo=0.0;
            aqiHi=50.0;
            aqiLo=0.0;
        } else if(pm10>=55.0 && pm10<=154.0){
            bpHi=154.0;
            bpLo=55.0;
            aqiHi=100.0;
            aqiLo=51.0;
        }else if(pm10>=155.0 && pm10<=254.0){
            bpHi=254.0;
            bpLo=155.0;
            aqiHi=150.0;
            aqiLo=101.0;
        }else if(pm10>=255.0 && pm10<=354.0){
            bpHi=354.0;
            bpLo=255.0;
            aqiHi=200.0;
            aqiLo=151.0;
        }else if(pm10>=355.0 && pm10<=424.0){
            bpHi=424.0;
            bpLo=355.0;
            aqiHi=300.0;
            aqiLo=201.0;
        }else if(pm10>=425.0 && pm10<=504.0){
            bpHi=504.0;
            bpLo=425.0;
            aqiHi=400.0;
            aqiLo=301.0;
        }else {
            bpHi=604.0;
            bpLo=505.0;
            aqiHi=500.0;
            aqiLo=401.0;
        }
        intermediateResult=hbAi/hbI;
        intermediateResult=intermediateResult*pm10;
        intermediateResult=intermediateResult-bpLo;
        result=aqiHi-aqiLo;
        result1=bpHi-bpLo;
        result=result/result1;
        result=result*intermediateResult;
        result=result+ aqiLo;
        return result;
    }

    public Double calculateNO2AQI(PM pm){
        no2=pm.getNo2()/1.88;
        if(no2>0 && no2<=53.0){
            bpHi=53.0;
            bpLo=0.0;
            aqiHi=50.0;
            aqiLo=0.0;
        } else if(no2>=54.0 && no2<=100.0){
            bpHi=100.0;
            bpLo=54.0;
            aqiHi=100.0;
            aqiLo=51.0;
        }else if(no2>=101.0 && no2<=360.0){
            bpHi=360.0;
            bpLo=101.0;
            aqiHi=150.0;
            aqiLo=101.0;
        }else if(no2>=361.0 && no2<=649.0){
            bpHi=649.0;
            bpLo=361.0;
            aqiHi=200.0;
            aqiLo=151.0;
        }else if(no2>=650.0 && no2<=1249.0){
            bpHi=1249.0;
            bpLo=650.0;
            aqiHi=300.0;
            aqiLo=201.0;
        }else if(no2>=1250.0 && no2<=1649.0){
            bpHi=1649.0;
            bpLo=1250.0;
            aqiHi=400.0;
            aqiLo=301.0;
        }else {
            bpHi=2049.0;
            bpLo=1650.0;
            aqiHi=500.0;
            aqiLo=401.0;
        }
        intermediateResult=hbAi/hbI;
        intermediateResult=intermediateResult*no2;
        intermediateResult=intermediateResult-bpLo;
        result=aqiHi-aqiLo;
        result1=bpHi-bpLo;
        result=result/result1;
        result=result*intermediateResult;
        result=result+ aqiLo;
        return result;
    }

    public Double calculateSO2AQI(PM pm){
        so2=pm.getSo2()/2.62;
        if(so2>0 && so2<=35.0){
            bpHi=35.0;
            bpLo=0.0;
            aqiHi=50.0;
            aqiLo=0.0;
        } else if(so2>=36.0 && so2<=75.0){
            bpHi=75.0;
            bpLo=36.0;
            aqiHi=100.0;
            aqiLo=51.0;
        }else if(so2>=76.0 && so2<=185.0){
            bpHi=185.0;
            bpLo=76.0;
            aqiHi=150.0;
            aqiLo=101.0;
        }else if(so2>=186.0 && so2<=304.0){
            bpHi=304.0;
            bpLo=186.0;
            aqiHi=200.0;
            aqiLo=151.0;
        }else if(so2>=305.0 && so2<=604.0){
            bpHi=604.0;
            bpLo=305.0;
            aqiHi=300.0;
            aqiLo=201.0;
        }else if(so2>=605.0 && so2<=804.0){
            bpHi=804.0;
            bpLo=605.0;
            aqiHi=400.0;
            aqiLo=301.0;
        }else {
            bpHi=1004.0;
            bpLo=805.0;
            aqiHi=500.0;
            aqiLo=401.0;
        }
        intermediateResult=hbAi/hbI;
        intermediateResult=intermediateResult*so2;
        intermediateResult=intermediateResult-bpLo;
        result=aqiHi-aqiLo;
        result1=bpHi-bpLo;
        result=result/result1;
        result=result*intermediateResult;
        result=result+ aqiLo;
        return result;
    }

    public Double calculateCOAQI(PM pm){
        co=pm.getCo()/1.145;
        co=co*0.001;
        if(co>0 && co<=4.4){
            bpHi=4.4;
            bpLo=0.0;
            aqiHi=50.0;
            aqiLo=0.0;
        } else if(co>=4.5 && co<=9.4){
            bpHi=9.4;
            bpLo=4.5;
            aqiHi=100.0;
            aqiLo=51.0;
        }else if(co>=9.5 && co<=12.4){
            bpHi=12.4;
            bpLo=9.5;
            aqiHi=150.0;
            aqiLo=101.0;
        }else if(co>=12.5 && co<=15.4){
            bpHi=15.4;
            bpLo=12.5;
            aqiHi=200.0;
            aqiLo=151.0;
        }else if(co>=15.5 && co<=30.4){
            bpHi=30.4;
            bpLo=15.5;
            aqiHi=300.0;
            aqiLo=201.0;
        }else if(co>=30.5 && co<=40.4){
            bpHi=40.4;
            bpLo=30.5;
            aqiHi=400.0;
            aqiLo=301.0;
        }else {
            bpHi=40.5;
            bpLo=50.4;
            aqiHi=500.0;
            aqiLo=401.0;
        }
        intermediateResult=hbAi/hbI;
        intermediateResult=intermediateResult*co;
        intermediateResult=intermediateResult-bpLo;
        result=aqiHi-aqiLo;
        result1=bpHi-bpLo;
        result=result/result1;
        result=result*intermediateResult;
        result=result+ aqiLo;
        return result;
    }

    public Double calculateO3AQI(PM pm){
        o3=pm.getO3()/2.00;
        o3=o3*0.001;
        if(o3>0 && o3<=0.054){
            bpHi=0.054;
            bpLo=0.0;
            aqiHi=50.0;
            aqiLo=0.0;
        } else if(o3>=0.055 && o3<=0.070){
            bpHi=0.070;
            bpLo=0.055;
            aqiHi=100.0;
            aqiLo=51.0;
        }else if(o3>=0.071 && o3<=0.085){
            bpHi=0.085;
            bpLo=0.071;
            aqiHi=150.0;
            aqiLo=101.0;
        }else if(o3>=0.086 && o3<=0.105){
            bpHi=0.105;
            bpLo=0.086;
            aqiHi=200.0;
            aqiLo=151.0;
        }else if(o3>=0.106 && o3<=0.200){
            bpHi=0.200;
            bpLo=0.106;
            aqiHi=300.0;
            aqiLo=201.0;
        }else if(o3>=0.205 && o3<=0.504){
            bpHi=0.504;
            bpLo=0.205;
            aqiHi=400.0;
            aqiLo=301.0;
        }else {
            bpHi=0.604;
            bpLo=0.505;
            aqiHi=500.0;
            aqiLo=401.0;
        }
        intermediateResult=hbAi/hbI;
        intermediateResult=intermediateResult*o3;
        intermediateResult=intermediateResult-bpLo;
        result=aqiHi-aqiLo;
        result1=bpHi-bpLo;
        result=result/result1;
        result=result*intermediateResult;
        result=result+ aqiLo;
        return result;
    }

    /*This function sets the activity recognised value*/

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {


        for(DetectedActivity activity : probableActivities) {
            switch( activity.getType() ) {

                /*case DetectedActivity.IN_VEHICLE: {
                    activityRec="IV";
                    break;
                }

                case DetectedActivity.ON_FOOT: {
                    activityRec="OF";
                    break;
                }

                case DetectedActivity.TILTING: {
                    activityRec="T";
                    break;
                }*/

                case DetectedActivity.STILL:{
                    activityRec="S";
                    activityPerformed="STILL";
                    break;
                }

                case DetectedActivity.ON_BICYCLE: {
                    activityRec="C";
                    activityPerformed="CYCLING";
                    break;
                }

                case DetectedActivity.RUNNING: {
                    activityRec="R";
                    activityPerformed="RUNNING";
                    break;
                }
                case DetectedActivity.WALKING: {
                    activityRec="W";
                    activityPerformed="WALKING";
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    activityRec="W";
                    activityPerformed="WALKING";
                    break;
                }

            }

        }


    }

    /*This function checks if the location has been changed and
    * if the location is changed it invokes corresponding onLocationChanged Listener*/

    public void location() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            assert locManager != null;
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (network_enabled) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
        if(gps_enabled){
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        if(location!=null){
            locManager.removeUpdates(this);
            lat_new=location.getLongitude();
            lon_new =location.getLatitude();
            if(Objects.requireNonNull(locManager.getProvider(LocationManager.GPS_PROVIDER)).supportsBearing()){
                userDirection = Math.round(location.getBearing());
            }

            longitude = Double.toString(location.getLongitude());
            latitude = Double.toString(location.getLatitude());

            userSpeed=Math.round(location.getSpeed()*2.237);

            lat_old=lat_new;
            lon_old=lon_new;
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }



    /*Inner Class which extends AsyncTask in order to perform computaion in background
    * without blocking the UI thread */

    private class JSONWeatherTask extends AsyncTask<String[], Void, HashMap<String,Double>> {

        @Override
        protected HashMap<String, Double> doInBackground(String[]... params) {


            WeatherData weather = new WeatherData();

            String weatherData = ( (new WeatherClient()).getWeatherInformation(params[0][0]));
            String aqiData= ( (new WeatherClient()).getAQI(params[0][1]));
            String pmData = ( (new WeatherClient()).getPMData(params[0][0]));


            try {

                weather = JSONParser.getWeather(weatherData);
                airQualityIndex=JSONParser.getPollutant(aqiData);
                pm = JSONParser.getPM(pmData);
                windSpeed= weather.windData.getSpeed();
                windSpeed=Math.round(windSpeed*2.237);
                windDirection=Math.round(weather.windData.getDeg());
                //pm2_5=pm.getPm25();
                String hrData = ( (new WeatherClient()).getHR(
                 age+"&gender="+gender+"&activity="+activityRec+"&ihr="+hbI+"&user_speed="
                        +(int)userSpeed+"&user_direction="+(int)userDirection+"&wind_speed="+(int)windSpeed+
                         "&wind_direction="+(int)windDirection+"&username="+userID+"&date_recorded="
                         +LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"&time_recorded="+
                         new SimpleDateFormat("HH:mm").format(new Date())
                ));

                hbAi= Double.parseDouble(hrData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return calculateAQI(airQualityIndex,pm);

        }


        /* Calculated personalised AQI along with other information is
        broadcasted to the MainActivity where it is displayed as output*/
        @Override
        protected void onPostExecute(HashMap<String,Double> input) {
            super.onPostExecute(input);
            Intent intent = new Intent(LOCAL_BROADCAST_NAME);
            intent.putExtra(LOCAL_BROADCAST_EXTRA, input);
            intent.putExtra("WIND_SPEED", windSpeed);
            intent.putExtra("WIND_DIRECTION", windDirection);
            intent.putExtra("USER_SPEED", userSpeed);
            intent.putExtra("USER_DIRECTION", userDirection);
            //intent.putExtra("PM", pm2_5);
            intent.putExtra("HR",hbAi);
            intent.putExtra("ACTIVITY",activityPerformed);
            intent.putExtra("LAT",latitude);
            intent.putExtra("Lon",longitude);
            intent.putExtra("airQualityIndex",airQualityIndex);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        }

    }



}

