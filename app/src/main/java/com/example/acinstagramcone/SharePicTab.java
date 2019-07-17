package com.example.acinstagramcone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePicTab extends Fragment implements View.OnClickListener{

    private ImageView imageView;
    private EditText edtDescription;
    private Button btnShareImage;
    private Bitmap receiveImageBitmap;

    public SharePicTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_share_pic_tab, container, false);

        imageView=view.findViewById(R.id.imgShare);
        edtDescription=view.findViewById(R.id.edtDescription);
        btnShareImage=view.findViewById(R.id.btnShareImage);

        imageView.setOnClickListener(SharePicTab.this);
        btnShareImage.setOnClickListener(SharePicTab.this);

        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==imageView.getId()){

            if(android.os.Build.VERSION.SDK_INT >=23 && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
            }
            else{

                getChosenImage();

            }

        }
        else if(view.getId()==btnShareImage.getId()){

            if(receiveImageBitmap != null){

                if(edtDescription.getText().toString().equals("")){

                    FancyToast.makeText(getContext(),"Error: You must speicfy description",FancyToast.LENGTH_SHORT,FancyToast.WARNING,true).show();
                }
                else{

                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    receiveImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] bytes= byteArrayOutputStream.toByteArray();
                    ParseFile parseFile=new ParseFile("img.png",bytes);
                    ParseObject parseObject=new ParseObject("Photo");
                    parseObject.put("picture",parseFile);
                    parseObject.put("image_des",edtDescription.getText().toString());
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    final ProgressDialog progressDialog=new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e == null){
                                FancyToast.makeText(getContext(),"Done!!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                progressDialog.dismiss();
                            }
                            else {
                                FancyToast.makeText(getContext(),"Unknown Error: "+e.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.WARNING,true).show();
                            }

                        }
                    });

                }

            }
            else{

                FancyToast.makeText(getContext(),"Error: You must select an image", FancyToast.LENGTH_SHORT,FancyToast.WARNING,true).show();

            }

        }

    }

    private void getChosenImage() {

        //FancyToast.makeText(getContext(),"Access the Images", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
        Intent A=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(A,2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1000){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getChosenImage();

            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2000){

            if(resultCode == Activity.RESULT_OK){

                try{

                    Uri selectedImage= data.getData();
                    String[] filePathColumn={MediaStore.Images.Media.DATA};
                    Cursor cursor= getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                    String PicturePath=cursor.getString(columnIndex);
                    cursor.close();
                    receiveImageBitmap= BitmapFactory.decodeFile(PicturePath);
                    imageView.setImageBitmap(receiveImageBitmap);

                }
                catch (Exception e){

                    e.printStackTrace();

                }

            }

        }

    }
}
