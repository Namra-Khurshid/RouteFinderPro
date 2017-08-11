package com.example.user.routefinderpro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NavigationActivity extends AppCompatActivity {
    public static final String ARG_ITEM_ID = "product_list";
    private static String[] QUERY_TITLE;
    Button button1;
    LocationService locationService;
    EditText editText1;
    private double lat, lng;

    public void StartMapsQuery(String paramString) {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("google.navigation:q=" + paramString));
        localIntent.setPackage("com.google.android.apps.maps");
        startActivity(localIntent);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_navigation);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        toolbar.setTitle("Route Finder");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.nearbyplaces);
        setSupportActionBar(toolbar);

        try {
            getCurrentLocation();
        } catch (Exception e) {
        }

        this.editText1 = ((EditText) findViewById(R.id.editText1));
        this.button1 = ((Button) findViewById(R.id.button1));
        this.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramAnonymousView) {
                if (NavigationActivity.this.editText1.length() != 0) {
                    NavigationActivity.this.StartMapsQuery(NavigationActivity.this.editText1.getText().toString());
                }
                else
                Toast.makeText(NavigationActivity.this, "Please Enter Destination", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    protected void getCurrentLocation() throws Exception {
            lat = locationService.curentlocation.getLatitude();
            lng = locationService.curentlocation.getLongitude();
//            Toast.makeText(getApplicationContext(), "Your Location is:\nLat: " + lat + "\nLong: " + lng,
//                    Toast.LENGTH_LONG).show();
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }
}

