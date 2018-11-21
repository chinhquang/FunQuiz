package com.example.chinhtrinhquang.funquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {
    Button btnPlay;
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
    }
    void Inflate(){
        btnPlay = (Button) findViewById(R.id.btnPlay);
    }
}
