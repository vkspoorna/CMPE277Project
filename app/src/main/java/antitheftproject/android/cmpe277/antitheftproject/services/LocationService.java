package antitheftproject.android.cmpe277.antitheftproject.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import antitheftproject.android.cmpe277.antitheftproject.constant.Constant;

public class LocationService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(getApplicationContext(), "Location service initialization", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent locServResponse = new Intent();
                Location currentLocation = getCurrentLocation();
                if (currentLocation != null) {
                    locServResponse.putExtra("longitude", currentLocation.getLongitude());
                    locServResponse.putExtra("latitude", currentLocation.getLatitude());
                    locServResponse.setAction(Constant.LOCATION_SERVICE);
                    Log.i(" Location Lat ", String.valueOf(currentLocation.getLatitude()));
                    Log.i(" Location Lon ", String.valueOf(currentLocation.getLongitude()));
                } else {
                    locServResponse.setAction(Constant.NO_LOCATION_SERVICE);
                    Log.i("Location return", "empty");
                }
                sendBroadcast(locServResponse);
                stopSelf();
            }
        }).start();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Location getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        } else {
            LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria mCriteria = new Criteria();
            Location bestLocation = null;
            Location myCurrentLocation = null;
            List<String> providers = mLocationManager.getAllProviders();
            for (String provider : providers) {
                myCurrentLocation = mLocationManager.getLastKnownLocation(provider);
                if (myCurrentLocation != null) {
                    if (bestLocation == null || (bestLocation != null && myCurrentLocation.getAccuracy() < bestLocation.getAccuracy())) {
                        bestLocation = myCurrentLocation;
                    }
                }
            }

            return bestLocation;
        }

    }
}
