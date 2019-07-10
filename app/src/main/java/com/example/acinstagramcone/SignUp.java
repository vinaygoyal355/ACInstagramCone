package com.example.acinstagramcone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave,btnGetAllKickBoxer,switchToAnotherActivity;
    private TextView txtGetData;
    private String allKickBoxers;
    private EditText edtKickSpeed,edtKickPower,edtName,edtPunchPower,edtPunchSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchToAnotherActivity=findViewById(R.id.switchtoanotheractivity);
        switchToAnotherActivity.setOnClickListener(this);

        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnGetAllKickBoxer=findViewById(R.id.btnGetAllKickBoxer);
        btnGetAllKickBoxer.setOnClickListener(this);

        edtName=findViewById(R.id.edtName);
        edtKickPower=findViewById(R.id.edtKickPower);
        edtKickSpeed=findViewById(R.id.edtKickSpeed);
        edtPunchPower=findViewById(R.id.edtPunchPower);
        edtPunchSpeed=findViewById(R.id.edtPunchSpeed);

        txtGetData=findViewById(R.id.getDataFromServer);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("ZAGwbBz5rC", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e==null){

                             txtGetData.setText(object.get("Name").toString()+ "- PunchPower: "+ object.get("PunchPower")
                                     + "- PunchSpeed: "+ object.get("PunchSpeed") + "- KickPower: "+ object.get("KickPower")
                                     + "- KickSpeed: "+ object.get("KickSpeed"));

                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==btnSave.getId()){

            final ParseObject KickBoxer=new ParseObject("KickBoxer");

            String a;
            int b,c,d,e;
            a=edtName.getText().toString();
            b=Integer.parseInt(edtKickSpeed.getText().toString());
            c=Integer.parseInt(edtKickPower.getText().toString());
            d=Integer.parseInt(edtPunchSpeed.getText().toString());
            e=Integer.parseInt(edtPunchPower.getText().toString());

            Log.i("Punch","Running");

            KickBoxer.put("Name",a);
            KickBoxer.put("KickSpeed",b);
            KickBoxer.put("PunchPower",e);
            KickBoxer.put("KickPower",c);
            KickBoxer.put("PunchSpeed",d);
            KickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        FancyToast.makeText(SignUp.this,KickBoxer.get("Name")+" is saved to server",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    }
                    else{
                        FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                }
            });

        }

        // Retrieving data from parse server

        else if(view.getId()==btnGetAllKickBoxer.getId()){

            allKickBoxers="";

            ParseQuery<ParseObject> queryAll=ParseQuery.getQuery("KickBoxer");

            // for retrieving a single object we use saveInBackground and for retrieving more than one object we use findInBackground

           // queryAll.whereGreaterThan("PunchPower",100);
            queryAll.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if(e==null){

                       if(objects.size() > 0){

                           for(ParseObject kickBoxer : objects){

                               allKickBoxers= allKickBoxers+ kickBoxer.get("Name")+ "\n";

                           }

                           FancyToast.makeText(SignUp.this,allKickBoxers,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();


                       }

                       else{

                           FancyToast.makeText(SignUp.this,"List Objects Are Empty",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();


                       }

                    }

                }
            });

        }
        else if(view.getId()==switchToAnotherActivity.getId()){

            Intent A=new Intent(SignUp.this,SignupLoginActivity.class);
            startActivity(A);

        }
    }
}
