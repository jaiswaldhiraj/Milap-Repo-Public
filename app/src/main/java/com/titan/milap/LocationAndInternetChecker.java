package com.titan.milap;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationAndInternetChecker {

    public static final int LOCATION_PERMISSION_CODE = 101;

    public static void check(Activity activity) {
        if (!hasLocationPermission(activity)) {
            requestLocationPermission(activity);
        } else {
            checkGPSAndInternet(activity);
        }
    }

    private static boolean hasLocationPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private static void requestLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_CODE);
    }

    public static void handlePermissionResult(Activity activity, int requestCode, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkGPSAndInternet(activity);
            } else {
                Toast.makeText(activity, "Location permission is required!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static void checkGPSAndInternet(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkConnected = isNetworkAvailable(activity);

        if (!gpsEnabled && !networkConnected) {
            showGPSDialog(activity);
            showInternetDialog(activity);
        }else {
            Toast.makeText(activity, "All good! GPS and Internet are ON", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                return capabilities != null && (
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                );
            }
        }
        return false;
    }


    private static void showGPSDialog(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Enable GPS")
                .setMessage("This app requires GPS to be enabled. Turn it on in settings.")
                .setCancelable(false)
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    activity.startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private static void showInternetDialog(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Enable Internet")
                .setMessage("Please enable mobile data or WiFi to continue.")
                .setCancelable(false)
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    activity.startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}

