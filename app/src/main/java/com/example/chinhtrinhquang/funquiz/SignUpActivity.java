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

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    Button btnSignUp;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirmPassword;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        Inflate();
        String DB_Path = "/data/data/com.example.chinhtrinhquang.funquiz/databases/";
        String Database_Name = "xxx.sqlite";
        database = new Database(this,Database_Name,null,1,DB_Path);

        database.queryData("CREATE TABLE IF NOT EXISTS ACCOUNT(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME text(20), PASSWORD text(20))");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cfpw = txtConfirmPassword.getText().toString();
                String pw = txtPassword.getText().toString();
                if(cfpw.equals(pw)){
                    String sql = "Select * from ACCOUNT where USERNAME = '" + txtUsername.getText().toString()+"'";

                    Cursor getData = database.getData(sql);
                    if(getData.getCount()>0){
                        Toast.makeText(getContext(),"Username is existed",Toast.LENGTH_SHORT).show();
                        getData.close();
                    }else {

                        getData.close();
                        sql = "INSERT INTO ACCOUNT(USERNAME,PASSWORD) VALUES ('"+txtUsername.getText().toString()+ "','" + txtPassword.getText().toString()+"')";
                        database.queryData(sql);
                        Toast.makeText(getContext(),"Sign up successfully",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getContext(),SignInActivity.class);
                        startActivity(i);
                    }

                }else{
                    Toast.makeText(getContext(),"Password is not matched, please type again",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    void Inflate(){

        btnSignUp = (Button) this.findViewById(R.id.btnSignUp);
        txtUsername = (EditText)this.findViewById(R.id.txtUsername);
        txtPassword = (EditText)this.findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText)this.findViewById(R.id.txtConfirmPassword);
    }
    Context getContext(){
        return this;
    }
}
