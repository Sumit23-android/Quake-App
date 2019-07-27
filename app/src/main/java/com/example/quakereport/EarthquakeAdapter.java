package com.example.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter  extends ArrayAdapter<Earthquake1> {
    private String FormatDate(Date dateobject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd,yyyy");
        return dateFormat.format(dateobject);
    }

    private String FormatTime(Date dateobject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateobject);
    }
    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeformat=new DecimalFormat("0.0");
        return magnitudeformat.format(magnitude);

    }


private static final String LOCATION_SEPERATOR="of";


    public EarthquakeAdapter(Activity context , ArrayList<Earthquake1> earthquakes) {
        super(context , 0 , earthquakes);
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_earthquake , parent , false);
        }
        Earthquake1 currentword = getItem(position);
        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        String formattedMag=formatMagnitude(currentword.getmMagnitude());
        magnitude.setText(formattedMag);
        GradientDrawable magnitudeCircle=(GradientDrawable) magnitude.getBackground();
        int magnitudeColor=getMagnitudeColor(currentword.getmMagnitude());
        magnitudeCircle.setColor(magnitudeColor);
        String originalLocation = currentword.getLocation();


        String primaryLocation;
        String locationOffset;


              if (originalLocation.contains(LOCATION_SEPERATOR)) {


            String[] parts = originalLocation.split(LOCATION_SEPERATOR);
            // Location offset should be "5km N " + " of " --> "5km N of"
            locationOffset= parts[0] + LOCATION_SEPERATOR;
            // Primary location should be "Cairo, Egypt"
            primaryLocation = parts[1];
        } else {
            // Otherwise, there is no " of " text in the originalLocation string.
            // Hence, set the default location offset to say "Near the".
            locationOffset = getContext().getString(R.string.near_the);
            // The primary location will be the full location string "Pacific-Antarctic Ridge".
            primaryLocation = originalLocation;
        }




        TextView loactionView = (TextView) listItemView.findViewById(R.id.location_off);
        loactionView.setText(locationOffset);
        TextView location=(TextView) listItemView.findViewById(R.id.location);
        location.setText(primaryLocation);
        Date dateobject = new Date(currentword.getmMilliSeconds());
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String FormattedDate = FormatDate(dateobject);

        dateView.setText(FormattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        String FormattedTime = FormatTime(dateobject);
        timeView.setText(FormattedTime);
        return listItemView;

    }
    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor=(int)Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId=R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId=R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId=R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId=R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId=R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId=R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId=R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId=R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId=R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId=R.color.magnitude10plus;
                break;

        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }



}