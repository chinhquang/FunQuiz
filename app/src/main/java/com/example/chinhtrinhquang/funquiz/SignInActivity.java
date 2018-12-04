package com.example.chinhtrinhquang.funquiz;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class SignInActivity extends AppCompatActivity {
    public static String username;
    public static String password;
    EditText txtUsername;
    EditText txtPassword;
    Button btnSignIn;
    Button btnBack;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        Inflate();
        String DB_Path = "/data/data/com.example.chinhtrinhquang.funquiz/databases/";
        String Database_Name = "xxx.sqlite";
        database = new Database(this,Database_Name,null,1,DB_Path);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Logging in",Toast.LENGTH_SHORT).show();
                String pw = txtPassword.getText().toString();
                String us = txtUsername.getText().toString();
                if(us.length()==0 || pw.length()==0){
                    Toast.makeText(getContext(),"Fields cannot be null",Toast.LENGTH_SHORT).show();
                }
                else {
                    String sql = "Select * from ACCOUNT where USERNAME = '" + us + "' and "+"PASSWORD = '"+pw+"'";
                    Cursor data = database.getData(sql);
                    if (data.getCount() >=1){
                        SignInActivity.password = pw;
                        SignInActivity.username = us;
                        Toast.makeText(getContext(),"Loggin successfully",Toast.LENGTH_SHORT).show();
                        data.close();

                        Intent intent = new Intent(getApplication(), OptionsActivity.class);
                        startActivity(intent);
                    }else{
                        data.close();
                        Toast.makeText(getContext(),"Loggin unsuccessfully",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    Context getContext() {return this;}

    void Inflate() {
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnBack = (Button) findViewById(R.id.btnBack);
    }

}

