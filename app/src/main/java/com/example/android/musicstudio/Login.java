package com.example.android.musicstudio;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Login extends AppCompatActivity {

    SharedPreferences sharedPref;

    Button loginButton;
    ProgressBar loginProgress;
    EditText login_username;
    EditText login_password;
    TextView login_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        loginButton = (Button) findViewById(R.id.loginButton);
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        loginProgress = (ProgressBar) findViewById(R.id.loginProgress);
        loginProgress.setVisibility(View.GONE);
        login_register = (TextView) findViewById(R.id.login_register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setVisibility(View.INVISIBLE);
                loginProgress.setVisibility(View.VISIBLE);
                verifikasiLogin(login_username.getText().toString(), login_password.getText().toString());
            }
        });

        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
    }

    private void verifikasiLogin(String username, String password) {
        String url = "http://cloudofoasis.com/api/Ivan/login.php?username=" + username + "&password=" + password;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.startsWith("s")) {
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean("login", true);
                            editor.putString("id", response);
                            editor.commit();
                            Toast.makeText(Login.this, "Login Successfull",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else if (response.startsWith("g")) {
                            Toast.makeText(Login.this, "Username dan Password tidak cocok",
                                    Toast.LENGTH_SHORT).show();
                            loginButton.setVisibility(View.VISIBLE);
                            loginProgress.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(Login.this, "Something wrong, Please try again",
                                    Toast.LENGTH_SHORT).show();
                            loginButton.setVisibility(View.VISIBLE);
                            login_register.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DEBUG_", "Error :" + error.toString());
                Toast.makeText(Login.this, "Something went wrong, Please try again",
                        Toast.LENGTH_SHORT).show();
                loginButton.setVisibility(View.VISIBLE);
                login_register.setVisibility(View.INVISIBLE);
            }

        });
        queue.add(stringRequest);
    }
}