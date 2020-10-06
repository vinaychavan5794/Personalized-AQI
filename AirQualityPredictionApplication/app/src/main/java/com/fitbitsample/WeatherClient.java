package com.fitbitsample;


import android.util.Log;

import com.fitbitsample.util.DateUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

public class WeatherClient {

	//OpenWeatherMap api to fetch windData speed and direction based on latitude and longitude

	private static String OPENWEATHERMAP_URL = "https://api.openweathermap.org/data/2.5/weather?lat=";

	//Weatherbit api to fetch pm 2.5 concentration based on latitude and longitude

	private static String PM_URL = "https://api.weatherbit.io/v2.0/current/airquality?lat=";

	private static String FITBITHR_URL="https://api.fitbit.com/1/user/-/activities/heart/date/"+
			DateUtil.convertDateFormat(new Date())+"/1d.json";


	private static String FITBITUSER_URL="https://api.fitbit.com/1/user/-/profile.json";
    private static String FITBITLASTSYNC_URL="https://api.fitbit.com/1/user/-/devices.json";
	//

	// URL of API (which is deployed on AWS EC2) to fetch predicted Heart Rate of the user
    private static String HR_URL = "XXXXXXXXX";

	// URL of API (which is deployed on AWS EC2) to send examples that will get stored in MySQL DB (AWS RDS)
	private static String SEND_HR_URL = "XXXXXXX";
	private static String AQI_URL = "http://www.airnowapi.org/aq/observation/latLong/current/?format=application/json&latitude=";
    private static String openWeatherMapKey="XXXXXXXX";
	private static String weatherBitApiKey="XXXXXXXX";
	private static String airNowApiKey="XXXXXXXXX";


	//Method to get the wind speed and direction of the User's location using OpenWeatherMap  API

	public String getWeatherInformation(String location) {
		HttpURLConnection httpConnection = null ;
		InputStream is = null;

		try {
			httpConnection = (HttpURLConnection) ( new URL(OPENWEATHERMAP_URL + location+openWeatherMapKey)).openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			//Log.i("Request Openweather"," "+httpConnection);
			httpConnection.connect();
			
			// read the response
			StringBuffer buffer = new StringBuffer();
			is = httpConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while (  (line = br.readLine()) != null )
				buffer.append(line + "\r\n");
			
			is.close();
			httpConnection.disconnect();
			return buffer.toString();
	    }
		catch(Throwable throwable) {
			throwable.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable throwable) {}
			try { httpConnection.disconnect(); } catch(Throwable throwable) {}
		}

