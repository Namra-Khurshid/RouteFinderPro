package com.example.user.routefinderpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button my_location = (Button) findViewById(R.id.button1);
        Button route_finder = (Button) findViewById(R.id.button2);
        Button nearby_places = (Button) findViewById(R.id.button3);
        Button compass = (Button) findViewById(R.id.button4);

        startService(new Intent(this, LocationService.class));

        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(MainActivity.this, MyLocationActivity.class);
                startActivity(i);
            }
        });

        route_finder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, RouteFinder.class);
                startActivity(i);
            }
        });

        nearby_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, NearbyPlaces.class);
                startActivity(i);
            }
        });

        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Compass.class);
                startActivity(i);
            }
        });

        startService(new Intent(this, LocationService.class));

    }
}
