package com.example.acinstagramcone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
    private TextView txtGetData;
    private EditText edtKickSpeed,edtKickPower,edtName,edtPunchPower,edtPunchSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

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
}