		return null;
				
	}

	//Method to get the AQI and Pollutant information of the User's location using AirNow API

	public String getAQI(String location) {
		HttpURLConnection httpConnection = null ;
		InputStream is = null;

		try {
			httpConnection = (HttpURLConnection) ( new URL(AQI_URL + location+airNowApiKey)).openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			//Log.i("Request AirNow"," "+httpConnection);
			httpConnection.connect();

			// read the response
			StringBuffer buffer = new StringBuffer();
			is = httpConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while (  (line = br.readLine()) != null )
				buffer.append(line + "\r\n");

			is.close();
			httpConnection.disconnect();
			return buffer.toString();
		}
		catch(Throwable throwable) {
			throwable.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable throwable) {}
			try { httpConnection.disconnect(); } catch(Throwable throwable) {}
		}

		return null;

	}

	//Method to get the Heart Rate of the Fitbit User

	public String getHeartRateInformation(String authorization) {
		HttpURLConnection httpConnection = null ;
		InputStream is = null;

		try {

			String response="";

			HttpURLConnection connection = (HttpURLConnection)( new URL(FITBITHR_URL)).openConnection();
			connection.setReadTimeout(10000);
			connection.setConnectTimeout(10000);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			connection.addRequestProperty("Authorization", authorization);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			int responceCode = connection.getResponseCode();

			if (responceCode == HttpURLConnection.HTTP_OK)
			{
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = br.readLine()) != null)
				{
					response = "";// String variable declared global
					response += line;
				}
				return response;
			}

		}
		catch(Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			try { is.close(); } catch(Throwable throwable) {}
			try { httpConnection.disconnect(); } catch(Throwable throwable) {}
		}

		return null;

	}

	//Method to get the Heart Rate of the Fitbit User

	public String getFinalHeartRate(String authorization) {
		HttpURLConnection httpConnection = null ;
		InputStream is = null;

		try {

			String response="";

			HttpURLConnection connection = (HttpURLConnection)( new URL(FITBITHR_URL)).openConnection();
			connection.setReadTimeout(10000);
			connection.setConnectTimeout(10000);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			connection.addRequestProperty("Authorization", authorization);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			int responceCode = connection.getResponseCode();

			if (responceCode == HttpURLConnection.HTTP_OK)
			{
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = br.readLine()) != null)
				{
					response = "";// String variable declared global
					response += line;
				}
				return response;
			}

		}
		catch(Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			try { is.close(); } catch(Throwable throwable) {}
			try { httpConnection.disconnect(); } catch(Throwable throwable) {}
		}

		return null;

	}

	//Method to Post the  heart rate of the user to the MySQL DB

	public String sendFinalHeartRate(String json) {

		HttpURLConnection httpConnection = null ;
		String response = null;
		try {

			httpConnection = (HttpURLConnection) ( new URL(SEND_HR_URL )).openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("Accept", "application/json");

			httpConnection.setDoOutput(true);
			OutputStream outStream = httpConnection.getOutputStream();
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
			outStreamWriter.write(json);
			outStreamWriter.flush();
			outStreamWriter.close();
			outStream.close();
			DataInputStream input = null;
			input = new DataInputStream (httpConnection.getInputStream());
			response = input.readLine();
			input.close ();
			return response;

		} catch (IOException e) {
			e.printStackTrace();
		}
	return null;
	}

	//Method to get the Fitbit user information

	public String getUserInformation(String authorization) {
		HttpURLConnection httpConnection = null ;
		InputStream is = null;

		try {
			httpConnection = (HttpURLConnection) ( new URL(FITBITUSER_URL)).openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.setUseCaches(false);
			httpConnection.addRequestProperty("Authorization", authorization);
			httpConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpConnection.connect();

			// read the response
			StringBuffer buffer = new StringBuffer();
			is = httpConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while (  (line = br.readLine()) != null )
				buffer.append(line + "\r\n");

			is.close();
			httpConnection.disconnect();
			return buffer.toString();
		}
		catch(Throwable throwable) {
			throwable.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable throwable) {}
			try { httpConnection.disconnect(); } catch(Throwable throwable) {}
		}

		return null;

	}

	//Method to get the last Sync date of the fitbit device

    public String getLastSyncInformation(String authorization) {
        HttpURLConnection httpConnection = null ;
        InputStream is = null;

        try {
            httpConnection = (HttpURLConnection) ( new URL(FITBITLASTSYNC_URL)).openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            httpConnection.addRequestProperty("Authorization", authorization);
            httpConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpConnection.connect();

            // read the response
            StringBuffer buffer = new StringBuffer();
            is = httpConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            httpConnection.disconnect();
            return buffer.toString();
        }
        catch(Throwable throwable) {
            throwable.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable throwable) {}
            try { httpConnection.disconnect(); } catch(Throwable throwable) {}
        }

        return null;

    }

	//Method to Predicted Heart Rate of the User from the API

    public String getHR(String input) {
        HttpURLConnection httpConnection = null ;
        InputStream is = null;

        try {
			httpConnection = (HttpURLConnection) ( new URL(HR_URL + input)).openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(false);
			//Log.i("Request PHR"," "+httpConnection);
			httpConnection.connect();


            // read the response
            StringBuffer buffer = new StringBuffer();
            is = httpConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line);

            is.close();
			httpConnection.disconnect();
			Log.i("Response"," "+buffer.toString());
            return buffer.toString();
        }
        catch(Throwable throwable) {
			throwable.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable throwable) {}
            try { httpConnection.disconnect(); } catch(Throwable throwable) {}
        }

        return null;

    }

    //Method to PM 2.5 Pollutant from the WeatherBit API

	public String getPMData(String location) {
		HttpURLConnection httpConnection = null ;
		InputStream is = null;

		try {
			httpConnection = (HttpURLConnection) ( new URL(PM_URL + location+weatherBitApiKey)).openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			//Log.i("Request WeatherBit"," "+httpConnection);
			httpConnection.connect();

			// read the response
			StringBuffer buffer = new StringBuffer();
			is = httpConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while (  (line = br.readLine()) != null )
				buffer.append(line + "\r\n");

			is.close();
			httpConnection.disconnect();
			return buffer.toString();
		}
		catch(Throwable throwable) {
			throwable.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable throwable) {}
			try { httpConnection.disconnect(); } catch(Throwable throwable) {}
		}

		return null;

	}

}
