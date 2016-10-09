package antitheftproject.android.cmpe277.antitheftproject.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags, startId);
        Toast.makeText(getApplicationContext(), "Service Running", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkPojo availableNetwork = NetworkDetectionService.getAvailableNetwork(getApplicationContext());
                if (availableNetwork != null) {
                    Log.i("Service ", "running " +  availableNetwork.getNetworkId() + "," + availableNetwork.getNetworkName() + ", " +  availableNetwork.getNetworkState());
                    //stopSelf();
                    Intent networkIntent = new Intent(Constant.NETWORK_INTENT);
                    sendBroadcast(networkIntent);
                } else {
                    Log.i("No network", "activity");
                }
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(Constant.SERVICE_KILLED);
        //intent.putExtra("counter", i);
        sendBroadcast(intent);
    }
}
