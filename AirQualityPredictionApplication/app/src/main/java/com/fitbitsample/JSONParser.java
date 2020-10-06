
package com.fitbitsample;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONParser {

	public static WeatherData getWeather(String data) throws JSONException  {
		WeatherData weatherData = new WeatherData();

		// create JSONObject from the data
		JSONObject jObj = new JSONObject(data);

		// Wind Data
		JSONObject wObj = getObject("wind", jObj);
		weatherData.windData.setSpeed(getFloat("speed", wObj));
		weatherData.windData.setDeg(getFloat("deg", wObj));
		return weatherData;
	}
	public static AirQualityIndex getPollutant(String data) throws JSONException  {
		AirQualityIndex airQualityIndex=new AirQualityIndex();

		JSONArray jsonarray = new JSONArray(data);
		for (int i = 0; i < jsonarray.length(); i++) {
			AirQualityIndex.Pollutant pollutant=new AirQualityIndex.Pollutant();
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			JSONObject categoryObj = getObject("Category", jsonobject);
			pollutant.setAqi(jsonobject.getInt("AQI"));
			pollutant.setPollutant(jsonobject.getString("ParameterName"));
			pollutant.setCategory(categoryObj.getString("Name"));
			airQualityIndex.getPollutantList().add(pollutant);
		}
		return airQualityIndex;
	}

    public static String getLastSync(String data) throws JSONException  {

        JSONArray jsonarray = new JSONArray(data);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            return jsonobject.getString("lastSyncTime");

        }
        return null;
    }

	public static PM getPM(String data) throws JSONException  {
		PM pm=new PM();

		// create JSONArray from the data
		JSONObject jObj = new JSONObject(data);
		JSONArray jsonarray =  (JSONArray) jObj.getJSONArray("data");
		// PM
		pm.setPm25(jsonarray.getJSONObject(0).getDouble("pm25"));
		pm.setPm10(jsonarray.getJSONObject(0).getDouble("pm10"));
		pm.setSo2(jsonarray.getJSONObject(0).getDouble("so2"));
		pm.setNo2(jsonarray.getJSONObject(0).getDouble("no2"));
		pm.setO3(jsonarray.getJSONObject(0).getDouble("o3"));
		pm.setCo(jsonarray.getJSONObject(0).getDouble("co"));


		return pm;
	}

	public static FitbitHR getHeartRate(String data) throws JSONException  {
		FitbitHR fitbitHR=new FitbitHR ();

		// create JSONArray from the data
		JSONObject jObj = new JSONObject(data);

		JSONObject activitiesHRObj = getObject("activities-heart-intraday", jObj);

		JSONArray jsonarray =  (JSONArray) activitiesHRObj.getJSONArray("dataset");
		// PM
		if(jsonarray.length()>0){
			fitbitHR.setHeartRate(jsonarray.getJSONObject(jsonarray.length()-1).getInt("value"));
		}



		return fitbitHR;
	}
	public static String getHeartRateWithUserID(String[] data) throws JSONException  {

		// create JSONArray from the data
		JSONObject jObj = new JSONObject(data[0]);
		jObj.put("username",data[1]);

		return jObj.toString();
	}



	public static FitbitUser getUserInfo(String data) throws JSONException  {
		FitbitUser fitbitUser=new FitbitUser ();

		// create JSONArray from the data
		JSONObject jObj = new JSONObject(data);
		JSONObject userObj = getObject("user", jObj);
		fitbitUser.setAge(userObj.getInt("age"));
		fitbitUser.setGender(userObj.getString("gender"));
		fitbitUser.setUserID(userObj.getString("encodedId"));

		return fitbitUser;
	}


	
	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		return jObj.getJSONObject(tagName);
	}

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}


	
}
