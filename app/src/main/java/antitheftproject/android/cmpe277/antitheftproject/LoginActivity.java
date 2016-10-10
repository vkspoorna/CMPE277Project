package antitheftproject.android.cmpe277.antitheftproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import antitheftproject.android.cmpe277.antitheftproject.constant.Constant;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Log";
    private final String loginUrl = Constant.url + "/users/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
    }

    public void logIn(View v) {
        EditText userNameET = (EditText) findViewById(R.id.userNameET);
        EditText userPwdET = (EditText) findViewById(R.id.userPwdET);

        String userName = userNameET.getText().toString();
        String userPwd = userPwdET.getText().toString();

        if (userName.equals("") || userPwd.equals("")) {
            Toast.makeText(this, "Field required", Toast.LENGTH_LONG).show();
            return;
        } else {
            JSONObject json = new JSONObject();
            try {
                json.put("userName", userName);
                json.put("password", userPwd);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (json.length() > 0) {
                new AuthenticateAsync().execute(String.valueOf(json));
            }
        }
    }

    public void register(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private class AuthenticateAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            String JsonResponse = null;
            String JsonDATA = params[0];
            URL url;

            try {

                url = new URL(loginUrl);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
                System.out.println(JsonDATA);
                writer.flush();
                writer.close();

                InputStream inputStream = conn.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                JsonResponse = buffer.toString();
                System.out.println(JsonResponse);

                //response data
                Log.i(TAG, JsonResponse);
                //send to post execute
                return JsonResponse;


            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return JsonResponse;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                JSONObject parentObject = new JSONObject(response);
                int errorCode = parentObject.getInt("errorCode");
                System.out.println(errorCode);
                if (errorCode == 0) {
                    Intent intentService = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentService);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username/ password, please try again", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
