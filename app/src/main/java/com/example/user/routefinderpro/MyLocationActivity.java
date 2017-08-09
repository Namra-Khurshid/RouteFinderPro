package com.example.user.routefinderpro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationService locationService;
    private Menu mainMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        statusCheck();
        System.out.println("My Location Actvity"+locationService.curentlocation.getLatitude());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final Geocoder geocoder = new Geocoder(MyLocationActivity.this);
        List<Address> addressList = null;
        String address= null;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try{
        addressList=geocoder.getFromLocation(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude(),1);

            if (addressList != null && addressList.size() > 0) {
            String locality = addressList.get(0).getFeatureName() + ", " + addressList.get(0).getLocality() +", " + addressList.get(0).getAdminArea();
            String country = addressList.get(0).getCountryName();
            if (!locality.isEmpty() && !country.isEmpty()){
                address = locality + "  " + country;}
        }
        else address = "Address not found";


    } catch (IOException e) {
        e.printStackTrace();
    }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng sydney = new LatLng(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title(address).draggable(true));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude()), 14.0f));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                List<Address> addressList = null;
                String address= "Address: ";
                try{
                    addressList=geocoder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);

                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getFeatureName() + ", " + addressList.get(0).getLocality() +", " + addressList.get(0).getAdminArea();
                        String country = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() && !country.isEmpty()){
                            address = locality + "  " + country;}
                            marker.setTitle(address);
                    }
                    else {
                        address = "Address not found";
                        marker.setTitle(address);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        final AlertDialog alert = builder.create();
        alert.show();
    }

@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mylocation, menu);
        mainMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normalMap:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.hybridMap:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.satelliteMap:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.terrainMap:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
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
            case KeyEvent.KEYCODE_MENU:
                if (mainMenu != null) {
                    mainMenu.performIdentifierAction(R.id.itemViewSubMenu, 0);
                }
        }

        return super.onKeyUp(keycode, e);
    }


}



