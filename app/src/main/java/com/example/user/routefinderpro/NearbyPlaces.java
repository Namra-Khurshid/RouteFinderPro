package com.example.user.routefinderpro;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NearbyPlaces extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 10000;
    LocationService locationService;
    Marker mCurrLocationMarker;
    private Menu mainMenu;
    LatLng latLng = new LatLng(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("Route Finder");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.nearbyplaces);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusCheck();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nearbyplaces, menu);
        mainMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restaurants: {
                String Restaurant = "restaurant";
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude(), Restaurant);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(Restaurant);
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(NearbyPlaces.this, "Nearby Restaurants", Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.schools: {
                String School = "school";
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                String url = getUrl(latitude, longitude, School);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(School);
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(NearbyPlaces.this, "Nearby Schools", Toast.LENGTH_LONG).show();

            }
            break;
            case R.id.hospitals: {
                String Hospital = "hospital";
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude(), Hospital);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(Hospital);
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(NearbyPlaces.this, "Nearby Hospitals", Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.banks: {
                String Bank = "bank";
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude(), Bank);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(Bank);
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(NearbyPlaces.this, "Nearby Banks", Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.airport: {
                String Airport = "airport";
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude(), Airport);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(Airport);
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(NearbyPlaces.this, "Nearby Airports", Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.mosques: {
                String Mosque = "mosque";
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude(), Mosque);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(Mosque);
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(NearbyPlaces.this, "Nearby Mosques", Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.atm: {
                String ATM = "atm";
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                String url = getUrl(locationService.curentlocation.getLatitude(), locationService.curentlocation.getLongitude(), ATM);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(ATM);
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(NearbyPlaces.this, "Nearby ATM", Toast.LENGTH_LONG).show();
            }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //move map camera
        final Geocoder geocoder = new Geocoder(NearbyPlaces.this);
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

        System.out.println("NearbyPlaces" + latLng);
        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        Toast.makeText(NearbyPlaces.this,"Your Current Location", Toast.LENGTH_LONG).show();

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

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyCrr2B6PU3Al8bmIkpZ0WcUVgkD6pVasC4");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

}