package antitheftproject.android.cmpe277.antitheftproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasscodeActivity extends Activity {

    EditText containsEnteredPasscode;
    public static final String tag = "Broadcast Passcode";
    Button submitButton;
    String obtainPasscode, anotherCopyFromTextView, copyFromSharedPreference,returnedResult;
    String channel;
    SharedPreferences sharedPreferences;
    Intent broadcastIntent;

    @Override
    //create an activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passcode_activity_main);

        containsEnteredPasscode = (EditText) findViewById(R.id.enteredPasscode);
        submitButton = (Button) findViewById(R.id.afterEnteringPasscodeButton);
        submitButton.setOnClickListener(onClickListener);
    }

    //obtain passcode from user entered in edit text and store in shared preference file
    public void getPasscode() {
        obtainPasscode = containsEnteredPasscode.getText().toString();
        anotherCopyFromTextView = obtainPasscode;
        storePasscodeToSharedPreferenceFile(obtainPasscode);
    }

    //store passcode to shared preference file
    public void storePasscodeToSharedPreferenceFile(String passCode) {
        sharedPreferences = getSharedPreferences("PASSCODE_ACTIVITY", MODE_PRIVATE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Passcode", passCode);
        editor.commit();

    }

    //compare passcode retrieved from shared prefernce and user entered passcode
    public boolean verifyPasscodeOnButtonClick(final String verifyPassCode)
    {
        channel = (sharedPreferences.getString("Passcode", ""));
        if (channel.equals(verifyPassCode)) {
            Toast.makeText(getApplicationContext(), "matched", Toast.LENGTH_LONG).show();
            return true;

        } else {
            Toast.makeText(getApplicationContext(), "did not matched", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            getPasscode();
            switch (v.getId())
            {
                case R.id.afterEnteringPasscodeButton: boolean result = verifyPasscodeOnButtonClick(obtainPasscode);
                    if(result == true)
                    {
                        testSendBroadcast();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"cant shudown",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };


    //broadcast the shutdown intent to receiver
    private void testSendBroadcast()
    {
        //Print out what your running thread id is
        Utils.logThreadSignature(tag);

        //Create an intent with an action
     // Intent broadcastIntent = new Intent("android.intent.action.ACTION_BOOT_COMPLETED");
        broadcastIntent = new Intent("android.intent.action.ACTION_BOOT_COMPLETED");
        //Intent broadcastIntent = new Intent( "android.intent.action.QUICKBOOT_POWEROFF");
        String action = broadcastIntent.getAction();
        Log.d(tag,action);

      // Intent broadcastIntent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");

        broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //load up the intent with a message
        //you want to broadcast

       broadcastIntent.putExtra("passcode",obtainPasscode);

       //registerReceiver(myRe, broadcastIntent);


       // Intent i = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        broadcastIntent.putExtra("android.intent.extra.KEY_CONFIRM", false);
       // startActivity(broadcastIntent);

        //send out the broadcast
        //there may be multiple receivers receiving it
        this.sendBroadcast(broadcastIntent);

        //Log a message after sending the broadcast
        //This message should appear first in the log file
        //before the log messages from the broadcast
        //because they all run on the same thread
        Log.d(tag,"after send broadcast from main menu");
    }


}
