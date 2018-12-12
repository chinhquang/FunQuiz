package com.example.chinhtrinhquang.funquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {
    Button btnPlay;
    Button btnSetting;
    Button btnHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        Inflate();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),QuizAcitivity.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplication(),Settings.class);
                startActivity(intent2);
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplication(),Help.class);
                startActivity(intent3);
            }
        });
    }
    void Inflate(){
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnSetting = (Button) findViewById(R.id.btnSetting);
        btnHelp = (Button) findViewById(R.id.btnHelp);
    }
}
