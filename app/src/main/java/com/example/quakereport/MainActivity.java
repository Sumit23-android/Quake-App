package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EarthquakeAdapter mAdapter;
    public static final String LOG_TAG=MainActivity.class.getName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView EarthquakelistView=(ListView) findViewById(R.id.list);
        mAdapter=new EarthquakeAdapter(this,new ArrayList<Earthquake1>());
        EarthquakelistView.setAdapter(mAdapter);
        EarthquakelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView , View view , int i , long l) {
                Earthquake1 currentEarthqwake=mAdapter.getItem(i);
                Uri earthUri=Uri.parse(currentEarthqwake.getURL());
                Intent websiteIntent=new Intent(Intent.ACTION_VIEW,earthUri);
                startActivity(websiteIntent);
            }
        });
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake1>> {

        @Override
        protected List<Earthquake1> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Earthquake1> result = QueryUtils1.fetchEarthquakeData(urls[0]);
            return result;
        }





        @Override
        protected void onPostExecute(List<Earthquake1> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }

}
