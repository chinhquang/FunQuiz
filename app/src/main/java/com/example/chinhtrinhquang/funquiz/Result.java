package com.example.chinhtrinhquang.funquiz;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends AppCompatActivity {

    private TextView score;
    private Button ok;

    @Override
    protected void onCreate(final Bundle savedInstantState)
    {
        super.onCreate(savedInstantState);
        setContentView(R.layout.result);

        score = (TextView) findViewById(R.id.txtScore);
        //score.setText(QuizAcitivity.score);

        ok = (Button) findViewById(R.id.btnOK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Result.this, R.string.back_home, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
