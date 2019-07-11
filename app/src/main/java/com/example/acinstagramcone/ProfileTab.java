package com.example.acinstagramcone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName,edtBio,edtProfession,edtHobbies,edtFavouriteSport;
    private  Button btnUpdateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtProfileName=view.findViewById(R.id.edtProfileName);
        edtBio=view.findViewById(R.id.edtBio);
        edtProfession=view.findViewById(R.id.edtProfession);
        edtHobbies=view.findViewById(R.id.edtHobbies);
        edtFavouriteSport=view.findViewById(R.id.edtFavoriteSport);
        btnUpdateInfo=view.findViewById(R.id.btnUpdateInfo);

        final ParseUser appUser=ParseUser.getCurrentUser();

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appUser.put("profileName",edtProfileName.getText().toString());
                appUser.put("profileBio",edtBio.getText().toString());
                appUser.put("profileProfession",edtProfession.getText().toString());
                appUser.put("profileHobbies",edtHobbies.getText().toString());
                appUser.put("profileFavSport",edtFavouriteSport.getText().toString());

                appUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(edtProfileName.getText().toString().equals("") || edtBio.getText().toString().equals("") ||
                                edtProfession.getText().toString().equals("") || edtHobbies.getText().toString().equals("") ||
                                    edtFavouriteSport.getText().toString().equals("")){

                            FancyToast.makeText(getContext(), "All Fields are Required", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();


                        }
                        else {

                            if (e == null) {

                                FancyToast.makeText(getContext(), "Info Updated", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                            } else {

                                FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();

                            }
                        }

                    }
                });
            }
        });
        return view;
    }

}
