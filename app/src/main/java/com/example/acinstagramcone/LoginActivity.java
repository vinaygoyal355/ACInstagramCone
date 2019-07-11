package com.example.acinstagramcone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import static java.lang.System.exit;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin,btnCancel;
    private EditText edtEmail,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail=findViewById(R.id.edtLoginEmail);
        edtPassword=findViewById(R.id.edtLoginPassword);

       /* edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){

                    onClick(btnLogin);

                }

                return false;
            }
        });*/

        btnLogin=findViewById(R.id.btnLogIn);

        if(ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtEmail.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {

                    FancyToast.makeText(LoginActivity.this,"Email And Password are Required", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                }
                else{

                    final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Loging In");

                    ParseUser.logInInBackground(edtEmail.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {

                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if (user != null && e == null) {

                                FancyToast.makeText(LoginActivity.this, user.getUsername() + " is Signed Up Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                edtEmail.setText("");
                                edtPassword.setText("");
                                progressDialog.dismiss();

                            } else {

                                FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                                progressDialog.dismiss();
                            }

                        }
                    });

                }
            }
        });

        btnCancel=findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit(0);
            }
        });

    }

    public void rootLayoutIsTappedLogin(View view){

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
