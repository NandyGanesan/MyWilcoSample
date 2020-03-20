package com.android.wilcoconnect.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.login.Login;
import com.android.wilcoconnect.model.login.TokenData;
import com.android.wilcoconnect.model.common.UserData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.wilcoconnect.app.MainApplication.MY_PREFS_NAME;

public class LoginActivity extends AppCompatActivity {

    /*
    * Initialize the XML element or views
    * */
    private EditText email, password;
    private Button login;
    private UserData user;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
         * Check whether the Token is Enable or not
         * */
        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        if(preferences!=null){
            MainApplication.token_data = preferences.getString("header","No name defined");
        }
        /*
         * Initialize the UI elements
         * */
        email = findViewById(R.id.tiet_email);
        password = findViewById(R.id.tiet_password);
        login = findViewById(R.id.btn_login);

        /*
        * Set the validation condition in email and password field
        * */
        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        email.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

        /*
         * Click to Submit Login Data
         * */
        login.setOnClickListener(v -> {
            if (email.getText().toString().equals("") || !(email.getText().toString().matches(emailPattern))) {
                email.setError("Enter the Valid Email");
            } else if (password.getText().toString().equals("")) {
                password.setError("Enter the Valid Password");
            } else {
                /*
                * To call the Api to get the Token Authorization
                * */
                Login data = new Login(email.getText().toString(), password.getText().toString(), "password");
                ApiManager.getInstance().getToken(data, new Callback<TokenData>() {
                    /*
                    * When the Api Success.
                    * */
                    @Override
                    public void onResponse(@NonNull Call<TokenData> call, @NonNull Response<TokenData> response) {
                        TokenData tokenData = response.body();
                        if (response.isSuccessful() && tokenData != null) {
                            /*
                            * When define the Header from the Api Result
                            * Header should be Assigned by the Shared Preference.
                            * */
                            String header = tokenData.getToken_type() + " " + tokenData.getAccess_token();
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
                            editor.putString("header", header);
                            editor.apply();

                            MainApplication.token_data = header;
                            Login login = new Login(email.getText().toString(), password.getText().toString());

                            /*
                            * Reset the Token For the New Login
                            * */
                            ApiManager.resetInstance();

                            /*
                            * Api call to get the User Data
                            * */
                            ApiManager.getInstance().createUser(login, new Callback<UserData>() {
                                /*
                                 * From the Api Success to get the Particular User Data.
                                 * */
                                @Override
                                public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                                    user = response.body();
                                    /*
                                    * Response Success then pass the Data through the Intent.
                                    * */
                                    if (response.isSuccessful() && user.getData().getEmail() != null) {
                                        Log.d(TAG, "Login Success");
                                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        Gson gson = new Gson();
                                        String s = gson.toJson(user);
                                        homeIntent.putExtra("key", s);
                                        startActivity(homeIntent);
                                        Toast.makeText(getApplicationContext(),
                                                "Successfully Logged in",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    /*
                                    * Response Result Failure.
                                    * */
                                    else{
                                        finish();
                                        Toast.makeText(LoginActivity.this, "Login Failure", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(LoginActivity.this,"Email or Password is Incorrect",Toast.LENGTH_LONG).show();
                                        password.setError("Email or Password is Incorrect");
                                        Log.d(TAG, "Login Failure");
                                    }
                                }

                                /*
                                 * When the api call is Failure..
                                 * */
                                @Override
                                public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                                    Toast.makeText(LoginActivity.this, "Login Failure", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(LoginActivity.this,"Email or Password is Incorrect",Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "createUser: " + t.getLocalizedMessage());
                                }
                            });
                        }
                    }
                    /*
                    * When the api call is Failure..
                    * */
                    @Override
                    public void onFailure(@NonNull Call<TokenData> call, @NonNull Throwable t) {
                        Toast.makeText(LoginActivity.this, "Login Failure", Toast.LENGTH_SHORT).show();
                        password.setError("Email or Password is Incorrect");
                        Toast.makeText(LoginActivity.this,"Email or Password is Incorrect",Toast.LENGTH_LONG).show();
                        Log.e(TAG, "createUser: " + t.getLocalizedMessage());
                    }
                });
            }
        });
    }
}
