package antitheftproject.android.cmpe277.antitheftproject.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import antitheftproject.android.cmpe277.antitheftproject.api.NetworkDetectionAPI;
import antitheftproject.android.cmpe277.antitheftproject.api.SharedPreferenceAPI;
import antitheftproject.android.cmpe277.antitheftproject.constant.Constant;
import antitheftproject.android.cmpe277.antitheftproject.model.NetworkPojo;

public class MyService extends Service {
    static private int i;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags, startId);
        Toast.makeText(getApplicationContext(), "Service Running", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean notifyUser = false;
                NetworkPojo availableNetwork = NetworkDetectionAPI.getAvailableNetwork(getApplicationContext());
                if (availableNetwork != null) {
                    //Intent networkIntent = new Intent(Constant.NETWORK_INTENT);
                    Set<String> networkIds = new HashSet<String>(Arrays.asList("networkId", "networkName", "networkState", "isWifiNetwork"));
                    Map<String, String> data = SharedPreferenceAPI.readSharePreferences(getApplicationContext(), Constant.NETWORK, networkIds);
                    Log.i("Network ", "available");
                    /*if (data.keySet().size() == 0) {

                    } else {
                        if (availableNetwork.isWifiNetwork() && !availableNetwork.getNetworkName().equals(data.get("networkName"))) {
                            Log.i("Strange", "network");
                            notifyUser = true;
                        }
                    }*/

                    //networkIntent.putExtra("requiredNotify", notifyUser);
                    //sendBroadcast(networkIntent);
                } else {
                    //intent.putExtra("NETWORK", false);
                    Log.i("No network", "activity");
                }
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Intent intent = new Intent(Constant.SERVICE_KILLED);
        //sendBroadcast(intent);
    }
}
