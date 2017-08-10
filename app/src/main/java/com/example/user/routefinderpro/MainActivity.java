package com.example.user.routefinderpro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class MainActivity extends ActivityManagePermission {

    Button my_location, route_finder,nearby_places,compass, navigation ;
    boolean flag= false;
    Menu mainMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        toolbar.setTitle("Route Finder");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar);
        my_location = (Button) findViewById(R.id.button1);
        route_finder = (Button) findViewById(R.id.button2);
        nearby_places = (Button) findViewById(R.id.button3);
        compass = (Button) findViewById(R.id.button4);
        navigation = (Button) findViewById(R.id.button6);
        my_location.setVisibility(View.INVISIBLE);
        if (flag==true)
        {
            statusCheck();
        }

        String permissionAsk[] = {PermissionUtils.Manifest_ACCESS_FINE_LOCATION};
        askCompactPermissions(permissionAsk, new PermissionResult() {
            @Override
            public void permissionGranted() {
                flag = true;
                statusCheck();
                initiateview();
            }

            @Override
            public void permissionDenied() {
                Toast.makeText(MainActivity.this, "Application might not run without this permission", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void permissionForeverDenied() {
                SinglePermissionDialog();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mainMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rate:
            break;
            case R.id.share:
            break;
            case R.id.more:
            break;
            case R.id.feedback:
            break;
            case R.id.privacy:
            break;
            case R.id.itemViewexit:
                finish();
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keycode, KeyEvent e) {
        switch (keycode) {
            case android.view.KeyEvent.KEYCODE_MENU:
                if (mainMenu != null) {
                    mainMenu.performIdentifierAction(R.id.itemViewSubMenu, 0);
                }
        }

        return super.onKeyUp(keycode, e);
    }


    private void SinglePermissionDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.attention);
        builder.setMessage(R.string.message_single_perm);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openSettingsApp(MainActivity.this);
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }


    public void initiateview()
    {

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

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(i);
            }
        });

        startService(new Intent(this, LocationService.class));
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
        else initiateview();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        initiateview();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}