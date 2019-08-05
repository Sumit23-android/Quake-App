package com.example.quakereport;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.quakereport.MainActivity.LOG_TAG;


public class QueryUtils1 {




    /** Sample JSON response for a USGS query */

    private QueryUtils1() {
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }





    private static ArrayList<Earthquake1> extractFeatureFromJson(String earthquakeJSON)  {
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }


        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake1> earthquakes = new ArrayList<>();

        // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and

        try {
            JSONObject baseJasonResponse=new JSONObject(earthquakeJSON);
            JSONArray earthquakeArray=baseJasonResponse.getJSONArray("features");
            for(int i=0;i<earthquakeArray.length();i++){
                JSONObject currentEarthquake=earthquakeArray.getJSONObject(i);
                JSONObject properties=currentEarthquake.getJSONObject("properties");
                Double mag=properties.getDouble("mag");
                String place=properties.getString("place");
                long time=properties.getLong("time");
                String URL=properties.getString("url");
                Earthquake1 earthquake=new Earthquake1(mag,place,time,URL);
                earthquakes.add(earthquake);
            }



        } catch (JSONException e) {

            Log.e("QueryUtils1", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }
    public static List<Earthquake1> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Earthquake1> earthquakes = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }


}

