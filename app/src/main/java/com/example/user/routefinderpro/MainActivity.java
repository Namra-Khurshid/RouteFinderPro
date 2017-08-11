package com.example.user.routefinderpro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class MainActivity extends ActivityManagePermission {

    Button my_location, route_finder,nearby_places,compass, navigation ;
    boolean flag= false;
    Menu mainMenu;
    private InterstitialAd mInterstitialAd;
    public static String mailSource = "toptrendinggames2016@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
        NativeAd();
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
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName()));
                startActivity(browserIntent);
            }
            break;
            case R.id.share:
            {
                String playStoreLink = "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
                String yourShareText = getApplicationContext().getResources().getString(R.string.share_app) + playStoreLink;
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share It");
                intent.putExtra(Intent.EXTRA_TEXT, yourShareText);
                startActivity(Intent.createChooser(intent, "Share App!"));
            }
            break;
            case R.id.more:
            {
                Uri uri = Uri.parse("https://play.google.com/store/apps/dev?id=4854990210404955584&hl=en");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            break;
            case R.id.feedback:
            {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailSource});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));

            }
            break;
            case R.id.privacy:
            {
                privacyPolicy dialogFragment = new privacyPolicy();
                android.app.FragmentManager fm = getFragmentManager();
                dialogFragment.show(fm, "");
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
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                            startActivity(i);
                        }
                    });
                } else {
                    Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                    startActivity(i);
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
//                Intent i = new Intent(MainActivity.this, NavigationActivity.class);
//                startActivity(i);
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
    public void NativeAd() {
        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
    }
}