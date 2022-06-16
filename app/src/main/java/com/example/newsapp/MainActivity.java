package com.example.newsapp;

import android.content.Intent;
import android.database.Cursor;
//import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    EditText Login, Password;
    Button SignIn,Registration,btnScan;
    DatabaseHelper databaseHelper;
    BiometricPrompt biometricPrompt;
    Executor executor;
    BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnTouch);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor,new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString){
                super.onAuthenticationError(errorCode,errString);
                Log.e("ErrAuth",errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result){
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Успешно!", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(MainActivity.this, AdminPage.class);
                mainIntent.putExtra("user", "2");
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }

            @Override
            public void onAuthenticationFailed(){
                super.onAuthenticationFailed();
                Log.e("FailedAUTH","Fail");
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация в новостное приложение")
                .setSubtitle("Прислоните палец")
                .setNegativeButtonText("Отмена")
                .build();

        btnScan.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });

        Login = findViewById(R.id.et_email);
        Password = findViewById(R.id.et_password);
        SignIn = findViewById(R.id.sign_in);
        Registration = findViewById(R.id.registration);
        databaseHelper = new DatabaseHelper(this);
        SignIn.setOnClickListener(view -> {
            int roleid = 0;
            Cursor role = databaseHelper.checkLoginPassword(Login.getText().toString(),Password.getText().toString());
            //Login.setText(role.getString(0).toString());
            if(role!=null) {
                while (role.moveToNext()) {
                    roleid = role.getInt(0);
                }
            }
            if (roleid == 1) {
                Intent mainIntent = new Intent(MainActivity.this, AdminPage.class);
                mainIntent.putExtra("user", "1");
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
            else if(roleid == 2) {
                Intent mainIntent = new Intent(MainActivity.this, AdminPage.class);
                mainIntent.putExtra("user", "2");
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
            else
                Toast.makeText(getApplicationContext(), "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
        });

        Registration.setOnClickListener(view -> {
            Intent mainIntent = new Intent(this, RegistrationPage.class);
            startActivity(mainIntent);
            /*this.finish();*/
        });
    }
}