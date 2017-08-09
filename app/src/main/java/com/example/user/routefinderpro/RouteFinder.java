package com.example.user.routefinderpro;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;

public class RouteFinder extends AppCompatActivity  {
     String from, to;
     Double Latitude, Longitude, Latitudeto, LongitudeTo;
     LatLng fromPostion, toPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_finder);
        Button routefinder = (Button) findViewById(R.id.button5);
        routefinder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (from != null && to != null) {
                        Intent i = new Intent(RouteFinder.this, DisplayRoute.class);
                        i.putExtra("latitudefrom", Latitude);
                        i.putExtra("longitudefrom", Longitude);
                        i.putExtra("latitudeto", Latitudeto);
                        i.putExtra("longitudeto", LongitudeTo);
                        startActivity(i);
                    } else
                        Toast.makeText(RouteFinder.this, "Enter Location", Toast.LENGTH_SHORT).show();
                }
            });
            PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();
            autocompleteFragment.setFilter(typeFilter);

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    from = String.valueOf(place.getName());
                    Log.i("Place", "From: " + place.getName());//get place details here
                    try {
                        getLocationFromAddress(from);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i("Error", "An error occurred: " + status);
                }
            });

            PlaceAutocompleteFragment autocompleteFragment1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);

            autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    to = String.valueOf(place.getName());
                    Log.i("Place", "To: " + place.getName());//get place details here
                    try {
                        getLocationToAddress(to);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i("Error", "An error occurred: " + status);
                }
            });
        }



    public Barcode.GeoPoint getLocationFromAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Barcode.GeoPoint p1 = null;

            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new Barcode.GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));
              Latitude = location.getLatitude();
             Longitude = location.getLongitude();
             fromPostion = new LatLng(Latitude,Longitude);
        System.out.println("Position"+location.getLatitude()+","+location.getLongitude());

            return p1;

    }

    public Barcode.GeoPoint getLocationToAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Barcode.GeoPoint p1 = null;

        address = coder.getFromLocationName(strAddress,5);
        if (address==null) {
            return null;
        }
        Address location=address.get(0);
        location.getLatitude();
        location.getLongitude();

        p1 = new Barcode.GeoPoint((double) (location.getLatitude() * 1E6),
                (double) (location.getLongitude() * 1E6));
        Latitudeto = location.getLatitude();
        LongitudeTo = location.getLongitude();
        toPosition = new LatLng(Latitudeto,LongitudeTo);
        System.out.println("Position"+location.getLatitude()+","+location.getLongitude());

        return p1;

    }
}
