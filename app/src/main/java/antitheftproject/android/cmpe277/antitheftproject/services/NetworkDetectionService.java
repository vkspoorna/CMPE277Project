package antitheftproject.android.cmpe277.antitheftproject.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import antitheftproject.android.cmpe277.antitheftproject.model.NetworkPojo;

public class NetworkDetectionService {
    public static NetworkPojo getAvailableNetwork(Context context) {
        NetworkPojo networkPojo = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            networkPojo = new NetworkPojo();
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                networkPojo.setNetworkId(-1);
                networkPojo.setWifiNetwork(false);
                networkPojo.setNetworkName(activeNetworkInfo.getSubtypeName());
                networkPojo.setNetworkState(activeNetworkInfo.getState().name());
                return networkPojo;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null && wifiInfo.getNetworkId() != -1) {
                    networkPojo.setWifiNetwork(true);
                    networkPojo.setNetworkName(activeNetworkInfo.getTypeName());
                    networkPojo.setNetworkState(activeNetworkInfo.getState().name());
                    networkPojo.setNetworkId(wifiInfo.getNetworkId());
                    return networkPojo;
                }
            }
        }
        return null;
    }
}
