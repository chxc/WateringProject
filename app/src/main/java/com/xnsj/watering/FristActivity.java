package com.xnsj.watering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FristActivity extends AppCompatActivity {

    private View frist_test;
    private long lastTime;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);
        frist_test=(View) findViewById(R.id.frist_test);
        activity=this;
        frist_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long currentTime=System.currentTimeMillis();

                if(currentTime-lastTime<300)
                    startActivity(new Intent(activity,MainActivity.class));
                lastTime=currentTime;
            }
        });
    }
}