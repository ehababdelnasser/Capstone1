package com.example.lenovo.my_app_mov2;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Lenovo on 04/2/2018.
 */

public class WidgetListProvider implements RemoteViewsService.RemoteViewsFactory {




    private ArrayList<NoteAttr> noteAttrList = new ArrayList();


    private Context con;
    private int appWidgetId;
    ChildEventListener mChildEventListner;
    private DatabaseReference mMessagesDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;

    // auth
    FirebaseAuth mAuth;

    public WidgetListProvider(Context context, Intent intent) {
        this.con = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
//        if (user == null)
//            return null;
        try {
            String currentUserEmail = user.getEmail().replace(".", "_");
            mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("message/" + currentUserEmail);

        }catch (Exception e) {
        }
        mChildEventListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                NoteAttr noteAttr = dataSnapshot.getValue(NoteAttr.class);
                noteAttrList.add(noteAttr);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        try {
            mMessagesDatabaseReference.addChildEventListener(mChildEventListner);
        }catch (Exception e){

        }
    }

    @Override
    public void onCreate() {

    }

    /*
    * getView of Adapter where instead of View we return RemoteViews
    */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                con.getPackageName(), R.layout.list_item);

        remoteView.setTextViewText(R.id.Tit, noteAttrList.get(position).getTitle());
        remoteView.setTextViewText(R.id.Steps, noteAttrList.get(position).getStep());


        return remoteView;
    }

    @Override
    public int getCount() {
        return noteAttrList.size();
    }



    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }}
