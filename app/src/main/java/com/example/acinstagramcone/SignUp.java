package com.example.acinstagramcone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
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
                        FancyToast.makeText(SignUp.this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true);
                    }
                    else{
                        Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
}
