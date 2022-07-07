package com.example.ideabytes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

       // getSupportActionBar().hide();

        Thread t = new Thread(){
            public void run(){
                try{
                    sleep(4500);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(Home_page.this,Login.class));
                    finish();
                }
            }

        };t.start();
    }
}