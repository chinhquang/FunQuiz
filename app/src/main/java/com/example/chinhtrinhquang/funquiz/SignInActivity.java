package com.example.chinhtrinhquang.funquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class SignInActivity extends AppCompatActivity {

    EditText username;
    EditText passwd;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        Inflate();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.logged_in, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), QuizAcitivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    Context getContext() {return this;}

    void Inflate() {
        username = (EditText)findViewById(R.id.txtUsername);
        passwd = (EditText)findViewById(R.id.txtPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
    }
<<<<<<< Updated upstream
}
=======


}
>>>>>>> Stashed changes
