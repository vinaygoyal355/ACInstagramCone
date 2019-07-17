package com.example.acinstagramcone;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    private TextView txtLoadingUsers;

    public UserTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_tab, container, false);
        txtLoadingUsers=view.findViewById(R.id.txtloadingusers);

        listView=view.findViewById(R.id.listView);
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,arrayList);


        listView.setOnItemClickListener(UserTab.this);
        listView.setOnItemLongClickListener(UserTab.this);

        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();

        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().get("username")).toString();

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if(e == null){

                    if(objects.size()>0){


                        for(ParseUser user: objects){

                            arrayList.add(user.getUsername());

                        }
                        listView.setAdapter(arrayAdapter);
                        txtLoadingUsers.animate().alpha(0).setDuration(2000);
                        listView.setVisibility(View.VISIBLE);
                    }
                }

                else{
                    if(e != null) {
                        FancyToast.makeText(getContext(), "Unknown Error: " + e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                    }
                }

            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

        Intent A=new Intent(getContext(),UsersPosts.class);
        A.putExtra("username",arrayList.get(i));
        startActivity(A);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereEqualTo("username",arrayList.get(i));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if(e == null && user != null){

                   // FancyToast.makeText(getContext(),user.get("profileProfession")+"",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

                   final PrettyDialog prettyDialog= new PrettyDialog(getContext());
                   prettyDialog.setTitle("User Info").setMessage("Bio: "+ user.get("profileBio") + "\n"
                                        + "Profession: "+ user.get("profileProfession")+"\n"
                                        + "Hobbies: "+ user.get("profileHobbies")+"\n"
                                        + "Favourite Sport: "+ user.get("profileFavSport"))
                                        .setIcon(R.drawable.person)
                                        .addButton("OK"
                                                , R.color.pdlg_color_white  //button text Color
                                                ,R.color.pdlg_color_green   //button background color
                                                , new PrettyDialogCallback() {
                                                    @Override
                                                    public void onClick() {
                                                        prettyDialog.dismiss();
                                                    }
                                                }).show();
                }
            }
        });

        return true;
    }
}
