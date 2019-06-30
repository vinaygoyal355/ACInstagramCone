package com.example.acinstagramcone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void hello_worldTapped(View view){
    /*    ParseObject boxer=new ParseObject("Boxer");
        boxer.put("punch_speed",200);
        boxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(SignUp.this,"Boxer Object is saved successfully",Toast.LENGTH_LONG).show();
                }
            }
        });*/

        ParseObject KickBoxer=new ParseObject("KickBoxer");
        KickBoxer.put("Name","Vinay");
        KickBoxer.put("KickSpeed",100);
        KickBoxer.put("KickPower",200);
        KickBoxer.put("PunchSpeed",300);
        KickBoxer.put("PunchPower",400);
        KickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(SignUp.this,"KickBoxer is saved successfully",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
