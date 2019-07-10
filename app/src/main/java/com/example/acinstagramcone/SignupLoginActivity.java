package com.example.acinstagramcone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignupLoginActivity extends AppCompatActivity {

    Button btnSignUp,btnLogIn;
    EditText loginUsername,loginPassword,SignUpUsername,SignUpPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        btnSignUp=findViewById(R.id.btnSignUp);
        btnLogIn=findViewById(R.id.btnLogin);

        loginUsername=findViewById(R.id.logUserName);
        loginPassword=findViewById(R.id.logPassword);

        SignUpUsername=findViewById(R.id.SignUpUserName);
        SignUpPassword=findViewById(R.id.SignUpPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseUser appUser= new ParseUser();
                appUser.setUsername(SignUpUsername.getText().toString());
                appUser.setPassword(SignUpPassword.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(SignupLoginActivity.this,appUser.get("username")+ " is Signed Up Successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            Intent A=new Intent(SignupLoginActivity.this,WelcomeActivity.class);
                            A.putExtra("UserName",SignUpUsername.getText().toString());
                            SignUpUsername.setText("");
                            SignUpPassword.setText("");
                            startActivity(A);
                        }
                        else{
                            FancyToast.makeText(SignupLoginActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseUser.logInInBackground(loginUsername.getText().toString(), loginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(user != null && e == null){
                            FancyToast.makeText(SignupLoginActivity.this,user.get("username")+ " is Logged In Successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            Intent A=new Intent(SignupLoginActivity.this,WelcomeActivity.class);
                            A.putExtra("UserName",loginUsername.getText().toString());
                            loginUsername.setText("");
                            loginPassword.setText("");
                            startActivity(A);

                        }
                        else{
                            FancyToast.makeText(SignupLoginActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }

                    }
                });

            }
        });

    }
}
