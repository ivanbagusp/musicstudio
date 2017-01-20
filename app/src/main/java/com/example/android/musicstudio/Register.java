package com.example.android.musicstudio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    Button registerBtn;
    EditText namaRegister, emailRegister, usernameRegister, passwordRegister;
    ProgressBar registerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        registerBtn = (Button) findViewById(R.id.registerBtn);
        emailRegister = (EditText) findViewById(R.id.emailRegister);
        passwordRegister = (EditText) findViewById(R.id.passwordRegister);
        usernameRegister = (EditText) findViewById(R.id.usernameRegister);
        namaRegister = (EditText) findViewById(R.id.namaRegister);
        registerProgress = (ProgressBar) findViewById(R.id.registerProgress);
        registerProgress.setVisibility(View.GONE);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBtn.setVisibility(View.INVISIBLE);
                registerProgress.setVisibility(View.VISIBLE);
                if (emailRegister.getText().toString().equals("")||
                        passwordRegister.getText().toString().equals("") ||
                        usernameRegister.getText().toString().equals("") ||
                        namaRegister.getText().toString().equals("")){
                    Toast.makeText(Register.this, "Please fill out your identity",
                            Toast.LENGTH_SHORT).show();
                    registerBtn.setVisibility(View.VISIBLE);
                    registerProgress.setVisibility(View.INVISIBLE);
                }
                else {
                    verifikasiRegister(
                            usernameRegister.getText().toString(),
                            passwordRegister.getText().toString(),
                            emailRegister.getText().toString(),
                            namaRegister.getText().toString());
                    Toast.makeText(Register.this, "Register Sucessfull",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifikasiRegister(final String usernameRegister,
                                    final String passwordRegister,
                                    final String emailRegister,
                                    final String namaRegister) {
        String url = "http://cloudofoasis.com/api/Ivan/sign_up.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("cek", response);
                        registerBtn.setVisibility(View.VISIBLE);
                        registerProgress.setVisibility(View.INVISIBLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("cek", error.toString());
                registerBtn.setVisibility(View.VISIBLE);
                registerProgress.setVisibility(View.INVISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> url_maps = new HashMap<String, String>();
                url_maps.put("nama", namaRegister);
                url_maps.put("email", emailRegister);
                url_maps.put("username", usernameRegister);
                url_maps.put("password", passwordRegister);

                return url_maps;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    //untuk tombol back di hp
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Are you sure want to leave this registration?")
                .setCancelable(false)
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Register.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    //untuk tombol back di pojok kiri atas
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                new AlertDialog.Builder(this)
                        .setTitle("Alert")
                        .setMessage("Are you sure want to leave this registration?")
                        .setCancelable(false)
                        .setIcon(R.drawable.icn_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Register.this.finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
