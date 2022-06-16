package com.example.newsapp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class RegistrationPage extends AppCompatActivity{
    EditText FIO,Email, Password,Password2,adm;
    Button SignIn,Registration;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_registration);
        FIO = findViewById(R.id.et_FIO);
        Email = findViewById(R.id.et_email);
        Password = findViewById(R.id.et_password);
        Password2 = findViewById(R.id.et_password2);
        SignIn = findViewById(R.id.sign_in);
        Registration = findViewById(R.id.registration);
        adm=findViewById(R.id.et_admin);
        databaseHelper = new DatabaseHelper(this);
        SignIn.setOnClickListener(view -> {
            /*Intent mainIntent = new Intent(RegistrationPage.this, MainActivity.class);
            RegistrationPage.this.startActivity(mainIntent);*/
            RegistrationPage.this.finish();
        });

        Registration.setOnClickListener(view -> {
            boolean checkInsertData;
            if(Password.getText().toString().equals("") || Password2.getText().toString().equals("")||FIO.getText().toString().equals("")||Email.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "Все поля должны быть заполнены!", Toast.LENGTH_LONG).show();
            else {
                if (!Password.getText().toString().equals(Password2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show();
                    return;
                }
                if (adm.getText().toString().equals("qwerty"))
                    checkInsertData = databaseHelper.insert(FIO.getText().toString(), Email.getText().toString(), Password.getText().toString(), 1);
                else
                    checkInsertData = databaseHelper.insert(FIO.getText().toString(), Email.getText().toString(), Password.getText().toString(), 2);
                if (checkInsertData == true) {
                    Toast.makeText(getApplicationContext(), "Успешная регистрация", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(RegistrationPage.this, MainActivity.class);
                    startActivity(mainIntent);
                    RegistrationPage.this.finish();
                } else if (checkInsertData == false) {
                    Toast.makeText(getApplicationContext(), "Такая почта уже зарегистрирована", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
