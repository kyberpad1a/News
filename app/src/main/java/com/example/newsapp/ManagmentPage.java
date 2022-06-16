package com.example.newsapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ManagmentPage extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText title, description;
    TextView datenews;
    Button btnInsert,btnUpdate,btnDelete,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_managmentnews);
        title = findViewById(R.id.Title);
        datenews = findViewById(R.id.DateNews);
        description=findViewById(R.id.Description);
        btnInsert = findViewById(R.id.Insertbtn);
        btnUpdate = findViewById(R.id.Updatebtn);
        btnDelete = findViewById(R.id.Deletebtn);
        btnBack = findViewById(R.id.btnBack);
        databaseHelper = new DatabaseHelper(this);

        Bundle arguments = getIntent().getExtras();
        if(arguments.get("add").toString().equals("1"))
        {
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnInsert.setVisibility(View.VISIBLE);
            datenews.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime()));
        }
        else if(arguments.get("add").toString().equals("0"))
        {
            title.setText(arguments.get("title").toString());
            datenews.setText(arguments.get("date").toString());
            description.setText(arguments.get("description").toString());
        }
        else{
            title.setText(arguments.get("title").toString());
            datenews.setText(arguments.get("date").toString());
            description.setText(arguments.get("description").toString());
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnInsert.setVisibility(View.GONE);

        }

        btnInsert.setOnClickListener(view -> {
            if(title.getText().toString().equals("") || datenews.getText().toString().equals("") || description.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_LONG).show();
                return;
            }
            Boolean checkInsertData = databaseHelper.insertNews(title.getText().toString(), datenews.getText().toString(), description.getText().toString());
            if (checkInsertData) {
                Intent mainIntent = new Intent(ManagmentPage.this, AdminPage.class);
                mainIntent.putExtra("user", "1");
                ManagmentPage.this.startActivity(mainIntent);
                ManagmentPage.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });


        btnUpdate.setOnClickListener(view -> {
            if(title.getText().toString().equals("") || datenews.getText().toString().equals("") || description.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_LONG).show();
                return;
            }
            Boolean checkUpdateData = databaseHelper.UpdateNews(arguments.get("id").toString(),title.getText().toString(), datenews.getText().toString(), description.getText().toString());
            if (checkUpdateData) {
                Intent mainIntent = new Intent(ManagmentPage.this, AdminPage.class);
                mainIntent.putExtra("user", "1");
                ManagmentPage.this.startActivity(mainIntent);
                ManagmentPage.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });

        btnDelete.setOnClickListener(view -> {
            Boolean checkDeleteData = databaseHelper.DeleteNews(arguments.get("id").toString());
            if (checkDeleteData) {
                Intent mainIntent = new Intent(ManagmentPage.this, AdminPage.class);
                mainIntent.putExtra("user", "1");
                ManagmentPage.this.startActivity(mainIntent);
                ManagmentPage.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });

        btnBack.setOnClickListener(view -> {
            if(arguments.get("add").toString().equals("1") || arguments.get("add").toString().equals("0")) {
                Intent mainIntent = new Intent(ManagmentPage.this, AdminPage.class);
                mainIntent.putExtra("user", "1");
                ManagmentPage.this.startActivity(mainIntent);
                ManagmentPage.this.finish();
            }
            else {
                Intent mainIntent = new Intent(ManagmentPage.this, AdminPage.class);
                mainIntent.putExtra("user", "2");
                ManagmentPage.this.startActivity(mainIntent);
                ManagmentPage.this.finish();
            }
        });

    }
}
