package com.example.acinstagramcone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPosts extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        linearLayout=findViewById(R.id.linearLayout);

        Intent receivedIntent=getIntent();
        String receivedUserName= receivedIntent.getStringExtra("username");

        setTitle(receivedUserName+"'s posts");

        ParseQuery<ParseObject> parseQuery=new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUserName);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog progressDialog=new ProgressDialog(UsersPosts.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null && objects.size() > 0){

                    for(ParseObject obj: objects){

                        final TextView postDescription=new TextView(UsersPosts.this);
                        postDescription.setText(obj.get("image_des")+"");
                        ParseFile postPicture = (ParseFile) obj.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if(data != null && e == null){

                                    Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView=new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams imageview_params=new LinearLayout.LayoutParams
                                            (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageview_params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageview_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params=new LinearLayout.LayoutParams
                                            (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5,5,5,5);
                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.BLUE);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);
                                    progressDialog.dismiss();

                                }
                            }
                        });
                    }

                }

                else{

                    if(e != null) {
                        FancyToast.makeText(getApplicationContext(), "Unknown Error: " + e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                    }
                    else {
                        FancyToast.makeText(getApplicationContext(), "The User have no post yet", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                        finish();
                    }
                    progressDialog.dismiss();
                }

            }
        });
    }
}
