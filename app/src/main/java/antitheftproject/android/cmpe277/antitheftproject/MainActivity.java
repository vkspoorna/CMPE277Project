package antitheftproject.android.cmpe277.antitheftproject;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import antitheftproject.android.cmpe277.antitheftproject.constant.Constant;
import antitheftproject.android.cmpe277.antitheftproject.services.MyService;

public class MainActivity extends AppCompatActivity {
    MyBroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.NETWORK_INTENT);
        broadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isMyServiceRunning(MyService.class)) {
            Toast.makeText(this, "My Service class is running", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "My Service class is not running", Toast.LENGTH_LONG).show();
        }
    }

    public void startService(View v) {
        Intent intentService = new Intent(MainActivity.this, MyService.class);
        startService(intentService);
    }

    public void stopService(View v) {
        Intent intentService = new Intent(getApplicationContext(), MyService.class);
        stopService(intentService);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.NETWORK_INTENT)) {
                Toast.makeText(context, "Network is detected", Toast.LENGTH_LONG).show();
            } else if (intent.getAction().equals(Constant.SERVICE_KILLED)) {
                context.startService(new Intent(context, MyService.class));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    // This method is use to check if MyService class is still running after Activity class gets killed
    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            } else {
                Log.i("Service running: ", service.service.getClassName());
            }
        }
        return false;
    }

}
