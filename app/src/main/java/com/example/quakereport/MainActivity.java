package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG=MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

             ArrayList<Earthquake1> earthquake2 = QueryUtils.extractEarthquakes();
        ListView EarthquakelistView=(ListView) findViewById(R.id.list);
       final EarthquakeAdapter Adapter=new EarthquakeAdapter(this,earthquake2);
        EarthquakelistView.setAdapter(Adapter);
        EarthquakelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView , View view , int i , long l) {
                Earthquake1 currentEarthqwake=Adapter.getItem(i);
                Uri earthUri=Uri.parse(currentEarthqwake.getURL());
                Intent websiteIntent=new Intent(Intent.ACTION_VIEW,earthUri);
                startActivity(websiteIntent);
            }
        });

    }
}
