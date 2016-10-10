package antitheftproject.android.cmpe277.antitheftproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by neha on 10/8/2016.
 */
public class PerformShutdown extends BroadcastReceiver
{
    private static final String tag = "TestReceiver";

    @Override
    //receive shutdown intent from broadcast class
    public void onReceive(Context context, Intent intent)
    {

        Utils.logThreadSignature(tag);
        Log.d("TestReceiver", "intent=" + intent);
        String message = intent.getStringExtra("passcode");
      Toast.makeText(context,"Phone is shutting down now!",Toast.LENGTH_SHORT).show();

        if (intent.getAction().equals("android.intent.action.ACTION_BOOT_COMPLETED")) {
            Log.d("Power", "Shutdown Complete");
        }

        Log.d(tag, message);


    }
}
