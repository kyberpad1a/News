package com.example.newsapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button btnAdd, btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_admin);
        databaseHelper = new DatabaseHelper(this);
        btnAdd = findViewById(R.id.btnAdd);
        btnExit = findViewById(R.id.btnExit);
        Bundle arguments = getIntent().getExtras();

        if(arguments.get("user").toString().equals("2"))
            btnAdd.setVisibility(View.GONE);

        Cursor res = databaseHelper.getdata();
        while (res.moveToNext()){
            Button myButton = new Button(this);
            myButton.setText(res.getString(1)+"\n"+res.getString(2));
            LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
            myButton.setOnClickListener(view -> {
                String[] arrSplit = myButton.getText().toString().split("\n");
                Cursor NewsData = databaseHelper.getNews(arrSplit[0],arrSplit[1]);
                String id="";
                String title="";
                String date = "";
                String description = "";
                while(NewsData.moveToNext()){
                    id = NewsData.getString(0);
                    title = NewsData.getString(1);
                    date = NewsData.getString(2);
                    description = NewsData.getString(3);
                }
                Intent mainIntent = new Intent(AdminPage.this, ManagmentPage.class);
                mainIntent.putExtra("id", id);
                mainIntent.putExtra("title", title);
                mainIntent.putExtra("date", date);
                mainIntent.putExtra("description", description);
                if(arguments.get("user").toString().equals("2"))
                    mainIntent.putExtra("add", "-1");
                else
                    mainIntent.putExtra("add", "0");
                AdminPage.this.startActivity(mainIntent);
                AdminPage.this.finish();
            });
        }
        btnAdd.setOnClickListener(view -> {
            Intent mainIntent = new Intent(AdminPage.this, ManagmentPage.class);
            mainIntent.putExtra("add", "1");
            AdminPage.this.startActivity(mainIntent);
            AdminPage.this.finish();
        });
        btnExit.setOnClickListener(view -> {
            Intent mainIntent = new Intent(AdminPage.this, MainActivity.class);
            AdminPage.this.startActivity(mainIntent);
            AdminPage.this.finish();
        });
    }

}
