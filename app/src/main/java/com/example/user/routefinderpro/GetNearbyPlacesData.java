package com.example.user.routefinderpro;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

/**
 * Created by navneet on 23/7/16.
 */
public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    String place;

    public GetNearbyPlacesData(String places){
        place = places;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParserNearby dataParser = new DataParserNearby();
        nearbyPlacesList =  dataParser.parse(result);
        ShowNearbyPlaces(nearbyPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute","Entered into showing locations");
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat, lng);
            if (place=="restaurant"){
            mMap.addMarker(new MarkerOptions().position(latLng).title(placeName + " : " + vicinity)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant_black_24dp)));
            }
            else if (place=="school"){
                mMap.addMarker(new MarkerOptions().position(latLng).title(placeName + " : " + vicinity)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_school_black_24dp)));
            }
            else if (place=="hospital"){
                mMap.addMarker(new MarkerOptions().position(latLng).title(placeName + " : " + vicinity)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_local_hospital_black_24dp)));
            }
            else if (place=="bank"){
                mMap.addMarker(new MarkerOptions().position(latLng).title(placeName + " : " + vicinity)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account_balance_black_24dp)));
            }
            else if (place=="atm"){
                mMap.addMarker(new MarkerOptions().position(latLng).title(placeName + " : " + vicinity)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_local_atm_black_24dp)));
            }
            else if (place=="airport"){
                mMap.addMarker(new MarkerOptions().position(latLng).title(placeName + " : " + vicinity)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_local_airport_black_24dp)));
            }
            else if (place=="mosque"){
                mMap.addMarker(new MarkerOptions().position(latLng).title(placeName + " : " + vicinity)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_store_mall_directory_black_24dp)));
            }
            else {
                mMap.addMarker(new MarkerOptions().position(latLng).title(placeName + " : " + vicinity)
                        .icon(BitmapDescriptorFactory.defaultMarker()));
            }
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }
    }
}